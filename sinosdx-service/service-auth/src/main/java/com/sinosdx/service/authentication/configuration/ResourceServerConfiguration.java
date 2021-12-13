package com.sinosdx.service.authentication.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * @author wendy
 * @date 2020/11/24
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    private static final String DEMO_RESOURCE_ID = "order";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(DEMO_RESOURCE_ID).stateless(true);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .requestMatchers().anyRequest()
                .and()
                .anonymous()
                .and()
                .authorizeRequests()
                //                    .antMatchers("/product/**").access("#oauth2.hasScope('select') and hasRole('ROLE_USER')")
                .antMatchers("/auth/api/**").authenticated()
                .and()
                .authorizeRequests()
                .antMatchers("/auth/login").permitAll()
                .antMatchers("/auth/logout").permitAll()
                .antMatchers("/oauth/**").permitAll();//配置访问控制，必须认证过后才可以访问
    }
}
