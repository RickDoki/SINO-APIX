package com.sinosdx.client.gateway.dto;

import java.util.List;
import java.util.Map;

import com.sinosdx.client.gateway.Constants;
import com.sinosdx.client.gateway.http.AuthType;
import com.sinosdx.client.gateway.http.HttpCookie;
import com.sinosdx.client.gateway.http.HttpMethod;
import com.sinosdx.client.gateway.http.Protocol;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.http.entity.ContentType;

/**
 * @author pengjiahu
 * @date 2021-08-16
 * @description
 */
@Setter
@Getter
@NoArgsConstructor
public class Request {

    Map<String, HttpCookie> cookies;
    private Protocol protocol = Protocol.http;
    private AuthType authType = AuthType.token;
    private ContentType contentType = ContentType.APPLICATION_JSON;

    private HttpMethod method = HttpMethod.POST;
    private String path;
    private Map<String, String> headers;
    private Map<String, String> queryParams;
    private Map<String, String> formBody;
    private String stringBody;
    private byte[] bytesBody;
    private Long contentLength;
    private List<String> accept;
    private String requestId;
    private Integer requestExpireTime = Constants.DEFAULT_HTTP_REQ_EXPIRED_TIME;

    public Request(HttpMethod method, String path) {
        this.method = method;
        this.path = path;
    }

}

