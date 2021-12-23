package com.sinosdx.common.base.constants;

/**
 * @author wendy
 * @date 2021/2/26
 */
public class UrlConstant {

    /**
     * 获取IAM access_token
     */
    public static final String GET_IAM_ACCESS_TOKEN_URL = "%s/api/v1/oauth/token?client_id=%s&client_secret=%s";
    /**
     * 刷新IAM access_token
     */
    public static final String REFRESH_IAM_ACCESS_TOKEN_URL = "%s/api/v1/oauth/token?client_id=%s&client_secret=%s&refresh_token=%s";
    /**
     * IAM 判断手机号是否已注册
     */
    public static final String IAM_JUDGE_MOBILE_HAS_REGISTERED_URL = "%s/api/v1/register/checkmobile?access_token=%s";

    /**
     * IAM 普通用户手机验证码登录接口
     */
    public static final String IAM_USER_LOGIN_WITH_SMS_CODE_URL = "%s/api/v1/login/login_with_sms?access_token=%s";

    /**
     * IAM获取图片验证码接口
     */
    public static final String IAM_GET_PIC_VALIDATE_CODE = "%s/api/v1/common/get_pic_validate_code?access_token=%s";

    /**
     * IAM验证图片验证码接口
     */
    public static final String IAM_VALIDATE_PIC_CODE = "%s/api/v1/common/validate_pic_code?access_token=%s";

    /**
     * IAM有图片发送手机验证码接口
     */
    public static final String IAM_SEND_SMS_CODE = "%s/api/v1/common/send_sms_code?access_token=%s";

    /**
     * IAM验证手机验证码接口
     */
    public static final String IAM_VALIDATE_SMS_CODE = "%s/api/v1/common/validate_sms_code?access_token=%s";

    /**
     * IAM用户注册接口
     */
    public static final String IAM_USER_REGISTER = "%s/api/v1/register/create_account?access_token=%s";

    /**
     * 创建公司
     */
    public static final String BUSINESS_MANAGE_MARKET_COMPANY = "%s/api/market/company";

    /**
     * 创建部门
     */
    public static final String BUSINESS_MANAGE_MARKET_DEPARTMENT = "%s/api/market/department";

    /**
     * 创建租户
     */
    public static final String BUSINESS_MANAGE_MARKET_TENANT = "%s/api/market/tenant";

    /**
     * 根据user_token获取jwt
     */
    public static final String IAM_GET_JWT = "%s/v2/oauth2/getJWTV1?userToken=%s";

    /**
     * 根据jwt获取digestKey
     */
    public static final String IAM_GET_DIGEST_KEY = "%s/v1/digest";

    /**
     * 根据JWT获取用户信息
     */
    public static final String IAM_GET_USER_INFO_FROM_JWT = "%s/v1/userInfo/%s";

    /**
     * 根据user_token获取用户信息
     */
    public static final String IAM_GET_USER_INFO_FROM_USER_TOKEN = "%s/api/v1/user/get_userdetail?access_token=%s";

    /**
     * 开通产品配额
     */
    public static final String CSP_OPEN_PRODUCT_QUOTA = "%s/api/quota/market/apply";

    /**
     * 初始化DBass用户
     */
    public static final String INITIALIZE_DBASS_USER = "%s/api/v1/users";

    /**
     * 开通DBass配额（DBass平台）
     */
    public static final String OPEN_DBASS_QUOTA = "%s/api/v1/users/%s";

    /**
     * 获取token专门为Csp提供
     */
    public static final String ECS_PRODUCT_USER_TOKEN = "%s/jitstack/v3/user/getTokenByDigestKey";

    /**
     * 获取云主机区域
     */
    public static final String ECS_PRODUCT_GET_REGION = "%s/jitstack/v3/api/regions/regionlistandorg";

    /**
     * 获取云主机参数1
     */
    public static final String ECS_PRODUCT_GET_PARAM_FIRST = "%s/jitstack/v3/api/instanceModles/addlistget?regionId=%s&instancePooltype=1";

    /**
     * 获取云主机参数2
     */
    public static final String ECS_PRODUCT_GET_PARAM_SECOUND = "%s/jitstack/v3/api/instanceModles/settingsget?regionId=%s&type=server&status=enable";

    /**
     * 获取创建硬盘所需参数
     */
    public static final String ECS_PRODUCT_GET_SYS_CINDER_POOLS = "%s/jitstack/v3/api/syscinderpools/listbyorg?regionId=%s";

    /**
     * 获取子网port
     */
    public static final String ECS_PRODUCT_GET_PORT_LIST = "%s/jitstack/v3/api/ports/list?subnetworkId=%s&regionId=%s";


    /**
     * 获取创建子网dns
     */
    public static final String ECS_PRODUCT_GET_DNS = "%s/jitstack/v3/api/regions/dns/%s";


    /**
     * 创建硬盘
     */
    public static final String ECS_PRODUCT_CREATE_SYS_CINDER_POOLS = "%s/jitstack/v3/api/volumes/add";

    /**
     * 创建vpc网络 生产可用,uat不可用
     * 暂时将完整路径配置到 配置文件
     */
    public static final String ECS_PRODUCT_CREATE_SYS_NETS = "%s/vm3/sysnetadd";


    /**
     * 创建vpc子网
     */
    public static final String ECS_PRODUCT_CREATE_SUB_NET_WORKS = "%s/jitstack/v3/api/subnetworks/add";

    /**
     * 创建安全组
     */
    public static final String ECS_PRODUCT_CREATE_SECURITY_GROUPS = "%s/jitstack/v3/api/securitygroups/add";


