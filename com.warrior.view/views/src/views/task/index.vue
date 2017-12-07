<style scoped lang="less">
@import '../../styles/common.less';
</style>
<template>
    <div class="layout-content">
        <div class="layout-content-main">
            <div class="layout-tools">
                
                <div style="display:inline;">
                    <span>任务名称：</span>
                    <Input size="small" v-model="taskName" style="width:120px;"></Input>
                </div>

                <div style="display:inline;">
                    <span>任务分组：</span>
                    <Input size="small" v-model="taskGroup" style="width:120px;"></Input>
                </div>

                <div style="display:inline;">
                    <span>任务状态：</span>
                    <Select size="small" placeholder="状态" v-model="status" style="width:120px;">
                        <Option :value="-1">全部</Option>
                        <Option v-for="item in sel_status" :key="item.dicKey" :value="item.dicKey">{{item.dicValue}}</Option>
                    </Select>
                </div>
                <div style="display:inline;">
                    <Button type="primary" size="small" icon="ios-search" @click="query">查询</Button>
                </div>
                <Row style="margin-top: 10px;">
                    <Col span="24">
                    <permissionButton type="primary" size="small" icon="plus" v-on:increment="addItem()" perStr="admin:task:add" text="新增"></permissionButton>
                    </Col>
                </Row>
            </div>
            <Table stripe :columns="columns" :data="data"></Table>
            <div style="margin: 10px;overflow: hidden">
                <div style="float: right;">
                    <Page :total="total" size="small" :page-size="pageSize" show-elevator show-sizer @on-change="pageChange" @on-page-size-change="pageSizeChange"></Page>
                </div>
            </div>
            <Modal v-model="showModel" :title="modelTitle" :mask-closable="false" :width="540" :closable="false">
                <Form ref="form-task" :model="formInline" :rules="ruleInline" inline style="padding-right:30px;">
                    <Row>
                        <Col span="12">
                            <FormItem prop="taskName" label="任务名称：" :label-width="100">
                                <Input v-model="formInline.taskName" placeholder="请输入"></Input>
                            </FormItem>
                        </Col>
                        <Col span="12">
                            <FormItem prop="taskGroup" label="任务分组：" :label-width="100">
                                <Input v-model="formInline.taskGroup" placeholder="请输入"></Input>
                            </FormItem>
                        </Col>
                    </Row>
                    <Row>
                        <Col span="12">
                            <FormItem prop="cron" label="时间表达式：" :label-width="100">
                                <Input v-model="formInline.cron" disabled placeholder="请输入">
                                    <a slot="append" @click="showTimeWin(formInline.cron)" href="javascript:void(0);">配置</a>
                                </Input>
                            </FormItem>
                        </Col>
                        <Col span="12">
                            <FormItem prop="remark" label="任务描述：" :label-width="100">
                                <Input v-model="formInline.remark" placeholder="请输入"></Input>
                            </FormItem>
                        </Col>
                    </Row>
                    <Row>
                        <Col span="24">
                            <FormItem prop="className" label="任务目标：" :label-width="100">
                                <RadioGroup v-model="targetType" @on-change="handleChange()">
                                    <Radio label="0" ><span>JobClass</span></Radio>
                                    <Radio label="1" ><span>groovy脚本</span></Radio>
                                </RadioGroup>
                                <Select size="small" placeholder="任务目标" v-model="formInline.className" v-if="targetType == 0">
                                    <Option value=""></Option>
                                    <Option v-for="item in targetClass" :key="item.value" :value="item.value">{{item.label}}</Option>
                                </Select>
                                <Input v-model="formInline.scriptName" placeholder="请输入" style="width:220px;" v-if="targetType == 1"></Input>
                            </FormItem>
                        </Col>
                    </Row>
                    <Row>
                        <Col span="12">
                            <FormItem prop="status" label="任务状态：" :label-width="100">
                                <Select size="small" placeholder="状态" v-model="formInline.status" style="width:120px;">
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
                            <Button type="primary" size="large" :loading="isSaveing" @click="handleSubmit('form-task')">保存</Button>
                        </FormItem>
                        </Col>
                    </Row>
                </Form>
                <div slot="footer"></div>
            </Modal>

            <Modal v-model="showTimeModel" title="时间表达式配置" :mask-closable="false" :width="820" :closable="false">
                <scheduleSet v-on:timeOk="timeModelOk" v-on:timeCancel="timeModelCancel" :currentCron="currentCorn"></scheduleSet>
                <div slot="footer"></div>
            </Modal>
        </div>
    </div>
