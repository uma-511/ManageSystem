<style scoped lang="less">
@import '../../styles/common.less';
</style>
<template>
  <div class="layout-content">
    <div class="layout-content-main">
      <div class="layout-tools">

        <div style="display:inline;">
          <span>昵称：</span>
          <Input size="small"
                 v-model="nickName"
                 style="width:120px;"></Input>
        </div>
        <div style="display:inline;">
          <span>电话号码：</span>
          <Input size="small"
                 v-model="phone"
                 style="width:120px;"></Input>
        </div>
        <div style="display:inline;">
          <span>性别：</span>
          <Select v-model="gender"
                  size="small"
                  style="width:120px">
            <Option v-for="item in genderList"
                    :value="item.value"
                    :key="item.value">{{ item.label }}</Option>
          </Select>
        </div>
        <div style="display:inline;">
          <span>用户状态：</span>
          <Select v-model="status"
                  size="small"
                  style="width:120px">
            <Option v-for="item in statusList"
                    :value="item.value"
                    :key="item.value">{{ item.label }}</Option>
          </Select>
        </div>
        <div style="display:inline;">
          <Button type="primary"
                  size="small"
                  icon="ios-search"
                  @click="query">查询</Button>
        </div>
      </div>
      <Table stripe
             :columns="columns"
             :data="data"></Table>
      <div style="margin: 10px;overflow: hidden">
        <div style="float: right;">
          <Page :total="total"
                size="small"
                :page-size="pageSize"
                show-elevator
                show-sizer
                @on-change="pageChange"
                @on-page-size-change="pageSizeChange"></Page>
        </div>
      </div>
      <Modal v-model="showModel"
             :title="modelTitle"
             :mask-closable="false"
             :width="540"
             :closable="false">
        <Form ref="form-appUser"
              :model="formInline"
              :rules="ruleInline"
              inline
              style="padding-right:30px;">
          <div style="width:50%;float: left;">
            <FormItem prop="status"
                      label="用户状态:"
                      :label-width="80">
              <Select v-model="formInline.status"
                      size="small"
                      style="width:120px">
                <Option v-for="item in statusList"
                        :value="item.value"
                        :key="item.value">{{ item.label }}</Option>
              </Select>
            </FormItem>
          </div>
          <div style="width:50%;float: left;">
            <FormItem prop="level"
                      label="用户等级："
                      :label-width="80">
              <InputNumber :min="0"
                           v-model="formInline.level"
                           placeholder="请输入"></InputNumber>
            </FormItem>
          </div>
          <div style="clear: both;"></div>
          <Row>
            <Col span="12"
                 style="text-align:right;">
            <FormItem style="margin-bottom: 0px;">
              <Button size="large"
                      @click="modelCancel">取消</Button>
            </FormItem>
            </Col>
            <Col span="12"
                 style="text-align:left;padding-left:30px;">
            <FormItem style="margin-bottom: 0px;">
              <Button type="primary"
                      size="large"
                      :loading="isSaveing"
                      @click="handleSubmit('form-appUser')">保存</Button>
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
import permissionButton from '../main_components/button'

