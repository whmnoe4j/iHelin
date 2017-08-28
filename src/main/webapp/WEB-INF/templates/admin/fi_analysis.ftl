<#import "admin_frame.ftl" as main>
<#assign html_other_script in main>
<script>
    new Vue({
        el: '#app',
        data(){
            let vm = this;
            return {
                pageNum: 1,
                pageSize: 10,
                total: 0,
                agings: [],
                summaryData: [],
                summaryDetailVisible: false,
                clearData: [],
                clearVisible: false,
                clearColumns: [
                    {
                        title: '收款日期',
                        align: 'center',
                        render: (h, params) => {
                            return h('span', new Date(params.row.receiptDate).format('yyyy-MM-dd'));
                        }
                    },
                    {
                        title: '起算日',
                        align: 'center',
                        render: (h, params) => {
                            return h('span', new Date(params.row.baseDate).format('yyyy-MM-dd'));
                        }
                    }, {
                        title: '到期日',
                        align: 'center',
                        render: (h, params) => {
                            return h('span', new Date(params.row.dueDate).format('yyyy-MM-dd'));
                        }
                    },
                    {
                        title: '账期',
                        align: 'right',
                        key: 'accPeriod'
                    },
                    {
                        title: '收款前',
                        align: 'right',
                        key: 'beforeAmount',
                        render: (h, params) => {
                            return h('span', params.row.beforeAmount.toFixed(2));
                        }
                    },
                    {
                        title: '收款后',
                        align: 'right',
                        key: 'afterAmount',
                        render: (h, params) => {
                            return h('span', params.row.afterAmount.toFixed(2));
                        }
                    }, {
                        title: '收款金额',
                        align: 'right',
                        key: 'paymentAmount',
                        render: (h, params) => {
                            return h('span', params.row.paymentAmount.toFixed(2));
                        }
                    }, {
                        title: '冲销金额',
                        align: 'right',
                        key: 'actualAmount',
                        render: (h, params) => {
                            return h('span', params.row.actualAmount.toFixed(2));
                        }
                    }
                ],
                summaryColumns: [
                    {
                        title: '起算日',
                        align: 'center',
                        render: (h, params) => {
                            return h('span', new Date(params.row.baseDate).format('yyyy-MM-dd'));
                        }
                    }, {
                        title: '到期日',
                        align: 'center',
                        render: (h, params) => {
                            return h('span', new Date(params.row.dueDate).format('yyyy-MM-dd'));
                        }
                    }, {
                        title: '账期',
                        align: 'right',
                        key: 'accPeriod'
                    },
                    {
                        title: '借方金额',
                        align: 'right',
                        key: 'debitAmount',
                        render: (h, params) => {
                            return h('span', params.row.debitAmount.toFixed(2));
                        }
                    }, {
                        title: '贷方金额',
                        align: 'right',
                        key: 'creditAmount',
                        render: (h, params) => {
                            return h('span', params.row.creditAmount.toFixed(2));
                        }
                    },
                    {
                        title: '余额',
                        align: 'right',
                        key: 'balanceAmount',
                        render: (h, params) => {
                            return h('span', params.row.balanceAmount.toFixed(2));
                        }
                    }
                ],
                columns: [
                    {
                        title: '客户名称',
                        align: 'center',
                        key: 'customerName'
                    },
                    {
                        title: '应收',
                        key: 'action',
                        align: 'right',
                        render: (h, params) => {
                            if (params.row.totalDebitAmount > 0) {
                                return h('Button', {
                                    props: {
                                        type: 'text',
                                        size: 'small'
                                    },
                                    style: {
                                        border: 0,
                                        padding: 0
                                    },
                                    on: {
                                        click() {
                                            vm.searchDetail(params.row.customerID);
                                        }
                                    }
                                }, params.row.totalDebitAmount.toFixed(2));
                            } else {
                                return h('span', params.row.totalDebitAmount.toFixed(2));
                            }
                        }
                    },
                    {
                        title: '已消',
                        align: 'right',
                        render: (h, params) => {
                            if (params.row.totalCreditAmount >= 0) {
                                return h('Button', {
                                    props: {
                                        type: 'text',
                                        size: 'small'
                                    },
                                    style: {
                                        border: 0,
                                        padding: 0
                                    },
                                    on: {
                                        click: () => {
                                            vm.getAlreadyClear(params.row.customerID);
                                        }
                                    }
                                }, params.row.totalCreditAmount.toFixed(2));
                            } else {
                                return h('span', params.row.totalCreditAmount.toFixed(2));
                            }
                        }
                    },
                    {
                        title: '未消',
                        align: 'right',
                        render: (h, params) => {
                            if (params.row.totalBalanceAmount > 0) {
                                return h('Button', {
                                    props: {
                                        type: 'text',
                                        size: 'small'
                                    },
                                    style: {
                                        border: 0,
                                        padding: 0
                                    },
                                    on: {
                                        click: () => {
                                            vm.searchDetail(params.row.customerID);
                                        }
                                    }
                                }, params.row.totalBalanceAmount.toFixed(2));
                            } else {
                                return h('span', params.row.totalBalanceAmount.toFixed(2));
                            }
                        }
                    },
                    {
                        title: '15',
                        align: 'right',
                        render: (h, params) => {
                            if (params.row.amt15 > 0) {
                                return h('Button', {
                                    props: {
                                        type: 'text',
                                        size: 'small'
                                    },
                                    style: {
                                        border: 0,
                                        padding: 0
                                    },
                                    on: {
                                        click: () => {
                                            vm.searchDetail(params.row.customerID, 15);
                                        }
                                    }
                                }, params.row.amt15.toFixed(2));
                            } else {
                                return h('span', params.row.amt15.toFixed(2));
                            }
                        }
                    },
                    {
                        title: '30',
                        align: 'right',
                        render: (h, params) => {
                            if (params.row.amt30 > 0) {
                                return h('Button', {
                                    props: {
                                        type: 'text',
                                        size: 'small'
                                    },
                                    style: {
                                        border: 0,
                                        padding: 0,
                                        color: 'orange'
                                    },
                                    on: {
                                        click: () => {
                                            vm.searchDetail(params.row.customerID, 30);
                                        }
                                    }
                                }, params.row.amt30.toFixed(2));
                            } else {
                                return h('span', params.row.amt30.toFixed(2));
                            }
                        }
                    },
                    {
                        title: '60',
                        align: 'right',
                        render: (h, params) => {
                            if (params.row.amt60 > 0) {
                                return h('Button', {
                                    props: {
                                        type: 'text',
                                        size: 'small'
                                    },
                                    style: {
                                        border: 0,
                                        padding: 0,
                                        color: 'red'
                                    },
                                    on: {
                                        click: () => {
                                            vm.searchDetail(params.row.customerID, 60);
                                        }
                                    }
                                }, params.row.amt60.toFixed(2));
                            } else {
                                return h('span', params.row.amt60.toFixed(2));
                            }
                        }
                    },
                    {
                        title: '90',
                        align: 'right',
                        render: (h, params) => {
                            if (params.row.amt90 > 0) {
                                return h('Button', {
                                    props: {
                                        type: 'text',
                                        size: 'small'
                                    },
                                    style: {
                                        border: 0,
                                        padding: 0,
                                        color: 'red'
                                    },
                                    on: {
                                        click: () => {
                                            vm.searchDetail(params.row.customerID, 90);
                                        }
                                    }
                                }, params.row.amt90.toFixed(2));
                            } else {
                                return h('span', params.row.amt90.toFixed(2));
                            }
                        }
                    },
                    {
                        title: '120',
                        align: 'right',
                        render: (h, params) => {
                            if (params.row.amt120 > 0) {
                                return h('Button', {
                                    props: {
                                        type: 'text',
                                        size: 'small'
                                    },
                                    style: {
                                        border: 0,
                                        padding: 0,
                                        color: 'red'
                                    },
                                    on: {
                                        click: () => {
                                            vm.searchDetail(params.row.customerID, 120);
                                        }
                                    }
                                }, params.row.amt120.toFixed(2));
                            } else {
                                return h('span', params.row.amt120.toFixed(2));
                            }
                        }
                    }
                ],
            }
        },
        methods: {
            init(pageNum){
                if (pageNum) {
                    this.pageNum = pageNum;
                }
                this.$http.get("${request.contextPath}/admin/fi/aging/analysis", {
                    params: {
                        pageNum: this.pageNum,
                        pageSize: this.pageSize
                    }
                }).then(res => {
                    this.agings = res.data.data;
                    this.total = res.data.total;
                });
            },
            searchDetail(customerID, type){
                this.summaryData = [];
                this.$http.get('${request.contextPath}/admin/fi/analysis/detail', {
                    params: {
                        customerID: customerID,
                        type: type
                    }
                }).then(res => {
                    this.summaryDetailVisible = true;
                    this.summaryData = res.data.data;
                });
            },
            getAlreadyClear(customerID){
                this.clearData = [];
                this.$http.get('${request.contextPath}/admin/fi/analysis/clear', {
                    params: {
                        customerID: customerID
                    }
                }).then(res => {
                    this.clearVisible = true;
                    this.clearData = res.data.data;
                });
            }
        },
        mounted(){
            this.init();
        }
    })
</script>
</#assign>
<@main.page title="账龄分析">
<div class="layout-breadcrumb">
    <Breadcrumb>
        <Breadcrumb-item href="${request.contextPath}/admin/index">首页</Breadcrumb-item>
        <Breadcrumb-item href="javascript:void(0);">财务</Breadcrumb-item>
        <Breadcrumb-item>账龄分析</Breadcrumb-item>
    </Breadcrumb>
</div>
<div class="layout-content">
    <div class="layout-content-main">
        <Row style="margin-top: 5px">
            <i-table stripe border :columns="columns" :data="agings"></i-table>
        </Row>
        <Page :current="pageNum"
              :page-size="pageSize"
              :total="total"
              show-total
              style="margin-top: 5px;"
              @on-change="init"></Page>
    </div>
</div>
<Modal v-model="summaryDetailVisible" title="已消明细" width="800px">
    <i-table stripe border :columns="summaryColumns" :data="summaryData"></i-table>
</Modal>
<Modal v-model="clearVisible" title="账龄分析" width="800px">
    <i-table stripe border :columns="clearColumns" :data="clearData"></i-table>
</Modal>
</@main.page>