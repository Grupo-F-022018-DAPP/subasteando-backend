package unq.desapp.grupo_f.backend.services.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import unq.desapp.grupo_f.backend.model.auction.Auction;
import unq.desapp.grupo_f.backend.services.AuctionService;
import unq.desapp.grupo_f.backend.services.dto.AuctionDTO;

@RestController
@RequestMapping(path="/auctions")
public class AuctionController {
	
	@Autowired 
	private AuctionService service;
	
	
	@GetMapping(path="/all")
	public Iterable<Auction> getAll(){
		System.out.println("auctions all");
		return service.getAll();
	}
	
	@PostMapping(path="/new")
	public Auction newAuction(@RequestBody AuctionDTO auctionDTO) {
		return this.service.createAuction(auctionDTO);
	}
	
	

}
