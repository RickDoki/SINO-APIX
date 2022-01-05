<template>
  <div class="main">
    <div class="list_top list_top_bom">
      <div class="list_title">上游列表</div>
      <div class="list_search">
        <el-input
          size="small"
          placeholder="搜索"
          suffix-icon="el-icon-search"
          v-model="search.name"
          class="list_searchInput"
          @change="getUpstreamList()"
        >
        </el-input>
        <el-button type="primary" size="small" icon="el-icon-plus" @click="gotoDetail">添加新上游</el-button>
      </div>
    </div>
    <div class="table_box">
      <el-table :row-style="{height: '50px'}" :data="tableData" highlight-current-row :header-cell-style="{'font-weight': 400, 'font-size':'16px', color:'#1D1C35'}">
        <el-table-column prop="name" label="上游名称" show-overflow-tooltip></el-table-column>
        <el-table-column prop="protocol" label="协议" width="100"></el-table-column>
        <el-table-column prop="server_address" label="服务地址" show-overflow-tooltip width="320"></el-table-column>
        <el-table-column prop="description" label="描述" show-overflow-tooltip width="300"></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button type="text" @click="edit(scope.row)">配置</el-button>
            <span class="handle">|</span>
            <el-button type="text" @click="view(scope.row)">查看</el-button>
            <span class="handle">|</span>
            <el-button type="text" @click="delCert(scope.row)" class="textBut-danger">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        background
        class="list-pagination"
        :current-page.sync="page"
        layout="prev, pager, next"
        :total="total"
        @current-change="handleCurrentChange"
      />
    </div>
    <el-drawer
      title="数据编辑器"
      :before-close="handleClose"
      :visible.sync="drawer"
      direction="rtl"
      size="35%"
    >
      <div class="demo-drawer__content">
        <div style="text-align: right;">
          <div class="but-right2">
            <el-select size="small" v-model="codeType" style="width: 85px;">
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
            <el-button size="small" type="primary" style="background-color: #2650FF; margin-left: 10px;">复制</el-button>
            <el-button size="small">文档</el-button>
          </div>
        </div>
        <div class="code-box">
            <prism-editor
              class="my-editor height-300 default-prism-editor"
              v-model="requestExample"
              :highlight="highlighter"
              :line-numbers="lineNumbers"
            />
        </div>
        <div class="demo-drawer__footer">
          <el-button @click="resetForm('form')">取 消</el-button>
          <el-button style="background-color: #2650FF;" type="primary" @click="$refs.drawer.closeDrawer()" :loading="loading">{{ loading ? '提交中 ...' : '提 交' }}</el-button>
        </div>
      </div>
    </el-drawer>
    <el-drawer
      title="上游服务详情"
      :before-close="handleClose"
      :visible.sync="drawerInfo"
      direction="rtl"
      size="45%"
    >
      <div class="demo-drawer__content">
        <el-descriptions title=" " size="medium" :column="2" border>
          <el-descriptions-item label="服务名称">{{ upstreamInfo.name }}</el-descriptions-item>
          <el-descriptions-item label="负载均衡算法">{{ upstreamInfo.load_balance }}</el-descriptions-item>
          <el-descriptions-item label="服务端口">{{ upstreamInfo.port }}</el-descriptions-item>
          <el-descriptions-item label="路由前置路径">{{ upstreamInfo.prefix_path }}</el-descriptions-item>
          <el-descriptions-item label="协议">{{ upstreamInfo.protocol }}</el-descriptions-item>
          <el-descriptions-item label="服务地址">{{ upstreamInfo.server_address }}</el-descriptions-item>
          <el-descriptions-item label="服务描述">{{ upstreamInfo.description }}</el-descriptions-item>
        </el-descriptions>
      </div>
    </el-drawer>
  </div>
</template>

