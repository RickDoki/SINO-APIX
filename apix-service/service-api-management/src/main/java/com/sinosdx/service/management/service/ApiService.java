package com.sinosdx.service.management.service;

import com.sinosdx.service.management.controller.vo.ApiVersionVo;
import com.sinosdx.service.management.controller.vo.ApiVo;
import com.sinosdx.service.management.dao.entity.Api;
import com.sinosdx.service.management.dao.entity.ApiVersion;
import com.sinosdx.service.management.result.R;

import java.util.List;

/**
 * @author wendy
 * @date 2020/12/10
 */
public interface ApiService {

    /**
     * 创建API
     *
     * @param api
     * @return
     */
    R<Object> createApi(Api api);

    /**
     * 修改API
     *
     * @param apiVo
     * @return
     */
    R<Object> updateApi(ApiVo apiVo);

    /**
     * 删除API
     *
     * @param apiId
     * @return
     */
    R<Object> deleteApi(Integer apiId);

    /**
     * 查询API列表
     *
     * @param apiName
     * @param apiUrl
     * @param requestMethod
     * @param apiVersion
     * @param startTime
     * @param endTime
     * @param limit
     * @param offset
     * @param developerId
     * @return
     */
    R<Object> queryApiList(String apiName, String apiUrl, String requestMethod, String apiVersion,
                           Long startTime, Long endTime, Integer limit, Integer offset, Integer developerId);

    /**
     * 创建api新版本
     *
     * @param apiVersion
     * @return
     */
    R<Object> addNewApiVersion(ApiVersion apiVersion);

    /**
     * 修改Api历史版本
     *
     * @param apiVersionVo
     * @return
     */
    R<Object> modifyApiVersion(ApiVersionVo apiVersionVo);

    /**
     * 查询api模板列表
     *
     * @param name
     * @param url
     * @param requestMethod
     * @param limit
     * @param offset
     * @return
     */
    R<Object> queryApiTemplateList(String name, String url, String requestMethod, Integer limit, Integer offset);

    /**
     * 查看api模板
     *
     * @param id
     * @return
     */
    R<Object> getApiTemplate(Integer id);

    /**
     * 查看api详情
     *
     * @param apiId
     * @return
     */
    R<Object> queryApiDetail(Integer apiId);

    /**
     * 查看api详情
     *
     * @param appCode
     * @param appVersionId
     * @return
     */
    R<Object> queryApiListByAppVersionId(String appCode,Integer appVersionId);

    /**
     * 查询api列表中未被其他服务或服务版本使用的api
     *
     * @param apiList
     * @param appId
     * @return
     */
    R<List<Api>> getApiListNotUsedByOtherAppOrAppVersion(List<Api> apiList, Integer appId);

}
