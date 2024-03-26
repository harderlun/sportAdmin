import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default {
    state: {
        menuList: [],
        permList: [],
        hasRoute: false,
        editableTabsValue: 'index',
        editableTabs: [{
            title: '首页',
            name: 'index'
        }]
    },
    mutations: {
        setMenuList(state,menus){
            state.menuList = menus;
        },
        setPermList(state,perms){
            state.permList = perms;
        },
        changeRoutesStatus(state,hasRoute){
            state.hasRoute = hasRoute;
            sessionStorage.setItem("hasRoute",hasRoute);
        },
        addTab(state,tab) {
            let index = state.editableTabs.findIndex(e => e.name === tab.name);
            if (index === -1){
                state.editableTabs.push({
                    title: tab.title,
                    name: tab.name
                });
            }
            state.editableTabsValue = tab.name;
        },
        resetState: (state,tab) => {
            state.menuList = [];
            state.permList = [];
            state.hasRoute = false;
            state.editableTabsValue = 'index';
            state.editableTabs = [{
                title: '首页',
                name: 'index'
            }]
        }
    },
    actions: {},

}
