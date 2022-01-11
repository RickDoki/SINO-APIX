<template>
  <div class="main">
    <div class="list_top">
      <div class="list_title">{{ applicationVersion.version }}</div>
      <div class="list_search">
        <div class="but-left">
          <el-dropdown @command="handleCommand">
            <el-button type="primary" size="small">
              操作<i class="el-icon-arrow-down el-icon--right"></i>
            </el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item command="del">删除版本</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
        <el-button type="primary" size="small" @click="docsEdit()">编辑文档</el-button>
      </div>
    </div>
    <div class="secondTitle">{{ applicationVersion.description }}</div>
    <div class="status">
      <div class="left-span">
        <span>已关联api数量: </span>
        <span class="divbox">{{ table.length }}个</span>
      </div>
      <div class="time">
        <div>
          <span>创建时间 : </span>
          <span>{{ applicationVersion.creationDate }}</span>
        </div>
        <div>
          <span>更新时间 : </span>
          <span>{{ applicationVersion.lastUpdateDate }}</span>
        </div>
      </div>
    </div>
    <div class="table_box mode-margin">
      <!-- <p>版本</p> -->
      <div class="serve-table">
        <div class="table-tile">已关联API</div>
        <el-button
          plain
          type="primary"
          size="small"
          @click="gonewEdition"
          class="add-but"
          >关联API
        </el-button>
      </div>
      <el-table
        :data="table"
        empty-text="暂无数据"
        :row-style="{ height: '50px' }"
        highlight-current-row
        :header-cell-style="{
          'font-weight': 400,
          'font-size': '16px',
          color: '#1D1C35',
        }"
      >
        <el-table-column prop="name" label="API名称" />
        <el-table-column prop="protocol" label="协议" />
        <el-table-column prop="domain" label="域名" />
        <el-table-column prop="url" label="路径" />
        <el-table-column prop="description" label="API描述" />
      </el-table>
    </div>
    <el-drawer
      title="关联"
      size="40%"
      :visible.sync="drawer"
      :direction="direction"
      :before-close="handleClose"
    >
      <div class="formBox" style="margin-left: 20px">
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
              class="inputWidth drawer"
              :disabled="true"
              clearable
            >
            </el-input>
          </el-form-item>
          <el-form-item label="版本描述" prop="describe">
            <el-input
              v-model="ruleForm.describe"
              placeholder=""
              class="inputWidth drawer"
              :disabled="true"
              clearable
            >
            </el-input>
          </el-form-item>
          <el-form-item label="关联API" prop="API">
            <el-select
              multiple
              class="inputWidth drawer"
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
          </el-form-item>
          <div @click="createdApi" style="margin-top: 30px" class="show-but">
            还没有API?去创建 >>
          </div>
          <div style="margin: 0 auto" class="bottom_button_a">
            <el-button size="small" @click="resetForm('ruleForm')"
              >取消</el-button
            >
            <el-button
              size="small"
              type="primary"
              @click="submitForm('ruleForm')"
              >创建</el-button
            >
          </div>
        </el-form>
      </div>
    </el-drawer>
  </div>
</template>

<script>
import {
  queryApiList,
  apiList,
  changeAppversion,
  delApiversion,
} from "@/api/AboutServe.js";
import { getToken } from "@/utils/auth"; // get token from cookie
import "./../mainCss/index.scss";
export default {
  data () {
    return {
      drawer: false,
      direction: "rtl",
      table: [],
      ruleForm: {
        name: "",
        describe: "",
        API: [],
      },
      options: [],
      rules: {
        API: [{ required: true, message: "请选择API", trigger: "blur" }],
      },
      appCode: "",
      appVersionId: "",
      applicationVersion: {},
    };
  },
  created () {
    this.developerId = getToken("userId_api");
    this.getApiList();
    this.appCode = this.$route.query.appCode;
    this.appVersionId = this.$route.query.appVersionId;
    this.getqueryApiList();
  },
  methods: {
    // 编辑版本文档
    docsEdit () {
      this.$router.push('/docsEdit/' + 'version?id=' + this.appVersionId + '&name=' + this.version)
    },
    // 查询关联apilist
    getqueryApiList () {
      const query = {
        appCode: this.appCode,
        appVersionId: this.appVersionId,
      };
      queryApiList(query).then((res) => {
        if (res.code === 200) {
          this.table = res.data.apiList;
          this.applicationVersion = res.data.applicationVersion;
          this.ruleForm.name = res.data.applicationVersion.version;
          this.ruleForm.describe = res.data.applicationVersion.description;
          this.ruleForm.API = []
          for (let index = 0; index < res.data.apiList.length; index++) {
            this.ruleForm.API.push(res.data.apiList[index].id);
          }
        }
      });
    },
    // 查询全部api
    getApiList () {
      apiList(this.developerId).then((res) => {
        if (res.code === 200) {
          this.options = res.data.apiList;
        }
      });
    },
    // 去创建api
    createdApi () {
      this.$router.push({ path: '/api/add' })
    },
    handleClose (done) {
      done();
    },
    // 取消侧面
    resetForm () {
      this.drawer = false;
    },
    gonewEdition () {
      this.drawer = true;
    },
    // 版本关联api
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // alert("submit!");
          const query = {
            apiIds: this.ruleForm.API.toString(),
          };
          changeAppversion(this.appVersionId, query).then((res) => {
            if (res.code === 200) {
              this.drawer = false;
              this.getqueryApiList();
            }
          });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    // 删除版本
    handleCommand (command) {
      // console.log(command)
      if (command === "del") {
        delApiversion(this.appVersionId).then((res) => {
          if (res.code === 200) {
            // this.getServeDeatil();
            // 删除成功跳转详情页
            this.$router.push({ path: '/serve/serveDetail/' + this.appCode })
          }
        });
      }
    },
  },
};
</script>

<style scoped lang='scss'>
.mode-margin {
  margin-top: 24px;
}
.serve-table {
  display: flex;
  justify-content: space-between;
  .table-tilelong {
    line-height: 30px;
  }
}
.drawer {
  width: 80% !important;
}
.divbox {
  display: inline-block;
  margin-left: 5px;
  padding: 0px 5px;
  background-color: #e1e6ee;
  color: #6c757d;
  border-radius: 3px;
}
</style>