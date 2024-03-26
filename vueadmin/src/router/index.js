import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import index from '../views/index.vue'
import Equipment from "../views/user/equipment";
import CompetitionDetail from "@/views/user/competitionDetail";
import Competition from "../views/user/competition"
import Details from "@/views/notice/Details";
import noticeList from "@/views/notice/noticeList";
import Order from "@/views/user/order";
import axios from "../axios";
import store from "@/store";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home,
    children: [
      {
        path: '/index',
        name: 'index',
        meta: {
          title: "首页"
        },
        component: index
      },
      {
        path: '/user/equipment',
        name: 'equipment',
        meta: {
          title: "器材列表"
        },
        component: Equipment
      },
      {
        path: '/user/competition',
        name: 'competition',
        meta: {
          title: "竞赛列表"
        },
        component: Competition
      },
      {
        path: '/user/competitionDetail',
        name: 'competitionDetail',
        meta: {
          title: "竞赛详情"
        },
        component: CompetitionDetail
      },
      {
        path: '/user/order',
        name: 'order',
        meta: {
          title: "我的订单"
        },
        component: Order
      },
      {
        path: '/notice/Details',
        name: 'Details',
        meta: {
          title: "公告详情"
        },
        component: Details
      },
      {
        path: '/notice/noticeList',
        name: 'noticeList',
        meta: {
          title: "公告列表"
        },
        component: noticeList
      },
      {
        path: '/userCenter',
        name: 'UserCenter',
        meta: {
          title: "个人中心"
        },
        component: () => import('../views/UserCenter.vue')
      }
    ]
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to,from,next) => {
  let hasRoute = store.state.menus.hasRoute;
  let token = localStorage.getItem("token");
  if (to.path == '/login'){
    next();
  }else if (!token){
    next({path: '/login'});
  }else if(token && !hasRoute){
    axios.get("/sys/menu/nav",{
      headers: {
        Authorization: localStorage.getItem("token")
      }
    }).then(res => {
      //拿到menuList
      store.commit("setMenuList",res.data.data.nav);
      //拿到用户权限
      store.commit("setPermList",res.data.data.authorities);
      //动态绑定路由
      let newRoutes = router.options.routes;
      res.data.data.nav.forEach(menu => {
        if(menu.children){
          menu.children.forEach(e => {
            //转成路由
            let  route = menuToRoute(e);
            //把路由添加到路由管理中
            if (route){
              newRoutes[0].children.push(route);
            }
          })
        }
      })
      router.addRoutes(newRoutes);
      hasRoute = true;
      store.commit("changeRoutesStatus",hasRoute);
    })
  }
  next()
})

const menuToRoute = (menu) => {
  if (!menu.component){
    return null;
  }
  let route = {
    name: menu.name,
    path: menu.path,
    meta: {
      icon: menu.icon,
      title: menu.title
    }
  }
  route.component = () => import('@/views/'+ menu.component +'.vue');
  return route;
}

export default router
