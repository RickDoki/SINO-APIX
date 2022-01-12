package com.sinosdx.service.management.constants;

/**
 * @author wendy
 * @date 2020/12/10
 */
public class Constants {

    public static final String OAUTH_SCOPE = "all";
    public static final String AUTHORIZED_GRANT_TYPES = "client_credentials,password,refresh_token";
    public static final Integer ACCESS_TOKEN_VALIDITY = 7200;
    public static final Integer REFRESH_TOKEN_VALIDITY = 36000;

    /**
     * AUTH_HEADER
     */
    public static final String AUTH_HEADER = "Authorization";

    /**
     * csp2.0 jwt
     */
    public static final String AUTH_JWT = "JWT";

    /**
     * AUTH_HEADER_PREFIX
     */
    public static final String AUTH_HEADER_PREFIX = "Bearer ";

    /** 应用未发布 */
    public static final String APP_STATUS_IS_NOT_PUBLISHED = "60001";
    /** 应用已发布 */
    public static final String APP_STATUS_IS_PUBLISHED = "60005";
    /** 应用已上架（启用） */
    public static final String APP_STATUS_IS_ADDED = "60002";
    /** 应用异常 */
    public static final String APP_STATUS_ERROR = "60003";
    /** 应用停用（下架） */
    public static final String APP_STATUS_OFF = "60004";
    /** API已发布 */
    public static final String API_IS_PUBLISHED = "60011";
    /** API未发布 */
    public static final String API_IS_NOT_PUBLISHED = "60012";

    /** 用户角色：开发者 */
    public static final Integer ROLE_DEVELOPER = 50012;

    public static final String THREAD_CONTEXT_USER_ID = "userId";
    public static final String THREAD_CONTEXT_USERNAME = "username";
    public static final String THREAD_CONTEXT_PHONE = "phone";
    public static final String THREAD_CONTEXT_CLIENT_ID = "clientId";
    public static final String THREAD_CONTEXT_TOKEN = "token";

    public static final String REDIS_PREFIX_APP_PLUGIN = "app_plugin_";

    /**
     * 中文正则
     */
    public static final String REGEX_CHINESE = ".*[\u4e00-\u9fa5]+.*";

    /**
     * IP正则
     */
    public static final String REGEX_IP = "((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})(\\.((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})){3}";
}
