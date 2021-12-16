<template>
  <div class="app_list_main">
    <el-dialog
      title="文档"
      :visible.sync="MKDialogVisible"
      :modal-append-to-body="false"
    >
      <div>
        <p>请求参数:</p>
        <vxe-table ref="xTable" border show-overflow :data="requestParams">
          <!-- <vxe-table-column type="checkbox" width="60" /> -->
          <vxe-table-column
            field="parame"
            title="参数"
            :edit-render="{ name: 'input' }"
          />
          <vxe-table-column
            field="type"
            title="类型"
            :edit-render="{ name: 'input' }"
          />
          <vxe-table-column
            field="isHaveto"
            title="必选"
            :edit-render="{ name: 'input' }"
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
        <!-- <vue-context-menu
          :contextMenuData="contextMenuData"
          @savedata="savedata"
          @newdata="newdata"
        /> -->
      </div>
      <div>
        <p>请求示例:</p>
        <prism-editor
          class="my-editor height-300"
          v-model="requestExample"
          :highlight="highlighter"
          :line-numbers="lineNumbers"
          :readonly="readonly"
        />
      </div>
      <div>
        <p>返回示例:</p>
        <prism-editor
          class="my-editor height-300"
          v-model="responseExample"
          :highlight="highlighter"
          :line-numbers="lineNumbers"
          :readonly="readonly"
        />
      </div>
      <!-- <mavon-editor
        class="md"
        :value="webDataString"
        :subfield="false"
        :defaultOpen="'preview'"
        :toolbarsFlag="false"
        :editable="false"
        :scrollStyle="true"
        :ishljs="true"
      /> -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="MKDialogVisible = false">关 闭</el-button>
      </span>
    </el-dialog>
    <el-row>
      <el-col style="margin-bottom: 10px">
        <span>版本信息</span>
        <el-button size="mini" style="float: right" @click="GoBack"
          >返回</el-button
        >
      </el-col>
      <el-col :span="24">
        <el-table :default-expand-all="true" :data="data" class="table" border>
          <el-table-column type="expand">
            <template slot-scope="props">
              <el-table
                border
                empty-text="暂无数据"
                :data="props.row.apiVersionList"
              >
                <el-table-column
                  align="center"
                  prop="apiName"
                  label="API名称"
                />
                <el-table-column align="center" prop="version" label="版本号" />
                <el-table-column
                  align="center"
                  prop="description"
                  label="描述"
                />
                <el-table-column
                  align="center"
                  prop="requestMethod"
                  label="请求方式"
                />
                <el-table-column align="center" prop="url" label="请求路径" />
                <!-- <el-table-column align="center" label="操作">
                  <template slot-scope="scope">
                    <el-button
                      size="mini"
                      @click="handleEdit(scope.$index, scope.row)"
                      >查看文档</el-button
                    >
                  </template>
                </el-table-column> -->
              </el-table>
            </template>
          </el-table-column>
          <el-table-column align="center" prop="version" label="版本号" />
          <el-table-column align="center" prop="description" label="版本描述" />
        </el-table>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { detail } from "@/api/AboutApp";
import { mavonEditor } from "mavon-editor";
import "mavon-editor/dist/css/index.css";
import { PrismEditor } from "vue-prism-editor";
import "vue-prism-editor/dist/prismeditor.min.css"; // import the styles somewhere
// import highlighting library (you can use any library you want just return html string)
import { highlight, languages } from "prismjs/components/prism-core";
import "prismjs/components/prism-clike";
import "prismjs/components/prism-javascript";
import "prismjs/themes/prism-tomorrow.css"; // import syntax highlighting styles
export default {
  components: {
    mavonEditor,
    PrismEditor
  },
  data() {
    return {
      appCode: "",
      readonly: true,
      data: [],
      lineNumbers: true,
      MKDialogVisible: false,
      webDataString: "",
      requestParams: [],
      requestExample: "",
      responseExample: "",
    };
  },
  created() {
    this.appCode = this.$route.query.appCode;
    detail(this.appCode, "").then((res) => {
      if (res.code === 200) {
        this.data = res.data.appVersions;
      }
    });
  },
  methods: {
    highlighter(code) {
      return highlight(code, languages.js);
    },
    // 返回
    GoBack() {
      this.$router.push({ path: "/market/detail?appCode=" + this.appCode });
    },
    handleEdit(index, row) {
      //   console.log(row)
      console.log(row)
      this.MKDialogVisible = true;
      this.webDataString = row.apiMarkdown;
    },
    handleClose(done) {
      this.$confirm("确认关闭？")
        .then((_) => {
          done();
        })
        .catch((_) => {});
    },
  },
};
</script>

<style lang='scss' scoped>
.app_list_main {
  width: 95%;
  margin: 0 auto;
  margin-top: 20px;
  border-radius: 5px;
  background-color: #fff;
  overflow: hidden;
  padding: 20px;
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
  background: #2d2d2d;
  color: #ccc;
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
::v-deep .el-dialog__wrapper {
  z-index: 999 !important;
}
::v-deep .v-modal {
  z-index: 998 !important;
}
</style>
