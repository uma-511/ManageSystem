import Main from './views/Main.vue';

// 不作为Main组件的子页面展示的页面单独写，如下
export const loginRouter = {
    path: '/login',
    name: 'login',
    meta: {
        title: 'Login - 登录'
    },
    component: resolve => { require(['./views/login.vue'], resolve); }
};

export const page404 = {
    path: '/*',
    name: 'error_404',
    meta: {
        title: '404-页面不存在'
    },
    component: resolve => { require(['./views/error_page/404.vue'], resolve); }
};

export const page401 = {
    path: '/401',
    meta: {
        title: '401-权限不足'
    },
    name: 'error_401',
    component: resolve => { require(['./views/error_page/401.vue'], resolve); }
};

export const page500 = {
    path: '/500',
    meta: {
        title: '500-服务端错误'
    },
    name: 'error_500',
    component: resolve => { require(['./views/error_page/500.vue'], resolve); }
};

export const locking = {
    path: '/locking',
    name: 'locking',
    component: resolve => { require(['./views/main_components/locking-page.vue'], resolve); }
};

// 作为Main组件的子页面展示但是不在左侧菜单显示的路由写在otherRouter里
export const otherRouter = {
    path: '/',
    name: 'otherRouter',
    component: Main,
    children: [
        { path: 'home', title: { i18n: 'home' }, name: 'home_index', component: resolve => { require(['./views/home/home.vue'], resolve); } },
        { path: 'ownspace', title: '个人中心', name: 'ownspace_index', component: resolve => { require(['./views/own-space/own-space.vue'], resolve); } },
        { path: 'message', title: '消息中心', name: 'message_index', component: resolve => { require(['./views/message/message.vue'], resolve); } }
    ]
};

// 作为Main组件的子页面展示并且在左侧菜单显示的路由写在appRouter里
export const appRouter = [{
        path: '/user',
        icon: 'person',
        name: 'user',
        title: '用户管理',
        access: 'admin:user:view',
        component: Main,
        children: [
            { path: 'index', title: '用户管理', name: 'user_index', component: resolve => { require(['./views/user/index.vue'], resolve); } }
        ]
    },
    {
        path: '/role',
        icon: 'person',
        title: '角色管理',
        name: 'role',
        access: 'admin:role:view',
        component: Main,
        children: [
            { path: 'index', title: '角色管理', name: 'role_index', component: resolve => { require(['./views/role/index.vue'], resolve); } }
        ]
    },
    {
        path: '/system',
        icon: 'ios-folder',
        name: 'system',
        title: '系统管理',
        component: Main,
        children: [
            { path: 'resources', title: '资源管理', name: 'resources', icon: 'ios-folder', access: 'admin:resource:view', component: resolve => { require(['./views/resource/index.vue'], resolve); } },
            { path: 'dict', title: '字典管理', name: 'dict', icon: 'briefcase', access: 'admin:dict:view', component: resolve => { require(['./views/dict/index.vue'], resolve); } },
            { path: 'log', title: '系统日志', name: 'log', icon: 'ios-list-outline', access: 'admin:log:view', component: resolve => { require(['./views/log/index.vue'], resolve); } }
        ]
    }
];

// 所有上面定义的路由都要写在下面的routers里
export const routers = [
    loginRouter,
    otherRouter,
    locking,
    ...appRouter,
    page500,
    page401,
    page404
];