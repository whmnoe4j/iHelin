<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>${article.title!''} | Ian He</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="format-detection" content="telephone=no">
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <link rel="alternate icon" type="image/png" href="{{assets}}i/favicon.png">
    <link rel="stylesheet" href="${request.contextPath}/plugins/amazeui/css/amazeui.css"/>
    <style>
        @media only screen and (min-width: 641px) {
            .am-offcanvas {
                display: block;
                position: static;
                background: none;
            }

            .am-offcanvas-bar {
                position: static;
                width: auto;
                background: none;
                -webkit-transform: translate3d(0, 0, 0);
                -ms-transform: translate3d(0, 0, 0);
                transform: translate3d(0, 0, 0);
            }

            .am-offcanvas-bar:after {
                content: none;
            }

        }

        @media only screen and (max-width: 640px) {
            .am-offcanvas-bar .am-nav > li > a {
                color: #ccc;
                border-radius: 0;
                border-top: 1px solid rgba(0, 0, 0, .3);
                box-shadow: inset 0 1px 0 rgba(255, 255, 255, .05)
            }

            .am-offcanvas-bar .am-nav > li > a:hover {
                background: #404040;
                color: #fff
            }

            .am-offcanvas-bar .am-nav > li.am-nav-header {
                color: #777;
                background: #404040;
                box-shadow: inset 0 1px 0 rgba(255, 255, 255, .05);
                text-shadow: 0 1px 0 rgba(0, 0, 0, .5);
                border-top: 1px solid rgba(0, 0, 0, .3);
                font-weight: 400;
                font-size: 75%
            }

            .am-offcanvas-bar .am-nav > li.am-active > a {
                background: #1a1a1a;
                color: #fff;
                box-shadow: inset 0 1px 3px rgba(0, 0, 0, .3)
            }

            .am-offcanvas-bar .am-nav > li + li {
                margin-top: 0;
            }
        }

        .my-head {
            margin-top: 40px;
            text-align: center;
        }

        .my-button {
            position: fixed;
            top: 0;
            right: 0;
            border-radius: 0;
        }

        .my-sidebar {
            padding-right: 0;
            border-right: 1px solid #eeeeee;
        }

        .my-footer {
            border-top: 1px solid #eeeeee;
            padding: 10px 0;
            margin-top: 10px;
            text-align: center;
        }

        img {
            width: auto;
            height: auto;
            max-height: 100%;
            max-width: 100%;
        }
    </style>
</head>
<body>
<header class="am-g my-head">
    <div class="am-u-sm-12 am-article">
        <h1 class="am-article-title">${article.title!''}</h1>
        <p class="am-article-meta">
        ${article.author!''} @ ${article.createTime?string('MMMM dd,yyyy')!''}
        </p>
    </div>
</header>
<hr class="am-article-divider"/>
<div class="am-g">
    <div class="am-u-md-10 am-u-md-push-2">
        <div class="am-g">
            <div class="am-u-sm-12 am-u-sm-centered">
                <blockquote id="summary">
                ${article.summary!''}
                </blockquote>
                <div class="am-cf am-article" id="content">
                ${article.content!''}
                </div>
                <p class="am-text-right"><span style="font-family: Georgia;">#{article.readNum!''}</span>阅</p>
                <!--PC和WAP自适应版-->
                <div id="SOHUCS" sid="${article.id!}"></div>
                <script type="text/javascript">
                    (function () {
                        var appid = 'cysWz2225';
                        var conf = '28580395836c063154ed2dbc8342f255';
                        var width = window.innerWidth || document.documentElement.clientWidth;
                        if (width < 960) {
                            window.document.write('<script id="changyan_mobile_js" charset="utf-8" type="text/javascript" src="https://changyan.sohu.com/upload/mobile/wap-js/changyan_mobile.js?client_id=' + appid + '&conf=' + conf + '"><\/script>');
                        } else {
                            var loadJs = function (d, a) {
                                var c = document.getElementsByTagName("head")[0] || document.head || document.documentElement;
                                var b = document.createElement("script");
                                b.setAttribute("type", "text/javascript");
                                b.setAttribute("charset", "UTF-8");
                                b.setAttribute("src", d);
                                if (typeof a === "function") {
                                    if (window.attachEvent) {
                                        b.onreadystatechange = function () {
                                            var e = b.readyState;
                                            if (e === "loaded" || e === "complete") {
                                                b.onreadystatechange = null;
                                                a()
                                            }
                                        }
                                    } else {
                                        b.onload = a
                                    }
                                }
                                c.appendChild(b)
                            };
                            loadJs("https://changyan.sohu.com/upload/changyan.js", function () {
                                window.changyan.api.config({appid: appid, conf: conf})
                            });
                        }
                    })();
                </script>
            </div>
        </div>
    </div>
    <div class="am-u-md-2 am-u-md-pull-10 my-sidebar">
        <div class="am-offcanvas" id="sidebar">
            <div class="am-offcanvas-bar">
                <ul class="am-nav">
                    <li><a href="#summary">摘要</a></li>
                    <li><a href="#content">原文</a></li>
                    <li><a href="#SOHUCS">读者评论</a></li>
                    <li><a href="javascript:history.go(-1)">返回</a></li>
                </ul>
            </div>
        </div>
    </div>
    <a href="#sidebar"
       class="am-btn am-btn-sm am-btn-success am-icon-bars am-show-sm-only my-button" data-am-offcanvas>
        <span class="am-sr-only">侧栏导航</span>
    </a>
</div>
<div data-am-widget="gotop" class="am-gotop am-gotop-fixed">
    <a href="#top" title="回到顶部">
        <span class="am-gotop-title">回到顶部</span>
        <i class="am-gotop-icon am-icon-chevron-up"></i>
    </a>
</div>
<footer class="my-footer" style="background: #555;color: #fff;">
    <p style="margin-top: 1.6rem;">皖ICP备16015099号<br>
        <small>Copyright © iHelin. ${.now?string('yyyy')}</small>
    </p>
</footer>

<script src="${request.contextPath}/js/jquery-2.2.1.min.js"></script>
<script src="${request.contextPath}/plugins/amazeui/js/amazeui.js"></script>
</body>
</html>
