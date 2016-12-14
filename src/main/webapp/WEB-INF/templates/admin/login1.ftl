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
    <link href="${request.contextPath}/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <style>
        .login-form {
            background-color: #eceff1;
            border-radius: 6px;
            padding: 24px 23px 20px;
            position: relative;
        }

        .login-form .login-field {
            font-size: 17px;
            padding-bottom: 11px;
            padding-top: 11px;
            text-indent: 3px;

        }

        .login-form .login-field-icon {
            color: #bfc9ca;
            font-size: 16px;
            position: absolute;
            right: 28px;
            top: 9px;
            -webkit-transition: 0.25s;
            -moz-transition: 0.25s;
            -o-transition: 0.25s;
            transition: 0.25s;
            -webkit-backface-visibility: hidden;
        }
    </style>
</head>
<body class="">
<div class="container">
    <div class="row">
        <div class="col-sm-12">
            <h2 class="text-center">登录</h2>
            <hr>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-6 col-sm-offset-3 col-xs-10 col-xs-offset-1 login-form">
            <form role="form" class="form-horizontal" action="${request.contextPath}/admin/login" method="post">
                <input type="hidden" name="from" value="${from!}">
                <div class="form-group control-group ">
                    <label for="username" class="col-sm-2 control-label">用户名</label>
                    <div class="col-sm-10">
                        <input type="text" name="username" class="form-control login-field" id="username"
                               placeholder="用户名" required>
                        <i class="fa fa-user login-field-icon"></i>
                    </div>
                </div>
                <div class="form-group">
                    <label for="password" class="col-sm-2 control-label">密码</label>
                    <div class="col-sm-10">
                        <input type="password" name="password" class="form-control login-field" id="password"
                               placeholder="密码" required>
                        <i class="fa fa-lock login-field-icon"></i>
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
                    <div class="col-sm-12 text-center">
                        <button type="submit" class="btn btn-primary">登录</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
