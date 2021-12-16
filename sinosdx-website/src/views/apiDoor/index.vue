<template>
  <div class="apiMain">
    <div class="apiMain_content">
      <div class="apiMain_search">
        <div>
          <el-input placeholder="请输入" v-model="searchKey">
            <template slot="append">
              <div @click="search" style="cursor: pointer">搜索</div>
            </template>
          </el-input>
        </div>
      </div>
      <div v-if="isshow">
        <div
          v-for="(item, index) in items"
          :key="index"
          @click="itemsClick(item)"
          class="apiMain_list"
        >
          <div class="list_items">
            <div>
              <img src="./../../assets/img/door-p.png" alt="" />
            </div>
            <div class="items_message">
              <div class="one">
                <span>{{ item.appName }}</span>
              </div>
              <div class="two">
                <span>{{ item.description }}</span>
              </div>
              <div class="three">
                <span>操作系统: windows</span>
                <span>交付方式: 人工服务</span>
              </div>
              <div class="three">
                <span>服务商: 上汽集团</span>
                <span>发布时间: {{ item.creationDate }}</span>
              </div>
              <div class="five">
                <span v-for="(tag, idx) in item.label" :key="idx">{{ tag }}</span>
              </div>
            </div>
            <div class="price">
              <span>¥00.00</span>
              <i>/年</i>
            </div>
          </div>
        </div>
        <div class="bottomPage">
          <el-pagination
            style="float: right; margin: 20px"
            :current-page.sync="currentPage"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total"
            @current-change="handleCurrentChange"
            @size-change="handleSizeChange"
            :page-sizes="[10, 20, 30, 40]"
            :page-size="size"
          />
        </div>
      </div>
      <div v-if="!isshow">
        <div class="shuoming">
          <img src="./../../assets/img/picture.png" alt="" />
          <p>暂无数据</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { list } from "@/api/AboutApp";

export default {
  data() {
    return {
      searchKey: "",
      items: [],
      isshow: true,
      size: 10,
      currentPage: 1,
      total: 0,
    };
  },
  created() {
    this.resquery();
  },
  methods: {
    resquery() {
      const query =
        "?market=true&limit=" + this.size + "&offset=" + this.currentPage;
      list(query).then((res) => {
        console.log(res);
        this.total = res.data.total;
        this.items = res.data.appList;
        if (res.data.appList.length === 0) {
          this.isshow = false;
        } else {
          this.isshow = true;
        }
      });
    },
    handleCurrentChange(val) {
      this.currentPage = val;
      this.resquery();
    },
    handleSizeChange(val) {
      this.size = val;
      this.resquery();

      // console.log(val);
      // this.$emit("sizeChange", val);
    },
    itemsClick(i) {
      console.log(i);
      this.$router.push({
        path: "/apiDoor/detail?message=" + JSON.stringify(i),
      });
    },
    search() {
      // console.log('搜索')
      const query = "?market=true&appName=" + this.searchKey;
      list(query).then((res) => {
        this.items = res.data.appList;
        // if(res.data.appList)
        // console.log(res.data.appList.length)
        if (res.data.appList.length === 0) {
          this.isshow = false;
        } else {
          this.isshow = true;
        }
      });
    },
  },
};
</script>

<style lang='scss' scoped>
.apiMain {
  margin: 10px;
  min-height: calc(100vh - 185px);
  background-color: #fff;
}
.bottomPage {
  width: 100%;
  height: 80px;
  background-color: #fff;
}
.shuoming {
  height: 300px;
  text-align: center;
  p {
    color: #999999;
  }
}
.apiMain_content {
  width: 100%;
  background-color: #fff;
  .apiMain_search {
    padding: 15px 30%;
    ::v-deep .el-input-group__append {
      background-color: #2c66fb;
      color: #fff;
    }
  }
  .apiMain_list {
    padding: 24px;
    cursor: pointer;
    .list_items {
      // height: 130px;
      border-bottom: 1px solid #ccc;
      display: flex;
      .items_message {
        width: 70%;
        margin-left: 24px;
        font-size: 12px;
        div {
          padding: 5px 0px;
        }
        .one {
          color: #000;
        }
        .two {
          color: #666666;
          line-height: 18px;
          width: 780px;
          white-space: nowrap;
          text-overflow: ellipsis;
          overflow: hidden;
        }
        .three {
          color: #999999;
          span {
            display: inline-block;
            width: 30%;
          }
        }
        .four {
          color: #999999;
        }
        .five {
          span {
            display: inline-block;
            padding: 3px;
            margin-right: 5px;
            color: #2c66fb;
            background: rgba(44, 102, 251, 0.1);
          }
        }
      }
      .price {
        margin-top: 23px;
        span {
          color: #f6323c;
          font-size: 16px;
        }
        i {
          font-style: normal;
          font-size: 12px;
          color: #999999;
        }
      }
    }
  }
}
</style>