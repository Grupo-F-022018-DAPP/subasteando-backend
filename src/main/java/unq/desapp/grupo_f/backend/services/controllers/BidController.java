package unq.desapp.grupo_f.backend.services.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import unq.desapp.grupo_f.backend.model.bid.Bid;
import unq.desapp.grupo_f.backend.services.BidService;
import unq.desapp.grupo_f.backend.utils.CustomLogAnnotation;

@RestController
public class BidController {	
	
	@Autowired
	private BidService service;
	
	//{{url}} = http://localhost:8080
	
	@GetMapping(path= "/bids")				//{{url}}/bids
	@Transactional
	@CustomLogAnnotation
	public Iterable<Bid> getAll(){
		return this.service.getAll(); 
	}
	@GetMapping(path= "/bids/user")			//{{url}}/bids/user?userId=1
	@Transactional
	@CustomLogAnnotation
	public Iterable<Bid> getAllOfUser(@RequestParam Integer userId){
		return this.service.getAllBidsOfUser(userId);
	}
	@GetMapping(path= "/bids/auction")		//{{url}}/bids/auction?auctionId=1
	@Transactional
	@CustomLogAnnotation
	public Iterable<Bid> getAllOfAuction(@RequestParam Integer auctionId){
		return this.service.getAllBidsOfAuction(auctionId);
	}
	@GetMapping(path= "/bids/page")			//{{url}}/bids/page?pageAmount=10&pageIndex=0
	@Transactional
	@CustomLogAnnotation
	public Iterable<Bid> getAllPaginated(@RequestParam Integer pageAmount, @RequestParam Integer pageIndex){
		return this.service.getAllPaginated(pageAmount, pageIndex); 
	}
	@GetMapping(path= "/bids/user/page")	//{{url}}/bids/user/page?userId=1&pageAmount=10&pageIndex=0
	@Transactional
	@CustomLogAnnotation
	public Iterable<Bid> getAllOfUserPaginated(@RequestParam Integer userId, @RequestParam Integer pageAmount, @RequestParam Integer pageIndex){
		return this.service.getAllBidsOfUserPaginated(userId, pageAmount, pageIndex);
	}
	@GetMapping(path= "/bids/auction/page")	//{{url}}/bids/auction/page?auctionId?1&pageAmount=10&pageIndex=0
	@Transactional
	@CustomLogAnnotation
	public Iterable<Bid> getAllOfAuctionPaginated(@RequestParam Integer auctionId, @RequestParam Integer pageAmount, @RequestParam Integer pageIndex){
		return this.service.getAllBidsOfAuctionPaginated(auctionId, pageAmount, pageIndex);
	}
	@GetMapping(path= "/bids/{bidId}")		//{{url}}/bids/1
	@Transactional
	@CustomLogAnnotation
	public Bid getBid(@PathVariable Integer bidId) {
		return this.service.getBid(bidId);
	}
	@PostMapping(path= "/bids/newManual")	//{{url}}/bids/newManual?auctionId=1&userId=1
	@Transactional
	@CustomLogAnnotation
	public Bid postManualBid(@RequestParam Integer auctionId, @RequestParam Integer userId) {
		return this.service.createManualBid(auctionId, userId);
	}
	@PostMapping(path= "/bids/newAutomatic")//{{url}}/bids/newAutomatic?auctionId=1&userId=1&autoBiddingLimit=1000
	@Transactional
	@CustomLogAnnotation
	public Bid postAutomaticBid(@RequestParam Integer auctionId, @RequestParam Integer userId, @RequestParam Integer autoBiddingLimit) {
		return this.service.createAutomaticBid(auctionId, userId, autoBiddingLimit);
	}
	
	
	
	

}
