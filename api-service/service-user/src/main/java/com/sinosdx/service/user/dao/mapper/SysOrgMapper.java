package com.sinosdx.service.user.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sinosdx.service.user.dao.entity.SysOrg;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author hwd
 * @date 2021-09-17 16:06
 * @description
 */
@Repository
public interface SysOrgMapper extends BaseMapper<SysOrg> {

    /**
     * 查询组织列表
     *
     * @param name
     * @param userIdList
     * @return
     */
    List<Object> queryOrgList(@Param("name") String name,
                              @Param("userIdList") List<Integer> userIdList,
                              @Param("limit") Integer limit,
                              @Param("offset") Integer offset);

}
