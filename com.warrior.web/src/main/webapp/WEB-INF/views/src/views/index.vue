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
                  <MenuItem name="1" style="padding-left:14px;" :name="menu.resId" v-for="menu in menuList" :key="menu.resId" v-on:click.native="toggleClick()">
                      <Icon :type="menu.icon" size="32"></Icon>
                  </MenuItem>
                  <MenuItem name="1000" style="padding-left:14px;" v-on:click.native="showModel = true">
                      <Icon type="compose" size="32"></Icon>
                  </MenuItem>
                </Menu>

                <Menu active-name="1" theme="dark" width="auto" v-if="itemShow">
                    <div class="layout-logo-left">后台管理系统</div>
                    <Submenu :name="item.resId" v-if="item.child !=null && item.child.length>0" v-for="item in menuList" :key="item.resId">
                        <template slot="title">
                            <Icon :type="item.icon" size="20"></Icon>
                            <span class="layout-text" >{{item.resName}}</span>
                        </template>
                        <MenuItem :name="sub.resId" v-for="sub in item.child" :key="sub.resId" v-on:click.native="toPage(sub.url,sub.resMap)">
                          <Icon :type="sub.icon" size="20"></Icon>
                          <span class="layout-text" >{{sub.resName}}</span>
                        </MenuItem>
                    </Submenu>
                    <MenuItem :name="item.resId" v-else v-on:click.native="toPage(item.url,item.resMap)">
                        <Icon :type="item.icon" size="20"></Icon>
                        <span class="layout-text">{{item.resName}}</span>
                    </MenuItem>

                    <MenuItem :name="100000" v-on:click.native="showModel = true">
                        <Icon type="compose" size="20"></Icon>
                        <span class="layout-text">修改密码</span>
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
                        <a href="swagger-ui.html" target="_blank">API文档</a>&nbsp;&nbsp;
                        <a title="把这个链接拖到你的Chrome收藏夹工具栏中" href='javascript:(function(){function c(){var e=document.createElement("link");e.setAttribute("type","text/css");e.setAttribute("rel","stylesheet");e.setAttribute("href",f);e.setAttribute("class",l);document.body.appendChild(e)}function h(){var e=document.getElementsByClassName(l);for(var t=0;t<e.length;t++){document.body.removeChild(e[t])}}function p(){var e=document.createElement("div");e.setAttribute("class",a);document.body.appendChild(e);setTimeout(function(){document.body.removeChild(e)},100)}function d(e){return{height:e.offsetHeight,width:e.offsetWidth}}function v(i){var s=d(i);return s.height>e&amp;&amp;s.height<n&amp;&amp;s.width>t&amp;&amp;s.width<r}function m(e){var t=e;var n=0;while(!!t){n+=t.offsetTop;t=t.offsetParent}return n}function g(){var e=document.documentElement;if(!!window.innerWidth){return window.innerHeight}else if(e&amp;&amp;!isNaN(e.clientHeight)){return e.clientHeight}return 0}function y(){if(window.pageYOffset){return window.pageYOffset}return Math.max(document.documentElement.scrollTop,document.body.scrollTop)}function E(e){var t=m(e);return t>=w&amp;&amp;t<=b+w}function S(){var e=document.createElement("audio");e.setAttribute("class",l);e.src=i;e.loop=false;e.addEventListener("canplay",function(){setTimeout(function(){x(k)},500);setTimeout(function(){N();p();for(var e=0;e<O.length;e++){T(O[e])}},15500)},true);e.addEventListener("ended",function(){N();h()},true);e.innerHTML=" <p>If you are reading this, it is because your browser does not support the audio element. We recommend that you get a new browser.</p> <p>";document.body.appendChild(e);e.play()}function x(e){e.className+=" "+s+" "+o}function T(e){e.className+=" "+s+" "+u[Math.floor(Math.random()*u.length)]}function N(){var e=document.getElementsByClassName(s);var t=new RegExp("\\b"+s+"\\b");for(var n=0;n<e.length;){e[n].className=e[n].className.replace(t,"")}}var e=30;var t=30;var n=350;var r=350;var i="//s3.amazonaws.com/moovweb-marketing/playground/harlem-shake.mp3";var s="mw-harlem_shake_me";var o="im_first";var u=["im_drunk","im_baked","im_trippin","im_blown"];var a="mw-strobe_light";var f="//s3.amazonaws.com/moovweb-marketing/playground/harlem-shake-style.css";var l="mw_added_css";var b=g();var w=y();var C=document.getElementsByTagName("*");var k=null;for(var L=0;L<C.length;L++){var A=C[L];if(v(A)){if(E(A)){k=A;break}}}if(A===null){console.warn("Could not find a node of the right size. Please try a different page.");return}c();S();var O=[];for(var L=0;L<C.length;L++){var A=C[L];if(v(A)){O.push(A)}}})()'>High一下!</a>
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
        <Modal v-model="showModel" title="密码修改" :mask-closable="false" :width="350" @on-ok="okBtn">
          <Form ref="form" :model="formInline" :rules="ruleInline" inline style="padding-right:30px;">
            <FormItem prop="oldPw" label="旧密码：" :label-width="120">
                <Input type="password" v-model="formInline.oldPw" placeholder="请输入"></Input>
            </FormItem>
            <FormItem prop="newPw" label="新密码：" :label-width="120">
                <Input type="password" v-model="formInline.newPw" placeholder="请输入"></Input>
            </FormItem>
          </Form>
        </Modal>
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
                crumbList:new Array(),
                showModel:false,
                formInline:{
                  oldPw:'',
                  newPw:''
                },
                ruleInline:{
                    oldPw:[
                      { required: true, message: '请填写旧密码', trigger: 'blur' }
                    ],
                    newPw:[
                      { required: true, message: '请填写新密码', trigger: 'blur' }
                    ]
                }
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
                    this.spanLeft = 1;
                    this.spanRight = 23;
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
            },
            okBtn(){
              this.$refs['form'].validate((vali)=>{
                if(vali){
                  util.ajax.put('/user/password/new',this.formInline)
                  .then(rep => {
                    if(rep.success){
                      this.$Message.info('密码修改成功！');
                    }else{
                      this.$Message.error('密码修改失败！');
                    }
                    this.$refs['form'].resetFields();
                  });
                }
              });
            }
        }
    }
</script>
