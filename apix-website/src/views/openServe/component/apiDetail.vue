<template>
  <div class="he_main">
    <el-row style="height:100%">
      <el-col style="height:100%" :span="4">
        <div class="apiList">
          <el-select v-model="apiValue" size="mini" placeholder="请选择" @change="apiValueChange">
            <el-option
              v-for="item in apiOptions"
              :key="item.id"
              :label="item.version"
              :value="item.id"
            >
            </el-option>
          </el-select>
          <p
            :class="classList[index]"
            v-for="(item, index) in defaultApiList"
            :key="index"
            style="cursor: pointer"
            @click="choseApi(item, index)"
          >
            {{ item.apiName }}
          </p>
        </div>
      </el-col>
      <el-col style="height:100%" :span="20">
        <div class="apiMessage">
          <div class="api-info">
            <div class="title">{{ apiMessageAll.apiName }}</div>
            <div class="secondTitle">{{ apiMessageAll.description }}</div>
          </div>
          <div class="api-info">
            <span class="label-color">调用路径 : </span>
            <span class="conten-color">{{ apiMessageAll.domain }}</span>
          </div>
          <div class="api-info">
            <span class="label-color agrement">协议类型 : </span>
            <span class="conten-color">{{ apiMessageAll.protocol }}</span>
          </div>
          <div class="api-info">
            <span class="label-color">请求方式 : </span>
            <span class="conten-color">{{ apiMessageAll.requestMethod }}</span>
          </div>
          <div class="api-info">
            <span class="label-color">返回类型 : </span>
            <span class="conten-color">JSON</span>
          </div>
          <div class="api-info">
            <span class="label-color">请求参数 : </span>
            <div class="table_box table_top">
              <el-table
                :data="table"
                empty-text="暂无数据"
                :row-style="{ height: '50px' }"
                highlight-current-row
                :header-cell-style="{ 'font-weight': 400, color: '#494E6A' }"
              >
                <el-table-column prop="parame" label="名称"/>
                <el-table-column prop="type" label="类型"/>
                <el-table-column prop="isHaveto" label="是否必选"/>
                <el-table-column prop="describe" label="描述"/>
                <el-table-column prop="default" label="默认值"/>
              </el-table>
            </div>
          </div>
          <div class="api-info">
            <span class="label-color">状态码 : </span>
            <div class="table_box table_top">
              <el-table
                :data="statusTable"
                empty-text="暂无数据"
                :row-style="{ height: '50px' }"
                highlight-current-row
                :header-cell-style="{ 'font-weight': 400, color: '#494E6A' }"
              >
                <el-table-column prop="statusCode" label="状态码" width="200"/>
                <el-table-column prop="describe" label="描述"/>
              </el-table>
            </div>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import {apiMessage, queryApiList} from "@/api/AboutServe.js";

export default {
  props: ['apiOptions'],
  created() {
  },
  methods: {
    apiValueChange() {
      const query = {
        appCode: this.$route.query.code,
        appVersionId: this.apiValue,
      };
      queryApiList(query).then((res) => {
        if (res.code === 200) {
          this.defaultApiList = res.data.apiList
          this.classList = []
        }
      });
    },
    getapiMessage(e) {
      apiMessage(e).then((res) => {
        if (res.code === 200) {
          this.apiMessageAll = res.data
          this.table = JSON.parse(res.data.requestParams);
        }
      });
    },
    choseApi(e, i) {
      this.classList = [];
      for (let index = 0; index < this.defaultApiList.length; index++) {
        if (index === i) {
          this.classList.push("hitClass");
        } else {
          this.classList.push("nohit");
        }
      }
      // 查询选中api详情
      const query = e.apiId;
      this.getapiMessage(query)
    },
  },
  data() {
    return {
      table: [],
      classList: [],
      apiValue: '',
      defaultApiList: [],
      apiMessageAll: {},
      statusTable: [
        {
          statusCode: 200,
          describe: "操作成功",
        },
        {
          statusCode: 400,
          describe: "操作失败",
        },
      ],
    };
  },
  watch: {
    apiOptions() {
      this.apiValue = this.apiOptions[0].id;
      this.apiValueChange()
    },
    defaultApiList() {
      this.classList = [];
      for (let index = 0; index < this.defaultApiList.length; index++) {
        // this.classList.push('hitClass')
        if (index === 0) {
          this.classList.push("hitClass");
        } else {
          this.classList.push("nohit");
        }
      }
      const query = this.defaultApiList[0].apiId;
      this.getapiMessage(query);
    },
  },
};
</script>

<style scoped lang='scss'>
.he_main {
  // min-height: inherit;
  height: 100%;
  overflow: hidden;
}

.apiList {
  padding: 20px 0px;
}

.hitClass {
  cursor: pointer;
  color: #2650ff;
}

.nohit {
  cursor: pointer;
}

.apiMessage {
  overflow: auto;
  border-left: 1px solid #e1e6ee;
  padding: 24px;
  margin-left: 16px;
  height: 100%;
  padding-bottom: 150px;
  // margin-bottom: 20px;
  .api-info {
    margin-bottom: 24px;

    .table_box {
      padding: 15px 30px 30px 30px;
      border: 1px solid #e1e6ee;
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

    .title {
      line-height: 22px;
      font-size: 16px;
      font-weight: bold;
      color: #1d1c35;
    }

    .secondTitle {
      margin-top: 6px;
      color: #727491;
    }
  }
}

.apiMessage::-webkit-scrollbar {
  display: none;
}
</style>
