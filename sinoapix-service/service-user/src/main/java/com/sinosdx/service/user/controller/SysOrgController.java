package com.sinosdx.service.user.controller;

import com.sinosdx.common.base.result.R;
import com.sinosdx.service.user.dao.entity.SysOrg;
import com.sinosdx.service.user.service.SysOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wendy
 * @date 2021/9/24
 */
@RestController
@RequestMapping("/user/sys/org")
public class SysOrgController {

    @Autowired
    private SysOrgService sysOrgService;

    /**
     * 更新组织
     *
     * @param sysOrg
     * @return
     */
    @PutMapping("/{id}")
    public R<Object> updateOrg(@PathVariable("id") Integer id, @RequestBody SysOrg sysOrg) {
        sysOrg.setId(id);
        return sysOrgService.updateOrg(sysOrg);
    }

    /**
     * 查询组织列表
     *
     * @param name
     * @param limit
     * @param offset
     * @return
     */
    @GetMapping("/list")
    public R<Object> queryOrgList(@RequestParam(value = "name", required = false) String name,
                                  @RequestParam(value = "limit", required = false) Integer limit,
                                  @RequestParam(value = "offset", required = false) Integer offset) {
        return sysOrgService.queryOrgList(name, limit, offset);
    }
}
