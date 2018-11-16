package unq.desapp.grupo_f.backend.model.builders;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Random;

import unq.desapp.grupo_f.backend.model.Auction;
import unq.desapp.grupo_f.backend.model.User;
import unq.desapp.grupo_f.backend.model.exceptions.AuctionBuilderException;
import unq.desapp.grupo_f.backend.model.exceptions.IncorrectParameterException;

public class AuctionBuilder {
	
	private Auction auction;
	private enum Property {Title, Description, InitialPrice, StartDate, EndDate, Owner}
	private HashMap<Property, Boolean> hasProperty;
	
	
	public AuctionBuilder() {
		this.auction = new Auction();
		this.hasProperty = new HashMap<>();
		this.hasProperty.put(Property.Title, false);
		this.hasProperty.put(Property.Description, false);
		this.hasProperty.put(Property.InitialPrice, false);
		this.hasProperty.put(Property.StartDate, false);
		this.hasProperty.put(Property.EndDate, false);
		this.hasProperty.put(Property.Owner, false);
	}


	/* ******************************
	 * 		 Getters and Setters	*
	 ********************************/
	
	public AuctionBuilder setTitle(String title) {
		AuctionBuilder copy = this.copy();
		copy.auction.setTitle(title);
		copy.hasProperty.put(Property.Title, true);
		return copy;
	}
	public AuctionBuilder setDescription(String description) {
		AuctionBuilder copy = this.copy();
		copy.auction.setDescription(description);
		copy.hasProperty.put(Property.Description, true);
		return copy;
	}
	public AuctionBuilder setInitialPrice(Integer initialPrice) {
		AuctionBuilder copy = this.copy();
		copy.auction.setInitialPrice(initialPrice);
		copy.hasProperty.put(Property.InitialPrice, true);
		return copy;
	}
	public AuctionBuilder setStartDate(LocalDate startDate) {
		if(startDate == null) { throw new IncorrectParameterException("Please set a start date for the auction");}
		AuctionBuilder copy = this.copy();
		copy.auction.setStartDate(startDate);
		copy.hasProperty.put(Property.StartDate, true);
		return copy;
	}
	public AuctionBuilder setEndDate(LocalDateTime endDate) {
		if(endDate == null) { throw new IncorrectParameterException("Please set a end date for the auction");}
		AuctionBuilder copy = this.copy();
		copy.auction.setEndDate(endDate);
		copy.hasProperty.put(Property.EndDate, true);
		return copy;
	}
	public AuctionBuilder setDirection(String direction) {
		AuctionBuilder copy = this.copy();
		copy.auction.setDirection(direction);
		return copy;
	}
	public AuctionBuilder setOwner(User owner) {
		AuctionBuilder copy = this.copy();
		copy.auction.setOwner(owner);
		copy.hasProperty.put(Property.Owner, true);
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
		return auction;
	}
	
	public Auction buildRandomAuction() {
		Auction randomAuction = new Auction();
		Random random = new Random();
		String possibleChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		randomAuction.setInitialPrice(random.nextInt(1000)  + 10);
		randomAuction.setDescription(this.generateRandomString(random, possibleChars, 15));
		randomAuction.setDirection(this.generateRandomString(random, possibleChars, 10));
		randomAuction.setStartDate(LocalDate.now().plusDays(random.nextInt(29)));
		randomAuction.setEndDate(LocalDateTime.now().plusMonths(random.nextInt(12)));
		randomAuction.setTitle(this.generateRandomString(random, possibleChars, 11));
		randomAuction.setOwner(new UserBuilder().buildRandomUser());
		
		return randomAuction;
	}
	


	/* ******************************
	 * 		  Private Methods		*
	 ********************************/
	
	private AuctionBuilder copy() {
		AuctionBuilder copy = new AuctionBuilder();
		if(this.hasProperty.get(Property.Title)) {
			copy.auction.setTitle(this.auction.getTitle());
		}
		if(this.hasProperty.get(Property.Description)) {
			copy.auction.setDescription(this.auction.getDescription());
		}
		if(this.hasProperty.get(Property.InitialPrice)) {
			copy.auction.setInitialPrice(this.auction.getInitialPrice());
		}
		if(this.hasProperty.get(Property.StartDate)) {
			copy.auction.setStartDate(this.auction.getStartDate());
		}
		if(this.hasProperty.get(Property.EndDate)) {
			copy.auction.setEndDate(this.auction.getEndDate());
		}
		if(this.hasProperty.get(Property.Owner)) {
			copy.auction.setOwner(this.auction.getOwner());
		}
		for (Property prop : Property.values()) {
			copy.hasProperty.put(prop, this.hasProperty.get(prop));
		}
		copy.auction.setDirection(this.auction.getDirection());
		
		return copy;
	}
	private Boolean hasEverything() {
		Boolean hasEverything = true;
		for(Boolean bool: this.hasProperty.values()) {
			hasEverything = hasEverything && bool; 
		}
		return hasEverything;
	}
	
	private String generateRandomString(Random random, String possibleChars, Integer length) {
		char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = possibleChars.charAt(random.nextInt(possibleChars.length()));
        }
        return new String(text);
	}

}
