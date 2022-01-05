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
          <div class="font">请求计数</div>
          <div>123</div>
        </div>
        <div class="requestError">
          <div class="font">失败的请求</div>
          <div>123</div>
        </div>
        <div class="edition">
          <div class="font">版本计数</div>
          <div>123</div>
        </div>
      </div>
      <div class="table_box">
        <!-- <p>版本</p> -->
        <div class="serve-table">
          <div class="table-tile">版本</div>
          <div @click="gonewEdition" class="he-button">添加新版本</div>
        </div>
        <el-table
          :data="table"
          empty-text="暂无数据"
          stripe
          style="width: 100%"
        >
          <el-table-column prop="appName" label="应用名称" />
          <el-table-column prop="appCode" label="APPCode" />
          <el-table-column prop="appCode" label="启用状态" />
          <el-table-column prop="appCode" label="描述" />
          <el-table-column label="操作" width="180px">
            <template slot-scope="scope">
              <div class="handle">
                <span @click="getMessage(scope.row)" class="linkcolor"
                  >查看</span
                >
                <span class="handle_middle">|</span>
                <span class="linkcolor">退订</span>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div class="table_box" style="margin-top: 10px">
        <div class="serve-table">
          <div class="table-tile">插件</div>
          <div class="he-button">添加插件</div>
        </div>
        <el-table
          :data="table"
          empty-text="暂无数据"
          stripe
          style="width: 100%"
        >
          <el-table-column prop="appName" label="应用名称" />
          <el-table-column prop="appCode" label="APPCode" />
          <el-table-column prop="appCode" label="启用状态" />
          <el-table-column prop="appCode" label="描述" />
          <el-table-column label="操作" width="180px">
            <template slot-scope="scope">
              <div class="handle">
                <span @click="getMessage(scope.row)" class="linkcolor"
                  >查看</span
                >
                <span class="handle_middle">|</span>
                <span class="linkcolor">退订</span>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div class="table_box" style="margin-top: 10px">
        <div class="serve-table">
          <div class="table-tilelong">数据统计-http日志/log日志</div>
          <!-- <div class="he-button">添加新版本</div> -->
        </div>
        <el-table
          :data="table"
          empty-text="暂无数据"
          stripe
          style="width: 100%"
        >
          <el-table-column prop="appName" label="应用名称" />
          <el-table-column prop="appCode" label="APPCode" />
          <el-table-column prop="appCode" label="启用状态" />
          <el-table-column prop="appCode" label="描述" />
          <el-table-column label="操作" width="180px">
            <template slot-scope="scope">
              <div class="handle">
                <span @click="getMessage(scope.row)" class="linkcolor"
                  >查看</span
                >
                <span class="handle_middle">|</span>
                <span class="linkcolor">退订</span>
              </div>
            </template>
          </el-table-column>
        </el-table>
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
      table: [],
    };
  },
  created() {
    // console.log(this.$route);
    if (this.$route.name === "serveDteail") {
      this.routerView = false;
    } else {
      this.routerView = true;
    }
  },
  methods: {
    gonewEdition() {
      this.$router.push({path:'/serve/newEdition'})
    },
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
    vertical-align: middle;
    div {
      display: block;
      height: 50px;
      width: 100%;
      text-align: center;
    }
    .font {
      line-height: 50px;
    }
  }
  .requestError {
    border-left: 1px solid #e1e6ee;
    border-right: 1px solid #e1e6ee;
  }
}
.serve-table {
  display: flex;
  justify-content: space-between;
}
</style>