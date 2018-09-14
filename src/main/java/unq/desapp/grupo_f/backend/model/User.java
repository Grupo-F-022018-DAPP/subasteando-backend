package unq.desapp.grupo_f.backend.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import unq.desapp.grupo_f.backend.model.auction.Auction;
import unq.desapp.grupo_f.backend.model.exceptions.IncorrectParameterException;

public class User {
	
	
	private String name;
	private String surname;
	private String email;
	private String password;
	private LocalDate birthDate;
	private List<Auction> auctions;
	
	public User() {
		this.name = "";
		this.surname = "";
		this.email = "";
		this.password = "";
		this.birthDate = LocalDate.now();
		this.auctions = new ArrayList<Auction>();
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
		//TODO: devolver lista no modificable o copia
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
		if(password.length() > 10) {
			throw new IncorrectParameterException("The parameter password for the User, must be less or equal than 10 characters");
		}
		this.password = password;
	}
	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}
	public void addAuction(Auction auction) {
		this.auctions.add(auction);
	}
	public void removeAuction(Auction auction) {
		this.auctions.remove(auction);
		//TODO: ¿ auction.delete ?
	}

	/* ******************************
	 * 		  Public Methods		*
	 ********************************/
	
	public void SubmitManualBid(Auction auction) {
		Bid bid = new ManualBid(auction, this);
		auction.addBid(bid);
	}
	
	public void SubmitAutomaticBid(Auction auction, Integer autoBiddingLimit) {
		Bid bid = new AutomaticBid(auction, this, autoBiddingLimit);
		auction.addBid(bid);
	}
	
	/* ******************************
	 * 		  Private Methods		*
	 ********************************/




}
