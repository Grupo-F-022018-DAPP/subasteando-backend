package unq.desapp.grupo_f.backend.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import unq.desapp.grupo_f.backend.model.bid.AutomaticBid;
import unq.desapp.grupo_f.backend.model.bid.Bid;
import unq.desapp.grupo_f.backend.model.bid.ManualBid;
import unq.desapp.grupo_f.backend.model.exceptions.IncorrectParameterException;
import unq.desapp.grupo_f.backend.model.exceptions.UserException;

@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class User {
	

	@Id
    @GeneratedValue(strategy=GenerationType.TABLE)
    private Integer id;
    
	private String name;
	private String surname;
	private String email;
	private String password;
	private LocalDate birthDate;
	@ManyToMany(targetEntity= Auction.class, cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Auction> auctions;
	@OneToMany(targetEntity= Auction.class, mappedBy="owner", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Auction> myAuctions;
	
	//UserDetails
	//private Boolean enabled;
	
	public User() {
		this.name		= "";
		this.surname	= "";
		this.email		= "";
		this.password	= "";
		this.birthDate	= LocalDate.now();
		this.auctions	= new ArrayList<Auction>();
		this.myAuctions	= new ArrayList<Auction>();
		
		//this.enabled 				= true;
	}
	
	/* ******************************
	 * 			Getters				*
	 ********************************/
	public List<Integer> getAuctionsIds(){
		List<Integer> ids = new ArrayList<Integer>();
		for(Auction auct: this.auctions) {
			ids.add(auct.getId());
		}
		return ids;
	}
	public List<Integer> getMyAuctionsIds(){
		List<Integer> ids = new ArrayList<Integer>();
		for(Auction auct: this.myAuctions) {
			ids.add(auct.getId());
		}
		return ids;
	}
	
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
	public Integer getId(){
		return this.id;
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
		if(password.length() > 10 || password.length() < 4) {
			throw new IncorrectParameterException("The parameter password for the User, must be less or equal than 10, and more or equal than 4 characters");
		}
		this.password = password;
	}
	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}
	public void setAuctions(List<Auction> auctions) {
		this.auctions = auctions;
	}
	public void setMyAuctions(List<Auction> myAuctions) {
		this.myAuctions = myAuctions;
	}

	public void setId(Integer userId) {
		this.id = userId;
	}

	/* ******************************
	 * 		  Public Methods		*
	 ********************************/
	public void createAuction(Auction auction) {
		this.myAuctions.add(auction);
	}
	public Bid submitManualBid(Auction auction) {
		this.submitBid(auction);
		Bid bid = new ManualBid(auction, this);
		auction.addBid(bid);
		return bid;
	}
	
	public Bid submitAutomaticBid(Auction auction, Integer autoBiddingLimit) {
		Bid bid = new AutomaticBid(auction, this, autoBiddingLimit);
		auction.addBid(bid);
		this.submitBid(auction);
		return bid;
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
		return this.myAuctions.stream().filter(auct -> auct.isInProgress()).count() < 5;
	}
	private void addAuction(Auction auction) {
		this.auctions.add(auction);
	}
	/* ******************************
	 * 		UserDetails Methods		*
	 ********************************/


//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		return new ArrayList<GrantedAuthority>();
//	}
//	@Override
//	public String getUsername() {
//		return this.name;
//	}
//	@Override
//	public boolean isAccountNonExpired() {
//		return true;
//	}
//	@Override
//	public boolean isAccountNonLocked() {
//		return true;
//	}
//	@Override
//	public boolean isCredentialsNonExpired() {
//		return true;
//	}
//	@Override
//	public boolean isEnabled() {
//		return this.enabled;
//	}
//	public void setUsername(String username) {
//		this.setName(username);
//	}
//	public void setEnabled(Boolean enabled) {
//		this.enabled = enabled;
//	}
	

}
