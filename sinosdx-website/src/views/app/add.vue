<template>
  <div class="api_list_main">
    <div class="main_content">
      <el-card>
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
              show-word-limit
            />
          </el-form-item>
          <el-form-item label="归属应用:" prop="Productid">
            <el-select
              style="width: 380px"
              v-model="ruleForm.Productid"
              placeholder="请选择"
            >
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
            <!-- <el-input
            v-model="ruleForm.Productid"
            style="width: 380px"
            maxlength="10"
            size="large"
            show-word-limit
          /> -->
          </el-form-item>
          <el-form-item label="应用标签:">
            <el-tag
              :key="tag"
              v-for="tag in ruleForm.label"
              closable
              :disable-transitions="false"
              @close="handleClose(tag)">
              {{tag}}
            </el-tag>
            <el-input
              class="input-new-tag"
              v-if="inputVisible"
              v-model="inputValue"
              ref="saveTagInput"
              size="small"
              @keyup.enter.native="handleInputConfirm"
              @blur="handleInputConfirm"
            >
            </el-input>
            <el-button v-else class="button-new-tag" size="small" :disabled="ruleForm.label.length > 4" @click="showInput">+ 添加</el-button>
          </el-form-item>
          <el-form-item prop="description" label="应用描述:">
            <el-input
              v-model="ruleForm.description"
              type="textarea"
              style="width: 380px"
              :autosize="{ minRows: 3, maxRows: 5 }"
              maxlength="500"
              show-word-limit
            />
          </el-form-item>
          <div style="margin-top:50px; margin-left:9%">
            <el-button
              v-if="!isChange"
              type="primary"
              size="mini"
              @click="submitForm('ruleForm')"
              >立即创建</el-button
            >
            <el-button
              v-if="isChange"
              type="primary"
              @click="backList('ruleForm')"
              size="mini"
              >返回列表</el-button
            >
            <el-button
              v-if="isChange"
              size="mini"
              type="primary"
              @click="sureEdit('ruleForm')"
              >确认修改</el-button
            >
          </div>
        </el-form>
      </el-card>
      <el-card style="margin-top:20px">
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
      </el-card>
    </div>
  </div>
</template>

<script>
import { create, appEdit, getOption } from "@/api/AboutApp";
export default {
  data() {
    const checkMobile = (rule, value, callback) => {
      // 验证手机号的正则表达式
      const regMobile = /^[a-z]+[a-z0-9-]{0,10}$/;
      if (regMobile.test(value)) {
        return callback();
      }
      callback(
        new Error("1-10个字符，可使用小写字母、数字、中划线，需以小写字母开头")
      );
    };
    return {
      inputVisible: false,
      inputValue: '',
      ruleForm: {
        appName: '',
        description: '',
        Productid: '',
        label: []
        // appCode:'123456'
      },
      options: [],
      isChange: false,
      appId: '',
      rules: {
        appName: [
          { required: true, message: "请输入应用名称", trigger: "change" },
          {
            min: 3,
            max: 20,
            message: "长度在 3 到 20 个字符",
            trigger: "change",
          }
        ],
        description: [
          { required: true, message: "请输入应用描述", trigger: "change" }
        ]
      }
    }
  },
  created() {
    this.getoptions()
    if (this.$route.query.message) {
      this.isChange = true
      const message = JSON.parse(this.$route.query.message)
      this.ruleForm = {
        Productid: message.Productid,
        description: message.description,
        appName: message.appName,
        label: message.label
      }
      this.appId = message.appCode
    }
  },
  methods: {
    handleClose(tag) {
      this.ruleForm.label.splice(this.ruleForm.label.indexOf(tag), 1)
    },

    showInput() {
      this.inputVisible = true
      this.$nextTick(_ => {
        this.$refs.saveTagInput.$refs.input.focus()
      })
    },

    handleInputConfirm() {
      const inputValue = this.inputValue
      if (inputValue) {
        this.ruleForm.label.push(inputValue)
      }
      this.inputVisible = false
      this.inputValue = ''
    },
    // 获取归属
    getoptions() {
      getOption().then((res) => {
        console.log(res.data);
        this.options = res.data;
      });
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          //   alert("submit!");
          create(this.ruleForm).then((res) => {
            if (res.code === 200) {
              this.$message({
                message: res.msg,
                type: "success",
              });
              this.$router.push("/app/list");
            } else {
              this.$message({
                message: res.msg,
                type: "error",
              });
            }
          });
        } else {
          return false;
        }
      });
    },
    backList() {
      this.$router.push("/app/list");
    },
    sureEdit(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          appEdit(this.appId, this.ruleForm).then((res) => {
            if (res.code === 200) {
              this.$message({
                message: res.msg,
                type: "success",
              });
              this.$router.push("/app/list");
            } else {
              this.$message({
                message: res.msg,
                type: "error",
              });
            }
          });
        }
      });
    },
  },
};
</script>

<style lang='scss' scoped>
.el-tag {
  margin-right: 10px;
}
.button-new-tag {
  width: 90px;
  height: 32px;
  line-height: 30px;
  padding-top: 0;
  padding-bottom: 0;
}
.input-new-tag {
  width: 150px;
  vertical-align: bottom;
}
.api_list_main {
  width: 98%;
  margin: 0 auto;
  margin-top: 10px;
  border-radius: 5px;
  overflow: hidden;
  .main_content {
    padding-bottom: 20px;
  }
}
</style>
