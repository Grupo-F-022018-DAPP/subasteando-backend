package unq.desapp.grupo_f.backend.model.bid;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import unq.desapp.grupo_f.backend.model.Auction;
import unq.desapp.grupo_f.backend.model.User;
import unq.desapp.grupo_f.backend.model.exceptions.BiddingException;

@Entity
public class ManualBid extends Bid {

	public ManualBid(Auction auction, User user) {
		super(auction, user);
	}
	public ManualBid() {}

	@Override
	@JsonIgnore
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
