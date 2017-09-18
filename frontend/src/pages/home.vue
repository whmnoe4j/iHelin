<template>
  <el-row>
    <el-col :span="4">
      <el-menu default-active="index" router theme="dark" style="height: 100%;">
        <el-menu-item index="/index">首页</el-menu-item>
        <el-submenu index="1">
          <template slot="title">财务</template>
          <el-menu-item index="/fi/customer">客户管理</el-menu-item>
          <el-menu-item index="/test">应收明细</el-menu-item>
          <el-menu-item index="/test2">账龄分析</el-menu-item>
          <el-submenu index="1-4">
            <template slot="title">选项4</template>
            <el-menu-item index="1-4-1">选项1</el-menu-item>
          </el-submenu>
        </el-submenu>
        <el-menu-item index="3">导航三</el-menu-item>
      </el-menu>
    </el-col>
    <el-col :span="20">
      <router-view></router-view>
    </el-col>
  </el-row>
</template>

<script>
  export default {}
</script>
