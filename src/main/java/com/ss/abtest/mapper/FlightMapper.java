package com.ss.abtest.mapper;

import com.ss.abtest.pojo.domain.Flight;
import com.ss.abtest.pojo.domain.Layer;
import com.ss.abtest.pojo.domain.Version;
import com.ss.abtest.pojo.dto.FlightDto;
import com.ss.abtest.pojo.vo.Bucket;
import com.ss.abtest.pojo.vo.FlightUser;
import org.apache.ibatis.annotations.Mapper;

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

    List<Flight> listFlightByCompanyId(long companyId);

    Layer getLayerById(long id);

    List<FlightUser> listFlightUserById(long flightId);

    List<Version> getVersionByFlightId(Long layerId);
}
