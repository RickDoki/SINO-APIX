package com.sinosdx.service.authentication.consumer;

import com.sinosdx.common.base.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * @author wendy
 * @date 2020/12/4
 */
@FeignClient(value = "service-log")
public interface HandleLogServiceFeign {

    /**
     * 保存应用api数据埋点
     *
     * @param logApi
     * @return
     */
    @PostMapping("/save-apilog")
    R<Boolean> saveApiLog(@RequestBody Map<String, Object> logApi);
}
