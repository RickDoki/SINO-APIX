<template>
  <div :id="id" :style="{'width':'100%','height':'100%'}">
  </div>
</template>

<script>
import Guid from "./guid";
import echarts from "echarts";
import 'echarts/map/js/world.js'
export default {
  data() {
    return {
      id: '',
      echarts: null
    }
  },
  props: ['echartData', 'guid'],
  created: function() {
    this.id = Guid.methods.getGuid()
  },
  mounted: function() {
    setTimeout(() => {
      this.echarts = echarts.init(document.getElementById(this.id + ''))
      this.initEchart()
    }, 100)
     
  },
  methods: {
    initEchart: function() {
      if (!this.echartData || !this.echarts) {
        return
      }
      let option = {
        title: this.echartData.title,
        xAxis: this.echartData.xAxis,
        tooltip: this.echartData.tooltip,
        legend: this.echartData.legend,
        grid: this.echartData.grid,
        yAxis: this.echartData.yAxis,
        series: this.echartData.series,
        geo:this.echartData.geo,
        backgroundColor:this.echartData.backgroundColor,
        radar:this.echartData.radar,
        polar: this.echartData.polar,
        angleAxis: this.echartData.angleAxis,
        radiusAxis: this.echartData.radiusAxis,
        animationEasing: "elasticOut",
        dataZoom: this.echartData.dataZoom
      };
      this.echarts.setOption(option,true);
      this.echarts.off('click');
      this.echarts.on("click", params => {
        this.$emit("curNodeClick", params);
      });
      window.addEventListener("resize", () => {
        this.echarts.resize();
      });
      this.echarts.setOption(option,true);
      this.echarts.on('click',(params)=>{
          this.$emit('getDatas',params)
      })


  this.echarts.on("mouseover", (params)=>{
    let isSelectedDot = params.event.target.__dimIdx;
        this.$emit('radarindex',isSelectedDot)
    //做鼠标滑过拐点的操作
});


    }
  },
  watch: {
    guid: function() {
      // this.initEchart();
      setTimeout(() => {
        this.initEchart()
      }, 1000)
    },
    echartData: {
      handler(newValue, oldValue) {
        this.initEchart()
      },
      deep: true
    },
    width: () => {
      // this.echarts.resize();
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
