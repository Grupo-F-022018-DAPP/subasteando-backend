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
import unq.desapp.grupo_f.backend.repositories.AuctionRepository;
import unq.desapp.grupo_f.backend.repositories.UserRepository;

@Component
public class DataLoader {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private AuctionRepository auctionRepo;

    //method invoked during the startup
    @PostConstruct
    public void loadData() {
    	List<User> users = createUsers();
    	List<Auction> auctions = createAuctions(users.subList(0, 30));
    	
    	List<Auction> inProgress = auctions.subList(0, 25);
    	inProgress.stream().forEach(auct -> auct.startAuction());
    	
    	createBids(users.subList(30, 100), inProgress);
    	

    	List<Auction> finalized = auctions.subList(0, 10);
    	finalized.stream().forEach(auct -> auct.finishAuction());
    	
    	users.subList(20, 25).stream().forEach(user -> user.closeAuction(user.getMyAuctions().get(0)));
    	userRepo.saveAll(users);
    	auctionRepo.saveAll(auctions);
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
    		LocalDate start = LocalDate.now().plusDays(1l).plusDays(i*30l);
    		LocalDateTime end = LocalDateTime.now().plusDays(1+ (i*30)).plusMonths(2+i).minusDays(new Random().nextInt(29));
    		auction = emptyBuilder.setOwner(owners.get(i))
    							  .setTitle(RandomStrings.generateRandomString(new Random(), possibleTitleWords, 3))
    							  .setDescription("Auction Nro: " + i)
    							  .setInitialPrice(100*(i*50))
    							  .setDirection("mi casa")
    							  .setEndDate(end)
    							  .setStartDate(start)
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
