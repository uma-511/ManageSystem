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
  <div>
    <div class="layout-content">
        <div class="layout-content-main">
          <div class="layout-tools">
            <Row>
              <Col span="5">
                <span>角色名称：</span>
                <Input size="small" v-model="roleName" style="width:120px;"></Input>
              </Col>
              <Col span="4" class="layout-col-padding">
                <span>状态：</span>
                <Select size="small" placeholder="状态" v-model="status" style="width:120px;">
                    <Option :value="-1">全部</Option>
                    <Option v-for="item in sel_status" :key="item.dicKey" :value="item.dicKey">{{item.dicValue}}</Option>
                </Select>
              </Col>
              <Col span="15" class="layout-col-padding">
                  <Button type="primary" size="small" icon="ios-search" @click="query">查询</Button>
              </Col>
            </Row>
            <Row style="margin-top: 10px;">
              <Col span="24">
                <Button type="primary" size="small" icon="plus" @click="addItem()" v-if="checkPermission('admin:role:add')">新增</Button>
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
              <FormItem prop="roleName" label="角色用户名：" :label-width="120">
                  <Input v-model="formInline.roleName" placeholder="请输入"></Input>
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
            <Col span="12">
              <FormItem prop="remark" label="备注：" :label-width="120">
                  <Input v-model="formInline.remark" placeholder="请输入"></Input>
              </FormItem>
            </Col>
            <Col span="12"></Col>
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
    <Modal v-model="permissionModel" title="权限设置" :mask-closable="false" :width="240" style="padding-left:15px;">
        <ZTree ref="pers_tree" :treeData="primissData" :options="treeOptions" />
        <div slot="footer">
            <Button type="default" size="large" @click="cancelBtn()">取消</Button>
            <Button type="primary" size="large" @click="okBtn" v-if="checkPermission('admin:roleperm:update')">确定</Button>
        </div>
    </Modal>
  </div>
</template>
<script>
  import util from '../../libs/util';
  import ZTree from '../../components/vue2-lazy-tree/vue2-tree.min'
  import '../../components/vue2-lazy-tree/vue2-tree.min.css'

  export default{
    components:{
      ZTree
    },
    data(){
      return {
        currentRid:0,
        permissionModel:false,
        primissData:[],
        treeOptions:{
          showCheckbox: true,
          halfCheck: true,
          iconClass: {
              close: 'icon-youjiantou',
              open: 'icon-xiajiantou',
              add: 'icon-add'
          },
          iconStyle: {
              color: '#108ee9'
          }
        },
        showModel:false,
        isSaveing:false,
        addOrUpdate:'add',
        modelTitle:'新增角色',
        formInline:{
          rid:0,
          roleName:'',
          remark:'',
          status:0
        },
        ruleInline:{
          roleName:[
            { required: true, message: '请填写角色名称', trigger: 'blur' },
            { type: 'string', max:30, message: '名称长度不能超过30个字符', trigger: 'blur' }
          ]
        },
        columns:[
          { title:'编号',key:'index',type:'index',align:'center'},
          { title:'角色名称',key:'roleName',align:'center' },
          { title:'状态',key:'status',align:'center',render:(h,params)=>{
            return h('Span',{},this.sel_status[params.row.status].dicValue);
          }},
          { title:'备注',key:'remark',align:'center' },
          { title:'操作',key:'rid',align:'center',width:180,render:(h,params)=>{
            return h('div',[
              h('Button',{
                props:{type:'primary',size:'small',disabled:!this.checkPermission('admin:roleperm:view')},
                style:{marginRight:'5px'},
                on:{click:()=>{
                  this.currentRid = params.row.rid;
                  this.permissionModel = true;
                  this.loadPermission(params.row.rid);
                }}
              },'权限'),
              h('Button',{
                props:{type:'primary',size:'small',disabled:!this.checkPermission('admin:role:update')},
                style:{marginRight:'5px'},
                on:{click:()=>{
                  this.updateItem(params.row.rid);
                }}
              },'修改'),
              h('Button',{
                props:{type:'error',size:'small',disabled:!this.checkPermission('admin:role:del')},
                on:{click:()=>{
                  this.$Modal.confirm({
                    title:'操作提示',
                    content:'确认删除当前数据？',
                    onOk:()=>{
                      this.delItem(params.row.rid);
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
        roleName:'',
        status:-1,
        sel_status:[]
      };
    },
    created() {
      util.vue = this;
      util.ajax.get('/dictionary/list/model/1')
      .then(rep => {
        this.sel_status = rep;
      });
      this.query();
    },
    methods: {
      query(){
        let params = '?roleName='+this.roleName+'&status='+this.status+'&page='+this.page+'&rows='+this.pageSize;
        util.ajax.get('/role/list'+params)
        .then(rep => {
          this.data = rep.records;
          this.total = rep.total;
        });
      },
      delItem(id){
        util.ajax.delete('/role/'+id)
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
        this.modelTitle = '新增用户';
      },
      updateItem(id){
        this.addOrUpdate = 'update';
        util.ajax.get('/role/'+id)
        .then(rep => {
          this.formInline.rid = rep.rid;
          this.formInline.roleName = rep.roleName;
          this.formInline.remark = rep.remark;
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
                util.ajax.post('/role',this.formInline)
                .then(rep=>{
                  this.$Message.info('保存数据成功！');
                  this.query();
                  this.$refs['form-res'].resetFields();
                  this.isSaveing = false;
                  this.showModel=false;
                });
              }else{
                util.ajax.put('/role/'+this.formInline.rid,this.formInline).then(rep=>{
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
      },
      cancelBtn(){
        this.permissionModel = false;
      },
      loadPermission(rid){
        util.ajax.get('/permission/role/'+rid)
        .then(rep =>{
            this.primissData.splice(0,this.primissData.length);
            for(let item of rep){
              let node = {id:item.resId,label:item.resName,open:true,checked:item.checked,children:[],nodeSelectNotAll:false};
              this.addChild(node,item.child);
              this.primissData.push(node);
            }
        });
      },
      addChild(node,children){
        if(children.length == 0){
          return;
        }
        for(let item of children){
          let child = {id:item.resId,label:item.resName,open:true,checked:item.checked,nodeSelectNotAll:false,children:[]};
          this.addChild(child,item.child);
          node.children.push(child);
        }
      },
      okBtn(){
        let ids = this.$refs['pers_tree'].getSelectedNodeIds();
        util.ajax.put('/permission/rolePermission',{rid:this.currentRid,permissions:ids.join()})
        .then(rep => {
          if(rep.success){
            this.$Message.info('保存数据成功！');
            this.permissionModel = false;
          }else{
            this.$Message.error('保存数据失败！');
          }
        });
      },
      checkPermission(perStr){
        let str = this.$store.getters.getPerStr.perStr;
        if (str == undefined || str==='') {
          return false;
        }
        return str.indexOf(perStr) >= 0 ? true : false;
      }
    }
  }
</script>
