<#import "admin_frame.ftl" as main>
<#assign html_other_script in main>
<style>
    .time {
        font-size: 13px;
        color: #999;
    }

    .bottom {
        margin-top: 13px;
        line-height: 12px;
    }

    .button {
        padding: 0;
        float: right;
    }

    .image {
        width: 100%;
        display: block;
    }

    .clearfix:before,
    .clearfix:after {
        display: table;
        content: "";
    }

    .clearfix:after {
        clear: both
    }
</style>
<script>
    new Vue({
        el: '#app',
        data: function () {
            return {
                files: []
            }
        },
        mounted: function () {
            this.init();
        },
        methods: {
            init: function () {
                this.$http.get('${request.contextPath}/admin/files').then(res => {
                    this.files = res.data;
                });
            },
            deleteFile: function (key) {

                this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$resource('${request.contextPath}/admin/files').delete({
                        key: key
                    }).then(res => {
                        if (res.data.status === 'success') {
                            this.init();
                            this.$message({
                                type: 'success',
                                message: '删除成功!'
                            });
                        }
                    });
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });

            }
        }
    })
</script>
</#assign>
<@main.page title="文件管理">
<el-row :gutter="10">
    <el-col :span="6" v-for="file in files" :key="file.key" v-if="file.mimeType.indexOf('image')>=0">
        <el-card :body-style="{ padding: '0px' }">
            <img :src="'http://resource.ianhe.me/'+file.key" :alt="file.key" class="image">
            <div style="padding: 14px;">
                <span>{{file.key}}</span>
                <div class="bottom clearfix">
                    <time class="time">{{ file.putTime }}</time>
                    <el-button type="text" @click="deleteFile(file.key)" class="button">删除</el-button>
                </div>
            </div>
        </el-card>
    </el-col>
</el-row>
</@main.page>