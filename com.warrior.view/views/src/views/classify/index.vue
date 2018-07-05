<style scoped lang="less">
@import '../../styles/common.less';
</style>
<template>
	<div class="layout-content">
		<div class="layout-content-main">
			<div class="layout-tools">

				<div style="display:inline;">
					<span>分类名称：</span>
					<Input size="small"
					       v-model="name"
					       style="width:120px;"></Input>
				</div>
				<div style="display:inline;">
					<span>状态：</span>
					<Select v-model="state"
					        size="small"
					        style="width:120px">
						<Option v-for="item in stateSelectList"
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
				<Row style="margin-top: 10px;">
					<Col span="24">
					<permissionButton type="primary"
					                  size="small"
					                  icon="plus"
					                  v-on:increment="addItem()"
					                  perStr="admin:classify:add"
					                  text="新增一级分类"></permissionButton>
					</Col>
				</Row>
			</div>
			<!-- <Table stripe :columns="columns" :data="data"></Table> -->
			<zk-table ref="table"
			          sum-text="sum"
			          index-text="#"
			          :data="data"
			          :columns="columns"
			          :stripe="props.stripe"
			          :border="props.border"
			          :show-header="props.showHeader"
			          :show-summary="props.showSummary"
			          :show-row-hover="props.showRowHover"
			          :show-index="props.showIndex"
			          :tree-type="props.treeType"
			          :is-fold="props.isFold"
			          :expand-type="props.expandType"
			          :selection-type="props.selectionType">
				<template slot="state"
				          scope="scope">
					{{scope.row.state==='1'?'启用':'停用'}}
				</template>
				<template slot="opt"
				          scope="scope">
					<permissionButton type="primary"
					                  size="small"
					                  icon="plus"
					                  v-on:increment="addSub(scope.row)"
					                  perStr="admin:classify:add"
					                  text="新增子类"></permissionButton>
					<permissionButton type="primary"
					                  size="small"
					                  icon="edit"
					                  v-on:increment="edit(scope.row)"
					                  perStr="admin:classify:update"
					                  text="编辑"></permissionButton>
					<permissionButton type="error"
					                  size="small"
					                  icon="android-delete"
					                  v-on:increment="delItem(scope.row.id)"
					                  perStr="admin:classify:del"
					                  text="删除"></permissionButton>
				</template>
			</zk-table>
			<!-- <div style="margin: 10px;overflow: hidden">
				<div style="float: right;">
					<Page :total="total"
					      size="small"
					      :page-size="pageSize"
					      show-elevator
					      show-sizer
					      @on-change="pageChange"
					      @on-page-size-change="pageSizeChange"></Page>
				</div>
			</div> -->
			<Modal v-model="showModel"
			       :title="modelTitle"
			       :mask-closable="false"
			       :width="540"
			       :closable="false"
			       @on-visible-change="resetForm">
				<Form ref="form-classify"
				      :model="formInline"
				      :rules="ruleInline"
				      inline
				      style="padding-right:30px;">
					<div style="width:50%;float: left;">
						<FormItem v-if="formInline.pname!==''"
						          label="父级分类："
						          :label-width="80">
							<Input v-model="formInline.pname"
							       readonly></Input>
						</FormItem>
					</div>
					<div style="width:50%;float: left;">
						<FormItem prop="name"
						          label="分类名称："
						          :label-width="80">
							<Input v-model="formInline.name"
							       placeholder="请输入"></Input>
						</FormItem>
					</div>
					<div style="width:50%;float: left;">
						<FormItem prop="idc"
						          label="拼音简写："
						          :label-width="80">
							<Input v-model="formInline.idc"
							       placeholder="请输入"></Input>
						</FormItem>
					</div>
					<div style="width:50%;float: left;">
						<FormItem prop="letters"
						          label="首字母："
						          :label-width="80">
							<Input v-model="formInline.letters"
							       :maxlength=1
							       placeholder="请输入"></Input>
						</FormItem>
					</div>
					<div style="width:50%;float: left;">
						<FormItem prop="yinyin"
						          label="拼音："
						          :label-width="80">
							<Input v-model="formInline.yinyin"
							       placeholder="请输入"></Input>
						</FormItem>
					</div>
					<div style="width:50%;float: left;">
						<FormItem prop="state"
						          label="状态"
						          :label-width="80">
							<Select v-model="formInline.state"
							        size="small"
							        style="width:120px">
								<Option v-for="item in stateList"
								        :value="item.value"
								        :key="item.value">{{ item.label }}</Option>
							</Select>
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
							        @click="handleSubmit('form-classify')">保存</Button>
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
import util from '../../libs/util'
import permissionButton from '../main_components/button'

