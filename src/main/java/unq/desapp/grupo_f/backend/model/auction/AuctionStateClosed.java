package unq.desapp.grupo_f.backend.model.auction;

import unq.desapp.grupo_f.backend.model.bid.Bid;
import unq.desapp.grupo_f.backend.model.exceptions.AuctionStateException;

public class AuctionStateClosed implements AuctionState {

	@Override
	public Boolean isNew() {
		return false;
	}

	@Override
	public Boolean isInProgress() {
		return false;
	}

	@Override
	public Boolean isFinished() {
		return false;
	}

	@Override
	public void addBidForAuction(Auction auction, Bid bid) {
		this.imClosed();

	}
	private void imClosed(){
		throw new AuctionStateException("This auction is closed");
	}
	

}
