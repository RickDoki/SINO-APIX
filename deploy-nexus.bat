@REM
@REM Copyright © 2022 SinoSDX (biz@sinosdx.com)
@REM
@REM Licensed under the Apache License, Version 2.0 (the "License");
@REM you may not use this file except in compliance with the License.
@REM You may obtain a copy of the License at
@REM
@REM     http://www.apache.org/licenses/LICENSE-2.0
@REM
@REM Unless required by applicable law or agreed to in writing, software
@REM distributed under the License is distributed on an "AS IS" BASIS,
@REM WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@REM See the License for the specific language governing permissions and
@REM limitations under the License.
@REM

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
