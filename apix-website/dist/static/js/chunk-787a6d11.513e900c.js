(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-787a6d11"],{"03af":function(t,e,a){},"474b":function(t,e,a){"use strict";a("03af")},65663:function(t,e,a){"use strict";a("bfdc")},"6d07":function(t,e,a){},a1ad:function(t,e,a){"use strict";a.d(e,"u",(function(){return i})),a.d(e,"g",(function(){return s})),a.d(e,"v",(function(){return r})),a.d(e,"s",(function(){return o})),a.d(e,"t",(function(){return l})),a.d(e,"d",(function(){return u})),a.d(e,"b",(function(){return c})),a.d(e,"n",(function(){return p})),a.d(e,"h",(function(){return d})),a.d(e,"a",(function(){return g})),a.d(e,"p",(function(){return h})),a.d(e,"e",(function(){return f})),a.d(e,"x",(function(){return b})),a.d(e,"k",(function(){return v})),a.d(e,"w",(function(){return m})),a.d(e,"c",(function(){return _})),a.d(e,"m",(function(){return C})),a.d(e,"q",(function(){return w})),a.d(e,"r",(function(){return y})),a.d(e,"o",(function(){return L})),a.d(e,"j",(function(){return x})),a.d(e,"i",(function(){return j})),a.d(e,"l",(function(){return O})),a.d(e,"f",(function(){return k}));var n=a("b775");function i(t){return Object(n["a"])({url:"/app/list?"+t,method:"get"})}function s(t){return Object(n["a"])({url:"/app/create",method:"post",data:t})}function r(t,e){return Object(n["a"])({url:"/app/"+t,method:"put",data:e})}function o(t){return Object(n["a"])({url:"/app/"+t,method:"delete"})}function l(t){return Object(n["a"])({url:"/app/"+t,method:"get"})}function u(t){return Object(n["a"])({url:"/app/data/"+t+"/appNum",method:"post"})}function c(t){return Object(n["a"])({url:"/app/api/list?developerId="+t,method:"get"})}function p(t,e){return Object(n["a"])({url:"/app/"+t+"/publish",method:"post",data:e})}function d(t){return Object(n["a"])({url:"/app/appVersion/"+t,method:"delete"})}function g(t){return Object(n["a"])({url:"/app/subscribed/list?"+t,method:"get"})}function h(t){return Object(n["a"])({url:"/app/api/queryApiList",method:"post",data:t})}function f(t,e){return Object(n["a"])({url:"/app/appVersion/"+t,method:"post",data:e})}function b(t){return Object(n["a"])({url:"/app/appVersion/"+t,method:"get"})}function v(t){return Object(n["a"])({url:"/gateway/log?"+t,method:"get"})}function m(t){return Object(n["a"])({url:"/app/subscribed/"+t,method:"get"})}function _(t){return Object(n["a"])({url:"/app/api/"+t,method:"get"})}function C(t){return Object(n["a"])({url:"/app/plugin",method:"post",data:t})}function w(){return Object(n["a"])({url:"/app/plugin/randomKey",method:"get"})}function y(t){return Object(n["a"])({url:"/app/rateLimit/save",method:"post",data:t})}function L(t){return Object(n["a"])({url:"/app/plugin",method:"put",data:t})}function x(t){return Object(n["a"])({url:"/app/unSubscribe/"+t,method:"post"})}function j(t,e){return Object(n["a"])({url:"/app/plugin/"+t+"/"+e,method:"get"})}function O(t){return Object(n["a"])({url:"/app/rateLimit/open?appId="+t,method:"post"})}function k(t){return Object(n["a"])({url:"/app/rateLimit/close?appId="+t,method:"post"})}},bfdc:function(t,e,a){},d797:function(t,e,a){"use strict";a("6d07")},f77e:function(t,e,a){"use strict";a.r(e);var n=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"main"},[a("div",{staticClass:"he_height"},[a("div",{staticClass:"list_top"},[a("div",{staticClass:"list_title"},[t._v(t._s(t.serveAllMeaasge.appName))]),a("div",{staticClass:"list_search"},[a("el-button",{staticClass:"td-but",attrs:{type:"primary",size:"small"},on:{click:t.unSubscribe}},[t._v("退订")])],1)]),a("div",{staticClass:"secondTitle"},[t._v(t._s(t.serveAllMeaasge.appDescription))]),a("div",{staticClass:"status"},[t._m(0),a("div",{staticClass:"time"},[a("div",[a("span",[t._v("发布时间 : ")]),a("span",[t._v(t._s(t.serveAllMeaasge.appPublishDate))])]),a("div",[a("span",[t._v("订阅时间 : ")]),a("span",[t._v(t._s(t.serveAllMeaasge.subscribeDate))])])])]),a("div",{staticClass:"tabsMessage"},[a("el-tabs",{directives:[{name:"loading",rawName:"v-loading",value:t.loading,expression:"loading"}],on:{"tab-click":t.handleClick},model:{value:t.activeName,callback:function(e){t.activeName=e},expression:"activeName"}},[a("el-tab-pane",{attrs:{label:"API详情",name:"first"}},[a("api-detail",{attrs:{list:t.versionList,defaultApiList:t.apiList},on:{changeVersion:t.changeVersion}})],1),a("el-tab-pane",{attrs:{label:"插件详情",name:"second"}},[a("plug-in")],1)],1)],1)])])},i=[function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"left-span"},[a("span",[t._v("服务商 : ")]),t._v(" "),a("span",[t._v(" 博冀信息")])])}],s=(a("fcb4"),function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"he_main"},[a("el-row",{staticStyle:{height:"100%"}},[a("el-col",{staticStyle:{height:"100%"},attrs:{span:4}},[a("div",{staticClass:"apiList"},[a("el-select",{attrs:{size:"mini",placeholder:"请选择"},on:{change:t.apiValueChange},model:{value:t.apiValue,callback:function(e){t.apiValue=e},expression:"apiValue"}},t._l(t.list,(function(t){return a("el-option",{key:t.value,attrs:{label:t.label,value:t.value}})})),1),t._l(t.defaultApiList,(function(e,n){return a("p",{key:n,class:t.classList[n],on:{click:function(a){return t.choseApi(e,n)}}},[t._v(" "+t._s(e.apiName)+" ")])}))],2)]),a("el-col",{staticStyle:{height:"100%"},attrs:{span:20}},[a("div",{staticClass:"apiMessage"},[a("div",{staticClass:"api-info"},[a("div",{staticClass:"title"},[t._v(t._s(t.apiMessageAll.apiName))]),a("div",{staticClass:"secondTitle"},[t._v(t._s(t.apiMessageAll.description))])]),a("div",{staticClass:"api-info"},[a("span",{staticClass:"label-color"},[t._v("调用路径 : ")]),a("span",{staticClass:"conten-color"},[t._v(t._s(t.apiMessageAll.domain))]),a("i",{directives:[{name:"clipboard",rawName:"v-clipboard:copy",value:t.apiMessageAll.domain,expression:"apiMessageAll.domain",arg:"copy"},{name:"clipboard",rawName:"v-clipboard:success",value:t.onCopy,expression:"onCopy",arg:"success"},{name:"clipboard",rawName:"v-clipboard:error",value:t.onError,expression:"onError",arg:"error"}],staticClass:"el-icon-copy-document icon-color"})]),a("div",{staticClass:"api-info"},[a("span",{staticClass:"label-color agrement"},[t._v("协议类型 : ")]),a("span",{staticClass:"conten-color"},[t._v(t._s(t.apiMessageAll.protocol))])]),a("div",{staticClass:"api-info"},[a("span",{staticClass:"label-color"},[t._v("请求方式 : ")]),a("span",{staticClass:"conten-color"},[t._v(t._s(t.apiMessageAll.requestMethod))])]),a("div",{staticClass:"api-info"},[a("span",{staticClass:"label-color"},[t._v("请求参数 : ")]),a("div",{staticClass:"table_box table_top"},[a("el-table",{attrs:{data:t.table,"empty-text":"暂无数据","row-style":{height:"50px"},"highlight-current-row":"","header-cell-style":{"font-weight":400,color:"#494E6A"}}},[a("el-table-column",{attrs:{prop:"parame",label:"名称"}}),a("el-table-column",{attrs:{prop:"type",label:"类型"}}),a("el-table-column",{attrs:{prop:"isHaveto",label:"是否必选"}}),a("el-table-column",{attrs:{prop:"describe",label:"描述"}}),a("el-table-column",{attrs:{prop:"default",label:"默认值"}})],1)],1)]),a("div",{staticClass:"api-info"},[a("span",{staticClass:"label-color"},[t._v("状态码 : ")]),a("div",{staticClass:"table_box table_top"},[a("el-table",{attrs:{data:t.statusTable,"empty-text":"暂无数据","row-style":{height:"50px"},"highlight-current-row":"","header-cell-style":{"font-weight":400,color:"#494E6A"}}},[a("el-table-column",{attrs:{prop:"statusCode",label:"状态码",width:"200"}}),a("el-table-column",{attrs:{prop:"describe",label:"描述"}})],1)],1)])])])],1)],1)}),r=[],o=a("a1ad"),l={data:function(){return{table:[],apiOptions:[],apiValue:"",classList:[],appCode:"",apiMessageAll:{},statusTable:[{statusCode:200,describe:"操作成功"},{statusCode:400,describe:"操作失败"}]}},props:["list","defaultApiList"],created:function(){this.appCode=this.$route.params.appCode},mounted:function(){},methods:{choseApi:function(t,e){this.classList=[];for(var a=0;a<this.defaultApiList.length;a++)a===e?this.classList.push("hitClass"):this.classList.push("nohit");console.log(t);var n=t.apiId;this.getapiMessage(n)},getapiMessage:function(t){var e=this;Object(o["c"])(t).then((function(t){200===t.code&&(e.apiMessageAll=t.data,e.table=JSON.parse(t.data.requestParams),console.log(e.table))}))},apiValueChange:function(){var t=this,e={appCode:this.appCode,appVersionId:this.apiValue};Object(o["p"])(e).then((function(e){200===e.code&&t.$emit("changeVersion",e.data.apiList)}))},onCopy:function(){this.$message("复制成功")},onError:function(){this.$message("复制失败")}},watch:{list:function(){this.apiValue=this.list[0].value},defaultApiList:function(){this.classList=[];for(var t=0;t<this.defaultApiList.length;t++)0===t?this.classList.push("hitClass"):this.classList.push("nohit");console.log(this.defaultApiList);var e=this.defaultApiList[0].apiId;this.getapiMessage(e)}}},u=l,c=(a("65663"),a("2877")),p=Object(c["a"])(u,s,r,!1,null,"83c749da",null),d=p.exports,g=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"plug-in auto"},[a("div",{staticClass:"table_box mode-margin"},[a("p",[t._v("已添加插件")]),a("el-table",{directives:[{name:"loading",rawName:"v-loading",value:t.versionLoading,expression:"versionLoading"}],attrs:{data:t.pluginsTable,"empty-text":"暂无数据","row-style":{height:"50px"},"highlight-current-row":"","header-cell-style":{"font-weight":400,"font-size":"16px",color:"#1D1C35"}}},[a("el-table-column",{attrs:{prop:"appName",label:"插件名称"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("div",[t._v(" "+t._s(t._f("plugName")(e.row.pluginType))+" ")])]}}])}),a("el-table-column",{attrs:{prop:"appCode",label:"启用状态"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("div",[a("el-switch",{attrs:{"active-color":"#6EE4A5","inactive-color":"#E1E6EE","active-value":1,"inactive-value":0},on:{change:function(a){return t.enabledChange(e.row)}},model:{value:e.row.enabled,callback:function(a){t.$set(e.row,"enabled",a)},expression:"scope.row.enabled"}})],1)]}}])}),a("el-table-column",{attrs:{label:"操作",width:"180px"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("el-button",{attrs:{type:"text"},on:{click:function(a){return t.getMessage(e.row)}}},[t._v("查看")]),t.goConfig(e.row.pluginType)?a("span",{staticClass:"handle"},[t._v("|")]):t._e(),t.goConfig(e.row.pluginType)?a("el-button",{attrs:{type:"text"},on:{click:function(a){return t.pluginConfig(e.row)}}},[t._v("配置")]):t._e()]}}])})],1)],1),a("div",{staticClass:"table_box mode-margin"},[t._m(0),a("el-table",{directives:[{name:"loading",rawName:"v-loading",value:t.requestLoding,expression:"requestLoding"}],attrs:{data:t.requestTable,"empty-text":"暂无数据","row-style":{height:"50px"},"highlight-current-row":"","header-cell-style":{"font-weight":400,"font-size":"16px",color:"#1D1C35"}}},[a("el-table-column",{attrs:{prop:"requestUri",label:"请求地址",width:"280px"}}),a("el-table-column",{attrs:{prop:"httpMethod",label:"请求方式"}}),a("el-table-column",{attrs:{prop:"remoteIp",label:"客户端IP"}}),a("el-table-column",{attrs:{prop:"serverIp",label:"服务端IP"}}),a("el-table-column",{attrs:{prop:"consumingTime",label:"耗时(ms)"}}),a("el-table-column",{attrs:{label:"调用时间",width:"180px"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("span",[t._v(t._s(t._f("formatDate")(e.row.eventTime)))])]}}])}),a("el-table-column",{attrs:{label:"操作"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("el-button",{attrs:{type:"text"},on:{click:function(a){return t.getMessage(e.row)}}},[t._v("查看")])]}}])})],1),a("el-pagination",{staticClass:"list-pagination",attrs:{background:"","current-page":t.currentPageRequest,layout:"prev, pager, next",total:t.totalRequest},on:{"update:currentPage":function(e){t.currentPageRequest=e},"update:current-page":function(e){t.currentPageRequest=e},"current-change":t.handleCurrentChangeRequest}})],1),a("div",{staticClass:"table_box mode-margin"},[t._m(1),a("el-table",{directives:[{name:"loading",rawName:"v-loading",value:t.errorLoading,expression:"errorLoading"}],attrs:{data:t.ErrorTable,"row-style":{height:"50px"},"highlight-current-row":"","header-cell-style":{"font-weight":400,"font-size":"16px",color:"#1D1C35"}}},[a("el-table-column",{attrs:{prop:"requestUri",label:"请求地址",width:"280px"}}),a("el-table-column",{attrs:{prop:"httpMethod",label:"请求方式"}}),a("el-table-column",{attrs:{prop:"remoteIp",label:"客户端IP"}}),a("el-table-column",{attrs:{prop:"serverIp",label:"服务端IP"}}),a("el-table-column",{attrs:{prop:"consumingTime",label:"耗时(ms)"}}),a("el-table-column",{attrs:{label:"调用时间",width:"180px"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("span",[t._v(t._s(t._f("formatDate")(e.row.eventTime)))])]}}])}),a("el-table-column",{attrs:{label:"操作"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("el-button",{attrs:{type:"text"},on:{click:function(a){return t.getMessage(e.row)}}},[t._v("查看")])]}}])})],1),a("el-pagination",{staticClass:"list-pagination",attrs:{background:"","current-page":t.currentPageError,layout:"prev, pager, next",total:t.totalError},on:{"update:currentPage":function(e){t.currentPageError=e},"update:current-page":function(e){t.currentPageError=e},"current-change":t.handleCurrentChangeError}})],1)])},h=[function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"serve-table"},[a("div",{staticClass:"table-tilelong"},[t._v("请求日志")])])},function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"serve-table"},[a("div",{staticClass:"table-tilelong"},[t._v("错误日志")])])}],f={filters:{plugName:function(t){var e={jwt:"Jwt-auth",base_auth:"basic_auth",oauth2:"OAuth2.0",black_list_ip:"IP 黑名单控制",white_list_ip:"IP 白名单控制",cors:"cors跨域",sign:"防篡改签名",replay_attacks:"防网络重放攻击",error_log:"error log",http_log:"http log",sentinel:"sentinel",gzip:"gzip",proxy_cache:"proxy_cache",real_ip:"real_ip",response_rewrite:"response-rewrite"};return t?e[t]:""}},data:function(){return{ErrorTable:[],pluginsTable:[],appCode:"",versionLoading:!1,appId:"",requestLoding:!1,requestTable:[],currentPageRequest:1,totalRequest:0,errorLoading:!1,currentPageError:1,totalError:0}},created:function(){this.appCode=this.$route.params.appCode,this.getServeDeatil(),this.getLog("request"),this.getLog("error")},methods:{getServeDeatil:function(){var t=this;this.versionLoading=!0,Object(o["t"])(this.appCode).then((function(e){200===e.code&&(t.versionLoading=!1,t.appId=e.data.appId,t.pluginsTable=e.data.plugins)}))},enabledChange:function(t){var e=this;if("sentinel"===t.pluginType)0===t.enabled?Object(o["l"])(t.appId).then((function(a){if(200===a.code){var n={appId:t.appId,appCode:t.appCode,pluginType:t.pluginType,enabled:t.enabled,id:t.id};Object(o["o"])(n).then((function(t){200===t.code&&e.getServeDeatil()}))}})):Object(o["f"])(t.appId).then((function(a){if(200===a.code){var n={appId:t.appId,appCode:t.appCode,pluginType:t.pluginType,enabled:t.enabled,id:t.id};Object(o["o"])(n).then((function(t){200===t.code&&e.getServeDeatil()}))}}));else{var a={appId:t.appId,appCode:t.appCode,pluginType:t.pluginType,enabled:t.enabled,id:t.id};Object(o["o"])(a).then((function(t){200===t.code&&e.getServeDeatil()}))}},pluginConfig:function(t){console.log(t),this.$router.push("/serve/serveDetail/pluginConfig/"+t.pluginType+"?appcode="+t.appCode+"&appid="+t.appId+"&id="+t.id+"&pluginParams=true")},getLog:function(t){var e=this;if("request"===t){this.requestLoding=!0;var a="appCode="+this.appCode+"&limit=10&offset="+this.currentPageRequest+"&statusCode=200";Object(o["k"])(a).then((function(t){200===t.code&&(e.requestTable=t.data.logList,e.requestLoding=!1,e.totalRequest=t.data.total)}))}else{var n="appCode="+this.appCode+"&limit=10&offset="+this.currentPageError+"&statusCode=500";this.errorLoading=!0,Object(o["k"])(n).then((function(t){200===t.code&&(e.errorLoading=!1,e.ErrorTable=t.data.logList,e.totalError=t.data.total)}))}},handleCurrentChangeRequest:function(t){this.currentPageRequest=t,this.getLog("request")},handleCurrentChangeError:function(t){this.currentPageError=t,this.getLog("error")},goConfig:function(t){return"jwt"===t||"oauth2"===t||"black_list_ip"===t||"white_list_ip"===t||"cors"===t||"sign"===t||"sentinel"===t}}},b=f,v=(a("474b"),Object(c["a"])(b,g,h,!1,null,"7ff2ddaf",null)),m=v.exports,_={data:function(){return{activeName:"first",appCode:"",serveAllMeaasge:{},loading:!1,versionList:[],apiList:[]}},created:function(){this.appCode=this.$route.params.appCode,this.getSubscribed()},components:{apiDetail:d,plugIn:m},methods:{handleClick:function(){},getSubscribed:function(){var t=this;this.loading=!0,Object(o["w"])(this.appCode).then((function(e){if(200===e.code){t.loading=!1,t.serveAllMeaasge=e.data,t.versionList=[];for(var a=0;a<e.data.appVersion.length;a++)t.versionList.push({label:e.data.appVersion[a].version,value:e.data.appVersion[a].id});t.apiList=e.data.appVersion[0].apiList}}))},changeVersion:function(t){this.apiList=t},unSubscribe:function(){var t=this;this.$confirm("确认退订服务"+this.serveAllMeaasge.appName+"?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then((function(){Object(o["j"])(t.appCode).then((function(e){200===e.code&&t.$router.push({path:"/serve/subscribe"})}))})).catch((function(){}))}}},C=_,w=(a("d797"),Object(c["a"])(C,n,i,!1,null,"9ea988e0",null));e["default"]=w.exports},fcb4:function(t,e,a){}}]);