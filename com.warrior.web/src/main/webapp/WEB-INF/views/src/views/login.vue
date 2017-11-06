<style scoped>
</style>
<template>
  <Row :style="styleObject">
      <Col span="8" >&nbsp;</Col>
      <Col span="8">
          <Card shadow>
              <p slot="title" style="text-align:center;">用户登录</p>
              <Form ref="formInline" :model="formInline" :rules="ruleInline" inline style="height:150px;">
                <FormItem prop="user" style="width:100%;">
                    <Input type="text" v-model="formInline.user" placeholder="Username" :disabled="logining">
                        <Icon type="ios-person-outline" slot="prepend"></Icon>
                    </Input>
                </FormItem>
                <FormItem prop="password" style="width:100%;">
                    <Input type="password" v-model="formInline.password" placeholder="Password" :disabled="logining">
                        <Icon type="ios-locked-outline" slot="prepend"></Icon>
                    </Input>
                </FormItem>
                <FormItem style="width:100%;text-align:center;">
                    <Button type="primary" @click="handleSubmit('formInline')" :loading="logining">
                      <span v-if="!logining">登录</span>
                      <span v-else>系统登录中...</span>
                    </Button>
                </FormItem>
            </Form>
          </Card>
      </Col>
      <Col span="8" >&nbsp;</Col>
  </Row>
</template>
<script>
  import util from '../libs/util';

  export default{
      data(){
        return {
            logining:false,
            formHeight: 0,
            formInline: {
              user: '',
              password: ''
            },
            ruleInline: {
              user: [
                { required: true, message: '请填写用户名', trigger: 'blur' }
              ],
              password: [
                {required: true, message: '请填写密码', trigger: 'blur' },
                { type: 'string', min: 6, message: '密码长度不能小于6位', trigger: 'blur' }
              ]
            },
            styleObject:{
              background:'#eee',
              height:document.documentElement.clientHeight+'px',
              paddingTop:(document.documentElement.clientHeight/3)+'px'
            }
        };
      },
      created() {
        util.vue = this;
        if(localStorage.getItem('currentUser')){
           this.$router.push('/index');
        }
        window.addEventListener('resize',this.handleResize);
        window.addEventListener('keydown',this.handleKeyDown);
      },
      beforeDestroy() {
        window.removeEventListener('resize',this.handleResize);
        window.removeEventListener('keydown',this.handleKeyDown);
      },
      methods: {
        showErrorMsg(msg){
          this.$Message.error(msg);
        },
        handleResize(event) {
          this.styleObject.height = document.documentElement.clientHeight+'px';
          this.styleObject.paddingTop = (document.documentElement.clientHeight/3)+'px';
        },
        handleKeyDown(event){
          if((event.keyCode | event.which) == 13){
            this.handleSubmit('formInline');
          }
        },
        handleSubmit(name) {
          this.$refs[name].validate((valid) => {
            if (valid) {
              this.logining = true;
              util.ajax.post('/doLogin',{
                  userName:this.formInline.user,
                  passWord:this.formInline.password
              }).then(rep=>{
                  localStorage.setItem('currentUser',rep);
                  this.$router.push('/index');
              });
            }
          })
        }
      }
  }
</script>
