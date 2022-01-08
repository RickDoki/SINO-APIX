<template>
  <div class="main">
    <div class="list_top">
      <div class="list_title">服务名称</div>
      <div class="list_search">
        <div class="but-left">
          <el-dropdown>
            <el-button type="primary" size="small">
              操作<i class="el-icon-arrow-down el-icon--right"></i>
            </el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>黄金糕</el-dropdown-item>
              <el-dropdown-item>狮子头</el-dropdown-item>
              <el-dropdown-item>螺蛳粉</el-dropdown-item>
              <el-dropdown-item>双皮奶</el-dropdown-item>
              <el-dropdown-item>蚵仔煎</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
        <el-button type="primary" size="small">编辑文档</el-button>
      </div>
    </div>
    <div class="secondTitle">创建服务来管理和代理现有API或发布到门户。</div>
    <div class="status">
      <div class="left-span">
        <span>已关联api数量: </span>
        <span>15个</span>
      </div>
      <div class="time">
        <div>
          <span>创建时间 : </span>
          <span>2021-08-05 10:05:00:00</span>
        </div>
        <div>
          <span>更新时间 : </span>
          <span>2021-08-05 10:05:00:00</span>
        </div>
      </div>
    </div>
    <div class="table_box mode-margin">
      <!-- <p>版本</p> -->
      <div class="serve-table">
        <div class="table-tile">版本</div>
        <el-button
          plain
          type="primary"
          size="small"
          @click="gonewEdition"
          class="add-but"
          >关联API
        </el-button>
      </div>
      <el-table
        :data="table"
        empty-text="暂无数据"
        :row-style="{ height: '50px' }"
        highlight-current-row
        :header-cell-style="{ 'font-weight': 400, color: '#494E6A' }"
      >
        <el-table-column prop="appName" label="应用名称" />
        <el-table-column prop="appCode" label="APPCode" />
        <el-table-column prop="appCode" label="启用状态" />
        <el-table-column prop="appCode" label="描述" />
        <!-- <el-table-column label="操作" width="180px">
          <template slot-scope="scope">
            <el-button type="text" @click="edition(scope.row)">编辑</el-button>
            <span class="handle">|</span>
            <el-button
              style="color: red"
              type="text"
              @click="getMessage(scope.row)"
              >删除</el-button
            >
          </template>
        </el-table-column> -->
      </el-table>
    </div>
    <el-drawer
      title="关联"
      size='40%'
      :visible.sync="drawer"
      :direction="direction"
      :before-close="handleClose"
    >
      <div class="formBox" style="margin-left:20px">
        <el-form
          :model="ruleForm"
          :rules="rules"
          ref="ruleForm"
          label-position="top"
        >
          <el-form-item label="版本名称" prop="name">
            <el-input
              v-model="ruleForm.name"
              placeholder=""
              class="inputWidth drawer"
              clearable
            >
            </el-input>
          </el-form-item>
          <el-form-item label="版本描述" prop="describe">
            <el-input
              v-model="ruleForm.describe"
              placeholder=""
              class="inputWidth drawer"
              clearable
            >
            </el-input>
          </el-form-item>
          <el-form-item label="关联API" prop="API">
            <el-select
              class="inputWidth drawer"
              v-model="ruleForm.API"
              placeholder="请选择"
            >
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
            <!-- <span @click="createdApi" class="show-but"
              >还没有API?去创建 >>
            </span> -->
          </el-form-item>
          <div class="bottom_button_a">
            <el-button
              size="small"
              type="primary"
              @click="submitForm('ruleForm')"
              >立即创建</el-button
            >
            <el-button size="small" @click="resetForm('ruleForm')"
              >重置</el-button
            >
          </div>
        </el-form>
      </div>
    </el-drawer>
  </div>
</template>

<script>
export default {
  data() {
    return {
      drawer: false,
      direction: "rtl",
      table: [],
      ruleForm: {
        name: "",
        describe: "",
        API: "",
      },
      options: [{ label: "111", value: "111" }],
      rules: {
        name: [
          { required: true, message: "请输入活动名称", trigger: "blur" },
          { min: 3, max: 5, message: "长度在 3 到 5 个字符", trigger: "blur" },
        ],
        describe: [
          { required: true, message: "请输入活动名称", trigger: "blur" },
          { min: 3, max: 5, message: "长度在 3 到 5 个字符", trigger: "blur" },
        ],
        API: [
          { required: true, message: "请输入活动名称", trigger: "blur" },
          { min: 3, max: 5, message: "长度在 3 到 5 个字符", trigger: "blur" },
        ],
      },
    };
  },
  methods: {
    handleClose(done) {
      done();
    },
    gonewEdition() {
      this.drawer = true;
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          alert("submit!");
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
  },
};
</script>

<style scoped lang='scss'>
.mode-margin {
  margin-top: 24px;
}
.serve-table {
  display: flex;
  justify-content: space-between;
  .table-tilelong {
    line-height: 30px;
  }
}
.drawer {
  width: 80% !important;
}
</style>