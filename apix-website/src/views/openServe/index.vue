<template>
  <div class="main">
    <navbar></navbar>
    <div class="apiMain_content">
      <div class="welcome">欢迎访问我们的开放服务平台</div>
      <div class="all_services">您可以在我们所有的服务中找到需要的那一个</div>
      <div style="width: 100%;text-align: center;margin-top: 5vh">
        <el-input placeholder="请输入服务名称" v-model="searchKey" class="input-with-select">
          <el-button type="primary" slot="append">搜一下</el-button>
        </el-input>
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
          <div v-for="(item,index) in serviceList" :key='index' class="service_list_item" @click="goDetail">
            <div class="list_item_title">{{ item.title }}</div>
            <div class="list_item_content">{{ item.content }}</div>
            <div>
              <img src="../../../src/assets/img/guanjun.png" style="width: 20px;height: 20px;margin-right: 10px">
              <img src="../../../src/assets/img/xunzhang.png" style="width: 20px;height: 20px">
            </div>
            <div class="list_item_v">{{ item.verion }}</div>
            <div class="list_item_button">订阅</div>
          </div>
        </div>
      </transition>
      <transition name="el-fade-in-linear">
        <div class="open_service_cards" v-show="isshow===2">
          <div v-for="(item,index) in serviceList" :key='index' class="service_cards_item">
            <div class="cards_item_button">订阅</div>
            <div class="cards_item_title">{{ item.title }}</div>
            <div class="cards_item_content">{{ item.content }}</div>
            <div>
              <img src="../../../src/assets/img/guanjun.png" style="width: 20px;height: 20px;margin-right: 10px">
              <img src="../../../src/assets/img/xunzhang.png" style="width: 20px;height: 20px">
            </div>
            <div class="cards_item_v">{{ item.verion }}</div>
          </div>
        </div>
      </transition>
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
import {list} from "@/api/AboutApp";
import navbar from "@/views/openServe/component/Navbar";

export default {
  components: {navbar},
  data() {
    return {
      searchKey: "",
      items: [],
      isshow: 1,
      serviceList: [
        {
          title: "测试服务",
          content: "这是一段简洁的开放服务的 简介，希望你可以好好看看呀这是一段简洁的开放服务的这是一段简洁的开放服务的 简介，希望你可以好好看看呀这是一段简洁的开放服务的",
          verion: "v1.0"
        },
        {
          title: "测试服务",
          content: "这是一段简洁的开放服务的 简介，希望你可以好好看看呀这是一段简洁的开放服务的...",
          verion: "v1.0"
        },
        {
          title: "测试服务",
          content: "这是一段简洁的开放服务的 简介，希望你可以好好看看呀这是一段简洁的开放服务的...",
          verion: "v1.0"
        },
        {
          title: "测试服务",
          content: "这是一段简洁的开放服务的 简介，希望你可以好好看看呀这是一段简洁的开放服务的...",
          verion: "v1.0"
        },
        {
          title: "测试服务",
          content: "这是一段简洁的开放服务的 简介，希望你可以好好看看呀这是一段简洁的开放服务的...",
          verion: "v1.0"
        },
        {
          title: "测试服务",
          content: "这是一段简洁的开放服务的 简介，希望你可以好好看看呀这是一段简洁的开放服务的...",
          verion: "v1.0"
        },
        {
          title: "测试服务",
          content: "这是一段简洁的开放服务的 简介，希望你可以好好看看呀这是一段简洁的开放服务的...",
          verion: "v1.0"
        },
        {
          title: "测试服务",
          content: "这是一段简洁的开放服务的 简介，希望你可以好好看看呀这是一段简洁的开放服务的...",
          verion: "v1.0"
        },
        {
          title: "测试服务",
          content: "这是一段简洁的开放服务的 简介，希望你可以好好看看呀这是一段简洁的开放服务的...",
          verion: "v1.0"
        },
        {
          title: "测试服务",
          content: "这是一段简洁的开放服务的 简介，希望你可以好好看看呀这是一段简洁的开放服务的...",
          verion: "v1.0"
        },
        {
          title: "测试服务",
          content: "这是一段简洁的开放服务的 简介，希望你可以好好看看呀这是一段简洁的开放服务的...",
          verion: "v1.0"
        },
        {
          title: "测试服务",
          content: "这是一段简洁的开放服务的 简介，希望你可以好好看看呀这是一段简洁的开放服务的...",
          verion: "v1.0"
        },
        {
          title: "测试服务",
          content: "这是一段简洁的开放服务的 简介，希望你可以好好看看呀这是一段简洁的开放服务的...",
          verion: "v1.0"
        },
        {
          title: "测试服务",
          content: "这是一段简洁的开放服务的 简介，希望你可以好好看看呀这是一段简洁的开放服务的...",
          verion: "v1.0"
        },
        {
          title: "测试服务",
          content: "这是一段简洁的开放服务的 简介，希望你可以好好看看呀这是一段简洁的开放服务的...",
          verion: "v1.0"
        },
        {
          title: "测试服务",
          content: "这是一段简洁的开放服务的 简介，希望你可以好好看看呀这是一段简洁的开放服务的...",
          verion: "v1.0"
        },
        {
          title: "测试服务",
          content: "这是一段简洁的开放服务的 简介，希望你可以好好看看呀这是一段简洁的开放服务的...",
          verion: "v1.0"
        }
      ]
    };
  },
  created() {
    // const query = "?market=true";
    // list(query).then((res) => {
    //   console.log(res);
    //   this.items = res.data.appList;
    //   if (res.data.appList.length === 0) {
    //     this.isshow = false;
    //   } else {
    //     this.isshow = true;
    //   }
    // });
  },
  methods: {
    itemsClick(i) {
      console.log(i);
      this.$router.push({
        path: "/apiDoor/detail?message=" + JSON.stringify(i),
      });
    },
    search() {
      // console.log('搜索')
      const query = "?market=true&appName=" + this.searchKey;
      list(query).then((res) => {
        this.items = res.data.appList;
        // if(res.data.appList)
        // console.log(res.data.appList.length)
        if (res.data.appList.length === 0) {
          this.isshow = false;
        } else {
          this.isshow = true;
        }
      });
    },
    goDetail() {
      this.$router.push({
        name: 'openServeDetail'
      })
    }
  },
};
</script>

