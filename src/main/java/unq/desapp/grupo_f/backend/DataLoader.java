package unq.desapp.grupo_f.backend;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import unq.desapp.grupo_f.backend.model.Auction;
import unq.desapp.grupo_f.backend.model.User;
import unq.desapp.grupo_f.backend.model.builders.AuctionBuilder;
import unq.desapp.grupo_f.backend.model.builders.UserBuilder;
import unq.desapp.grupo_f.backend.model.utils.RandomStrings;
import unq.desapp.grupo_f.backend.repositories.UserRepository;

@Component
public class DataLoader {

    @Autowired
    private UserRepository userRepo;

    //method invoked during the startup
    @PostConstruct
    public void loadData() {
    	List<User> users = createUsers();
    	List<Auction> auctions = createAuctions(users.subList(0, 10));
    	
    	List<Auction> inProgress = auctions.subList(0, 5);
    	inProgress.stream().forEach(auct -> auct.startAuction());
    	
    	createBids(users.subList(10, 100), inProgress);
    	
    	userRepo.saveAll(users);
//      userRepository.save(new User("user"));
    }
    
    private List<User> createUsers(){
    	UserBuilder emptyBuilder = new UserBuilder();
    	List<User> users = new ArrayList<User>();
    	User user;
    	
    	for (Integer i = 0; i <= 99; i++) {
    		user = emptyBuilder.setName("Test")
   				 				.setSurname("" + i)
   				 				.setEmail("calleFalsa@123.com")
   				 				.setBirthDate(LocalDate.now().minusYears(20))
   				 				.setPassword("012345678")
   				 				.build();
    		users.add(user);
        }
    	return users;
    }
    
    private List<Auction> createAuctions(List<User> owners){
    	List<Auction> auctions = new ArrayList<Auction>();
    	AuctionBuilder emptyBuilder = new AuctionBuilder();
    	Auction auction;
    	List<String> possibleTitleWords = Arrays.asList("Gato", "Con", "Botas", "Argentino", "Pulcro", "Random", "Why not", "Teclado", "Usado", "El lider", "NaNaNaNaNaNa");
    	
    	for(Integer i = 0; i < owners.size(); i++) {
    		auction = emptyBuilder.setOwner(owners.get(i))
    							  .setTitle(RandomStrings.generateRandomString(new Random(), possibleTitleWords, 3))
    							  .setDescription("Auction Nro: " + i)
    							  .setInitialPrice(100*(i*50))
    							  .setDirection("mi casa")
    							  .setStartDate(LocalDate.now().plusDays(1+ (i*30)))
    							  .setEndDate(LocalDateTime.now().plusDays(1+ (i*30)).plusMonths(2+i).minusDays(new Random().nextInt(29)))
    							  .build();
    		owners.get(i).createAuction(auction);
    		auctions.add(auction);
    	}
    	return auctions;
    }
    
    private void createBids(List<User> notOwners, List<Auction> inProgressAuctions){
    	Random random = new Random();
    	for(Integer i = 0; i < random.nextInt(10); i++) {
    		for(Auction auct: inProgressAuctions) {
        		notOwners.get(random.nextInt(notOwners.size() - 1)).submitManualBid(auct);
        	}
    	}
    }

//    //method invoked during the shutdown
//    @PreDestroy
//    public void removeData() {
//        userRepository.deleteAll();
//    }
    
    
}
