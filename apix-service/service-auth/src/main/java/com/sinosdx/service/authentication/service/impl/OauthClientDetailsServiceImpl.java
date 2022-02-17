/*
 * Copyright © 2022 SinoSDX (biz@sinosdx.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sinosdx.service.authentication.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sinosdx.service.authentication.controller.RevokeTokenEndpoint;
import com.sinosdx.service.authentication.dao.entity.OauthClientDetails;
import com.sinosdx.service.authentication.dao.mapper.OauthClientDetailsMapper;
import com.sinosdx.service.authentication.result.R;
import com.sinosdx.service.authentication.service.OauthClientDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author wendy
 * @date 2020/12/7
 */
@Service
@Slf4j
public class OauthClientDetailsServiceImpl implements OauthClientDetailsService {

    @Resource
    private OauthClientDetailsMapper oauthClientDetailsMapper;

    @Autowired
    RevokeTokenEndpoint revokeTokenService;

    /**
     * 创建新客户端（客户端认证使用）
     *
     * @param oauthClientDetail
     * @return
     */
    @Override
    public R<Object> createOAuthClientDetail(OauthClientDetails oauthClientDetail) {
        log.info(oauthClientDetail.toString());
        oauthClientDetailsMapper.insert(oauthClientDetail);
        return R.success(oauthClientDetail);
    }

    /**
     * 删除认证客户端
     *
     * @param clientId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<Object> deleteOAuthClientDetail(String clientId) {
        //        List<OauthClientDetails> oauthClientDetails = oauthClientDetailsMapper.selectList(new QueryWrapper<OauthClientDetails>());
        //        for (OauthClientDetails oauthClientDetail : oauthClientDetails) {
        //            String additionalInformation = oauthClientDetail.getAdditionalInformation();
        //            if (null == additionalInformation || additionalInformation.isEmpty()) {
        //                continue;
        //            }
        //            JSONObject infoJson = JSONObject.parseObject(additionalInformation);
        //            String lessorCode = infoJson.getString("lessorCode");
        //            String lesseeCode = infoJson.getString("lesseeCode");
        //            // 删除认证
        //            if (appCode.equals(lessorCode) || appCode.equals(lesseeCode)) {
        //                oauthClientDetailsMapper.deleteById(oauthClientDetail);
        //            }
        //        }
        oauthClientDetailsMapper.delete(new LambdaQueryWrapper<OauthClientDetails>()
                .eq(OauthClientDetails::getClientId, clientId));
        return R.success();
    }

    /**
     * 查询client信息
     *
     * @param clientId
     * @return
     */
    @Override
    public R<OauthClientDetails> queryByClientId(String clientId) {
        return R.success(oauthClientDetailsMapper.selectOne(new QueryWrapper<OauthClientDetails>()
                .eq("client_id", clientId)));
    }
}