</template>

<script>
    import util from '../../libs/util';
    import permissionButton from '../main_components/button'
    import scheduleSet from './scheduleSet';

    export default{
        components:{
            permissionButton,
            scheduleSet
        },
        data(){
            return {
                currentCorn:'',
                showTimeModel:false,
                addOrUpdate:'add',
                showModel:false,
                isSaveing:false,
                modelTitle:'新增定时作业',
                formInline:{
                    taskName:'',
                    taskGroup:'',
                    cron:'',
                    remark:'',
                    className:'',
                    scriptName:'',
                    status:0,
                    id:0
                },
                ruleInline:{
                    taskName: [{ required: true, message: "请填写任务名称", trigger: "blur" }],
                    cron: [{ required: true, message: "请填写时间表达式", trigger: "blur" }],
                    remark: [{ required: true, message: "请填写任务描述", trigger: "blur" }]
                },
                columns:[
                    { title:'编号',key:'index',type:'index',align:'center',width:60},
                    { title:'任务名称',key:'taskName',align:'center'},
                    { title:'任务分组',key:'taskGroup',align:'center',width:120,render:(h,params)=>{
                        return h('Span',{},params.row.taskGroup === '' ? 'defalut_group' : params.row.taskGroup);
                    }},
                    { title:'时间表达式',key:'cron',align:'center',width:120},
                    { title:'任务描述',key:'remark',align:'center'},
                    { title:'任务目标',key:'className',align:'center',width:160,render:(h,params)=>{
                        let val = params.row.className;
                        if(params.row.className == ''){
                            val = params.row.scriptName;
                        }
                        return h('Span',{},val);
                    }},
                    { title:'任务状态',key:'status',align:'center',render:(h,params)=>{
                        let val = this.sel_status[params.row.status];
                        val = val == undefined ? '' : val.dicValue;
                        let color = params.row.status === 0 ? 'green' : 'red';
                        return h('Span',{style:{color:color}},val);
                    }},
                    { title:'创建时间',key:'createTime',align:'center',width:120,render:(h,params)=>{
                        return h('Span',{},util.formatDate(new Date(params.row.createTime),'yyyy-MM-dd hh:mm:ss'));
                    }},
                    { title:'操作',key:'id',align:'center',width:180,render:(h,params)=>{
                        return h('div',[
                            h(permissionButton,{
                                props:{type:params.row.status == 0 ? 'error' : 'success',size:'small',perStr:'admin:task:del',text:params.row.status == 0 ? '暂停':'开启'},
                                style:{marginRight:'5px'},
                                on:{increment:()=>{
                                    this.pauseOrResume(params.row.id,params.row.status==0?1:0);
                                }}
                            }),
                            h(permissionButton,{
                                props:{type:'primary',size:'small',perStr:'admin:task:update',text:'修改'},
                                style:{marginRight:'5px'},
                                on:{increment:()=>{
                                    this.modelTitle = '修改定时作业';
                                    this.addOrUpdate = 'update';
                                    util.ajax.get('/task/'+params.row.id)
                                            .then(rep => {
                                                if(rep.code==0){
                                                    this.formInline.id = rep.data.id;
                                                    this.formInline.taskName = rep.data.taskName;
                                                    this.formInline.taskGroup = rep.data.taskGroup;
                                                    this.formInline.cron = rep.data.cron;
                                                    this.formInline.remark = rep.data.remark;
                                                    this.formInline.className = rep.data.className;
                                                    this.formInline.scriptName = rep.data.scriptName;
                                                    this.formInline.status = rep.data.status;
                                                    this.showModel = true;
                                                }
                                            });
                                }}
                            }),
                            h(permissionButton,{
                                props:{type:'error',size:'small',perStr:'admin:task:del',text:'删除'},
                                on:{increment:()=>{
                                    this.delItem(params.row.id);
                                }}
                            })
                        ]);
                    }}
                ],
                targetType:0,
                targetClass:[],
                sel_status:[],
                taskName:'',
                taskGroup:'',
                status:-1,
                data:[],
                total:0,
                pageSize:10,
                page:1,
                href:util.ajaxUrl+'/schedule-ui/index.html'
            };
        },
        created(){
            util.ajax.get('/dictionary/list/model/5').then(rep=>{
                if(rep.code == 0){
                    this.sel_status = rep.data;
                }
            });
            util.ajax.get('/task/jobClass').then(rep=>{
                if(rep.code == 0){
                    this.targetClass = rep.data;
                }
            });
            this.query();
        },
        methods:{
            query(){
                util.ajax.get('/task/list',{
                    params:{
                        taskName:this.taskName,
                        taskGroup:this.taskGroup,
                        status:this.status,
                        page:this.page,
                        rows:this.pageSize
                    }
                }).then(rep=>{
                    if(rep.code == 0){
                        this.data = rep.data.records;
                        this.total = rep.data.total;
                    }
                });
            },
            addItem(){
                this.showModel = true;
                this.modelTitle = '新增定时作业';
                this.addOrUpdate = 'add';
            },
            delItem(id){
                const _this = this;
                util.confirm('确认删除当前数据？',function(){
                    util.ajax.delete('/task/'+id).then(rep =>{
                    if(rep.code==0){
                        util.success('删除数据成功！');
                        if(_this.data.length == 1){
                            _this.page = _this.page - 1;
                        }
                        _this.query();
                    }else{
                        util.error('删除数据失败！');
                    }
                });
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
                this.$refs['form-task'].resetFields();
            },
            timeModelOk(data){
                this.showTimeModel = false;
                this.formInline.cron = data;
            },
            timeModelCancel(){
                this.showTimeModel = false;
            },
            handleSubmit(name){
                this.$refs[name].validate((valid) => {
                    if(valid){
                        if(this.targetType == 0){
                            if(this.formInline.className === ''){
                                util.error('请选择JobClass！');
                                return;
                            }
                        }else{
                            if(this.formInline.scriptName === ''){
                                util.error('请填写groovy脚本路径！');
                                return;
                            }
                        }
                        this.isSaveing = true;
                        if(this.addOrUpdate === 'add'){
                            util.ajax.post('/task',this.formInline)
                                    .then(rep=>{
                                        if(rep.code==0){util.success('保存数据成功！');}
                                        this.query();
                                        this.$refs['form-task'].resetFields();
                                        this.isSaveing = false;
                                        this.showModel=false;
                                    });
                        }else{
                            util.ajax.put('/task',this.formInline).then(rep=>{
                                if(rep.code==0){util.success('保存数据成功！');}
                                this.query();
                                this.$refs['form-task'].resetFields();
                                this.isSaveing = false;
                                this.showModel=false;
                            });
                        }
                    }
                });
            },
            handleChange(event){
                if(parseInt(this.targetType) === 0){
                    this.formInline.scriptName = '';
                }else{
                    this.formInline.className = '';
                }
            },
            pauseOrResume(id,status){
                util.ajax.put('/task/'+id+'/'+status).then(rep=>{
                    if(rep.code == 0){
                        util.success('操作成功！');
                        this.query();
                    }
                });
            },
            showTimeWin(cron){
                this.showTimeModel=true;
                this.currentCorn = cron;
            }
        }
    }
</script>
