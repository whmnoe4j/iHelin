<#import "admin_frame.ftl" as main>
<#assign html_other_script in main>
<script src='${request.contextPath}/plugins/form-datepicker/js/bootstrap-datepicker.js'></script>
<script src='${request.contextPath}/plugins/form-datepicker/js/locales/bootstrap-datepicker.zh-CN.js'></script>
<script>
    var app = new Vue({
        el: '#wrap',
        data: {
            id: null,
            project: '',
            staff: #{staff.id},
            labour: null,
            bonus: null,
            date: null,
            modalTitle: ''
        },
        mounted: function () {
            $("#date").datepicker({
                minViewMode: 3,
                format: "yyyy-mm-dd",
                autoclose: true,
                todayHighlight: true,
                language: "zh-CN"
            });
        },
        methods: {
            add: function () {
                this.modalTitle = '添加活动';
                this.id = null;
                this.project = '';
                this.labour = null;
                this.bonus = null;
                this.date = null;
                $('#modal_id').modal({
                    keyboard: false
                });
            },
            saveStaff: function () {
                this.date = this.date || $('#date').val();

                if ($('#modal_form').parsley("validate")) {
                    var index = layer.load(1, {
                        shade: [0.1, '#000']
                    });
                    this.$http.post("${request.contextPath}/admin/activity", {}, {
                        params: {
                            id: this.id,
                            staff: this.staff,
                            project: this.project,
                            labour: this.labour,
                            bonus: this.bonus,
                            dateStr: this.date
                        }
                    }).then(function (res) {
                        layer.close(index);
                        if (res.data.status == 'success') {
                            window.location.reload();
                        }
                    });
                }
            },
            deleteActivity: function (id) {
                console.log(123);
                layer.confirm('确定要删除吗？', {
                    btn: ['确定', '取消']
                }, function () {
                    var index = layer.load(2, {
                        shade: [0.3, '#000']
                    });
                    Vue.http.delete("${request.contextPath}/admin/activity/" + id).then(function (res) {
                        if (res.data.status == "success") {
                            window.location.reload();
                        }
                    });
                });
            },
            edit: function (id) {
                var vm = this;
                vm.modalTitle = '编辑活动';
                vm.id = id;
                var index = layer.load(2, {
                    shade: [0.3, '#000']
                });
                Vue.http.get("${request.contextPath}/admin/activity/" + id).then(function (res) {
                    layer.close(index);
                    if (res.data.status == "success") {
                        vm.project = res.data.data.project;
                        vm.labour = res.data.data.labour;
                        vm.bonus = res.data.data.bonus;
                        var date = new Date(res.data.data.date).format('yyyy-MM-dd')
                        vm.date = date;
                        $('#date').val(date);
                        $('#modal_id').modal({
                            keyboard: false
                        });
                    }
                });
            }
        }
    });

</script>
</#assign>
<@main.page title="${staff.name!}">
<div id="page-heading">
    <ol class="breadcrumb">
        <li><a href="${request.contextPath}/admin/index">首页</a></li>
        <li>财务管理</li>
        <li>员工-${staff.name!}</li>
    </ol>
    <h1>${staff.name!}</h1>
</div>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-primary">
                <div class="panel-body">
                    <div class="row">
                        <form class="form-horizontal">
                            <div class="form-group">
                                <label class="col-sm-1 control-label"><strong>关键词:</strong></label>
                                <div class="col-sm-2">
                                    <input class="input-small form-control" name="title" type="text"
                                           value="${title!}" placeholder=""/>
                                </div>
                                <div class="col-md-1">
                                    <button type="submit" class="btn btn-md btn-primary">
                                        <i class="fa fa-search"></i> 查询
                                    </button>
                                </div>
                            </div>
                        </form>
                        <div class="pull-right">
                            <a href="javascript:void(0);" @click="add" class="btn btn-md btn-success"><i
                                    class="fa fa-plus"></i> 新增</a>
                        </div>
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th class="text-center">项目</th>
                                <th class="text-center">劳务</th>
                                <th class="text-center">奖金</th>
                                <th class="text-center">日期</th>
                                <th class="text-center">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                                <#if activities??>
                                    <#if activities?size==0>
                                    <tr>
                                        <td align="center" colspan="9">暂无任何活动</td>
                                    <tr>
                                    <#else>
                                        <#list activities as activity>
                                            <#if activity??>
                                            <tr>
                                                <td class="text-center">
                                                ${activity.project!}
                                                </td>
                                                <td class="text-center">
                                                ${activity.labour?string("0.00")}
                                                </td>
                                                <td class="text-center">
                                                ${activity.bonus?string("0.00")}
                                                </td>
                                                <td class="text-center">
                                                ${activity.date?string("yyyy-MM-dd")}
                                                </td>
                                                <td class="text-center" style="vertical-align: middle;">
                                                    <button class="btn btn-sm btn-primary-alt tips" title="编辑"
                                                            @click="edit(#{activity.id!});">
                                                        <i class="fa fa-edit"></i>
                                                    </button>
                                                    <button class="btn btn-sm btn-danger-alt tips" title="删除"
                                                            @click="deleteActivity(#{activity.id!});">
                                                        <i class="fa fa-trash"></i>
                                                    </button>
                                                </td>
                                            </tr>
                                            </#if>
                                        </#list>
                                    </#if>
                                </#if>
                            </tbody>
                        </table>
                        <#import "pagination.ftl" as pager>
                        <#assign currentUrl>?</#assign>
                        <@pager.pageul pagination=pagination url="${currentUrl}" />
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade form-group" id="modal_id" tabindex="-1" role="dialog" aria-labelledby="menuModalTitle">
    <div id="demandModalmain" class="modal-dialog" role="document" style="max-width:60%;width:auto;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true"><i
                        class="fa fa-times"></i></span></button>
                <h4 class="modal-title" id="menuModalTitle">{{modalTitle}}</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal myform" id="modal_form" data-validate="parsley">
                    <input type="hidden" v-model="id" id="id">
                    <input type="hidden" v-model="staff" id="staff">
                    <div class="form-group">
                        <label for="name" class="col-sm-3 control-label">项目</label>
                        <div class="col-sm-6">
                            <input type="text" id="name" v-model="project" class="form-control" data-maxlength="16"
                                   required="required">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="basicWage" class="col-sm-3 control-label">劳务</label>
                        <div class="col-sm-6">
                            <input type="text" v-model="labour" id="labour" class="form-control"
                                   data-type="number"
                                   required="required">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="subsidized_meals" class="col-sm-3 control-label">奖金</label>
                        <div class="col-sm-6">
                            <input type="text" v-model="bonus" id="bonus" class="form-control"
                                   data-type="number"
                                   required="required">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label" for="date">活动日期</label>
                        <div class="col-sm-6">
                            <input type="text" id="date" v-model="date" class="input-small form-control" name="start"
                            <#--data-type="date"-->
                                   required="required"
                                   placeholder="请选择"/>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="save_btn" @click="saveStaff">保存</button>
            </div>
        </div>
    </div>
</div>
</@main.page>