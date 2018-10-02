package unq.desapp.grupo_f.backend.model.auction;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

import unq.desapp.grupo_f.backend.model.bid.Bid;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "state_type")
public abstract class AuctionState {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	public abstract Boolean isNew();
	public abstract Boolean isInProgress();
	public abstract Boolean isFinished();
	public abstract Boolean isClosed();
	public abstract void addBidForAuction(Auction auction, Bid bid);
}
