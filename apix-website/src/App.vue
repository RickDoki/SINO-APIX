<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
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
.main .el-table {
  border: 1px solid #ebeef5;
}
.mian .el-table__header tr,
.el-table__header th {
  height: 54px;
}
.main .el-card__body {
  padding: 24px;
}
.main {
  margin: 24px 40px;
  margin-bottom: 70px;
  background-color: #fff;
  min-height: calc(100vh - 185px);
  .bottom {
    position: fixed;
    width: calc(100% - 210px);
    height: 60px;
    background-color: #fff;
    bottom: 0px;
    .bottom_button {
      margin-left: 185px;
      margin-top: 10px;
      div {
        display: inline-block;
        padding: 5px 8px;
        margin-right: 20px;
        cursor: pointer;
      }
    }
    .bottom_button_b {
      margin-left: 480px;
      margin-top: 10px;
    }
  }
  .top {
    padding: 0px 75px;
  }
  .formBox {
    margin: 30px 0px;
    .contentDiv {
      display: flex;
      .item-div {
        margin-left: 20px;
      }
    }
    .item {
      position: absolute;
      right: -15px;
      top: 3px;
    }
    .addDiv {
      margin-top: 10px;
    }
    .add_span {
      color: #2c66fb;
    }
    .inputWidth {
      width: 48%;
    }
    .show-but {
      margin-left: 16px;
      height: 20px;
      font-size: 14px;
      font-family: Microsoft YaHei UI-Regular, Microsoft YaHei UI;
      font-weight: 400;
      color: #2650ff;
      line-height: 20px;
      cursor: pointer;
    }
    .formBut {
      text-align: right;
    }
    .numberWdith {
      width: 180px;
    }
    .numberWdith2 {
      width: 205px;
    }
    .divider {
      display: flex;
      line-height: 45px;
      margin: 20px 0px;
      .dividerTitle {
        font-size: 16px;
        font-weight: 500;
        margin-right: 20px;
        width: 90px;
      }
    }
  }
}
</style>

