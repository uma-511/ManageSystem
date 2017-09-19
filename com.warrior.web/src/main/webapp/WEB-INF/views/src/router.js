const routers = [{
    path: '/',
    meta: {
        title: '管理后台'
    },
    component: (resolve) => require(['./views/login.vue'], resolve)
},
{
    path:'/index',
    meta: {
        title: '管理后台'
    },
    component:(resolve) => require(['./views/index.vue'], resolve),
    children:[
      {
          path:'/welcome',
          meta: {
              title: '管理后台-首页'
          },
          component:(resolve) => require(['./views/welcome.vue'],resolve)
      },
      {
          path:'/user/list',
          meta: {
              title: '管理后台-用户列表'
          },
          component:(resolve) => require(['./views/user/userList.vue'],resolve)
      },
      {
          path:'/resource/list',
          meta: {
              title: '管理后台-资源列表'
          },
          component:(resolve) => require(['./views/resource/resourceList.vue'],resolve)
      }
    ]
}
];
export default routers;
