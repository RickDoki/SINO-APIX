<template>
  <div class="main">
    <div class="list_top">
      <div class="list_title">{{apiInfo.name}}</div>
      <div class="list_search">
        <div class="but-left">
          <el-dropdown>
            <el-button type="primary" size="small" style="width:85px">
              操作<i class="el-icon-arrow-down el-icon--conten-color"></i>
            </el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>编辑API</el-dropdown-item>
              <el-dropdown-item>删除API</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
        <el-button type="primary" size="small" style="width:85px">编辑文档</el-button>
      </div>
    </div>
    <div class="secondTitle">
      {{apiInfo.description || '-'}}
    </div>
    <div class="status">
      <div class="left-span">
        <span>被调用次数 : </span>
        <div class="tag info">18次</div>
      </div>
      <div class="left-span">
        <span>调用失败次数 : </span>
        <div class="tag info">3次</div>
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
        <span class="conten-color">{{apiInfo.domain}}</span> <i class="el-icon-copy-document icon-color"/>
      </div>
      <div class="api-info">
        <span class="label-color">路径 : </span>
        <span class="conten-color">{{apiInfo.url}}</span> <i class="el-icon-copy-document icon-color"/>
      </div>
      <div class="api-info">
        <span class="label-color">请求方式 : </span>
        <span class="conten-color">{{apiInfo.requestMethod}}</span>
      </div>
      <div class="api-info">
        <span class="label-color">返回类型 : </span>
        <span class="conten-color">{{apiInfo.requestMethod}}</span>
      </div>
      <div class="api-info">
        <span class="label-color">请求参数 : </span>
        <div class="table_box table_top">
          <el-table
            :data="paramsTable"
            empty-text="暂无数据"
            :row-style="{ height: '50px' }"
            highlight-current-row
            :header-cell-style="{ 'font-weight': 400, color: '#494E6A' }"
          >
            <el-table-column prop="parame" label="名称" />
            <el-table-column prop="type" label="类型" />
            <el-table-column prop="isHaveto" label="是否必选" />
            <el-table-column prop="describe" label="描述" />
            <el-table-column prop="default" label="默认值" />
          </el-table>
        </div>
      </div>
      <div class="api-info">
        <span class="label-color">状态码 : </span>
        <div class="table_box table_top">
          <el-table
            :data="statusTable"
            empty-text="暂无数据"
            :row-style="{ height: '50px' }"
            highlight-current-row
            :header-cell-style="{ 'font-weight': 400, color: '#494E6A' }"
          >
            <el-table-column prop="code" label="状态码" width="200"/>
            <el-table-column prop="desc" label="描述" />
          </el-table>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { detail } from "@/api/AboutApi";
import "./../mainCss/index.scss";
export default {
  data () {
    return {
      apiId: '',
      apiInfo: {},
      paramsTable: [],
      statusTable: [
        { code: '200', desc: '操作成功' }
      ]
    };
  },
  created () {
    console.log(this.$route);
    this.apiId = this.$route.params.id
    this.getDetail()
  },
  methods: {
    getDetail () {
      // 获取api详情
      detail(this.apiId).then((res) => {
        if (res.code === 200) {
          this.apiInfo = res.data
          this.paramsTable = JSON.parse(res.data.requestParams)
        }
      });
    }
  }
};
</script>

<style lang='scss' scoped>
</style>