<template>
  <div calss="main">

    <div class="search" style="margin-bottom: 30px">
      <el-button type="primary" style="margin-left: 10px" @click="addvaccine()">新增</el-button>
    </div>

    <div class="biaoge">
      <el-table
          :data="tableData"
          style="width: 100%">

        <el-table-column prop="bianhao" label="疫苗编号"></el-table-column>


        <el-table-column prop="vaccine_name" label="疫苗名称"></el-table-column>

        <el-table-column prop="vaccine_datil" label="疫苗详情"></el-table-column>

        <el-table-column label="疫苗图片">
          <template v-slot="scope">
            <el-image
                style="width: 100px; height: 100px"
                :src="'http://192.168.1.101:8080/api/file/'+ scope.row.vaccine_pic"
                :preview-src-list="['http://192.168.1.101:8080/api/file/'+ scope.row.vaccine_pic]"></el-image>
          </template>
        </el-table-column>

        <el-table-column prop="vaccine_count" label="疫苗数量"></el-table-column>


        <el-table-column label="操作">

          <template slot-scope="scope">
            <el-button type="primary" icon="el-icon-edit" circle @click="edit(scope.row)"></el-button>
            <el-popconfirm title="确定删除吗？" @confirm="delte(scope.row.vaccine_id)">
              <el-button type="danger" icon="el-icon-delete" slot="reference" circle
                         style="margin-left: 5px"></el-button>
            </el-popconfirm>
          </template>

        </el-table-column>
      </el-table>
    </div>

    <div class="block" style="margin-top: 30px">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="params.pNum"
          :page-sizes="[5, 10, 15, 20]"
          :page-size="params.pSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>
    <div class="dialog">
      <el-dialog title="请填写疫苗信息" :visible.sync="dialogFormVisible" width="30%">
        <el-form :model="form">
          <el-form-item label="疫苗名称" label-width="25%">
            <el-input v-model="form.vaccine_name" autocomplete="off" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="疫苗编号" label-width="25%">
            <el-input v-model="form.bianhao" autocomplete="off" style="width: 80%"></el-input>
          </el-form-item>
            <el-form-item label="疫苗详情" label-width="25%">
              <el-input v-model="form.vaccine_datil" autocomplete="off" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="疫苗数量" label-width="25%">
            <el-input v-model="form.vaccine_count" autocomplete="off" style="width: 80%"></el-input>
          </el-form-item>
<!--          上传图片到服务器和数据库-->
          <el-form-item v-model="form.vaccine_pic" label="疫苗图片" label-width="25%">
            <el-upload
                action="http://192.168.1.101:8080/api/file/upload"

                :data="{vaccinename:this.form.vaccine_name}"
                :on-success="successupload"
                >
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
  name: "Vaccine",
  data() {
    return {
      params: {
        name: '',
        pNum: 1,
        pSize: 5
      },
      total: 0,
      tableData: [],
      dialogFormVisible: false,
      form: {}
    }
  },
  created() {
    this.findvaccine();
  },
  methods: {
    //查询疫苗信息方法
    findvaccine() {
      request.get("/vaccine/pagesearchvaccine", {params: this.params}).then(res => {
        if (res.code === '0') {
          this.tableData = res.data.list;
          this.total = res.data.total;
        } else {
          this.$message({
            message: res.msg,
            type: 'error'
          })
        }
      })
    },
    //  新增疫苗按钮的方法
    addvaccine() {
      this.form = {};
      this.dialogFormVisible = true;
    },
    //提交新增疫苗
    submit() {
      request.post("/vaccine/addvaccine", this.form).then(res => {
        if (res.code === '0') {
          this.$message({
            message: "操作成功",
            type: "success"
          })
          this.dialogFormVisible = false;
          this.findvaccine();
        } else {
          this.$message({
            message: res.msg,
            type: 'error'
          })
        }
      })
    },
    //  删除疫苗的方法
    delte(id) {
      request.delete("/vaccine/" + id).then(res => {
        if (res.code === '0') {
          this.$message({
            message: '删除成功',
            type: 'success'
          })
          this.findvaccine();
        } else {
          this.$message({
            message: res.msg,
            type: 'error'
          })
        }
      })

    },
    //  页数改变的方法
    handleSizeChange(psize) {
      this.params.pSize = psize;
      this.findvaccine();
    },
    //  当前页改变的方法
    handleCurrentChange(pnum) {
      this.params.pNum = pnum;
      this.findvaccine();
    },
  //  编辑疫苗信息
    edit(abc){
      this.form = abc;
      this.dialogFormVisible = true;
    },
    //上传成功的事件
    successupload(res){
      this.form.vaccine_pic = res.data;

    }
  }
}
</script>

<style scoped>

</style>