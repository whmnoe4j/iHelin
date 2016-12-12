<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="iHelin 的博客">
    <meta name="author" content="iHelin">
    <title></title>
    <link href="${request.contextPath}/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${request.contextPath}/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
</head>

<body>
<div class="container">
    <div class="clearfix">
        <div class="pull-left">
            <strong class="text-primary">留言管理</strong>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-12 col-md-6">
            <div class="btn-toolbar">
                <div class="btn-group btn-group-sm">
                    <button type="button" class="btn btn-primary"><span class="icon-archive"></span> 审核
                    </button>
                    <button type="button" class="btn btn-danger"><span class="icon-trash-o"></span> 删除
                    </button>
                </div>
            </div>
        </div>
        <div class="col-sm-12 col-md-3 col-md-offset-3">
            <div class="input-group">
                <input type="text" class="form-control">
                <div class="input-group-btn">
                    <button class="btn btn-primary" type="button"><i class="fa fa-search"></i> 搜索</button>
                </div><!-- /btn-group -->
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-12">
            <div class="table-responsive">
                <table class="table">
                    <thead>
                    <tr>
                        <th class="table-check">
                            <input type="checkbox"/>
                        </th>
                        <th class="table-id">序号</th>
                        <th class="table-title">姓名</th>
                        <th class="table-type">邮箱</th>
                        <th class="table-author hide-sm-only">内容</th>
                        <th class="table-date hide-sm-only">创建日期</th>
                        <th class="table-set">操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#if advices??>
                        <#list advices as advice>
                            <#if advice??>
                            <tr>
                                <td><input type="checkbox"/></td>
                                <td>${advice_index+1}</td>
                                <td><a href="javascript:void(0);">${advice.name}</a></td>
                                <td>${advice.email}</td>
                                <td class="hide-sm-only">${advice.message}</td>
                                <td class="hide-sm-only">${advice.createTime?string("yyyy-MM-dd HH:mm:ss")}</td>
                                <td>
                                    <div class="btn-toolbar">
                                        <div class="btn-group btn-group-xs">
                                            <button class="btn btn-info"><span
                                                    class="icon-pencil-square-o"></span> 回复
                                            </button>
                                            <button class="btn btn-danger">
                                                <span class="icon-trash-o"></span> 删除
                                            </button>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                            </#if>
                        </#list>
                    </#if>
                    </tbody>
                </table>
                <div class="cf">共 ${advices?size} 条记录
                    <div class="fr">
                        <ul class="pagination">
                            <li class="disabled"><a href="#">«</a>
                            </li>
                            <li class="active"><a href="#">1</a>
                            </li>
                            <li><a href="#">2</a>
                            </li>
                            <li><a href="#">3</a>
                            </li>
                            <li><a href="#">4</a>
                            </li>
                            <li><a href="#">5</a>
                            </li>
                            <li><a href="#">»</a>
                            </li>
                        </ul>
                    </div>
                </div>
                <hr/>
            </div>
        </div>
    </div>
</div>
<!-- content end -->
</body>
</html>