package com.sinosdx.client.event;

import com.sinosdx.client.event.service.IEventBusService;
import com.sinosdx.client.event.service.IEventCompletion;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.UUID;

/**
 * 事件
 *
 * @author pengjiahu
 * @date 2020-11-17 15:50
 * @description
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CloudEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 事件总线名称
     */
    @Length(max = 128)
    @NotBlank(message = "事件总线名称不能为空")
    private String busName = "DefaultEventBus";

    /**
     * 事件唯一标识
     */
    @Length(max = 40)
    @NotBlank(message = "事件唯一标识不能为空")
    private String eventUuid = UUID.randomUUID().toString();

    /**
     * 事件源
     */
    @Length(max = 128)
    @NotBlank(message = "事件源不能为空")
    private String source;

    /**
     * 事件内容
     */
    private Object data;

    /**
     * 规格版本，默认1.0
     */
    private String specVersion = "1.0";

    /**
     * 事件类型标识
     */
    @Length(max = 64)
    @NotBlank(message = "事件类型标识不能为空")
    private String type;

    public static CloudEvent builder() {
        return new CloudEvent();
    }

    public CloudEvent busName(String busName) {
        this.busName = busName;
        return this;
    }

    public CloudEvent eventUuid(String eventUuid) {
        this.eventUuid = eventUuid;
        return this;
    }

    public CloudEvent source(String source) {
        this.source = source;
        return this;
    }

    public CloudEvent data(String data) {
        this.data = data;
        return this;
    }

    public CloudEvent specVersion(String specVersion) {
        this.specVersion = specVersion;
        return this;
    }

    public CloudEvent type(String type) {
        this.type = type;
        return this;
    }

    public CloudEvent build(IEventCompletion eventCompletion) {
        CloudEvent cloudEvent = build();
        eventCompletion.onCompletion(cloudEvent);
        return cloudEvent;
    }

    public CloudEvent build(IEventBusService eventBusService) {
        CloudEvent cloudEvent = build();
        eventBusService.saveEvent(cloudEvent);
        return cloudEvent;
    }

    public CloudEvent build() {
        CloudEvent cloudEvent = new CloudEvent();
        cloudEvent.setBusName(busName == null ? "DefaultEventBus" : busName);
        cloudEvent.setEventUuid(eventUuid == null ? UUID.randomUUID().toString() : eventUuid);
        cloudEvent.setSource(source);
        cloudEvent.setType(type);
        cloudEvent.setSpecVersion(specVersion == null ? "1.0" : specVersion);
        cloudEvent.setData(data);
        return cloudEvent;
    }
}
