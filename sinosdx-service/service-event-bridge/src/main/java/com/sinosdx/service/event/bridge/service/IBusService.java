package com.sinosdx.service.event.bridge.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sinosdx.service.event.bridge.controller.dto.BusDTO;
import com.sinosdx.service.event.bridge.controller.dto.BusSaveDTO;
import com.sinosdx.service.event.bridge.controller.dto.BusUpdateDTO;
import com.sinosdx.service.event.bridge.controller.query.BusQuery;
import com.sinosdx.service.event.bridge.dao.entity.Bus;

/**
 * @author pengjiahu
 * @date 2020-11-24 14:55
 * @description
 */
public interface IBusService {

    /**
     * 保存事件总线
     *
     * @param busSaveDTO
     * @return
     */
    boolean saveBus(BusSaveDTO busSaveDTO);

    /**
     * 修改事件总线
     *
     * @param busUpdateDTO
     * @return
     */
    boolean updateBus(BusUpdateDTO busUpdateDTO);

    /**
     * 查询事件总线详情
     *
     * @param busId
     * @return
     */
    BusDTO getBusDetail(Integer busId);

    /**
     * 查询事件总线列表
     *
     * @param page
     * @param busQuery
     * @return
     */
    IPage getBusList(Page page, BusQuery busQuery);

    /**
     * 删除事件总线
     *
     * @param busId
     * @return
     */
    boolean deleteBus(Integer busId);

    /**
     * 根据总线名称查询总线
     *
     * @param busName
     * @return
     */
    Bus getBusByBusName(String busName);

    /**
     * 检查名称是否重复
     *
     * @param name
     * @param excludeId
     * @return
     */
    boolean checkExistName(String name, Integer excludeId);
}
