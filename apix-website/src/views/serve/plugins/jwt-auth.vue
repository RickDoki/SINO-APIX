<template>
  <div class="formBox">
    <el-form
      :model="ruleForm"
      :rules="rules"
      ref="ruleForm"
      label-position="top"
    >
      <el-form-item label="config.Header" prop="header">
        <el-input
          v-model="ruleForm.header"
          placeholder=""
          class="inputWidth"
          :disabled="true"
          clearable
        >
        </el-input>
      </el-form-item>
      <el-form-item label="config.key" prop="key">
        <el-input
          v-model="ruleForm.key"
          placeholder=""
          class="inputWidth"
          :disabled="true"
          clearable
        >
        </el-input>
      </el-form-item>
      <el-form-item label="config.Maximum (过期时间ms)" prop="maximum">
        <el-input-number
          v-model="ruleForm.maximum"
          @change="handleChange"
          :min="1"
        ></el-input-number>
      </el-form-item>
      <div class="bottom_button_a">
        <el-button size="small" type="primary" @click="submitForm('ruleForm')"
          >添加</el-button
        >
        <el-button size="small" @click="resetForm('ruleForm')">取消</el-button>
      </div>
    </el-form>
  </div>
</template>

<script>
import { postPlugin } from "@/api/AboutServe.js";

export default {
  data() {
    return {
      appCode: "",
      appId: "",
      ruleForm: {
        header: "authorizatuion",
        key: "iss",
        maximum: "7200000",
      },
      rules: {
        header: [
          { required: true, message: "config.Header为必选项", trigger: "blur" },
        ],
        key: [{ required: true, message: "config.key为必选项", trigger: "blur" }],
        maximum: [
          { required: true, message: "过期时间为必选项", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.appCode = this.$route.query.appcode;
    this.appId = this.$route.query.appid;
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          const pluginParams = {
            HeaderNames: this.ruleForm.header,
            keyClaimName: this.ruleForm.key,
            TokenExpiration: this.ruleForm.maximum
          }
          const query = {
              pluginType: 'jwt',
              appCode: this.appCode,
              appId: this.appId,
              pluginParams:JSON.stringify(pluginParams)
            };
          postPlugin(query).then(res=>{
            if(res.code === 200) {
              this.$router.push({path:'/serve/serveDetail/' + this.appCode})
            }
          })
        } else {
          return false;
        }
      });
    },
    resetForm() {
      this.$router.push({ path: "/serve/serveDetail/" + this.appCode });
    },
    handleChange(value) {
      console.log(value);
    },
  },
};
</script>

<style>
</style>