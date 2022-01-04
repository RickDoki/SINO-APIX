<template>
  <div class="app_list_main">
    <el-dialog
      title="api文档"
      :visible.sync="paramsDialogVisible"
      :modal-append-to-body='false'

    >
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
        <el-button @click="paramsDialogVisible = false">取 消</el-button>
        <!-- <el-button type="primary" @click="MKSure()">确 定</el-button> -->
      </span>
    </el-dialog>
    <el-dialog
      title="编辑"
      :visible.sync="editDialogVisible"
    >
      <el-form
        ref="ruleForm"
        :model="ruleForm"
        :rules="rules"
        label-width="100px"
        class="editForm"
      >
        <el-form-item label="应用名称:" prop="appName">
          <el-input v-model="ruleForm.appName" />
        </el-form-item>
        <el-form-item label="活动形式:" prop="description">
          <el-input
            v-model="ruleForm.description"
            type="textarea"
            :autosize="{ minRows: 10, maxRows: 15 }"
            maxlength="500"
          />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="editSure('ruleForm')"
          >确 定</el-button
        >
      </span>
    </el-dialog>
    <el-dialog
      title="发布新版本"
      :visible.sync="appVDialogVisible"
    >
      <el-form
        ref="appVForm"
        :model="appVForm"
        :rules="appVrules"
        label-width="100px"
        class="editForm"
      >
        <el-form-item label="新版本号:" prop="appVersion">
          <el-input v-model="appVForm.appVersion" />
        </el-form-item>
        <el-form-item label="新版本描述:" prop="versionDesc">
          <el-input
            v-model="appVForm.versionDesc"
            type="textarea"
            :autosize="{ minRows: 3, maxRows: 3 }"
            maxlength="500"
          />
        </el-form-item>
        <el-form-item label="选择api版本:" prop="description">
          <el-collapse accordion>
            <el-collapse-item
              v-for="(item, index) in apiList"
              :key="index"
              :title="'api名称:  ' + item.apiName"
              :name="index"
            >
              <el-radio-group v-model="radio[index]">
                <el-radio
                  v-for="item1 in item.apiVersions"
                  :key="item1.versionId"
                  :label="item1.versionId"
                  >{{ item1.apiVersion }}</el-radio
                >
              </el-radio-group>
            </el-collapse-item>
          </el-collapse>
        </el-form-item>
      </el-form>
      <!-- <el-pagination
        style="margin-top: 20px"
        :current-page.sync="currentPage"
        layout="prev, pager, next"
        :total="total"
        :page-size="5"
        @current-change="handleCurrentChange"
      /> -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="appVDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="newVSure('appVForm')"
          >确 定</el-button
        >
      </span>
    </el-dialog>
    <el-dialog
      title="文档编辑"
      :visible.sync="MKDialogVisible"
    >
      <mavon-editor
        ref="md"
        v-model="content"
        :toolbars="toolbars"
        :subfield="false"
      />
      <span slot="footer" class="dialog-footer">
        <el-button @click="MKDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="MKSure()">确 定</el-button>
      </span>
    </el-dialog>
    <el-dialog
      title="编辑"
      :visible.sync="USERDialogVisible"
    >
      <el-row>
        <el-col :span="18">
          <el-form
            ref="USERForm"
            :model="USERForm"
            :rules="USERrules"
            label-width="100px"
            style="position: realtive"
          >
            <el-form-item label="用户名:" prop="username">
              <el-input v-model="USERForm.username" style="width: 220px" />
            </el-form-item>
            <el-form-item label="手机号:" prop="phone">
              <el-input v-model="USERForm.phone" style="width: 220px" />
            </el-form-item>
          </el-form>
        </el-col>
        <el-col :span="6">
          <el-button
            type="primary"
            class="addUser"
            @click="userAddSure('USERForm')"
          >
            + 添加成员
          </el-button>
        </el-col>
      </el-row>
      <el-table :data="usertable" stripe style="width: 90%; margin: 0 auto">
        <el-table-column prop="developerName" label="成员名称" />
        <el-table-column align="right" label="操作">
          <template slot-scope="scope">
            <el-button size="mini" type="danger" @click="deletdUser(scope.row)"
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
    <el-card class="card_middle">
      <p class="table_font">应用信息</p>
      <el-table empty-text="暂无数据" :data="appMessage">
        <el-table-column align="center" prop="appCode" label="APP Code" />
        <el-table-column align="center" prop="appName" label="应用名称" />
        <el-table-column align="center" prop="creationByUsername" label="创建者" />
        <el-table-column align="center" prop="creationDate" label="创建时间" />
        <el-table-column align="center" prop="lastUpdatedByUsername" label="更新者" />
        <el-table-column align="center" prop="lastUpdateDate" label="更新时间" />
        <!-- <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button
              size="mini"
              :disabled="scope.row.isPublished === '60001' ? false : true"
              @click="handleEdit(scope.row)"
            >编辑</el-button>
          </template>
        </el-table-column> -->
      </el-table>
    </el-card>
    <el-card class="card_middle">
      <p class="table_font">版本信息</p>
      <el-table empty-text="暂无数据" :data="appVersions">
        <el-table-column type="expand">
          <template slot-scope="props">
            <el-table border :data="props.row.apiVersionList">
              <el-table-column align="center" prop="version" label="当前版本" />
              <el-table-column align="center" prop="apiName" label="api名称" />
              <el-table-column align="center" prop="url" label="API路径" />
              <!-- <el-table-column prop="markdown" label="API参数" /> -->
              <el-table-column align="center" prop="description" label="描述" />
              <el-table-column align="center" prop="requestMethod" label="请求方式" />
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
        <el-table-column align="center" prop="description" label="版本描述" />
      </el-table>
    </el-card>
    <!-- <el-card class="card_middle">
      <p class="table_font">正在使用的应用</p>
      <el-table :data="usingAppList">
        <el-table-column prop="appCode" label="APP Code" />
        <el-table-column prop="appName" label="应用名称" />
        <el-table-column prop="clientId" label="client Id" />
        <el-table-column prop="clientSecret" label="client Secret" />
      </el-table>
    </el-card> -->
    <div class="bottom_button">
      <el-button size="small" type="warning" @click="pushNewVersion"
        >新版本发布</el-button
      >
      <!-- <el-button size="small" type="success" @click="MKedit"
        >文档编辑</el-button
      > -->
      <el-button size="small" type="primary" @click="UserManage"
        >成员管理</el-button
      >
      <el-button size="small" type="danger" @click="appDeleted"
        >删除应用</el-button
      >
      <el-button size="small" @click="backList">返回列表</el-button>
    </div>
  </div>
