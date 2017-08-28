<#import "admin_frame.ftl" as main>
<#assign html_other_script in main>
<script>
    new Vue({
        el: '#app',
        data: {
            visible: false,
            customers: [],
            pageNum: 1,
            pageSize: 10,
            customerName: '',
            total: 0,
            columns: [
                {
                    title: '客户名称',
                    key: 'customerName',
                    align: 'center'
                },
                {
                    title: '信用天数',
                    key: 'creditDays',
                    align: 'center'
                },
                {
                    title: '操作',
                    key: 'action',
                    width: 150,
                    align: 'center',
                    render: (h, params) => {
                        return h('div', [
                            h('Button', {
                                props: {
                                    type: 'primary',
                                    size: 'small'
                                },
                                style: {
                                    marginRight: '5px'
                                },
                                on: {
                                    click: () => {
                                        console.log(params)
                                    }
                                }
                            }, '编辑'),
                            h('Button', {
                                props: {
                                    type: 'error',
                                    size: 'small'
                                },
                                on: {
                                    click: () => {
                                        console.log(params)
                                    }
                                }
                            }, '删除')
                        ]);
                    }
                }
            ],
            company: {
                customerName: '',
                creditDays: 30
            },
            ruleValidate: {
                customerName: [
                    {required: true, message: '客户名称不能为空', trigger: 'blur'}
                ]
            }
        },
        mounted(){
            this.init();
        },
        methods: {
            init(pageNum){
                if (pageNum) {
                    this.pageNum = pageNum;
                }
                this.$http.get("${request.contextPath}/admin/fi/customers", {
                    params: {
                        customerName: this.customerName,
                        pageNum: this.pageNum,
                        pageSize: this.pageSize
                    }
                }).then(res => {
                    this.customers = res.data.data;
                    this.total = res.data.total;
                });
            },
            addCustomer(){
                this.visible = true;
                this.$nextTick(() => {
                    this.handleReset();
                });
            },
            handleAdd () {
                this.$refs['formValidate'].validate((valid) => {
                    if (valid) {
                        this.$http.post("${request.contextPath}/admin/fi/customer", {
                            customerName: this.company.customerName,
                            creditDays: this.company.creditDays
                        }, {emulateJSON: true}).then(res => {
                            if (res.data.status == 'success') {
                                this.$Message.success('保存成功!');
                                this.init();
                                this.visible = false;
                            }
                        });
                    }
                });
            },
            handleReset () {
                this.$refs['formValidate'].resetFields();
            }
        }
    });
</script>
</#assign>
<@main.page title="客户管理">
<div class="layout-breadcrumb">
    <Breadcrumb>
        <Breadcrumb-item href="${request.contextPath}/admin/index">首页</Breadcrumb-item>
        <Breadcrumb-item href="javascript:void(0);">财务</Breadcrumb-item>
        <Breadcrumb-item>客户管理</Breadcrumb-item>
    </Breadcrumb>
</div>
<div class="layout-content">
    <div class="layout-content-main">
        <Row>
            <i-input placeholder="请输入客户名称" v-model="customerName" style="width: 300px"
                     @keyup.enter.native="init(1)"></i-input>
            <i-button @click="init(1)">查询</i-button>
            <i-button type="success" @click="addCustomer" style="float: right;">新增</i-button>
        </Row>
        <Row style="margin-top: 5px">
            <i-table stripe :columns="columns" :data="customers"></i-table>
        </Row>
        <Page :current="pageNum"
              :page-size="pageSize"
              :total="total"
              show-total
              style="margin-top: 5px;"
              @on-change="init"></Page>
        <Modal v-model="visible"
               @on-ok="handleAdd()"
               title="添加客户" ok-text="提交" :loading="true">
            <i-form ref="formValidate" :model="company" :rules="ruleValidate" :label-width="80">
                <Form-item label="客户名称" prop="customerName">
                    <i-input v-model="company.customerName" placeholder="请输入客户名称">
                </Form-item>
                <Form-item label="信用天数">
                    <i-input v-model="company.creditDays" placeholder="请输入信用天数">
                </Form-item>
            </i-form>
        </Modal>
    </div>
</div>
</@main.page>