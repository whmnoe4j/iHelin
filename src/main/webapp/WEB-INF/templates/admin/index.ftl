<#import "admin_frame.ftl" as main>
<#assign html_other_script in main>
<script>
    new Vue({
        el: '#app'
    })
</script>
</#assign>
<@main.page title="首页">
<div class="layout-breadcrumb">
    <Breadcrumb>
        <Breadcrumb-item href="${request.contextPath}/admin/index">首页</Breadcrumb-item>
    </Breadcrumb>
</div>
<div class="layout-content">
    <div class="layout-content-main">
        <#if userCount??>用户数量：${userCount?c}<#else>0</#if>
    </div>
</div>
</@main.page>