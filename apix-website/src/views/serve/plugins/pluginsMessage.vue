<template>
  <el-drawer
    title="插件详情"
    :visible.sync="drawer"
    :direction="direction"
    :before-close="handleClose"
    size="50%"
  >
    <div class="plugin_main">
      <div class="flexBox">
        <div class="left">插件名称:</div>
        <div class="right">{{ pluginParams.name }}</div>
      </div>
      <div class="flexBox">
        <div class="left">插件介绍:</div>
        <div class="right">
          {{ pluginParams.description }}
        </div>
      </div>
      <div class="flexBox" v-if="pluginParams.header">
        <div class="left">Header:</div>
        <div class="right">{{ pluginParams.header }}</div>
      </div>
      <div class="flexBox" v-if="pluginParams.claimKey">
        <div class="left">key:</div>
        <div class="right">{{ pluginParams.claimKey }}</div>
      </div>
      <div
        class="flexBox"
        v-if="
          pluginParams.tokenExpiration && pluginParams.pluginType === 'jwt'
        "
      >
        <div class="left">过期时间:</div>
        <div class="right">{{ pluginParams.tokenExpiration }} ms</div>
      </div>
      <div class="flexBox" v-if="pluginParams.ProvisionKey">
        <div class="left">Key:</div>
        <div class="right">{{ pluginParams.ProvisionKey }}</div>
      </div>
      <div
        class="flexBox"
        v-if="
          pluginParams.tokenExpiration && pluginParams.pluginType === 'oauth2'
        "
      >
        <div class="left">Token Expiration:</div>
        <div class="right">{{ pluginParams.tokenExpiration }} ms</div>
      </div>
      <div class="flexBox" v-if="pluginParams.pluginType === 'oauth2'">
        <div class="left">client id：</div>
        <div class="right"> {{ pluginParams.clientId || '--' }} </div>
      </div>
      <div class="flexBox" v-if="pluginParams.pluginType === 'oauth2'">
        <div class="left">client Secret：</div>
        <div class="right"> {{ pluginParams.clientSecret || '--' }} </div>
      </div>
      <div class="flexBox" v-if="pluginParams.requestUrl">
        <div class="left">请求地址:</div>
        <div class="right"> {{ pluginParams.requestUrl }} </div>
      </div>
      <div class="flexBox" v-if="pluginParams.requestType">
        <div class="left">请求方式:</div>
        <div class="right"> {{ pluginParams.requestType }} </div>
      </div>
      <div class="flexBox" v-if="pluginParams.requestParam">
        <div class="left">请求参数:</div>
        <div class="right"> {{ pluginParams.requestParam }} </div>
      </div>
      <div class="flexBox" v-if="pluginParams.response">
        <div class="left">返回值:</div>
        <div class="right"> {{ pluginParams.response }} </div>
      </div>
      <div class="flexBox" v-if="pluginParams.clientCredential">
        <div class="left">客户端认证:</div>
        <div class="button_plug">
          {{ pluginParams.clientCredential === true ? "开启" : "关闭" }}
        </div>
      </div>
      <div class="flexBox" v-if="pluginParams.refreshTokenExpiration">
        <div class="left">Refresh Expiration:</div>
        <div class="right">{{ pluginParams.refreshTokenExpiration }} ms</div>
      </div>
      <div class="flexBox" v-if="pluginParams.blackListIp">
        <div class="left">ip黑名单:</div>
        <div class="right">{{ pluginParams.blackListIp }}</div>
      </div>
      <div class="flexBox" v-if="pluginParams.whiteListIp">
        <div class="left">ip白名单:</div>
        <div class="right">{{ pluginParams.whiteListIp }}</div>
      </div>
      <div class="flexBox" v-if="pluginParams.allowOrigins">
        <div class="left">allowOrigins:</div>
        <div class="right">{{ pluginParams.allowOrigins }}</div>
      </div>
      <div class="flexBox" v-if="pluginParams.allowHeaders">
        <div class="left">allowHeaders:</div>
        <div class="right">{{ pluginParams.allowHeaders }}</div>
      </div>
      <div class="flexBox" v-if="pluginParams.exposeHeaders">
        <div class="left">exposeHeaders:</div>
        <div class="right">{{ pluginParams.exposeHeaders }}</div>
      </div>
      <div class="flexBox" v-if="pluginParams.exposeHeaders">
        <div class="left">操作 Methods:</div>
        <div
          v-for="(item, index) in pluginParams.allowMethods.split(',')"
          :key="index"
          class="methods"
        >
          {{ item }}
        </div>
        <!-- <div class="right">{{ pluginParams.exposeHeaders }}</div> -->
      </div>
      <div class="flexBox" v-if="pluginParams.maxAge">
        <div class="left">允许Cookie:</div>
        <div
          :class="
            pluginParams.allowCredentials === true ? 'enable' : 'noenable'
          "
        >
          {{ pluginParams.allowCredentials === true ? "开启" : "关闭" }}
        </div>
      </div>
      <div class="flexBox" v-if="pluginParams.maxAge">
        <div class="left">maxAge:</div>
        <div class="right">{{ pluginParams.maxAge }}</div>
      </div>
      <!-- <div class="flexBox" v-if="pluginParams.appKey">
        <div class="left">appKey:</div>
        <div class="right">{{ pluginParams.appKey }}</div>
      </div> -->
      <div class="flexBox" v-if="pluginParams.secretKey">
        <div class="left">appSecret:</div>
        <div class="right">{{ pluginParams.secretKey }}</div>
      </div>
      <div class="flexBox" v-if="pluginParams.sentinelInterval">
        <div class="left">服务控制时长:</div>
        <div class="right">
          {{ pluginParams.sentinelInterval }}
        </div>
      </div>
      <div class="flexBox" v-if="pluginParams.sentinelCount">
        <div class="left">服务控流值:</div>
        <div class="right">{{ pluginParams.sentinelCount }}</div>
      </div>
      <div v-if="apiTable.length > 0" class="flexBox">
        <div class="left">API控流配置:</div>
      </div>
      <div v-if="apiTable.length > 0" class="table_box">
        <el-table
          :data="apiTable"
          :row-style="{ height: '50px' }"
          highlight-current-row
          :header-cell-style="{
            'font-weight': 400,
            'font-size': '16px',
            color: '#1D1C35',
          }"
        >
          <el-table-column prop="apiName" label="api名称" />
          <el-table-column prop="apiInterval" label="api控流时长" align="center" />
          <el-table-column prop="apiCount" label="api控流值" align="center" />
        </el-table>
      </div>
    </div>
  </el-drawer>
