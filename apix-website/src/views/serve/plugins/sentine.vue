<template>
  <div class="s_main">
    <p>服务控流配置</p>
    <p class="s_title">
      服务控制时长 <span style="color: red">*</span>
      <el-tooltip
        effect="light"
        content="单位时间内当前服务的请求次数上限"
        placement="right"
      >
        <i class="el-icon-warning-outline s_icon"></i>
      </el-tooltip>
    </p>
    <div style="margin-top: 20px">
      <el-input-number
        v-model="servetime"
        @change="handleChange"
        :min="1"
      ></el-input-number>
      <el-select style="width: 80px; margin-left: 5px" v-model="servetimeValue">
        <el-option
          v-for="item in serveOptions"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        >
        </el-option>
      </el-select>
    </div>
    <p class="s_title">
      服务控流值
      <el-tooltip
        effect="light"
        content="当前服务能够被访问的次数上限"
        placement="right"
      >
        <i class="el-icon-warning-outline s_icon"></i>
      </el-tooltip>
    </p>
    <div style="margin-top: 20px">
      <el-input-number
        v-model="servenum"
        @change="handleChange"
        :min="1"
      ></el-input-number>
    </div>
    <p style="margin-top: 20px">API控流配置</p>
    <div class="s_apiType">
      <div class="one">API</div>
      <div class="two">API控制时长</div>
      <div class="three">
        API控流值
        <el-tooltip
          effect="light"
          content="单位时间内一个api能够被访问的次数上限"
          placement="right"
        >
          <i class="el-icon-warning-outline s_icon"></i>
        </el-tooltip>
      </div>
    </div>
    <div
      v-for="(item, index) in apiConfigList"
      :key="index"
      class="information"
    >
      <div class="one">
        <el-select
          style="width: 200px"
          @change="apiChose(index)"
          value-key="apiName"
          v-model="apivalueList[index]"
        >
          <el-option
            v-for="item in options"
            :key="item.apiId"
            :label="item.apiName"
            :value="item"
          >
          </el-option>
        </el-select>
      </div>
      <div class="two">
        <el-input-number
          style="margin-left: 15px"
          v-model="apitime[index]"
          @change="handleChange"
          :min="1"
        ></el-input-number>
        <el-select
          style="width: 80px; margin-left: 5px"
          v-model="apitimeValue[index]"
        >
          <el-option
            v-for="item in serveOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </div>
      <div class="three">
        <el-input-number
          style="margin-left: 15px"
          v-model="apiNum[index]"
          @change="handleChange"
          :min="1"
        ></el-input-number>
      </div>
      <div class="four">
        <el-button v-if="index === 0" @click="apiConfigAdd">添加</el-button>
        <el-button v-if="index !== 0" type="danger" @click="apiConfigDel(index)"
          >删除</el-button
        >
      </div>
    </div>
    <div class="bottom_button_a">
      <el-button size="small" type="primary" @click="submitForm()">{{
        buttonFont
      }}</el-button>
      <el-button size="small" @click="resetForm('ruleForm')">取消</el-button>
    </div>
  </div>
</template>

<script>
import {
  apiList,
  save,
  postPlugin,
  getPlugin,
  putPlugin,
} from "@/api/AboutServe";
import { getToken } from "@/utils/auth"; // get token from cookie

