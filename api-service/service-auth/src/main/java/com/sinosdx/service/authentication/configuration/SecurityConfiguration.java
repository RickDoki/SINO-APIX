package com.sinosdx.service.authentication.configuration;

import com.sinosdx.service.authentication.security.filter.AjaxAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author wendy
 * @date 2020/11/24
 */
@Configuration
@EnableWebSecurity
@Order(99)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private AjaxAuthenticationProvider ajaxAuthenticationProvider;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.authenticationProvider(ajaxAuthenticationProvider);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .anonymous().disable()
                .requestMatchers().anyRequest()
                .and()
                .authorizeRequests()
                .antMatchers("/auth/oauth/**").permitAll()
                .antMatchers("/auth/login").permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/auth/api/**").authenticated();
    }

}
