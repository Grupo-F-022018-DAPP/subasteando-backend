package unq.desapp.grupo_f.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import unq.desapp.grupo_f.backend.controllers.dto.AuctionDTO;
import unq.desapp.grupo_f.backend.model.User;
import unq.desapp.grupo_f.backend.model.auction.Auction;
import unq.desapp.grupo_f.backend.model.builders.AuctionBuilder;
import unq.desapp.grupo_f.backend.repositories.AuctionRepository;

@Service
public class AuctionService {

	@Autowired
	private AuctionRepository repository;

	public Iterable<Auction> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public Auction createAuction(AuctionDTO auctionDTO, User currentUser) {
		AuctionBuilder builder 	= new AuctionBuilder(currentUser);
		Auction auction 		= builder.setTitle(		  	auctionDTO.getTitle()		)
										 .setDescription(	auctionDTO.getDescription()	)
										 .setInitialPrice(	auctionDTO.getInitialPrice())
										 .setStartDate(		auctionDTO.getStartDate()	)
										 .setEndDate(		auctionDTO.getEndDate()		)
										 .build();
		repository.save(auction);
		return auction;
	}
	
	
}
