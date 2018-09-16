package unq.desapp.grupo_f.backend.model.auction;

import unq.desapp.grupo_f.backend.model.bid.Bid;

public class AuctionStateInProgress implements AuctionState {

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
		bid.setPrice(auction.getNextPrice());
		auction.setActualPrice(auction.getNextPrice());
		auction.getBiddings().stream().filter(bidding -> bidding.canAutoBid(auction.getActualPrice()))
							  .sorted((bid1, bid2) -> bid1.getBiddingLimit().compareTo(bid2.getBiddingLimit()))
							  .findFirst().ifPresent(bidding -> bidding.autoBid());
	}

}
