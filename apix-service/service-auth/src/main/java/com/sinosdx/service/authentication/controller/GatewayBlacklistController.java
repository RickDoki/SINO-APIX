package com.sinosdx.service.authentication.controller;

import com.sinosdx.service.authentication.dao.entity.GatewayBlacklist;
import com.sinosdx.service.authentication.result.R;
import com.sinosdx.service.authentication.service.GatewayBlacklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author wendy
 * @date 2021/1/6
 */
@RestController
@RequestMapping("/auth/blacklist")
public class GatewayBlacklistController {

    @Autowired
    private GatewayBlacklistService gatewayBlacklistService;

    /**
     * 创建黑名单
     *
     * @param gatewayBlacklist
     * @return
     */
    @PostMapping("/create")
    public R<GatewayBlacklist> createGatewayBlacklist(@RequestBody GatewayBlacklist gatewayBlacklist) {
        return gatewayBlacklistService.createGatewayBlacklist(gatewayBlacklist);
    }

    /**
     * 修改黑名单
     *
     * @param id
     * @param gatewayBlacklist
     * @return
     */
    @PutMapping("/{id}")
    public R<GatewayBlacklist> updateGatewayBlacklist(@PathVariable("id") Integer id,
                                                      @RequestBody GatewayBlacklist gatewayBlacklist) {
        return gatewayBlacklistService.updateGatewayBlacklist(id, gatewayBlacklist);
    }

    /**
     * 删除黑名单
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public R<Object> deleteGatewayBlacklist(@PathVariable("id") Integer id) {
        return gatewayBlacklistService.deleteGatewayBlacklist(id);
    }

    /**
     * 查询黑名单列表
     *
     * @param type
     * @param content
     * @param limit
     * @param offset
     * @return
     */
    @GetMapping("/list")
    public R<Map<String, Object>> queryGatewayBlacklist(@RequestParam(value = "type", required = false) String type,
                                                        @RequestParam(value = "content", required = false) String content,
                                                        @RequestParam(value = "limit", required = false) Integer limit,
                                                        @RequestParam(value = "offset", required = false) Integer offset) {
        return gatewayBlacklistService.queryGatewayBlacklist(type, content, limit, offset);
    }

        /**
         * 查询黑名单
         *
         * @param type
         * @param content
         * @return
         */
        @GetMapping("/query")
        public R<Map<String, String>> queryGatewayBlacklist(@RequestParam(value = "type", required = false) String type,
                @RequestParam(value = "content", required = false) String content) {
            return gatewayBlacklistService.queryGatewayBlacklist(type, content);
    }
}
