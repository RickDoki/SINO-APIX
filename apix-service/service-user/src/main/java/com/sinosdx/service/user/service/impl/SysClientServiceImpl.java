package com.sinosdx.service.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sinosdx.common.base.result.R;
import com.sinosdx.service.user.dao.entity.SysClient;
import com.sinosdx.service.user.dao.mapper.SysClientMapper;
import com.sinosdx.service.user.enums.SysClientEnum;
import com.sinosdx.service.user.service.SysClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author wendy
 * @date 2021/12/21
 */
@Service
@Slf4j
public class SysClientServiceImpl implements SysClientService {

    @Resource
    private SysClientMapper sysClientMapper;

    /**
     * 根据用户id查询client
     *
     * @param sysUserId
     * @return
     */
    @Override
    public R<Object> queryClientByUserId(Integer sysUserId) {
        SysClient sysClient = sysClientMapper.selectOne(new LambdaQueryWrapper<SysClient>()
                .eq(SysClient::getResourceId, sysUserId)
                .eq(SysClient::getResourceType, SysClientEnum.USER.getName()));
        return R.success(sysClient);
    }
}
