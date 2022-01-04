<template>
  <div class="navbar_top">
    <div class="navbar">
      <div class="navber_userHandle">
        <img src="./../../assets/img/img_avatar.png" alt=""/>
        <el-dropdown trigger="click" @command="handleCommand">
          <span style="color: #1D1C35" class="el-dropdown-link">
            {{phone}}16621797423<i class="el-icon-arrow-down el-icon--right"></i>
          </span>
          <el-dropdown-menu slot="dropdown" class="drop-div">
            <el-dropdown-item command="a">个人信息</el-dropdown-item>
            <el-dropdown-item command="b">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </div>
    <div class="breadcrumb">
      <el-breadcrumb
        class="brea-div"
        separator=">"
      >
        <!-- <el-breadcrumb-item 
          v-if="routerList[0].path === '/dashboard' ? false : true"
          :to="{ path: '/dashboard/index' }">控制台</el-breadcrumb-item> -->
        <template v-for="(item, index) in routerList">
          <el-breadcrumb-item
            :key="index"
            v-if="item.meta.title ? true : false"
            >{{ item.meta.title }}</el-breadcrumb-item
          >
        </template>
      </el-breadcrumb>
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
  data () {
    return {
      logoMenu: ["right-menu", "widthTrue"],
      searchContent: "",
      routerList: [],
      phone: ''
    };
  },
  created () {
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
    handleCommand (command) {
      // this.$message("click on item " + command);
      this.$router.push({ path: '/login' })
    },
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
  width: calc(100% - 210px);
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
  justify-content: flex-end;
  .navber_notification {
    margin-left: 50px;
    cursor: pointer;
    margin-top: 17px;
    color: #f1f1f1;
  }
  .navber_userHandle {
    margin: 0px 50px 150px;
    line-height: 55px;
    img {
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
