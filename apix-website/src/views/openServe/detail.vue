<template>
  <div class="main">
    <div>
      <div class="list_top">
        <div>
          <div class="list_top_title">服务名称</div>
          <div class="introduction">这是一段服务描述这是一段服务描述这是一段服务描述这是一段服务描述这是一段服务描述</div>
        </div>
        <div class="">
          <el-button type="primary" size="small" style="width: 100px">订阅</el-button>
        </div>
      </div>
      <div class="list_top2">
        <div style="display: flex">
          <div class="service_providers">服务商：博冀科技</div>
          <div class="service_providers">发布时间：2021-10-05 08:05:00</div>
          <div class="service_providers" style="display: flex">
            已添加的插件：
            <div class="plug-in" style="display: flex">
              <el-tooltip class="item" effect="light" content="Left Bottom 提示文字" placement="bottom-start">
                <div class="chajian_qian"></div>
              </el-tooltip>
              <div>jwt-auth</div>
            </div>
          </div>
        </div>
        <div class="release_time">发布时间： 2021-08-05 10:05:00</div>
      </div>
      <div style="margin-top: 20px">
        <api-detail></api-detail>
      </div>
    </div>
  </div>
</template>

<script>
// import apidetail from "./detail/detail.vue";
// import apiTest from "./detail/test.vue";
// import help from "./detail/help.vue";
import {detail, list, AppLease} from "@/api/AboutApp";
import {getToken} from "@/utils/auth"; // get token from cookie
import apiDetail from './component/apiDetail'

export default {
  components: {
    apiDetail
    // apidetail,
    // apiTest,
    // help,
  },
  data() {
    return {
      activeName: "first",
      appMessage: "",
      options: [],
      value: "",
      appCode: "",
    };
  },
  methods: {
    handleClick(tab, event) {
      console.log(tab, event);
    },
    getappMessage() {
      detail(this.appMessage.appCode, "").then((res) => {
        console.log(res);
      });
    },
    getUserapp() {
      const developerId = getToken("userId");
      const query = "?developerId=" + developerId;
      this.options = [];
      list(query).then((res) => {
        if (res.code === 200) {
          res.data.appList.forEach((item) => {
            if (item.isPublished === "60001") {
            } else {
              this.options.push({
                value: item.appCode,
                label: item.appName,
              });
            }
          });
        }
      });
    },
    leaseSure() {
      if (this.value === "") {
        this.messageERROR("请先选择应用");
        return false;
      } else {
        AppLease(this.value, this.appMessage.appCode, {}).then((res) => {
          if (res.code === 200) {
            this.messageOK(res.msg);
          } else {
            this.messageERROR(res.msg);
          }
        });
      }
    },
    // 成功消息
    messageOK(msg) {
      this.$message({
        message: msg,
        type: "success",
      });
    },
    // 失败消息
    messageERROR(msg) {
      this.$message({
        message: msg,
        type: "error",
      });
    },
  },
  created() {
    // this.appMessage = JSON.parse(this.$route.query.message);
    // this.getUserapp();
    // console.log(this.appMessage);
    // this.getappMessage();
  },
};
</script>

<style lang='scss' scoped>
.list_top {
  .list_top_title {
    height: 26px;
    font-size: 20px;
    font-family: Microsoft YaHei UI-Bold, Microsoft YaHei UI;
    font-weight: bold;
    color: #1D1C35;
    line-height: 26px;
  }

  .introduction {
    margin-top: 5px;
    height: 20px;
    font-size: 14px;
    font-family: Microsoft YaHei UI-Regular, Microsoft YaHei UI;
    font-weight: 400;
    color: #727491;
    line-height: 20px;
  }
}

.list_top2 {
  margin-top: 30px;
  display: flex;
  justify-content: space-between;

  .service_providers {
    height: 20px;
    font-size: 14px;
    font-family: Microsoft YaHei UI-Regular, Microsoft YaHei UI;
    font-weight: 400;
    color: #1D1C35;
    line-height: 20px;
    margin-right: 40px;

    .plug-in {
      margin-right: 10px;

      .chajian_qian {
        margin-right: 10px;
        width: 20px;
        height: 20px;
        background: #F1F1F1;
        border-radius: 0px 0px 0px 0px;
        opacity: 1;
      }
    }
  }

  .release_time {
    height: 20px;
    font-size: 12px;
    font-family: Microsoft YaHei UI-Regular, Microsoft YaHei UI;
    font-weight: 400;
    color: #727491;
    line-height: 20px;
  }
}
</style>
