#! /bin/shell

#======================================================================
# 先下载按照脚本，多次执行该脚本，会覆盖以下下载的文件
# 快速安装jdk/git/maven/redis/mysql
#======================================================================

# 下载脚本
wget -O install-all.sh https://gitee.com/sinosdx/sino-apix/tree/master/docs/deploy/env/install-all.sh
wget -O install-jdk.sh  https://gitee.com/sinosdx/sino-apix/tree/master/docs/deploy/env/install-jdk.sh
wget -O install-git.sh  https://gitee.com/sinosdx/sino-apix/tree/master/docs/deploy/env/install-git.sh
wget -O install-maven.sh  https://gitee.com/sinosdx/sino-apix/tree/master/docs/deploy/env/install-maven.sh
wget -O install-redis.sh  https://gitee.com/sinosdx/sino-apix/tree/master/docs/deploy/env/install-redis.sh
wget -O install-mysql.sh  https://gitee.com/sinosdx/sino-apix/tree/master/docs/deploy/env/install-mysql.sh

# 执行安装所有
sh install-all.sh
