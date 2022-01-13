<template>
  <el-drawer
    title="插件详情"
    :visible.sync="drawer"
    :direction="direction"
    :before-close="handleClose"
    size="50%"
  >
  <span>查看详情</span>
  </el-drawer>
</template>

<script>
export default {
  props: ["drawerProps"],
  watch: {
    drawerProps() {
      this.drawer = this.drawerProps;
    },
  },
  data() {
    return {
      drawer: false,
      direction: "rtl",
    };
  },
  methods: {
    handleClose(done) {
      done();
      this.$emit("showChange", false);
    },
  },
};
</script>

<style>
</style>