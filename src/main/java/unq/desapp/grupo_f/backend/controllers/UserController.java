package unq.desapp.grupo_f.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import unq.desapp.grupo_f.backend.model.User;
import unq.desapp.grupo_f.backend.model.auction.Auction;
import unq.desapp.grupo_f.backend.model.builders.UserBuilder;
import unq.desapp.grupo_f.backend.repositories.UserRepository;

@Controller
@RequestMapping(path="/demo")
public class UserController {

	@Autowired 
	private UserRepository userRepository;
	
	@GetMapping(path="/add") // Map ONLY GET Requests
	public @ResponseBody String addNewUser (@RequestParam String name, @RequestParam String email) {
		User n = new User();
		n.setName(name);
		n.setEmail(email);
		Auction auct1 = new Auction(n);
		auct1.startAuction();
		User n2 = new User();
		n2.setName("asd");
		n2.submitManualBid(auct1);
		userRepository.save(n);
		userRepository.save(n2);
		return "Saved";
	}
	
	@GetMapping(path="/all")
	public @ResponseBody Iterable<User> getAllUsers() {
	// This returns a JSON or XML with the users
		return userRepository.findAll();
	}
	
}
