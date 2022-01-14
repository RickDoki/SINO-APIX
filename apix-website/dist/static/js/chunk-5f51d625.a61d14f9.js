(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-5f51d625"],{"0a0a":function(t,e,n){"use strict";var a=[{type:"身份认证",content:[{name:"Jwt-auth",code:"jwt",imgUrl:"",message:"验证和认证JSON Web令牌",defaultShow:!0,configuration:!0},{name:"basic_auth",code:"base_auth",imgUrl:"",message:"为您的服务添加基本身份验证",defaultShow:!0,configuration:!1},{name:"OAuth2.0",code:"oauth2",imgUrl:"",message:"将SDX与第三方OAuth2.0授权服务器集成",defaultShow:!0,configuration:!0}]},{type:"安全防护",content:[{name:"IP 黑名单控制",code:"black_list_ip",imgUrl:"",message:"可以发出请求的黑名单",defaultShow:!0,configuration:!0},{name:"IP 白名单控制",code:"white_list_ip",imgUrl:"",message:"可以发出请求的白名单",defaultShow:!0,configuration:!0},{name:"cors跨域",code:"cors",imgUrl:"",message:"允许开发人员从浏览器发出请求",defaultShow:!0,configuration:!0},{name:"防篡改签名",code:"sign",imgUrl:"",message:"-",defaultShow:!0,configuration:!0},{name:"防网络重放攻击",code:"replay_attacks",imgUrl:"",message:"网络请求重试限制",defaultShow:!0,configuration:!1}]},{type:"可观测性",content:[{name:"error log",code:"error_log",imgUrl:"",message:"错误日志记录器",defaultShow:!0,configuration:!1},{name:"http log",code:"http_log",imgUrl:"",message:"将请求和响应日志发送到http服务器",defaultShow:!0,configuration:!1}]},{type:"流量控制",content:[{name:"sentinel",code:"sentinel",imgUrl:"",message:"外部系统集成控流插件",defaultShow:!0,configuration:!0}]},{type:"其他插件",content:[{name:"gzip",code:"gzip",imgUrl:"",message:"请求压缩",defaultShow:!0,configuration:!1},{name:"proxy-cache",code:"proxy_cache",imgUrl:"",message:"代理缓存",defaultShow:!0,configuration:!1},{name:"real_ip",code:"real_ip",imgUrl:"",message:"获取真实ip",defaultShow:!0,configuration:!1},{name:"response-rewrite",code:"response_rewrite",imgUrl:"",message:"请求返回信息重写",defaultShow:!0,configuration:!1}]}];e["a"]=a},"4fe3":function(t,e,n){},"9f13":function(t,e,n){"use strict";n("4fe3")},a1ad:function(t,e,n){"use strict";n.d(e,"u",(function(){return u})),n.d(e,"g",(function(){return i})),n.d(e,"v",(function(){return o})),n.d(e,"s",(function(){return r})),n.d(e,"t",(function(){return c})),n.d(e,"d",(function(){return s})),n.d(e,"b",(function(){return p})),n.d(e,"n",(function(){return l})),n.d(e,"h",(function(){return d})),n.d(e,"a",(function(){return f})),n.d(e,"p",(function(){return m})),n.d(e,"e",(function(){return g})),n.d(e,"x",(function(){return h})),n.d(e,"k",(function(){return b})),n.d(e,"w",(function(){return v})),n.d(e,"c",(function(){return C})),n.d(e,"m",(function(){return w})),n.d(e,"q",(function(){return O})),n.d(e,"r",(function(){return j})),n.d(e,"o",(function(){return _})),n.d(e,"j",(function(){return y})),n.d(e,"i",(function(){return S})),n.d(e,"l",(function(){return U})),n.d(e,"f",(function(){return L}));var a=n("b775");function u(t){return Object(a["a"])({url:"/app/list?"+t,method:"get"})}function i(t){return Object(a["a"])({url:"/app/create",method:"post",data:t})}function o(t,e){return Object(a["a"])({url:"/app/"+t,method:"put",data:e})}function r(t){return Object(a["a"])({url:"/app/"+t,method:"delete"})}function c(t){return Object(a["a"])({url:"/app/"+t,method:"get"})}function s(t){return Object(a["a"])({url:"/app/data/"+t+"/appNum",method:"post"})}function p(t){return Object(a["a"])({url:"/app/api/list?developerId="+t,method:"get"})}function l(t,e){return Object(a["a"])({url:"/app/"+t+"/publish",method:"post",data:e})}function d(t){return Object(a["a"])({url:"/app/appVersion/"+t,method:"delete"})}function f(t){return Object(a["a"])({url:"/app/subscribed/list?"+t,method:"get"})}function m(t){return Object(a["a"])({url:"/app/api/queryApiList",method:"post",data:t})}function g(t,e){return Object(a["a"])({url:"/app/appVersion/"+t,method:"post",data:e})}function h(t){return Object(a["a"])({url:"/app/appVersion/"+t,method:"get"})}function b(t){return Object(a["a"])({url:"/gateway/log?"+t,method:"get"})}function v(t){return Object(a["a"])({url:"/app/subscribed/"+t,method:"get"})}function C(t){return Object(a["a"])({url:"/app/api/"+t,method:"get"})}function w(t){return Object(a["a"])({url:"/app/plugin",method:"post",data:t})}function O(){return Object(a["a"])({url:"/app/plugin/randomKey",method:"get"})}function j(t){return Object(a["a"])({url:"/app/rateLimit/save",method:"post",data:t})}function _(t){return Object(a["a"])({url:"/app/plugin",method:"put",data:t})}function y(t){return Object(a["a"])({url:"/app/unSubscribe/"+t,method:"post"})}function S(t,e){return Object(a["a"])({url:"/app/plugin/"+t+"/"+e,method:"get"})}function U(t){return Object(a["a"])({url:"/app/rateLimit/open?appId="+t,method:"post"})}function L(t){return Object(a["a"])({url:"/app/rateLimit/close?appId="+t,method:"post"})}},efe9:function(t,e,n){"use strict";n.r(e);var a=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"plug-main"},[n("div",{staticClass:"plugDetail"},[n("div",{staticClass:"plug-top"},[n("div",{staticClass:"titleFont left"},[t._v("插件中心")]),n("div",[n("el-input",{staticClass:"list_searchInput",attrs:{size:"small",placeholder:"搜索","suffix-icon":"el-icon-search"},on:{input:t.nameChange},model:{value:t.name,callback:function(e){t.name=e},expression:"name"}})],1)]),n("div",{staticClass:"plugDetailLeft"},t._l(t.plugNameList,(function(e,a){return n("div",{key:a,class:t.defaultCss[a],on:{click:function(n){return t.hitplugName(e,a)}}},[t._v(" "+t._s(e.type)+" ")])})),0),n("div",{staticClass:"plugDetailRight"},[t._l(t.plugTypeList,(function(e,a){return[e.defaultShow?n("div",{key:a,staticClass:"messageBox"},[n("div",{staticClass:"title"},[t._v(t._s(e.name))]),n("div",{staticClass:"middle"},[n("div",{staticClass:"middle-img"},[n("img",{attrs:{src:"",alt:""}}),n("span",[t._v(t._s(e.message))])])]),n("div",{staticClass:"plug-bottom",on:{click:function(n){return t.addPulgin(e)}}},[t._v("添加")])]):t._e()]}))],2)])])},u=[],i=(n("b0c0"),n("fcb4"),n("0a0a")),o=n("a1ad"),r={data:function(){return{name:"",plugNameList:i["a"],defaultCss:["defaultCss hitCss","defaultCss","defaultCss","defaultCss","defaultCss"],plugTypeList:i["a"][0].content,appCode:"",appId:""}},created:function(){this.appCode=this.$route.query.appcode,this.appId=this.$route.query.appid},methods:{hitplugName:function(t,e){for(var n=[],a=0;a<this.defaultCss.length;a++)a===e?(n.push("defaultCss hitCss"),this.plugTypeList=t.content):n.push("defaultCss");this.defaultCss=n},nameChange:function(){for(var t in this.plugTypeList)this.plugTypeList[t].name.indexOf(this.name)>-1?this.plugTypeList[t].defaultShow=!0:this.plugTypeList[t].defaultShow=!1},addPulgin:function(t){var e=this;t.configuration?this.$confirm(t.name+" 插件需要配置后才能添加","提示",{confirmButtonText:"去配置",cancelButtonText:"取消",type:"warning"}).then((function(){e.$router.push({path:"/serve/serveDetail/pluginConfig/"+t.code+"?appcode="+e.appCode+"&appid="+e.appId})})).catch((function(){})):this.$confirm("是否将 "+t.name+" 插件添加到当前服务?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"success"}).then((function(){console.log(t);var n={pluginType:t.code,appCode:e.appCode,appId:e.appId};Object(o["m"])(n).then((function(t){200===t.code&&e.$router.push({path:"/serve/serveDetail/"+e.appCode})}))})).catch((function(){}))}}},c=r,s=(n("9f13"),n("2877")),p=Object(s["a"])(c,a,u,!1,null,"34f0dcce",null);e["default"]=p.exports},fcb4:function(t,e,n){}}]);
