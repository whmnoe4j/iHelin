<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>Config</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="format-detection" content="telephone=no">
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <link rel="alternate icon" type="image/png" href="${request.contextPath}/images/favicon.png">
    <link rel="stylesheet" href="${request.contextPath}/plugins/bootstrap/css/bootstrap.min.css"/>
</head>
<script src="${request.contextPath}/js/jquery-2.2.1.min.js"></script>
<body>
<header class="am-topbar am-topbar-fixed-top">
    <div class="am-g am-container am-text-center">
        <h1>Config</h1>
    </div>
</header>
<div class="am-g am-container">
    <ul class="am-list am-list-static am-list-border am-list-striped">
    <#if props??>
        <#list props?keys as key>
            <li><strong>${key}:</strong> ${props[key]!''}</li>
        </#list>
    </#if>
    </ul>
</div>
</body>
</html>