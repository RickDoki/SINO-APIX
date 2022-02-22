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
package com.sinosdx.service.management.sentinel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author shenjian
 * @create 2021-12-30 15:39
 * @Description 根据app和 resource 确定唯一性
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RuleDefinitionEntity {

    //app code
    private String app;

    //routeId 或者 api_group_name
    private String resource;

    //限流次数  在线程数模式下-线程数量
    private Integer count;

    //限流间隔-秒 = interval*intervalUnit
    private Long intervalSec;

    //限流间隔
    private Long interval;

    //限流时间单位 0-秒 1-分 2-时 3-天
    private Integer intervalUnit;

    //资源类型 0-routeId 1-api_group_name  1
    private Integer resourceMode;

    //限流类型 0-线程数 1-QPS  1
    private Integer grade;

    //流控方式 0-快速失败 2-匀速排队
    private Integer controlBehavior;

    //快速失败-最大突发数 0
    private Integer burst;

    //匀速排队-最大时间/毫秒
    private Integer maxQueueingTimeoutMs;

}
