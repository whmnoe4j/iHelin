<#import "admin_frame.ftl" as main>
<#assign html_other_script in main>
<script>

</script>
</#assign>
<@main.page title="文章管理">
<div id="page-heading">
    <ol class="breadcrumb">
        <li><a href="${request.contextPath}/admin/index">首页</a></li>
        <li>文章管理</li>
    </ol>
    <h1>文章管理</h1>
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
                                    <input class="input-small form-control" name="nickName" type="text"
                                           value="${nickName!}" placeholder="标题"/>
                                </div>
                                <div class="col-md-1">
                                    <button type="submit" class="btn btn-md btn-primary"><i class="fa fa-search"></i> 查询
                                    </button>
                                </div>
                            </div>
                        </form>
                        <div class="pull-right">
                            <a href="${request.contextPath}/admin/article/add" class="btn btn-md btn-success"><i
                                    class="fa fa-plus"></i> 新增</a>
                        </div>
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th class="text-center">标题</th>
                                <th class="text-center">阅读量</th>
                                <th class="text-center">创建时间</th>
                                <th class="text-center">手机</th>
                                <th class="text-center">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td class="text-center" style="vertical-align: middle;">
                                    12
                                </td>
                                <td class="text-center" style="vertical-align: middle;">
                                    <button class="btn btn-sm btn-danger-alt tips" title="删除"
                                            onclick="deleteUser(1);"><i
                                            class="fa fa-trash"></i></button>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    <#--<#import "pagination.ftl" as pager>
                    <#assign currentUrl>user_admin?nickName=${nickName!}&</#assign>
                    <@pager.pageul pagination=pagination url="${currentUrl}" />-->
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</@main.page>