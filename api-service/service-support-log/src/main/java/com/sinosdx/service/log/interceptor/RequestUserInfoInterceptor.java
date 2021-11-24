package com.sinosdx.service.log.interceptor;

import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sinosdx.common.base.context.ThreadContext;
import com.sinosdx.service.log.constants.Constants;
import com.sinosdx.service.log.enums.ResultCodeEnum;
import com.sinosdx.service.log.exception.LogException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wendy
 * @date 2020/11/30
 */
@Slf4j
public class RequestUserInfoInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info(String.format("requestUrl: %s", request.getRequestURL().toString()));
        String token = request.getHeader(Constants.AUTH_HEADER);
        if (StringUtils.isEmpty(token)) {
            throw new LogException(ResultCodeEnum.TOKEN_NULL);
        }
        if (!StpUtil.isLogin()) {
            throw new LogException(ResultCodeEnum.TOKEN_EXPIRED);
        }
        Object loginIdByToken = StpUtil.getLoginIdByToken(token);

        String loginToken = loginIdByToken.toString();
        String userId;
        String username = "";
        String phone = "";
        try {
            JSONObject jsonObject = JSON.parseObject(loginToken);
            userId = jsonObject.getString("id");
            username = jsonObject.getString("username");
            phone = jsonObject.getString("phone");
        } catch (Exception e) {
            userId = loginToken;
        }

        ThreadContext.put(Constants.THREAD_CONTEXT_USER_ID, userId);
        ThreadContext.put(Constants.THREAD_CONTEXT_USERNAME, username);
        ThreadContext.put(Constants.THREAD_CONTEXT_PHONE, phone);
        ThreadContext.put(Constants.THREAD_CONTEXT_TOKEN, token);
        return true;
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ThreadContext.remove(Constants.THREAD_CONTEXT_USER_ID);
        ThreadContext.remove(Constants.THREAD_CONTEXT_USERNAME);
        ThreadContext.remove(Constants.THREAD_CONTEXT_PHONE);
        ThreadContext.remove(Constants.THREAD_CONTEXT_TOKEN);
    }
}
