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
                <span>名称：</span>
                <Input size="small" v-model="name" style="width:120px;"></Input>
              </Col>
              <Col span="4" class="layout-col-padding">
                <span>URL：</span>
                <Input size="small" v-model="url" style="width:120px;"></Input>
              </Col>
              <Col span="4" class="layout-col-padding">
                <span>状态：</span>
                <Select size="small" placeholder="状态" v-model="status" style="width:120px;">
                    <Option :value="-1">全部</Option>
                    <Option v-for="item in sel_status" :key="item.dicKey" :value="item.dicKey">{{item.dicValue}}</Option>
                </Select>
              </Col>
              <Col span="4" class="layout-col-padding">
                <span>是否显示：</span>
                <Select size="small" placeholder="是否显示" v-model="isShow" style="width:100px;">
                    <Option :value="-1">全部</Option>
                    <Option v-for="item in sel_isShow" :value="item.dicKey" :key="item.dicKey">{{item.dicValue}}</Option>
                </Select>
              </Col>
              <Col span="4" class="layout-col-padding">
                <span>类型：</span>
                <Select size="small" placeholder="类型" v-model="type" style="width:120px;">
                    <Option :value="-1">全部</Option>
                    <Option v-for="item in sel_type" :value="item.dicKey" :key="item.dicKey">{{item.dicValue}}</Option>
                </Select>
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
              <FormItem prop="resName" label="名称：" :label-width="80">
                  <Input v-model="formInline.resName" placeholder="请输入"></Input>
              </FormItem>
            </Col>
            <Col span="12">
              <FormItem prop="url" label="URL：" :label-width="80">
                  <Input v-model="formInline.url" placeholder="请输入"></Input>
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="12">
              <FormItem prop="parentId" label="父级：" :label-width="80">
                <Select size="small" placeholder="请选择" v-model="formInline.parentId" style="width:120px;">
                    <Option :value="0">无</Option>
                    <Option v-for="item in data" :key="item.resId" :value="item.resId" v-if="item.type==0">{{item.resName}}</Option>
                </Select>
              </FormItem>
            </Col>
            <Col span="12">
              <FormItem prop="icon" label="图标：" :label-width="80">
                  <Input v-model="formInline.icon" placeholder="请输入"></Input>
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="12">
              <FormItem prop="type" label="类型：" :label-width="80">
                <Select size="small" placeholder="类型" v-model="formInline.type">
                    <Option v-for="item in sel_type" :value="item.dicKey" :key="item.dicKey">{{item.dicValue}}</Option>
                </Select>
              </FormItem>
            </Col>
            <Col span="12">
              <FormItem prop="isShow" label="是否显示：" :label-width="80">
                <RadioGroup v-model="formInline.isShow">
                    <Radio label="0">显示</Radio>
                    <Radio label="1">不显示</Radio>
                </RadioGroup>
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="12">
              <FormItem prop="status" label="状态：" :label-width="80">
                <Select size="small" placeholder="状态" v-model="formInline.status" >
                    <Option v-for="item in sel_status" :key="item.dicKey" :value="item.dicKey">{{item.dicValue}}</Option>
                </Select>
              </FormItem>
            </Col>
            <Col span="12">
              <FormItem prop="sort" label="排序：" :label-width="80">
                  <InputNumber :min="1" v-model="formInline.sort"></InputNumber>
              </FormItem>
            </Col>
          </Row>
          <Row>
            <Col span="12">
              <FormItem prop="permission" label="权限值：" :label-width="80">
                  <Input v-model="formInline.permission" placeholder="请输入"></Input>
              </FormItem>
            </Col>
            <Col span="12">
              <FormItem prop="remark" label="备注：" :label-width="80">
                  <Input v-model="formInline.remark" placeholder="请输入"></Input>
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
        modelTitle:'新增资源',
        formInline:{
          resId:0,
          resName:'',
          url:'',
          icon:'',
          parentId:0,
          type:0,
          isShow:0,
          status:0,
          sort:1,
          permission:'',
          remark:''
        },
        ruleInline:{
          resName:[
            { required: true, message: '请填写名称', trigger: 'blur' },
            { type: 'string', max:30, message: '名称长度不能超过30个字符', trigger: 'blur' }
          ]
        },
        columns:[
          { title:'编号',key:'index',type:'index',align:'center'},
          { title:'名称',key:'resName',align:'center' },
          { title:'URL',key:'url',align:'center' },
          { title:'图标',key:'icon',align:'center',render:(h,params)=>{
              return h('Icon',{props:{type:params.row.icon,size:28,color:'gray'}},'');
          }},
          { title:'类型',key:'type',align:'center',render:(h,params)=>{
            return h('Span',{},this.sel_type[params.row.type].dicValue);
          }},
          { title:'显示',key:'isShow',align:'center',render:(h,params)=>{
            return h('Span',{},this.sel_isShow[params.row.isShow].dicValue);
          }},
          { title:'状态',key:'status',align:'center',render:(h,params)=>{
            return h('Span',{},this.sel_status[params.row.status].dicValue);
          }},
          { title:'排序',key:'sort',align:'center' },
          { title:'备注',key:'remark',align:'center' },
          { title:'操作',key:'resId',align:'center',width:125,render:(h,params)=>{
            return h('div',[
              h('Button',{
                props:{type:'primary',size:'small'},
                style:{marginRight:'5px'},
                on:{click:()=>{
                  this.updateItem(params.row.resId);
                }}
              },'修改'),
              h('Button',{
                props:{type:'error',size:'small'},
                on:{click:()=>{
                  this.$Modal.confirm({
                    title:'操作提示',
                    content:'确认删除当前数据？',
                    onOk:()=>{
                      this.delItem(params.row.resId);
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
        name:'',
        url:'',
        status:-1,
        isShow:-1,
        type:-1,
        sel_status:[],
        sel_isShow:[],
        sel_type:[]
      };
    },
    created() {
      util.vue = this;
      util.ajax.get('/dictionary/list/model/1')
      .then(rep => {
        this.sel_status = rep;
      });
      util.ajax.get('/dictionary/list/model/2')
      .then(rep => {
        this.sel_isShow = rep;
      });
      util.ajax.get('/dictionary/list/model/3')
      .then(rep => {
        this.sel_type = rep;
      });
      this.query();
    },
    methods: {
      query(){
        let params = '?name='+this.name+'&url='+this.url+'&status='+this.status+'&isShow='+this.isShow+'&type='+this.type+'&page='+this.page+'&rows='+this.pageSize;
        util.ajax.get('/resource/list'+params)
        .then(rep => {
          this.data = rep.records;
          this.total = rep.total;
        });
      },
      delItem(id){
        util.ajax.delete('/resource/'+id)
        .then(rep=>{
          this.$Message.info(rep.success ? '删除数据成功！' : '删除数据失败！');
          if(rep.success){
            if(this.data.length == 1){
              this.page = this.page - 1;
            }
            this.query();
          }
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
        this.modelTitle = '新增资源';
      },
      updateItem(id){
        this.addOrUpdate = 'update';
        util.ajax.get('/resource/'+id)
        .then(rep => {
          this.formInline.resId = rep.resId;
          this.formInline.resName = rep.resName;
          this.formInline.url = rep.url;
          this.formInline.icon = rep.icon;
          this.formInline.parentId = rep.parentId;
          this.formInline.type = rep.type;
          this.formInline.status = rep.status;
          this.formInline.isShow = rep.isShow;
          this.formInline.sort = rep.sort;
          this.formInline.permission = rep.permission;
          this.formInline.remark = rep.remark;
        });
        this.showModel = true;
        this.modelTitle = '修改资源';
      },
      handleSubmit(name){
        this.$refs[name].validate((valid) => {
          if(valid){
              this.isSaveing = true;
              if(this.addOrUpdate === 'add'){
                util.ajax.post('/resource',this.formInline)
                .then(rep=>{
                  this.$Message.info('保存数据成功！');
                  if(this.data.length == this.pageSize){
                    this.page = this.page + 1;
                  }
                  this.query();
                  this.$refs['form-res'].resetFields();
                  this.isSaveing = false;
                  this.showModel=false;
                });
              }else{
                util.ajax.put('/resource/'+this.formInline.resId,this.formInline).then(rep=>{
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
