package unq.desapp.grupo_f.backend.model.bid;

import unq.desapp.grupo_f.backend.model.User;
import unq.desapp.grupo_f.backend.model.auction.Auction;

public class AutomaticBid extends Bid {
	
	private Integer autoBiddingLimit;
	public AutomaticBid(Auction auction, User user, Integer autoBiddingLimit) {
		super(auction, user);		
		this.autoBiddingLimit = autoBiddingLimit;
	}
	
	public Integer getBiddingLimit() {
		return autoBiddingLimit;
	}

	@Override
	public void autoBid() {
		Bid newBid = new AutomaticBid(this.auction, this.user, this.autoBiddingLimit);
		this.auction.addBid(newBid);
	}

	@Override
	public Boolean canAutoBid(Integer actualPriceOfAuction) {
		return this.autoBiddingLimit > actualPriceOfAuction;
	}

}
