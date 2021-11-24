package com.sinosdx.client.event.service;


import com.sinosdx.client.event.CloudEvent;

/**
 * @author pengjiahu
 * @date 2020-04-26
 * @description
 */
public interface IEventCompletion {

    void onCompletion(CloudEvent cloudEvent);
}
