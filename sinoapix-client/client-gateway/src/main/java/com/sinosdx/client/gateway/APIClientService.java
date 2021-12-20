package com.sinosdx.client.gateway;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sinosdx.client.gateway.callback.IResponseHandler;
import com.sinosdx.client.gateway.dto.*;
import com.sinosdx.client.gateway.http.Protocol;
import com.sinosdx.client.gateway.http.RespErrCode;
import com.sinosdx.client.gateway.util.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.*;
import java.util.Map.Entry;


/**
 * @author pengjiahu
 * @date 2021-08-16
 * @description
 */
@Slf4j
public class APIClientService implements Cloneable {

    private String appToken;
    private String gatewayAddr;
    private Integer gatewayPort;
    private Integer gatewayPortOfSSL;
    private String appID;
    private String authKey;
    private Integer defaultRequestExpireTime;
    private CloseableHttpClient httpClient;
    private CloseableHttpClient httpsClient;

    public APIClientService() {
        this.defaultRequestExpireTime = Constants.DEFAULT_HTTP_REQ_EXPIRED_TIME;
        PoolingHttpClientConnectionManager cmOfHttp = new PoolingHttpClientConnectionManager();
        cmOfHttp.setMaxTotal(Constants.DEFAULT_HTTPCLIENT_CONNECTIONPOOL_MAX_CONN);
        cmOfHttp.setDefaultMaxPerRoute(Constants.DEFAULT_HTTPCLIENT_CONNECTIONPOOL_MAX_PER_ROUTE);
        this.httpClient = HttpClients.custom().setConnectionManager(cmOfHttp)
                .setConnectionManagerShared(true).build();
        this.httpsClient = HttpClientUtil
                .createHttpSSLClient(Constants.DEFAULT_HTTPCLIENT_CONNECTIONPOOL_MAX_CONN,
                        Constants.DEFAULT_HTTPCLIENT_CONNECTIONPOOL_MAX_PER_ROUTE);
    }

    public void sendRequest(Request request, IResponseHandler responseHandler) {
        log.info("发送请求");
        assert StringUtils.isNotEmpty(this.gatewayAddr) && StringUtils.isNotEmpty(this.authKey)
                && StringUtils.isNotEmpty(this.appID);

        try {
            RespErrMsg respErrMsg;
            try {
                List<Header> headers = this.buildHeader(request);
                HttpEntity bodyEntity = this.buildBody(request);
                String uri = this.buildUri(request);
                if (log.isDebugEnabled()) {
                    log.debug("Request uri: " + uri);
                }

                Integer curReqExpiredTime = request.getRequestExpireTime();
                if (curReqExpiredTime == null) {
                    curReqExpiredTime = this.defaultRequestExpireTime;
                }

                RequestConfig timeOutConfig = this.buildTimeOutConfig(curReqExpiredTime);
                CloseableHttpResponse closeableHttpResponse = this
                        .doRequest(headers, request, bodyEntity, uri, timeOutConfig);
                this.doResponse(request, closeableHttpResponse, responseHandler);
            } catch (ConnectTimeoutException var15) {
                var15.printStackTrace();
                respErrMsg = new RespErrMsg();
                respErrMsg.setErrMsg("跟网关建立连接超时，" + var15.getMessage());
                respErrMsg.setErrCode(RespErrCode.ConnectTimeoutException);
                responseHandler.onFailure(request, respErrMsg);
            } catch (SocketTimeoutException var16) {
                var16.printStackTrace();
                respErrMsg = new RespErrMsg();
                respErrMsg.setErrMsg("网关响应超时，" + var16.getMessage());
                respErrMsg.setErrCode(RespErrCode.SocketTimeoutException);
                responseHandler.onFailure(request, respErrMsg);
            } catch (LoginException var17) {
                var17.printStackTrace();
                respErrMsg = new RespErrMsg();
                respErrMsg.setErrMsg("登录异常，" + var17.getMessage());
                respErrMsg.setErrCode(RespErrCode.AUTH_INVALID_ACCOUNT_OR_PSW);
                responseHandler.onFailure(request, respErrMsg);
            } catch (Exception var18) {
                var18.printStackTrace();
                respErrMsg = new RespErrMsg();
                respErrMsg.setErrMsg("调用目标服务异常，" + var18.getMessage());
                respErrMsg.setErrCode(RespErrCode.GATEWAY_UNAVAILABLE);
                responseHandler.onFailure(request, respErrMsg);
            }

        } finally {
            ;
        }
    }

