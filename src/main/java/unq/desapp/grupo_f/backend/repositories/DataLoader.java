package unq.desapp.grupo_f.backend.repositories;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import unq.desapp.grupo_f.backend.model.Auction;
import unq.desapp.grupo_f.backend.model.User;
import unq.desapp.grupo_f.backend.model.builders.AuctionBuilder;
import unq.desapp.grupo_f.backend.model.builders.UserBuilder;

@Component
public class DataLoader {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private AuctionRepository auctionRepo;

    //method invoked during the startup
    @PostConstruct
    @Transactional
    public void loadData() throws MalformedURLException {
    	createRandomData();
    }

	private void createRandomData() throws MalformedURLException {
		List<User> users = createUsers();
    	List<Auction> auctions = createAuctions(users.subList(0, 30), 5, 10, 10, 5);
    	
    	List<Auction> inProgress = auctions.subList(5, 15);
    	createBids(users.subList(30, 100), inProgress);
    	
    	List<User>	closedOwners = users.subList(25, 30);
    	closedOwners.stream().forEach(user -> user.closeAuction(user.getMyAuctions().get(0)));
    	 
    	

    	userRepo.saveAll(users);
    	auctionRepo.saveAll(auctions);
	}
    
    private List<Auction> createAuctions(List<User> owners, Integer amountNew, Integer amountInProgress, Integer amountFinished, Integer amountClosed) throws MalformedURLException {
    	//PRECONDICION: la suma de los montos == owners.size
    	Integer newStart, newFinish, inProgressFinish, finishedFinish,closedFinish;
    	newStart=0; newFinish=amountNew;
    	inProgressFinish	=newFinish + amountInProgress;
    	finishedFinish		=inProgressFinish + amountFinished;
    	closedFinish		=finishedFinish	+ amountClosed;
    	
		List<Auction> auctions = new ArrayList<Auction>();
		auctions.addAll(createNewAuctions		(owners.subList(newStart		, newFinish			)));
		auctions.addAll(createInProgressAuctions(owners.subList(newFinish		, inProgressFinish	)));
		auctions.addAll(createFinishedAuctions	(owners.subList(inProgressFinish, finishedFinish	)));
		auctions.addAll(createClosedAuctions	(owners.subList(finishedFinish	, closedFinish		)));
		return auctions;
	}

	private List<Auction> createClosedAuctions(List<User> owners) throws MalformedURLException {
		List<String> possibleTitles = Arrays.asList("Gato con botas", "Auto lindo", "Puzzle magico", "Lote de mangas", "Disco de oro de Britney");
    	List<String> photos = Arrays.asList("3u7oL3a.jpg", "aJJVxCr.jpg", "Ogra0dU.jpg", "DoHRnIo.jpg", "eAxn7sz.jpg" );
    	String descriptionStart = "Gran oportunidad de obtener ese ";
    	String descriptionEnd = " que tando deseabas!";
    	Random random = new Random();
		List<Auction> auctions = new ArrayList<Auction>();
		
		String title;
		Auction auction;
		for(Integer i = 0; i < owners.size(); i++) {
    		LocalDate start = LocalDate.now().minusDays(random.nextInt(i+1)).plusDays(random.nextInt(i+1));
    		LocalDateTime end = LocalDateTime.now().minusDays(random.nextInt(i+1)).plusDays(random.nextInt(i+1));
    		title = "Closed: " + possibleTitles.get(random.nextInt(possibleTitles.size() - 1)) + "Nro " + i;
    		URL pict = new URL("https://i.imgur.com/" + photos.get(random.nextInt(4)));
    		
    		auction = new Auction();
    		auction.setOwner(owners.get(i));
    		auction.setTitle(title);
    		auction.setDescription(descriptionStart + title + descriptionEnd);
    		auction.setInitialPrice(100*(i*50));
    		auction.setDirection("mi casa");
    		auction.setDates(start, end);
    		auction.addPicture(pict);
    		auction.setState(Auction.States.Closed);
    		owners.get(i).createAuction(auction);
    		auctions.add(auction);
    	}
		return auctions;
	}

