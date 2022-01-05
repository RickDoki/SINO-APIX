<template>
  <div class="main">
    <div class="list_top list_top_bom">
      <div class="list_title titleFont">API列表</div>
      <div class="list_search">
        <el-input
          suffix-icon="el-icon-search"
          class="list_searchInput"
          v-model="APIName"
          size="small"
          placeholder="请输入API名称"
          @change="search()" />
        <el-date-picker
          v-model="value1"
          type="datetimerange"
          class="list_searchInput_date"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          size="small"
          @change="search()"
          value-format="timestamp"
          :default-time="['00:00:00', '23:59:59']"
        />
        <el-button type="primary" size="small" icon="el-icon-plus" @click="goApicreate">添加新API</el-button>
      </div>
    </div>
    <div class="table_box">
      <el-table :row-style="{height: '50px'}" :data="tableData" highlight-current-row :header-cell-style="{'font-weight': 400, 'font-size':'16px', color:'#1D1C35'}">
        <el-table-column prop="name" label="API名称" show-overflow-tooltip></el-table-column>
        <el-table-column prop="code" label="协议" show-overflow-tooltip></el-table-column>
        <el-table-column prop="status" label="域名"></el-table-column>
        <el-table-column prop="version" label="路径"></el-table-column>
        <el-table-column prop="version2" label="API描述"></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button type="text" @click="delAPI(scope.row)" class="textBut-danger">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        background
        class="list-pagination"
        :current-page.sync="page"
        layout="prev, pager, next"
        :total="total"
        @current-change="handleCurrentChange"
      />
    </div>
  </div>
</template>

<script>
import { list } from "@/api/AboutApi";
import { getToken } from "@/utils/auth"; // get token from cookie

export default {
  components: {
  },
  data () {
    return {
      APIName: "",
      value1: [],
      tableData: [{ name: '', code: '', status: '', version: '', version2: '' }, { name: '', code: '', status: '', version: '', version2: '' }],
      total: 0,
      developerId: "",
      page: 1,
      size: 10
    };
  },
  created () {
    this.developerId = getToken("userId_api");
    const query = "limit=" + this.size + "&offset=" + this.page + "&developerId=" + this.developerId;
    this.getList(query);
  },
  methods: {
    // 删除API数据信息
    delAPI (row) {
      this.$confirm('确认删除API：' + row.name + ', 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
      // .then(() => ({
      //   deleteApi(row.id).then(res => {
      //     this.$message({
      //       type: 'success',
      //       message: '删除成功!'
      //     })
      //     this.page = 1
      //     this.search()
      //   })
      // }).catch(() => {
      // })
    },
    sizeChange (val) {
      this.size = val
      const query =
        "?limit=" + this.size + "&offset=" + this.page + "&developerId=" + this.developerId;
      this.getList(query);
    },
    search () {
      this.page = 1
      if (this.value1) {
        if (this.value1.length === 0) {
          const query =
            "limit=" + this.size + "&offset=" + this.page + "&apiName=" +
            this.APIName +
            "&developerId=" +
            this.developerId;
          this.getList(query);
        } else {
          const query =
            "limit=" + this.size + "&offset=" + this.page + "&apiName=" +
            this.APIName +
            "&startTime=" +
            this.value1[0] +
            "&endTime=" +
            this.value1[1] +
            "&developerId=" +
            this.developerId;
          this.getList(query);
        }
      } else {
        const query =
          "limit=" + this.size + "&offset=" + this.page + "&apiName=" +
          this.APIName +
          "&developerId=" +
          this.developerId;
        this.getList(query);
      }
    },
    getList (query) {
      // 获取api列表
      list(query).then((res) => {
        if (res.code === 200) {
          this.tableData = res.data.apiList;
          console.log(this.tableData)
          this.total = res.data.total;
        }
      });
    },
    refresh () {
      const query = "limit=" + this.size + "&offset= " + this.page + "&developerId=" + this.developerId;
      this.getList(query);
    },
    reset () {
      this.value1 = [];
      this.APIName = "";
      this.search();
    },
    handleCurrentChange (val) {
      this.page = val
      const query =
        "limit=" + this.size + "&offset=" + this.page + "&developerId=" + this.developerId;
      this.getList(query);
    },
    goApicreate () {
      this.$router.push('/api/add')
    }
  },
};
</script>

<style lang='scss' scoped>
.api_list_main {
  width: 98%;
  margin: 0 auto;
  margin-top: 10px;
  border-radius: 5px;
  background-color: #fff;
  overflow: hidden;
  min-height: calc(100vh - 185px);
  .search {
    margin-top: 20px;
    padding: 0px 30px;
  }
}
</style>
