<template>
  <div class="plug-in">
    <div class="table_box">
      <p>已添加插件</p>
      <el-table :data="table" empty-text="暂无数据" stripe style="width: 100%">
        <el-table-column prop="appName" label="应用名称" />
        <el-table-column prop="appCode" label="APPCode" />
        <el-table-column prop="appCode" label="启用状态" />
        <el-table-column prop="appCode" label="描述" />
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
    </div>
    <div class="table_box second">
      <p>数据统计-http日志/log日志</p>
      <el-table :data="table" empty-text="暂无数据" stripe style="width: 100%">
        <el-table-column prop="appName" label="应用名称" />
        <el-table-column prop="appCode" label="APPCode" />
        <el-table-column prop="appCode" label="启用状态" />
        <el-table-column prop="appCode" label="描述" />
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
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      table: [],
    };
  },
};
</script>

<style scoped lang='scss'>
p {
  margin: 0px;
  margin-bottom: 10px;
}
.plug-in {
  padding: 20px 10px;
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
  .second {
    margin-top: 20px;
  }
}
</style>