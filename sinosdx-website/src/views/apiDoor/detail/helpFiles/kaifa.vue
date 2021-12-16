<template>
  <div>
    <p class="title">认证方式介绍</p>
    <div class="content">
      前端请求：已通过csp营销门户(https://www.sail-cloud.com)登录的用户，可通过cookies中的FSH_AUTH获取csp2颁发的jwt调用api（请求头中须携带JWT: jwt），jwt的有效时间为8小时。
    </div>
    <div class="content">
      后端请求：通过OAuth2客户端认证方式获取token调用接口。
    </div>
    <p class="title">
    获取AccessToken
    </p>
    <div>
      <p class="content">用户在API管理平台订阅应用后会生成client_id和client_secret，开发者可在“应用管理->已订阅的应用”中查看client_id和client_secret，然后通过调用客户端认证接口获取token，后续使用token调用已订阅应用的api（请求头中须携带Authorization: Bearer token），token的有效时间为2小时。</p>
      <p class="content">客户端认证接口：URL: https://www.sail-cloud.com/mt-api/auth/login HTTP METHOD: POST</p>
      <!-- <p class="content">参数部分的"Authorize"的值由应用管理页的应用标识和应用密钥做加密生成。加密方式为拼接"应用标识":"应用密钥"做base64加密。例如"Ugz0940gh9:2c90d231-c419-4de0-939e-9198efbee45f"。</p> -->
    </div>
    <p class="title">发布接口</p>
    <div>
      <p class="content">产品侧发布到API管理中台的接口，不能有认证（或者中台网关对于产品侧应该是白名单），中台网关会对接口调用做统一认证</p>
    </div>
  </div>
</template>

<script>
export default {

}
</script>

<style lang='scss' scoped>
.title {
  color: #333333;
  font-size: 14px;
}
.content {
  color: #999999;
  font-size: 12px;
  margin-top: 5px;
}
</style>