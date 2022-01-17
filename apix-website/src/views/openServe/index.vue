<template>
  <div class="main_open">
    <navbar></navbar>
    <div style="min-height: calc(100vh - 238px - 60px)">
      <div class="apiMain_content">
        <div class="welcome">{{ pageInfo.title || '欢迎访问我们的开放服务平台' }}</div>
        <div class="all_services">{{ pageInfo.description || '您可以在我们所有的服务中找到需要的那一个' }}</div>
        <div class="input-with-select">
          <el-input placeholder="请输入服务名称" v-model="searchKey"></el-input>
          <el-button type="primary" slot="append" @click="search">搜一下</el-button>
        </div>
      </div>
      <div class="open_service">
        <div style="display: flex;justify-content: space-between">
          <div class="open_service_title">开放服务</div>
          <div>
            <img src="../../../src/assets/img/icon_list.png"
                 style="width: 20px;height: 20px;margin-right: 10px;cursor: pointer" @click="isshow=1">
            <img src="../../../src/assets/img/icon_cards.png" style="width: 20px;height: 20px;cursor: pointer"
                 @click="isshow=2">
          </div>
        </div>
        <transition name="el-fade-in-linear">
          <div class="open_service_list" v-show="isshow===1">
            <div v-for="(item,index) in serviceList" :key='index' class="service_list_item"
                 @click="goDetail(item)">
              <div class="list_item_title">{{ item.appName }}</div>
              <div class="list_item_content">{{ item.description }}</div>
              <div style="width: 140px">
                <img v-for="(items,indexs) in item.plugins" v-show="indexs<5" :key="indexs" :src="items.icon"
                     width="20px" height="20px" style="margin-right: 5px">
                <span v-if="item.plugins.length>5">...</span>
              </div>
              <div style="width: 100px;text-align: center">
                <div class="list_item_v" v-if="item.appVersions[0]">{{ item.appVersions[0] }}</div>
              </div>
              <div class="list_item_button" v-if="!item.subscribed" @click.stop="subscribe(item)">订阅</div>
              <div class="list_item_button_dis" v-else>已订阅</div>
            </div>
          </div>
        </transition>
        <transition name="el-fade-in-linear">
          <div class="open_service_cards" v-show="isshow===2">
            <div class="service_cards_item"
                 @click="goDetail(item)" v-for="(item,index) in serviceList" :key='index'>
              <div class="cards_item_button" v-if="!item.subscribed" @click.stop="subscribe(item)">订阅</div>
              <div class="cards_item_button_dis" v-else>已订阅</div>
              <div class="cards_item_title">{{ item.appName }}</div>
              <div class="cards_item_content">{{ item.description }}</div>
              <div style="display: flex;width: 100%">
                <img v-for="(items,indexs) in item.plugins" v-show="indexs<5" :key="indexs" :src="items.icon"
                     width="20px" height="20px" style="margin-right: 5px">
                <span v-if="item.plugins.length>5">...</span>
              </div>
              <div>
                <div class="cards_item_v" v-if="item.appVersions[0]">{{ item.appVersions[0] }}</div>
                <div v-else style="width: 20px;height: 20px"></div>
              </div>
            </div>
            <div v-if="serviceList.length%4===3 || serviceList.length%4===2" style="width: 270px;height: 300px">
            </div>
            <div v-if="serviceList.length%4===2" style="width: 270px;height: 300px">
            </div>
          </div>
        </transition>
        <transition name="el-fade-in-linear" v-if="serviceList.length===0">
          <el-empty description="暂无开放服务"></el-empty>
        </transition>
      </div>
    </div>
    <div class="service_footer">
      <div>
        <img src="../../../src/assets/img/img_sinosdx_logo.png" style="width: 119px;height: 43px;opacity: 1;">
      </div>
      <div class="footer_text1">
        <div style="margin-right: 50px">上海博冀信息科技有限公司</div>
        <div>联系电话 000-0000-8888</div>
      </div>
      <div class="footer_text2">Copyright © 2021 上海博冀信息科技有限公司</div>
    </div>
  </div>
</template>

<script>
import {getDoorConfig, updateDoorConfig} from "@/api/user"
import {openList, subscribe} from "@/api/AboutApp";
import navbar from "@/views/openServe/component/Navbar";
import {getToken} from "@/utils/auth";
import plugin from "@/views/serve/plugin";

