<template>
  <div class="main">
    <div class="list_top">
      <div class="list_title">添加新本版本</div>
    </div>
    <div>
      <span class="secondTitle">向现有的服务添加新版本</span>
    </div>
    <div class="formBox">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-position="top">
        <el-form-item label="版本名称" prop="name">
          <el-input 
            v-model="ruleForm.name"
            placeholder=""
            class="inputWidth"
            clearable>
          </el-input>
        </el-form-item>
        <el-form-item label="版本描述" prop="describe">
          <el-input 
            v-model="ruleForm.describe"
            placeholder=""
            class="inputWidth"
            clearable>
          </el-input>
        </el-form-item>
        <el-form-item label="关联API" prop="API">
          <el-select
            class="inputWidth"
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
          <span class="show-but">还没有API?去创建 >> </span>
        </el-form-item>
        <div class="bottom_button_a">
          <el-button size="small" type="primary" @click="submitForm('ruleForm')">立即创建</el-button>
          <el-button size="small" @click="resetForm('ruleForm')">重置</el-button>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import "./../mainCss/index.scss";
export default {
  data () {
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
    submitForm (formName) {
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
</style>