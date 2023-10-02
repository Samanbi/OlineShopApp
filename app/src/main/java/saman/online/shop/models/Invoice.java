package saman.online.shop.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Invoice implements Serializable {
    private long id;
    private Customer customer;
    private List<OrderItem> orderItems;
    private Date invoiceData;
    private Date PaymentDate;
    private boolean payed;
    private String invoiceDateStr;
    private String payedDateStr;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Date getInvoiceData() {
        return invoiceData;
    }

    public void setInvoiceData(Date invoiceData) {
        this.invoiceData = invoiceData;
    }

    public Date getPaymentDate() {
        return PaymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        PaymentDate = paymentDate;
    }

    public boolean isPayed() {
        return payed;
    }

    public void setPayed(boolean payed) {
        this.payed = payed;
    }

    public String getInvoiceDateStr() {
        return invoiceDateStr;
    }

    public void setInvoiceDateStr(String invoiceDateStr) {
        this.invoiceDateStr = invoiceDateStr;
    }

    public String getPayedDateStr() {
        return payedDateStr;
    }

    public void setPayedDateStr(String payedDateStr) {
        this.payedDateStr = payedDateStr;
    }
}
