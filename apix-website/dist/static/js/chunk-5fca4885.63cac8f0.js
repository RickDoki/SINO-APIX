(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-5fca4885"],{"096c":function(t,a,e){"use strict";e.r(a);var s=function(){var t=this,a=t.$createElement,e=t._self._c||a;return e("div",{staticClass:"main_open"},[e("navbar"),e("div",{staticClass:"content"},[e("div",{staticClass:"list_top"},[e("div",[e("div",{staticClass:"list_top_title"},[t._v(t._s(t.appName))]),e("div",{staticClass:"introduction"},[t._v(t._s(t.appDescription))]),e("div",{staticClass:"service_providers",staticStyle:{"margin-top":"30px"}},[t._v("发布者： "),e("span",{staticStyle:{display:"inline-block","margin-left":"40px"}},[t._v(t._s(t.appProvider))])]),e("div",{staticStyle:{display:"flex","margin-top":"10px"}},[t.plugins.length>0?e("div",{staticClass:"service_providers",staticStyle:{"margin-right":"0"}},[t._v("已添加的插件：")]):t._e(),e("div",{staticStyle:{width:"669px",display:"flex","flex-wrap":"wrap"}},t._l(t.plugins,(function(a,s){return e("div",{key:s,staticClass:"plug-in service_providers",staticStyle:{display:"flex"}},[e("el-tooltip",{staticClass:"item",attrs:{effect:"light",content:a.msg,placement:"bottom-start"}},[e("div",{staticClass:"chajian_qian"},[e("img",{staticStyle:{width:"100%",height:"100%"},attrs:{src:a.icon}})])]),e("div",[t._v(t._s(a.pluginType))])],1)})),0)])]),e("div",[e("div",[t.subscribed?e("el-button",{staticStyle:{width:"100px"},attrs:{type:"primary",size:"small",disabled:!0},on:{click:t.subscribe}},[t._v("已订阅 ")]):e("el-button",{staticStyle:{width:"100px"},attrs:{type:"primary",size:"small"},on:{click:t.subscribe}},[t._v("订阅 ")]),e("el-button",{staticStyle:{width:"100px"},attrs:{size:"small",icon:"el-icon-back"},on:{click:function(a){return t.$router.push({name:"openServe"})}}},[t._v(" 返回 ")])],1),e("div",{staticClass:"release_time"},[t._v("发布时间："+t._s(t.appCreationDate))])])]),e("div",{staticClass:"list_content"},[e("api-detail",{attrs:{apiOptions:t.appVersion}})],1)])],1)},i=[],n=e("2909"),l=(e("159b"),e("d81d"),e("b0c0"),e("1e8f")),o=e("5f87"),c=function(){var t=this,a=t.$createElement,e=t._self._c||a;return e("div",{staticClass:"he_main"},[e("el-row",{staticStyle:{height:"100%"}},[e("el-col",{staticStyle:{height:"100%"},attrs:{span:4}},[e("div",{staticClass:"apiList"},[e("el-select",{attrs:{size:"mini",placeholder:"请选择"},on:{change:t.apiValueChange},model:{value:t.apiValue,callback:function(a){t.apiValue=a},expression:"apiValue"}},t._l(t.apiOptions,(function(t){return e("el-option",{key:t.id,attrs:{label:t.version,value:t.id}})})),1),t._l(t.defaultApiList,(function(a,s){return e("p",{key:s,class:t.classList[s],staticStyle:{cursor:"pointer"},on:{click:function(e){return t.choseApi(a,s)}}},[t._v(" "+t._s(a.apiName)+" ")])}))],2)]),e("el-col",{staticStyle:{height:"100%"},attrs:{span:20}},[e("div",{staticClass:"apiMessage"},[e("div",{staticClass:"api-info"},[e("div",{staticClass:"title"},[t._v(t._s(t.apiMessageAll.apiName))]),e("div",{staticClass:"secondTitle"},[t._v(t._s(t.apiMessageAll.description))])]),e("div",{staticClass:"api-info"},[e("span",{staticClass:"label-color"},[t._v("调用路径 : ")]),e("span",{staticClass:"conten-color"},[t._v(t._s(t.apiMessageAll.domain))])]),e("div",{staticClass:"api-info"},[e("span",{staticClass:"label-color agrement"},[t._v("协议类型 : ")]),e("span",{staticClass:"conten-color"},[t._v(t._s(t.apiMessageAll.protocol))])]),e("div",{staticClass:"api-info"},[e("span",{staticClass:"label-color"},[t._v("请求方式 : ")]),e("span",{staticClass:"conten-color"},[t._v(t._s(t.apiMessageAll.requestMethod))])]),e("div",{staticClass:"api-info"},[e("span",{staticClass:"label-color"},[t._v("返回类型 : ")]),e("span",{staticClass:"conten-color"},[t._v("JSON")])]),e("div",{staticClass:"api-info"},[e("span",{staticClass:"label-color"},[t._v("请求参数 : ")]),e("div",{staticClass:"table_box table_top"},[e("el-table",{attrs:{data:t.table,"empty-text":"暂无数据","row-style":{height:"50px"},"highlight-current-row":"","header-cell-style":{"font-weight":400,color:"#494E6A"}}},[e("el-table-column",{attrs:{prop:"parame",label:"名称"}}),e("el-table-column",{attrs:{prop:"type",label:"类型"}}),e("el-table-column",{attrs:{prop:"isHaveto",label:"是否必选"}}),e("el-table-column",{attrs:{prop:"describe",label:"描述"}}),e("el-table-column",{attrs:{prop:"default",label:"默认值"}})],1)],1)]),e("div",{staticClass:"api-info"},[e("span",{staticClass:"label-color"},[t._v("状态码 : ")]),e("div",{staticClass:"table_box table_top"},[e("el-table",{attrs:{data:t.statusTable,"empty-text":"暂无数据","row-style":{height:"50px"},"highlight-current-row":"","header-cell-style":{"font-weight":400,color:"#494E6A"}}},[e("el-table-column",{attrs:{prop:"statusCode",label:"状态码",width:"200"}}),e("el-table-column",{attrs:{prop:"describe",label:"描述"}})],1)],1)])])])],1)],1)},r=[],p=e("a1ad"),u={props:["apiOptions"],created:function(){},methods:{apiValueChange:function(){var t=this,a={appCode:this.$route.query.code,appVersionId:this.apiValue};Object(p["p"])(a).then((function(a){200===a.code&&(t.defaultApiList=a.data.apiList,t.classList=[])}))},getapiMessage:function(t){var a=this;Object(p["c"])(t).then((function(t){200===t.code&&(a.apiMessageAll=t.data,a.table=JSON.parse(t.data.requestParams))}))},choseApi:function(t,a){this.classList=[];for(var e=0;e<this.defaultApiList.length;e++)e===a?this.classList.push("hitClass"):this.classList.push("nohit");var s=t.apiId;this.getapiMessage(s)}},data:function(){return{table:[],classList:[],apiValue:"",defaultApiList:[],apiMessageAll:{},statusTable:[{statusCode:200,describe:"操作成功"},{statusCode:400,describe:"操作失败"}]}},watch:{apiOptions:function(){this.apiValue=this.apiOptions[0].id,this.apiValueChange()},defaultApiList:function(){this.classList=[];for(var t=0;t<this.defaultApiList.length;t++)0===t?this.classList.push("hitClass"):this.classList.push("nohit");var a=this.defaultApiList[0].apiId;this.getapiMessage(a)}}},d=u,h=(e("5bb8"),e("2877")),b=Object(h["a"])(d,c,r,!1,null,"90ee62d6",null),f=b.exports,v=e("90cc"),g=e("0a0a"),m={components:{navbar:v["a"],apiDetail:f},data:function(){return{appName:"",appDescription:"",appCreationDate:"",appVersion:[],appProvider:"",plugins:[],tooltipList:g["a"],subscribed:!0}},created:function(){this.query()},methods:{plugName:function(t){var a={jwt:"Jwt-auth",base_auth:"basic_auth",oauth2:"OAuth2.0",black_list_ip:"IP 黑名单控制",white_list_ip:"IP 白名单控制",cors:"cors跨域",sign:"防篡改签名",replay_attacks:"防网络重放攻击",error_log:"error log",http_log:"http log",sentinel:"sentinel",gzip:"gzip",proxy_cache:"proxy-cache",real_ip:"real_ip",response_rewrite:"response-rewrite"};return t?a[t]:""},query:function(){var t=this;Object(l["b"])(this.$route.query.code).then((function(a){if(200===a.code){t.appName=a.data.appName,t.appDescription=a.data.appDescription,t.appCreationDate=a.data.appCreationDate,t.appProvider=a.data.appProvider,t.appVersion=a.data.appVersion||[],t.subscribed=a.data.subscribed,t.plugins=a.data.plugins;var e=[];t.tooltipList.forEach((function(t){e.push.apply(e,Object(n["a"])(t.content))})),t.plugins.map((function(a,s){a.pluginType=t.plugName(a.pluginType),e.forEach((function(t,e){a.pluginType===t.name&&(a.msg=t.message,a.icon=t.icon)}))}))}}))},subscribe:function(){var t=this;Object(o["a"])("token")?this.$confirm("确认订阅："+this.appName+"吗, 是否继续?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then((function(){Object(l["g"])(t.$route.query.code).then((function(a){200===a.code&&(t.$message.success("订阅成功"),t.query())}))})):this.$router.push({path:"/login",query:{path:this.$route.path,code:this.$route.query.code}})}}},_=m,C=(e("95ac"),Object(h["a"])(_,s,i,!1,null,"22167abe",null));a["default"]=C.exports},"1e8f":function(t,a,e){"use strict";e.d(a,"f",(function(){return i})),e.d(a,"c",(function(){return n})),e.d(a,"d",(function(){return l})),e.d(a,"a",(function(){return o})),e.d(a,"e",(function(){return c})),e.d(a,"b",(function(){return r})),e.d(a,"g",(function(){return p}));var s=e("b775");function i(t){return Object(s["a"])({url:"/app/appList/"+t,method:"get"})}function n(t){return Object(s["a"])({url:"/app/data/statistics",method:"post",data:t})}function l(t){return Object(s["a"])({url:"/app/subscribed/list"+t,method:"get"})}function o(t){return Object(s["a"])({url:"/app/api/list"+t,method:"get"})}function c(t){return Object(s["a"])({url:"/app/open/list"+t,method:"get"})}function r(t){return Object(s["a"])({url:"/app/open/".concat(t),method:"get"})}function p(t){return Object(s["a"])({url:"/app/open/subscribe/".concat(t),method:"get"})}},3918:function(t,a,e){},"40e8":function(t,a,e){"use strict";e("ceef")},"5bb8":function(t,a,e){"use strict";e("a74a")},"90cc":function(t,a,e){"use strict";var s=function(){var t=this,a=t.$createElement,s=t._self._c||a;return s("div",{staticClass:"navbar_top"},[s("div",{staticClass:"navbar"},[t._m(0),s("div",{staticClass:"navber_userHandle"},[s("div",{staticClass:"kongzhitai",on:{click:t.godashboard}},[t._v("控制台")]),t.isLogin?t._e():s("div",{staticClass:"kongzhitai",staticStyle:{"margin-left":"20px"},on:{click:t.gologin}},[t._v("登录")]),t.isLogin?s("img",{attrs:{src:e("f92b"),alt:""}}):t._e(),t.isLogin?s("el-dropdown",{attrs:{trigger:"click"},on:{command:t.handleCommand}},[s("span",{staticClass:"el-dropdown-link",staticStyle:{color:"#1D1C35"}},[t._v(" "+t._s(t.userName)),s("i",{staticClass:"el-icon-arrow-down el-icon--right"})]),s("el-dropdown-menu",{staticClass:"drop-div",attrs:{slot:"dropdown"},slot:"dropdown"},[s("el-dropdown-item",{attrs:{command:"a"}},[t._v("个人信息")]),s("el-dropdown-item",{attrs:{command:"b"}},[t._v("退出登录")])],1)],1):t._e()],1)])])},i=[function(){var t=this,a=t.$createElement,s=t._self._c||a;return s("div",{staticStyle:{width:"210px",height:"60px",display:"flex","justify-content":"center","align-items":"center"}},[s("img",{staticClass:"sidebar-logo",attrs:{src:e("4da1")}})])}],n=e("5530"),l=e("2f62"),o=e("5f87"),c={components:{},data:function(){return{phone:"",userName:"",isLogin:!0}},created:function(){this.phone=Object(o["a"])("apiPhone"),this.userName=Object(o["a"])("userName_api"),this.phone?this.isLogin=!0:this.isLogin=!1},watch:{$route:{handler:function(t,a){},deep:!0}},computed:Object(n["a"])({},Object(l["b"])(["sidebar","avatar","device"])),methods:{handleCommand:function(t){"a"==t?this.$router.push({path:"/system/index"}):this.$router.push({path:"/login"})},godashboard:function(){this.$router.push({name:"Dashboard"})},gologin:function(){this.$router.push({path:"/login"})}}},r=c,p=(e("40e8"),e("2877")),u=Object(p["a"])(r,s,i,!1,null,"1646885b",null);a["a"]=u.exports},"95ac":function(t,a,e){"use strict";e("3918")},a74a:function(t,a,e){},ceef:function(t,a,e){}}]);