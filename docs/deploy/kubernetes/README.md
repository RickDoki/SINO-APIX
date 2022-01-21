# 使用Kubernetes部署SINO APIX

## 环境要求
安装以下依赖软件：
- JDK1.8
- Maven3+
- nodejs v12
- Docker
- Kubernetes

## 准备

## 操作步骤

#### 1、部署mysql

```shell
root@SINOSDX-API001:~/deploy#  kubectl label node sinosdx-api002  deployrole=mysql
node/sinosdx-api002 labeled

root@SINOSDX-API001:~/deploy# kubectl get node --show-labels |grep mysql
sinosdx-api002   Ready    control-plane,master,worker   5h24m   v1.20.13   beta.kubernetes.io/arch=amd64,beta.kubernetes.io/os=linux,deployrole=mysql,kubernetes.io/arch=amd64,kubernetes.io/hostname=sinosdx-api002,kubernetes.io/os=linux,node-role.kubernetes.io/control-plane=,node-role.kubernetes.io/master=,node-role.kubernetes.io/worker=

root@SINOSDX-API001:~/deploy# kubectl apply -f deploy-mysql.yaml
```

#### 2、部署redis

[参考](https://www.cnblogs.com/ccubee/p/14213868.html)

```shell
root@SINOSDX-API001:~/deploy# kubectl label node sinosdx-api003  deployrole=redis

root@SINOSDX-API001:~/deploy# kubectl get node --show-labels |grep redis
sinosdx-api003   Ready    control-plane,master,worker   6h38m   v1.20.13   beta.kubernetes.io/arch=amd64,beta.kubernetes.io/os=linux,deployrole=redis,kubernetes.io/arch=amd64,kubernetes.io/hostname=sinosdx-api003,kubernetes.io/os=linux,node-role.kubernetes.io/control-plane=,node-role.kubernetes.io/master=,node-role.kubernetes.io/worker=

root@SINOSDX-API001:~/deploy# kubectl apply -f deploy-redis.yaml
```

#### 3、部署nacos 

```shell
root@SINOSDX-API001:~/deploy# kubectl label node sinosdx-api003 deployrole2=nacos node/sinosdx-api003 labeled

root@SINOSDX-API001:~/deploy# kubectl get node --show-labels |grep nacos sinosdx-api003 Ready
control-plane,master,worker 8h v1.20.13
beta.kubernetes.io/arch=amd64,beta.kubernetes.io/os=linux,deployrole2=nacos,deployrole=redis,kubernetes.io/arch=amd64,kubernetes.io/hostname=sinosdx-api003,kubernetes.io/os=linux,node-role.kubernetes.io/control-plane=,node-role.kubernetes.io/master=,node-role.kubernetes.io/worker=

root@SINOSDX-API001:~/deploy# kubectl apply -f deploy-nacos.yaml namespace/sinosdx-api unchanged service/nacos created
deployment.apps/nacos created
```




















