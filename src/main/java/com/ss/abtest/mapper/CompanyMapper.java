package com.ss.abtest.mapper;

import com.ss.abtest.pojo.domain.CompanyCreateApply;
import com.ss.abtest.pojo.domain.CompanyJoinApply;
import com.ss.abtest.pojo.domain.Layer;
import com.ss.abtest.pojo.domain.Reflux;
import com.ss.abtest.pojo.dto.LayerDto;
import com.ss.abtest.pojo.dto.RefluxDto;
import com.ss.abtest.pojo.vo.CompanyTable;
import com.ss.abtest.pojo.vo.CreateApplyTable;
import com.ss.abtest.pojo.vo.JoinApplyTable;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author senn
 * @since 2023/4/24 20:06
 **/
@Mapper
public interface CompanyMapper {
    List<JoinApplyTable> listJoinApplyByCompanyId(long companyId);

    boolean addCompany2User(CompanyJoinApply apply);

    CompanyJoinApply getJoinApplyById(long applyId);

    boolean addLayer(LayerDto layer);

    boolean addReflux(RefluxDto refluxDto);

    boolean updateReflux(RefluxDto refluxDto);

    Reflux getReflux(long companyId);

    List<CompanyTable> listAllCompany();

    boolean addJoinApply(CompanyJoinApply joinApply);

    List<CompanyJoinApply> getJoinApplyByUserId(Long userId);

    int getCreateApplyByUserId(@Param("userId") Long userId);

    boolean addCreateApply(CompanyCreateApply apply);

    List<CreateApplyTable> listCreateApply();

    boolean editCreateApplyStatus(long applyId, int status, LocalDateTime updateTime);

    boolean editJoinApplyStatus(long applyId, int status, LocalDateTime updateTime);

    CompanyCreateApply getCreateApplyById(long applyId);

    void addCompany(CompanyCreateApply apply);
}
