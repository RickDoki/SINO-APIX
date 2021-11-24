package com.sinosdx.service.authentication.configuration;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.sinosdx.service.authentication.exceptions.InvalidPasswordException;
import com.sinosdx.service.authentication.exceptions.UserBannedException;
import com.sinosdx.service.authentication.exceptions.UserNotRegisteredException;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.io.IOException;

/**
 * @author wendy
 * @date 2021/1/15
 */
@Slf4j
@Configuration
public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        Exception exception = null;
        try {
            String json = Util.toString(response.body().asReader());
            JSONObject jsonObject = JSON.parseObject(json, Feature.DisableSpecialKeyDetect);
            JSONObject cause = jsonObject.getJSONObject("cause");
            String msg = cause.getString("message");
            Integer code = cause.getInteger("code");
            log.info(String.format("login exception code %d", code));
            switch (code) {
                case 820010:
                    exception = new UserBannedException(msg);
                    break;
                case 824017:
                    exception = new InvalidPasswordException(msg);
                    break;
                case 824018:
                    exception = new UserNotRegisteredException(msg);
                    break;
                default:
                    exception = new UsernameNotFoundException(json);
            }
            return exception;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}