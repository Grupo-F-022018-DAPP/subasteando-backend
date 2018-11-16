package unq.desapp.grupo_f.backend.model.auctionState;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import unq.desapp.grupo_f.backend.model.Auction;
import unq.desapp.grupo_f.backend.model.bid.Bid;

@Entity
public class AuctionStateInProgress extends AuctionState {

	@JsonIgnore
	@Override
	public Boolean isNew() {
		return false;
	}

	@JsonIgnore
	@Override
	public Boolean isInProgress() {
		return true;
	}

	@JsonIgnore
	@Override
	public Boolean isFinished() {
		return false;
	}
	
	@JsonIgnore
	@Override
	public Boolean isClosed() {
		return false;
	}

	@Override
	public void addBidForAuction(Auction auction, Bid bid) {
		auction.getBiddings().add(bid);
		bid.setPrice(auction.getNextPrice());
		auction.setActualPrice(auction.getNextPrice());
		auction.getBiddings().stream().filter(bidding -> bidding.canAutoBid(auction.getNextPrice()) 
													 && !bidding.getUser().equals(bid.getUser()))
							  .sorted((bid1, bid2) -> bid1.getBiddingLimit().compareTo(bid2.getBiddingLimit()))
							  .findFirst().ifPresent(bidding -> bidding.autoBid());
	}

	@Override
	public String getName() {
		return Auction.States.InProgress.toString();
	}

}
