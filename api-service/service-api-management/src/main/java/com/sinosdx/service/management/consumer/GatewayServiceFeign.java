package com.sinosdx.service.management.consumer;

import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author wendy
 * @date 2021/7/22
 */
@FeignClient("api-gateway")
public interface GatewayServiceFeign {

    @PostMapping("/gateway/route")
    Boolean create(@RequestBody JSONObject entity);

    @PutMapping("/gateway/route")
    Boolean update(@RequestBody JSONObject entity);
}
