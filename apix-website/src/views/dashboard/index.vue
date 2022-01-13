<template>
  <div class="main">
    <el-row :gutter="28" type="flex" class="dash_top" justify="space-between">
      <el-col :span="8">
        <div class="top_nums">
          <img src="./../../assets/img/dy_serve.png"/>
          <div class="nums_label">
            <div class="nums">{{serveNum}}</div>
            <div class="title">发布的服务</div>
          </div>
        </div>
      </el-col>
      <el-col :span="8">
        <div class="top_nums">
          <img src="./../../assets/img/fb_serve.png"/>
          <div class="nums_label">
            <div class="nums">{{subscribeNum}}</div>
            <div class="title">订阅的服务</div>
          </div>
        </div>
      </el-col>
      <el-col :span="8">
        <div class="top_nums">
          <img src="./../../assets/img/apiNums.png"/>
          <div class="nums_label">
            <div class="nums">{{apiNum}}</div>
            <div class="title">API数</div>
          </div>
        </div>
      </el-col>
    </el-row>
    <el-row :gutter="28" type="flex" class="dash_bottom" justify="space-between">
      <el-col :span="16">
        <div class="bottom_left">
          <div class="box-top">
            <div class="top_left">我的服务每日调用次数</div>
            <div class="top_right">
              <el-select
                v-model="valueMy"
                @change="changeMy"
                size="mini"
                placeholder="请选择"
              >
                <el-option
                  v-for="item in optionsMy"
                  :key="item.appCode"
                  :label="item.appName"
                  :value="item.appCode"
                >
                </el-option>
              </el-select>
            </div>
          </div>
          <div id="myChart" class="box_chart"></div>
        </div>
      </el-col>
      <el-col :span="8">
        <div class="bottom_right">
          <div class="commonUse_title">常用入口</div>
          <div class="commonUse_content" @click="go_serve_add">
            <img src="./../../assets/img/create_serve.png"/>
            <div class="content_title">创建服务</div>
            <i class="el-icon-arrow-right"></i>
          </div>
          <div class="commonUse_content" @click="go_upstream_detail">
            <img src="./../../assets/img/create_upstream.png"/>
            <div class="content_title">创建上游服务</div>
            <i class="el-icon-arrow-right"></i>
          </div>
          <div class="commonUse_content" @click="go_api_add">
            <img src="./../../assets/img/create_api.png"/>
            <div class="content_title">创建API</div>
            <i class="el-icon-arrow-right"></i>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import { getToken } from "@/utils/auth"; // get token from cookie
