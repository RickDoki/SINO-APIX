<template>
  <div class="hickyDetail">
    <el-row>
      <el-col :span="24">
        <!-- <el-tabs tab-position="left">
          <el-tab-pane label="人脸检测">
            <api-detail></api-detail>
          </el-tab-pane>
          <el-tab-pane label="创建人脸库">创建人脸库</el-tab-pane>
          <el-tab-pane label="添加人脸">添加人脸</el-tab-pane>
          <el-tab-pane>
            <div class="tab-title" slot="label">搜索人脸</div>
            <div class="tab-title"></div>
          </el-tab-pane>
        </el-tabs> -->
        <div class="jiekou">
          <img src="./../../../assets/img/picture.png" alt="">
          <p>网站建设中</p>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import apiDetail from "./APIitems/items.vue";
export default {
  components: {
    apiDetail,
  },
};
</script>

<style lang='scss' scoped>
.hickyDetail {
  .jiekou {
    width: 100%;
    height: 200px;
    text-align: center;
    p {
      text-align: center;
      color: #ccc;
    }
  }
}
</style>