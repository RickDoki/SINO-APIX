#基础镜像：拉取alpine版本的
FROM registry.sxc.sh/public/alpine-oraclejdk8:skywalking-agent-v2
MAINTAINER pengjiahu jiahu.peng@sinosdx.com
# 添加变量，如果使用dockerfile-maven-plugin，则会自动替换这里的变量内容
ARG JAR_FILE=target/*.jar
ARG JAR_PORT=8888
ARG ARG_JAVA_OPTS="-server -Xms4g -Xmx4g"
ARG ARG_SKYWALKING_AGENT=/usr/app/agent/skywalking-agent.jar
ARG ARG_SKYWALKING_SERVICE_NAME=service-unknown-DIN
ARG ARG_SKYWALKING_URL=10.135.141.252:24107
ARG ARG_APP_ENV="-Dlogstash.server.addr=10.135.141.252:20943"
ENV ENV_JAVA_OPTS=${ARG_JAVA_OPTS}
ENV ENV_SKYWALKING_AGENT=${ARG_SKYWALKING_AGENT}
ENV ENV_SKYWALKING_SERVICE_NAME=${ARG_SKYWALKING_SERVICE_NAME}
ENV ENV_SKYWALKING_URL=${ARG_SKYWALKING_URL}
ENV APP_ENV=${ARG_APP_ENV}

#将打包好的spring程序拷贝到容器中的指定位置
ADD ${JAR_FILE} /usr/app/app.jar
ADD docs/skywalking/ /usr/app/agent/plugins/
#配置时区
ENV TZ=PRC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
#容器对外暴露8888端口
EXPOSE ${JAR_PORT}
#容器启动后需要执行的命令
#ENTRYPOINT [ "sh", "-c", "java $ENV_JAVA_OPTS -javaagent:$ENV_SKYWALKING_AGENT -Dskywalking.agent.service_name=$ENV_SKYWALKING_SERVICE_NAME -Dskywalking.collector.backend_service=$ENV_SKYWALKING_URL -Djava.security.egd=file:/dev/./urandom $APP_ENV -jar /usr/app/app.jar"]
ENTRYPOINT [ "sh", "-c", "java $ENV_JAVA_OPTS -Djava.security.egd=file:/dev/./urandom $APP_ENV -jar /usr/app/app.jar"]
