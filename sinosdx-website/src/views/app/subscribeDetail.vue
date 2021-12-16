<template>
  <div class="app_list_main">
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="生产环境" name="first">
        <div class="first_list">
          <div class="left">接口请求地址:</div>
          <div class="right">
            <el-input :disabled="true" v-model="gatewayDomain"></el-input>
          </div>
        </div>
        <div class="first_list">
          <div class="left">client id:</div>
          <div class="right">
            <el-input
              :disabled="true"
              v-model="usingAppList[0].clientId"
            ></el-input>
          </div>
        </div>
        <div class="first_list">
          <div class="left">client Secret:</div>
          <div class="right">
            <el-input
              :disabled="true"
              v-model="usingAppList[0].clientSecret"
            ></el-input>
          </div>
        </div>
      </el-tab-pane>
      <el-tab-pane label="订阅列表" name="second">
        <div>
          <el-table
            :data="dydata"
            class="table"
            :header-cell-style="{ background: '#F0F2F5' }"
            border
            empty-text="暂无数据"
          >
            <el-table-column align="center" prop="name" label="名称" />
            <el-table-column align="center" prop="apiName" label="状态">
              <template slot-scope="scope">
                <el-tag size="mini" :type="status[scope.row.isPublished][0]">
                  {{ status[scope.row.isPublished][1] }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column align="center" prop="apiName" label="操作">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="danger"
                  @click="handleDelete(scope.$index, scope.row)"
                >
                  取消订阅
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-tab-pane>
      <el-tab-pane label="API详情" name="three">
        <div>
          <el-table
            empty-text="暂无数据"
            :header-cell-style="{ background: '#F0F2F5' }"
            border
            :data="appVersions"
          >
            <el-table-column type="expand">
              <template slot-scope="props">
                <el-table
                  border
                  :header-cell-style="{ background: '#F0F2F5' }"
                  :data="props.row.apiVersionList"
                >
                  <el-table-column
                    align="center"
                    prop="version"
                    label="当前版本"
                  />
                  <el-table-column
                    align="center"
                    prop="apiName"
                    label="api名称"
                  />
                  <el-table-column align="center" prop="url" label="API路径" />
                  <!-- <el-table-column prop="markdown" label="API参数" /> -->
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
                  <el-table-column align="center" label="操作">
                    <template slot-scope="scope">
                      <el-button
                        size="mini"
                        @click="handleEditapi(scope.$index, scope.row)"
                        >查看文档</el-button
                      >
                    </template>
                  </el-table-column>
                </el-table>
              </template>
            </el-table-column>
            <el-table-column align="center" prop="version" label="应用版本号" />
            <el-table-column
              align="center"
              prop="description"
              label="版本描述"
            />
          </el-table>
        </div>
      </el-tab-pane>
    </el-tabs>
    <el-drawer
      title="API文档"
      :before-close="handleClosedrawerapi"
      :visible.sync="drawerapi"
      direction="rtl"
      size="45%"
    >
      <div style="padding: 20px" class="demo-drawer__content">
        <div>
          <p>请求参数:</p>
          <vxe-table
            ref="xTable"
            border
            show-overflow
            :data="requestParams"
            :edit-config="{ trigger: 'click', mode: 'cell' }"
          >
            <!-- <vxe-table-column type="checkbox" width="60" />s -->
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
      </div>
    </el-drawer>
  </div>
</template>

<script>
import {
  list,
  detail,
  Apidetail,
  getcurrent,
  Apilease,
} from "@/api/subscribed.js";
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
  data() {
    return {
      drawerapi: false,
      status: {
        60001: ["info", "未启用"],
        60005: ["success", "已启用"],
        60002: ["success", "已上架"],
        60003: ["danger", "异常中"],
        60004: ["warning", "已停用"],
      },
      dydata: [],
      data: [],
      appVersions: [],
      activeName: "first",
      readonly: true,
      gatewayDomain: "",
      usingAppList: [
        {
          clientId: "",
          clientSecret: "",
        },
      ],
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
  created() {
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
        this.appVersions = res.data.appVersions;
      });
      const userId = getToken("userId_api");
      getcurrent(userId, this.$route.query.appCode).then((res) => {
        console.log(res);
        this.dydata = res.data;
      });
    }
  },
  methods: {
    handleClosedrawerapi() {
      this.drawerapi = false;
    },
    handleEditapi(index, item) {
      console.log(item);
      this.requestExample = JSON.parse(item.requestExample);
      this.requestParams = JSON.parse(item.requestParams);
      this.responseExample = JSON.parse(item.responseExample);
      this.drawerapi = true;
    },
    handleDelete(i, j) {
      console.log(i, j);
      Apilease(j.code, this.$route.query.appCode).then((res) => {
        if (res.code === 200) {
          this.messageOK(res.msg);
          const userId = getToken("userId_api");
          getcurrent(userId, this.$route.query.appCode).then((res) => {
            console.log(res);
            this.dydata = res.data;
          });
        } else {
          this.messageERROR(res.msg);
        }
      });
    },
    handleClick(tab, event) {
      console.log(tab, event);
    },
    highlighter(code) {
      return highlight(code, languages.js);
    },
    // 新增行
    async insertEvent() {
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
    showMenu() {
      event.preventDefault();
      var x = event.clientX;
      var y = event.clientY;
      this.contextMenuData.axis = {
        x,
        y,
      };
    },
    savedata() {
      // 新增一列
      this.insertEvent();
    },
    newdata() {
      // 删除一列
      this.$refs.xTable.removeCheckboxRow();
    },
    handleEdit(index, e) {
      console.log(e);
      this.MKDialogVisible = true;
      Apidetail(e.apiId).then((res) => {
        // console.log(res)
        this.requestExample = JSON.parse(res.data.requestExample);
        this.requestParams = JSON.parse(res.data.requestParams);
        this.responseExample = JSON.parse(res.data.responseExample);
      });
    },
    handleClose(done) {
      this.$confirm("确认关闭？")
        .then((_) => {
          done();
        })
        .catch((_) => {});
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
.app_list_main {
  width: 98%;
  margin: 0 auto;
  margin-top: 10px;
  border-radius: 5px;
  background-color: #fff;
  overflow: hidden;
  padding: 20px;
  min-height: calc(100vh - 185px);
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
  .first_list {
    display: flex;
    padding: 20px 0px;
    .left {
      width: 150px;
      vertical-align: middle;
      line-height: 36px;
    }
    .right {
      vertical-align: middle;
      width: calc(100% - 150px);
    }
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
