 <template>
  <div id="main">
    <noticeComponent v-if="notice" :text="notice" @click="openTip" />
    <div class="left-notice">
      <el-tabs style="float: left" type="border-card">
        <el-tab-pane v-for="place in this.placeList" :key="place" :label="place.place">
          <el-image style="float: left; margin-right:80px;height: 170px; width: 300px;" :src="place.url"></el-image>
          <div style="float: left;">
            <p>联系人： {{ place.people }}</p>
            <p>联系电话： {{ place.phone }}</p>
            <p>场地地址： {{ place.address }}</p>
            <p>{{ place.description }}</p>
          </div>
        </el-tab-pane>
      </el-tabs>
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>通知公告</span>
          <el-button style="float: right; cursor: pointer; padding: 3px 0" type="text">更多</el-button>
        </div>
        <div v-for="notice in this.noticeList.slice(0,4)" class="text item" @click="toNoticeDetail(notice.id)">
          <span class="title">{{ notice.title.length > 30 ? notice.title.substring(0, 30) + "..." : notice.title }}</span>
          <span class="time">{{ dateFormat(notice.created) }}</span>
        </div>
      </el-card>
    </div>
    <el-card style="width: 98%;" class="box-appointment">
      <div slot="header" class="clearfix">
        <!--        头部-->
        <div class="hd">
          <span>场地预约情况</span>
          <span><i></i>已预约</span>
          <span><i></i>可预约</span>
          <span><i></i>不可用</span>
          <el-select v-model="curDate" @change="ChangeDate">
            <el-option v-for="placeState in this.placeStateList" :key="placeState.id" :label="placeState.date" :value="placeState.id">
            </el-option>
          </el-select>
        </div>
        <!--        时间段-->
        <div class="content">
          <div class="placeTime">
            <span>场地</span>
            <span>8:00 <br>
              ~<br>
              9:00
            </span>
            <span>9:00 <br>
              ~<br>
              10:00
            </span>
            <span>10:00 <br>
              ~<br>
              11:00
            </span>
            <span>11:00 <br>
              ~<br>
              12:00
            </span>
            <span>12:00 <br>
              ~<br>
              13:00
            </span>
            <span>13:00 <br>
              ~<br>
              14:00
            </span>
            <span>14:00 <br>
              ~<br>
              15:00
            </span>
            <span>15:00 <br>
              ~<br>
              16:00
            </span>
            <span>16:00 <br>
              ~<br>
              17:00
            </span>
            <span>17:00 <br>
              ~<br>
              18:00
            </span>
            <span>18:00 <br>
              ~<br>
              19:00
            </span>
            <span>19:00 <br>
              ~<br>
              20:00
            </span>
          </div>
          <div v-for="vdState in this.placeStateList[this.dateIndex].vdstateList" class="bd">
            <span>{{ vdState.placeName }}</span>
            <span v-for="idx in vdState.vdstatest.length" :key="idx">
              <i v-if="vdState.vdstatest.substring(idx - 1, idx) == 0 && !hasAuth('sys:appointment:forbid')" @click="orderPlace(idx,vdState.placeName,vdState.placeid,vdState.vdstatedate,vdState.id)" style="background-color: #34bfa7;border-color: #34bfa7"></i>
              <i v-if="vdState.vdstatest.substring(idx - 1, idx) == 0 && hasAuth('sys:appointment:forbid')" @click="sysOrder(idx,vdState.placeName,vdState.placeid,vdState.vdstatedate,vdState.id)" style="background-color: #34bfa7;border-color: #34bfa7"></i>
              <i v-if="vdState.vdstatest.substring(idx - 1, idx) == 1" style="background-color:#ccc;" @click="notOrder()"></i>
              <i v-if="vdState.vdstatest.substring(idx - 1, idx) == 2" style="background-color:#bf8f43; border-color: #bf8f43" @click="notOrder()"></i>
            </span>
          </div>
        </div>
      </div>
    </el-card>
    <el-card style="width: 98%;" class="box-competition">
      <div slot="header" class="clearfix">
        <span>赛事列表</span>
        <router-link to='/user/competition'>
          <el-button style="float: right; cursor: pointer; padding: 3px 0" type="text">更多</el-button>
        </router-link>
        <div style="margin-top: 10px;">
          <div v-for="competition in this.competitionList.slice(0,4)" class="competition" @click="toDetail(competition.id)">
            <el-image :src="competition.url" class="competition_photo"></el-image>
            <div>
              <div style="font-size: 14px;">
                <el-tag type="success">最新发布</el-tag>
                <span>
                  <el-tag v-if="new Date() < new Date(competition.starttime)" type="warning">报名中...</el-tag>
                  <el-tag v-else-if="new Date(competition.starttime) <= new Date() && new Date() <= new Date(competition.endtime)" type="success">比赛中...</el-tag>
                  <el-tag v-else>比赛结束</el-tag>
                </span>
                {{ competition.name }}
              </div>
              <div class="creator">
                {{ '创建人: ' + competition.username }}
              </div>
              <div>
                <el-button v-if="new Date() < new Date(competition.starttime) && competition.applyStatus === 1" class="apply">立即报名
                </el-button>
                <el-button v-if="competition.applyStatus === 0" style="background-color:#ccc;" class="apply">已报名</el-button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </el-card>
    <el-card style="width: 98%;margin-bottom: 50px">
      <div slot="header" class="clearfix">
        <span>器材</span>
        <router-link to='/user/equipment'>
          <el-button style="float: right; cursor: pointer; padding: 3px 0" type="text">更多</el-button>
        </router-link>
      </div>
      <div id="echarts1"></div>
    </el-card>
    <el-dialog title="预约" :visible.sync="dialogVisible" width="600px" :before-close="handleClose">
      <el-form :model="orderForm" ref="orderForm">
        <el-form-item label="预约状态" prop="title" label-width="100px">
          <el-radio v-model="orderForm.radio" label="2">预约</el-radio>
          <el-radio v-model="orderForm.radio" label="1">禁止</el-radio>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="resetForm()">取 消</el-button>
        <el-button type="primary" @click="sysOrderPlace">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import noticeComponent from "@/components/noticeComponent";
