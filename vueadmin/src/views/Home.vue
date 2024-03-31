<template>
  <el-container>
    <el-aside width="160px">
      <SideMenu></SideMenu>
    </el-aside>
    <el-container>
      <el-header>
        <div style="display: flex; align-items: center; overflow: hidden;">
          <Tabs style="width: 1200px;"></Tabs>
          <el-popover placement="bottom" width="400" trigger="hover">
            <div class="notice_item" v-for="notice in (this.borrowList.length > 0 ? this.borrowList : this.compensateList)" :key="notice">
              <div v-if="notice.equipmentid">
                <span class="info">
                  {{ '一条来自 ' + notice.username + ' 的器材申请，等待您的审批！！！ ' }}
                </span>
                <span>{{ '申请时间： ' + notice.created }}</span>
                <router-link to='/sys/Borrow'>
                  <span class="go-approve">去审批>></span>
                </router-link>
              </div>
              <div v-if="notice.borrowid">
                <span class="info">
                  {{ '您需要支付一笔器材赔偿，赔偿原因：' + notice.reason + ',赔偿金额: ' + notice.price + '元 ，请尽快缴纳，否则无法租用器材' }}
                </span>
                <span>{{ '时间： ' + notice.created }}</span>
                <span class="go-approve" @click="pay(notice)">去支付>></span>
              </div>
            </div>
            <el-button slot="reference" class="item">
              <el-badge :value="this.num" :max="99">
                <i class="el-icon-message-solid" style="width: 25px;"></i>
              </el-badge>
            </el-button>
          </el-popover>
          <div class="header-avatar">
            <el-avatar size="medium" :src="userInfo.avatar"></el-avatar>
            <el-dropdown>
              <span class="el-dropdown-link">
                {{ userInfo.username }}<i class="el-icon-arrow-down el-icon--right"></i>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item>
                  <router-link to="/userCenter">个人中心</router-link>
                </el-dropdown-item>
                <!--
                <el-dropdown-item>
                  <router-link to='/user/order'>我的订单</router-link>
                </el-dropdown-item>
                -->
                <el-dropdown-item @click.native="logout">退出</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>

        </div>

      </el-header>
      <el-main>
        <div style="margin: 0 15px;">
          <router-view></router-view>
        </div>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import SideMenu from "@/views/inc/SideMenu.vue";
import Tabs from "@/views/inc/Tabs.vue"

export default {
  name: "Home",
  components: {
    SideMenu,
    Tabs
  },
  data () {
    return {
      userInfo: {
        id: "",
        username: "",
        avatar: ""
      },
      num: 0,
      borrowList: [],
      compensateList: [],
    }
  },
  created () {
    this.getUserInfo();
    this.getBorrowNum();
  },
  mounted () {
    this.handleMsg();
  },
  methods: {
    //实时获取echarts的数据
    handleMsg () {
      this.$globalWebSocket.ws.onmessage = this.getMessage
    },
    getMessage: function (e) {
      this.$notify.info({
        title: '通知',
        message: e.data
      });
      this.getBorrowNum();
      this.getCompensateNum();
    },
    pay (notice) {
      this.$confirm(`器材租用编号：${notice.borrowid}，赔偿原因：${notice.reason}，赔偿金额：${notice.price}，是否继续支付`, '缴纳赔偿', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$axios.post("/compensate/pay/" + notice.id).then(res => {
          this.$message({
            type: 'success',
            message: '支付成功!',
            onClose: () => {
              this.num--;
              this.getCompensateNum();
            }
          });
        })
      })
    },
    dateFormat (time) {
      let date = new Date(time);
      let year = date.getFullYear();
      let month =
        date.getMonth() + 1 < 10
          ? "0" + (date.getMonth() + 1)
          : date.getMonth() + 1;
      let day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
      return (year + "-" + month + "-" + day);
    },
    getBorrowNum () {
      this.$axios.get("/sys/borrow/getBorrowNum").then(res => {
        this.num = res.data.data.borrowNum;
        this.borrowList = res.data.data.borrowNoticeList;
        for (let i = 0; i < this.borrowList.length; i++) {
          this.borrowList[i].created = this.dateFormat(this.borrowList[i].created);
        }
      })
    },
    getCompensateNum () {
      this.$axios.get("/compensate/getCompensateNum/" + this.userInfo.id).then(res => {
        this.num += res.data.data.compensateNum;
        this.compensateList = res.data.data.compensateList;
        for (let i = 0; i < this.compensateList.length; i++) {
          this.compensateList[i].created = this.dateFormat(this.compensateList[i].created);
        }
      })
    },
    getUserInfo () {
      this.$axios.get("/sys/userInfo").then(res => {
        this.userInfo.id = res.data.data.id;
        localStorage.setItem("userId", this.userInfo.id);
        this.userInfo.username = res.data.data.username;
        this.userInfo.avatar = res.data.data.avatar;
        this.getCompensateNum();
      })
    },
    logout () {
      this.$axios.post("/logout").then(res => {
        localStorage.clear();
        sessionStorage.clear();
        this.$store.commit("resetState");
        this.$router.push("/login");
      })
    }
  }
}
</script>

<style scoped>
.el-container {
  padding: 0;
  margin: 0;
  height: 100vh;
}

.notice_item {
  border-bottom: 1px solid #868daa;
  margin-bottom: 10px;
  overflow: hidden;
}

.header-avatar {
  float: right;
  width: 120px;
  display: flex;
  justify-content: space-around;
  align-items: center;
}

.order {
  float: right;

  height: 20px;
  /*background-color: pink;*/
  line-height: 20px;
  cursor: pointer;
  font-size: 14px;
  margin-top: 20px;
  margin-right: 10px;
}

.order:hover {
  color: #409eff;
}

.el-dropdown-link {
  cursor: pointer;
}

.el-header {
  color: #333;
  text-align: center;
  line-height: 60px;
  box-shadow: 0 3px 6px rgba(0, 0, 0, 0.4); /* 设置阴影 */
  margin-bottom: 10px;
}

.el-aside {
  background-color: #d3dce6;
  color: #333;
  line-height: 200px;
}

.el-main {
  color: #333;
  padding: 0;
}

a {
  text-decoration: none;
}

.router-link-active {
  text-decoration: none;
}

.item {
  float: right;
  border: none;
  padding: 0 20px;
}

.info {
  display: block;
  margin-bottom: 5px;
}

.go-approve {
  float: right;
  display: inline-block;
  border-bottom: 1px solid transparent;
  color: #8bd2f4;
  cursor: pointer;
  margin-bottom: 5px;
}

.go-approve:hover {
  color: #409eff;
  border-bottom: 1px solid #409eff;
}
</style>
<style>
/*.el-badge__content.is-fixed{*/
/*  transform: translateY(50%) translateX(100%) !important;*/
/*}*/
</style>
