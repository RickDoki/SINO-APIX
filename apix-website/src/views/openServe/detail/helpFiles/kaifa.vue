<template>
  <div>
    <p class="title">上汽开放平台鉴权机制</p>
    <div class="content">
      上汽开放接口的调用，如上汽订单查询，上汽经销商查询等，都是需要获取用户身份认证的。 目前上汽开放平台用户身份鉴权采用的是OAuth2.0。请参考下面步骤，获取AccessToken、AccessToken验证。 调用API接口的例子是调用天气预报，地点是北京。
    </div>
    <p class="title">
      一、获取上汽开放平台AccessToken
    </p>
    <div>
      <p class="content">进入API云市场首页，点击右上角【控制台】（url:https://openapi.saicmotor.com/store/api/dashboard/applic ation)后,进入应用管理菜单。 创建新的应用，填写应用名称、应用描述。 进入新创建的应用，点击【生成密钥】，完成应用标识、应用密钥的生成。</p>
      <p class="content">同时也可以使用接口的方式生成AccessToken，生成方式：使用获取AccessToken调用地址 （url:https://openapi.saicmotor.com/api/am/store/v0.12/applications/getAccessToken) 进行AccessToken生成。</p>
      <p class="content">参数部分的"Authorize"的值由应用管理页的应用标识和应用密钥做加密生成。加密方式为拼接"应用标识":"应用密钥"做base64加密。例如"Ugz0940gh9:2c90d231-c419-4de0-939e-9198efbee45f"。</p>
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
}
</style>