package com.sinosdx.service.event.bridge.service.impl;

import com.alibaba.fastjson.JSON;
import com.sinosdx.common.base.exceptions.BusinessException;
import com.sinosdx.common.base.result.R;
import com.sinosdx.service.event.bridge.service.IPlatformApiService;
import com.sinosdx.service.event.bridge.service.bo.PlatformApiBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author pengjiahu
 * @date 2021-01-17 01:10
 * @description
 */
@Slf4j
@Service
public class PlatformApiServiceImpl implements IPlatformApiService {

    private static final String SUFFIX = "/status/urls";

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private RestTemplate restTemplate;

    public Map<String, List<ServiceInstance>> instances() {
        Map<String, List<ServiceInstance>> instances = new HashMap<>(16);
        List<String> services = discoveryClient.getServices();
        services.forEach(s -> {
            List<ServiceInstance> list = discoveryClient.getInstances(s);
            instances.put(s, list);
        });
        return instances;
    }

    public ServiceInstance getServiceInstance(String serviceName) {
        return this.instances().entrySet().parallelStream()
                .filter(e -> e.getKey().equals(serviceName))
                .map(e -> {
                    //多实例随机获取
                    int i = new Random().nextInt(e.getValue().size());
                    return e.getValue().get(i);
                })
                .findAny()
                .orElseThrow(() -> new BusinessException("当前服务没有实例"));
    }

    @Override
    public List<PlatformApiBO> getApiList(String serviceName) {
        ServiceInstance serviceInstance = this.getServiceInstance(serviceName);
        String url = serviceInstance.getUri() + SUFFIX;
        R<PlatformApiBO> response = restTemplate.getForObject(url, R.class);
        List<PlatformApiBO> platformApiList = JSON
                .parseArray(JSON.toJSONString(response.getData()), PlatformApiBO.class);
        return platformApiList;
    }
}
