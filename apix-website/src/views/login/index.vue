<template>
  <div class="login_main">
    <div class="login_left">
      <img class="left_bgc" src="./../../assets/img/login.png" alt="" />
      <div class="login_logo">
        <img src="./../../assets/img/APIlogo.png" alt="" />
      </div>
    </div>
    <div class="login_right">
      <div v-if="isLogin" class="right_main">
        <div class="right_title">
          <span>API管理平台</span>
        </div>
        <div class="login_error">
          <el-alert v-if="errorShow" :title="errorMSG" type="error" show-icon>
          </el-alert>
        </div>
        <div class="right_form">
          <el-form
            style="width: 45%; margin: 0 auto"
            :model="ruleForm"
            :rules="rules"
            ref="ruleForm"
          >
            <el-form-item prop="username">
              <el-input placeholder="请输入用户名" v-model="ruleForm.username">
                <i
                  slot="prefix"
                  style="color: #476fd3"
                  class="el-input__icon el-icon-user"
                ></i>
              </el-input>
            </el-form-item>
            <el-form-item prop="password">
              <el-input
                :type="type"
                placeholder="请输入密码"
                v-model="ruleForm.password"
              >
                <i
                  slot="prefix"
                  style="color: #476fd3"
                  class="el-input__icon el-icon-lock"
                ></i>
                <i
                  slot="suffix"
                  style="cursor: pointer"
                  @click="isShow"
                  :class="iconClass"
                ></i>
              </el-input>
            </el-form-item>
          </el-form>
        </div>
        <div class="right_handle">
          <div class="handle_left">
            <el-checkbox v-model="checked">记住密码</el-checkbox>
          </div>
          <div class="handle_right">
            <span @click="goRegister">注册账号</span>
          </div>
        </div>
        <div class="right_login">
          <div @click="login('ruleForm')">登录</div>
        </div>
        <div class="right_login_csp">
          <div @click="CSPLogin">CSP2.0用户登录</div>
        </div>
        <!-- <p>API管理平台</p> -->
      </div>
      <div v-if="!isLogin" class="register">
        <div class="register_title">
          <span>注册账号</span>
        </div>
        <div class="register_form">
          <el-form
            style="width: 45%; margin: 0 auto"
            :model="re_ruleForm"
            :rules="re_rules"
            ref="re_ruleForm"
          >
            <el-form-item prop="mobile">
              <el-input placeholder="请输入手机号" v-model="re_ruleForm.mobile">
                <i
                  slot="prefix"
                  style="color: #476fd3; font-size: 14px; margin-left: 6px"
                  class="iconfont icon-shouji"
                ></i>
              </el-input>
            </el-form-item>
            <el-form-item prop="email">
              <el-input placeholder="请输入邮箱" v-model="re_ruleForm.email">
                <i
                  slot="prefix"
                  style="color: #476fd3; font-size: 12px; margin-left: 6px"
                  class="iconfont icon-youjian"
                ></i>
              </el-input>
            </el-form-item>
            <el-form-item prop="password">
              <el-input
                :type="re_type"
                placeholder="请输入密码"
                v-model="re_ruleForm.password"
              >
                <i
                  slot="prefix"
                  style="color: #476fd3"
                  class="el-input__icon el-icon-lock"
                ></i>
                <i
                  slot="suffix"
                  style="cursor: pointer"
                  @click="isShow_re"
                  :class="re_iconClass"
                ></i>
              </el-input>
            </el-form-item>
            <!-- <el-form-item prop="authCode">
              <el-input
                placeholder="请输入验证码"
                v-model="re_ruleForm.authCode"
                maxlength="6"
              >
                <i
                  slot="prefix"
                  style="color: #476fd3; font-size: 14px; margin-left: 6px"
                  class="iconfont icon-anquanbaozhang-xianxing"
                ></i>
                <i
                  v-if="!timeBegin"
                  slot="suffix"
                  style="
                    cursor: pointer;
                    color: #476fd3;
                    font-size: 12px;
                    font-style: normal;
                  "
                  @click="getAuthCode"
                  >获取验证码</i
                >
                <i
                  slot="suffix"
                  v-if="timeBegin"
                  style="
                    cursor: pointer;
                    color: #476fd3;
                    font-size: 12px;
                    font-style: normal;
                  "
                  >({{ time }}s)后重新获取</i
                >
              </el-input>
            </el-form-item> -->
          </el-form>
        </div>
        <div class="goLogin">
          <span style="font-size: 12px; color: #666666">已有账号?</span>
          <span
            @click="goLogin"
            style="
              font-size: 12px;
              color: #2650FF;
              margin-left: 5px;
              cursor: pointer;
            "
            >去登录</span
          >
        </div>
        <div class="register_login">
          <div @click="register('re_ruleForm')">注册</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { setToken, getToken, removeToken } from "@/utils/auth"; // get token from cookie
import { Apiregister, Apilogin } from "@/api/user";
import "@/assets/icons/eyes/iconfont.css";
import "@/assets/icons/register/iconfont.css";

