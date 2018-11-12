package unq.desapp.grupo_f.backend.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import unq.desapp.grupo_f.backend.model.User;
import unq.desapp.grupo_f.backend.model.bid.Bid;

public interface BidRepository extends CrudRepository<Bid, Integer> {
	
	List<Bid> findByUser(User user);

}