import { serveList, getStatistics, listSubscribe, Apilistnums } from "@/api/AboutApp";
export default {
  name: "Dashboard",
  components: {
  },
  data () {
    return {
      serveNum: 0,
      subscribeNum: 0,
      apiNum: 0,
      optionsMy: [],
      valueMy: "all",
      myApiQueryList: []
    }
  },
  created () {
    this.developerId = getToken("userId_api");
    const query = "?developerId=" + this.developerId;
    this.getservelist();
    this.getlistSubscribe(query);
    this.getApiNums(query);

    setTimeout(() => {
      // console.log(999999999999)
      this.createApi = JSON.parse(sessionStorage.getItem('createApi'))
      this.ApiState = JSON.parse(sessionStorage.getItem('ApiState'))
      this.upstrem = JSON.parse(sessionStorage.getItem('upstrem'))
      this.createApp = JSON.parse(sessionStorage.getItem('createApp'))
    }, 2100)
  },
  methods: {
    // 获取Api数量
    getApiNums (query) {
      Apilistnums(query).then((res) => {
        this.apiNum = res.data.total;
      });
    },
    getlistSubscribe (query) {
      listSubscribe(query).then((res) => {
        this.subscribeNum = res.data.total;
      });
    },
    getservelist () {
      serveList(this.developerId).then((res) => {
        if (res.code === 200) {
          this.optionsMy = res.data;
          this.serveNum = res.data.length;
          if (this.optionsMy.length > 0) {
            this.optionsMy.forEach((item) => {
              this.myApiQueryList.push(item.appCode);
            });
          }
          const query = {
            appCodes: this.myApiQueryList,
          };
          this.optionsMy.push({
            appName: "全部",
            appCode: "all",
          });
          this.getChartsOptions(query, "optionsMy");
        }
      });
    },
    go_api_add () {
      this.$router.push({ path: "/api/add" });
    },
    go_serve_add () {
      this.$router.push({ path: "/serve/create" });
    },
    go_upstream_detail () {
      this.$router.push({ path: "/api/upstream/create" });
    },
    changeMy () {
      const query = {};
      if (this.valueMy === "all") {
        query.appCodes = this.myApiQueryList;
      } else {
        query.appCodes = [this.valueMy];
      }
      this.getChartsOptions(query, "optionsMy");
    },
    getChartsOptions (query, optionsId) {
      getStatistics(query).then((res) => {
        if (optionsId === "optionsMy") {
          let myChart = this.$echarts.init(document.getElementById('myChart'))
          myChart.setOption({
            tooltip: {
              formatter: '<span style="color: #657180; margin-left: 8px; float: right;">{a} <br/>{b} : <span style="color: #2650FF;">{c}</span> 次</span>',
              backgroundColor: '#FAFBFF',	//tooltip背景色
              color: '#2650FF',
              trigger: 'axis',
              axisPointer: {
                type: 'cross',
                lineStyle: {
                  color: ['#E6E6E6'],
                  width: 1,
                  type: 'solid'
                },
                label: {
                  backgroundColor: '#B9C6FF'
                }
              }
            },
            grid: {
              left: '5%',
              right: '4%',
              bottom: '3%',
              containLabel: true
            },
            xAxis: [
              {
                type: 'category',
                boundaryGap: false,
                data: res.data.keys,
                axisLine: {
                  show: false,//不显示坐标轴线
                },
                axisTick: {
                  show: false//不显示坐标轴刻度线
                },
              }
            ],
            yAxis: [
              {
                type: 'value',
                axisLine: {
                  show: false,//不显示坐标轴线
                },
                axisTick: {
                  show: false//不显示坐标轴刻度线
                },
                //设置网格线颜色
                splitLine: {
                  show: true,
                  lineStyle: {
                    color: ['#E6E6E6'],
                    width: 1,
                    type: 'solid'
                  }
                }
              }
            ],
            series: [
              {
                name: '调用次数',
                type: 'line',
                stack: '总量',
                smooth: true,//设置折线图平滑
                symbol: "none", //不显示拐点
                itemStyle: {
                  normal: {
                    areaStyle: {
                      type: 'default',
                      //渐变色实现===
                      color: new this.$echarts.graphic.LinearGradient(0, 0, 0, 1,
                        //三种由深及浅的颜色
                        [{
                          offset: 0,
                          color: '#B9C6FF'
                        }, {
                          offset: 1,
                          color: '#ffffff'
                        }]),
                    },
                    lineStyle: {  //线的颜色
                      color: '#2650FF'
                    },
                    //以及在折线图每个日期点顶端显示数字
                    label: {
                      show: true,
                      position: 'top',
                      textStyle: {
                        color: 'white'
                      }
                    }
                  },
                },
                areaStyle: {},
                data: res.data.values
              }
            ]
          });
          window.onresize = myChart.resize;
        }
      });
    }
  }
}
</script>
<style lang="scss" scoped>
.main {
  /deep/ .el-input__inner {
    border-radius: 25px 25px 25px 25px;
    border: 1px solid #f3f3f3;
  }
  /deep/ .el-input__inner:hover {
    border: 1px solid rgba(38, 80, 255, 0.3);
  }
  .dash_top {
    display: flex;
    .top_nums {
      display: flex;
      height: 128px;
      .nums_label {
        width: 100%;
        background: #ffffff;
        border-radius: 16px 16px 16px 16px;
        padding: 36px 10%;
        box-shadow: #f3f3f3 4px 0px 10px;
        .nums {
          font-size: 24px;
          font-family: Microsoft YaHei UI-Bold, Microsoft YaHei UI;
          font-weight: bold;
          color: #1d1c35;
          line-height: 36px;
        }
        .title {
          font-size: 16px;
          font-family: Microsoft YaHei UI-Regular, Microsoft YaHei UI;
          font-weight: 400;
          color: #494e6a;
          line-height: 20px;
        }
      }
    }
  }
  .dash_bottom {
    margin: 32px 0px;
    display: flex;
    .bottom_left {
      background: #ffffff;
      border-radius: 16px 16px 16px 16px;
      box-shadow: #f3f3f3 0px 0px 10px;
      min-height: calc(100vh - 320px);
      padding-bottom: 24px;
      .box-top {
        width: 100%;
        height: 70px;
        display: flex;
        div {
          width: 50%;
        }
        .top_left {
          padding: 0px 24px;
          line-height: 70px;
          font-size: 16px;
          font-family: Microsoft YaHei UI-Regular, Microsoft YaHei UI;
          font-weight: 400;
          color: #000000;
        }
        .top_right {
          text-align: right;
          padding-right: 24px;
          padding-top: 22px;
        }
      }
      .box_chart {
        height: calc(100vh - 420px);
        width: 100%;
      }
    }
    .bottom_right {
      padding: 10px 24px;
      background: #ffffff;
      border-radius: 16px 16px 16px 16px;
      box-shadow: #f3f3f3 0px 0px 10px;
      min-height: calc(100vh - 320px);
      .commonUse_title {
        height: 70px;
        line-height: 70px;
      }
      .commonUse_content {
        margin-bottom: 24px;
        padding: 24px;
        display: flex;
        width: 100%;
        height: 80px;
        border-radius: 8px 8px 8px 8px;
        border: 1px solid rgba(0, 0, 0, 0.1);
        .content_title {
          margin: auto;
          margin-left: 10%;
        }
        .el-icon-arrow-right {
          margin: auto;
          margin-right: 1%;
        }
      }
      .commonUse_content:hover {
        box-shadow: 0px 0px 8px 1px rgba(38, 80, 255, 0.3);
        border: 1px solid #2650ff;
      }
    }
  }
}
</style>
