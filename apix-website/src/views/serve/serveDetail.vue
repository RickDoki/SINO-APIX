<template>
  <div class="main">
    <el-drawer
      title="日志详情"
      :visible.sync="drawer"
      :direction="direction"
      :before-close="handleClose"
      size="50%"
    >
      <json-view
        :data="this.historylist"
        theme="index"
        :deep="2"
        :fontSize="12"
      />
    </el-drawer>
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
          <el-button type="primary" size="small" @click="docsEdit()"
            >编辑文档</el-button
          >
        </div>
      </div>
      <div class="secondTitle">创建服务来管理和代理现有API或发布到门户。</div>
      <div class="status">
        <div class="left-span">
          <span>门户状态: </span>
          <span class="tag info" v-if="serveData.isPublished === '60001'"
            >未发布</span
          >
          <span class="tag success" v-else>已发布</span>
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
          :header-cell-style="{
            'font-weight': 400,
            'font-size': '16px',
            color: '#1D1C35',
          }"
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
          :data="pluginsTable"
          v-loading="versionLoading"
          empty-text="暂无数据"
          :row-style="{ height: '50px' }"
          highlight-current-row
          :header-cell-style="{
            'font-weight': 400,
            'font-size': '16px',
            color: '#1D1C35',
          }"
        >
          <el-table-column prop="appName" label="插件名称">
            <template slot-scope="scope">
              <div>
                {{ scope.row.pluginType | plugName }}
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="appCode" label="启用状态">
            <template slot-scope="scope">
              <div>
                <el-switch
                  @change="enabledChange(scope.row)"
                  v-model="scope.row.enabled"
                  active-color="#6EE4A5"
                  inactive-color="#E1E6EE"
                  :active-value="1"
                  :inactive-value="0"
                >
                </el-switch>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="180px">
            <template slot-scope="scope">
              <el-button type="text" @click="getMessage(scope.row)"
                >查看</el-button
              >
              <span class="handle" v-if="goConfig(scope.row.pluginType)"
                >|</span
              >
              <el-button
                type="text"
                v-if="goConfig(scope.row.pluginType)"
                @click="pluginConfig(scope.row)"
                >配置</el-button
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
          :data="requestTable"
          empty-text="暂无数据"
          v-loading="requestLoding"
          :row-style="{ height: '50px' }"
          highlight-current-row
          :header-cell-style="{
            'font-weight': 400,
            'font-size': '16px',
            color: '#1D1C35',
          }"
        >
          <el-table-column prop="requestUri" label="请求地址" width="280px" />
          <el-table-column prop="httpMethod" label="请求方式" />
          <el-table-column prop="remoteIp" label="客户端IP" />
          <el-table-column prop="serverIp" label="服务端IP" />
          <el-table-column prop="consumingTime" label="耗时(ms)" />
          <el-table-column label="调用时间" width="180px">
            <template slot-scope="scope">
              <span>{{ scope.row.eventTime | formatDate }}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button type="text" @click="getlogs(scope.row)"
                >查看</el-button
              >
            </template>
          </el-table-column>
        </el-table>
        <el-pagination
          background
          class="list-pagination"
          :current-page.sync="currentPageRequest"
          layout="prev, pager, next"
          :total="totalRequest"
          @current-change="handleCurrentChangeRequest"
        />
      </div>
      <div class="table_box mode-margin">
        <div class="serve-table">
          <div class="table-tilelong">错误日志</div>
        </div>
        <el-table
          :data="ErrorTable"
          v-loading="errorLoading"
          :row-style="{ height: '50px' }"
          highlight-current-row
          :header-cell-style="{
            'font-weight': 400,
            'font-size': '16px',
            color: '#1D1C35',
          }"
        >
          <el-table-column prop="requestUri" label="请求地址" width="280px" />
          <el-table-column prop="httpMethod" label="请求方式" />
          <el-table-column prop="remoteIp" label="客户端IP" />
          <el-table-column prop="serverIp" label="服务端IP" />
          <el-table-column prop="consumingTime" label="耗时(ms)" />
          <el-table-column label="调用时间" width="180px">
            <template slot-scope="scope">
              <span>{{ scope.row.eventTime | formatDate }}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button type="text" @click="getlogs(scope.row)"
                >查看</el-button
              >
            </template>
          </el-table-column>
        </el-table>
        <el-pagination
          background
          class="list-pagination"
          :current-page.sync="currentPageError"
          layout="prev, pager, next"
          :total="totalError"
          @current-change="handleCurrentChangeError"
        />
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
  log,
  putPlugin,
  open,
  close,
} from "@/api/AboutServe.js";
import jsonView from "./json-view/index.vue";
export default {
  components: {
    jsonView,
  },
  filters: {
    plugName: function (value) {
      const nameFiter = {
        jwt: "Jwt-auth",
        base_auth: "basic_auth",
        oauth2: "OAuth2.0",
        black_list_ip: "IP 黑名单控制",
        white_list_ip: "IP 白名单控制",
        cors: "cors跨域",
        sign: "防篡改签名",
        replay_attacks: "防网络重放攻击",
        error_log: "error log",
        http_log: "http log",
        sentinel: "sentinel",
        gzip: "gzip",
        proxy_cache: "proxy_cache",
        real_ip: "real_ip",
        response_rewrite: "response-rewrite",
      };
      if (!value) return "";
      return nameFiter[value];
    },
  },
  data() {
    return {
      routerView: false,
      drawer: false,
      historylist: {},
      direction: "rtl",
      table: [],
      pluginsTable: [],
      dropdownItems: [],
      appCode: "",
      serveData: {},
      serveNum: {},
      appId: "",
      versionLoading: false,
      // 请求日志分页
      currentPageRequest: 1,
      totalRequest: 0,
      requestTable: [],
      requestLoding: false,
      // 错误日志分页
      currentPageError: 1,
      totalError: 0,
      ErrorTable: [],
      errorLoading: false,
    };
  },
  created() {
    if (this.$route.name === "serveDteail") {
      this.routerView = false;
      // 获取appcode
      this.appCode = this.$route.params.appcode;
      this.getServeDeatil();
      this.getAppNum();
      this.getLog("request");
      this.getLog("error");
    } else {
      this.routerView = true;
    }
  },
  methods: {
    //操作抽屉
    handleClose(done) {
      done();
    },
    getlogs(e) {
      // console.log('查看日志')
      this.drawer = true;
      this.historylist = e;
    },
    // 编辑服务文档
    docsEdit() {
      this.$router.push(
        "/docsEdit/" +
          "serve?id=" +
          this.appCode +
          "&name=" +
          this.serveData.appName
      );
    },
    // 通过appcode查询详情
    getServeDeatil() {
      this.versionLoading = true;
      serveDetail(this.appCode).then((res) => {
        if (res.code === 200) {
          this.versionLoading = false;
          this.serveData = res.data;
          this.appId = res.data.appId;

          this.pluginsTable = res.data.plugins;
          console.log(this.pluginsTable);
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
    // 请求日志
    getLog(e) {
      if (e === "request") {
        this.requestLoding = true;
        const query =
          "appCode=" +
          this.appCode +
          "&limit=10" +
          "&offset=" +
          this.currentPageRequest +
          "&statusCode=200";
        log(query).then((res) => {
          if (res.code === 200) {
            this.requestTable = res.data.logList;
            this.requestLoding = false;
            this.totalRequest = res.data.total;
          }
        });
      } else {
        const query =
          "appCode=" +
          this.appCode +
          "&limit=10" +
          "&offset=" +
          this.currentPageError +
          "&statusCode=500";
        this.errorLoading = true;
        log(query).then((res) => {
          if (res.code === 200) {
            this.errorLoading = false;
            this.ErrorTable = res.data.logList;
            this.totalError = res.data.total;
          }
        });
      }
    },
    // 请求日志页面跳转
    handleCurrentChangeRequest(val) {
      this.getLog("request");
    },
    // 错误日志页面跳转
    handleCurrentChangeError(val) {
      this.getLog("error");
    },
    gonewEdition() {
      this.$router.push({ path: "/serve/newEdition?appcode=" + this.appCode });
    },
    goplugin() {
      this.$router.push({
        path:
          "/serve/serveDetail/plug-in?appcode=" +
          this.appCode +
          "&appid=" +
          this.appId,
      });
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
    // 插件切换状态
    enabledChange(e) {
      if (e.pluginType === "sentinel") {
        if (e.enabled === 0) {
          open(e.appId).then((res) => {
            if (res.code === 200) {
              const query = {
                appId: e.appId,
                appCode: e.appCode,
                pluginType: e.pluginType,
                enabled: e.enabled,
                id: e.id,
              };
              putPlugin(query).then((res) => {
                if (res.code === 200) {
                  this.getServeDeatil();
                }
              });
            }
          });
        } else {
          close(e.appId).then((res) => {
            if (res.code === 200) {
              const query = {
                appId: e.appId,
                appCode: e.appCode,
                pluginType: e.pluginType,
                enabled: e.enabled,
                id: e.id,
              };
              putPlugin(query).then((res) => {
                if (res.code === 200) {
                  this.getServeDeatil();
                }
              });
            }
          });
        }
      } else {
        const query = {
          appId: e.appId,
          appCode: e.appCode,
          pluginType: e.pluginType,
          enabled: e.enabled,
          id: e.id,
        };
        putPlugin(query).then((res) => {
          if (res.code === 200) {
            this.getServeDeatil();
          }
        });
      }
    },
    // 跳转修改插件配置
    pluginConfig(e) {
      console.log(e);
      this.$router.push(
        "/serve/serveDetail/pluginConfig/" +
          e.pluginType +
          "?appcode=" +
          e.appCode +
          "&appid=" +
          e.appId +
          "&id=" +
          e.id +
          "&pluginParams=true"
      );
    },
    // 控制配置显示
    goConfig(value) {
      if (
        value === "jwt" ||
        value === "oauth2" ||
        value === "black_list_ip" ||
        value === "white_list_ip" ||
        value === "cors" ||
        value === "sign" ||
        value === "sentinel"
      ) {
        return true;
      } else {
        return false;
      }
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
::v-deep .el-drawer__body::-webkit-scrollbar {
  display: none;
}
::v-deep .el-drawer__body {
  margin-right: 20px;
}
</style>