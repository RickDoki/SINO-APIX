<template>
  <div class="user">
    <el-tabs tab-position="left" style="height: 100%">
      <el-tab-pane label="基本信息">
        <div class="content">
          <div class="title">
            <p>基本信息</p>
          </div>
          <div class="flexDiv">
            <div class="left">组织名称:</div>
            <div class="right">
              <el-input v-model="orgName" disabled size="mini"></el-input>
            </div>
          </div>
          <div class="flexDiv">
            <div class="left">手机号:</div>
            <div class="right">
              <el-input v-model="mobile" disabled size="mini"></el-input>
            </div>
          </div>
          <div class="flexDiv">
            <div class="left">邮箱:</div>
            <div class="right">
              <el-input v-model="email" size="mini"></el-input>
            </div>
          </div>
          <div class="flexDiv">
            <div @click="baseSure" class="sure">确认</div>
          </div>
        </div>
      </el-tab-pane>
      <el-tab-pane label="修改密码">
        <div class="content">
          <div class="title">
            <p>修改密码</p>
          </div>
          <div class="flexDiv">
            <div class="left">原密码:</div>
            <div class="right">
              <el-input v-model="yuanPass" size="mini"></el-input>
            </div>
          </div>
          <div class="flexDiv">
            <div class="left">新密码:</div>
            <div class="right">
              <el-input v-model="newPass" size="mini"></el-input>
            </div>
          </div>
          <div class="flexDiv">
            <div class="left">确认新密码:</div>
            <div class="right">
              <el-input v-model="surePass" size="mini"></el-input>
            </div>
          </div>
          <div class="flexDiv">
            <div class="sure" @click="changePass">确认</div>
          </div>
        </div>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import { getuser, updateUser, updateorg, changePass } from "@/api/user";
import { getToken } from "@/utils/auth"; // get token from cookie

export default {
  data() {
    return {
      name: "",
      orgName: "",
      mobile: "",
      email: "",
      userId: "",
      yuanPass: "",
      newPass: "",
      surePass: "",
    };
  },
  created() {
    // getToken('userId')
    const userId = getToken("userId_api");
    this.userId = userId;
    getuser(userId).then((res) => {
      console.log(res);
      this.orgName = res.data.orgName;
      this.email = res.data.email;
      this.mobile = res.data.mobile;
    });
  },
  methods: {
    baseSure() {
      const data = {
        email: this.email,
        // mobile: this.mobile,
      };
      updateUser(this.userId, data).then((res) => {
        // console.log(res)
        if (res.code === 200) {
          this.isLogin = true;
          this.$message({
            message: res.msg,
            type: "success",
          });
        } else {
          this.$message({
            message: res.msg,
            type: "error",
          });
        }
      });
      getuser(this.userId).then((res) => {
        this.orgName = res.data.orgName;
        this.email = res.data.email;
        this.mobile = res.data.mobile;
      });
      // const orgData = {
      //   name: this.orgName,
      // };
      // updateorg(this.userId, orgData).then((res) => {
      //   if (res.code === 200) {
      //     this.isLogin = true;
      //     this.$message({
      //       message: res.msg,
      //       type: "success",
      //     });
      //   } else {
      //     this.$message({
      //       message: res.msg,
      //       type: "error",
      //     });
      //   }
      // });
    },
    changePass() {
      if (this.newPass === this.surePass) {
        const data = {
          oldPwd: btoa(this.yuanPass),
          newPwd: btoa(this.newPass),
        };
        changePass(data).then((res) => {
          // console.log(res)
          if (res.code === 200) {
            this.isLogin = true;
            this.$message({
              message: res.msg,
              type: "success",
            });
          } else {
            this.$message({
              message: res.msg,
              type: "error",
            });
          }
        });
      } else {
        this.$message({
          message: "两次密码输入不一致,请核对后再提交",
          type: "error",
        });
      }
    },
  },
};
</script>

<style lang='scss' scoped>
.user {
  margin: 10px;
  background-color: #fff;
  min-height: calc(100vh - 185px);
  padding: 24px;
  .content {
    // height: 100px;
    padding-left: 24px;
    .title {
    }
    .flexDiv {
      display: flex;
      vertical-align: middle;
      margin-top: 20px;
      .left {
        width: 100px;
        vertical-align: middle;
        line-height: 28px;
        font-size: 14px;
      }
      .right {
        width: 300px;
        vertical-align: middle;
      }
      .sure {
        width: 65px;
        height: 32px;
        background-color: #2c66fb;
        color: #fff;
        line-height: 32px;
        border-radius: 2px;
        text-align: center;
        font-size: 14px;
        margin-left: 100px;
        cursor: pointer;
      }
    }
  }
}
</style>