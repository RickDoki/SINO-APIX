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
package com.sinosdx.service.user.constants;

/**
 * @author wendy
 * @date 2021/2/26
 */
public class Constants {

    public static final String REDIS_PREFIX_ACCESS_TOKEN = "iam_access_token";
    public static final String REDIS_PREFIX_REFRESH_TOKEN = "iam_refresh_token";
    public static final Long REDIS_EXPIRED_TIME_ACCESS_TOKEN = 1800L;
    public static final Long REDIS_EXPIRED_TIME_REFRESH_TOKEN = 3600L;
    public static final Long REDIS_EXPIRED_TIME_USER_TOKEN = 7200L;
    public static final Long REDIS_EXPIRED_TIME_SOC_TOKEN = 60L;
    public static final Long REDIS_EXPIRED_TIME_DIGEST_KEY = 32400L;
    public static final String REDIS_PREFIX_DIGEST_KEY = "digest_key:";
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
    public static final String THREAD_CONTEXT_USER_ID = "userId";
    public static final String THREAD_CONTEXT_USERNAME = "username";
    public static final String THREAD_CONTEXT_PHONE = "phone";
    public static final String THREAD_CONTEXT_TOKEN = "token";

    public static final String INTERFACE_TYPE_INTERNAL = "internal";
    public static final String INTERFACE_TYPE_EXTERNAL = "external";

    public static final String DEFAULT_PASSWORD = "Saic1234@";

}
