	package unq.desapp.grupo_f.backend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import unq.desapp.grupo_f.backend.model.User;
import unq.desapp.grupo_f.backend.model.bid.AutomaticBid;
import unq.desapp.grupo_f.backend.model.bid.Bid;
import unq.desapp.grupo_f.backend.model.bid.ManualBid;

public interface BidRepository extends CrudRepository<Bid, Integer> {
	
	List<Bid> findByUser(User user);
	

	public Bid save(AutomaticBid bid);
	public Bid save(ManualBid bid);
	@Override
	public Optional<Bid> findById(Integer id);


	Page<Bid> findAll(Pageable page);

	@Query(value="SELECT bid FROM Bid bid WHERE bid.user.id = ?1 ORDER BY bid.id asc",
	  countQuery="SELECT count(*) FROM Bid bid WHERE bid.user.id = ?1 ORDER BY bid.id asc")
	Page<Bid> findAllByUserId(Integer userId, Pageable page);

	@Query(value="SELECT bid FROM Bid bid WHERE bid.auction.id = ?1 ORDER BY bid.id asc",
	  countQuery="SELECT count(*) FROM Bid bid WHERE bid.auction.id = ?1 ORDER BY bid.id asc")
	Page<Bid> findAllByAuctionId(Integer auctionId, Pageable of);

}
