package me.ianhe.db.entity;

/**
 * FiCustomer
 *
 * @author iHelin
 * @since 2017/10/17 15:28
 */
public class FiCustomer {
    private Integer id;

    private String customerName;

    private Integer creditDays;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getCreditDays() {
        return creditDays;
    }

    public void setCreditDays(Integer creditDays) {
        this.creditDays = creditDays;
    }
}