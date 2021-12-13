package com.sinosdx.common.base.constants;

/**
 * @author wendy
 * @date 2021/2/26
 */
public class Constants {

    public static final String REDIS_PREFIX_ACCESS_TOKEN = "iam_access_token";
    public static final String REDIS_PREFIX_REFRESH_TOKEN = "iam_refresh_token";
    public static final Long REDIS_EXPIRED_TIME_ACCESS_TOKEN = 1800L;
    public static final Long REDIS_EXPIRED_TIME_REFRESH_TOKEN = 3600L;
    public static final Long REDIS_EXPIRED_TIME_USER_TOKEN = 28800L;
    public static final Long REDIS_EXPIRED_TIME_MIDDLEWARE_TOKEN = 7200L;
    public static final Long REDIS_EXPIRED_TIME_SOC_TOKEN = 60L;
    public static final String REDIS_PREFIX_SMS_TICKET = "sms_ticket:";
    public static final String REDIS_PREFIX_IAM_USER_TOKEN = "iam_user_token:";
    public static final String REDIS_PREFIX_IAM_DIGEST_KEY = "iam_digest_key:";
    public static final String REDIS_PREFIX_VM_ADMIN_TOKEN = "vm_admin_token";
    public static final String REDIS_PREFIX_IAM_JWT = "iam_jwt:";
    public static final String REDIS_PREFIX_SMS_ACCESS_TOKEN = "sms_access_token";
    public static final String REDIS_PREFIX_REGISTER_MOBILE = "register_mobile:";
    public static final String REDIS_PREFIX_MOBILE_UUID = "mobile_uuid:";
    public static final String REDIS_PREFIX_MIDDLEWARE_TOKEN = "middleware_token:";
    public static final String REDIS_PREFIX_SOC_TOKEN = "soc_token:";
    public static final String REDIS_PREFIX_PV = "PV_URL";
    public static final String REDIS_PREFIX_PRODUCT_SUBSCRIBE = "product_subscribe:";
    public static final String REDIS_PREFIX_CSP2_DIGEST_KEY = "csp2_digest_key:";

    public static final String REGEX_MOBILE = "^[1][3-9]+\\d{9}$";
    public static final String REGEX_EMAIL = "^[a-z0-9A-Z]+[- | a-z0-9A-Z . _]+@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-z]{2,}$";

    public static final String AUTH_HEADER_AUTHORIZATION = "Authorization";
    public static final String AUTH_HEADER_JWT = "JWT";
    public static final String AUTH_HEADER_TOKEN = "token";
    public static final String AUTH_HEADER_PREFIX = "Bearer ";

    public static final String THREAD_CONTEXT_JWT = "jwt";
    public static final String THREAD_CONTEXT_MOBILE = "mobile";
    public static final String THREAD_CONTEXT_EMAIL = "email";
    public static final String THREAD_CONTEXT_IP = "ip";
    public static final String THREAD_CONTEXT_URI = "uri";
    public static final String THREAD_CONTEXT_USER_TOKEN = "user_token";

    public static final String INTERFACE_TYPE_INTERNAL = "internal";
    public static final String INTERFACE_TYPE_EXTERNAL = "external";

    public static final String PV_TYPE_SRC = "src";
    public static final String PV_TYPE_AI = "ai";
    public static final String PV_TYPE_ZABBIX = "zabbix";
    public static final String PV_TYPE_ZABBIX_C2B = "c2b";
    public static final String PV_TYPE_ZABBIX_TOC = "toc";
    public static final String PV_TYPE_SLB = "slb";

}
