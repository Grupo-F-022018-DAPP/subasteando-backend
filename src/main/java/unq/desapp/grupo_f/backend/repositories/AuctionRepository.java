package unq.desapp.grupo_f.backend.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import unq.desapp.grupo_f.backend.model.Auction;

public interface AuctionRepository extends CrudRepository<Auction, Integer> {
	
	@Override
	public Auction save(Auction auct);
	@Override
	public Optional<Auction> findById(Integer id);

}