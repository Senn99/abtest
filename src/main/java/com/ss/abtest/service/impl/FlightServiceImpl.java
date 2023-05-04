package com.ss.abtest.service.impl;

import com.ss.abtest.exception.IllegalParamException;
import com.ss.abtest.mapper.FlightMapper;
import com.ss.abtest.mapper.LayerMapper;
import com.ss.abtest.mapper.UserMapper;
import com.ss.abtest.pojo.domain.*;
import com.ss.abtest.pojo.dto.FlightDto;
import com.ss.abtest.pojo.dto.FlightTrafficDto;
import com.ss.abtest.pojo.dto.TableDto;
import com.ss.abtest.pojo.status.FlightStatus;
import com.ss.abtest.pojo.status.Position;
import com.ss.abtest.pojo.vo.Bucket;
import com.ss.abtest.pojo.vo.FlightTable;
import com.ss.abtest.pojo.vo.FlightUser;
import com.ss.abtest.service.FlightService;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
public class FlightServiceImpl implements FlightService {

    @Autowired
    FlightMapper flightMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    LayerMapper layerMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public FlightDto createFlight(FlightDto flightDto) {
        //1、参数校验。
        verifyFlightParams(flightDto);
        //2、配置校验。
        verifyLayerConfig(flightDto);
        //2、数据库中创建实验
        addFlight(flightDto);
        //3、关联实验用户。
        addFlightUser(flightDto);
        //4、关联实验测试用户
        addFlightTestUser(flightDto);
        //5、数据库中创建实验组
        addFlightVersions(flightDto);
        //6、分配流量
        distributeTraffic(flightDto);

        return flightDto;
    }

    /**
     * 添加测试用户
     */
    private void addFlightTestUser(FlightDto flightDto) {
        List<FlightTestUser> testUsers = flightDto.getTestUsers();
        testUsers.forEach(ts -> flightMapper.addFlightTestUser(ts));
    }

    /**
     * 不同层配置检查
     */
    private void verifyLayerConfig(FlightDto flightDto) {
        List<Flight> flights = flightMapper.listFlight();
        Map<String, String> map = flightDto.getFlight().getFilterMap();
        for (Flight flight : flights) {
            // 不同层之间要配置检查
            if (!flight.getLayerId().equals(flightDto.getLayerId())) {
                Map<String, String> filterMap = flight.getFilterMap();
                if (filterMap.isEmpty()) {
                    continue;
                }
                map.forEach((k, v) -> {
                    if (filterMap.containsKey(k)) {
                        throw new IllegalParamException("配置冲突... filter: " + k);
                    }
                });
            }
        }
    }

    @Override
    public TableDto<FlightTable> listFlightByCompanyId(long companyId, int page, int limit) {
        List<FlightTable> flightTables = flightMapper.listFlightByCompanyId(companyId, (page - 1) * limit, limit);
        int total = flightMapper.countFlightByCompanyId(companyId);
        TableDto<FlightTable> flightTableDto = new TableDto<>();
        flightTableDto.setList(flightTables);
        flightTableDto.setTotal(total);
        return flightTableDto;
    }

    @Override
    public FlightDto getFlightById(String flight_id) {
        Flight flight = flightMapper.getFlightById(Long.parseLong(flight_id));
        FlightDto flightDto = new FlightDto();
        Long flightId = flight.getFlightId();
        flightDto.setFlight(flight);
        flightDto.setFlightId(flightId);
        Layer layer = flightMapper.getLayerById(flight.getLayerId());
        flightDto.setLayer(layer);
        User owner = userMapper.getUserById(flight.getOwnerId());
        flightDto.setOwner(owner);
        List<FlightUser> list = flightMapper.listFlightUserById(flightId);
        flightDto.setUsers(list);
        if (flight.getStatus() == FlightStatus.TEST.getValue()) {
            List<FlightTestUser> testUsers = flightMapper.listFlightTestUsersByFlightId(flightId);
            flightDto.setTestUsers(testUsers);
        }
        List<Version> versions = flightMapper.listVersionsByFlightId(flightId);
        flightDto.setVersions(versions);
        return flightDto;
    }


    public void editFlightStatus(long flight_id, FlightStatus status) {
        flightMapper.editFlightStatus(flight_id, status.getValue());
    }

