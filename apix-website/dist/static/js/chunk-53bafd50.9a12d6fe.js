(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-53bafd50"],{"3f22":function(e,r,s){e.exports=s.p+"static/img/img_logo.5b479ea5.png"},"58ec":function(e,r,s){"use strict";s("e2de")},"9ed6":function(e,r,s){"use strict";s.r(r);var t=function(){var e=this,r=e.$createElement,s=e._self._c||r;return s("div",{staticClass:"login-main"},[e._m(0),s("div",{staticClass:"main-center"},[e._m(1),s("div",{staticClass:"center-welcome"},[e._v("欢迎使用博冀开放服务平台")]),s("div",{staticClass:"center-form"},[s("div",{directives:[{name:"show",rawName:"v-show",value:e.isLogin,expression:"isLogin"}]},[s("el-form",{ref:"ruleForm",attrs:{model:e.ruleForm,rules:e.rules}},[s("el-form-item",{attrs:{prop:"username"}},[s("el-input",{attrs:{placeholder:"请输入账号"},model:{value:e.ruleForm.username,callback:function(r){e.$set(e.ruleForm,"username",r)},expression:"ruleForm.username"}})],1),s("el-form-item",{staticClass:"paws-input",attrs:{prop:"password"}},[s("el-input",{attrs:{"show-password":"",placeholder:"请输入密码"},model:{value:e.ruleForm.password,callback:function(r){e.$set(e.ruleForm,"password",r)},expression:"ruleForm.password"}})],1)],1),s("div",{staticClass:"right_handle"},[s("div",{staticClass:"handle_left"},[s("el-checkbox",{model:{value:e.checked,callback:function(r){e.checked=r},expression:"checked"}},[e._v("记住密码")])],1),s("div",{staticClass:"handle_right"},[s("span",{directives:[{name:"show",rawName:"v-show",value:!1,expression:"false"}]},[e._v("忘记密码 ?")])])]),s("el-button",{staticClass:"dl-but",attrs:{type:"primary",size:"small"},on:{click:function(r){return e.login("ruleForm")}}},[e._v("立即登录")]),s("div",{staticClass:"handle_bom"},[s("span",{on:{click:function(r){return e.goOther()}}},[e._v("没有账号？"),s("span",{staticClass:"goreg-col"},[e._v("去注册")])])])],1),s("div",{directives:[{name:"show",rawName:"v-show",value:!e.isLogin,expression:"!isLogin"}]},[s("el-form",{ref:"re_ruleForm",attrs:{model:e.re_ruleForm,rules:e.re_rules}},[s("el-form-item",{attrs:{prop:"username"}},[s("el-input",{attrs:{placeholder:"请输入用户名"},model:{value:e.re_ruleForm.username,callback:function(r){e.$set(e.re_ruleForm,"username",r)},expression:"re_ruleForm.username"}})],1),s("el-form-item",{attrs:{prop:"mobile"}},[s("el-input",{attrs:{placeholder:"请输入手机号"},model:{value:e.re_ruleForm.mobile,callback:function(r){e.$set(e.re_ruleForm,"mobile",r)},expression:"re_ruleForm.mobile"}})],1),s("el-form-item",{attrs:{prop:"email"}},[s("el-input",{attrs:{placeholder:"请输入邮箱"},model:{value:e.re_ruleForm.email,callback:function(r){e.$set(e.re_ruleForm,"email",r)},expression:"re_ruleForm.email"}})],1),s("el-form-item",{attrs:{prop:"password"}},[s("el-input",{attrs:{"show-password":"",placeholder:"请输入密码"},model:{value:e.re_ruleForm.password,callback:function(r){e.$set(e.re_ruleForm,"password",r)},expression:"re_ruleForm.password"}})],1)],1),s("el-button",{staticClass:"zc-but",attrs:{type:"primary",size:"small"},on:{click:function(r){return e.register("re_ruleForm")}}},[e._v("立即注册")]),s("div",{staticClass:"handle_bom"},[s("span",{on:{click:function(r){return e.goOther()}}},[e._v("已有账号？"),s("span",{staticClass:"goreg-col"},[e._v("去登录")])])])],1)])]),e._m(2)])},a=[function(){var e=this,r=e.$createElement,t=e._self._c||r;return t("div",{staticClass:"main-top"},[t("img",{attrs:{src:s("4da1"),alt:""}})])},function(){var e=this,r=e.$createElement,t=e._self._c||r;return t("div",{staticClass:"center-logo"},[t("img",{attrs:{src:s("3f22"),alt:""}})])},function(){var e=this,r=e.$createElement,s=e._self._c||r;return s("div",{staticClass:"main-bottom"},[s("span",[e._v("Copyright © 2021 上海博冀信息科技有限公司")])])}],o=(s("ac1f"),s("5319"),s("5f87")),l=s("c24f"),i={data:function(){return{ruleForm:{username:"",password:""},rules:{username:{required:!0,message:"请输入账号",trigger:"blur"},password:{required:!0,message:"请输入密码",trigger:"blur"}},re_ruleForm:{mobile:"",password:"",email:"",username:""},re_rules:{username:[{required:!0,message:"请输入用户名",trigger:"blur"},{min:2,max:20,message:"长度在 2 到 20 个字符",trigger:"change"}],mobile:{required:!0,message:"请输入手机号",trigger:"blur"},email:[{required:!0,message:"请输入邮箱",trigger:"blur"},{type:"email",message:"请输入正确的邮箱地址",trigger:"change"}],password:{required:!0,message:"请输入密码",trigger:"blur"}},checked:!1,isLogin:!0}},computed:{},methods:{goOther:function(){this.isLogin=!this.isLogin},register:function(e){var r=this;this.$refs[e].validate((function(e){if(!e)return!1;console.log(r.re_ruleForm);var s={mobile:r.re_ruleForm.mobile,password:btoa(r.re_ruleForm.password),email:r.re_ruleForm.email,username:r.re_ruleForm.username};console.log(s),Object(l["b"])(s).then((function(e){console.log(e),200===e.code?(r.isLogin=!0,r.$message({message:e.msg,type:"success"})):r.$message({message:e.msg,type:"error"})}))}))},login:function(e){var r=this;this.$refs[e].validate((function(e){if(!e)return console.log("error submit!!"),!1;r.checked?(Object(o["c"])("name",r.ruleForm.username),Object(o["c"])("word",btoa(r.ruleForm.password)),Object(o["c"])("ischecked",!0)):Object(o["c"])("ischecked",!1);var s={username:r.ruleForm.username,password:btoa(r.ruleForm.password)};Object(l["a"])(s).then((function(e){console.log(e),200===e.code?(Object(o["c"])("token",e.data.token),Object(o["c"])("userId_api",e.data.userId),Object(o["c"])("apiPhone",e.data.mobile),Object(o["c"])("userName_api",e.data.username),r.$route.query.path&&r.$route.query.code?r.$router.push({path:r.$route.query.path,query:{code:r.$route.query.code}}):r.$route.query.path?r.$router.push(r.$route.query.path):r.$router.push("/dashboard/index")):r.$message({message:e.msg,type:"error"})}))}))}},created:function(){Object(o["b"])("apiPhone"),Object(o["b"])("token"),Object(o["b"])("userId_api"),Object(o["b"])("userName_api");var e=Object(o["a"])("ischecked");if("true"===e&&(this.ruleForm={username:Object(o["a"])("name"),password:atob(Object(o["a"])("word"))},this.checked=!0),-1!=this.$route.fullPath.indexOf("mobile")){var r=decodeURIComponent(this.$route.fullPath.replace("/login?mobile=",""));console.log(r),this.isLogin=!1,this.re_ruleForm.mobile=r}}},n=i,u=(s("58ec"),s("2877")),c=Object(u["a"])(n,t,a,!1,null,"0ffb1031",null);r["default"]=c.exports},e2de:function(e,r,s){}}]);