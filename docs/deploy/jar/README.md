# 使用源码部署SINO APIX

## 环境要求
安装以下依赖软件：
- JDK1.8
- Maven3+
- nodejs v12
- Mysql v5.7或以上版本
- Nacos v2.0.3或以上版本
- Redis v3.2.8或以上版本

## 准备
1、 源码下载
```
git clone https://gitee.com/sinosdx/sino-apix.git
cd sino-apix
mvn clean install
```

## 操作步骤

1、导入相关配置和数据库

- 导入MySQL脚本，目录`/docs/mysql/`下四个数据库脚本文件。
- 导入Nacos脚本，目录`/docs/nacos/`下配置文件，在Nacos控制台导入即可。
- 进入Nacos控制台，修改`common-mysql.yml`数据库配置和`common-redis.yml`缓存配置成部署环境的配置。

2、后端构建

- 打包成jar，`mvn clean package -DskipTests`。
- 拷贝spring-cloud-gateway、service-api-management、service-auth、service-support-log、service-user各目录target下的jar包文件到和service.sh同一目录。

3、前端构建

cd website，将命令提示符跳转到front目录

- 执行`npm install`
这一步是下载nodejs相关依赖

前端请求的服务器地址配置在`.env.development`文件中，默认是`http://localhost:8080`

4、运行
- 后端启动
    - 启动：sh service.sh start
    - 停止：sh service.sh stop
    - 重启：sh service.sh restart
- 前端启动
    - 执行`npm run dev`

5、访问

访问`http://localhost:8080/`



















