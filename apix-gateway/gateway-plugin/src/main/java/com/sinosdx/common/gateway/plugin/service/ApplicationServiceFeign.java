package com.sinosdx.common.gateway.plugin.service;

import com.alibaba.fastjson.JSONObject;
import com.sinosdx.common.base.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author wendy
 * @date 2020/12/4
 */
@FeignClient(value = "service-application")
public interface ApplicationServiceFeign {

    /**
     * 根据订阅编号查询订阅信息
     *
     * @param subscribeCode
     * @return
     */
    @GetMapping("/app/open/subscribe")
    R<JSONObject> queryAppCodeBySubscribeCode(@RequestParam String subscribeCode);

}
