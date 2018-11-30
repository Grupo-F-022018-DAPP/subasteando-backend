package unq.desapp.grupo_f.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import unq.desapp.grupo_f.backend.model.Auction;
import unq.desapp.grupo_f.backend.model.User;
import unq.desapp.grupo_f.backend.model.bid.Bid;
import unq.desapp.grupo_f.backend.model.exceptions.AuctionException;
import unq.desapp.grupo_f.backend.model.exceptions.BidException;
import unq.desapp.grupo_f.backend.model.exceptions.UserException;
import unq.desapp.grupo_f.backend.repositories.AuctionRepository;
import unq.desapp.grupo_f.backend.repositories.BidRepository;
import unq.desapp.grupo_f.backend.repositories.UserRepository;

@Service
public class BidService {
	
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private AuctionRepository auctionRepo;
	@Autowired
	private BidRepository bidRepo;
	
	public Iterable<Bid> getAll() {
		return this.bidRepo.findAll();
	}

	public Iterable<Bid> getAllBidsOfUser(Integer userId) {
//		List<Auction> auctions = new ArrayList();
//		Optional<User> user = this.userRepo.findById(userId);
//		List<Bid> biddings = new ArrayList();
//		if(user.isPresent()) {
//			auctions = user.get().getAuctions();
//		}
//		for(Auction auct: auctions) {
//			biddings.addAll(auct.getBiddings().stream()
//							.filter((bid) -> 
//									{return bid.getUser().getId() == userId;})
//							.collect(Collectors.toList()));
//		}
		User user = userRepo.findById(userId).orElseThrow(() -> new UserException("User " + userId + " not found"));
		return bidRepo.findByUser(user);
	}

	public Iterable<Bid> getAllBidsOfAuction(Integer auctionId) {
		return this.auctionRepo
				.findById(auctionId).orElseThrow(() -> new AuctionException("Auction " + auctionId + " not found"))
				.getBiddings();
	}

	public Bid getBid(Integer bidId) {
		return this.bidRepo.findById(bidId).orElseThrow(() -> new BidException("Bid " + bidId + " not found"));
	}

	public Bid createManualBid(Integer auctionId, Integer userId) {
		User user 		= this.userRepo.findById(userId)
								.orElseThrow(() -> new UserException("User " + userId + " not found"));
		Auction auction = this.auctionRepo.findById(auctionId)
								.orElseThrow(() -> new AuctionException("Auction " + auctionId + " not found"));
		Bid bid = user.submitManualBid(auction);
		bidRepo.save(bid);
		return bid;
		
		
	}
	public Bid createAutomaticBid(Integer auctionId, Integer userId, Integer autoBiddingLimit) {
		User user 		= this.userRepo.findById(userId)
								.orElseThrow(() -> new UserException("User " + userId + " not found"));
		Auction auction = this.auctionRepo.findById(auctionId)
								.orElseThrow(() -> new AuctionException("Auction " + auctionId + " not found"));
		
		Bid bid = user.submitAutomaticBid(auction, autoBiddingLimit);
		bidRepo.save(bid);
		return bid;
	}
	public void deleteBid(Integer bidId) {
		this.bidRepo.deleteById(bidId);
	}

	public Iterable<Bid> getAllPaginated(Integer pageAmount, Integer pageIndex) {
		// TODO Auto-generated method stub
		return bidRepo.findAll(PageRequest.of(pageIndex, pageAmount)).getContent();
	}

	public Iterable<Bid> getAllBidsOfUserPaginated(Integer userId, Integer pageAmount, Integer pageIndex) {
		return bidRepo.findAllByUserId(userId, PageRequest.of(pageIndex, pageAmount)).getContent();
	}

	public Iterable<Bid> getAllBidsOfAuctionPaginated(Integer auctionId, Integer pageAmount, Integer pageIndex) {
		return bidRepo.findAllByAuctionId(auctionId, PageRequest.of(pageIndex, pageAmount)).getContent();
	}

	

}
