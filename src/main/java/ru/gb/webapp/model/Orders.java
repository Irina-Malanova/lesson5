package ru.gb.webapp.model;

public class Orders {
    private int orderNummer;
    private long productsCount;
    private long userId;

    public Orders(int orderNummer, long productsCount, long userId) {
        this.orderNummer = orderNummer;
        this.productsCount = productsCount;
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getOrderNummer() {
        return orderNummer;
    }

    public void setOrderNummer(int orderNummer) {
        this.orderNummer = orderNummer;
    }

    public long getProductsCount() {
        return productsCount;
    }

    public void setProductsCount(long productsCount) {
        this.productsCount = productsCount;
    }
}
