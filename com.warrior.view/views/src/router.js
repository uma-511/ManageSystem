import Main from './views/Main.vue'

// 不作为Main组件的子页面展示的页面单独写，如下
export const loginRouter = {
  path: '/login',
  name: 'login',
  meta: {
    title: 'Login - 登录'
  },
  component: resolve => {
    require(['./views/login.vue'], resolve)
  }
}

export const page404 = {
  path: '/*',
  name: 'error_404',
  meta: {
    title: '404-页面不存在'
  },
  component: resolve => {
    require(['./views/error_page/404.vue'], resolve)
  }
}

export const page401 = {
  path: '/401',
  meta: {
    title: '401-权限不足'
  },
  name: 'error_401',
  component: resolve => {
    require(['./views/error_page/401.vue'], resolve)
  }
}

export const page500 = {
  path: '/500',
  meta: {
    title: '500-服务端错误'
  },
  name: 'error_500',
  component: resolve => {
    require(['./views/error_page/500.vue'], resolve)
  }
}

export const locking = {
  path: '/locking',
  name: 'locking',
  component: resolve => {
    require(['./views/main_components/locking-page.vue'], resolve)
  }
}

// 作为Main组件的子页面展示但是不在左侧菜单显示的路由写在otherRouter里
export const otherRouter = {
  path: '/',
  name: 'otherRouter',
  component: Main,
  children: [{
      path: 'home',
      title: {
        i18n: 'home'
      },
      name: 'home_index',
      component: resolve => {
        require(['./views/home/home.vue'], resolve)
      }
    },
    {
      path: 'ownspace',
      title: '个人中心',
      name: 'ownspace_index',
      component: resolve => {
        require(['./views/own-space/own-space.vue'], resolve)
      }
    },
    {
      path: 'message',
      title: '消息中心',
      name: 'message_index',
      component: resolve => {
        require(['./views/message/message.vue'], resolve)
      }
    }
  ]
}

// 作为Main组件的子页面展示并且在左侧菜单显示的路由写在appRouter里
export const appRouter = [{
    path: '/user',
    icon: 'person',
    name: 'user',
    title: '用户管理',
    access: 'admin:user:view',
    component: Main,
    children: [{
      path: 'index',
      title: '用户管理',
      name: 'user_index',
      component: resolve => {
        require(['./views/user/index.vue'], resolve)
      }
    }]
  },
  {
    path: '/role',
    icon: 'person',
    title: '角色管理',
    name: 'role',
    access: 'admin:role:view',
    component: Main,
    children: [{
      path: 'index',
      title: '角色管理',
      name: 'role_index',
      component: resolve => {
        require(['./views/role/index.vue'], resolve)
      }
    }]
  },
  {
    path: '/system',
    icon: 'ios-folder',
    name: 'system',
    title: '系统管理',
    component: Main,
    children: [{
        path: 'resources',
        title: '资源管理',
        name: 'resources',
        icon: 'ios-folder',
        access: 'admin:resource:view',
        component: resolve => {
          require(['./views/resource/index.vue'], resolve)
        }
      },
      {
        path: 'dict',
        title: '字典管理',
        name: 'dict',
        icon: 'briefcase',
        access: 'admin:dict:view',
        component: resolve => {
          require(['./views/dict/index.vue'], resolve)
        }
      },
      {
        path: 'log',
        title: '系统日志',
        name: 'log',
        icon: 'ios-list-outline',
        access: 'admin:log:view',
        component: resolve => {
          require(['./views/log/index.vue'], resolve)
        }
      },
      {
        path: 'task',
        title: '定时作业',
        name: 'task',
        icon: 'clock',
        access: 'admin:task:view',
        component: resolve => {
          require(['./views/task/index.vue'], resolve)
        }
      }
    ]
  },
  {
    path: '/article',
    icon: 'ios-folder',
    name: 'article',
    title: '文章管理',
    component: Main,
    children: [{
        path: 'article',
        title: '文章管理',
        name: 'artIndex',
        icon: 'clock',
        access: 'admin:article:view',
        component: resolve => {
          require(['./views/article/index.vue'], resolve)
        }
      },
      {
        path: 'classify',
        title: '文章分类',
        name: 'classify',
        icon: 'clock',
        access: 'admin:classify:view',
        component: resolve => {
          require(['./views/classify/index.vue'], resolve)
        }
      }
    ]
  },
  {
    path: '/appUser',
    icon: 'ios-folder',
    name: 'appUser',
    title: 'App用户管理',
    component: Main,
    children: [{
      path: 'index',
      title: 'App用户管理',
      name: 'appUserIndex',
      icon: 'clock',
      access: 'admin:appUser:view',
      component: resolve => {
        require(['./views/appUser/index.vue'], resolve)
      }
    }]
  },
  {
    path: '/report',
    icon: 'ios-folder',
    name: 'reportHome',
    title: '投诉管理',
    component: Main,
    children: [{
      path: 'report',
      title: '投诉管理',
      name: 'report',
      icon: 'clock',
      access: 'admin:report:view',
      component: resolve => {
        require(['./views/report/index.vue'], resolve)
      }
    }]
  },
  {
    path: '/recharge',
    icon: 'ios-folder',
    name: 'rechargeHome',
    title: '财务管理',
    component: Main,
    children: [{
      path: 'recharge',
      title: '充值管理',
      name: 'recharge',
      icon: 'clock',
      access: 'admin:recharge:view',
      component: resolve => {
        require(['./views/recharge/index.vue'], resolve)
      }
    }]
  }
]

// 所有上面定义的路由都要写在下面的routers里
export const routers = [
  loginRouter,
  otherRouter,
  locking,
  ...appRouter,
  page500,
  page401,
  page404
]