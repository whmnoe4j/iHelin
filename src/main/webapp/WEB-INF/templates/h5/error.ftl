<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1, user-scalable=no">
    <link rel="stylesheet" href="${request.contextPath}/css/weui.css"/>
    <title>错误</title>
</head>
<body>
<div class="weui_msg">
    <div class="weui_icon_area"><i class="weui_icon_warn weui_icon_msg"></i></div>
    <div class="weui_text_area">
        <h2 class="weui_msg_title">系统错误</h2>
        <p class="weui_msg_desc">${msg!''}</p>
    </div>
<#--<div class="weui_opr_area">
    <p class="weui_btn_area">
        <a href="javascript:;" class="weui_btn weui_btn_warn">确定</a>
        <a href="javascript:;" class="weui_btn weui_btn_default">取消</a>
    </p>
</div>
<div class="weui_extra_area">
    <a href="">查看详情</a>
</div>-->
</div>
</body>
</html>
