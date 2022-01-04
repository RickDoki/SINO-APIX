<template>
  <div class="api_list_main">
    <el-row class="search" :gutter="20">
      <el-col :span="4">
        <el-input v-model="APIName" size="mini" placeholder="请输入API名称" />
      </el-col>
      <el-col :span="10">
        <el-date-picker
          v-model="value1"
          type="datetimerange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          size="mini"
          value-format="timestamp"
          :default-time="['00:00:00', '23:59:59']"
        />
      </el-col>
      <el-col :offset="4" :span="6">
        <el-button size="mini" @click="reset">重置</el-button>
        <el-button type="primary" size="mini" @click="search">查询</el-button>
      </el-col>
    </el-row>
    <el-divider></el-divider>
    <el-row class="search" :gutter="20">
      <el-col :span="4">
        <el-button
          type="primary"
          size="mini"
          icon="el-icon-plus"
          @click="goApicreate"
          >创建</el-button
        >
      </el-col>
    </el-row>
    <table-list
      :total="total"
      :data="tableList"
      @refresh="refresh"
      @pageChange="pageChange"
      @sizeChange="sizeChange"
    />
  </div>
</template>

<script>
import tableList from "@/components/TableList";
import { list } from "@/api/AboutApi";
import { getToken } from "@/utils/auth"; // get token from cookie

export default {
  components: {
    tableList,
  },
  data () {
    return {
      APIName: "",
      value1: [],
      tableList: [],
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
          this.tableList = res.data.apiList;
          console.log(this.tableList)
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
    pageChange (val) {
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
