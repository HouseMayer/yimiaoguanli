<template>
  <div>
    <!-- 头部 -->
    <Header />
    <!-- 主题 -->
    <div style="display: flex;">
      <!-- 主题-左边 -->
      <Aside />
      <!-- 主题-右边  flex: 1 自适应布局 -->
      <router-view style="flex: 1;" />
    </div>
  </div>
</template>
    
    <!-- js -->
<script>
// 导入组件
import Header from "@/components/Header";
import Aside from "@/components/Aside";

export default {
  name: "Layout",
  //  使用哪些组件
  components: {
    Aside,
    Header,
  },
};
</script>