    /**
     * 初始化VM用户
     */
    public static final String INITIALIZE_VM_USER = "%s/jitstack/v3/orgs/market/add";

    /**
     * 获取VM admin token
     */
    public static final String GET_VM_ADMIN_TOKEN = "%s/jitstack/v3/user/getToken";

    /**
     * 发送产品开通成功短信
     */
    public static final String SEND_PRODUCT_SMS = "https://openapi.saicmotor.com/opensaic/cloud/sms/v1.0.0/msg/469";

    /**
     * 获取短信平台access_token
     */
    public static final String GET_SMS_ACCESS_TOKEN = "http://openapi.saicmotor.com/api/am/store/v0.12/applications/getAccessToken?Authorize=Basic NnBlaHpudHZzYTo1YTI0MjNiMy1mYTFmLTRlMjQtOTdkMy1mYThhMGQxZmVhNGE=";

    /**
     * vdi通过租户查询账户
     */
    public static final String VDI_FIND_ACCOUNT = "%s/api/vdi/account/%s";


    /**
     * ecs 创建vm
     */
    public static final String ECS_PRODUCT_CREATE_INSTANCE = "%s/jitstack/v3/api/instances/add";

    /**
     * devops,创建用户账号
     */
    public static final String DEVOPS_CREATE_USER = "%s/iam/choerodon/v1/organizations/5/users";

    /**
     * devops,获取角色信息
     */
    public static final String DEVOPS_GET_ROLE = "%s/iam/choerodon/v1/organizations/5/roles?role_name=%s";

    /**
     * CSP开通安全产品配额
     */
    public static final String CSP_OPEN_SECURITY_PRODUCT = "%s/api/market/service/open?tenantShortName=%s&serviceKey=%s&iamOpenid=%s";

    /**
     * 查询主机组ID
     */
    public static final String TP_HOST_GROUPS = "%s/tp/host-groups?name=%s";

    /**
     * 绑定主机组ID
     */
    public static final String TP_HOST_Id = "%s/tp/host/%s";

    /**
     * 查询设备未绑定对象
     */
    public static final String TP_HOST_UNBOUND="%s/tp/host/%s/path/unbound";

    /**
     * 绑定设备/分组与对象
     */
    public static final String TP_RULE_CREATE="%s/tp/rule/create";

    /**
     *查询已绑定对象
     */
    public static final String TP_STRATEGY_QUERY="%s/tp/strategy/query";


    /**
     * 新建策略
     */
    public static final String  TP_STRATEGY_CREATE="%s/tp/strategy/create";
    /**
     * 根据IP开启/停止策略
     */
    public static final String TP_STRATEGY_ENABLE="%s/tp/strategy/ip/enable";

    /**
     * 根据token获取二级用户信息
     */
    public static final String IAM_GET_SECOND_USER_INFO_FROM_USER_TOKEN = "%s/api/v1/user/get_info2?access_token=%s";

    /**
     * vdi升级接口
     */
    public static final String UPGRADE_VDI = "%s/api/vdi/mod";

    /**
     * SRC产品开通
     */
    public static final String SRC_URL="https://tps.saicsecurity.com/j/%s";

    /**
     *腾讯安全威胁情报云查服务
     */
    public static final String TICS_URL="https://xti.qq.com";

    /**
     * 获取中间件接口token
     */
    public static final String GET_MIDDLEWARE_TOKEN = "%s/api/oauth/checkUser";

    /**
     * 创建中间件实例
     */
    public static final String CREATE_MIDDLEWARE = "%s/api/%s/orchestrator/%s/create";

    /**
     * 获取中间件实例
     */
    public static final String GET_MIDDLEWARE_INSTANCE = "%s/api/%s/orchestrator/instance/%d";

    /**
     * 生成样本分析行为报告接口
     */
    public static final String START_SAMPLE_ANALYSIS = "https://api.habo.qq.com/api/start_analyse?pk=%s&md5=%s";

    /**
     * 创建博云soc租户
     */
    public static final String CREATE_SOC_TENANT = "%s/api/soc/a1/users";

    /**
     * 博云soc登录
     */
    public static final String SOC_LOGIN = "%s/api/soc/v1/users/login";

    /**
     * 博云soc登出
     */
    public static final String SOC_LOGOUT = "%s/api/soc/v1/users/logout";

    /**
     * 博云soc刷新token
     */
    public static final String SOC_REFRESH_TOKEN = "%s/api/soc/v1/token";

    /**
     * 博云soc验证存量用户
     */
    public static final String SOC_USER_REGISTERED = "%s/api/soc/v1/users/byMobile?condition=%s";

    /**
     * 获取c01菜单信息
     */
    public static final String GET_MENUS = "%s/fsh_nav/menu/getMenus?shortName=%s&timestemp=%s";

    /**
     * 获取soc租户列表
     */
    public static final String GET_SOC_TENANT_LIST = "%s/api/soc/a1/tenants/seqNo?seqNo=%s";

    /**
     * 获取c01租户列表
     */
    public static final String GET_C01_TENANT_LIST = "%s/tenants/map?shortName=%s";

    /**
     * 获取c01租户公司列表
     */
    public static final String GET_C01_TENANT_COMPANY_LIST = "%s/tenantGroups/map?shortName=%s";

    /**
     * 删除OSS实例
     */
    public static final String DELETE_OSS_INSTANCE = "https://oss.console.fin-shine.com/SaicStackOSS/oss/delete/bucket/%d?username=%s";

    /**
     * 删除OSS实例
     */
    public static final String DELETE_RANCHER_INSTANCE = "https://www.sail-cloud.com/by-console/api-rancher/v3/project/%s/workloads/%s";

}
