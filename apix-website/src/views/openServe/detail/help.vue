<template>
  <div>
    <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
      <el-tab-pane label="帮助文档" name="first">
        <help-files></help-files>
      </el-tab-pane>
      <el-tab-pane label="开发文档" name="second">
        <kaifa></kaifa>
      </el-tab-pane>
      
    </el-tabs>
  </div>
</template>

<script>
import helpFiles from './helpFiles/index.vue'
import kaifa from './helpFiles/kaifa.vue'

export default {
  components:{
    helpFiles,
    kaifa
  },
  data() {
    return {
      activeName: "first",
    };
  },
  methods: {
    handleClick(tab, event) {
      console.log(tab, event);
    },
  },
};
</script>

<style>
</style>