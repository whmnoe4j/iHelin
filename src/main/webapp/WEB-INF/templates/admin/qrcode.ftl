<#import "admin_frame.ftl" as main>
<#assign html_other_script in main>
<script>
    var app = new Vue({
        el: '#app',
        data: {
            url: '',
            content: null
        },
        methods: {
            generate: function () {
                var vm = this;
                if (this.content == "") {
                    layer.alert("请输入内容！");
                    return false;
                }
                var index = layer.load(1, {
                    shade: [0.1, '#000']
                });
                this.$http.post('${request.contextPath}/admin/qrcode/generate', {
                    content: this.content
                }, {
                    emulateJSON: true
                }).then(function (res) {
                    layer.close(index);
                    if (res.data.status == 'success') {
                        this.url = res.data.data;
                    } else {
                        layer.msg("生成失败");
                    }
                });
            }
        }
    });

</script>
</#assign>
<@main.page title="二维码">
<div id="page-heading">
    <ol class="breadcrumb">
        <li><a href="${request.contextPath}/admin/index">首页</a></li>
        <li>二维码</li>
    </ol>
    <h1>二维码</h1>
</div>
<div class="container">
    <div class="row">
        <div class="col-sm-12">
            <div class="panel panel-primary">
                <div class="panel-body">
                    <div class="col-sm-6">
                        <div class="panel panel-gray">
                            <div class="panel-body">
                                <form class="form-horizontal myform" id="product_form" method="post"
                                      data-validate="parsley">
                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">内容</label>
                                        <div class="col-sm-10">
                                            <input class="form-control" type="text" v-model="content"/>
                                        </div>
                                    </div>
                                </form>
                                <div align="center">
                                    <button class="btn btn-primary" @click="generate">生成</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-6">
                        <div style="min-width:300px;min-height:300px;margin: auto;" align="center">
                            <img :src="url"
                                 style="vertical-align: middle;width:300px;height:300px;"
                                 alt="二维码生成区">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</@main.page>