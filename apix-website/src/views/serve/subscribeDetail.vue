<template>
  <div class="app_list_main">
    <el-dialog
      title="详情"
      :visible.sync="MKDialogVisible"
      :modal-append-to-body='false'
    >
      <div>
        <p>请求参数:</p>
        <vxe-table
          ref="xTable"
          border
          show-overflow
          :data="requestParams"
          
        >
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
        <vue-context-menu
          :contextMenuData="contextMenuData"
          @savedata="savedata"
          @newdata="newdata"
        />
      </div>
      <div>
        <p>请求示例:</p>
        <prism-editor
          class="my-editor height-300"
          v-model="requestExample"
          :highlight="highlighter"
          :line-numbers="lineNumbers"
          :readonly='readonly'
        />
      </div>
      <div>
        <p>返回示例:</p>
        <prism-editor
          class="my-editor height-300"
          v-model="responseExample"
          :highlight="highlighter"
          :line-numbers="lineNumbers"
          :readonly='readonly'
        />
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="MKDialogVisible = false">取 消</el-button>
        <!-- <el-button type="primary" @click="MKSure()">确 定</el-button> -->
      </span>
    </el-dialog>
    <el-card>
      <p class="table_font">网关地址:{{ gatewayDomain }}</p>
    </el-card>
    <el-card class="card_middle">
      <p class="table_font">应用信息</p>
      <el-table :default-expand-all=" true" empty-text="暂无数据" :data="data" class="table" border>
        <el-table-column type="expand">
          <template slot-scope="props">
            <el-table border empty-text="暂无数据" :data="props.row.apiVersionList">
              <el-table-column align="center" prop="apiName" label="API名称" />
              <el-table-column align="center" width="75" prop="version" label="版本号" />
              <el-table-column align="center" prop="description" label="描述" />
              <el-table-column align="center" width="93" prop="requestMethod" label="请求方式" />
              <el-table-column align="center" prop="url" label="请求路径" />
              <el-table-column align="center" width="120" label="操作">
                <template slot-scope="scope">
                  <el-button
                    size="mini"
                    @click="handleEdit(scope.$index, scope.row)"
                    >查看文档</el-button
                  >
                </template>
              </el-table-column>
            </el-table>
          </template>
        </el-table-column>
        <el-table-column align="center" prop="version" label="应用版本号" />
        <el-table-column align="center" prop="description" label="版本描述" />
      </el-table>
    </el-card>
    <el-card class="card_middle">
      <p class="table_font">认证信息</p>
      <el-table :data="usingAppList">
        <el-table-column prop="clientId" label="client Id" />
        <el-table-column prop="clientSecret" label="client Secret" />
      </el-table>
    </el-card>
  </div>
</template>

<script>
import { list, detail, Apidetail } from "@/api/subscribed.js";
import { getToken } from "@/utils/auth"; // get token from cookie
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
    PrismEditor,
  },
  data () {
    return {
      data: [],
      readonly: true,
      gatewayDomain: "",
      usingAppList: [],
      requestExample: "",
      responseExample: "",
      // 可编辑模式
      lineNumbers: true,
      MKDialogVisible: false,
      requestParams: [
        {
          parame: "name",
          type: "String",
          isHaveto: "必选",
          describe: "描述",
          default: "默认值",
        },
      ],
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
    };
  },
  created () {
    if (this.$route.query.appCode) {
      // this.getDetail(this.$route.query.id)
      // console.log(this.$route.query.appCode)
      detail(this.$route.query.appCode).then((res) => {
        this.gatewayDomain = res.data.gatewayDomain;
        console.log(res);
        this.usingAppList = [
          {
            clientId: res.data.clientId,
            clientSecret: res.data.clientSecret,
          },
        ];
        this.data = res.data.appVersions;
      });
    }
  },
  methods: {
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
    handleEdit (index, e) {
      console.log(e);
      this.MKDialogVisible = true;
      Apidetail(e.apiId).then((res) => {
        // console.log(res)
        this.requestExample = JSON.parse(res.data.requestExample);
        this.requestParams = JSON.parse(res.data.requestParams);
        this.responseExample = JSON.parse(res.data.responseExample);
      });
    },
    handleClose (done) {
      this.$confirm("确认关闭？")
        .then((_) => {
          done();
        })
        .catch((_) => { });
    },
    // 成功消息
    messageOK (msg) {
      this.$message({
        message: msg,
        type: "success",
      });
    },
    // 失败消息
    messageERROR (msg) {
      this.$message({
        message: msg,
        type: "error",
      });
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
  .editForm {
    margin: 0 50px;
    width: 480px;
  }
  .card_middle {
    margin-top: 20px;
    .table_font {
      margin: 5px 0px;
      font-size: 14px;
      color: #909399;
    }
  }
  .bottom_button {
    text-align: center;
    margin: 20px 0px;
  }
  .addUser {
    float: right;
    margin-top: 35px;
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
::v-deep .el-dialog__wrapper {
  z-index: 999 !important;
}
::v-deep .v-modal {
  z-index: 998 !important;
}
</style>
