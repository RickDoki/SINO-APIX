package com.sinosdx.client.gateway.dto;

import org.apache.http.entity.ContentType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author pengjiahu
 * @date 2021-08-16
 * @description
 */
public class Response {

    private ContentType contentType = ContentType.APPLICATION_JSON;

    private Map<String, String> headers = new HashMap<>();

    private String body;

    private int statusCode;

    private String requestId;

    public int getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public ContentType getContentType() {
        return this.contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getHeader(String key) {
        if (null != this.headers) {
            return this.headers.get(key);
        }
        return null;
    }

    public void setHeader(String key, String value) {
        if (null == this.headers) {
            this.headers = new HashMap<>();
        }
        this.headers.put(key, value);
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getRequestId() {
        return this.requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}


