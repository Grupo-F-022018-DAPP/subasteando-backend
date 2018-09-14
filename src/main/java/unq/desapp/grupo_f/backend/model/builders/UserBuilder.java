package unq.desapp.grupo_f.backend.model.builders;

import java.time.LocalDate;

import unq.desapp.grupo_f.backend.model.User;
import unq.desapp.grupo_f.backend.model.exceptions.BuilderException;

public class UserBuilder {
	
	private User user;
	private Boolean hasName;
	private Boolean hasSurname;
	private Boolean hasEmail;
	private Boolean hasPassword;
	private Boolean hasBirthDate;
	
	
	public UserBuilder() {
		this.user = new User();
		this.hasName = false;
		this.hasSurname = false;
		this.hasEmail = false;
		this.hasPassword = false;
		this.hasBirthDate = false;
	}

	/* ******************************
	 * 		  Public Methods		*
	 ********************************/
	
	public UserBuilder setName(String name) {
		UserBuilder next = this.copy();
		next.user.setName(name);
		next.hasName = true;
		return next;
	}
	
	public UserBuilder setSurname(String surname) {
		UserBuilder next = this.copy();
		next.user.setSurname(surname);
		next.hasSurname = true;
		return next;
	}
	
	public UserBuilder setEmail(String email) {
		UserBuilder next = this.copy();
		next.user.setEmail(email);
		next.hasEmail = true;
		return next;
	}
	public UserBuilder setPassword(String password) {
		UserBuilder next = this.copy();
		next.user.setPassword(password);;
		next.hasPassword = true;
		return next;
	}
	public UserBuilder setBirthDate(LocalDate birthDate) {
		UserBuilder next = this.copy();
		next.user.setBirthDate(birthDate);
		next.hasBirthDate = true;
		return next;
	}
	
	public User build() {
		if(!this.hasEverything()) {
			throw new BuilderException("An user needs Name, Surname, Email, Passwrod, and BirthDate. One or more of them are missing");
		}
		return user;
	}
	

	/* ******************************
	 * 		  Private Methods		*
	 ********************************/
	
	private Boolean hasEverything() {
		return hasName && hasSurname && hasEmail && hasPassword && hasBirthDate;
	}
	private UserBuilder copy() {
		UserBuilder copy = new UserBuilder();
		
		copy.user.setName(this.user.getName());
		copy.user.setSurname(this.user.getSurname());
		copy.user.setEmail(this.user.getEmail());
		copy.user.setPassword(this.user.getPassword());
		copy.user.setBirthDate(this.user.getBirthDate());
		
		copy.hasName = this.hasName;
		copy.hasSurname = this.hasSurname;
		copy.hasEmail = this.hasEmail;
		copy.hasPassword = this.hasPassword;
		copy.hasBirthDate = this.hasBirthDate;
		
		return copy;
	}
	
	
}
