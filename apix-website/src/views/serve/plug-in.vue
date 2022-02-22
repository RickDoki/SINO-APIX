<template>
  <div class="plug-main">
    <!-- 插件中心 -->
    <div class="plugDetail">
      <div class="plug-top">
        <div class="titleFont left">插件中心</div>
        <div>
          <el-input
            size="small"
            placeholder="搜索"
            suffix-icon="el-icon-search"
            v-model="name"
            class="list_searchInput"
            @input="nameChange"
          >
          </el-input>
        </div>
      </div>
      <div class="plugDetailLeft">
        <div
          :class="defaultCss[index]"
          v-for="(item, index) in plugNameList"
          :key="index"
          @click="hitplugName(item, index)"
        >
          {{ item.type }}
        </div>
      </div>
      <div class="plugDetailRight">
        <template v-for="(item, index) in plugTypeList">
          <div class="messageBox" :key="index" v-if="item.defaultShow">
            <div class="title">{{ item.name }}</div>
            <div class="middle">
              <div class="middle-img">
                <img :src="item.imgUrl" alt="" />
                <span>{{ item.message }}</span>
              </div>
            </div>
            <div class="plug-bottom" @click="addPulgin(item)">添加</div>
          </div>
        </template>
      </div>
    </div>
  </div>
</template>

<script>
import "./../mainCss/index.scss";
import plugs from "./plugin";
import { postPlugin } from "@/api/AboutServe.js";
// import  jwt from '@/assets/pluginImgs/icon_jwt-auth.png'
export default {
  data() {
    return {
      name: "",
      plugNameList: plugs,
      defaultCss: [
        "defaultCss hitCss",
        "defaultCss",
        "defaultCss",
        "defaultCss",
        "defaultCss",
      ],
      plugTypeList: plugs[0].content,
      appCode: "",
      appId: "",
    };
  },
  created() {
    this.appCode = this.$route.query.appcode;
    this.appId = this.$route.query.appid;
  },
  methods: {
    // 切换插件类型
    hitplugName(e, i) {
      const defaultCss = [];
      for (let index = 0; index < this.defaultCss.length; index++) {
        if (index === i) {
          defaultCss.push("defaultCss hitCss");
          this.plugTypeList = e.content;
        } else {
          defaultCss.push("defaultCss");
        }
      }
      this.defaultCss = defaultCss;
    },
    nameChange() {
      for (let index in this.plugTypeList) {
        if (this.plugTypeList[index].name.indexOf(this.name) > -1) {
          this.plugTypeList[index].defaultShow = true;
        } else {
          this.plugTypeList[index].defaultShow = false;
        }
      }
    },
    addPulgin(e) {
      // console.log(e)
      if (e.configuration) {
        this.$router.push({
          path:
            "/serve/serveDetail/pluginConfig/" +
            e.code +
            "?appcode=" +
            this.appCode +
            "&appid=" +
            this.appId,
        });
        // this.$confirm(e.name + " 插件需要配置后才能添加", "提示", {
        //   confirmButtonText: "去配置",
        //   cancelButtonText: "取消",
        //   type: "warning",
        // })
        //   .then(() => {
        //     this.$router.push({
        //       path:
        //         "/serve/serveDetail/pluginConfig/" +
        //         e.code +
        //         "?appcode=" +
        //         this.appCode +
        //         "&appid=" +
        //         this.appId,
        //     });
        //   })
        //   .catch(() => {});
      } else {
        this.$router.push({
          path:
            "/serve/serveDetail/pluginConfig/" +
            e.code +
            "?appcode=" +
            this.appCode +
            "&appid=" +
            this.appId,
        });
        // this.$confirm("是否将 " + e.name + " 插件添加到当前服务?", "提示", {
        //   confirmButtonText: "确定",
        //   cancelButtonText: "取消",
        //   type: "success",
        // })
        //   .then(() => {
        //     // 添加插件
        //     console.log(e);
        //     const query = {
        //       pluginType: e.code,
        //       appCode: this.appCode,
        //       appId: this.appId,
        //     };
        //     postPlugin(query).then((res) => {
        //       if (res.code === 200) {
        //         this.$router.push({
        //           path: "/serve/serveDetail/" + this.appCode,
        //         });
        //       }
        //     });
        //   })
        //   .catch(() => {});
      }
    },
  },
};
</script>

<style lang='scss' scoped>
.plug-main {
  min-height: inherit;
  .plugDetail {
    min-height: inherit;
    position: relative;
    .plug-top {
      display: flex;
      justify-content: space-between;
      .left {
        line-height: 32px;
      }
    }
    .plugDetailLeft {
      width: 120px;
      position: absolute;
      height: 100%;
      top: 40px;
      border-right: 1px solid #e1e6ee;
      border-top: 1px solid #e1e6ee;
      .defaultCss {
        margin: 20px;
        font-size: 12px;
        cursor: pointer;
      }
      .hitCss {
        position: relative;
        color: #2650ff;
        &:after {
          content: " ";
          position: absolute;
          right: 20px;
          top: 3px;
          width: 6px;
          height: 6px;
          background-color: #2650ff;
          border-radius: 3px;
        }
      }
    }
    .plugDetailRight {
      width: calc(100% - 120px);
      position: absolute;
      top: 40px;
      left: 120px;
      border-top: 1px solid #e1e6ee;
      display: flex;
      justify-content: flex-start;
      padding: 20px 0px 0px 20px;
      flex-wrap: wrap;
      overflow: auto;
      height: 100%;
      align-content: flex-start;
      .messageBox {
        width: 32%;
        margin-left: 1%;
        height: 180px;
        border: 1px solid #e1e6eb;
        border-radius: 3px;
        margin-bottom: 15px;
        .title {
          width: 100%;
          height: 40px;
          line-height: 40px;
          text-align: center;
          background-color: #f4f6ff;
        }
        .middle {
          width: 100%;
          height: 100px;
          overflow: hidden;
          .middle-img {
            margin: 10px;
            height: 80px;
            vertical-align: middle;
            // background-color: #ccc;
            img {
              width: 80px;
              height: 80px;
              background-color: #fff;
              vertical-align: middle;
              display: inline-block;
            }
            span {
              width: calc(100% - 100px);
              display: inline-block;
              vertical-align: middle;
              word-break: hyphenate;
              text-align: center;
              // padding: 0px 10px;
              margin-left: 5px;
            }
          }
        }
        .plug-bottom {
          height: 38px;
          width: 100%;
          color: #2650ff;
          text-align: center;
          line-height: 38px;
          cursor: pointer;
          font-size: 12px;
        }
        .plug-bottom:hover {
          background-color: #2650ff;
          font-size: 12px;
          color: #fff;
          border: 2px solid #2650ff;
          border-radius: 3px;
        }
      }
      .messageBox:hover {
        border: 1px solid #2650ff;
      }
    }
    .plugDetailRight::-webkit-scrollbar {
      display: none;
    }
  }
}
</style>