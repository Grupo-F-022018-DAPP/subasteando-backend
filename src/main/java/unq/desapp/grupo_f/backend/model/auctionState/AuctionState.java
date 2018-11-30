package unq.desapp.grupo_f.backend.model.auctionState;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import unq.desapp.grupo_f.backend.model.Auction;
import unq.desapp.grupo_f.backend.model.bid.Bid;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "state_type")
public abstract class AuctionState{
	
    @Id
	protected Integer id;
    public abstract String 	getName();
	public abstract Boolean isNew();
	public abstract Boolean isInProgress();
	public abstract Boolean isFinished();
	public abstract Boolean isClosed();
	public abstract void addBidForAuction(Auction auction, Bid bid);
	public static AuctionState stateFor(Auction auction, AuctionState currentState) {
		if(currentState.isClosed()) {
			return currentState;
		}
		LocalDateTime now = LocalDateTime.now();
		LocalDate start = auction.getStartDate();
		LocalDateTime end = auction.getEndDate();
		
		Boolean shouldBeInProgress = start.isBefore(now.toLocalDate()) && end.isAfter(now);
		if(!currentState.isInProgress() && shouldBeInProgress) {
			return AuctionStateInProgress.getInstance();
		}
		Boolean shouldBeFinished = end.isBefore(now);
		if(!currentState.isFinished() && shouldBeFinished) {
			return AuctionStateFinished.getInstance();
		}
		
		return currentState;
	}
	
}
