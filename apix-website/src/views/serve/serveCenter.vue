<template>
  <div class="app_list_main">
    <div class="app_list_top">
      <div class="app_list_title">
        <div class="titleFont">服务中心</div>
        <div style="margin-top: 10px" class="secondTitle">
          创建服务来管理和代理现有API或发布到门户。
        </div>
      </div>
      <div class="app_list_search">
        <el-input
          size="mini"
          placeholder="搜索"
          suffix-icon="el-icon-search"
          v-model="name"
          style="display: inline-block"
        >
        </el-input>
      </div>
      <div class="addServe">
        <el-button type="primary" size="mini" icon="el-icon-plus">添加新服务</el-button>
      </div>
    </div>
    <div class="table_box">
      <el-table :data="table" empty-text="暂无数据" stripe style="width: 100%">
        <el-table-column prop="appName" label="应用名称" />
        <el-table-column prop="appCode" label="code" />
        <el-table-column prop="appCode" label="门户状态" />
        <el-table-column prop="appCode" label="版本" />
        <el-table-column label="操作" width="180px">
          <template slot-scope="scope">
            <div class="handle">
              <span @click="getMessage(scope.row)" class="linkcolor">查看</span>
              <span class="handle_middle">|</span>
              <span class="linkcolor">退订</span>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        background
        style="margin-top: 20px; text-align: center"
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
  data() {
    return {
      table: [],
      total: 100,
      currentPage: 1,
      name: "",
    };
  },
  created() {},
  methods: {
    handleCurrentChange() {
      console.log("页面跳转");
    },
  },
};
</script>

<style lang='scss' scoped>
.app_list_main {
  width: 95%;
  margin: 0 auto;
  margin-top: 20px;
  border-radius: 5px;
  background-color: #fff;
  overflow: hidden;
  .app_list_top {
    display: flex;
    margin-bottom: 20px;
    justify-content: space-between;
    .app_list_title {
      width: calc(100% - 330px)
    }
    .app_list_search {
      .addServe {
        display: inline-block;
      }
    }
  }
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
    .handle {
      display: flex;
      .handle_middle {
        // width: 15px;
        margin: 0px 5px;
      }
    }
  }
}
</style>
