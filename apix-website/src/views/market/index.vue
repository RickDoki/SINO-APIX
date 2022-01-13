<template>
  <div class="app_list_main">
    <div>
      <el-row :gutter="20">
        <el-col :span="6">
          <el-input v-model="appName" placeholder="请输入应用名" />
        </el-col>
        <el-col :span="8"
          ><el-button @click="search" type="primary" icon="el-icon-search"
            >搜索</el-button
          ></el-col
        >
      </el-row>
    </div>
    <div class="apps-list">
      <el-row :gutter="50">
        <el-col
          v-for="(item, index) in appList"
          :key="index"
          :span="4"
          style="margin-top: 20px"
          @click="getDetail(item)"
        >
          <div class="icon-box" @click="getDetail(item)">
            <img class="app-img" src="./../../assets/img/icon9.png" alt="" />
          </div>
          <div class="app-text">
            {{ item.appName }}
          </div>
        </el-col>
        <el-col v-if="appList.length===0">
          <div class="NODATA" style="text-align:center">暂无数据</div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import { list } from "@/api/AboutApp";

export default {
  data() {
    return {
      appName: "",
      appList: "",
    };
  },
  created() {
    const query = "?market=true";
    list(query).then((res) => {
      // console.log(res)
      this.appList = res.data.appList;
    });
  },
  methods: {
    getDetail(e) {
      // console.log(e)

      this.$router.push({ path: "/market/detail?appCode=" + e.appCode });
    },
    search() {
      const query = "?market=true&appName=" + this.appName;
      list(query).then((res) => {
        this.appList = res.data.appList;
      });
    },
  },
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
  padding: 20px;
  .apps-list {
    margin: 20px 0px;
    .icon-box {
      text-align: center;
      border-radius: 4px;
      border: 1px solid #e6ebf5;
      box-shadow: 0px 2px 12px 0px #ccc;
      cursor: pointer;
      padding: 5px;
      .app-img {
        width: 70%;
        height: 70%;
      }
    }
    .app-text {
      text-align: center;
      background-color: #eee;
      font-size: 14px;
      color: #555;
    }
  }
}
.NODATA {
  font-size: 18px;
  color: #666;
}
</style>
