<template>
  <div class="api_list_main">
    <el-dialog
      :title='tableDataname'
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
            title="是否必选"
            :edit-render="{ name: '$select', options: [{ value: '是', label: '是' },
            { value: '否', label: '否' }] }"
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
      <span slot="footer" class="dialog-footer">
        <el-button @click="MKDialogVisible = false">取 消</el-button>
        <!-- <el-button type="primary" @click="MKSure()">确 定</el-button> -->
      </span>
    </el-dialog>
    <el-row class="search" :gutter="20">
      <el-col :span="4">
        <el-input v-model="APIName" @keyup.enter.native="search" placeholder="请输入模板名称" />
      </el-col>
      <el-col :span="6">
        <el-button type="primary" @click="search" icon="el-icon-search">搜索</el-button>
      </el-col>
    </el-row>
    <div class="tableBox">
      <el-table :data="tableData" empty-text="暂无数据" style="width: 100%">
        <el-table-column align="center" prop="name" label="模板名称" />
        <el-table-column align="center" width="80" prop="requestMethod" label="请求方式" />
        <el-table-column align="center" prop="url" label="请求路径" />
        <!-- <el-table-column prop="creationByUsername" label="创建人" /> -->
        <el-table-column align="center" prop="description" label="模板描述" />
        <el-table-column align="center" label="操作">
          <template slot-scope="scope">
            <el-button size="mini" type="success" @click="goCreate(scope.row)">
              使用模板
            </el-button>
            <el-button size="mini" type="success" @click="getDetail(scope.row)">
              详情
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
import { templateList } from "@/api/apiTemplate";
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
      tableData: [],
      readonly: true,
      tableDataname: '',
      requestExample: "",
      responseExample: "",
      requestParams: [
        // { parame: 'name', type: 'String', isHaveto: '必选', describe: '描述', default: '默认值' }
      ],
      APIName: "",
      MKDialogVisible: false,
      lineNumbers: true,
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
    templateList('').then((res) => {
      // console.log(res)
      if (res.code === 200) {
        this.tableData = res.data.templateList;
      }
    });
  },
  methods: {
    goCreate (e) {
      console.log(e);
      this.$router.push({ path: "/api/add?id=" + e.id });
    },
    getDetail (e) {
      console.log(e)
      this.tableDataname = e.name
      this.MKDialogVisible = true
      this.requestExample = JSON.parse(e.requestExample)
      this.responseExample = JSON.parse(e.responseExample)
      this.requestParams = JSON.parse(e.requestParams)
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
    handleClose (done) {
      this.$confirm("确认关闭？")
        .then((_) => {
          done();
        })
        .catch((_) => { });
    },
    search () {
      // console.log(this.APIName)
      const query = '?name=' + this.APIName
      templateList(query).then((res) => {
        // console.log(res)
        if (res.code === 200) {
          this.tableData = res.data.templateList;
        }
      });
    }
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
  .search {
    padding: 30px 30px 0px 30px;
  }
  .tableBox {
    padding: 30px;
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
