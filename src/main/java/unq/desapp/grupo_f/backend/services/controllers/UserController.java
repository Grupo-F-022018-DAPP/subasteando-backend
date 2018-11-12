package unq.desapp.grupo_f.backend.services.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import unq.desapp.grupo_f.backend.model.User;
import unq.desapp.grupo_f.backend.services.UserService;

@RestController
public class UserController {

	@Autowired 
	private UserService service;
	
	@GetMapping(path="/users")
	public List<User> getAll(){
		return service.getAll();
	}
	@GetMapping("/users/{userId}")
	public User getUser(@PathVariable Integer userId) {		
		return this.service.getUser(userId);
	}
	@PostMapping(path="/users/new")
	public User newUser(@RequestBody User user) {
		return this.service.createUser(user);
	}
	@PutMapping(path="/users/{userId}")
	public User updateUser(@PathVariable Integer userId, @RequestBody User user) {
		return this.service.updateUser(userId, user);
	}
	@DeleteMapping("/users/{userId}")
	public void deleteUser(@PathVariable Integer userId) {		
		this.service.deleteUser(userId);
	}
	
}
