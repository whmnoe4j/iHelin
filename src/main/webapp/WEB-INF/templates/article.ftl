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
<div class="am-g am-g-fixed">
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
            <#--<hr/>
             <ul class="am-comments-list" id="commont">
                 <li class="am-comment">
                     <a href="#link-to-user-home">
                         <img src="http://s.amazeui.org/media/i/demos/bw-2014-06-19.jpg?imageView/1/w/96/h/96/q/80"
                              alt="" class="am-comment-avatar" width="48" height="48">
                     </a>
                     <div class="am-comment-main">
                         <header class="am-comment-hd">
                             <div class="am-comment-meta">
                                 <a href="#link-to-user" class="am-comment-author">某人</a> 评论于
                                 <time datetime="2013-07-27T04:54:29-07:00" title="2013年7月27日 下午7:54 格林尼治标准时间+0800">
                                     2014-7-12 15:30
                                 </time>
                             </div>
                         </header>
                         <div class="am-comment-bd">
                             <p>《永远的蝴蝶》一文，还吸收散文特长，多采用第一人称，淡化情节，体现一种思想寄托和艺术追求。</p>
                         </div>
                     </div>
                 </li>
                 <li class="am-comment">
                     <a href="#link-to-user-home">
                         <img src="http://s.amazeui.org/media/i/demos/bw-2014-06-19.jpg?imageView/1/w/96/h/96/q/80"
                              alt="" class="am-comment-avatar" width="48" height="48">
                     </a>
                     <div class="am-comment-main">
                         <header class="am-comment-hd">
                             <div class="am-comment-meta">
                                 <a href="#link-to-user" class="am-comment-author">路人甲</a> 评论于
                                 <time datetime="2013-07-27T04:54:29-07:00" title="2013年7月27日 下午7:54 格林尼治标准时间+0800">
                                     2014-7-13 0:03
                                 </time>
                             </div>
                         </header>
                         <div class="am-comment-bd">
                             <p>感觉仿佛是自身的遭遇一样，催人泪下</p>
                         </div>
                     </div>
                 </li>
             </ul>-->
            </div>
        </div>
    </div>
    <div class="am-u-md-2 am-u-md-pull-10 my-sidebar">
        <div class="am-offcanvas" id="sidebar">
            <div class="am-offcanvas-bar">
                <ul class="am-nav">
                    <li><a href="#summary">摘要</a></li>
                    <li><a href="#content">原文</a></li>
                    <li><a href="#commont">读者评论</a></li>
                </ul>
            </div>
        </div>
    </div>
    <a href="#sidebar" class="am-btn am-btn-sm am-btn-success am-icon-bars am-show-sm-only my-button" data-am-offcanvas><span
            class="am-sr-only">侧栏导航</span></a>
</div>

<footer class="my-footer">
    <p>皖ICP备16015099号<br>
        <small>Copyright © iHelin. ${.now?string('yyyy')}</small>
    </p>
</footer>

<script src="${request.contextPath}/js/jquery-2.2.1.min.js"></script>
<script src="${request.contextPath}/plugins/amazeui/js/amazeui.js"></script>
</body>
</html>
