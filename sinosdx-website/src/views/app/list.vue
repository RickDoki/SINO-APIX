<template>
  <div class="app_list_main">
    <el-row class="search" :gutter="20">
      <el-col :span="4">
        <el-input v-model="APIName" size="mini" placeholder="请输入API名称" />
      </el-col>
      <el-col :span="10">
        <el-date-picker
          v-model="value1"
          type="datetimerange"
          size="mini"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          value-format="timestamp"
          :default-time="['00:00:00', '23:59:59']"
        />
      </el-col>
      <el-col :span="10">
        <el-button size="mini" @click="reset">重置</el-button>
        <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="search"
          >搜索</el-button
        >
        <!-- <el-button
          type="primary"
          @click="goCreate"
        >创建应用</el-button> -->
      </el-col>
    </el-row>
    <el-divider></el-divider>
    <el-row class="search" :gutter="20">
      <el-col :span="4">
        <el-button
          v-if="ischuangjianShow"
          type="primary"
          size="mini"
          icon="el-icon-plus"
          @click="goCreate"
          >创建</el-button
        >
      </el-col>
    </el-row>
    <table-list
      :total="total"
      :table="table"
      @refresh="refresh"
      @pageChange="pageChange"
      @sizeChange="sizeChange"
    />
  </div>
</template>

<script>
import tableList from "@/components/TableList/app.vue";
import { list } from "@/api/AboutApp";
import { getToken } from "@/utils/auth"; // get token from cookie

export default {
  components: {
    tableList,
  },
  data() {
    return {
      value1: [],
      APIName: "",
      total: 0,
      table: [],
      developerId: "",
      page: 1,
      size: 10,
      ischuangjianShow: false
    };
  },
  created() {
    this.developerId = getToken("userId_api");
    const query = "?limit=10&offset=1&developerId=" + this.developerId;
    this.getapplist(query);
    const name = this.$router.currentRoute.meta.title;
    console.log(name);
    const buttonList = JSON.parse(sessionStorage.getItem("buttonList"));
    console.log(buttonList);
    for (let index = 0; index < buttonList.length; index++) {
      if (buttonList[index].name === name) {
        // console.log(buttonList[index].list)
        for (let index1 = 0; index1 < buttonList[index].list.length; index1++) {
          if (buttonList[index].list[index1].name === "创建") {
            this.ischuangjianShow = true;
          }
        }
      }
    }
  },
  methods: {
    search() {
      if (this.value1) {
        if (this.value1.length === 0) {
          const query =
            "?limit=10&offset=1&appName=" +
            this.APIName +
            "&developerId=" +
            this.developerId;
          this.getapplist(query);
        } else {
          const query =
            "?limit=10&offset=1&appName=" +
            this.APIName +
            "&startTime=" +
            this.value1[0] +
            "&endTime=" +
            this.value1[1] +
            "&developerId=" +
            this.developerId;
          this.getapplist(query);
        }
      } else {
        const query =
          "?limit=10&offset=1&appName=" +
          this.APIName +
          "&developerId=" +
          this.developerId;
        this.getapplist(query);
      }
    },
    pageChange(val) {
      this.page = val;
      const query =
        "?limit=" +
        this.size +
        "&offset=" +
        this.page +
        "&developerId=" +
        this.developerId;
      this.getapplist(query);
    },
    sizeChange(val) {
      this.size = val;
      const query =
        "?limit=" +
        this.size +
        "&offset=" +
        this.page +
        "&developerId=" +
        this.developerId;
      this.getapplist(query);
    },
    getapplist(query) {
      list(query).then((res) => {
        if (res.code === 200) {
          this.table = res.data.appList;
          this.total = res.data.total;
        } else {
          this.$message({
            message: res.msg,
            type: "error",
          });
        }
      });
    },
    refresh() {
      const query = "?limit=10&offset=1" + "&developerId=" + this.developerId;
      this.getapplist(query);
    },
    // 去创建
    goCreate() {
      this.$router.push({ path: "/app/add" });
    },
    // 重置查询条件
    reset() {
      this.APIName = "";
      this.value1 = "";
    },
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
  .search {
    margin-top: 20px;
    padding: 0px 30px;
  }
}
</style>
