package com.auction;
import com.auction.model.*;
import com.auction.model.ItemType.Electronics;
import com.auction.model.ItemType.Item;
import com.auction.model.User.Bidder;

public class Main {
    public static void main(String[] args) {
<<<<<<< HEAD
        Item item = new Electronics("iPhone", 1000);
        Auction auction = new Auction(item, LocalDateTime.now(), LocalDateTime.now().plusMinutes(1));
=======
        Item item = new Item("iPhone", 1000);
        Auction auction = new Auction(item);
>>>>>>> parent of 4c854b1 (Update Main.java)

        Bidder a = new Bidder("1", "Minh");
        Bidder b = new Bidder("2", "An");

        auction.placeBid(new Bid(a, 1100));
        auction.placeBid(new Bid(b, 1200));

        System.out.println(auction.getHighestBid().getAmount());
    }
}
