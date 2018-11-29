package unq.desapp.grupo_f.backend.model.auctionState;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import unq.desapp.grupo_f.backend.model.Auction;
import unq.desapp.grupo_f.backend.model.bid.Bid;
import unq.desapp.grupo_f.backend.model.exceptions.AuctionStateException;

@Entity
public class AuctionStateNew extends AuctionState {


	private static AuctionStateNew instance; 
	
	private AuctionStateNew() {}
	static public AuctionStateNew getInstance() {
		if(instance == null) {
			instance = new AuctionStateNew();
		}
		return instance;
	}
	
	
	@JsonIgnore
	@Override
	public Boolean isNew() {
		return true;
	}

	@JsonIgnore
	@Override
	public Boolean isInProgress() {
		return false;
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
		throw new AuctionStateException("You can not bid in a auction that has not started yet");
		
	}

	@Override
	public String getName() {
		return Auction.States.New.toString();	
	}

}
