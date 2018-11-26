package unq.desapp.grupo_f.backend.model.builders;

import java.util.Optional;
import java.util.Random;

import unq.desapp.grupo_f.backend.model.Auction;
import unq.desapp.grupo_f.backend.model.User;
import unq.desapp.grupo_f.backend.model.bid.AutomaticBid;
import unq.desapp.grupo_f.backend.model.bid.ManualBid;
import unq.desapp.grupo_f.backend.model.exceptions.BuilderException;

public class ManualBidBuilder {
	
	private Optional<Auction> auction;
	private Optional<User> user;
	
	
	public ManualBidBuilder() {
		this.auction			= Optional.empty();
		this.user				= Optional.empty();
	}
	public Optional<Auction> getAuction() {
		return auction;
	}
	public Optional<User> getUser() {
		return user;
	}
	public ManualBidBuilder setAuction(Auction auction) {
		ManualBidBuilder copy = this.copy();
		copy.auction = Optional.ofNullable(auction);
		return copy;
	}
	public ManualBidBuilder setUser(User user) {
		ManualBidBuilder copy = this.copy();
		copy.user = Optional.ofNullable(user);
		return copy;
	}
	
	
	public ManualBid build() {
		if(!hasEverything()) {
			throw new BuilderException("A manual bid needs an user and an auction. "
					+ "	One or both of them are missing");
		}
		return new ManualBid(auction.get(),user.get());
	}
	public ManualBid buildRandom() {
		User randomUser = new UserBuilder().buildRandom();
		Auction randomAuction = new AuctionBuilder().buildRandom();
		
		return new ManualBid(randomAuction, randomUser);
	}
	
	private ManualBidBuilder copy() {
		ManualBidBuilder copy = new ManualBidBuilder();
		copy.auction = this.auction;
		copy.user = this.user;
		
		return copy;
	}
	private Boolean hasEverything() {
		return auction.isPresent() && user.isPresent();
	}
}
	