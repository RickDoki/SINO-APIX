<template>
  <div class="main">
    <el-card class="box-card" shadow="never">
      <div class="card-top">
        <div class="input-box">
          <span>组织名：</span>
          <el-input
            size="small"
            v-model="search.name"
            style="width: 77%"
            placeholder="请输入组织名查询"
          ></el-input>
        </div>
        <div style="margin-left: 40px" class="input-box">
          <!-- <span>角色：</span>
          <el-select
            size="small"
            v-model="search.role"
            placeholder="请选择"
            style="width: 77%"
          >
            <el-option
              v-for="item in roleList"
              :key="item.roleId"
              :label="item.roleName"
              :value="item.roleId"
            >
            </el-option>
          </el-select> -->
        </div>
        <div class="but-right">
          <el-button size="small" @click="resetSearch">重置</el-button>
          <el-button
            type="primary"
            size="small"
            style="background-color: #2c66fb"
            @click="getuserList()"
            >查询</el-button
          >
        </div>
      </div>
      <el-table
        :row-style="{ height: '48px' }"
        :data="tableData"
        stripe
        :header-cell-style="{ background: '#F0F2F5', color: '#333333' }"
      >
        <el-table-column
          prop="name"
          label="组织名"
          show-overflow-tooltip
        ></el-table-column>
        <!-- <el-table-column prop="roleName" label="角色">
          <template slot-scope="scope">
            <el-tag size="small">{{ scope.row.roleName }}</el-tag>
          </template>
        </el-table-column> -->
        <!-- <el-table-column prop="email" label="邮箱"></el-table-column>
        <el-table-column prop="mobile" label="手机号"></el-table-column> -->
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button
              type="text"
              v-if="ischangeShow"
              @click="edit(scope.row)"
              style="color: #2c66fb"
              >修改</el-button
            >
            <span style="padding: 0 6px; color: #D8D8D8">|</span>
            <el-button
              type="text"
              v-if="istianjiaShow"
              @click="editUser(scope.row)"
              style="color: #2c66fb"
              >添加成员</el-button
            >
            <!-- <span style="padding: 0 6px; color: #D8D8D8">|</span> -->
            <!-- <el-button type="text" @click="delCert(scope.row)" style="color: #F6323C;">删除</el-button> -->
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        style="margin: 24px 0px; float: right"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="offset"
        :page-sizes="[10, 20, 30, 40, 50]"
        :page-size="limit"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      >
      </el-pagination>
    </el-card>
    <el-drawer
      title="添加成员"
      :before-close="handleCloseAdd"
      :visible.sync="drawerAdd"
      direction="rtl"
      size="35%"
      ><div class="demo-drawer__content">
        <el-form
          :model="formuser"
          ref="formuser"
          :rules="rulesuser"
          label-position="top"
        >
          <el-form-item label="手机号" prop="mobile">
            <el-input
              maxlength="11"
              show-word-limit
              v-model="formuser.mobile"
              autocomplete="off"
              placeholder="请输入手机号"
            ></el-input>
          </el-form-item>
          <el-form-item label="组织名" prop="orgId">
            <el-input
              placeholder="请输如组织名"
              v-model="formuser.orgId"
              disabled
              autocomplete="off"
            ></el-input>
          </el-form-item>
          <el-form-item label="邮箱" prop="email">
            <el-input
              placeholder="请输入邮箱"
              v-model="formuser.email"
              autocomplete="off"
            ></el-input>
          </el-form-item>
          <el-form-item label="用户名" prop="用户名">
            <el-input
              placeholder="请输入用户名"
              v-model="formuser.username"
              autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-form>
      </div>
      <div style="padding-right: 20px" class="demo-drawer__footer">
        <el-button
          type="primary"
          size="small"
          style="background-color: #2c66fb; border-color: #2c66fb"
          @click="orgSure('formuser')"
          >{{ "确 定" }}</el-button
        >
      </div>
    </el-drawer>
    <el-drawer
      title="组织管理"
      :before-close="handleClose"
      :visible.sync="drawer"
      direction="rtl"
      size="35%"
    >
      <div class="demo-drawer__content">
        <!-- <el-descriptions
          class="margin-top"
          :column="1"
          size="small"
          border
          style="margin-bottom: 10px"
        >
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-mobile-phone"></i>
              手机号
            </template>
            {{ form.mobile }}
          </el-descriptions-item>
        </el-descriptions> -->
        <el-form :model="form" ref="form" :rules="rules" label-position="top">
          <el-form-item label="组织名" prop="username">
            <el-input
              maxlength="25"
              show-word-limit
              v-model="form.username"
              autocomplete="off"
              placeholder="请输入新的组织名"
            ></el-input>
          </el-form-item>
          <!-- <el-form-item label="邮箱" prop="email">
            <el-input
              placeholder="请输入邮箱"
              v-model="form.email"
              autocomplete="off"
            ></el-input>
          </el-form-item> -->
          <!-- <el-form-item label="角色" prop="roleId">
            <el-select
              v-model="form.roleId"
              placeholder="请选择"
              style="width: 100%"
            >
              <el-option
                v-for="item in roleList"
                :key="item.roleId"
                :label="item.roleName"
                :value="item.roleId"
              >
              </el-option>
            </el-select>
          </el-form-item> -->
          <!-- <el-form-item label="是否启用" class="is-required">
            <el-switch
              v-model="form.enabled"
              active-color="#2C66FB"
            ></el-switch>
          </el-form-item> -->
        </el-form>
        <div class="demo-drawer__footer">
          <el-button size="small" @click="resetForm('form')">取 消</el-button>
          <el-button
            type="primary"
            size="small"
            style="background-color: #2c66fb; border-color: #2c66fb"
            @click="submitForm('form')"
            :loading="loading"
            >{{ loading ? "提交中 ..." : "确 定" }}</el-button
          >
        </div>
      </div>
    </el-drawer>
  </div>
