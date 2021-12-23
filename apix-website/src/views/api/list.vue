<template>
  <div class="api_list_main">
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
      <el-col :span="6">
        <el-button
          type="primary"
          icon="el-icon-search"
          @click="search"
        >搜索</el-button>
      </el-col>
    </el-row>
    <table-list
      :total="total"
      :data="tableList"
      @refresh="refresh"
      @pageChange="pageChange"
    />
  </div>
</template>

<script>
import tableList from '@/components/TableList'
import { list } from '@/api/AboutApi'
import { getToken } from '@/utils/auth' // get token from cookie

export default {
  components: {
    tableList
  },
  data() {
    return {
      APIName: '',
      value1: [],
      tableList: [],
      total: 0,
      developerId: ''
    }
  },
  created() {
    this.developerId = getToken('userId')
    const query = 'limit=10&offset=1&developerId=' + this.developerId
    this.getList(query)
  },
  methods: {
    search() {
      if (this.value1) {
        if (this.value1.length === 0) {
          const query = 'limit=10&offset=1&apiName=' + this.APIName + '&developerId=' + this.developerId
          this.getList(query)
        } else {
          const query =
            'limit=10&offset=1&apiName=' +
            this.APIName +
            '&startTime=' +
            this.value1[0] +
            '&endTime=' +
            this.value1[1] +
            '&developerId=' +
            this.developerId
          this.getList(query)
        }
      } else {
        const query = 'limit=10&offset=1&apiName=' + this.APIName + '&developerId=' + this.developerId
        this.getList(query)
      }
    },
    getList(query) {
      // 获取api列表
      list(query).then((res) => {
        if (res.code === 200) {
          this.tableList = res.data.apiList
          this.total = res.data.total
        }
      })
    },
    refresh() {
      const query = 'limit=10&offset=1&developerId=' + this.developerId
      this.getList(query)
    },
    pageChange(val) {
      const query = 'limit=10' + '&offset=' + val + '&developerId=' + this.developerId
      this.getList(query)
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
