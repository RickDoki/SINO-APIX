<template>
  <div class="navbar_top">
    <div class="navbar">
      <div class="navbar_search">
        <el-input
          style="opacity: 0.5"
          placeholder="搜索..."
          size="mini"
          v-model="searchContent"
        >
          <i
            slot="prefix"
            style="color: #476fd3"
            class="el-input__icon el-icon-search"
          ></i>
        </el-input>
      </div>
      <div class="navber_notification">
        <el-badge is-dot>
          <i class="el-icon-bell"></i>
        </el-badge>
      </div>
      <div class="navber_userHandle">
        <img src="./../../assets/img/nav-bar-user.png" alt="" />
        <el-dropdown trigger="click" @command="handleCommand">
          <span style="color: #fff" class="el-dropdown-link">
            {{phone}}<i class="el-icon-arrow-down el-icon--right"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item command="b">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </div>
    <div class="breadcrumb">
      <el-breadcrumb
        v-if="routerList[0].path === '/dashboard' ? false : true"
        style="height: 34px; line-height: 34px; color: #999999"
        separator="/"
      >
        <el-breadcrumb-item :to="{ path: '/dashboard/index' }"
          >控制台</el-breadcrumb-item
        >
        <template v-for="(item, index) in routerList">
          <el-breadcrumb-item
            :key="index"
            v-if="item.meta.title ? true : false"
            >{{ item.meta.title }}</el-breadcrumb-item
          >
        </template>
      </el-breadcrumb>
      <div class="dashboard">{{ routerList[1].meta.title }}</div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import Breadcrumb from "@/components/Breadcrumb";
import Hamburger from "@/components/Hamburger";
import ErrorLog from "@/components/ErrorLog";
import { getToken, removeToken } from "@/utils/auth";
// import Screenfull from '@/components/Screenfull'
// import SizeSelect from '@/components/SizeSelect'
// import Search from '@/components/HeaderSearch'

export default {
  components: {
    Breadcrumb,
    Hamburger,
    ErrorLog,
    // Screenfull,
    // SizeSelect,
    // Search
  },
  data() {
    return {
      logoMenu: ["right-menu", "widthTrue"],
      searchContent: "",
      routerList: [],
      phone:''
    };
  },
  created() {
    // console.log(this.$route)
    this.routerList = this.$route.matched;
    console.log(this.routerList[0].meta.title);
    this.phone = getToken('apiPhone')
  },
  watch: {
    $route: {
      handler: function (val, oldVal) {
        console.log(val.matched);
        this.routerList = val.matched;
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
      this.$router.push({path:'/login'})
    },
  },
};
</script>

<style lang="scss" scoped>
.widthTrue {
  // width: calc(100% - 210px);
  margin-right: 210px;
}
.widthFalse {
  // width: calc(100% - 54px);
  margin-right: 54px;
}
.navbar_top {
  overflow: hidden;
  position: fixed;
  top: 0px;
  z-index: 99;
  width: calc(100% - 210px);
}
.breadcrumb {
  // width: calc(100% - 210px);
  padding-left: 30px;
  background-color: #ffffff;
  .dashboard {
    height: 44px;
    line-height: 44px;
  }
}
.navbar {
  overflow: hidden;
  height: 50px;
  // width: calc(100% - 210px);
  background: #2c66fb;
  box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);
  display: flex;
  justify-content: flex-end;
  .navbar_search {
    height: 50px;
    margin-top: 11px;
  }
  .navber_notification {
    margin-left: 50px;
    cursor: pointer;
    margin-top: 17px;
    color: #f1f1f1;
  }
  .navber_userHandle {
    margin: 0px 50px 150px;
    line-height: 50px;
    img {
      width: 16px;
      height: 16px;
      vertical-align: middle;
      margin-right: 3px;
    }
    .el-dropdown-link {
      cursor: pointer;
      color: #409eff;
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
