<template>
     <div>
          <el-menu style="width: 250px; min-height: calc(100vh - 50px)" default-active="2" class="el-menu-vertical-demo"
               @open="handleOpen" @close="handleClose" :default-openeds="[path]" router>
               <!-- 1 用户模块-->
               <el-submenu index="1">
                    <template slot="title">
                         <i class="el-icon-user"></i>
                         <span>用户模块</span>
                    </template>
                    <el-menu-item-group>
                         <el-menu-item index="/userList">用户账号列表</el-menu-item>
                         <el-menu-item index="/userInfoList">用户详细信息</el-menu-item>
                    </el-menu-item-group>
               </el-submenu>
               <!-- 2 疫苗模块 -->
               <el-submenu index="2">
                    <template slot="title">
                         <i class="el-icon-menu"></i>
                         <span slot="title">疫苗模块</span>
                    </template>
                    <el-menu-item-group>
                         <el-menu-item index="/vaccinumtypelist">疫苗分类</el-menu-item>
                         <el-menu-item index="/vaccinumlist">疫苗列表</el-menu-item>
                    </el-menu-item-group>
               </el-submenu>
               <!-- 3 预约模块-->
               <el-submenu index="3">
                    <template slot="title">
                         <i class="el-icon-s-order"></i>
                         <span slot="title">预约模块</span>
                    </template>
                    <el-menu-item-group>
                         <el-menu-item index="/appointablelist">可预约列表</el-menu-item>
                         <el-menu-item index="/appointment">预约记录</el-menu-item>
                    </el-menu-item-group>
               </el-submenu>
               <!-- 4 健康资讯模块 -->
               <el-submenu index="4">
                    <template slot="title">
                         <i class="el-icon-document"></i>
                         <span slot="title">健康资讯</span>
                    </template>
                    <el-menu-item-group>
                         <el-menu-item index="/list">资讯列表</el-menu-item>
                         <el-menu-item index="/userlist">健康上报</el-menu-item>
                    </el-menu-item-group>
               </el-submenu>
               <!-- 5 核酸模块 -->
               <el-submenu index="5">
                    <template slot="title">
                         <i class="el-icon-first-aid-kit"></i>
                         <span slot="title">核酸模块</span>
                    </template>
                    <el-menu-item-group>
                         <el-menu-item index="/list">资讯列表</el-menu-item>
                         <el-menu-item index="/userlist">健康上报</el-menu-item>
                    </el-menu-item-group>
               </el-submenu>
          </el-menu>
     </div>
</template>
         
<script>
export default {
     data() {
          return {
               //path变量
               path: this.$route.path,
          };
     },
     methods: {
          handleOpen(key, keyPath) {
               console.log(key, keyPath);
          },
          handleClose(key, keyPath) {
               console.log(key, keyPath);
          },
     },
};
</script>