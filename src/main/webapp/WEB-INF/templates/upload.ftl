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
    <title>File Upload</title>
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
            <strong class="am-text-xxl" style="margin-top: 8px;">File Upload</strong>
        </div>
    </header>
    <div class="am-g">
        <ol class="am-breadcrumb am-breadcrumb-slash">
            <li><a href="${request.contextPath}/index">首页</a></li>
            <li class="am-active">File Upload</li>
        </ol>
        <form enctype="multipart/form-data" method="post" action="${request.contextPath}/upload">
            <div class="am-form-group am-form-file">
                <button type="button" class="am-btn am-btn-default am-btn-sm">
                    <i class="am-icon-cloud-upload"></i> 选择要上传的文件
                </button>
                <input type="file" name="file" id="doc-form-file">
            </div>
            <input type="submit"
                   value="上传"
                   data-am-loading="{loadingText: '努力上传中...'}"
                   class="am-btn am-btn-default am-radius btn-loading">
        </form>
        <div id="file-list"></div>
    <#if msg??>
        <p class="am-text-center">${msg!}</p>
    </#if>
    <#if src??>
        <img src="${src!}"></img>
    </#if>
    </div>
</div>
</body>
<script>
    $(function () {
        $('#doc-form-file').on('change', function () {
            var fileNames = '';
            $.each(this.files, function () {
                fileNames += '<span class="am-badge">' + this.name + '</span> ';
            });
            $('#file-list').html(fileNames);
        });
    });

    $('.btn-loading').click(function () {
        var $btn = $(this)
        $btn.button('loading');
        setTimeout(function () {
            $btn.button('reset');
        }, 5000);
    });
</script>
</html>