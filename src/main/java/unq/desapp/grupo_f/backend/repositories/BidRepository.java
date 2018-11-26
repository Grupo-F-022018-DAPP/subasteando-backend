	package unq.desapp.grupo_f.backend.repositories;

import java.util.List;
import java.util.Optional;

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

}
