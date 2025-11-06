package com.learnwithravi.orderconsumer.DTO;

public class Order {
    private String customerName;
    private String itemName;
    private int quantity;

    public Order(String customerName, String itemName, int quantity) {}
    public Order() {}
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
