<#macro page title>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>${title!} - 管理后台</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta name="description" content=""/>
    <meta name="author" content=""/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="icon" href="${request.contextPath}/favicon.ico"/>
    <link href="${request.contextPath}/css/styles.css" rel="stylesheet" type='text/css' media="all"/>
    <link href="${request.contextPath}/plugins/font-awesome/css/font-awesome.css" rel="stylesheet" type='text/css'
          media="all"/>
    <script src='${request.contextPath}/plugins/jquery/jquery.min.js'></script>
    <script src="${request.contextPath}/plugins/layer/layer.js"></script>
</head>
<body>
<header class="navbar navbar-inverse navbar-fixed-top" role="banner">
    <a id="leftmenu-trigger" class="pull-left tips" data-toggle="tooltip" data-placement="bottom" title="打开/关闭侧边栏"></a>
    <div class="navbar-header pull-left">
        <a class="navbar-brand" href="${request.contextPath}/admin/index"
           style='background:url("${request.contextPath}/favicon.ico") no-repeat center center;background-size: contain'></a>
    </div>
    <ul class="nav navbar-nav pull-left">
        <li class="dropdown">
            <a href="index" class="username"><span class="hidden-xs">Ian He</span></a>
        </li>
    </ul>
    <ul class="nav navbar-nav  pull-right toolbar">
        <li id="right_dropdown" class="dropdown ">
            <a href="javascript:;" class="dropdown-toggle username"
               data-toggle="dropdown"><#if (adminUser.nickName)??>${adminUser.nickName?html}</#if> <i
                    class="fa fa-caret-down fa-scale"></i></a>
            <ul class="dropdown-menu userinfo arrow">
                <li class="username">
                    <a href="javascript:;">
                        <div class="pull-left"><h5>您好, <#if (adminUser.nickName)??>${adminUser.nickName?html}<#else>
                            未设置昵称</#if>!</h5></div>
                    </a>
                </li>
                <li class="userlinks">
                    <ul class="dropdown-menu">
                        <li><a href="${request.contextPath}/admin/logout" class="text-right">退出</a></li>
                    </ul>
                </li>
            </ul>
        </li>
    </ul>
</header>

<div id="page-container">
    <nav id="page-leftbar" role="navigation">
        <!-- BEGIN SIDEBAR MENU -->
        <ul class="acc-menu" id="sidebar">
            <li>
                <a href="${request.contextPath}/admin/index">
                    <i class="fa fa-home"></i><span>首页</span>
                </a>
            </li>
            <li>
                <a href="${request.contextPath}/admin/article"><i class="fa fa-file"></i>
                    <span>文章管理</span>
                </a>
            </li>
            <li>
                <a href="${request.contextPath}/admin/user_admin"><i class="fa fa-user"></i>
                    <span>用户管理</span>
                </a>
            </li>
            <li>
                <a href="${request.contextPath}/admin/menu_admin"><i class="fa fa-th"></i>
                    <span>自定义菜单</span>
                </a>
            </li>
            <li>
                <a href="${request.contextPath}/admin/advice"><i class="fa fa-envelope"></i>
                    <span>留言管理</span>
                </a>
            </li>
            <li>
                <a href="${request.contextPath}/admin/qrcode"><i class="fa fa-qrcode"></i>
                    <span>二维码</span>
                </a>
            </li>
            <li>
                <a href="${request.contextPath}/admin/finance"><i class="fa fa-dollar"></i>
                    <span>财务管理</span>
                </a>
            </li>
        </ul>
        <!-- END SIDEBAR MENU -->
    </nav>
    <div id="page-content">
        <div id='wrap'>
            <#nested>
        </div>
    </div>
    <footer role="contentinfo">
        <div class="clearfix">
            <ul class="list-unstyled list-inline">
                <li>Ian He &copy; ${.now?string('yyyy')}</li>
                <button class="pull-right btn btn-inverse btn-xs" id="back-to-top"
                        style="margin-top: -1px; text-transform: uppercase;"><i class="fa fa-arrow-up"></i></button>
            </ul>
        </div>
    </footer>
</div> <!-- page-container -->
<script src='${request.contextPath}/plugins/bootstrap/js/bootstrap.min.js'></script>
<script src='${request.contextPath}/js/enquire.js'></script>
<script src='${request.contextPath}/js/jquery.cookie.js'></script>
<script src='${request.contextPath}/js/jquery.touchSwipe.min.js'></script>
<script src='${request.contextPath}/js/jquery.nicescroll.min.js'></script>
<script src='${request.contextPath}/js/application.js'></script>
<script src='${request.contextPath}/plugins/form-parsley/parsley.min.js'></script>
<script src='${request.contextPath}/js/formvalidation.js'></script>
<script src="${request.contextPath}/js/vue.js"></script>
<script src="${request.contextPath}/js/vue-resource.js"></script>
<script type='text/javascript'>
    $(function () {
        $('.tips').tooltip();
    });
</script>
${html_other_script!}
</body>
</html>
</#macro>
