<template>
  <div class="Api_create">
    <div class="top">
      <elx-steps-horizontal
        v-model="active"
        :abstracts="panelTitles"
        @change="stepChange"
      />
    </div>
    <div class="middle">
      <div v-if="active === 0" class="formBox">
        <el-form
          ref="form"
          :model="form"
          :rules="rules"
          label-width="130px"
          label-position="top"
          size="small"
        >
          <el-form-item label="已有模板" prop="name">
            <el-select
              v-model="form.name"
              placeholder="请选择上游服务"
              class="inputWidth"
              clearable
              @change="upstreamChange"
            >
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="服务地址" prop="serverAddress">
            <el-input
              v-model="form.serverAddress"
              class="inputWidth"
              placeholder="请输入服务地址"
              @input="addressFlag = false"
            />
            <br><span
              v-show="addressFlag"
              style="color: #ff4949; font-size: 12px"
            >请输入合法的ip地址或服务地址！</span>
          </el-form-item>
          <el-form-item label="服务端口" prop="port">
            <el-input
              v-model.number="form.port"
              class="inputWidth"
              placeholder="请输入服务端口"
            />
          </el-form-item>
          <el-form-item label="路由前置路径" prop="upstreamPrefixPath">
            <el-input
              v-model="form.upstreamPrefixPath"
              placeholder="请输入路由前置路径"
              class="inputWidth"
            />
          </el-form-item>
          <el-form-item label="负载均衡算法" prop="loadBalance" v-show="false">
            <el-select
              v-model="form.loadBalance"
              placeholder="请输入上游服务的名称"
              class="inputWidth"
            >
              <el-option label="轮询" value="roundRobin" />
            </el-select>
          </el-form-item>
          <el-form-item label="协议" prop="protocol">
            <el-select
              v-model="form.protocol"
              placeholder="请选择上游服务协议"
              class="inputWidth"
            >
              <el-option label="Http" value="http" />
              <el-option label="Https" value="https" />
            </el-select>
            <span @click="changeShow" class="show-but">展示剩余四项配置 <i class="el-icon-arrow-down" v-if="!showTimeFlag"></i><i class="el-icon-arrow-up" v-else></i></span>
          </el-form-item>
          <div v-show="showTimeFlag">
            <el-form-item>
              <template slot="label">
                <span style="position: relative">
                  <span>重试次数</span>
                  <el-tooltip class="item" placement="top">
                    <div slot="content">
                      <p>
                        重试机制将请求发到下一个上游<br>节点。值为 0
                        表示禁用重试机制，<br>留空表示使用可用后端节点的数量。
                      </p>
                    </div>
                    <i class="el-icon-question table-msg" />
                  </el-tooltip>
                </span>
              </template>
              <el-input v-model="tautologyNum" class="inputWidth" readonly>
                <template slot="append">次</template>
              </el-input>
            </el-form-item>
            <el-form-item>
              <template slot="label">
                <span style="position: relative">
                  <span>连接超时</span>
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
              <el-input v-model="connectNum" class="inputWidth" readonly>
                <template slot="append">s</template>
              </el-input>
            </el-form-item>
            <el-form-item>
              <template slot="label">
                <span style="position: relative">
                  <span>发送超时</span>
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
              <el-input v-model="sendNum" class="inputWidth" readonly>
                <template slot="append">s</template>
              </el-input>
            </el-form-item>
            <el-form-item>
              <template slot="label">
                <span style="position: relative">
                  <span>接收超时</span>
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
              <el-input v-model="receiveNum" class="inputWidth" readonly>
                <template slot="append">s</template>
              </el-input>
            </el-form-item>
          </div>
        </el-form>
      </div>
      <div v-if="active === 1" class="formBox">
        <el-form
          ref="ruleForm"
          :model="ruleForm"
          :rules="rulesapi"
          label-width="150px"
          label-position="top"
          size="small"
        >
          <el-form-item label="API名称" prop="apiName">
            <el-input
              v-model="ruleForm.apiName"
              maxlength="20"
              class="inputWidth"
              show-word-limit
            />
          </el-form-item>
          <el-form-item label="API路径" prop="apiUrl">
            <el-input
              v-model="ruleForm.apiUrl"
              class="inputWidth"
              maxlength="500"
              show-word-limit
            />
          </el-form-item>
          <el-form-item label="请求方式" prop="requestMethod">
            <el-select
              v-model="ruleForm.requestMethod"
              class="inputWidth"
              placeholder=""
            >
              <el-option label="GET" value="GET" />
              <el-option label="POST" value="POST" />
              <el-option label="PUT" value="PUT" />
              <el-option label="DELETE" value="DELETE" />
            </el-select>
          </el-form-item>
          <!-- <el-form-item label="API版本号" prop="apiVersion">
            <el-input
              v-model="ruleForm.apiVersion"
              maxlength="20"
              class="inputWidth"
              show-word-limit
            />
          </el-form-item> -->
          <el-form-item label="API前置路径" prop="prefixPath">
            <el-input
              v-model="ruleForm.prefixPath"
              maxlength="500"
              class="inputWidth"
              show-word-limit
            />
          </el-form-item>
          <el-form-item label="是否为中台接口">
            <el-select
              v-model="ruleForm.isInternal"
              class="inputWidth"
              placeholder=""
            >
              <el-option label="是" value="1" />
              <el-option label="否" value="0" />
            </el-select>
          </el-form-item>
          <el-form-item label="API描述">
            <el-input
              v-model="ruleForm.description"
              type="textarea"
              class="inputWidth"
              :autosize="{ minRows: 8, maxRows: 15 }"
              maxlength="500"
              show-word-limit
            />
          </el-form-item>
          <el-form-item label="请求参数">
            <div @contextmenu="showMenu" class="content-boder">
              <vxe-table
                :header-cell-style="{backgroundColor:'#fff',color:'#494E6A'}"
                ref="xTable"
                border="none"
                max-height="280"
                show-overflow
                stripe
                :row-config="{isCurrent: true, isHover: true}"
                :data="requestParams"
                :edit-config="{ trigger: 'click', mode: 'cell', showIcon: false }"
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
                <el-button size="mini" @click="savedata" class="add-but">
                  新增
                </el-button>
                <el-button size="mini" @click="newdata" class="del-but">
                  删除
                </el-button>
              </div>
            </div>
          </el-form-item>
          <el-form-item label="请求示例">
            <prism-editor
              v-model="requestExample"
              class="my-editor height-300"
              :highlight="highlighter"
              :line-numbers="lineNumbers"
            />
          </el-form-item>
          <el-form-item label="返回参数">
            <div @contextmenu="showMenu" class="content-boder">
              <vxe-table
                :header-cell-style="{backgroundColor:'#fff',color:'#494E6A'}"
                ref="xTableres"
                border="none"
                max-height="280"
                show-overflow
                stripe
                :row-config="{isCurrent: true, isHover: true}"
                :data="responseParams"
                :edit-config="{ trigger: 'click', mode: 'cell', showIcon: false  }"
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
                <el-button size="mini" @click="savedatares" class="add-but">
                  新增
                </el-button>
                <el-button size="mini" @click="newdatares" class="del-but">
                  删除
                </el-button>
              </div>
            </div>
          </el-form-item>
          <el-form-item label="返回示例">
            <prism-editor
              v-model="responseExample"
              class="my-editor height-300"
              :highlight="highlighter"
              :line-numbers="lineNumbers"
            />
          </el-form-item>
        </el-form>
      </div>
    </div>
    <div class="bottom">
      <div class="bottom_button">
        <el-button size="small" v-if="active === 0" @click="backList">取消</el-button>
        <el-button size="small" type="primary" v-if="active === 0" @click="goNext('form')">下一步</el-button>
      </div>
      <div class="bottom_button_b">
        <el-button size="small" v-if="active === 1" @click="goBACK">上一步</el-button>
        <el-button size="small" type="primary" v-if="active === 0 ? false : true" @click="addSure('ruleForm')">提交</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import ElxStepsHorizontal from '@/components/ElxStepsHorizontal'
