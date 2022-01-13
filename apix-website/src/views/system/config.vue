<template>
  <div class="main">
    <div class="list_top">
      <div class="list_title">开放门户配置</div>
    </div>
    <div>
      <span class="secondTitle">配置开放门户个性化信息展示，支持用户自定义。</span>
    </div>
    <div class="formBox">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-position="top">
        <el-form-item label="门户标题" prop="title">
          <el-input maxlength="15" show-word-limit v-model="ruleForm.title" class="inputWidth"></el-input>
        </el-form-item>
        <el-form-item label="门户简介" prop="description">
          <el-input 
            v-model="ruleForm.description" 
            class="inputWidth"
            maxlength="22"
            show-word-limit>
          </el-input>
        </el-form-item>
      </el-form>
      <div class="bottom_button_a">
        <el-button size="small" type="primary" @click="submitForm('ruleForm')">保存</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import { getDoorConfig, updateDoorConfig } from "@/api/user"
import "./../mainCss/index.scss";
export default {
  data () {
    return {
      ruleForm: {
        title: "",
        description: ""
      },
      rules: {
        title: [
          { required: true, message: "请输入门户标题", trigger: "blur" }
        ],
        description: [
          { required: true, message: "请输入门户简介", trigger: "blur" }
        ]
      },
    };
  },
  mounted () {
    this.getPageInfo()
  },
  methods: {
    getPageInfo () {
      getDoorConfig().then((res) => {
        this.ruleForm = res.data
      });
    },
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          updateDoorConfig(this.ruleForm).then((res) => {
            if (res.code === 200) {
              this.$message('保存成功');
            }
          });
        }
      });
    },
  },
};
</script>

<style scoped lang='scss'>
</style>