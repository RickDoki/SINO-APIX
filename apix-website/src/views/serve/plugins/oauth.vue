<template>
  <div class="formBox">
    <el-form
      :model="ruleForm"
      :rules="rules"
      ref="ruleForm"
      label-position="top"
    >
      <el-form-item label="Provision Key" prop="ProvisionKey">
        <el-input
          v-model="ruleForm.ProvisionKey"
          placeholder=""
          class="inputWidth"
          clearable
        >
        </el-input>
      </el-form-item>
      <el-form-item label="Token Expiration" prop="TokenExpiration">
        <el-input-number
          v-model="ruleForm.TokenExpiration"
          @change="handleChange"
          :min="1"
        ></el-input-number>
      </el-form-item>
      <el-form-item prop="timeout">
        <el-checkbox disabled v-model="ruleForm.EnableClientCredentials"
          >客户端认证</el-checkbox
        >
      </el-form-item>
      <el-form-item
        label="Refresh Token Expiration"
        prop="RefreshTokenExpiration"
      >
        <el-input-number
          v-model="ruleForm.RefreshTokenExpiration"
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
        ProvisionKey: "",
        EnableClientCredentials: true,
        TokenExpiration: "7200",
        RefreshTokenExpiration: "1209600",
      },
      rules: {
        ProvisionKey: [
          { required: true, message: "请输入ProvisionKey", trigger: "blur" },
        ],
        TokenExpiration: [
          { required: true, message: "请输入TokenExpiration", trigger: "blur" },
        ],
        RefreshTokenExpiration: [
          {
            required: true,
            message: "请输入RefreshTokenExpiration",
            trigger: "blur",
          },
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
          const query = {
            pluginType: "oauth2",
            appCode: this.appCode,
            appId: this.appId,
            pluginParams: JSON.stringify(this.ruleForm),
          };
          postPlugin(query).then((res) => {
            if (res.code === 200) {
              this.$router.push({ path: "/serve/serveDetail/" + this.appCode });
            }
          });
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