</template>

<script>
import { getPlugin, getPluginDetail } from "@/api/AboutServe.js";

export default {
  filters: {
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
        proxy_cache: "proxy_cache",
        real_ip: "real_ip",
        response_rewrite: "response-rewrite",
      };
      if (!value) return "";
      return nameFiter[value];
    },
    pluginIntroduction: function (value) {
      const nameFiter = {
        jwt: "jwt-auth鉴权插件用于验证包含 HS256 签名的 JSON Web 令牌的请求。该插件检查Authorization请求头中的用户凭据。",
        base_auth:
          "basic-auth鉴权插件用于给调用服务的用户添加基本身份认证。该插件检查Authorization请求头中的用户凭据。",
        oauth2:
          "OAuth2鉴权插件用于给服务接口请求提供基于OAuth2协议的鉴权。该插件检查Authorization请求头中的用户凭据。",
        black_list_ip:
          "为限制访问资源的用户，提升服务的安全性，您可以设置黑名单，实现对访客身份的识别和过滤。添加IP到黑名单，那么该IP无法访问当前服务。",
        white_list_ip:
          "为限制访问资源的用户，提升服务的安全性，您可以设置白名单，实现对访客身份的识别和过滤。添加IP到白名单，那么只有该IP能够访问当前服务。",
        cors: "CORS跨域插件用于动态配置接口跨域资源访问，关于CORS跨域资源访问的基础知识可参考跨域资源共享（CORS）机制。",
        sign: "sign-auth插件用于保护请求数据中途不被篡改，相当于您给API网关颁发了一个token，开启插件后，请求接口时需要根据约定的加签处理方法和key生成签名并放到请求头中，网关会对比签名和服务器端计算的签名是否一致，实现身份验证。",
        replay_attacks: "防网络重放攻击",
        error_log: "添加此插件可观测服务调用失败的日志",
        http_log: "添加此插件可观测服务调用成功的日志",
        sentinel:
          "对服务和服务下的api进行流量控制，限流规则持久化到nacos配置中心，网关服务监听该配置文件，实时更新生效",
        gzip: "gzip协议压缩就是依据HTTP协议进行压缩，不需要程序员进行压缩，解压编码，而是把压缩过程交给WEB服务器，将解压过程交给客户端。 如果客户端为支持GZIP压缩的浏览器，那么解压过程也不需要程序员参与，浏览器会按照一定的规则自动进行解压缩。",
        proxy_cache:
          "proxy-cache插件用于动态缓存请求的返回数据，可以将后端返回的应答缓存在API网关服务层面，有效降低后端的负荷，增加平滑度，开启插件后第一次请求会真实转发到服务端获取数据，之后在过期时间内的请求会从缓存中获取，默认使用redis分布式缓存。",
        real_ip:
          "real-ip插件用于获取客户端获取真实ip，默认情况下经过多层代理或转发后，服务端会获取多个不同ip，开启后网关层会自动解析判断获取客户端真实ip并通过响应头返回",
        response_rewrite:
          "发布的服务，当接口还不可用时，开启返回值重写可以拦截掉对服务的请求，并给请求返回一个固定值的响应",
      };
      if (!value) return "";
      return nameFiter[value];
    },
    unit: function (value) {
      if (value === "0") {
        return "秒";
      } else if (value === "1") {
        return "分";
      } else if (value === "2") {
        return "时";
      } else if (value === "3") {
        return "天";
      }
    },
  },
  props: ["drawerProps", "pluginId"],
  watch: {
    drawerProps () {
      this.drawer = this.drawerProps;
    },
    pluginId () {
      console.log(this.pluginId);
      // this.getDetail();
      this.getPluginDetail()
    },
  },
  data () {
    return {
      drawer: false,
      direction: "rtl",
      appCode: "",
      pluginsMessage: {},
      pluginParams: {},
      apiTable: [],
    };
  },
  created () {
    this.appCode = this.$route.params.appcode || this.$route.params.appCode;
    console.log(this.appCode)
  },
  methods: {
    handleClose (done) {
      done();
      this.$emit("showChange", false);
    },
    // 时间单位的filter
    timeunit (value) {
      if (value === "0") {
        return "秒";
      } else if (value === "1") {
        return "分";
      } else if (value === "2") {
        return "时";
      } else if (value === "3") {
        return "天";
      }
    },
    // 获取插件详情
    getPluginDetail () {
      getPluginDetail(this.pluginId).then((res) => {
        if (res.code === 200) {
          // console.log(res);
          this.pluginParams = res.data;
          if (this.pluginParams.pluginType === "sentinel") {
            console.log("9999");
            this.apiTable = res.data.sentinelApiConfig
            console.log(this.apiTable);
          } else {
            this.apiTable = []
          }
        }
      });
    },
    // 获取插件详情
    getDetail () {
      getPlugin(this.pluginId, this.appCode).then((res) => {
        if (res.code === 200) {
          // console.log(res);
          this.pluginsMessage = res.data;
          if (res.data.pluginParams !== "") {
            if (this.pluginsMessage.pluginType === "sentinel") {
              console.log("9999");
              this.pluginParams = {};
              this.pluginParamslist = JSON.parse(res.data.pluginParams);
              for (
                let index = 1;
                index < this.pluginParamslist.length;
                index++
              ) {
                this.apiTable.push({
                  apiname: this.pluginParamslist[index].apiId.apiName,
                  apiTime:
                    this.pluginParamslist[index].interval +
                    this.timeunit(this.pluginParamslist[index].intervalUnit),
                  apiNum: this.pluginParamslist[index].count,
                });
              }
              console.log(this.pluginParamslist);
              console.log(this.apiTable);
            } else {
              this.apiTable = [];
              this.pluginParamslist = [{}];
              this.pluginParams = JSON.parse(res.data.pluginParams);
              console.log(this.pluginParamslist);
            }
          } else {
            this.pluginParams = {};
          }
        }
      });
    },
  },
};
</script>

<style scoped lang='scss'>
.plugin_main {
  padding: 0px 20px;
  .flexBox {
    display: flex;
    margin-bottom: 20px;
    .left {
      width: 150px;
      padding-right: 20px;
      // text-align: right;
      font-size: 14px;
    }
    .right {
      width: calc(100% - 150px);
      font-size: 14px;
      line-height: 20px;
    }
    .button_plug {
      // width: 50px;
      padding: 0 8px;
      color: #2650ff;
      background-color: #d4dcff;
      border-radius: 3px;
      font-size: 14px;
    }
    .enable {
      padding: 0 8px;
      color: #2650ff;
      background-color: #d4dcff;
      border-radius: 3px;
      font-size: 14px;
    }
    .noenable {
      padding: 0 8px;
      color: #6c757d;
      background-color: #e1e6ee;
      border-radius: 3px;
      font-size: 14px;
    }
    .methods {
      padding: 0 8px;
      color: #61b874;
      background-color: #e1f8da;
      border-radius: 3px;
      font-size: 14px;
      margin-left: 3px;
    }
  }
}
</style>