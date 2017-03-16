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
    <link rel="icon" type="image/png" href="${request.contextPath}/favicon.png">
    <link rel="stylesheet" href="${request.contextPath}/plugins/amazeui/css/amazeui.css"/>
</head>
<body>
<div class="am-container">
    <header class="am-topbar am-topbar-fixed-top">
        <div class="am-text-center">
            <strong class="am-text-xxl" style="margin-top: 8px;">System Config</strong>
        </div>
    </header>
    <div class="am-g" style="margin-top: 5.5rem;">
        <ol class="am-breadcrumb am-breadcrumb-slash">
            <li><a href="${request.contextPath}/index">首页</a></li>
            <li class="am-active">System Config</li>
        </ol>
        <dl>
        <#if props??>
            <#list props?keys as key>
                <dt>${key}</dt>
                <dd class="am-scrollable-horizontal">${props[key]!''}</dd>
            </#list>
        </#if>
        </dl>
    </div>
</div>
</body>
</html>