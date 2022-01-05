<template>
  <div class="main box-card">
    <div class="titleFont" style="margin-bottom: 20px">审计日志</div>
    <div class="card-top">
      <div class="input-box">
        <span>用户名称：</span>
        <el-input size="small" v-model="search.username" style="width: 70%" placeholder="请输入用户名称查询"></el-input>
      </div>
      <div class="input-box input-left">
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
    </div>
    <div class="card-top">
      <div class="input-box">
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
      <div class="input-left">
        <el-button size="small" @click="resetSearch">重置</el-button>
        <el-button type="primary" size="small" style="background-color: #2650FF; border-color: #2650FF;"
                   @click="getLogList()">查询
        </el-button>
      </div>
    </div>
    <div class="table_box">
      <el-table :data="tableData" empty-text="暂无数据" stripe style="width: 100%">
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
        style="margin-top: 20px; text-align: center"
        @current-change="handleCurrentChange"
        :current-page="offset"
        :page-sizes="[100, 200, 300, 400]"
        :page-size="100"
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
          <codemirror :json='requestExample'></codemirror>
        </div>
      </div>
    </el-drawer>
  </div>
</template>

<script>
import {getLogList} from '@/api/data'
import "../../mainCss/index.scss";
import codemirror from "@/components/codemirror/index.vue";

export default {
  components: {
    codemirror
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
      margin-left: 10%;
    }
  }

  .table_box {
    padding: 30px;
    border: 1px solid #e1e6ee;
    border-radius: 10px;

    ::v-deep {
      .el-table,
      .el-table th.el-table__cell.is-leaf,
      .el-table td.el-table__cell {
        border: none;
      }

      .el-table::before {
        height: 0px;
      }
    }

    .handle {
      display: flex;

      .handle_middle {
        // width: 15px;
        margin: 0px 5px;
      }
    }
  }
}
</style>

