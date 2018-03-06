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
                      format="yyyy-MM-dd HH:mm"
                      v-model="createTime_start"
                      placeholder="选择开始日期"
                      style="width:136px;"></DatePicker>
          <DatePicker size="small"
                      type="datetime"
                      format="yyyy-MM-dd HH:mm"
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
        <div style="display:inline;">
          <span>热门：</span>
          <Input size="small"
                 v-model="isHot"
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
                            perStr="admin:article:add"
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
        <Form ref="form-article"
              :model="formInline"
              :rules="ruleInline"
              inline
              style="padding-right:30px;">
          <div style="width:50%;float: left;">
            <FormItem prop="likes"
                      label="点赞数："
                      :label-width="80">
              <InputNumber :min="0"
                           v-model="formInline.likes"
                           placeholder="请输入"></InputNumber>
            </FormItem>
          </div>
          <div style="width:50%;float: left;">
            <FormItem prop="reads"
                      label="浏览次数："
                      :label-width="80">
              <InputNumber :min="0"
                           v-model="formInline.reads"
                           placeholder="请输入"></InputNumber>
            </FormItem>
          </div>
          <div style="width:50%;float: left;">
            <FormItem prop="video"
                      label="视频地址："
                      :label-width="80">
              <Input v-model="formInline.video"
                     placeholder="请输入"></Input>
            </FormItem>
          </div>
          <div style="width:50%;float: left;">
            <FormItem prop="typeId"
                      label="类别id："
                      :label-width="80">
              <InputNumber :min="0"
                           v-model="formInline.typeId"
                           placeholder="请输入"></InputNumber>
            </FormItem>
          </div>
          <div style="width:50%;float: left;">
            <FormItem prop="isHot"
                      label="是否热门：热门：1，非热门：0："
                      :label-width="80">
              <Input v-model="formInline.isHot"
                     placeholder="请输入"></Input>
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
        likes: 0,
        reads: 0,
        video: '',
        typeId: 0,
        isHot: '',
        id: 0
      },
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
        { title: '', key: 'id', align: 'center' },
        { title: '用户id', key: 'uid', align: 'center' },
        { title: '', key: 'content', align: 'center' },
        { title: '纬度', key: 'lat', align: 'center' },
        { title: '经度', key: 'lng', align: 'center' },
        { title: '价格', key: 'price', align: 'center' },
        {          title: '创建时间', key: 'createTime', align: 'center', render: (h, params) => {
            return h('Span', {}, util.formatDate(new Date(params.row.createTime), 'yyyy-MM-dd hh:mm:ss'));
          }        },
        { title: '点赞数', key: 'likes', align: 'center' },
        { title: '浏览次数', key: 'reads', align: 'center' },
        { title: '类别id', key: 'typeId', align: 'center' },
        { title: '是否热门：热门：1，非热门：0', key: 'isHot', align: 'center' },
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
                          this.formInline.isHot = rep.data.isHot;
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
      isHot: '',
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
          createTime_start: util.formatDate(this.createTime_start, 'yyyy-MM-dd hh:mm'),
          createTime_end: util.formatDate(this.createTime_end, 'yyyy-MM-dd hh:mm'),
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
