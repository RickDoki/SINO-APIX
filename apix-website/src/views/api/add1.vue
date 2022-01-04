<template>
  <div class="api_list_main">
    <div class="main_content">
      <el-form
        ref="ruleForm"
        :model="ruleForm"
        :rules="rules"
        label-width="150px"
        class="demo-ruleForm"
      >
        <el-form-item label="API名称:" prop="apiName">
          <el-input
            v-model="ruleForm.apiName"
            size="large"
            maxlength="20"
            style="width: 380px"
            show-word-limit
          />
        </el-form-item>
        <el-form-item label="API路径:" prop="apiUrl">
          <el-input
            :disabled="isTempalte"
            v-model="ruleForm.apiUrl"
            size="large"
            style="width: 380px"
            maxlength="500"
            show-word-limit
          />
        </el-form-item>
        <el-form-item label="请求方式:" prop="requestMethod">
          <el-select
            v-model="ruleForm.requestMethod"
            :disabled="isTempalte"
            size="large"
            style="width: 380px"
            placeholder=""
          >
            <el-option label="GET" value="GET" />
            <el-option label="POST" value="POST" />
            <el-option label="PUT" value="PUT" />
            <el-option label="DELETE" value="DELETE" />
          </el-select>
        </el-form-item>
        <el-form-item label="API版本号:" prop="apiVersion">
          <el-input
            v-model="ruleForm.apiVersion"
            size="large"
            maxlength="20"
            style="width: 380px"
            show-word-limit
          />
        </el-form-item>
        <el-form-item label="API地址:" prop="domain">
          <el-input
            v-model="ruleForm.domain"
            size="large"
            maxlength="500"
            style="width: 380px"
            show-word-limit
          />
        </el-form-item>
        <el-form-item label="API前置路径:" prop="prefixPath">
          <el-input
            v-model="ruleForm.prefixPath"
            size="large"
            maxlength="500"
            style="width: 380px"
            show-word-limit
          />
        </el-form-item>
        <el-form-item label="是否为中台接口:">
          <el-select
            v-model="ruleForm.isInternal"
            size="large"
            style="width: 380px"
            placeholder=""
          >
            <el-option label="是" value="1" />
            <el-option label="否" value="0" />
          </el-select>
        </el-form-item>
        <el-form-item label="API描述:">
          <el-input
            v-model="ruleForm.description"
            type="textarea"
            style="width: 380px"
            :autosize="{ minRows: 8, maxRows: 15 }"
            maxlength="500"
            show-word-limit
          />
        </el-form-item>
        <el-form-item label="请求参数:">
          <!-- <el-tooltip class="item" effect="dark" content="可通过右键" placement="top">
              <el-button>上边</el-button>
              <svg-icon style="cursor:pointer;color:red" icon-class="wenhao" />
            </el-tooltip> -->
          <div @contextmenu="showMenu">
            <vxe-table
              ref="xTable"
              border
              show-overflow
              :data="requestParams"
              :edit-config="{ trigger: 'click', mode: 'cell' }"
            >
              <vxe-table-column type="checkbox" width="60" />
              <vxe-table-column
                field="parame"
                title="参数"
                :edit-render="{ name: 'input' }"
              />
              <vxe-table-column
                field="type"
                title="类型"
                :edit-render="{
                  name: '$select',
                  options: [
                    { value: 'Integer', label: 'Num' },
                    { value: 'String', label: 'String' },
                    { value: 'Object', label: 'Object' },
                    { value: 'Float', label: 'Float' },
                    { value: 'List', label: 'List' },
                    { value: 'Long', label: 'Long' },
                    { value: 'Boolean', label: 'Boolean' },
                    { value: 'Integer', label: 'Integer' },
                  ],
                }"
              />
              <vxe-table-column
                field="isHaveto"
                title="是否必选"
                :edit-render="{
                  name: '$select',
                  options: [
                    { value: '是', label: '是' },
                    { value: '否', label: '否' },
                  ],
                }"
              />
              <vxe-table-column
                field="describe"
                title="描述"
                :edit-render="{ name: 'input' }"
              />
              <vxe-table-column
                field="default"
                title="默认值"
                :edit-render="{ name: 'input' }"
              />
            </vxe-table>
            <div class="table-button">
              <el-button type="primary" size="mini" @click="savedata">
                新增
              </el-button>
              <el-button type="danger" size="mini" @click="newdata">
                删除
              </el-button>
            </div>

            <!-- <vue-context-menu
              :contextMenuData="contextMenuData"
              @savedata="savedata"
              @newdata="newdata"
            /> -->
          </div>
        </el-form-item>
        <el-form-item label="请求示例:">
          <prism-editor
            class="my-editor height-300"
            v-model="requestExample"
            :highlight="highlighter"
            :line-numbers="lineNumbers"
          />
        </el-form-item>
        <el-form-item label="返回示例:">
          <prism-editor
            class="my-editor height-300"
            v-model="responseExample"
            :highlight="highlighter"
            :line-numbers="lineNumbers"
          />
        </el-form-item>
        <!-- <el-form-item label="API参数:">
          <editor-vue></editor-vue>
          <mavon-editor
            ref="editor"
            v-model="ruleForm.markdown"
            :toolbars="toolbars"
            :subfield="false"
          />
        </el-form-item> -->
      </el-form>
      <div class="main_bottom">
        <el-button
          v-if="!isChange"
          type="primary"
          @click="submitForm('ruleForm')"
          >发布</el-button
        >
        <el-button v-if="isChange" @click="backList"> 返回列表 </el-button>
        <el-button type="primary" v-if="isChange" @click="sureEdit">
          确认修改
        </el-button>
      </div>
    </div>
  </div>
