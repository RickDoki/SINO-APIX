<template>
  <div>
    <div class="titleFont">
      {{ pluginName | plugName }}
    </div>
    <div class="content">{{ pluginName | pluginIntroduction }}</div>
    <jwt-auth v-if="pluginName === 'jwt'"></jwt-auth>
    <oauth v-else-if="pluginName === 'oauth2'"></oauth>
    <sign v-else-if="pluginName === 'sign'"></sign>
    <black v-else-if="pluginName === 'black_list_ip'"></black>
    <white v-else-if="pluginName === 'white_list_ip'"></white>
    <cors v-else-if="pluginName === 'cors'"></cors>
    <sentinel v-else-if="pluginName === 'sentinel'"></sentinel>
    <div v-else class="bottom_button_a button">
      <el-button @click="addPulgin" size="small" type="primary">添加</el-button>
      <el-button @click="resetForm" size="small">取消</el-button>
    </div>
  </div>
</template>

<script>
import "./../mainCss/index.scss";
import jwtAuth from "./plugins/jwt-auth.vue";
import oauth from "./plugins/oauth.vue";
import sign from "./plugins/sign.vue";
import black from "./plugins/black.vue";
import white from "./plugins/white.vue";
import cors from "./plugins/cors.vue";
import sentinel from "./plugins/sentine.vue";
import { postPlugin } from "@/api/AboutServe.js";

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
  },
  components: {
    jwtAuth,
    oauth,
    sign,
    black,
    white,
    cors,
    sentinel,
  },
  data() {
    return {
      plugsContent: "",
      appCode: "",
      appId: "",
      ruleForm: {
        header: "",
        key: "",
        maximum: "",
      },
      // rules: {
      //   header: [
      //     { required: true, message: "请输入版本名称", trigger: "blur" },
      //   ],
      //   key: [{ required: true, message: "请输入版本描述", trigger: "blur" }],
      //   maximum: [
      //     { required: true, message: "请选择关联API", trigger: "blur" },
      //   ],
      // },
    };
  },
  created() {
    this.pluginName = this.$route.params.plugincode;
    this.appCode = this.$route.query.appcode;
    this.appId = this.$route.query.appid;
  },
  methods: {
    submitForm() {},
    resetForm() {},
    handleChange(value) {
      console.log(value);
    },
    resetForm() {
      this.$router.push({
        path:
          "/serve/serveDetail/plug-in?" +
          "appcode=" +
          this.appCode +
          "&appid=" +
          this.appId,
      });
    },
    addPulgin() {
      const query = {
        pluginType: this.pluginName,
        appCode: this.appCode,
        appId: this.appId,
      };
      postPlugin(query).then((res) => {
        if (res.code === 200) {
          this.$router.push({
            path: "/serve/serveDetail/" + this.appCode,
          });
        }
      });
    },
  },
};
</script>

<style lang='scss' scoped>
.content {
  margin-top: 10px;
  color: #666;
  font-size: 14px;
}
.button {
  margin-top: 30%;
}
</style>