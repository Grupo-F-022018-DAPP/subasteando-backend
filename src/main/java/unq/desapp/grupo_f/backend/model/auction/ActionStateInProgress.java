package unq.desapp.grupo_f.backend.model.auction;

import unq.desapp.grupo_f.backend.model.bid.Bid;
import unq.desapp.grupo_f.backend.model.exceptions.AuctionStateException;

public class ActionStateInProgress implements AuctionState {

	@Override
	public Boolean isNew() {
		return false;
	}

	@Override
	public Boolean isInProgress() {
		return true;
	}

	@Override
	public Boolean isFinished() {
		return false;
	}

	@Override
	public void addBidForAuction(Auction auction, Bid bid) {
		auction.getBiddings().stream().filter(bidding -> bidding.canAutoBid(auction.getActualPrice()))
							  .sorted((bid1, bid2) -> bid1.getBiddingLimit().compareTo(bid2.getBiddingLimit())) //TODO: implement Comparable in Bid class
							  .findFirst().ifPresent(bidding -> bidding.autoBid());
	}

}
