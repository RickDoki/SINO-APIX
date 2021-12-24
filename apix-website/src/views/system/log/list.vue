<template>
  <div class="main">
    <el-card class="box-card" shadow="never">
      <div class="card-top">
        <div class="input-box">
          <span>用户名称：</span>
          <el-input size="small" v-model="search.username" style="width: 70%" placeholder="请输入用户名称查询"></el-input>
        </div>
        <div class="input-box input-left">
          <span>用户 ID：</span>
          <el-input size="small" v-model="search.userId" style="width: 70%; margin-left: 12px;" placeholder="请输入用户ID"></el-input>
        </div>
        <div class="input-box input-left">
          <span>事件类型：</span>
          <el-input size="small" v-model="search.eventType" style="width: 70%" placeholder="请输入事件类型"></el-input>
        </div>
      </div>
      <div class="card-top">
        <div class="input-box">
          <span>资源名称：</span>
          <el-input size="small" v-model="search.resourceName" style="width: 70%" placeholder="请输入资源名称"></el-input>
        </div>
        <div class="input-box input-left">
          <span>开始时间：</span>
          <el-date-picker
            :picker-options="startTime"
            value-format="timestamp"
            size="small"
            style="width: 70%"
            v-model="search.startTime"
            type="datetime"
            placeholder="选择日期时间">
          </el-date-picker>
        </div>
        <div class="input-box input-left">
          <span>结束时间：</span>
          <el-date-picker
            :picker-options="endTime"
            value-format="timestamp"
            size="small"
            style="width: 70%"
            v-model="search.endTime"
            type="datetime"
            placeholder="选择日期时间"
            default-time="23:59:59">
          </el-date-picker>
        </div>
        <div class="but-right">
          <el-button size="small" @click="resetSearch">重置</el-button>
          <el-button type="primary" size="small" style="background-color: #4461D7; border-color: #4461D7;" @click="getLogList()">查询</el-button>
        </div>
      </div>
      <el-table :row-style="{height: '48px'}" :data="tableData" stripe :header-cell-style="{background:'#F0F2F5',color:'#333333'}">
        <el-table-column prop="username" label="用户"></el-table-column>
        <el-table-column prop="eventType" label="事件类型" show-overflow-tooltip></el-table-column>
        <el-table-column prop="resourceName" label="资源名称" show-overflow-tooltip></el-table-column>
        <el-table-column prop="eventTime" label="创建时间"></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button type="text" @click="detail(scope.row)" style="color: #4461D7;">详情</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        style="margin: 24px 0px; float: right;"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="offset"
        :page-sizes="[10, 20, 30, 40, 50]"
        :page-size="limit"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
    </el-card>
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
      </div>
    </el-drawer>
  </div>
</template>

<script>
import { getLogList } from '@/api/data'
export default {
  components: {
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
      tableData: []
    }
  },
  created() {
    this.getLogList()
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
.box-card {
  margin: 24px;
  .card-top {
    width: 100%;
    display: flex;
    margin-bottom: 24px;
    .input-box {
      width: 290px;
    }
    .input-left {
      margin-left: 40px;
    }
    .but-right {
      margin-left: auto;
    }
  }
}
</style>