export default {
  components: {navbar},
  data() {
    return {
      searchKey: "",
      items: [],
      isshow: 1,
      serviceList: [],
      pageInfo: {},
      plugin: plugin
    };
  },
  created() {
    this.search()
    this.getPageInfo()
  },
  methods: {
    getPageInfo() {
      getDoorConfig().then((res) => {
        this.pageInfo = res.data
      });
    },
    plugName: function (value) {
      const nameFiter = {
        jwt: "Jwt-auth",
        base_auth: "basic_auth",
        oauth2: "OAuth2.0",
        black_list_ip: "IP 黑名单控制",
        white_list_ip: "IP 白名单控制",
        cors: "cors跨域",
        sign: "防篡改签名",
        replay_attacks: "防网络重放攻击",
        error_log: "error log",
        http_log: "http log",
        sentinel: "sentinel",
        gzip: "gzip",
        proxy_cache: "proxy-cache",
        real_ip: "real_ip",
        response_rewrite: "response-rewrite",
      };
      if (!value) return "";
      return nameFiter[value];
    },
    search() {
      const query = "?market=true&appName=" + this.searchKey;
      openList(query).then((res) => {
        this.serviceList = res.data.appList
        let arr = []
        this.plugin.forEach(item => {
          arr.push(...item.content)
        })
        this.serviceList.map((item, index) => {
          item.plugins.map((items, indexs) => {
            items.pluginType = this.plugName(items.pluginType)
            arr.forEach((itemss, indexss) => {
              if (items.pluginType === itemss.name) {
                items.icon = itemss.icon
              }
            })
          })
        })
      });
    },
    goDetail(item) {
      this.$router.push({
        name: 'openServeDetail',
        query: {
          code: item.appCode
        }
      })
    },
    subscribe(item) {
      if (getToken('token')) {
        this.$confirm('确认订阅：' + item.appName + '吗, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          subscribe(item.appCode).then(res => {
            if (res.code === 200) {
              this.$message.success('订阅成功')
              this.search()
            }
          })
        })
      } else {
        this.$router.push({
          path: '/login',
          query: {
            path: this.$route.path
          }
        })
      }
    }
  },
};
</script>

