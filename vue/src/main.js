import Vue from 'vue'
import App from './App.vue'
import router from './router'
import 'element-ui/lib/theme-chalk/index.css';
import ElementUI from 'element-ui';
import vue from "vue";
// 完整引入
import * as echarts from 'echarts';
import '@/assets/global.css';

Vue.config.productionTip = false
vue.use(ElementUI);
vue.use(echarts);

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
