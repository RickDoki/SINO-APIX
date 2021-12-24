<template>
  <div class="detail_main">
    <div class="apiMain_list">
      <div class="list_items">
        <div>
          <img src="./../../assets/img/door-p.png" alt="" />
        </div>
        <div class="items_message">
          <div class="one">
            <span>API{{ 1 }}</span>
          </div>
          <div class="two">
            <span
              >在中台产品的研发过程中，会出现不同的设计规范和实现方式，会出现不同的设计规范和实现方式...</span
            >
          </div>
          <div class="three">
            <span>操作系统: windows</span>
            <span>交付方式: 人工服务</span>
            <span>发布时间: 2021-09-08</span>
          </div>
          <div class="four">
            <span>服务商: 上汽集团</span>
          </div>
          <div class="five">
            <span>人工智能服务</span>
            <span> api</span>
          </div>
        </div>
        <div class="price">
          <span>¥00.00</span>
          <i>/年</i>
        </div>
      </div>
    </div>
    <div class="main_tables">
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane name="first">
          <span slot="label"><i class="iconfont icon-detail"></i>接口详情</span>
          <apidetail></apidetail>
        </el-tab-pane>
        <!-- <el-tab-pane name="second">
          <span slot="label"
            ><i class="iconfont icon-xiuding fontSize"></i>接口测试</span
          >
          <api-test></api-test>
        </el-tab-pane> -->
        <el-tab-pane name="third">
          <span slot="label"
            ><i class="iconfont icon-wenjian1 fontSize"></i>产品说明</span
          >
          <div class="shuoming">
            <img src="./../../assets/img/picture.png" alt="" />
            <p>暂无说明</p>
          </div></el-tab-pane
        >
        <!-- <el-tab-pane name="fourth">
          <span slot="label"
            ><i class="iconfont icon-wenjian fontSize"></i>接口文档</span
          >
          接口文档</el-tab-pane
        >
        <el-tab-pane name="fivth">
          <span slot="label"
            ><i class="iconfont icon-pinglun fontSize"></i>评论</span
          >
          评论</el-tab-pane
        > -->
        <el-tab-pane name="sixth">
          <span slot="label"
            ><i class="iconfont icon-wenhao fontSize"></i>帮助文档</span
          >
          <help></help>
        </el-tab-pane>
      </el-tabs>
    </div>
    <div class="bottom">
      <div class="left">
        <el-select
          v-model="value"
          style="width: 200px"
          size="mini"
          placeholder="请选择应用"
        >
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </div>
      <div class="price">
        <span>¥00.00</span>
        <i>/年</i>
        <div @click="leaseSure" class="dingyue">订阅</div>
      </div>
    </div>
  </div>
</template>

<script>
import "@/assets/icons/apiIcon/iconfont.css";
import apidetail from "./detail/detail.vue";
import apiTest from "./detail/test.vue";
import help from "./detail/help.vue";
import { detail, list, AppLease } from "@/api/AboutApp";
import { getToken } from "@/utils/auth"; // get token from cookie

export default {
  components: {
    apidetail,
    apiTest,
    help,
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
    this.appMessage = JSON.parse(this.$route.query.message);
    this.getUserapp();
    console.log(this.appMessage);
    this.getappMessage();
  },
};
</script>

<style lang='scss' scoped>
.detail_main {
  padding: 24px;
  .apiMain_list {
    background-color: #fff;
    padding: 24px;
    cursor: pointer;
    .list_items {
      height: 130px;
      display: flex;
      .items_message {
        width: 70%;
        margin-left: 24px;
        font-size: 12px;
        div {
          padding: 5px 0px;
        }
        .one {
          color: #000;
        }
        .two {
          color: #666666;
        }
        .three {
          color: #999999;
          span {
            display: inline-block;
            width: 30%;
          }
        }
        .four {
          color: #999999;
        }
        .five {
          span {
            display: inline-block;
            padding: 3px;
            margin-right: 5px;
            color: #4461D7;
            background: rgba(44, 102, 251, 0.1);
          }
        }
      }
      .price {
        margin-top: 23px;
        span {
          color: #f6323c;
          font-size: 16px;
        }
        i {
          font-style: normal;
          font-size: 12px;
          color: #999999;
        }
      }
    }
  }
  .main_tables {
    background-color: #fff;
    margin-top: 24px;
    padding: 24px;
    margin-bottom: 50px;
  }
  .fontSize {
    font-size: 14px;
    margin-left: 5px;
    padding-right: 8px;
  }
  .shuoming {
    height: 300px;
    text-align: center;
    p {
      text-align: center;
      font-size: 12px;
      color: #ccc;
    }
  }
  .bottom {
    position: fixed;
    width: calc(100% - 210px);
    height: 50px;
    background-color: #fff;
    bottom: 0px;
    right: 0px;
    display: flex;
    justify-content: space-between;
    div {
      width: 30%;
    }
    .left {
      margin-top: 10px;
    }
    .price {
      margin-top: 15px;
      vertical-align: middle;
      span {
        color: #f6323c;
        font-size: 16px;
        vertical-align: middle;
      }
      i {
        font-style: normal;
        font-size: 12px;
        color: #999999;
        vertical-align: middle;
      }
      .dingyue {
        display: inline-block;
        width: 70px;
        text-align: center;
        line-height: 30px;
        height: 30px;
        vertical-align: middle;
        background-color: #4461D7;
        color: #fff;
        margin-left: 30px;
      }
    }
  }
}
</style>