</template>

<script>
import {
  detail,
  published,
  apiList,
  publish,
  appEdit,
  addUser,
  userList,
  userDeleted,
  appDelete,
} from "@/api/AboutApp";
import { Apidetail } from "@/api/subscribed.js";
import { mavonEditor } from "mavon-editor";
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
    mavonEditor,
    PrismEditor,
  },
  data () {
    return {
      content: "",
      readonly: true,
      lineNumbers: true,
      requestExample: "",
      responseExample: "",
      requestParams: [
        {
          parame: "name",
          type: "String",
          isHaveto: "必选",
          describe: "描述",
          default: "默认值",
        },
      ],
      paramsDialogVisible: false,
      radio: [],
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
        appName: [
          { required: true, message: "应用名称不能为空", trigger: "change" },
        ],
      },
      appVrules: {
        appVersion: [
          { required: true, message: "版本不能为空", trigger: "change" },
        ],
      },
      ruleForm: {
        appName: "",
        description: "",
      },
      appVForm: {
        appVersion: "",
        versionDesc: "",
        apiVersionIds: "",
      },
      USERForm: {
        username: "",
        phone: "",
      },
      USERrules: {
        username: [
          { required: true, message: "用户名不能为空", trigger: "change" },
        ],
        phone: [
          { required: true, message: "手机号不能为空", trigger: "change" },
        ],
      },
      appList: {},
      appMessage: [],
      appVersions: [],
      usingAppList: [],
      editDialogVisible: false,
      appCode: "",
      appVDialogVisible: false,
      apiList: [],
      currentPage: 1,
      total: 0,
      apiVersionIds: "",
      MKDialogVisible: false,
      USERDialogVisible: false,
      usertable: [],
      developerId: "",
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
    const developerId = getToken("userId");
    this.developerId = developerId;
    this.appCode = this.$route.query.appCode;
    this.getMessageList(this.appCode, developerId);
    this.getAPIlist(1);
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
    // 获取详情
    getMessageList (e, i) {
      detail(e, i).then((res) => {
        this.appList = res.data;
        this.appMessage.push({
          appCode: res.data.appCode,
          creationByUsername: res.data.appCreationUser,
          creationDate: res.data.appCreationDate,
          lastUpdatedByUsername: res.data.appLastUpdateUser,
          lastUpdateDate: res.data.appLastUpdateDate,
          isPublished: res.data.isPublished,
          appName: res.data.appName,
          appDescription: res.data.appDescription,
        });
        this.ruleForm = {
          appName: res.data.appName,
          description: res.data.appDescription,
        };
        this.appVersions = res.data.appVersions;
        this.usingAppList = res.data.usingAppList;
        this.content = res.data.appMarkdown;
      });
    },
    getAPIlist (page) {
      // 获取api版本列表
      const query = "developerId=" + this.developerId;
      apiList(query).then((res) => {
        this.apiList = res.data.apiList;
        this.total = res.data.total;
        for (let index = 0; index < this.apiList.length; index++) {
          this.radio.push("");
        }
      });
    },
    // 返回列表
    backList () {
      this.$router.push("/app/list");
    },
    // 点击编辑
    handleEdit (e) {
      this.editDialogVisible = true;
    },
    // 查看api详细信息
    handleEditapi (index, e) {
      console.log(e);
      Apidetail(e.apiId).then((res) => {
        // console.log(res);
        this.requestExample = JSON.parse(res.data.requestExample);
        this.requestParams = JSON.parse(res.data.requestParams);
        this.responseExample = JSON.parse(res.data.responseExample);
      });
      this.paramsDialogVisible = true;
    },
    // 编辑确认
    editSure (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          published(this.appCode, this.ruleForm).then((res) => {
            if (res.code === 200) {
              this.messageOK(res.msg);
              this.editDialogVisible = false;
            } else {
              this.messageERROR(res.msg);
              this.editDialogVisible = false;
            }
          });
        } else {
          return false;
        }
      });
    },
    // 发布新版本
    pushNewVersion () {
      this.appVDialogVisible = true;
    },
    // 选择api版本
    handleSelectionChange (val) {
      console.log(val);
      const apiIds = [];
      val.forEach((item) => {
        apiIds.push(item.versionId);
      });
      this.appVForm.apiVersionIds = apiIds.toString();
    },
    // 分页
    handleCurrentChange (val) {
      this.currentPage = val;
      this.getAPIlist(val);
    },
    // 确认发布新版本
    newVSure (appVForm) {
      const newlist = [];
      for (let index = 0; index < this.radio.length; index++) {
        if (this.radio[index] !== "") {
          newlist.push(this.radio[index]);
        }
      }
      const listString = newlist.toString();
      this.$refs[appVForm].validate((valid) => {
        if (valid) {
          if (listString === "") {
            this.messageERROR("至少需要选择一个api版本");
            return false;
          }
          this.appVForm.apiVersionIds = listString;
          publish(this.appCode, this.appVForm).then((res) => {
            if (res.code === 200) {
              this.messageOK(res.msg);
            } else {
              this.messageERROR(res.msg);
            }
            this.appVDialogVisible = false;
            this.getMessageList(this.appCode, "5");
          });
          // console.log(this.appVForm)
          // if(this.appVForm.)
          // publish(this.appCode, )
        } else {
          return false;
        }
      });
    },
    // 文档编辑
    MKedit () {
      this.MKDialogVisible = true;
    },
    // MK文档确认
    MKSure () {
      console.log(this.content);
      const query = {
        markdown: this.content,
      };
      appEdit(this.appCode, query).then((res) => {
        // console.log(res)
        if (res.code === 200) {
          this.messageOK(res.msg);
        } else {
          this.messageERROR(res.msg);
        }
        this.MKDialogVisible = false;
      });
    },
    // 成员管理
    UserManage () {
      this.USERDialogVisible = true;
      this.getUserList();
    },
    // 查询现有成员
    getUserList () {
      userList(this.appCode).then((res) => {
        // console.log(res)
        this.usertable = res.data;
      });
    },
    // 添加成员
    userAddSure (USERForm) {
      this.$refs[USERForm].validate((valid) => {
        if (valid) {
          addUser(this.appCode, this.USERForm).then((res) => {
            if (res.code === 200) {
              this.messageOK(res.msg);
            } else {
              this.messageERROR(res.msg);
            }
            this.getUserList();
            this.USERForm = {
              username: "",
              phone: "",
            };
          });
        } else {
          // console.log('不成功')
          return false;
        }
      });
    },
    // 移除成员
    deletdUser (e) {
      // console.log(e)
      userDeleted(this.appCode, e.developerId).then((res) => {
        if (res.code === 200) {
          this.messageOK(res.msg);
        } else {
          this.messageERROR(res.msg);
        }
        this.getUserList();
      });
    },
    // 删除应用
    appDeleted () {
      this.$confirm("确认是否删除?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          appDelete(this.appCode).then((res) => {
            if (res.code === 200) {
              this.messageOK(res.msg);
              this.$router.push("/app/list");
            } else {
              this.messageERROR(res.msg);
            }
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
    // 关闭弹窗
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
    // text-align: center;
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
