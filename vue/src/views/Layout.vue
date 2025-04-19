<template>
  <div>
    <el-container>
      <el-header style="background-color: #3d4349">
        <img src="@/assets/vaccine.png" alt="" style="width:40px;position:relative; top:10px"/>
        <span style="font-size: 20px;margin-left: 15px;color: white">儿童疫苗接种平台后台管理系统</span>

        <el-dropdown style="float: right;height: 60px;line-height: 60px">
  <span class="el-dropdown-link" style="color: aliceblue;font-size: 20px">
    {{AdminName.user_name}}<i class="el-icon-arrow-down el-icon--right"></i>
  </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item><div @click="loginout()">
              退出登录
            </div></el-dropdown-item>

          </el-dropdown-menu>
        </el-dropdown>
      </el-header>
    </el-container>


    <el-container>
      <el-aside style="overflow: hidden;min-height: 100vh;width: 250px;background-color: #545c64">
        <el-menu
            :default-active="$route.path"
            router
            class="el-menu-vertical-demo"
            @open="handleOpen"
            @close="handleClose"
            background-color="#545c64"
            text-color="#fff"
            active-text-color="#ffd04b">
          <el-menu-item index="/">
            <i class="el-icon-menu"></i>
            <span slot="title">系统首页</span>
          </el-menu-item>
          <el-submenu index="1">
            <template slot="title">
              <i class="el-icon-location"></i>
              <span>信息管理</span>
            </template>
            <el-menu-item index="/admin">用户管理</el-menu-item>
            <el-menu-item index="/vaccine">疫苗管理</el-menu-item>
            <el-menu-item index="/gonggao">公告管理</el-menu-item>
            <el-menu-item index="/OrderView">用户预约管理</el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>

      <el-main>
        <router-view/>
      </el-main>
    </el-container>


  </div>
</template>

<script>
export default {
  name: "Layout",
  data(){
    return {
      AdminName:localStorage.getItem("AdminName")?JSON.parse(localStorage.getItem("AdminName")):{}
    }
  },
  methods:{
    loginout(){
      localStorage.removeItem("AdminName");
      this.$router.push("/login");

    }
  }
}
</script>

<style>
.el-menu {
  border-right: none !important;
}
</style>