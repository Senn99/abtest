package com.ss.abtest.service;

import com.ss.abtest.pojo.domain.Layer;

import java.util.List;

/**
 * @author senn
 * @since 2023/4/20 21:35
 **/
public interface CompanyService {
    List<Layer> listLayerByCompanyId(long companyId);
}
