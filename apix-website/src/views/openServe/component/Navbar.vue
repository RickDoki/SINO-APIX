<template>
  <div class="navbar_top">
    <div class="navbar">
      <div style="width: 210px;height: 60px;display: flex;justify-content: center;align-items: center">
        <img src="../../../assets/img/sdx_logo.png" class="sidebar-logo">
      </div>
      <div class="navber_userHandle">
        <div class="kongzhitai" @click="godashboard">控制台</div>
        <div class="kongzhitai" style="margin-left: 20px" @click="gologin" v-if="!isLogin">登录</div>
        <img src="../../../assets/img/img_avatar.png" alt="" v-if="isLogin"/>
        <el-dropdown trigger="click" @command="handleCommand" v-if="isLogin">
          <span style="color: #1D1C35" class="el-dropdown-link">
            {{ phone }}<i class="el-icon-arrow-down el-icon--right"></i>
          </span>
          <el-dropdown-menu slot="dropdown" class="drop-div">
            <el-dropdown-item command="a">个人信息</el-dropdown-item>
            <el-dropdown-item command="b">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </div>
  </div>
</template>

<script>
import {mapGetters} from "vuex";
import {getToken, removeToken} from "@/utils/auth";

export default {
  components: {},
  data() {
    return {
      phone: '',
      isLogin: true
    };
  },
  created() {
    this.phone = getToken('apiPhone')
    if (this.phone) {
      this.isLogin = true
    } else {
      this.isLogin = false
    }
  },
  watch: {
    $route: {
      handler: function (val, oldVal) {
      },
      // 深度观察监听
      deep: true,
    },
  },
  computed: {
    ...mapGetters(["sidebar", "avatar", "device"]),
  },
  methods: {
    handleCommand(command) {
      // this.$message("click on item " + command);
      if (command == 'a') {
        this.$router.push({path: '/system/index'})
      } else {
        this.$router.push({path: '/login'})
      }
    },
    godashboard() {
      this.$router.push({
        name: 'Dashboard'
      })
    },
    gologin() {
      this.$router.push({
        path: '/login'
      })
    }
  },
};
</script>

<style lang="scss" scoped>
.drop-div {
  top: 32px !important;
}

.el-dropdown-menu__item:focus,
.el-dropdown-menu__item:not(.is-disabled) {
  &:hover {
    border: none !important;
    background-color: #fff !important;
    color: #2650ff;
  }
}

.brea-div {
  height: 20px;
  font-size: 14px;
  font-family: Microsoft YaHei UI-Regular, Microsoft YaHei UI;
  font-weight: 400;
  color: #727491;
  line-height: 20px;
}

.widthTrue {
  margin-right: 210px;
}

.widthFalse {
  margin-right: 54px;
}

.navbar_top {
  overflow: hidden;
  position: fixed;
  top: 0px;
  z-index: 99;
  width: 100%;
}

.breadcrumb {
  padding: 24px 0px 0px 40px;
  background-color: #ffffff;

  .dashboard {
    margin-top: 24px;
    height: 26px;
    font-size: 20px;
    font-family: Microsoft YaHei UI-Bold, Microsoft YaHei UI;
    font-weight: bold;
    color: #1d1c35;
    line-height: 26px;
  }
}

.navbar {
  overflow: hidden;
  height: 60px;
  background: #fff;
  border-bottom: 1px solid #e9e9e9;
  display: flex;
  justify-content: space-between;
  align-items: center;

  .sidebar-logo {
    width: 154px;
    height: 26px;
  }

  .navber_notification {
    margin-left: 50px;
    cursor: pointer;
    margin-top: 17px;
    color: #f1f1f1;
  }

  .navber_userHandle {
    padding-right: 50px;

    .kongzhitai {
      cursor: pointer;
      display: inline-block;
      height: 20px;
      font-size: 14px;
      font-family: Microsoft YaHei UI-Regular, Microsoft YaHei UI;
      font-weight: 400;
      color: #000000;
      line-height: 20px;
    }

    img {
      margin-left: 30px;
      width: 34px;
      height: 34px;
      vertical-align: middle;
      margin-right: 3px;
    }

    .el-dropdown-link {
      cursor: pointer;
      color: #2650ff;
    }

    .el-icon-arrow-down {
      font-size: 12px;
    }

    .demonstration {
      display: block;
      color: #8492a6;
      font-size: 14px;
      margin-bottom: 20px;
    }
  }
}
</style>
