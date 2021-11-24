package com.sinosdx.service.management.controller;

import com.sinosdx.common.base.annotation.AuditLog;
import com.sinosdx.service.management.dao.entity.UpstreamServer;
import com.sinosdx.service.management.result.R;
import com.sinosdx.service.management.service.UpstreamServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 上游服务
 *
 * @author wendy
 * @date 2021/9/14
 */
@RestController
@RequestMapping("/app/upstream")
public class UpstreamServerController {

    @Autowired
    private UpstreamServerService upstreamServerService;

    /**
     * 查询上游服务列表
     *
     * @param name
     * @param limit
     * @param offset
     * @return
     */
    @AuditLog(type = "查询上游服务列表", name = "上游服务")
    @GetMapping("/list")
    public R<Object> queryUpstreamServerList(@RequestParam(required = false) String name,
                                             @RequestParam(required = false) Integer limit,
                                             @RequestParam(required = false) Integer offset) {
        return upstreamServerService.queryUpstreamServerList(name, limit, offset);
    }

    /**
     * 查询上游服务详情
     *
     * @param id
     * @return
     */
    @AuditLog(type = "查询上游服务详情", name = "上游服务")
    @GetMapping("/{id}")
    public R<Object> queryUpstreamServerDetail(@PathVariable(value = "id") Integer id) {
        return upstreamServerService.queryUpstreamServerDetail(id);
    }

    /**
     * 新增上游服务
     *
     * @param upstreamServer
     * @return
     */
    @AuditLog(type = "新增上游服务", name = "上游服务")
    @PostMapping
    public R<Object> addUpstreamServer(@RequestBody UpstreamServer upstreamServer) {
        return upstreamServerService.addUpstreamServer(upstreamServer);
    }

    /**
     * 修改上游服务
     *
     * @param upstreamServer
     * @return
     */
    @AuditLog(type = "修改上游服务", name = "上游服务")
    @PutMapping("/{id}")
    public R<Object> updateUpstreamServer(@PathVariable(value = "id") Integer id, @RequestBody UpstreamServer upstreamServer) {
        upstreamServer.setId(id);
        return upstreamServerService.updateUpstreamServer(upstreamServer);
    }

    /**
     * 删除上游服务
     *
     * @param id
     * @return
     */
    @AuditLog(type = "删除上游服务", name = "上游服务")
    @DeleteMapping("/{id}")
    public R<Object> deleteUpstreamServer(@PathVariable(value = "id") Integer id) {
        return upstreamServerService.deleteUpstreamServer(id);
    }
}
