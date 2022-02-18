# 使用Docker-Compose部署SINO APIX

## 环境要求
安装以下依赖软件：
- Docker-Compose

## 准备
1、参考[源码部署](../jar/README.md)方法中的前后端构建，拷贝后端jar文件和前端dist文件夹到本目录

2、构建前后端服务为docker镜像

3、推送到公共镜像仓库


## 操作步骤

1、拉取 `/docs/depoly/docker-compose/docker-compose.yml`

2、执行 `docker-compose up -d`

3、第一次启动失败,需再次执行 步骤 2

## 运行

访问：`${ip}:8080`




















