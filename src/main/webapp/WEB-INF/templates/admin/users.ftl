<#import "admin_frame.ftl" as main>
<#assign html_other_script in main>
<script>
    new Vue({
        el: '#app',
        mounted: function () {
            this.defaultActiveTag = 'users';
        }
    })
</script>
</#assign>
<@main.page title="首页">
<div>users</div>
</@main.page>