</template>

<script>
// import editorVue from "@/components/mavonEditor";
import "mavon-editor/dist/css/index.css";
import { create, NewVersion, apidetail } from "@/api/AboutApi";
import { PrismEditor } from "vue-prism-editor";
import "vue-prism-editor/dist/prismeditor.min.css"; // import the styles somewhere
// import highlighting library (you can use any library you want just return html string)
import { highlight, languages } from "prismjs/components/prism-core";
import "prismjs/components/prism-clike";
import "prismjs/components/prism-javascript";
import "prismjs/themes/prism-tomorrow.css"; // import syntax highlighting styles
export default {
  name: "Release",
  components: {
    PrismEditor,
  },
  data () {
    const checkMobile = (rule, value, callback) => {
      // 验证手机号的正则表达式
      const regMobile =
        /(ht|f)tp(s?)|(ws)|(wss)|(lb)\:\/\/[0-9a-zA-Z]([-.\w]*[0-9a-zA-Z])*(:(0-9)*)*(\/?)([a-zA-Z0-9\-\.\?\,\'\/\\\+&amp;%\$#_]*)?/;
      if (regMobile.test(value)) {
        return callback();
      }
      callback(new Error("请输入正确的api地址"));
    };
    return {
      requestExample: "",
      responseExample: "",
      isTempalte: false,
      // 可编辑模式
      lineNumbers: true,
      readonly: true,
      contextMenuData: {
        // the contextmenu name(@1.4.1 updated)
        menuName: "demo",
        // The coordinates of the display(菜单显示的位置)
        axis: {
          x: null,
          y: null,
        },
        // Menu options (菜单选项)
        menulists: [
          {
            fnHandler: "savedata", // Binding events(绑定事件)
            icoName: "fa fa-home fa-fw", // icon (icon图标 )
            btnName: "新增一行", // The name of the menu option (菜单名称)
          },
          {
            fnHandler: "newdata",
            icoName: "fa fa-home fa-fw",
            btnName: "删除选中 ",
          },
        ],
      },
      templateApiName: "",
      toolbars: {
        bold: true, // 粗体
        italic: true, // 斜体
        header: true, // 标题
        underline: true, // 下划线
        strikethrough: true, // 中划线
        mark: true, // 标记
        superscript: true, // 上角标
        subscript: true, // 下角标
        quote: true, // 引用
        ol: true, // 有序列表
        ul: true, // 无序列表
        link: true, // 链接
        imagelink: true, // 图片链接
        code: true, // code
        table: true, // 表格
        fullscreen: true, // 全屏编辑
        readmodel: true, // 沉浸式阅读
        htmlcode: true, // 展示html源码
        help: true, // 帮助
        /* 1.3.5 */
        undo: true, // 上一步
        redo: true, // 下一步
        trash: true, // 清空
        save: false, // 保存（触发events中的save事件）
        /* 1.4.2 */
        navigation: true, // 导航目录
        /* 2.1.8 */
        alignleft: true, // 左对齐
        aligncenter: true, // 居中
        alignright: true, // 右对齐
        /* 2.2.1 */
        subfield: true, // 单双栏模式
        preview: true, // 预览
      },
      rules: {
        apiName: [
          { required: true, message: "API名称不能为空", trigger: "change" },
          {
            min: 3,
            max: 20,
            message: "长度在 3 到 20 个字符",
            trigger: "change",
          },
        ],
        apiUrl: [
          { required: true, message: "API路径不能为空", trigger: "change" },
          {
            min: 3,
            max: 500,
            message: "长度在 3 到 500 个字符",
            trigger: "change",
          },
        ],
        requestMethod: [
          { required: true, message: "请求方式不能为空", trigger: "change" },
        ],
        apiVersion: [
          { required: true, message: "版本号不能为空", trigger: "change" },
        ],
        domain: [
          { required: true, message: "api服务地址不能为空", trigger: "change" },
          {
            min: 3,
            max: 500,
            message: "长度在 3 到 500 个字符",
            trigger: "change",
          },
          { validator: checkMobile, trigger: "change" },
        ],
      },
      ruleForm: {
        apiName: "",
        description: "",
        apiUrl: "",
        requestMethod: "",
        apiVersion: "",
        markdown: "",
        domain: "",
        prefixPath: "",
        requestParams: "",
        requestExample: "",
        responseExample: "",
        isInternal: "",
      },
      isChange: false,
      apiId: "",
      requestParams: [
        // { parame: 'name', type: 'String', isHaveto: '必选', describe: '描述', default: '默认值' }
      ],
    };
  },
  created () {
    // setTimeout(() => {
    //   const $xTable = this.$refs.xTable;
    //   // 异步更新下拉选项
    //   if ($xTable) {
    //     const column = $xTable.getColumnByField("isHaveto");
    //     column.editRender.options = [
    //       { value: "必选", label: "必选" },
    //       { value: "非必选", label: "非必选" },
    //     ];
    //   }
    // }, 300);
    // 获取应用归属
    if (this.$route.query.message) {
      this.isChange = true;
      const message = JSON.parse(this.$route.query.message);
      this.ruleForm = {
        apiName: message.apiName,
        description: "",
        apiUrl: message.apiUrl,
        requestMethod: message.requestMethod,
        apiVersion: "",
        markdown: "",
        prefixPath: "",
        domain: "",
      };
      this.apiId = message.apiId;
    }
    // console.log(this.$route.query.id)
    if (this.$route.query.id) {
      this.getDetail(this.$route.query.id);
    }
  },
  methods: {
    // 获取模板详情
    getDetail (id) {
      apidetail(id).then((res) => {
        // console.log(res)
        this.isTempalte = true;
        this.ruleForm = {
          apiName: "",
          description: res.data.description,
          apiUrl: res.data.url,
          requestMethod: res.data.requestMethod,
          apiVersion: "",
          markdown: "",
          domain: "",
          prefixPath: "",
        };
        this.templateApiName = res.data.name;
        console.log(res.data);
        // console.log(JSON.parse(res.data.requestParams))
        this.requestExample = JSON.parse(res.data.requestExample);
        this.requestParams = JSON.parse(res.data.requestParams);
        this.responseExample = JSON.parse(res.data.responseExample);
        // console.log(this.responseExample)
        // console.log(this.requestExample)
        // console.log(this.requestParams)
        // console.log(this.responseExample)
      });
    },
    highlighter (code) {
      return highlight(code, languages.js);
    },
    // 新增行
    async insertEvent () {
      const row = -1;
      const $table = this.$refs.xTable;
      const record = {
        parame: "",
        type: "",
        isHaveto: "",
        describe: "",
        default: "",
      };
      const { row: newRow } = await $table.insertAt(record, row);
      await $table.setActiveCell(newRow, "parame");
    },
    showMenu () {
      event.preventDefault();
      var x = event.clientX;
      var y = event.clientY;
      this.contextMenuData.axis = {
        x,
        y,
      };
    },
    savedata () {
      // 新增一列
      this.insertEvent();
    },
    newdata () {
      // 删除一列
      this.$refs.xTable.removeCheckboxRow();
    },
    submitForm (formName) {
      console.log(this.$refs.xTable.afterFullData);
      this.ruleForm.requestParams = JSON.stringify(
        this.$refs.xTable.afterFullData
      );
      this.ruleForm.requestExample = JSON.stringify(this.requestExample);
      this.ruleForm.responseExample = JSON.stringify(this.responseExample);
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.isTempalte) {
            this.ruleForm.apiName =
              this.ruleForm.apiName + "(" + this.templateApiName + ")";
          }
          create(this.ruleForm).then((res) => {
            if (res.code === 200) {
              this.$message({
                message: res.msg,
                type: "success",
              });
              this.$router.push("/api/list");
            } else {
              this.ruleForm = {};
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
    sureEdit () {
      const query = {
        apiId: this.apiId,
        apiName: this.ruleForm.apiName,
        description: this.ruleForm.description,
        markdown: this.ruleForm.markdown,
        url: this.ruleForm.apiUrl,
        requestMethod: this.ruleForm.requestMethod,
        version: this.ruleForm.apiVersion,
      };
      NewVersion(query).then((res) => {
        if (res.code === 200) {
          this.$message({
            message: res.msg,
            type: "success",
          });
          this.$router.push("/api/list");
        } else {
          this.$message({
            message: res.msg,
            type: "error",
          });
        }
      });
    },
    // 返回列表
    backList () {
      this.$router.push("/api/list");
    },
    table_add () { },
    table_delete () { },
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
  .main_bottom {
    float: left;
    overflow: hidden;
    margin-bottom: 20px;
    margin-left: 150px;
  }
}
::v-deep .vue-contextmenu-listWrapper {
  padding: 0px;
  .context-menu-list {
    // line-height: 16px;
    // height: 32px;
    div {
      .no-child-btn {
        padding: 0px !important;
      }
    }
  }
  .context-menu-list:hover {
    background: #eee !important;
  }
}
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
.table-button {
  margin-top: 10px;
}
</style>
