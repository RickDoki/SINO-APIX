<template>
  <div class="api_list_main">
    <!-- <el-row class="search" :gutter="20">
      <el-col :span="4">
        <el-input v-model="APIName" placeholder="请输入API名称" />
      </el-col>
      <el-col :span="8">
        <el-date-picker
          v-model="value1"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        />
      </el-col>
      <el-col :span="6">
        <el-button type="primary" icon="el-icon-search">搜索</el-button>
      </el-col>
    </el-row> -->
    <table-list
      :total="total"
      :table-list="tableList"
      :loading='loading'
      @refresh="refresh"
      @pageChange="pageChange"
    />
  </div>
</template>

<script>
import tableList from '@/components/TableList/data.vue'
import { getList } from '@/api/data'
export default {
  components: {
    tableList
  },
  data() {
    return {
      APIName: '',
      value1: '',
      tableList: [],
      total: 0,
      loading:true
    }
  },
  created() {
    const query = '?limit=10&offset=1'
    this.list(query)
  },
  methods: {
    list(query) {
      getList(query).then((res) => {
        if (res.code === 200) {
          this.tableList = res.data.logList
          this.total = res.data.total
          this.loading = false
        }
      })
    },
    refresh() {},
    pageChange(val) {
      const query = '?limit=10&offset=' + val
      this.list(query)
    }
  }
}
</script>

<style lang='scss' scoped>
.api_list_main {
  width: 95%;
  margin: 0 auto;
  margin-top: 20px;
  border-radius: 5px;
  background-color: #fff;
  overflow: hidden;
  .search {
    margin-top: 20px;
    padding: 0px 30px;
  }
}
</style>
