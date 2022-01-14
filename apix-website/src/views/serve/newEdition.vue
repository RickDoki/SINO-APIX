<template>
  <div class="main">
    <div class="list_top">
      <div class="list_title">添加新本版本</div>
    </div>
    <div>
      <span class="secondTitle">向现有的服务添加新版本</span>
    </div>
    <div class="formBox">
      <el-form
        :model="ruleForm"
        :rules="rules"
        ref="ruleForm"
        label-position="top"
      >
        <el-form-item label="版本名称" prop="name">
          <el-input
            v-model="ruleForm.name"
            placeholder=""
            class="inputWidth"
            clearable
          >
          </el-input>
        </el-form-item>
        <el-form-item label="版本描述" prop="describe">
          <el-input
            v-model="ruleForm.describe"
            placeholder=""
            class="inputWidth"
            clearable
          >
          </el-input>
        </el-form-item>
        <el-form-item label="关联API" prop="API">
          <el-select
            class="inputWidth"
            multiple
            v-model="ruleForm.API"
            placeholder="请选择"
          >
            <el-option
              v-for="item in options"
              :key="item.apiId"
              :label="item.apiName"
              :value="item.apiId"
            >
            </el-option>
          </el-select>
          <span @click="createdApi" class="show-but">还没有API?去创建 >> </span>
        </el-form-item>
        <div class="bottom_button_a">
          <el-button size="small" @click="resetForm('ruleForm')"
            >取消</el-button
          >
          <el-button size="small" type="primary" @click="submitForm('ruleForm')"
            >创建</el-button
          >
        </div>
      </el-form>
    </div>
    <created-api
      :drawerProps="drawerIsshow"
      @showChange="showChange"
    ></created-api>
  </div>
</template>

<script>
import "./../mainCss/index.scss";
import createdApi from "./component/createdApi.vue";
import { apiList, publish } from "@/api/AboutServe.js";
import { getToken } from "@/utils/auth"; // get token from cookie

export default {
  components: {
    createdApi,
  },
  data() {
    return {
      drawerIsshow: false,
      developerId: "",
      appCode: "",
      ruleForm: {
        name: "",
        describe: "",
        API: [],
      },
      options: [],
      rules: {
        name: [{ required: true, message: "请输入版本名称", trigger: "blur" }],
        describe: [
          { required: true, message: "请输入版本描述", trigger: "blur" },
        ],
        API: [{ required: true, message: "请选择关联API", trigger: "blur" }],
      },
    };
  },
  created() {
    // 获取appcode
    this.appCode = this.$route.query.appcode;
    this.developerId = getToken("userId_api");
    this.getApiList();
  },
  methods: {
    resetForm() {
      this.$router.go(-1);
    },
    // 获取apilist
    getApiList() {
      apiList(this.developerId).then((res) => {
        if (res.code === 200) {
          // console.log(res)
          this.options = res.data.apiList;
        }
      });
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          const query = {
            appVersion: this.ruleForm.name,
            versionDesc: this.ruleForm.describe,
            apiIds: this.ruleForm.API.toString(),
          };
          publish(this.appCode, query).then((res) => {
            if (res.code === 200) {
              this.$router.push({ path: "/serve/serveDetail/" + this.appCode });
            }
          });
        } else {
          return false;
        }
      });
    },
    createdApi() {
      this.drawerIsshow = true;
    },
    showChange() {
      // console.log('change')
      this.drawerIsshow = false;
      setTimeout(() => {
        this.getApiList();
      }, 100);
    },
  },
};
</script>

<style scoped lang='scss'>
</style>