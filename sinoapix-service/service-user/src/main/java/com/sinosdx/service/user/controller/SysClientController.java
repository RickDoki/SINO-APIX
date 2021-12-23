package com.sinosdx.service.user.controller;

import com.sinosdx.common.base.result.R;
import com.sinosdx.service.user.service.SysClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wendy
 * @date 2021/12/21
 */
@RestController
@RequestMapping("/user/sys/client")
public class SysClientController {

    @Autowired
    private SysClientService sysClientService;

    /**
     * 根据用户id查询client
     *
     * @param sysUserId
     * @return
     */
    @GetMapping()
    public R<Object> queryClientByUserId(@RequestParam Integer sysUserId) {
        return sysClientService.queryClientByUserId(sysUserId);
    }
}
