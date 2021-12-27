<template>
  <div class="tableFather">
    <el-dialog title="详情" :modal-append-to-body='false' :visible.sync="MKDialogVisible">
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
        <vue-context-menu
          :contextMenuData="contextMenuData"
          @savedata="savedata"
          @newdata="newdata"
          :readonly="readonly"
        />
        <!-- <div class="table-button">
          <el-button type="primary" size="mini" @click="savedata">
            新增
          </el-button>
          <el-button type="danger" size="mini" @click="newdata">
            删除
          </el-button>
        </div> -->
      </div>
      <div>
        <p>请求示例:</p>
        <prism-editor
          class="my-editor height-300"
          v-model="requestExample"
          :highlight="highlighter"
          :readonly="readonly"
          :line-numbers="lineNumbers"
        />
      </div>
      <div>
        <p>返回示例:</p>
        <prism-editor
          class="my-editor height-300"
          v-model="responseExample"
          :highlight="highlighter"
          :readonly="readonly"
          :line-numbers="lineNumbers"
        />
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="MKDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="MKSure()">确 定</el-button>
      </span>
    </el-dialog>
    <el-table :data="data" class="table" empty-text="暂无数据" border>
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-table border :data="props.row.apiVersions">
            <el-table-column align="center" width="82" prop="apiVersion" label="当前版本" />
            <el-table-column align="center" prop="apiLastUpdateDate" label="修改时间" />
            <el-table-column align="center" prop="apiUrl" label="API路径" />
            <!-- <el-table-column prop="markdown" label="API参数" /> -->
            <el-table-column align="center" prop="description" label="描述" />
            <el-table-column align="center" width="95" prop="requestMethod" label="请求方式" />
            <el-table-column align="center" width="105" label="操作">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  @click="handleEdit(scope.$index, scope.row)"
                  >查看详情</el-button
                >
              </template>
            </el-table-column>
          </el-table>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="apiName" label="API名称" />
      <el-table-column align="center" width="110" prop="apiVersion" label="最新版本号" />
      <el-table-column align="center" prop="apiCreationUser" label="创建人" />
      <el-table-column align="center" width="158" prop="apiCreationDate" label="创建时间" />
      <el-table-column align="center" width="226" label="操作">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="primary"
            @click="publishNV(scope.$index, scope.row)"
            >发布新版本</el-button
          >
          <el-button
            size="mini"
            type="danger"
            @click="handleDelete(scope.$index, scope.row)"
          >
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      style="margin-top: 20px"
      :current-page.sync="currentPage"
      layout="prev, pager, next"
      :total="total"
      @current-change="handleCurrentChange"
    />
  </div>
</template>

<script>
import { listDelete } from "@/api/AboutApi";
import "mavon-editor/dist/css/index.css";
import { editApiVersion } from "@/api/AboutApi";
import { PrismEditor } from "vue-prism-editor";
import "vue-prism-editor/dist/prismeditor.min.css"; // import the styles somewhere
// import highlighting library (you can use any library you want just return html string)
import { highlight, languages } from "prismjs/components/prism-core";
import "prismjs/components/prism-clike";
import "prismjs/components/prism-javascript";
import "prismjs/themes/prism-tomorrow.css"; // import syntax highlighting styles
export default {
  // props: ['data', 'total'],
  components: {
    PrismEditor,
  },
  props: {
    data: {
      type: Array,
      default: null,
    },
    total: {
      type: Number,
      default: 0,
    },
  },
  data () {
    return {
      readonly: true,
      currentPage: 1,
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
      content: "",
      apiId: "",
    };
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
    handleCurrentChange (val) {
      this.currentPage = val;
      this.$emit("pageChange", val);
    },
    handleEdit (index, row) {
      // this.$router.push({ path: '/api/add?message=' + JSON.stringify(row) })
      this.MKDialogVisible = true;
      // setTimeout(() => {
      //   const $xTable = this.$refs.xTable;
      //   console.log($xTable)
      //   // 异步更新下拉选项
      //   if ($xTable) {
      //     const column = $xTable.getColumnByField("isHaveto");
      //     column.editRender.options = [
      //       { value: "是", label: "是" },
      //       { value: "否", label: "否" },
      //     ];
      //   }
      // }, 1000);
      console.log(row);
      this.content = row.markdown;
      this.apiVersionId = row.versionId;
      console.log(JSON.parse(row.requestParams));
      this.requestParams = JSON.parse(row.requestParams);
      this.requestExample = JSON.parse(row.requestExample);
      this.responseExample = JSON.parse(row.responseExample);
    },
    handleDelete (index, row) {
      this.$confirm("是否确认删除?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          listDelete(row.apiId).then((res) => {
            if (res.code !== 200) {
              this.$message({
                message: res.msg,
                type: "error",
              });
            } else {
              this.$emit("refresh", true);
              this.$message({
                message: res.msg,
                type: "success",
              });
            }
          });
          // this.$message({
          //   type: 'success',
          //   message: '删除成功!'
          // });
        })
    },
    // 发布新版本
    publishNV (index, row) {
      this.$router.push({ path: "/api/add?message=" + JSON.stringify(row) });
    },
    MKSure () {
      const query = {
        // markdown: this.content,
      };
      editApiVersion(this.apiVersionId, query).then((res) => {
        // console.log(res)
        if (res.code === 200) {
          this.messageOK(res.msg);
        } else {
          this.messageERROR(res.msg);
        }
        this.MKDialogVisible = false;
        this.$emit("refresh", true);
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
.tableFather {
  padding: 30px;
  .table {
    width: 100%;
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
  background: #f5f7fa;
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
::v-deep .el-dialog__wrapper {
  z-index: 999 !important;
}
::v-deep .v-modal {
  z-index: 998 !important;
}
</style>
