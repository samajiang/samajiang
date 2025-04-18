<template>
  <div calss="main">

    <div class="search" style="margin-bottom: 30px">
      <el-input v-model="params.name" placeholder="请输入预定人姓名" style="width: 160px"></el-input>
      <el-input v-model="params.phone" placeholder="请输入预定人电话" style="width: 160px;margin-left: 10px"></el-input>
      <el-button type="warning" style="margin-left: 10px" @click="fetchorderlist()">查询</el-button>
      <el-button type="warning" style="margin-left: 10px" @click="rest()">清空</el-button>
    </div>

    <div class="biaoge">
      <el-table
          :data="tableData"
          style="width: 100%">

        <el-table-column prop="user_name" label="预定人姓名"></el-table-column>


        <el-table-column prop="order_name" label="接种人"></el-table-column>

        <el-table-column prop="order_vaccine" label="接种疫苗"></el-table-column>

        <el-table-column prop="order_time" label="接种时间"></el-table-column>
        <el-table-column prop="order_addr" label="接种地点"></el-table-column>
        <el-table-column prop="order_phone" label="电话"></el-table-column>

        <el-table-column label="操作">

          <template slot-scope="scope">
            <el-button type="primary" icon="el-icon-edit" circle @click="edit(scope.row)">

            </el-button>
            <el-popconfirm title="这是一段内容确定删除吗？" @confirm="delte(scope.row.order_id)">
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
      <el-dialog title="请修改预定信息" :visible.sync="dialogFormVisible" width="50%">
        <el-form :model="form">
          <el-form-item label="预定人姓名" label-width="15%">
            <el-input disabled = "false" v-model="form.user_name" autocomplete="off" style="width: 90%"></el-input>
          </el-form-item>
          <el-form-item label="接种人姓名" label-width="15%">
            <el-input disabled = "fales" v-model="form.order_name" autocomplete="off" style="width: 90%"></el-input>
          </el-form-item>
          <el-form-item label="接种时间" label-width="15%">
            <el-input  v-model="form.order_time" autocomplete="off" style="width: 90%"></el-input>
          </el-form-item>
          <el-form-item label="接种疫苗" label-width="15%">
            <el-input disabled = "fales" v-model="form.order_vaccine" autocomplete="off" style="width: 90%"></el-input>
          </el-form-item>
          <el-form-item label="电话" label-width="15%">
            <el-input disabled = "fales" v-model="form.order_phone" autocomplete="off" style="width: 90%"></el-input>
          </el-form-item>
          <el-form-item label="接种地点" label-width="15%">
            <el-input disabled = "fales" v-model="form.order_addr" autocomplete="off" style="width: 90%"></el-input>
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
  name:'OrderView',
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
    this.fetchorderlist();
  },
  //会定义一些页面上控件发出的事件,调用的方法
  methods: {

    //定义查询按钮点击事件
    fetchorderlist() {
      request.get("/order/fetchorderlist", {
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
      this.fetchorderlist();
    },
    //  定义分页条数改变
    handleSizeChange(pagesize) {
      this.params.pageSize = pagesize;
      this.fetchorderlist();
    },
    //  定义当前页改变
    handleCurrentChange(pagenum) {
      this.params.pegeNum = pagenum;
      this.fetchorderlist();
    },
    //定义添加用户按钮
    addadmin() {
      this.form = {};
      this.dialogFormVisible = true;

    },
    //  点击确定按钮把数据提交到后台点击事件
    submit() {
      request.post("/order/updatabyid", this.form).then(res => {
        if (res.code === '0') {
          this.$message({
            message: '操作成功',
            type: 'success'
          })
          this.dialogFormVisible = false;
          this.fetchorderlist();
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
    delte(id) {
      request.delete("/order/deleteorderbyid/"+id).then(res => {
        if (res.code === '0') {
          this.$message({
            message: '删除成功',
            type: 'success'
          })
          this.fetchorderlist();
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