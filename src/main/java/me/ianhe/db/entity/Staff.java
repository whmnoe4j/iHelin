package me.ianhe.db.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.math.BigDecimal;

public class Staff implements Serializable {

    private static final long serialVersionUID = 1L;

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

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(name).append(basicWage).append(subsidizedMeals)
                .append(socialSecurity).append(accumulationFund).append(other).toHashCode();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o, false);
    }

    public static void main(String[] args) {
        Staff staff = new Staff();
        staff.setName("seven");
        staff.setId(1);
        staff.setAccumulationFund(new BigDecimal("100.0"));
        System.out.println(staff);
    }
}