package unq.desapp.grupo_f.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import unq.desapp.grupo_f.backend.controllers.dto.AuctionDTO;
import unq.desapp.grupo_f.backend.model.User;
import unq.desapp.grupo_f.backend.model.auction.Auction;
import unq.desapp.grupo_f.backend.repositories.AuctionRepository;
import unq.desapp.grupo_f.backend.repositories.UserRepository;
import unq.desapp.grupo_f.backend.services.AuctionService;

@Controller
@RequestMapping(path="/auctions")
public class AuctionController {
	
	@Autowired 
	private AuctionService service;
	
	
	@GetMapping(path="/all")
	public Iterable<Auction> getAll(){
		return service.getAll();
	}
	
	@PostMapping(path="/new")
	public Auction newAuction(@RequestBody AuctionDTO auctionDTO, @RequestBody User currentuser) {
		return this.service.createAuction(auctionDTO, currentuser);
	}
	
	

}
