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
  padding-left:2px;
}
</style>
<template>
  <div class="layout-content">
      <div class="layout-content-main">
        <div class="layout-tools">
          <Row>
            <Col span="5" class="layout-col-padding">
              <span>字典类型：</span>
              <Select size="small" placeholder="状态" v-model="dictType" style="width:120px;">
                  <Option :value="-1">全部</Option>
                  <Option v-for="item in sel_type" :key="item.dicKey" :value="item.dicKey">{{item.dicValue}}</Option>
              </Select>
            </Col>
            <Col span="19" class="layout-col-padding">
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
        <Modal v-model="showModel" :title="modelTitle" :mask-closable="false" :width="540">
            <Form ref="form-dict" :model="formInline" :rules="ruleInline" inline style="padding-right:30px;">
              <Row>
                <Col span="12">
                  <FormItem prop="dicValue" label="字典名称：" :label-width="120">
                      <Input v-model="formInline.dicValue" placeholder="请输入"></Input>
                  </FormItem>
                </Col>
                <Col span="12">
                  <FormItem prop="dicKey" label="字典值：" :label-width="120">
                    <Input v-model="formInline.dicKey" placeholder="请输入"></Input>
                  </FormItem>
                </Col>
              </Row>
              <Row>
                <Col span="12">
                  <FormItem prop="dicType" label="类型编码：" :label-width="120">
                      <InputNumber :min="1" v-model="formInline.dicType"></InputNumber>
                  </FormItem>
                </Col>
                <Col span="12">
                  <FormItem prop="typeValue" label="类型名称：" :label-width="120">
                      <Input v-model="formInline.typeValue" placeholder="请输入"></Input>
                  </FormItem>
                </Col>
              </Row>
              <Row>
                <Col span="12">
                  <FormItem prop="isShow" label="是否显示：" :label-width="120">
                    <RadioGroup v-model="formInline.isShow">
                        <Radio label="0">显示</Radio>
                        <Radio label="1">不显示</Radio>
                    </RadioGroup>
                  </FormItem>
                </Col>
                <Col span="12">
                  <FormItem prop="sort" label="排序：" :label-width="120">
                      <InputNumber :min="1" v-model="formInline.sort"></InputNumber>
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
                    <Button type="primary" size="large" :loading="isSaveing" @click="handleSubmit('form-dict')">保存</Button>
                  </FormItem>
                </Col>
              </Row>
            </Form>
            <div slot="footer"></div>
        </Modal>
      </div>
  </div>
</template>

<script>
  import util from '../../libs/util';
  export default{
    data(){
      return {
        addOrUpdate:'add',
        showModel:false,
        isSaveing:false,
        modelTitle:'新增字典',
        formInline:{
          id:0,
          dicValue:'',
          dicKey:'',
          dicType:0,
          typeValue:'',
          isShow:0,
          sort:1
        },
        ruleInline:{
          dicValue:[
            { required: true, message: '请填写字典名称', trigger: 'blur' }
          ],
          dicKey:[
            { required: true, message: '请填写字典值', trigger: 'blur' }
          ],
          typeValue:[
            { required: true, message: '请填写类型名称', trigger: 'blur' }
          ]
        },
        columns:[
          { title:'编号',key:'index',type:'index',align:'center'},
          { title:'字典名称',key:'dicValue',align:'center' },
          { title:'字典值',key:'dicKey',align:'center' },
          { title:'字典类型',key:'dicType',align:'center' },
          { title:'类型名称',key:'typeValue',align:'center' },
          { title:'操作',key:'id',align:'center',width:180,render:(h,params)=>{
            return h('div',[
              h('Button',{
                props:{type:'primary',size:'small'},
                style:{marginRight:'5px'},
                on:{click:()=>{
                  this.showModel = true;
                  this.modelTitle = '修改字典';
                  this.addOrUpdate = 'update';
                  util.ajax.get('/dictionary/'+params.row.id)
                  .then(rep => {
                    this.formInline.id= rep.id;
                    this.formInline.dicValue = rep.dicValue;
                    this.formInline.dicKey = rep.dicKey;
                    this.formInline.dicType = rep.dicType;
                    this.formInline.typeValue = rep.typeValue;
                    this.formInline.isShow = rep.isShow;
                    this.formInline.sort = rep.sort;
                  });
                }}
              },'修改'),
              h('Button',{
                props:{type:'error',size:'small'},
                on:{click:()=>{
                  this.delItem(params.row.id);
                }}
              },'删除')
            ]);
          }}
        ],
        dictType:'',
        sel_type:[],
        data:[],
        total:0,
        pageSize:10,
        page:1
      };
    },
    created(){
      util.vue = this;
      util.ajax.get('/dictionary/type/list')
      .then(rep=>{
        this.sel_type = rep;
      });
      this.query();
    },
    methods:{
      query(){
        let params = '?dictType='+this.dictType+'&page='+this.page+'&rows='+this.pageSize;
        util.ajax.get('/dictionary/list'+params)
        .then(rep=>{
          this.data = rep.records;
          this.total = rep.total;
        });
      },
      addItem(){
        this.showModel = true;
        this.modelTitle = '新增字典';
        this.addOrUpdate = 'add';
      },
      delItem(id){
        this.$Modal.confirm({
          title:'操作提示',
          content:'确认删除当前数据？',
          onOk:()=>{
            util.ajax.delete('/dictionary/'+id)
            .then(rep =>{
              this.$Message.info(rep.success ? '删除数据成功！' : '删除数据失败！');
              if(rep.success){
                if(this.data.length == 1){
                  this.page = this.page - 1;
                }
                this.query();
              }
            });
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
      modelCancel(){
        this.showModel = false;
        this.$refs['form-dict'].resetFields();
      },
      handleSubmit(name){
        this.$refs[name].validate((valid) => {
          if(valid){
              this.isSaveing = true;
              if(this.addOrUpdate === 'add'){
                util.ajax.post('/dictionary',this.formInline)
                .then(rep=>{
                  this.$Message.info('保存数据成功！');
                  if(this.data.length == this.pageSize){
                    this.page = this.page + 1;
                  }
                  this.query();
                  this.$refs['form-dict'].resetFields();
                  this.isSaveing = false;
                  this.showModel=false;
                });
              }else{
                util.ajax.put('/dictionary/'+this.formInline.id,this.formInline).then(rep=>{
                  this.$Message.info('保存数据成功！');
                  this.query();
                  this.$refs['form-dict'].resetFields();
                  this.isSaveing = false;
                  this.showModel=false;
                });
              }
          }
        });
      }
    }
  }
</script>
