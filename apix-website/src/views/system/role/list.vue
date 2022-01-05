<template>
  <div class="main">
    <div class="list_top list_top_bom">
      <div class="list_title">角色管理</div>
      <div class="list_search">
        <el-input
          suffix-icon="el-icon-search"
          class="list_searchInput"
          size="small"
          clearable
          v-model="roleName"
          placeholder="输入角色名查询"
          @change="getRoleList('?limit=10&offset=1')" />
      </div>
    </div>
    <div class="table_box">
      <el-table :row-style="{height: '50px'}" :data="tableData" highlight-current-row :header-cell-style="{'font-weight': 400, 'font-size':'16px', color:'#1D1C35'}">
        <el-table-column prop="roleName" label="名称" show-overflow-tooltip></el-table-column>
        <el-table-column prop="remark" label="描述" show-overflow-tooltip></el-table-column>
        <el-table-column prop="updateTime" label="更新时间">
          <template slot-scope="scope">
            <div>{{ scope.row.updateTime | formateDate }}</div>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button type="text" @click="edit(scope.row)">配置</el-button>
            <span class="handle">|</span>
            <el-button type="text" @click="delCert(scope.row)" :class="(scope.row.roleId === 1 || scope.row.roleId === 2)?'del-bro':'del-red'" :disabled="scope.row.roleId === 1 || scope.row.roleId === 2">删除</el-button>
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
      :title="drawerTitle"
      :before-close="handleClose"
      :visible.sync="drawer"
      direction="rtl"
      size="35%"
    >
    <div class="demo-drawer__content">
      <el-form :model="dataForm" ref="dataForm" :rules="rules" label-position="top">
        <el-form-item label="角色名" prop="roleName">
          <el-input maxlength="25" show-word-limit v-model="dataForm.roleName" autocomplete="off" placeholder="请输入角色名"></el-input>
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
            check-strictly
            :default-expand-all="true"
            show-checkbox>
          </el-tree>
        </el-form-item>
      </el-form>
      <div class="demo-drawer__footer">
        <el-button size="small" @click="handleClose">取 消</el-button>
        <el-button size="small" type="primary" style="background-color: #2C66FB; border-color: #2C66FB;" @click="submitForm('dataForm')" :loading="loading">{{ loading ? '提交中 ...' : '确 定' }}</el-button>
      </div>
    </div>
    </el-drawer>
  </div>
</template>

<script>
import { getRoleList, getRoutes, getroleNav, addRole, updateRole, deleteRole, getRoleInfo } from '@/api/role'
// import { getroleNav } from '@/api/user'
import { treeDataTranslate } from '@/utils'
import { getToken } from '@/utils/auth'
import moment from 'moment'
export default {
  components: {
  },
  filters: {
    formateDate (date) {
      return moment(date).format('YYYY-MM-DD HH:mm:ss')
    }
  },
  data () {
    return {
      menuList: [],
      menuListTreeProps: {
        label: 'name',
        children: 'children'
      },
      tempKey: -666666, // 临时key, 用于解决tree半选中状态项不能传给后台接口问题. # 待优化
      drawer: false,
      loading: false,
      drawerTitle: '',
      userId: 0, // 缓存中取
      roleName: '',
      dataForm: {
        roleId: 0,
        roleName: '',
        remark: ''
      },
      rules: {
        roleName: [
          { required: true, message: '请输入角色名称', trigger: 'blur' }
        ]
      },
      page: 1,
      limit: 10,
      total: 0,
      tableData: []
    }
  },
  created () {
    this.userId = parseInt(getToken('userId_api'))
    this.getRoleList()
    const name = this.$router.currentRoute.meta.title
  },
  methods: {
    // 获取列表
    getRoleList () {
      let params = `?page=${this.page}&limit=${this.limit}&userId=${this.userId}`
      if (this.roleName) {
        params += `&roleName=${this.roleName}`
      }
      getRoleList(params).then(res => {
        if (res.code === 200) {
          this.tableData = res.data.list
          this.total = res.data.totalCount
        }
      })
    },
    // 获取权限列表
    getRoutes (roleId) {
      const id = getToken('userId_api')
      getroleNav(id).then(res => {
        if (res.code === 200) {
          this.menuList = treeDataTranslate(res.data, 'menuId')
          if (this.dataForm.roleId) {
            this.getRoleInfo(this.dataForm.roleId)
          }
        }
      })
    },
    // 创建角色
    add () {
      // 打开抽屉
      this.drawer = true
      this.drawerTitle = '创建角色'
      this.getRoutes()
    },
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (!this.$refs.menuListTree.getCheckedKeys().length) {
            this.$message('请选择要分配的权限！')
            return
          }
          const params = {
            roleName: this.dataForm.roleName,
            remark: this.dataForm.remark,
            createUserId: this.userId,
            menuIdList: [].concat(this.$refs.menuListTree.getCheckedKeys(), [this.tempKey], this.$refs.menuListTree.getHalfCheckedKeys())
          }
          if (this.dataForm.roleId === 0) {
            // 添加角色
            addRole(params).then(res => {
              if (res.code === 200) {
                // 重置表单
                this.resetForm('dataForm')
                this.drawer = false
                this.$refs.menuListTree.setCheckedKeys([])
                // 查询列表
                this.page = 1
                this.getRoleList()
              }
            })
          } else {
            // 编辑角色
            params.roleId = this.dataForm.roleId
            updateRole(params).then(res => {
              if (res.code === 200) {
                // 重置表单
                this.resetForm('dataForm')
                this.drawer = false
                this.$refs.menuListTree.setCheckedKeys([])
                // 查询列表
                this.page = 1
                this.getRoleList()
              }
            })
          }
        }
      })
    },
    resetForm (formName) {
      this.$refs[formName].resetFields()
    },
    // 修改角色信息
    edit (row) {
      // 打开抽屉
      this.drawer = true
      this.drawerTitle = '配置角色'
      this.dataForm.roleId = row.roleId
      this.getRoutes()
    },
    // 获取角色信息
    getRoleInfo (roleId) {
      getRoleInfo(roleId).then(res => {
        if (res.code === 200) {
          const data = res.data
          this.dataForm.roleName = data.roleName
          this.dataForm.remark = data.remark
          var idx = data.menuIdList.indexOf(this.tempKey)
          if (idx !== -1) {
            data.menuIdList.splice(idx, data.menuIdList.length - idx)
          }
          this.$refs.menuListTree.setCheckedKeys(data.menuIdList)
        }
      })
    },
    // 关闭抽屉
    handleClose () {
      this.drawer = false
      this.resetForm('dataForm')
      this.$refs.menuListTree.setCheckedKeys([])
    },
    // 删除角色信息
    delCert (row) {
      const params = {
        roleIds: [row.roleId]
      }
      this.$confirm('确认删除角色：' + row.roleName + ', 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteRole(params).then(res => {
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
          this.page = 1
          this.getRoleList()
        })
      }).catch(() => {
      })
    },
    handleSizeChange (val) {
      console.log(`每页 ${val} 条`)
      this.limit = val
      this.getRoleList()
    },
    handleCurrentChange (val) {
      console.log(`当前页: ${val}`)
      this.page = val
      this.getRoleList()
    }
  }
}
</script>

<style lang='scss' scoped>
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
</style>

