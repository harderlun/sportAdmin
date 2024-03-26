<template>
  <el-menu
      :default-active="this.$store.state.menus.editableTabsValue"
      class="el-menu-vertical-demo"
      background-color="#545c64"
      text-color="#fff"
      active-text-color="#ffd04b" :collapse="isCollapse">
    <router-link to="/index">
      <el-menu-item index="index" @click="selectMenu({name: 'index',title: '首页'})">
        <i class="el-icon-s-home"></i>
        <template slot="title">
          <span slot="title">首页</span>
        </template>
      </el-menu-item>
    </router-link>
    <router-link to="/user/equipment">
      <el-menu-item index="equipment" @click="selectMenu({name: 'equipment',title: '器材列表'})">
        <i class="el-icon-basketball"></i>
        <template slot="title">
          <span slot="title">器材列表</span>
        </template>
      </el-menu-item>
    </router-link>
    <router-link to="/user/competition">
      <el-menu-item index="competition" @click="selectMenu({name: 'competition',title: '竞赛列表'})">
        <i class="el-icon-football"></i>
        <template slot="title">
          <span slot="title">竞赛列表</span>
        </template>
      </el-menu-item>
    </router-link>
    <router-link to="/notice/noticeList">
      <el-menu-item index="noticeList" @click="selectMenu({name: 'noticeList',title: '公告列表'})">
        <i class="el-icon-message-solid"></i>
        <template slot="title">
          <span slot="title">公告列表</span>
        </template>
      </el-menu-item>
    </router-link>
    <el-submenu :index="menu.name" v-for="(menu,index) in menuList" :key="index">
      <template slot="title">
        <i :class="menu.icon" ></i>
        <span class="title">{{menu.title}}</span>
      </template>
      <router-link :to="item.path" v-for="(item,index) in menu.children" :key="index">
        <el-menu-item :index="item.name" @click="selectMenu(item)">
          <template slot="title">
            <i :class="item.icon"></i>
            <span slot="title">{{item.title}}</span>
          </template>
        </el-menu-item>
      </router-link>
    </el-submenu>
    <el-menu-item style="overflow: hidden;">
      <div style="width: 200px;" @click="changeCollapse()">
        <i class="el-icon-caret-right"  v-model="isCollapse"></i>
      </div>
    </el-menu-item>
  </el-menu>
</template>

<script>
export default {
  name: "SideMenu",
  data(){
    return{
      isCollapse: false
    }
  },
  computed: {
    menuList: {
      get(){
        return this.$store.state.menus.menuList
      }
    }
  },
  methods: {
    selectMenu(item){
      this.$store.commit("addTab",item);
    },
    changeCollapse(){
      this.isCollapse = !this.isCollapse;
    }
  }
}
</script>

<style scoped>


.el-menu-vertical-demo{
  height: 100%;
}
.el-submenu:hover .title{
  color: #409EFF!important;
}
.el-submenu:hover .el-icon-s-operation{
  color: #409EFF!important;
}
.el-submenu:hover .el-icon-baseball{
  color: #409EFF!important;
}
.el-submenu:hover .el-icon-location-outline{
  color: #409EFF!important;
}
.el-submenu:hover .el-icon-s-tools{
  color: #409EFF!important;
}
.el-menu-item{
  transition: all .3s linear;
}
.el-menu-item:hover i{
  color: #409EFF!important;
}
.el-menu-item:hover{
  color: #409EFF!important;
}
.el-menu-item:hover{
  transform: scale(1.06);
}
a{
  text-decoration: none;
}
.router-link-active{
  text-decoration: none;
}
</style>
