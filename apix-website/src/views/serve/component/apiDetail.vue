<template>
  <div class="he_main">
    <el-row style="height:100%">
      <el-col style="height:100%" :span="4">
        <div class="apiList">
          <el-select v-model="apiValue" size="mini" placeholder="请选择">
            <el-option
              v-for="item in apiOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </div>
      </el-col>
      <el-col style="height:100%" :span="20">
        <div class="apiMessage">
          <div class="api-info">
            <div class="title">API名称</div>
            <div class="secondTitle">这是一段API描述</div>
          </div>
          <div class="api-info">
            <span class="label-color">调用路径 : </span>
            <span class="conten-color">https://www.baidu.com</span> <i class="el-icon-copy-document icon-color"/>
          </div>
          <div class="api-info">
            <span class="label-color agrement">协议类型 : </span>
            <span class="conten-color">https</span>
          </div>
          <div class="api-info">
            <span class="label-color">请求方式 : </span>
            <span class="conten-color">GET</span>
          </div>
          <div class="api-info">
            <span class="label-color">返回类型 : </span>
            <span class="conten-color">JSON</span>
          </div>
          <div class="api-info">
            <span class="label-color">请求参数 : </span>
            <div class="table_box table_top">
              <el-table
                :data="table"
                empty-text="暂无数据"
                :row-style="{ height: '50px' }"
                highlight-current-row
                :header-cell-style="{ 'font-weight': 400, color: '#494E6A' }"
              >
                <el-table-column prop="appName" label="名称" />
                <el-table-column prop="appCode" label="类型" />
                <el-table-column prop="appCode" label="是否必选" />
                <el-table-column prop="appCode" label="描述" />
                <el-table-column prop="appCode" label="默认值" />
              </el-table>
            </div>
          </div>
          <div class="api-info">
            <span class="label-color">状态码 : </span>
            <div class="table_box table_top">
              <el-table
                :data="table"
                empty-text="暂无数据"
                :row-style="{ height: '50px' }"
                highlight-current-row
                :header-cell-style="{ 'font-weight': 400, color: '#494E6A' }"
              >
                <el-table-column prop="appName" label="状态码" width="200"/>
                <el-table-column prop="appCode" label="描述" />
              </el-table>
            </div>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  data () {
    return {
      table: [],
      apiOptions: [],
      apiValue: ''
    };
  },
};
</script>

<style scoped lang='scss'>
.he_main {
  // min-height: inherit;
  height: 100%;
  overflow: hidden;
}
.apiList {
  padding: 20px 0px;
}
.apiMessage {
  overflow: auto;
  border-left: 1px solid #e1e6ee;
  padding: 24px;
  margin-left: 16px;
  height: 100%;
  padding-bottom: 150px;
  // margin-bottom: 20px;
  .api-info {
    margin-bottom: 24px;
    .title {
      line-height: 22px;
      font-size: 16px;
      font-weight: bold;
      color: #1d1c35;
    }
    .secondTitle {
      margin-top: 6px;
      color: #727491;
    }
  }
}
.apiMessage::-webkit-scrollbar {
      display: none;
    }
</style>