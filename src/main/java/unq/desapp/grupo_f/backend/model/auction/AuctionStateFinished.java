package unq.desapp.grupo_f.backend.model.auction;

import unq.desapp.grupo_f.backend.model.bid.Bid;
import unq.desapp.grupo_f.backend.model.exceptions.AuctionStateException;

public class AuctionStateFinished implements AuctionState {

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
		return true;
	}

	@Override
	public void addBidForAuction(Auction auction, Bid bid) {
		throw new AuctionStateException("You can not bid in a finished auction");
		
	}

}
