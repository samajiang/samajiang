

//创建一个axios对象出来
import axios from "axios";

const request = axios.create({
    baseURL: 'http://localhost:8080/api',
    timeout: 5000

})

//request拦截器
//可以自请求发送前对请求做一些处理
//比如统一token,对参数统一加密
request.interceptors.request.use(config => {
    config.headers['Content-Type'] = 'application/json;charset=utf-8';

    const user = localStorage.getItem("AdminName");
    if(user){
        config.headers['token'] = JSON.parse(user).token;
    }
//    config.headers['token'] = user.token; //设置请求头
    return config
}, error => {
    return Promise.reject(error)
});

//request拦截器
//可以在接口响应后统一处理结果
request.interceptors.response.use(
    response => {
        //    respondse.data即为后端返回的Result
        let res = response.data;
        //    兼容服务端返回的字符串数据
        if (typeof res == 'string') {
            res = res ? JSON.parent(res) : res
        }
        return res;
    },
    error => {
        console.log('err' + error) //for debug
        return Promise.reject(error)
    }
)

export default request