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
        <img src="${request.contextPath}/img/tmp.png" alt="">
    </div>
</div>
<script>
    new Vue({
        el: '#app'
    })
</script>
</body>
</html>
