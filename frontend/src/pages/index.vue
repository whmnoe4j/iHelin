<template>
  <div>
    <div style="text-align: center">
      <el-button style="margin-top: 10px" @click="show = !show">Click Me</el-button>
    </div>
    <hr>
    <el-row>
      <el-col :span="6">
        <transition name="el-fade-in-linear">
          <div v-show="show" class="transition-box">.el-fade-in-linear</div>
        </transition>
      </el-col>
      <el-col :span="6">
        <transition name="el-fade-in">
          <div v-show="show" class="transition-box">.el-fade-in</div>
        </transition>
      </el-col>
      <el-col :span="6">
        <transition name="el-zoom-in-center">
          <div v-show="show" class="transition-box">.el-zoom-in-center</div>
        </transition>
      </el-col>
      <el-col :span="6">
        <transition name="el-zoom-in-top">
          <div v-show="show" class="transition-box">.el-zoom-in-top</div>
        </transition>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="6">
        <transition name="el-zoom-in-bottom">
          <div v-show="show" class="transition-box">.el-zoom-in-bottom</div>
        </transition>
      </el-col>
      <el-col :span="6">
        <el-collapse-transition>
          <div v-show="show">
            <div class="transition-box">el-collapse-transition</div>
            <div class="transition-box">el-collapse-transition</div>
            <div class="transition-box">el-collapse-transition</div>
            <div class="transition-box">el-collapse-transition</div>
          </div>
        </el-collapse-transition>
      </el-col>
    </el-row>
  </div>
</template>
<script>
  export default {
    data() {
      return {
        show: true
      }
    }
  }
</script>
<style scoped>
  .transition-box {
    margin-bottom: 10px;
    height: 100px;
    border-radius: 4px;
    background-color: #20A0FF;
    text-align: center;
    color: #fff;
    padding: 40px 20px;
    box-sizing: border-box;
    margin-right: 20px;
  }
</style>
