<template>
  <div class="main">
    <div class="list_top">
      <div class="list_title">个人信息</div>
    </div>
    <el-tabs tab-position="left" class="main_content" @tab-click="tabChange">
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
            <el-form-item label="用户名" prop="username">
              <el-input
                v-model="userForm.username"
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
                show-password
                v-model="paswForm.oldPass"
                class="inputWidth"
              />
            </el-form-item>
            <el-form-item label="新密码" prop="newPass">
              <el-input
                show-password
                v-model="paswForm.newPass"
                class="inputWidth"
              />
            </el-form-item>
            <el-form-item label="确认密码" prop="surePass">
              <el-input
                show-password
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
import { getToken, setToken } from "@/utils/auth"; // get token from cookie

export default {
  data () {
    const equalToPassword = (rule, value, callback) => {
      if (this.paswForm.newPass !== value) {
        callback(new Error("两次输入的密码不一致！"));
      } else {
        callback();
      }
    };
    const equalToOldPassword = (rule, value, callback) => {
      if (this.paswForm.oldPass === value) {
        callback(new Error("新密码不能与旧密码相同！"));
      } else {
        callback();
      }
    };
    return {
      mobile: "",
      userId: '',
      paswForm: {
        oldPass: "",
        newPass: "",
        surePass: "",
      },
      pasw_rules: {
        oldPass: [
          { required: true, message: "请输入原密码", trigger: "blur" }
        ],
        newPass: [
          { required: true, message: "新密码不能为空", trigger: "blur" },
          { min: 8, max: 12, message: "长度在 8 到 12个字符", trigger: "change" },
          { required: true, validator: equalToOldPassword, trigger: "blur" }
        ],
        surePass: [
          { required: true, message: "确认密码不能为空", trigger: "blur" },
          { required: true, validator: equalToPassword, trigger: "blur" }
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
          { required: true, message: "请输入用户名", trigger: "blur" },
          { min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'change' }
        ],
        mobile: { required: true, message: "请输入手机号", trigger: "blur" },
        email: [
          { required: true, message: "请输入邮箱", trigger: "blur" },
          { type: "email", message: "请输入正确的邮箱地址", trigger: "change" },
        ]
      },
    };
  },
  created () {
    this.mobile = getToken("apiPhone");
    this.userId = getToken("userId_api");
    this.getUserInfo()
  },
  methods: {
    getUserInfo () {
      getuser(this.mobile).then((res) => {
        console.log(res);
        this.userForm = res.data
      });
    },
    tabChange (tab, event) {
      // console.log(tab.label);
      if (tab.label === '基本信息') {
        this.getUserInfo()
      }
    },
    baseSure () {
      updateUser(this.userId, this.userForm).then((res) => {
        if (res.code === 200) {
          setToken("userName_api", this.userForm.username);
          this.$message('保存成功');
          // location.reload()
        }
      });
    },
    changePass () {
      const params = {
        oldPwd: btoa(this.paswForm.oldPass),
        newPwd: btoa(this.paswForm.newPass),
      };
      changePass(params).then((res) => {
        if (res.code === 200) {
          this.$message('修改成功');
        }
      });
    }
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