export default {
  components: {
    permissionButton
  },
  data () {
    return {
      addOrUpdate: 'add',
      showModel: false,
      isSaveing: false,
      modelTitle: '新增用户管理',
      formInline: {
        status: '',
        level: 0,
        id: 0
      },
      ruleInline: {

      },
      statusList: [{
        value: 'all',
        label: '全部'
      }, {
        value: 'normal',
        label: '正常'
      }, {
        value: 'disable',
        label: '冻结'
      }, {
        value: 'cancel',
        label: '删除'
      }],
      columns: [
        { title: '编号', key: 'index', type: 'index', align: 'center' },
        { title: '昵称', key: 'nickName', align: 'center' },
        {          title: '性别', key: 'gender', align: 'center', render: (h, params) => {
            return h('Span', {}, params.row.gender === 'man' ? '男' : '女');
          }        },
        // { title: '性别', key: 'age', align: 'center' },
        // { title: '头像', key: 'avatar', align: 'center' },
        { title: '电话号码', key: 'phone', align: 'center' },
        {          title: '用户状态', key: 'status', align: 'center', render: (h, params) => {
            return h('Span', {}, this.statusConvert(params.row.status))
          }        },
        {          title: '注册时间', key: 'createTime', align: 'center', render: (h, params) => {
            return h('Span', {}, util.formatDate(new Date(params.row.createTime), 'yyyy-MM-dd hh:mm:ss'));
          }        },
        {          title: '最后登录时间', key: 'lastLoginTime', align: 'center', render: (h, params) => {
            return h('Span', {}, util.formatDate(new Date(params.row.lastLoginTime), 'yyyy-MM-dd hh:mm:ss'));
          }        },
        { title: '地址', key: 'address', align: 'center' },
        { title: '网址', key: 'website', align: 'center' },
        { title: '个性签名', key: 'sign', align: 'center' },
        { title: '用户等级', key: 'level', align: 'center' },
        {          title: '操作', key: 'id', align: 'center', width: 180, render: (h, params) => {
            return this.initOpt(h, params);
          }
        }
      ],
      nickName: '',
      status: 'all',
      phone: '',
      gender: 'all',
      genderList: [{
        value: 'all',
        label: '全部'
      }, {
        value: 'man',
        label: '男'
      }, {
        value: 'woman',
        label: '女'
      }],
      data: [],
      total: 0,
      pageSize: 10,
      page: 1
    };
  },
  created () {
    this.query();
  },
  methods: {
    statusConvert (status) {
      let statusName = '';
      switch (status) {
        case 'normal':
          statusName = '正常';
          break;
        case 'disable':
          statusName = '停用';
          break;
        case 'cancel':
          statusName = '注销';
          break;
      }
      return statusName;
    },
    query () {
      util.ajax.get('/appUser/list', {
        params: {
          nickName: this.nickName,
          status: this.status,
          phone: this.phone,
          gender: this.gender,
          page: this.page,
          rows: this.pageSize
        }
      }).then(rep => {
        if (rep.code == 0) {
          this.data = rep.data.records;
          this.total = rep.data.total;
        }
      });
    },
    addItem () {
      this.showModel = true;
      this.modelTitle = '新增用户管理';
      this.addOrUpdate = 'add';
    },
    delItem (id) {
      const _this = this;
      util.confirm('确认删除当前用户？', function () {
        util.ajax.delete('/appUser/' + id).then(rep => {
          if (rep.code == 0) {
            util.success('删除用户成功！');
            if (_this.data.length == 1) {
              _this.page = _this.page - 1;
            }
            _this.query();
          } else {
            util.error('删除用户失败！');
          }
        });
      });
    },
    freezeUser (id) {
      const _this = this;
      util.confirm('确认冻结当前用户？', function () {
        util.ajax.get('/appUser/freezeUser/' + id).then(rep => {
          if (rep.code == 0) {
            util.success('冻结用户成功！');
            if (_this.data.length == 1) {
              _this.page = _this.page - 1;
            }
            _this.query();
          } else {
            util.error('冻结用户失败！');
          }
        });
      });
    },
    enableUser (id) {
      const _this = this;
      util.confirm('确认解冻当前用户？', function () {
        util.ajax.get('/appUser/enableUser/' + id).then(rep => {
          if (rep.code == 0) {
            util.success('解冻用户成功！');
            if (_this.data.length == 1) {
              _this.page = _this.page - 1;
            }
            _this.query();
          } else {
            util.error('解冻用户失败！');
          }
        });
      });
    },
    pageChange (page) {
      this.page = page;
      this.query();
    },
    pageSizeChange (pageSize) {
      this.pageSize = pageSize;
      this.query();
    },
    modelCancel () {
      this.showModel = false;
      this.$refs['form-appUser'].resetFields();
    },
    handleSubmit (name) {
      this.$refs[name].validate((valid) => {
        if (valid) {
          this.isSaveing = true;
          if (this.addOrUpdate === 'add') {
            util.ajax.post('/appUser', this.formInline)
              .then(rep => {
                if (rep.code == 0) { util.success('保存数据成功！'); }
                this.query();
                this.$refs['form-appUser'].resetFields();
                this.isSaveing = false;
                this.showModel = false;
              });
          } else {
            util.ajax.put('/appUser', this.formInline).then(rep => {
              if (rep.code == 0) { util.success('保存数据成功！'); }
              this.query();
              this.$refs['form-appUser'].resetFields();
              this.isSaveing = false;
              this.showModel = false;
            });
          }
        }
      });
    },
    initOpt (h, params) {
      let editBtn = h(permissionButton, {
        props: { type: 'primary', size: 'small', perStr: 'admin:appUser:update', text: '修改' },
        style: { marginRight: '5px' },
        on: {          increment: () => {
            this.showModel = true;
            this.modelTitle = '修改用户管理';
            this.addOrUpdate = 'update';
            util.ajax.get('/appUser/' + params.row.id)
              .then(rep => {
                if (rep.code == 0) {
                  this.formInline.id = rep.data.id;
                  this.formInline.status = rep.data.status;
                  this.formInline.deviceToken = rep.data.deviceToken;
                  this.formInline.level = rep.data.level;
                }
              });
          }
        }
      });
      let freezeBtn = h(permissionButton, {
        props: { type: 'error', size: 'small', perStr: 'admin:appUser:del', text: '冻结' },
        on: {
          increment: () => {
            this.freezeUser(params.row.id);
          }
        }
      });
      let delBtn = h(permissionButton, {
        props: { type: 'error', size: 'small', perStr: 'admin:appUser:del', text: '删除' },
        on: {
          increment: () => {
            this.delItem(params.row.id);
          }
        }
      });
      let enableBtn = h(permissionButton, {
        props: { type: 'success', size: 'small', perStr: 'admin:appUser:del', text: '解冻' },
        on: {
          increment: () => {
            this.enableUser(params.row.id);
          }
        }
      });
      let btns = []
      const status = params.row.status
      if (status === 'normal') {
        btns = [editBtn, freezeBtn, delBtn]
      } else if (status === 'disable') {
        btns = [editBtn, enableBtn, delBtn]
      } else if (status === 'cancel') {
        btns = [editBtn, enableBtn]
      }
      const res = btns;
      return res;
    }
  }
}
</script>
