<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>后台登录 | Ian He</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="format-detection" content="telephone=no">
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <link rel="alternate icon" type="image/png" href="${request.contextPath}/i/favicon.png">
    <link rel="stylesheet" href="${request.contextPath}/plugins/bootstrap/css/bootstrap.min.css"/>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-sm-12">
            <h2 class="text-center">登录</h2>
            <hr>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-6 col-sm-offset-3">
            <form role="form" class="form-horizontal" action="${request.contextPath}/admin/login" method="post">
                <input type="hidden" name="from" value="${from!}">
                <div class="form-group">
                    <label for="username" class="col-sm-2 control-label">用户名</label>
                    <div class="col-sm-10">
                        <input type="text" name="username" class="form-control" id="username" placeholder="用户名" required>
                    </div>
                </div>
                <div class="form-group">
                    <label for="password" class="col-sm-2 control-label">密码</label>
                    <div class="col-sm-10">
                        <input type="password" name="password" class="form-control" id="password" placeholder="密码" required>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <div class="checkbox">
                            <label>
                                <input type="checkbox" name="remember"> 保存登录信息
                            </label>
                        </div>
                    </div>
                </div>
            <#if error??>
                <p class="text-danger text-center danger">${error!}</p>
            </#if>
                <div class="form-group">
                    <div class="col-sm-10 text-right">
                        <button type="submit" class="btn btn-primary">登录</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
