<template>
  <div class="elx-steps-horizontal">
    <div v-for="(item,index) in abstracts" class="info" :key="index">
      <div class="info-content">
        <div  v-if="index < value" class="square squarefinish" @click="btnClick( index )">{{ index + 1 }}</div>
        <div  v-else-if="index == value" class="square squareactive" @click="btnClick( index )">{{ index + 1 }}</div>
        <div  v-else class="square" @click="btnClick( index )">{{ index + 1 }}</div>
        <div class="info-text-groups">
          <span v-if="index == value" class="text-active"></span>
          <span v-if="index > value" class="text-active"></span>
          <span v-if="index < value" class="text-active"></span>
          <div class="sline" v-show="index !=  ( abstracts.length - 1 )" :class="index < value?'active-line':''"></div>
        </div>
      </div>
      <div class="title">
        <div :class="['info-text',index < value?'text-active':'']" @click="btnClick( index )">{{ item }}</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ElxStepsHorizontal',
  components: {},
  props: {
    value: {
      default: 0,
      type: Number,
      required: true
    },
    abstracts: {
      // default: [],
      type: Array,
      required: true
    }
  },
  data() {
    return {}
  },
  computed: {},
  mounted() {
    console.log('abstracts', this.abstracts)
  },
  methods: {
    // 中英文字符长度计算
    fnGetencnlength(str) {
      let sLen = 0
      // let strs = str;
      try {
        // 先将回车换行符做特殊处理
        // strs = str.replace(/(\r\n+|\s+|+)/g, '龘');
        // 处理英文字符数字，连续字母、数字、英文符号视为一个单词
        // strs = str.replace(/[\x00-\xff]/g, 'm');
        // 合并字符m，连续字母、数字、英文符号视为一个单词
        // str = str.replace(/m+/g,"*");
        // 去掉回车换行符
        // strs = str.replace(/龘+/g, '');
        // 返回字数
        // sLen = str.length;
        for (let i = 0; i < str.length; ++i) {
          const charCode = str.charCodeAt(i)
          if (charCode >= 0 && charCode <= 128) {
            sLen += 1
          } else {
            sLen += 2
          }
        }
        return sLen
      } catch (e) {
        console.log('e', e)
      }
      return sLen
    },
    btnClick(index) {
      this.$emit('change', index)
    }
  },
  watch: {}
}
</script>

<style rel="stylesheet/scss" lang="scss">
.elx-steps-horizontal {
  width: 750px;
  padding-left: 30px;
  min-width: 400px;
  // height: 60px;
  position: relative;
  overflow: hidden;

  display: flex;
  flex-flow: row nowrap;
  justify-content: space-around;
  align-items: center;
  .info {
    width:100%;
    display: flex;
    flex-direction: column;
    .title {
      padding-left: 20px;
      .info-text{
        margin-top: 24px;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
        margin-left: -35px !important;
        cursor: pointer;
        height: 20px;
        font-size: 14px;
        font-family: Microsoft YaHei UI-Regular, Microsoft YaHei UI;
        font-weight: 400;
        color: #1D1C35;
        line-height: 20px;
        margin-top: 16px;
    }
    }
    .info-content{
      width:100%;
      height: 40px;
      padding: 2px;
      display: flex;
      flex-flow: row nowrap;
      justify-content: flex-start;
      align-items: center;
      position: relative;
      overflow: hidden;
      text-overflow: ellipsis;
      .square{
        // border: 1px solid $--border-color-base;
        // background-color: $--color-white;
        color: #fff;
        background: #93A8FF;
        border: 8px solid #fff;
        text-align: center;
        line-height: 18px;
        font-weight: bold;
        border-radius: 50%;
        width: 40px;
        height: 40px;
        min-width: 40px;
        padding-top: 3px;
        font-size: 12px;
        text-align: center;
        cursor: pointer;
    }
    .squareactive {
      // background-color: $--color-primary;
      // color: $--color-white;
      color: #fff;
      background: #4461D7;
      border: 8px solid #E5EBFE;
      text-align: center;
      line-height: 18px;
      font-weight: bold;
      border-radius: 50%;
    }
    .squarefinish {
      color: #fff;
      background: #4461D7;
      border: 8px solid #fff;
      text-align: center;
      line-height: 18px;
      font-weight: bold;
      border-radius: 50%;
      // background-color: $--color-white;
      // color: $--color-primary;
      // border: 1px solid $--color-primary;
    }
    .info-text-groups{
      display: flex;
      flex-flow: row nowrap;
      justify-content: flex-start;
      align-items: center;
      width: 100%;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
      .sline {
        border-top: 1px solid rgba(0,0,0,0.1);
        width: calc(100%);
    }
    .active-line {
      // border-color: $--color-primary;
      border-color:#E1E6EE;
    }
      .text-active {
        height: 1px;
      }
      .info-active{
        padding-top: 16px;
        padding-left: 3px;
        display: flex;
        flex-direction: column;
      }
    }

    }

  }
}
</style>
