import axios from 'axios'
import router from "@/router";
import qs from "qs"
import Cookies from "js-cookie";
axios.defaults.withCredentials = true //让ajax请求携带cookie
// 创建对象
const request = axios.create({
    baseURL: 'http://localhost:8088', // 注意！！ 这里是全局统一加上了 baseURL 前缀，
    timeout: 5000
})

//添加一个请求的拦截器、过滤post请求体的参数、进行格式化
request.interceptors.request.use(config => {
    //允许通过访问的路径
    let this_url = config.url;
    console.log("this_url==" + this_url);
    let passpath = (this_url == "/gec/user/loginUser" || this_url == "/gec/user/register" || this_url == "/captcha/getCaptcha");
    //不是允许通过访问的路径、则判断有没有进行登录
    if (!passpath) {
        //对当前的请求做登录拦截的判断
        let user = Cookies.get('user');
        if (!user) {//不存在该信息
            //则跳转到登录页面
            router.push("/login");
            //返回配置
            return config
        }
    }


    //请求方式不为get
    if (config.method != 'get') {
        //通过queryString的工具类来格式化请求参数
        config.data = qs.stringify(config.data);
    }
    config.headers['Content-Type'] = 'application/x-www-form-urlencoded';
    return config
}, error => {
    return Promise.reject(error)
});

// response 拦截器
// 可以在接口响应后统一处理结果
request.interceptors.response.use(
    response => {
        let res = response.data;
        // 如果是返回的文件
        if (response.config.responseType === 'blob') {
            return res
        }
        // 兼容服务端返回的字符串数据
        if (typeof res === 'string') {
            res = res ? JSON.parse(res) : res
        }
        return res;
    },
    error => {
        console.log('err' + error) // for debug
        return Promise.reject(error)
    }
)

export default request