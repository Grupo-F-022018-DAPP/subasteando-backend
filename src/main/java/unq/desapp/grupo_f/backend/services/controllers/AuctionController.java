package unq.desapp.grupo_f.backend.services.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import unq.desapp.grupo_f.backend.model.Auction;
import unq.desapp.grupo_f.backend.services.AuctionService;
import unq.desapp.grupo_f.backend.services.dto.AuctionDTO;

@RestController
public class AuctionController {
	
	@Autowired 
	private AuctionService service;
	
	
	@GetMapping(path="/auctions")
	public Iterable<Auction> getAll(){
		System.out.println("auctions all");
		return service.getAll();
	}
	@GetMapping("/auctions/{auctionId}")
	public Auction getAuction(@PathVariable Integer auctionId) {		
		return this.service.getAuction(auctionId);
	}
	@GetMapping("/auctions/recent")
	public List<Auction> getRecentAuctions(@RequestParam Integer pageAmount, @RequestParam Integer pageIndex){
		return this.service.getRecentAuctions(pageAmount, pageIndex);
	}
	
	@PostMapping(path="/auctions/new")
	public Auction newAuction(@RequestParam Integer userId, @RequestBody AuctionDTO auctionDTO) {
		return this.service.createAuction(auctionDTO, userId);
	}
	@PutMapping(path="/auctions/{auctionId}")
	public Auction updateAuction(@PathVariable Integer auctionId, @RequestBody AuctionDTO auctionDTO) {
		return this.service.updateAuction(auctionId, auctionDTO);
	}
	@DeleteMapping("/auctions/{auctionId}")
	public void deleteAuction(@PathVariable Integer auctionId) {		
		this.service.deleteAuction(auctionId);
	}
	
	

}
