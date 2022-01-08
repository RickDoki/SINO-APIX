<template>
  <div class="main">
    <div class="list_top">
      <div class="list_title">API名称</div>
      <div class="list_search">
        <div class="but-left">
          <el-dropdown>
            <el-button type="primary" size="small">
              操作<i class="el-icon-arrow-down el-icon--conten-color"></i>
            </el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>黄金糕</el-dropdown-item>
              <el-dropdown-item>狮子头</el-dropdown-item>
              <el-dropdown-item>螺蛳粉</el-dropdown-item>
              <el-dropdown-item>双皮奶</el-dropdown-item>
              <el-dropdown-item>蚵仔煎</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
        <el-button type="primary" size="small">编辑文档</el-button>
      </div>
    </div>
    <div class="secondTitle">
      这是一段API描述
    </div>
    <div class="status">
      <div class="left-span">
        <span>使用状态: </span>
        <span>停用</span>
      </div>
      <div class="left-span">
        <span>门户状态: </span>
        <span>已发布</span>
      </div>
      <div class="time">
        <div>
          <span>创建时间 : </span>
          <span>2021-08-05 10:05:00:00</span>
        </div>
        <div>
          <span>更新时间 : </span>
          <span>2021-08-05 10:05:00:00</span>
        </div>
      </div>
    </div>
    <div class="apiMessage">
      <div class="api-info">
        <span class="label-color">域名 : </span>
        <span class="conten-color">https://www.baidu.com</span> <i class="el-icon-copy-document icon-color"/>
      </div>
      <div class="api-info">
        <span class="label-color">路径 : </span>
        <span class="conten-color">/api/serve</span> <i class="el-icon-copy-document icon-color"/>
      </div>
      <div class="api-info">
        <span class="label-color agrement">协议 : </span>
        <span class="conten-color">https</span>
      </div>
      <div class="api-info">
        <span class="label-color">请求方式 : </span>
        <span class="conten-color">GET</span>
      </div>
      <div class="api-info">
        <span class="label-color">返回类型 : </span>
        <span class="conten-color">JSON</span>
      </div>
      <div class="api-info">
        <span class="label-color">请求参数 : </span>
        <div class="table_box table_top">
          <el-table
            :data="table"
            empty-text="暂无数据"
            :row-style="{ height: '50px' }"
            highlight-current-row
            :header-cell-style="{ 'font-weight': 400, color: '#494E6A' }"
          >
            <el-table-column prop="appName" label="名称" />
            <el-table-column prop="appCode" label="类型" />
            <el-table-column prop="appCode" label="是否必选" />
            <el-table-column prop="appCode" label="描述" />
            <el-table-column prop="appCode" label="默认值" />
          </el-table>
        </div>
      </div>
      <div class="api-info">
        <span class="label-color">状态码 : </span>
        <div class="table_box table_top">
          <el-table
            :data="table"
            empty-text="暂无数据"
            :row-style="{ height: '50px' }"
            highlight-current-row
            :header-cell-style="{ 'font-weight': 400, color: '#494E6A' }"
          >
            <el-table-column prop="appName" label="状态码" width="200"/>
            <el-table-column prop="appCode" label="描述" />
          </el-table>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import "./../mainCss/index.scss";
export default {
  data () {
    return {
    };
  },
  created () {
    console.log(this.$route);
  },
};
</script>

<style lang='scss' scoped>
</style>