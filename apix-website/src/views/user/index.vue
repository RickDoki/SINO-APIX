<template>
  <div class="main">
    <div class="list_top">
      <div class="list_title">个人信息</div>
    </div>
    <el-tabs tab-position="left" class="main_content">
      <el-tab-pane label="基本信息">
        <div class="formBox">
          <el-form
            :model="userForm"
            :rules="user_rules"
            ref="userForm"
            label-width="150px"
            label-position="top"
            size="small"
          >
            <el-form-item label="手机号" prop="mobile">
              <el-input
                v-model="userForm.mobile"
                class="inputWidth"
                disabled
              />
            </el-form-item>
            <el-form-item label="用户名" prop="userName">
              <el-input
                v-model="userForm.userName"
                maxlength="20"
                class="inputWidth"
                show-word-limit
              />
            </el-form-item>
            <el-form-item label="邮箱" prop="email">
              <el-input
                v-model="userForm.email"
                class="inputWidth"
              />
            </el-form-item>
          </el-form>
          <el-button type="primary" size="small" @click="baseSure" class="save_but">保存</el-button>
        </div>
      </el-tab-pane>
      <el-tab-pane label="修改密码">
         <div class="formBox">
          <el-form
            :model="paswForm"
            :rules="pasw_rules"
            ref="paswForm"
            label-width="150px"
            label-position="top"
            size="small"
          >
            <el-form-item label="原密码" prop="oldPass">
              <el-input
                v-model="paswForm.oldPass"
                class="inputWidth"
              />
            </el-form-item>
            <el-form-item label="新密码" prop="newPass">
              <el-input
                v-model="paswForm.newPass"
                maxlength="20"
                class="inputWidth"
                show-word-limit
              />
            </el-form-item>
            <el-form-item label="确认密码" prop="surePass">
              <el-input
                v-model="paswForm.surePass"
                class="inputWidth"
              />
            </el-form-item>
          </el-form>
          <el-button type="primary" size="small" @click="changePass" class="save_but">修改</el-button>
        </div>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import { getuser, updateUser, updateorg, changePass } from "@/api/user";
import { getToken } from "@/utils/auth"; // get token from cookie

export default {
  data () {
    return {
      userId: "",
      paswForm: {
        oldPass: "",
        newPass: "",
        surePass: "",
      },
      pasw_rules: {
        oldPass: [
          { required: true, message: "请输入用户名", trigger: "change" },
          { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'change' }
        ],
        newPass: { required: true, message: "请输入手机号", trigger: "change" },
        surePass: [
          { required: true, message: "请输入邮箱", trigger: "change" },
          { type: "email", message: "请输入正确的邮箱地址", trigger: ["blur", "change"] },
        ]
      },
      userForm: {
        mobile: "",
        password: "",
        email: "",
        username: "",
      },
      user_rules: {
        username: [
          { required: true, message: "请输入用户名", trigger: "change" },
          { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'change' }
        ],
        mobile: { required: true, message: "请输入手机号", trigger: "change" },
        email: [
          { required: true, message: "请输入邮箱", trigger: "change" },
          { type: "email", message: "请输入正确的邮箱地址", trigger: ["blur", "change"] },
        ]
      },
    };
  },
  created () {
    // getToken('userId')
    const userId = getToken("userId_api");
    this.userId = userId;
    getuser(userId).then((res) => {
      console.log(res);
      this.userName = res.data.userName;
      this.email = res.data.email;
      this.mobile = res.data.mobile;
    });
  },
  methods: {
    baseSure () {
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
        this.userName = res.data.userName;
        this.email = res.data.email;
        this.mobile = res.data.mobile;
      });
    },
    changePass () {
      if (this.newPass === this.surePass) {
        const data = {
          oldPwd: btoa(this.oldPass),
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
.main {
  .formBox {
    margin: 0px 30px;
    .save_but {
      margin-top: 20px;
    }
  }
  .main_content {
    height: 680px;
    padding: 24px 0px;
    /deep/ .el-tabs__item {
      padding-left: 0px;
    }
  }
}
</style>