package com.sinosdx.service.management.consumer;

import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * @author wendy
 * @date 2021/7/22
 */
@FeignClient("spring-cloud-gateway")
@RequestMapping("/gateway/route")
public interface GatewayServiceFeign {

    @PostMapping()
    Boolean create(@RequestBody JSONObject entity);

    @PutMapping()
    Boolean update(@RequestBody JSONObject entity);

    @DeleteMapping("/{id}")
    Mono<ResponseEntity<Object>> delete(@PathVariable String id);
}