import * as echarts from 'echarts'
export default {
  name: "index",
  components: {
    noticeComponent
  },
  data () {
    return {
      notice: '公告：',
      activeName: 'first',
      noticeList: [],
      placeList: [],
      competitionList: [],
      placeStateList: [],
      chartDom: "",
      myChart: "",
      option: "",
      source: [
        ['product', '剩余', '租用价格']
      ],
      curDate: '请选择预约时间',
      dateIndex: -1,
      orderForm: {},
      dialogVisible: false,
      userId: 0
    }
  },
  created () {
    this.userId = localStorage.getItem("userId");
    this.getUserCompetitionList();
    this.getNoticeList();
    this.getPlaceList();
    this.getPlaceStateList();
  },
  mounted () {
    setTimeout(() => {
      this.getEquipmentList();
    }, 100);
    this.handleMsg();
  },
  methods: {
    //实时获取echarts的数据
    handleMsg () {
      this.$globalWebSocket.ws.onmessage = this.getMessage
    },
    getMessage (e) {
      this.getEquipmentList();
    },
    sysOrder (idx, placeName, placeId, orderDate, placeStateId) {
      this.dialogVisible = true;
      this.orderForm = {
        placeid: placeId,
        orderdate: orderDate,
        userid: localStorage.getItem("userId"),
        orderstart: idx + 7,
        orderend: idx + 8,
        price: 15,
        placeStateId: placeStateId
      }
    },
    notOrder () {
      this.$message({
        type: 'error',
        message: '不可预约!',
      });
    },
    sysOrderPlace () {
      if (this.orderForm.radio != 1 && this.orderForm.radio != 2) {
        this.$message({
          type: 'error',
          message: '请选择预约状态',
        });
        return;
      }
      this.$axios.post("/appointment/order", this.orderForm).then(res => {
        this.$message({
          type: 'success',
          message: '预约成功!',
          onClose: () => {
            this.getPlaceStateList()
          }
        });
        this.dialogVisible = false;
      })
    },
    resetForm () {
      this.dialogVisible = false;
    },
    handleClose () {
      this.resetForm()
    },
    orderPlace (idx, placeName, placeId, orderDate, placeStateId) {
      this.$confirm(`是否预约${(idx + 7)}:00~${(idx + 8)}:00的${placeName}？`, '预约场地', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.orderForm = {
          placeid: placeId,
          orderdate: orderDate,
          userid: localStorage.getItem("userId"),
          orderstart: idx + 7,
          orderend: idx + 8,
          price: 15,
          placeStateId: placeStateId,
          radio: 2
        }
        this.$axios.post("/appointment/order", this.orderForm).then(res => {
          this.$message({
            type: 'success',
            message: '预约成功!',
            onClose: () => {
              this.getPlaceStateList()
            }
          });
        })
      })
    },
    ChangeDate (id) {
      this.curDate = this.placeStateList[id].date;
      this.dateIndex = this.placeStateList[id].id;
    },
    getPlaceStateList () {
      this.$axios.get("/place/vdstate/getPlaceState").then(res => {
        this.placeStateList = res.data.data.placeDateStateList;
        for (let i = 0; i < this.placeStateList.length; i++) {
          this.placeStateList[i].date = this.dateFormat(this.placeStateList[i].date);
        }
        if (this.placeStateList.length > 0 && this.curDate === '请选择预约时间' && this.dateIndex === -1) {
          this.curDate = this.placeStateList[0].date;
          this.dateIndex = this.placeStateList[0].id;
        }
      })
    },
    initChart () {
      this.option = {
        legend: {},
        tooltip: {},
        dataset: {
          source: this.source
        },
        xAxis: { type: 'category' },
        yAxis: {},
        series: [{ type: 'bar' }, { type: 'bar' }]
      };
      this.option && this.myChart.setOption(this.option);
    },
    toDetail (id) {
      sessionStorage.setItem("competitionId", id);
      this.$router.push({
        path: '/user/competitionDetail'
      })
    },
    toNoticeDetail (id) {
      sessionStorage.setItem("noticeId", id);
      this.$router.push({
        path: '/notice/Details'
      })
    },
    openTip () {
    },
    handleClick (tab, event) {
      console.log(tab, event);
    },
    getUserCompetitionList () {
      this.$axios.get("/sys/userInfo").then(res => {
        let id = res.data.data.id;
        this.$axios.get("/competition/getCompetitionList/" + id).then(res => {
          this.competitionList = res.data.data.competitionList;
        })
      })
    },
    getEquipmentList () {
      this.$axios.get('/equipment/list').then(res => {
        console.log(res.data.data.pageData.records);
        for (let i = 0; i < res.data.data.pageData.records.length; i++) {
          let tmp = [];
          tmp.push(res.data.data.pageData.records[i].name);
          tmp.push(res.data.data.pageData.records[i].surplus);
          tmp.push(res.data.data.pageData.records[i].money);
          this.source.push(tmp);
        }
        this.$nextTick(() => {
          this.chartDom = document.getElementById("echarts1");
          this.myChart = echarts.init(this.chartDom);
          this.initChart();
        });
      })
    },
    getNoticeList () {
      this.$axios.get('/notice/list').then(res => {
        this.noticeList = res.data.data.pageData.records;
        for (let i = 0; i < this.noticeList.length; i++) {
          this.notice += (i + 1) + ". " + this.noticeList[i].title + " ";
        }
      })
    },
    getPlaceList () {
      this.$axios.get('/place/list').then(res => {
        this.placeList = res.data.data.placeList;
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
  }
}
</script>

<style scoped>
/*场地预约表格头部*/
.hd {
  height: 40px;
  line-height: 40px;
}
/*已预约、不可用*/
.hd span {
  margin-right: 20px;
}
/*颜色标记*/
.hd i {
  display: inline-block;
  height: 13px;
  width: 13px;
  margin-bottom: -3px;
  margin-right: 3px;
  border: 2px solid transparent;
}
.hd span:nth-child(2) i {
  background-color: #bf8f43;
}
.hd span:nth-child(3) i {
  background-color: #34bfa7;
}
.hd span:nth-child(4) i {
  background-color: #ccc;
}

/*表格*/
.content {
  box-sizing: border-box;
  border: 1px solid #ccc;
  margin-top: 10px;
  border-radius: 5px 5px 0 0;
  overflow: hidden;
}
/*表格头部时间段*/
.placeTime {
  display: flex;
  width: 100%;
  background-color: #f2f2f2;
}
/*每个时间段flex：1*/
.placeTime span {
  flex: 1;
  text-align: center;
  line-height: 15px;
  color: #333333;
  border-left: 1px solid #ebebeb;
  padding: 8px 0;
}
.placeTime span:nth-child(1) {
  line-height: 45px;
  border-left: 0;
}

/*表格主体*/
.bd {
  display: flex;
}
/*表格内部每个格子占一份*/
.bd span {
  display: flex;
  flex: 1;
  height: 40px;
  text-align: center;
  /*line-height: 40px;*/
  justify-content: center;
  align-items: center;
  border-top: 1px solid #ebebeb;
  border-right: 1px solid #ebebeb;
}
/*表格颜色按钮*/
.bd span i {
  display: inline-block;
  width: 78%;
  height: 60%;
  border: 2px solid #ccc;
  cursor: pointer;
  transition: all 0.2s linear;
}
/*表格颜色按钮缩放效果*/
.bd span i:hover {
  transform: scale(1.08);
}
/*每一列最后一个各自不要右边框*/
.bd span:last-child {
  border-right: 0;
}
/*日期下拉框*/
.el-select {
  float: right;
}

#echarts1 {
  width: 100%;
  height: 300px;
}

.time {
  float: right;
  color: #ccc;
}
/*公告部分*/
.left-notice {
  margin-top: 10px;
  overflow: hidden;
}
/*每一条公告*/
.left-notice .el-tabs {
  height: 245px;
}
.el-tabs {
  width: 55%;
}

.text {
  font-size: 14px;
  transition: all 0.5s linear;
}
/*每一条公告缩放特效*/
.text:hover {
  cursor: pointer;
  transform: scale(1.03);
}
/*鼠标悬停公告，标题变色，时间不变色*/
.text:hover span:first-child {
  color: #409eff;
}

/*赛事部分*/
.competition {
  display: inline-block;
  cursor: pointer;
  margin: 20px;
  width: 260px;
  transition: all 0.3s linear;
}
.competition:hover {
  transform: scale(1.03);
  color: #409eff;
}
.competition_photo {
  width: 260px;
  height: 210px;
  border-radius: 15px;
  transition: all 0.3s linear;
  margin-bottom: 10px;
}

/*赛事立即报名按钮*/
.apply {
  float: right;
  padding: 10px;
  font-size: 14px;
  font-weight: 600;
  background-color: #34bfa7;
  color: #fff;
}

.apply:hover {
  background-color: #34bfa7;
  color: #fff;
}

.el-tag {
  height: 20px;
  line-height: 20px;
  border: none;
  width: auto;
  padding: 0;
  margin-right: 5px;
  font-size: 12px;
}
/*赛事创建人*/
.creator {
  font-size: 12px;
  color: #aaa;
  margin-top: 5px;
}

.item {
  margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both;
}

.box-competition,
.box-appointment {
  margin: 30px 0;
}

.box-card {
  width: 40%;
  float: left;
  margin-left: 40px;
}
</style>

