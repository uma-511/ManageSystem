import axios from 'axios';
import qs from 'qs';
import crypto from 'crypto'
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
  let isLogin = false;
  if(config.url === ajaxUrl+'/doLogin'){
    isLogin = true;
  }
  let params = {};
  if(config.method === 'post' || config.method == 'put'){
    params = !config.data ? {} : config.data;
    config.data = qs.stringify(config.data);
  }else{
    params = !config.params ? {} : config.params;
  }
  if (!isLogin) {
    if(!config.params){
      config.params = {};
    }
    let time = new Date().getTime();
    config.params['token'] = localStorage.getItem('currentUser') ? localStorage.getItem('currentUser') : "";
    config.params['time']=time;
    params['token'] = config.params['token'];
    params['time'] = config.params['time'];
    config.params['sign'] = util.signStr(params);
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
      return Promise.reject(res);
    }
    if(util.vue.logining != undefined){util.vue.logining = false;}
    let data = res.data instanceof Object ? res.data : JSON.parse(res.data);
    util.vue.$Message.error({content:'<span style="color:red;">'+(data.msg == undefined ? '服务端错误！' : data.msg)+'</span>',duration:5,closable:true});
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

util.signStr=function(array){
  let keys = Object.keys(array);
  keys.sort((item1,item2)=>{
    if(item1 > item2){
      return 1;
    }else if(item1 < item2){
      return -1;
    }else{
      return 0;
    }
  });
  let sign = "";
  for(let item of keys){
    sign +=item+'='+encodeURIComponent(array[item])+"&";
  }
  sign = sign.substr(0,sign.length-1);
  let md5 = crypto.createHmac('md5','0202040103');
  md5.update(sign);
  return md5.digest('hex');
}
export default util;