    private void doResponse(Request request, CloseableHttpResponse closeableHttpResponse,
                            IResponseHandler responseHandler) throws Exception {
        try {
            HttpEntity entity = closeableHttpResponse.getEntity();
            String content = EntityUtils.toString(entity);
            if (log.isDebugEnabled()) {
                log.debug("Response content length: " + entity.getContentLength() + ", content: "
                        + content);
            }

            JSONObject jsonObject;
            try {
                jsonObject = JSON.parseObject(content);
            } catch (Exception var11) {
                jsonObject = null;
            }

            if (null != jsonObject && jsonObject.containsKey("errCode")) {
                RespErrMsg respErrMsg = JSON.parseObject(content, RespErrMsg.class);
                switch (respErrMsg.getErrCode()) {
                    case AUTH_TOKEN_EXPIRED:
                        this.loginAndUpdateToken(request.getContentType());
                        this.sendRequest(request, responseHandler);
                        break;
                    case AUTH_INVALID_TOKEN:
                    default:
                        responseHandler.onFailure(request, respErrMsg);
                }
            } else {
                responseHandler.onResponse(request,
                        this.buildResponse(closeableHttpResponse, content, entity));
            }
        } finally {
            closeableHttpResponse.close();
        }

    }

    private CloseableHttpResponse doRequest(List<Header> headers, Request request,
                                            HttpEntity bodyEntity, String uri, RequestConfig timeOutConfig) throws IOException {
        CloseableHttpResponse closeableHttpResponse = null;
        CloseableHttpClient theHttpClient = this.httpClient;
        if (request.getProtocol() == Protocol.https) {
            theHttpClient = this.httpsClient;
        }

        switch (request.getMethod()) {
            case GET:
                HttpGet httpGet = new HttpGet(uri);
                httpGet.setHeaders(headers.toArray(new Header[0]));
                httpGet.setConfig(timeOutConfig);
                closeableHttpResponse = theHttpClient.execute(httpGet);
                break;
            case POST:
                HttpPost httpPost = new HttpPost(uri);
                httpPost.setHeaders(headers.toArray(new Header[0]));
                httpPost.setConfig(timeOutConfig);
                if (bodyEntity != null) {
                    httpPost.setEntity(bodyEntity);
                }

                closeableHttpResponse = theHttpClient.execute(httpPost);
                break;
            case PUT:
                HttpPut httpPut = new HttpPut(uri);
                httpPut.setHeaders(headers.toArray(new Header[0]));
                httpPut.setConfig(timeOutConfig);
                if (bodyEntity != null) {
                    httpPut.setEntity(bodyEntity);
                }

                closeableHttpResponse = theHttpClient.execute(httpPut);
                break;
            case DELETE:
                HttpDelete httpDelete = new HttpDelete(uri);
                httpDelete.setHeaders(headers.toArray(new Header[0]));
                httpDelete.setConfig(timeOutConfig);
                closeableHttpResponse = theHttpClient.execute(httpDelete);
        }

        return closeableHttpResponse;
    }

    private RequestConfig buildTimeOutConfig(Integer reqExpireTime) {
        RequestConfig timeOutConfig = RequestConfig.copy(RequestConfig.DEFAULT)
                .setConnectionRequestTimeout(reqExpireTime).setConnectTimeout(reqExpireTime)
                .setSocketTimeout(reqExpireTime).build();
        return timeOutConfig;
    }

