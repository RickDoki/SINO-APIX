/*
 * Copyright © 2022 SinoSDX (biz@sinosdx.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sinosdx.service.user.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sinosdx.common.base.result.R;
import com.sinosdx.common.usf.configuration.SSLRestTemplate;
import com.sinosdx.service.user.constants.UrlConstant;
import com.sinosdx.service.user.enums.ResultCodeEnum;
import com.sinosdx.service.user.service.SocService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * @author wendy
 * @date 2021/6/17
 */
@Service
@Slf4j
@RefreshScope
public class SocServiceImpl implements SocService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SSLRestTemplate sslRestTemplate;

    @Value("${csp.soc.domain:http://10.130.171.14:60008}")
    private String cspSocDomain;

    @Value("${csp.soc.client.id:Saic-C01}")
    private String cspSocClientId;

    @Value("${csp.soc.client.secret:U2FpYy12bW0xMjM0}")
    private String cspSocClientSecret;

    /**
     * 获取区域列表
     *
     * @param region
     * @param serviceKey
     * @return data:{
     * 	"service_code":"vmware.storage.oss",
     * 	"region":"zz",
     * 	"azList": [
     *        {
     * 		"availablitiyZone":"ZZ-JK",
     * 		"availablitiyZoneName":"郑州经开",
     * 		"cluster":"ZZ-JK-UAT",
     * 		"clusterName":"郑州经开测试"
     *    }
     * 	]
     * }
     */
    @Override
    public R<Object> getRegionList(String region, String serviceKey) {
        switch (serviceKey) {
            case "redis":
                serviceKey = "kvs.redis";
                break;
            default:
        }

        String url = String.format(UrlConstant.SOC_GET_REGION, cspSocDomain);
        //headers
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        requestHeaders.add("clientId", cspSocClientId);
        requestHeaders.add("clientSecret", cspSocClientSecret);
        //body
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        JSONObject paramJson = new JSONObject();
        paramJson.put("serviceCode", serviceKey);
        paramJson.put("region", region);
        requestBody.add("params", paramJson.toJSONString());
        //HttpEntity
        HttpEntity<Map> requestEntity = new HttpEntity<>(requestBody, requestHeaders);
        //post
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        JSONObject jsonObject = JSONObject.parseObject(responseEntity.getBody());
        log.info(String.format("调用%s返回结果%s", url, jsonObject));

        if (!jsonObject.containsKey("success")
                || !jsonObject.getBoolean("success")) {
            log.error("soc获取区域信息失败");
            return R.success(Collections.emptyList());
        }

        JSONObject data = jsonObject.getJSONObject("data");
        List<JSONObject> regionResp = getC01TemplateRegionList(data, region);

        return R.success(regionResp);
    }

    private List<JSONObject> getC01TemplateRegionList(JSONObject data, String region) {
        List<JSONObject> regionResp = new ArrayList<>();
        JSONArray azList = data.getJSONArray("azList");
        for (Object o : azList) {
            JSONObject clusterJson = (JSONObject) o;
            if (null == region) {
                region = clusterJson.getString("region");
            }
            String availablitiyZone = clusterJson.getString("availablitiyZone");
            String availablitiyZoneName = clusterJson.getString("availablitiyZoneName");
            String cluster = clusterJson.getString("cluster");
            String clusterName = clusterJson.getString("clusterName");
            String regionName = clusterJson.getString("regionName");

            // 构建c01格式的返回值
            JSONObject newClusterJson = new JSONObject();
            newClusterJson.put("cluster", cluster);
            newClusterJson.put("clusterActions", Arrays.asList("tenant_member", "create", "update", "remove", "retrive"));
            newClusterJson.put("clusterName", clusterName);
            String envType = null;
            if (cluster.toLowerCase().contains("prod")) {
                envType = "PROD";
            } else {
                envType = "UAT";
            }
            newClusterJson.put("envType", envType);
            newClusterJson.put("region", region);
            newClusterJson.put("regionClusterAliasName", cluster);
            newClusterJson.put("regionClusterKeyName", cluster);
            newClusterJson.put("regionClusterName", clusterName);
            newClusterJson.put("regionName", regionName);
            newClusterJson.put("zone", availablitiyZone);
            newClusterJson.put("zoneName", availablitiyZoneName);
            regionResp.add(newClusterJson);
        }
        return regionResp;
    }

    /**
     * 获取soc租户列表
     *
     * @param shortName
     * @return
     */
    @Override
    public R<Object> getSocTenantList(String shortName) {
        String url = String.format(UrlConstant.GET_SOC_TENANT_LIST, cspSocDomain, shortName);
        //headers
        HttpHeaders requestHeaders = new HttpHeaders();
        //        requestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        //        requestHeaders.add(Constants.AUTH_HEADER_JWT, jwt);
        requestHeaders.add("clientId", cspSocClientId);
        requestHeaders.add("clientSecret", cspSocClientSecret);
        //body
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        //HttpEntity
        HttpEntity<Map> requestEntity = new HttpEntity<>(requestBody, requestHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        JSONObject jsonObject = JSONObject.parseObject(responseEntity.getBody());
        log.info(String.format("调用%s返回结果%s", url, jsonObject));

        if (!jsonObject.containsKey("success") || !jsonObject.getBoolean("success")) {
            log.error("获取soc租户列表失败");
            return R.fail(ResultCodeEnum.INTERFACE_OUTER_INVOKE_ERROR);
        }
        return R.success(jsonObject.get("data"));
    }

}
