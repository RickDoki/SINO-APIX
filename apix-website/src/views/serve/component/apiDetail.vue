<template>
  <div class="he_main">
    <el-row style="height: 100%">
      <el-col style="height: 100%" :span="4">
        <div class="apiList">
          <el-select
            v-model="apiValue"
            size="mini"
            placeholder="请选择"
            @change="apiValueChange"
          >
            <el-option
              v-for="item in list"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
          <p
            :class="classList[index]"
            v-for="(item, index) in defaultApiList"
            :key="index"
            @click="choseApi(item, index)"
          >
            {{ item.apiName }}
          </p>
        </div>
      </el-col>
      <el-col style="height:100%" :span="20">
        <div class="apiMessage">
          <div class="api-info">
            <span class="title">{{ applicationVersion.version }}
              <el-button type="text" @click="goDocxV">详细文档</el-button>
            </span>
          </div>
          <div class="api-info">
            <span class="label-color">版本描述 : </span>
            <span class="conten-color">{{ applicationVersion.description }}</span>
          </div>
          <el-divider></el-divider>
          <div class="api-info">
            <div class="title">{{ apiMessageAll.apiName }}
              <el-button type="text" @click="goDocx">详细文档</el-button>
            </div>
            <div class="secondTitle">{{ apiMessageAll.description }}</div>
          </div>
          <div class="api-info">
            <span class="label-color">调用路径 : </span>
            <span class="conten-color">{{ gatewayDomain + apiMessageAll.url }}</span>
            <i
              v-clipboard:copy="gatewayDomain + apiMessageAll.url"
              v-clipboard:success="onCopy"
              v-clipboard:error="onError"
              class="el-icon-copy-document icon-color"
            />
          </div>
          <div class="api-info">
            <span class="label-color agrement">协议类型 : </span>
            <span class="conten-color">{{ apiMessageAll.protocol }}</span>
          </div>
          <div class="api-info">
            <span class="label-color">请求方式 : </span>
            <span class="conten-color">{{ apiMessageAll.requestMethod }}</span>
          </div>
          <!-- <div class="api-info">
            <span class="label-color">返回类型 : </span>
            <span class="conten-color">JSON</span>
          </div> -->
          <div class="api-info">
            <span class="label-color">请求参数 : </span>
            <div class="table_box table_top">
              <el-table
                :data="requestParamsTable"
                empty-text="暂无数据"
                :row-style="{ height: '50px' }"
                highlight-current-row
                :header-cell-style="{ 'font-weight': 400, color: '#494E6A' }"
              >
                <el-table-column prop="parame" label="名称" />
                <el-table-column prop="type" label="类型" />
                <el-table-column prop="isHaveto" label="是否必选" />
                <el-table-column prop="describe" label="描述" />
                <el-table-column prop="default" label="默认值" />
              </el-table>
            </div>
          </div>
          <div class="api-info">
            <span class="label-color">请求示例 : </span>
            <div class="table_top">
              <prism-editor
                readonly
                v-model="requestExample"
                class="my-editor height-300"
                :highlight="highlighter"
                :line-numbers="lineNumbers"
              />
            </div>
          </div>
          <div class="api-info">
            <span class="label-color">返回参数 : </span>
            <div class="table_box table_top">
              <el-table
                :data="responseParamsTable"
                empty-text="暂无数据"
                :row-style="{ height: '50px' }"
                highlight-current-row
                :header-cell-style="{ 'font-weight': 400, color: '#494E6A' }"
              >
                <el-table-column prop="parame" label="名称" />
                <el-table-column prop="type" label="类型" />
                <el-table-column prop="isHaveto" label="是否必选" />
                <el-table-column prop="describe" label="描述" />
                <el-table-column prop="default" label="默认值" />
              </el-table>
            </div>
          </div>
          <div class="api-info">
            <span class="label-color">返回示例 : </span>
            <div class="table_top">
              <prism-editor
                readonly
                v-model="responseExample"
                class="my-editor height-300"
                :highlight="highlighter"
                :line-numbers="lineNumbers"
              />
            </div>
          </div>
          <div class="api-info">
            <span class="label-color">状态码 : </span>
            <div class="table_box table_top">
              <el-table
                :data="statusTable"
                empty-text="暂无数据"
                :row-style="{ height: '50px' }"
                highlight-current-row
                :header-cell-style="{ 'font-weight': 400, color: '#494E6A' }"
              >
                <el-table-column prop="statusCode" label="状态码" width="200" />
                <el-table-column prop="describe" label="描述" />
              </el-table>
            </div>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { queryApiList, apiMessage } from "@/api/AboutServe.js";
