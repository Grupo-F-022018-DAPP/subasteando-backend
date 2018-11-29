package unq.desapp.grupo_f.backend.model.builders;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Random;

import unq.desapp.grupo_f.backend.model.User;
import unq.desapp.grupo_f.backend.model.exceptions.BuilderException;
import unq.desapp.grupo_f.backend.model.utils.RandomStrings;

public class UserBuilder {
	
	private Optional<String> name;
	private Optional<String> surname;
	private Optional<String> email;
	private Optional<String> password;
	private Optional<LocalDate> birthDate;
	
	
	public UserBuilder() {
		name 		= Optional.empty();
		surname 	= Optional.empty();
		email	 	= Optional.empty();
		password 	= Optional.empty();
		birthDate	= Optional.empty();
	}

	/* ******************************
	 * 		Getters and Setters		*
	 ********************************/
	
	public UserBuilder setName(String name) {
		UserBuilder next = this.copy();
		next.name = Optional.of(name);
		return next;
	}
	
	public UserBuilder setSurname(String surname) {
		UserBuilder next = this.copy();
		next.surname = Optional.of(surname);
		return next;
	}
	
	public UserBuilder setEmail(String email) {
		UserBuilder next = this.copy();
		next.email = Optional.of(email);
		return next;
	}
	public UserBuilder setPassword(String password) {
		UserBuilder next = this.copy();
		next.password = Optional.of(password);
		return next;
	}
	public UserBuilder setBirthDate(LocalDate birthDate) {
		UserBuilder next = this.copy();
		next.birthDate = Optional.of(birthDate);
		return next;
	}

	/* ******************************
	 * 		  Public Methods		*
	 ********************************/
	
	public User build() {
		if(!this.hasEverything()) {
			throw new BuilderException("An user needs Name, Surname, Email, Passwrod, and BirthDate. One or more of them are missing");
		}
		User user = new User();
		user.setName(name.get());
		user.setSurname(surname.get());
		user.setEmail(email.get());
		user.setPassword(password.get());
		user.setBirthDate(birthDate.get());
		return user;
	}
	
	public User buildRandom() {
		
		User randomUser = new User();
		Random random = new Random();
		String possibleChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		
		randomUser.setName(RandomStrings.generateRandomString(random, possibleChars, 10));
		randomUser.setSurname(RandomStrings.generateRandomString(random, possibleChars, 10));
		randomUser.setEmail(RandomStrings.generateRandomString(random, possibleChars, 8) + "@gmail.com");
		randomUser.setPassword(RandomStrings.generateRandomString(random, possibleChars, 8));
		randomUser.setBirthDate(LocalDate.now().minusYears(random.nextInt(50)));
		
		return randomUser;

	}
	

	/* ******************************
	 * 		  Private Methods		*
	 ********************************/
	
	private Boolean hasEverything() {
		return this.name.isPresent() && surname.isPresent() && email.isPresent() && password.isPresent() && birthDate.isPresent();
	}
	private UserBuilder copy() {
		UserBuilder copy = new UserBuilder();
		
		copy.name = name;
		copy.surname = surname;
		copy.email = email;
		copy.password = password;
		copy.birthDate = birthDate;
		
		return copy;
	}
	
	
}
