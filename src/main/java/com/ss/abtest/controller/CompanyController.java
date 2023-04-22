package com.ss.abtest.controller;

import com.ss.abtest.pojo.RequestResult;
import com.ss.abtest.pojo.domain.Layer;
import com.ss.abtest.service.CompanyService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author senn
 * @since 2023/4/20 21:35
 **/
@Api(tags = "企业相关")
@RestController
public class CompanyController {

    @Autowired
    CompanyService companyService;

    @GetMapping("/company/layers")
    public String listLayerByCompanyId(long company_id) {
        List<Layer> layer = companyService.listLayerByCompanyId(company_id);
        return RequestResult.successResult(layer);
    }
}
