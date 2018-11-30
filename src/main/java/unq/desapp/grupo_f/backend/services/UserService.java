package unq.desapp.grupo_f.backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import unq.desapp.grupo_f.backend.model.User;
import unq.desapp.grupo_f.backend.model.exceptions.UserException;
import unq.desapp.grupo_f.backend.repositories.UserRepository;
import unq.desapp.grupo_f.backend.services.dto.UserDTO;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repository;

	public List<User> getAll() {
		return repository.findAll();
	}

	public User getUser(Integer userId) {
		return repository.findById(userId).orElseThrow(() -> new UserException("User " + userId + " not found"));
	}

	public User createUser(UserDTO user) {
		
		return this.repository.save(user.mapToUser());
	}

	public User updateUser(Integer userId, User user) {
		if(this.repository.existsById(userId)) {
			user.setId(userId);
			this.repository.save(user);
		}else {
			new UserException("User " + userId + " not found");
		}
		
		return user;
	}

	public void deleteUser(Integer userId) {
		this.repository.deleteById(userId);
	}

	public List<User> getAllPaginated(Integer pageAmount, Integer pageIndex) {
		return repository.findAll(PageRequest.of(pageIndex, pageAmount)).getContent();
	}

}
