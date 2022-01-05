<template>
  <div class="app_list_main">
    <div class="app_list_top">
      <div class="app_list_title">
        <div class="titleFont">服务名称</div>
        <div style="margin-top: 10px" class="secondTitle">这段是服务描述</div>
        <div style="margin-top: 10px">
          <span class="company">服务商:</span>
          <span class="company">搏冀信息</span>
        </div>
      </div>
      <div class="handle">
        <div>
          <el-button type="primary" size="mini"> 退订 </el-button>
        </div>
        <div class="time" style="margin-top: 10px">
          <span> 发布时间: </span>
          <span>2021-08-05 10:05:00</span>
        </div>
        <div class="time" style="margin-top: 10px">
          <span> 发布时间: </span>
          <span>2021-08-05 10:05:00</span>
        </div>
      </div>
    </div>
    <div class="tabsMessage">
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="API详情" name="first">
          <api-detail></api-detail>
        </el-tab-pane>
        <el-tab-pane label="插件详情" name="second">
          <plug-in></plug-in>
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<script>
import "./../mainCss/index.scss";
import apiDetail from "./component/apiDetail.vue";
import plugIn from './component/plug-in.vue'
export default {
  data() {
    return {
      activeName: "first",
    };
  },
  components: {
    apiDetail,
    plugIn
  },
  methods: {
    // 切换tab
    handleClick() {},
  },
};
</script>

<style scoped lang='scss'>
.app_list_main {
  width: 95%;
  margin: 0 auto;
  margin-top: 20px;
  border-radius: 5px;
  background-color: #fff;
  overflow: hidden;
  .app_list_top {
    display: flex;
    justify-content: space-between;
  }
  .tabsMessage {
    margin-top: 20px;
    ::v-deep {
      .el-tabs__header {
        margin: 0px;
      }
    }
  }
}
</style>