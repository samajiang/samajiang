<template>
  <div>
    <div style="width: 400px;height: 350px;margin: 150px auto;background-color: rgba(107,149,224,0.5);border: 10px">
      <div style="width: 100%;height: 100px;font-size: 30px;line-height: 100px;text-align: center;color: #4a5ed0">管理员注册
      </div>
      <div style="margin-top: 25px;text-align: center;height: 320px">
        <el-form :model="admin">
          <el-form-item>
            <el-input v-model="admin.user_name" prefix-icon="el-icon-user" style="width: 80%"
                      placeholder="请输入用户名"></el-input>
          </el-form-item>
          <el-form-item>
            <el-input v-model="admin.password" prefix-icon="el-icon-lock" style="width: 80%"
                      placeholder="请输入密码"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button style="width: 80%;margin-top: 10px" type="primary" @click="register()">注册</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
import request from "@/Utils/Request";

export default {
  name: "Register",
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
    register() {
      request.post("/admin/register",this.admin).then(res =>{
        if(res.code === '0'){
          this.$message({
            message:'注册成功',
            type:'success'
          });
          this.$router.push("/login");
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