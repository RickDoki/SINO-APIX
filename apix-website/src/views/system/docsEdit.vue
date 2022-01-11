<template>
  <div class="main">
    <div class="list_top">
      <div class="list_title">{{docsName}} - {{pageTitle}}</div>
    </div>
    <div>
      <span class="secondTitle">用户自定义文档信息。</span>
    </div>
    <!-- 预览 -->
    <mavon-editor
      v-show="viewFlag"
      v-html="html" 
      ref="md"  
      class="sub-content"
      style="padding: 25px;"
      defaultOpen= "preview"
      :subfield= false
      :toolbarsFlag= false
    />
    <!-- 编辑 -->
    <mavon-editor 
      v-show="!viewFlag"
      v-model="content" 
      ref="md"  
      class="sub-content"
      @change="change" 
      :toolbars="toolbars"
    />
      <!-- @imgAdd="$imgAdd" -->
    <div class="sub-div">
      <el-button v-show="viewFlag" class="sub-but right_bottom" type="primary" @click="edit">编辑</el-button>
      <el-button v-show="!viewFlag" class="sub-but right_bottom_a" @click="cancel">取消</el-button>
      <el-button v-show="!viewFlag" class="sub-but right_bottom" type="primary" @click="updatePlatformMarkdown">提交</el-button>
    </div>
  </div>
</template>

<style lang="less" scoped>
.v-note-wrapper {
  z-index: 10;
  margin-top: 24px;
}
.sub-div {
  text-align: right;
}
.sub-content {
  min-height: 635px;
}
.sub-but {
  margin-top: 20px;
  overflow: hidden;
  z-index: 9999;
  position: fixed;
}
.right_bottom_a {
  right: 125px;
  bottom: 85px;
}
.right_bottom {
  right: 45px;
  bottom: 85px;
}
</style>


<script>
// 导入组件 及 组件样式
import { mavonEditor } from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
export default {
  // 注册
  components: {
    mavonEditor,
  },
  data () {
    return {
      docsId: '',
      pageTitle: '',
      viewFlag: true,
      content: '', // 输入的markdown
      html: '',    // 及时转的html
      platName: 'dev',  //用户平台
      toolbars: {
        bold: true, // 粗体
        italic: true, // 斜体
        header: true, // 标题
        underline: true, // 下划线
        strikethrough: true, // 中划线
        mark: true, // 标记
        superscript: true, // 上角标
        subscript: true, // 下角标
        quote: true, // 引用
        ol: true, // 有序列表
        ul: true, // 无序列表
        link: true, // 链接
        imagelink: false, // 图片链接
        code: true, // code
        table: true, // 表格
        fullscreen: true, // 全屏编辑
        readmodel: true, // 沉浸式阅读
        htmlcode: true, // 展示html源码
        help: true, // 帮助
        /* 1.3.5 */
        undo: true, // 上一步
        redo: true, // 下一步
        trash: true, // 清空
        save: true, // 保存（触发events中的save事件）
        /* 1.4.2 */
        navigation: true, // 导航目录
        /* 2.1.8 */
        alignleft: true, // 左对齐
        aligncenter: true, // 居中
        alignright: true, // 右对齐
        /* 2.2.1 */
        subfield: true, // 单双栏模式
        preview: true, // 预览
      }
    }
  },
  created () {
    switch (this.$route.params.type) {
      case 'api':
        this.pageTitle = 'API文档'
        break;
      case 'version':
        this.pageTitle = '版本文档'
        break;
      case 'serve':
        this.pageTitle = '服务文档'
        break;
    }
    this.docsId = this.$route.query.id
    this.docsName = this.$route.query.name
    console.log(this.$route)
    // this.getPlatformMarkdown();
  },
  methods: {
    /**
    * 取消编辑
    */
    cancel () {
      this.viewFlag = true;
      // this.getPlatformMarkdown();
    },
    /**
     * 获取用户手册详情
     */
    // getPlatformMarkdown () {
    //   this.$http.request(this.$baseUrl.getPlatformMarkdown.url + this.platName, null, this.$baseUrl.getPlatformMarkdown.method).then((res) => {
    //     if (res.code == 200) {
    //       this.content = res.data.markdown;
    //     } else {
    //       this.html = '';
    //     }
    //   }).catch(() => {
    //     this.html = '';
    //   });
    // },
    // 所有操作都会被解析重新渲染
    change (value, render) {
      // render 为 markdown 解析后的结果[html]
      this.html = render;
    },
    //编辑
    edit () {
      this.viewFlag = false;
    },
    //markowdn文件上传
    // $imgAdd (pos, $file) {
    //   if ($file.size > 10 * 1024 * 1024) {
    //     this.$message("文件大小不能超过10M");
    //     return;
    //   }
    //   var formdata = new FormData();
    //   formdata.append("file", $file);
    //   let url = this.$baseUrl.markdownUpload.url
    //   let method = this.$baseUrl.markdownUpload.method;
    //   this.$http.request(url, formdata, method)
    //     .then(res => {
    //       if (res.code == 200) {
    //         this.$refs.md.$img2Url(pos, res.data);
    //       } else {
    //         this.$message(res.msg);
    //       }
    //     })
    //     .catch((error) => {
    //       this.$message.error(error.msg);
    //     });
    // },
    // 提交
    updatePlatformMarkdown () {
      let params = {
        platName: this.platName,
        markdown: this.content
      }
      let url = this.$baseUrl.updatePlatformMarkdown.url + this.platName;
      let method = this.$baseUrl.updatePlatformMarkdown.method;
      this.$http.request(url, params, method)
        .then(res => {
          if (res.code == 200) {
            this.$message(res.msg);
            //提交好了展示预览页面
            this.getPlatformMarkdown();
            this.viewFlag = true;
          } else {
            this.$message(res.msg);
          }
        })
        .catch((error) => {
          this.$alert(error.response.data.msg, '提示', {
            confirmButtonText: '确定',
            type: 'warning',
            callback: action => {
            }
          });
        });
    }
  }
}
</script>