<template>
  <div class="main">
    <el-card class="box-card" shadow="never">
      <div class="theSteps">
        <!-- <el-steps :active="active" finish-status="success">
          <el-step title="基本信息"></el-step>
          <el-step title="预览"></el-step>
        </el-steps> -->
      </div>
      <div class="formBox">
        <el-form
          ref="form"
          :model="form"
          :rules="rules"
          label-width="130px"
          label-position="left"
          size="small"
        >
          <el-form-item label="名称：" prop="name">
            <el-input
              maxlength="50"
              show-word-limit
              v-model="form.name"
              placeholder="请输入上游服务名称"
              class="inputWidth"
            ></el-input>
          </el-form-item>
          <el-form-item label="描述：">
            <el-input
              maxlength="500"
              show-word-limit
              class="inputWidth"
              type="textarea"
              v-model="form.description"
              :autosize="{ minRows: 4, maxRows: 6 }"
              placeholder="请输入上游服务描述"
            ></el-input>
          </el-form-item>
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
          <el-form-item label="路由前置路径：" prop="prefixPath">
            <el-input
              v-model="form.prefixPath"
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
          <!-- <el-form-item label="目标节点：">
            <div class="contentDiv">
              <div>
                <el-button icon="el-icon-minus" circle size="mini" class="add_span"></el-button>
              </div>
              <div style="margin-left: 5px;">
                <label>主机名：</label>
                <el-input class="numberWdith" v-model="form.name"></el-input>
              </div>
              <div class="item-div">
                <label>端口：</label>
                <el-input class="numberWdith" v-model="form.name"></el-input>
              </div>
              <div class="item-div">
                <label>权重：</label>
                <el-input class="numberWdith" v-model="form.name"></el-input>
              </div>
            </div>
            <div class="addDiv">
              <el-button icon="el-icon-plus" circle size="mini" class="add_span"></el-button>
              <span class="add_span"> 新建</span><span> (1/20)</span>
            </div>
          </el-form-item> -->
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
            <el-input
              v-model="tautologyNum"
              disabled
              class="selectWidth"
              readonly
            >
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
            <el-input
              v-model="connectNum"
              disabled
              class="selectWidth"
              readonly
            >
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
            <el-input
              v-model="receiveNum"
              disabled
              class="selectWidth"
              readonly
            >
              <template slot="append">s</template>
            </el-input>
          </el-form-item>
          <!-- 健康检查 -->
          <!-- <div class="divider">
            <div class="dividerTitle">健康检查</div>
            <el-divider></el-divider>
          </div>
          <el-form-item label="主动检查：">
            <el-switch active-color="#2C66FB" v-model="form.delivery"></el-switch>
          </el-form-item>
          <el-form-item label="类型：">
            <el-select v-model="form.region" placeholder="请选择健康检查类型" class="selectWidth">
              <el-option label="区域一" value="shanghai"></el-option>
              <el-option label="区域二" value="beijing"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="超时时间：" prop="name">
            <el-input v-model="form.name" class="numberWdith">
              <template slot="append">s</template>
            </el-input>
          </el-form-item>
          <el-form-item label="并行数量：" prop="name">
            <el-input v-model="form.name" class="numberWdith"></el-input>
          </el-form-item>
          <el-form-item label="主机名：">
            <el-input v-model="form.name"></el-input>
          </el-form-item>
          <el-form-item label="端口：">
            <el-input v-model="form.name" class="numberWdith"></el-input>
          </el-form-item>
          <el-form-item label="请求路径：">
            <el-input v-model="form.name"></el-input>
          </el-form-item>
          <el-form-item label="请求头：">
            <div class="contentDiv">
              <div>
                <el-button icon="el-icon-minus" circle size="mini" class="add_span"></el-button>
              </div>
              <div style="margin-left: 5px;">
                <el-input class="numberWdith" v-model="form.name"></el-input>
              </div>
            </div>
            <div class="addDiv">
              <el-button icon="el-icon-plus" circle size="mini" class="add_span"></el-button>
              <span class="add_span"> 新建</span><span> (1/20)</span>
            </div>
          </el-form-item> -->
          <!-- 健康状态 -->
          <!-- <div class="divider">
            <div class="dividerTitle">健康状态</div>
            <el-divider></el-divider>
          </div>
          <el-form-item label="超时时间：" prop="name">
            <el-input v-model="form.name" class="numberWdith">
              <template slot="append">s</template>
            </el-input>
          </el-form-item>
          <el-form-item label="间隔时间：" prop="name">
            <el-input v-model="form.name" class="numberWdith">
              <template slot="append">s</template>
            </el-input>
          </el-form-item>
          <el-form-item label="状态码：" prop="name">
            <div class="contentDiv">
              <div>
                <el-button icon="el-icon-minus" circle size="mini" class="add_span"></el-button>
              </div>
              <div style="margin-left: 5px;">
                <el-input class="numberWdith" v-model="form.name"></el-input>
              </div>
            </div>
            <div class="addDiv">
              <el-button icon="el-icon-plus" circle size="mini" class="add_span"></el-button>
              <span class="add_span"> 新建</span><span> (1/20)</span>
            </div>
          </el-form-item> -->
          <!-- 不健康状态 -->
          <!-- <div class="divider">
            <div class="dividerTitle">不健康状态</div>
            <el-divider></el-divider>
          </div>
          <el-form-item label="超时时间：" prop="name">
            <el-input v-model="form.name" class="numberWdith">
              <template slot="append">s</template>
            </el-input>
          </el-form-item>
          <el-form-item label="间隔时间：" prop="name">
            <el-input v-model="form.name" class="numberWdith">
              <template slot="append">s</template>
            </el-input>
          </el-form-item>
          <el-form-item label="状态码：" prop="name">
            <div class="contentDiv">
              <div>
                <el-button icon="el-icon-minus" circle size="mini" class="add_span"></el-button>
              </div>
              <div style="margin-left: 5px;">
                <el-input class="numberWdith" v-model="form.name"></el-input>
              </div>
            </div>
            <div class="addDiv">
              <el-button icon="el-icon-plus" circle size="mini" class="add_span"></el-button>
              <span class="add_span"> 新建</span><span> (1/20)</span>
            </div>
          </el-form-item>
          <el-form-item label="HTTP 失败次数：" prop="name">
            <el-input v-model="form.name" class="selectWidth">
              <template slot="append">次</template>
            </el-input>
          </el-form-item>
          <el-form-item label="TCP 失败次数：" prop="name">
            <el-input v-model="form.name" class="selectWidth">
              <template slot="append">次</template>
            </el-input>
          </el-form-item> -->
          <!-- 被动检查 -->
          <!-- <div class="divider">
            <el-divider></el-divider>
          </div>
          <el-form-item label="被动检查：">
            <el-switch active-color="#2C66FB" v-model="form.delivery"></el-switch>
          </el-form-item> -->
          <el-form-item class="formBut">
            <!-- <el-button @click="step" :disabled="active == 0">上一步</el-button>
            <el-button @click="next" v-if="active == 0">下一步</el-button>
            <el-button type="primary" @click="onSubmit" style="background-color: #2C66FB;" v-else>提交</el-button> -->
            <el-button @click="goBack">取消</el-button>
            <el-button
              type="primary"
              @click="submitForm('form')"
              style="background-color: #2c66fb; border-color: #2c66fb"
              >提交</el-button
            >
          </el-form-item>
        </el-form>
      </div>
    </el-card>
  </div>
