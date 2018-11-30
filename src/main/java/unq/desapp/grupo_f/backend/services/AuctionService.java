package unq.desapp.grupo_f.backend.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import unq.desapp.grupo_f.backend.model.Auction;
import unq.desapp.grupo_f.backend.model.User;
import unq.desapp.grupo_f.backend.model.builders.AuctionBuilder;
import unq.desapp.grupo_f.backend.model.exceptions.AuctionException;
import unq.desapp.grupo_f.backend.model.exceptions.UserException;
import unq.desapp.grupo_f.backend.repositories.AuctionRepository;
import unq.desapp.grupo_f.backend.repositories.UserRepository;
import unq.desapp.grupo_f.backend.services.dto.AuctionDTO;

@Service
public class AuctionService {

	@Autowired
	private AuctionRepository repository;
	@Autowired
	private UserRepository userRepo;

	public Iterable<Auction> getAll() {
		return repository.findAll();
	}
	
	public List<Auction> getAllPaginated(Integer pageAmount, Integer pageIndex) {
		List<Auction> auctions = new ArrayList<Auction>();
		Pageable page = PageRequest.of(pageIndex, pageAmount);
		auctions.addAll(repository.findAll(page).getContent());
		return auctions;
	}
	
	public Auction createAuction(AuctionDTO auctionDTO, Integer userId) {		 
		User owner = userRepo.findById(userId).orElseThrow(() -> new UserException("User " + userId + " not found" ));
		AuctionBuilder builder 	= new AuctionBuilder();		
		Auction auction 		= builder.setTitle(		  	auctionDTO.getTitle()		)
		 		 						 .setDescription(	auctionDTO.getDescription()	)
		 		 						 .setInitialPrice(	auctionDTO.getInitialPrice())
		 		 						 .setStartDate(		auctionDTO.getStartDate()	)
		 		 						 .setEndDate(		auctionDTO.getEndDate()		)
		 		 						 .setOwner(			owner						)
		 		 						 .build();
		auctionDTO.getPictures().forEach((pic)-> auction.addPicture(pic));
		owner.createAuction(auction);
		repository.save(auction);
		return auction;
	}

	public Auction getAuction(Integer auctionId) {
		return repository.findById(auctionId).orElseThrow(()-> new AuctionException("Auction " + auctionId + " not found"));
	}

	public void deleteAuction(Integer auctionId) {
		repository.deleteById(auctionId);
	}

	public Auction updateAuction(Integer auctionId, AuctionDTO auctionDTO) {
		Auction auction = this.getAuction(auctionId);
		auction.setDescription(	auctionDTO.getDescription()	);
		if(auction.getState().isNew()) {
			auction.setTitle(		auctionDTO.getTitle()		);
			auction.setInitialPrice(auctionDTO.getInitialPrice());
			auction.setStartDate(	auctionDTO.getStartDate()	);
			auction.setEndDate(		auctionDTO.getEndDate()		);
		}	
		auctionDTO.getPictures().forEach((pic) -> auction.addPicture(pic));
		auction.changeStateTo(auctionDTO.getState());
		repository.save(auction);
		return auction;
	}

	public List<Auction> getRecentAuctions(Integer pageAmount, Integer pageIndex) {
		List<Auction> auctions = new ArrayList<Auction>();
		Pageable page = PageRequest.of(pageIndex, pageAmount);
		auctions.addAll(repository.findAuctionsInProgress(page).getContent());
		return auctions;
	}

	
	
	
}
