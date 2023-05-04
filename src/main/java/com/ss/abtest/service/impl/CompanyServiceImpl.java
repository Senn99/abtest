package com.ss.abtest.service.impl;

import com.ss.abtest.mapper.CompanyMapper;
import com.ss.abtest.mapper.LayerMapper;
import com.ss.abtest.pojo.domain.CompanyCreateApply;
import com.ss.abtest.pojo.domain.CompanyJoinApply;
import com.ss.abtest.pojo.domain.Layer;
import com.ss.abtest.pojo.domain.Reflux;
import com.ss.abtest.pojo.dto.LayerDto;
import com.ss.abtest.pojo.dto.RefluxDto;
import com.ss.abtest.pojo.dto.TableDto;
import com.ss.abtest.pojo.status.ApplyStatus;
import com.ss.abtest.pojo.vo.CompanyTable;
import com.ss.abtest.pojo.vo.CreateApplyTable;
import com.ss.abtest.pojo.vo.JoinApplyTable;
import com.ss.abtest.service.CompanyService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author senn
 * @since 2023/4/20 21:36
 **/
@Log4j2
@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    LayerMapper layerMapper;

    @Autowired
    CompanyMapper companyMapper;

    @Override
    public List<Layer> listLayerByCompanyId(long companyId) {
        return layerMapper.listLayerByCompanyId(companyId);
    }

    @Override
    public TableDto<JoinApplyTable> listJoinApplyByCompanyId(long companyId) {
        List<JoinApplyTable> companyJoinApplies = companyMapper.listJoinApplyByCompanyId(companyId);
        TableDto<JoinApplyTable> tableDto = new TableDto<>();
        tableDto.setList(companyJoinApplies);
        return tableDto;
    }




    @Override
    public boolean createLayer(LayerDto layer) {
        //1、参数校验
        layer.checkCreateParamsNotNull();
        //2、存入数据库
        return companyMapper.addLayer(layer);
    }

    @Override
    public boolean editReflux(RefluxDto refluxDto) {
        refluxDto.checkParamNotNull();
        if (refluxDto.isIdEmpty()) {
            return companyMapper.addReflux(refluxDto);
        } else {
            refluxDto.setUpdateTime(LocalDateTime.now());
            return companyMapper.updateReflux(refluxDto);
        }
    }

    @Override
    public Reflux getReflux(long companyId) {

        return companyMapper.getReflux(companyId);
    }

    @Override
    public TableDto<CompanyTable> listAllCompany() {
        List<CompanyTable> tables = companyMapper.listAllCompany();
        TableDto<CompanyTable> tableDto = new TableDto<>();
        tableDto.setList(tables);
        return tableDto;
    }

    @Override
    public boolean createJoinApply(CompanyJoinApply joinApply) {
        joinApply.checkIdNotNull();
        List<CompanyJoinApply> applies = companyMapper.getJoinApplyByUserId(joinApply.getUserId());
        if (!applies.isEmpty()) {
            return false;
        }
        return companyMapper.addJoinApply(joinApply);
    }

    @Override
    public boolean createCompany(CompanyCreateApply apply) {
        apply.checkIdNotNull();
        int applies = companyMapper.getCreateApplyByUserId(apply.getUserId());
        if (applies > 0) {
            return false;
        }
        return companyMapper.addCreateApply(apply);
    }

    @Override
    public TableDto<CreateApplyTable> listCompanyCreateApply() {
        List<CreateApplyTable> createApplyTables = companyMapper.listCreateApply();
        TableDto<CreateApplyTable> tableDto = new TableDto<>();
        tableDto.setList(createApplyTables);
        return tableDto;
    }

    @Override
    public boolean editCreateApplyStatus(long apply_id, ApplyStatus status) {
        return companyMapper.editCreateApplyStatus(apply_id, status.getStatus(), LocalDateTime.now());
    }

    @Transactional
    @Override
    public boolean editCreateApplyStatus2Agree(long applyId) {
        boolean flag = editCreateApplyStatus(applyId, ApplyStatus.AGREE);
        if (flag) {
            CompanyCreateApply apply = companyMapper.getCreateApplyById(applyId);
            companyMapper.addCompany(apply);
            CompanyJoinApply companyJoinApply = new CompanyJoinApply();
            companyJoinApply.setCompanyId(apply.getCompanyId());
            companyJoinApply.setUserId(apply.getUserId());
            companyJoinApply.setStatus(0);
            companyMapper.addCompany2User(companyJoinApply);
            return true;
        }
        return false;
    }

    @Override
    public boolean editJoinApplyStatus(long apply_id, ApplyStatus status) {
        return companyMapper.editJoinApplyStatus(apply_id, status.getStatus(), LocalDateTime.now());
    }

    @Transactional
    @Override
    public boolean editJoinApplyStatus2Agree(long applyId) {
        boolean flag = editJoinApplyStatus(applyId, ApplyStatus.AGREE);
        if (flag) {
            CompanyJoinApply apply = companyMapper.getJoinApplyById(applyId);
            apply.setStatus(2);
            return companyMapper.addCompany2User(apply);
        }
        return false;
    }

    @Override
    public boolean deleteLayer(long layerId) {
        Layer layer = layerMapper.getLayerById(layerId);
        if (layer.getTraffic() != 1000) {
            return false;
        }
        if (layer.getTest() != 0) {
            return false;
        }
        return layerMapper.deleteLayer(layerId) == 1;
    }
}