</template>

<script>
import { addUpstream, updateUpstream, getUpstreamInfo } from "@/api/upstream";
export default {
  components: {},
  data() {
    return {
      active: 0,
      tautologyNum: 1,
      connectNum: 30,
      sendNum: 15,
      receiveNum: 15,
      editFlag: false,
      upstreamId: 0,
      addressFlag: false,
      form: {
        name: "",
        description: "",
        protocol: "",
        serverAddress: "",
        port: "",
        prefixPath: "",
        loadBalance: "roundRobin",
      },
      rules: {
        name: [
          { required: true, message: "请输入上游服务名称", trigger: "blur" },
        ],
        protocol: [
          { required: true, message: "请选择上游服务协议", trigger: "change" },
        ],
        loadBalance: [
          { required: true, message: "请选择负载均衡算法", trigger: "change" },
        ],
        serverAddress: [
          { required: true, message: "请输入服务地址", trigger: "blur" },
        ],
        prefixPath: [
          // { pattern: /^\/(\w+\/?)+$/, message: '请输入合法的路径：以"/"开头，允许字母，数字，下划线' }
          {
            pattern: /^\/(?!.*?-$)[a-zA-Z0-9-_/]*$/,
            message:
              '请输入合法的路径：以"/"开头，允许字母，数字，下划线，短横线',
          },
        ],
        port: [
          { required: true, message: "请输入服务端口", trigger: "blur" },
          {
            pattern:
              /^([0-9]|[1-9]\d|[1-9]\d{2}|[1-9]\d{3}|[1-5]\d{4}|6[0-4]\d{3}|65[0-4]\d{2}|655[0-2]\d|6553[0-5])$/,
            message: "请输入正确的端口号：1到65535",
          },
        ],
      },
    };
  },
  created() {
    if (this.$route.params.id) {
      this.upstreamId = this.$route.params.id;
      this.editFlag = true;
      this.getUpstreamInfo(this.upstreamId);
    }
  },
  methods: {
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
    goBack() {
      this.$router.push({ path: "/upstream/index" });
    },
    step() {
      console.log(this.active);
      if (this.active !== 0 && this.active-- > 1) this.active = 0;
    },
    next() {
      console.log(this.active);
      if (this.active !== 2 && this.active++ > 2) this.active = 0;
    },
    // 获取上游服务信息
    getUpstreamInfo(upstreamId) {
      getUpstreamInfo(upstreamId).then((res) => {
        if (res.code === 200) {
          const data = res.data;
          this.form = {
            name: data.name,
            description: data.description,
            protocol: data.protocol,
            serverAddress: data.serverAddress,
            port: data.port,
            prefixPath: data.prefixPath,
            loadBalance: data.loadBalance,
          };
        }
      });
    },
    // 提交数据
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          const ipTest = this.isValidIP(this.form.serverAddress);
          const webTest = this.isValidWeb(this.form.serverAddress);
          if (!ipTest && !webTest) {
            this.$message("请输入合法的ip地址或服务地址！");
            this.addressFlag = true;
            return;
          }
          if (this.upstreamId === 0) {
            // 添加角色
            addUpstream(this.form).then((res) => {
              if (res.code === 200) {
                // 返回列表页面
                this.goBack();
              } else {
                this.$message({
                  message: res.msg,
                  type: "error",
                });
              }
            });
          } else {
            // 编辑角色
            this.form.id = this.upstreamId;
            updateUpstream(this.upstreamId, this.form).then((res) => {
              if (res.code === 200) {
                // 返回列表页面
                this.goBack();
              }
            });
          }
        }
      });
    },
  },
};
</script>

