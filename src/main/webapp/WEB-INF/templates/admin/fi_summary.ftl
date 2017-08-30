<#import "admin_frame.ftl" as main>

<#assign html_other_script in main>
<script>
    new Vue({
        el: '#app',
        data: {
            pageNum: 1,
            pageSize: 10,
            total: 0,
            summaries: [],
            customers: [],
            visible: false,
            columns: [
                {
                    title: '客户名称',
                    align: 'center',
                    render: (h, params) => {
                        return h('span', params.row.fiCustomer.customerName);
                    }
                },
                {
                    title: '起算日',
                    key: 'baseDate',
                    align: 'center',
                    render: (h, params) => {
                        return h('span', new Date(params.row.baseDate).format('yyyy-MM-dd'));
                    }
                },
                {
                    title: '到期日',
                    key: 'dueDate',
                    align: 'center',
                    render: (h, params) => {
                        return h('span', new Date(params.row.dueDate).format('yyyy-MM-dd'));
                    }
                },
                {
                    title: '账期',
                    key: 'accPeriod',
                    align: 'center'
                },
                {
                    title: '借方金额',
                    key: 'debitAmount',
                    align: 'right',
                    render: (h, params) => {
                        return h('span', params.row.debitAmount.toFixed(2));
                    }
                },
                {
                    title: '贷方金额',
                    key: 'creditAmount',
                    align: 'right',
                    render: (h, params) => {
                        return h('span', params.row.creditAmount.toFixed(2));
                    }
                },
                {
                    title: '余额',
                    key: 'balanceAmount',
                    align: 'right',
                    render: (h, params) => {
                        return h('span', params.row.balanceAmount.toFixed(2));
                    }
                },
            ],
            summary: {
                customerID: null,
                baseDate: null,
                debitAmount: 100,
                remark: ''
            },
            ruleValidate: {
                baseDate: [
                    {required: true, type: 'date', message: '请选择日期', trigger: 'change'}
                ]
            },
            paymentRule: {
                receiptDate: [
                    {required: true, type: 'date', message: '请选择日期', trigger: 'change'}
                ]
            },
            paymentVisible: false,
            payment: {
                paymentAmount: 100,
                receiptDate: null,
                customerID: null,
                remark: ''
            }
        },
        mounted(){
            this.init();
            this.getAllCustomers();
        },
        methods: {
            handleAdd(){
                this.$refs['formValidate'].validate((valid) => {
                    if (valid) {
                        this.$http.post("${request.contextPath}/admin/fi/summary", {
                            customerID: this.summary.customerID,
                            debitAmount: this.summary.debitAmount,
                            baseDate: this.summary.baseDate.format('yyyy-MM-dd HH:mm:ss'),
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
            handleReset(){
                this.$refs['formValidate'].resetFields();
            },
            getAllCustomers(){
                this.$http.get("${request.contextPath}/admin/fi/customers/component").then(res => {
                    this.customers = res.data.data;
                });
            },
            addSummary(){
                this.visible = true;
                this.$nextTick(() => {
                    this.handleReset();
                });
            },
            pageSizeChange(pageSize){
                this.pageSize = pageSize;
                this.init(1);
            },
            init(pageNum){
                if (pageNum) {
                    this.pageNum = pageNum;
                }
                this.$http.get("${request.contextPath}/admin/fi/summaries", {
                    params: {
                        pageNum: this.pageNum,
                        pageSize: this.pageSize
                    }
                }).then(res => {
                    this.summaries = res.data.data;
                    this.total = res.data.total;
                });
            },
            addPayment(){
                this.paymentVisible = true;
                this.$nextTick(() => {
                    this.$refs['paymentForm'].resetFields();
                });
            },
            handleAddPayment(){
                this.$refs['paymentForm'].validate(valid => {
                    if (valid) {
                        this.$http.post("${request.contextPath}/admin/fi/payment", {
                            customerID: this.payment.customerID,
                            paymentAmount: this.payment.paymentAmount,
                            receiptDate: this.payment.receiptDate.format('yyyy-MM-dd HH:mm:ss'),
                            remark: this.payment.remark
                        }, {emulateJSON: true}).then(res => {
                            if (res.data.status == 'success') {
                                this.$Message.success('保存成功!');
                                this.init();
                                this.paymentVisible = false;
                            }
                        });
                    }
                });
            }
        }
    });
</script>
</#assign>
<@main.page title="应收明细">
<div class="layout-breadcrumb">
    <Breadcrumb>
        <Breadcrumb-item href="${request.contextPath}/admin/index">首页</Breadcrumb-item>
        <Breadcrumb-item href="javascript:void(0);">财务</Breadcrumb-item>
        <Breadcrumb-item>应收明细</Breadcrumb-item>
    </Breadcrumb>
</div>
<div class="layout-content">
    <div class="layout-content-main">
        <Row>
            <Button-group style="float: right;">
                <i-button type="success" @click="addSummary">新增应收</i-button>
                <i-button type="primary" @click="addPayment">新增收款</i-button>
            </Button-group>
        </Row>
        <Row style="margin-top: 5px">
            <i-table stripe :columns="columns" :data="summaries"></i-table>
        </Row>
        <Page :current="pageNum"
              :page-size="pageSize"
              :total="total"
              show-total
              show-sizer
              placement="top"
              @on-page-size-change="pageSizeChange"
              style="margin-top: 5px;"
              @on-change="init"></Page>
    </div>
</div>
<Modal v-model="visible"
       title="新增应收"
       @on-ok="handleAdd()"
       ok-text="提交" :loading="true">
    <i-form ref="formValidate" :model="summary" :rules="ruleValidate" :label-width="80">
        <Form-item label="客户">
            <i-select v-model="summary.customerID">
                <i-option v-for="customer in customers" :value="customer.id" :key="customer.id">
                    {{ customer.customerName}}
                </i-option>
            </i-select>
        </Form-item>
        <Form-item label="起算日" prop="baseDate">
            <Date-picker v-model="summary.baseDate" type="date" placeholder="选择日期" style="width: 100%;"></Date-picker>
        </Form-item>
        <Form-item label="借方金额">
            <Input-number :max="10000000" :min="1" v-model="summary.debitAmount" style="width: 100%;"></Input-number>
        </Form-item>
        <Form-item label="应收备注">
            <i-input v-model="summary.remark" type="textarea" :autosize="{minRows: 3,maxRows: 8}" style="width: 100%;"
                     placeholder="请输入..."></i-input>
        </Form-item>
    </i-form>
</Modal>
<Modal v-model="paymentVisible"
       title="新增收款"
       @on-ok="handleAddPayment()"
       ok-text="提交" :loading="true">
    <i-form ref="paymentForm" :model="payment" :label-width="80" :rules="paymentRule">
        <Form-item label="客户">
            <i-select v-model="payment.customerID">
                <i-option v-for="customer in customers" :value="customer.id" :key="customer.id">
                    {{ customer.customerName}}
                </i-option>
            </i-select>
        </Form-item>
        <Form-item label="收款日期" prop="receiptDate">
            <Date-picker v-model="payment.receiptDate" type="date" placeholder="选择日期"
                         style="width: 100%;"></Date-picker>
        </Form-item>
        <Form-item label="收款金额">
            <Input-number :max="10000000" :min="1" v-model="payment.paymentAmount" style="width: 100%;"></Input-number>
        </Form-item>
        <Form-item label="收款备注">
            <i-input v-model="payment.remark" type="textarea" :autosize="{minRows: 3,maxRows: 8}" style="width: 100%;"
                     placeholder="请输入..."></i-input>
        </Form-item>
    </i-form>
</Modal>
</@main.page>