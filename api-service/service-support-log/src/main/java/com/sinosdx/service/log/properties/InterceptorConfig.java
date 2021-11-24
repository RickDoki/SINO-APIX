package com.sinosdx.service.log.properties;

import com.sinosdx.service.log.interceptor.RequestUserInfoInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author wendy
 * @date 2020/11/30
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(new RequestUserInfoInterceptor());
        registration.addPathPatterns("/**");
        registration.excludePathPatterns("/login/**", "/oauth/**");
    }
}
