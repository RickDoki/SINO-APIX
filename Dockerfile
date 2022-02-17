#
# Copyright © 2022 SinoSDX (biz@sinosdx.com)
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

#基础镜像：拉取alpine版本的
FROM registry.cn-shanghai.aliyuncs.com/sinosdx/alpine-oraclejdk8:skywalking-agent-v2
MAINTAINER pengjiahu jiahu.peng@sinosdx.com
# 添加变量，如果使用dockerfile-maven-plugin，则会自动替换这里的变量内容
ARG JAR_FILE=target/*.jar
ARG JAR_PORT=8888
ARG ARG_JAVA_OPTS="-server -Xms1g -Xmx1g -Xmn512m -XX:MetaspaceSize=64m -XX:MaxMetaspaceSize=256m -XX:-OmitStackTraceInFastThrow"
ENV ENV_JAVA_OPTS=${ARG_JAVA_OPTS}

#将打包好的spring程序拷贝到容器中的指定位置
ADD ${JAR_FILE} /usr/app/app.jar
ADD docs/skywalking/ /usr/app/agent/plugins/
#配置时区
ENV TZ=PRC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
#容器对外暴露8888端口
EXPOSE ${JAR_PORT}
#容器启动后需要执行的命令
ENTRYPOINT [ "sh", "-c", "java $ENV_JAVA_OPTS -Djava.security.egd=file:/dev/./urandom $APP_ENV -jar /usr/app/app.jar"]
