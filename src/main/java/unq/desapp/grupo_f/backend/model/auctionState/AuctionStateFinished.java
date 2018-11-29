package unq.desapp.grupo_f.backend.model.auctionState;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import unq.desapp.grupo_f.backend.model.Auction;
import unq.desapp.grupo_f.backend.model.bid.Bid;
import unq.desapp.grupo_f.backend.model.exceptions.AuctionStateException;

@Entity
public class AuctionStateFinished extends AuctionState {

	private static AuctionStateFinished instance; 
	
	private AuctionStateFinished() {}
	static public AuctionStateFinished getInstance() {
		if(instance == null) {
			instance = new AuctionStateFinished();
		}
		return instance;
	}
	
	@JsonIgnore
	@Override
	public Boolean isNew() {
		return false;
	}
	
	@JsonIgnore
	@Override
	public Boolean isInProgress() {
		return false;
	}
	
	@JsonIgnore
	@Override
	public Boolean isFinished() {
		return true;
	}
	
	@JsonIgnore
	@Override
	public Boolean isClosed() {
		return false;
	}

	@Override
	public void addBidForAuction(Auction auction, Bid bid) {
		throw new AuctionStateException("You can not bid in a finished auction");
		
	}

	@Override
	public String getName() {
		return Auction.States.Finished.toString();
	}

}