	private List<Auction> createFinishedAuctions(List<User> owners) throws MalformedURLException {
		List<String> possibleTitles = Arrays.asList("Gato con botas", "Auto lindo", "Puzzle magico", "Lote de mangas", "Disco de oro de Britney");
    	List<String> photos = Arrays.asList("3u7oL3a.jpg", "aJJVxCr.jpg", "Ogra0dU.jpg", "DoHRnIo.jpg", "eAxn7sz.jpg" );
    	String descriptionStart = "Gran oportunidad de obtener ese ";
    	String descriptionEnd = " que tando deseabas!";
    	Random random = new Random();
		List<Auction> auctions = new ArrayList<Auction>();
		
		String title;
		Auction auction;
		for(Integer i = 0; i < owners.size(); i++) {
    		LocalDate start = LocalDate.now().minusMonths(1l);
    		LocalDateTime end = LocalDateTime.now().minusDays(1l);
    		title = "Finished: " + possibleTitles.get(random.nextInt(possibleTitles.size() - 1)) + "Nro " + i;
    		URL pict = new URL("https://i.imgur.com/" + photos.get(random.nextInt(4)));
    		
    		auction = new Auction();
    		auction.setOwner(owners.get(i));
    		auction.setTitle(title);
    		auction.setDescription(descriptionStart + title + descriptionEnd);
    		auction.setInitialPrice(100*(i*50));
    		auction.setDirection("mi casa");
    		auction.setDates(start, end);
    		auction.addPicture(pict);
    		owners.get(i).createAuction(auction);
    		auctions.add(auction);
    	}
		return auctions;
	}

	private List<Auction> createInProgressAuctions(List<User> owners) throws MalformedURLException {
		List<String> possibleTitles = Arrays.asList("Gato con botas", "Auto lindo", "Puzzle magico", "Lote de mangas", "Disco de oro de Britney");
    	List<String> photos = Arrays.asList("3u7oL3a.jpg", "aJJVxCr.jpg", "Ogra0dU.jpg", "DoHRnIo.jpg", "eAxn7sz.jpg" );
    	String descriptionStart = "Gran oportunidad de obtener ese ";
    	String descriptionEnd = " que tando deseabas!";
    	Random random = new Random();
		List<Auction> auctions = new ArrayList<Auction>();
		
		String title;
		Auction auction;
		for(Integer i = 0; i < owners.size(); i++) {
    		LocalDate start = LocalDate.now().minusMonths(1l);
    		LocalDateTime end = LocalDateTime.now().plusHours(random.nextInt(i + 1)).plusMinutes(10l);
    		title = "In Progress: " + possibleTitles.get(random.nextInt(possibleTitles.size() - 1)) + "Nro " + i;
    		URL pict = new URL("https://i.imgur.com/" + photos.get(random.nextInt(4)));
    		
    		auction = new Auction();
    		auction.setOwner(owners.get(i));
    		auction.setTitle(title);
    		auction.setDescription(descriptionStart + title + descriptionEnd);
    		auction.setInitialPrice(100*(i*50));
    		auction.setDirection("mi casa");
    		auction.setDates(start, end);
    		auction.addPicture(pict);
    		owners.get(i).createAuction(auction);
    		auctions.add(auction);
    	}
		return auctions;
	}

	private List<Auction> createNewAuctions(List<User> owners) throws MalformedURLException {
		List<String> possibleTitles = Arrays.asList("Gato con botas", "Auto lindo", "Puzzle magico", "Lote de mangas", "Disco de oro de Britney");
    	List<String> photos = Arrays.asList("3u7oL3a.jpg", "aJJVxCr.jpg", "Ogra0dU.jpg", "DoHRnIo.jpg", "eAxn7sz.jpg" );
    	String descriptionStart = "Gran oportunidad de obtener ese ";
    	String descriptionEnd = " que tando deseabas!";
    	Random random = new Random();
		AuctionBuilder emptyBuilder = new AuctionBuilder();
		List<Auction> auctions = new ArrayList<Auction>();
		
		String title;
		Auction auction;
		for(Integer i = 0; i < owners.size(); i++) {
    		LocalDate start = LocalDate.now().plusDays(1l).plusDays(i);
    		LocalDateTime end = LocalDateTime.now().plusDays(1+ (i*30)).plusMonths(2+i).minusDays(new Random().nextInt(29));
    		URL pict = new URL("https://i.imgur.com/" + photos.get(random.nextInt(4)));
    		title = "Closed: " + possibleTitles.get(random.nextInt(possibleTitles.size() - 1)) + "Nro " + i;
    		
    		auction = emptyBuilder.setOwner(owners.get(i))
    							  .setTitle(title)
    							  .setDescription(descriptionStart + title + descriptionEnd)
    							  .setInitialPrice(100*(i*50))
    							  .setDirection("mi casa")
    							  .setEndDate(end)
    							  .setStartDate(start)
    							  .build();
    		auction.addPicture(pict);
    		owners.get(i).createAuction(auction);
    		auctions.add(auction);
    	}
		return auctions;
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
