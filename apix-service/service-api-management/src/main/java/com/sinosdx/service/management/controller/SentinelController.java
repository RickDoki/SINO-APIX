package com.sinosdx.service.management.controller;

import com.sinosdx.service.management.result.R;
import com.sinosdx.service.management.sentinel.SentinelProvider;
import com.sinosdx.service.management.sentinel.entity.LimitInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author shenjian
 * @create 2022-01-05 12:02
 * @Description
 */
@RestController
@RequestMapping("/sentinel")
public class SentinelController {

    @Autowired
    private SentinelProvider service;

    @PostMapping("/save")
    public R save(@RequestBody List<LimitInfo> list){
        String save = service.save(list);
        if(save.endsWith("ok")){
            return R.success();
        }
        return R.fail(save);
    }

    @GetMapping("/list")
    public R getRules(){
        return R.success(service.getRules());
    }

    @PostMapping("/open")
    public R open(@RequestParam("code") String code){
        service.open(code);
        return R.success();
    }

    @PostMapping("/close")
    public R close(@RequestParam("code") String code){
        service.close(code);
        return R.success();
    }
}
