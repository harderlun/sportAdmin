<template>
  <div style="text-align: center">
    <div style="text-align: left;width: 1080px;margin: 0 auto">
      <el-image :src="info.url" class="photo"></el-image>
      <div class="hd">
        <h3 @click="changeToPeople()" :class="isShow ? 'tran' : 'blue'" id="details">竞赛信息</h3>
        <h3 @click="changeToInfo()" :class="isShow ? 'blue' : 'tran'" id="people">报名列表</h3>

        <div style="float: right" v-if="new Date() < new Date(info.starttime)">
          <el-button v-if="new Date() < new Date(info.starttime) && info.applyStatus === 1" class="baoMing" @click="apply()">立即报名</el-button>
          <el-button  v-if="info.applyStatus === 0" class="baoMing" @click="cancel()">取消报名</el-button>
        </div>
        <div class="apply">
          <span v-if="new Date() < new Date(info.starttime)">报名时间&nbsp;</span>
          <span v-if="new Date() < new Date(info.starttime)">距离报名截止还有 {{ parseInt((new Date(info.starttime).getTime() - new Date().getTime()) / (1000 * 60 * 60 * 24)) }} 天</span>
          <span v-else>报名结束</span>
          <p>{{ dateFormat(new Date()) + ' - ' + dateFormat(info.starttime) }}</p>
        </div>
      </div>
      <div class="content-left">
        <div v-if="!isShow" class="info">
          <h1 class="name">{{ info.name }}</h1>
          <p>
            {{ info.description }}
          </p>
        </div>
        <div v-if="isShow" class="people">
          <div v-if="applyPeopleList.length > 0" v-for="apply in applyPeopleList">
          <span>
            <el-image :src="apply.userAvatar" class="avatar"></el-image>
          </span>
            <span>
            {{'用户名: ' + apply.username}}
          </span>
            <span>
            {{'报名时间: ' + dateFormat(apply.created)}}
          </span>
          </div>
          <div v-if="applyPeopleList.length == 0">
            暂时无人报名，赶快来报名吧！！！
          </div>
        </div>
      </div>
      <div class="content-right">
        <p>
          <span>竞赛地点</span>
          {{ info.place }}
        </p>
        <p>
          <span>竞赛创建人</span>
          {{ info.username }}
        </p>
        <p>
          <span>报名人数</span>
          {{applyNum}}
        </p>
      </div>

    </div>

  </div>
</template>

<script>
export default {
  name: "competitionDetail",
  data() {
    return {
      competitionId: 0,
      info: {},
      isShow: false,
      applyForm: {},
      applyPeopleList: [],
      applyNum: 0,
      userId: 0
    }
  },
  created() {
    this.competitionId = sessionStorage.getItem("competitionId");
    this.userId = localStorage.getItem("userId");
    this.getCompetitionInfo();
    this.getApplyPeopleList();
  },
  methods: {
    changeToPeople(){
      this.isShow = false;
    },
    changeToInfo(){
      this.isShow = true;
    },
    getApplyPeopleList(){
      this.$axios.get("/competition/getApplyPeopleList/" + this.competitionId).then(res => {
        this.applyPeopleList = res.data.data.userApplyList;
        this.applyNum = this.applyPeopleList.length;
      })
    },
    apply(){
      this.applyForm.competitionid = this.competitionId;
      this.applyForm.userid = this.userId;
      this.$confirm('此操作将报名该比赛, 是否继续?', '报名', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$axios.post("/user/apply/save",this.applyForm).then(res => {
          this.$message({
            type: 'success',
            message: '报名成功!',
            onClose:() => {
              this.getCompetitionInfo();
              this.getApplyPeopleList();
            }
          });
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消报名'
        });
      });
    },
    cancel(){
      this.$confirm('此操作将取消报名该比赛, 是否继续?', '取消报名', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$axios.post("/user/apply/cancel/" + this.competitionId + "/" + this.userId).then(res => {
          this.$message({
            type: 'success',
            message: '取消报名成功!',
            onClose:() => {
              this.getCompetitionInfo();
              this.getApplyPeopleList();
            }
          });
        })
      })
    },
    getCompetitionInfo() {
      this.$axios.get("/competition/getCompetitionInfo/" + this.competitionId + "/" + localStorage.getItem("userId")).then(res => {
        this.info = res.data.data.competitionInfo;
      })
    },
    dateFormat(time) {
      let date = new Date(time);
      let year = date.getFullYear();
      let month =
          date.getMonth() + 1 < 10
              ? "0" + (date.getMonth() + 1)
              : date.getMonth() + 1;
      let day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
      return (year + "年" + month + "月" + day + "日");
    },
  }
}
</script>

<style scoped>
.info{
  margin-top: 20px;
  line-height: 25px;
}
.people .el-image{
  width: 36px;
  height: 36px;
  vertical-align: middle;
}
.people{
  padding-top: 30px;
  text-align: center;
}
.people div{
  height: 45px;
  border-bottom: 1px solid #ccc;
  margin-bottom: 20px;
}
.people div:nth-last-child(1){
  border-bottom: 0;
}

.people span{
  display: inline-block;
  margin-right: 30px;
  height: 44px;
  line-height: 44px;
}
.people span:nth-child(1){
  float: left;
  width: 36px;
  height: 36px;
  margin-left: 50px;
  margin-top: 4px;
}
.people span:nth-child(2){
  text-align: left;
  width: 150px;
  margin-right: 50px;
  margin-left: 50px;
}
.people span:nth-child(3){
  text-align: left;
  width: 300px;
  margin-left: 30px;
  margin-right: 0;
}
.avatar{
  width: 36px;
  height: 36px;
  line-height: 36px;
  border-radius: 50%;
}
.hd{
  height: 60px;
  line-height: 60px;
  border-bottom: 1px solid #ccc;
}
.hd h3{
  display: inline-block;
  margin: 0;
  cursor: pointer;
  width: 100px;
  height: 58px;
  line-height: 58px;
  border-bottom: 2px solid transparent;
  text-align: center;
}
.hd h3:nth-child(1){
  border-bottom-color:#409EFF ;
}
.hd h3:hover{
  color: #409EFF;
}
.hd p{
  margin: 0;
}
.content-left{
  float: left;
  width: 750px;
}
.content-right{
  float: left;
  margin-left: 70px;
  margin-top: 30px;
}
.content-right p{
  color: #666;
}
.content-right p span{
  display: inline-block;
  width: 80px;
  height: 20px;
  line-height: 20px;
  font-weight: 600;
  padding-left: 10px;
  color: #666;
  border-left: 5px solid #409EFF;
}
.apply{
  float: right;
  line-height: 25px;
  padding-top: 7px;
  color: #666;
  font-weight: 600;
  margin-right: 20px;
}

.apply p{
  font-weight: 400;
  color: #aaa;
}
.name {
  font-size: 26px;
  font-weight: 600;
}

.baoMing {
  font-size: 18px;
  font-weight: 600;
  background-color: #34bfa7;
  color: #ffffff;
}

.photo {
  width: 1080px;
  height: 382px;
}
.blue{
  border-bottom-color: #409EFF!important;
}
.tran{
  border-bottom-color: transparent!important;
}
</style>
