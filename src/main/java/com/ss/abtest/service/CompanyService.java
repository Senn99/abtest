package com.ss.abtest.service;

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
import com.ss.abtest.pojo.vo.FlightTable;
import com.ss.abtest.pojo.vo.JoinApplyTable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author senn
 * @since 2023/4/20 21:35
 **/
public interface CompanyService {
    List<Layer> listLayerByCompanyId(long companyId);

    TableDto<JoinApplyTable> listJoinApplyByCompanyId(long companyId);

    boolean editJoinApplyStatus(long apply_id, ApplyStatus status);

    boolean editJoinApplyStatus2Agree(long applyId);

    boolean createLayer(LayerDto layer);

    boolean editReflux(RefluxDto refluxDto);

    Reflux getReflux(long company_id);

    TableDto<CompanyTable> listAllCompany();

    boolean createJoinApply(CompanyJoinApply joinApply);

    boolean createCompany(CompanyCreateApply apply);

    TableDto<CreateApplyTable> listCompanyCreateApply();

    boolean editCreateApplyStatus(long apply_id, ApplyStatus status);

    boolean editCreateApplyStatus2Agree(long apply_id);

    boolean deleteLayer(long layerId);
}
