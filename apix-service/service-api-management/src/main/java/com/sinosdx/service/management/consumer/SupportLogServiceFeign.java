package com.sinosdx.service.management.consumer;

import com.sinosdx.common.base.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author wendy
 * @date 2021/7/22
 */
@FeignClient("service-support-log")
public interface SupportLogServiceFeign {

    @GetMapping("gateway/log/queryListByAppCode")
    R<Object> queryListByAppCode(@RequestParam(name = "appCodes", required = false) List<String> appCodes,
                     @RequestParam(name = "startTime", required = false) Long startTime,
                     @RequestParam(name = "endTime", required = false) Long endTime);

    @GetMapping("gateway/log/queryGatewayLogByStatus")
    R<Object> queryGatewayLogByStatus(@RequestParam(name = "appCode", required = false) String appCode,
                                      @RequestParam(name = "requestUri", required = false) String requestUri,
                                             @RequestParam(name = "startTime", required = false) Long startTime,
                                             @RequestParam(name = "endTime", required = false) Long endTime);
}
