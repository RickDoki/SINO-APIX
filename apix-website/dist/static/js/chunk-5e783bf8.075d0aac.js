(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-5e783bf8"],{"007c":function(e,t,r){"use strict";r("122c")},"0d53":function(e,t,r){"use strict";r("bb51")},"11f9":function(e,t,r){"use strict";r("aeff")},"122c":function(e,t,r){},"235e":function(e,t,r){"use strict";r("fa21")},"2c28":function(e,t,r){"use strict";r.r(t);var i=function(){var e=this,t=e.$createElement,r=e._self._c||t;return r("div",[r("div",{staticClass:"titleFont"},[e._v(" "+e._s(e._f("plugName")(e.pluginName))+" ")]),"jwt"===e.pluginName?r("jwt-auth"):"oauth2"===e.pluginName?r("oauth"):"sign"===e.pluginName?r("sign"):"black_list_ip"===e.pluginName?r("black"):"white_list_ip"===e.pluginName?r("white"):"cors"===e.pluginName?r("cors"):r("sentinel")],1)},a=[],o=(r("fcb4"),function(){var e=this,t=e.$createElement,r=e._self._c||t;return r("div",{staticClass:"formBox"},[r("el-form",{ref:"ruleForm",attrs:{model:e.ruleForm,rules:e.rules,"label-position":"top"}},[r("el-form-item",{attrs:{label:"config.Header",prop:"header"}},[r("el-input",{staticClass:"inputWidth",attrs:{placeholder:"",disabled:!0,clearable:""},model:{value:e.ruleForm.header,callback:function(t){e.$set(e.ruleForm,"header",t)},expression:"ruleForm.header"}})],1),r("el-form-item",{attrs:{label:"config.key",prop:"key"}},[r("el-input",{staticClass:"inputWidth",attrs:{placeholder:"",disabled:!0,clearable:""},model:{value:e.ruleForm.key,callback:function(t){e.$set(e.ruleForm,"key",t)},expression:"ruleForm.key"}})],1),r("el-form-item",{attrs:{label:"config.Maximum (过期时间ms)",prop:"maximum"}},[r("el-input-number",{attrs:{min:1},on:{change:e.handleChange},model:{value:e.ruleForm.maximum,callback:function(t){e.$set(e.ruleForm,"maximum",t)},expression:"ruleForm.maximum"}})],1),r("div",{staticClass:"bottom_button_a"},[r("el-button",{attrs:{size:"small",type:"primary"},on:{click:function(t){return e.submitForm("ruleForm")}}},[e._v(e._s(e.buttonFont))]),r("el-button",{attrs:{size:"small"},on:{click:function(t){return e.resetForm("ruleForm")}}},[e._v("取消")])],1)],1)],1)}),n=[],l=r("a1ad"),s={data:function(){return{appCode:"",appId:"",id:"",pluginParams:{},enabled:0,ruleForm:{header:"authorizatuion",key:"iss",maximum:"7200000"},buttonFont:"添加",rules:{header:[{required:!0,message:"config.Header为必选项",trigger:"blur"}],key:[{required:!0,message:"config.key为必选项",trigger:"blur"}],maximum:[{required:!0,message:"过期时间为必选项",trigger:"blur"}]}}},created:function(){var e=this;this.appCode=this.$route.query.appcode,this.appId=this.$route.query.appid,this.$route.query.pluginParams?(this.id=this.$route.query.id,Object(l["i"])(this.id,this.appCode).then((function(t){if(200===t.code){e.enabled=t.data.enabled;var r=JSON.parse(t.data.pluginParams);e.ruleForm={header:r.HeaderNames,key:r.keyClaimName,maximum:r.TokenExpiration}}})),this.buttonFont="应用"):this.buttonFont="添加"},methods:{submitForm:function(e){var t=this;this.$refs[e].validate((function(e){if(!e)return!1;if("添加"===t.buttonFont){var r={HeaderNames:t.ruleForm.header,keyClaimName:t.ruleForm.key,TokenExpiration:t.ruleForm.maximum},i={pluginType:"jwt",appCode:t.appCode,appId:t.appId,pluginParams:JSON.stringify(r)};Object(l["m"])(i).then((function(e){200===e.code&&t.$router.push({path:"/serve/serveDetail/"+t.appCode})}))}else{var a={HeaderNames:t.ruleForm.header,keyClaimName:t.ruleForm.key,TokenExpiration:t.ruleForm.maximum},o={pluginType:"jwt",id:t.id,appCode:t.appCode,appId:t.appId,enabled:t.enabled,pluginParams:JSON.stringify(a)};console.log(o),Object(l["o"])(o).then((function(e){200===e.code&&t.$router.push({path:"/serve/serveDetail/"+t.appCode})}))}}))},resetForm:function(){this.$router.push({path:"/serve/serveDetail/"+this.appCode})},handleChange:function(e){console.log(e)}}},u=s,p=r("2877"),c=Object(p["a"])(u,o,n,!1,null,null,null),d=c.exports,m=function(){var e=this,t=e.$createElement,r=e._self._c||t;return r("div",{staticClass:"formBox"},[r("el-form",{ref:"ruleForm",attrs:{model:e.ruleForm,rules:e.rules,"label-position":"top"}},[r("el-form-item",{attrs:{label:"Provision Key",prop:"ProvisionKey"}},[r("el-input",{staticClass:"inputWidth",attrs:{placeholder:"",clearable:""},model:{value:e.ruleForm.ProvisionKey,callback:function(t){e.$set(e.ruleForm,"ProvisionKey",t)},expression:"ruleForm.ProvisionKey"}})],1),r("el-form-item",{attrs:{label:"Token Expiration",prop:"TokenExpiration"}},[r("el-input-number",{attrs:{min:1},on:{change:e.handleChange},model:{value:e.ruleForm.TokenExpiration,callback:function(t){e.$set(e.ruleForm,"TokenExpiration",t)},expression:"ruleForm.TokenExpiration"}})],1),r("el-form-item",{attrs:{prop:"timeout"}},[r("el-checkbox",{attrs:{disabled:""},model:{value:e.ruleForm.EnableClientCredentials,callback:function(t){e.$set(e.ruleForm,"EnableClientCredentials",t)},expression:"ruleForm.EnableClientCredentials"}},[e._v("客户端认证")])],1),r("el-form-item",{attrs:{label:"Refresh Token Expiration",prop:"RefreshTokenExpiration"}},[r("el-input-number",{attrs:{min:1},on:{change:e.handleChange},model:{value:e.ruleForm.RefreshTokenExpiration,callback:function(t){e.$set(e.ruleForm,"RefreshTokenExpiration",t)},expression:"ruleForm.RefreshTokenExpiration"}})],1),r("div",{staticClass:"bottom_button_a"},[r("el-button",{attrs:{size:"small",type:"primary"},on:{click:function(t){return e.submitForm("ruleForm")}}},[e._v(e._s(e.buttonFont))]),r("el-button",{attrs:{size:"small"},on:{click:function(t){return e.resetForm("ruleForm")}}},[e._v("取消")])],1)],1)],1)},h=[],f={data:function(){return{id:"",appCode:"",appId:"",buttonFont:"添加",enabled:0,ruleForm:{ProvisionKey:"",EnableClientCredentials:!0,TokenExpiration:"7200",RefreshTokenExpiration:"1209600"},rules:{ProvisionKey:[{required:!0,message:"请输入ProvisionKey",trigger:"blur"}],TokenExpiration:[{required:!0,message:"请输入TokenExpiration",trigger:"blur"}],RefreshTokenExpiration:[{required:!0,message:"请输入RefreshTokenExpiration",trigger:"blur"}]}}},created:function(){var e=this;this.appCode=this.$route.query.appcode,this.appId=this.$route.query.appid,this.$route.query.pluginParams?(this.id=this.$route.query.id,Object(l["i"])(this.id,this.appCode).then((function(t){if(200===t.code){e.enabled=t.data.enabled;var r=JSON.parse(t.data.pluginParams);e.ruleForm=r}})),this.buttonFont="应用"):this.buttonFont="添加"},methods:{submitForm:function(e){var t=this;this.$refs[e].validate((function(e){if(!e)return!1;if("添加"===t.buttonFont){var r={pluginType:"oauth2",appCode:t.appCode,appId:t.appId,pluginParams:JSON.stringify(t.ruleForm)};Object(l["m"])(r).then((function(e){200===e.code&&t.$router.push({path:"/serve/serveDetail/"+t.appCode})}))}else{var i={pluginType:"oauth2",appCode:t.appCode,appId:t.appId,id:t.id,enabled:t.enabled,pluginParams:JSON.stringify(t.ruleForm)};Object(l["o"])(i).then((function(e){200===e.code&&t.$router.push({path:"/serve/serveDetail/"+t.appCode})}))}}))},resetForm:function(){this.$router.push({path:"/serve/serveDetail/"+this.appCode})},handleChange:function(e){console.log(e)}}},b=f,v=Object(p["a"])(b,m,h,!1,null,null,null),g=v.exports,F=function(){var e=this,t=e.$createElement,r=e._self._c||t;return r("div",{staticClass:"formBox"},[r("el-form",{ref:"ruleForm",attrs:{model:e.ruleForm,rules:e.rules,"label-position":"top"}},[r("el-form-item",{attrs:{label:"config.key",prop:"appKey"}},[r("el-input",{staticClass:"inputWidth",attrs:{placeholder:"",clearable:""},model:{value:e.ruleForm.appKey,callback:function(t){e.$set(e.ruleForm,"appKey",t)},expression:"ruleForm.appKey"}}),r("span",{staticClass:"show-but",on:{click:e.getrandomKey}},[e._v("系统生成")])],1),r("el-form-item",{attrs:{label:"config.Secret",prop:"appSecret"}},[r("el-input",{staticClass:"inputWidth",attrs:{placeholder:"",clearable:""},model:{value:e.ruleForm.appSecret,callback:function(t){e.$set(e.ruleForm,"appSecret",t)},expression:"ruleForm.appSecret"}})],1),r("el-form-item",{attrs:{label:"再次输入Secret",prop:"appSecret2"}},[r("el-input",{staticClass:"inputWidth",attrs:{placeholder:"",clearable:""},model:{value:e.ruleForm.appSecret2,callback:function(t){e.$set(e.ruleForm,"appSecret2",t)},expression:"ruleForm.appSecret2"}})],1),r("div",{staticClass:"bottom_button_a"},[r("el-button",{attrs:{size:"small",type:"primary"},on:{click:function(t){return e.submitForm("ruleForm")}}},[e._v(e._s(e.buttonFont))]),r("el-button",{attrs:{size:"small"},on:{click:function(t){return e.resetForm("ruleForm")}}},[e._v("取消")])],1)],1)],1)},C=[],y={data:function(){var e=this,t=function(t,r,i){""===r?i(new Error("请再次输入请输入config.Secret")):r!==e.ruleForm.appSecret?i(new Error("两次输入请输入config.Secret不一致!")):i()};return{appCode:"",appId:"",id:"",enabled:0,buttonFont:"添加",ruleForm:{appKey:"",appSecret:"",appSecret2:""},rules:{appSecret2:[{validator:t,trigger:"blur"}],appSecret:[{required:!0,message:"请输入config.key",trigger:"blur"}],appKey:[{required:!0,message:"请输入config.Secret",trigger:"blur"}]}}},created:function(){var e=this;this.appCode=this.$route.query.appcode,this.appId=this.$route.query.appid,this.$route.query.pluginParams?(this.id=this.$route.query.id,Object(l["i"])(this.id,this.appCode).then((function(t){if(200===t.code){e.enabled=t.data.enabled;var r=JSON.parse(t.data.pluginParams);e.ruleForm={appKey:r.appKey,appSecret:r.appSecret,appSecret2:r.appSecret}}})),this.buttonFont="应用"):this.buttonFont="添加"},methods:{submitForm:function(e){var t=this;this.$refs[e].validate((function(e){if(!e)return!1;if("添加"===t.buttonFont){var r={appKey:t.ruleForm.appKey,appSecret:t.ruleForm.appSecret},i={pluginType:"sign",appCode:t.appCode,appId:t.appId,pluginParams:JSON.stringify(r)};Object(l["m"])(i).then((function(e){200===e.code&&t.$router.push({path:"/serve/serveDetail/"+t.appCode})}))}else{var a={appKey:t.ruleForm.appKey,appSecret:t.ruleForm.appSecret},o={pluginType:"sign",appCode:t.appCode,appId:t.appId,id:t.id,enabled:t.enabled,pluginParams:JSON.stringify(a)};Object(l["o"])(o).then((function(e){200===e.code&&t.$router.push({path:"/serve/serveDetail/"+t.appCode})}))}}))},resetForm:function(){this.$router.push({path:"/serve/serveDetail/"+this.appCode})},getrandomKey:function(){var e=this;Object(l["q"])().then((function(t){200===t.code&&(console.log(t),e.ruleForm.appKey=t.data)}))}}},_=y,x=(r("007c"),Object(p["a"])(_,F,C,!1,null,"43e14a32",null)),k=x.exports,O=function(){var e=this,t=e.$createElement,r=e._self._c||t;return r("div",{staticClass:"formBox"},[r("el-form",{ref:"ruleForm",attrs:{model:e.ruleForm,rules:e.rules,"label-position":"top"}},[r("el-form-item",{attrs:{label:"ip黑名单",prop:"black_list_ip"}},[r("el-input",{staticStyle:{width:"48%"},attrs:{type:"textarea",autosize:{minRows:4,maxRows:4},placeholder:"请输入需要控制访问的IP,多个IP需要用英文分号分隔"},model:{value:e.ruleForm.black_list_ip,callback:function(t){e.$set(e.ruleForm,"black_list_ip",t)},expression:"ruleForm.black_list_ip"}})],1),r("div",{staticClass:"bottom_button_a"},[r("el-button",{attrs:{size:"small",type:"primary"},on:{click:function(t){return e.submitForm("ruleForm")}}},[e._v(e._s(e.buttonFont))]),r("el-button",{attrs:{size:"small"},on:{click:function(t){return e.resetForm("ruleForm")}}},[e._v("取消")])],1)],1)],1)},w=[],$={data:function(){return{appCode:"",appId:"",id:"",enabled:0,buttonFont:"添加",ruleForm:{black_list_ip:""},rules:{black_list_ip:[{required:!0,message:"请输入ip或者域名",trigger:"blur"}]}}},created:function(){var e=this;this.appCode=this.$route.query.appcode,this.appId=this.$route.query.appid,this.$route.query.pluginParams?(this.id=this.$route.query.id,Object(l["i"])(this.id,this.appCode).then((function(t){if(200===t.code){e.enabled=t.data.enabled;var r=JSON.parse(t.data.pluginParams);e.ruleForm=r}})),this.buttonFont="应用"):this.buttonFont="添加"},methods:{submitForm:function(e){var t=this;this.$refs[e].validate((function(e){if(!e)return!1;if("添加"===t.buttonFont){var r={pluginType:"black_list_ip",appCode:t.appCode,appId:t.appId,pluginParams:JSON.stringify(t.ruleForm)};Object(l["m"])(r).then((function(e){200===e.code&&t.$router.push({path:"/serve/serveDetail/"+t.appCode})}))}else{var i={pluginType:"black_list_ip",id:t.id,enabled:t.enabled,appCode:t.appCode,appId:t.appId,pluginParams:JSON.stringify(t.ruleForm)};Object(l["o"])(i).then((function(e){200===e.code&&t.$router.push({path:"/serve/serveDetail/"+t.appCode})}))}}))},resetForm:function(){this.$router.push({path:"/serve/serveDetail/"+this.appCode})}}},I=$,S=(r("235e"),Object(p["a"])(I,O,w,!1,null,"0574c86a",null)),j=S.exports,P=function(){var e=this,t=e.$createElement,r=e._self._c||t;return r("div",{staticClass:"formBox"},[r("el-form",{ref:"ruleForm",attrs:{model:e.ruleForm,rules:e.rules,"label-position":"top"}},[r("el-form-item",{attrs:{label:"ip白名单",prop:"white_list_ip"}},[r("el-input",{staticStyle:{width:"48%"},attrs:{type:"textarea",autosize:{minRows:4,maxRows:4},placeholder:"请输入需要控制访问的IP,多个IP需要用英文分号分隔"},model:{value:e.ruleForm.white_list_ip,callback:function(t){e.$set(e.ruleForm,"white_list_ip",t)},expression:"ruleForm.white_list_ip"}})],1),r("div",{staticClass:"bottom_button_a"},[r("el-button",{attrs:{size:"small",type:"primary"},on:{click:function(t){return e.submitForm("ruleForm")}}},[e._v(e._s(e.buttonFont))]),r("el-button",{attrs:{size:"small"},on:{click:function(t){return e.resetForm("ruleForm")}}},[e._v("取消")])],1)],1)],1)},N=[],q={data:function(){return{appCode:"",appId:"",id:"",enabled:0,buttonFont:"添加",ruleForm:{white_list_ip:""},rules:{white_list_ip:[{required:!0,message:"请输入ip或者域名",trigger:"blur"}]}}},created:function(){var e=this;this.appCode=this.$route.query.appcode,this.appId=this.$route.query.appid,this.$route.query.pluginParams?(this.id=this.$route.query.id,Object(l["i"])(this.id,this.appCode).then((function(t){if(200===t.code){e.enabled=t.data.enabled;var r=JSON.parse(t.data.pluginParams);e.ruleForm=r}})),this.buttonFont="应用"):this.buttonFont="添加"},methods:{submitForm:function(e){var t=this;this.$refs[e].validate((function(e){if(!e)return!1;if("添加"===t.buttonFont){var r={pluginType:"white_list_ip",appCode:t.appCode,appId:t.appId,pluginParams:JSON.stringify(t.ruleForm)};Object(l["m"])(r).then((function(e){200===e.code&&t.$router.push({path:"/serve/serveDetail/"+t.appCode})}))}else{var i={pluginType:"white_list_ip",appCode:t.appCode,appId:t.appId,id:t.id,enabled:t.enabled,pluginParams:JSON.stringify(t.ruleForm)};Object(l["o"])(i).then((function(e){200===e.code&&t.$router.push({path:"/serve/serveDetail/"+t.appCode})}))}}))},resetForm:function(){this.$router.push({path:"/serve/serveDetail/"+this.appCode})}}},T=q,E=(r("f3e5"),Object(p["a"])(T,P,N,!1,null,"1ed92d94",null)),H=E.exports,L=function(){var e=this,t=e.$createElement,r=e._self._c||t;return r("div",{staticClass:"formBox"},[r("el-form",{ref:"ruleForm",attrs:{model:e.ruleForm,rules:e.rules,"label-position":"top"}},[r("el-form-item",{attrs:{label:"来源 Origin",prop:"allowOrigins"}},[r("el-input",{staticStyle:{width:"48%"},attrs:{type:"textarea",autosize:{minRows:4,maxRows:4},placeholder:"允许所有填*,以http://或https://开头,多个Origin用英文逗号分隔"},model:{value:e.ruleForm.allowOrigins,callback:function(t){e.$set(e.ruleForm,"allowOrigins",t)},expression:"ruleForm.allowOrigins"}})],1),r("el-form-item",{attrs:{label:"操作 Method",prop:"allowMethods"}},[r("el-checkbox-group",{attrs:{min:1,max:7},model:{value:e.ruleForm.allowMethods,callback:function(t){e.$set(e.ruleForm,"allowMethods",t)},expression:"ruleForm.allowMethods"}},e._l(e.methodsOptions,(function(t){return r("el-checkbox",{key:t,attrs:{label:t}},[e._v(e._s(t))])})),1)],1),r("el-form-item",{attrs:{label:"Allow-Headers",prop:"allowHeaders"}},[r("el-input",{staticStyle:{width:"48%"},attrs:{type:"textarea",autosize:{minRows:4,maxRows:4},placeholder:"请输入允许的Header,多个Header间用英文逗号分隔,允许所有的Header则填*"},model:{value:e.ruleForm.allowHeaders,callback:function(t){e.$set(e.ruleForm,"allowHeaders",t)},expression:"ruleForm.allowHeaders"}})],1),r("el-form-item",{attrs:{label:"All-exposeHeaders",prop:"exposeHeaders"}},[r("el-input",{staticStyle:{width:"48%"},attrs:{type:"textarea",autosize:{minRows:4,maxRows:4},placeholder:"请输入允许暴露给XHLHttpRequest对象的Header,多个Header间用英文逗号分隔,允许所有的Header则填*"},model:{value:e.ruleForm.exposeHeaders,callback:function(t){e.$set(e.ruleForm,"exposeHeaders",t)},expression:"ruleForm.exposeHeaders"}})],1),r("el-form-item",[r("el-checkbox",{model:{value:e.ruleForm.allowCredentials,callback:function(t){e.$set(e.ruleForm,"allowCredentials",t)},expression:"ruleForm.allowCredentials"}},[e._v("允许Cookie")])],1),r("el-form-item",{attrs:{label:"超时 Max-Age(s)",prop:"maxAge"}},[r("el-input-number",{attrs:{min:1},model:{value:e.ruleForm.maxAge,callback:function(t){e.$set(e.ruleForm,"maxAge",t)},expression:"ruleForm.maxAge"}})],1),r("div",{staticClass:"bottom_button_a"},[r("el-button",{attrs:{size:"small",type:"primary"},on:{click:function(t){return e.submitForm("ruleForm")}}},[e._v(e._s(e.buttonFont))]),r("el-button",{attrs:{size:"small"},on:{click:function(t){return e.resetForm("ruleForm")}}},[e._v("取消")])],1)],1)],1)},A=[],D=(r("ac1f"),r("1276"),r("d3b7"),r("25f0"),{data:function(){return{appCode:"",appId:"",id:"",enabled:0,buttonFont:"添加",methodsOptions:["GET","POST","PUT","DELETE","HEAD","OPTIONS","PATCH"],ruleForm:{allowOrigins:"",allowHeaders:"",exposeHeaders:"",allowMethods:["GET","POST","PUT","DELETE","HEAD","OPTIONS","PATCH"],allowCredentials:!1,maxAge:"5"},rules:{allowOrigins:[{required:!0,message:"请输入来源Origins",trigger:"blur"}],allowHeaders:[{required:!0,message:"请输入Allow-Headers",trigger:"blur"}],exposeHeaders:[{required:!0,message:"请输入来源exposeHeaders",trigger:"blur"}]}}},created:function(){var e=this;this.appCode=this.$route.query.appcode,this.appId=this.$route.query.appid,this.$route.query.pluginParams?(this.id=this.$route.query.id,Object(l["i"])(this.id,this.appCode).then((function(t){if(200===t.code){e.enabled=t.data.enabled;var r=JSON.parse(t.data.pluginParams);r.allowMethods=r.allowMethods.split(","),e.ruleForm=r}})),this.buttonFont="应用"):this.buttonFont="添加"},methods:{submitForm:function(e){var t=this;this.$refs[e].validate((function(e){if(!e)return!1;if("添加"===t.buttonFont){var r={allowOrigins:t.ruleForm.allowOrigins,allowHeaders:t.ruleForm.allowHeaders,exposeHeaders:t.ruleForm.exposeHeaders,allowMethods:t.ruleForm.allowMethods.toString(),allowCredentials:t.ruleForm.allowCredentials,maxAge:t.ruleForm.maxAge},i={pluginType:"cors",appCode:t.appCode,appId:t.appId,pluginParams:JSON.stringify(r)};Object(l["m"])(i).then((function(e){200===e.code&&t.$router.push({path:"/serve/serveDetail/"+t.appCode})}))}else{var a={allowOrigins:t.ruleForm.allowOrigins,allowHeaders:t.ruleForm.allowHeaders,exposeHeaders:t.ruleForm.exposeHeaders,allowMethods:t.ruleForm.allowMethods.toString(),allowCredentials:t.ruleForm.allowCredentials,maxAge:t.ruleForm.maxAge},o={pluginType:"cors",appCode:t.appCode,appId:t.appId,id:t.id,enabled:t.enabled,pluginParams:JSON.stringify(a)};Object(l["o"])(o).then((function(e){200===e.code&&t.$router.push({path:"/serve/serveDetail/"+t.appCode})}))}}))},resetForm:function(){this.$router.push({path:"/serve/serveDetail/"+this.appCode})}}}),J=D,K=(r("11f9"),Object(p["a"])(J,L,A,!1,null,"4c0c9be9",null)),V=K.exports,z=function(){var e=this,t=e.$createElement,r=e._self._c||t;return r("div",{staticClass:"s_main"},[r("p",[e._v("服务控流配置")]),r("p",{staticClass:"s_title"},[e._v(" 服务控制时长 "),r("span",{staticStyle:{color:"red"}},[e._v("*")]),r("el-tooltip",{attrs:{effect:"light",content:"单位时间内当前服务的请求次数上限",placement:"right"}},[r("i",{staticClass:"el-icon-warning-outline s_icon"})])],1),r("div",{staticStyle:{"margin-top":"20px"}},[r("el-input-number",{attrs:{min:1},on:{change:e.handleChange},model:{value:e.servetime,callback:function(t){e.servetime=t},expression:"servetime"}}),r("el-select",{staticStyle:{width:"80px","margin-left":"5px"},model:{value:e.servetimeValue,callback:function(t){e.servetimeValue=t},expression:"servetimeValue"}},e._l(e.serveOptions,(function(e){return r("el-option",{key:e.value,attrs:{label:e.label,value:e.value}})})),1)],1),r("p",{staticClass:"s_title"},[e._v(" 服务控流值 "),r("el-tooltip",{attrs:{effect:"light",content:"当前服务能够被访问的次数上限",placement:"right"}},[r("i",{staticClass:"el-icon-warning-outline s_icon"})])],1),r("div",{staticStyle:{"margin-top":"20px"}},[r("el-input-number",{attrs:{min:1},on:{change:e.handleChange},model:{value:e.servenum,callback:function(t){e.servenum=t},expression:"servenum"}})],1),r("p",{staticStyle:{"margin-top":"20px"}},[e._v("API控流配置")]),r("div",{staticClass:"s_apiType"},[r("div",{staticClass:"one"},[e._v("API")]),r("div",{staticClass:"two"},[e._v("API控制时长")]),r("div",{staticClass:"three"},[e._v(" API控流值 "),r("el-tooltip",{attrs:{effect:"light",content:"单位时间内一个api能够被访问的次数上限",placement:"right"}},[r("i",{staticClass:"el-icon-warning-outline s_icon"})])],1)]),e._l(e.apiConfigList,(function(t,i){return r("div",{key:i,staticClass:"information"},[r("div",{staticClass:"one"},[r("el-select",{staticStyle:{width:"200px"},attrs:{"value-key":"apiName"},on:{change:function(t){return e.apiChose(i)}},model:{value:e.apivalueList[i],callback:function(t){e.$set(e.apivalueList,i,t)},expression:"apivalueList[index]"}},e._l(e.options,(function(e){return r("el-option",{key:e.apiId,attrs:{label:e.apiName,value:e}})})),1)],1),r("div",{staticClass:"two"},[r("el-input-number",{staticStyle:{"margin-left":"15px"},attrs:{min:1},on:{change:e.handleChange},model:{value:e.apitime[i],callback:function(t){e.$set(e.apitime,i,t)},expression:"apitime[index]"}}),r("el-select",{staticStyle:{width:"80px","margin-left":"5px"},model:{value:e.apitimeValue[i],callback:function(t){e.$set(e.apitimeValue,i,t)},expression:"apitimeValue[index]"}},e._l(e.serveOptions,(function(e){return r("el-option",{key:e.value,attrs:{label:e.label,value:e.value}})})),1)],1),r("div",{staticClass:"three"},[r("el-input-number",{staticStyle:{"margin-left":"15px"},attrs:{min:1},on:{change:e.handleChange},model:{value:e.apiNum[i],callback:function(t){e.$set(e.apiNum,i,t)},expression:"apiNum[index]"}})],1),r("div",{staticClass:"four"},[0===i?r("el-button",{on:{click:e.apiConfigAdd}},[e._v("添加")]):e._e(),0!==i?r("el-button",{attrs:{type:"danger"},on:{click:function(t){return e.apiConfigDel(i)}}},[e._v("删除")]):e._e()],1)])})),r("div",{staticClass:"bottom_button_a"},[r("el-button",{attrs:{size:"small",type:"primary"},on:{click:function(t){return e.submitForm()}}},[e._v(e._s(e.buttonFont))]),r("el-button",{attrs:{size:"small"},on:{click:function(t){return e.resetForm("ruleForm")}}},[e._v("取消")])],1)],2)},R=[],U=(r("a434"),r("5f87")),M={data:function(){return{options:[],servetime:"1",servetimeValue:"1",servenum:"1",developerId:"",apiConfigList:[""],apivalueList:[""],apitime:["1"],apitimeValue:["1"],apiNum:["1"],serveOptions:[{value:"0",label:"秒"},{value:"1",label:"分"},{value:"2",label:"时"},{value:"3",label:"天"}],appCode:"",appId:"",id:"",enabled:0,buttonFont:"添加"}},created:function(){var e=this;this.developerId=Object(U["a"])("userId_api"),this.appCode=this.$route.query.appcode,this.appId=this.$route.query.appid,this.getApiList(),this.$route.query.pluginParams?(this.id=this.$route.query.id,Object(l["i"])(this.id,this.appCode).then((function(t){if(200===t.code){e.enabled=t.data.enabled;var r=JSON.parse(t.data.pluginParams);console.log(r),e.servetime=r[0].interval,e.servenum=r[0].count,e.servetimeValue=r[0].intervalUnit,e.apivalueList=[],e.apiConfigList=[],e.apitime=[],e.apiNum=[],e.apitimeValue=[];for(var i=1;i<r.length;i++)e.apiConfigList.push(""),e.apivalueList.push(r[i].apiId),e.apitime.push(r[i].interval),e.apiNum.push(r[i].count),e.apitimeValue.push(r[i].intervalUnit)}})),this.buttonFont="应用"):this.buttonFont="添加"},methods:{getApiList:function(){var e=this;Object(l["b"])(this.developerId).then((function(t){200===t.code&&(e.options=t.data.apiList)}))},apiChose:function(e){console.log(this.apivalueList)},submitForm:function(){var e=this;if("添加"===this.buttonFont){for(var t=[{appId:this.appId,count:this.servenum,interval:this.servetime,intervalUnit:this.servetimeValue,path:""}],r=[{appId:this.appId,count:this.servenum,interval:this.servetime,intervalUnit:this.servetimeValue,path:""}],i=0;i<this.apiConfigList.length;i++)""===apivalueList[i]||(r.push({appId:this.appId,path:this.apivalueList[i].apiUrl,count:this.apiNum[i],interval:this.apitime[i],intervalUnit:this.apitimeValue[i]}),t.push({appId:this.appId,path:this.apivalueList[i].apiUrl,count:this.apiNum[i],interval:this.apitime[i],intervalUnit:this.apitimeValue[i],apiId:this.apivalueList[i]}));Object(l["r"])(r).then((function(r){if(200===r.code){var i={pluginType:"sentinel",appCode:e.appCode,appId:e.appId,pluginParams:JSON.stringify(t)};Object(l["m"])(i).then((function(t){200===t.code&&e.$router.push({path:"/serve/serveDetail/"+e.appCode})}))}}))}else{for(var a=[{appId:this.appId,count:this.servenum,interval:this.servetime,intervalUnit:this.servetimeValue,path:""}],o=[{appId:this.appId,count:this.servenum,interval:this.servetime,intervalUnit:this.servetimeValue,path:""}],n=0;n<this.apiConfigList.length;n++)""===apivalueList[n]||(o.push({appId:this.appId,path:this.apivalueList[n].apiUrl,count:this.apiNum[n],interval:this.apitime[n],intervalUnit:this.apitimeValue[n]}),a.push({appId:this.appId,path:this.apivalueList[n].apiUrl,count:this.apiNum[n],interval:this.apitime[n],intervalUnit:this.apitimeValue[n],apiId:this.apivalueList[n]}));Object(l["r"])(o).then((function(t){if(200===t.code){var r={pluginType:"sentinel",appCode:e.appCode,appId:e.appId,id:e.id,enabled:e.enabled,pluginParams:JSON.stringify(a)};Object(l["o"])(r).then((function(t){200===t.code&&e.$router.push({path:"/serve/serveDetail/"+e.appCode})}))}}))}},resetForm:function(){this.$router.push({path:"/serve/serveDetail/"+this.appCode})},handleChange:function(){},apiConfigAdd:function(){this.apiConfigList.push(""),this.apivalueList.push(""),this.apitime.push("0"),this.apitimeValue.push("0"),this.apiNum.push("0")},apiConfigDel:function(e){this.apiConfigList.splice(e,1)}}},B=M,W=(r("0d53"),Object(p["a"])(B,z,R,!1,null,"7e244871",null)),G=W.exports,X={filters:{plugName:function(e){var t={jwt:"Jwt-auth",base_auth:"basic_auth",oauth2:"OAuth2.0",black_list_ip:"IP 黑名单控制",white_list_ip:"IP 白名单控制",cors:"cors跨域",sign:"防篡改签名",replay_attacks:"防网络重放攻击",error_log:"error log",http_log:"http log",sentinel:"sentinel",gzip:"gzip",proxy_cache:"proxy_cache",real_ip:"real_ip",response_rewrite:"response-rewrite"};return e?t[e]:""}},components:{jwtAuth:d,oauth:g,sign:k,black:j,white:H,cors:V,sentinel:G},data:function(){return{pluginName:"",ruleForm:{header:"",key:"",maximum:""},rules:{header:[{required:!0,message:"请输入版本名称",trigger:"blur"}],key:[{required:!0,message:"请输入版本描述",trigger:"blur"}],maximum:[{required:!0,message:"请选择关联API",trigger:"blur"}]}}},created:function(){this.pluginName=this.$route.params.plugincode},methods:{submitForm:function(){},resetForm:function(){},handleChange:function(e){console.log(e)}}},Q=X,Y=Object(p["a"])(Q,i,a,!1,null,null,null);t["default"]=Y.exports},6419:function(e,t,r){},a1ad:function(e,t,r){"use strict";r.d(t,"u",(function(){return a})),r.d(t,"g",(function(){return o})),r.d(t,"v",(function(){return n})),r.d(t,"s",(function(){return l})),r.d(t,"t",(function(){return s})),r.d(t,"d",(function(){return u})),r.d(t,"b",(function(){return p})),r.d(t,"n",(function(){return c})),r.d(t,"h",(function(){return d})),r.d(t,"a",(function(){return m})),r.d(t,"p",(function(){return h})),r.d(t,"e",(function(){return f})),r.d(t,"x",(function(){return b})),r.d(t,"k",(function(){return v})),r.d(t,"w",(function(){return g})),r.d(t,"c",(function(){return F})),r.d(t,"m",(function(){return C})),r.d(t,"q",(function(){return y})),r.d(t,"r",(function(){return _})),r.d(t,"o",(function(){return x})),r.d(t,"j",(function(){return k})),r.d(t,"i",(function(){return O})),r.d(t,"l",(function(){return w})),r.d(t,"f",(function(){return $}));var i=r("b775");function a(e){return Object(i["a"])({url:"/app/list?"+e,method:"get"})}function o(e){return Object(i["a"])({url:"/app/create",method:"post",data:e})}function n(e,t){return Object(i["a"])({url:"/app/"+e,method:"put",data:t})}function l(e){return Object(i["a"])({url:"/app/"+e,method:"delete"})}function s(e){return Object(i["a"])({url:"/app/"+e,method:"get"})}function u(e){return Object(i["a"])({url:"/app/data/"+e+"/appNum",method:"post"})}function p(e){return Object(i["a"])({url:"/app/api/list?developerId="+e,method:"get"})}function c(e,t){return Object(i["a"])({url:"/app/"+e+"/publish",method:"post",data:t})}function d(e){return Object(i["a"])({url:"/app/appVersion/"+e,method:"delete"})}function m(e){return Object(i["a"])({url:"/app/subscribed/list?"+e,method:"get"})}function h(e){return Object(i["a"])({url:"/app/api/queryApiList",method:"post",data:e})}function f(e,t){return Object(i["a"])({url:"/app/appVersion/"+e,method:"post",data:t})}function b(e){return Object(i["a"])({url:"/app/appVersion/"+e,method:"get"})}function v(e){return Object(i["a"])({url:"/gateway/log?"+e,method:"get"})}function g(e){return Object(i["a"])({url:"/app/subscribed/"+e,method:"get"})}function F(e){return Object(i["a"])({url:"/app/api/"+e,method:"get"})}function C(e){return Object(i["a"])({url:"/app/plugin",method:"post",data:e})}function y(){return Object(i["a"])({url:"/app/plugin/randomKey",method:"get"})}function _(e){return Object(i["a"])({url:"/app/rateLimit/save",method:"post",data:e})}function x(e){return Object(i["a"])({url:"/app/plugin",method:"put",data:e})}function k(e){return Object(i["a"])({url:"/app/unSubscribe/"+e,method:"post"})}function O(e,t){return Object(i["a"])({url:"/app/plugin/"+e+"/"+t,method:"get"})}function w(e){return Object(i["a"])({url:"/app/rateLimit/open?appId="+e,method:"post"})}function $(e){return Object(i["a"])({url:"/app/rateLimit/close?appId="+e,method:"post"})}},aeff:function(e,t,r){},bb51:function(e,t,r){},f3e5:function(e,t,r){"use strict";r("6419")},fa21:function(e,t,r){},fcb4:function(e,t,r){}}]);