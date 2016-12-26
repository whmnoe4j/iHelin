<#import "admin_frame.ftl" as main>
<#assign html_other_script in main>
<link rel="stylesheet" type="text/css" href="${request.contextPath}/plugins/simditor/simditor.css"/>
<script type="text/javascript" src="${request.contextPath}/plugins/simditor/module.js"></script>
<script type="text/javascript" src="${request.contextPath}/plugins/simditor/hotkeys.js"></script>
<script type="text/javascript" src="${request.contextPath}/plugins/simditor/uploader.js"></script>
<script type="text/javascript" src="${request.contextPath}/plugins/simditor/simditor.js"></script>
<style>
    img {
        width: auto;
        height: auto;
        max-height: 100%;
        max-width: 100%;
    }
</style>
<script>
    var simditor;
    var blnCheckUnload = false;
    window.onbeforeunload = function (event) {
        var tips = "本页面要求您确认您要离开 - 您输入的数据可能不会被保存？";
        if (blnCheckUnload) {
            return tips;
        }
    };

    $("#article_name_inp").change(function () {
        blnCheckUnload = true;//离开提示失效
    });

    $(function () {
        //编辑器初始化
        simditor = new Simditor({
            textarea: $('#editor_id'),
            toolbarFloat: false,
            upload: {
                url: '${request.contextPath}/imgupload',
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

    function submitForm() {
        if ($('#article_form').parsley("validate")) {
            blnCheckUnload = false;
            var index = layer.load(1, {
                shade: [0.1, '#000']
            });
            $.post("${request.contextPath}/admin/article", $("#article_form").serialize(), function (data) {
                layer.close(index);
                if (data.status == 'success') {
                    window.location.href = '${request.contextPath}/admin/article';
                } else {

                }
            });
        }
    }

</script>
</#assign>
<@main.page title="添加文章">
<div id="page-heading">
    <ol class="breadcrumb">
        <li><a href="${request.contextPath}/admin/index">首页</a></li>
        <li><a href="${request.contextPath}/admin/article">文章管理</a></li>
        <li>添加文章</li>
    </ol>
    <h1>添加文章</h1>
</div>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-primary">
                <div class="panel-body">
                    <form class="form-horizontal myform" id="article_form" method="post" data-validate="parsley">
                        <div class="row">
                            <div class="col-sm-12">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label" for="article_name_inp">文章标题</label>
                                    <div class="col-sm-8">
                                        <input type="text" name="name" id="article_name_inp" placeholder="名称"
                                               class="form-control"
                                               value=""
                                               data-required="true" data-rangelength="[1,45]"/>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-12">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label" for="editor_id">内容</label>
                                    <div class="col-sm-10">
                                        <textarea class="form-control" id="editor_id" name="detail" placeholder="内容"
                                                  autofocus></textarea>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="panel-footer">
                    <div class="row">
                        <div class="col-sm-5 col-sm-offset-5">
                            <div class="btn-toolbar">
                                <button class="btn-primary btn" type="button" onclick="submitForm()">保存</button>
                                <button class="btn-primary btn" type="button" onclick="history.back();">取消</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</@main.page>