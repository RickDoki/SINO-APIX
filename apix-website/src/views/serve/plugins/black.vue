<template>
  <div class="formBox">
    <el-form
      :model="ruleForm"
      :rules="rules"
      ref="ruleForm"
      label-position="top"
    >
      <el-form-item label="ip黑名单" prop="black_list_ip">
        <el-input
          type="textarea"
          style="width: 48%"
          :autosize="{ minRows: 4, maxRows: 4 }"
          placeholder="请输入需要控制访问的IP,多个IP需要用英文分号分隔"
          v-model="ruleForm.black_list_ip"
        >
        </el-input>
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
      enabled: 0,
      buttonFont: "添加",
      ruleForm: {
        black_list_ip: "",
      },
      rules: {
        black_list_ip: [
          { required: true, message: "请输入ip或者域名", trigger: "blur" },
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
          this.enabled = res.data.enabled;
          const data = JSON.parse(res.data.pluginParams);
          this.ruleForm = data;
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
            const query = {
              pluginType: "black_list_ip",
              appCode: this.appCode,
              appId: this.appId,
              pluginParams: JSON.stringify(this.ruleForm),
            };
            postPlugin(query).then((res) => {
              if (res.code === 200) {
                this.$router.push({
                  path: "/serve/serveDetail/" + this.appCode,
                });
              }
            });
          } else {
            const query = {
              pluginType: "black_list_ip",
              id: this.id,
              enabled: this.enabled,
              appCode: this.appCode,
              appId: this.appId,
              pluginParams: JSON.stringify(this.ruleForm),
            };
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