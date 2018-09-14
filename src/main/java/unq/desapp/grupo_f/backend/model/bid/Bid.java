package unq.desapp.grupo_f.backend.model.bid;

import unq.desapp.grupo_f.backend.model.User;
import unq.desapp.grupo_f.backend.model.auction.Auction;

public abstract class Bid {
	protected User user;
	protected Auction auction;
	protected Integer price;
	
	public Bid(Auction auction, User user) {
		this.user = user;
		this.auction = auction;
		this.price = 0;
	}
	public User getUser() {
		return user;
	}
	public Auction getAuction() {
		return auction;
	}
	public Integer getPrice() {
		return this.price;
	}
	
	public abstract Boolean canAutoBid(Integer actualPriceOfAuction);
	public abstract Integer getBiddingLimit();
	public abstract void autoBid();
	public void setPrice(Integer nextPrice) {
		this.price = nextPrice;
	}

}
