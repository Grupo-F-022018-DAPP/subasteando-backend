package unq.desapp.grupo_f.backend.services.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import unq.desapp.grupo_f.backend.model.User;
import unq.desapp.grupo_f.backend.services.UserService;
import unq.desapp.grupo_f.backend.services.dto.UserDTO;
import unq.desapp.grupo_f.backend.utils.CustomLogAnnotation;

@RestController
public class UserController {

	@Autowired 
	private UserService service;
	
	@GetMapping(path="/users")
	@Transactional
	@CustomLogAnnotation
	public List<User> getAll(){
		return service.getAll();
	}
	@GetMapping(path="/users/page")
	@Transactional
	@CustomLogAnnotation
	public Page<User> getAllPaginated(@RequestParam Integer pageAmount, @RequestParam Integer pageIndex){
		return service.getAllPaginated(pageAmount, pageIndex);
	}
	@GetMapping("/users/{userId}")
	@Transactional
	@CustomLogAnnotation
	public User getUser(@PathVariable Integer userId) {		
		return this.service.getUser(userId);
	}
	@PostMapping(path="/users/new")
	@Transactional
	@CustomLogAnnotation
	public User newUser(@RequestBody UserDTO user) {
		return this.service.createUser(user);
	}
	@PutMapping(path="/users/{userId}")
	@Transactional
	@CustomLogAnnotation
	public User updateUser(@PathVariable Integer userId, @RequestBody UserDTO user) {
		return this.service.updateUser(userId, user.mapToUser());
	}
	@DeleteMapping("/users/{userId}")
	@Transactional
	@CustomLogAnnotation
	public void deleteUser(@PathVariable Integer userId) {		
		this.service.deleteUser(userId);
	}
	
}
