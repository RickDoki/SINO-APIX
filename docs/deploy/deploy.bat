@echo on
@echo =============================================================
@echo $                                                           $
@echo $                     Sinosdx Cloud                         $
@echo $                                                           $
@echo $                                                           $
@echo $                                                           $
@echo $  Sinosdx Studio All Right Reserved                        $
@echo $  Copyright (C) 2020-2050                                  $
@echo $                                                           $
@echo =============================================================
@echo.
@echo off

@title Sinosdx Cloud

call mvn clean deploy -DskipTests -e -P pro -pl ^
sinosdx-common/common-public,^
sinosdx-common/common-tools,^
sinosdx-common/common-usf,^
sinosdx-common-model/common-model-log,^
parent,^
sinosdx-spring-boot-starter/sinosdx-alarm-spring-boot-starter,^
sinosdx-spring-boot-starter/sinosdx-api-version-spring-boot-starter,^
sinosdx-spring-boot-starter/sinosdx-dingtalk-robot-spring-boot-starter,^
sinosdx-spring-boot-starter/sinosdx-event-bus-spring-boot-starter,^
sinosdx-spring-boot-starter/sinosdx-feign-spring-boot-starter,^
sinosdx-spring-boot-starter/sinosdx-jetcache-spring-boot-starter,^
sinosdx-spring-boot-starter/sinosdx-jwt-spring-boot-starter,^
sinosdx-spring-boot-starter/sinosdx-log-spring-boot-starter,^
sinosdx-spring-boot-starter/sinosdx-mongodb-spring-boot-starter,^
sinosdx-spring-boot-starter/sinosdx-mysql-spring-boot-starter,^
sinosdx-spring-boot-starter/sinosdx-nacos-spring-boot-starter,^
sinosdx-spring-boot-starter/sinosdx-oss-spring-boot-starter,^
sinosdx-spring-boot-starter/sinosdx-rabbitmq-spring-boot-starter,^
sinosdx-spring-boot-starter/sinosdx-redis-spring-boot-starter,^
sinosdx-spring-boot-starter/sinosdx-rocketmq-spring-boot-starter,^
sinosdx-spring-boot-starter/sinosdx-sa-token-spring-boot-starter,^
sinosdx-spring-boot-starter/sinosdx-seata-spring-boot-starter,^
sinosdx-spring-boot-starter/sinosdx-sentinel-spring-boot-starter,^
sinosdx-spring-boot-starter/sinosdx-stream-kafka-spring-boot-starter,^
sinosdx-spring-boot-starter/sinosdx-stream-rabbitmq-spring-boot-starter,^
sinosdx-spring-boot-starter/sinosdx-stream-rocketmq-spring-boot-starter,^
sinosdx-spring-boot-starter/sinosdx-swagger-spring-boot-starter,^
sinosdx-spring-boot-starter/sinosdx-work-weixin-robot-spring-boot-starter,^
sinosdx-spring-boot-starter/sinosdx-xxljob-spring-boot-starter,^
sinosdx-spring-boot-starter/sinosdx-zookeeper-spring-boot-starter,^
sinosdx-support/support-email^
 -am

pause
