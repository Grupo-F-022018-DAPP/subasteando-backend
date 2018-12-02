package unq.desapp.grupo_f.backend.services.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
import unq.desapp.grupo_f.backend.utils.CustomLogAnnotation;

@RestController
public class AuctionController {
	
	@Autowired 
	private AuctionService service;
	//{{url}} = http://localhost:8080
	
	@GetMapping(path="/auctions")				//{{url}}/auctions
	@Transactional
	@CustomLogAnnotation
	public Iterable<Auction> getAll(){
		return service.getAll();
	}
	@GetMapping(path="/auctions/page")				//{{url}}/auctions/page?pageAmount=10&pageIndex=0
	@Transactional
	@CustomLogAnnotation
	public Iterable<Auction> getAllPaginated(@RequestParam Integer pageAmount, @RequestParam Integer pageIndex){
		return service.getAllPaginated(pageAmount, pageIndex);
	}
	@GetMapping("/auctions/{auctionId}")		//{{url}}/auctions/1
	@Transactional
	@CustomLogAnnotation
	public Auction getAuction(@PathVariable Integer auctionId) {		
		return this.service.getAuction(auctionId);
	}
	@GetMapping("/auctions/recent")				//{{url}}/auctions/recent?pageAmount=10&pageIndex=0
	@Transactional
	@CustomLogAnnotation
	public Iterable<Auction> getRecentAuctions(@RequestParam Integer pageAmount, @RequestParam Integer pageIndex){
		return this.service.getRecentAuctions(pageAmount, pageIndex);
	}
	
	@PostMapping(path="/auctions/new")			//{{url}}/auctions/new?userId=1
	@Transactional
	@CustomLogAnnotation
	public Auction newAuction(@RequestParam Integer userId, @RequestBody AuctionDTO auctionDTO) {
		return this.service.createAuction(auctionDTO, userId);
	}
	@PutMapping(path="/auctions/{auctionId}")	//{{url}}/auctions/1
	@Transactional
	@CustomLogAnnotation
	public Auction updateAuction(@PathVariable Integer auctionId, @RequestBody AuctionDTO auctionDTO) {
		return this.service.updateAuction(auctionId, auctionDTO);
	}
	@DeleteMapping("/auctions/{auctionId}")		//{{url}}/auctions/1
	@Transactional
	@CustomLogAnnotation
	public void deleteAuction(@PathVariable Integer auctionId) {		
		this.service.deleteAuction(auctionId);
	}
	
	@GetMapping(path="/auctions/popular/{auctionId}")
	@Transactional
	@CustomLogAnnotation
	public List<Auction> popularAuctionsForAuction(@PathVariable Integer auctionId) {
		return this.service.popularAuctionsForAuction(auctionId);
	}
	
	@GetMapping(path="/auctions/byState/page")				//{{url}}/auctions/New/page?pageAmount=10&pageIndex=0
	@Transactional
	@CustomLogAnnotation
	public Iterable<Auction> getByStatePaginated(@RequestParam String state, @RequestParam Integer pageAmount, @RequestParam Integer pageIndex){
		
		return service.getStatePaginated(Auction.States.valueOf(state), pageAmount, pageIndex);
	}
	
	
	

}
