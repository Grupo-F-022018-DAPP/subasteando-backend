package unq.desapp.grupo_f.backend.model;

import java.time.LocalDate;

import unq.desapp.grupo_f.backend.model.Exceptions.IncorrectParameterException;

public class User {
	
	
	private String name;
	private String surname;
	private String email;
	private String password;
	private LocalDate birthDate;
	
	public User() {
		this.name = "";
		this.surname = "";
		this.email = "";
		this.password = "";
		this.birthDate = LocalDate.now();
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

	/* ******************************
	 * 		  Public Methods		*
	 ********************************/

	/* ******************************
	 * 		  Private Methods		*
	 ********************************/
	

}
