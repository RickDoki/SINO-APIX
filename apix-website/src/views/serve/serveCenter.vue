<template>
  <div class="main">
    <div class="list_top">
      <div class="list_title titleFont">服务中心</div>
      <div class="list_search">
        <el-input
          size="small"
          placeholder="搜索"
          suffix-icon="el-icon-search"
          v-model="name"
          class="list_searchInput"
        >
        </el-input>
        <el-button type="primary" size="small" icon="el-icon-plus">添加新服务</el-button>
      </div>
    </div>
    <div class="secondTitle list_top_bom">
      创建服务来管理和代理现有API或发布到门户。
    </div>
    <div class="table_box">
      <el-table :data="table" empty-text="暂无数据" :row-style="{height: '50px'}" highlight-current-row :header-cell-style="{'font-weight': 400, 'font-size':'16px', color:'#1D1C35'}">
        <el-table-column prop="appName" label="应用名称" />
        <el-table-column prop="appCode" label="code" />
        <el-table-column prop="appCode" label="门户状态" />
        <el-table-column prop="appCode" label="版本" />
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
      table: [],
      total: 100,
      currentPage: 1,
      name: "",
    };
  },
  created () { },
  methods: {
    handleCurrentChange () {
      console.log("页面跳转");
    },
  },
};
</script>

<style lang='scss' scoped>
</style>
