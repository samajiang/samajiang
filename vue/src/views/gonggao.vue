<template>
  <div calss="main">

    <div class="search" style="margin-bottom: 30px">
      <el-button type="primary" style="margin-left: 10px" @click="addgonggao()">新增</el-button>
    </div>

    <div class="biaoge">
      <el-table
          :data="tableData"
          style="width: 100%">

        <el-table-column prop="gonggao_id" label="公告ID"></el-table-column>


        <el-table-column prop="gonggao_name" label="公告名称"></el-table-column>

        <el-table-column prop="gonggao_pic" label="公告图片">
          <template v-slot="scope">
            <el-image
                style="width: 100px; height: 100px"
                :src="'http://192.168.1.101:8080/api/gonggao/'+ scope.row.gonggao_pic"
                :preview-src-list="['http://192.168.1.101:8080/api/gonggao/'+ scope.row.gonggao_pic]"></el-image>
          </template>
        </el-table-column>


        <el-table-column label="操作">

          <template slot-scope="scope">
            <el-button type="primary" icon="el-icon-edit" circle @click="edit(scope.row)"></el-button>
            <el-popconfirm title="确定删除吗？" @confirm="delte(scope.row.gonggao_id)">
              <el-button type="danger" icon="el-icon-delete" slot="reference" circle
                         style="margin-left: 5px"></el-button>
            </el-popconfirm>
          </template>

        </el-table-column>
      </el-table>
    </div>
    <div class="dialog">
    <el-dialog title="请填写公告信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form :model="form">
        <el-form-item label="公告名称" label-width="25%">
          <el-input v-model="form.gonggao_name" autocomplete="off" style="width: 80%"></el-input>
        </el-form-item>
        <!--          上传图片到服务器和数据库-->
        <el-form-item v-model="form.gonggao_pic" label="疫苗图片" label-width="25%">
          <el-upload
              action="http://192.168.1.101:8080/api/gonggao/uploadgonggao"

              :data="{gonggaoname:this.form.gonggao_name}"
              :on-success="successupload">
            <el-button size="small" type="primary">点击上传</el-button>
            <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="submit()">确 定</el-button>
      </div>
    </el-dialog>
  </div>
  </div>

</template>

<script>
import request from "@/Utils/Request";

export default {
  name: "gonggao",
  data() {
    return {
      form: {},
      tableData: [],
      dialogFormVisible: false,
    }
  },
  created() {
    this.findgonggao();
  },
  methods: {
    //查询公告信息方法
    findgonggao() {
      request.get("/gonggao/fetchgonggao").then(res => {
        this.tableData = res
        // if (res.code === '0') {
        //   this.tableData = res.data;
        //   console.log("公告数据",res.data)
        // } else {
        //   this.$message({
        //     message: res.msg,
        //     type: 'error'
        //   })
        // }
      })
    },
    //  编辑公告信息
    edit(abc){
      this.form = abc;
      this.dialogFormVisible = true;
    },
    //提交新增公告
    submit() {
      request.post("/gonggao/addgonggao", this.form).then(res => {
        console.log("ssss",this.form)
        if (res.code === '0') {
          this.$message({
            message: "操作成功",
            type: "success"
          })
          this.dialogFormVisible = false;
          this.findgonggao();
        } else {
          this.$message({
            message: res.msg,
            type: 'error'
          })
        }
      })
    },
    //  新增公告按钮的方法
    addgonggao() {
      this.form = {};
      this.dialogFormVisible = true;
    },
    //上传成功的事件
    successupload(res){
      this.form.gonggao_pic = res.data;

    },
    delte(id) {
      request.delete("/gonggao/deletgonggaobyid/" + id).then(res => {
        if (res.code === '0') {
          this.$message({
            message: '删除成功',
            type: 'success'
          })
          this.findgonggao();
        } else {
          this.$message({
            message: res.msg,
            type: 'error'
          })
        }
      })

    }
  }
}
</script>

<style scoped>

</style>