package unq.desapp.grupo_f.backend.controllers.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AuctionDTO {
	
	private String title;
	private String description;
	private Integer initialPrice;
	private LocalDate startDate;
	private LocalDateTime endDate;
	public AuctionDTO() {
		this.title = "";
		this.description = "";
		this.initialPrice = 1;
		this.startDate = LocalDate.now().plusDays(1l);
		this.endDate = LocalDateTime.now().plusYears(1l);
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
	
}
