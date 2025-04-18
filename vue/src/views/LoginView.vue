<template>
  <div>
    <div style="width: 500px;height: 400px;margin: 150px auto;background-color: rgba(107,149,224,0.5);border: 10px">
      <div style="width: 100%;height: 100px;font-size: 30px;line-height: 100px;text-align: center;color: #4a5ed0">欢迎登录
      </div>
      <div style="margin-top: 25px;text-align: center;height: 320px">
        <el-form :model="admin">
          <el-form-item>
            <el-input v-model="admin.user_name" prefix-icon="el-icon-user" style="width: 80%"
                      placeholder="请输入用户名"></el-input>
          </el-form-item>
          <el-form-item>
            <el-input v-model="admin.password" show-password prefix-icon="el-icon-lock" style="width: 80%"
                      placeholder="请输入密码"></el-input>
          </el-form-item>
          <el-row>
            <el-button style="width: 300px;margin-top: 10px" type="primary" @click="login()">登录</el-button>

            <el-button style="width: 300px;margin: auto;margin-top: 10px" type="primary" @click="registerbtn()">注册</el-button>
          </el-row>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
import request from "@/Utils/Request";

export default {
  name: "Login",
  data() {
    return {
      admin: {}
    }
  },
  //页面加载的时候做的事放在created里
  created() {
  },
  //定义页面控件触发的事件调用的方法
  methods: {
    registerbtn(){
      this.$router.push("/register");
    },
    login() {
      request.post("/admin/loginpc",this.admin).then(res =>{
        if(res.code === '0'){
          this.$message({
            message:'登录成功',
            type:'success'
          });
          //将后台查询到的用户信息存储到前端的Localstory中
          localStorage.setItem("AdminName",JSON.stringify(res.data));
          this.$router.push("/");
        }else{
          this.$message({
            message:res.msg,
            type:'error'
          });
        }
      })
    }
  }
}
</script>

<style>

</style>