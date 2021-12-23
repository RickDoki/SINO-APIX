<template>
  <div class="app_list_main">
    <div class="table_box">
      <el-table :data="table" empty-text="暂无数据" stripe style="width: 100%">
        <el-table-column align="center" prop="appName" label="应用名称" />
        <el-table-column align="center" prop="appCode" label="APPCode" />
        <!-- <el-table-column prop="creationByUsername" label="创建人" />
        <el-table-column prop="description" label="描述" /> -->
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="primary"
              @click="detial(scope.$index, scope.row)"
              >详情</el-button
            >
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
  </div>
</template>

<script>
import { list, detail} from '@/api/subscribed.js'
import { getToken } from '@/utils/auth' // get token from cookie

export default {
  data() {
    return {
      table: [
      ],
      total:100,
      currentPage:1
    };
  },
  created(){
    this.getList(1)
  },
  methods:{
    detial(index, e) {
      this.$router.push({path:'/app/subscribeDetail?appCode='+ e.appCode})
    },
    getList(e) {
      const developerId = getToken('userId')
      const msg = 'limit=10&offset=' + e + '&developerId=' + developerId
      list(msg).then(res=>{
        // console.log(res)
        this.table = res.data.appList
        this.total = res.data.total
      })
    },
    handleCurrentChange() {

    }
  }
};
</script>

<style lang='scss' scoped>
.app_list_main {
  width: 95%;
  margin: 0 auto;
  margin-top: 20px;
  border-radius: 5px;
  background-color: #fff;
  overflow: hidden;
  .table_box {
    padding: 30px;
  }
}
</style>
