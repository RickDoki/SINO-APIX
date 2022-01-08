<template>
  <div class="plug-in">
    <div class="table_box mode-margin">
      <p>已添加插件</p>
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
              <el-button type="text" @click="getMessage(scope.row)">查看</el-button>
              <span class="handle">|</span>
              <el-button type="text" @click="getMessage(scope.row)">退订</el-button>
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
            <el-button type="text" @click="getMessage(scope.row)">查看</el-button>
            <span class="handle">|</span>
            <el-button type="text" @click="getMessage(scope.row)">退订</el-button>
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
            <el-button type="text" @click="getMessage(scope.row)">查看</el-button>
            <span class="handle">|</span>
            <el-button type="text" @click="getMessage(scope.row)">退订</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      table: [],
    };
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
</style>