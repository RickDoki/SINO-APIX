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
            <el-link
            size="mini"
            :underline="false"
            style="margin-left: 15px"
            type="primary"
            v-if="ischuangjianShow"
             @click="detial(scope.$index, scope.row)"
            >详情</el-link
          >
            <!-- <el-button
              size="mini"
              type="primary"
              @click="detial(scope.$index, scope.row)"
              >详情</el-button
            > -->
            <!-- <el-button
              size="mini"
              type="danger"
              @click="del(scope.$index, scope.row)"
              >删除</el-button
            > -->
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
      style="float:right;margin: 20px"
      :current-page.sync="currentPage"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      @current-change="handleCurrentChange"
      @size-change="handleSizeChange"
      :page-sizes="[10, 20, 30, 40]"
      :page-size="size"
    />
    </div>
  </div>
</template>

<script>
import { list, detail, Apilease, getApplessor } from "@/api/subscribed.js";
import { getToken } from "@/utils/auth"; // get token from cookie

export default {
  data() {
    return {
      table: [],
      total: 10,
      currentPage: 1,
      size:10,
      ischuangjianShow:false
    };
  },
  created() {
    this.getList(this.currentPage);
    const name = this.$router.currentRoute.meta.title;
    console.log(name);
    const buttonList = JSON.parse(sessionStorage.getItem("buttonList"));
    console.log(buttonList);
    for (let index = 0; index < buttonList.length; index++) {
      if (buttonList[index].name === name) {
        // console.log(buttonList[index].list)
        for (let index1 = 0; index1 < buttonList[index].list.length; index1++) {
          if (buttonList[index].list[index1].name === "详情") {
            this.ischuangjianShow = true;
          }
        }
      }
    }
  },
  methods: {
    detial(index, e) {
      this.$router.push({ path: "/app/subscribeDetail?appCode=" + e.appCode });
    },
    del(index, e) {
      // console.log(e)
      // Apilease(28,'6543').then((res) => {});
      getApplessor('d90de077').then(res=>{
        console.log(res)
      })
    },
    getList() {
      const developerId = getToken("userId_api");
      const msg = "limit="+ this.size +"&offset=" + this.currentPage + "&developerId=" + developerId;
      list(msg).then((res) => {
        // console.log(res)
        this.table = res.data.appList;
        this.total = res.data.total;
      });
    },
    handleCurrentChange(val) {
      this.currentPage = val
      this.getList()
    },
    handleSizeChange(val) {
      this.size = val
      this.getList()
    }
  },
};
</script>

<style lang='scss' scoped>
.app_list_main {
  width: 98%;
  margin: 0 auto;
  margin-top: 10px;
  border-radius: 5px;
  background-color: #fff;
  overflow: hidden;
  min-height: calc(100vh - 185px);
  .table_box {
    padding: 30px;
  }
}
</style>
