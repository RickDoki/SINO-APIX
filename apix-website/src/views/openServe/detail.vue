<template>
  <div class="main_open">
    <navbar></navbar>
    <div class="content">
      <div class="list_top">
        <div>
          <div class="list_top_title">{{ appName }}</div>
          <div class="introduction">{{ appDescription }}
          </div>
          <div class="service_providers" style="margin-top: 30px">发布者：
            <span style="display: inline-block;margin-left: 40px">{{ appProvider }}</span>
          </div>
          <div style="display:flex;margin-top: 10px">
            <div class="service_providers" style="margin-right: 0" v-if="plugins.length>0">已添加的插件：</div>
            <div style="width: 669px;display: flex;flex-wrap: wrap;">
              <div class="plug-in service_providers" style="display: flex" v-for="(item,index) in plugins" :key="index">
                <el-tooltip class="item" effect="light" :content="item.msg" placement="bottom-start">
                  <div class="chajian_qian">
                    <img :src="item.icon" style="width: 100%;height: 100%">
                  </div>
                </el-tooltip>
                <div>{{ item.pluginType }}</div>
              </div>
            </div>
          </div>
        </div>
        <div>
          <div>
            <el-button type="primary" size="small" v-if="subscribed" :disabled="true" style="width: 100px"
                       @click="subscribe">已订阅
            </el-button>
            <el-button type="primary" size="small" v-else style="width: 100px" @click="subscribe">订阅
            </el-button>
            <el-button size="small" style="width: 100px" icon="el-icon-back" @click="$router.push({name:'openServe'})">
              返回
            </el-button>
          </div>
          <div class="release_time">发布时间：{{ appCreationDate }}</div>
        </div>
      </div>
      <div class="list_content">
        <api-detail :apiOptions="appVersion"></api-detail>
      </div>
    </div>
  </div>
</template>

<script>
// import apidetail from "./detail/detail.vue";
// import apiTest from "./detail/test.vue";
// import help from "./detail/help.vue";
import {appCodeDetail, subscribe} from "@/api/AboutApp";
import {getToken} from "@/utils/auth"; // get token from cookie
import apiDetail from './component/apiDetail'
import navbar from "@/views/openServe/component/Navbar";
import plugin from "@/views/serve/plugin";

export default {
  components: {
    navbar,
    apiDetail
    // apidetail,
    // apiTest,
    // help,
  },
  data() {
    return {
      appName: "",
      appDescription: "",
      appCreationDate: "",
      appVersion: [],
      appProvider: "",
      plugins: [],
      tooltipList: plugin,
      subscribed: true,
    };
  },
  created() {
    this.query()
  },
  methods: {
    plugName: function (value) {
      const nameFiter = {
        jwt: "Jwt-auth",
        base_auth: "basic_auth",
        oauth2: "OAuth2.0",
        black_list_ip: "IP 黑名单控制",
        white_list_ip: "IP 白名单控制",
        cors: "cors跨域",
        sign: "防篡改签名",
        replay_attacks: "防网络重放攻击",
        error_log: "error log",
        http_log: "http log",
        sentinel: "sentinel",
        gzip: "gzip",
        proxy_cache: "proxy-cache",
        real_ip: "real_ip",
        response_rewrite: "response-rewrite",
      };
      if (!value) return "";
      return nameFiter[value];
    },
    query() {
      appCodeDetail(this.$route.query.code).then(res => {
        if (res.code === 200) {
          this.appName = res.data.appName
          this.appDescription = res.data.appDescription
          this.appCreationDate = res.data.appCreationDate
          this.appProvider = res.data.appProvider
          this.appVersion = res.data.appVersion || []
          this.subscribed = res.data.subscribed
          this.plugins = res.data.plugins
          let arr = []
          this.tooltipList.forEach(item => {
            arr.push(...item.content)
          })
          this.plugins.map((item, index) => {
            item.pluginType = this.plugName(item.pluginType)
            arr.forEach((items, indexs) => {
              if (item.pluginType === items.name) {
                item.msg = items.message
                item.icon = items.icon
              }
            })
          })
        }
      })
    },
    subscribe() {
      if (getToken('token')) {
        this.$confirm('确认订阅：' + this.appName + '吗, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          subscribe(this.$route.query.code).then(res => {
            if (res.code === 200) {
              this.$message.success('订阅成功')
              this.query()
            }
          })
        })
      } else {
        this.$router.push({
          path: '/login',
          query: {
            path: this.$route.path,
            code: this.$route.query.code
          }
        })
      }
    }
  }
};
</script>

<style lang='scss' scoped>
.main_open {
  min-height: calc(100vh);
  width: 100%;
  background: #ffffff;
}

.content {
  max-width: 1200px;
  padding: 2rem 3rem var(--bottom-padding);
  margin: 0px auto;
  padding-top: 100px;
}

.list_top {
  display: flex;
  justify-content: space-between;

  .list_top_title {
    height: 26px;
    font-size: 20px;
    font-family: Microsoft YaHei UI-Bold, Microsoft YaHei UI;
    font-weight: bold;
    color: #1d1c35;
    line-height: 26px;
  }

  .introduction {
    width: 720px;
    margin-top: 5px;
    font-size: 14px;
    font-family: Microsoft YaHei UI-Regular, Microsoft YaHei UI;
    font-weight: 400;
    color: #727491;
    line-height: 20px;
  }

  .service_providers {
    height: 20px;
    font-size: 14px;
    font-family: Microsoft YaHei UI-Regular, Microsoft YaHei UI;
    font-weight: 400;
    color: #1d1c35;
    line-height: 20px;
    margin-right: 40px;
  }

  .plug-in {
    width: 150px;
    margin-right: 10px;
    margin-bottom: 10px;

    .chajian_qian {
      margin-right: 10px;
      width: 20px;
      height: 20px;
      border-radius: 0px 0px 0px 0px;
      opacity: 1;
    }
  }

  .release_time {
    margin-top: 40px;
    height: 20px;
    font-size: 12px;
    font-family: Microsoft YaHei UI-Regular, Microsoft YaHei UI;
    font-weight: 400;
    color: #727491;
    line-height: 20px;
    text-align: right;
  }
}

.list_top2 {
  margin: 24px 0px;
  display: flex;
}
</style>
