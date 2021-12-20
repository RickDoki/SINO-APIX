package com.sinosdx.service.authentication.service;

import com.sinosdx.service.authentication.dao.entity.GatewayBlacklist;
import com.sinosdx.service.authentication.result.R;

import java.util.Map;

/**
 * @author wendy
 * @date 2021/1/5
 */
public interface GatewayBlacklistService {

    /**
     * 创建黑名单
     *
     * @param gatewayBlacklist
     * @return
     */
    R<GatewayBlacklist> createGatewayBlacklist(GatewayBlacklist gatewayBlacklist);

    /**
     * 修改黑名单
     *
     * @param id
     * @param gatewayBlacklist
     * @return
     */
    R<GatewayBlacklist> updateGatewayBlacklist(Integer id, GatewayBlacklist gatewayBlacklist);

    /**
     * 删除黑名单
     *
     * @param id
     * @return
     */
    R<Object> deleteGatewayBlacklist(Integer id);

    /**
     * 查询黑名单列表
     *
     * @param type
     * @param content
     * @param limit
     * @param offset
     * @return
     */
    R<Map<String, Object>> queryGatewayBlacklist(String type, String content, Integer limit, Integer offset);

    /**
     * 查询黑名单
     *
     * @param type
     * @param content
     * @return
     */
    R<Map<String, String>> queryGatewayBlacklist(String type, String content);
}
