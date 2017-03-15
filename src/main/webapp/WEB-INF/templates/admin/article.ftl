<#import "admin_frame.ftl" as main>
<#assign html_other_script in main>
<script>
    function deleteArticle(id) {
        layer.confirm('确定要删除吗？', {
            btn: ['确定', '取消']
        }, function () {
            var index = layer.load(1, {
                shade: [0.1, '#000']
            });
            $.ajax({
                url: '${request.contextPath}/admin/article/' + id,
                type: 'DELETE',
                success: function (data) {
                    layer.close(index);
                    if (data.status == 'success') {
                        layer.msg("删除成功！");
                        setTimeout(function () {
                            window.location.reload();
                        }, 500);
                    } else {

                    }
                }
            });
        });
    }

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
                                    <input class="input-small form-control" name="title" type="text"
                                           value="${title!}" placeholder="标题"/>
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
                                <th class="text-center">摘要</th>
                                <th class="text-center">阅读量</th>
                                <th class="text-center">创建时间</th>
                                <th class="text-center">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                                <#if articles??>
                                    <#if articles?size==0>
                                    <tr>
                                        <td align="center" colspan="5">暂无任何文章</td>
                                    <tr>
                                    <#else>
                                        <#list articles as article>
                                        <tr>
                                            <td class="text-center">
                                                <span>${article.title!}</span>
                                            </td>
                                            <td class="text-center">
                                                <span>${article.summary!}</span>
                                            </td>
                                            <td class="text-center">
                                                <span>#{article.readNum!0}</span>
                                            </td>
                                            <td class="text-center">
                                                <#if article.createTime??>
                                                    <span>${article.createTime?string('yy-MM-dd HH:mm')}</span>
                                                </#if>
                                            </td>
                                            <td class="text-center">
                                                <a class="btn btn-sm btn-primary-alt tips" title="预览"
                                                   href="${request.contextPath}/post/#{article.id!}"
                                                   target="_blank"
                                                   type="button">
                                                    <i class="fa fa-arrow-right"></i>
                                                </a>
                                                <a class="btn btn-sm btn-primary-alt tips" title="编辑"
                                                   href="${request.contextPath}/admin/article/edit/#{article.id!}"
                                                   type="button">
                                                    <i class="fa fa-edit"></i>
                                                </a>
                                                <button class="btn btn-sm btn-danger-alt tips" title="删除"
                                                        onclick="deleteArticle(#{article.id!});"><i
                                                        class="fa fa-trash"></i></button>
                                            </td>
                                        </tr>
                                        </#list>
                                    </#if>
                                </#if>
                            </tbody>
                        </table>
                        <#import "pagination.ftl" as pager>
                        <#assign currentUrl>article?title=${title!}&</#assign>
                        <@pager.pageul pagination=pagination url="${currentUrl}" />
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</@main.page>