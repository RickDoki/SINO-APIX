<template>
  <div class="Api_create">
    <div class="top">
      <el-steps :active="active">
        <el-step title="设置上游服务"></el-step>
        <el-step title="设置API信息"></el-step>
      </el-steps>
    </div>
    <div class="middle">
      <div v-if="active === 1" class="formBox">
        <el-form
          ref="form"
          :model="form"
          :rules="rules"
          label-width="130px"
          label-position="left"
          size="small"
        >
          <el-form-item label="已有模板:" prop="name">
            <!-- <el-input
              maxlength="50"
              show-word-limit
              v-model="form.name"
              placeholder="请输入上游服务名称"
              class="inputWidth"
            ></el-input> -->
            <el-select
              v-model="form.name"
              placeholder="请选择上游服务"
              class="inputWidth"
              @change="upstreamChange"
              clearable
            >
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <!-- <el-form-item label="描述：">
            <el-input
              maxlength="500"
              show-word-limit
              class="inputWidth"
              type="textarea"
              v-model="form.description"
              :autosize="{ minRows: 4, maxRows: 6 }"
              placeholder="请输入上游服务描述"
            ></el-input>
          </el-form-item> -->
          <el-form-item label="服务地址：" prop="serverAddress">
            <el-input
              class="inputWidth"
              v-model="form.serverAddress"
              placeholder="请输入服务地址"
              @input="addressFlag = false"
            ></el-input>
            <br /><span
              v-show="addressFlag"
              style="color: #ff4949; font-size: 12px"
              >请输入合法的ip地址或服务地址！</span
            >
          </el-form-item>
          <el-form-item label="服务端口：" prop="port">
            <el-input
              class="inputWidth"
              v-model.number="form.port"
              placeholder="请输入服务端口"
            ></el-input>
          </el-form-item>
          <el-form-item label="路由前置路径：" prop="upstreamPrefixPath">
            <el-input
              v-model="form.upstreamPrefixPath"
              placeholder="请输入路由前置路径"
              class="inputWidth"
            ></el-input>
          </el-form-item>
          <el-form-item label="负载均衡算法：" prop="loadBalance">
            <el-select
              v-model="form.loadBalance"
              placeholder="请输入上游服务的名称"
              class="inputWidth"
            >
              <el-option label="轮询" value="roundRobin"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="协议：" prop="protocol">
            <el-select
              v-model="form.protocol"
              placeholder="请选择上游服务协议"
              class="inputWidth"
            >
              <el-option label="Http" value="http"></el-option>
              <el-option label="Https" value="https"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <template slot="label">
              <span style="position: relative">
                <span>重试次数：</span>
                <el-tooltip class="item" placement="top">
                  <div slot="content">
                    <p>
                      重试机制将请求发到下一个上游<br />节点。值为 0
                      表示禁用重试机制，<br />留空表示使用可用后端节点的数量。
                    </p>
                  </div>
                  <i class="el-icon-question table-msg" />
                </el-tooltip>
              </span>
            </template>
            <el-input v-model="tautologyNum" disabled class="selectWidth" readonly>
              <template slot="append">次</template>
            </el-input>
          </el-form-item>
          <el-form-item>
            <template slot="label">
              <span style="position: relative">
                <span>连接超时：</span>
                <el-tooltip class="item" placement="top">
                  <div slot="content">
                    <p>
                      连接超时时间为系统预设，暂不支持修改。如有需要请联系管理员！
                    </p>
                  </div>
                  <i class="el-icon-question table-msg" />
                </el-tooltip>
              </span>
            </template>
            <el-input v-model="connectNum" disabled class="selectWidth" readonly>
              <template slot="append">s</template>
            </el-input>
          </el-form-item>
          <el-form-item>
            <template slot="label">
              <span style="position: relative">
                <span>发送超时：</span>
                <el-tooltip class="item" placement="top">
                  <div slot="content">
                    <p>
                      发送超时时间为系统预设，暂不支持修改。如有需要请联系管理员！
                    </p>
                  </div>
                  <i class="el-icon-question table-msg" />
                </el-tooltip>
              </span>
            </template>
            <el-input v-model="sendNum" disabled class="selectWidth" readonly>
              <template slot="append">s</template>
            </el-input>
          </el-form-item>
          <el-form-item>
            <template slot="label">
              <span style="position: relative">
                <span>接收超时：</span>
                <el-tooltip class="item" placement="top">
                  <div slot="content">
                    <p>
                      接收超时时间为系统预设，暂不支持修改。如有需要请联系管理员！
                    </p>
                  </div>
                  <i class="el-icon-question table-msg" />
                </el-tooltip>
              </span>
            </template>
            <el-input v-model="receiveNum" disabled class="selectWidth" readonly>
              <template slot="append">s</template>
            </el-input>
          </el-form-item>
        </el-form>
      </div>
      <div class="apiMiddle" v-if="active === 2">
        <el-form
          ref="ruleForm"
          :model="ruleForm"
          :rules="rulesapi"
          label-width="150px"
          class="demo-ruleForm"
        >
          <el-form-item label="API名称:" prop="apiName">
            <el-input
              v-model="ruleForm.apiName"
              size="large"
              maxlength="20"
              style="width: 380px"
              show-word-limit
            />
          </el-form-item>
          <el-form-item label="API路径:" prop="apiUrl">
            <el-input
              v-model="ruleForm.apiUrl"
              size="large"
              style="width: 380px"
              maxlength="500"
              show-word-limit
            />
          </el-form-item>
          <el-form-item label="请求方式:" prop="requestMethod">
            <el-select
              v-model="ruleForm.requestMethod"
              size="large"
              style="width: 380px"
              placeholder=""
            >
              <el-option label="GET" value="GET" />
              <el-option label="POST" value="POST" />
              <el-option label="PUT" value="PUT" />
              <el-option label="DELETE" value="DELETE" />
            </el-select>
          </el-form-item>
          <el-form-item label="API版本号:" prop="apiVersion">
            <el-input
              v-model="ruleForm.apiVersion"
              size="large"
              maxlength="20"
              style="width: 380px"
              show-word-limit
            />
          </el-form-item>
          <!-- <el-form-item label="API地址:" prop="domain">
            <el-input
              v-model="ruleForm.domain"
              size="large"
              maxlength="500"
              style="width: 380px"
              show-word-limit
            />
          </el-form-item> -->
          <el-form-item label="API前置路径:" prop="prefixPath">
            <el-input
              v-model="ruleForm.prefixPath"
              size="large"
              maxlength="500"
              style="width: 380px"
              show-word-limit
            />
          </el-form-item>
          <el-form-item label="是否为中台接口:">
            <el-select
              v-model="ruleForm.isInternal"
              size="large"
              style="width: 380px"
              placeholder=""
            >
              <el-option label="是" value="1" />
              <el-option label="否" value="0" />
            </el-select>
          </el-form-item>
          <el-form-item label="API描述:">
            <el-input
              v-model="ruleForm.description"
              type="textarea"
              style="width: 380px"
              :autosize="{ minRows: 8, maxRows: 15 }"
              maxlength="500"
              show-word-limit
            />
          </el-form-item>
          <el-form-item label="请求参数:">
            <!-- <el-tooltip class="item" effect="dark" content="可通过右键" placement="top">
              <el-button>上边</el-button>
              <svg-icon style="cursor:pointer;color:red" icon-class="wenhao" />
            </el-tooltip> -->
            <div @contextmenu="showMenu">
              <vxe-table
                ref="xTable"
                border
                show-overflow
                :data="requestParams"
                :edit-config="{ trigger: 'click', mode: 'cell' }"
              >
                <vxe-table-column type="checkbox" width="60" />
                <vxe-table-column
                  field="parame"
                  title="参数"
                  :edit-render="{ name: 'input' }"
                />
                <vxe-table-column
                  field="type"
                  title="类型"
                  :edit-render="{
                    name: '$select',
                    options: [
                      { value: 'Integer', label: 'Num' },
                      { value: 'String', label: 'String' },
                      { value: 'Object', label: 'Object' },
                      { value: 'Float', label: 'Float' },
                      { value: 'List', label: 'List' },
                      { value: 'Long', label: 'Long' },
                      { value: 'Boolean', label: 'Boolean' },
                      { value: 'Integer', label: 'Integer' },
                    ],
                  }"
                />
                <vxe-table-column
                  field="isHaveto"
                  title="是否必选"
                  :edit-render="{
                    name: '$select',
                    options: [
                      { value: '是', label: '是' },
                      { value: '否', label: '否' },
                    ],
                  }"
                />
                <vxe-table-column
                  field="describe"
                  title="描述"
                  :edit-render="{ name: 'input' }"
                />
                <vxe-table-column
                  field="default"
                  title="默认值"
                  :edit-render="{ name: 'input' }"
                />
              </vxe-table>
              <div class="table-button">
                <el-button type="primary" size="mini" @click="savedata">
                  新增
                </el-button>
                <el-button type="danger" size="mini" @click="newdata">
                  删除
                </el-button>
              </div>

              <!-- <vue-context-menu
              :contextMenuData="contextMenuData"
              @savedata="savedata"
              @newdata="newdata"
            /> -->
            </div>
          </el-form-item>
          <el-form-item label="请求示例:">
            <prism-editor
              class="my-editor height-300"
              v-model="requestExample"
              :highlight="highlighter"
              :line-numbers="lineNumbers"
            />
          </el-form-item>
           <el-form-item label="返回参数:">
            <!-- <el-tooltip class="item" effect="dark" content="可通过右键" placement="top">
              <el-button>上边</el-button>
              <svg-icon style="cursor:pointer;color:red" icon-class="wenhao" />
            </el-tooltip> -->
            <div @contextmenu="showMenu">
              <vxe-table
                ref="xTableres"
                border
                show-overflow
                :data="responseParams"
                :edit-config="{ trigger: 'click', mode: 'cell' }"
              >
                <vxe-table-column type="checkbox" width="60" />
                <vxe-table-column
                  field="parame"
                  title="参数"
                  :edit-render="{ name: 'input' }"
                />
                <vxe-table-column
                  field="type"
                  title="类型"
                  :edit-render="{
                    name: '$select',
                    options: [
                      { value: 'Integer', label: 'Num' },
                      { value: 'String', label: 'String' },
                      { value: 'Object', label: 'Object' },
                      { value: 'Float', label: 'Float' },
                      { value: 'List', label: 'List' },
                      { value: 'Long', label: 'Long' },
                      { value: 'Boolean', label: 'Boolean' },
                      { value: 'Integer', label: 'Integer' },
                    ],
                  }"
                />
                <vxe-table-column
                  field="isHaveto"
                  title="是否必选"
                  :edit-render="{
                    name: '$select',
                    options: [
                      { value: '是', label: '是' },
                      { value: '否', label: '否' },
                    ],
                  }"
                />
                <vxe-table-column
                  field="describe"
                  title="描述"
                  :edit-render="{ name: 'input' }"
                />
                <vxe-table-column
                  field="default"
                  title="默认值"
                  :edit-render="{ name: 'input' }"
                />
              </vxe-table>
              <div class="table-button">
                <el-button type="primary" size="mini" @click="savedatares">
                  新增
                </el-button>
                <el-button type="danger" size="mini" @click="newdatares">
                  删除
                </el-button>
              </div>

              <!-- <vue-context-menu
              :contextMenuData="contextMenuData"
              @savedata="savedata"
              @newdata="newdata"
            /> -->
            </div>
          </el-form-item>
          <el-form-item label="返回示例:">
            <prism-editor
              class="my-editor height-300"
              v-model="responseExample"
              :highlight="highlighter"
              :line-numbers="lineNumbers"
            />
          </el-form-item>
          <!-- <el-form-item label="API参数:">
          <editor-vue></editor-vue>
          <mavon-editor
            ref="editor"
            v-model="ruleForm.markdown"
            :toolbars="toolbars"
            :subfield="false"
          />
        </el-form-item> -->
        </el-form>
      </div>
    </div>
    <div class="bottom">
      <div class="bottom_button">
        <!-- <div class="newversion">下一步</div> -->
        <div class="user" v-if="active === 1" @click="goNext('form')">
          下一步
        </div>
        <div v-if="active === 2" class="user" @click="goBACK">上一步</div>
        <div
          v-if="active === 1 ? false : true"
          class="newversion"
          @click="addSure('ruleForm')"
        >
          提交
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { getUpstreamList, deleteUpstream } from "@/api/upstream";
import { create, NewVersion, apidetail } from "@/api/AboutApi";

