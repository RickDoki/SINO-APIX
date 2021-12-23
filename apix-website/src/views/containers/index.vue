<template>
  <div>
    <!-- 容器页 -->
    <router-view></router-view>
  </div>
</template>

<script>
export default {

}
</script>

<style lang='scss' scoped>
  .top {
    width: 500px;
    height: 100px;
    background-color: #ccc;
    .top-child {
      width: 200px;
      height: 80px;
      background-color: pink;
    }
  }
</style>
