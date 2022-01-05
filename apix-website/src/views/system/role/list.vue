<template>
  <div class="main">
    <div class="list_top list_top_bom">
      <div class="list_title titleFont">角色管理</div>
      <div class="list_search">
        <el-input
          suffix-icon="el-icon-search"
          class="list_searchInput"
          size="small"
          v-model="roleName"
          placeholder="输入角色名查询"
          @change="getRoleList('?limit=10&offset=1')" />
        <el-button type="primary" size="small" icon="el-icon-plus" @click="add">添加新角色</el-button>
      </div>
    </div>
    <div class="table_box">
      <el-table  :row-style="{height: '50px'}" :data="tableData" highlight-current-row :header-cell-style="{'font-weight': 400, 'font-size':'16px', color:'#1D1C35'}">
        <el-table-column prop="roleName" label="名称" show-overflow-tooltip></el-table-column>
        <el-table-column prop="remark" label="描述" show-overflow-tooltip></el-table-column>
        <el-table-column prop="updateTime" label="更新时间">
          <template slot-scope="scope">
            <div>{{ scope.row.updateTime | formateDate }}</div>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button type="text" @click="edit(scope.row)" style="color: #2650FF;">配置</el-button>
            <span style="padding: 0 6px; color: #D8D8D8">|</span>
            <el-button type="text" @click="delCert(scope.row)" :class="(scope.row.roleId === 1 || scope.row.roleId === 2)?'del-bro':'del-red'" :disabled="scope.row.roleId === 1 || scope.row.roleId === 2">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        background
        class="list-pagination"
        :current-page.sync="offset"
        layout="prev, pager, next"
        :total="total"
        @current-change="handleCurrentChange"
      />
    </div>
    <el-drawer
      :title="drawerTitle"
      :before-close="handleClose"
      :visible.sync="drawer"
      direction="rtl"
      size="35%"
    >
      <div class="demo-drawer__content">
        <el-form :model="dataForm" ref="dataForm" :rules="rules" label-position="top">
          <el-form-item label="角色名" prop="roleName">
            <el-input maxlength="50" show-word-limit v-model="dataForm.roleName" autocomplete="off" placeholder="请输入角色名"></el-input>
          </el-form-item>
          <el-form-item label="描述" prop="remark">
            <el-input maxlength="100" show-word-limit type="textarea" v-model="dataForm.remark"></el-input>
          </el-form-item>
          <el-form-item label="权限" class="qx-item">
            <el-tree
              :data="menuList"
              :props="menuListTreeProps"
              node-key="menuId"
              ref="menuListTree"
              :default-expand-all="true"
              show-checkbox>
            </el-tree>
          </el-form-item>
        </el-form>
        <div class="demo-drawer__footer">
          <el-button size="small" @click="handleClose">取 消</el-button>
          <el-button size="small" type="primary" style="background-color: #2650FF; border-color: #2650FF;" @click="submitForm('dataForm')" :loading="loading">{{ loading ? '提交中 ...' : '确 定' }}</el-button>
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
      isbuttonShow: false,
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
    this.userId = parseInt(getToken('userId_api'))
    this.getuserList()
    this.getRoles()
    // console.log(this.$router.currentRoute.meta.title)
    const name = this.$router.currentRoute.meta.title
    const buttonList = JSON.parse(sessionStorage.getItem('buttonList'))
    for (let index = 0; index < buttonList.length; index++) {
      if (buttonList[index].name === name) {
        // console.log(buttonList[index].list)
        if (buttonList[index].list.length === 1) {
          this.isbuttonShow = true
        } else {
          this.isbuttonShow = false
        }
      }
    }
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
      getRoles().then(res => {
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
/deep/.el-checkbox__input.is-checked .el-checkbox__inner {
  background-color: #2650ff;
  border-color: #2650ff;
}
/deep/.el-checkbox__input.is-indeterminate .el-checkbox__inner {
  background-color: #2650ff;
  border-color: #2650ff;
}
.del-red {
  color: #f6323c;
}
.del-bro {
  color: #d8d8d8;
}
.qx-item {
  margin-top: 20px;
}
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
      display: flex;
      width: 240px;
    }
    .but-right {
      margin-left: auto;
    }
  }
}
</style>

