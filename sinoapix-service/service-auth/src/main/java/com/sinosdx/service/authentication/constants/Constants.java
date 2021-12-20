package com.sinosdx.service.authentication.constants;

/**
 * @author wendy
 * @date 2020/12/10
 */
public class Constants {

    public static final String THREAD_CONTEXT_USER_ID = "userId";
    public static final String THREAD_CONTEXT_USERNAME = "username";
    public static final String THREAD_CONTEXT_MOBILE = "mobile";
    public static final String THREAD_CONTEXT_CLIENT_ID = "clientId";
    public static final String THREAD_CONTEXT_TOKEN = "token";
    public static final String THREAD_CONTEXT_IP = "ip";

    /**
     * AUTH_HEADER
     */
    public static final String AUTH_HEADER = "Authorization";

    /**
     * AUTH_HEADER_PREFIX
     */
    public static final String AUTH_HEADER_PREFIX = "Bearer ";

    public static final String LOGIN_GRANT_TYPE = "grant_type";
    public static final String LOGIN_TYPE = "login_type";
    public static final String LOGIN_USERNAME = "username";
    public static final String LOGIN_PASSWORD = "password";
    public static final String LOGIN_CLIENT_ID = "client_id";
    public static final String LOGIN_CLIENT_SECRET = "client_secret";
    public static final String LOGIN_ADMIN_PHONE = "13893319833";
    public static final String LOGIN_ADMIN_CLIENT_ID = "admin_client";
    public static final String LOGIN_ADMIN_CLIENT_SECRET = "4259683a31aaa7f7227e991d9753fecf";
    public static final String LOGIN_FRONT_CLIENT_ID = "client_1";
    public static final String LOGIN_FRONT_CLIENT_SECRET = "123456";

    /**
     * IP正则
     */
    public static final String IP_REGEX = "((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})(\\.((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})){3}";

    public static final String BLACKLIST_TYPE_IP = "IP";

    public static final String LOGIN_ERROR_COUNT = "login_error_count:";
    public static final String LOGIN_ERROR_LOCK = "login_error_lock:";
    public static final String GATEWAY_WHITELIST = "gateway_whitelist";

    /**
     * 企业微信扫码登录
     */
    public static final String LOGIN_TYPE_QYWX = "qywx";
    /**
     * csp营销门户登录后免密登录
     */
    public static final String LOGIN_TYPE_CSP = "csp2";

    /**
     * 图片验证码UUID
     */
    public static final String VERIFICATION_CODE = "verification_code:";
}
