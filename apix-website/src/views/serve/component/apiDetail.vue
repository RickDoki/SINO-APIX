<template>
  <div>
    <el-row>
      <el-col :span="4">
        <div class="apiList">
          <el-select v-model="apiValue" size="mini" placeholder="请选择">
            <el-option
              v-for="item in apiOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </div>
      </el-col>
      <el-col :span="20">
        <div class="apiMessage">
          <div class="content">
            <p>测试Api</p>
            <p>测试api描述</p>
            <div class="flex font12">
              <div class="left leftFontColor">调用路径:</div>
              <div class="right">https://www.baidu.com</div>
            </div>
            <div class="flex font12">
              <div class="left leftFontColor agrement">协议:</div>
              <div class="right">https</div>
            </div>
            <div class="flex font12">
              <div class="left leftFontColor">请求方式:</div>
              <div class="right">get</div>
            </div>
            <div class="flex font12">
              <div class="left leftFontColor">返回类型:</div>
              <div class="right">JSON</div>
            </div>
            <div class="request">
              <div class="leftFontColor font12">请求参数:</div>
              <div class="table_box">
                <el-table
                  :data="table"
                  empty-text="暂无数据"
                  stripe
                  style="width: 100%"
                >
                  <el-table-column prop="appName" label="应用名称" />
                  <el-table-column prop="appCode" label="APPCode" />
                  <el-table-column prop="appCode" label="启用状态" />
                  <el-table-column prop="appCode" label="描述" />
                  <el-table-column label="操作" width="180px">
                    <template slot-scope="scope">
                      <div class="handle">
                        <span @click="getMessage(scope.row)" class="linkcolor"
                          >查看</span
                        >
                        <span class="handle_middle">|</span>
                        <span class="linkcolor">退订</span>
                      </div>
                    </template>
                  </el-table-column>
                </el-table>
              </div>
            </div>
            <div class="statusCode">
              <div class="leftFontColor font12">状态码:</div>
              <div class="table_box">
                <el-table
                  :data="table"
                  empty-text="暂无数据"
                  stripe
                  style="width: 100%"
                >
                  <el-table-column prop="appName" label="应用名称" />
                  <el-table-column prop="appCode" label="APPCode" />
                  <el-table-column prop="appCode" label="启用状态" />
                  <el-table-column prop="appCode" label="描述" />
                  <el-table-column label="操作" width="180px">
                    <template slot-scope="scope">
                      <div class="handle">
                        <span @click="getMessage(scope.row)" class="linkcolor"
                          >查看</span
                        >
                        <span class="handle_middle">|</span>
                        <span class="linkcolor">退订</span>
                      </div>
                    </template>
                  </el-table-column>
                </el-table>
              </div>
            </div>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  data() {
    return {
      table: [],
      apiOptions: [],
      apiValue: ''
    };
  },
};
</script>

<style scoped lang='scss'>
p {
  margin: 0px 0px 10px 0px;
}
.apiList {
  padding: 20px 10px;
}
.apiMessage {
  height: 100%;
  border-left: 1px solid #ccc;
  padding-top: 20px;
  .content {
    margin-left: 20px;
    .flex {
      display: flex;
      margin-bottom: 10px;
    }
    .font12 {
      font-size: 12px;
    }
    .left {
      width: 52px;
    }
    .agrement {
      text-align: right;
    }
    .right {
      margin-left: 10px;
      color: #373753;
    }
    .table_box {
      padding: 30px;
      border: 1px solid #e1e6ee;
      margin-top: 10px;
      border-radius: 10px;
      ::v-deep {
        .el-table th.el-table__cell.is-leaf,
        .el-table td.el-table__cell {
          border: none;
        }
        .el-table::before {
          height: 0px;
        }
      }
    }
    .statusCode {
      margin: 10px 0px;
    }
  }
}
.leftFontColor {
  color: #494e6a;
}
.rightFontColor {
  color: #373753;
}
</style>