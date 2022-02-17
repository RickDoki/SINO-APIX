#! /bin/shell
#
# Copyright © 2022 SinoSDX (biz@sinosdx.com)
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#


#======================================================================
# 先下载按照脚本，多次执行该脚本，会覆盖以下下载的文件
# 快速安装jdk/git/maven/redis/mysql
#======================================================================

# 下载脚本
wget -O install-all.sh https://gitee.com/sinosdx/sino-apix/tree/master/docs/deploy/centos-env/install-all.sh
wget -O install-jdk.sh  https://gitee.com/sinosdx/sino-apix/tree/master/docs/deploy/centos-env/install-jdk.sh
wget -O install-git.sh  https://gitee.com/sinosdx/sino-apix/tree/master/docs/deploy/centos-env/install-git.sh
wget -O install-maven.sh  https://gitee.com/sinosdx/sino-apix/tree/master/docs/deploy/centos-env/install-maven.sh
wget -O install-redis.sh  https://gitee.com/sinosdx/sino-apix/tree/master/docs/deploy/centos-env/install-redis.sh
wget -O install-mysql.sh  https://gitee.com/sinosdx/sino-apix/tree/master/docs/deploy/centos-env/install-mysql.sh

# 执行安装所有
sh install-all.sh
