package unq.desapp.grupo_f.backend.model.bid;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

import unq.desapp.grupo_f.backend.model.Auction;
import unq.desapp.grupo_f.backend.model.User;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@JsonTypeInfo(use = com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME
			 ,include = JsonTypeInfo.As.PROPERTY
			 ,property = "type")
@JsonSubTypes({@Type(value = ManualBid.class)
			  ,@Type(value = AutomaticBid.class)})
public abstract class Bid {
	

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Integer id;
    
    @ManyToOne(cascade= CascadeType.ALL)
	@JsonIgnoreProperties({ "password", "auctionsIds", "myAuctionsIds", "birthDate", "email"})
	//@JsonIgnore
	protected User user;
    @ManyToOne(cascade= CascadeType.ALL)
    @JsonIgnore
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
	public Integer getId() {
		return this.id;
	}
	
	public Integer getUserId() {
		return this.user.getId();
	}
	public Integer getAuctionId() {
		return this.auction.getId();
	}

}
