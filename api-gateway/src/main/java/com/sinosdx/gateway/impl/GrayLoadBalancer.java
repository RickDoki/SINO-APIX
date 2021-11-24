package com.sinosdx.gateway.impl;


import com.sinosdx.common.tools.common.WeightRandomUtils;
import com.sinosdx.common.tools.entity.WeightMeta;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.DefaultResponse;
import org.springframework.cloud.client.loadbalancer.EmptyResponse;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.loadbalancer.core.NoopServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.Map.Entry;

/**
 * 带灰度功能的负载均衡
 *
 * @author pengjiahu
 * @date 2021-09-07 16:24
 * @description
 */
@Slf4j
@AllArgsConstructor
public class GrayLoadBalancer implements ReactorServiceInstanceLoadBalancer {

    public static final String VERSION = "version";
    public static final String WEIGHT = "weight";
    public static final String V = "-v";
    public static final String W = "-w";
    private ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider;
    private String serviceId;
    private String scheme;

    /**
     * 选择匹配的服务实例
     *
     * @param request
     * @return
     */
    @Override
    public Mono<Response<ServiceInstance>> choose(Request request) {
        HttpHeaders headers = (HttpHeaders) request.getContext();
        //判断服务实例是否为空
        if (this.serviceInstanceListSupplierProvider != null) {
            //获取可用的服务实例
            ServiceInstanceListSupplier supplier = this.serviceInstanceListSupplierProvider
                    .getIfAvailable(NoopServiceInstanceListSupplier::new);
            //对服务实例列表进行匹配
            return ((Flux) supplier.get()).next()
                    .map(list -> getInstanceResponse((List<ServiceInstance>) list, headers));
        }
        return null;
    }

    /**
     * 匹配合适的规则
     *
     * @param instances
     * @param headers
     * @return
     */
    private Response<ServiceInstance> getInstanceResponse(List<ServiceInstance> instances,
            HttpHeaders headers) {
        if (instances.isEmpty()) {
            return getServiceInstanceEmptyResponse();
        }
        //根据版本
        if (scheme.contains(V)) {
            return getServiceInstanceResponseByVersion(instances, headers);
        }
        //根据权重
        else if (scheme.contains(W)) {
            return getServiceInstanceResponseWithWeight(instances);
        }
        //如果都未匹配到则取第一个
        else {
            return new DefaultResponse(instances.get(0));
        }
    }

    /**
     * 根据版本进行分发
     *
     * @param instances
     * @param headers
     * @return
     */
    private Response<ServiceInstance> getServiceInstanceResponseByVersion(
            List<ServiceInstance> instances, HttpHeaders headers) {
        String versionNo = headers.getFirst(VERSION);
        if (log.isDebugEnabled()) {
            log.debug("grayLoadBalancer by version,versionNo：{}", versionNo);
        }
        //构建版本对比参数
        Map<String, String> versionMap = new HashMap<>();
        versionMap.put(VERSION, versionNo);
        final Set<Entry<String, String>> attr = Collections.unmodifiableSet(versionMap.entrySet());
        //匹配对应的服务实例
        ServiceInstance serviceInstance = null;
        for (ServiceInstance instance : instances) {
            Map<String, String> metadata = instance.getMetadata();
            if (metadata.entrySet().containsAll(attr)) {
                serviceInstance = instance;
                break;
            }
        }
        //未匹配到，则返回空的服务实例
        if (ObjectUtils.isEmpty(serviceInstance)) {
            return getServiceInstanceEmptyResponse();
        }
        //构建默认的返回
        return new DefaultResponse(serviceInstance);
    }

    /**
     * 根据权重值进行分发
     *
     * @param instances
     * @return
     */
    private Response<ServiceInstance> getServiceInstanceResponseWithWeight(
            List<ServiceInstance> instances) {
        Map<ServiceInstance, Integer> weightMap = new HashMap<>();
        for (ServiceInstance instance : instances) {
            Map<String, String> metadata = instance.getMetadata();
            if (metadata.containsKey(WEIGHT)) {
                weightMap.put(instance, Integer.valueOf(metadata.get(WEIGHT)));
            }
        }
        WeightMeta<ServiceInstance> weightMeta = WeightRandomUtils.buildWeightMeta(weightMap);
        if (ObjectUtils.isEmpty(weightMeta)) {
            return getServiceInstanceEmptyResponse();
        }
        ServiceInstance serviceInstance = weightMeta.random();
        if (ObjectUtils.isEmpty(serviceInstance)) {
            return getServiceInstanceEmptyResponse();
        }
        return new DefaultResponse(serviceInstance);
    }

    /**
     * 返回空的服务实例
     *
     * @return
     */
    private Response<ServiceInstance> getServiceInstanceEmptyResponse() {
        log.warn("No servers available for service: " + this.serviceId);
        return new EmptyResponse();
    }
}
