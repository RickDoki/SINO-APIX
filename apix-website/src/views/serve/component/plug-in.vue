<template>
  <div class="plug-in auto">
    <div class="table_box mode-margin">
      <p>已添加插件</p>
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
            <span class="handle" v-if="goConfig(scope.row.pluginType)">|</span>
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
            <el-button type="text" @click="getMessage(scope.row)"
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
            <el-button type="text" @click="getMessage(scope.row)"
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
</template>

<script>
import { serveDetail, log, putPlugin, open, close } from "@/api/AboutServe.js";
export default {
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
      ErrorTable: [],
      pluginsTable: [],
      appCode: "",
      versionLoading: false,
      appId: "",
      requestLoding: false,
      requestTable: [],
      currentPageRequest: 1,
      totalRequest: 0,
      errorLoading: false,
      currentPageError: 1,
      totalError: 0,
    };
  },
  created() {
    this.appCode = this.$route.params.appCode;
    this.getServeDeatil();
    this.getLog("request");
    this.getLog("error");
  },
  methods: {
    // 通过appcode查询详情
    getServeDeatil() {
      this.versionLoading = true;
      serveDetail(this.appCode).then((res) => {
        if (res.code === 200) {
          this.versionLoading = false;
          this.appId = res.data.appId;
          this.pluginsTable = res.data.plugins;
        }
      });
    },
    // 切换插件启用状态
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
      this.currentPageRequest = val;
      this.getLog("request");
    },
    // 错误日志页面跳转
    handleCurrentChangeError(val) {
      this.currentPageError = val;
      this.getLog("error");
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

<style scoped lang='scss'>
.table_box {
  padding: 30px;
  border: 1px solid #e1e6ee;
  border-radius: 10px;
  ::v-deep {
    .el-table th.el-table__cell.is-leaf,
    .el-table td.el-table__cell {
      border: none;
    }
    .el-table::before {
      height: 0px;
    }
  }
}
.mode-margin {
  margin-top: 24px;
}
.auto {
  height: 100%;
  overflow: auto;
  padding-bottom: 200px;
}
.auto::-webkit-scrollbar {
  display: none;
}
</style>