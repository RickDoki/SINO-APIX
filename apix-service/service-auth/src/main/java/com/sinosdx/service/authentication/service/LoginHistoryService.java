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
package com.sinosdx.service.authentication.service;

import com.sinosdx.service.authentication.dao.entity.LoginHistory;
import com.sinosdx.service.authentication.result.R;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wendy
 * @date 2021/1/12
 */
@RestController
@RequestMapping("/auth/history/login")
public interface LoginHistoryService {

    /**
     * 查询用户的登录历史
     * @param userId
     * @return
     */
    @GetMapping("/user/{userId}")
    R<List<LoginHistory>> getLoginHistoryByUserId(@PathVariable("userId") Integer userId);

    /**
     * 插入新的登录记录
     *
     * @param loginHistory
     * @return
     */
    @PostMapping("/insert")
    R<Object> insertNewLoginHistory(@RequestBody LoginHistory loginHistory);
}
