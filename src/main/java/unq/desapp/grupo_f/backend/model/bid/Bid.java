package unq.desapp.grupo_f.backend.model.bid;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

import unq.desapp.grupo_f.backend.model.User;
import unq.desapp.grupo_f.backend.model.auction.Auction;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@bidId", scope= Bid.class)
@JsonTypeInfo(use = com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME
			 ,include = JsonTypeInfo.As.PROPERTY
			 ,property = "type")
@JsonSubTypes({@Type(value = ManualBid.class)
			  ,@Type(value = AutomaticBid.class)})
public abstract class Bid {
	

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    
    @ManyToOne
	protected User user;
    @ManyToOne
	protected Auction auction;
	protected Integer price;
	
	public Bid(Auction auction, User user) {
		this.user = user;
		this.auction = auction;
		this.price = 0;
	}
	public Bid() {}
	public User getUser() {
		return user;
	}
	public Auction getAuction() {
		return auction;
	}
	public Integer getPrice() {
		return this.price;
	}
	
	public abstract Boolean canAutoBid(Integer actualPriceOfAuction);
	public abstract Integer getBiddingLimit();
	public abstract void autoBid();
	public void setPrice(Integer nextPrice) {
		this.price = nextPrice;
	}

}
