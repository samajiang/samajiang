<template>
  <div calss="main">

    <div class="search" style="margin-bottom: 30px">
      <el-input v-model="params.name" placeholder="请输入姓名" style="width: 120px"></el-input>
      <el-input v-model="params.phone" placeholder="请输入电话" style="width: 120px;margin-left: 10px"></el-input>
      <el-button type="warning" style="margin-left: 10px" @click="findbyseach()">查询</el-button>
      <el-button type="warning" style="margin-left: 10px" @click="rest()">清空</el-button>
      <el-button type="primary" style="margin-left: 10px" @click="addadmin()">新增</el-button>
    </div>

    <div class="biaoge">
      <el-table
          :data="tableData"
          style="width: 100%">

        <el-table-column prop="user_name" label="姓名"></el-table-column>


        <el-table-column prop="age" label="年龄"></el-table-column>

        <el-table-column prop="sex" label="性别"></el-table-column>

        <el-table-column prop="phone" label="电话"></el-table-column>

        <el-table-column prop="root" label="管理员"></el-table-column>

        <el-table-column label="操作">

          <template slot-scope="scope">
            <el-button type="primary" icon="el-icon-edit" circle @click="edit(scope.row)"></el-button>
            <el-popconfirm title="这是一段内容确定删除吗？" @confirm="delte(scope.row.user_id)">
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
          :current-page="params.pegeNum"
          :page-sizes="[5, 10, 15, 20]"
          :page-size="params.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>
    <div class="dialog">
      <el-dialog title="请填写用户信息" :visible.sync="dialogFormVisible" width="30%">
        <el-form :model="form">
          <el-form-item label="姓名" label-width="15%">
            <el-input v-model="form.user_name" autocomplete="off" style="width: 90%"></el-input>
          </el-form-item>
          <el-form-item label="年龄" label-width="15%">
            <el-input v-model="form.age" autocomplete="off" style="width: 90%"></el-input>
          </el-form-item>
          <el-form-item label="性别" label-width="15%">
            <el-radio v-model="form.sex" label="男">男</el-radio>
            <el-radio v-model="form.sex" label="女">女</el-radio>
          </el-form-item>
          <el-form-item label="管理员" label-width="15%">
            <el-radio v-model="form.root" label="是">是</el-radio>
            <el-radio v-model="form.root" label="否">否</el-radio>
          </el-form-item>
          <el-form-item label="电话" label-width="15%">
            <el-input v-model="form.phone" autocomplete="off" style="width: 90%"></el-input>
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
  name: "AdminView",
  data() {
    return {
      params: {
        name: '',
        phone: '',
        pegeNum: 1,
        pageSize: 5
      },
      total: 0,
      tableData: [],
      dialogFormVisible: false,
      form: {}
    }
  },
  //页面加载的时候,做一些事情,在create里面
  created() {
    this.findbyseach();
  },
  //会定义一些页面上控件发出的事件,调用的方法
  methods: {

    //定义查询按钮点击事件
    findbyseach() {
      request.get("/admin/search", {
        params: this.params
      }).then(res => {
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
    //  定义清空按钮
    rest() {
      this.params = {
        name: '',
        phone: '',
        pegeNum: 1,
        pageSize: 5
      }
      this.findbyseach();
    },
    //  定义分页条数改变
    handleSizeChange(pagesize) {
      this.params.pageSize = pagesize;
      this.findbyseach();
    },
    //  定义当前页改变
    handleCurrentChange(pagenum) {
      this.params.pegeNum = pagenum;
      this.findbyseach();
    },
    //定义添加用户按钮
    addadmin() {
      this.form = {};
      this.dialogFormVisible = true;

    },
    //  点击确定按钮把数据提交到后台点击事件
    submit() {
      request.post("/admin/updata", this.form).then(res => {
        if (res.code === '0') {
          this.$message({
            message: '操作成功',
            type: 'success'
          })
          this.dialogFormVisible = false;
          this.findbyseach();
        } else {
          this.$message({
            message: res.msg,
            type: 'error'
          })
        }
      })
    },
    //定义编辑按钮事件
    edit(obj) {
      this.form = obj;
      this.dialogFormVisible = true;
    },
    //定义删除按钮事件
    delte(userid) {
      console.log('当前行数据:', userid); // 打印整个行数据
      request.delete("/admin/" + userid).then(res => {
        if (res.code === '0') {
          this.$message({
            message: '删除成功',
            type: 'success'
          })
          this.findbyseach();
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