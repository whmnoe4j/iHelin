<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>二维码工具</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta name="description" content=""/>
    <meta name="author" content=""/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="icon" href="${request.contextPath}/favicon.ico"/>
    <link href="${request.contextPath}/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type='text/css'
          media="all"/>
    <link href="${request.contextPath}/plugins/font-awesome/css/font-awesome.css" rel="stylesheet" type='text/css'
          media="all"/>
    <link rel="stylesheet" type="text/css" href="${request.contextPath}/plugins/simditor/simditor.css"/>
    <script src='${request.contextPath}/plugins/jquery/jquery.min.js'></script>
    <script src="${request.contextPath}/plugins/layer/layer.js"></script>

    <style>
        img {
            width: auto;
            height: auto;
            max-height: 100%;
            max-width: 100%;
        }
    </style>
</head>
<body>

<!-- Static navbar -->
<nav class="navbar navbar-default navbar-static-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                    aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="javascript:void(0);">二维码生成</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li><a href="javascript:void(0);">Home</a></li>
            </ul>
        </div>
    </div>
</nav>
<div class="container">

    <div class="jumbotron">
    <#--<h1>Navbar example</h1>
    <p>This example is a quick exercise to illustrate how the default, static and fixed to top navbar work. It
        includes the responsive CSS and HTML, so it also adapts to your viewport and device.</p>
    <p>To see the difference between static and fixed top navbars, just scroll.</p>
    <p>
        <a class="btn btn-lg btn-primary" href="../../components/#navbar" role="button">View navbar docs »</a>
    </p>-->
        <div class="row">
            <div class="col-sm-5">
                <div class="form-group">
                <textarea class="form-control" id="content" name="content" placeholder="内容"
                          autofocus></textarea>
                </div>
            </div>
            <div class="col-sm-2 text-center" style="margin-top: 125px;">
                <button class="btn btn-md btn-primary" onclick="generate();">生成二维码</button>
            </div>
            <div class="col-sm-4">
                <div style="min-width:300px;min-height:300px;margin: auto;" align="center">
                    <img id="img_id" style="vertical-align: middle;width:300px;height:300px;"
                         alt="二维码生成区">
                </div>
            </div>
        </div>
    </div>
</div>

<script type='text/javascript' src='${request.contextPath}/plugins/bootstrap/js/bootstrap.min.js'></script>
<script type='text/javascript' src='${request.contextPath}/plugins/form-parsley/parsley.min.js'></script>
<script type='text/javascript' src='${request.contextPath}/js/formvalidation.js'></script>
<script type="text/javascript" src="${request.contextPath}/plugins/simditor/module.js"></script>
<script type="text/javascript" src="${request.contextPath}/plugins/simditor/hotkeys.js"></script>
<script type="text/javascript" src="${request.contextPath}/plugins/simditor/uploader.js"></script>
<script type="text/javascript" src="${request.contextPath}/plugins/simditor/simditor.js"></script>
<script type='text/javascript'>
    var simditor;

    $(function () {
        $('.tips').tooltip();

        //编辑器初始化
        simditor = new Simditor({
            textarea: $('#content'),
            toolbarFloat: false,
            toolbar: ['title', 'bold', 'italic', 'underline', 'strikethrough', 'fontScale', 'color', 'ol', 'ul',
                'blockquote', 'code', 'table', 'link', 'image', 'hr', 'indent', 'outdent', 'alignment'
            ],
            upload: {
                url: '${request.contextPath}/img_upload',
                params: null,
                fileKey: 'file',
                connectionCount: 3,
                leaveConfirm: '文件正在上传，确定要离开吗？'
            },
            defaultImage: '${request.contextPath}/img/simditor-default.png',
            pasteImage: true,
            imageButton: ['upload']
        });
    });

    var generate = function () {
        var content = $("#content").val();
        if (!content) {
            layer.alert("请输入内容！");
            return false;
        }
        var index = layer.load(1, {
            shade: [0.1, '#000']
        });
        $.post('${request.contextPath}/generate_qrcode', {content: content}, function (data) {
            layer.close(index);
            data = JSON.parse(data);
            console.log(data);
            if (data.status == 'success') {
                var prefix = 'http://source.520lyx.cn/';
                $('#img_id').attr("src", prefix + data.url);
            } else {
                layer.msg("生成失败");
            }
        });

    }
</script>
</body>
</html>
