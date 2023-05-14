<template>
     <div style="
            height: 50px;
            line-height: 50px;
            border-bottom: 1px solid #ccc;
            display: flex;
           ">
          <div style="
             width: 200px;
             padding-left: 30px;
             font-weight: bold;
             color: dodgerblue;
            ">
               管理系统
          </div>
          <div style="flex: 1"></div>
          <div style="width: 150px">
               <el-dropdown @command="handleCommand">
                    <span class="el-dropdown-link">
                         <el-avatar src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"></el-avatar>
                         admin<i class="el-icon-arrow-down el-icon--right"></i>
                    </span>
                    <el-dropdown-menu slot="dropdown">
                         <el-dropdown-item command="a">个人信息</el-dropdown-item>
                         <el-dropdown-item command="b">退出系统</el-dropdown-item>
                    </el-dropdown-menu>
               </el-dropdown>
          </div>
     </div>
</template>
    
<script>
import router from "@/router";

export default {
     name: "Header",
     data() {
          return {
               name: "未登录",
          };
     },
     created() { },
     methods: {
          handleCommand(command) { },
     },
};
</script>