<template>
  <div class="login-main">
    <div class="main-top">
      <router-link key="collapse" class="sidebar-logo-link" to="/openServe">
        <img src="./../../assets/img/sdx_logo.png" alt=""/>
      </router-link>
    </div>
    <div class="main-center">
      <div class="center-logo">
        <img src="./../../assets/img/img_logo.png" alt=""/>
      </div>
      <div class="center-welcome">欢迎使用博冀开放服务平台</div>
      <div class="center-form">
        <div v-show="isLogin">
          <el-form :model="ruleForm" :rules="rules" ref="ruleForm">
            <el-form-item prop="username">
              <el-input placeholder="请输入手机号" v-model="ruleForm.username"/>
            </el-form-item>
            <el-form-item prop="password" class="paws-input">
              <el-input show-password placeholder="请输入密码" v-model="ruleForm.password"/>
            </el-form-item>
          </el-form>
          <div class="right_handle">
            <div class="handle_left">
              <el-checkbox v-model="checked">记住密码</el-checkbox>
            </div>
            <div class="handle_right">
              <span v-show="false">忘记密码 ?</span>
            </div>
          </div>
          <el-button type="primary" size="small" class="dl-but" @click="login('ruleForm')">立即登录</el-button>
          <div class="handle_bom">
            <span @click="goOther()">没有账号？<span class="goreg-col">去注册</span></span>
          </div>
        </div>
        <div v-show="!isLogin">
          <el-form :model="re_ruleForm" :rules="re_rules" ref="re_ruleForm">
            <el-form-item prop="username">
              <el-input placeholder="请输入用户名" v-model="re_ruleForm.username"></el-input>
            </el-form-item>
            <el-form-item prop="mobile">
              <el-input placeholder="请输入手机号" v-model="re_ruleForm.mobile"></el-input>
            </el-form-item>
            <el-form-item prop="email">
              <el-input placeholder="请输入邮箱" v-model="re_ruleForm.email"></el-input>
            </el-form-item>
            <el-form-item prop="password">
              <el-input show-password placeholder="请输入密码" v-model="re_ruleForm.password"></el-input>
            </el-form-item>
          </el-form>
          <el-button type="primary" size="small" class="zc-but" @click="register('re_ruleForm')">立即注册</el-button>
          <div class="handle_bom">
            <span @click="goOther()">已有账号？<span class="goreg-col">去登录</span></span>
          </div>
        </div>
      </div>
    </div>
    <div class="main-bottom">
      <span>Copyright © {{ year }} 上海博冀信息科技有限公司</span>
    </div>
  </div>
</template>

<script>
import { setToken, getToken, removeToken } from "@/utils/auth"; // get token from cookie
import { Apiregister, Apilogin } from "@/api/user";

