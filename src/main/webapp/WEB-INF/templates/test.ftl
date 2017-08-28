<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="iHelin 的博客">
    <meta name="author" content="iHelin">
    <title>index | Ian He</title>
    <link rel="icon" href="${request.contextPath}/favicon.ico"/>
    <link rel="stylesheet" type="text/css" href="http://unpkg.com/iview/dist/styles/iview.css">
    <script type="text/javascript" src="http://vuejs.org/js/vue.min.js"></script>
    <script type="text/javascript" src="http://unpkg.com/iview/dist/iview.min.js"></script>
    <style scoped>
        .layout {
            border: 1px solid #d7dde4;
            background: #f5f7f9;
        }

        .layout-logo {
            width: 100px;
            height: 30px;
            background: #5b6270;
            border-radius: 3px;
            float: left;
            position: relative;
            top: 15px;
            left: 20px;
        }

        .layout-nav {
            width: 420px;
            margin: 0 auto;
        }

        .layout-content {
            min-height: 200px;
            margin: 15px;
            overflow: hidden;
            background: #fff;
            border-radius: 4px;
        }

        .layout-content-main {
            padding: 10px;
        }

        .layout-copy {
            text-align: center;
            padding: 10px 0 20px;
            color: #9ea7b4;
        }
    </style>
</head>
<body>
<div id="app">
    <div class="layout">
        <i-menu mode="horizontal" theme="light" active-name="1">
            <div class="layout-logo">
                <img src="${request.contextPath}/favicon.ico" alt="">
            </div>
            <div class="layout-nav">
                <Menu-item name="1">
                    <Icon type="ios-home"></Icon>
                    首页
                </Menu-item>
                <Menu-item name="2">
                    <Icon type="ios-paper"></Icon>
                    文章
                </Menu-item>
                <Menu-item name="3">
                    <Icon type="music-note"></Icon>
                    音乐
                </Menu-item>
                <Menu-item name="4">
                    <Icon type="paper-airplane"></Icon>
                    关于我
                </Menu-item>
            </div>
        </i-menu>
        <div class="layout-content">
            内容区域
        </div>
        <div class="layout-copy">
            Copyright &copy; iHelin ${.now?string('yyyy')}
        </div>
    </div>
</div>
<script>
    new Vue({
        el: '#app'
    })
</script>
</body>
</html>
