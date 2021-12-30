package com.sinosdx.gateway.controller;

import cn.hutool.core.bean.BeanUtil;
import com.sinosdx.common.gateway.constants.GatewayConstants;
import com.sinosdx.common.model.log.entity.gateway.GatewayLogDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author pengjiahu
 * @date 2020/8/4 15:41
 * @description
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("discovery")
public class DiscoveryClientController {

    private final DiscoveryClient discoveryClient;
    @Autowired
    private StreamBridge streamBridge;

    /**
     * 获取服务实例
     */
    @GetMapping("instances")
    public Map<String, List<ServiceInstance>> instances() {
        Map<String, List<ServiceInstance>> instances = new HashMap<>(16);
        List<String> services = discoveryClient.getServices();
        services.forEach(s -> {
            List<ServiceInstance> list = discoveryClient.getInstances(s);
            instances.put(s, list);
        });
        return instances;
    }

    /**
     * 自定义日志记录
     *
     * @return
     */
    @PostMapping("log")
    public GatewayLogDTO customGatewayLog(@RequestBody GatewayLogDTO gatewayLog) {
        GatewayLogDTO gatewayLogDTO = new GatewayLogDTO();
        BeanUtil.copyProperties(gatewayLog, gatewayLogDTO);
        streamBridge.send(GatewayConstants.LOG_TOPIC, gatewayLogDTO);
        return gatewayLogDTO;
    }

}
