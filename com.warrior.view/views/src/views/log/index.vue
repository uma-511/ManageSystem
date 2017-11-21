<style scoped>
@import "../../styles/common.less";
</style>
<template>
  <div class="layout-content">
      <div class="layout-content-main">
        <div class="layout-tools">
          <Row>
            <Col span="4">
              <Input v-model="keyWord" style="width:150px;" placeholder=“请输入查询关键字”></Input>
            </Col>
            <Col span="4">
              <Button type="primary" icon="ios-search" @click="query">查询</Button>
            </Col>
            <Col span="16" style="text-align:right;">
              <permissionButton type="primary" icon="close" v-on:increment="del" perStr="admin:log:del" text="删除"></permissionButton>
              <permissionButton type="primary" icon="trash-a" v-on:increment="delAll" perStr="admin:log:del" text="清空"></permissionButton>
            </Col>
          </Row>
        </div>
        <Table stripe :columns="columns" :data="data" @on-selection-change="selectionChange"></Table>
        <div style="margin: 10px;overflow: hidden">
            <div style="float: right;">
                <Page :total="total" size="small" :page-size="pageSize" show-elevator show-sizer @on-change="pageChange" @on-page-size-change="pageSizeChange"></Page>
            </div>
        </div>
      </div>
  </div>
</template>
<script>
import util from "../../libs/util";
import expandRow from "./expand-row";
import permissionButton from "../main_components/button";

export default {
  components: {
    expandRow,
    permissionButton
  },
  data() {
    return {
      keyWord: "",
      columns: [
        { type: "selection", width: 60 },
        {
          type: "expand",
          width: 40,
          render: (h, params) => {
            return h(expandRow, {
              props: { row: params.row }
            });
          }
        },
        {
          title: "编号",
          key: "index",
          type: "index",
          align: "center",
          width: 60
        },
        { title: "用户名", key: "userName", align: "center" },
        { title: "操作", key: "operation", align: "center" },
        { title: "响应时间(ms)", key: "time", align: "center" },
        { title: "IP地址", key: "ip", align: "center" },
        {
          title: "操作时间",
          key: "createTime",
          align: "center",
          render: (h, params) => {
            return h(
              "Span",
              {},
              util.formatDate(
                new Date(params.row.createTime),
                "yyyy-MM-dd hh:mm"
              )
            );
          }
        }
      ],
      data: [],
      total: 0,
      page: 1,
      pageSize: 10,
      selection: []
    };
  },
  created() {
    util.vue = this;
    this.query();
  },
  methods: {
    query() {
      util.ajax
        .get("/syslog/list", {
          params: {
            keyWord: this.keyWord,
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
    pageChange(page) {
      this.page = page;
      this.query();
    },
    pageSizeChange(pageSize) {
      this.pageSize = pageSize;
      this.query();
    },
    selectionChange(selection) {
      this.selection = selection;
    },
    del() {
      if (this.selection.length == 0) {
        return false;
      }
      this.$Modal.confirm({
        title: "操作提示",
        content: "确认删除当前所选数据？",
        onOk: () => {
          let ids = "";
          this.selection.forEach(function(val) {
            ids += val.id + "-";
          });
          ids = ids.substr(0, ids.length - 1);
          util.ajax.delete("/syslog/" + ids).then(rep => {
            this.$Message.info(rep.code == 0 ? "删除数据成功！" : "删除数据失败！");
            if (rep.code == 0) {
              if (this.data.length == 1) {
                this.page = this.page - 1;
              }
              this.query();
            }
          });
        }
      });
    },
    delAll() {
      this.$Modal.confirm({
        title: "操作提示",
        content: "确认清空数据？",
        onOk: () => {
          util.ajax.delete("/syslog/all").then(rep => {
            this.$Message.info(rep.code == 0 ? "删除数据成功！" : "删除数据失败！");
            if (rep.code == 0) {
              if (this.data.length == 1) {
                this.page = this.page - 1;
              }
              this.query();
            }
          });
        }
      });
    },
    checkPermission(perStr) {
      let str = this.$store.getters.getPerStr;
      if (str == undefined || str === "") {
        return false;
      }
      return str.indexOf(perStr) >= 0 ? true : false;
    }
  }
};
</script>
