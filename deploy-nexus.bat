@echo on
@echo =============================================================
@echo $                                                           $
@echo $                     SINO APIX                             $
@echo $                                                           $
@echo $                                                           $
@echo $                                                           $
@echo $  Sinosdx Studio All Right Reserved                        $
@echo $  Copyright (C) 2020-2050                                  $
@echo $                                                           $
@echo =============================================================
@echo.
@echo off

@title SINO APIX

call mvn clean deploy -DskipTests -e -pl ^
apix-client/client-collect-api,^
apix-client/client-gateway,^
apix-common/common-base,^
apix-common/common-gateway,^
apix-core,^
apix-dependencies/base-dependencies,^
apix-dependencies/parent,^
apix-dependencies/service-parent,^
apix-gateway/gateway-plugin,^
apix-spring-boot-starter^
 -am

pause
