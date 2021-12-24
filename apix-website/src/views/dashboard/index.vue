<template>
  <div class="dashboard-container">
    <el-row :gutter="30">
      <el-col :span="16">
        <div class="introduction">
          <div class="introduction_title">快速入门</div>
          <div class="line"></div>
          <div class="introduction_photo">
            <div>
              <img src="./../../assets/img/icon_cjapi.png" alt="" />
            </div>
            <div>
              <img src="./../../assets/img/icon_fbapi.png" alt="" />
            </div>
            <div>
              <img src="./../../assets/img/icon_ccyy.png" alt="" />
            </div>
            <div>
              <img src="./../../assets/img/icon_sqyy.png" alt="" />
            </div>
          </div>
          <div class="introduction_steps">
            <div>
              <span style="color: #4461D7; font-size: 16px">1.</span>
              <span>创建API</span>
              <div class="steps_line_right"></div>
            </div>
            <div>
              <span style="color: #4461D7; font-size: 16px">2.</span>
              <div class="steps_line_left"></div>
              <span>发布API</span>
              <div class="steps_line_right"></div>
            </div>
            <div>
              <span style="color: #4461D7; font-size: 16px">3.</span>
              <div class="steps_line_left"></div>
              <span>创建应用</span>
              <div class="steps_line_right"></div>
            </div>
            <div>
              <span style="color: #4461D7; font-size: 16px">4.</span>
              <div class="steps_line_left"></div>
              <span>授权应用</span>
            </div>
          </div>
          <div class="introduction_content">
            <div>创建API并定义属性</div>
            <div>将API发布到Release环境或者至自定义环境</div>
            <div>为调用者创建应用，以验证其调用身份</div>
            <div>将API授权给对应的应用</div>
          </div>
        </div>
        <div class="box">
          <div class="box-top">
            <div class="top_left">我的应用每日调用次数</div>
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
          <div class="box-chart">
            <charts :echartData="options"></charts>
          </div>
        </div>
        <div class="box">
          <div class="box-top">
            <div class="top_left">已订阅应用每日调用次数</div>
            <div class="top_right">
              <el-select
                v-model="subscribe"
                @change="subscribeChange"
                size="mini"
                placeholder="请选择"
              >
                <el-option
                  v-for="item in optionsSubscribe"
                  :key="item.appCode"
                  :label="item.appName"
                  :value="item.appCode"
                >
                </el-option>
              </el-select>
            </div>
          </div>
          <div class="box-chart">
            <charts :echartData="optionsSubscribeCharts"></charts>
          </div>
        </div>
      </el-col>
      <el-col :span="8">
        <div class="statistics">
          <div class="statistics_title">数据统计</div>
          <div class="line"></div>
          <div class="statistics_numbers">
            <div>
              <p>注册应用数</p>
              <div class="content">{{ myAppNum }}</div>
            </div>
            <div>
              <p>订阅应用数</p>
              <div class="content">{{ subscribeNum }}</div>
            </div>
            <div>
              <p>API数量</p>
              <div class="content">{{ apiNum }}</div>
            </div>
          </div>
        </div>
        <div class="commonUse">
          <div class="commonUse_title">常用入口</div>
          <div class="line"></div>
          <div class="commonUse_content">
            <div @click="go_api_add">
              <div>
                <img src="./../../assets/img/icon1_cjapi.png" alt="" />
                <span style="margin-left: 20px">创建API</span>
              </div>
            </div>
            <div @click="go_app_add">
              <div>
                <img src="./../../assets/img/icon1_cjyy.png" alt="" />
                <span style="margin-left: 20px">创建应用</span>
              </div>
            </div>
            <div @clicl="go_data_Statistics">
              <div>
                <img src="./../../assets/img/icon1_sjtj.png" alt="" />
                <span style="margin-left: 20px">API数据统计</span>
              </div>
            </div>
            <div @click="go_upstream_detail">
              <div>
                <img src="./../../assets/img/icon1_cjsyfw.png" alt="" />
                <span style="margin-left: 20px">创建上游服务</span>
              </div>
            </div>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import charts from "@/components/Echarts/charts.vue";
