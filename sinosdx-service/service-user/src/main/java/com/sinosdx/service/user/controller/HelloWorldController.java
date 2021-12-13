package com.sinosdx.service.user.controller;

import com.sinosdx.common.base.result.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wendy
 * @date 2021/8/23
 */
@RestController
@RequestMapping("/user")
public class HelloWorldController {

    @GetMapping("/demo/helloworld")
    public R<Object> helloWorld() {
        return R.success("Hello world!");
    }
}
