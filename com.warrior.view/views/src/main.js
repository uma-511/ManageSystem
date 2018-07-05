import Vue from 'vue'
import iView from 'iview'
import VueRouter from 'vue-router'
import {
  routers,
  otherRouter,
  appRouter
} from './router'
import Vuex from 'vuex'
import Util from './libs/util'
import App from './app.vue'
import Cookies from 'js-cookie'
import 'iview/dist/styles/iview.css'
import VueImg from 'v-img'
import VueVideoPlayer from 'vue-video-player'

import VueI18n from 'vue-i18n'
import Locales from './locale'
import zhLocale from 'iview/src/locale/lang/zh-CN'
import enLocale from 'iview/src/locale/lang/en-US'
import zhTLocale from 'iview/src/locale/lang/zh-TW'
import ZkTable from 'vue-table-with-tree-grid'

Vue.use(ZkTable)
Vue.use(VueRouter)
Vue.use(Vuex)
Vue.use(VueI18n)
Vue.use(iView)
Vue.use(VueImg)
Vue.use(VueVideoPlayer)

// 自动设置语言
const navLang = navigator.language
const localLang = navLang === 'zh-CN' || navLang === 'en-US' ? navLang : false
const lang = window.localStorage.lang || localLang || 'zh-CN'

Vue.config.lang = lang

// 多语言配置
const locales = Locales
const mergeZH = Object.assign(zhLocale, locales['zh-CN'])
const mergeEN = Object.assign(enLocale, locales['en-US'])
const mergeTW = Object.assign(zhTLocale, locales['zh-TW'])
Vue.locale('zh-CN', mergeZH)
Vue.locale('en-US', mergeEN)
Vue.locale('zh-TW', mergeTW)

// 路由配置
const RouterConfig = {
  // mode: 'history',
  routes: routers
}

const router = new VueRouter(RouterConfig)

router.beforeEach((to, from, next) => {
  iView.LoadingBar.start()
  Util.title(to.meta.title)
  if (Cookies.get('locking') === '1' && to.name !== 'locking') {
    // 判断当前是否是锁定状态
    next(false)
    router.replace({
      name: 'locking'
    })
  } else if (Cookies.get('locking') === '0' && to.name === 'locking') {
    next(false)
  } else {
    if (!Cookies.get('token') && to.name !== 'login') {
      // 判断是否已经登录且前往的页面不是登录页
      Cookies.set('locking', 0)
      next({
        name: 'login'
      })
    } else if (Cookies.get('token') && to.name === 'login') {
      // 判断是否已经登录且前往的是登录页
      Util.title()
      next({
        name: 'home_index'
      })
    } else {
      Util.toDefaultPage([otherRouter, ...appRouter], to.name, router, next)
    }
  }
  iView.LoadingBar.finish()
})

