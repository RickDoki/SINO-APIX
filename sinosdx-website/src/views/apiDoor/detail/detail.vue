<template>
  <div class="hickyDetail">
    <div class="detail_mian">
      <div class="left">
        <el-menu
          default-active="0-0"
          class="el-menu-vertical-demo"
          @open="handleOpen"
          @close="handleClose"
          @select="handleSelect"
        >
          <el-submenu
            v-for="(item, index) in appMessage"
            :key="index"
            :index="index.toString()"
          >
            <template slot="title">
              <span>{{ item.version }}</span>
            </template>
            <el-menu-item-group
              v-for="(item1, index1) in item.apiVersionList"
              :key="index1"
            >
              <el-menu-item
                :index="index.toString() + '-' + index1.toString()"
                >{{ item1.apiName }}</el-menu-item
              >
            </el-menu-item-group>
          </el-submenu>
        </el-menu>
      </div>
      <div class="right">
        <p class="title">{{apiMessage.name}}</p>
        <p class="content">调用地址:{{ apiMessage.url }}</p>
        <p class="content">请求方式:{{ apiMessage.type}}</p>
        <p class="content">返回类型: JSON</p>
        <p class="content">描述: {{apiMessage.message}}</p>
        <el-collapse v-model="activeName" accordion>
          <el-collapse-item title="一致性 Consistency" name="1">
            <span class="collapse-title" slot="title">请求参数(Query)</span>
            <div class="table_box">
              <el-table
                :data="tableData"
                border
                style="width: 100%"
                :header-cell-style="{ background: '#F0F2F5' }"
              >
                <el-table-column prop="parame" label="名称">
                </el-table-column>
                <el-table-column prop="type" label="类型">
                </el-table-column>
                <el-table-column
                  prop="isHaveto"
                  label="是否必选"
                ></el-table-column>
                <el-table-column prop="describe" label="描述"></el-table-column>
                <el-table-column prop="default" label="默认值"></el-table-column>
              </el-table>
            </div>
          </el-collapse-item>
          <el-collapse-item title="反馈 Feedback" name="2">
            <span class="collapse-title" slot="title">HTTP状态码</span>

            <div class="table_box">
              <el-table
                :data="httpCodes"
                border
                style="width: 100%"
                :header-cell-style="{ background: '#F0F2F5' }"
              >
                <el-table-column prop="code" label="状态码" width="180">
                </el-table-column>
                <el-table-column prop="message" label="描述">
                </el-table-column>
              </el-table>
            </div>
          </el-collapse-item>
        </el-collapse>
      </div>
    </div>
    <!-- <el-row> -->
    <!-- <el-col :span="3"> -->
    <!-- <el-menu
          default-active="2"
          class="el-menu-vertical-demo"
          @open="handleOpen"
          @close="handleClose"
         
        >
          <el-submenu index="1">
            <template slot="title">
              <span>导航一</span>
            </template>
            <el-menu-item-group >
              <el-menu-item index="1-1">选项1</el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group>
              <el-menu-item index="1-3">选项3</el-menu-item>
            </el-menu-item-group>
          </el-submenu>
        </el-menu> -->
    <!-- <el-tabs tab-position="left">
          <el-tab-pane label="人脸检测">
            <api-detail></api-detail>
          </el-tab-pane>
          <el-tab-pane label="创建人脸库">创建人脸库</el-tab-pane>
          <el-tab-pane label="添加人脸">添加人脸</el-tab-pane>
          <el-tab-pane>
            <div class="tab-title" slot="label">搜索人脸</div>
            <div class="tab-title"></div>
          </el-tab-pane>
        </el-tabs> -->
    <!-- <div class="jiekou">
          <img src="./../../../assets/img/picture.png" alt="">
          <p>网站建设中</p>
        </div> -->
    <!-- </el-col>
    </el-row> -->
  </div>
</template>

<script>
import apiDetail from "./APIitems/items.vue";
export default {
  components: {
    apiDetail,
  },
  props: {
    appMessage: {
      type: Array,
      default: null,
    },
  },
  data() {
    return {
      activeName: "1",
      tableData: [
        {
          parame: "",
          isHaveto: "",
          type: "",
          describe: "",
          default:''
        },
      ],
      httpCodes:[{
        code:'200',
        message:"操作成功"
      }],
      apiMessage:{
        name:'',
        url:'',
        type:'',
        message:''
      }
    };
  },
  methods: {
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    },
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
      const index = keyPath[1].lastIndexOf("-");
      const obj = keyPath[1].substring(index + 1, keyPath[1].length);
      console.log(obj);
      console.log(this.appMessage[keyPath[0]].apiVersionList[obj]);
      const items = this.appMessage[keyPath[0]].apiVersionList[obj]
      this.apiMessage = {
        name:items.apiName,
        url:items.url,
        type:items.requestMethod,
        message:items.description
      }
      this.tableData = JSON.parse(items.requestParams)
    },
  },
  watch: {
    appMessage: {
      handler(newVal, oldVal) {
        console.log(newVal);
        this.apiMessage = {
        name:newVal[0].apiVersionList[0].apiName,
        url:newVal[0].apiVersionList[0].url,
        type:newVal[0].apiVersionList[0].requestMethod,
        message:newVal[0].apiVersionList[0].description
      }
      this.tableData = JSON.parse(newVal[0].apiVersionList[0].requestParams)
      // console.log(JSON.parse(newVal[0].apiVersionList[0].requestParams))
      },
      deep: true,
    },
  },
  // created() {
  //   // console.log(this.appMessage)
  //   setTimeout(()=>{
  //     console.log(this.appMessage)
  //   },100)
  // }
};
</script>

<style lang='scss' scoped>
.hickyDetail {
  .jiekou {
    width: 100%;
    height: 200px;
    text-align: center;
    p {
      text-align: center;
      color: #ccc;
    }
  }
  .detail_mian {
    display: flex;
    .left {
      width: 200px;
    }
    .right {
      width: calc(100% - 200px);
      // height: 200px;
      // background-color: #ccc;
      padding-left: 24px;
      p {
        margin: 10px 0px;
      }
      .title {
        color: #333333;
        margin-bottom: 10px;
      }
      .content {
        padding-left: 24px;
        font-size: 12px;
        color: #666666;
      }
      ::v-deep .el-collapse-item {
        margin-bottom: 20px;
        .el-collapse-item__header {
          background-color: #f0f2f5;
        }
      }

      .collapse-title {
        flex: 1 0 90%;
        order: 1;
      }

      .el-collapse-item__header {
        flex: 1 0 auto;
        order: -1;
      }
      .table_box {
        padding: 24px;
      }
    }
  }
}
</style>