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

    $("#title").change(function () {
        blnCheckUnload = true;//离开提示失效
    });

    $(function () {
        //编辑器初始化
        simditor = new Simditor({
            textarea: $('#content'),
            toolbarFloat: false,
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

    function submitForm() {
        if ($('#article_form').parsley("validate")) {
            blnCheckUnload = false;
            var index = layer.load(1, {
                shade: [0.1, '#000']
            });
            $.ajax({
                url: '${request.contextPath}/admin/article',
                type: 'PUT',
                data: $("#article_form").serialize(),
                success: function (data) {
                    layer.close(index);
                    if (data.status == 'success') {
                        window.location.href = '${request.contextPath}/admin/article';
                    }
                }
            });
        } else {
            return false;
        }
    }

</script>
</#assign>
<@main.page title="编辑文章">
<div id="page-heading">
    <ol class="breadcrumb">
        <li><a href="${request.contextPath}/admin/index">首页</a></li>
        <li><a href="${request.contextPath}/admin/article">文章管理</a></li>
        <li>编辑文章</li>
    </ol>
    <h1>编辑文章</h1>
</div>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-primary">
                <form class="form-horizontal myform" id="article_form"
                      data-validate="parsley">
                    <input type="hidden" name="id" value="#{(article.id)!}">
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-sm-12">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label" for="title">文章标题</label>
                                    <div class="col-sm-9">
                                        <input type="text" name="title" id="title" placeholder="名称"
                                               class="form-control"
                                               value="${(article.title)!''}"
                                               data-required="true" data-rangelength="[1,100]"/>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-12">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label" for="summary">摘要</label>
                                    <div class="col-sm-9">
                                        <textarea class="form-control" id="summary" name="summary"
                                                  placeholder="摘要"
                                                  data-required="true">${(article.summary)!''}</textarea>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-12">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label" for="content">内容</label>
                                    <div class="col-sm-9">
                                        <textarea class="form-control" id="content" name="content" placeholder="内容"
                                                  autofocus>
                                        ${(article.content)!''}
                                        </textarea>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="panel-footer">
                        <div class="row">
                            <div class="col-sm-5 col-sm-offset-5">
                                <div class="btn-toolbar">
                                    <button class="btn-primary btn" type="button" onclick="submitForm();">保存</button>
                                    <button class="btn-primary btn" type="button" onclick="history.back();">取消</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</@main.page>