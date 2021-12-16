<template>
  <div class="tableFather">
    <el-table :data="tableList" empty-text="暂无数据" border style="width: 100%">
      <el-table-column align="center" prop="username" label="用户名" width="180" />
      <el-table-column align="center" prop="mobile" label="手机号" width="180" />
      <el-table-column align="center" prop="email" label="邮箱" />
      <!-- <el-table-column prop="description" label="描述"> </el-table-column> -->
      <el-table-column align="center" width="150" label="操作">
        <template slot-scope="scope">
          <el-button
            size="mini"
            :disabled="true"
            @click="handleEdit(scope.$index, scope.row)"
          >编辑</el-button>
          <el-button
            size="mini"
            type="danger"
            :disabled="true"
            @click="handleDelete(scope.$index, scope.row)"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      style="margin-top: 20px"
      :current-page.sync="currentPage"
      layout="prev, pager, next"
      :total="total"
      @current-change="handleCurrentChange"
    />
  </div>
</template>

<script>

export default {
  // props: ['tableList', 'total'],
  props: {
    tableList: {
      type: Array,
      default: null
    },
    total: {
      type: Number,
      default: 0
    }
  },
  data() {
    return {
      currentPage: 1
    }
  },
  methods: {
    handleCurrentChange(val) {
      this.currentPage = val
      this.$emit('pageChange', val)
    },
    handleEdit(index, row) {
    //   this.$router.push({ path: "/app/add?message=" + JSON.stringify(row) });
    },
    handleDelete(index, row) {
    //   const id = row.appId;
    //   appDelete(id).then((res) => {
    //     if (res.code === 200) {
    //       this.$message({
    //         message: res.msg,
    //         type: "success",
    //       });
    //       this.$emit("refresh", true);
    //     }
    //   });
    }
  }
}
</script>

<style lang='scss' scoped>
.tableFather {
  padding: 30px;
  .table {
    width: 100%;
  }
}
</style>
