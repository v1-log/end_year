package com.auction.model;

import com.auction.model.ItemType.Item;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class Auction {
    private static final long SNIPING_WINDOW_SECONDS = 10;
    private static final long EXTENSION_SECONDS = 30;
    private static final Logger LOGGER = Logger.getLogger(Auction.class.getName());

    private final Item item;
    private final LocalDateTime startTime;
    private volatile LocalDateTime endTime;
    private volatile Bid highestBid;
    private volatile boolean isOpen;

    private ScheduledExecutorService scheduler;

    public Auction(Item item, LocalDateTime startTime, LocalDateTime endTime) {
        if (item == null) {
            throw new IllegalArgumentException("Item must not be null");
        }
        if (startTime == null || endTime == null) {
            throw new IllegalArgumentException("Start and end time must not be null");
        }
        if (!endTime.isAfter(startTime)) {
            throw new IllegalArgumentException("End time must be after start time");
        }
        this.item = item;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isOpen = true;

        startAutoClose();
    }

    public synchronized void placeBid(Bid bid) {
        if (bid == null) {
            throw new IllegalArgumentException("Bid must not be null");
        }

        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(startTime)) {
            throw new IllegalStateException("Auction has not started yet");
        }

        if (!isOpen || !now.isBefore(endTime)) {
            closeAuction();
            throw new IllegalStateException("Auction is closed");
        }

        if (bid.getAmount().compareTo(item.getCurrentPrice()) <= 0) {
            throw new IllegalArgumentException("Invalid bid: must be higher than current price");
        }

        long secondsLeft = Duration.between(now, endTime).getSeconds();
        if (secondsLeft <= SNIPING_WINDOW_SECONDS) {
            endTime = endTime.plusSeconds(EXTENSION_SECONDS);
            LOGGER.info("Auction extended by " + EXTENSION_SECONDS + " seconds!");

            restartScheduler();
        }

        item.setCurrentPrice(bid.getAmount());
        highestBid = bid;

        LOGGER.info("New highest bid: " + bid.getAmount() + " by " + bid.getBidder().getName());
    }

    private synchronized void startAutoClose() {
        scheduler = Executors.newSingleThreadScheduledExecutor();

        long delay = Duration.between(LocalDateTime.now(), endTime).toMillis();

        if (delay <= 0) {
            closeAuction();
            return;
        }
        scheduler.schedule(() -> {
            closeAuction();
            LOGGER.info("Auction closed automatically!");
        }, delay, TimeUnit.MILLISECONDS);
    }

    private synchronized void restartScheduler() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdownNow();
        }
        startAutoClose();
    }

    public synchronized void closeAuction() {
        isOpen = false;

        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
        }
    }

    public Bid getHighestBid() {
        return highestBid;
    }

    public Item getItem() {
        return item;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }
}
