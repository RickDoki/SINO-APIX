(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-3d9786ef"],{"504c":function(t,e,a){"use strict";a("f670")},adee:function(t,e,a){},bb0d:function(t,e,a){"use strict";a.r(e);var s=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"main"},[t._m(0),a("div",{staticClass:"formBox"},[a("el-form",{ref:"form",attrs:{model:t.form,rules:t.rules,"label-width":"130px","label-position":"top",size:"small"}},[a("el-form-item",{attrs:{label:"名称",prop:"name"}},[a("el-input",{staticClass:"inputWidth",attrs:{maxlength:"50","show-word-limit":"",placeholder:"请输入上游服务名称"},model:{value:t.form.name,callback:function(e){t.$set(t.form,"name",e)},expression:"form.name"}})],1),a("el-form-item",{attrs:{label:"描述"}},[a("el-input",{staticClass:"inputWidth",attrs:{maxlength:"500","show-word-limit":"",type:"textarea",autosize:{minRows:4,maxRows:6},placeholder:"请输入上游服务描述"},model:{value:t.form.description,callback:function(e){t.$set(t.form,"description",e)},expression:"form.description"}})],1),a("el-form-item",{attrs:{label:"服务地址",prop:"serverAddress"}},[a("el-input",{staticClass:"inputWidth",attrs:{placeholder:"请输入服务地址"},on:{input:function(e){t.addressFlag=!1}},model:{value:t.form.serverAddress,callback:function(e){t.$set(t.form,"serverAddress",e)},expression:"form.serverAddress"}}),a("br"),a("span",{directives:[{name:"show",rawName:"v-show",value:t.addressFlag,expression:"addressFlag"}],staticStyle:{color:"#ff4949","font-size":"12px"}},[t._v("请输入合法的ip地址或服务地址！")])],1),a("el-form-item",{attrs:{label:"服务端口",prop:"port"}},[a("el-input",{staticClass:"inputWidth",attrs:{placeholder:"请输入服务端口"},model:{value:t.form.port,callback:function(e){t.$set(t.form,"port",t._n(e))},expression:"form.port"}})],1),a("el-form-item",{attrs:{label:"路由前置路径",prop:"prefixPath"}},[a("el-input",{staticClass:"inputWidth",attrs:{placeholder:"请输入路由前置路径"},model:{value:t.form.prefixPath,callback:function(e){t.$set(t.form,"prefixPath",e)},expression:"form.prefixPath"}})],1),t._e(),a("el-form-item",{attrs:{label:"协议",prop:"protocol"}},[a("el-select",{staticClass:"inputWidth",attrs:{placeholder:"请选择上游服务协议"},model:{value:t.form.protocol,callback:function(e){t.$set(t.form,"protocol",e)},expression:"form.protocol"}},[a("el-option",{attrs:{label:"Http",value:"http"}}),a("el-option",{attrs:{label:"Https",value:"https"}})],1),a("span",{staticClass:"show-but",on:{click:t.changeShow}},[t._v("展示剩余四项配置 "),t.showTimeFlag?a("i",{staticClass:"el-icon-arrow-up"}):a("i",{staticClass:"el-icon-arrow-down"})])],1),a("div",{directives:[{name:"show",rawName:"v-show",value:t.showTimeFlag,expression:"showTimeFlag"}]},[a("el-form-item",[a("template",{slot:"label"},[a("span",{staticStyle:{position:"relative"}},[a("span",[t._v("重试次数")]),a("el-tooltip",{staticClass:"item",attrs:{placement:"top"}},[a("div",{attrs:{slot:"content"},slot:"content"},[a("p",[t._v("重试机制将请求发到下一个上游"),a("br"),t._v("节点。值为 0 表示禁用重试机制，"),a("br"),t._v("留空表示使用可用后端节点的数量。")])]),a("i",{staticClass:"el-icon-question table-msg"})])],1)]),a("el-input",{staticClass:"inputWidth",attrs:{readonly:""},model:{value:t.tautologyNum,callback:function(e){t.tautologyNum=e},expression:"tautologyNum"}},[a("template",{slot:"append"},[t._v("次")])],2)],2),a("el-form-item",[a("template",{slot:"label"},[a("span",{staticStyle:{position:"relative"}},[a("span",[t._v("连接超时")]),a("el-tooltip",{staticClass:"item",attrs:{placement:"top"}},[a("div",{attrs:{slot:"content"},slot:"content"},[a("p",[t._v("连接超时时间为系统预设，暂不支持修改。如有需要请联系管理员！")])]),a("i",{staticClass:"el-icon-question table-msg"})])],1)]),a("el-input",{staticClass:"inputWidth",attrs:{readonly:""},model:{value:t.connectNum,callback:function(e){t.connectNum=e},expression:"connectNum"}},[a("template",{slot:"append"},[t._v("s")])],2)],2),a("el-form-item",[a("template",{slot:"label"},[a("span",{staticStyle:{position:"relative"}},[a("span",[t._v("发送超时")]),a("el-tooltip",{staticClass:"item",attrs:{placement:"top"}},[a("div",{attrs:{slot:"content"},slot:"content"},[a("p",[t._v("发送超时时间为系统预设，暂不支持修改。如有需要请联系管理员！")])]),a("i",{staticClass:"el-icon-question table-msg"})])],1)]),a("el-input",{staticClass:"inputWidth",attrs:{readonly:""},model:{value:t.sendNum,callback:function(e){t.sendNum=e},expression:"sendNum"}},[a("template",{slot:"append"},[t._v("s")])],2)],2),a("el-form-item",[a("template",{slot:"label"},[a("span",{staticStyle:{position:"relative"}},[a("span",[t._v("接收超时")]),a("el-tooltip",{staticClass:"item",attrs:{placement:"top"}},[a("div",{attrs:{slot:"content"},slot:"content"},[a("p",[t._v("接收超时时间为系统预设，暂不支持修改。如有需要请联系管理员！")])]),a("i",{staticClass:"el-icon-question table-msg"})])],1)]),a("el-input",{staticClass:"inputWidth",attrs:{readonly:""},model:{value:t.receiveNum,callback:function(e){t.receiveNum=e},expression:"receiveNum"}},[a("template",{slot:"append"},[t._v("s")])],2)],2)],1)],1)],1),a("div",{staticClass:"bottom"},[a("div",{staticClass:"bottom_button_a"},[a("el-button",{attrs:{size:"small"},on:{click:t.goBack}},[t._v("取消")]),a("el-button",{attrs:{size:"small",type:"primary"},on:{click:function(e){return t.submitForm("form")}}},[t._v("提交")])],1)])])},o=[function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"list_top"},[a("div",{staticClass:"list_title"},[t._v("创建上游服务")])])}],i=(a("b0c0"),a("a4d3"),a("e01a"),a("ecbf")),r={components:{},data:function(){var t=this,e=function(e,a,s){var o=t.isValidIP(a),i=t.isValidWeb(a);o||i?s():s(new Error("请输入合法的ip地址或域名, 域名不需要加http等前缀"))};return{showTimeFlag:!1,active:0,tautologyNum:1,connectNum:30,sendNum:15,receiveNum:15,editFlag:!1,upstreamId:0,addressFlag:!1,form:{name:"",description:"",protocol:"",serverAddress:"",port:"",prefixPath:"",loadBalance:"roundRobin"},rules:{name:[{required:!0,message:"请输入上游服务名称",trigger:"blur"}],protocol:[{required:!0,message:"请选择上游服务协议",trigger:"change"}],loadBalance:[{required:!0,message:"请选择负载均衡算法",trigger:"change"}],serverAddress:[{required:!0,message:"请输入服务地址",trigger:"blur"},{required:!0,validator:e,trigger:"blur"}],prefixPath:[{pattern:/^\/(?!.*?-$)[a-zA-Z0-9-_/]*$/,message:'请输入合法的路径：以"/"开头，允许字母，数字，下划线，短横线',trigger:"blur"}],port:[{required:!0,message:"请输入服务端口",trigger:"blur"},{pattern:/^([0-9]|[1-9]\d|[1-9]\d{2}|[1-9]\d{3}|[1-5]\d{4}|6[0-4]\d{3}|65[0-4]\d{2}|655[0-2]\d|6553[0-5])$/,message:"请输入正确的端口号：1到65535"}]}}},created:function(){this.$route.params.id&&(this.upstreamId=this.$route.params.id,this.editFlag=!0,this.getUpstreamInfo(this.upstreamId))},methods:{changeShow:function(){this.showTimeFlag=!this.showTimeFlag},isValidIP:function(t){var e=/^((2[0-4]\d|25[0-5]|[01]?\d\d?)\.){3}(2[0-4]\d|25[0-5]|[01]?\d\d?)$/;return e.test(t)},isValidWeb:function(t){var e=/^(?=^.{3,255}$)[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+$/;return e.test(t)},goBack:function(){this.$router.push({path:"/api/upstream"})},step:function(){0!==this.active&&this.active-- >1&&(this.active=0)},next:function(){2!==this.active&&this.active++>2&&(this.active=0)},getUpstreamInfo:function(t){var e=this;Object(i["c"])(t).then((function(t){if(200===t.code){var a=t.data;e.form={name:a.name,description:a.description,protocol:a.protocol,serverAddress:a.serverAddress,port:a.port,prefixPath:a.prefixPath,loadBalance:a.loadBalance}}}))},submitForm:function(t){var e=this;this.$refs[t].validate((function(t){t&&(0===e.upstreamId?Object(i["a"])(e.form).then((function(t){200===t.code&&e.goBack()})):(e.form.id=e.upstreamId,Object(i["e"])(e.upstreamId,e.form).then((function(t){200===t.code&&e.goBack()}))))}))}}},l=r,n=(a("c490"),a("504c"),a("2877")),c=Object(n["a"])(l,s,o,!1,null,"7c82adea",null);e["default"]=c.exports},c490:function(t,e,a){"use strict";a("adee")},ecbf:function(t,e,a){"use strict";a.d(e,"d",(function(){return o})),a.d(e,"a",(function(){return i})),a.d(e,"c",(function(){return r})),a.d(e,"e",(function(){return l})),a.d(e,"b",(function(){return n}));var s=a("b775");function o(t){return Object(s["a"])({url:"/app/upstream/list"+t,method:"get"})}function i(t){return Object(s["a"])({url:"/app/upstream",method:"post",data:t})}function r(t){return Object(s["a"])({url:"/app/upstream/"+t,method:"get"})}function l(t,e){return Object(s["a"])({url:"/app/upstream/"+t,method:"put",data:e})}function n(t){return Object(s["a"])({url:"/app/upstream/"+t,method:"delete"})}},f670:function(t,e,a){}}]);