    private HttpEntity buildBody(Request request) {
        HttpEntity bodyEntity = null;
        if (null != request.getFormBody() && request.getFormBody().size() > 0) {
            List<BasicNameValuePair> nameValuePairs = new ArrayList();
            Iterator var4 = request.getFormBody().entrySet().iterator();

            while (var4.hasNext()) {
                Entry<String, String> entry = (Entry) var4.next();
                nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }

            bodyEntity = new UrlEncodedFormEntity(nameValuePairs,
                    request.getContentType().getCharset());
        } else if (StringUtils.isNotEmpty(request.getStringBody())) {
            bodyEntity = new StringEntity(request.getStringBody(), request.getContentType());
        }

        return bodyEntity;
    }

    private List<Header> buildHeader(Request request) throws Exception {
        List<Header> headers = new ArrayList();
        if (null != request.getHeaders()) {
            Iterator var3 = request.getHeaders().entrySet().iterator();

            while (var3.hasNext()) {
                Entry<String, String> entry = (Entry) var3.next();
                headers.add(new BasicHeader((String) entry.getKey(), (String) entry.getValue()));
            }
        }

        if (StringUtils.isNotEmpty(request.getRequestId())) {
            headers.add(new BasicHeader("api_gateway_reqID", request.getRequestId()));
        } else {
            headers.add(new BasicHeader("api_gateway_reqID", UUID.randomUUID().toString()));
        }

        headers.add(new BasicHeader("api_gateway_auth_app_id", this.appID));
        switch (request.getAuthType()) {
            case basic:
                headers.add(new BasicHeader("api_gateway_auth_app_id", this.appID));
                headers.add(new BasicHeader("api_gateway_auth_app_password", this.authKey));
                break;
            case token:
                if (null == this.appToken) {
                    this.loginAndUpdateToken(request.getContentType());
                }

                headers.add(new BasicHeader("api_gateway_auth_token", this.appToken));
        }

        return headers;
    }

    private void loginAndUpdateToken(ContentType contentType) throws LoginException {
        RequestConfig timeOutConfig = this.buildTimeOutConfig(this.defaultRequestExpireTime);
        AppLoginReqDTO appLoginReqDTO = new AppLoginReqDTO();
        appLoginReqDTO.setAppID(this.appID);
        appLoginReqDTO.setSecretKey(this.authKey);
        String reqBody = JSON.toJSONString(appLoginReqDTO);
        HttpEntity bodyEntity = new StringEntity(reqBody, contentType);
        String url = Protocol.https.name() + "://" + this.gatewayAddr + ":" + this.gatewayPortOfSSL
                + "/xbg/api/appLogin";
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(timeOutConfig);
        if (bodyEntity != null) {
            httpPost.setEntity(bodyEntity);
        }

        CloseableHttpResponse httpResponse = null;

        try {
            httpResponse = this.httpsClient.execute(httpPost);
            HttpEntity entity = httpResponse.getEntity();
            String content = EntityUtils.toString(entity);
            if (log.isDebugEnabled()) {
                log.debug("Response content length: " + entity.getContentLength() + ", content: "
                        + content);
            }

            AppLoginRespDTO respDTO = JSON.parseObject(content, AppLoginRespDTO.class);
            if (respDTO.getResult()) {
                this.appToken = respDTO.getToken();
            } else {
                String msg = "通过App ID登录失败, 具体原因如下：\n" + JSON.toJSONString(respDTO.getRespErrMsg());
                throw new LoginException(msg);
            }
        } catch (Exception var13) {
            log.error(var13.getMessage());
            throw new LoginException(var13.getMessage());
        }
    }

    private Response buildResponse(CloseableHttpResponse closeableHttpResponse, String content,
                                   HttpEntity entity) throws IOException {
        Response result = new Response();
        result.setBody(content);
        Arrays.stream(closeableHttpResponse.getAllHeaders()).forEach((e) -> {
            result.getHeaders().put(e.getName(), e.getValue());
            if (e.getName().equals("api_gateway_reqID")) {
                result.setRequestId(e.getValue());
            }

        });
        String contentTypeString = entity.getContentType().getValue();
        String charSet =
                contentTypeString.contains("charset=") ? contentTypeString.split("charset=")[1]
                        : "";
        result.setContentType(
                ContentType.create(entity.getContentType().getValue().split(";")[0], charSet));
        result.setStatusCode(closeableHttpResponse.getStatusLine().getStatusCode());
        return result;
    }

