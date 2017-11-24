<style scoped lang="less">
@import "../../styles/common.less";
</style>
<template>
  <div>
    <div class="layout-content">
        <div class="layout-content-main">
          <div class="layout-tools">

              <div style="display:inline;">
                <span>用户名：</span>
                <Input size="small" v-model="userName" style="width:120px;"></Input>
              </div>
              <div style="display:inline;">
                <span>类型：</span>
                <Select size="small" placeholder="类型" v-model="type" style="width:120px;">
                    <Option :value="-1">全部</Option>
                    <Option v-for="item in sel_type" :value="item.dicKey" :key="item.dicKey">{{item.dicValue}}</Option>
                </Select>
              </div>
              <div style="display:inline;">
                <span>状态：</span>
                <Select size="small" placeholder="状态" v-model="status" style="width:120px;">
                    <Option :value="-1">全部</Option>
                    <Option v-for="item in sel_status" :key="item.dicKey" :value="item.dicKey">{{item.dicValue}}</Option>
                </Select>
              </div>
              <div style="display:inline;">
                <span>注册时间：</span>
                <DatePicker size="small" type="datetime" format="yyyy-MM-dd HH:mm" v-model="startTime" placeholder="选择开始日期" style="width:136px;"></DatePicker>
                <DatePicker size="small" type="datetime" format="yyyy-MM-dd HH:mm" v-model="endTime" placeholder="选择结束日期" style="width:136px;"></DatePicker>
              </div>
              <div style="display:inline;">
                <Button type="primary" size="small" icon="ios-search" @click="query">查询</Button>
              </div>

            <Row style="margin-top: 10px;">
              <Col span="24">
                <permissionButton type="primary" size="small" icon="plus" v-on:increment="addItem()" perStr="admin:user:add" text="新增"></permissionButton>
              </Col>
            </Row>
          </div>
          <Table stripe :columns="columns" :data="data"></Table>
          <div style="margin: 10px;overflow: hidden">
              <div style="float: right;">
                  <Page :total="total" size="small" :page-size="pageSize" show-elevator show-sizer @on-change="pageChange" @on-page-size-change="pageSizeChange"></Page>
              </div>
          </div>
        </div>
    </div>
    <Modal v-model="showModel" :title="modelTitle" :mask-closable="false" :width="540" :closable="false">
        <Form ref="form-res" :model="formInline" :rules="ruleInline" inline style="padding-right:30px;">
          <Row>
            <Col span="12">
              <FormItem prop="userName" label="用户名：" :label-width="80">
                  <Input v-model="formInline.userName" placeholder="请输入"></Input>
              </FormItem>
            </Col>
            <Col span="12">
              <FormItem prop="passWord" label="密码：" :label-width="80">
                  <Input type="password" v-model="formInline.passWord" placeholder="请输入" :disabled="this.addOrUpdate=='update'?true:false"></Input>
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="12">
              <FormItem prop="gender" label="性别：" :label-width="80">
                <RadioGroup v-model="formInline.gender">
                    <Radio label="男">男</Radio>
                    <Radio label="女">女</Radio>
                </RadioGroup>
              </FormItem>
            </Col>
            <Col span="12">
              <FormItem prop="age" label="年龄：" :label-width="80">
                  <InputNumber :min="10" v-model="formInline.age" placeholder="请输入"></InputNumber>
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="12">
              <FormItem prop="userType" label="类型：" :label-width="80">
                <Select size="small" placeholder="类型" v-model="formInline.userType" style="width:150px;">
                    <Option v-for="item in sel_type" :value="item.dicKey" :key="item.dicKey">{{item.dicValue}}</Option>
                </Select>
              </FormItem>
            </Col>
            <Col span="12">
              <FormItem prop="status" label="状态：" :label-width="80">
                <Select size="small" placeholder="状态" v-model="formInline.status">
                    <Option v-for="item in sel_status" :key="item.dicKey" :value="item.dicKey">{{item.dicValue}}</Option>
                </Select>
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="12" style="text-align:right;">
              <FormItem style="margin-bottom: 0px;">
                <Button size="large" @click="modelCancel">取消</Button>
              </FormItem>
            </Col>
            <Col span="12" style="text-align:left;padding-left:30px;">
              <FormItem style="margin-bottom: 0px;">
                <Button type="primary" size="large" :loading="isSaveing" @click="handleSubmit('form-res')">保存</Button>
              </FormItem>
            </Col>
          </Row>
        </Form>
        <div slot="footer"></div>
    </Modal>
    <Modal v-model="permissionModel" title="权限设置" :mask-closable="false" :width="240" style="padding-left:15px;" :closable="false">
        <ZTree ref="pers_tree" :treeData="primissData" :options="treeOptions" />
        <div slot="footer">
            <Button type="default" size="large" @click="cancelBtn(1)">取消</Button>
            <permissionButton type="primary" size="large" v-on:increment="okBtn()" perStr="admin:userperm:update" text="确定"></permissionButton>
        </div>
    </Modal>
    <Modal v-model="roleModel" title="角色设置" :mask-closable="false" :width="240" style="padding-left:15px;" :closable="false">
        <ZTree ref="role_tree" :treeData="roleData" :options="treeOptions" />
        <div slot="footer">
            <Button type="default" size="large" @click="cancelBtn(2)">取消</Button>
            <permissionButton type="primary" size="large" v-on:increment="saveRoleBtn()" perStr="admin:userrole:update" text="确定"></permissionButton>
        </div>
    </Modal>
  </div>
