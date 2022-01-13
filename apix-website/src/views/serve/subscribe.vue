<template>
  <div class="main">
    <div class="list_top list_top_bom">
      <div class="list_title">我的订阅</div>
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
      </div>
    </div>
    <div class="table_box">
      <el-table
        :data="table"
        v-loading="loading"
        empty-text="暂无数据"
        :row-style="{ height: '50px' }"
        highlight-current-row
        :header-cell-style="{
          'font-weight': 400,
          'font-size': '16px',
          color: '#1D1C35',
        }"
      >
        <el-table-column prop="appName" label="服务名称">
          <template slot-scope="scope">
            <span @click="goserveDteail(scope.row)" class="linkcolor">{{
              scope.row.appName
            }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="appCode" label="APPCode" />
        <el-table-column label="启用状态">
          <template slot-scope="scope">
            <div class="hasPublished" v-if="scope.row.isPublished === '60005'">
              已发布
            </div>
            <div class="noPublished" v-else>未发布</div>
          </template>
        </el-table-column>
        <el-table-column prop="description" label="描述" />
        <el-table-column label="操作" width="180px">
          <template slot-scope="scope">
            <el-button type="text" @click="goserveDteail(scope.row)"
              >查看</el-button
            >
            <span class="handle">|</span>
            <el-button type="text" @click="goserveDteail(scope.row)"
              >退订</el-button
            >
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
</template>

<script>
import "./../mainCss/index.scss";
import { Mysubscribed } from "@/api/AboutServe.js";
export default {
  data() {
    return {
      table: [
        {
          appName: "测试数据",
        },
      ],
      total: 0,
      currentPage: 1,
      name: "",
      loading:false
    };
  },
  created() {
    this.getMysubscribed();
  },
  methods: {
    nameSerach() {
      this.currentPage = 1
      this.getMysubscribed()
    },
    getMysubscribed() {
      this.loading = true
      const query =
        "limit=10" +
        "&" +
        "offset=" +
        this.currentPage +
        "&" +
        "appName=" +
        this.name;
      Mysubscribed(query).then((res) => {
        // console.log(res);
        if(res.code === 200) {
          this.table = res.data.appList
          this.total = res.data.total
          this.total = 100
          this.loading = false
        }
      });
    },
    handleCurrentChange(val) {
      this.currentPage = val
      this.getMysubscribed()
    },
    // 跳转api详情
    goserveDteail(e) {
      this.$router.push({ path: "/serve/subscribeDetail/" + e.appCode });
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
</style>
