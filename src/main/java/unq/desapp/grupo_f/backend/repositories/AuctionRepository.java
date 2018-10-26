package unq.desapp.grupo_f.backend.repositories;

import org.springframework.data.repository.CrudRepository;

import unq.desapp.grupo_f.backend.model.User;
import unq.desapp.grupo_f.backend.model.auction.Auction;

public interface AuctionRepository extends CrudRepository<Auction, Integer> {

}