<style lang="scss" scoped>
/deep/ .contentDiv .el-form-item__label {
  width: 95px !important;
}
/deep/ .contentDiv .el-form-item__content {
  margin-left: 95px !important;
}
.box-card {
  margin: 10px;
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
</style>
<style scoped>
.theSteps {
  width: 50%;
  margin: 0px auto;
}
/* 进行中状态：圈线 */
.theSteps >>> .el-step__head.is-process {
  color: #2c66fb;
  border-color: #2c66fb;
}
/* 进行中状态：圈内 */
.theSteps >>> .el-step__head.is-process > .el-step__icon {
  background: #2c66fb;
  color: #fff;
}
/* 进行中状态：title（文字） */
.theSteps >>> .el-step__title.is-process {
  color: #2c66fb;
}
/* 完成状态：圈线 */
.theSteps >>> .el-step__head.is-success {
  color: #2c66fb;
  border-color: #2c66fb;
}
/* 完成状态：title（文字） */
.theSteps >>> .el-step__title.is-success {
  color: #2c66fb;
}
/* 完成状态：line
 * 描述：第一步完成，第二步进行时，之间的进度条有颜色
 */
.theSteps
  >>> .el-step__head.is-success
  > .el-step__line
  > .el-step__line-inner {
  width: 100% !important;
  border-width: 1px !important;
}
</style>

