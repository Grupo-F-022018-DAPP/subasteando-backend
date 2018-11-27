package unq.desapp.grupo_f.backend.services.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import unq.desapp.grupo_f.backend.model.Auction;
import unq.desapp.grupo_f.backend.model.User;

public class UserDTO {
	
	/*
	 * "name"		: "a",
	"surname"	: "a",
	"email"		: "",
	"password"	: "12345",
	"birthDate"	: "1995-10-09",
	"auctions"	: [],
	"myAuctions": []
	 */
	private String name;
	private String surname;
	private String email;
	private String password;
	private LocalDate birthDate;
	private List<Auction> auctions;
	private List<Auction> myAuctions;

	public UserDTO() {
		setName("");
		setSurname("");
		setEmail("");
		setPassword("");
		setBirthDate(LocalDate.now());
		setAuctions(new ArrayList<Auction>());
		setMyAuctions(new ArrayList<Auction>());
	}
	
	public User mapToUser() {
		User newUser = new User();
		newUser.setName(name);
		newUser.setSurname(surname);
		newUser.setEmail(email);
		newUser.setPassword(password);
		newUser.setBirthDate(birthDate);
		newUser.setAuctions(auctions);
		newUser.setMyAuctions(myAuctions);
		
		return newUser;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public List<Auction> getAuctions() {
		return auctions;
	}

	public void setAuctions(List<Auction> auctions) {
		this.auctions = auctions;
	}

	public List<Auction> getMyAuctions() {
		return myAuctions;
	}

	public void setMyAuctions(List<Auction> myAuctions) {
		this.myAuctions = myAuctions;
	}

}
