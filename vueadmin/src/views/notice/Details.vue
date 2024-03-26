<template>
  <div style="text-align: center">
    <div style="text-align: left;width: 1080px;margin: 0 auto">
      <div class="title">{{info.title}}</div>
      <div class="info">
        <span>{{'发布者：' + info.writer}}</span>
        <span>{{'发布时间：' + dateFormat(info.created)}}</span>
      </div>
      <div class="content">{{info.content}}</div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Details",
  data() {
    return {
      noticeId: 0,
      info: {}
    }
  },
  created() {
    this.noticeId = sessionStorage.getItem("noticeId");
    this.getNoticeInfo();
  },
  methods: {
    getNoticeInfo() {
      this.$axios.get("/notice/info/" + this.noticeId).then(res => {
        this.info = res.data.data.info;
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
/*公告标题*/
.title{
  font-size: 30px;
  font-weight: 700;
  text-align: center;
}
/*发布者/发布时间*/
.info{
  text-align: center;
  color: #ccc;
  margin: 10px 0;
}
.info span{
  margin: 0 20px;
}
/*公告内容*/
.content{
  margin: 0 auto;
  width: 900px;
  font-size: 16px;
  text-indent: 2em;
  line-height: 35px;
}
</style>
