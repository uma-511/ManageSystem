const routers = [{
    path: '/',
    meta: {
        title: '管理后台'
    },
    component: (resolve) => require(['./views/login.vue'], resolve)
},
    {
        path: '/index',
        meta: {
            title: '管理后台'
        },
        component: (resolve) => require(['./views/index.vue'], resolve),
        children: [
            {
                path: '/welcome',
                meta: {
                    title: '管理后台-首页'
                },
                component: (resolve) => require(['./views/welcome.vue'], resolve)
            },
            {
                path: '/user/list',
                meta: {
                    title: '管理后台-用户管理'
                },
                component: (resolve) => require(['./views/user/userList.vue'], resolve)
            },
            {
                path: '/resource/list',
                meta: {
                    title: '管理后台-资源管理'
                },
                component: (resolve) => require(['./views/resource/resourceList.vue'], resolve)
            },
            {
                path: '/role/list',
                meta: {
                    title: '管理后台-角色管理'
                },
                component: (resolve) => require(['./views/role/roleList.vue'], resolve)
            },
            {
                path: '/dict/list',
                meta: {
                    title: '管理后台-字典管理'
                },
                component: (resolve) => require(['./views/dict/dictList.vue'], resolve)
            },
            {
                path: '/log/list',
                meta: {
                    title: '管理后台-系统日志'
                },
                component: (resolve) => require(['./views/log/syslog.vue'], resolve)
            },
            {
                path: '/test/list',
                meta: {
                    title: '管理后台-测试'
                },
                component: (resolve) => require(['./views/test/index.vue'], resolve)
            }
        ]
    }
];
export default routers;