    public String buildUri(Request request) {
        StringBuilder sb = new StringBuilder();
        sb.append(request.getProtocol().name());
        sb.append("://");
        sb.append(this.gatewayAddr);
        sb.append(":");
        if (request.getProtocol() == Protocol.http) {
            sb.append(this.gatewayPort);
        } else {
            sb.append(this.gatewayPortOfSSL);
        }

        sb.append(request.getPath());
        StringBuilder queryPath = new StringBuilder();
        if (null != request.getQueryParams()) {
            for (Entry<String, String> stringStringEntry : request.getQueryParams().entrySet()) {
                Entry entry = stringStringEntry;
                queryPath.append(entry.getKey());
                queryPath.append("=");
                queryPath.append(entry.getValue());
                queryPath.append("&");
            }
        }

        String queryPathStr = queryPath.toString();
        queryPathStr = queryPathStr.length() < 1 ? ""
                : queryPathStr.substring(0, queryPathStr.length() - 1);
        if (queryPathStr.length() > 1) {
            sb.append("?");
            sb.append(queryPathStr);
        }

        return sb.toString();
    }

    public static class Builder {

        private APIClientService apiClientService = new APIClientService();

        public Builder() {
        }

        public Builder appID(String appID) {
            this.apiClientService.appID = appID;
            return this;
        }

        public Builder appAuthKey(String appAuthKey) {
            this.apiClientService.authKey = appAuthKey;
            return this;
        }

        public Builder gatewayAddr(String gatewayAddr) {
            this.apiClientService.gatewayAddr = gatewayAddr;
            return this;
        }

        public Builder gatewayPort(Integer gatewayPort) {
            this.apiClientService.gatewayPort = gatewayPort;
            return this;
        }

        public Builder gatewayPortOfSSL(Integer gatewayPortOfSSL) {
            this.apiClientService.gatewayPortOfSSL = gatewayPortOfSSL;
            return this;
        }

        public Builder requestExpireTime(String requestExpireTime) {
            try {
                this.apiClientService.defaultRequestExpireTime = Integer
                        .parseInt(requestExpireTime);
            } catch (Exception var3) {
                this.apiClientService.defaultRequestExpireTime = Constants.DEFAULT_HTTP_REQ_EXPIRED_TIME;
            }

            return this;
        }

        public Builder requestExpireTime(Integer requestExpireTime) {
            if (null != requestExpireTime) {
                this.apiClientService.defaultRequestExpireTime = requestExpireTime;
            } else {
                this.apiClientService.defaultRequestExpireTime = Constants.DEFAULT_HTTP_REQ_EXPIRED_TIME;
            }

            return this;
        }

        public Builder configuration(ClientConfig clientConfig) {
            this.apiClientService.appID = clientConfig.getAppID();
            this.apiClientService.authKey = clientConfig.getAppAuthKey();
            this.apiClientService.gatewayAddr = clientConfig.getGatewayAddr();
            this.apiClientService.gatewayPort = clientConfig.getGatewayPort();
            this.apiClientService.gatewayPortOfSSL = clientConfig.getGatewayPortOfSSL();

            try {
                this.apiClientService.defaultRequestExpireTime = Integer
                        .parseInt(clientConfig.getRequestExpireTime());
            } catch (Exception var3) {
                this.apiClientService.defaultRequestExpireTime = Constants.DEFAULT_HTTP_REQ_EXPIRED_TIME;
            }

            return this;
        }

        public APIClientService build() {
            APIClientService obj = null;

            try {
                obj = (APIClientService) this.apiClientService.clone();
            } catch (CloneNotSupportedException var3) {
                var3.printStackTrace();
            }

            return obj;
        }
    }
}


