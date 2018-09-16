package unq.desapp.grupo_f.backend.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import unq.desapp.grupo_f.backend.model.auction.Auction;
import unq.desapp.grupo_f.backend.model.bid.AutomaticBid;
import unq.desapp.grupo_f.backend.model.bid.Bid;
import unq.desapp.grupo_f.backend.model.bid.ManualBid;
import unq.desapp.grupo_f.backend.model.exceptions.IncorrectParameterException;
import unq.desapp.grupo_f.backend.model.exceptions.UserException;

public class User {
	
	
	private String name;
	private String surname;
	private String email;
	private String password;
	private LocalDate birthDate;
	private List<Auction> auctions;
	private List<Auction> myAuctions;
	
	public User() {
		this.name = "";
		this.surname = "";
		this.email = "";
		this.password = "";
		this.birthDate = LocalDate.now();
		this.auctions = new ArrayList<Auction>();
		this.myAuctions = new ArrayList<Auction>();
	}
	
	/* ******************************
	 * 			Getters				*
	 ********************************/
	public String getName() {
		return name;
	}
	public String getSurname() {
		return surname;
	}
	public String getEmail() {
		return email;
	}	
	public String getPassword() {
		return password;
	}	
	public LocalDate getBirthDate() {
		return birthDate;
	}
	public List<Auction> getAuctions(){
		return this.auctions;
	}
	public List<Auction> getMyAuctions(){
		return this.myAuctions;
	}
	
	/* ******************************
	 * 			Setters				*
	 ********************************/
	
	public void setName(String name) {
		if(name.length() > 30) {
			throw new IncorrectParameterException("The parameter Name for the User, must be less or equal than 30 characters");
		}
		this.name = name;
	}
	public void setSurname(String surname) {
		if(surname.length() > 30) {
			throw new IncorrectParameterException("The parameter Surname for the User, must be less or equal than 30 characters");
		}
		this.surname = surname;
	}
	public void setEmail(String email) {
		//TODO: Comprobar si el email es valido, en lo posible generar la clase Email, que sepa como hacerlo
		this.email = email;
	}
	public void setPassword(String password) {
		if(password.length() >= 10 || password.length() <= 4) {
			throw new IncorrectParameterException("The parameter password for the User, must be less or equal than 10 and more or equal than 4 characters");
		}
		this.password = password;
	}
	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	/* ******************************
	 * 		  Public Methods		*
	 ********************************/
	public void createAuction(Auction auction) {
		this.myAuctions.add(auction);
	}
	public void submitManualBid(Auction auction) {
		this.submitBid(auction);
		Bid bid = new ManualBid(auction, this);
		auction.addBid(bid);
	}
	
	public void submitAutomaticBid(Auction auction, Integer autoBiddingLimit) {
		this.submitBid(auction);
		Bid bid = new AutomaticBid(auction, this, autoBiddingLimit);
		auction.addBid(bid);
	}

	public void closeAuction(Auction auction) {
		if(!this.isMine(auction)) {
			throw new UserException("This user does not own the auction");
		}
		auction.closeAuction();
	}
	
	/* ******************************
	 * 		  Private Methods		*
	 ********************************/
	
	private void submitBid(Auction auction) {
		if(!haveParticipatedIn(auction) && !isMine(auction)) {
			this.addAuction(auction);
		}
	}

	private Boolean isMine(Auction auction) {
		return this.myAuctions.contains(auction);
	}

	private Boolean haveParticipatedIn(Auction auction) {
		return this.auctions.contains(auction);
	}

	public Boolean canStartAnAuction() {
		return this.auctions.stream().filter(auct -> auct.isInProgress()).count() <= 5;
	}
	private void addAuction(Auction auction) {
		this.auctions.add(auction);
	}


}
