<template>
  <div class="formBox">
    <el-form
      :model="ruleForm"
      :rules="rules"
      ref="ruleForm"
      label-position="top"
    >
      <el-form-item label="config.key" prop="appKey">
        <el-input
          v-model="ruleForm.appKey"
          placeholder=""
          class="inputWidth"
          clearable
        >
        </el-input>
        <span @click="getrandomKey" class="show-but">系统生成</span>
      </el-form-item>
      <el-form-item label="config.Secret" prop="appSecret">
        <el-input
          v-model="ruleForm.appSecret"
          placeholder=""
          class="inputWidth"
          clearable
        >
        </el-input>
      </el-form-item>
      <el-form-item label="再次输入Secret" prop="appSecret2">
        <el-input
          v-model="ruleForm.appSecret2"
          placeholder=""
          class="inputWidth"
          clearable
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
import {
  postPlugin,
  randomKey,
  getPlugin,
  putPlugin,
} from "@/api/AboutServe.js";

export default {
  data() {
    var validatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入请输入config.Secret"));
      } else if (value !== this.ruleForm.appSecret) {
        callback(new Error("两次输入请输入config.Secret不一致!"));
      } else {
        callback();
      }
    };
    return {
      appCode: "",
      appId: "",
      id: "",
      enabled: 0,
      buttonFont: "添加",
      ruleForm: {
        appKey: "",
        appSecret: "",
        appSecret2: "",
      },
      rules: {
        appSecret2: [{ validator: validatePass, trigger: "blur" }],
        appSecret: [
          { required: true, message: "请输入config.key", trigger: "blur" },
        ],
        appKey: [
          { required: true, message: "请输入config.Secret", trigger: "blur" },
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
          // const secret = data.appSecret
          this.ruleForm = {
            appKey: data.appKey,
            appSecret: data.appSecret,
            appSecret2: data.appSecret,
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
              appKey: this.ruleForm.appKey,
              appSecret: this.ruleForm.appSecret,
            };
            const query = {
              pluginType: "sign",
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
              appKey: this.ruleForm.appKey,
              appSecret: this.ruleForm.appSecret,
            };
            const query = {
              pluginType: "sign",
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
    // 获取随机值
    getrandomKey() {
      randomKey().then((res) => {
        if (res.code === 200) {
          console.log(res);
          this.ruleForm.appKey = res.data;
        }
      });
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