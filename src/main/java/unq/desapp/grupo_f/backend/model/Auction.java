package unq.desapp.grupo_f.backend.model;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import unq.desapp.grupo_f.backend.model.auctionState.AuctionState;
import unq.desapp.grupo_f.backend.model.auctionState.AuctionStateClosed;
import unq.desapp.grupo_f.backend.model.auctionState.AuctionStateFinished;
import unq.desapp.grupo_f.backend.model.auctionState.AuctionStateInProgress;
import unq.desapp.grupo_f.backend.model.auctionState.AuctionStateNew;
import unq.desapp.grupo_f.backend.model.bid.Bid;
import unq.desapp.grupo_f.backend.model.exceptions.AuctionStateException;
import unq.desapp.grupo_f.backend.model.exceptions.IncorrectParameterException;

@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Auction {
	public static enum States {New , InProgress, Closed, Finished}

    @Id
    @GeneratedValue(strategy=GenerationType.TABLE)
    private Integer id;
    
	private String title;
	private String description;
	private String direction; //TODO: Reemplazar por una direccion de googlemaps
	@ElementCollection
	@CollectionTable(name="PICTURES")
	private List<URL> pictures;
	private Integer initialPrice;
	private LocalDate startDate;
	private LocalDateTime endDate;
	@OneToOne(cascade= CascadeType.ALL)
	private AuctionState state;
	
	@OneToMany(targetEntity=Bid.class, mappedBy="auction", cascade= CascadeType.ALL)
	@JsonIgnore
	private List<Bid> biddings;
	private Integer actualPrice;
	
	
	@ManyToOne(cascade= CascadeType.ALL)
	@JsonIgnore
	private User owner;
	
	public Auction() {
		this.title = "";
		this.description = "";
		this.direction = "";
		this.pictures = new ArrayList<URL>();
		this.initialPrice = 0;
		this.startDate = LocalDate.now().plusDays(1l);
		this.endDate = LocalDateTime.now().plusYears(1l);
		this.state = new AuctionStateNew();
		this.biddings = new ArrayList<Bid>();
		this.actualPrice = 0;
	}
	

	/* ******************************
	 * 			Getters				*
	 ********************************/
	public List<Integer> getBiddingsIds() {
		List<Integer> ids = new ArrayList<Integer>();
		for(Bid bid: this.biddings) {
			ids.add(bid.getId());
		}
		return ids;
	}
	public Integer getOwnerId() {
		return this.owner.getId();
	}
	
	public String getTitle() {
		return title;
	}
	public String getDescription() {
		return description;
	}
	public String getDirection() {
		return direction;
	}
	public List<URL> getPictures() {
		return pictures;
	}
	public Integer getInitialPrice() {
		return initialPrice;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public LocalDateTime getEndDate() {
		return endDate;
	}	
	public AuctionState getState() {
		return state;
	}
	public Integer getActualPrice() {
		return actualPrice;
	}
	public List<Bid> getBiddings() {
		return this.biddings;
	}
	public User getOwner() {
		return owner;
	}
	public Integer getId() {
		return this.id;
	}
	

	/* ******************************
	 * 			Setters				*
	 ********************************/
	
	public void setTitle(String title) {
		if(title.length() < 10 || title.length() > 50) {
			throw new IncorrectParameterException("The Title for the Auction, must be more than 10 and less than 50 characters.");
		}
		this.title = title;
	}
	public void setDescription(String description) {
		if(description.length() < 10 || description.length() > 100) {
			throw new IncorrectParameterException("The Description for the Auction, must be more than 10 and less than 100 characters");
		}
		this.description = description;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public void addPicture(URL picture) {
		this.pictures.add(picture);
	}
	public void removePicture(URL picture) {
		this.pictures.remove(picture);
	}
	public void setInitialPrice(Integer initialPrice) {
		if(!this.state.isNew()) {
			throw new AuctionStateException("This auction has already started. It is not possible to change the Initial price");
		}
		this.initialPrice = initialPrice;
		this.actualPrice = initialPrice;
	}
	public void setStartDate(LocalDate startDate) {
		if(!this.state.isNew()) {
			throw new AuctionStateException("This auction has already started. Is is not possible to change the Start date.");
		}
		if(!startDate.isAfter(LocalDate.now())
				|| !startDate.isBefore(this.endDate.toLocalDate().minusDays(1l))) {
			throw new IncorrectParameterException("The Start date for the Auction, must be after today, and must be 2 diays before the end Date.");
		}
		
		this.startDate = startDate;
	}
	public void setEndDate(LocalDateTime endDate) {
		if(!this.startDate.isBefore(endDate.toLocalDate().minusDays(1l))) {
			throw new IncorrectParameterException("The End date for the Auction, must be 2 days after the start date, at minimum");
		}
		this.endDate = endDate;
	}
	public void setActualPrice(Integer actualPrice) {
		this.actualPrice = actualPrice;
	}
	
	public void setOwner(User owner) {
		this.owner = owner;
	}

	public void setId(Integer auctionId) {
		this.id = auctionId;
	}
	
	public void setState(AuctionState state) {
		this.state = state;
	}
	

	/* ******************************
	 * 		  Public Methods		*
	 ********************************/

	@JsonIgnore
	public Boolean isNew() {
		return this.state.isNew();
	}
	@JsonIgnore
	public Boolean isInProgress(){
		return this.state.isInProgress();
	}
	@JsonIgnore
	public Boolean isFinished(){
		return this.state.isFinished();
	}
	public void startAuction() {
		/*TODO: 5 subastas en progreso
		 * Actualmente, si el dueño tiene 5 o mas subastas en proceso, esta subasta no comienza. 
		 * Deberia agregarse a una cola para comenzarla mas tarde 
		 */
		if(this.isNew() && this.owner.canStartAnAuction()) {
			this.state = new AuctionStateInProgress();
		}else {
			throw new AuctionStateException("This auction cant start");
		}
	}
	public void finishAuction() {
		if(this.isInProgress()) {
			this.state = new AuctionStateFinished();
		}else {
			throw new AuctionStateException("An auction that isnt in progress, can not finish");
		}
	}
	public void closeAuction() {
		if(this.isFinished()) {
			throw new AuctionStateException("An auction that has already finished, can not close");
		}
		this.state = new AuctionStateClosed();
	}

	public void addBid(Bid bid) {
		this.state.addBidForAuction(this, bid);
		/*
		 * La logica para agregar una oferta en la subasta, esta en la clase de estado AuctionStateInProgress 
		 */
	}

	public Integer getNextPrice() {
		return this.actualPrice + (this.initialPrice / 100 * 5);
	}


	public void changeStateTo(States state) {
		if(state == States.InProgress && this.state.isNew()) {
			this.startAuction();
		}
		if(state == States.Finished && this.state.isInProgress()) {
			this.finishAuction();
		}
		if(state == States.Closed && !this.state.isFinished()) {
			this.closeAuction();
		}
	}






	

	/* ******************************
	 * 		  Private Methods		*
	 ********************************/
	
}
