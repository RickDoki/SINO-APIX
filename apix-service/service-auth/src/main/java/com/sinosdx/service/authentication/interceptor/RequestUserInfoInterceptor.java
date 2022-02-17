/*
 * Copyright © 2022 SinoSDX (biz@sinosdx.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sinosdx.service.authentication.interceptor;

import com.sinosdx.service.authentication.constants.Constants;
import com.sinosdx.service.authentication.utils.ThreadContext;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

/**
 * @author wendy
 * @date 2020/11/30
 */
@Slf4j
public class RequestUserInfoInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info(String.format("requestUrl: %s", request.getRequestURL().toString()));

        if (null != request.getHeader(Constants.AUTH_HEADER)) {
            String token = request.getHeader(Constants.AUTH_HEADER).substring(Constants.AUTH_HEADER_PREFIX.length());
            Claims claims = Jwts.parser().setSigningKey("sinosdx".getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(token).getBody();

            ThreadContext.put(Constants.THREAD_CONTEXT_CLIENT_ID, claims.get("client_id"));
            ThreadContext.put(Constants.THREAD_CONTEXT_TOKEN, token);

            if (!claims.containsKey(Constants.THREAD_CONTEXT_USER_ID)) {
                ThreadContext.put(Constants.THREAD_CONTEXT_USER_ID, 1);
                ThreadContext.put(Constants.THREAD_CONTEXT_USERNAME, "user_1");
                ThreadContext.put(Constants.THREAD_CONTEXT_MOBILE, null);
            } else {
                ThreadContext.put(Constants.THREAD_CONTEXT_USER_ID, claims.get("userId"));
                ThreadContext.put(Constants.THREAD_CONTEXT_USERNAME, claims.get("username"));
                ThreadContext.put(Constants.THREAD_CONTEXT_MOBILE, claims.get("mobile"));
            }
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ThreadContext.remove(Constants.THREAD_CONTEXT_CLIENT_ID);
        ThreadContext.remove(Constants.THREAD_CONTEXT_USER_ID);
        ThreadContext.remove(Constants.THREAD_CONTEXT_USERNAME);
        ThreadContext.remove(Constants.THREAD_CONTEXT_MOBILE);
        ThreadContext.remove(Constants.THREAD_CONTEXT_TOKEN);
    }
}
