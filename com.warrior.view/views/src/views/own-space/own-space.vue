<style lang="less">
@import "./own-space.less";
</style>

<template>
    <div>
        <Card>
            <p slot="title">
                <Icon type="person"></Icon>
                个人信息
            </p>
            <div>
                <Form 
                    ref="userForm"
                    :model="userForm" 
                    :label-width="100" 
                    label-position="right"
                >
                    <FormItem label="用户名：" prop="userName">
                        <div style="display:inline-block;width:300px;">
                            <span>{{ userForm.userName }}</span>
                        </div>
                    </FormItem>
                    <FormItem label="性别：" prop="gender" >
                        <div style="display:inline-block;width:204px;">
                            <RadioGroup v-model="userForm.gender">
                                <Radio label="男">
                                    <Icon type="man"></Icon>
                                    <span>男</span>
                                </Radio>
                                <Radio label="女">
                                    <Icon type="woman"></Icon>
                                    <span>女</span>
                                </Radio>
                            </RadioGroup>
                        </div>
                    </FormItem>
                    <FormItem label="年龄：" prop="age">
                        <div style="display:inline-block;width:300px;">
                            <InputNumber :max="120" :min="18" v-model="userForm.age"></InputNumber>
                        </div>
                    </FormItem>
                    <FormItem label="登录密码：">
                        <Button type="text" size="small" @click="showEditPassword">修改密码</Button>
                    </FormItem>
                    <div>
                        <Button type="text" style="width: 100px;" @click="cancelEditUserInfor">取消</Button>
                        <Button type="primary" style="width: 100px;" :loading="save_loading" @click="saveEdit">保存</Button>
                    </div>
                </Form>
            </div>
        </Card>
        <Modal v-model="editPasswordModal" :closable='false' :mask-closable=false :width="500">
            <h3 slot="header" style="color:#2D8CF0">修改密码</h3>
            <Form ref="editPasswordForm" :model="editPasswordForm" :label-width="100" label-position="right" :rules="passwordValidate">
                <FormItem label="原密码" prop="oldPass" :error="oldPassError">
                    <Input type="password" v-model="editPasswordForm.oldPass" placeholder="请输入现在使用的密码" ></Input>
                </FormItem>
                <FormItem label="新密码" prop="newPass">
                    <Input type="password" v-model="editPasswordForm.newPass" placeholder="请输入新密码，至少6位字符" ></Input>
                </FormItem>
                <FormItem label="确认新密码" prop="rePass">
                    <Input type="password" v-model="editPasswordForm.rePass" placeholder="请再次输入新密码" ></Input>
                </FormItem>
            </Form>
            <div slot="footer">
                <Button type="text" @click="cancelEditPass">取消</Button>
                <Button type="primary" :loading="savePassLoading" @click="saveEditPass">保存</Button>
            </div>
        </Modal>
    </div>
</template>

<script>
import util from "../../libs/util";
export default {
  name: "own-space",
  data() {
    const valideRePassword = (rule, value, callback) => {
      if (value !== this.editPasswordForm.newPass) {
        callback(new Error("两次输入密码不一致"));
      } else {
        callback();
      }
    };
    return {
      userForm: {
        uid: "",
        userName: "",
        gender: "",
        age: 0
      },
      save_loading: false,
      editPasswordModal: false, // 修改密码模态框显示
      savePassLoading: false,
      oldPassError: "",
      editPasswordForm: {
        oldPass: "",
        newPass: "",
        rePass: ""
      },
      passwordValidate: {
        oldPass: [{ required: true, message: "请输入原密码", trigger: "blur" }],
        newPass: [
          { required: true, message: "请输入新密码", trigger: "blur" },
          { min: 6, message: "请至少输入6个字符", trigger: "blur" },
          { max: 32, message: "最多输入32个字符", trigger: "blur" }
        ],
        rePass: [
          { required: true, message: "请再次输入新密码", trigger: "blur" },
          { validator: valideRePassword, trigger: "blur" }
        ]
      }
    };
  },
  methods: {
    showEditPassword() {
      this.editPasswordModal = true;
    },
    cancelEditUserInfor() {
      this.$store.commit("removeTag", "ownspace_index");
      localStorage.pageOpenedList = JSON.stringify(
        this.$store.state.pageOpenedList
      );
      let lastPageName = "";
      if (this.$store.state.pageOpenedList.length > 1) {
        lastPageName = this.$store.state.pageOpenedList[1].name;
      } else {
        lastPageName = this.$store.state.pageOpenedList[0].name;
      }
      this.$router.push({
        name: lastPageName
      });
    },
    saveEdit() {
      util.ajax.put("/user/updateCurrentUser", this.userForm).then(rep => {
        if (rep.code === 0) {
          this.$Message.info("保存数据成功！");
        }
      });
    },
    cancelEditPass() {
      this.editPasswordModal = false;
    },
    saveEditPass() {
      this.$refs["editPasswordForm"].validate(valid => {
        if (valid) {
          this.savePassLoading = true;
          util.ajax
            .put("/user/password/new", {
              oldPw: this.editPasswordForm.oldPass,
              newPw: this.editPasswordForm.newPass
            })
            .then(rep => {
              if (rep.code == 0) {
                this.$Message.info("密码修改成功！");
              } else {
                this.$Message.error("密码修改失败！");
              }
              this.savePassLoading = false;
              this.editPasswordModal = false;
              this.$refs["editPasswordForm"].resetFields();
            });
        }
      });
    },
    init() {
      util.ajax.get("/user/getCurrentUser").then(rep => {
        if (rep.code === 0) {
          this.userForm.uid = rep.data.uid;
          this.userForm.userName = rep.data.userName;
          this.userForm.gender = rep.data.gender;
          this.userForm.age = rep.data.age;
        }
      });
    }
  },
  mounted() {
    util.vue = this;
    this.init();
  }
};
</script>
