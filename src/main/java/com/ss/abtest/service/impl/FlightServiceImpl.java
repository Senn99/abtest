package com.ss.abtest.service.impl;

import com.ss.abtest.exception.IllegalParamException;
import com.ss.abtest.mapper.FlightMapper;
import com.ss.abtest.mapper.LayerMapper;
import com.ss.abtest.mapper.UserMapper;
import com.ss.abtest.pojo.domain.*;
import com.ss.abtest.pojo.dto.FlightDto;
import com.ss.abtest.pojo.dto.TableDto;
import com.ss.abtest.pojo.status.FlightStatus;
import com.ss.abtest.pojo.status.Position;
import com.ss.abtest.pojo.vo.Bucket;
import com.ss.abtest.pojo.vo.FlightTable;
import com.ss.abtest.pojo.vo.FlightUser;
import com.ss.abtest.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

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

    @Override
    public void editFlightStatus(long flight_id, FlightStatus status) {
        flightMapper.editFlightStatus(flight_id, status.getValue());
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

    private void updateLayerTraffic(long flight_id) {
        Flight flight = flightMapper.getFlightById(flight_id);
        flightMapper.updateLayerTraffic(flight.getLayerId(), flight.getTraffic());
    }

    private void addUserToFlight(FlightDto flightDto, List<FlightUser> list) {
        if (list != null && !list.isEmpty()) {
            for (FlightUser flightUser : list) {
                if (flightUser.getPosition() == Position.CREATER.getValue()) {
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
