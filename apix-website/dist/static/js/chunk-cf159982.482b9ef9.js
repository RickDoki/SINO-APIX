(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-cf159982"],{"6628c":function(t,e,n){},a1ad:function(t,e,n){"use strict";n.d(e,"u",(function(){return a})),n.d(e,"g",(function(){return u})),n.d(e,"v",(function(){return i})),n.d(e,"s",(function(){return o})),n.d(e,"t",(function(){return c})),n.d(e,"d",(function(){return s})),n.d(e,"b",(function(){return l})),n.d(e,"n",(function(){return p})),n.d(e,"h",(function(){return d})),n.d(e,"a",(function(){return f})),n.d(e,"p",(function(){return h})),n.d(e,"e",(function(){return b})),n.d(e,"x",(function(){return g})),n.d(e,"k",(function(){return m})),n.d(e,"w",(function(){return v})),n.d(e,"c",(function(){return j})),n.d(e,"m",(function(){return O})),n.d(e,"q",(function(){return _})),n.d(e,"r",(function(){return C})),n.d(e,"o",(function(){return w})),n.d(e,"j",(function(){return k})),n.d(e,"i",(function(){return y})),n.d(e,"l",(function(){return S})),n.d(e,"f",(function(){return x}));var r=n("b775");function a(t){return Object(r["a"])({url:"/app/list?"+t,method:"get"})}function u(t){return Object(r["a"])({url:"/app/create",method:"post",data:t})}function i(t,e){return Object(r["a"])({url:"/app/"+t,method:"put",data:e})}function o(t){return Object(r["a"])({url:"/app/"+t,method:"delete"})}function c(t){return Object(r["a"])({url:"/app/"+t,method:"get"})}function s(t){return Object(r["a"])({url:"/app/data/"+t+"/appNum",method:"post"})}function l(t){return Object(r["a"])({url:"/app/api/list?developerId="+t,method:"get"})}function p(t,e){return Object(r["a"])({url:"/app/"+t+"/publish",method:"post",data:e})}function d(t){return Object(r["a"])({url:"/app/appVersion/"+t,method:"delete"})}function f(t){return Object(r["a"])({url:"/app/subscribed/list?"+t,method:"get"})}function h(t){return Object(r["a"])({url:"/app/api/queryApiList",method:"post",data:t})}function b(t,e){return Object(r["a"])({url:"/app/appVersion/"+t,method:"post",data:e})}function g(t){return Object(r["a"])({url:"/app/appVersion/"+t,method:"get"})}function m(t){return Object(r["a"])({url:"/gateway/log?"+t,method:"get"})}function v(t){return Object(r["a"])({url:"/app/subscribed/"+t,method:"get"})}function j(t){return Object(r["a"])({url:"/app/api/"+t,method:"get"})}function O(t){return Object(r["a"])({url:"/app/plugin",method:"post",data:t})}function _(){return Object(r["a"])({url:"/app/plugin/randomKey",method:"get"})}function C(t){return Object(r["a"])({url:"/app/rateLimit/save",method:"post",data:t})}function w(t){return Object(r["a"])({url:"/app/plugin",method:"put",data:t})}function k(t){return Object(r["a"])({url:"/app/unSubscribe/"+t,method:"post"})}function y(t,e){return Object(r["a"])({url:"/app/plugin/"+t+"/"+e,method:"get"})}function S(t){return Object(r["a"])({url:"/app/rateLimit/open?appId="+t,method:"post"})}function x(t){return Object(r["a"])({url:"/app/rateLimit/close?appId="+t,method:"post"})}},b53b:function(t,e,n){"use strict";n("6628c")},d981:function(t,e,n){"use strict";n.r(e);var r=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"main"},[n("div",{staticClass:"list_top"},[n("div",{staticClass:"list_title"},[t._v("服务中心")]),n("div",{staticClass:"list_search"},[n("el-input",{staticClass:"list_searchInput",attrs:{size:"small",placeholder:"搜索","suffix-icon":"el-icon-search"},on:{input:t.nameSerach},model:{value:t.name,callback:function(e){t.name=e},expression:"name"}}),n("el-button",{attrs:{type:"primary",size:"small",icon:"el-icon-plus"},on:{click:t.goCreatdServe}},[t._v("添加新服务")])],1)]),n("div",{staticClass:"secondTitle list_top_bom"},[t._v(" 创建服务来管理和代理现有API或发布到门户。 ")]),n("div",{staticClass:"table_box"},[n("el-table",{directives:[{name:"loading",rawName:"v-loading",value:t.loading,expression:"loading"}],attrs:{data:t.table,"empty-text":"暂无数据","row-style":{height:"50px"},"highlight-current-row":"","header-cell-style":{"font-weight":400,"font-size":"16px",color:"#1D1C35"}}},[n("el-table-column",{attrs:{prop:"appName",label:"服务名称"},scopedSlots:t._u([{key:"default",fn:function(e){return[n("span",{staticClass:"linkcolor",on:{click:function(n){return t.goserveDteail(e)}}},[t._v(t._s(e.row.appName))])]}}])}),n("el-table-column",{attrs:{prop:"appCode",label:"code"}}),n("el-table-column",{attrs:{label:"启用状态"},scopedSlots:t._u([{key:"default",fn:function(e){return["60005"===e.row.isPublished?n("div",{staticClass:"tag success"},[t._v(" 已发布 ")]):n("div",{staticClass:"tag info"},[t._v("未发布")])]}}])}),n("el-table-column",{attrs:{"min-width":"250px",label:"版本"},scopedSlots:t._u([{key:"default",fn:function(e){return t._l(e.row.appVersions,(function(e,r){return n("div",{key:r,staticClass:"version"},[t._v(" "+t._s(e)+" ")])}))}}])}),n("el-table-column",{attrs:{label:"操作",width:"180px"},scopedSlots:t._u([{key:"default",fn:function(e){return["60005"===e.row.isPublished?n("div",[n("el-button",{staticStyle:{color:"#f03063"},attrs:{type:"text"},on:{click:function(n){return t.undercarriage(e.row)}}},[t._v("下架")])],1):n("div",[n("el-button",{attrs:{type:"text"},on:{click:function(n){return t.Published(e.row)}}},[t._v("发布到门户")]),n("span",{staticClass:"handle"},[t._v("|")]),n("el-button",{attrs:{type:"text"},on:{click:function(n){return t.del(e.row)}}},[t._v("删除")])],1)]}}])})],1),n("el-pagination",{staticClass:"list-pagination",attrs:{background:"","current-page":t.currentPage,layout:"prev, pager, next",total:t.total},on:{"update:currentPage":function(e){t.currentPage=e},"update:current-page":function(e){t.currentPage=e},"current-change":t.handleCurrentChange}})],1)])},a=[],u=(n("b0c0"),n("fcb4"),n("a1ad")),i=n("5f87"),o={data:function(){return{table:[{appName:"我的服务"}],total:100,currentPage:1,name:"",developerId:"",loading:!1}},created:function(){this.developerId=Object(i["a"])("userId_api"),this.getServeList()},methods:{getServeList:function(){var t=this;this.loading=!0;var e="developerId="+this.developerId+"&limit=10&offset="+this.currentPage+"&appName="+this.name+"&market=false";Object(u["u"])(e).then((function(e){200===e.code&&(t.loading=!1,t.table=e.data.appList,t.total=e.data.total)}))},handleCurrentChange:function(t){this.currentPage=t,this.getServeList()},goserveDteail:function(t){this.$router.push({path:"/serve/serveDetail/"+t.row.appCode})},goCreatdServe:function(){this.$router.push({path:"/serve/create"})},nameSerach:function(){this.currentPage=1,this.getServeList()},undercarriage:function(t){var e=this,n={isPublished:"60001"};Object(u["v"])(t.appCode,n).then((function(t){200===t.code&&e.getServeList()}))},Published:function(t){var e=this,n={isPublished:"60005"};Object(u["v"])(t.appCode,n).then((function(t){200===t.code&&e.getServeList()}))},del:function(t){var e=this;Object(u["s"])(t.appCode).then((function(t){200===t.code&&e.getServeList()}))}}},c=o,s=(n("b53b"),n("2877")),l=Object(s["a"])(c,r,a,!1,null,"1e1a7688",null);e["default"]=l.exports},fcb4:function(t,e,n){}}]);