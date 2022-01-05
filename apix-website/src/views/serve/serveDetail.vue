<template>
  <div class="main">
    <div v-if="!routerView">
      <div class="serve_top">
        <div class="titleFont">
          <div>服务名称</div>
        </div>
        <div class="serve_right">
          <div>
            <el-dropdown>
              <el-button type="primary">
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
          <div>
            <el-button type="primary">编辑文档</el-button>
          </div>
        </div>
      </div>
      <p class="secondTitle" style="margin: 0px 0px 10px 0px">这是一段描述</p>
      <div class="status">
        <div class="use">
          <span>使用状态:</span>
          <span>停用</span>
        </div>
        <div class="door">
          <span>门户状态:</span>
          <span>已发布</span>
        </div>
        <div class="time">
          <div>
            <span>创建时间:</span>
            <span>2021-08-05 10:05:00:00</span>
          </div>
          <div>
            <span>更新时间:</span>
            <span>2021-08-05 10:05:00:00</span>
          </div>
        </div>
      </div>
      <div class="numbers">
        <div class="requestAll">
          <div>
            请求计数
          </div>
        </div>
        <div class="requestError"></div>
        <div class="edition"></div>
      </div>
    </div>
    <router-view v-if="routerView"></router-view>
  </div>
</template>

<script>
import "./../mainCss/index.scss";
export default {
  data() {
    return {
      routerView: false,
    };
  },
  created() {
    console.log(this.$route);
    if (this.$route.name === "serveDteail") {
      this.routerView = false;
    } else {
      this.routerView = true;
    }
  },
};
</script>

<style lang='scss' scoped>
.serve_top {
  display: flex;
  justify-content: space-between;
  .serve_right {
    display: flex;
    justify-content: space-between;
    width: 220px;
  }
}
.status {
  display: flex;
  // width: 300px;
  justify-content: space-between;
  .use {
    width: 200px;
    font-size: 12px;
  }
  .door {
    font-size: 12px;
    width: 200px;
  }
  .time {
    width: calc(100% - 400px);
    text-align: right;
  }
}
.numbers {
  height: 100px;
  border: 1px solid #e1e6ee;
  margin: 10px 0px;
  border-radius: 10px;
  div {
    display: inline-block;
    width: 33.33%;
    height: 100px;
    div {
      height: 50px;
      line-height: 50px;
    }
  }
  .requestError {
    border-left: 1px solid #e1e6ee;
    border-right: 1px solid #e1e6ee;
  }
}
</style>