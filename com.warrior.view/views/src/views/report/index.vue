<style scoped lang="less">
@import '../../styles/common.less';
</style>
<template>
	<div class="layout-content">
		<div class="layout-content-main">
			<div class="layout-tools">

				<div style="display:inline;">
					<span>投诉时间：</span>
					<DatePicker size="small"
					            type="datetime"
					            format="yyyy-MM-dd HH:mm"
					            v-model="reportTime_start"
					            placeholder="选择开始日期"
					            style="width:136px;"></DatePicker>
					<DatePicker size="small"
					            type="datetime"
					            format="yyyy-MM-dd HH:mm"
					            v-model="reportTime_end"
					            placeholder="选择结束日期"
					            style="width:136px;"></DatePicker>
				</div>
				<div style="display:inline;">
					<span>处理状态：</span>
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
				<!-- <Row style="margin-top: 10px;">
                    <Col span="24">
                    <permissionButton type="primary"
                                      size="small"
                                      icon="plus"
                                      v-on:increment="addItem()"
                                      perStr="admin:report:add"
                                      text="新增"></permissionButton>
                    </Col>
                </Row> -->
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
				<Form ref="form-report"
				      :model="formInline"
				      :rules="ruleInline"
				      inline
				      style="padding-right:30px;">
					<div style="width:50%;float: left;">
						<FormItem prop="reporterName"
						          label="举报人："
						          :label-width="80">
							<Input readonly
							       v-model="formInline.reporterName"
							       placeholder="请输入"></Input>
						</FormItem>
					</div>
					<div style="width:50%;float: left;">
						<FormItem prop="defendantName"
						          label="被举报人："
						          :label-width="80">
							<Input readonly
							       v-model="formInline.defendantName"
							       placeholder="请输入"></Input>
						</FormItem>
					</div>
					<div style="width:50%;float: left;">
						<FormItem prop="reportTime"
						          label="举报时间："
						          :label-width="80">
							<Input v-model="formInline.reportTime"
							       readonly
							       placeholder="请输入"></Input>
						</FormItem>
					</div>
					<div style="clear: both;"></div>
					<Row>
						<Col span="24">
						<FormItem prop="content"
						          label="举报内容："
						          :label-width="80">
							<Input v-model="formInline.content"
							       readonly
							       type="textarea"
							       size="large"
							       placeholder="请输入"></Input>
						</FormItem>
						</Col>
					</Row>
					<Row v-if="formInline.artid!==''">
						<Col span="8"
						     style="text-align:right;">
						<FormItem style="margin-bottom: 0px;">
							<Button size="large"
							        @click="showArt">查看被举报文章</Button>
						</FormItem>
						</Col>
						<Col span="8"
						     style="text-align:right;">
						<FormItem style="margin-bottom: 0px;">
							<Button size="large"
							        @click="modelCancel">取消</Button>
						</FormItem>
						</Col>
						<Col span="8"
						     style="text-align:left;padding-left:30px;">
						<FormItem style="margin-bottom: 0px;">
							<Button type="primary"
							        size="large"
							        :loading="isSaveing"
							        @click="handleSubmit('form-report')">保存</Button>
						</FormItem>
						</Col>
					</Row>
					<Row v-if="formInline.artid===''">
						<Col span="12"
						     style="text-align:right;">
						<FormItem style="margin-bottom: 0px;">
							<Button size="large"
							        @click="modelCancel">关闭</Button>
						</FormItem>
						</Col>
						<Col span="12"
						     style="text-align:left;padding-left:30px;"
						     v-if="formInline.status<2">
						<FormItem style="margin-bottom: 0px;">
							<Button type="primary"
							        size="large"
							        :loading="isSaveing"
							        @click="handleSubmit('form-report')">{{formInline.status===0?'开始处理':formInline.status===1?'处理完成':''}}</Button>
						</FormItem>
						</Col>
					</Row>
				</Form>
				<div slot="footer"></div>
			</Modal>

			<Modal v-model="showArtModal"
			       :title="'被举报文章内容'"
			       :mask-closable="false"
			       :width="540"
			       :closable="false">
				<Form ref="form-report"
				      :model="formInline"
				      :rules="ruleInline"
				      inline
				      style="padding-right:30px;">
					<FormItem prop="artContent"
					          label="内容："
					          :label-width="80">
						<Input readonly
						       v-model="formInline.artContent"
						       placeholder="请输入"></Input>
					</FormItem>
					<FormItem v-for="item in imageCount"
					          :key="item"
					          prop="defendantName"
					          :label="'图片'+item"
					          v-if="this['img_'+item]!==''"
					          :label-width="80">
						<img v-img="{group:galleryId}"
						     class="img"
						     :src="res_domain+this['img_'+item]">
					</FormItem>
					<FormItem :key="item"
					          prop="defendantName"
					          :label="'视频'+item"
					          v-if="this.video!==''"
					          :label-width="80">
						<video-player class="vjs-custom-skin"
						              :options="playerOptions"
						              :playsinline="true"
						              @ready="playerReadied($event)"></video-player>
					</FormItem>
					<FormItem prop="reportTime"
					          label="举报时间："
					          :label-width="80">
						<Input v-model="formInline.reportTime"
						       readonly
						       placeholder="请输入"></Input>
					</FormItem>
					<div style="clear: both;"></div>
					<Row>
						<Col span="24">
						<FormItem prop="content"
						          label="举报内容："
						          :label-width="80">
							<Input v-model="formInline.content"
							       readonly
							       type="textarea"
							       size="large"
							       placeholder="请输入"></Input>
						</FormItem>
						</Col>
					</Row>
					<Row>
						<Col span="24"
						     style="text-align:center;">
						<FormItem style="margin-bottom: 0px;">
							<Button size="large"
							        @click="artModalCancel">返回</Button>
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
			showArtModal: false,
			modelTitle: '新增投诉管理',
			formInline: {
				id: 0,
				reporter: 0,
				reporterName: '',
				defendant: 0,
				defendantName: '',
				content: '',
				reportTime: '',
				processing_mode: '',
				result: '',
				artid: 0,
				img_1: '',
				img_2: '',
				img_3: '',
				img_4: '',
				img_5: '',
				img_6: '',
				img_7: '',
				img_8: '',
				img_9: '',
				video: '',
				artContent: ''
			},
			ruleInline: {

			},
			status: 'all',
			statusList: [{
				value: 'all',
				label: '全部'
			}, {
				value: '0',
				label: '待处理'
			}, {
				value: '1',
				label: '处理中'
			}, {
				value: '2',
				label: '已处理'
			}],
			columns: [
				{ title: '编号', key: 'index', type: 'index', align: 'center' },
				{ title: '举报人', key: 'reporterName', align: 'center' },
				{ title: '被举报人', key: 'defendantName', align: 'center' },
				{ title: '举报内容', key: 'content', align: 'center' },
				{					title: '举报时间', key: 'report_time', align: 'center', render: (h, params) => {
						return h('Span', {}, util.formatDate(new Date(params.row.report_time), 'yyyy-MM-dd hh:mm:ss'));
					}				},
				{					title: '处理状态', key: 'status', align: 'center', render: (h, params) => {
						return h('Span', {}, this.statusConvert(params.row.status))					}				},
				{					title: '操作', key: 'id', align: 'center', width: 180, render: (h, params) => {
						return h('div', [
							h(permissionButton, {
								props: { type: 'primary', size: 'small', perStr: 'admin:report:update', text: '详情' },
								style: { marginRight: '5px' },
								on: {									increment: () => {
										this.showModel = true;
										this.modelTitle = '投诉信息详情';
										this.addOrUpdate = 'update';
										util.ajax.get('/report/' + params.row.id)
											.then(rep => {
												if (rep.code == 0) {
													this.formInline.id = rep.data.id;
													this.formInline.reporter = rep.data.reporter;
													this.formInline.reporterName = rep.data.reporterName;
													this.formInline.defendant = rep.data.defendant;
													this.formInline.defendantName = rep.data.defendantName;
													this.formInline.content = rep.data.content;
													this.formInline.status = rep.data.status;
													this.formInline.reportTime = util.formatDate(new Date(params.row.report_time), 'yyyy-MM-dd hh:mm:ss');
													this.formInline.processing_mode = rep.data.processing_mode ? rep.data.processing_mode : '';
													this.formInline.result = rep.data.result ? rep.data.result : '';
													this.formInline.artid = rep.data.artid ? rep.data.artid : '';
													this.formInline.img_1 = rep.data.img_1 ? rep.data.img_1 : '';
													this.formInline.img_2 = rep.data.img_2 ? rep.data.img_2 : '';
													this.formInline.img_3 = rep.data.img_3 ? rep.data.img_3 : '';
													this.formInline.img_4 = rep.data.img_4 ? rep.data.img_4 : '';
													this.formInline.img_5 = rep.data.img_5 ? rep.data.img_5 : '';
													this.formInline.img_6 = rep.data.img_6 ? rep.data.img_6 : '';
													this.formInline.img_7 = rep.data.img_7 ? rep.data.img_7 : '';
													this.formInline.img_8 = rep.data.img_8 ? rep.data.img_8 : '';
													this.formInline.img_9 = rep.data.img_9 ? rep.data.img_9 : '';
													this.formInline.video = rep.data.video ? rep.data.video : '';
													this.formInline.artContent = rep.data.artContent ? rep.data.artContent : '';
												}
											});
									}								}
							}),
							// h(permissionButton, {
							// 	props: { type: 'primary', size: 'small', perStr: 'admin:report:update', text: '修改' },
							// 	style: { marginRight: '5px' },
							// 	on: {									increment: () => {
							// 			this.showModel = true;
							// 			this.modelTitle = '修改投诉管理';
							// 			this.addOrUpdate = 'update';
							// 			util.ajax.get('/report/' + params.row.id)
							// 				.then(rep => {
							// 					if (rep.code == 0) {
							// 						this.formInline.id = rep.data.id;
							// 						this.formInline.reporter = rep.data.reporter;
							// 						this.formInline.defendant = rep.data.defendant;
							// 						this.formInline.content = rep.data.content;
							// 						this.formInline.reportTime = rep.data.report_time;
							// 						this.formInline.artid = rep.data.artid;
							// 					}
							// 				});
							// 		}								}
							// }),
							// h(permissionButton, {
							// 	props: { type: 'error', size: 'small', perStr: 'admin:report:del', text: '删除' },
							// 	on: {									increment: () => {
							// 			this.delItem(params.row.id);
							// 		}								}
							// })
						]);
					}				}
			],
			reportTime_start: '',
			reportTime_end: '',
			data: [],
			total: 0,
			pageSize: 10,
			page: 1,
			galleryId: '',
			imageCount: 9,
			res_domain: 'http://120.78.220.187:8080/MWSystem/api/article/getresources?res='
		};
	},
	created () {
		this.query();
		this.galleryId = this.gen_gallery()
	},
	methods: {
		query () {
			util.ajax.get('/report/list', {
				params: {
					reportTime_start: util.formatDate(this.reportTime_start, 'yyyy-MM-dd 00:00:00'),
					reportTime_end: util.formatDate(this.reportTime_end, 'yyyy-MM-dd 23:59:59'),
					status: this.status,
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
			this.modelTitle = '新增投诉管理';
			this.addOrUpdate = 'add';
		},
		delItem (id) {
			const _this = this;
			util.confirm('确认删除当前数据？', function () {
				util.ajax.delete('/report/' + id).then(rep => {
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
			this.$refs['form-report'].resetFields();
		},
		showArt () {
			this.showArtModal = true;
		},
		artModalCancel () {
			this.showArtModal = false;
		},
		handleSubmit (name) {
			this.$refs[name].validate((valid) => {
				if (valid) {
					this.isSaveing = true;
					util.ajax.get('/report/updateStatus', {
						params: {
							id: this.formInline.id,
							status: this.formInline.status + 1,
							processing_mode: this.formInline.processing_mode,
							result: this.formInline.result
						}
					}).then(rep => {
						if (rep.code == 0) {
							this.query();
							this.$refs['form-report'].resetFields();
							this.isSaveing = false;
							this.showModel = false;
						}
					});
					// if (this.addOrUpdate === 'add') {
					// 	util.ajax.post('/report', this.formInline)
					// 		.then(rep => {
					// 			if (rep.code == 0) { util.success('保存数据成功！'); }
					// 			this.query();
					// 			this.$refs['form-report'].resetFields();
					// 			this.isSaveing = false;
					// 			this.showModel = false;
					// 		});
					// } else {
					// 	util.ajax.put('/report', this.formInline).then(rep => {
					// 		if (rep.code == 0) { util.success('保存数据成功！'); }
					// 		this.query();
					// 		this.$refs['form-report'].resetFields();
					// 		this.isSaveing = false;
					// 		this.showModel = false;
					// 	});
					// }
				}
			});
		},
		statusConvert (status) {
			let statusName = '';
			switch (status) {
				case 0:
					statusName = '待处理';
					break;
				case 1:
					statusName = '处理中';
					break;
				case 2:
					statusName = '已处理';
					break;
			}
			return statusName;
		}
	}
}
</script>
