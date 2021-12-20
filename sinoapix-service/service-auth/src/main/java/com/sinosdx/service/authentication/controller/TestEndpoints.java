package com.sinosdx.service.authentication.controller;

import com.sinosdx.service.authentication.dao.entity.BaseUser;
import com.sinosdx.service.authentication.result.R;
import com.sinosdx.service.authentication.result.enums.ResultCodeEnum;
import com.sinosdx.service.authentication.utils.ThreadContext;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Principal;

/**
 * @author wendy
 * @date 2020/11/24
 */
@RestController
@RequestMapping("/auth/api")
public class TestEndpoints {

    @GetMapping("/product/{id}")
    public R<Object> getProduct(@PathVariable String id) {
        //for debug
        return R.fail(ResultCodeEnum.TOKEN_NULL);
//        throw new AuthenticationException(ResultCodeEnum.TOKEN_NULL, "");
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        return "product id : " + id;
    }

    @GetMapping("/order/{id}")
    public String getOrder(@PathVariable String id, Principal principalUser) {
//        System.out.println(principalUser);
        //for debug
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        BaseUser principal = (BaseUser) authentication.getPrincipal();
        System.out.println(principal);

        return ThreadContext.get("CLIENT_ID");
    }

    @GetMapping("/me")
    public Object getCurrentUser(Authentication user, HttpServletRequest request) throws Exception {

        String token = request.getHeader("Authorization").substring(7);
        Claims claims = Jwts.parser().setSigningKey("sinosdx".getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token).getBody();
        System.out.println(claims);
        return user;
    }

}