    @Transactional
    @Override
    public void editFlightStatus2Run(long flight_id) {
        Flight flight = flightMapper.getFlightById(flight_id);
        if (flight.getStatus() == FlightStatus.TEST.getValue() || flight.getStatus() == FlightStatus.PAUSED.getValue()) {
            if (flight.getStatus() == FlightStatus.TEST.getValue()) {
                flightMapper.updateLayerTest(flight.getLayerId(), -1);
            }
            editFlightStatus(flight_id, FlightStatus.NORMAL);
        } else {
            log.warn("editFlightStatus2Run # edit flight status error flight status : {}", FlightStatus.getStatus(flight.getStatus()));
        }

    }

    @Override
    public void editFlightStatus2Pause(long flight_id) {
        Flight flight = flightMapper.getFlightById(flight_id);
        if (flight.getStatus() == FlightStatus.CREATED.getValue()) {
            log.warn("editFlightStatus2Pause # edit flight status error flight status : {}", FlightStatus.getStatus(flight.getStatus()));
            return;
        }
        editFlightStatus(flight_id, FlightStatus.PAUSED);
    }

    @Transactional
    @Override
    public void editFlightStatus2Test(long flight_id) {
        Flight flight = flightMapper.getFlightById(flight_id);
        if (flight.getStatus() != FlightStatus.CREATED.getValue()) {
            log.warn("editFlightStatus2Test # edit flight status error flight status : {}", FlightStatus.getStatus(flight.getStatus()));
            return;
        }
        editFlightStatus(flight_id, FlightStatus.TEST);
        flightMapper.updateLayerTest(flight.getLayerId(), 1);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void endFlight(long flight_id) {
        //1、更改实验状态
        flightMapper.editFlightStatus(flight_id, FlightStatus.END.getValue());
        //2、删除实验流量分配
        flightMapper.deleteFlightTraffic(flight_id);
        //3、更改实验层剩余流量
        updateLayerTraffic(flight_id);
    }

    @Override
    public TableDto<FlightTable> listFlightWithFilter(long companyId, String name, Integer status) {
        StringBuilder sb = new StringBuilder();
        if (Strings.isNotEmpty(name)) {
            sb.append(" and name like '%" + name + "%'");
        }
        if (status != null) {
            sb.append(" and status = " + status);
        }
        log.info("FlightWithFilter : {}", sb.toString());
        List<FlightTable> flightTables = flightMapper.listFlightWithFilter(companyId, sb.toString());
        TableDto<FlightTable> flightTableDto = new TableDto<>();
        flightTableDto.setList(flightTables);
        return flightTableDto;
    }

    @Transactional
    @Override
    public boolean updateFlightTraffic(FlightTrafficDto flightTrafficDto) {
        Long flightId = flightTrafficDto.getFlightId();
        Long layerId = flightTrafficDto.getLayerId();
        Integer newTraffic = flightTrafficDto.getTraffic();
        log.info("updateFlightTraffic # flightId :{}, layerId: {}, traffic: {}", flightId, layerId, newTraffic);
        Flight flightById = flightMapper.getFlightById(flightId);
        Integer oldTraffic = flightById.getTraffic();
        //相等则放弃
        if (oldTraffic.equals(newTraffic)) {
            return false;
        }
        Layer layer = layerMapper.getLayerById(flightById.getLayerId());
        //判断剩余流量大小
        if (newTraffic > oldTraffic && layer.getTraffic() < (newTraffic - oldTraffic)) {
            return false;
        }
        List<FlightTraffic> flightTraffic = layerMapper.getFlightTraffic(layerId);
        List<FlightTraffic> flightTrafficList = new ArrayList<>();
        // 获取实验对应流量
        flightTraffic.forEach(ft -> {
            if (ft.getFlightId().equals(flightId)) {
                flightTrafficList.add(ft);
            }
        });

        if (newTraffic < oldTraffic) {
            int sum = oldTraffic - newTraffic;
            for (int i = 0; i < sum; i++) {
                FlightTraffic ft = flightTrafficList.get(i);
                layerMapper.deleteTrafficById(ft.getId());
            }
        } else {
            int sum = newTraffic - oldTraffic;
            int[] arr = new int[1000];
            flightTraffic.forEach(ft -> arr[ft.getBucket()]++);
            for (int i = 0; i < 1000 && sum > 0; i++) {
                if (arr[i] == 0) {
                    Bucket bucket = new Bucket();
                    bucket.setFlightId(flightId);
                    bucket.setLayerId(layerId);
                    bucket.setBucket(i);
                    flightMapper.addFlightTraffic(bucket);
                    sum--;
                }
            }
        }
        //更新 实验 流量
        flightMapper.updateFlightTraffic(flightId, newTraffic);
        //更新 实验层 流量
        flightMapper.updateLayerTraffic(layerId, oldTraffic - newTraffic);
        return true;
    }

    private void updateLayerTraffic(long flight_id) {
        Flight flight = flightMapper.getFlightById(flight_id);
        flightMapper.updateLayerTraffic(flight.getLayerId(), flight.getTraffic());
    }

    private void addUserToFlight(FlightDto flightDto, List<FlightUser> list) {
        if (list != null && !list.isEmpty()) {
            for (FlightUser flightUser : list) {
                if (flightUser.getPosition() == Position.CREATOR.getValue()) {
                    flightDto.setOwner(flightUser.getUser());
                    list.remove(flightUser);
                    break;
                }
            }
            flightDto.setUsers(list);
        }
    }

    /**
     * 关联实验用户。
     *
     * @param flightDto flightDto
     */
    private void addFlightUser(FlightDto flightDto) {
        List<FlightUser> list = flightDto.getFlightUsers();
        list.forEach(user -> flightMapper.addFlightUser(user));
    }

    /**
     * 分配流量
     *
     * @param flightDto flightDto
     */
    private void distributeTraffic(FlightDto flightDto) {
        // 1、检验实验层剩余流量
        checkLayerTraffic(flightDto);
        // 2、获取可分配流量桶 bucket
        List<Bucket> buckets = getDistributiveBucket(flightDto);
        // 3、数据库中创建数据
        buckets.forEach(bucket -> flightMapper.addFlightTraffic(bucket));
        // 4、更新实验层的剩余流量 LayerTraffic = LayerTraffic - FlightTraffic;
        layerMapper.updateLayerTraffic(flightDto.getFlightTraffic(), flightDto.getLayerId());
    }

    /**
     * 获取可分配流量桶
     *
     * @param flightDto flightDto
     * @return List<Bucket>
     */
    private List<Bucket> getDistributiveBucket(FlightDto flightDto) {
        Long flightId = flightDto.getFlightId();
        Long layerId = flightDto.getLayerId();
        int flightTraffic = flightDto.getFlightTraffic();
        List<Integer> list = flightMapper.getLayerTraffic(layerId);
        list.sort(Comparator.comparingInt(o -> o));
        List<Bucket> buckets = new ArrayList<>();
        int index = 0;
        int sum = 0;
        for (int i = 0; i < 1000 && sum < flightTraffic; i++) {
            if (index < list.size() && list.get(index) == i) {
                index++;
            } else {
                Bucket bucket = new Bucket(flightId, layerId, i);
                buckets.add(bucket);
                sum++;
            }
        }
        return buckets;
    }

    private void checkLayerTraffic(FlightDto flightDto) {
        int traffic = layerMapper.getTraffic(flightDto.getLayerId());
        if (traffic < flightDto.getFlightTraffic()) {
            throw new IllegalParamException("layer traffic (" + traffic + ") < flight traffic" + flightDto.getFlightTraffic() + " error");
        }
    }

    /**
     * 添加 实验组
     *
     * @param flightDto flightDto
     */
    private void addFlightVersions(FlightDto flightDto) {
        List<Version> versions = flightDto.getVersionEntry();
        for (Version version : versions) {
            flightMapper.addVersion(version);
        }
    }

    /**
     * 添加实验
     *
     * @param flightDto flightDto
     */
    private void addFlight(FlightDto flightDto) {
        flightMapper.addFlight(flightDto.getFlightEntry());
    }

    /**
     * 检验参数
     *
     * @param flightDto flightDto
     */
    private void verifyFlightParams(FlightDto flightDto) {
        if (flightDto == null) {
            throw new IllegalParamException("flightDto is null");
        }
        flightDto.verifyParams();
    }
}
