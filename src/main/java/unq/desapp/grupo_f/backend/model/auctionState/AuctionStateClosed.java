package unq.desapp.grupo_f.backend.model.auctionState;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import unq.desapp.grupo_f.backend.model.Auction;
import unq.desapp.grupo_f.backend.model.bid.Bid;
import unq.desapp.grupo_f.backend.model.exceptions.AuctionStateException;

@Entity
public class AuctionStateClosed extends AuctionState {

	private static AuctionStateClosed instance; 
	
	private AuctionStateClosed() {
		id=4;
	}
	static public AuctionStateClosed getInstance() {
		if(instance == null) {
			instance = new AuctionStateClosed();
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
		return false;
	}
	@JsonIgnore
	@Override
	public Boolean isClosed() {
		return true;
	}
	

	@Override
	public void addBidForAuction(Auction auction, Bid bid) {
		this.imClosed();

	}
	private void imClosed(){
		throw new AuctionStateException("This auction is closed");
	}

	@Override
	public String getName() {
		return Auction.States.Closed.toString();
	}


}