export default {
  data () {
    return {
      ruleForm: {
        username: "",
        password: "",
      },
      rules: {
        username: {
          required: true,
          message: "请输入账户名",
          trigger: "change",
        },
        password: { required: true, message: "请输入密码", trigger: "change" },
      },
      re_ruleForm: {
        mobile: "",
        password: "",
        email: "",
        authCode: "123456",
      },
      re_rules: {
        mobile: {
          required: true,
          message: "请输入手机号",
          trigger: "change",
        },
        email: [
          { required: true, message: "请输入邮箱", trigger: "change" },
          {
            type: "email",
            message: "请输入正确的邮箱地址",
            trigger: ["blur", "change"],
          },
        ],
        authCode: {
          required: true,
          message: "请输入验证码",
          trigger: "change",
        },
        password: { required: true, message: "请输入密码", trigger: "change" },
      },
      flag: false,
      re_flag: false,
      checked: false,
      isLogin: true,
      errorShow: false,
      time: 60,
      _inter: "",
      timeBegin: false,
      errorMSG: "",
    };
  },
  computed: {
    type () {
      return this.flag ? "text" : "password";
    },
    iconClass () {
      return this.flag ? "el-icon-view" : "iconfont icon-eye-close";
    },
    re_type () {
      return this.re_flag ? "text" : "password";
    },
    re_iconClass () {
      return this.re_flag ? "el-icon-view" : "iconfont icon-eye-close";
    },
  },
  watch: {
    time (i, j) {
      // ...
      if (i === 0) {
        clearInterval(this._inter);
        this.timeBegin = false;
      }
    },
  },
  methods: {
    // 切换密码可见
    isShow () {
      this.flag = !this.flag;
    },
    isShow_re () {
      this.re_flag = !this.re_flag;
    },
    // 去注册
    goRegister () {
      this.isLogin = false;
    },
    // 注册
    register (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          console.log(this.re_ruleForm);
          Apiregister(this.re_ruleForm).then((res) => {
            console.log(res);
            if (res.code === 200) {
              this.isLogin = true
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
          // alert("submit!");
          // this.errorShow = true;
          if (this.checked) {
            setToken("name", this.ruleForm.username);
            setToken("word", this.ruleForm.password);
            setToken("ischecked", true);
          } else {
            setToken("ischecked", false);
          }
          Apilogin(this.ruleForm).then((res) => {
            console.log(res);
            if (res.code === 200) {
              setToken("FSH_AUTH_api", res.data.token);
              setToken("userId", res.data.userId);
              setToken('apiPhone', res.data.mobile)
              this.$router.push("/dashboard/index");
            } else {
              this.errorShow = true;
              this.errorMSG = res.msg;
            }
          });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    // 已有账号去登录
    goLogin () {
      this.isLogin = true;
    },
    //
    timing () {
      this.time = 60;
      this._inter = setInterval(() => {
        this.time--;
      }, 1000);
    },
    // 获取验证码
    getAuthCode () {
      this.timeBegin = true;
      this.timing();
    },
    CSPLogin () { },
  },
  created () {
    removeToken("FSH_AUTH_api");
    const ischecked = getToken("ischecked");
    console.log(ischecked);
    if (ischecked === "true") {
      this.ruleForm = {
        username: getToken("name"),
        password: getToken("word"),
      };
      this.checked = true;
    } else {
    }
    console.log("初始化");
  },
};
</script>

<style lang="scss" scoped>
.login_main {
  width: 100%;
  height: 100%;
  min-width: 1400px;
  display: flex;
  .login_left {
    width: 60%;
    font-size: 0px;
    position: relative;
    .login_logo {
      position: absolute;
      left: 50px;
      top: 20px;
      img {
        width: 190px;
        height: 34px;
      }
    }
    // height: 1vh;
    .left_bgc {
      width: 100%;
      height: 100vh;
    }
  }
  .login_right {
    width: 40%;
    height: 100vh;
    overflow: hidden;
    position: relative;
    .right_main {
      margin-top: 25%;
      text-align: center;
      // width: 40%;
      .right_title {
        font-size: 18px;
        font-weight: 700;
        letter-spacing: 5px;
        color: #476fd3;
      }
      .login_error {
        margin: 0 auto;
        margin-top: 80px;
        width: 45%;
        height: 35.33px;
      }
      .right_form {
        margin-top: 3px;
      }
      .right_handle {
        margin-top: 24px;
        width: 45%;
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
            color: #476fd3;
            font-size: 14px;
            cursor: pointer;
          }
        }
      }
      .right_login {
        margin: 24px auto;
        width: 45%;
        height: 30px;
        div {
          line-height: 30px;
          background-color: #2650ff;
          color: #fff;
          font-size: 14px;
          border-radius: 2px;
          cursor: pointer;
        }
      }
      .right_login_csp {
        margin: 24px auto;
        width: 45%;
        height: 30px;
        div {
          line-height: 30px;
          background-color: #f0f2f5;
          border-radius: 2px;
          color: #2650ff;
          font-size: 14px;
          cursor: pointer;
        }
      }
    }
    .register {
      margin-top: 25%;
      text-align: center;
      .register_title {
        font-size: 18px;
        font-weight: 700;
        letter-spacing: 5px;
        color: #476fd3;
      }
      .register_form {
        margin-top: 83px;
      }
      .register_login {
        margin: 0 auto;
        width: 45%;
        height: 30px;
        div {
          line-height: 30px;
          background-color: #2650ff;
          color: #fff;
          font-size: 14px;
          border-radius: 2px;
          cursor: pointer;
        }
      }
      .goLogin {
        margin: 24px auto;
        text-align: left;
        width: 45%;
      }
    }
  }
}
</style>
