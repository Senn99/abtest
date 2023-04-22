package com.ss.abtest.service.impl;

import com.ss.abtest.mapper.LayerMapper;
import com.ss.abtest.pojo.domain.Layer;
import com.ss.abtest.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author senn
 * @since 2023/4/20 21:36
 **/
@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    LayerMapper layerMapper;

    @Override
    public List<Layer> listLayerByCompanyId(long companyId) {
        return layerMapper.listLayerByCompanyId(companyId);
    }
}
