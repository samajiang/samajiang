<template>
  <div style="display: flex;width: 100%">

<div style="width: 50%">

  <div id="bie" style="width: 100%;height: 600px">

  </div>

</div>
    <div style="width: 50%">
      <div id="gonggao" style="width: 100%;height: 600px">
        <div style="margin-bottom: 10px">公告</div>
        <template>
          <el-collapse v-model="activeNames" @change="handleChange">
            <el-collapse-item v-for="item in gonggaolist" :title="item.gonggao_name" :name="item.id">
              <div>
                <el-image
                    style="width: 100px; height: 100px"
                    :src="'http://192.168.1.101:8080/api/gonggao/'+item.gonggao_pic"
                    :preview-src-list="['http://192.168.1.101:8080/api/gonggao/'+ item.gonggao_pic]">
                    </el-image>
              </div>
            </el-collapse-item>
          </el-collapse>
        </template>
      </div>
    </div>
  </div>


</template>

<script>
import * as echarts from 'echarts';
import request from "@/Utils/Request";
export default {
  name: 'HomeView',
  data(){
    return{
      activeNames: ['1'],
      gonggaolist:[]
    }
  },
mounted() {
    this.fetchbiedata()
  this.fetchgonggao()
},
  methods:{
    initbie(data){
      let chartDom = document.getElementById('bie');
      let myChart = echarts.init(chartDom);
      let option;

      option = {
        title: {
          text: '疫苗数量统计',
          subtext: '饼图',
          left: 'center'
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'vertical',
          left: 'left'
        },
        series: [
          {
            name: '疫苗统计',
            type: 'pie',
            radius: '50%',
            data: data,
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      };

      option && myChart.setOption(option);
    },
    fetchbiedata(){
      request.get("/vaccine/fetchbievaccine").then(res => {
        if(res.code === '0'){
          this.changtype(res.data)
        }else{
         this.$message.error(res.msg)
        }
      })
    },
    changtype(data){
      let result = Object.entries(data[0]).map(([vaccinevalue,vaccinename]) =>({
        value:vaccinevalue,
        name:vaccinename
      }));
      this.initbie(result)
    },
    fetchgonggao(){
      request.get("/gonggao/fetchgonggao").then(res => {
this.gonggaolist = res
      })
    }
  }
}
</script>
