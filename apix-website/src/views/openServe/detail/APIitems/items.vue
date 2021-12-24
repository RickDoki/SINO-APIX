<template>
  <div class="item_main">
    <el-collapse v-model="activeName" accordion>
      <el-collapse-item title="一致性 Consistency" name="1">
        <span class="collapse-title" slot="title">请求参数(Query)</span>
        <div class="table_box">
          <el-table :data="tableData" border style="width: 100%" :header-cell-style="{background:'#F0F2F5'}">
            <el-table-column prop="date" label="名称" width="180">
            </el-table-column>
            <el-table-column prop="name" label="类型" width="180">
            </el-table-column>
            <el-table-column prop="name" width="180" label="是否必选"></el-table-column>
            <el-table-column prop="address" label="描述"></el-table-column>
          </el-table>
        </div>
      </el-collapse-item>
      <el-collapse-item title="反馈 Feedback" name="2">
        <span class="collapse-title" slot="title">HTTP状态码</span>
        <div>
          控制反馈：通过界面样式和交互动效让用户可以清晰的感知自己的操作；
        </div>
        <div>页面反馈：操作后，通过页面元素的变化清晰地展现当前状态。</div>
      </el-collapse-item>
    </el-collapse>
  </div>
</template>

<script>
export default {
  data() {
    return {
      activeName: "1",
      tableData: [
        {
          date: "2016-05-02",
          name: "王小虎",
          address: "上海市普陀区金沙江路 1518 弄",
        },
        {
          date: "2016-05-04",
          name: "王小虎",
          address: "上海市普陀区金沙江路 1517 弄",
        },
        {
          date: "2016-05-01",
          name: "王小虎",
          address: "上海市普陀区金沙江路 1519 弄",
        },
        {
          date: "2016-05-03",
          name: "王小虎",
          address: "上海市普陀区金沙江路 1516 弄",
        },
      ],
    };
  },
};
</script>

<style lang='scss' scoped>
.item_main {
  padding: 24px;
  .el-collapse {
    border: 0px;
  }
  ::v-deep .el-collapse-item {
    margin-bottom: 20px;
    .el-collapse-item__header {
      background-color: #F0F2F5;
    }
  }

  .collapse-title {
    flex: 1 0 90%;
    order: 1;
  }

  .el-collapse-item__header {
    flex: 1 0 auto;
    order: -1;
  }
  .table_box {
    padding: 24px;
  }
}
</style>