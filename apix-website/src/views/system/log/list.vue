<template>
  <div class="main">
    <div class="list_top list_top_bom">
      <div class="list_title titleFont">审计日志</div>
      <div class="list_search">
        <el-input size="small" v-model="search.username" suffix-icon="el-icon-search"
                  class="list_searchInput" placeholder="请输入用户名称查询"
                  @change="getLogList"></el-input>
        <el-input size="small" v-model="search.resourceName" suffix-icon="el-icon-search"
                  class="list_searchInput" placeholder="请输入资源名称"
                  @change="getLogList"></el-input>
        <el-date-picker
          v-model="search.startTime"
          type="datetimerange"
          class="list_searchInput_date"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          size="small"
          @change="getLogList()"
          value-format="timestamp"
          :default-time="['00:00:00', '23:59:59']"
        />
      </div>
    </div>
    <div class="table_box">
      <el-table :data="tableData" :row-style="{height: '50px'}" highlight-current-row
                :header-cell-style="{'font-weight': 400, 'font-size':'16px', color:'#1D1C35'}">
        <el-table-column prop="appName" label="用户"/>
        <el-table-column prop="appCode" label="事件类型"/>
        <el-table-column prop="appCode" label="资源名称"/>
        <el-table-column prop="appCode" label="发生时间"/>
        <el-table-column label="操作" width="180px">
          <template slot-scope="scope">
            <div class="handle">
              <span @click="detail(scope.row)" class="linkcolor">查看</span>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        background
        class="list-pagination"
        @current-change="handleCurrentChange"
        :current-page="offset"
        layout=" prev, pager, next"
        :total="total">
      </el-pagination>
    </div>
    <el-drawer
      title="日志详情"
      :before-close="handleClose"
      :visible.sync="drawer"
      direction="rtl"
      size="35%"
    >
      <div class="demo-drawer__content">
        <el-descriptions title=" " size="medium" :column="1" border>
          <el-descriptions-item label="用户名称">{{ infoObj.username }}</el-descriptions-item>
          <el-descriptions-item label="事件类型">{{ infoObj.eventType }}</el-descriptions-item>
          <el-descriptions-item label="资源名称">{{ infoObj.resourceName }}</el-descriptions-item>
          <!-- <el-descriptions-item label="创建时间">
          <el-tag size="small">学校</el-tag>
          </el-descriptions-item> -->
          <el-descriptions-item label="创建时间">{{ infoObj.eventTime }}</el-descriptions-item>
        </el-descriptions>
        <div style="margin-top: 20px">
          <json-view :data="infoObj" theme="vs-code" :deep="2" :fontSize="12"/>
        </div>
      </div>
    </el-drawer>
  </div>
</template>

<script>
import {getLogList} from '@/api/data'
import jsonView from "@/components/json-view/index.vue";
import "../../mainCss/index.scss";

export default {
  components: {
    jsonView
  },
  data() {
    return {
      startTime: {
        disabledDate: time => {
          if (this.search.endTime) {
            return (
              time.getTime() > new Date(this.search.endTime).getTime()
            )
          }
        }
      },
      endTime: {
        disabledDate: time => {
          if (this.search.startTime) {
            return (
              time.getTime() < new Date(this.search.startTime).getTime()
            )
          }
        }
      },
      drawer: false,
      infoObj: {},
      search: {
        username: '',
        userId: '',
        eventType: '',
        resourceName: '',
        startTime: '',
        endTime: ''
      },
      offset: 1,
      limit: 10,
      total: 0,
      requestExample: JSON.stringify({
        appName: "开发数据"
      }),
      tableData: [{
        appName: "开发数据"
      }]
    }
  },
  created() {
    // this.getLogList()
  },
  methods: {
    // 获取列表
    getLogList() {
      let params = `?offset=${this.offset}&limit=${this.limit}`
      if (this.search.username) {
        params += `&username=${this.search.username}`
      }
      if (this.search.userId) {
        params += `&userId=${this.search.userId}`
      }
      if (this.search.eventType) {
        params += `&eventType=${this.search.eventType}`
      }
      if (this.search.resourceName) {
        params += `&resourceName=${this.search.resourceName}`
      }
      if (this.search.startTime) {
        params += `&startTime=${this.search.startTime}`
      }
      if (this.search.endTime) {
        params += `&endTime=${this.search.endTime}`
      }
      getLogList(params).then(res => {
        if (res.code === 200) {
          this.tableData = res.data.logList
          this.total = res.data.total
        }
      })
    },
    // 查看详情
    detail(row) {
      // 打开抽屉
      this.drawer = true
      this.infoObj = row
    },
    // 关闭抽屉
    handleClose(done) {
      this.drawer = false
    },
    // 重置搜索条件
    resetSearch() {
      this.search = {
        username: '',
        userId: '',
        eventType: '',
        resourceName: '',
        startTime: '',
        endTime: ''
      }
      this.getLogList()
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`)
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`)
    }
  }
}
</script>

<style lang='scss' scoped>
.demo-drawer__content {
  padding: 0px 24px;
}

.demo-drawer__footer {
  text-align: right;
  margin-top: 40px;
}
</style>

