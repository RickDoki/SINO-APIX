package com.sinosdx.service.management.service;

import com.alibaba.fastjson.JSONObject;
import com.sinosdx.service.management.controller.dto.ApplicationNumDTO;
import com.sinosdx.service.management.controller.vo.ApplicationNumVo;
import com.sinosdx.service.management.controller.vo.ApplicationVersionVo;
import com.sinosdx.service.management.controller.vo.ApplicationVo;
import com.sinosdx.service.management.dao.entity.Application;
import com.sinosdx.service.management.dao.entity.ApplicationPlugin;
import com.sinosdx.service.management.dao.entity.ApplicationSubscribe;
import com.sinosdx.service.management.result.R;

import java.util.List;
import java.util.Map;

/**
 * @author wendy
 * @date 2020/12/7
 */
public interface ApplicationService {

    /**
     * 创建新应用
     *
     * @param application
     * @return
     */
    R<Object> createApplication(Application application);

    /**
     * 查询应用列表
     *
     * @param developerId 开发者id（传入此参数显示开发者关联的应用列表）
     * @param appName
     * @param appCode
     * @param appId
     * @param isPublished 是否发布到资源市场（传入此参数显示资源市场应用列表）
     * @param startTime   开始时间戳查询（根据创建时间）
     * @param endTime     结束时间戳查询（根据创建时间）
     * @param limit
     * @param offset
     * @return
     */
    R<Object> queryApplicationList(Integer developerId, String appName, String appCode, Integer appId,
                                   String isPublished, Long startTime, Long endTime, Integer limit, Integer offset, Boolean market);

    /**
     * 查看应用详情
     *
     * @param appCode
     * @param developerId
     * @return
     */
    R<Object> queryApplicationDetails(String appCode, Integer developerId);

    /**
     * 修改应用
     *
     * @param applicationVo
     * @return
     */
    R<Object> updateApplication(ApplicationVo applicationVo);

    /**
     * 删除应用
     *
     * @param appCode
     * @return
     */
    R<Object> deleteApplication(String appCode);

    /**
     * 发布应用新版本
     *
     * @param applicationVersionVo
     * @return
     */
    R<Object> publishNewAppVersion(ApplicationVersionVo applicationVersionVo);

    /**
     * 绑定应用服务（使用资源市场应用服务）
     *
     * @param appLesseeCode
     * @param appLessorCode
     * @return
     */
    R<Object> appLease(String appLesseeCode, String appLessorCode);

    /**
     * 订阅服务
     *
     * @param appSubscribedCode
     * @return
     */
    R<Object> appSubscribe(String appSubscribedCode);

    /**
     * 解除订阅服务
     *
     * @param appSubscribedCode
     * @return
     */
    R<Object> unSubscribe(String appSubscribedCode);

    /**
     * 添加应用开发者
     *
     * @param appCode
     * @param phone
     * @return
     */
    R<Object> addAppDeveloper(String appCode, String phone);

    /**
     * 查看应用开发者列表
     *
     * @param appCode
     * @return
     */
    R<Object> queryAppDeveloperList(String appCode);

    /**
     * 移除应用开发者
     *
     * @param appCode
     * @param developerId
     * @return
     */
    R<Object> deleteAppDeveloper(String appCode, Integer developerId);

    /**
     * 提供首页仪表盘所需第一行数据
     * 首页仪表盘：
     * <p>
     * 一、第一行
     * 1、开发者应用总数
     * 形式：数值
     * 说明：当前平台内所有应用的数量，包含创建未发布、上架和未上架的
     * 2、开发者API总数
     * 形式：数值
     * 说明：所有状态正常的API总数
     * 3、上架开放应用总数
     * 形式：数值
     * 说明：上架的应用数量，不区分应用版本
     * 4、开发者总数
     * 形式：数值
     * 说明：权限包含开发者权限的用户总数
     * <p>
     * 五、第五行
     * 1、24小时上架应用健康度
     * 形式：饼图，初版仅展示不同状态的应用数量，后续再开发点击至具体列表页，初版需支持手动数据库导出明细
     * 应用上架状态说明：
     * （1）正常(即启用)(绿色)：状态为启用的正常上架应用；
     * （2）异常(黄色)：统计24小时内，应用的接口出现超时或网络异常、以及中台内部应用抛出堆栈错误，都需捕获持久化，日志服务定时轮询扫描发现后，将该应用状态置为异常，仅能通过开发者或运营者手动变更为启用。
     * （3）停用(红色)：上架应用的开发者手动可以手动停用，此时网关认证是无法通过的，应用对接肯定会报错（应用对接状态的监控也会关联并置为异常），所以用红色展示，需手动启用或合规删除才能变更此状态；
     *
     * @return
     */
    R<Map<String, Object>> queryAppDataForDashboard();

