<template>
  <div class="formBox">
    <el-form
      :model="ruleForm"
      :rules="rules"
      ref="ruleForm"
      label-position="top"
    >
      <el-form-item label="ip白名单" prop="white_list_ip">
        <el-input
          type="textarea"
          style="width: 48%"
          :autosize="{ minRows: 4, maxRows: 4 }"
          placeholder="请输入需要控制访问的IP,多个IP需要用英文分号分隔"
          v-model="ruleForm.white_list_ip"
        >
        </el-input>
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
        white_list_ip: "",
      },
      rules: {
        white_list_ip: [
          { required: true, message: "请输入ip或者域名", trigger: "blur" },
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
            pluginType: "white_list_ip",
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
  },
};
</script>

<style scoped lang='scss'>
.sysClass {
  position: absolute;
  right: 0px;
  top: 0px;
}
</style>