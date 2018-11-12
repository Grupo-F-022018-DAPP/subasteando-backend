package unq.desapp.grupo_f.backend.services.dto;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import unq.desapp.grupo_f.backend.model.auction.Auction;

public class AuctionDTO {
	
	private String title;
	private String description;
	private Integer initialPrice;
	@JsonFormat(pattern="dd-MM-yyyy")
	private LocalDate startDate;
	@JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
	private LocalDateTime endDate;
	private List<URL> pictures;
	private Auction.States state;
	public AuctionDTO() {
		this.title 			= "";
		this.description	= "";
		this.initialPrice	= 1;
		this.startDate 		= LocalDate.now().plusDays(1l);
		this.endDate 		= LocalDateTime.now().plusYears(1l);
		this.pictures 		= new ArrayList<>();
		this.state 			= Auction.States.New;
	}
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getInitialPrice() {
		return initialPrice;
	}
	public void setInitialPrice(Integer initialPrice) {
		this.initialPrice = initialPrice;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDateTime getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}
	public List<URL> getPictures() {
		return pictures;
	}
	public void setPictures(List<URL> pictures) {
		this.pictures = pictures;
	}
	public Auction.States getState() {
		return state;
	}
	public void setState(String state) {
		this.state = Auction.States.valueOf(state);
	}
}
