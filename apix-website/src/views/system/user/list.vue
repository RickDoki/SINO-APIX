<template>
  <div class="main">
    <div class="list_top list_top_bom">
      <div class="list_title titleFont">用户管理</div>
      <div class="list_search">
        <el-input
          suffix-icon="el-icon-search"
          class="list_searchInput"
          size="small"
          v-model="search.name"
          placeholder="输入用户名查询"
          @change="getuserList()" />
        <el-select size="small" v-model="search.role" placeholder="角色筛选" class="list_searchInput" @change="getuserList()">
          <el-option
            v-for="item in roleList"
            :key="item.roleId"
            :label="item.roleName"
            :value="item.roleId">
          </el-option>
        </el-select>
      </div>
    </div>
    <div class="table_box">
      <el-table :row-style="{height: '50px'}" :data="tableData" highlight-current-row :header-cell-style="{'font-weight': 400, 'font-size':'16px', color:'#1D1C35'}">
        <el-table-column prop="username" label="用户名" width="150" show-overflow-tooltip></el-table-column>
        <el-table-column prop="roleName" label="角色">
          <template slot-scope="scope">
            <el-tag size="small">{{ scope.row.roleName }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="email" label="邮箱"></el-table-column>
        <el-table-column prop="mobile" label="手机号"></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button type="text" @click="edit(scope.row)">配置</el-button>
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
      title="用户设置"
      :before-close="handleClose"
      :visible.sync="drawer"
      direction="rtl"
      size="35%"
    >
    <div class="demo-drawer__content">
      <el-descriptions class="margin-top" :column="1" size="small" border style="margin-bottom: 10px;">
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-mobile-phone"></i>
            手机号
          </template>
          {{ form.mobile }}
        </el-descriptions-item>
      </el-descriptions>
      <el-form :model="form" ref="form" :rules="rules" label-position="top">
        <el-form-item label="用户名" prop="username">
          <el-input maxlength="50" show-word-limit v-model="form.username" autocomplete="off" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input
            placeholder="请输入邮箱"
            v-model="form.email"
            autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item label="角色" prop="roleId">
          <el-select v-model="form.roleId" placeholder="请选择" style="width: 100%;">
            <el-option
              v-for="item in roleList"
              :key="item.roleId"
              :label="item.roleName"
              :value="item.roleId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="是否启用" class="is-required">
          <el-switch v-model="form.enabled" active-color="#2650FF"></el-switch>
        </el-form-item>
      </el-form>
      <div class="demo-drawer__footer">
        <el-button size="small" @click="resetForm('form')">取 消</el-button>
        <el-button type="primary" size="small" style="background-color: #2650FF; border-color: #2650FF;" @click="submitForm('form')" :loading="loading">{{ loading ? '提交中 ...' : '确 定' }}</el-button>
      </div>
    </div>
    </el-drawer>
  </div>
</template>

<script>
import { getList, updateUser } from '@/api/user'
import { getRoles } from '@/api/role'
import { getToken } from '@/utils/auth'
export default {
  components: {
  },
  data () {
    return {
      userId: 0,
      drawer: false,
      loading: false,
      search: {
        name: '',
        role: ''
      },
      form: {
        id: '',
        username: '',
        mobile: '',
        email: '',
        roleId: '',
        enabled: true
      },
      roleList: [],
      offset: 1,
      limit: 10,
      total: 0,
      tableData: [],
      rules: {
        username: [
          { required: true, message: '请输入用户名称', trigger: 'blur' }
        ],
        email: [
          { pattern: /^[a-zA-Z0-9._-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/, message: '请输入正确的邮箱地址' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ],
        roleId: [
          { required: true, message: '请选用户角色', trigger: 'change' }
        ]
      }
    }
  },
  created () {
    this.userId = getToken('userId')
    this.getuserList()
    this.getRoles()
  },
  methods: {
    // 获取列表
    getuserList () {
      let params = `?offset=${this.offset}&limit=${this.limit}`
      if (this.search.name) {
        params += `&username=${this.search.name}`
      }
      if (this.search.role) {
        params += `&roleId=${this.search.role}`
      }
      getList(params).then(res => {
        if (res.code === 200) {
          this.tableData = res.data.userList
          this.total = res.data.total
        }
      })
    },
    // 获取角色列表
    getRoles () {
      getRoles(this.userId).then(res => {
        if (res.code === 200) {
          this.roleList = res.data
        }
      })
    },
    // 重置搜索条件
    resetSearch () {
      this.search.name = ''
      this.search.role = ''
      this.getuserList()
    },
    // 重置表单
    resetForm (formName) {
      this.$refs[formName].resetFields()
    },
    // 修改用户信息
    edit (row) {
      // 打开抽屉
      this.drawer = true
      this.form = {
        id: row.id,
        username: row.username,
        mobile: row.mobile,
        email: row.email,
        roleId: row.roleId,
        enabled: row.enabled
      }
      if (row.roleId === 0) {
        this.form.roleId = ''
      }
    },
    // 提交数据
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // 修改用户
          updateUser(this.form.id, this.form).then(res => {
            if (res.code === 200) {
              // 重置表单
              this.resetForm('form')
              this.drawer = false
              // 查询列表
              this.page = 1
              this.getuserList()
            }
          })
        }
      })
    },
    // 关闭抽屉
    handleClose (done) {
      this.drawer = false
      this.resetForm('form')
    },
    // 删除用户信息
    delCert (row) {
      this.$confirm('确认删除用户：' + row.username + ', 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$message({
          type: 'success',
          message: '删除成功!'
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
      this.offset = val
      this.getuserList()
    }
  }
}
</script>

<style lang='scss' scoped>
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
.box-card {
  margin: 24px;
  .card-top {
    width: 100%;
    display: flex;
    margin-bottom: 24px;
    .input-box {
      width: 280px;
    }
    .but-right {
      margin-left: auto;
    }
  }
}
</style>

