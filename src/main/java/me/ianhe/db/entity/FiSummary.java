package me.ianhe.db.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * FiSummary
 *
 * @author iHelin
 * @since 2017/10/17 15:29
 */
public class FiSummary {
    private Integer id;

    private BigDecimal debitAmount;

    private BigDecimal creditAmount;

    private BigDecimal balanceAmount;

    private Integer customerID;

    private FiCustomer fiCustomer;

    private Date baseDate;

    private Date dueDate;

    private Integer accPeriod;

    private Boolean creditFlag;

    private Boolean clearFlag;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getDebitAmount() {
        return debitAmount;
    }

    public void setDebitAmount(BigDecimal debitAmount) {
        this.debitAmount = debitAmount;
    }

    public BigDecimal getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(BigDecimal creditAmount) {
        this.creditAmount = creditAmount;
    }

    public BigDecimal getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(BigDecimal balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public Date getBaseDate() {
        return baseDate;
    }

    public void setBaseDate(Date baseDate) {
        this.baseDate = baseDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Integer getAccPeriod() {
        return accPeriod;
    }

    public void setAccPeriod(Integer accPeriod) {
        this.accPeriod = accPeriod;
    }

    public Boolean getCreditFlag() {
        return creditFlag;
    }

    public void setCreditFlag(Boolean creditFlag) {
        this.creditFlag = creditFlag;
    }

    public Boolean getClearFlag() {
        return clearFlag;
    }

    public void setClearFlag(Boolean clearFlag) {
        this.clearFlag = clearFlag;
    }

    public FiCustomer getFiCustomer() {
        return fiCustomer;
    }

    public void setFiCustomer(FiCustomer fiCustomer) {
        this.fiCustomer = fiCustomer;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}