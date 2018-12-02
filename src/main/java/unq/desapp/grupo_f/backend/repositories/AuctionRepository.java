package unq.desapp.grupo_f.backend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import unq.desapp.grupo_f.backend.model.Auction;

public interface AuctionRepository extends CrudRepository<Auction, Integer> {
	
	@SuppressWarnings("unchecked")
	@Override
	public Auction save(Auction auct);
	@Override
	public Optional<Auction> findById(Integer id);
	public List<Auction> findAll();
	public Page<Auction> findAll(Pageable page);
	@Query(value="SELECT auction FROM Auction auction WHERE auction.state = 'InProgress' ORDER BY auction.startDate asc, auction.endDate asc ",
			countQuery="SELECT count(*) FROM Auction auction WHERE auction.state = 'InProgress' ORDER BY auction.startDate, auction.endDate asc")
	public Page<Auction> findAuctionsInProgress(Pageable page);
	


}