import { getUpstreamList } from '@/api/upstream'
import { create } from '@/api/AboutApi'
import { PrismEditor } from 'vue-prism-editor'
import 'vue-prism-editor/dist/prismeditor.min.css' // import the styles somewhere
import { highlight, languages } from 'prismjs/components/prism-core'
import 'prismjs/components/prism-clike'
import 'prismjs/components/prism-javascript'
import 'prismjs/themes/prism-tomorrow.css' // import syntax highlighting styles
export default {
  components: {
    PrismEditor,
    ElxStepsHorizontal
  },
  data () {
    const checkMobile = (rule, value, callback) => {
      // 验证手机号的正则表达式
      const regMobile =
        /(ht|f)tp(s?)|(ws)|(wss)|(lb)\:\/\/[0-9a-zA-Z]([-.\w]*[0-9a-zA-Z])*(:(0-9)*)*(\/?)([a-zA-Z0-9\-\.\?\,\'\/\\\+&amp;%\$#_]*)?/
      if (regMobile.test(value)) {
        return callback()
      }
      callback(new Error('请输入正确的api地址'))
    }
    return {
      // 展示折叠配置
      showTimeFlag: false,
      // 默认步骤数
      active: 0,
      panelTitles: ['设置上游服务', '设置API信息'], // 根据需要传入，可N步
      lineNumbers: true,
      readonly: true,
      contextMenuData: {
        menuName: 'demo',
        axis: {
          x: null,
          y: null
        },
        menulists: [
          {
            fnHandler: 'savedata', // Binding events(绑定事件)
            icoName: 'fa fa-home fa-fw', // icon (icon图标 )
            btnName: '新增一行' // The name of the menu option (菜单名称)
          },
          {
            fnHandler: 'newdata',
            icoName: 'fa fa-home fa-fw',
            btnName: '删除选中 '
          }
        ]
      },
      tautologyNum: 1,
      connectNum: 30,
      sendNum: 15,
      receiveNum: 15,
      addressFlag: false,
      upstreamList: [],
      options: [],
      requestParams: [],
      responseParams: [],
      requestExample: '',
      responseExample: '',
      form: {
        name: '',
        description: '',
        protocol: '',
        serverAddress: '',
        port: '',
        upstreamPrefixPath: '',
        loadBalance: 'roundRobin'
      },
      ruleForm: {
        apiName: '',
        description: '',
        apiUrl: '',
        requestMethod: '',
        apiVersion: '',
        markdown: '',
        domain: '',
        prefixPath: '',
        requestParams: '',
        requestExample: '',
        responseExample: '',
        isInternal: '0'
      },
      rules: {
        // name: [
        //   { required: true, message: "请输入上游服务名称", trigger: "change" },
        // ],
        protocol: [
          { required: true, message: '请选择上游服务协议', trigger: 'change' }
        ],
        loadBalance: [
          { required: true, message: '请选择负载均衡算法', trigger: 'change' }
        ],
        serverAddress: [
          { required: true, message: '请输入服务地址', trigger: 'change' }
        ],
        upstreamPrefixPath: [
          // { pattern: /^\/(\w+\/?)+$/, message: '请输入合法的路径：以"/"开头，允许字母，数字，下划线' }
          {
            pattern: /^\/(?!.*?-$)[a-zA-Z0-9-_/]*$/,
            message:
              '请输入合法的路径：以"/"开头，允许字母，数字，下划线，短横线'
          }
        ],
        port: [
          { required: true, message: '请输入服务端口', trigger: 'change' },
          {
            pattern:
              /^([0-9]|[1-9]\d|[1-9]\d{2}|[1-9]\d{3}|[1-5]\d{4}|6[0-4]\d{3}|65[0-4]\d{2}|655[0-2]\d|6553[0-5])$/,
            message: '请输入正确的端口号：1到65535'
          }
        ]
      },
      rulesapi: {
        apiName: [
          { required: true, message: 'API名称不能为空', trigger: 'change' },
          {
            min: 3,
            max: 20,
            message: '长度在 3 到 20 个字符',
            trigger: 'change'
          }
        ],
        apiUrl: [
          { required: true, message: 'API路径不能为空', trigger: 'change' },
          {
            min: 3,
            max: 500,
            message: '长度在 3 到 500 个字符',
            trigger: 'change'
          }
        ],
        requestMethod: [
          { required: true, message: '请求方式不能为空', trigger: 'change' }
        ],
        apiVersion: [
          { required: true, message: '版本号不能为空', trigger: 'change' }
        ]
      }
    }
  },
  created () {
    this.getList()
  },
  methods: {
    // 点击步骤条切换
    stepChange () {
      if (this.active === 1) {
        this.active = 0
      } else {
        this.goNext('form')
      }
    },
    // 展开剩余配置
    changeShow () {
      this.showTimeFlag = !this.showTimeFlag
    },
    // 返回列表
    backList () {
      this.$router.push("/api/list");
    },
    addSure (formName) {
      console.log(JSON.stringify(this.$refs.xTable.afterFullData))
      console.log(JSON.stringify(this.$refs.xTableres.afterFullData))

      this.$refs[formName].validate((valid) => {
        if (valid) {
          console.log(this.form)
          console.log(this.ruleForm)
          const params = {
            apiName: this.ruleForm.apiName,
            description: this.ruleForm.description,
            domain: this.form.serverAddress,
            apiUrl: this.ruleForm.apiUrl,
            requestMethod: this.ruleForm.requestMethod,
            requestParams: JSON.stringify(this.$refs.xTable.afterFullData),
            responseParams: JSON.stringify(this.$refs.xTableres.afterFullData),
            requestExample: JSON.stringify(this.requestExample),
            responseExample: JSON.stringify(this.responseExample),
            apiVersion: this.ruleForm.apiVersion,
            isInternal: this.ruleForm.isInternal,
            upstreamPrefixPath: this.form.upstreamPrefixPath,
            prefixPath: this.ruleForm.prefixPath,
            protocol: this.form.protocol,
            loadBalance: this.form.loadBalance,
            port: this.form.port
          }
          create(params).then((res) => {
            // console.log(res)
            if (res.code === 200) {
              this.$message({
                message: res.msg,
                type: 'success'
              })
              this.$router.push('/api/list')
            } else {
              this.ruleForm = {}
              this.$message({
                message: res.msg,
                type: 'error'
              })
            }
          })
        }
      })
    },
    goBACK () {
      this.active = 0
      this.$refs['ruleForm'].resetFields()
    },
    highlighter (code) {
      return highlight(code, languages.js)
    },
    // 新增行
    async insertEvent () {
      const row = -1
      const $table = this.$refs.xTable
      const record = {
        parame: '',
        type: '',
        isHaveto: '',
        describe: '',
        default: ''
      }
      const { row: newRow } = await $table.insertAt(record, row)
      await $table.setActiveCell(newRow, 'parame')
    },
    async insertEventres () {
      const row = -1
      const $table = this.$refs.xTableres
      const record = {
        parame: '',
        type: '',
        isHaveto: '',
        describe: '',
        default: ''
      }
      const { row: newRow } = await $table.insertAt(record, row)
      await $table.setActiveCell(newRow, 'parame')
    },
    showMenu () {
      event.preventDefault()
      var x = event.clientX
      var y = event.clientY
      this.contextMenuData.axis = {
        x,
        y
      }
    },
    savedata () {
      // 新增一列
      this.insertEvent()
    },
    savedatares () {
      // 新增一列
      this.insertEventres()
    },
    newdata () {
      // 删除一列
      this.$refs.xTable.removeCheckboxRow()
    },
    newdatares () {
      // 删除一列
      this.$refs.xTableres.removeCheckboxRow()
    },
    // 校验ip
    isValidIP (ip) {
      var reg =
        /^((2[0-4]\d|25[0-5]|[01]?\d\d?)\.){3}(2[0-4]\d|25[0-5]|[01]?\d\d?)$/
      return reg.test(ip)
    },
    // 校验域名
    isValidWeb (web) {
      var reg =
        /^(?=^.{3,255}$)[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+$/
      return reg.test(web)
    },
    getList () {
      getUpstreamList('').then((res) => {
        if (res.code === 200) {
          const array = res.data.upstreamList
          this.upstreamList = res.data.upstreamList
          array.forEach((items) => {
            this.options.push({
              value: items.name,
              label: items.name
            })
          })
        }
      })
    },
    upstreamChange () {
      console.log('change')
      console.log(this.form.name)
      if (this.form.name === '') {
        console.log('ppp')
        this.form = {
          name: '',
          description: '',
          protocol: '',
          serverAddress: '',
          port: '',
          upstreamPrefixPath: '',
          loadBalance: 'roundRobin'
        }
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
              loadBalance: 'roundRobin'
            }
          }
        }
      }
    },
    goNext (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          const ipTest = this.isValidIP(this.form.serverAddress);
          const webTest = this.isValidWeb(this.form.serverAddress);
          if (!ipTest && !webTest) {
            this.$message("请输入合法的ip地址或服务地址！");
            this.addressFlag = true;
            return;
          }
          this.active = 1;
        } else {
          return;
        }
      });
    },
  }
}
</script>

