package com.sinosdx.service.user.constants;

/**
 * @author wendy
 * @date 2021/2/26
 */
public class UrlConstant {

    /**
     * 博云soc获取区域
     */
    public static final String SOC_GET_REGION = "%s/api/cmp/a1/pool/groups/azList";

    /**
     * 从csp1 1.0获取租户
     */
    public static final String tenantsMap = "tenants/map";

    /**
     * 从csp 2.0获取租户
     */
    public static final String tenantsMap2 = "api/soc/a1/tenants/seqNo";

    /**s
     * 获取用户信息
     */
    public static final String GET_USER_INFO =  "%s/api/soc/a1/users/info?mobile=%s";

    /**
     * 获取区域列表
     */
    public static final String REGION_LIST =  "%s/api/cmp/a1/dictionaries/regionList";

    /**
     * 获取c01租户列表
     */
    public static final String ALL_C01_TENANTS =  "%s/v2/user/allTenants";

    /**
     * 获取soc租户列表
     */
    public static final String GET_SOC_TENANT_LIST = "%s/api/soc/a1/tenants/seqNo?seqNo=%s";
}
