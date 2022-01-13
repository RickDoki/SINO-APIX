<template>
  <div class="formBox">
    <el-form
      :model="ruleForm"
      :rules="rules"
      ref="ruleForm"
      label-position="top"
    >
      <el-form-item label="来源 Origin" prop="allowOrigins">
        <el-input
          type="textarea"
          style="width: 48%"
          :autosize="{ minRows: 4, maxRows: 4 }"
          placeholder="允许所有填*,以http://或https://开头,多个Origin用英文逗号分隔"
          v-model="ruleForm.allowOrigins"
        >
        </el-input>
      </el-form-item>
      <el-form-item label="操作 Method" prop="allowMethods">
        <el-checkbox-group v-model="ruleForm.allowMethods" :min="1" :max="7">
          <el-checkbox
            v-for="methodsOptions in methodsOptions"
            :label="methodsOptions"
            :key="methodsOptions"
            >{{ methodsOptions }}</el-checkbox
          >
        </el-checkbox-group>
      </el-form-item>
      <el-form-item label="Allow-Headers" prop="allowHeaders">
        <el-input
          type="textarea"
          style="width: 48%"
          :autosize="{ minRows: 4, maxRows: 4 }"
          placeholder="请输入允许的Header,多个Header间用英文逗号分隔,允许所有的Header则填*"
          v-model="ruleForm.allowHeaders"
        >
        </el-input>
      </el-form-item>
      <el-form-item label="All-exposeHeaders" prop="exposeHeaders">
        <el-input
          type="textarea"
          style="width: 48%"
          :autosize="{ minRows: 4, maxRows: 4 }"
          placeholder="请输入允许暴露给XHLHttpRequest对象的Header,多个Header间用英文逗号分隔,允许所有的Header则填*"
          v-model="ruleForm.exposeHeaders"
        >
        </el-input>
      </el-form-item>
      <el-form-item>
        <el-checkbox v-model="ruleForm.allowCredentials"
          >允许Cookie</el-checkbox
        >
      </el-form-item>
      <el-form-item label="超时 Max-Age(s)" prop="maxAge">
        <el-input-number v-model="ruleForm.maxAge" :min="1"></el-input-number>
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
      methodsOptions: [
        "GET",
        "POST",
        "PUT",
        "DELETE",
        "HEAD",
        "OPTIONS",
        "PATCH",
      ],
      ruleForm: {
        allowOrigins: "",
        allowHeaders: "",
        exposeHeaders: "",
        allowMethods: [
          "GET",
          "POST",
          "PUT",
          "DELETE",
          "HEAD",
          "OPTIONS",
          "PATCH",
        ],
        allowCredentials: false,
        maxAge: "5",
      },
      rules: {
        allowOrigins: [
          { required: true, message: "请输入来源Origins", trigger: "blur" },
        ],
        allowHeaders: [
          { required: true, message: "请输入Allow-Headers", trigger: "blur" },
        ],
        exposeHeaders: [
          {
            required: true,
            message: "请输入来源exposeHeaders",
            trigger: "blur",
          },
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
          data.allowMethods = data.allowMethods.split(",");
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
            const pluginParams = {
              allowOrigins: this.ruleForm.allowOrigins,
              allowHeaders: this.ruleForm.allowHeaders,
              exposeHeaders: this.ruleForm.exposeHeaders,
              allowMethods: this.ruleForm.allowMethods.toString(),
              allowCredentials: this.ruleForm.allowCredentials,
              maxAge: this.ruleForm.maxAge,
            };
            const query = {
              pluginType: "cors",
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
              allowOrigins: this.ruleForm.allowOrigins,
              allowHeaders: this.ruleForm.allowHeaders,
              exposeHeaders: this.ruleForm.exposeHeaders,
              allowMethods: this.ruleForm.allowMethods.toString(),
              allowCredentials: this.ruleForm.allowCredentials,
              maxAge: this.ruleForm.maxAge,
            };
            const query = {
              pluginType: "cors",
              appCode: this.appCode,
              appId: this.appId,
              id: this.id,
              enabled: this.enabled,
              pluginParams: JSON.stringify(pluginParams),
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