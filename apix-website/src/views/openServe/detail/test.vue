<template>
  <div class="test_api">
    <!-- 接口测试 -->
    <div class="items">生产和沙箱环境URL:</div>
    <div class="items URL SIZE">http://www.baidu.com</div>
    <div class="items URL SIZE">https://www.baidu.com</div>
    <div class="items">API调试详情:</div>
    <div class="items FLEX">
      <div style="line-height: 36px">调试应用选择:</div>
      <div>
        <el-select v-model="value" placeholder="请选择">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </div>
    </div>
    <div class="items FLEX">
      <div style="line-height: 36px">调试环境选择:</div>
      <div>
        <el-select v-model="value" placeholder="请选择">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </div>
    </div>
    <div class="items">
      <el-input placeholder="请输入内容" v-model="input1">
        <template slot="prepend">Authorization: Bearer</template>
      </el-input>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      value: "",
      options: [],
      input1: ''
    };
  },
};
</script>

<style lang='scss' scoped>
.test_api {
  padding: 24px;
  .URL {
    color: #2650ff;
    padding-left: 20px;
  }
  .SIZE {
    font-size: 12px;
  }
  .middle {
    vertical-align: middle;
  }
  .FLEX {
    display: flex;
  }
  .items {
    margin-bottom: 15px;
  }
}
</style>