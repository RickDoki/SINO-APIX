<template>
  <div class="main">
    <div class="list_top">
      <div class="list_title titleFont">添加新本版本</div>
    </div>
    <div>
      <span class="secondTitle">向现有的服务添加新版本</span>
    </div>
    <div class="createdNewedition">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm">
        <el-form-item label="版本名称:" prop="name">
          <el-input v-model="ruleForm.name"></el-input>
        </el-form-item>
        <el-form-item label="版本描述:" prop="describe">
          <el-input v-model="ruleForm.describe"></el-input>
        </el-form-item>
        <el-form-item label="关联API:" prop="API">
          <el-select
            class="selectCss"
            v-model="ruleForm.API"
            placeholder="请选择"
          >
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
          <span class="goCreate">还没有API?去创建>></span>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')"
            >立即创建</el-button
          >
          <el-button @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import "./../mainCss/index.scss";
export default {
  data() {
    return {
      ruleForm: {
        name: "",
        describe: "",
        API: "",
      },
      options: [{ label: "111", value: "111" }],
      rules: {
        name: [
          { required: true, message: "请输入活动名称", trigger: "blur" },
          { min: 3, max: 5, message: "长度在 3 到 5 个字符", trigger: "blur" },
        ],
        describe: [
          { required: true, message: "请输入活动名称", trigger: "blur" },
          { min: 3, max: 5, message: "长度在 3 到 5 个字符", trigger: "blur" },
        ],
        API: [
          { required: true, message: "请输入活动名称", trigger: "blur" },
          { min: 3, max: 5, message: "长度在 3 到 5 个字符", trigger: "blur" },
        ],
      },
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          alert("submit!");
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
  },
};
</script>

<style scoped lang='scss'>
.createdNewedition {
  margin-top: 10px;
  width: 500px;
  .selectCss {
    display: block;
    // vertical-align: middle;
    ::v-deep .el-input__suffix {
      height: 36px !important;
      top: 36px;
    }
  }
  .goCreate {
    position: absolute;
    top: 35px;
    right: -140px;
    cursor: pointer;
    color: #2650FF;
  }
}
</style>