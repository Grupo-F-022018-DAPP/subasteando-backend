package unq.desapp.grupo_f.backend.model.builders;

import java.util.Optional;
import java.util.Random;

import unq.desapp.grupo_f.backend.model.Auction;
import unq.desapp.grupo_f.backend.model.User;
import unq.desapp.grupo_f.backend.model.bid.AutomaticBid;
import unq.desapp.grupo_f.backend.model.exceptions.BuilderException;

public class AutomaticBidBuilder {
	
	private Optional<Auction> auction;
	private Optional<User> user;
	private Optional<Integer> autoBiddingLimit;
	
	
	public AutomaticBidBuilder() {
		this.auction			= Optional.empty();
		this.user				= Optional.empty();
		this.autoBiddingLimit	= Optional.empty();
	}
	public Optional<Auction> getAuction() {
		return auction;
	}
	public Optional<User> getUser() {
		return user;
	}
	public Optional<Integer> getAutoBiddingLimit() {
		return autoBiddingLimit;
	}
	public AutomaticBidBuilder setAuction(Auction auction) {
		AutomaticBidBuilder copy = this.copy();
		copy.auction = Optional.ofNullable(auction);
		return copy;
	}
	public AutomaticBidBuilder setUser(User user) {
		AutomaticBidBuilder copy = this.copy();
		copy.user = Optional.ofNullable(user);
		return copy;
	}
	public AutomaticBidBuilder setAutoBiddingLimit(Integer autoBiddingLimit) {
		AutomaticBidBuilder copy = this.copy();
		copy.autoBiddingLimit = Optional.ofNullable(autoBiddingLimit);
		return copy;
	}
	
	
	public AutomaticBid build() {
		if(!hasEverything()) {
			throw new BuilderException("An automatic bid needs an user, auction, and bidding limit. "
					+ "	One or more of them are missing");
		}
		return new AutomaticBid(auction.get(),user.get(), autoBiddingLimit.get());
	}
	public AutomaticBid buildRandom() {
		User randomUser = new UserBuilder().buildRandom();
		Auction randomAuction = new AuctionBuilder().buildRandom();
		
		return new AutomaticBid(randomAuction, randomUser, new Random().nextInt(1000));
	}
	
	
	
	private AutomaticBidBuilder copy() {
		AutomaticBidBuilder copy = new AutomaticBidBuilder();
		copy.auction = this.auction;
		copy.user = this.user;
		copy.autoBiddingLimit = this.autoBiddingLimit;
		
		return copy;
	}
	private Boolean hasEverything() {
		return auction.isPresent() && user.isPresent() && autoBiddingLimit.isPresent();
	}
	
	
}
