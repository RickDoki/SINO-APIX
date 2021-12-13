package com.sinosdx.service.event.bridge.service.impl.cell;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.sinosdx.common.base.context.SpringContextHolder;
import com.sinosdx.service.event.bridge.dao.entity.RuleTargetHttp;
import com.sinosdx.service.event.bridge.enums.DataRequestWayEnum;
import com.sinosdx.service.event.bridge.service.bo.TargetCallBO;
import com.sinosdx.service.event.bridge.service.bo.TargetCallProcessBase;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Callable;

/**
 * @author pengjiahu
 * @date 2020-11-24 19:50
 * @description
 */
@Slf4j
public class ProcessHttpCall extends AbstractProcessCall implements Callable<TargetCallBO> {

    private static final String DATA_STR = "data";

    private static final String ESCAPE = "\"";

    private final RestTemplate restTemplate = SpringContextHolder.getBean(RestTemplate.class);

    private final RuleTargetHttp ruleTargetHttp;

    private TargetCallBO targetCall;

    public ProcessHttpCall(TargetCallBO targetCall, TargetCallProcessBase ruleTargetHttp) {
        this.targetCall = targetCall;
        this.ruleTargetHttp = (RuleTargetHttp) ruleTargetHttp;
    }

    @Override
    public TargetCallBO call() {
        return processTargetCall(targetCall);
    }

    @Override
    public String process() {
        String url = ruleTargetHttp.getUrl();
        Object data;
        try {
            String sourceData = (String) targetCall.getData();
            log.info("http call,begin source:{}", sourceData);
            if (StrUtil.startWith(sourceData, ESCAPE)) {
                sourceData = StringUtils.removeEnd(sourceData, ESCAPE);
                sourceData = StringUtils.removeStart(sourceData, ESCAPE);
                sourceData = StringEscapeUtils.unescapeJava(sourceData);
                log.info("http call,转义处理:{}", sourceData);
            }
            data = JSON.parseObject(sourceData);
            log.info("http call,begin new:{}", data);
        } catch (JSONException e) {
            log.error("http call,json parse object exception,source data:{}", targetCall.getData());
            data = targetCall.getData();
        }
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Object> request;
        switch (DataRequestWayEnum.form(ruleTargetHttp.getDataRequestWay())) {
            case BODY:
                headers.setContentType(MediaType.APPLICATION_JSON);
                request = new HttpEntity<>(data, headers);
                break;
            case PARAMS:
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
                paramMap.add(DATA_STR, data);
                request = new HttpEntity<>(paramMap, headers);
                break;
            default:
                return "未知数据请求方式";
        }
        log.info("http call,request:{}", request);
        ResponseEntity<Object> response = restTemplate.postForEntity(url, request, Object.class);
        return JSON.toJSONString(response);
    }
}
