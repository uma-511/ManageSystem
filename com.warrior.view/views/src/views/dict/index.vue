<style scoped>
@import "../../styles/common.less";
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
              <permissionButton type="primary" size="small" icon="plus" v-on:increment="addItem()" perStr="admin:dict:add" text="新增"></permissionButton>
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
import util from "../../libs/util";
import permissionButton from "../main_components/button";
export default {
  components: {
    permissionButton
  },
  data() {
    return {
      addOrUpdate: "add",
      showModel: false,
      isSaveing: false,
      modelTitle: "新增字典",
      formInline: {
        id: 0,
        dicValue: "",
        dicKey: "",
        dicType: 0,
        typeValue: "",
        isShow: 0,
        sort: 1
      },
      ruleInline: {
        dicValue: [{ required: true, message: "请填写字典名称", trigger: "blur" }],
        dicKey: [{ required: true, message: "请填写字典值", trigger: "blur" }],
        typeValue: [{ required: true, message: "请填写类型名称", trigger: "blur" }]
      },
      columns: [
        { title: "编号", key: "index", type: "index", align: "center" },
        { title: "字典名称", key: "dicValue", align: "center" },
        { title: "字典值", key: "dicKey", align: "center" },
        { title: "字典类型", key: "dicType", align: "center" },
        { title: "类型名称", key: "typeValue", align: "center" },
        {
          title: "操作",
          key: "id",
          align: "center",
          width: 180,
          render: (h, params) => {
            return h("div", [
              h(permissionButton, {
                props: {
                  type: "primary",
                  size: "small",
                  perStr: "admin:dict:update",
                  text: "修改"
                },
                style: { marginRight: "5px" },
                on: {
                  increment: () => {
                    this.showModel = true;
                    this.modelTitle = "修改字典";
                    this.addOrUpdate = "update";
                    util.ajax.get("/dictionary/" + params.row.id).then(rep => {
                      if (rep.code == 0) {
                        this.formInline.id = rep.data.id;
                        this.formInline.dicValue = rep.data.dicValue;
                        this.formInline.dicKey = rep.data.dicKey;
                        this.formInline.dicType = rep.data.dicType;
                        this.formInline.typeValue = rep.data.typeValue;
                        this.formInline.isShow = rep.data.isShow;
                        this.formInline.sort = rep.data.sort;
                      }
                    });
                  }
                }
              }),
              h(permissionButton, {
                props: {
                  type: "error",
                  size: "small",
                  perStr: "admin:dict:del",
                  text: "删除"
                },
                on: {
                  increment: () => {
                    this.delItem(params.row.id);
                  }
                }
              })
            ]);
          }
        }
      ],
      dictType: "",
      sel_type: [],
      data: [],
      total: 0,
      pageSize: 10,
      page: 1
    };
  },
  created() {
    util.ajax.get("/dictionary/type/list").then(rep => {
      if (rep.code == 0) {
        this.sel_type = rep.data;
      }
    });
    this.query();
  },
  methods: {
    query() {
      util.ajax
        .get("/dictionary/list", {
          params: {
            dictType: this.dictType,
            page: this.page,
            rows: this.pageSize
          }
        })
        .then(rep => {
          if (rep.code == 0) {
            this.data = rep.data.records;
            this.total = rep.data.total;
          }
        });
    },
    addItem() {
      this.showModel = true;
      this.modelTitle = "新增字典";
      this.addOrUpdate = "add";
    },
    delItem(id) {
      const _this = this;
      util.confirm("确认删除当前数据？",function(){
        util.ajax.delete("/dictionary/" + id).then(rep => {
            if (rep.code == 0) {
              util.success("删除数据成功！");
              if (_this.data.length == 1) {
                _this.page = _this.page - 1;
              }
              _this.query();
            }else{
              util.error("删除数据失败！");
            }
          });
      });
    },
    pageChange(page) {
      this.page = page;
      this.query();
    },
    pageSizeChange(pageSize) {
      this.pageSize = pageSize;
      this.query();
    },
    modelCancel() {
      this.showModel = false;
      this.$refs["form-dict"].resetFields();
    },
    handleSubmit(name) {
      this.$refs[name].validate(valid => {
        if (valid) {
          this.isSaveing = true;
          if (this.addOrUpdate === "add") {
            util.ajax.post("/dictionary", this.formInline).then(rep => {
              if (rep.code == 0) {
                util.success("保存数据成功！");
              }
              this.query();
              this.$refs["form-dict"].resetFields();
              this.isSaveing = false;
              this.showModel = false;
            });
          } else {
            util.ajax.put("/dictionary", this.formInline).then(rep => {
              if (rep.code == 0) {
                util.success("保存数据成功！");
              }
              this.query();
              this.$refs["form-dict"].resetFields();
              this.isSaveing = false;
              this.showModel = false;
            });
          }
        }
      });
    }
  }
};
</script>
