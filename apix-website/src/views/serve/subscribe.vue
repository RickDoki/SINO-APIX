<template>
  <div class="main">
    <div class="list_top list_top_bom">
      <div class="list_title titleFont">我的订阅</div>
      <div class="list_search">
        <el-input
          size="small"
          placeholder="搜索"
          suffix-icon="el-icon-search"
          v-model="name"
          class="list_searchInput"
          @change="search()"
        >
        </el-input>
      </div>
    </div>
    <div class="table_box">
      <el-table :data="table" empty-text="暂无数据" :row-style="{height: '50px'}" highlight-current-row :header-cell-style="{'font-weight': 400, 'font-size':'16px', color:'#1D1C35'}">
        <el-table-column prop="appName" label="应用名称" />
        <el-table-column prop="appCode" label="APPCode" />
        <el-table-column prop="appCode" label="启用状态" />
        <el-table-column prop="appCode" label="描述" />
        <el-table-column label="操作" width="180px">
          <template slot-scope="scope">
            <el-button type="text" @click="getMessage(scope.row)">查看</el-button>
            <span class="handle">|</span>
            <el-button type="text" @click="getMessage(scope.row)">退订</el-button>
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
export default {
  data () {
    return {
      table: [
        {
          appName: "测试数据",
        },
      ],
      total: 100,
      currentPage: 1,
      name: "",
    };
  },
  created () { },
  methods: {
    search () {

    },
    handleCurrentChange () {
      console.log("页面跳转");
    },
    // 跳转api详情
    getMessage () {
      this.$router.push({ path: "/serve/subscribeDetail" });
    },
  },
};
</script>

<style lang='scss' scoped>
</style>
