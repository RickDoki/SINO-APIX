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
        <el-form-item label="门户标题" prop="name">
          <el-input v-model="ruleForm.name" class="inputWidth"></el-input>
        </el-form-item>
        <el-form-item label="门户简介" prop="describe">
          <el-input 
            v-model="ruleForm.describe" 
            type="textarea"
            class="inputWidth"
            :autosize="{ minRows: 4, maxRows: 10 }"
            maxlength="500"
            show-word-limit>
          </el-input>
        </el-form-item>
        <el-form-item label="门户Logo">
          <el-upload
            :limit="1"
            action="#"
            list-type="picture-card"
            :auto-upload="false">
              <i slot="default" class="el-icon-plus"></i>
              <div slot="file" slot-scope="{file}">
                <img
                  class="el-upload-list__item-thumbnail"
                  :src="file.url" alt=""
                >
                <span class="el-upload-list__item-actions">
                  <span
                    class="el-upload-list__item-preview"
                    @click="handlePictureCardPreview(file)"
                  >
                    <i class="el-icon-zoom-in"></i>
                  </span>
                  <span
                    v-if="!disabled"
                    class="el-upload-list__item-delete"
                    @click="handleDownload(file)"
                  >
                    <i class="el-icon-download"></i>
                  </span>
                  <span
                    v-if="!disabled"
                    class="el-upload-list__item-delete"
                    @click="handleRemove(file)"
                  >
                    <i class="el-icon-delete"></i>
                  </span>
                </span>
              </div>
          </el-upload>
          <el-dialog :visible.sync="dialogVisible">
            <img width="100%" :src="dialogImageUrl" alt="">
          </el-dialog>
        </el-form-item>
        <el-form-item label="门户Icon">
          <el-upload
            :limit="1"
            action="#"
            list-type="picture-card"
            :auto-upload="false">
              <i slot="default" class="el-icon-plus"></i>
              <div slot="file" slot-scope="{file}">
                <img
                  class="el-upload-list__item-thumbnail"
                  :src="file.url" alt=""
                >
                <span class="el-upload-list__item-actions">
                  <span
                    class="el-upload-list__item-preview"
                    @click="handlePictureCardPreview(file)"
                  >
                    <i class="el-icon-zoom-in"></i>
                  </span>
                  <span
                    v-if="!disabled"
                    class="el-upload-list__item-delete"
                    @click="handleDownload(file)"
                  >
                    <i class="el-icon-download"></i>
                  </span>
                  <span
                    v-if="!disabled"
                    class="el-upload-list__item-delete"
                    @click="handleRemove(file)"
                  >
                    <i class="el-icon-delete"></i>
                  </span>
                </span>
              </div>
          </el-upload>
          <el-dialog :visible.sync="dialogVisible">
            <img width="100%" :src="dialogImageUrl" alt="">
          </el-dialog>
        </el-form-item>
      </el-form>
      <div class="bottom_button_a">
        <el-button size="small" @click="resetForm('ruleForm')">取消</el-button>
        <el-button size="small" type="primary" @click="submitForm('ruleForm')">保存</el-button>
      </div>
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
        API: ""
      },
      dialogImageUrl: '',
      dialogVisible: false,
      disabled: false,
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
    handleRemove (file) {
      console.log(file);
    },
    handlePictureCardPreview (file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    handleDownload (file) {
      this.disabled = false
      console.log(file);
    },
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