import { getToken } from "@/utils/auth"; // get token from cookie
import {
  list,
  getStatistics,
  listSubscribe,
  Apilistnums,
} from "@/api/AboutApp";

export default {
  name: "Dashboard",
  components: {
    charts,
  },
  data() {
    return {
      myAppNum: "",
      subscribeNum: "",
      apiNum: "",
      optionsMy: [],
      valueMy: "",
      myApiQueryList: [],
      subscribe: "",
      optionsSubscribe: [],
      subscribeList: [],
      optionsSubscribeCharts: {
        title: {
          //图表的标题，注释掉就没有名称了
          // text: "柱状图设计1-带滑动条、柱子带圆弧角",
        },
        color: ["#3A85D3", "#5AD8A6", "#5470c6", "#008000", "#61a0a8"], //下面这种直接配置演示也行
        //color: ['#3A85D3','#5AD8A6','#5470c6',  '#008000', '#61a0a8'], //依次选择颜色，默认为第一个颜色，多根柱子多个颜色
        tooltip: {
          trigger: "axis", //触发类型；轴触发，axis则鼠标hover到一条柱状图显示全部数据，item则鼠标hover到折线点显示相应数据，
          axisPointer: {
            // 坐标轴指示器，坐标轴触发有效
            type: "shadow", // 默认为直线，可选为：'line' | 'shadow'
          },
        },
        legend: {
          // data: ["直接访问", "线下访问"], //这里设置柱状图上面的方块，名称跟series里的name保持一致
          align: "right", //上面方块靠右还是居中的设置.不设置则居中
          right: 10,
        },
        grid: {
          left: "3%", //柱状图距离左边的距离，也可以用像素px
          right: "4%", //柱状图距离右边的距离，也可以用像素px
          bottom: "8%", //网格图（柱状图、折线图、气泡图等）离底部的距离，也可以用像素比如10px
          containLabel: true, //grid 区域是否包含坐标轴的刻度标签。false可能溢出，默认为false
        },

        // 缩放组件
        dataZoom: {
          type: "slider",
          start: 0,
          end: 80, //缩放组件显示70%的位置
        },
        xAxis: [
          {
            type: "category",
            data: [],
            axisTick: {
              alignWithLabel: true,
            },
            axisLabel: {
              rotate: 45, //控制柱状图X轴label是否倾斜显示
            },
          },
        ],
        yAxis: [
          {
            type: "value",
          },
        ],
        series: [
          {
            //柱状图-柱子1
            // name: "直接访问", //需要跟legend配置项对应
            type: "bar",
            itemStyle: {
              barBorderRadius: [10, 10, 0, 0], //控制柱状图的圆角显示弧度，（顺时针左上，右上，右下，左下）
            },
            barWidth: "30%", //barWidth设置每根柱状图的宽度
            data: [],
          },
        ],
      },
      options: {
        title: {
          //图表的标题，注释掉就没有名称了
          // text: "柱状图设计1-带滑动条、柱子带圆弧角",
        },
        color: ["#3A85D3", "#5AD8A6", "#5470c6", "#008000", "#61a0a8"], //下面这种直接配置演示也行
        //color: ['#3A85D3','#5AD8A6','#5470c6',  '#008000', '#61a0a8'], //依次选择颜色，默认为第一个颜色，多根柱子多个颜色
        tooltip: {
          trigger: "axis", //触发类型；轴触发，axis则鼠标hover到一条柱状图显示全部数据，item则鼠标hover到折线点显示相应数据，
          axisPointer: {
            // 坐标轴指示器，坐标轴触发有效
            type: "shadow", // 默认为直线，可选为：'line' | 'shadow'
          },
        },
        legend: {
          // data: ["直接访问", "线下访问"], //这里设置柱状图上面的方块，名称跟series里的name保持一致
          align: "right", //上面方块靠右还是居中的设置.不设置则居中
          right: 10,
        },
        grid: {
          left: "3%", //柱状图距离左边的距离，也可以用像素px
          right: "4%", //柱状图距离右边的距离，也可以用像素px
          bottom: "8%", //网格图（柱状图、折线图、气泡图等）离底部的距离，也可以用像素比如10px
          containLabel: true, //grid 区域是否包含坐标轴的刻度标签。false可能溢出，默认为false
        },

        // 缩放组件
        dataZoom: {
          type: "slider",
          start: 0,
          end: 80, //缩放组件显示70%的位置
        },
        xAxis: [
          {
            type: "category",
            data: [],
            axisTick: {
              alignWithLabel: true,
            },
            axisLabel: {
              rotate: 45, //控制柱状图X轴label是否倾斜显示
            },
          },
        ],
        yAxis: [
          {
            type: "value",
          },
        ],
        series: [
          {
            //柱状图-柱子1
            // name: "直接访问", //需要跟legend配置项对应
            type: "bar",
            itemStyle: {
              barBorderRadius: [10, 10, 0, 0], //控制柱状图的圆角显示弧度，（顺时针左上，右上，右下，左下）
            },
            barWidth: "30%", //barWidth设置每根柱状图的宽度
            data: [],
          },
        ],
      },
    };
  },
  created() {
    this.developerId = getToken("userId");
    const query = "?developerId=" + this.developerId;
    this.getapplist(query);
    this.getlistSubscribe(query);
    this.getApiNums(query);
  },
  methods: {
    getapplist(query) {
      list(query).then((res) => {
        if (res.code === 200) {
          this.optionsMy = res.data.appList;
          this.myAppNum = res.data.total;
          this.optionsMy.forEach((item) => {
            this.myApiQueryList.push(item.appCode);
          });
          const query = {
            appCodes: this.myApiQueryList,
          };
          this.getChartsOptions(query, "optionsMy");
        } else {
        }
      });
    },
    getChartsOptions(query, optionsId) {
      getStatistics(query).then((res) => {
        if (optionsId === "optionsMy") {
          console.log(111);
          this.options = {
            title: {
              //图表的标题，注释掉就没有名称了
              // text: "柱状图设计1-带滑动条、柱子带圆弧角",
            },
            color: ["#749AFD"], //下面这种直接配置演示也行
            //color: ['#3A85D3','#5AD8A6','#5470c6',  '#008000', '#61a0a8'], //依次选择颜色，默认为第一个颜色，多根柱子多个颜色
            tooltip: {
              trigger: "axis", //触发类型；轴触发，axis则鼠标hover到一条柱状图显示全部数据，item则鼠标hover到折线点显示相应数据，
              axisPointer: {
                // 坐标轴指示器，坐标轴触发有效
                type: "shadow", // 默认为直线，可选为：'line' | 'shadow'
              },
            },
            legend: {
              // data: ["直接访问", "线下访问"], //这里设置柱状图上面的方块，名称跟series里的name保持一致
              align: "right", //上面方块靠右还是居中的设置.不设置则居中
              right: 10,
            },
            grid: {
              left: "3%", //柱状图距离左边的距离，也可以用像素px
              right: "4%", //柱状图距离右边的距离，也可以用像素px
              bottom: "8%", //网格图（柱状图、折线图、气泡图等）离底部的距离，也可以用像素比如10px
              containLabel: true, //grid 区域是否包含坐标轴的刻度标签。false可能溢出，默认为false
            },

            // 缩放组件
            dataZoom: {
              type: "slider",
              start: 0,
              end: 80, //缩放组件显示70%的位置
            },
            xAxis: [
              {
                type: "category",
                data: res.data.keys,
                axisTick: {
                  alignWithLabel: true,
                },
                axisLabel: {
                  rotate: 45, //控制柱状图X轴label是否倾斜显示
                },
              },
            ],
            yAxis: [
              {
                type: "value",
              },
            ],
            series: [
              {
                //柱状图-柱子1
                // name: "直接访问", //需要跟legend配置项对应
                type: "bar",
                itemStyle: {
                  barBorderRadius: [10, 10, 0, 0], //控制柱状图的圆角显示弧度，（顺时针左上，右上，右下，左下）
                  color: ["#749AFD"],
                },
                barWidth: "30%", //barWidth设置每根柱状图的宽度
                data: res.data.values,
              },
            ],
          };
        } else {
          this.optionsSubscribeCharts = {
            title: {
              //图表的标题，注释掉就没有名称了
              // text: "柱状图设计1-带滑动条、柱子带圆弧角",
            },
            color: ["#749AFD"], //下面这种直接配置演示也行
            //color: ['#3A85D3','#5AD8A6','#5470c6',  '#008000', '#61a0a8'], //依次选择颜色，默认为第一个颜色，多根柱子多个颜色
            tooltip: {
              trigger: "axis", //触发类型；轴触发，axis则鼠标hover到一条柱状图显示全部数据，item则鼠标hover到折线点显示相应数据，
              axisPointer: {
                // 坐标轴指示器，坐标轴触发有效
                type: "shadow", // 默认为直线，可选为：'line' | 'shadow'
              },
            },
            legend: {
              // data: ["直接访问", "线下访问"], //这里设置柱状图上面的方块，名称跟series里的name保持一致
              align: "right", //上面方块靠右还是居中的设置.不设置则居中
              right: 10,
            },
            grid: {
              left: "3%", //柱状图距离左边的距离，也可以用像素px
              right: "4%", //柱状图距离右边的距离，也可以用像素px
              bottom: "8%", //网格图（柱状图、折线图、气泡图等）离底部的距离，也可以用像素比如10px
              containLabel: true, //grid 区域是否包含坐标轴的刻度标签。false可能溢出，默认为false
            },

            // 缩放组件
            dataZoom: {
              type: "slider",
              start: 0,
              end: 80, //缩放组件显示70%的位置
            },
            xAxis: [
              {
                type: "category",
                data: res.data.keys,
                axisTick: {
                  alignWithLabel: true,
                },
                axisLabel: {
                  rotate: 45, //控制柱状图X轴label是否倾斜显示
                },
              },
            ],
            yAxis: [
              {
                type: "value",
              },
            ],
            series: [
              {
                //柱状图-柱子1
                // name: "直接访问", //需要跟legend配置项对应
                type: "bar",
                itemStyle: {
                  barBorderRadius: [10, 10, 0, 0], //控制柱状图的圆角显示弧度，（顺时针左上，右上，右下，左下）
                  color: ["#F6D066"],
                },
                barWidth: "30%", //barWidth设置每根柱状图的宽度
                data: res.data.values,
              },
            ],
          };
        }
      });
    },
    changeMy() {
      const query = {
        appCodes: [this.subscribe],
      };
      this.getChartsOptions(query, "optionsMy");
    },
    subscribeChange() {
      const query = {
        appCodes: [this.subscribe],
      };
      this.getChartsOptions(query, "noOptions");
    },
    getlistSubscribe(query) {
      listSubscribe(query).then((res) => {
        this.optionsSubscribe = res.data.appList;
        this.subscribeNum = res.data.total;
        this.optionsSubscribe.forEach((item) => {
          this.subscribeList.push(item.appCode);
        });
        const query = {
          appCodes: this.subscribeList,
        };
        this.getChartsOptions(query, "noOptions");
      });
    },
    // 获取Api数量
    getApiNums(query) {
      Apilistnums(query).then((res) => {
        this.apiNum = res.data.total;
      });
    },
    go_api_add() {
      this.$router.push({ path: "/api/add" });
    },
    go_app_add() {
      this.$router.push({ path: "/app/add" });
    },
    go_data_Statistics() {
      this.$router.push({ path: "/data/Statistics" });
    },
    go_upstream_detail() {
      this.$router.push({path:"/upstream/detail"})
    }
  },
};
</script>
<style lang='scss' scoped>
.box {
  width: 100%;
  height: 650px;
  margin-bottom: 20px;
  background-color: #fff;
  .box-top {
    width: 100%;
    height: 50px;
    border-bottom: 1px solid #e9e9e9;
    display: flex;
    div {
      width: 50%;
    }
    .top_left {
      line-height: 50px;
      padding-left: 24px;
      font-weight: 700;
    }
    .top_right {
      text-align: right;
      padding-right: 24px;
      line-height: 50px;
    }
  }
  .box-chart {
    height: 600px;
    width: 100%;
  }
}
.dashboard-container {
  // margin-top: 20px;
  padding: 20px;
  .introduction {
    width: 100%;
    margin-bottom: 20px;
    padding-bottom: 20px;
    background-color: #fff;
    .introduction_title {
      height: 40px;
      line-height: 40px;
      padding-left: 20px;
    }
    .line {
      width: 100%;
      height: 1px;
      border-bottom: 1px solid #e9e9e9;
    }
    .introduction_photo {
      // height: 20px;
      margin: 15px 0px;
      width: 100%;
      display: flex;
      justify-content: center;
      padding: 0px 20px;
      div {
        border: 1px dashed #000;
        width: 25%;
        text-align: center;
      }
    }
    .introduction_steps {
      padding: 0 20px;
      width: 100%;
      display: flex;
      justify-content: center;
      div {
        width: 25%;
        text-align: center;
        position: relative;
        span {
          font-size: 14px;
        }
        .steps_line_right {
          width: 25%;
          height: 2px;
          background-color: #0058ea;
          position: absolute;
          top: 8px;
          right: 0px;
        }
        .steps_line_left {
          width: 25%;
          height: 2px;
          background-color: #0058ea;
          position: absolute;
          top: 8px;
          left: 0px;
        }
      }
    }
    .introduction_content {
      padding: 0 20px;
      width: 100%;
      display: flex;
      justify-content: center;
      margin-top: 20px;
      div {
        width: 25%;
        text-align: center;
        padding: 0px 20px;
        font-size: 12px;
        color: #7f7f7f;
      }
    }
  }
  .statistics {
    width: 100%;
    margin-bottom: 20px;
    padding-bottom: 20px;
    background-color: #fff;
    .statistics_title {
      height: 40px;
      line-height: 40px;
      padding-left: 20px;
    }
    .line {
      width: 100%;
      height: 1px;
      border-bottom: 1px solid #e9e9e9;
    }
    .statistics_numbers {
      display: flex;
      margin: 15px 0px;
      padding: 0px 20px;
      justify-content: space-evenly;
      div {
        width: 30%;
        height: 103px;
        background-color: #f8fafe;
        p {
          font-size: 12px;
          text-align: center;
          color: #333333;
        }
        .content {
          height: 50px;
          width: 100%;
          line-height: 50px;
          text-align: center;
          color: #333333;
          font-size: 18px;
        }
      }
    }
  }
  .commonUse {
    width: 100%;
    margin-bottom: 20px;
    padding-bottom: 20px;
    background-color: #fff;
    .commonUse_title {
      height: 40px;
      line-height: 40px;
      padding-left: 20px;
    }
    .line {
      width: 100%;
      height: 1px;
      border-bottom: 1px solid #e9e9e9;
    }
    .commonUse_content {
      padding: 20px;
      div {
        height: 122px;
        margin-bottom: 20px;
        width: 100%;
        background-color: #f8fafe;
        padding-left: 20px;
        overflow: hidden;
        cursor: pointer;
        div {
          margin-top: 39px;
          img {
            // margin-top: 39px;
            vertical-align: middle;
          }
          span {
            vertical-align: middle;
          }
        }
      }
    }
  }
}
</style>
