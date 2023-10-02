package saman.online.shop.models;

import java.io.Serializable;
import java.util.List;

import saman.online.shop.enums.PaymentType;

public class Payment implements Serializable {
    private PaymentUserVM customer;
    private List<OrderItem> orderItems;
    private PaymentType paymentType;


    public PaymentUserVM getCustomer() {
        return customer;
    }

    public void setCustomer(PaymentUserVM customer) {
        this.customer = customer;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }
}
