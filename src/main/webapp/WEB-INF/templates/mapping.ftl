<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="description" content="">
    <meta name="keywords" content="Ian He">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="format-detection" content="telephone=no">
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <title>System Config</title>
    <link rel="icon" type="image/png" href="${request.contextPath}/favicon.ico">
    <link rel="stylesheet" href="${request.contextPath}/plugins/amazeui/css/amazeui.css"/>
</head>
<body>
<div class="am-container">
    <header class="am-topbar am-topbar-fixed-top">
        <div class="am-text-center">
            <strong class="am-text-xxl" style="margin-top: 8px;">System Mappings</strong>
        </div>
    </header>
    <div class="am-g" style="margin-top: 5.5rem;">
        <ol class="am-breadcrumb am-breadcrumb-slash">
            <li><a href="${request.contextPath}/index">首页</a></li>
            <li class="am-active">System Mappings</li>
        </ol>
        <dl>
            <table class="am-table am-table-bordered">
                <thead>
                <tr>
                    <th>序号</th>
                    <th>请求路径</th>
                    <th>请求方法</th>
                    <th>类名</th>
                    <th>方法名</th>
                </tr>
                </thead>
                <tbody>
                <#if mappings??>
                    <#list mappings as item>
                    <tr>
                        <td>${(item_index!0)+1}</td>
                        <td>${item.url!}</td>
                        <td>${item.method!}</td>
                        <td>${item.className!}</td>
                        <td>${item.classMethod!}</td>
                    </tr>
                    </#list>
                </#if>
                </tbody>
            </table>
        </dl>
    </div>
</div>
</body>
</html>