package com.ss.abtest.controller;

import com.ss.abtest.pojo.RequestResult;
import com.ss.abtest.pojo.domain.*;
import com.ss.abtest.pojo.dto.LayerDto;
import com.ss.abtest.pojo.dto.RefluxDto;
import com.ss.abtest.pojo.dto.TableDto;
import com.ss.abtest.pojo.status.ApplyStatus;
import com.ss.abtest.pojo.vo.CompanyTable;
import com.ss.abtest.pojo.vo.CreateApplyTable;
import com.ss.abtest.pojo.vo.JoinApplyTable;
import com.ss.abtest.service.CompanyService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/company/create/apply")
    public String listCompanyCreateApply() {
        TableDto<CreateApplyTable> tableDto = companyService.listCompanyCreateApply();
        return RequestResult.successResult(tableDto);
    }

    @GetMapping("/companys")
    public String listCompany() {
        TableDto<CompanyTable> tableDto = companyService.listAllCompany();
        return RequestResult.successResult(tableDto);
    }

    @GetMapping("/company/reflux")
    public String getReflux(@RequestParam long companyId) {
        Reflux reflux = companyService.getReflux(companyId);
        return RequestResult.successResult(reflux);
    }

    @PostMapping("/company/create")
    public String createCompany(@RequestBody CompanyCreateApply apply) {
        boolean success = companyService.createCompany(apply);
        return RequestResult.successResult(success);
    }

    @PostMapping("/company/join/create")
    public String createJoin(@RequestBody CompanyJoinApply joinApply) {
        boolean success = companyService.createJoinApply(joinApply);
        return RequestResult.successResult(success);
    }

    @PostMapping("/company/reflux/edit")
    public String editReflux(@RequestBody RefluxDto refluxDto) {
        boolean success = companyService.editReflux(refluxDto);
        return RequestResult.successResult(success);
    }

    @GetMapping("/company/layers")
    public String listLayerByCompanyId(@RequestParam long companyId) {
        List<Layer> layer = companyService.listLayerByCompanyId(companyId);
        return RequestResult.successResult(layer);
    }

    @PostMapping("/company/layer/create")
    public String createLayer(@RequestBody LayerDto layer) {
        boolean success = companyService.createLayer(layer);
        return RequestResult.successResult(success);
    }


    @GetMapping("/company/join/apply")
    public String listJoinApplyByCompanyId(long company_id) {
        TableDto<JoinApplyTable> tableDto = companyService.listJoinApplyByCompanyId(company_id);
        return RequestResult.successResult(tableDto);
    }

    @PostMapping("/company/join/apply/agree")
    public String agreeJoinApplyById(long apply_id) {
        boolean flag = companyService.editJoinApplyStatus2Agree(apply_id);
        return RequestResult.successResult(flag);
    }

    @PostMapping("/company/join/apply/refuse")
    public String refusedJoinApplyById(long apply_id) {
        boolean flag = companyService.editJoinApplyStatus(apply_id, ApplyStatus.REFUSE);
        return RequestResult.successResult(flag);
    }


    @PostMapping("/company/create/apply/agree")
    public String agreeCreateApplyById(long apply_id) {
        boolean flag = companyService.editCreateApplyStatus2Agree(apply_id);
        return RequestResult.successResult(flag);
    }

    @PostMapping("/company/create/apply/refuse")
    public String refusedCreateApplyById(long apply_id) {
        boolean flag = companyService.editCreateApplyStatus(apply_id, ApplyStatus.REFUSE);
        return RequestResult.successResult(flag);
    }

    @PostMapping("/company/layer/delete")
    public String deleteLayer(long layerId) {
        boolean flag =  companyService.deleteLayer(layerId);
        return RequestResult.successResult(flag);
    }
}
