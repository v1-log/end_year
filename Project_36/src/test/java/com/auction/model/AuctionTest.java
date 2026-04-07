package com.auction.model;

import com.auction.model.ItemType.Electronics;
import com.auction.model.ItemType.Item;
import com.auction.model.User.Bidder;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AuctionTest {

    @Test
    void shouldRejectLowerOrEqualBid() {
        Item item = new Electronics("Phone", BigDecimal.valueOf(1000));
        Auction auction = new Auction(item, LocalDateTime.now(), LocalDateTime.now().plusMinutes(1));
        Bidder bidder = new Bidder("1", "Alice");

        assertThrows(
                IllegalArgumentException.class,
                () -> auction.placeBid(new Bid(bidder, BigDecimal.valueOf(1000)))
        );
    }

    @Test
    void shouldExtendEndTimeForLateBid() {
        Item item = new Electronics("Phone", BigDecimal.valueOf(1000));
        Auction auction = new Auction(item, LocalDateTime.now(), LocalDateTime.now().plusSeconds(5));
        Bidder bidder = new Bidder("1", "Alice");

        LocalDateTime before = auction.getEndTime();
        auction.placeBid(new Bid(bidder, BigDecimal.valueOf(1100)));
        LocalDateTime after = auction.getEndTime();

        assertTrue(Duration.between(before, after).getSeconds() >= 29);
    }

    @Test
    void shouldAutoCloseAuctionAfterEndTime() throws InterruptedException {
        Item item = new Electronics("Phone", BigDecimal.valueOf(1000));
        Auction auction = new Auction(item, LocalDateTime.now(), LocalDateTime.now().plusNanos(200_000_000));

        assertTrue(auction.isOpen());
        Thread.sleep(450);
        assertFalse(auction.isOpen());
    }
}
