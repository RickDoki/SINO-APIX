package com.sinosdx.common.gateway.plugin.event;

import com.sinosdx.common.gateway.plugin.entity.ResponseData;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author pengjiahu
 * @date 2021-12-31 15:53
 * @description
 */
@Getter
public class ResponseDataEvent extends ApplicationEvent {

    private final ResponseData responseData;

    public ResponseDataEvent(Object source, ResponseData responseData) {
        super(source);
        this.responseData = responseData;
    }
}
