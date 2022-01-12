<template>
  <div class="main">
    <navbar></navbar>
    <div style="padding: 90px 30px 0 30px;position: relative;min-height:calc(100vh - 211px)">
      <div class="list_top">
        <div>
          <div class="list_top_title">{{ appName }}</div>
          <div class="introduction">{{ appDescription }}</div>
        </div>
        <div class="">
          <el-button type="primary" size="small" v-if="subscribed" :disabled="true" style="width: 100px"
                     @click="subscribe">已订阅
          </el-button>
          <el-button type="primary" size="small" v-else style="width: 100px" @click="subscribe">订阅
          </el-button>
          <el-button size="small" style="width: 100px" icon="el-icon-back" @click="$router.push({name:'openServe'})">返回
          </el-button>
        </div>
      </div>
      <div class="list_top2">
        <div style="display: flex">
          <div class="service_providers">服务商：{{ appProvider }}</div>
          <!--          <div class="service_providers">发布时间：2021-10-05 08:05:00</div>-->
          <div class="service_providers" style="display: flex">
            已添加的插件：
            <div class="plug-in" style="display: flex">
              <el-tooltip class="item" effect="light" content="Left Bottom 提示文字" placement="bottom-start">
                <div class="chajian_qian"></div>
              </el-tooltip>
              <div>jwt-auth</div>
            </div>
          </div>
        </div>
        <div class="release_time">发布时间： {{ appCreationDate }}</div>
      </div>
      <div
        style="margin-top: 20px;padding-left:30px;position: absolute;left: 0;right: 0;width: 100%;height: 100%;background: #FFFFFF">
        <api-detail :apiOptions="appVersion"></api-detail>
      </div>
    </div>
  </div>
</template>

<script>
// import apidetail from "./detail/detail.vue";
// import apiTest from "./detail/test.vue";
// import help from "./detail/help.vue";
import {appCodeDetail, subscribe} from "@/api/AboutApp";
import {getToken} from "@/utils/auth"; // get token from cookie
import apiDetail from './component/apiDetail'
import navbar from "@/views/openServe/component/Navbar";

export default {
  components: {
    navbar,
    apiDetail
    // apidetail,
    // apiTest,
    // help,
  },
  data() {
    return {
      appName: "",
      appDescription: "",
      appCreationDate: "",
      appVersion: [],
      appProvider: "",
      subscribed: true
    };
  },
  created() {
    this.query()
  },
  methods: {
    query() {
      appCodeDetail(this.$route.query.code).then(res => {
        if (res.code === 200) {
          this.appName = res.data.appName
          this.appDescription = res.data.appDescription
          this.appCreationDate = res.data.appCreationDate
          this.appProvider = res.data.appProvider
          this.appVersion = res.data.appVersion
          this.subscribed = res.data.subscribed
        }
      })
    },
    subscribe() {
      if (getToken('token')) {
        this.$confirm('确认订阅：' + this.appName + '吗, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          subscribe(this.$route.query.code).then(res => {
            if (res.code === 200) {
              this.$message.success('订阅成功')
              this.query()
            }
          })
        })
      } else {
        this.$router.push({
          path: '/login',
          query: {
            path: this.$route.path,
            code: this.$route.query.code
          }
        })
      }
    }
  }
};
</script>

<style lang='scss' scoped>
.main {
  margin: 0px;
}

.list_top {
  .list_top_title {
    height: 26px;
    font-size: 20px;
    font-family: Microsoft YaHei UI-Bold, Microsoft YaHei UI;
    font-weight: bold;
    color: #1D1C35;
    line-height: 26px;
  }

  .introduction {
    margin-top: 5px;
    height: 20px;
    font-size: 14px;
    font-family: Microsoft YaHei UI-Regular, Microsoft YaHei UI;
    font-weight: 400;
    color: #727491;
    line-height: 20px;
  }
}

.list_top2 {
  margin-top: 30px;
  display: flex;
  justify-content: space-between;

  .service_providers {
    height: 20px;
    font-size: 14px;
    font-family: Microsoft YaHei UI-Regular, Microsoft YaHei UI;
    font-weight: 400;
    color: #1D1C35;
    line-height: 20px;
    margin-right: 40px;

    .plug-in {
      margin-right: 10px;

      .chajian_qian {
        margin-right: 10px;
        width: 20px;
        height: 20px;
        background: #F1F1F1;
        border-radius: 0px 0px 0px 0px;
        opacity: 1;
      }
    }
  }

  .release_time {
    height: 20px;
    font-size: 12px;
    font-family: Microsoft YaHei UI-Regular, Microsoft YaHei UI;
    font-weight: 400;
    color: #727491;
    line-height: 20px;
  }
}
</style>
