package com.auction.model.ItemType;

import java.math.BigDecimal;

public abstract class Item {
    private final String name;
    private BigDecimal currentPrice;

    public Item(String name, BigDecimal price) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Item name must not be blank");
        }
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Item price must be non-negative");
        }
        this.name = name;
        this.currentPrice = price;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Item price must be non-negative");
        }
        this.currentPrice = price;
    }
}
