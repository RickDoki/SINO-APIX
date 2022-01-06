package com.sinosdx.service.management.consumer;

import com.sinosdx.common.base.result.R;
import com.sinosdx.service.management.dao.entity.ClientAppSecret;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author wendy
 * @date 2022/1/6
 */
@FeignClient("service-auth")
@RequestMapping("/auth/token")
public interface TokenServiceFeign {

    /**
     * 保存客户端申请token的secretKey
     *
     * @param secret
     * @return
     */
    @PostMapping("/secretKey")
    R<Object> saveClientAppSecretKey(@RequestBody ClientAppSecret secret);
}
