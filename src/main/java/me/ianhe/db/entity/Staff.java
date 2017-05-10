package me.ianhe.db.entity;

import java.math.BigDecimal;

public class Staff {

    private Integer id;

    private String name;

    private BigDecimal basicWage;

    private BigDecimal subsidizedMeals;

    private BigDecimal socialSecurity;

    private BigDecimal accumulationFund;

    private BigDecimal other;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBasicWage() {
        return basicWage;
    }

    public void setBasicWage(BigDecimal basicWage) {
        this.basicWage = basicWage;
    }

    public BigDecimal getSubsidizedMeals() {
        return subsidizedMeals;
    }

    public void setSubsidizedMeals(BigDecimal subsidizedMeals) {
        this.subsidizedMeals = subsidizedMeals;
    }

    public BigDecimal getSocialSecurity() {
        return socialSecurity;
    }

    public void setSocialSecurity(BigDecimal socialSecurity) {
        this.socialSecurity = socialSecurity;
    }

    public BigDecimal getAccumulationFund() {
        return accumulationFund;
    }

    public void setAccumulationFund(BigDecimal accumulationFund) {
        this.accumulationFund = accumulationFund;
    }

    public BigDecimal getOther() {
        return other;
    }

    public void setOther(BigDecimal other) {
        this.other = other;
    }
}