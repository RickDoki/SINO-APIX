package com.sinosdx.service.user.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.auth0.jwt.interfaces.Claim;
import com.sinosdx.common.base.result.R;
import com.sinosdx.service.user.constants.Constants;
import com.sinosdx.service.user.constants.UrlConstant;
import com.sinosdx.service.user.result.ResultCodeEnum;
import com.sinosdx.service.user.service.SocService;
import com.sinosdx.service.user.service.TenantsService;
import com.sinosdx.service.user.service.dto.Tenant;
import com.sinosdx.service.user.service.dto.TenantsGroup;
import com.sinosdx.service.user.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author shenjian
 * @create 2021-06-25 10:04
 * @Description 从csp1.0和csp2.0获取租户信息，返回给dbaas
 */
@Service
@Slf4j
@RefreshScope
public class TenantsServiceImpl implements TenantsService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private SocService socService;

    /**
     * 从csp1.0获取租户信息
     */
    @Value("${domain.csp1:http://user-common8080.c01.shjq-uat-a.sxc.sh/}")
    private String domainCsp1;

    /**
     * 从csp2.0获取租户信息
     */
    @Value("${domain.csp2:http://10.130.171.14:60008/}")
    private String domainCsp2;

    @Value("${domain.Csp2UserInfo:http://10.135.141.252:22783}")
    private String domainCsp2UserInfo;

    @Value("${jwt.secretKey:this_is_so_secret}")
    private String jwtSecretKey;

    private final String clientId = "Saic-dbass";

    private final String clientSecret = "U2FpYy12bW0xMjM0";

    @Override
    public  HashMap<String, Tenant> getTenants(){
        HashMap<String, Tenant> tenants = new HashMap<>();
        //从csp1.0获取租户信息
        String url1=domainCsp1+ UrlConstant.tenantsMap;
        log.info("getTenants.url1=="+url1);
//        String url1 = "http://user-common8080.c01.shjq-uat-a.sxc.sh/tenants/map";
        ResponseEntity<String> resp1 = restTemplate.getForEntity(url1, String.class);
        JSONObject jsonObject1 = JSONObject.parseObject(resp1.getBody());
        Map<String, Object> map1 = JSONObject.parseObject(jsonObject1.toJSONString(), new TypeReference<Map<String, Object>>() {});
        Object[] strings = map1.keySet().toArray();
        for(int i=0;i<strings.length;i++){
            Object shortName=map1.get(strings[i]);
            Map<String,Object> map2 = (Map<String,Object>)shortName;
            tenants.put(map2.get("shortName").toString(),new Tenant(map2.get("name").toString(),map2.get("shortName").toString(),
                    map2.get("description").toString(),map2.get("id").toString()));
        }
        //从csp2.0获取租户信息
        String url2=domainCsp2+ UrlConstant.tenantsMap2;
//        String url2="http://10.130.171.14:60008/api/soc/a1/tenants/seqNo";
        log.info("getTenants.url2=="+url2);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("clientId",clientId);
        httpHeaders.set("clientSecret",clientSecret);
        HttpEntity httpEntity = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<String> resp2 = restTemplate.exchange(url2, HttpMethod.GET ,httpEntity, String.class);
        JSONObject jsonObject2 = JSONObject.parseObject(resp2.getBody());
        JSONArray data = JSONObject.parseArray(jsonObject2.get("data").toString());
        for (int i=0;i<data.size();i++){
            Map<String,Object> maps = (Map<String,Object>)data.get(i);
            tenants.put(maps.get("seqNo").toString(),new Tenant(maps.get("name").toString(),maps.get("seqNo").toString(),
                    maps.get("name").toString(),maps.get("uuid").toString()));
        }
        log.info("向dbaas返回的租户信息===="+tenants);
        return tenants;
    }

    @Override
    public List<TenantsGroup> getTenantsGroup(String tenantId) {
        TenantsGroup g1 = new TenantsGroup(1.53922606005308E9,"groupA",33,"Group01",tenantId,1.53922606005308E9);
        TenantsGroup g2 = new TenantsGroup(1.5476276567615E9,"123",34,"tewt123",tenantId,1.54762765676151E9);
        List<TenantsGroup> groups = new ArrayList<>();
        groups.add(g1);
        groups.add(g2);
        return groups;
    }

    @Override
    public Map<String, Object> getUserInfo(String jwt) {
        Map<String, Claim> claimMap = JwtUtil.verifyJwt(jwtSecretKey, jwt);
        String open_id = claimMap.get("iam_openid").asString();
        String user_token = claimMap.get("user_token").asString();
        String phone_number = claimMap.get("phone_number").asString();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("clientId",clientId);
        httpHeaders.set("clientSecret",clientSecret);
        HttpEntity httpEntity = new HttpEntity<>(null, httpHeaders);

        String url = String.format(UrlConstant.GET_USER_INFO,domainCsp2UserInfo,phone_number);
        log.info("getUserInfo.url=="+url);
//        ResponseEntity<String> resp = restTemplate.postForEntity(url,httpEntity,String.class);
        ResponseEntity<String> resp = restTemplate.exchange(url,HttpMethod.GET,httpEntity,String.class);

        JSONObject jsonObject = JSONObject.parseObject(resp.getBody());
        Map<String, Object> map = JSONObject.parseObject(jsonObject.toJSONString(), new TypeReference<Map<String, Object>>() {});
        log.info("getUserInfo.map=="+map);
        Object[] strings = map.keySet().toArray();
        Map<String, Object> returnMap = new HashMap<>();
        for(int i=0;i<strings.length;i++){
            String key=strings[i].toString();
            returnMap.put(key,map.get(key));
        }
        returnMap.put("iam_openid",open_id);
        returnMap.put("user_token",user_token);
        returnMap.put("address","上海");
        returnMap.put("agreement",false);
        returnMap.put("company_name","rancher");
        returnMap.put("oauth2_info_completed",true);
        returnMap.put("platform_role","platform_member");
        return returnMap;
    }

    @Override
    public JSONObject getRegionList() {
        String url= String.format(UrlConstant.REGION_LIST,domainCsp2UserInfo);
        log.info("getRegionList.url=="+url);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("clientId",clientId);
        httpHeaders.set("clientSecret",clientSecret);
        HttpEntity httpEntity = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<String> resp = restTemplate.exchange(url,HttpMethod.GET,httpEntity,String.class);
        JSONObject jsonObject = JSONObject.parseObject(resp.getBody());
        log.info("getRegionList.jsonObject==="+jsonObject);
        return jsonObject;
    }

    @Override
    public R<Object> getAllTenants(String jwt) {
        String sub = null;
        String mobile = null;
        try {
            Map<String, Claim> claimMap = JwtUtil.verifyJwt(null, jwt);
            if (null == claimMap) {
                return R.fail(ResultCodeEnum.VERIFY_JWT_ERROR);
            }
            sub = claimMap.get("sub").asString();
            mobile = claimMap.get("phone_number").asString();

        } catch (Exception e) {
            e.printStackTrace();
            return R.fail(ResultCodeEnum.VERIFY_JWT_ERROR);
        }

        // csp2 jwt
        if ("csp2".equals(sub)) {
            R<Object> socTenantListR = socService.getSocTenantList("csp2" + mobile);
            if (socTenantListR.notSuccess()) {
                return R.fail(ResultCodeEnum.USER_NOT_EXIST);
            }

            List<JSONObject> socTenantList = (List<JSONObject>) socTenantListR.getData();
            List<JSONObject> dataList = new ArrayList<>();
            for (JSONObject tenant : socTenantList) {
                JSONObject jsonObject = new JSONObject();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
                Long createDateTime = null;
                try {
                    Date createDate = dateFormat.parse(tenant.getString("gmtCreate"));
                    createDateTime = createDate.getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                jsonObject.put("created_at", createDateTime);
                jsonObject.put("default_access_rule", "1");
                jsonObject.put("description", mobile);
                jsonObject.put("group", tenant.getInteger("id"));
                jsonObject.put("name", tenant.getString("name"));
                jsonObject.put("short_name", mobile);
                jsonObject.put("tenant_role", "tenant_admin");
                dataList.add(jsonObject);
            }

//            Map<String, Object> resultMap = new HashMap<>();
//            resultMap.put("code", "00000");
//            resultMap.put("data", dataList);
            return R.success(dataList);
        }

        // c01 jwt
        String url = String.format(UrlConstant.ALL_C01_TENANTS, domainCsp1);
        //headers
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add(Constants.AUTH_HEADER_AUTHORIZATION, Constants.AUTH_HEADER_PREFIX + jwt);
        //body
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        //HttpEntity
        HttpEntity<Map> requestEntity = new HttpEntity<>(requestBody, requestHeaders);
        ResponseEntity<String> responseEntity = null;
        try {
            // 处理返回值中文乱码
            List<HttpMessageConverter<?>> httpMessageConverters = restTemplate.getMessageConverters();
            httpMessageConverters.forEach(httpMessageConverter -> {
                if(httpMessageConverter instanceof StringHttpMessageConverter){
                    StringHttpMessageConverter messageConverter = (StringHttpMessageConverter) httpMessageConverter;
                    messageConverter.setDefaultCharset(StandardCharsets.UTF_8);
                }
            });
            responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        } catch (RestClientException e) {
            log.error("获取c01租户列表失败", e);
            e.printStackTrace();
            return R.fail(ResultCodeEnum.INTERFACE_OUTER_INVOKE_ERROR);
        }
        JSONObject jsonObject = JSONObject.parseObject(responseEntity.getBody());
        log.info(String.format("调用%s返回结果%s", url, jsonObject));

        if (!jsonObject.containsKey("code") || !"00000".equals(jsonObject.getString("code"))) {
            log.error("获取c01租户列表失败");
            return R.fail(ResultCodeEnum.INTERFACE_OUTER_INVOKE_ERROR);
        }

        return R.success(jsonObject.getJSONArray("data"));
    }

}
