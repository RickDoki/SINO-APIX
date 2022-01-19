<template>
  <div class="main">
    <div v-if="!routerView">
      <div class="list_top">
        <div class="list_title">服务中心</div>
        <div class="list_search">
          <el-input
            size="small"
            placeholder="搜索"
            suffix-icon="el-icon-search"
            v-model="name"
            class="list_searchInput"
            @input="nameSerach"
          >
          </el-input>
          <el-button
            type="primary"
            size="small"
            icon="el-icon-plus"
            @click="goCreatdServe"
            >添加新服务</el-button
          >
        </div>
      </div>
      <div class="secondTitle list_top_bom">
        创建服务来管理和代理现有API或发布到门户。
      </div>
      <div class="table_box">
        <el-table
          :data="table"
          empty-text="暂无数据"
          v-loading="loading"
          :row-style="{ height: '50px' }"
          highlight-current-row
          :header-cell-style="{
            'font-weight': 400,
            'font-size': '16px',
            color: '#1D1C35',
          }"
        >
          <el-table-column prop="appName" label="服务名称" show-overflow-tooltip >
            <template slot-scope="scope">
              <span @click="goserveDteail(scope)" class="linkcolor">{{
                scope.row.appName
              }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="appCode" label="code" />
          <el-table-column label="启用状态">
            <template slot-scope="scope">
              <div class="tag success" v-if="scope.row.isPublished === '60005'">
                已发布
              </div>
              <div class="tag info" v-else>未发布</div>
            </template>
          </el-table-column>
          <el-table-column min-width="250px" label="版本">
            <template slot-scope="scope">
              <div
                v-for="(item, index) in scope.row.appVersions"
                :key="index"
                class="version"
              >
                {{ item }}
              </div>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="180px">
            <template slot-scope="scope">
              <div v-if="scope.row.isPublished === '60005'">
                <el-button
                  style="color: #f03063"
                  type="text"
                  @click="undercarriage(scope.row)"
                  >下架</el-button
                >
              </div>
              <div v-else>
                <el-button type="text" @click="Published(scope.row)"
                  >发布到门户</el-button
                >
                <span class="handle">|</span>
                <el-button type="text" style="color: #f03063" @click="del(scope.row)">删除</el-button>
              </div>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination
          background
          class="list-pagination"
          :current-page.sync="currentPage"
          layout="prev, pager, next"
          :total="total"
          @current-change="handleCurrentChange"
        />
      </div>
    </div>
    <router-view v-if="routerView"></router-view>
  </div>
</template>

<script>
import "./../mainCss/index.scss";
import { serveList, serveupdate, serveDelete } from "@/api/AboutServe.js";
import { getToken } from "@/utils/auth"; // get token from cookie

export default {
  data () {
    return {
      routerView: false,
      table: [
        {
          appName: "我的服务",
        },
      ],
      total: 100,
      currentPage: 1,
      name: "",
      developerId: "",
      // 数据加载
      loading: false,
    };
  },
  created () {
    if (this.$route.name === "serveCenter") {
      this.routerView = false
      this.developerId = getToken("userId_api");
      this.getServeList();
    } else {
      this.routerView = true
    }
  },
  methods: {
    // 获取列表
    getServeList () {
      this.loading = true;
      const query =
        "developerId=" +
        this.developerId +
        "&" +
        "limit=10" +
        "&" +
        "offset=" +
        this.currentPage +
        "&" +
        "appName=" +
        this.name +
        "&" +
        "market=false";
      serveList(query).then((res) => {
        if (res.code === 200) {
          this.loading = false;
          this.table = res.data.appList;
          this.total = res.data.total;
        }
      });
    },
    // 页面跳转
    handleCurrentChange (val) {
      this.currentPage = val;
      this.getServeList();
    },
    // 查看详情
    goserveDteail (e) {
      this.$router.push({ path: "/serve/serveDetail/" + e.row.appCode });
    },
    //创建新服务
    goCreatdServe () {
      this.$router.push({ path: "/serve/create" });
    },
    // 搜索
    nameSerach () {
      this.currentPage = 1;
      this.getServeList();
    },
    // 下架服务
    undercarriage (e) {
      const query = {
        isPublished: "60001",
      };
      serveupdate(e.appCode, query).then((res) => {
        if (res.code === 200) {
          this.getServeList();
        }
      });
    },
    // 发布到门户
    Published (e) {
      const query = {
        isPublished: "60005",
      };
      serveupdate(e.appCode, query).then((res) => {
        if (res.code === 200) {
          this.getServeList();
        }
      });
    },
    // 删除
    del (e) {
      this.$confirm("确认删除服务：" + e.appName + ", 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        serveDelete(e.appCode).then((res) => {
          if (res.code === 200) {
            this.getServeList();
          }
        });
      });
    },
  },
};
</script>

<style lang='scss' scoped>
.hasPublished {
  width: 58px;
  height: 20px;
  background-color: #e1f8da;
  color: #61b874;
  border-radius: 3px;
  text-align: center;
}
.noPublished {
  width: 58px;
  height: 20px;
  background-color: #e1e6ee;
  color: #727491;
  border-radius: 3px;
  text-align: center;
}
.version {
  display: inline-block;
  padding: 0px 5px;
  color: #2650ff;
  margin: 2px 5px 0px 0px;
  background-color: #d4dcff;
  border-radius: 3px;
}
</style>
