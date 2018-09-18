package unq.desapp.grupo_f.backend.model.builders;

import java.time.LocalDate;
import java.time.LocalDateTime;

import unq.desapp.grupo_f.backend.model.User;
import unq.desapp.grupo_f.backend.model.auction.Auction;
import unq.desapp.grupo_f.backend.model.exceptions.AuctionBuilderException;

public class AuctionBuilder {
	
	private Auction auction;
	private Boolean hasTitle;
	private Boolean hasDescription;
	private Boolean hasInitialPrice;
	private Boolean hasStartDate;
	private Boolean hasEndDate;
	
	
	public AuctionBuilder(User ownerOfAuction) {
		this.auction = new Auction(ownerOfAuction);
		this.hasTitle		= false;
		this.hasDescription	= false;
		this.hasInitialPrice= false;
		this.hasStartDate	= false;
		this.hasEndDate		= false;
	}


	/* ******************************
	 * 		  Public Methods		*
	 ********************************/
	
	public AuctionBuilder setTitle(String title) {
		AuctionBuilder copy = this.copy();
		copy.auction.setTitle(title);
		copy.hasTitle = true;
		return copy;
	}
	public AuctionBuilder setDescription(String description) {
		AuctionBuilder copy = this.copy();
		copy.auction.setDescription(description);
		copy.hasDescription = true;
		return copy;
	}
	public AuctionBuilder setInitialPrice(Integer initialPrice) {
		AuctionBuilder copy = this.copy();
		copy.auction.setInitialPrice(initialPrice);
		copy.hasInitialPrice = true;
		return copy;
	}
	public AuctionBuilder setStartDate(LocalDate startDate) {
		AuctionBuilder copy = this.copy();
		copy.auction.setStartDate(startDate);
		copy.hasStartDate = true;
		return copy;
	}
	public AuctionBuilder setEndDate(LocalDateTime endDate) {
		AuctionBuilder copy = this.copy();
		copy.auction.setEndDate(endDate);
		copy.hasEndDate = true;
		return copy;
	}
	public AuctionBuilder setDirection(String direction) {
		AuctionBuilder copy = this.copy();
		copy.auction.setDirection(direction);
		return copy;
	}
	
	public Auction build() {
		if(!this.hasEverything()) {
			throw new AuctionBuilderException("An auction needs a title, description"
											+ ", an initial price, start date, and finish date. "
											+ "One or more of them are missing");
		}
		return auction;
	}


	/* ******************************
	 * 		  Private Methods		*
	 ********************************/
	
	private AuctionBuilder copy() {
		AuctionBuilder copy = new AuctionBuilder(auction.getOwner());
		copy.auction.setTitle(this.auction.getTitle());
		copy.auction.setDescription(this.auction.getDescription());
		copy.auction.setDirection(this.auction.getDirection());
		copy.auction.setInitialPrice(this.auction.getInitialPrice());
		copy.auction.setStartDate(this.auction.getStartDate());
		copy.auction.setEndDate(this.auction.getEndDate());
		
		copy.hasTitle = this.hasTitle;
		copy.hasDescription = this.hasDescription;
		copy.hasInitialPrice = this.hasInitialPrice;
		copy.hasStartDate = this.hasStartDate;
		copy.hasEndDate = this.hasEndDate;
		
		return copy;
	}
	private Boolean hasEverything() {
		return hasTitle && hasDescription && hasInitialPrice && hasStartDate && hasEndDate;
	}

}
