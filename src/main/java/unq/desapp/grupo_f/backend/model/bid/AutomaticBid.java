package unq.desapp.grupo_f.backend.model.bid;

import javax.persistence.Entity;

import unq.desapp.grupo_f.backend.model.Auction;
import unq.desapp.grupo_f.backend.model.User;

@Entity
public class AutomaticBid extends Bid {
	
	private Integer autoBiddingLimit;
	public AutomaticBid(Auction auction, User user, Integer autoBiddingLimit) {
		super(auction, user);		
		this.autoBiddingLimit = autoBiddingLimit;
	}
	public AutomaticBid() {}
	
	public Integer getBiddingLimit() {
		return autoBiddingLimit;
	}

	@Override
	public void autoBid() {
		Bid newBid = new ManualBid(this.auction, this.user);
		this.auction.addBid(newBid);
	}

	@Override
	public Boolean canAutoBid(Integer nextPriceOfAuction) {
		return this.autoBiddingLimit > nextPriceOfAuction;
	}

}
