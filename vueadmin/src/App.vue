<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
export default {
  name: "App",
  data() {
    return {
      socket: null,
      userId: localStorage.getItem("userId")
    }
  },
  created() {
    if (this.userId != null){
      this.initWebSocket();
    }
  },
  methods: {
    initWebSocket() {
      let that = this;
      if ("WebSocket" in window) {
        console.log("您的浏览器支持 WebSocket!");
        let socketUrl = "http://localhost:8081/websocket/" + this.userId;
        socketUrl = socketUrl.replace("https", "ws").replace("http", "ws");
        that.ws = new WebSocket(socketUrl);
        that.$globalWebSocket.setWs(that.ws);
        that.ws.onopen = function () {
          console.log('webSocket connect successful')
        };
        that.ws.onclose = function () {
          console.log("webSocket connect closed");
          setTimeout(() => {
            that.initWebSocket();
          }, 2000);
        };
      } else {
        console.log("您的浏览器不支持 WebSocket!");
      }
    }
  },
  watch: {
    $route(to, from) {
      if (to.path != '/login') {
        let obj = {
          name: to.name,
          title: to.meta.title
        }
        this.$store.commit("addTab", obj);
      }
    }
  }
}
</script>

<style>
.el-aside {
  width: auto !important;
}

html, body, #app {
  font-family: "Helvetica Neue", "Hiragino Sans GB", 'WenQuanYi Micro Hei', "Microsoft YaHei", sans-serif;
  height: 100%;
  padding: 0;
  margin: 0;
  font-size: 15px;

}

.el-popover {
  max-height: 400px;
  overflow: auto;
}
</style>
