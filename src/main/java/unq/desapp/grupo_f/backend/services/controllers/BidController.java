package unq.desapp.grupo_f.backend.services.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import unq.desapp.grupo_f.backend.model.bid.Bid;
import unq.desapp.grupo_f.backend.services.BidService;

@RestController
public class BidController {	
	
	@Autowired
	private BidService service;
	
	@GetMapping(path= "/bids")
	public Iterable<Bid> getAll(){
		return this.service.getAll(); 
	}
	@GetMapping(path= "/bids/user")
	public Iterable<Bid> getAllOfUser(@RequestParam Integer userId){
		return this.service.getAllBidsOfUser(userId);
	}
	@GetMapping(path= "/bids/auction")
	public Iterable<Bid> getAllOfAuction(@RequestParam Integer auctionId){
		return this.service.getAllBidsOfAuction(auctionId);
	}
	@GetMapping(path= "/bids/{bidId}")
	public Bid getBid(@PathVariable Integer bidId) {
		return this.service.getBid(bidId);
	}
	@PostMapping(path= "/bids/newManual")
	public Bid postManualBid(@RequestParam Integer auctionId, @RequestParam Integer userId) {
		return this.service.createManualBid(auctionId, userId);
	}
	@PostMapping(path= "/bids/newAutomatic")
	public Bid postAutomaticBid(@RequestParam Integer auctionId, @RequestParam Integer userId, @RequestParam Integer autoBiddingLimit) {
		return this.service.createAutomaticBid(auctionId, userId, autoBiddingLimit);
	}
	
	
	
	

}
