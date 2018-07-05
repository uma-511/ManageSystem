<style scoped lang="less">
@import '../../styles/common.less';
</style>
<template>
  <div class="layout-content">
    <div class="layout-content-main">
      <div class="layout-tools">

        <div style="display:inline;">
          <span>创建时间：</span>
          <DatePicker size="small"
                      type="datetime"
                      format="yyyy-MM-dd"
                      v-model="createTime_start"
                      placeholder="选择开始日期"
                      style="width:136px;"></DatePicker>
          <DatePicker size="small"
                      type="datetime"
                      format="yyyy-MM-dd"
                      v-model="createTime_end"
                      placeholder="选择结束日期"
                      style="width:136px;"></DatePicker>
        </div>
        <div style="display:inline;">
          <span>类型：</span>
          <Input size="small"
                 v-model="typeId"
                 style="width:120px;"></Input>
        </div>
        <!-- <div style="display:inline;">
          <span>热门：</span>
          <Checkbox v-model="isHot"
                    true-value=1
                    false-value=0></Checkbox>
        </div> -->

        <div style="display:inline;">
          <span>是否热门:</span>
          <Select v-model="isHot"
                  size="small"
                  style="width:120px">
            <Option v-for="item in hotList"
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
        <Form ref="form-article"
              :model="formInline"
              :rules="ruleInline"
              inline
              style="padding-right:30px;">
          <!-- <div style="width:50%;float: left;">
            <FormItem prop="likes"
                      label="点赞数："
                      :label-width="80">
              <InputNumber :min="0"
                           v-model="formInline.likes"
                           placeholder="请输入"></InputNumber>
            </FormItem>
          </div> -->
          <div style="width:50%;float: left;">
            <FormItem prop="reads"
                      label="浏览次数："
                      :label-width="80">
              <InputNumber :min="0"
                           v-model="formInline.reads"
                           placeholder="请输入"></InputNumber>
            </FormItem>
          </div>
          <!-- <div style="width:50%;float: left;">
            <FormItem prop="video"
                      label="视频地址："
                      :label-width="80">
              <Input v-model="formInline.video"
                     placeholder="请输入"></Input>
            </FormItem>
          </div> -->
          <!-- <div style="width:50%;float: left;">
            <FormItem prop="typeId"
                      label="类别id："
                      :label-width="80">
              <InputNumber :min="0"
                           v-model="formInline.typeId"
                           placeholder="请输入"></InputNumber>
            </FormItem>
          </div> -->
          <div style="width:50%;float: left;">
            <FormItem prop="hot"
                      label="是否热门"
                      :label-width="80">
              <Checkbox v-model="formInline.hot"></Checkbox>
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
                      @click="handleSubmit('form-article')">保存</Button>
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
import expandRow from './index_expand.vue';
import permissionButton from '../main_components/button'

export default {
  components: {
    permissionButton,
    expandRow
  },
  data () {
    return {
      addOrUpdate: 'add',
      showModel: false,
      isSaveing: false,
      modelTitle: '新增文章管理',
      formInline: {
        // likes: 0,
        reads: 0,
        // video: '',
        // typeId: 0,
        hot: 0,
        uid: 0,
        id: 0
      },
      hotList: [{
        value: 'all',
        label: '全部'
      }, {
        value: 'true',
        label: '是'
      }, {
        value: 'false',
        label: '否'
      }],
      ruleInline: {

      },
      columns: [
        {
          type: 'expand',
          width: 50,
          render: (h, params) => {
            return h(expandRow, {
              props: {
                row: params.row,
                res_domain: 'http://120.78.220.187:8080/MWSystem/api/article/getresources?res=',
                video: 'http://7xkwa7.media1.z0.glb.clouddn.com/sample_video_L'
              }
            })
          }
        },
        { title: '编号', key: 'index', type: 'index', align: 'center' },
        { title: '用户名', key: 'nick_name', align: 'center' },
        { title: '价格', key: 'price', align: 'center' },
        {          title: '发布时间', key: 'create_time', align: 'center', render: (h, params) => {
            return h('Span', {}, util.formatDate(new Date(params.row.create_time), 'yyyy-MM-dd hh:mm:ss'));
          }        },
        { title: '点赞数', key: 'likes', align: 'center' },
        { title: '浏览次数', key: 'reads', align: 'center' },
        { title: '类别', key: 'typeName', align: 'center' },
        {          title: '是否热门', key: 'is_hot', align: 'center', render: (h, params) => {
            return h('Span', {}, this.hot_convert(params.row.is_hot));
          }        },
        {          title: '操作', key: 'id', align: 'center', width: 180, render: (h, params) => {
            return h('div', [
              h(permissionButton, {
                props: { type: 'primary', size: 'small', perStr: 'admin:article:update', text: '修改' },
                style: { marginRight: '5px' },
                on: {                  increment: () => {
                    this.showModel = true;
                    this.modelTitle = '修改文章管理';
                    this.addOrUpdate = 'update';
                    util.ajax.get('/article/' + params.row.id)
                      .then(rep => {
                        if (rep.code == 0) {
                          this.formInline.id = rep.data.id;
                          this.formInline.likes = rep.data.likes;
                          this.formInline.reads = rep.data.reads;
                          this.formInline.video = rep.data.video;
                          this.formInline.typeId = rep.data.typeId;
                          this.formInline.uid = rep.data.uid;
                          this.formInline.hot = rep.data.hot;
                        }
                      });
                  }                }
              }),
              h(permissionButton, {
                props: { type: 'error', size: 'small', perStr: 'admin:article:del', text: '删除' },
                on: {                  increment: () => {
                    this.delItem(params.row.id);
                  }                }
              })
            ]);
          }        }
      ],
      createTime_start: '',
      createTime_end: '',
      typeId: -1,
      isHot: 'all',
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
      util.ajax.get('/article/list', {
        params: {
          createTime_start: util.formatDate(this.createTime_start, 'yyyy-MM-dd 00:00:00'),
          createTime_end: util.formatDate(this.createTime_end, 'yyyy-MM-dd 23:59:59'),
          typeId: this.typeId,
          isHot: this.isHot,
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
      this.modelTitle = '新增文章管理';
      this.addOrUpdate = 'add';
    },
    delItem (id) {
      const _this = this;
      util.confirm('确认删除当前数据？', function () {
        util.ajax.delete('/article/' + id).then(rep => {
          if (rep.code == 0) {
            util.success('删除数据成功！');
            if (_this.data.length == 1) {
              _this.page = _this.page - 1;
            }
            _this.query();
          } else {
            util.error('删除数据失败！');
          }
        });
      });
    },
    hot_convert (isHot) {
      return isHot ? '是' : '否';
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
      this.$refs['form-article'].resetFields();
    },
    handleSubmit (name) {
      this.$refs[name].validate((valid) => {
        if (valid) {
          this.isSaveing = true;
          if (this.addOrUpdate === 'add') {
            util.ajax.post('/article', this.formInline)
              .then(rep => {
                if (rep.code == 0) { util.success('保存数据成功！'); }
                this.query();
                this.$refs['form-article'].resetFields();
                this.isSaveing = false;
                this.showModel = false;
              });
          } else {
            util.ajax.put('/article', this.formInline).then(rep => {
              if (rep.code == 0) { util.success('保存数据成功！'); }
              debugger;
              this.query();
              this.$refs['form-article'].resetFields();
              this.isSaveing = false;
              this.showModel = false;
            });
          }
        }
      });
    }
  }
}
</script>
