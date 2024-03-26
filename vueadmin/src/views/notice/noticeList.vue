<template>
  <div>
    <el-collapse accordion>
      <el-collapse-item v-for="notice in this.noticeList" :key="notice.id">
        <template slot="title">
          <div class="title">{{notice.title}}</div>
        </template>
        <div class="creator">{{'发布人: ' + notice.writer}}</div>
        <div class="time">{{'发布时间: ' + dateFormat(notice.created) }}</div>
        <div @click="toNoticeDetail(notice.id)" class="toInfo">查看详情</div>
      </el-collapse-item>

    </el-collapse>



  </div>
</template>

<script>
export default {
  name: "competition",
  data(){
    return{
      noticeList: []
    }
  },
  created() {
    this.getUserNoticeList();
  },
  methods:{
    toNoticeDetail(id){
      sessionStorage.setItem("noticeId", id);
      this.$router.push({
        path: '/notice/Details'
      })
    },
    getUserNoticeList() {
      this.$axios.get("/notice/list").then(res => {
        this.noticeList = res.data.data.pageData.records;
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
div{
  color: #666;
}
/*标题*/
.title{
  font-size: 18px;
  font-weight: 600;
  color: #666;
}
/*发布人*/
.creator , .time , .toInfo{
  font-size: 16px;
}
/*查看详情*/
.toInfo:hover{
  cursor: pointer;
  color: #409EFF;
}
</style>
