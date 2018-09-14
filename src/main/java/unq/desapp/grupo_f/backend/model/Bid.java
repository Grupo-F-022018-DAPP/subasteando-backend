package unq.desapp.grupo_f.backend.model;

import unq.desapp.grupo_f.backend.model.auction.Auction;

public abstract class Bid {
	protected User user;
	protected Auction auction;
	
	public Bid(Auction auction, User user) {
		this.user = user;
		this.auction = auction;
	}
	public User getUser() {
		return user;
	}
	public Auction getAuction() {
		return auction;
	}
	
	public abstract Boolean canAutoBid(Integer actualPriceOfAuction);
	public abstract Integer getBiddingLimit();
	public abstract void autoBid();

}
