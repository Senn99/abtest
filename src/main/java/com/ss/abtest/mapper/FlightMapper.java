package com.ss.abtest.mapper;

import com.ss.abtest.pojo.domain.Flight;
import com.ss.abtest.pojo.domain.FlightTestUser;
import com.ss.abtest.pojo.domain.Layer;
import com.ss.abtest.pojo.domain.Version;
import com.ss.abtest.pojo.vo.Bucket;
import com.ss.abtest.pojo.vo.FlightTable;
import com.ss.abtest.pojo.vo.FlightUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author senn
 * @since 2023/4/2 20:22
 **/
@Mapper
public interface FlightMapper {

    int addFlight(Flight flight);

    int addVersion(Version version);

    List<Integer> getLayerTraffic(long layerId);

    int addFlightTraffic(Bucket bucket);

    int addFlightUser(FlightUser flightUser);

    List<FlightTable> listFlightByCompanyId(long companyId, int page, int limit);

    Layer getLayerById(long id);

    List<FlightUser> listFlightUserById(long flightId);

    List<Version> getVersionByFlightId(Long flightId);

    List<FlightTestUser> listFlightTestUsers(long layerId);

    Flight getFlightById(@Param("flightId") Long flightId);

    List<Flight> listFlight();

    int countFlightByCompanyId(long companyId);

    List<FlightTestUser> listFlightTestUsersByFlightId(@Param("flightId")Long flightId);

    List<Version> listVersionsByFlightId(@Param("flightId")Long flightId);

    void addFlightTestUser(FlightTestUser testUser);

    void editFlightStatus(long flight_id, int status);

    void deleteFlightTraffic(long flight_id);

    void updateLayerTraffic(@Param("layer_id") long layer_id, @Param("traffic") int traffic);
}
