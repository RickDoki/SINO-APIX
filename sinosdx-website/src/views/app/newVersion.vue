<template>
  <div class="newVersion">
    <div class="version_main">
      <div class="version_code">
        <div class="left">
          <span class="xh">*</span>
          <span class="font14">新版本号:</span>
        </div>
        <div class="right">
          <el-input v-model="code"></el-input>
        </div>
      </div>
      <div class="version_des">
        <div class="left">
          <span class="font14">新版本描述:</span>
        </div>
        <div class="right">
          <el-input
            v-model="description"
            type="textarea"
            :autosize="{ minRows: 3, maxRows: 5 }"
            maxlength="500"
            show-word-limit
          />
        </div>
      </div>
      <div class="version_chose" style="font-size: 16px">选择api版本</div>
      <div style="margin-bottom:30px">
        <div class="ver_radio" v-for="(item, index) in apiList" :key="index">
          <div class="radio_left">{{ item.apiName }}</div>
          <div class="radio_right">
            <el-radio-group v-model="radio[index]">
              <el-radio
                v-for="item1 in item.apiVersions"
                :key="item1.versionId"
                :label="item1.versionId"
                >{{ item1.apiVersion }}</el-radio
              >
            </el-radio-group>
          </div>
        </div>
      </div>
    </div>
    <div class="bottom">
      <div class="bottom_button">
        <div class="newversion">取消</div>
        <div class="user" @click="newVSure">提交</div>
      </div>
    </div>
  </div>
</template>

<script>
import { apiList, publish } from "@/api/AboutApp";
import { getToken } from "@/utils/auth"; // get token from cookie

export default {
  data() {
    return {
      developerId: "",
      code: "",
      description: "",
      radio: [],
      apiList: [],
      appCode:''
    };
  },
  created() {
    const developerId = getToken("userId_api");
    this.developerId = developerId;
    this.appCode = this.$route.query.appcode;
    this.getAPIlist();
  },
  methods: {
    clickitem(e,i) {
      console.log(e,i)
      e === this.radio[i] ? this.radio[i] = "" : this.radio[i] = e
      console.log(this.radio)

    },
    getAPIlist(page) {
      // 获取api版本列表
      const query = "developerId=" + this.developerId;
      apiList(query).then((res) => {
        this.apiList = res.data.apiList;
        // this.apiList = res.data.apiList;
        // this.total = res.data.total;
        for (let index = 0; index < this.apiList.length; index++) {
          this.radio.push("");
        }
      });
    },
    // 提交
    newVSure() {
      const newlist = [];
      for (let index = 0; index < this.radio.length; index++) {
        console.log(this.radio[index] !== "")
        if (this.radio[index] !== "") {
          newlist.push(this.radio[index]);
        }
      }
      console.log(this.radio)
      console.log(newlist)
      const listString = newlist.toString();
      if (listString === "") {
        this.messageERROR("至少需要选择一个api版本");
        return false;
      }
      if (this.code === "") {
        this.messageERROR("新版本号为必填项");
        return false;
      }
      const appVForm = {
        appVersion: this.code,
        versionDesc: this.description,
        apiVersionIds: listString,
      }
      // this.appVForm.apiVersionIds = listString;
      publish(this.appCode, appVForm).then((res) => {
        if (res.code === 200) {
          this.messageOK(res.msg);
          this.$router.push({path:"/app/detail?appCode=" + this.appCode});
        } else {
          this.messageERROR(res.msg);
        }
        // this.appVDialogVisible = false;
        // this.getMessageList(this.appCode, "5");
      });
      // console.log(this.appVForm)
      // if(this.appVForm.)
      // publish(this.appCode, )
    },
    messageERROR(msg) {
      this.$message({
        message: msg,
        type: "error",
      });
    },
    messageOK(msg) {
      this.$message({
        message: msg,
        type: "success",
      });
    },
  },
};
</script>

<style lang='scss' scoped>
.font14 {
  font-size: 14px;
}
.newVersion {
  padding: 10px;
  width: 100%;
  // background-color: #fff;
  .version_main {
    background-color: #fff;
    width: 100%;
    // padding: 20px;
    .version_code {
      display: flex;
      padding: 20px 20px;
      .left {
        width: 15%;
        .xh {
          color: red;
          vertical-align: middle;
        }
        span {
          line-height: 36px;
        }
      }
      .right {
        width: 80%;
      }
    }
    .version_des {
      display: flex;
      padding: 0px 20px;
      .left {
        width: 15%;
        .xh {
          color: red;
          vertical-align: middle;
        }
        span {
          line-height: 36px;
        }
      }
      .right {
        width: 80%;
      }
    }
    .version_chose {
      padding: 20px;
    }
    .ver_radio {
      display: flex;
      padding: 20px;
      .radio_left {
        width: 200px;
        font-size: 14px;
      }
      .radio_right {
      }
    }
  }
}
.bottom {
  position: fixed;
  width: calc(100% - 210px);
  height: 50px;
  background-color: #fff;
  bottom: 0px;
  right: 0px;
  .bottom_button {
    float: right;
    margin-right: 50px;
    div {
      display: inline-block;
      padding: 5px 8px;
      margin-right: 20px;
      cursor: pointer;
    }
    .newversion {
      background-color: #fff;
      color: #666666;
      border: 1px solid #d9d9d9;
    }
    .user {
      background-color: #2c66fb;
      color: #fff;
      border: 1px solid #2c66fb;
    }
    .del {
      border: 1px solid #f5222d;
      color: #f5222d;
    }
  }
}
</style>