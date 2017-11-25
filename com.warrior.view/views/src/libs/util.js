import axios from 'axios';
import qs from 'qs';
import crypto from 'crypto'
import env from '../config/env';
import swal from "sweetalert";
import Cookies from 'js-cookie';

let util = {
    ajaxUrl: ''
};
util.title = function(title) {
    title = title || '管理后台';
    window.document.title = title;
};

const ajaxUrl = env === 'development' ?
    'http://127.0.0.1:8083' :
    env === 'production' ?
    'http://127.0.0.1:8083' :
    'https://debug.url.com';
util.ajaxUrl = ajaxUrl;
util.ajax = axios.create({
    baseURL: ajaxUrl + '/admin',
    timeout: 30000,
    headers: { 'X-Requested-With': 'XMLHttpRequest' },
    withCredentials: true
});
util.ajax.interceptors.request.use((config) => {
    let params = {};
    if (config.method === 'post' || config.method == 'put') {
        params = !config.data ? {} : config.data;
        config.data = qs.stringify(config.data);
    } else {
        params = !config.params ? {} : config.params;
    }
    if (config.url != ajaxUrl + '/doLogin') {
        if (!config.params) {
            config.params = {};
        }
        let time = new Date().getTime();
        config.params['token'] = Cookies.get('token');
        config.params['time'] = time;
        params['token'] = config.params['token'];
        params['time'] = config.params['time'];
        config.params['sign'] = util.signStr(params);
    }
    return config;
}, (error) => {
    util.error("网络错误！");
    return Promise.reject(error);
});

util.ajax.interceptors.response.use((res) => {
    if (!res.data || res.data == "") {
        util.error("服务器异常！");
        res.data = { 'code': -1 };
    } else {
        if (res.data.code != 0) {
            if (res.data.code == 6) {
                Cookies.remove('token');
                util.error('登录已失效，请重新登录！', function() { location.reload(); });
            } else {
                let data = res.data instanceof Object ? res.data : JSON.parse(res.data);
                util.error((data.msg == undefined ? '服务端错误！' : data.msg));
            }
        }
    }
    return res.data;
}, (error) => {
    util.error("网络错误！");
    return Promise.reject(error);
});

util.inOf = function(arr, targetArr) {
    let res = true;
    arr.map(item => {
        if (targetArr.indexOf(item) < 0) {
            res = false;
        }
    });
    return res;
};

util.oneOf = function(ele, targetArr) {
    if (targetArr.indexOf(ele) >= 0) {
        return true;
    } else {
        return false;
    }
};

util.showThisRoute = function(itAccess, currentAccess) {
    if (typeof itAccess === 'object' && itAccess.isArray()) {
        return util.oneOf(currentAccess, itAccess);
    } else {
        return currentAccess.indexOf(itAccess) >= 0;
    }
};

util.getRouterObjByName = function(routers, name) {
    let routerObj = {};
    routers.forEach(item => {
        if (item.name === 'otherRouter') {
            item.children.forEach((child, i) => {
                if (child.name === name) {
                    routerObj = item.children[i];
                }
            });
        } else {
            if (item.children.length === 1) {
                if (item.children[0].name === name) {
                    routerObj = item.children[0];
                }
            } else {
                item.children.forEach((child, i) => {
                    if (child.name === name) {
                        routerObj = item.children[i];
                    }
                });
            }
        }
    });
    return routerObj;
};

util.handleTitle = function(vm, item) {
    if (typeof item.title === 'object') {
        return vm.$t(item.title.i18n);
    } else {
        return item.title;
    }
};

util.setCurrentPath = function(vm, name) {
    let title = '';
    let isOtherRouter = false;
    vm.$store.state.routers.forEach(item => {
        if (item.children.length === 1) {
            if (item.children[0].name === name) {
                title = util.handleTitle(vm, item);
                if (item.name === 'otherRouter') {
                    isOtherRouter = true;
                }
            }
        } else {
            item.children.forEach(child => {
                if (child.name === name) {
                    title = util.handleTitle(vm, child);
                    if (item.name === 'otherRouter') {
                        isOtherRouter = true;
                    }
                }
            });
        }
    });
    let currentPathArr = [];
    if (name === 'home_index') {
        currentPathArr = [{
            title: util.handleTitle(vm, util.getRouterObjByName(vm.$store.state.routers, 'home_index')),
            path: '',
            name: 'home_index'
        }];
    } else if ((name.indexOf('_index') >= 0 || isOtherRouter) && name !== 'home_index') {
        currentPathArr = [{
                title: util.handleTitle(vm, util.getRouterObjByName(vm.$store.state.routers, 'home_index')),
                path: '/home',
                name: 'home_index'
            },
            {
                title: title,
                path: '',
                name: name
            }
        ];
    } else {
        let currentPathObj = vm.$store.state.routers.filter(item => {
            if (item.children.length <= 1) {
                return item.children[0].name === name;
            } else {
                let i = 0;
                let childArr = item.children;
                let len = childArr.length;
                while (i < len) {
                    if (childArr[i].name === name) {
                        return true;
                    }
                    i++;
                }
                return false;
            }
        })[0];
        if (currentPathObj.children.length <= 1 && currentPathObj.name === 'home') {
            currentPathArr = [{
                title: '首页',
                path: '',
                name: 'home_index'
            }];
        } else if (currentPathObj.children.length <= 1 && currentPathObj.name !== 'home') {
            currentPathArr = [{
                    title: '首页',
                    path: '/home',
                    name: 'home_index'
                },
                {
                    title: currentPathObj.title,
                    path: '',
                    name: name
                }
            ];
        } else {
            let childObj = currentPathObj.children.filter((child) => {
                return child.name === name;
            })[0];
            currentPathArr = [{
                    title: '首页',
                    path: '/home',
                    name: 'home_index'
                },
                {
                    title: currentPathObj.title,
                    path: '',
                    name: currentPathObj.name
                },
                {
                    title: childObj.title,
                    path: currentPathObj.path + '/' + childObj.path,
                    name: name
                }
            ];
        }
    }
    vm.$store.commit('setCurrentPath', currentPathArr);

    return currentPathArr;
};

