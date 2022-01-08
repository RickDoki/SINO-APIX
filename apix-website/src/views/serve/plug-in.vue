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
          {{ item }}
        </div>
      </div>
      <div class="plugDetailRight">
        <div
          class="messageBox"
          v-for="(item, index) in plugTypeList"
          :key="index"
        >
          <div class="title">jwt-auth</div>
          <div class="middle">
            <div class="middle-img">
              <img src="" alt="" />
              <span>123456</span>
            </div>
          </div>
          <div class="plug-bottom">添加</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import "./../mainCss/index.scss";
export default {
  data() {
    return {
      name: "",
      plugNameList: [
        "身份认证",
        "安全防护",
        "流量控制",
        "可观测性",
        "其他插件",
      ],
      defaultCss: [
        "defaultCss hitCss",
        "defaultCss",
        "defaultCss",
        "defaultCss",
        "defaultCss",
      ],
      plugTypeList: ["", "", "", "", "", ""],
    };
  },
  methods: {
    // 切换插件类型
    hitplugName(e, i) {
      const defaultCss = [];
      for (let index = 0; index < this.defaultCss.length; index++) {
        console.log(index, i);
        if (index === i) {
          defaultCss.push("defaultCss hitCss");
        } else {
          defaultCss.push("defaultCss");
        }
      }
      this.defaultCss = defaultCss;
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
      justify-content: space-between;
      padding: 20px 0px 0px 20px;
      flex-wrap: wrap;
      overflow: auto;
      height: 100%;
      align-content: flex-start;
      .messageBox {
        width: 32%;
        height: 180px;
        border: 1px solid #ccc;
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
              background-color: #ccc;
              vertical-align: middle;
            }
            span {
              vertical-align: middle;
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
    }
    .plugDetailRight::-webkit-scrollbar {
      display: none;
    }
  }
}
</style>