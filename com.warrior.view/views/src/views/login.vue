<style lang="less">
@import "./login.less";
</style>

<template>
    <div class="login" v-bind:style="{backgroundImage:'url('+backgroudImg+')'}" @keydown.enter="handleSubmit">
        <div class="login-con">
            <Card :bordered="false">
                <p slot="title">
                    <Icon type="log-in"></Icon>
                    欢迎登录
                </p>
                <div class="form-con">
                    <Form ref="loginForm" :model="form" :rules="rules">
                        <FormItem prop="userName">
                            <Input v-model="form.userName" placeholder="请输入用户名" :disabled="logining">
                                <span slot="prepend">
                                    <Icon :size="16" type="person"></Icon>
                                </span>
                            </Input>
                        </FormItem>
                        <FormItem prop="password">
                            <Input type="password" v-model="form.password" placeholder="请输入密码" :disabled="logining">
                                <span slot="prepend">
                                    <Icon :size="14" type="locked"></Icon>
                                </span>
                            </Input>
                        </FormItem>
                        <FormItem>
                            <Button @click="handleSubmit" type="primary" long :loading="logining">
                                <span v-if="!logining">登录</span>
                                <span v-else>系统登录中...</span>
                            </Button>
                        </FormItem>
                    </Form>
                </div>
            </Card>
        </div>
    </div>
</template>

<script>
import Cookies from "js-cookie";
import util from "../libs/util";
import swal from 'sweetalert';

export default {
  data() {
    return {
      logining: false,
      backgroudImg:util.ajaxUrl+'/images/bg.jpeg',
      form: {
        userName: "",
        password: ""
      },
      rules: {
        userName: [{ required: true, message: "账号不能为空", trigger: "blur" }],
        password: [{ required: true, message: "密码不能为空", trigger: "blur" }]
      }
    };
  },
  created() {
    util.vue = this;
  },
  methods: {
    handleSubmit() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.logining = true;
          util.ajax
            .post("/doLogin", {
              userName: this.form.userName,
              passWord: this.form.password
            })
            .then(rep => {
              if (rep.code === 0) {
                Cookies.set("token", rep.data);
                Cookies.set("userName",this.form.userName);
                swal({
                    text: "登录成功!",
                    icon: "success",
                    buttons:false,
                    closeOnClickOutside:false,
                    timer:2000
                }).then((value)=>{
                    this.$router.push({
                        name: "home_index"
                    });
                });
              }
              this.logining = false;
            });
        }
      });
    }
  }
};
</script>

<style>

</style>
