<template>
  <div class="main">
    <div v-if="!routerView">
      <div class="list_top">
        <div class="list_title">服务名称</div>
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
      <div class="secondTitle">创建服务来管理和代理现有API或发布到门户。</div>
      <div class="status">
        <div class="left-span">
          <span>使用状态:</span>
          <span>停用</span>
        </div>
        <div class="left-span">
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
      <div class="numbers mode-margin">
        <div class="requestAll">
          <div class="font">请求计数</div>
          <div class="num">123</div>
        </div>
        <div class="requestError">
          <div class="font">失败的请求</div>
          <div class="num">123</div>
        </div>
        <div class="edition">
          <div class="font">版本计数</div>
          <div class="num">123</div>
        </div>
      </div>
      <div class="table_box mode-margin">
        <!-- <p>版本</p> -->
        <div class="serve-table">
          <div class="table-tile">版本</div>
          <el-button
            plain
            type="primary"
            size="small"
            @click="gonewEdition"
            class="add-but"
            >添加版本
          </el-button>
        </div>
        <el-table
          :data="table"
          empty-text="暂无数据"
          :row-style="{ height: '50px' }"
          highlight-current-row
          :header-cell-style="{ 'font-weight': 400, color: '#494E6A' }"
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
      <div class="table_box mode-margin">
        <div class="serve-table">
          <div class="table-tile">插件</div>
          <el-button
            plain
            type="primary"
            size="small"
            class="add-but"
            @click="goplugin"
            >添加插件
          </el-button>
        </div>
        <el-table
          :data="table"
          empty-text="暂无数据"
          :row-style="{ height: '50px' }"
          highlight-current-row
          :header-cell-style="{ 'font-weight': 400, color: '#494E6A' }"
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
      <div class="table_box mode-margin">
        <div class="serve-table">
          <div class="table-tilelong">请求日志</div>
        </div>
        <el-table
          :data="table"
          empty-text="暂无数据"
          :row-style="{ height: '50px' }"
          highlight-current-row
          :header-cell-style="{ 'font-weight': 400, color: '#494E6A' }"
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
      <div class="table_box mode-margin">
        <div class="serve-table">
          <div class="table-tilelong">错误日志</div>
        </div>
        <el-table
          :data="table"
          empty-text="暂无数据"
          :row-style="{ height: '50px' }"
          highlight-current-row
          :header-cell-style="{ 'font-weight': 400, color: '#494E6A' }"
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
      this.$router.push({ path: "/serve/newEdition" });
    },
    goplugin() {
      this.$router.push({ path: "/serve/serveDetail/plug-in" });
    },
  },
};
</script>

<style lang='scss' scoped>
.mode-margin {
  margin-top: 24px;
}
.numbers {
  height: 116px;
  border: 1px solid #e1e6ee;
  border-radius: 10px;
  div {
    display: inline-block;
    width: 33.33%;
    height: 115px;
    vertical-align: middle;
    div {
      display: block;
      height: 50px;
      width: 100%;
      text-align: center;
    }
    .font {
      line-height: 58px;
    }
    .num {
      line-height: 40px;
      font-weight: bold;
      font-size: 26px;
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
  .table-tilelong {
    line-height: 30px;
  }
}
</style>