# api linker 开放平台


## API Linker是什么？

## 演示环境（Demo）

## 设计
### 模块结构
```lua
api-linker
    └── docs
        ├── shell
        ├── 分支管理.md
        └── 升级日志.md
    └── api-client                                  
    └── api-common                                 
    └── api-core                            
    └── api-gateway                           
    └── api-service                                 
    └── api-spring-boot-starter                                
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
