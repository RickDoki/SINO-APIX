<template>
  <div>
    <p>创建api流程</p>
    <div class="ApiLC">
      <div class="apiLC_box">
        <el-steps direction="vertical" :active="4">
          <el-step
            title="定义API"
            description="填写服务相关信息的表单，其中：API名称、API版本、API图标、API可见性是设置查看
修改API的用户权限、API资源是处理特定类型的请求等，初步创建一个API"
          ></el-step>
          <el-step
            title="设计API"
            description="完成API基本信息填写后点击【下一步】，可以开始设计API：例如制定API接口对应的资源、接口调用的方法或者需要传递的参数等"
          ></el-step>
          <el-step
            title="管理API"
            description="完成API设计后点击【下一步】，可进一步填写API管理：例如传输协议、生产地址、沙箱地址、缓存、负载均衡策略"
          ></el-step>
          <el-step
            title="发布API"
            description="设置完成后，进行节流设置：TPS、计费规格、订阅者调用限制等设置。
保存并发布"
          ></el-step>
        </el-steps>
      </div>
      <div class="apiLC_box">
        <el-steps direction="vertical" :active="4">
          <el-step
            title="创建应用程序"
            description="创建或使用默认应用程序，设置名称、每个令牌限额、描述。应用程序是API的逻辑
集合，主要用于将消费者与API分离。它允许您：为多个API生成并使用单个密钥"
          ></el-step>
          <el-step
            title="创建订阅"
            description="选择刚刚创建的应用程序，并选择访问限制层次，然后单击【订阅】。
出现提示时，单击【查看订阅】按钮。【订阅】选项卡打开"
          ></el-step>
          <el-step
            title="创建秘钥"
            description="单击创建密钥选项卡，然后单击生成密钥以创建应用程序访问令牌。您可以使用此标记来调用相同应用程序被订阅的所有API"
          ></el-step>
          <el-step
            title="调用与取消"
            description="已订阅一个API后可以调用它。
要取消订阅API，请单击应用程序菜单，然后单击用于订阅的应用程序旁边的【查看】。
转到【订阅】选项卡，找到API，单击与之关联的取消订阅链接"
          ></el-step>
        </el-steps>
      </div>
    </div>
    <p>优势能力</p>
    <div class="apiBox">
      <div class="box-son">
        <el-card>
          <p>丰富的API资源</p>
          <div>
            您可以以接口的形式访问上汽开放平台的各类资源，利用现有的遗留系统的值，以此构建自己的资源管理系统、自动化运维系统、开发工具等
          </div>
        </el-card>
      </div>
      <div class="box-son">
        <el-card>
          <p>毫秒级调用速度</p>
          <div>
            利用对超过150种服务的即时访问,包括能够围绕移动、微型服务和持续交互而提升现代体系结构、改进汽车物联网服务,让API管理性能极佳
          </div>
        </el-card>
      </div>
    </div>
    <div class="apiBox">
      <div class="box-son">
        <el-card>
          <p>支持在线测试</p>
          <div>
            支持开发者在线的API调试，接口管理，快速生成文档，项目管理，快速解决性能困扰，助您高效优化API服务，使得产品有良好的用户体验
          </div>
        </el-card>
      </div>
      <div class="box-son">
        <el-card>
          <p>权威的数据分析</p>
          <div>
            为开发者提供多种精准的数据统计分析，帮助开发者优化产品策略，延长生命周期
          </div>
        </el-card>
      </div>
    </div>
    <div class="apiBox">
      <div class="box-son">
        <el-card>
          <p>高度安全的数据调用</p>
          <div>
            采用通过token认证，才能获取接口数据,提高您的数据安全性，保护您的品牌免受安全威胁
          </div>
        </el-card>
      </div>
      <div class="box-son">
        <el-card>
          <p>标准的统一接口</p>
          <div>
            在与其他部门、第三方合作完成产品研发的过程中，接口是相互之间完成数据交互的主要手段，统一对接口进行管理,达到节约开发时间，和后期维护的时间，大大提高运营成本
          </div>
        </el-card>
      </div>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style lang="scss" scoped>
.ApiLC {
  display: flex;
  width: 100%;
  .apiLC_box {
    width: 50%;
    // background-color: #ccc;
    height: 300px;
  }
}
.apiBox {
  display: flex;
  justify-content: space-between;
  .box-son {
    width: 48%;
    margin-bottom: 5px;
    p {
      color: #333;
      font-size: 14px;
    }
    div {
      color: #999999;
      font-size: 12px;
    }
  }
}
</style>