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
    <title>Image</title>
    <link rel="icon" type="image/png" href="${request.contextPath}/favicon.png">
    <link rel="stylesheet" href="${request.contextPath}/plugins/amazeui/css/amazeui.css"/>
    <script src="${request.contextPath}/js/jquery-2.2.1.min.js"></script>
    <script src="${request.contextPath}/plugins/amazeui/js/amazeui.js"></script>
    <style>
        img {
            max-width: 100%;
            height: auto;
        }
    </style>
</head>
<body>
<div class="am-container">
    <header class="am-topbar am-topbar-fixed-top">
        <div class="am-text-center">
            <strong class="am-text-xxl" style="margin-top: 8px;">Image</strong>
        </div>
    </header>
    <div class="am-g" style="margin-top: 10px;">
        <div class="am-slider am-slider-default am-slider-carousel"
             data-am-flexslider="{itemWidth: 200, itemMargin: 5, slideshow: false}">
            <ul class="am-slides">
            <#list fileInfos as file>
                <#if file??>
                    <li data-thumb="http://resource.ianhe.me/${file.key!}">
                        <img src="http://resource.ianhe.me/${file.key!}"/>
                    </li>
                </#if>
            </#list>
            </ul>
        </div>
    </div>
</div>
</body>
</html>