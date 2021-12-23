package com.sinosdx.service.authentication.controller;

import com.sinosdx.common.base.result.R;
import com.sinosdx.service.authentication.result.enums.ResultCodeEnum;
import com.sinosdx.service.authentication.service.ImageVerificationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author wendy
 * @date 2021/2/23
 */
@RestController
@RequestMapping("/auth")
public class VerificationCodeController {

    @Autowired
    private ImageVerificationCodeService imageVerificationCodeService;

    /**
     * 获取验证码图片
     *
     * @param response
     * @throws IOException
     */
    @GetMapping("/captcha/image")
    public void getCaptchaImage(HttpServletResponse response) throws IOException {
        imageVerificationCodeService.getImage(response);
    }

    /**
     * 验证图片验证码
     *
     * @param paramsMap
     * @return
     */
    @PostMapping("/captcha/image/verify")
    public R<Object> verifyImageCaptcha(@RequestBody Map<String, String> paramsMap) {
        if (!paramsMap.containsKey("uuid") || !paramsMap.containsKey("code")) {
            return R.fail(ResultCodeEnum.PARAM_IS_BLANK);
        }

        return imageVerificationCodeService.verifyCode(paramsMap.get("code"), paramsMap.get("uuid"));
    }
}
