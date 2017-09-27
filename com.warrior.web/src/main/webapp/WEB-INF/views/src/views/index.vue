<style scoped>
    .layout{
        border: 1px solid #d7dde4;
        background: #f5f7f9;
        position: relative;
        border-radius: 4px;
        overflow: hidden;
    }
    .layout-breadcrumb{
        padding: 10px 15px 0;
    }
    .layout-copy{
        text-align: center;
        padding: 10px 0 20px;
        color: #9ea7b4;
    }
    .layout-menu-left{
        background: #464c5b;
    }
    .layout-header{
        height: 60px;
        background: #fff;
        box-shadow: 0 1px 1px rgba(0,0,0,.1);
    }
    .layout-logo-left{
        width: 90%;
        height: 30px;
        background: #5b6270;
        border-radius: 3px;
        margin: 15px auto;
        text-align: center;
        color: #FFFFFF;
        font-weight: bold;
        padding:4px 0px 5px 0px;
    }
    .layout-ceiling-main a{
        color: #9ba7b5;
    }
    .layout-hide-text .layout-text{
        display: none;
    }
    .ivu-col{
        transition: width .2s ease-in-out;
    }
</style>
<template>
    <div class="layout" :class="{'layout-hide-text': spanLeft < 4}" :style="{'min-height':minHeight}">
        <Row type="flex">
            <Col :span="spanLeft" class="layout-menu-left" :style="{'min-height':minHeight}">
                <Menu active-name="1" theme="dark" width="auto" v-if="!itemShow">
                  <MenuItem name="1" style="text-align:center;" :name="menu.resId" v-for="menu in menuList" :key="menu.resId">
                      <Icon :type="menu.icon" size="36"></Icon>
                  </MenuItem>
                </Menu>

                <Menu active-name="1" theme="dark" width="auto" v-if="itemShow">
                    <div class="layout-logo-left">后台管理系统</div>
                    <Submenu :name="item.resId" v-if="item.child !=null && item.child.length>0" v-for="item in menuList" :key="item.resId">
                        <template slot="title">
                            <Icon :type="item.icon" size="14"></Icon>
                            <span class="layout-text" >{{item.resName}}</span>
                        </template>
                        <MenuItem :name="sub.resId" v-for="sub in item.child" :key="sub.resId" v-on:click.native="toPage(sub.url,sub.resMap)">
                          <Icon :type="sub.icon" size="14"></Icon>
                          <span class="layout-text" >{{sub.resName}}</span>
                        </MenuItem>
                    </Submenu>
                    <MenuItem :name="item.resId" v-else v-on:click.native="toPage(item.url,item.resMap)">
                        <Icon :type="item.icon" size="14"></Icon>
                        <span class="layout-text">{{item.resName}}</span>
                    </MenuItem>
                </Menu>
            </Col>
            <Col :span="spanRight" :style="{'min-height':minHeight}">
                <div class="layout-header">
                  <Row>
                      <Col span="12">
                        <Button type="text" @click="toggleClick">
                            <Icon type="navicon" size="32"></Icon>
                        </Button>
                      </Col>
                      <Col span="12" style="text-align: right;">
                        <Button @click="logout" type="text">
                          <Icon type="log-out" size="32"></Icon>
                        </Button>
                      </Col>
                  </Row>
                </div>
                <div class="layout-breadcrumb">
                    <Breadcrumb>
                        <BreadcrumbItem href="#" v-on:click.native="toPage('/welcome')" >首页</BreadcrumbItem>
                        <BreadcrumbItem href='#' v-for="item in crumb" :key="item.name" v-on:click.native="toPage(item.url)" v-if="item != undefined">{{item.name}}</BreadcrumbItem>
                    </Breadcrumb>
                </div>
                <router-view></router-view>
                <div class="layout-copy">
                    2010-2017 &copy; 无名之士
                </div>
            </Col>
        </Row>
    </div>
</template>
<script>
    import util from '../libs/util';

    export default {
        data () {
            return {
                minHeight:document.documentElement.clientHeight+'px',
                spanLeft: 4,
                spanRight: 20,
                itemShow:true,
                menuList:[],
                loading:false,
                crumb:[],
                crumbList:new Array()
            }
        },
        created() {
          if(!localStorage.getItem('currentUser')){
            this.$router.push('/');
          }
          util.vue = this;
          window.addEventListener('resize',this.handleResize);
          util.ajax.get('/permission/userPermission')
          .then(rep=>{
            this.menuList = rep.list;
            this.$store.dispatch({type:'setPermission',perStr:rep.permStr});
          });
        },
        beforeMount() {
          this.$router.push('/welcome');
        },
        beforeDestroy() {
            window.removeEventListener('resize',this.handleResize);
        },
        methods: {
            handleResize(event){
                this.minHeight = document.documentElement.clientHeight+'px';
            },
            logout(){
              this.loading = true;
              this.$Modal.confirm({
                title:'退出系统',
                content:'确认退出当前系统？',
                onOk:()=>{
                  util.ajax.get('/doLogOut')
                  .then(rep=>{
                    localStorage.removeItem('currentUser');
                    this.$router.push('/');
                  });
                },
                onCancel:()=>{
                  this.loading = false;
                }
              });
            },
            toggleClick () {
                if (this.spanLeft === 4) {
                    this.spanLeft = 2;
                    this.spanRight = 22;
                    this.itemShow = false;
                } else {
                    this.spanLeft = 4;
                    this.spanRight = 20;
                    this.itemShow = true;
                }
            },
            toPage(url,map){
              if(url == undefined || url == null || url === ''){return;}
              if(map == undefined){
                map = this.crumbList[url];
              }else{
                if(!util.contains(this.crumbList,url)){
                  this.crumbList[url]=map;
                }
              }
              this.crumb = map;
              this.$router.push(url);
            }
        }
    }
</script>
