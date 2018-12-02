package unq.desapp.grupo_f.backend.model.auctionState;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import unq.desapp.grupo_f.backend.model.Auction;
import unq.desapp.grupo_f.backend.model.Auction.States;
import unq.desapp.grupo_f.backend.model.bid.Bid;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "state_type")
public abstract class AuctionState{
	
    @Id
	protected Integer id;
	public abstract Boolean isNew();
	public abstract Boolean isInProgress();
	public abstract Boolean isFinished();
	public abstract Boolean isClosed();
	public abstract void addBidForAuction(Auction auction, Bid bid);
	@JsonIgnore
	public abstract States getEnum();
	public String getName() {
		return getEnum().toString();
	}
	public static AuctionState stateFor(Auction auction, Auction.States currentState) {		
		if(currentState == Auction.States.Closed) {
			return AuctionStateClosed.getInstance();
		}
		LocalDateTime now = LocalDateTime.now();
		LocalDate start = auction.getStartDate();
		LocalDateTime end = auction.getEndDate();
		
		Boolean shouldBeInProgress = start.isBefore(now.toLocalDate()) && end.isAfter(now);
		if(shouldBeInProgress) {
			return AuctionStateInProgress.getInstance();
		}		
		
		Boolean shouldBeFinished = end.isBefore(now);
		if(shouldBeFinished) {
			return AuctionStateFinished.getInstance();
		}		
		
		return AuctionStateNew.getInstance();
	}
	
}
