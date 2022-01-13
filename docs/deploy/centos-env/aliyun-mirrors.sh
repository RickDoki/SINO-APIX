#! /bin/shell

#======================================================================
# 使用阿里云镜像
#======================================================================

yum clean all
yum update
wget -O /etc/yum.repos.d/CentOS-Base.repo http://mirrors.aliyun.com/repo/Centos-7.repo
yum makecache