<script>
import { getUpstreamList, deleteUpstream } from '@/api/upstream'
import { highlight, languages } from 'prismjs/components/prism-core'
import 'vue-prism-editor/dist/prismeditor.min.css' // import the styles somewhere
import { PrismEditor } from 'vue-prism-editor'
import 'prismjs/components/prism-clike'
import 'prismjs/components/prism-javascript'
import 'prismjs/themes/prism-tomorrow.css' // import syntax highlighting styles
export default {
  components: {
    PrismEditor
  },
  data () {
    return {
      codeType: 'json',
      requestExample: '{}',
      // 可编辑模式
      lineNumbers: true,
      drawer: false,
      drawerInfo: false,
      upstreamInfo: {},
      loading: false,
      search: {
        name: ''
      },
      form: {
        name: '',
        email: '',
        password: '',
        role: []
      },
      options: [{
        value: 'json',
        label: 'JSON'
      }, {
        value: 'yaml',
        label: 'YAML'
      }],
      page: 1,
      limit: 10,
      total: 0,
      tableData: [{ name: '', code: '', status: '', version: '' }, { name: '', code: '', status: '', version: '' }]
    }
  },
  created () {
    this.getUpstreamList()
  },
  methods: {
    // 跳转创建上游管理信息页面
    gotoDetail () {
      this.$router.push({ path: '/api/upstream/create' })
    },
    // 获取上游服务列表
    getUpstreamList () {
      let params = `?page=${this.page}&limit=${this.limit}`
      if (this.search.name) {
        params += `&name=${this.search.name}`
      }
      getUpstreamList(params).then(res => {
        if (res.code === 200) {
          this.tableData = res.data.upstreamList
          this.total = res.data.total
        }
      })
    },
    // 查看上游服务详情
    view (row) {
      this.drawerInfo = true
      this.upstreamInfo = row
    },
    // 修改上游数据信息
    edit (row) {
      this.$router.push({ path: '/api/upstream/edit/' + row.id })
    },
    // 打开数据编辑器抽屉
    editData () {
      this.drawer = true
    },
    // 关闭抽屉
    handleClose (done) {
      this.drawer = false
      this.drawerInfo = false
    },
    // 删除上游数据信息
    delCert (row) {
      this.$confirm('确认删除上游服务：' + row.name + ', 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteUpstream(row.id).then(res => {
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
          this.page = 1
          this.getUpstreamList()
        })
      }).catch(() => {
      })
    },
    handleSizeChange (val) {
      console.log(`每页 ${val} 条`)
      this.limit = val
      this.getuserList()
    },
    handleCurrentChange (val) {
      console.log(`当前页: ${val}`)
      this.page = val
      this.getuserList()
    },
    highlighter (code) {
      return highlight(code, languages.js)
    }
  }
}
</script>

<style lang='scss' scoped>
/* required class */
.default-prism-editor {
  /* we dont use `language-` classes anymore so thats why we need to add background and text color manually */
  /*background: #2d2d2d;*/
  color: #80118c;

  /* you must provide font-family font-size line-height. Example: */
  font-family: Fira code, Fira Mono, Consolas, Menlo, Courier, monospace;
  font-size: 16px;
  margin: 24px 0px;
  padding: 15px 0px;
  min-height: 520px;
  border: 1px solid #dcdfe6;
  border-radius: 4px;
}

/* optional class for removing the outline */
.prism-editor__textarea:focus {
  outline: none;
}
// .code-box {
//   margin: 24px 0px;
//   padding: 15px 0px;
//   border: 1px solid #DCDFE6;
//   min-height: 520px;
// }
.el-form-item {
  margin-bottom: 5px;
}
.demo-drawer__content {
  padding: 0px 24px;
}
.demo-drawer__footer {
  text-align: right;
  margin-top: 40px;
}
.but-right2 {
  display: flex;
  justify-content: flex-end;
}
.box-card {
  margin: 24px;
  .card-top {
    width: 100%;
    display: flex;
    margin-bottom: 24px;
    .input-box {
      display: flex;
      width: 240px;
    }
    .but-right {
      margin-left: auto;
    }
  }
}
</style>

