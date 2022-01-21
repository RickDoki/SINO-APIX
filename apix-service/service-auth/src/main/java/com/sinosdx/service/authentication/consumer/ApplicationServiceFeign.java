package com.sinosdx.service.authentication.consumer;

import com.alibaba.fastjson.JSONObject;
import com.sinosdx.service.authentication.dao.entity.Application;
import com.sinosdx.service.authentication.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author wendy
 * @date 2020/12/4
 */
@FeignClient(value = "service-api-management")
public interface ApplicationServiceFeign {

    /**
     * 根据code查询app
     *
     * @param appCode
     * @return
     */
    @GetMapping("/app/query")
    R<Application> queryAppByAppCode(@RequestParam(value = "appCode") String appCode);

    /**
     * 通过查询clientId验证ApplicationLease是否存在
     *
     * @param clientId
     * @return
     */
    @PostMapping("/app/lease/verify/{clientId}")
    R<Boolean> verifyClientId(@PathVariable("clientId") String clientId);

    /**
     * 查询服务插件的配置参数
     *
     * @param pluginType
     * @param appCode
     * @return
     */
    @GetMapping("/app/plugin/configs")
    R<JSONObject> queryPluginConfigs(@RequestParam String pluginType,
                                            @RequestParam String appCode);
}
