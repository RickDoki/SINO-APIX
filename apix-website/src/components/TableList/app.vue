<template>
  <div class="tableFather">
    <el-table :data="table" stripe empty-text="暂无数据" style="width: 100%">
      <el-table-column align="center" prop="appName" label="应用名称" />
      <el-table-column align="center" prop="appCode" label="APPCode" />
      <el-table-column
        align="center"
        prop="creationByUsername"
        label="创建人"
      />
      <el-table-column
        align="center"
        width="160"
        prop="creationDate"
        label="创建时间"
      />
      <el-table-column
        align="center"
        show-overflow-tooltip
        prop="description"
        label="描述"
      />
      <el-table-column align="center" label="应用状态">
        <template slot-scope="scope">
          <el-tag size="mini" :type="status[scope.row.isPublished][0]">
            {{ status[scope.row.isPublished][1] }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="address" width="250" label="操作">
        <template slot-scope="scope">
          <el-link
            size="mini"
            type="primary"
            :underline="false"
            v-if="isxiangqingShow"
            @click="detial(scope.$index, scope.row)"
            >详情</el-link
          >
          <el-link
            v-show="
              scope.row.isPublished == 60001 || scope.row.isPublished == 60004
            "
            type="success"
            v-if="isqiyongShow"
            :underline="false"
            size="mini"
            style="margin-left: 15px"
            @click="isPublishedApp(scope.row, '启用')"
            >启用</el-link
          >
          <el-link
            v-show="scope.row.isPublished == 60005"
            size="mini"
            v-if="isshangjiaShow"
            :underline="false"
            style="margin-left: 15px"
            type="success"
            @click="isPublishedApp(scope.row, '上架')"
            >上架</el-link
          >
          <el-link
            v-show="
              scope.row.isPublished == 60002 ||
              scope.row.isPublished == 60003 ||
              scope.row.isPublished == 60005
            "
            size="mini"
            v-if="istingyongShow"
            style="margin-left: 15px"
            type="warning"
            :underline="false"
            @click="isPublishedApp(scope.row, '停用')"
            >停用</el-link
          >
          <!-- <el-button
            size="mini"
            @click="handleEdit(scope.$index, scope.row)"
          >编辑</el-button>
          <el-button
            size="mini"
            type="danger"
            @click="handleDelete(scope.$index, scope.row)"
          >删除</el-button> -->
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      style="float: right; margin: 20px"
      :current-page.sync="currentPage"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      @current-change="handleCurrentChange"
      @size-change="handleSizeChange"
      :page-sizes="[10, 20, 30, 40]"
      :page-size="size"
    />
  </div>
</template>

<script>
import { appDelete, published } from "@/api/AboutApp";
export default {
  // props: ['table', 'total'],
  props: {
    table: {
      type: Array,
      default: null,
    },
    total: {
      type: Number,
      default: 0,
    },
  },
  data() {
    return {
      currentPage: 1,
      status: {
        60001: ["info", "未启用"],
        60005: ["success", "已启用"],
        60002: ["success", "已上架"],
        60003: ["danger", "异常中"],
        60004: ["warning", "已停用"],
      },
      size: 10,
      isxiangqingShow:false,
      isshangjiaShow:false,
      isqiyongShow:false,
      istingyongShow:false
    };
  },
  created() {
    const name = this.$router.currentRoute.meta.title;
    console.log(name);
    const buttonList = JSON.parse(sessionStorage.getItem("buttonList"));
    console.log(buttonList);
    for (let index = 0; index < buttonList.length; index++) {
      if (buttonList[index].name === name) {
        // console.log(buttonList[index].list)
        for (let index1 = 0; index1 < buttonList[index].list.length; index1++) {
          if (buttonList[index].list[index1].name === "详情") {
            this.isxiangqingShow = true;
          }
          if (buttonList[index].list[index1].name === "上架") {
            this.isshangjiaShow = true;
          }
          if (buttonList[index].list[index1].name === "启用") {
            this.isqiyongShow = true;
          }
          if (buttonList[index].list[index1].name === "停用") {
            this.istingyongShow = true;
          }
        }
      }
    }
  },
  methods: {
    // 分页
    handleCurrentChange(val) {
      this.currentPage = val;
      this.$emit("pageChange", val);
    },
    handleSizeChange(val) {
      console.log(val);
      this.$emit("sizeChange", val);
    },
    // 编辑
    handleEdit(index, row) {
      this.$router.push({ path: "/app/add?message=" + JSON.stringify(row) });
    },
    // 删除
    handleDelete(index, row) {
      const id = row.appCode;
      appDelete(id).then((res) => {
        if (res.code === 200) {
          this.$message({
            message: res.msg,
            type: "success",
          });
          this.currentPage = 1;
          this.size = 10;
          this.$emit("refresh", true);
        }
      });
    },
    // go page detail
    detial(e, i) {
      console.log(i);
      this.$router.push({ path: "/app/detail?appCode=" + i.appCode });
    },
    // 启用
    isPublishedApp(e, status) {
      // console.log(e, status)
      const appCode = e.appCode;
      if (status === "启用") {
        const data = {
          isPublished: "60005",
        };
        published(appCode, data).then((res) => {
          if (res.code === 200) {
            this.$message({
              message: res.msg,
              type: "success",
            });
            this.$emit("refresh", true);
            this.currentPage = 1;
            this.size = 10;
          }
        });
      } else if (status === "上架") {
        const data = {
          isPublished: "60002",
        };
        published(appCode, data).then((res) => {
          if (res.code === 200) {
            this.$message({
              message: res.msg,
              type: "success",
            });
            this.$emit("refresh", true);
            this.currentPage = 1;
            this.size = 10;
          } else {
            this.$message({
              message: res.msg,
              type: "error",
            });
          }
        });
      } else {
        const data = {
          isPublished: "60004",
        };
        published(appCode, data).then((res) => {
          if (res.code === 200) {
            this.$message({
              message: res.msg,
              type: "success",
            });
            this.$emit("refresh", true);
            this.currentPage = 1;
            this.size = 10;
          }
        });
      }
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
</style>
