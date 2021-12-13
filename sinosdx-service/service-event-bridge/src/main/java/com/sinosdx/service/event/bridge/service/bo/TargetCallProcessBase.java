package com.sinosdx.service.event.bridge.service.bo;

import com.sinosdx.common.base.base.entity.Entity;

/**
 * @author pengjiahu
 * @date 2021-01-08 00:56
 * @description
 */
public abstract class TargetCallProcessBase extends Entity<Integer> {

    /**
     * 参数检查
     */
    public abstract void paramCheck();

    /**
     * 默认事件目标对象
     *
     * @return
     */
    public abstract String defaultTarget();
}
