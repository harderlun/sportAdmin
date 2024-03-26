<template>
  <div>
    <div v-for="competition in this.competitionList" class="competition">
      <div style="float: left">
        <el-image :src="competition.url" class="competition_photo"></el-image>
      </div>
      <div style="display: inline-block;">
        <h3>
          <el-tag v-if="new Date() < new Date(competition.starttime)" type="warning" >报名中...</el-tag>
          <el-tag v-if="new Date(competition.starttime) <= new Date() && new Date() <= new Date(competition.endtime)" type="success">比赛中...</el-tag>
          <el-tag v-if="new Date() > new Date(competition.endtime)">比赛结束</el-tag>
          <a @click="toDetail(competition.id)" style="cursor: pointer;">{{competition.name}}</a>
        </h3>
        <p>
          <span class="hd">创建人</span>
          <span>{{competition.username}}</span>

        </p>
        <p>
          <span class="hd">竞赛级别</span>
          <span>全国性</span>
        </p>
        <p>
          <span class="hd">报名时间</span>
          <span>{{dateFormat(competition.starttime) + '截至'}}</span>

        </p>
        <p>
          <span class="hd">比赛时间</span>
          <span>{{dateFormat(competition.starttime) + ' ~ ' + dateFormat(competition.endtime)}}</span>

        </p>
      </div>
      <div class="info" >
        <el-button v-if="new Date() < new Date(competition.starttime) && competition.applyStatus === 1" @click="toDetail(competition.id)">点击报名</el-button>
        <el-button v-if="new Date() < new Date(competition.starttime) && competition.applyStatus === 0" @click="toDetail(competition.id)">取消报名</el-button>
        <p v-if="new Date() < new Date(competition.starttime)">距离报名截止还有 {{parseInt((new Date(competition.starttime).getTime() - new Date().getTime()) / (1000 *60 * 60 *24))}} 天</p>
        <p v-else>报名结束</p>
        <p>
          <span style="cursor: pointer" @click="toDetail(competition.id)">竞赛详情 ></span>
        </p>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "competition",
  data(){
    return{
      competitionList: [],
      userId: 0
    }
  },
  created() {
    this.userId = localStorage.getItem("userId");
    this.getUserCompetitionList();
  },
  methods:{
    toDetail(id){
      sessionStorage.setItem("competitionId",id);
      this.$router.push({
        path: '/user/competitionDetail'
      })
    },
    getUserCompetitionList() {
      this.$axios.get("/competition/getCompetitionList/" + this.userId).then(res => {
        this.competitionList = res.data.data.competitionList;
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
      return (year + "-" + month + "-" + day);
    },
  }
}
</script>

<style scoped>
.competition{
  border: 5px solid #fafafa;
  padding: 20px;
  margin-bottom: 20px;
}

h3{
  font-size: 25px;
  margin: 10px 0;
  letter-spacing: 3px;
}
p{
  color: #aaa;
}
.hd{
  float: left;
  width: 100px;
  overflow: hidden;
  font-weight: 600;
}
.info{
  float: right;
  padding-top: 30px;
  width: 200px;
  margin-right: 30px;
}
.info  .el-button{
  background-color: #34bfa7;
  color: #fff;
  font-weight:600;
  padding: 15px 30px;
}
.info p{
  color: #666;
  font-weight: 600;
}
.el-tag{
  display: inline-block;
  height: 20px;
  /*margin-bottom: 10px;*/
  margin-right: 10px;
  margin-top: -10px;
  line-height: 20px;
  border: 0;
  padding: 0;
  font-size: 16px;
}
.competition_photo{
  height: 200px;
  width: 300px;
  border-radius: 15px;
  margin-right: 50px;
  margin-left: 50px;
  vertical-align: bottom;
}
</style>