export default {
  data() {
    return {
      // api列表
      options: [],
      // 服务时长
      servetime: "1",
      servetimeValue: "1",
      // 服务控流
      servenum: "1",
      // 用户id
      developerId: "",
      // 控制api数量
      apiConfigList: [""],
      // 选中api信息
      apivalueList: [""],
      //api时长
      apitime: ["1"],
      // api时间value
      apitimeValue: ["1"],
      // api控流
      apiNum: ["1"],
      // 时长选择框
      serveOptions: [
        {
          value: "0",
          label: "秒",
        },
        {
          value: "1",
          label: "分",
        },
        {
          value: "2",
          label: "时",
        },
        {
          value: "3",
          label: "天",
        },
      ],
      appCode: "",
      appId: "",
      id: "",
      enabled: 0,
      buttonFont: "添加",
    };
  },
  created() {
    this.developerId = getToken("userId_api");
    this.appCode = this.$route.query.appcode;
    this.appId = this.$route.query.appid;
    this.getApiList();
    // 是否为配置插件
    if (this.$route.query.pluginParams) {
      this.id = this.$route.query.id;
      //查询当前插件详情
      getPlugin(this.id, this.appCode).then((res) => {
        if (res.code === 200) {
          this.enabled = res.data.enabled;
          const data = JSON.parse(res.data.pluginParams);
          console.log(data);
          this.servetime = data[0].interval;
          this.servenum = data[0].count;
          this.servetimeValue = data[0].intervalUnit;
          this.apivalueList = [];
          this.apiConfigList = [];
          this.apitime = [];
          this.apiNum = [];
          this.apitimeValue = [];
          for (let index = 1; index < data.length; index++) {
            this.apiConfigList.push("");
            this.apivalueList.push(data[index].apiId);
            this.apitime.push(data[index].interval);
            this.apiNum.push(data[index].count);
            this.apitimeValue.push(data[index].intervalUnit);
          }
          // data.allowMethods = data.allowMethods.split(",");
          // this.ruleForm = data;
        }
      });
      this.buttonFont = "应用";
    } else {
      this.buttonFont = "添加";
    }
  },
  methods: {
    // 获取apilist
    getApiList() {
      apiList(this.developerId).then((res) => {
        if (res.code === 200) {
          // console.log(res)
          this.options = res.data.apiList;
        }
      });
    },
    // 获取api
    apiChose(i) {
      console.log(this.apivalueList);
    },
    submitForm() {
      if (this.buttonFont === "添加") {
        const plugQuery = [
          {
            appId: this.appId,
            count: this.servenum,
            interval: this.servetime,
            intervalUnit: this.servetimeValue,
            path: "",
          },
        ];
        const query = [
          {
            appId: this.appId,
            count: this.servenum,
            interval: this.servetime,
            intervalUnit: this.servetimeValue,
            path: "",
          },
        ];
        for (let index = 0; index < this.apiConfigList.length; index++) {
          if (apivalueList[index] === "") {
          } else {
            query.push({
              appId: this.appId,
              path: this.apivalueList[index].apiUrl,
              count: this.apiNum[index],
              interval: this.apitime[index],
              intervalUnit: this.apitimeValue[index],
            });
            plugQuery.push({
              appId: this.appId,
              path: this.apivalueList[index].apiUrl,
              count: this.apiNum[index],
              interval: this.apitime[index],
              intervalUnit: this.apitimeValue[index],
              apiId: this.apivalueList[index],
            });
          }
        }
        save(query).then((res) => {
          if (res.code === 200) {
            const pluginquery = {
              pluginType: "sentinel",
              appCode: this.appCode,
              appId: this.appId,
              pluginParams: JSON.stringify(plugQuery),
            };
            postPlugin(pluginquery).then((res) => {
              if (res.code === 200) {
                this.$router.push({
                  path: "/serve/serveDetail/" + this.appCode,
                });
              }
            });
          }
        });
      } else {
        const plugQuery = [
          {
            appId: this.appId,
            count: this.servenum,
            interval: this.servetime,
            intervalUnit: this.servetimeValue,
            path: "",
          },
        ];
        const query = [
          {
            appId: this.appId,
            count: this.servenum,
            interval: this.servetime,
            intervalUnit: this.servetimeValue,
            path: "",
          },
        ];
        for (let index = 0; index < this.apiConfigList.length; index++) {
          if (apivalueList[index] === "") {
          } else {
            query.push({
              appId: this.appId,
              path: this.apivalueList[index].apiUrl,
              count: this.apiNum[index],
              interval: this.apitime[index],
              intervalUnit: this.apitimeValue[index],
            });
            plugQuery.push({
              appId: this.appId,
              path: this.apivalueList[index].apiUrl,
              count: this.apiNum[index],
              interval: this.apitime[index],
              intervalUnit: this.apitimeValue[index],
              apiId: this.apivalueList[index],
            });
          }
        }
        save(query).then((res) => {
          if (res.code === 200) {
            const pluginquery = {
              pluginType: "sentinel",
              appCode: this.appCode,
              appId: this.appId,
              id: this.id,
              enabled: this.enabled,
              pluginParams: JSON.stringify(plugQuery),
            };
            putPlugin(pluginquery).then((res) => {
              if (res.code === 200) {
                this.$router.push({
                  path: "/serve/serveDetail/" + this.appCode,
                });
              }
            });
          }
        });
      }
    },
    // 取消
    resetForm() {
      this.$router.push({
        path: "/serve/serveDetail/" + this.appCode,
      });
    },
    handleChange() {},
    apiConfigAdd() {
      this.apiConfigList.push("");
      this.apivalueList.push("");
      this.apitime.push("0");
      this.apitimeValue.push("0");
      this.apiNum.push("0");
    },
    apiConfigDel(i) {
      this.apiConfigList.splice(i, 1);
    },
  },
};
</script>

<style scoped lang='scss'>
.s_main {
  padding-left: 30px;
  font-size: 14px;
  font-weight: 700;
  .s_title {
    margin-top: 30px;
    font-weight: 400;
    .s_icon {
      margin-left: 5px;
      cursor: pointer;
    }
  }
  .s_apiType {
    display: flex;
    div {
      text-align: center;
    }
    .one {
      width: 200px;
    }
    .two {
      width: 300px;
    }
    .three {
      width: 220px;
    }
  }
  .information {
    display: flex;
    margin-top: 20px;
    .one {
      width: 200px;
    }
    .two {
      width: 300px;
    }
    .three {
      width: 220px;
    }
  }
}
</style>