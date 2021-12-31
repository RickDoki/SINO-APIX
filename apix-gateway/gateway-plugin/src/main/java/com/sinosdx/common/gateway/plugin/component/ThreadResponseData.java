package com.sinosdx.common.gateway.plugin.component;

import com.sinosdx.common.base.context.SpringContextHolder;
import com.sinosdx.common.gateway.plugin.entity.ResponseData;
import com.sinosdx.common.gateway.plugin.event.ResponseDataEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author pengjiahu
 * @date 2021-12-31 15:13
 * @description
 */
@Slf4j
@AllArgsConstructor
public class ThreadResponseData implements Runnable {

   private ResponseData dataeee;

    @Override
    public void run() {
        SpringContextHolder.publishEvent(new ResponseDataEvent(this,dataeee));
    }
}
