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
        :min="0"
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
        :min="0"
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
          :min="0"
        ></el-input-number>
        <el-select style="width: 80px; margin-left: 5px" v-model="apitimeValue[index]">
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
          :min="0"
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
      <el-button size="small" type="primary" @click="submitForm()"
        >添加</el-button
      >
      <el-button size="small" @click="resetForm('ruleForm')">取消</el-button>
    </div>
  </div>
</template>

<script>
import { apiList, save, open } from "@/api/AboutServe";
import { getToken } from "@/utils/auth"; // get token from cookie

export default {
  data() {
    return {
      // api列表
      options: [],
      // 服务时长
      servetime: "0",
      servetimeValue: "0",
      // 服务控流
      servenum: "0",
      // 用户id
      developerId: "",
      // 控制api数量
      apiConfigList: [""],
      // 选中api信息
      apivalueList: [""],
      //api时长
      apitime: ["0"],
      // api时间value
      apitimeValue: ["0"],
      // api控流
      apiNum: ["0"],
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
    };
  },
  created() {
    this.developerId = getToken("userId_api");
    this.appCode = this.$route.query.appcode;
    this.appId = this.$route.query.appid;
    this.getApiList();
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
      const query = [
        {
          appId:this.appId,
          count:this.servenum,
          interval:this.servetime,
          intervalUnit:this.servetimeValue
        }
      ]
      for (let index = 0; index < this.apiConfigList.length; index++) {
        query.push({
          appId:this.appId,
          path:this.apivalueList[index].apiUrl,
          count:this.apiNum[index],
          interval:this.apitime[index],
          intervalUnit:this.apitimeValue[index]
        })
      }
      // console.log(query)
      save(query).then(res=>{
        if (res.code ===200) {
          const query1 = {
            appId :this.appId
          }
          open(query1).then(res=>{
            if (res ===200) {
              this.$router.push({ path: "/serve/serveDetail/" + this.appCode });
            }
          })
        }
      })
    },
    handleChange() {},
    apiConfigAdd() {
      this.apiConfigList.push("");
      this.apivalueList.push('')
      this.apitime.push('0')
      this.apitimeValue.push('0')
      this.apiNum.push('0')
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