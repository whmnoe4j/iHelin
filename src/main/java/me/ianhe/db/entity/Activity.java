package me.ianhe.db.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Activity {
    private Integer id;

    private Integer staff;

    private String project;

    private BigDecimal labour;

    private BigDecimal bonus;

    private Date date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStaff() {
        return staff;
    }

    public void setStaff(Integer staff) {
        this.staff = staff;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public BigDecimal getLabour() {
        return labour;
    }

    public void setLabour(BigDecimal labour) {
        this.labour = labour;
    }

    public BigDecimal getBonus() {
        return bonus;
    }

    public void setBonus(BigDecimal bonus) {
        this.bonus = bonus;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}