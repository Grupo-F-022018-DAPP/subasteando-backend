package unq.desapp.grupo_f.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import unq.desapp.grupo_f.backend.model.User;
import unq.desapp.grupo_f.backend.model.auction.Auction;
import unq.desapp.grupo_f.backend.model.builders.AuctionBuilder;
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

	public Auction createAuction(AuctionDTO auctionDTO) {
		//TODO: sacar la creacion de owner
		AuctionBuilder builder 	= new AuctionBuilder();		
		User owner = new User();
		Auction auction 		= builder.setTitle(		  	auctionDTO.getTitle()		)
		 		 						 .setDescription(	auctionDTO.getDescription()	)
		 		 						 .setInitialPrice(	auctionDTO.getInitialPrice())
		 		 						 .setStartDate(		auctionDTO.getStartDate()	)
		 		 						 .setEndDate(		auctionDTO.getEndDate()		)
		 		 						 .setOwner(			owner						)
		 		 						 .build();
		owner.createAuction(auction);
		userRepo.save(owner);
		repository.save(auction);
		return auction;
	}
	
	
}
