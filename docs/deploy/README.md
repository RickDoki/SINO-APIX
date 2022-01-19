## 基础环境搭建

## 1. JDK
1.安装java运行环境
```
Ubuntu:
apt install openjdk-8-jdk
```
```
Centos:
yum install java-1.8.0-openjdk
```
```
Windows:
下载JDK安装包 https://www.oracle.com/java/technologies/downloads/
配置JAVA运行环境
JAVA_HOME : JDK安装目录
Path : JDK安装目录\bin
重启电脑
```
## 2. CentOS快速安装环境
- 下载安装脚本
安装 jdk, git, maven, redis, mysql
```
wget -O download-install-all.sh https://gitee.com/sinosdx/sino-apix/tree/master/docs/deploy/env/download-install-all.sh
```
- 运行安装脚本
```
sh download-install-all.sh
```

## 3. docker-standalone ---mac系统本地化脚本
已在mac系统上进行测试验证，支持docker-compose一键部署
- nacos 2.0.0
- mysql 8.0.21
- redis 6.0.9
- rocketmq 当前最新版本

### 操作命令

- 启动
```bash
cd doc/deploy/docker-standalone

docker-compose up -d
```
- 其他
```bash
# 关闭
docker-compose stop
# 卸载
docer-compose down
# 启动
docker-compose start
```

## 4. Nacos 服务发现与配置
下载Nacos 1.2.1

https://github.com/alibaba/nacos/releases/tag/1.2.1

启动Nacos

sh nacos/bin/startup.sh -m standalone

Nacos文档

https://nacos.io/zh-cn/docs/quick-start.html

访问Nacos控制台

http://localhost:8848/nacos 

Nacos控制台账号密码：nacos/nacos

- docker方式
```
docker pull nacos/nacos-server
docker run --name nacos -itd -p 8848:8848 -p 9848:9848 -p 9849:9849 --restart=always -e MODE=standalone nacos/nacos-server
```

## 5. Sentinel 熔断/限流等
下载Sentinel 1.7.2

https://github.com/alibaba/Sentinel/releases/tag/1.7.2

Sentinel文档

https://sentinelguard.io/zh-cn/docs/quick-start.html

启动Sentinel
 ```
java -Dserver.port=8600 -Dcsp.sentinel.dashboard.server=localhost:8600 -Dproject.name=sentinel-dashboard -jar sentinel-dashboard-1.7.2.jar
 ```
访问Sentinel控制台

http://localhost:8600 Sentinel控制台账号密码：sentinel/sentinel

## 6. Redis
- docker方式
```
docker pull redis:latest
docker run -itd --name redis -p 6379:6379 redis
```

## 7. Mysql
- docker方式
```
docker pull mysql:latest
docker run -itd --name mysql-test -p 3306:3306 -e MYSQL_ROOT_PASSWORD=123456 mysql
```
## 8. 其他
- 基础环境redis、mysql、nacos也可通过docker-compose编排方式

文件：base-env-docker-compose.yml

执行命令
```
docker-compose -f base-env-docker-compose.yml up -d
```
