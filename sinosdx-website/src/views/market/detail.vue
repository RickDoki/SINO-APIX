<template>
  <div class="api_list_main">
    <el-dialog title="应用对接" :visible.sync="editDialogVisible" width="500px">
      <el-row>
        <el-col :span="20">
          <el-select v-model="value" placeholder="请选择应用">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-col>
      </el-row>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="leaseSure('ruleForm')"
          >确 定</el-button
        >
      </span>
    </el-dialog>
    <el-dialog title="MK文档" :visible.sync="MKDialogVisible">
      <mavon-editor
        class="md"
        :value="webDataString"
        :subfield="false"
        :defaultOpen="'preview'"
        :toolbarsFlag="false"
        :editable="false"
        :scrollStyle="true"
        :ishljs="true"
      />
      <span slot="footer" class="dialog-footer">
        <el-button @click="MKDialogVisible = false">关 闭</el-button>
      </span>
    </el-dialog>
    <div class="main_content">
      <el-form
        ref="ruleForm"
        :model="ruleForm"
        :rules="rules"
        label-width="100px"
        class="demo-ruleForm"
      >
        <el-form-item label="应用名称:" prop="appName">
          <el-input
            v-model="ruleForm.appName"
            style="width: 380px"
            maxlength="20"
            size="large"
            disabled
          />
        </el-form-item>
        <el-form-item label="APP Code:" prop="appCode">
          <el-input
            v-model="ruleForm.appCode"
            style="width: 380px"
            maxlength="20"
            size="large"
            disabled
          />
        </el-form-item>
        <el-form-item label="开发者:" prop="appCreationUser">
          <el-input
            v-model="ruleForm.appCreationUser"
            style="width: 380px"
            maxlength="20"
            size="large"
            disabled
          />
        </el-form-item>
        <el-form-item label="创建时间:" prop="appCreationDate">
          <el-input
            v-model="ruleForm.appCreationDate"
            style="width: 380px"
            maxlength="20"
            size="large"
            disabled
          />
        </el-form-item>
        <el-form-item prop="description" label="应用描述:">
          <el-input
            v-model="ruleForm.appDescription"
            type="textarea"
            style="width: 380px"
            :autosize="{ minRows: 5, maxRows: 6 }"
            maxlength="500"
            disabled
          />
        </el-form-item>
      </el-form>
      <div style="margin: 30px">应用上架流程</div>
      <el-steps style="margin-bottom: 30px" :active="6" align-center>
        <el-step
          title="步骤1"
          description="首先，您需要创建一个属于自己的应用"
        />
        <el-step title="步骤2" description="接下来创建一些API吧" />
        <el-step title="步骤3" description="发布您创建的API" />
        <el-step
          title="步骤4"
          description="将您已经发布的可用的API与应用进行绑定"
        />
        <el-step
          title="步骤5"
          description="将已经绑定好API的应用填写版本号上架到资源市场"
        />
        <el-step title="步骤6" description="在资源市场中看到您上架的应用" />
      </el-steps>
      <div style="padding-left: 50px">
        <el-button type="success" @click="lease">对接</el-button>
        <!-- <el-button type="primary" @click="views">应用文档</el-button> -->
        <el-button type="primary" @click="GoApiList">API列表</el-button>
        <el-button type="info" @click="GoBack">返回</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import { detail, list, AppLease } from "@/api/AboutApp";
import { getToken } from "@/utils/auth"; // get token from cookie
import { mavonEditor } from "mavon-editor";
import "mavon-editor/dist/css/index.css";
export default {
  components: {
    mavonEditor,
  },
  data() {
    return {
      ruleForm: {},
      webDataString: "",
      rules: {},
      appCode: "",
      editDialogVisible: false,
      MKDialogVisible: false,
      options: [],
      value: "",
    };
  },
  created() {
    this.appCode = this.$route.query.appCode;
    detail(this.appCode, "").then((res) => {
      console.log(res);
      if (res.code === 200) {
        this.ruleForm = res.data;
        this.webDataString = res.data.appMarkdown;
      }
    });
    this.getUserapp();
  },
  methods: {
    handleClose(done) {
      this.$confirm("确认关闭？")
        .then((_) => {
          done();
        })
        .catch((_) => {});
    },
    // 获取当前用户的应用
    getUserapp() {
      const developerId = getToken("userId_api");
      const query = "?developerId=" + developerId;
      this.options = [];
      list(query).then((res) => {
        if (res.code === 200) {
          res.data.appList.forEach((item) => {
            if (item.isPublished === "60001") {
            } else {
              this.options.push({
                value: item.appCode,
                label: item.appName,
              });
            }
          });
        }
      });
    },
    lease() {
      this.editDialogVisible = true;
    },
    // 确认对接
    leaseSure() {
      if (this.value === "") {
        this.messageERROR("请先选择应用");
        return false;
      } else {
        AppLease(this.value, this.appCode, {}).then((res) => {
          if (res.code === 200) {
            this.messageOK(res.msg);
            this.editDialogVisible = false;
          } else {
            this.messageERROR(res.msg);
          }
        });
      }
    },
    // 文档预览
    views() {
      this.MKDialogVisible = true;
    },
    // api列表
    GoApiList() {
      this.$router.push({ path: "/market/apiList?appCode=" + this.appCode });
    },
    GoBack() {
      this.$router.push("/market/dashboard");
    },
    // 成功消息
    messageOK(msg) {
      this.$message({
        message: msg,
        type: "success",
      });
    },
    // 失败消息
    messageERROR(msg) {
      this.$message({
        message: msg,
        type: "error",
      });
    },
  },
};
</script>

<style lang='scss' scoped>
.api_list_main {
  width: 95%;
  margin: 0 auto;
  margin-top: 20px;
  border-radius: 5px;
  background-color: #fff;
  overflow: hidden;
  .main_content {
    padding: 20px;
  }
}
</style>
