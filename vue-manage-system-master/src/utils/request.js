import axios from 'axios'
// import { getToken, setToken, removeToken } from '@/utils/LocalStorage.js'
// 创建axios实例
const service = axios.create({
    /* todo: 这里记得改 */
    baseURL: 'http://localhost:8888', // api的base_url
    timeout: 5000 // 请求超时时间
})

// request拦截器
// service.interceptors.request.use(config => {
//     // console.log("请求拦截");
//     // 统一加上令牌
//     if (getToken() !== "") {
//         config.headers.token = getToken();
//     }
//     return config
// }, error => {
//     return Promise.reject(error);
// })

// // respone拦截器
// service.interceptors.response.use(
//     response => {
//         // console.log(response);
//         // console.log("响应拦截");
//         // 没有token跳转到登录
//
//         // 跨域问题
//         if (response.data.code == 4) {
//             window.location.href = "/";
//             return;
//         }
//
//         return response;
//     }, error => {
//         console.log(error);
//         return Promise.reject(error.response.data)
//     })

export default service