import { PrismEditor } from 'vue-prism-editor'
import 'vue-prism-editor/dist/prismeditor.min.css' // import the styles somewhere
import { highlight, languages } from 'prismjs/components/prism-core'
import 'prismjs/components/prism-clike'
import 'prismjs/components/prism-javascript'
import 'prismjs/themes/prism-tomorrow.css' // import syntax highlighting styles

export default {
  components: {
    PrismEditor
  },
  data () {
    return {
      lineNumbers: true,
      requestParamsTable: [],
      responseParamsTable: [],
      requestExample: '',
      responseExample: '',
      applicationVersion: {},
      apiValue: "",
      classList: [],
      appCode: "",
      apiMessageAll: {},
      statusTable: [
        {
          statusCode: 200,
          describe: "操作成功",
        },
        {
          statusCode: 400,
          describe: "操作失败",
        },
      ],
    };
  },
  props: ["list", "defaultApiList", "gatewayDomain"],
  created () {
    this.appCode = this.$route.params.appCode;
  },
  mounted () { },
  methods: {
    goDocxV() {
      this.$router.push({
        name: 'openServedocxDetail',
        query: {
          name: this.applicationVersion.version,
          id: this.apiValue
        },
        params: {
          type: 'version'
        }
      })
    },
    goDocx() {
      this.$router.push({
        name: 'openServedocxDetail',
        query: {
          name: this.apiMessageAll.apiName,
          id: this.apiMessageAll.apiId
        },
        params: {
          type: 'api'
        }
      })
    },
    choseApi (e, i) {
      this.classList = [];
      for (let index = 0; index < this.defaultApiList.length; index++) {
        if (index === i) {
          this.classList.push("hitClass");
        } else {
          this.classList.push("nohit");
        }
      }
      // 查询选中api详情
      console.log(e);
      const query = e.apiId;
      this.getapiMessage(query);
    },
    highlighter (code) {
      return highlight(code, languages.js)
    },
    getapiMessage (e) {
      apiMessage(e).then((res) => {
        if (res.code === 200) {
          this.apiMessageAll = res.data;
          this.requestParamsTable = JSON.parse(res.data.requestParams);
          this.responseParamsTable = JSON.parse(res.data.responseParams)
          this.requestExample = JSON.parse(res.data.requestExample)
          this.responseExample = JSON.parse(res.data.responseExample)
        }
      });
    },
    apiValueChange () {
      const query = {
        appCode: this.appCode,
        appVersionId: this.apiValue,
      };
      queryApiList(query).then((res) => {
        if (res.code === 200) {
          this.$emit("changeVersion", res.data.apiList);
          this.applicationVersion = res.data.applicationVersion
        }
      });
    },
    // 粘贴复制
    onCopy () {
      this.$message("复制成功");
    },
    onError () {
      this.$message("复制失败");
    },
  },
  watch: {
    list () {
      this.apiValue = this.list[0].value;
      this.apiValueChange()
    },
    defaultApiList () {
      this.classList = [];
      for (let index = 0; index < this.defaultApiList.length; index++) {
        // this.classList.push('hitClass')
        if (index === 0) {
          this.classList.push("hitClass");
        } else {
          this.classList.push("nohit");
        }
      }
      console.log(this.defaultApiList);
      const query = this.defaultApiList[0].apiId;
      this.getapiMessage(query);
    },
  },
};
</script>

<style scoped lang='scss'>
.my-editor {
  background: #f4f6ff;
  color: #373753;
  border: 0px;
  font-family: Fira code, Fira Mono, Consolas, Menlo, Courier, monospace;
  font-size: 14px;
  line-height: 1.5;
  padding: 5px;
}
/* optional */
.prism-editor__textarea:focus {
  outline: none;
}
/* not required: */
.height-300 {
  height: 150px;
}
.he_main {
  // min-height: inherit;
  height: 100%;
  overflow: hidden;
}
.apiList {
  padding: 20px 0px;
}
.apiMessage {
  overflow: auto;
  border-left: 1px solid #e1e6ee;
  padding: 24px;
  margin-left: 16px;
  height: 100%;
  padding-bottom: 150px;
  // margin-bottom: 20px;
  .api-info {
    margin-bottom: 24px;
    .title {
      line-height: 22px;
      font-size: 16px;
      font-weight: bold;
      color: #1d1c35;
    }
    .secondTitle {
      margin-top: 6px;
      color: #727491;
    }
  }
}
.apiMessage::-webkit-scrollbar {
  display: none;
}
.hitClass {
  cursor: pointer;
  color: #2650ff;
}
.nohit {
  cursor: pointer;
}
</style>