</template>

<script>
import { getList, updateUser, orgList, neworg, orgAddUser } from "@/api/user";
import { getRoles } from "@/api/role";
import { getToken } from "@/utils/auth";
export default {
  components: {},
  data() {
    return {
      userId: 0,
      drawer: false,
      loading: false,
      drawerAdd: false,
      search: {
        name: "",
        role: "",
      },
      formuser: {
        mobile: "",
        orgId: "",
        email: "",
        username: "",
      },
      form: {
        id: "",
        username: "",
        mobile: "",
        email: "",
        roleId: "",
        enabled: true,
      },
      rowid: "",
      roleList: [],
      offset: 1,
      limit: 10,
      total: 0,
      tableData: [],
      rules: {
        username: [
          { required: true, message: "请输入新的组织名", trigger: "blur" },
        ],
        email: [
          {
            pattern: /^[a-zA-Z0-9._-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/,
            message: "请输入正确的邮箱地址",
          },
        ],
        password: [{ required: true, message: "请输入密码", trigger: "blur" }],
        roleId: [
          { required: true, message: "请选用户角色", trigger: "change" },
        ],
      },
      rulesuser: {
        email: [
          {
            pattern: /^[a-zA-Z0-9._-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/,
            message: "请输入正确的邮箱地址",
          },
        ],
        mobile: [
          { required: true, message: "请输入手机号", trigger: "change" },
        ],
      },
      orgName: "",
      ischangeShow:false,
      istianjiaShow:false
    };
  },
  created() {
    this.userId = parseInt(getToken("userId_api"));
    this.getuserList();
    this.getRoles();
    const name = this.$router.currentRoute.meta.title
    const buttonList = JSON.parse(sessionStorage.getItem('buttonList'))
    console.log(buttonList)
    for (let index = 0; index < buttonList.length; index++) {
      if(buttonList[index].name === name) {
        // console.log(buttonList[index].list)
        for (let index1 = 0; index1 < buttonList[index].list.length; index1++) {
          
          if(buttonList[index].list[index1].name === '修改') {
            this.ischangeShow = true
          }
          if(buttonList[index].list[index1].name === '添加成员') {
            this.istianjiaShow = true
          }
        }
      }
    }
  },
  methods: {
    // 获取列表
    getuserList() {
      let params = `?offset=${this.offset}&limit=${this.limit}`;
      if (this.search.name) {
        params += `&name=${this.search.name}`;
      }
      if (this.search.role) {
        params += `&roleId=${this.search.role}`;
      }
      orgList(params).then((res) => {
        if (res.code === 200) {
          this.tableData = res.data.orgList;
          this.total = res.data.total;
        }
      });
    },
    // 获取角色列表
    getRoles() {
      getRoles().then((res) => {
        if (res.code === 200) {
          this.roleList = res.data;
        }
      });
    },
    editUser(row) {
      console.log(row);
      this.drawerAdd = true;
      this.formuser.orgId = row.name;
      this.orgName = row.id;
    },
    // 重置搜索条件
    resetSearch() {
      this.search.name = "";
      this.search.role = "";
      this.getuserList();
    },
    // 重置表单
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    // 修改用户信息
    edit(row) {
      // 打开抽屉
      this.drawer = true;
      console.log(row);
      // this.form
      this.rowid = row.id;
      // this.form = {
      //   id: row.id,
      //   username: row.username,
      //   mobile: row.mobile,
      //   email: row.email,
      //   roleId: row.roleId,
      //   enabled: row.enabled,
      // };
      // if (row.roleId === 0) {
      //   this.form.roleId = "";
      // }
    },
    // 提交数据
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // 修改用户
          console.log(this.form);
          const data = {
            name: this.form.username,
          };
          console.log(data);
          neworg(this.rowid, data).then((res) => {
            if (res.code === 200) {
              // 重置表单
              this.resetForm("form");
              this.drawer = false;
              // 查询列表
              this.page = 1;
              this.getuserList();
            }
          });
        }
      });
    },
    orgSure(formName) {
      console.log(9999);
      this.$refs[formName].validate((valid) => {
        if (valid) {
          const data = {
            mobile: this.formuser.mobile,
            orgId: this.orgName,
            email: this.formuser.email,
            username: this.formuser.username,
          };
          orgAddUser(data).then((res) => {
            // console.log(res)
            if (res.code === 200) {
              this.messageOK(res.msg);
              this.formuser = {
                mobile: "",
                orgId: "",
                email: "",
                username: "",
              };
              this.orgName = ''
              this.drawerAdd = false
            } else {
              this.messageERROR(res.msg);
            }
          });
        }
      });
    },
    // 成功消息
    messageOK(msg) {
      this.$message({
        message: msg,
        type: "success",
      });
    },
    // 失败消息
    messageERROR(msg) {
      this.$message({
        message: msg,
        type: "error",
      });
    },
    // 关闭抽屉
    handleClose(done) {
      this.drawer = false;
      this.resetForm("form");
    },
    // 关闭抽屉
    handleCloseAdd(done) {
      this.drawerAdd = false;
      this.resetForm("formuser");
    },
    // 删除用户信息
    delCert(row) {
      this.$confirm("确认删除用户：" + row.username + ", 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          // this.$message({
          //   type: "success",
          //   message: "删除成功!",
          // });
        })
        .catch(() => {});
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.limit = val;
      this.getuserList();
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.offset = val;
      this.getuserList();
    },
  },
};
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
  margin: 10px;
  // padding-top
  min-height: calc(100vh - 185px);

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

