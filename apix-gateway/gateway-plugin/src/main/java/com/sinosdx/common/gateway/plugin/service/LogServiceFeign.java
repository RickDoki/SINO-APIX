package com.sinosdx.common.gateway.plugin.service;

import com.sinosdx.common.base.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * @author wendy
 * @date 2020/12/4
 */
@FeignClient(value = "service-support-log")
public interface LogServiceFeign {

    @PostMapping(value = "/log/common/analysis",consumes = "text/html;charset=UTF-8")
    R<Object> analysisGatewayLogSave(@RequestBody String log);
    /**
     * 根据type保存log 日志
     *
     * @return
     */
    @PostMapping(path = "/log/common/{logType}", consumes = "text/html;charset=UTF-8")
    void saveLog(@PathVariable String logType, @RequestBody String string);
}
