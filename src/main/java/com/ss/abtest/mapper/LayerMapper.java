package com.ss.abtest.mapper;

import com.ss.abtest.pojo.domain.FlightTraffic;
import com.ss.abtest.pojo.domain.Layer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author senn
 * @since 2023/4/2 20:44
 **/
@Mapper
public interface LayerMapper {

    int updateLayerTraffic(int flightTraffic, long id);

    int getTraffic(@Param("id") Long id);

    List<Layer> listLayer();

    List<FlightTraffic> getFlightTraffic(@Param("layerId") Long layerId);

    List<Layer> listLayerByCompanyId(@Param("companyId")long companyId);
}
