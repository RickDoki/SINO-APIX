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
        <el-button
          size="small"
          type="primary"
          @click="submitForm('ruleForm')"
          >{{ buttonFont }}</el-button
        >
        <el-button size="small" @click="resetForm('ruleForm')">取消</el-button>
      </div>
    </el-form>
  </div>
</template>

<script>
import { postPlugin, getPlugin, putPlugin } from "@/api/AboutServe.js";

export default {
  data() {
    return {
      appCode: "",
      appId: "",
      id: "",
      pluginParams: {},
      enabled: 0,
      ruleForm: {
        header: "authorizatuion",
        key: "iss",
        maximum: "7200000",
      },
      buttonFont: "添加",
      rules: {
        header: [
          { required: true, message: "config.Header为必选项", trigger: "blur" },
        ],
        key: [
          { required: true, message: "config.key为必选项", trigger: "blur" },
        ],
        maximum: [
          { required: true, message: "过期时间为必选项", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.appCode = this.$route.query.appcode;
    this.appId = this.$route.query.appid;
    // 是否为配置插件
    if (this.$route.query.pluginParams) {
      this.id = this.$route.query.id;
      //查询当前插件详情
      getPlugin(this.id, this.appCode).then((res) => {
        if (res.code === 200) {
          this.enabled = res.data.enabled
          const data = JSON.parse(res.data.pluginParams);
          this.ruleForm = {
            header: data.HeaderNames,
            key: data.keyClaimName,
            maximum: data.TokenExpiration,
          };
        }
      });
      this.buttonFont = "应用";
    } else {
      this.buttonFont = "添加";
    }
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.buttonFont === "添加") {
            const pluginParams = {
              HeaderNames: this.ruleForm.header,
              keyClaimName: this.ruleForm.key,
              TokenExpiration: this.ruleForm.maximum,
            };
            const query = {
              pluginType: "jwt",
              appCode: this.appCode,
              appId: this.appId,
              pluginParams: JSON.stringify(pluginParams),
            };
            postPlugin(query).then((res) => {
              if (res.code === 200) {
                this.$router.push({
                  path: "/serve/serveDetail/" + this.appCode,
                });
              }
            });
          } else {
            const pluginParams = {
              HeaderNames: this.ruleForm.header,
              keyClaimName: this.ruleForm.key,
              TokenExpiration: this.ruleForm.maximum,
            };
            const query = {
              pluginType: "jwt",
              id:this.id,
              appCode: this.appCode,
              appId: this.appId,
              enabled:this.enabled,
              pluginParams: JSON.stringify(pluginParams),
            };
            console.log(query)
            putPlugin(query).then((res) => {
              if (res.code === 200) {
                this.$router.push({
                  path: "/serve/serveDetail/" + this.appCode,
                });
              }
            });
          }
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