<style lang='scss' scoped>
.main {
  margin: 0px;

  .apiMain_content {
    margin-top: 60px;
    background-image: url("../../../src/assets/img/img_bg.png");
    background-size: 100% 100%;
    background-repeat: no-repeat;
    height: 50vh;
    width: 100%;
    background-color: #fff;
    padding-top: 7vh;

    ::v-deep {
      .el-input--medium .el-input__inner {
        height: 48px;
        line-height: 48px;
        background: #F1F4FE;
        opacity: 1;
        color: #494E6A;
      }

      .el-button--primary:hover, .el-button--primary:focus {
        height: 48px;
        color: #FFFFFF;
        background: #5173ff;
        border-color: #5173ff;
      }

      .el-button--primary {
        height: 48px;
        background: rgba(38, 80, 255, 0.85);
        border-color: #2650FF;
        color: #FFFFFF;
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
      width: 55vh;
      height: 48px;
    }
  }

  .open_service {
    margin: 0 auto;
    margin-top: 3vh;
    width: 90%;
    padding: 20px;

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
        background: #FFFFFF;
        box-shadow: 0px 0px 8px 1px rgba(29, 28, 53, 0.2);
        border-radius: 8px 8px 8px 8px;
        opacity: 1;
        display: flex;
        justify-content: space-around;
        align-items: center;
        margin-bottom: 20px;

        .list_item_title {
          height: 20px;
          font-size: 14px;
          font-family: Microsoft YaHei UI-Bold, Microsoft YaHei UI;
          font-weight: bold;
          color: #000000;
          line-height: 20px;
        }

        .list_item_content {
          overflow: hidden;
          width: 60%;
          white-space: nowrap; /*不换行*/
          text-overflow: ellipsis;
          height: 20px;
          font-size: 14px;
          font-family: Microsoft YaHei UI-Regular, Microsoft YaHei UI;
          font-weight: 400;
          color: #494E6A;
          line-height: 20px;
        }

        .list_item_v {
          display: flex;
          justify-content: center;
          align-items: center;
          width: 44px;
          font-size: 14px;
          font-family: Microsoft YaHei UI-Regular, Microsoft YaHei UI;
          font-weight: 400;
          color: #2650FF;
          height: 20px;
          background: #E0E6FF;
          border-radius: 4px 4px 4px 4px;
        }

        .list_item_button {
          display: flex;
          justify-content: center;
          align-items: center;
          font-size: 14px;
          font-family: Microsoft YaHei UI-Regular, Microsoft YaHei UI;
          font-weight: 400;
          color: #2650FF;
          line-height: 20px;
          width: 74px;
          height: 36px;
          border-radius: 4px 4px 4px 4px;
          opacity: 1;
          border: 1px solid #2650FF;
        }

        .list_item_button:hover {
          cursor: pointer;
          display: flex;
          justify-content: center;
          align-items: center;
          font-size: 14px;
          background: #2650FF;
          font-family: Microsoft YaHei UI-Regular, Microsoft YaHei UI;
          font-weight: 400;
          color: #FFFFFF;
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
        background: #FFFFFF;
        box-shadow: 0px 0px 8px 1px rgba(38, 80, 255, 0.3);
        border: 1px solid #2650FF;
        border-radius: 8px 8px 8px 8px;
        opacity: 1;
        display: flex;
        justify-content: space-around;
        align-items: center;

        .list_item_title {
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
          width: 60%;
          text-overflow: ellipsis;
          height: 20px;
          font-size: 14px;
          font-family: Microsoft YaHei UI-Regular, Microsoft YaHei UI;
          font-weight: 400;
          color: #494E6A;
          line-height: 20px;
        }

        .list_item_v {
          display: flex;
          justify-content: center;
          align-items: center;
          width: 44px;
          font-size: 14px;
          font-family: Microsoft YaHei UI-Regular, Microsoft YaHei UI;
          font-weight: 400;
          color: #2650FF;
          height: 20px;
          background: #E0E6FF;
          border-radius: 4px 4px 4px 4px;
        }

        .list_item_button {
          display: flex;
          justify-content: center;
          align-items: center;
          font-size: 14px;
          font-family: Microsoft YaHei UI-Regular, Microsoft YaHei UI;
          font-weight: 400;
          color: #2650FF;
          line-height: 20px;
          width: 74px;
          height: 36px;
          border-radius: 4px 4px 4px 4px;
          opacity: 1;
          border: 1px solid #2650FF;
        }

        .list_item_button:hover {
          cursor: pointer;
          display: flex;
          justify-content: center;
          align-items: center;
          font-size: 14px;
          background: #2650FF;
          font-family: Microsoft YaHei UI-Regular, Microsoft YaHei UI;
          font-weight: 400;
          color: #FFFFFF;
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
      flex-wrap: wrap;
      flex-direction: row;

      .service_cards_item {
        cursor: pointer;
        margin-right: 20px;
        box-sizing: border-box;
        margin-bottom: 20px;
        display: flex;
        flex-direction: column;
        justify-content: space-around;
        padding: 20px;
        width: 250px;
        height: 300px;
        background: #FFFFFF;
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
          color: #2650FF;
          line-height: 20px;
          width: 74px;
          height: 36px;
          border-radius: 4px 4px 4px 4px;
          opacity: 1;
          border: 1px solid #2650FF;
        }

        .cards_item_button:hover {
          cursor: pointer;
          display: flex;
          justify-content: center;
          align-items: center;
          font-size: 14px;
          background: #2650FF;
          font-family: Microsoft YaHei UI-Regular, Microsoft YaHei UI;
          font-weight: 400;
          color: #FFFFFF;
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
          color: #494E6A;
          line-height: 20px;
        }

        .cards_item_v {
          display: flex;
          justify-content: center;
          align-items: center;
          width: 44px;
          font-size: 14px;
          font-family: Microsoft YaHei UI-Regular, Microsoft YaHei UI;
          font-weight: 400;
          color: #2650FF;
          height: 20px;
          background: #E0E6FF;
          border-radius: 4px 4px 4px 4px;
        }
      }

      .service_cards_item:hover {
        width: 250px;
        height: 300px;
        background: #FFFFFF;
        box-shadow: 0px 0px 8px 1px rgba(38, 80, 255, 0.3);
        border-radius: 8px 8px 8px 8px;
        opacity: 1;
        border: 1px solid #2650FF;
      }
    }
  }

  .service_footer {
    width: 100%;
    min-height: 199px;
    background: #1D1C35;
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
      color: #FFFFFF;
      line-height: 20px;
      display: flex;
    }

    .footer_text2 {
      height: 15px;
      font-size: 12px;
      font-family: Microsoft YaHei UI-Regular, Microsoft YaHei UI;
      font-weight: 400;
      color: #FFFFFF;
      line-height: 14px;
    }
  }
}
</style>
