package com.sinosdx.service.log.mq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sinosdx.common.base.context.SpringContextHolder;
import com.sinosdx.common.model.log.constants.LogConstant;
import com.sinosdx.service.log.dao.entity.*;
import com.sinosdx.service.log.properties.LogConfig;
import com.sinosdx.service.log.service.*;
import com.sinosdx.starter.redis.service.RedisService;
import com.sinosdx.support.email.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

/**
 * @author pengjiahu
 * @date 2021-06-19 00:54
 * @description
 */
@Slf4j
@Component
public class LogStreamMessage {

    public static final String ENTITY = "entity";
    public static final String TYPE = "type";
    public static final String REQUEST_PATH = "requestPath";

    @Autowired
    private IApiLogService apiLogService;

    @Autowired
    private IErrorLogService errorLogService;

    @Autowired
    private IBizLogService bizLogService;

    @Autowired
    private IGatewayLogService gatewayLogService;

    @Autowired
    private ILoginLogService loginLogService;

    @Autowired
    private IAuditLogService auditLogService;

    @Autowired
    private StreamBridge streamBridge;

    @Autowired
    private RedisService redisService;

    @Autowired
    private LogConfig logConfig;

    @Autowired
    private EmailService emailService;

    @Bean
    public Function<Flux<Message<Object>>, Mono<Void>> log() {
        return flux -> flux.map(message -> {
            try {
                String msg = message.getPayload().toString();
                JSONObject jsonObject = JSON.parseObject(msg).getJSONObject(ENTITY);
                String type = jsonObject.getString(TYPE);
                String requestPath = jsonObject.getString(REQUEST_PATH);
                log.debug("MQ,接收到[{}]日志信息：{}", type, JSON.toJSONString(msg));
                //数据处理
                jsonObject.remove(TYPE);
                String appCode = requestPath.split("/")[1];
                jsonObject.put("appCode", appCode);
                if (StringUtils.isBlank(type)) {
                    log.error("日志消费到type类型为null的数据，请检查！");
                } else {
                    switch (type) {
                        case LogConstant.API:
                            ApiLog apiLog = jsonObject.toJavaObject(ApiLog.class);
                            apiLogService.save(apiLog);
                            break;
                        case LogConstant.BIZ:
                            BizLog bizLog = jsonObject.toJavaObject(BizLog.class);
                            bizLogService.save(bizLog);
                            break;
                        case LogConstant.ERROR:
                            ErrorLog errorLog = jsonObject.toJavaObject(ErrorLog.class);
                            errorLogService.save(errorLog);
                            SpringContextHolder.getBean(LogStreamMessage.class)
                                    .errorRequestIpCache(errorLog.getRemoteIp());
                            boolean result = streamBridge
                                    .send("errorLog-out-0",
                                            MessageBuilder.withPayload(errorLog).build());
                            if (!result) {
                                log.error("log service send error log mq error");
                            }
                            break;
                        case LogConstant.GATEWAY:
                            GatewayLog gatewayLog = jsonObject.toJavaObject(GatewayLog.class);
                            gatewayLogService.save(gatewayLog);
                            break;
                        case LogConstant.LOGIN:
                            LoginLog loginLog = jsonObject.toJavaObject(LoginLog.class);
                            loginLogService.save(loginLog);
                            break;
                        case LogConstant.AUDIT:
                            AuditLog auditLog = jsonObject.toJavaObject(AuditLog.class);
                           /* if (StringUtils.isNotEmpty(auditLog.getAuthorization())) {
                                Claims claims = Jwts.parser()
                                        .setSigningKey("sinosdx".getBytes(StandardCharsets.UTF_8))
                                        .parseClaimsJws(auditLog.getAuthorization()).getBody();
                                auditLog.setUserId((Integer) claims.get("userId"));
                                auditLog.setUserName((String) claims.get("username"));
                                auditLog.setMobile((String) claims.get("phone"));
                            }*/
                            // TODO: 登录注册的审计日志可能需要特殊处理
                            auditLogService.save(auditLog);
                            break;
                        default:
                            log.error("未匹配到类型");
                            break;
                    }
                }
            } catch (Exception e) {
                log.error("日志消息消费错误", e);
                return message;
            }
            return message;
        }).then();
    }

    /**
     * 缓存异常请求ip（一天）
     *
     * @param ip
     */
    @Async
    public void errorRequestIpCache(String ip) {
        if (!logConfig.isEnable()) {
            return;
        }
        if (StringUtils.isBlank(ip)) {
            log.warn("异常请求客户端ip缓存错误，参数ip为空!");
            return;
        }
        boolean allow = redisService
                .allowLimitTimes(LogConstant.EXCEPTION_LOG_CACHE_KEY, ip, logConfig.getInterval(),
                        logConfig.getCount());
        if (!allow) {
            redisService.set(LogConstant.EXCEPTION_LOG_BLACKLIST_CACHE_KEY + ":" + ip, ip,
                    logConfig.getBlacklistTime());
            //邮件通知
            log.info("异常请求客户端ip缓存错误,IP:{}", ip);
            String content =
                    "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"></haed><body><style>table{font-size:14px;}</style><div align=\"center\"><font color=\"#525252\">\n"
                            + "<table border=\"0\" style=\"border:5px solid #F2F2F2;\" cellspacing=\"2\" cellpadding=\"2\" width=\"80%\" style=\"table-layout:fixed\">\n"
                            + "<tr bgcolor=\"#D1D1D1\"><th align=\"center\" style=\"font-size:23px;color:ff0000;\">异常请求超过阈值加入黑名单</marquee></th></tr>\n"
                            + "<tr><td align=\"left\" style=\"font-family:微软雅黑; size=5\" style=\"WORD-WRAP:break-word\"><b>IP：</b>"
                            + ip + "</td></tr>\n"
                            + "<tr><td align=\"left\" style=\"font-family:微软雅黑; size=5\" style=\"WORD-WRAP:break-word\"><b>阈值时间间隔（秒）：</b>"
                            + logConfig.getInterval() + "</td></tr>\n"
                            + "<tr><td align=\"left\" style=\"font-family:微软雅黑; size=5\" style=\"WORD-WRAP:break-word\"><b>阈值在时间间隔内的次数：</b>"
                            + logConfig.getCount() + "</td></tr>\n"
                            + "<tr><td align=\"left\" style=\"font-family:微软雅黑; size=5\" style=\"WORD-WRAP:break-word\"><b>黑名单过期时间：</b>"
                            + logConfig.getBlacklistTime() + "</td></tr>\n"
                            + "</table><p>此邮件为CSP2.0-技术中台系统自动发送，请勿回复!</p></body></html>";
            emailService.send(logConfig.getEmailSubject(), content, logConfig.getReceiverList());
        }
    }
}