    /**
     * 根据code查询app
     *
     * @param appCode
     * @return
     */
    R<Application> queryAppByAppCode(String appCode);

    /**
     * 查询对接应用时的应用列表（不包含当前应用）
     *
     * @param appCode
     * @param developerId
     * @return
     */
    R<List<Map<String, String>>> queryAppNameAndCodeForLease(String appCode, Integer developerId);

    /**
     * 查询当前开发者所有对接应用
     *
     * @param developerId
     * @return
     */
    R<List<Map<String, Object>>> queryAllAppLeaseByDeveloper(Integer developerId);

    /**
     * 移除应用对接
     *
     * @param appLesseeCode
     * @param appLessorCode
     * @return
     */
    R<Object> removeAppLease(String appLesseeCode, String appLessorCode);

    /**
     * 查询调用当前应用的应用编号列表
     *
     * @param lessorAppCode
     * @return
     */
    R<List<String>> queryAppCodeListByLessorAppCode(String lessorAppCode);

    /**
     * 通过查询clientId验证ApplicationLease是否存在
     *
     * @param clientId
     * @return
     */
    R<Boolean> verifyClientId(String clientId);

    /**
     * 注销解绑客户端token
     *
     * @param appCode
     * @return
     */
    R<Object> revokeClientToken(String appCode);

    /**
     * 查询已订阅应用列表
     *
     * @param developerId 开发者id
     * @param appName
     * @param appCode
     * @param appId
     * @param limit
     * @param offset
     * @return
     */
    R<Object> querySubscribedAppList(Integer developerId, String appName, String appCode, Integer appId, Integer limit, Integer offset);

    /**
     * 查看已订阅应用详情
     *
     * @param appCode
     * @return
     */
    R<Object> querySubscribedAppDetail(String appCode);

    /**
     * 查询产品列表
     *
     * @return
     */
    R<Object> queryProductList();

    /**
     * 查询首页 应用数量，及 api 数量
     *
     * @param applicationNumVo
     * @return
     */
    ApplicationNumDTO queryApplicationNum(ApplicationNumVo applicationNumVo);
    /**
     * 查询应用内部请求数、请求失败数、已订阅数
     *
     * @param appCode
     * @return
     */
    Long applicationSubscribeNum(String appCode, Long startTime,Long endTime);

    /**
     * 查询订阅当前应用的应用列表
     *
     * @param appCode
     * @param developerId
     * @return
     */
    R<Object> querySubscribedAppList(String appCode, Integer developerId);

    /**
     * 服务添加插件
     *
     * @param applicationPlugin
     * @return
     */
    R<Object> addAppPlugin(ApplicationPlugin applicationPlugin);

    /**
     * 修改服务插件
     *
     * @param applicationPlugin
     * @return
     */
    R<Object> updateAppPlugin(ApplicationPlugin applicationPlugin);

    /**
     * 获取服务插件
     *
     * @param pluginId
     * @param appCode
     * @return
     */
    R<Object> getAppPlugin(String pluginId, String appCode);

    /**
     * UserIds 转换为  ClientIds
     * @param userIds
     * @return
     */
    List<Integer> changeUserIdsToClientIds(List<Integer> userIds);

    /**
     * 更新appVersion
     * @param applicationVersionVo
     * @return
     */
    R<Object> updateAppVersion(Integer appVersionId,ApplicationVersionVo applicationVersionVo);

    /**
     * 删除appVersion
     * @param appVersionId
     * @return
     */
    R<Object> deleteAppVersion(Integer appVersionId);

    /**
     * 查询appVersion详情
     * @param appVersionId
     * @return
     */
    R<Object> queryAppVersion(Integer appVersionId);

    /**
     * 查询服务插件的配置参数
     *
     * @param pluginType
     * @param appCode
     * @return
     */
    R<JSONObject> queryPluginConfigs(String pluginType, String appCode);

    /**
     * 根据订阅编号查询订阅信息
     *
     * @param subscribeCode
     * @return
     */
    R<ApplicationSubscribe> queryAppCodeBySubscribeCode(String subscribeCode);

    /**
     * 查询鉴权过滤器链
     *
     * @param appCode
     * @return
     */
    R<List<String>> queryAppAuthPluginNameList(String appCode);
}