<style lang='scss' scoped>
.Api_create {
  margin: 24px 40px;
  margin-bottom: 70px;
  background-color: #fff;
  min-height: calc(100vh - 185px);
  .top {
    padding: 0px 75px;
  }
  .formBox {
    margin-top: 30px;
    .contentDiv {
      display: flex;
      .item-div {
        margin-left: 20px;
      }
    }
    .item {
      position: absolute;
      right: -15px;
      top: 3px;
    }
    .addDiv {
      margin-top: 10px;
    }
    .add_span {
      color: #2c66fb;
    }
    .inputWidth {
      width: 48%;
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
  .bottom_button {
    margin-left: 185px;
    margin-top: 10px;
    div {
      display: inline-block;
      padding: 5px 8px;
      margin-right: 20px;
      cursor: pointer;
    }
  }
  .bottom_button_b {
    margin-left: 480px;
    margin-top: 10px;
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
  max-width: 1140px;
  background: #f5f7fa;
  color: #373753;
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
.show-but {
  margin-left: 16px;
  height: 20px;
  font-size: 14px;
  font-family: Microsoft YaHei UI-Regular, Microsoft YaHei UI;
  font-weight: 400;
  color: #2650ff;
  line-height: 20px;
}
.content-boder {
  .vxe-table {
    max-width: 1140px;
  }
  max-width: 1140px;
  border-radius: 8px;
  border: 1px solid #e1e6ee !important;
  padding: 10px 24px 24px 24px !important;
}
.table-button {
  margin-top: 15px;
  .add-but {
    border: 1px solid #2650ff;
    border-color: #2650ff;
    color: #2650ff;
  }
  .del-but {
    border: 1px solid #f03063;
    border-color: #f03063;
    color: #f03063;
  }
  .del-but:hover {
    background-color: #fef0f0;
  }
}
</style>
