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
    private static final long SHORT_AUCTION_DURATION_MS = 200L;
    private static final long WAIT_FOR_AUTO_CLOSE_MS = 450L;

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

        assertTrue(Duration.between(before, after).getSeconds() >= 30);
    }

    @Test
    void shouldAutoCloseAuctionAfterEndTime() throws InterruptedException {
        Item item = new Electronics("Phone", BigDecimal.valueOf(1000));
        Auction auction = new Auction(
                item,
                LocalDateTime.now(),
                LocalDateTime.now().plus(Duration.ofMillis(SHORT_AUCTION_DURATION_MS))
        );

        assertTrue(auction.isOpen());
        Thread.sleep(WAIT_FOR_AUTO_CLOSE_MS);
        assertFalse(auction.isOpen());
    }
}
