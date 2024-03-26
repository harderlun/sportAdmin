<template>
  <div class="notice-bar" @click="tipClick">
    <div class="notice-bar__icon">
      <img :src="require('@/assets/notice.png')">
    </div>
    <div ref="wrap" class="notice-bar__wrap">
      <div ref="content" class="notice-bar__content" :style="contentStyle">{{ text }}</div>
    </div>
  </div>
</template>

<script>
export default {
  name: "noticeComponent",
  props: {
    text: {
      type: String,
      default: ''
    },
    speed: {
      type: Number,
      default: 40
    },
    defaultWidth: {
      type: Number,
      default: 375
    }
  },
  data () {
    return {
      contentStyle: {
        transitionDuration: '0s',
        transform: 'translateX(0px)'
      },
      wrapWidth: 0,
      contentWidth: 0,
      time: 0,
      timer: null,
      convertSpeed: 1
    }
  },
  created () {},
  mounted () {
    if (this.text) {
      this.init()
    }
  },
  watch: {
    text () {
      this.init()
    }
  },
  methods: {
    init () {
      const _width = window.innerWidth
      this.convertSpeed = _width / this.defaultWidth * this.speed  // 根据分辨率转化成不同的速度
      this.wrapWidth = this.$refs.wrap.offsetWidth
      this.contentWidth = this.$refs.content.offsetWidth
      this.startAnimate()
      this.timer = setInterval(() => {
        this.startAnimate()
      }, this.time * 1100)
      this.$once('hook:beforeDestroy', () => {
        clearInterval(this.timer)
        this.timer = null
      })
    },
    startAnimate () {
      this.contentStyle.transitionDuration = '0s'
      this.contentStyle.transform = 'translateX(' + this.wrapWidth*2 + 'px)'
      this.time = (this.wrapWidth*2 + this.contentWidth) / this.convertSpeed
      setTimeout(() => {
        this.contentStyle.transitionDuration = this.time + 's'
        this.contentStyle.transform = 'translateX(-' + this.contentWidth + 'px)'
      }, 200)
    },
    tipClick () {
      this.$emit('click')
    }
  }
}
</script>
<style scoped>
.notice-bar {
  position: relative;
  background-color: #f8f8f8;
  width: 100%;
  height: 40px;
  padding-left: 0;
  padding-right: 0;
  font-family: 微软雅黑;
  fontSize:14px;
  font-weight: 400;
  color: #868DAA;
  display: flex;
  align-items: center;
}
.notice-bar__wrap {
  position: relative;
  display: flex;
  flex: 1;
  height: 100%;
  align-items: center;
  overflow: hidden;
}
.notice-bar__content {
  position: absolute;
  white-space: nowrap;
  transition-timing-function: linear;
}
.notice-bar__icon {
    width: 20px;
    height: 20px;
    margin-left: 20px;
}
.notice-bar__icon img{
  width: 100%;
}
</style>
