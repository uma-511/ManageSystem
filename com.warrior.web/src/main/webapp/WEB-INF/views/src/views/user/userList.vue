<style scoped>
.layout-content{
    min-height: 400px;
    margin: 10px;
    overflow: hidden;
    background: #fff;
    border-radius: 4px;
}
.layout-content-main{
    padding: 5px;
}
.layout-tools{
  padding-bottom:5px;
}
.layout-col-padding{
  padding-left:5px;
}
</style>
<template>
  <div>
    <div class="layout-content">
        <div class="layout-content-main">
          <div class="layout-tools">
            <Row>
              <Col span="4">
                <Input size="small" v-model="userName">
                  <span slot="prepend">用户名：</span>
                </Input>
              </Col>
              <Col span="4" class="layout-col-padding">
                <Select size="small" placeholder="类型" v-model="type">
                    <Option value="-1">全部</Option>
                    <Option v-for="item in sel_type" :value="item.dicKey" :key="item.dicKey">{{item.dicValue}}</Option>
                </Select>
              </Col>
              <Col span="4" class="layout-col-padding">
                <Select size="small" placeholder="状态" v-model="status">
                    <Option value="-1">全部</Option>
                    <Option v-for="item in sel_status" :key="item.dicKey" :value="item.dicKey">{{item.dicValue}}</Option>
                </Select>
              </Col>
              <Col span="4" class="layout-col-padding">
                <DatePicker size="small" type="datetime" format="yyyy-MM-dd HH:mm" v-model="startTime" placeholder="选择开始日期"></DatePicker>
              </Col>
              <Col span="4" class="layout-col-padding">
                <DatePicker size="small" type="datetime" format="yyyy-MM-dd HH:mm" v-model="endTime" placeholder="选择结束日期"></DatePicker>
              </Col>
              <Col span="4" class="layout-col-padding">
                  <Button type="primary" size="small" icon="ios-search" @click="query">查询</Button>
              </Col>
            </Row>
            <Row style="margin-top: 10px;">
              <Col span="24">
                <Button type="primary" size="small" icon="plus" @click="addItem()">新增</Button>
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
    <Modal v-model="showModel" :title="modelTitle" :mask-closable="false" :width="540">
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
  </div>
</template>
<script>
  import util from '../../libs/util';
  export default{
    data(){
      return {
        showModel:false,
        isSaveing:false,
        addOrUpdate:'add',
        modelTitle:'新增用户',
        formInline:{
          uid:0,
          userName:'',
          passWord:'',
          gender:'男',
          age:0,
          userType:1,
          status:0
        },
        ruleInline:{
          userName:[
            { required: true, message: '请填写名称', trigger: 'blur' },
            { type: 'string', max:30, message: '名称长度不能超过30个字符', trigger: 'blur' }
          ],
          passWord:[
            { required: true, message: '请填写URL', trigger: 'blur' }
          ]
        },
        columns:[
          { title:'编号',key:'index',type:'index',align:'center'},
          { title:'用户名',key:'userName',align:'center' },
          { title:'性别',key:'gender',align:'center' },
          { title:'年龄',key:'age',align:'center'},
          { title:'类型',key:'userType',align:'center',render:(h,params)=>{
            return h('Span',{},this.sel_type[params.row.userType-1].dicValue);
          }},
          { title:'状态',key:'status',align:'center',render:(h,params)=>{
            return h('Span',{},this.sel_status[params.row.status].dicValue);
          }},
          { title:'注册时间',key:'createTime',align:'center',width:150,render:(h,params)=>{
            return h('Span',{},util.formatDate(new Date(params.row.createTime),'yyyy-MM-dd hh:mm'));
          }},
          { title:'操作',key:'uid',align:'center',width:125,render:(h,params)=>{
            return h('div',[
              h('Button',{
                props:{type:'primary',size:'small'},
                style:{marginRight:'5px'},
                on:{click:()=>{
                  this.updateItem(params.row.uid);
                }}
              },'修改'),
              h('Button',{
                props:{type:'error',size:'small'},
                on:{click:()=>{
                  this.$Modal.confirm({
                    title:'操作提示',
                    content:'确认删除当前数据？',
                    onOk:()=>{
                      this.delItem(params.row.uid);
                    }
                  });
                }}
              },'删除')
            ]);
          }}
        ],
        data:[],
        page:1,
        total:0,
        pageSize:10,
        userName:'',
        status:-1,
        type:-1,
        startTime:'',
        endTime:'',
        sel_status:[],
        sel_type:[]
      };
    },
    created() {
      util.vue = this;
      util.ajax.get('/dictionary/list/model/1')
      .then(rep => {
        this.sel_status = rep;
      });
      util.ajax.get('/dictionary/list/model/4')
      .then(rep => {
        this.sel_type = rep;
      });
      this.query();
    },
    methods: {
      query(){
        let params = '?userName='+this.userName+'&userType='+this.type+'&status='+this.status+'&startTime='+
        util.formatDate(this.startTime,'yyyy-MM-dd hh:mm')+'&endTime='+util.formatDate(this.endTime,'yyyy-MM-dd hh:mm')+'&page='+this.page+'&rows='+this.pageSize;
        util.ajax.get('/user/list'+params)
        .then(rep => {
          this.data = rep.list;
          this.total = rep.total;
        });
      },
      delItem(id){
        util.ajax.delete('/user/'+id)
        .then(rep=>{
          this.$Message.info(rep.success ? '删除数据成功！' : '删除数据失败！');
          if(rep.success){this.query();}
        });
      },
      pageChange(page){
        this.page = page;
        this.query();
      },
      pageSizeChange(pageSize){
        this.pageSize = pageSize;
        this.query();
      },
      addItem(){
        this.addOrUpdate = 'add';
        this.showModel = true;
        this.modelTitle = '新增用户';
      },
      updateItem(id){
        this.addOrUpdate = 'update';
        util.ajax.get('/user/'+id)
        .then(rep => {
          this.formInline.uid = rep.uid;
          this.formInline.userName = rep.userName;
          this.formInline.passWord = rep.passWord;
          this.formInline.gender = rep.gender;
          this.formInline.age = rep.age;
          this.formInline.userType = rep.userType;
          this.formInline.status = rep.status;
        });
        this.showModel = true;
        this.modelTitle = '修改用户信息';
      },
      handleSubmit(name){
        this.$refs[name].validate((valid) => {
          if(valid){
              this.isSaveing = true;
              if(this.addOrUpdate === 'add'){
                util.ajax.post('/user',this.formInline)
                .then(rep=>{
                  this.$Message.info('保存数据成功！');
                  this.query();
                  this.$refs['form-res'].resetFields();
                  this.isSaveing = false;
                  this.showModel=false;
                });
              }else{
                util.ajax.put('/user/'+this.formInline.uid,this.formInline).then(rep=>{
                  this.$Message.info('保存数据成功！');
                  this.query();
                  this.$refs['form-res'].resetFields();
                  this.isSaveing = false;
                  this.showModel=false;
                });
              }
          }
        });
      },
      modelCancel(){
        this.showModel=false;
        this.$refs['form-res'].resetFields();
      }
    }
  }
</script>
