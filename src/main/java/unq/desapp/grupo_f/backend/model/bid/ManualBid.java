package unq.desapp.grupo_f.backend.model.bid;

import unq.desapp.grupo_f.backend.model.User;
import unq.desapp.grupo_f.backend.model.auction.Auction;
import unq.desapp.grupo_f.backend.model.exceptions.BiddingException;

public class ManualBid extends Bid {

	public ManualBid(Auction auction, User user) {
		super(auction, user);
	}

	@Override
	public Integer getBiddingLimit() {
		throw new BiddingException("A manual bid doesnt have bidding limit");
	}

	@Override
	public void autoBid() {
		throw new BiddingException("A manual bid doesnt do automatic bidding");
	}

	@Override
	public Boolean canAutoBid(Integer actualPriceOfAuction) {
		return false;
	}

}
