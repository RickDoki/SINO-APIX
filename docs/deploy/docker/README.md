# 使用Docker部署SINO APIX

## 环境要求

## 准备

## 操作步骤

1. 下载对应版本的镜像：docker pull sinosdx/sino-apix:{version}

2. 通过环境变量方式修改redis配置（其它配置同理）并运行镜像
```
docker run --rm -d -p 18765:18765 \
 -e "apix.redis.host={your redis host IP}" \
 -e "apix.redis.port={your redis port}" \
 -e "apix.redis.password={your redis password}" \
 -e "apix.redis.database={your redis database}" \
 sinosdx/sino-apix
```




















