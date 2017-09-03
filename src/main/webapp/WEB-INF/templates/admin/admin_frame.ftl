<#macro page title>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>${title!} - Ian He 管理后台</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta name="description" content=""/>
    <meta name="author" content=""/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="icon" href="${request.contextPath}/favicon.ico"/>
    <link rel="stylesheet" type="text/css" href="${request.contextPath}/iview/iview.css">
    <style scoped>
        .layout {
            background: #f5f7f9;
            position: relative;
            overflow: hidden;
        }

        .layout-breadcrumb {
            padding: 10px 15px 0;
        }

        .layout-content {
            min-height: 456px;
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

        .layout-menu-left {
            background: #464c5b;
        }

        .layout-header {
            height: 60px;
            background: #fff;
            box-shadow: 0 1px 1px rgba(0, 0, 0, .1);
        }

        .layout-logo-left {
            width: 90%;
            height: 30px;
            background: #5b6270;
            border-radius: 3px;
            margin: 15px auto;
        }

        .layout-ceiling-main a {
            color: #9ba7b5;
        }

    </style>
</head>
<body>
<div id="app">
    <div class="layout">
        <Row type="flex">
            <i-col :span="4" class="layout-menu-left">
                <i-menu :active-name="active" theme="dark" width="auto" accordion>
                    <div class="layout-logo-left"></div>
                    <a href="${request.contextPath}/admin/index">
                        <Menu-item name="index">
                            <Icon type="ios-navigate" :size="14"></Icon>
                            <span class="layout-text">首页</span>
                        </Menu-item>
                    </a>
                    <Menu-group title="财务">
                        <a href="${request.contextPath}/admin/fi/customer">
                            <Menu-item name="fi/customer">
                                <Icon type="document-text" :size="14"></Icon>
                                客户管理
                            </Menu-item>
                        </a>
                        <a href="${request.contextPath}/admin/fi/summary">
                            <Menu-item name="fi/summary">
                                <Icon type="chatbubbles" :size="14"></Icon>
                                应收明细
                            </Menu-item>
                        </a>
                        <a href="${request.contextPath}/admin/fi/analysis">
                            <Menu-item name="fi/analysis">
                                <Icon type="chatbubbles" :size="14"></Icon>
                                账龄分析
                            </Menu-item>
                        </a>
                    </Menu-group>
                </i-menu>
            </i-col>
            <i-col :span="20">
                <div class="layout-header">
                </div>
                <#nested>
                <div class="layout-copy">
                    Copyright &copy; iHelin ${.now?string('yyyy')}
                </div>
            </i-col>
        </Row>
    </div>
</div>
<script src="${request.contextPath}/js/vue.js"></script>
<script src="${request.contextPath}/js/vue-resource.js"></script>
<script src="${request.contextPath}/iview/iview.js"></script>

<script type='text/javascript'>
    Vue.http.interceptors.push((request, next) => {
        iview.LoadingBar.start();
        next(res => {
            switch (res.status) {
                case 200:
                    iview.LoadingBar.finish();
                    if (res.data.status != "success") {
                        iview.Message.error(res.data.data);
                    }
                    break;
                case 403:
                    iview.Message.error("权限不足！");
                    break;
                case 404:
                    console.log("404");
                default:
                    iview.LoadingBar.error();
                    break;
            }
        });
    });

    Date.prototype.format = function (format) {
        var o = {
            "M+": this.getMonth() + 1, //month
            "d+": this.getDate(), //day
            "H+": this.getHours(), //hour
            "m+": this.getMinutes(), //minute
            "s+": this.getSeconds(), //second
            "q+": Math.floor((this.getMonth() + 3) / 3), //quarter
            "S": this.getMilliseconds() //millisecond
        }

        if (/(y+)/i.test(format)) {
            format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        }

        for (var k in o) {
            if (new RegExp("(" + k + ")").test(format)) {
                format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
            }
        }
        return format;
    }

    Vue.mixin({
        data() {
            let a = window.location.href.substr(window.location.href.indexOf('admin') + 6);
            return {
                active: a
            }
        },
        computed: {},
        methods: {}
    });
</script>
${html_other_script!}
</body>
</html>
</#macro>