export default {
  data () {
    return {
      year: '',
      ruleForm: {
        username: "",
        password: "",
      },
      rules: {
        username: { required: true, message: "请输入手机号", trigger: "blur" },
        password: { required: true, message: "请输入密码", trigger: "blur" },
      },
      re_ruleForm: {
        mobile: "",
        password: "",
        email: "",
        username: "",
      },
      re_rules: {
        username: [
          { required: true, message: "请输入用户名", trigger: "blur" },
          { min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'change' }
        ],
        mobile: { required: true, message: "请输入手机号", trigger: "blur" },
        email: [
          { required: true, message: "请输入邮箱", trigger: "blur" },
          { type: "email", message: "请输入正确的邮箱地址", trigger: "change" },
        ],
        password: { required: true, message: "请输入密码", trigger: "blur" },
      },
      checked: false,
      isLogin: true,
    };
  },
  computed: {},
  methods: {
    // 去注册
    // 已有账号去登录
    goOther () {
      this.isLogin = !this.isLogin;
    },
    // 注册
    register (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          console.log(this.re_ruleForm);
          const btoaform = {
            mobile: this.re_ruleForm.mobile,
            password: btoa(this.re_ruleForm.password),
            email: this.re_ruleForm.email,
            username: this.re_ruleForm.username
          }
          console.log(btoaform)
          Apiregister(btoaform).then((res) => {
            console.log(res);
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
          return false;
        }
      });
    },
    // 登录
    login (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.checked) {
            setToken("name", this.ruleForm.username);
            setToken("word", btoa(this.ruleForm.password));
            setToken("ischecked", true);
          } else {
            setToken("ischecked", false);
          }
          const data = {
            username: this.ruleForm.username,
            password: btoa(this.ruleForm.password)
          }
          Apilogin(data).then((res) => {
            console.log(res);
            if (res.code === 200) {
              setToken("token", res.data.token);
              setToken("userId_api", res.data.userId);
              setToken("apiPhone", res.data.mobile);
              setToken("userName_api", res.data.username);
              if (this.$route.query.path && this.$route.query.code) {
                this.$router.push({
                  path: this.$route.query.path,
                  query: {
                    code: this.$route.query.code
                  }
                });
              } else if (this.$route.query.path) {
                this.$router.push(this.$route.query.path);
              } else {
                this.$router.push("/dashboard/index");
              }
            }
          });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
  },
  created () {
    removeToken("apiPhone");
    removeToken("token");
    removeToken("userId_api");
    removeToken("userName_api");
    // 回车键触发
    const that = this
    document.onkeydown = function(e){
      var key = window.event.keyCode;
      // 小键盘和大键盘
      if(key == 13 || key == 100){
        if (that.isLogin) {
          that.login('ruleForm'); //自己写的登录方法，点击事件
        } else {
          // 注册
          that.register('re_ruleForm')
        }
      }
    }
    this.year = (new Date()).getFullYear()
    const ischecked = getToken("ischecked");
    if (ischecked === "true") {
      this.ruleForm = {
        username: getToken("name"),
        password: atob(getToken("word")),
      };
      this.checked = true;
    } else {
    }
    if (this.$route.fullPath.indexOf('mobile') != -1) {
      const query = decodeURIComponent(this.$route.fullPath.replace('/login?mobile=', ''))
      console.log(query)
      this.isLogin = false
      this.re_ruleForm.mobile = query
    }
    // console.log("初始化");
  },
};
</script>

<style lang="scss" scoped>
.login-main {
  .main-top {
    margin: 17px 0px 0px 40px;
  }

  .main-center {
    text-align: center;
    height: 53%;
    width: 24%;
    position: absolute;
    left: 0;
    right: 0;
    bottom: 30%;
    margin: auto;

    .center-welcome {
      margin: 18px 0px 24px 0px;
      font-size: 24px;
      font-family: Microsoft YaHei UI-Regular, Microsoft YaHei UI;
      font-weight: 400;
      color: #000000;
    }

    .center-form {
      .paws-input {
        margin: 30px 0px 14px 0px;
      }

      .right_handle {
        margin: 0 auto;

        .handle_left {
          width: 50%;
          display: inline-block;
          text-align: left;
        }

        .handle_right {
          width: 50%;
          display: inline-block;
          text-align: right;

          span {
            font-size: 14px;
            cursor: pointer;
          }

          span:hover {
            color: #2650ff;
          }
        }
      }

      .dl-but {
        margin-top: 30px;
        width: 100%;
        box-shadow: -2px 10px 10px #e2e7fe;
      }

      .zc-but {
        margin-top: 8px;
        width: 100%;
        box-shadow: 0px 15px 10px #e2e7fe;
      }

      .handle_bom {
        margin-top: 20px;
        .goreg-col {
          color: #2650ff;
          cursor: pointer;
        }
      }
    }
  }

  .main-bottom {
    text-align: center;
    font-size: 12px;
    width: 100%;
    position: absolute;
    bottom: 0px;
    line-height: 50px;
    height: 70px;
    background: url('./../../assets/img/img_login_bg.png') no-repeat fixed;
  }
}
</style>
