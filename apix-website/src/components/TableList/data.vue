<template>
  <div class="tableFather">
    <el-dialog width="900px" title="详情" :visible.sync="MKDialogVisible">
      <div>
        <!-- <p>请求示例:</p> -->
        <!-- <prism-editor
          class="my-editor height-300"
          v-model="requestExample"
          :highlight="highlighter"
          :readonly="readonly"
          :line-numbers="lineNumbers"
        /> -->
        <json-view
          :data="requestExample"
          theme="vs-code"
          :deep="2"
          :fontSize="12"
        />
      </div>
      <!-- <div>
        <p>返回示例:</p>
        <prism-editor
          class="my-editor height-300"
          v-model="responseExample"
          :highlight="highlighter"
          :readonly="readonly"
          :line-numbers="lineNumbers"
        />
      </div> -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="MKDialogVisible = false">取 消</el-button>
        <!-- <el-button type="primary" @click="MKSure()">确 定</el-button> -->
      </span>
    </el-dialog>
    <el-table
      :data="tableList"
      empty-text="暂无数据"
      v-loading="loading"
      border
      style="width: 100%"
    >
      <el-table-column align="center" prop="trace" label="请求标识" />
      <el-table-column align="center" prop="requestUri" label="请求地址" />
      <el-table-column
        align="center"
        width="80"
        prop="httpMethod"
        label="请求方式"
      />
      <el-table-column align="center" show-overflow-tooltip prop="params" label="请求参数" />
      <el-table-column align="center" prop="remoteIp" label="客户端 Ip" />
      <el-table-column align="center" prop="serverIp" label="服务端 Ip" />
      <el-table-column align="center" width="65" prop="env" label="环境" />
      <el-table-column
        align="center"
        width="80"
        prop="consumingTime"
        label="耗时(ms)"
      />
      <el-table-column align="center" width="100" prop="eventTime" label="调用时间">
        <template slot-scope="scope">
          {{ scope.row.eventTime | TimeRanges }}
        </template>
      </el-table-column>
      <!-- <el-table-column prop="type" label="type" /> -->
      <el-table-column align="center" width="82" label="操作">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.$index, scope.row)"
            >详情</el-button
          >
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
import { PrismEditor } from "vue-prism-editor";
import "vue-prism-editor/dist/prismeditor.min.css"; // import the styles somewhere
// import highlighting library (you can use any library you want just return html string)
import { highlight, languages } from "prismjs/components/prism-core";
import "prismjs/components/prism-clike";
import "prismjs/components/prism-javascript";
import "prismjs/themes/prism-tomorrow.css"; // import syntax highlighting styles
import jsonView from "@/components/json-view/index.vue";

export default {
  // props: ['tableList', 'total'],
  filters: {
    TimeRanges (value) {
      let date = new Date(value); //时间戳为10位需*1000，时间戳为13位的话不需乘1000
      let Y = date.getFullYear() + "-";
      let M =
        (date.getMonth() + 1 < 10
          ? "0" + (date.getMonth() + 1)
          : date.getMonth() + 1) + "-";
      let D = date.getDate() + " ";
      let h = date.getHours() + ":";
      let m = date.getMinutes() + ":";
      let s = date.getSeconds();
      return Y + M + D + h + m + s;
    },
  },
  components: {
    PrismEditor,
    jsonView,
  },

  props: {
    tableList: {
      type: Array,
      default: null,
    },
    total: {
      type: Number,
      default: 0,
    },
    loading: {
      type: Boolean,
      default: true,
    },
  },
  data () {
    return {
      currentPage: 1,
      MKDialogVisible: false,
      requestExample: "",
      responseExample: "",
      lineNumbers: true,
      readonly: true,
    };
  },
  methods: {
    handleCurrentChange (val) {
      this.currentPage = val;
      this.$emit("pageChange", val);
    },
    highlighter (code) {
      return highlight(code, languages.js);
    },
    handleEdit (index, row) {
      //   this.$router.push({ path: "/app/add?message=" + JSON.stringify(row) });
      console.log(row);
      this.MKDialogVisible = true;

      this.requestExample = row;
      // this.requestExample = {}
    },
    handleDelete (index, row) {
      //   const id = row.appId;
      //   appDelete(id).then((res) => {
      //     if (res.code === 200) {
      //       this.$message({
      //         message: res.msg,
      //         type: "success",
      //       });
      //       this.$emit("refresh", true);
      //     }
      //   });
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
  height: 500px;
}
.abow_dialog {
  display: flex;
  justify-content: center;
  align-items: Center;
  overflow: scroll;
  .el-dialog {
    margin: 0 auto !important;
    height: 90%;
    overflow: scroll;
    .el-dialog__body {
      position: absolute;
      left: 0;
      top: 54px;
      bottom: 0;
      right: 0;
      padding: 0;
      z-index: 1;
      overflow: scroll;
      overflow-y: scroll;
    }
  }
}
</style>
