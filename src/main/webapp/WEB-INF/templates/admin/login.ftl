<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="iHelin 的博客">
    <meta name="author" content="iHelin">
    <title>后台管理登录 | Ian He</title>
    <!-- BEGIN GLOBAL MANDATORY STYLES -->
    <link href="${request.contextPath}/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="${request.contextPath}/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css"/>
    <!-- END GLOBAL MANDATORY STYLES -->
    <!-- BEGIN THEME GLOBAL STYLES -->
    <link href="${request.contextPath}/css/components.min.css" rel="stylesheet" id="style_components" type="text/css"/>
    <link href="${request.contextPath}/css/plugins.min.css" rel="stylesheet" type="text/css"/>
    <!-- END THEME GLOBAL STYLES -->
    <!-- BEGIN PAGE LEVEL STYLES -->
    <link href="${request.contextPath}/css/login.css" rel="stylesheet" type="text/css"/>
    <!-- END PAGE LEVEL STYLES -->
    <link rel="shortcut icon" href="/favicon.ico"/>
</head>

<body class=" login">
<div class="menu-toggler sidebar-toggler"></div>
<!-- BEGIN LOGO -->
<div class="logo">
    <a href="index.html">
        <img src="" alt=""/></a>
</div>
<!-- END LOGO -->
<!-- BEGIN LOGIN -->
<div class="content">
    <form class="login-form" action="${request.contextPath}/admin/login" method="post">
        <input type="hidden" name="from" value="${from!}">
        <h3 class="form-title font-green">登录</h3>
        <div class="alert alert-danger display-hide">
            <button class="close" data-close="alert"></button>
            <span>请输入用户名和密码。</span>
        </div>
        <div class="form-group">
            <input class="form-control form-control-solid placeholder-no-fix" type="text" autocomplete="off"
                   placeholder="用户名" name="username" required/></div>
        <div class="form-group">
            <input class="form-control form-control-solid placeholder-no-fix" type="password" autocomplete="off"
                   placeholder="密码" name="password" required/></div>
    <#if error??>
        <p class="text-danger text-center danger">${error!}</p>
    </#if>
        <div class="form-actions">
            <button type="submit" class="btn green uppercase">Login</button>
            <label class="rememberme check">
                <input type="checkbox" name="remember" value="1"/>Remember </label>
        </div>
    </form>
    <!-- END LOGIN FORM -->
</div>
<div class="copyright"> ${.now?string('yyyy')} © Ian He.</div>
<!-- BEGIN CORE PLUGINS -->
<script src="${request.contextPath}/plugins/jquery/jquery.min.js"></script>
<script src="${request.contextPath}/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${request.contextPath}/plugins/js.cookie.min.js" type="text/javascript"></script>
<script src="${request.contextPath}/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js"
        type="text/javascript"></script>
<script src="${request.contextPath}/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
<script src="${request.contextPath}/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="${request.contextPath}/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
<!-- END CORE PLUGINS -->
<!-- BEGIN PAGE LEVEL PLUGINS -->
<script src="${request.contextPath}/plugins/jquery-validation/js/jquery.validate.min.js"
        type="text/javascript"></script>
<script src="${request.contextPath}/plugins/jquery-validation/js/additional-methods.min.js"
        type="text/javascript"></script>
<script src="${request.contextPath}/plugins/select2/js/select2.full.min.js" type="text/javascript"></script>
<!-- END PAGE LEVEL PLUGINS -->
<!-- BEGIN THEME GLOBAL SCRIPTS -->
<script src="${request.contextPath}/js/app.min.js" type="text/javascript"></script>
<!-- END THEME GLOBAL SCRIPTS -->
<script>
    var Login = function () {
        var e = function () {
            $(".login-form").validate({
                errorElement: "span",
                errorClass: "help-block",
                focusInvalid: !1,
                rules: {username: {required: !0}, password: {required: !0}, remember: {required: !1}},
                messages: {
                    username: {required: "Username is required."},
                    password: {required: "Password is required."}
                },
                invalidHandler: function (e, r) {
                    $(".alert-danger", $(".login-form")).show()
                },
                highlight: function (e) {
                    $(e).closest(".form-group").addClass("has-error")
                },
                success: function (e) {
                    e.closest(".form-group").removeClass("has-error"), e.remove()
                },
                errorPlacement: function (e, r) {
                    e.insertAfter(r.closest(".input-icon"));
                },
                submitHandler: function (e) {
                    e.submit();
                }
            }), $(".login-form input").keypress(function (e) {
                return 13 == e.which ? ($(".login-form").validate().form() && $(".login-form").submit(), !1) : void 0
            });
        };
        return {
            init: function () {
                e();
            }
        }
    }();
    $(function () {
        Login.init();
    });
</script>
</body>

</html>