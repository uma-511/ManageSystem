<style scoped lang="less">
@import '../../styles/common.less';
</style>
<template>
  <div class="layout-content">
    <div class="layout-content-main">
      <div class="layout-tools">
        <div style="display:inline;">
          <span>充值时间：</span>
          <DatePicker size="small"
                      type="datetime"
                      format="yyyy-MM-dd"
                      v-model="time_start"
                      placeholder="选择开始日期"
                      style="width:136px;"></DatePicker>
          <DatePicker size="small"
                      type="datetime"
                      format="yyyy-MM-dd"
                      v-model="time_end"
                      placeholder="选择结束日期"
                      style="width:136px;"></DatePicker>
        </div>
        <div style="display:inline;">
          <span>用户ID：</span>
          <Input size="small"
                 v-model="uid"
                 style="width:120px;"></Input>
        </div>
        <div style="display:inline;">
          <span>充值渠道：</span>
          <Select v-model="channel"
                  size="small"
                  style="width:120px">
            <Option v-for="item in channelList"
                    :value="item.value"
                    :key="item.value">{{ item.label }}</Option>
          </Select>
        </div>
        <div style="display:inline;">
          <span>充值状态：</span>
          <Select v-model="status"
                  size="small"
                  style="width:120px">
            <Option v-for="item in statusList"
                    :value="item.value"
                    :key="item.value">{{ item.label }}</Option>
          </Select>
        </div>
        <div style="display:inline;">
          <span>订单号：</span>
          <Input size="small"
                 v-model="outtradeno"
                 style="width:120px;"></Input>
        </div>
        <div style="display:inline;">
          <Button type="primary"
                  size="small"
                  icon="ios-search"
                  @click="query">查询</Button>
        </div>
        <Row style="margin-top: 10px;">
          <Col span="24">
          <permissionButton type="primary"
                            size="small"
                            icon="plus"
                            v-on:increment="addItem()"
                            perStr="admin:recharge:add"
                            text="新增"></permissionButton>
          </Col>
        </Row>
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
        <Form ref="form-recharge"
              :model="formInline"
              :rules="ruleInline"
              inline
              style="padding-right:30px;">
          <div style="width:50%;float: left;">
            <FormItem prop="outtradeno"
                      label="订单号："
                      :label-width="80">
              <Input v-model="formInline.outtradeno"
                     placeholder="请输入"></Input>
            </FormItem>
          </div>
          <div style="width:50%;float: left;">
            <FormItem prop="status"
                      label="订单状态"
                      :label-width="80">
              <InputNumber :min="0"
                           v-model="formInline.status"
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
                      @click="handleSubmit('form-recharge')">保存</Button>
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
      modelTitle: '新增充值管理',
      formInline: {
        outtradeno: '',
        status: 0,
        id: 0
      },
      ruleInline: {

      },
      columns: [
        { title: '编号', key: 'index', type: 'index', align: 'center' },
        { title: '用户ID', key: 'uid', align: 'center' },
        { title: '昵称', key: 'nick_name', align: 'center' },
        { title: '充值金额', key: 'money', align: 'center' },
        {          title: '充值时间', key: 'time', align: 'center', render: (h, params) => {
            return h('Span', {}, util.formatDate(new Date(params.row.time), 'yyyy-MM-dd hh:mm:ss'));
          }        },
        {          title: '充值渠道', key: 'channel', align: 'center', render: (h, params) => {
            return h('Span', {}, this.covertChannel(params.row.channel));
          }        },
        { title: '订单号', key: 'outtradeno', align: 'center' },
        {          title: '订单状态', key: 'status', align: 'center', render: (h, params) => {
            return h('Span', {}, this.statusConvert(params.row.status))
          }        }
        // {          title: '操作', key: 'id', align: 'center', width: 180, render: (h, params) => {
        //     return h('div', [
        //       h(permissionButton, {
        //         props: { type: 'primary', size: 'small', perStr: 'admin:recharge:update', text: '修改' },
        //         style: { marginRight: '5px' },
        //         on: {                  increment: () => {
        //             this.showModel = true;
        //             this.modelTitle = '修改充值管理';
        //             this.addOrUpdate = 'update';
        //             util.ajax.get('/recharge/' + params.row.id)
        //               .then(rep => {
        //                 if (rep.code == 0) {
        //                   this.formInline.id = rep.data.id;
        //                   this.formInline.outtradeno = rep.data.outtradeno;
        //                   this.formInline.status = rep.data.status;
        //                 }
        //               });
        //           }                }
        //       }),
        //       h(permissionButton, {
        //         props: { type: 'error', size: 'small', perStr: 'admin:recharge:del', text: '删除' },
        //         on: {                  increment: () => {
        //             this.delItem(params.row.id);
        //           }                }
        //       })
        //     ]);
        //   }        }
      ],
      uid: '',
      channel: 'all',
      channelList: [{
        value: 'all',
        label: '全部'
      }, {
        value: 'alipay',
        label: '支付宝'
      }, {
        value: 'wechat',
        label: '微信'
      }, {
        value: 'unionpay',
        label: '银联'
      }, {
        value: 'wallet',
        label: '钱包'
      }],
      status: 'all',
      statusList: [{
        value: 'all',
        label: '全部'
      }, {
        value: '0',
        label: '提交订单'
      }, {
        value: '1',
        label: '完成充值'
      }],
      time_start: '',
      time_end: '',
      outtradeno: '',
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
    query () {
      util.ajax.get('/recharge/list', {
        params: {
          time_start: util.formatDate(this.time_start, 'yyyy-MM-dd 00:00:00'),
          time_end: util.formatDate(this.time_end, 'yyyy-MM-dd 23:59:59'),
          uid: this.uid,
          channel: this.channel,
          status: this.status,
          outtradeno: this.outtradeno,
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
      this.modelTitle = '新增充值管理';
      this.addOrUpdate = 'add';
    },
    delItem (id) {
      const _this = this;
      util.confirm('确认删除当前数据？', function () {
        util.ajax.delete('/recharge/' + id).then(rep => {
          if (rep.code == 0) {
            util.success('删除数据成功！');
            if (this.data.length == 1) {
              this.page = this.page - 1;
            }
            this.query();
          } else {
            util.error('删除数据失败！');
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
      this.$refs['form-recharge'].resetFields();
    },
    handleSubmit (name) {
      this.$refs[name].validate((valid) => {
        if (valid) {
          this.isSaveing = true;
          if (this.addOrUpdate === 'add') {
            util.ajax.post('/recharge', this.formInline)
              .then(rep => {
                if (rep.code == 0) { util.success('保存数据成功！'); }
                this.query();
                this.$refs['form-recharge'].resetFields();
                this.isSaveing = false;
                this.showModel = false;
              });
          } else {
            util.ajax.put('/recharge', this.formInline).then(rep => {
              if (rep.code == 0) { util.success('保存数据成功！'); }
              this.query();
              this.$refs['form-recharge'].resetFields();
              this.isSaveing = false;
              this.showModel = false;
            });
          }
        }
      });
    },
    covertChannel (channel) {
      let channelName = '';
      switch (channel) {
        case 'Alipay':
          channelName = '支付宝';
          break;
        case 'Wechat':
          channelName = '微信';
          break;
        case 'UnionPay':
          channelName = '银联';
          break;
      }
      return channelName;
    },
    statusConvert (status) {
      let statusName = '';
      switch (status) {
        case 0:
          statusName = '提交订单';
          break;
        case 1:
          statusName = '完成充值';
          break;
      }
      return statusName;
    }
  }
}
</script>
