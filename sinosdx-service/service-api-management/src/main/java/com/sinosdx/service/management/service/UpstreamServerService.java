package com.sinosdx.service.management.service;

import com.sinosdx.service.management.dao.entity.UpstreamServer;
import com.sinosdx.service.management.result.R;

/**
 * @author wendy
 * @date 2021/9/14
 */
public interface UpstreamServerService {

    /**
     * 查询上游服务列表
     *
     * @param name
     * @param limit
     * @param offset
     * @return
     */
    R<Object> queryUpstreamServerList(String name, Integer limit, Integer offset);

    /**
     * 查询上游服务详情
     *
     * @param id
     * @return
     */
    R<Object> queryUpstreamServerDetail(Integer id);

    /**
     * 新增上游服务
     *
     * @param upstreamServer
     * @return
     */
    R<Object> addUpstreamServer(UpstreamServer upstreamServer);

    /**
     * 修改上游服务
     *
     * @param upstreamServer
     * @return
     */
    R<Object> updateUpstreamServer(UpstreamServer upstreamServer);

    /**
     * 删除上游服务
     *
     * @param id
     * @return
     */
    R<Object> deleteUpstreamServer(Integer id);
}
