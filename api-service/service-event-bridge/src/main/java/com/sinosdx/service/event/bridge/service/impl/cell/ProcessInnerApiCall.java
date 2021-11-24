package com.sinosdx.service.event.bridge.service.impl.cell;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.sinosdx.common.base.context.SpringContextHolder;
import com.sinosdx.service.event.bridge.dao.entity.RuleTargetInnerApi;
import com.sinosdx.service.event.bridge.service.bo.TargetCallBO;
import com.sinosdx.service.event.bridge.service.bo.TargetCallProcessBase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @author pengjiahu
 * @date 2020-11-24 19:50
 * @description
 */
@Slf4j
public class ProcessInnerApiCall extends AbstractProcessCall {

    private final RestTemplate restTemplate = SpringContextHolder.getBean(RestTemplate.class);

    private final LoadBalancerClient loadBalancerClient = SpringContextHolder
            .getBean(LoadBalancerClient.class);

    private final RuleTargetInnerApi ruleTargetInnerApi;

    private final TargetCallBO targetCall;

    public ProcessInnerApiCall(TargetCallBO targetCall, TargetCallProcessBase ruleTargetInnerApi) {
        this.targetCall = targetCall;
        this.ruleTargetInnerApi = (RuleTargetInnerApi) ruleTargetInnerApi;
    }

    @Override
    public TargetCallBO call() {
        return processTargetCall(targetCall);
    }

    @Override
    public String process() {
        String serviceId = "";
        String method = "";
        method = StrUtil.isBlank(method) ? "POST" : method;
        String path = "";
        String contentType = "";
        contentType = StrUtil.isBlank(contentType) ? MediaType.APPLICATION_FORM_URLENCODED_VALUE
                : contentType;
        String body = "";
        ServiceInstance serviceInstance = loadBalancerClient.choose(serviceId);
        // 获取服务实例
        if (serviceInstance == null) {
            throw new RuntimeException(String.format("%s服务不可用", serviceId));
        }
        String url = String.format("%s%s", serviceInstance.getUri(), path);
        HttpHeaders headers = new HttpHeaders();
        HttpMethod httpMethod = HttpMethod.resolve(method.toUpperCase());
        headers.setContentType(MediaType.parseMediaType(contentType));
        HttpEntity requestEntity = null;
        if (contentType.contains(MediaType.APPLICATION_JSON_VALUE)) {
            // json格式
            requestEntity = new HttpEntity(body, headers);
        } else {
            // 表单形式
            MultiValueMap<String, String> params = new LinkedMultiValueMap();
            if (StrUtil.isNotBlank(body)) {
                Map data = JSONObject.parseObject(body, Map.class);
                params.putAll(data);
                requestEntity = new HttpEntity(params, headers);
            }
        }
        ResponseEntity<String> result = restTemplate
                .exchange(url, httpMethod, requestEntity, String.class);
        log.debug("url[{}] method[{}] data=[{}] result=[{}]", url, httpMethod, requestEntity,
                result);
        return result.toString();
    }
}
