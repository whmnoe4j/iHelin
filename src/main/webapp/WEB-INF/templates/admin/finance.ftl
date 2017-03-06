<#import "admin_frame.ftl" as main>
<#assign html_other_script in main>
<script>
    var app = new Vue({
        el: '#wrap',
        data: {
            id: null,
            name: '',
            basicWage: null,
            subsidizedMeals: null,
            socialSecurity: null,
            accumulationFund: null,
            other: null,
            modalTitle: '添加员工'
        },
        mounted: function () {
        },
        methods: {
            exportExcel: function () {
                console.log(111);
                window.location.href = '${request.contextPath}/admin/excel';
            },
            addStaff: function () {
                this.modalTitle = '添加员工';
                this.id = null;
                this.name = '';
                this.basicWage = null;
                this.subsidizedMeals = null;
                this.socialSecurity = null;
                this.accumulationFund = null;
                this.other = null;
                $('#modal_id').modal({
                    keyboard: false
                });
            },
            saveStaff: function () {
                if ($('#modal_form').parsley("validate")) {
                    var index = layer.load(1, {
                        shade: [0.1, '#000']
                    });
                    this.$http.post("${request.contextPath}/admin/staff",{
                        id: this.id,
                        name: this.name,
                        basicWage: this.basicWage,
                        subsidizedMeals: this.subsidizedMeals,
                        socialSecurity: this.socialSecurity,
                        accumulationFund: this.accumulationFund,
                        other: this.other
                    }).then(function (res) {
                        layer.close(index);
                        if (res.data.status == 'success') {
                            window.location.reload();
                        }
                    });
                }
            },
            deleteStaff: function (id) {
                layer.confirm('确定要删除吗？', {
                    btn: ['确定', '取消']
                }, function () {
                    var index = layer.load(2, {
                        shade: [0.3, '#000']
                    });
                    Vue.http.delete("${request.contextPath}/admin/staff/" + id).then(function (res) {
                        if (res.data.status == "success") {
                            window.location.reload();
                        }
                    });
                });
            },
            editStaff: function (id) {
                var vm = this;
                vm.modalTitle = '编辑员工';
                vm.id = id;
                var index = layer.load(2, {
                    shade: [0.3, '#000']
                });
                Vue.http.get("${request.contextPath}/admin/staff/" + id).then(function (res) {
                    layer.close(index);
                    if (res.data.status == "success") {
                        vm.name = res.data.data.name;
                        vm.basicWage = res.data.data.basicWage;
                        vm.subsidizedMeals = res.data.data.subsidizedMeals;
                        vm.socialSecurity = res.data.data.socialSecurity;
                        vm.accumulationFund = res.data.data.accumulationFund;
                        vm.other = res.data.data.other;
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
<@main.page title="财务管理">
<div id="page-heading">
    <ol class="breadcrumb">
        <li><a href="${request.contextPath}/admin/index">首页</a></li>
        <li>财务管理</li>
    </ol>
    <h1>财务管理</h1>
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
                            <button @click="exportExcel" class="btn btn-md btn-info">
                                <i class="fa fa-download"></i> Excel 导出
                            </button>
                            <button @click="addStaff" class="btn btn-md btn-success"><i
                                    class="fa fa-plus"></i> 新增
                            </button>
                        </div>
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th class="text-center">姓名</th>
                                <th class="text-center">基本工资</th>
                                <th class="text-center">餐补</th>
                                <th class="text-center">社保</th>
                                <th class="text-center">公积金</th>
                                <th class="text-center">其他</th>
                                <th class="text-center">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                                <#if staffs??>
                                    <#if staffs?size==0>
                                    <tr>
                                        <td align="center" colspan="9">暂无任何员工</td>
                                    <tr>
                                    <#else>
                                        <#list staffs as staff>
                                            <#if staff??>
                                            <tr>
                                                <td class="text-center">
                                                    <a href="${request.contextPath}/admin/finance/staff/#{staff.id!}">${staff.name!}</a>
                                                </td>
                                                <td class="text-center">
                                                ${staff.basicWage?string("0.00")}
                                                </td>
                                                <td class="text-center">
                                                ${staff.subsidizedMeals?string("0.00")}
                                                </td>
                                                <td class="text-center">
                                                ${staff.socialSecurity?string("0.00")}
                                                </td>
                                                <td class="text-center">
                                                ${staff.accumulationFund?string("0.00")}
                                                </td>
                                                <td class="text-center">
                                                ${staff.other?string("0.00")}
                                                </td>
                                                <td class="text-center" style="vertical-align: middle;">
                                                    <button class="btn btn-sm btn-primary-alt tips" title="编辑"
                                                            @click="editStaff(#{staff.id!});">
                                                        <i class="fa fa-edit"></i>
                                                    </button>
                                                    <button class="btn btn-sm btn-danger-alt tips" title="删除"
                                                            @click="deleteStaff(#{staff.id!});">
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
                        <#assign currentUrl>finance?</#assign>
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
                    <div class="form-group">
                        <label for="name" class="col-sm-3 control-label">姓名</label>
                        <div class="col-sm-6">
                            <input type="text" id="name" v-model="name" class="form-control" data-maxlength="16"
                                   required="required">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="basicWage" class="col-sm-3 control-label">基本工资</label>
                        <div class="col-sm-6">
                            <input type="text" v-model="basicWage" id="basicWage" class="form-control"
                                   data-type="number"
                                   required="required">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="subsidized_meals" class="col-sm-3 control-label">餐补</label>
                        <div class="col-sm-6">
                            <input type="text" v-model="subsidizedMeals" id="subsidized_meals" class="form-control"
                                   data-type="number"
                                   required="required">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="social_security" class="col-sm-3 control-label">社保</label>
                        <div class="col-sm-6">
                            <input type="text" v-model="socialSecurity" id="social_security" class="form-control"
                                   data-type="number"
                                   required="required">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="accumulation_fund" class="col-sm-3 control-label">公积金</label>
                        <div class="col-sm-6">
                            <input type="text" v-model="accumulationFund" id="accumulation_fund" class="form-control"
                                   data-type="number"
                                   required="required">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="other" class="col-sm-3 control-label">其他</label>
                        <div class="col-sm-6">
                            <input type="text" v-model="other" id="other" class="form-control"
                                   data-type="number"
                                   required="required">
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