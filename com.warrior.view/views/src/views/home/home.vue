<style lang="less">
@import "./home.less";
@import "../../styles/common.less";
</style>
<template>
    <div class="home-main">
        <Row>
            <Col span="8">
                <Row>
                    <Card>
                        <Row type="flex" class="user-infor">
                            <Col span="8">
                                <Row class-name="made-child-con-middle" type="flex" align="middle">
                                    <img class="avator-img" :src="avatorPath" />
                                </Row>
                            </Col>
                            <Col span="16" style="padding-left:6px;">
                                <Row class-name="made-child-con-middle" type="flex" align="middle">
                                    <div>
                                        <b class="card-user-infor-name">{{user.userName}}</b>
                                        <p>{{user.roleName}}</p>
                                    </div>
                                </Row>
                            </Col>
                        </Row>
                        <div class="line-gray"></div>
                        <Row class="margin-top-8">
                            <Col span="8"><p class="notwrap">上次登录时间:</p></Col>
                            <Col span="16" class="padding-left-8">{{user.lastTime}}</Col>
                        </Row>
                        <Row class="margin-top-8">
                            <Col span="8"><p class="notwrap">上次登录IP地点:</p></Col>
                            <Col span="16" class="padding-left-8">{{user.ip}}</Col>
                        </Row>
                    </Card>
                </Row>
            </Col>
            <Col span="16">
                <Row >
                    <Card>
                        <p slot="title" class="card-title">
                            <Icon type="android-checkbox-outline"></Icon>
                            待办事项
                        </p>
                        <a type="text" slot="extra" @click.prevent="addNewToDoItem">
                            <Icon type="plus-round"></Icon>
                        </a>
                        <Modal
                            v-model="showAddNewTodo"
                            :title="toDoTitle"
                            @on-ok="addNew"
                            @on-cancel="cancelAdd">
                            <Row type="flex" justify="center">
                                <Input v-model="newToDoItemValue" icon="compose" placeholder="请输入..." style="width: 100%;" />
                            </Row>
                            <Row slot="footer">
                                <Button type="text" @click="cancelAdd">取消</Button>
                                <Button type="primary" @click="addNew">确定</Button>
                            </Row>
                        </Modal>
                        <div class="to-do-list-con">
                            <div v-for="(item, index) in toDoList" :key="index" class="to-do-item">
                                <Row class="to-do-list-item">
                                    <Col span="1" class="height-100" style="padding:6px;">
                                        <Checkbox :value="item.status==1?false:true" v-on:click.native="handleHasDid(item.id,item.status==1?2:1)"></Checkbox>
                                    </Col>
                                    <Col span="21" class="height-100" style="padding:6px;">
                                        <p class="to-do-list-item-text" :class="{hasDid: item.status==1?false:true}">{{ item.note }}</p>
                                    </Col>
                                    <Col span="2" class="height-100" style="text-align:right;padding:6px;">
                                        <Icon type="edit" color="#228EE9" style="cursor: pointer;" v-on:click.native="edit(item.id,item.note)"></Icon>&nbsp;&nbsp;
                                        <Icon type="trash-a" color="#FF5223" size="18" style="cursor: pointer;" v-on:click.native="del(item.id)"></Icon>
                                    </Col>
                                </Row>
                            </div>
                        </div>
                    </Card>
                </Row>
            </Col>
        </Row>
    </div>
</template>

<script>
import util from "../../libs/util";
import Cookies from "js-cookie";

export default {
  name: "home",
  data() {
    return {
      toDoList: [],
      showAddNewTodo: false,
      newToDoItemValue: "",
      toDoTitle: "添加新的待办事项",
      id: 0
    };
  },
  computed: {
    avatorPath() {
      return this.$store.getters.getUser.img;
    },
    user() {
      return this.$store.getters.getUser;
    }
  },
  created() {
    this.query();
  },
  methods: {
    addNewToDoItem() {
      this.showAddNewTodo = true;
      this.toDoTitle = "添加新的待办事项";
    },
    query() {
      util.ajax.get("/notes/list").then(rep => {
        if (rep.code == 0) {
          this.toDoList = rep.data;
        }
      });
    },
    addNew() {
      if (this.newToDoItemValue.length !== 0) {
        if(this.newToDoItemValue.length > 100){
            util.error('内容长度不能超过100个字符！');
            return;
        }
        if (this.id > 0) {
          util.ajax
            .put("/notes/"+this.id, { note: this.newToDoItemValue})
            .then(rep => {
              if (rep.code == 0) {
                util.success("修改成功！");
                this.query();
                this.cancelAdd();
              }
            });
        } else {
          util.ajax
            .post("/notes", { note: this.newToDoItemValue })
            .then(rep => {
              if (rep.code == 0) {
                util.success("添加成功！");
                this.query();
                this.cancelAdd();
              }
            });
        }
      } else {
        util.error("请输入待办事项内容");
      }
    },
    cancelAdd() {
      this.showAddNewTodo = false;
      this.newToDoItemValue = "";
    },
    del(id) {
      const _this = this;
      util.confirm("确认删除该数据？", function() {
        util.ajax.delete("/notes/" + id).then(rep => {
          if (rep.code == 0) {
            util.success("删除成功！");
            _this.query();
          }
        });
      });
    },
    edit(id, note) {
      this.toDoTitle = "修改待办事项";
      this.id = id;
      this.newToDoItemValue = note;
      this.showAddNewTodo = true;
    },
    handleHasDid(id,status) {
      util.ajax.put("/notes/" + id + "/" + status).then(rep=>{
          if(rep.code == 0){
              this.query();
          }
      });
    }
  }
};
</script>
