<template>
  <div class="sidebar-logo-container" :class="{'collapse':collapse}">
    <transition name="sidebarLogoFade">
      <router-link key="collapse" class="sidebar-logo-link" to="/openServe">
        <img src="./../../../assets/img/sdx_logo.png" class="sidebar-logo">
        <!-- <p class="sidebar-title">SINO APIX</p> -->
      </router-link>
    </transition>
  </div>
</template>

<script>
export default {
  name: 'SidebarLogo',
  props: {
    collapse: {
      type: Boolean,
      required: true
    }
  },
  data () {
    return {
      title: 'API管理平台',
      logo: './../../../assets/img/logo.png'
    }
  }
}
</script>

<style lang="scss" scoped>
.sidebarLogoFade-enter-active {
  transition: opacity 1.5s;
}

.sidebarLogoFade-enter,
.sidebarLogoFade-leave-to {
  opacity: 0;
}

.sidebar-logo-container {
  position: relative;
  width: 100%;
  height: 60px;
  border-bottom: 1px solid #e9e9e9;
  line-height: 15px;
  background: #fff;
  text-align: center;
  overflow: hidden;

  & .sidebar-logo-link {
    height: 100%;
    width: 100%;
    & .sidebar-logo {
      margin-top: 15px;
      vertical-align: middle;
      text-align: center;
    }

    & .sidebar-title {
      display: inline-block;
      margin-left: 8px;
      font-size: 22px;
      font-family: Roboto;
      font-weight: bold;
      color: #1d1c35;
      vertical-align: middle;
    }
  }

  &.collapse {
    .sidebar-logo {
      margin-right: 0px;
    }
  }
}
</style>
