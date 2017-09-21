import axios from 'axios';
import qs from 'qs';
import env from '../config/env';

let util = {
  vue:null
};
util.title = function(title) {
    title = title ? title : '';
    window.document.title = title;
};

const ajaxUrl = env === 'development' ?
    'http://127.0.0.1:8083' :
    env === 'production' ?
    'http://127.0.0.1:8083' :
    'https://debug.url.com';

util.ajax = axios.create({
    baseURL: ajaxUrl,
    timeout: 30000,
    headers:{'X-Requested-With':'XMLHttpRequest'},
    withCredentials:true
});

util.ajax.interceptors.request.use((config)=>{
  if(config.method === 'post' || config.method == 'put'){
    config.data = qs.stringify(config.data);
  }
  return config;
},(error)=>{
  util.vue.$Message.error({content:'<span style="color:red;">网络错误！</span>',duration:5,closable:true});
  return Promise.reject(error);
});
util.ajax.interceptors.response.use((res)=>{
  if(!res.data || res.data==""){
    if(util.vue.logining != undefined){util.vue.logining = false;}
    util.vue.$Message.error({content:'<span style="color:red;">服务器异常！</span>',duration:5,closable:true});
    return Promise.reject(res);
  }
  if (!res.data.success) {
    if(res.data.login != undefined && !res.data.login){
      localStorage.removeItem('currentUser');
      window.location.href = window.location.protocol+'//'+window.location.host;
      return Promise.reject(error);
    }
    if(util.vue.logining != undefined){util.vue.logining = false;}
    util.vue.$Message.error({content:'<span style="color:red;">'+res.data.msg+'</span>',duration:5,closable:true});
    return Promise.reject(res);
  }
  return res.data.data ? res.data.data : res.data;
},(error)=>{
  util.vue.$Message.error({content:'<span style="color:red;">网络错误！</span>',duration:5,closable:true});
  return Promise.reject(error);
});
util.contains=function(array,value){
  if(array == undefined || array.length == 0){
    return false;
  }
  if(array[0] instanceof Object){
    for(let key of Object.keys(array)){
      if(key == value){
        return true;
      }
    }
  }else{
    for(let val of array){
        if(val == value){
          return true;
        }
    }
  }
  return false;
};
util.formatDate = function(date,fmt){
  if(date == undefined || date === '' || date == null){
    return '';
  }
  if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (date.getFullYear() + '').substr(4 - RegExp.$1.length));
    }
    let o = {
        'M+': date.getMonth() + 1,
        'd+': date.getDate(),
        'h+': date.getHours(),
        'm+': date.getMinutes(),
        's+': date.getSeconds()
    };
    for (let k in o) {
        if (new RegExp(`(${k})`).test(fmt)) {
            let str = o[k] + '';
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? str : padLeftZero(str));
        }
    }
    return fmt;
}
function padLeftZero(str) {
    return ('00' + str).substr(str.length);
}
export default util;
