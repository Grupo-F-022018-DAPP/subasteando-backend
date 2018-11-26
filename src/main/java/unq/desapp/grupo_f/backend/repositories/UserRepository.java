package unq.desapp.grupo_f.backend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import unq.desapp.grupo_f.backend.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {

    User findByName(String name);
    @Override
    List<User> findAll();

	@Override
	public User save(User user);
	@Override
	public Optional<User> findById(Integer id);
}