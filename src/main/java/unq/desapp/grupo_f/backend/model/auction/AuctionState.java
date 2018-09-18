package unq.desapp.grupo_f.backend.model.auction;

import unq.desapp.grupo_f.backend.model.bid.Bid;

public interface AuctionState {
	public Boolean isNew();
	public Boolean isInProgress();
	public Boolean isFinished();
	public Boolean isClosed();
	public void addBidForAuction(Auction auction, Bid bid);
}
