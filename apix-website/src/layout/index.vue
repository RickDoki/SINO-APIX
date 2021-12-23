<template>
  <div :class="classObj" class="app-wrapper">
    <!-- <div
      v-if="device === 'mobile' && sidebar.opened"
      class="drawer-bg"
      @click="handleClickOutside"
    /> -->
    <sidebar class="sidebar-container" />
    <div :class="{ hasTagsView: needTagsView }" class="main-container">
      <div :class="{ 'fixed-header': fixedHeader }">
        <navbar />
      </div>
      <!-- <div class="breadcrumb">
        <el-breadcrumb
          v-if="routerList[0].path === '/dashboard' ? false : true"
          style="height: 34px; line-height: 34px; color: #999999"
          separator="/"
        >
          <el-breadcrumb-item :to="{ path: '/dashboard/index' }">控制台</el-breadcrumb-item>
          <el-breadcrumb-item v-for="(item, index) in routerList" :key="index">{{item.meta.title}}</el-breadcrumb-item>
        </el-breadcrumb>
        <div class="dashboard">{{routerList[1].meta.title}}</div>
      </div> -->
      <app-main />
    </div>
  </div>
</template>

<script>
import RightPanel from "@/components/RightPanel";
import { AppMain, Navbar, Settings, Sidebar, TagsView } from "./components";
import ResizeMixin from "./mixin/ResizeHandler";
import { mapState } from "vuex";

export default {
  name: "Layout",
  components: {
    AppMain,
    Navbar,
    RightPanel,
    Settings,
    Sidebar,
    TagsView,
  },
  data() {
    return {
      routerList:[]
    }
  },
  created() {
    // console.log(this.$route)
    this.routerList = this.$route.matched
  },
  mixins: [ResizeMixin],
  computed: {
    ...mapState({
      sidebar: (state) => state.app.sidebar,
      device: (state) => state.app.device,
      showSettings: (state) => state.settings.showSettings,
      needTagsView: (state) => state.settings.tagsView,
      fixedHeader: (state) => state.settings.fixedHeader,
    }),
    classObj() {
      return {
        hideSidebar: !this.sidebar.opened,
        openSidebar: this.sidebar.opened,
        withoutAnimation: this.sidebar.withoutAnimation,
        mobile: this.device === "mobile",
      };
    },
  },
  methods: {
    handleClickOutside() {
      this.$store.dispatch("app/closeSideBar", { withoutAnimation: false });
    },
  },
  watch: {
    $route: {
      handler: function (val, oldVal) {
        console.log(val.matched);
        this.routerList = val.matched
      },
      // 深度观察监听
      deep: true,
    },
  }
};
</script>

<style lang="scss" scoped>
@import "~@/styles/mixin.scss";
@import "~@/styles/variables.scss";
.breadcrumb {
  padding-left: 32px;
  border-left: 1px solid #E9E9E9;
  position: fixed;
  // overflow: hidden;
  width: 100%;
  top: 50px;
  z-index: 100;
  background-color: #fff;
  .dashboard {
    height: 44px;
    line-height: 44px;
  }
  // height: 64px;
}
.app-wrapper {
  @include clearfix;
  position: relative;
  height: 100%;
  width: 100%;

  &.mobile.openSidebar {
    position: fixed;
    top: 0;
  }
}

.drawer-bg {
  background: #000;
  opacity: 0.3;
  width: 100%;
  top: 0;
  height: 100%;
  position: absolute;
  z-index: 999;
}

.fixed-header {
  position: fixed;
  top: 0;
  right: 0;
  z-index: 9;
  width: calc(100% - #{$sideBarWidth});
  transition: width 0.28s;
}

.hideSidebar .fixed-header {
  width: calc(100% - 54px);
}

.mobile .fixed-header {
  width: 100%;
}
</style>
