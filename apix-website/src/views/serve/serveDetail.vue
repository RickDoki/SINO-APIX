<template>
  <div class="main">
    <div v-if="!routerView">
      <div class="list_top">
        <div class="list_title">{{ serveData.appName }}</div>
        <div class="list_search">
          <div class="but-left">
            <el-dropdown @command="handleCommand">
              <el-button type="primary" size="small">
                操作<i class="el-icon-arrow-down el-icon--right"></i>
              </el-button>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item
                  v-for="(item, index) in dropdownItems"
                  :key="index"
                  :command="item"
                  >{{ item }}</el-dropdown-item
                >
              </el-dropdown-menu>
            </el-dropdown>
          </div>
          <el-button type="primary" size="small">编辑文档</el-button>
        </div>
      </div>
      <div class="secondTitle">创建服务来管理和代理现有API或发布到门户。</div>
      <div class="status">
        <div class="left-span">
          <span>门户状态: </span>
          <span class="noPublished" v-if="serveData.isPublished === '60001'"
            >未发布</span
          >
          <span class="hasPublished" v-else>已发布</span>
        </div>
        <div class="time">
          <div>
            <span>创建时间 : </span>
            <span>{{ serveData.appCreationDate }}</span>
          </div>
          <div>
            <span>更新时间 : </span>
            <span>{{ serveData.appLastUpdateDate }}</span>
          </div>
        </div>
      </div>
      <div class="numbers mode-margin">
        <div class="requestAll">
          <div class="font">请求计数</div>
          <div class="num">{{ serveNum.totalNum }}</div>
        </div>
        <div class="requestError">
          <div class="font">失败的请求</div>
          <div class="num">{{ serveNum.failNum }}</div>
        </div>
        <div class="edition">
          <div class="font">版本计数</div>
          <div class="num">{{ serveNum.subscribedNum }}</div>
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
          :data="serveData.appVersion"
          empty-text="暂无数据"
          v-loading="versionLoading"
          :row-style="{ height: '50px' }"
          highlight-current-row
          :header-cell-style="{ 'font-weight': 400, color: '#494E6A' }"
        >
          <el-table-column prop="version" label="版本名称" />
          <el-table-column prop="description" label="版本描述" />
          <el-table-column label="操作" width="180px">
            <template slot-scope="scope">
              <el-button type="text" @click="edition(scope.row)"
                >编辑</el-button
              >
              <span class="handle">|</span>
              <el-button
                style="color: red"
                type="text"
                @click="delversion(scope.row)"
                >删除</el-button
              >
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
              <el-button type="text" @click="getMessage(scope.row)"
                >查看</el-button
              >
              <span class="handle">|</span>
              <el-button type="text" @click="getMessage(scope.row)"
                >退订</el-button
              >
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
              <el-button type="text" @click="getMessage(scope.row)"
                >查看</el-button
              >
              <span class="handle">|</span>
              <el-button type="text" @click="getMessage(scope.row)"
                >退订</el-button
              >
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
              <el-button type="text" @click="getMessage(scope.row)"
                >查看</el-button
              >
              <span class="handle">|</span>
              <el-button type="text" @click="getMessage(scope.row)"
                >退订</el-button
              >
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
import {
  serveDetail,
  appNum,
  serveupdate,
  serveDelete,
  delApiversion,
} from "@/api/AboutServe.js";

export default {
  data() {
    return {
      routerView: false,
      table: [],
      dropdownItems: [],
      appCode: "",
      serveData: {},
      serveNum: {},
      versionLoading: false
    };
  },
  created() {
    if (this.$route.name === "serveDteail") {
      this.routerView = false;
    } else {
      this.routerView = true;
    }
    // 获取appcode
    this.appCode = this.$route.query.appcode;
    this.getServeDeatil();
    this.getAppNum();
  },
  methods: {
    // 通过appcode查询详情
    getServeDeatil() {
      this.versionLoading = true
      serveDetail(this.appCode).then((res) => {
        if (res.code === 200) {
          this.versionLoading = false
          this.serveData = res.data;
          if (res.data.isPublished === "60005") {
            this.dropdownItems = ["下架"];
          } else {
            this.dropdownItems = ["发布到门户", "删除"];
          }
        }
      });
    },
    // 内部详情
    getAppNum() {
      appNum(this.appCode).then((res) => {
        // console.log(res);
        if (res.code === 200) {
          this.serveNum = res.data;
        }
      });
    },
    // 操作
    handleCommand(command) {
      // console.log(command)
      if (command === "下架") {
        const query = {
          isPublished: "60001",
        };
        serveupdate(this.appCode, query).then((res) => {
          if (res.code === 200) {
            this.getServeDeatil();
          }
        });
      } else if (command === "发布到门户") {
        const query = {
          isPublished: "60005",
        };
        serveupdate(this.appCode, query).then((res) => {
          if (res.code === 200) {
            this.getServeDeatil();
          }
        });
      } else {
        serveDelete(this.appCode).then((res) => {
          if (res.code === 200) {
            this.$router.push({ path: "/serve/center" });
          }
        });
      }
    },
    // 删除服务版本
    delversion(e) {
      delApiversion(e.id).then((res) => {
        if (res.code === 200) {
          this.getServeDeatil();
        }
      });
    },
    gonewEdition() {
      this.$router.push({ path: "/serve/newEdition?appcode=" + this.appCode });
    },
    goplugin() {
      this.$router.push({ path: "/serve/serveDetail/plug-in" });
    },
    // 版本详情
    edition(e) {
      this.$router.push({
        path:
          "/serve/editionDetail?appCode=" +
          this.appCode +
          "&appVersionId=" +
          e.id,
      });
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
.hasPublished {
  width: 58px;
  height: 20px;
  background-color: #e1f8da;
  color: #61b874;
  border-radius: 3px;
  text-align: center;
  display: inline-block;
  line-height: 20px;
}
.noPublished {
  width: 58px;
  height: 20px;
  background-color: #e1e6ee;
  color: #727491;
  border-radius: 3px;
  text-align: center;
  display: inline-block;
  line-height: 20px;
}
</style>