<#import "frame.ftl" as main>
<@main.page title="post">
<style>
    img {
        width: auto;
        height: auto;
        max-height: 100%;
        max-width: 100%;
    }
</style>
<!-- Set your background image for this header on the line below. -->
    <#if article??>
    <header class="intro-header" style="background-image: url('${request.contextPath}/img/post-bg.jpg')">
        <div class="container">
            <div class="row">
                <div class="col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1">
                    <div class="post-heading">
                        <h1>${article.title!''}</h1>
                        <h2 class="subheading">${article.summary!''}</h2>
                        <span class="meta">
                        Posted by
                        <a href="${request.contextPath}/index">${article.author!''}</a>
                        on ${article.createTime?string('MMMM dd,yyyy')!''}
                    </span>
                        <p><span style="font-family: Georgia;">#{article.readNum!''}</span>阅</p>
                    </div>
                </div>
            </div>
        </div>
    </header>

    <!-- Post Content -->
    <article>
        <div class="container">
            <div class="row">
                <div class="col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1">
                ${article.content!''}
                </div>
            </div>
        </div>
    </article>
    <#else>
    <blockquote>
        <p class="text-center" style="padding: 50px 0;margin-top: 0;">文章不存在 <a
                href="${request.contextPath}/index">返回首页</a></p>
    </blockquote>
    </#if>
</@main.page>
