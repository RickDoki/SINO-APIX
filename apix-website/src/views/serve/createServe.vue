<template>
  <div class="main">
    <div class="list_top">
      <div class="list_title">创建服务</div>
    </div>
    <div>
      <span class="secondTitle"
        >创建服务来管理和代理现有API或发布到门户。服务包含一个或多个版本。</span
      >
    </div>
    <div class="formBox">
      <el-form
        :model="ruleForm"
        :rules="rules"
        ref="ruleForm"
        label-position="top"
      >
        <el-form-item label="服务名称" prop="name">
          <el-input
            v-model="ruleForm.name"
            placeholder=""
            class="inputWidth"
            clearable
          >
          </el-input>
        </el-form-item>
        <el-form-item label="服务标签" prop="dynamicTags">
          <el-tag
            :key="tag"
            v-for="tag in ruleForm.dynamicTags"
            closable
            :disable-transitions="false"
            @close="handleClose(tag)"
          >
            {{ tag }}
          </el-tag>
          <el-input
            class="input-new-tag"
            v-if="inputVisible"
            v-model="inputValue"
            ref="saveTagInput"
            size="small"
            @keyup.enter.native="handleInputConfirm"
            @blur="handleInputConfirm"
          >
          </el-input>
          <el-button
            v-else
            class="button-new-tag"
            size="small"
            @click="showInput"
            >+ New Tag</el-button
          >
        </el-form-item>
        <el-form-item label="服务描述" prop="describe">
          <el-input
            type="textarea"
            style="width:48%"
            :autosize="{ minRows: 4, maxRows: 4 }"
            placeholder="请输入内容"
            v-model="ruleForm.describe"
          >
          </el-input>
        </el-form-item>
        <div class="bottom_button_a">
          <el-button size="small" type="primary" @click="submitForm('ruleForm')"
            >立即创建</el-button
          >
          <el-button size="small" @click="resetForm('ruleForm')"
            >取消</el-button
          >
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import "./../mainCss/index.scss";
export default {
  data() {
    return {
      drawerIsshow: false,
      ruleForm: {
        name: "",
        describe: "",
        dynamicTags: ["标签一", "标签二", "标签三"],
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
        dynamicTags: [
          { required: true, message: "请输入活动名称", trigger: "blur" },
        ],
      },

      inputVisible: false,
      inputValue: "",
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
    handleClose(tag) {
      this.ruleForm.dynamicTags.splice(
        this.ruleForm.dynamicTags.indexOf(tag),
        1
      );
    },
    showInput() {
      this.inputVisible = true;
      this.$nextTick((_) => {
        this.$refs.saveTagInput.$refs.input.focus();
      });
    },
    handleInputConfirm() {
      let inputValue = this.inputValue;
      if (inputValue) {
        this.ruleForm.dynamicTags.push(inputValue);
      }
      this.inputVisible = false;
      this.inputValue = "";
    },
  },
};
</script>

<style scoped lang='scss'>
.el-tag + .el-tag {
  margin-left: 10px;
}
.button-new-tag {
  margin-left: 10px;
  height: 32px;
  line-height: 30px;
  padding-top: 0;
  padding-bottom: 0;
}
.input-new-tag {
  width: 90px;
  margin-left: 10px;
  vertical-align: bottom;
}
</style>