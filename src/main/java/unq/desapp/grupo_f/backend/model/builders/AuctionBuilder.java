package unq.desapp.grupo_f.backend.model.builders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import unq.desapp.grupo_f.backend.model.Auction;
import unq.desapp.grupo_f.backend.model.User;
import unq.desapp.grupo_f.backend.model.exceptions.AuctionBuilderException;
import unq.desapp.grupo_f.backend.model.utils.RandomStrings;

public class AuctionBuilder {
	
	private Optional<String> 		title;
	private Optional<String> 		description;
	private Optional<Integer> 		initialPrice;
	private Optional<LocalDate> 	startDate;
	private Optional<LocalDateTime> endDate;
	private Optional<User> 			owner;
	private Optional<String> 		direction;
	
	
	public AuctionBuilder() {
		this.title 			= Optional.empty();
		this.description 	= Optional.empty();
		this.initialPrice 	= Optional.empty();
		this.startDate 		= Optional.empty();
		this.endDate 		= Optional.empty();
		this.owner 			= Optional.empty();
		this.direction 		= Optional.empty();
	}


	/* ******************************
	 * 		 Getters and Setters	*
	 ********************************/
	
	public AuctionBuilder setTitle(String title) {
		AuctionBuilder copy = this.copy();
		copy.title = Optional.of(title);
		return copy;
	}
	public AuctionBuilder setDescription(String description) {
		AuctionBuilder copy = this.copy();
		copy.description = Optional.of(description);
		return copy;
	}
	public AuctionBuilder setInitialPrice(Integer initialPrice) {
		AuctionBuilder copy = this.copy();
		copy.initialPrice = Optional.of(initialPrice);
		return copy;
	}
	public AuctionBuilder setStartDate(LocalDate startDate) {
		AuctionBuilder copy = this.copy();
		copy.startDate = Optional.of(startDate);
		return copy;
	}
	public AuctionBuilder setEndDate(LocalDateTime endDate) {
		AuctionBuilder copy = this.copy();
		copy.endDate = Optional.of(endDate);
		return copy;
	}
	public AuctionBuilder setDirection(String direction) {
		AuctionBuilder copy = this.copy();
		copy.direction = Optional.of(direction);
		return copy;
	}
	public AuctionBuilder setOwner(User owner) {
		AuctionBuilder copy = this.copy();
		copy.owner = Optional.of(owner);
		return copy;
	}

	/* ******************************
	 * 		 Public methods			*
	 ********************************/
	
	
	public Auction build() {
		if(!this.hasEverything()) {
			throw new AuctionBuilderException("An auction needs a title, description"
											+ ", an initial price, start date, finish date"
											+ ", and owner. One or more of them are missing");
		}
		Auction auction = new Auction();
		
		auction.setTitle(title.get());
		auction.setDescription(description.get());
		auction.setInitialPrice(initialPrice.get());
		
		auction.areCorrectDates(startDate.get(), endDate.get());
		
		auction.setEndDate(endDate.get());
		auction.setStartDate(startDate.get());
		auction.setOwner(owner.get());
		direction.ifPresent(direction -> auction.setDirection(direction));
		return auction;
	}
	
	public Auction buildRandom() {
		Auction randomAuction = new Auction();
		Random random = new Random();
		String possibleChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		randomAuction.setInitialPrice(random.nextInt(1000)  + 10);
		randomAuction.setDescription(RandomStrings.generateRandomString(random, possibleChars, 15));
		randomAuction.setDirection(RandomStrings.generateRandomString(random, possibleChars, 10));
		randomAuction.setStartDate(LocalDate.now().plusDays(random.nextInt(29)));
		randomAuction.setEndDate(LocalDateTime.now().plusMonths(random.nextInt(12)));
		randomAuction.setTitle(RandomStrings.generateRandomString(random, possibleChars, 11));
		randomAuction.setOwner(new UserBuilder().buildRandom());
		
		return randomAuction;
	}
	


	/* ******************************
	 * 		  Private Methods		*
	 ********************************/
	
	private AuctionBuilder copy() {
		AuctionBuilder copy = new AuctionBuilder();
		copy.title = this.title;
		copy.description = this.description;
		copy.initialPrice = this.initialPrice;
		copy.startDate = this.startDate;
		copy.endDate = this.endDate;
		copy.owner = this.owner;
		copy.direction = this.direction;
		
		return copy;
	}
	private Boolean hasEverything() {
		return title.isPresent() && description.isPresent() && 
			   initialPrice.isPresent() && startDate.isPresent() && 
			   endDate.isPresent() && owner.isPresent();
	}

}
