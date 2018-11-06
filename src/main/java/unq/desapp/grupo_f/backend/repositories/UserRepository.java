package unq.desapp.grupo_f.backend.repositories;

import org.springframework.data.repository.CrudRepository;

import unq.desapp.grupo_f.backend.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {

    User findByName(String name);
}