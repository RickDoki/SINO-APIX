<template>
  <div class="app_list_main">
    <el-row class="search" :gutter="20">
      <el-col :span="4">
        <el-input v-model="APIName" placeholder="请输入API名称" />
      </el-col>
      <el-col :span="10">
        <el-date-picker
          v-model="value1"
          type="datetimerange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          value-format="timestamp"
          :default-time="['00:00:00', '23:59:59']"
        />
      </el-col>
      <el-col :span="10">
        <el-button
          type="primary"
          icon="el-icon-search"
          @click="search"
        >搜索</el-button>
        <el-button
          type="primary"
          @click="goCreate"
        >创建应用</el-button>
      </el-col>
    </el-row>
    <table-list
      :total="total"
      :table="table"
      @refresh="refresh"
      @pageChange="pageChange"
    />
  </div>
</template>

<script>
import tableList from '@/components/TableList/app.vue'
import { list } from '@/api/AboutApp'
import { getToken } from '@/utils/auth' // get token from cookie

export default {
  components: {
    tableList
  },
  data() {
    return {
      value1: [],
      APIName: '',
      total: 0,
      table: [],
      developerId: ''
    }
  },
  created() {
    this.developerId = getToken('userId')
    const query = '?limit=10&offset=1&developerId=' + this.developerId
    this.getapplist(query)
  },
  methods: {
    search() {
      if (this.value1) {
        if (this.value1.length === 0) {
          const query = '?limit=10&offset=1&appName=' + this.APIName + '&developerId=' + this.developerId
          this.getapplist(query)
        } else {
          const query =
            '?limit=10&offset=1&appName=' +
            this.APIName +
            '&startTime=' +
            this.value1[0] +
            '&endTime=' +
            this.value1[1] +
            '&developerId=' +
            this.developerId
          this.getapplist(query)
        }
      } else {
        const query = '?limit=10&offset=1&appName=' + this.APIName + '&developerId=' + this.developerId
        this.getapplist(query)
      }
    },
    pageChange(val) {
      const query = '?limit=10' + '&offset=' + val + '&developerId=' + this.developerId
      this.getapplist(query)
    },
    getapplist(query) {
      list(query).then((res) => {
        if (res.code === 200) {
          this.table = res.data.appList
          this.total = res.data.total
        } else {
          this.$message({
            message: res.msg,
            type: 'error'
          })
        }
      })
    },
    refresh() {
      const query = '?limit=10&offset=1' + '&developerId=' + this.developerId
      this.getapplist(query)
    },
    // 去创建
    goCreate() {
      this.$router.push({path:'/app/add'})
    }
  }
}
</script>

<style lang='scss' scoped>
.app_list_main {
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
