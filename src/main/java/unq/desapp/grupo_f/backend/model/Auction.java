package unq.desapp.grupo_f.backend.model;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import unq.desapp.grupo_f.backend.model.Exceptions.IncorrectParameterException;

public class Auction {
	//Posibles nombres de clase: Auction, Sale, Bidding

	private String title;
	private String description;
	private String direction; //TODO: Reemplazar por una direccion de googlemaps
	private List<URL> pictures;
	private Integer initialPrice;
	private LocalDate startDate;
	private LocalDateTime endDate;
	
	public Auction() {
		this.title = "";
		this.description = "";
		this.direction = "";
		this.pictures = new ArrayList<URL>();
		this.initialPrice = 0;
		this.startDate = LocalDate.now().plusDays(1l);
		this.endDate = LocalDateTime.now().plusDays(3l);
	}
	

	/* ******************************
	 * 			Getters				*
	 ********************************/

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

	/* ******************************
	 * 			Setters				*
	 ********************************/
	
	public void setTitle(String title) {
		if(title.length() < 10 || title.length() > 50) {
			throw new IncorrectParameterException("The Title for the Auction, must be more than 10 and less than 50 characters");
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
	public void setPictures(List<URL> pictures) {
		this.pictures = pictures;
	}
	public void addPicture(URL picture) {
		this.pictures.add(picture);
	}
	public void removePicture(URL picture) {
		this.pictures.remove(picture);
	}
	public void setInitialPrice(Integer initialPrice) {
		this.initialPrice = initialPrice;
	}
	public void setStartDate(LocalDate startDate) {
		if(!startDate.isAfter(LocalDate.now())
				&& startDate.isBefore(this.endDate.toLocalDate().minusDays(1l))) {
			throw new IncorrectParameterException("The Start date for the Auction, must be after today, and must be 2 diays before the end Date.");
		}
		this.startDate = startDate;
	}
	public void setEndDate(LocalDateTime endDate) {
		if(this.startDate.isBefore(endDate.toLocalDate().minusDays(1l))) {
			throw new IncorrectParameterException("The End date for the Auction, must be 2 days after the start date, at minimum");
		}
		this.endDate = endDate;
	}

	/* ******************************
	 * 		  Public Methods		*
	 ********************************/

	/* ******************************
	 * 		  Private Methods		*
	 ********************************/
	
}
