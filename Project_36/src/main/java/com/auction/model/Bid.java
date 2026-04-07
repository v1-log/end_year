package com.auction.model;

import com.auction.model.User.Bidder;
import java.math.BigDecimal;

public class Bid {
    private final Bidder bidder;
    private final BigDecimal amount;

    public Bid(Bidder bidder, BigDecimal amount) {
        if (bidder == null) {
            throw new IllegalArgumentException("Bidder must not be null");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Bid amount must be greater than zero");
        }
        this.bidder = bidder;
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Bidder getBidder() {
        return bidder;
    }
}
