<template>
  <div>
    <div class="titleFont">
      {{ pluginName | plugName }}
    </div>
    <jwt-auth v-if="pluginName === 'jwt'"></jwt-auth>
    <oauth v-else-if="pluginName === 'oauth2'"></oauth>
    <sign v-else-if="pluginName === 'sign'"></sign>
    <black v-else-if="pluginName === 'black_list_ip'"></black>
    <white v-else-if="pluginName === 'white_list_ip'"></white>
    <cors v-else-if="pluginName === 'cors'"></cors>
    <sentinel v-else></sentinel>
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
      pluginName: "",
      ruleForm: {
        header: "",
        key: "",
        maximum: "",
      },
      rules: {
        header: [
          { required: true, message: "请输入版本名称", trigger: "blur" },
        ],
        key: [{ required: true, message: "请输入版本描述", trigger: "blur" }],
        maximum: [
          { required: true, message: "请选择关联API", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.pluginName = this.$route.params.plugincode;
  },
  methods: {
    submitForm() {},
    resetForm() {},
    handleChange(value) {
      console.log(value);
    },
  },
};
</script>

<style>
</style>