router.afterEach(() => {
  iView.LoadingBar.finish()
  window.scrollTo(0, 0)
})
// 状态管理
const store = new Vuex.Store({
  state: {
    user: [],
    permission: '', //当前用户权限
    routers: [otherRouter, ...appRouter],
    menuList: [],
    tagsList: [...otherRouter.children],
    pageOpenedList: [{
      title: '首页',
      path: '',
      name: 'home_index'
    }],
    currentPageName: '',
    currentPath: [{
      title: '首页',
      path: '',
      name: 'home_index'
    }], // 面包屑数组
    openedSubmenuArr: [], // 要展开的菜单数组
    menuTheme: '', // 主题
    theme: '',
    cachePage: [],
    lang: '',
    isFullScreen: false,
    dontCache: ['user_index', 'role'] // 在这里定义你不想要缓存的页面的name属性值(参见路由配置router.js)
  },
  getters: {
    getPerStr: state => {
      return state.permission
    },
    getUser: state => {
      return state.user
    }
  },
  mutations: {
    initUserInfo(state, user) {
      state.user = user
      state.permission = user.perStr
    },
    setTagsList(state, list) {
      state.tagsList.push(...list)
    },
    closePage(state, name) {
      state.cachePage.forEach((item, index) => {
        if (item === name) {
          state.cachePage.splice(index, 1)
        }
      })
    },
    increateTag(state, tagObj) {
      if (!Util.oneOf(tagObj.name, state.dontCache)) {
        state.cachePage.push(tagObj.name)
        localStorage.cachePage = JSON.stringify(state.cachePage)
      }
      state.pageOpenedList.push(tagObj)
    },
    initCachepage(state) {
      if (localStorage.cachePage) {
        state.cachePage = JSON.parse(localStorage.cachePage)
      }
    },
    removeTag(state, name) {
      state.pageOpenedList.map((item, index) => {
        if (item.name === name) {
          state.pageOpenedList.splice(index, 1)
        }
      })
    },
    pageOpenedList(state, get) {
      let openedPage = state.pageOpenedList[get.index]
      if (get.argu) {
        openedPage.argu = get.argu
      }
      if (get.query) {
        openedPage.query = get.query
      }
      state.pageOpenedList.splice(get.index, 1, openedPage)
      localStorage.pageOpenedList = JSON.stringify(state.pageOpenedList)
    },
    clearAllTags(state) {
      state.pageOpenedList.splice(1)
      router.push({
        name: 'home_index'
      })
      state.cachePage.length = 0
      localStorage.pageOpenedList = JSON.stringify(state.pageOpenedList)
    },
    clearOtherTags(state, vm) {
      let currentName = vm.$route.name
      let currentIndex = 0
      state.pageOpenedList.forEach((item, index) => {
        if (item.name === currentName) {
          currentIndex = index
        }
      })
      if (currentIndex === 0) {
        state.pageOpenedList.splice(1)
      } else {
        state.pageOpenedList.splice(currentIndex + 1)
        state.pageOpenedList.splice(1, currentIndex - 1)
      }
      let newCachepage = state.cachePage.filter(item => {
        return item === currentName
      })
      state.cachePage = newCachepage
      localStorage.pageOpenedList = JSON.stringify(state.pageOpenedList)
    },
    setOpenedList(state) {
      state.pageOpenedList = localStorage.pageOpenedList ?
        JSON.parse(localStorage.pageOpenedList) :
        [otherRouter.children[0]]
    },
    setCurrentPath(state, pathArr) {
      state.currentPath = pathArr
    },
    setCurrentPageName(state, name) {
      state.currentPageName = name
    },
    addOpenSubmenu(state, name) {
      let hasThisName = false
      let isEmpty = false
      if (name.length === 0) {
        isEmpty = true
      }
      if (state.openedSubmenuArr.indexOf(name) > -1) {
        hasThisName = true
      }
      if (!hasThisName && !isEmpty) {
        state.openedSubmenuArr.push(name)
      }
    },
    clearOpenedSubmenu(state) {
      state.openedSubmenuArr.length = 0
    },
    changeMenuTheme(state, theme) {
      state.menuTheme = theme
    },
    changeMainTheme(state, mainTheme) {
      state.theme = mainTheme
    },
    lock(state) {
      Cookies.set('locking', '1')
    },
    unlock(state) {
      Cookies.set('locking', '0')
    },
    setMenuList(state, menulist) {
      state.menuList = menulist
    },
    updateMenulist(state) {
      let menuList = []
      appRouter.forEach((item, index) => {
        if (item.access !== undefined) {
          if (Util.showThisRoute(item.access, state.permission)) {
            if (item.children.length === 1) {
              menuList.push(item)
            } else {
              let len = menuList.push(item)
              let childrenArr = []
              childrenArr = item.children.filter(child => {
                if (child.access !== undefined) {
                  if (Util.showThisRoute(child.access, state.permission)) {
                    return child
                  }
                } else {
                  return child
                }
              })
              menuList[len - 1].children = childrenArr
            }
          }
        } else {
          if (item.children.length === 1) {
            menuList.push(item)
          } else {
            let len = menuList.push(item)
            let childrenArr = []
            childrenArr = item.children.filter(child => {
              if (child.access !== undefined) {
                if (Util.showThisRoute(child.access, state.permission)) {
                  return child
                }
              } else {
                return child
              }
            })
            let handledItem = JSON.parse(JSON.stringify(menuList[len - 1]))
            handledItem.children = childrenArr
            menuList.splice(len - 1, 1, handledItem)
          }
        }
      })
      state.menuList = menuList
    },
    switchLang(state, lang) {
      state.lang = lang
      Vue.config.lang = lang
    },
    handleFullScreen(state) {
      let main = document.body
      if (state.isFullScreen) {
        if (document.exitFullscreen) {
          document.exitFullscreen()
        } else if (document.mozCancelFullScreen) {
          document.mozCancelFullScreen()
        } else if (document.webkitCancelFullScreen) {
          document.webkitCancelFullScreen()
        } else if (document.msExitFullscreen) {
          document.msExitFullscreen()
        }
      } else {
        if (main.requestFullscreen) {
          main.requestFullscreen()
        } else if (main.mozRequestFullScreen) {
          main.mozRequestFullScreen()
        } else if (main.webkitRequestFullScreen) {
          main.webkitRequestFullScreen()
        } else if (main.msRequestFullscreen) {
          main.msRequestFullscreen()
        }
      }
    },
    changeFullScreenState(state) {
      state.isFullScreen = !state.isFullScreen
    }
  },
  actions: {}
})

new Vue({
  el: '#app',
  router: router,
  store: store,
  render: h => h(App),
  data: {
    currentPageName: ''
  },
  mounted() {
    this.currentPageName = this.$route.name
    this.$store.commit('initCachepage')
    // 全屏相关
    document.addEventListener('fullscreenchange', () => {
      this.$store.commit('changeFullScreenState')
    })
    document.addEventListener('mozfullscreenchange', () => {
      this.$store.commit('changeFullScreenState')
    })
    document.addEventListener('webkitfullscreenchange', () => {
      this.$store.commit('changeFullScreenState')
    })
    document.addEventListener('msfullscreenchange', () => {
      this.$store.commit('changeFullScreenState')
    })
  },
  created() {
    let tagsList = []
    appRouter.map(item => {
      if (item.children.length <= 1) {
        tagsList.push(item.children[0])
      } else {
        tagsList.push(...item.children)
      }
    })
    this.$store.commit('setTagsList', tagsList)
  }
})