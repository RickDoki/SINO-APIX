(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-faf7a370"],{"11ca":function(t,e,n){},"1b2e":function(t,e,n){"use strict";n("11ca")},"49d7":function(t,e,n){"use strict";n.r(e);var a=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"main"},[n("div",{staticClass:"list_top"},[n("div",{staticClass:"list_title"},[t._v(t._s(t.docsName)+" - "+t._s(t.pageTitle))])]),t._m(0),n("mavon-editor",{directives:[{name:"show",rawName:"v-show",value:t.viewFlag,expression:"viewFlag"}],ref:"md",staticClass:"sub-content",staticStyle:{padding:"25px"},attrs:{defaultOpen:"preview",subfield:!1,toolbarsFlag:!1},domProps:{innerHTML:t._s(t.html)}}),n("mavon-editor",{directives:[{name:"show",rawName:"v-show",value:!t.viewFlag,expression:"!viewFlag"}],ref:"md",staticClass:"sub-content",attrs:{toolbars:t.toolbars},on:{change:t.change},model:{value:t.content,callback:function(e){t.content=e},expression:"content"}}),n("div",{staticClass:"sub-div"},[n("el-button",{directives:[{name:"show",rawName:"v-show",value:t.viewFlag,expression:"viewFlag"}],staticClass:"sub-but right_bottom",attrs:{type:"primary"},on:{click:t.edit}},[t._v("编辑")]),n("el-button",{directives:[{name:"show",rawName:"v-show",value:!t.viewFlag,expression:"!viewFlag"}],staticClass:"sub-but right_bottom_a",on:{click:t.cancel}},[t._v("取消")]),n("el-button",{directives:[{name:"show",rawName:"v-show",value:!t.viewFlag,expression:"!viewFlag"}],staticClass:"sub-but right_bottom",attrs:{type:"primary"},on:{click:t.updateDocs}},[t._v("提交")])],1)],1)},r=[function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",[n("span",{staticClass:"secondTitle"},[t._v("用户自定义文档信息。")])])}],i=(n("b0c0"),n("b2d8")),o=(n("64e1"),n("b2cc")),c=n("a1ad"),u={components:{mavonEditor:i["mavonEditor"]},data:function(){return{type:"",docsId:"",pageTitle:"",viewFlag:!1,doscInfo:{},content:"",html:"",platName:"dev",toolbars:{bold:!0,italic:!0,header:!0,underline:!0,strikethrough:!0,mark:!0,superscript:!0,subscript:!0,quote:!0,ol:!0,ul:!0,link:!0,imagelink:!1,code:!0,table:!0,fullscreen:!0,readmodel:!0,htmlcode:!0,help:!0,undo:!0,redo:!0,trash:!0,save:!0,navigation:!0,alignleft:!0,aligncenter:!0,alignright:!0,subfield:!0,preview:!0}}},created:function(){switch(this.tyep=this.$route.params.type,this.tyep){case"api":this.pageTitle="API文档";break;case"version":this.pageTitle="版本文档";break;case"serve":this.pageTitle="服务文档";break}this.docsId=this.$route.query.id,this.docsName=this.$route.query.name,console.log(this.$route),this.getDetail()},methods:{cancel:function(){this.viewFlag=!0,this.getDetail()},getDetail:function(){var t=this;switch(this.tyep){case"api":Object(o["c"])(this.docsId).then((function(e){200===e.code&&(t.doscInfo=e.data,t.content=e.data.markdown)}));break;case"version":Object(c["x"])(this.docsId).then((function(e){200===e.code&&(t.doscInfo=e.data.applicationVersion,t.content=e.data.applicationVersion.markdown)}));break;case"serve":Object(c["t"])(this.docsId).then((function(e){200===e.code&&(t.doscInfo=e.data,t.content=e.data.appMarkdown)}));break}},change:function(t,e){this.html=e},edit:function(){this.viewFlag=!1},updateDocs:function(){var t=this;"serve"==this.type?this.doscInfo.appMarkdown=this.content:this.doscInfo.markdown=this.content;var e={markdown:this.content};switch(this.tyep){case"api":Object(o["e"])(this.docsId,e).then((function(e){200===e.code&&(t.$message(e.msg),t.getDetail(),t.viewFlag=!0)}));break;case"version":var n={markdown:this.content,appCode:this.doscInfo.appCode};Object(c["e"])(this.docsId,n).then((function(e){200===e.code&&(t.$message(e.msg),t.getDetail(),t.viewFlag=!0)}));break;case"serve":Object(c["v"])(this.docsId,e).then((function(e){200===e.code&&(t.$message(e.msg),t.getDetail(),t.viewFlag=!0)}));break}}}},s=u,d=(n("1b2e"),n("2877")),p=Object(d["a"])(s,a,r,!1,null,"272d5bc7",null);e["default"]=p.exports},a1ad:function(t,e,n){"use strict";n.d(e,"u",(function(){return r})),n.d(e,"g",(function(){return i})),n.d(e,"v",(function(){return o})),n.d(e,"s",(function(){return c})),n.d(e,"t",(function(){return u})),n.d(e,"d",(function(){return s})),n.d(e,"b",(function(){return d})),n.d(e,"n",(function(){return p})),n.d(e,"h",(function(){return l})),n.d(e,"a",(function(){return h})),n.d(e,"p",(function(){return f})),n.d(e,"e",(function(){return b})),n.d(e,"x",(function(){return m})),n.d(e,"k",(function(){return v})),n.d(e,"w",(function(){return g})),n.d(e,"c",(function(){return w})),n.d(e,"m",(function(){return j})),n.d(e,"q",(function(){return O})),n.d(e,"r",(function(){return k})),n.d(e,"o",(function(){return I})),n.d(e,"j",(function(){return _})),n.d(e,"i",(function(){return y})),n.d(e,"l",(function(){return F})),n.d(e,"f",(function(){return C}));var a=n("b775");function r(t){return Object(a["a"])({url:"/app/list?"+t,method:"get"})}function i(t){return Object(a["a"])({url:"/app/create",method:"post",data:t})}function o(t,e){return Object(a["a"])({url:"/app/"+t,method:"put",data:e})}function c(t){return Object(a["a"])({url:"/app/"+t,method:"delete"})}function u(t){return Object(a["a"])({url:"/app/"+t,method:"get"})}function s(t){return Object(a["a"])({url:"/app/data/"+t+"/appNum",method:"post"})}function d(t){return Object(a["a"])({url:"/app/api/list?developerId="+t,method:"get"})}function p(t,e){return Object(a["a"])({url:"/app/"+t+"/publish",method:"post",data:e})}function l(t){return Object(a["a"])({url:"/app/appVersion/"+t,method:"delete"})}function h(t){return Object(a["a"])({url:"/app/subscribed/list?"+t,method:"get"})}function f(t){return Object(a["a"])({url:"/app/api/queryApiList",method:"post",data:t})}function b(t,e){return Object(a["a"])({url:"/app/appVersion/"+t,method:"post",data:e})}function m(t){return Object(a["a"])({url:"/app/appVersion/"+t,method:"get"})}function v(t){return Object(a["a"])({url:"/gateway/log?"+t,method:"get"})}function g(t){return Object(a["a"])({url:"/app/subscribed/"+t,method:"get"})}function w(t){return Object(a["a"])({url:"/app/api/"+t,method:"get"})}function j(t){return Object(a["a"])({url:"/app/plugin",method:"post",data:t})}function O(){return Object(a["a"])({url:"/app/plugin/randomKey",method:"get"})}function k(t){return Object(a["a"])({url:"/app/rateLimit/save",method:"post",data:t})}function I(t){return Object(a["a"])({url:"/app/plugin",method:"put",data:t})}function _(t){return Object(a["a"])({url:"/app/unSubscribe/"+t,method:"post"})}function y(t,e){return Object(a["a"])({url:"/app/plugin/"+t+"/"+e,method:"get"})}function F(t){return Object(a["a"])({url:"/app/rateLimit/open?appId="+t,method:"post"})}function C(t){return Object(a["a"])({url:"/app/rateLimit/close?appId="+t,method:"post"})}},b2cc:function(t,e,n){"use strict";n.d(e,"a",(function(){return r})),n.d(e,"f",(function(){return i})),n.d(e,"c",(function(){return o})),n.d(e,"d",(function(){return c})),n.d(e,"b",(function(){return u})),n.d(e,"e",(function(){return s}));var a=n("b775");function r(t){return Object(a["a"])({url:"/app/api/create",method:"post",data:t})}function i(t){return Object(a["a"])({url:"/app/api/list?"+t,method:"get"})}function o(t){return Object(a["a"])({url:"/app/api/"+t,method:"get"})}function c(t){return Object(a["a"])({url:"/app/data/"+t+"/apiNum",method:"get"})}function u(t){return Object(a["a"])({url:"/app/api/"+t,method:"delete"})}function s(t,e){return Object(a["a"])({url:"/app/api/"+t,method:"put",data:e})}}}]);