<style lang='scss' scoped>
.main_open {
  background: #ffffff;
  margin: 0px;
  min-height: calc(100vh - 60px);

  .apiMain_content {
    margin-top: 60px;
    background-image: url('../../../src/assets/img/img_bg.png');
    background-size: 100% 100%;
    background-repeat: no-repeat;
    height: 50vh;
    width: 100%;
    background-color: #fff;
    padding-top: 7vh;

    ::v-deep {
      .el-input--medium .el-input__inner {
        height: 42px;
        line-height: 42px;
        background: #f1f4fe;
        border-color: #f1f4fe;
        box-shadow: -3px 10px 10px #e2e7fe;
      }

      input::-webkit-input-placeholder {
        font-weight: 400;
        color: #494e6a;
      }

      input::-moz-input-placeholder {
        font-weight: 400;
        color: #494e6a;
      }

      input::-ms-input-placeholder {
        font-weight: 400;
        color: #494e6a;
      }

      .el-button--primary:hover,
      .el-button--primary:focus {
        height: 42px;
        color: #ffffff;
        background: #5173ff;
        border-color: #5173ff;
      }

      .el-button--primary {
        height: 42px;
        background: #2650ff;
        border-color: #2650ff;
        color: #ffffff;
        box-shadow: -3px 10px 10px #e2e7fe;
      }
    }

    .welcome {
      text-align: center;
      width: 100%;
      height: 28px;
      font-size: 24px;
      font-family: Roboto-Regular, Roboto;
      font-weight: 400;
      color: #000000;
      line-height: 28px;
    }

    .all_services {
      margin-top: 5vh;
      text-align: center;
      width: 100%;
      height: 41px;
      font-size: 32px;
      font-family: Microsoft YaHei UI-Bold, Microsoft YaHei UI;
      font-weight: bold;
      color: #000000;
      line-height: 38px;
    }

    .input-with-select {
      display: flex;
      width: 500px;
      text-align: center;
      margin: 5vh auto;
    }
  }

  .open_service {
    width: 100%;
    max-width: 1200px;
    padding: 2rem 3rem var(--bottom-padding);
    margin: 35px auto;

    .open_service_title {
      height: 25px;
      font-size: 20px;
      font-family: Microsoft YaHei UI-Bold, Microsoft YaHei UI;
      font-weight: bold;
      color: #000000;
      line-height: 23px;
    }

    .open_service_list {
      margin-top: 20px;

      .service_list_item {
        cursor: pointer;
        width: 100%;
        height: 84px;
        background: #ffffff;
        border: 1px solid #ffffff;
        box-shadow: 0px 0px 8px 1px rgba(29, 28, 53, 0.2);
        border-radius: 8px 8px 8px 8px;
        opacity: 1;
        display: flex;
        justify-content: space-around;
        align-items: center;
        margin-bottom: 20px;

        .list_item_title {
          margin-left: 24px;
          width: 100px;
          height: 20px;
          font-size: 14px;
          font-family: Microsoft YaHei UI-Bold, Microsoft YaHei UI;
          font-weight: bold;
          color: #000000;
          line-height: 20px;
        }

        .list_item_content {
          overflow: hidden;
          width: 50%;
          padding-right: 24px;
          white-space: nowrap; /*不换行*/
          text-overflow: ellipsis;
          height: 20px;
          font-size: 14px;
          font-family: Microsoft YaHei UI-Regular, Microsoft YaHei UI;
          font-weight: 400;
          color: #494e6a;
          line-height: 20px;
        }

        .list_item_v {
          display: inline-block;
          padding: 5px;
          //display: flex;
          //justify-content: center;
          //align-items: center;
          font-size: 14px;
          font-family: Microsoft YaHei UI-Regular, Microsoft YaHei UI;
          font-weight: 400;
          color: #2650ff;
          height: 20px;
          line-height: 10px;
          background: #e0e6ff;
          border-radius: 4px 4px 4px 4px;
        }

        .list_item_button {
          margin-right: 24px;
          margin-left: 100px;
          display: flex;
          justify-content: center;
          align-items: center;
          font-size: 14px;
          font-family: Microsoft YaHei UI-Regular, Microsoft YaHei UI;
          font-weight: 400;
          color: #2650ff;
          line-height: 20px;
          width: 74px;
          height: 36px;
          border-radius: 4px 4px 4px 4px;
          opacity: 1;
          border: 1px solid #2650ff;
        }

        .list_item_button:hover {
          cursor: pointer;
          display: flex;
          justify-content: center;
          align-items: center;
          font-size: 14px;
          background: #2650ff;
          font-family: Microsoft YaHei UI-Regular, Microsoft YaHei UI;
          font-weight: 400;
          color: #ffffff;
          line-height: 20px;
          width: 74px;
          height: 36px;
          border-radius: 4px 4px 4px 4px;
          opacity: 1;
        }

        .list_item_button_dis {
          margin-right: 24px;
          margin-left: 100px;
          cursor: default;
          display: flex;
          justify-content: center;
          align-items: center;
          font-size: 14px;
          background: #2650ff;
          font-family: Microsoft YaHei UI-Regular, Microsoft YaHei UI;
          font-weight: 400;
          color: #ffffff;
          line-height: 20px;
          width: 74px;
          height: 36px;
          border-radius: 4px 4px 4px 4px;
          opacity: 1;
        }
      }

      .service_list_item:hover {
        width: 100%;
        height: 84px;
        background: #ffffff;
        box-shadow: 0px 0px 8px 1px rgba(38, 80, 255, 0.3);
        border: 1px solid #2650ff;
        border-radius: 8px 8px 8px 8px;
        opacity: 1;
        display: flex;
        justify-content: space-around;
        align-items: center;

        .list_item_title {
          margin-left: 24px;
          height: 20px;
          font-size: 14px;
          font-family: Microsoft YaHei UI-Bold, Microsoft YaHei UI;
          font-weight: bold;
          color: #000000;
          line-height: 20px;
        }

        .list_item_content {
          overflow: hidden;
          white-space: nowrap; /*不换行*/
          width: 50%;
          padding-right: 24px;
          text-overflow: ellipsis;
          height: 20px;
          font-size: 14px;
          font-family: Microsoft YaHei UI-Regular, Microsoft YaHei UI;
          font-weight: 400;
          color: #494e6a;
          line-height: 20px;
        }

        .list_item_v {
          display: inline-block;
          padding: 5px;
          //display: flex;
          //justify-content: center;
          //align-items: center;
          font-size: 14px;
          font-family: Microsoft YaHei UI-Regular, Microsoft YaHei UI;
          font-weight: 400;
          color: #2650ff;
          height: 20px;
          background: #e0e6ff;
          border-radius: 4px 4px 4px 4px;
        }

        .list_item_button {
          margin-right: 24px;
          margin-left: 100px;
          display: flex;
          justify-content: center;
          align-items: center;
          font-size: 14px;
          font-family: Microsoft YaHei UI-Regular, Microsoft YaHei UI;
          font-weight: 400;
          color: #2650ff;
          line-height: 20px;
          width: 74px;
          height: 36px;
          border-radius: 4px 4px 4px 4px;
          opacity: 1;
          border: 1px solid #2650ff;
        }

        .list_item_button:hover {
          cursor: pointer;
          display: flex;
          justify-content: center;
          align-items: center;
          font-size: 14px;
          background: #2650ff;
          font-family: Microsoft YaHei UI-Regular, Microsoft YaHei UI;
          font-weight: 400;
          color: #ffffff;
          line-height: 20px;
          width: 74px;
          height: 36px;
          border-radius: 4px 4px 4px 4px;
          opacity: 1;
        }
      }
    }

    .open_service_cards {
      margin-top: 20px;
      display: flex;
      justify-content: space-between;
      flex-wrap: wrap;

      .service_cards_item {
        width: 270px;
        cursor: pointer;
        box-sizing: border-box;
        margin-bottom: 30px;
        display: flex;
        flex-direction: column;
        justify-content: space-around;
        padding: 20px;
        //width: 250px;
        height: 300px;
        background: #ffffff;
        box-shadow: 0px 0px 8px 1px rgba(29, 28, 53, 0.2);
        border-radius: 8px 8px 8px 8px;
        opacity: 1;
        border: 1px solid #ffffff;

        .cards_item_button {
          margin-top: 10px;
          display: flex;
          justify-content: center;
          align-items: center;
          font-size: 14px;
          font-family: Microsoft YaHei UI-Regular, Microsoft YaHei UI;
          font-weight: 400;
          color: #2650ff;
          line-height: 20px;
          width: 74px;
          height: 36px;
          border-radius: 4px 4px 4px 4px;
          opacity: 1;
          border: 1px solid #2650ff;
        }

        .cards_item_button:hover {
          margin-top: 10px;
          cursor: pointer;
          display: flex;
          justify-content: center;
          align-items: center;
          font-size: 14px;
          background: #2650ff;
          font-family: Microsoft YaHei UI-Regular, Microsoft YaHei UI;
          font-weight: 400;
          color: #ffffff;
          line-height: 20px;
          width: 74px;
          height: 36px;
          border-radius: 4px 4px 4px 4px;
          opacity: 1;
        }

        .cards_item_button_dis {
          margin-top: 10px;
          cursor: default;
          display: flex;
          justify-content: center;
          align-items: center;
          font-size: 14px;
          background: #2650ff;
          font-family: Microsoft YaHei UI-Regular, Microsoft YaHei UI;
          font-weight: 400;
          color: #ffffff;
          line-height: 20px;
          width: 74px;
          height: 36px;
          border-radius: 4px 4px 4px 4px;
          opacity: 1;
        }

        .cards_item_title {
          margin-top: 10px;
          height: 20px;
          font-size: 14px;
          font-family: Microsoft YaHei UI-Bold, Microsoft YaHei UI;
          font-weight: bold;
          color: #000000;
          line-height: 20px;
        }

        .cards_item_content {
          margin-top: 10px;
          width: 100%;
          height: 15vh;
          overflow: hidden;
          text-overflow: ellipsis;
          font-size: 14px;
          font-family: Microsoft YaHei UI-Regular, Microsoft YaHei UI;
          font-weight: 400;
          color: #494e6a;
          line-height: 20px;
        }

        .cards_item_v {
          padding: 5px;
          display: inline-block;
          margin-top: 5px;
          font-size: 14px;
          font-family: Microsoft YaHei UI-Regular, Microsoft YaHei UI;
          font-weight: 400;
          color: #2650ff;
          height: 20px;
          line-height: 10px;
          background: #e0e6ff;
          border-radius: 4px 4px 4px 4px;
        }
      }

      .service_cards_item:hover {
        //width: 250px;
        height: 300px;
        background: #ffffff;
        box-shadow: 0px 0px 8px 1px rgba(38, 80, 255, 0.3);
        border-radius: 8px 8px 8px 8px;
        opacity: 1;
        border: 1px solid #2650ff;
      }
    }
  }

  .service_footer {
    padding: 20px 0;
    width: 100%;
    min-height: 238px;
    background: #1d1c35;
    opacity: 1;
    display: flex;
    flex-direction: column;
    justify-content: space-around;
    align-items: center;

    .footer_text1 {
      height: 20px;
      font-size: 14px;
      font-family: Microsoft YaHei UI-Regular, Microsoft YaHei UI;
      font-weight: 400;
      color: #ffffff;
      line-height: 20px;
      display: flex;
    }

    .footer_text2 {
      height: 15px;
      font-size: 12px;
      font-family: Microsoft YaHei UI-Regular, Microsoft YaHei UI;
      font-weight: 400;
      color: #ffffff;
      line-height: 14px;
    }
  }
}
</style>
