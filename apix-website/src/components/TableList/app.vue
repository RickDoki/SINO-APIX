<template>
  <div class="tableFather">
    <el-table :data="table" stripe empty-text="暂无数据" style="width: 100%">
      <el-table-column align="center" prop="appName" label="应用名称" />
      <el-table-column align="center" prop="appCode" label="APPCode" />
      <el-table-column align="center" prop="creationByUsername" label="创建人" />
      <el-table-column align="center" width="150" prop="creationDate" label="创建时间" />
      <el-table-column align="center" prop="description" label="描述" />
      <el-table-column align="center" label="应用状态">
        <template slot-scope="scope">
          <el-tag :type="status[scope.row.isPublished][0]">
            {{ status[scope.row.isPublished][1] }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="address" width="250" label="操作">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="primary"
            @click="detial(scope.$index, scope.row)"
          >详情</el-button>
          <el-button
            v-show="
              scope.row.isPublished == 60001 || scope.row.isPublished == 60004
            "
            type="success"
            size="mini"
            @click="isPublishedApp(scope.row, '启用')"
          >启用</el-button>
          <el-button
            v-show="
              scope.row.isPublished == 60005
            "
            size="mini"
            type="success"
            @click="isPublishedApp(scope.row, '上架')"
          >上架</el-button>
          <el-button
            v-show="
              scope.row.isPublished == 60002 ||
                scope.row.isPublished == 60003 ||
                scope.row.isPublished == 60005
            "
            size="mini"
            type="warning"
            @click="isPublishedApp(scope.row, '停用')"
          >停用</el-button>
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
      style="margin-top: 20px"
      :current-page.sync="currentPage"
      layout="prev, pager, next"
      :total="total"
      @current-change="handleCurrentChange"
    />
  </div>
</template>

<script>
import { appDelete, published } from '@/api/AboutApp'
export default {
  // props: ['table', 'total'],
  props: {
    table: {
      type: Array,
      default: null
    },
    total: {
      type: Number,
      default: 0
    }
  },
  data() {
    return {
      currentPage: 1,
      status: {
        60001: ['info', '未启用'],
        60005: ['success', '已启用'],
        60002: ['success', '已上架'],
        60003: ['danger', '异常中'],
        60004: ['warning', '已停用']
      }
    }
  },
  methods: {
    // 分页
    handleCurrentChange(val) {
      this.currentPage = val
      this.$emit('pageChange', val)
    },
    // 编辑
    handleEdit(index, row) {
      this.$router.push({ path: '/app/add?message=' + JSON.stringify(row) })
    },
    // 删除
    handleDelete(index, row) {
      const id = row.appCode
      appDelete(id).then((res) => {
        if (res.code === 200) {
          this.$message({
            message: res.msg,
            type: 'success'
          })
          this.$emit('refresh', true)
        }
      })
    },
    // go page detail
    detial(e, i) {
      console.log(i)
      this.$router.push({ path: '/app/detail?appCode=' + i.appCode })
    },
    // 启用
    isPublishedApp(e, status) {
      // console.log(e, status)
      const appCode = e.appCode
      if (status === '启用') {
        const data = {
          isPublished: '60005'
        }
        published(appCode, data).then(res => {
          if (res.code === 200) {
            this.$message({
              message: res.msg,
              type: 'success'
            })
            this.$emit('refresh', true)
          }
        })
      } else if (status === '上架') {
        const data = {
          isPublished: '60002'
        }
        published(appCode, data).then(res => {
          if (res.code === 200) {
            this.$message({
              message: res.msg,
              type: 'success'
            })
            this.$emit('refresh', true)
          }else {
             this.$message({
              message: res.msg,
              type: 'error'
            })
          }
        })
      } else {
        const data = {
          isPublished: '60004'
        }
        published(appCode, data).then(res => {
          if (res.code === 200) {
            this.$message({
              message: res.msg,
              type: 'success'
            })
            this.$emit('refresh', true)
          }
        })
      }
    }
  }
}
</script>

<style lang='scss' scoped>
.tableFather {
  padding: 30px;
  .table {
    width: 100%;
  }
}
</style>