import { PrismEditor } from "vue-prism-editor";
import "vue-prism-editor/dist/prismeditor.min.css"; // import the styles somewhere
// import highlighting library (you can use any library you want just return html string)
import { highlight, languages } from "prismjs/components/prism-core";
import "prismjs/components/prism-clike";
import "prismjs/components/prism-javascript";
import "prismjs/themes/prism-tomorrow.css"; // import syntax highlighting styles
export default {
  components: {
    PrismEditor,
  },
  data() {
    const checkMobile = (rule, value, callback) => {
      // 验证手机号的正则表达式
      const regMobile =
        /(ht|f)tp(s?)|(ws)|(wss)|(lb)\:\/\/[0-9a-zA-Z]([-.\w]*[0-9a-zA-Z])*(:(0-9)*)*(\/?)([a-zA-Z0-9\-\.\?\,\'\/\\\+&amp;%\$#_]*)?/;
      if (regMobile.test(value)) {
        return callback();
      }
      callback(new Error("请输入正确的api地址"));
    };
    return {
      lineNumbers: true,
      readonly: true,
      contextMenuData: {
        // the contextmenu name(@1.4.1 updated)
        menuName: "demo",
        // The coordinates of the display(菜单显示的位置)
        axis: {
          x: null,
          y: null,
        },
        // Menu options (菜单选项)
        menulists: [
          {
            fnHandler: "savedata", // Binding events(绑定事件)
            icoName: "fa fa-home fa-fw", // icon (icon图标 )
            btnName: "新增一行", // The name of the menu option (菜单名称)
          },
          {
            fnHandler: "newdata",
            icoName: "fa fa-home fa-fw",
            btnName: "删除选中 ",
          },
        ],
        
      },
      active: 1,
      tautologyNum: 1,
      connectNum: 30,
      sendNum: 15,
      receiveNum: 15,
      addressFlag: false,
      upstreamList: [],
      options: [],
      requestParams: [],
      responseParams:[],
      requestExample: "",
      responseExample: "",
      form: {
        name: "",
        description: "",
        protocol: "",
        serverAddress: "",
        port: "",
        upstreamPrefixPath: "",
        loadBalance: "roundRobin",
      },
      ruleForm: {
        apiName: "",
        description: "",
        apiUrl: "",
        requestMethod: "",
        apiVersion: "",
        markdown: "",
        domain: "",
        prefixPath: "",
        requestParams: "",
        requestExample: "",
        responseExample: "",
        isInternal: "0",
      },
      rules: {
        // name: [
        //   { required: true, message: "请输入上游服务名称", trigger: "change" },
        // ],
        protocol: [
          { required: true, message: "请选择上游服务协议", trigger: "change" },
        ],
        loadBalance: [
          { required: true, message: "请选择负载均衡算法", trigger: "change" },
        ],
        serverAddress: [
          { required: true, message: "请输入服务地址", trigger: "change" },
        ],
        upstreamPrefixPath: [
          // { pattern: /^\/(\w+\/?)+$/, message: '请输入合法的路径：以"/"开头，允许字母，数字，下划线' }
          {
            pattern: /^\/(?!.*?-$)[a-zA-Z0-9-_/]*$/,
            message:
              '请输入合法的路径：以"/"开头，允许字母，数字，下划线，短横线',
          },
        ],
        port: [
          { required: true, message: "请输入服务端口", trigger: "change" },
          {
            pattern:
              /^([0-9]|[1-9]\d|[1-9]\d{2}|[1-9]\d{3}|[1-5]\d{4}|6[0-4]\d{3}|65[0-4]\d{2}|655[0-2]\d|6553[0-5])$/,
            message: "请输入正确的端口号：1到65535",
          },
        ],
      },
      rulesapi: {
        apiName: [
          { required: true, message: "API名称不能为空", trigger: "change" },
          {
            min: 3,
            max: 20,
            message: "长度在 3 到 20 个字符",
            trigger: "change",
          },
        ],
        apiUrl: [
          { required: true, message: "API路径不能为空", trigger: "change" },
          {
            min: 3,
            max: 500,
            message: "长度在 3 到 500 个字符",
            trigger: "change",
          },
        ],
        requestMethod: [
          { required: true, message: "请求方式不能为空", trigger: "change" },
        ],
        apiVersion: [
          { required: true, message: "版本号不能为空", trigger: "change" },
        ],
        // domain: [
        //   { required: true, message: "api服务地址不能为空", trigger: "change" },
        //   {
        //     min: 3,
        //     max: 500,
        //     message: "长度在 3 到 500 个字符",
        //     trigger: "change",
        //   },
        //   { validator: checkMobile, trigger: "change" },
        // ],
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    addSure(formName) {
      console.log(JSON.stringify(this.$refs.xTable.afterFullData))
      console.log(JSON.stringify(this.$refs.xTableres.afterFullData))

      this.$refs[formName].validate((valid) => {
        if (valid) {
          console.log(this.form);
          console.log(this.ruleForm);
          const params = {
            apiName: this.ruleForm.apiName,
            description: this.ruleForm.description,
            domain: this.form.serverAddress,
            apiUrl: this.ruleForm.apiUrl,
            requestMethod: this.ruleForm.requestMethod,
            requestParams: JSON.stringify(this.$refs.xTable.afterFullData),
            responseParams: JSON.stringify(this.$refs.xTableres.afterFullData),
            requestExample:JSON.stringify(this.requestExample),
            responseExample: JSON.stringify(this.responseExample),
            apiVersion:this.ruleForm.apiVersion,
            isInternal:this.ruleForm.isInternal,
            upstreamPrefixPath: this.form.upstreamPrefixPath,
            prefixPath:this.ruleForm.prefixPath,
            protocol:this.form.protocol,
            loadBalance:this.form.loadBalance,
            port:this.form.port
          };
          create(params).then((res) => {
            // console.log(res)
            if (res.code === 200) {
              this.$message({
                message: res.msg,
                type: "success",
              });
              this.$router.push("/api/list");
            } else {
              this.ruleForm = {};
              this.$message({
                message: res.msg,
                type: "error",
              });
            }
          });
        }
      });
    },
    goBACK() {
      this.active = 1;
      this.$refs['ruleForm'].resetFields();
    },
    highlighter(code) {
      return highlight(code, languages.js);
    },
    // 新增行
    async insertEvent() {
      const row = -1;
      const $table = this.$refs.xTable;
      const record = {
        parame: "",
        type: "",
        isHaveto: "",
        describe: "",
        default: "",
      };
      const { row: newRow } = await $table.insertAt(record, row);
      await $table.setActiveCell(newRow, "parame");
    },
    async insertEventres() {
      const row = -1;
      const $table = this.$refs.xTableres;
      const record = {
        parame: "",
        type: "",
        isHaveto: "",
        describe: "",
        default: "",
      };
      const { row: newRow } = await $table.insertAt(record, row);
      await $table.setActiveCell(newRow, "parame");
    },
    showMenu() {
      event.preventDefault();
      var x = event.clientX;
      var y = event.clientY;
      this.contextMenuData.axis = {
        x,
        y,
      };
    },
    savedata() {
      // 新增一列
      this.insertEvent();
    },
    savedatares() {
      // 新增一列
      this.insertEventres();
    },
    newdata() {
      // 删除一列
      this.$refs.xTable.removeCheckboxRow();
    },
    newdatares() {
      // 删除一列
      this.$refs.xTableres.removeCheckboxRow();
    },
    // 校验ip
    isValidIP(ip) {
      var reg =
        /^((2[0-4]\d|25[0-5]|[01]?\d\d?)\.){3}(2[0-4]\d|25[0-5]|[01]?\d\d?)$/;
      return reg.test(ip);
    },
    // 校验域名
    isValidWeb(web) {
      var reg =
        /^(?=^.{3,255}$)[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+$/;
      return reg.test(web);
    },
    getList() {
      getUpstreamList("").then((res) => {
        if (res.code === 200) {
          const array = res.data.upstreamList;
          this.upstreamList = res.data.upstreamList;
          array.forEach((items) => {
            this.options.push({
              value: items.name,
              label: items.name,
            });
          });
        }
      });
    },
    upstreamChange() {
      console.log("change");
      console.log(this.form.name);
      if (this.form.name === "") {
        console.log("ppp");
        this.form = {
          name: "",
          description: "",
          protocol: "",
          serverAddress: "",
          port: "",
          upstreamPrefixPath: "",
          loadBalance: "roundRobin",
        };
      } else {
        for (let index = 0; index < this.upstreamList.length; index++) {
          if ((this.upstreamList[index].name == this.form.name)) {
            this.form = {
              name: this.form.name,
              description: this.upstreamList[index].description,
              protocol: this.upstreamList[index].protocol,
              serverAddress: this.upstreamList[index].server_address,
              port: this.upstreamList[index].port,
              upstreamPrefixPath: this.upstreamList[index].prefix_path,
              loadBalance: "roundRobin",
            };
          }
        }
      }
    },
    goNext(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          const ipTest = this.isValidIP(this.form.serverAddress);
          const webTest = this.isValidWeb(this.form.serverAddress);
          if (!ipTest && !webTest) {
            this.$message("请输入合法的ip地址或服务地址！");
            this.addressFlag = true;
            return;
          }
          this.active = 2;
        } else {
          return;
        }
      });
    },
  },
};
</script>

<style lang='scss' scoped>
.Api_create {
  margin: 10px;
  margin-bottom: 70px;
  background-color: #fff;
  min-height: calc(100vh - 185px);

  .top {
    padding: 10px 54px;
  }
  .formBox {
    // margin-top: 30px;
    padding: 0px 54px 20px 54px;
    .contentDiv {
      display: flex;
      .item-div {
        margin-left: 20px;
      }
    }
    .item {
      position: absolute;
      right: -10px;
      top: 3px;
    }
    .addDiv {
      margin-top: 10px;
    }
    .add_span {
      color: #2c66fb;
    }
    .inputWidth {
      width: 618px;
    }
    .selectWidth {
      width: 295px;
    }
    .formBut {
      text-align: right;
    }
    .numberWdith {
      width: 180px;
    }
    .numberWdith2 {
      width: 205px;
    }
    .divider {
      display: flex;
      line-height: 45px;
      margin: 20px 0px;
      .dividerTitle {
        font-size: 16px;
        font-weight: 500;
        margin-right: 20px;
        width: 90px;
      }
    }
  }
}
.bottom {
  position: fixed;
  width: calc(100% - 210px);
  height: 50px;
  background-color: #fff;
  bottom: 0px;
  right: 0px;
  .bottom_button {
    float: right;
    margin-right: 50px;
    margin-top: 10px;
    div {
      display: inline-block;
      padding: 5px 8px;
      margin-right: 20px;
      cursor: pointer;
    }
    .newversion {
      background-color: #2c66fb;
      color: #fff;
      border-radius: 3px;

      border: 1px solid #2c66fb;
    }
    .user {
      border: 1px solid #2c66fb;
      color: #2c66fb;
      border-radius: 3px;

    }
    .del {
      border: 1px solid #f5222d;
      color: #f5222d;
      border-radius: 3px;

    }
  }
}
::v-deep .vue-contextmenu-listWrapper {
  padding: 0px;
  .context-menu-list {
    // line-height: 16px;
    // height: 32px;
    div {
      .no-child-btn {
        padding: 0px !important;
      }
    }
  }
  .context-menu-list:hover {
    background: #eee !important;
  }
}
.my-editor {
  background: #2d2d2d;
  color: #ccc;
  border: 0px;
  font-family: Fira code, Fira Mono, Consolas, Menlo, Courier, monospace;
  font-size: 14px;
  line-height: 1.5;
  padding: 5px;
}
/* optional */
.prism-editor__textarea:focus {
  outline: none;
}
/* not required: */
.height-300 {
  height: 150px;
}
.table-button {
  margin-top: 10px;
}
.apiMiddle {
  padding: 0px 20px 20px 0px;
}
</style>