# api linker 开放平台


## API Linker是什么？

## 演示环境（Demo）

## 设计
### 模块结构
```lua
SinoSdx
    └── docs                                --开发文档
        ├── deploy                          --包含多种部署方式说明（k8s、虚机、docker），及对应的sql脚本、nacos配置、启动脚本等文件
        ├── images                  
        ├── 分支管理.md              
        ├── 升级日志.md              
        ├── 自定义配置.md              
        └── 常见问题.md              
    └── sinosdx-dependencies                --框架依赖模块                                 
        ├── parent                          --基本库，负责引入lib库版本定义、maven发布配置、环境配置等
        ├── starter-dependencies            --自定义组件依赖定义
        └── service-dependencies            --微服务依赖定义
    └── sinosdx-client                      --客户端模块                                  
        ├── client-gateway                  --客户端调用接口SDK
        ├── client-collect-api              --客户端快速收集API push到平台SDK
    └── sinosdx-common                      --公共模块                                 
        ├── common-base                     --基础公共类（BaseResponse、Exception、Constants...）
        └── common-gateway                  --网关公共类（用于后期支持不同类型网关）
    └── sinosdx-core                        --框架及核心代码                            
    └── sinosdx-gateway                     --网关模块
        ├── spring-cloud-gateway            --spring cloud gateway网关
        └── gateway-plugin                  --插件库核心代码（用于后期支持不同类型网关）                          
    └── sinosdx-service                     --微服务集                                 
        ├── service-api-management          --管理平台服务
        ├── service-auth                    --认证授权服务
        ├── service-support-log             --日志服务
        └── service-user                    --用户服务
    └── sinosdx-spring-boot-starter         --自定义组件模块                                
    └── sinosdx-example                     --测试服务模块                                
        ├── demo1              
        └── demo2
    └── sinosdx-website                     --前端工程                                
```

### 目录约定
   	示例：
   	sinosdx-middle-----------------以下目录为项目约定目录结构
        com.sinosdx.service.base
            controller
                vo-------接口实体 
            dao
                entity------db对应实体
                mapper------mapper接口目录
            service------------业务层
                dto
                bo
                impl
            configuration
                properties
            constants
            enums
            utils
            runner
### 技术栈
    需要`Java 8`环境，推荐使用`IDEA`作为开发工具，以下是所用到的技术：
    
    1. 核心框架 Spring Boot 2.4.2
    2. 微服务规范 Spring Cloud 2020.0.0
    3. 微服务框架 Spring Cloud Alibaba 2021.1
    4. API文档 Knife4j(Swagger) 2.0.1
    5. Java工具类库 Hutool 4.2.1
    6. 数据库连接池 Druid 1.1.21
    7. 持久层框架 Mybatis-plus 3.2.0
    8. Java库 Guava 28.1-jre
    9. 参数校验 Hibernate Validator 6.1.5.Final
    10. JSON序列化 Fastjson 1.2.62
    11. 代码简化插件 Lombok 1.18.12
    12. 分布式缓存 Redis  
    13. 非关系数据库 MongoDB
    14. 数据库 Mysql
    15. 分布式任务调度 XXL-Job 
    15. 服务发现/配置 Nacos 
    16. 消息队列 RabbitMQ
    17. 源码版本控制 Gitlab
    18. 项目构建框架 Maven
    19. 代码质量检测分析 SonarQube


## 部署说明

- IDEA 启动
- Jar部署
- Docker部署 
- K8S部署

## 系统截图

## QA
