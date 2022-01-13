<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
import "./styles/index.scss";
export default {
  name: 'App'
}
</script>
<style lang="scss">
.main .el-table td,
.main .el-table th.is-leaf,
.main .el-table--border,
.main .el-table--group {
  border: none;
  cursor: pointer;
  padding: 0;
}
.main .el-table::before {
  height: 0;
}
.mian .el-table__header tr,
.el-table__header th {
  height: 54px;
}
.main .el-card__body {
  padding: 24px;
}
</style>

