<template>
  <div class="main">
    <div class="list_top">
      <div class="list_title">API名称</div>
      <div class="list_search">
        <div class="but-left">
          <el-dropdown>
            <el-button type="primary" size="small">
              操作<i class="el-icon-arrow-down el-icon--right"></i>
            </el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>黄金糕</el-dropdown-item>
              <el-dropdown-item>狮子头</el-dropdown-item>
              <el-dropdown-item>螺蛳粉</el-dropdown-item>
              <el-dropdown-item>双皮奶</el-dropdown-item>
              <el-dropdown-item>蚵仔煎</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
        <el-button type="primary" size="small">编辑文档</el-button>
      </div>
    </div>
    <div class="secondTitle">
      这是一段API描述
    </div>
    <div class="status">
      <div class="left-span">
        <span>被调用次数: </span>
        <span>停用</span>
      </div>
      <div class="left-span">
        <span>调用失败次数: </span>
        <span>已发布</span>
      </div>
      <div class="time">
        <div>
          <span>创建时间 : </span>
          <span>2021-08-05 10:05:00:00</span>
        </div>
        <div>
          <span>更新时间 : </span>
          <span>2021-08-05 10:05:00:00</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
    };
  },
  created () {
    console.log(this.$route);
  },
};
</script>

<style lang='scss' scoped>
</style>