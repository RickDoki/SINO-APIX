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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sinosdx.service.authentication.dao.entity.LoginHistory;
import com.sinosdx.service.authentication.dao.mapper.LoginHistoryMapper;
import com.sinosdx.service.authentication.result.R;
import com.sinosdx.service.authentication.service.LoginHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wendy
 * @date 2021/1/21
 */
@Slf4j
@Service
public class LoginHistoryServiceImpl implements LoginHistoryService {

    @Resource
    private LoginHistoryMapper loginHistoryMapper;

    /**
     * 查询用户的登录历史
     *
     * @param userId
     * @return
     */
    @Override
    public R<List<LoginHistory>> getLoginHistoryByUserId(Integer userId) {
        return R.success(loginHistoryMapper.selectList(new QueryWrapper<LoginHistory>()
                .eq("user_id", userId).eq("del_flag", 0)));
    }

    /**
     * 插入新的登录记录
     *
     * @param loginHistory
     * @return
     */
    @Override
    public R<Object> insertNewLoginHistory(LoginHistory loginHistory) {
        loginHistoryMapper.insert(loginHistory);
        return R.success();
    }
}