util.openNewPage = function(vm, name, argu, query) {
    let pageOpenedList = vm.$store.state.pageOpenedList;
    let openedPageLen = pageOpenedList.length;
    let i = 0;
    let tagHasOpened = false;
    while (i < openedPageLen) {
        if (name === pageOpenedList[i].name) { // 页面已经打开
            vm.$store.commit('pageOpenedList', {
                index: i,
                argu: argu,
                query: query
            });
            tagHasOpened = true;
            break;
        }
        i++;
    }
    if (!tagHasOpened) {
        let tag = vm.$store.state.tagsList.filter((item) => {
            if (item.children) {
                return name === item.children[0].name;
            } else {
                return name === item.name;
            }
        });
        tag = tag[0];
        tag = tag.children ? tag.children[0] : tag;
        if (argu) {
            tag.argu = argu;
        }
        if (query) {
            tag.query = query;
        }
        vm.$store.commit('increateTag', tag);
    }
    vm.$store.commit('setCurrentPageName', name);
};

util.toDefaultPage = function(routers, name, route, next) {
    let len = routers.length;
    let i = 0;
    let notHandle = true;
    while (i < len) {
        if (routers[i].name === name && routers[i].redirect === undefined) {
            route.replace({
                name: routers[i].children[0].name
            });
            notHandle = false;
            next();
            break;
        }
        i++;
    }
    if (notHandle) {
        next();
    }
};

util.contains = function(array, value) {
    if (array == undefined || array.length == 0) {
        return false;
    }
    if (array[0] instanceof Object) {
        for (let key of Object.keys(array)) {
            if (key == value) {
                return true;
            }
        }
    } else {
        for (let val of array) {
            if (val == value) {
                return true;
            }
        }
    }
    return false;
};

util.formatDate = function(date, fmt) {
    if (date == undefined || date === '' || date == null) {
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
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? str : ('00' + str).substr(str.length));
        }
    }
    return fmt;
}

util.signStr = function(array) {
    let keys = Object.keys(array);
    keys.sort((item1, item2) => {
        if (item1 > item2) {
            return 1;
        } else if (item1 < item2) {
            return -1;
        } else {
            return 0;
        }
    });
    let sign = "";
    for (let item of keys) {
        sign += item + '=' + encodeURIComponent(array[item]) + "&";
    }
    sign = sign.substr(0, sign.length - 1);
    let md5 = crypto.createHmac('md5', '0202040103');
    md5.update(sign);
    return md5.digest('hex');
}
util.success = function(arg, callback) {
    alertMsg(arg, "success", callback);
}
util.error = function(arg, callback) {
    alertMsg(arg, "error", callback);
}
util.warning = function(arg, callback) {
    alertMsg(arg, 'warning', callback);
}
util.info = function(arg, callback) {
    alertMsg(arg, 'info', callback);
}
util.confirm = function(msg, ok, cancel) {
    swal({
        icon: 'warning',
        text: msg,
        buttons: {
            cancel: '取消',
            ok: { text: '确认', value: 'ok' }
        },
        closeOnClickOutside: false
    }).then((val) => {
        switch (val) {
            case 'ok':
                if (ok) { ok(); }
                break;
            default:
                if (cancel) { cancel(); }
        }
    });
}

function alertMsg(arg, type, callback) {
    if (arg instanceof Object) {
        swal(arg).then((val) => {
            if (callback) { callback(); }
        });
    } else {
        swal({
            text: arg,
            icon: type,
            buttons: false,
            closeOnClickOutside: false,
            timer: 2000
        }).then((val) => {
            if (callback) { callback(); }
        });
    }
}
export default util;