</template>
<script>
import util from "../../libs/util";
import ZTree from "../../components/vue2-lazy-tree/vue2-tree.min";
import "../../components/vue2-lazy-tree/vue2-tree.min.css";
import permissionButton from "../main_components/button";

export default {
  components: {
    ZTree,
    permissionButton
  },
  data() {
    return {
      currentUid: 0,
      permissionModel: false,
      primissData: [],
      roleModel: false,
      roleData: [],
      treeOptions: {
        showCheckbox: true,
        halfCheck: true,
        iconClass: {
          close: "icon-youjiantou",
          open: "icon-xiajiantou",
          add: "icon-add"
        },
        iconStyle: {
          color: "#108ee9"
        }
      },
      showModel: false,
      isSaveing: false,
      addOrUpdate: "add",
      modelTitle: "新增用户",
      formInline: {
        uid: 0,
        userName: "",
        passWord: "",
        gender: "男",
        age: 0,
        userType: 1,
        status: 0
      },
      ruleInline: {
        userName: [
          { required: true, message: "请填写名称", trigger: "blur" },
          { type: "string", max: 30, message: "名称长度不能超过30个字符", trigger: "blur" }
        ],
        passWord: [{ required: true, message: "请填写URL", trigger: "blur" }]
      },
      columns: [
        { title: "编号", key: "index", type: "index", align: "center" },
        { title: "用户名", key: "userName", align: "center" },
        { title: "性别", key: "gender", align: "center" },
        { title: "年龄", key: "age", align: "center" },
        {
          title: "类型",
          key: "userType",
          align: "center",
          render: (h, params) => {
            let temp = this.sel_type[params.row.userType - 1];
            return h("Span", {}, !temp ? "" : temp.dicValue);
          }
        },
        {
          title: "状态",
          key: "status",
          align: "center",
          render: (h, params) => {
            let temp = this.sel_status[params.row.status];
            return h("Span", {}, !temp ? "" : temp.dicValue);
          }
        },
        {
          title: "创建时间",
          key: "createTime",
          align: "center",
          width: 150,
          render: (h, params) => {
            return h(
              "Span",
              {},
              util.formatDate(
                new Date(params.row.createTime),
                "yyyy-MM-dd hh:mm"
              )
            );
          }
        },
        {
          title: "操作",
          key: "uid",
          align: "center",
          width: 220,
          render: (h, params) => {
            return h("div", [
              h(permissionButton, {
                props: {
                  type: "primary",
                  size: "small",
                  perStr: "admin:userrole:view",
                  text: "角色"
                },
                style: { marginRight: "5px" },
                on: {
                  increment: () => {
                    this.currentUid = params.row.uid;
                    this.roleModel = true;
                    this.loadRole(params.row.uid);
                  }
                }
              }),
              h(permissionButton, {
                props: {
                  type: "primary",
                  size: "small",
                  perStr: "admin:userperm:view",
                  text: "权限"
                },
                style: { marginRight: "5px" },
                on: {
                  increment: () => {
                    this.currentUid = params.row.uid;
                    this.permissionModel = true;
                    this.loadPermission(params.row.uid);
                  }
                }
              }),
              h(permissionButton, {
                props: {
                  type: "primary",
                  size: "small",
                  perStr: "admin:user:update",
                  text: "修改"
                },
                style: { marginRight: "5px" },
                on: {
                  increment: () => {
                    this.updateItem(params.row.uid);
                  }
                }
              }),
              h(permissionButton, {
                props: { type: "error", size: "small", perStr: "", text: "删除" },
                on: {
                  increment: () => {
                    const _this = this;
                    util.confirm("确认删除当前数据？",function(){
                      _this.delItem(params.row.uid);
                    });
                  }
                }
              })
            ]);
          }
        }
      ],
      data: [],
      page: 1,
      total: 0,
      pageSize: 10,
      userName: "",
      status: -1,
      type: -1,
      startTime: "",
      endTime: "",
      sel_status: [],
      sel_type: []
    };
  },
  created() {
    util.ajax.get("/dictionary/list/model/1").then(rep => {
      if (rep.code === 0) {
        this.sel_status = rep.data;
      }
    });
    util.ajax.get("/dictionary/list/model/4").then(rep => {
      if (rep.code === 0) {
        this.sel_type = rep.data;
      }
    });
    this.query();
  },
  methods: {
    query() {
      util.ajax
        .get("/user/list", {
          params: {
            userNane: this.userName,
            userType: this.type,
            status: this.status,
            startTime: util.formatDate(this.startTime, "yyyy-MM-dd hh:mm"),
            endTime: util.formatDate(this.endTime, "yyyy-MM-dd hh:mm"),
            page: this.page,
            rows: this.pageSize
          }
        })
        .then(rep => {
          if (rep.code === 0) {
            this.data = rep.data.records;
            this.total = rep.data.total;
          }
        });
    },
    delItem(id) {
      util.ajax.delete("/user/" + id).then(rep => {
        if (rep.code === 0) {
          util.success('删除数据成功！');
          if (this.data.length == 1) {
            this.page = this.page - 1;
          }
          this.query();
        }else{
          util.error('删除数据失败！');
        }
      });
    },
    pageChange(page) {
      this.page = page;
      this.query();
    },
    pageSizeChange(pageSize) {
      this.pageSize = pageSize;
      this.query();
    },
    addItem() {
      this.addOrUpdate = "add";
      this.showModel = true;
      this.modelTitle = "新增用户";
    },
    updateItem(id) {
      this.addOrUpdate = "update";
      util.ajax.get("/user/" + id).then(rep => {
        if (rep.code === 0) {
          this.formInline.uid = rep.data.uid;
          this.formInline.userName = rep.data.userName;
          this.formInline.passWord = rep.data.passWord;
          this.formInline.gender = rep.data.gender;
          this.formInline.age = rep.data.age;
          this.formInline.userType = rep.data.userType;
          this.formInline.status = rep.data.status;
        }
      });
      this.showModel = true;
      this.modelTitle = "修改用户信息";
    },
    handleSubmit(name) {
      this.$refs[name].validate(valid => {
        if (valid) {
          this.isSaveing = true;
          if (this.addOrUpdate === "add") {
            util.ajax.post("/user", this.formInline).then(rep => {
              if (rep.code === 0) {
                util.success('保存数据成功！');
              }
              this.query();
              this.$refs["form-res"].resetFields();
              this.isSaveing = false;
              this.showModel = false;
            });
          } else {
            util.ajax.put("/user", this.formInline).then(rep => {
              if (rep.code === 0) {
                util.success('保存数据成功！');
              }
              this.query();
              this.$refs["form-res"].resetFields();
              this.isSaveing = false;
              this.showModel = false;
            });
          }
        }
      });
    },
    modelCancel() {
      this.showModel = false;
      this.$refs["form-res"].resetFields();
    },
    loadPermission(uid) {
      util.ajax.get("/permission/user/" + uid).then(rep => {
        if (rep.code === 0) {
          this.primissData.splice(0, this.primissData.length);
          for (let item of rep.data) {
            let node = {
              id: item.resId,
              label: item.resName,
              open: true,
              checked: item.checked,
              children: [],
              nodeSelectNotAll: false
            };
            this.addChild(node, item.child);
            this.primissData.push(node);
          }
        }
      });
    },
    addChild(node, children) {
      if (children.length == 0) {
        return;
      }
      for (let item of children) {
        let child = {
          id: item.resId,
          label: item.resName,
          open: true,
          checked: item.checked,
          nodeSelectNotAll: false,
          children: []
        };
        this.addChild(child, item.child);
        node.children.push(child);
      }
    },
    cancelBtn(type) {
      if (type == 1) {
        //权限
        this.permissionModel = false;
      } else {
        //角色
        this.roleModel = false;
      }
    },
    okBtn() {
      let ids = this.$refs["pers_tree"].getSelectedNodeIds();
      util.ajax
        .put("/permission/userPermission", {
          uid: this.currentUid,
          permissions: ids.join()
        })
        .then(rep => {
          if (rep.code === 0) {
            util.success('保存数据成功！');
          } else {
            util.error('保存数据失败！');
          }
          this.permissionModel = false;
        });
    },
    loadRole(uid) {
      util.ajax.get("/role/list/" + uid).then(rep => {
        if (rep.code === 0) {
          this.roleData.splice(0, this.roleData.length);
          for (let item of rep.data) {
            this.roleData.push({
              id: item.rid,
              label: item.roleName,
              open: true,
              checked: item.checked,
              nodeSelectNotAll: false
            });
          }
        }
      });
    },
    saveRoleBtn() {
      let ids = this.$refs["role_tree"].getSelectedNodeIds();
      util.ajax
        .put("/role/userRole", {
          uid: this.currentUid,
          permissions: ids.join()
        })
        .then(rep => {
          if (rep.code === 0) {
            util.success('保存数据成功！');
          } else {
            util.error('保存数据失败！');
          }
          this.roleModel = false;
        });
    }
  }
};
</script>