export default {
	components: {
		permissionButton
	},
	data () {
		return {
			props: {
				stripe: true,
				border: true,
				showHeader: true,
				showSummary: false,
				showRowHover: true,
				showIndex: false,
				treeType: true,
				isFold: true,
				expandType: false,
				selectionType: false,
			},
			addOrUpdate: 'add',
			showModel: false,
			isSaveing: false,
			modelTitle: '新增分类管理',
			formInline: {
				pid: 0,
				name: '',
				idc: '',
				letters: '',
				yinyin: '',
				state: '1',
				id: 0,
				pname: ''
			},
			ruleInline: {

			},
			columns: [
				{ label: '编号', prop: 'id', type: 'index', align: 'center', headerAlign: 'center' },
				{ label: '父级id', prop: 'pid', align: 'center', headerAlign: 'center' },
				{ label: '分类名称', prop: 'name', align: 'center', headerAlign: 'center' },
				{ label: '拼音简写', prop: 'idc', align: 'center', headerAlign: 'center' },
				{ label: '首字母', prop: 'letters', align: 'center', headerAlign: 'center' },
				{ label: '拼音', prop: 'yinyin', align: 'center', headerAlign: 'center', headerAlign: 'center' },
				{					label: '状态', prop: 'state', align: 'center', headerAlign: 'center', type: 'template',
					template: 'state'				},
				{ label: '操作', align: 'center', headerAlign: 'center', width: 100, type: 'template', template: 'opt' }
				// {					label: '操作', align: 'center', headerAlign: 'center', width: 180
				// , render: (h, params) => {
				// 		return h('div', [
				// 			h(permissionButton, {
				// 				props: { type: 'primary', size: 'small', perStr: 'admin:classify:update', text: '修改' },
				// 				style: { marginRight: '5px' },
				// 				on: {									increment: () => {
				// 						this.showModel = true;
				// 						this.modelTitle = '修改分类管理';
				// 						this.addOrUpdate = 'update';
				// 						util.ajax.get('/classify/' + params.row.id)
				// 							.then(rep => {
				// 								if (rep.code == 0) {
				// 									this.formInline.id = rep.data.id;
				// 									this.formInline.pid = rep.data.pid;
				// 									this.formInline.name = rep.data.name;
				// 									this.formInline.idc = rep.data.idc;
				// 									this.formInline.letters = rep.data.letters;
				// 									this.formInline.yinyin = rep.data.yinyin;
				// 									this.formInline.state = rep.data.state;
				// 								}
				// 							});
				// 					}								}
				// 			}),
				// 			h(permissionButton, {
				// 				props: { type: 'error', size: 'small', perStr: 'admin:classify:del', text: '删除' },
				// 				on: {									increment: () => {
				// 						this.delItem(params.row.id);
				// 					}								}
				// 			})
				// 		]);
				// 	}				
				// }
			],
			name: '',
			state: 'all',
			data: [],
			stateList: [{
				value: '1',
				label: '启用'
			}, {
				value: '0',
				label: '停用'
			}],
			stateSelectList: [{
				value: 'all',
				label: '全部'
			}, {
				value: '1',
				label: '启用'
			}, {
				value: '0',
				label: '停用'
			}]
		};
	},
	created () {
		this.query();
	},
	methods: {
		edit (row) {
			this.showModel = true;
			this.modelTitle = '修改分类';
			this.addOrUpdate = 'update';
			var _this = this;
			_this.formInline.id = row.id;
			_this.formInline.pid = row.pid;
			_this.formInline.name = row.name;
			_this.formInline.idc = row.idc;
			_this.formInline.letters = row.letters;
			_this.formInline.yinyin = row.yinyin;
			_this.formInline.state = row.state;
		},
		addSub (row) {
			this.showModel = true;
			this.modelTitle = '新增子分类';
			this.addOrUpdate = 'add';
			this.formInline.id = 0;
			this.formInline.pid = row.id;
			this.formInline.pname = row.name;
		},
		query () {
			util.ajax.get('/classify/tree', {
				params: {
					name: this.name,
					state: this.state
				}
			}).then(rep => {
				if (rep.code == 0) {
					this.data = rep.data;
					this.total = rep.data.length;
				}
			});
		},
		addItem () {
			this.showModel = true;
			this.modelTitle = '新增分类';
			this.addOrUpdate = 'add';
		},
		resetForm (res) {
			if (!res) {
				this.formInline = {
					pid: 0,
					name: '',
					idc: '',
					letters: '',
					yinyin: '',
					state: '1',
					id: 0,
					pname: ''
				};
			}
		},
		delItem (id) {
			const _this = this;
			util.confirm('确认删除当前数据？', function () {
				util.ajax.delete('/classify/' + id).then(rep => {
					if (rep.code == 0) {
						util.success('删除数据成功！');
						_this.query();
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
			this.$refs['form-classify'].resetFields();
		},
		handleSubmit (name) {
			this.$refs[name].validate((valid) => {
				if (valid) {
					this.isSaveing = true;
					if (this.addOrUpdate === 'add') {
						util.ajax.post('/classify', this.formInline)
							.then(rep => {
								if (rep.code == 0) { util.success('保存数据成功！'); }
								this.query();
								this.$refs['form-classify'].resetFields();
								this.isSaveing = false;
								this.showModel = false;
								// this.resetForm();
							});
					} else {
						util.ajax.put('/classify', this.formInline).then(rep => {
							if (rep.code == 0) { util.success('保存数据成功！'); }
							this.query();
							this.$refs['form-classify'].resetFields();
							this.isSaveing = false;
							this.showModel = false;
							// this.resetForm();
						});
					}
				}
			});
		}
	}
}
</script>
