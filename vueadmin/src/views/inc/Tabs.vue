<template>
  <!-- 使用 Element UI 的标签页组件 -->
  <el-tabs style="height: 50px;" v-model="editableTabsValue" type="card" closable @tab-remove="removeTab" @tab-click="clickTab">
    <!-- 使用 v-for 循环渲染标签页 -->
    <el-tab-pane style="height: 50px;" v-for="(item, index) in editableTabs" :key="item.name" :label="item.title" :name="item.name">
    </el-tab-pane>
  </el-tabs>
</template>

<script>
export default {
  name: "Tabs",
  data () {
    return {

    }
  },
  computed: {
    // 使用 computed 属性监听可编辑标签页的状态
    editableTabs: {
      get () {
        return this.$store.state.menus.editableTabs;
      },
      set (val) {
        this.$store.state.menus.editableTabs = val;
      }
    },
    editableTabsValue: {
      get () {
        return this.$store.state.menus.editableTabsValue;
      },
      set (val) {
        this.$store.state.menus.editableTabsValue = val;
      }
    }
  },
  methods: {
    // 方法用于移除标签页
    removeTab (targetName) {
      let tabs = this.editableTabs;
      let activeName = this.editableTabsValue;
      if (targetName == 'index') return;
      if (activeName === targetName) {
        tabs.forEach((tab, index) => {
          if (tab.name === targetName) {
            let nextTab = tabs[index + 1] || tabs[index - 1];
            if (nextTab) {
              activeName = nextTab.name;
            }
          }
        });
      }
      // 更新标签页列表和当前激活的标签页
      this.editableTabsValue = activeName;
      this.editableTabs = tabs.filter(tab => tab.name !== targetName);
      // 切换路由至下一个标签页
      this.$router.push({ name: activeName });
    },
    // 方法用于点击标签页
    clickTab (target) {
      // 切换路由至点击的标签页
      this.$router.push({ name: target.name });
    }
  }
}
</script>

<style scoped>
.el-tabs__nav-wrap .el-tabs__nav-scroll .el-tabs__nav {
  background-color: red;
}
</style>
