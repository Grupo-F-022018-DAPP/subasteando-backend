package unq.desapp.grupo_f.backend.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import unq.desapp.grupo_f.backend.model.auction.Auction;
import unq.desapp.grupo_f.backend.model.bid.AutomaticBid;
import unq.desapp.grupo_f.backend.model.bid.Bid;
import unq.desapp.grupo_f.backend.model.bid.ManualBid;
import unq.desapp.grupo_f.backend.model.exceptions.BiddingException;

public class BidTest {
	
	ManualBid manualBid;
	AutomaticBid autoBid;
	Auction mockAuction;
	User mockUser;
	
	@Before
	public void before() {
		mockAuction = Mockito.mock(Auction.class);
		mockUser 	= Mockito.mock(User.class);
		
		manualBid 	= new ManualBid(mockAuction, mockUser);
		autoBid 	= new AutomaticBid(mockAuction, mockUser, 10);
	}

	@Test
	public void testABidIsFromAnUserToAnAuction() {
		assertEquals(mockAuction, manualBid.getAuction());
		assertEquals(mockUser, manualBid.getUser());
		assertEquals(mockAuction, autoBid.getAuction());
		assertEquals(mockUser, autoBid.getUser());
	}
	@Test
	public void testABidHasPrice() {
		manualBid.setPrice(10);
		autoBid.setPrice(10);
		
		assertTrue(10 == manualBid.getPrice());
		assertTrue(10 == autoBid.getPrice());
	}

	@Test(expected = BiddingException.class)
	public void testAManualBidDoesntHaveBiddingLimit() {
		Integer anyInt = 0;
		assertFalse(manualBid.canAutoBid(anyInt));
		
		manualBid.getBiddingLimit();
	}
	@Test(expected = BiddingException.class)
	public void testAManualBidCantAutoBid() {
		manualBid.autoBid();;
	}
	@Test
	public void testAnAutomaticBidHasBiddingLimit() {
		assertTrue(10 == autoBid.getBiddingLimit());
	}
	@Test
	public void testAnAutomaticBidCanAutoBidForAmountsLessThanTheBiddingLimit() {
		AutomaticBid automaticBid = new AutomaticBid(mockAuction, mockUser, 100);
		
		assertTrue(automaticBid.canAutoBid(10));
		assertFalse(automaticBid.canAutoBid(110));
	}
	@Test
	public void testAnAutomaticBidBidsWhenAnotherBidsInTheSameAuction() {
		
		//Preparacion
		Auction auction = new Auction(mockUser);
		Auction spyAuction = Mockito.spy(auction);
		spyAuction.setInitialPrice(100);
		
		AutomaticBid automaticBid = new AutomaticBid(spyAuction, mockUser, 1000);
		AutomaticBid spyAutoBid = Mockito.spy(automaticBid);
		Mockito.when(mockUser.canStartAnAuction()).thenReturn(true);
		spyAuction.startAuction();
		
		User mockUser2 = Mockito.mock(User.class);
		ManualBid manualBid2 = new ManualBid(spyAuction, mockUser2);
		
		//Proceso
		
		spyAuction.addBid(spyAutoBid);
		
		Mockito.verify(spyAutoBid, Mockito.never()).autoBid();
		assertTrue(spyAuction.getBiddings().contains(spyAutoBid));
		
		spyAuction.addBid(manualBid2);
				
		//Comprobacion

		Mockito.verify(spyAutoBid).autoBid();
		assertTrue(spyAuction.getBiddings().contains(manualBid2));
		assertEquals(3, spyAuction.getBiddings().size());
		Mockito.verify(spyAuction, Mockito.times(3)).addBid(Mockito.any(Bid.class));
		
	}
	@Test
	public void testTwoAutomaticBidsInTheSameAuctionWillBidUntilReachingTheirBiddingLimit() {
			
		//Preparacion

		User owner = Mockito.mock(User.class);
		User user1 = Mockito.mock(User.class);
		User user2 = Mockito.mock(User.class);
		Auction auction = new Auction(owner);
		Auction spyAuction = Mockito.spy(auction);
		spyAuction.setInitialPrice(100); //aumenta de a 5
		
		AutomaticBid automaticBid1 = new AutomaticBid(spyAuction, user1, 156);
		AutomaticBid automaticBid2 = new AutomaticBid(spyAuction, user2, 151);
		AutomaticBid spyAutoBid1 = Mockito.spy(automaticBid1);
		AutomaticBid spyAutoBid2 = Mockito.spy(automaticBid2);
		Mockito.when(owner.canStartAnAuction()).thenReturn(true);
		spyAuction.startAuction();
		
		//Proceso
		
		spyAuction.addBid(spyAutoBid1);		
		spyAuction.addBid(spyAutoBid2);
				
		//Comprobacion

		Mockito.verify(spyAuction, Mockito.atLeastOnce()).setActualPrice(Mockito.anyInt());
		Mockito.verify(spyAutoBid1, Mockito.atLeastOnce()).autoBid();
		Mockito.verify(spyAutoBid2, Mockito.atLeastOnce()).autoBid();
		assertEquals(new Integer(155), spyAuction.getActualPrice());
		
	}	

}
