package unq.desapp.grupo_f.backend.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import unq.desapp.grupo_f.backend.model.auctionState.AuctionStateInProgress;
import unq.desapp.grupo_f.backend.model.bid.ManualBid;
import unq.desapp.grupo_f.backend.model.builders.AuctionBuilder;
import unq.desapp.grupo_f.backend.model.exceptions.AuctionStateException;
import unq.desapp.grupo_f.backend.model.exceptions.IncorrectParameterException;

public class AuctionTest {

	private User mockedUser;
	private Auction anyAuction;
	
	@Before
	public void before() {
		mockedUser = Mockito.mock(User.class);
		anyAuction = new Auction();
		anyAuction.setOwner(mockedUser);
	}
	@Test
	public void testUnaSubastaTieneDueño() {
		assertEquals(mockedUser, anyAuction.getOwner());
	}
	@Test
	public void testUnaSubastaTieneTitulo() {
		String title = "TituloDe20Caracteres";
		anyAuction.setTitle(title);
		assertEquals(title, anyAuction.getTitle());
	}
	@Test(expected = IncorrectParameterException.class)
	public void testElTituloDeUnaSubastaNoPuedeTenerMenosDe10Caracteres() {
		anyAuction.setTitle("123456789");
	}
	@Test(expected = IncorrectParameterException.class)
	public void testElTituloDeUnaSubastaNoPuedeTenerMasDe50Caracteres() {
		String tituloDe51Caracteres = "123456789-123456789-123456789-123456789-123456789-1";
		anyAuction.setTitle(tituloDe51Caracteres);
	}
	@Test
	public void testUnaSubastaTieneDescripcion() {
		String descripcionDe11Caracteres = "12345678901";
		anyAuction.setDescription(descripcionDe11Caracteres);
		assertEquals(descripcionDe11Caracteres, anyAuction.getDescription());
	}
	@Test(expected = IncorrectParameterException.class)
	public void testLaDescripcionDeUnaSubastaNoPuedeTenerMenosDe10Caracteres() {
		String descripcionDe9Caracteres = "123456789";
		anyAuction.setDescription(descripcionDe9Caracteres);
	}
	@Test(expected = IncorrectParameterException.class)
	public void testLaDescripcionDeUnaSubastaNoPuedeTenerMasDe100Caracteres() {
		String descripcionDe101Caracteres = "123456789-123456789-123456789-123456789-123456789-"
										  + "123456789-123456789-123456789-123456789-123456789-1";
		anyAuction.setDescription(descripcionDe101Caracteres);
	}
	@Test
	public void testUnaSubastaTieneDireccion() {
		String direccion = "calle falsa 123";
		anyAuction.setDirection(direccion);
		assertEquals(direccion, anyAuction.getDirection());
	}
	@Test
	public void testAUnaSubastaSeLePuedenAgregarURLsDeFotos() {
		//URL url = Mockito.mock(URL.class);
		//No se puede mockear por alguna razon
		URL url;
		try {
			
			url = new URL("https://www.google.com/");
			anyAuction.addPicture(url);
			assertEquals(1, anyAuction.getPictures().size());
			
		} catch (MalformedURLException e) {
			fail("La url puesta no funciona en el test, cambienla.");
			e.printStackTrace();
		}
	}
	@Test
	public void testAUnaSubastaSeLePuedenBorrarURLsDeFotos() {
		//URL url = Mockito.mock(URL.class);
		//No se puede mockear por alguna razon
		URL url;
		try {
			url = new URL("https://www.google.com/");

			anyAuction.addPicture(url);
			
			assertEquals(1, anyAuction.getPictures().size());
			
			anyAuction.removePicture(url);
			
			assertEquals(0, anyAuction.getPictures().size());
			
		} catch (MalformedURLException e) {
			fail("La url puesta no funciona en el test, cambienla.");
			e.printStackTrace();
		}
		
	}
	@Test
	public void testUnaSubastaTienePrecioInicial() {
		Integer precioInicial = 10;
		anyAuction.setInitialPrice(precioInicial);
		assertEquals(precioInicial, anyAuction.getInitialPrice());
	}
	@Test
	public void testUnaSubastaTieneFechaDeInicio() {
		LocalDate fechaDeInicio = LocalDate.now().plusDays(1);
		anyAuction.setStartDate(fechaDeInicio);
		assertEquals(fechaDeInicio, anyAuction.getStartDate());
	}
	@Test(expected = IncorrectParameterException.class)
	public void testLaFechaDeInicioDeUnaSubastaTieneQueSerDespuesDelDiaDeHoy() {
		LocalDate fechaDeInicio = LocalDate.now();
		anyAuction.setStartDate(fechaDeInicio);
	}
	@Test
	public void testUnaSubastaTieneFechaDeFinalizacion() {
		LocalDateTime fechaFin = LocalDateTime.now().plusDays(3);
		anyAuction.setEndDate(fechaFin);
		assertEquals(fechaFin, anyAuction.getEndDate());
	}
	@Test(expected = IncorrectParameterException.class)
	public void testLaFechaDeFinalizacionDeUnaSubastaTieneQueSerDespuesDe2DiasDeLaFechaDeInicio() {
		LocalDate fechaDeInicio = LocalDate.now().plusDays(1);
		LocalDateTime fechaFin = LocalDateTime.now().plusDays(2);
		anyAuction.setStartDate(fechaDeInicio);
		anyAuction.setEndDate(fechaFin);
	}
	@Test(expected = IncorrectParameterException.class)
	public void testLaFechaDeInicioDeUnaSubastaTieneQueAntesDeLaFechaDeFinalizacion() {
		LocalDate fechaDeInicio = LocalDate.now().plusDays(3);
		LocalDateTime fechaFin = LocalDateTime.now().plusDays(2);
		anyAuction.setEndDate(fechaFin);
		
		anyAuction.setStartDate(fechaDeInicio);
	}
	@Test
	public void testUnaSubastaRecienCreadaTieneComoEstadoNuevaSubasta(){
		Auction newAuction = new Auction();
		newAuction.setOwner(mockedUser);
		assertTrue(newAuction.getAuctionState().isNew());
		assertFalse(newAuction.getAuctionState().isInProgress());
		assertFalse(newAuction.getAuctionState().isFinished());
		assertFalse(newAuction.getAuctionState().isClosed());
	}
	@Test
	public void testUnaSubastaQueDebeComenzarTieneComoEstadoEnProgreso(){
		Auction startedAuction = new Auction();
		startedAuction.setOwner(mockedUser);
		LocalDate startDate = LocalDate.now().minusDays(1);
		LocalDateTime endDate = LocalDateTime.now().plusDays(1);
		startedAuction.setDates(startDate, endDate);
		
		assertFalse(startedAuction.getAuctionState().isNew());
		assertTrue(startedAuction.getAuctionState().isInProgress());
		assertFalse(startedAuction.getAuctionState().isFinished());
		assertFalse(startedAuction.getAuctionState().isClosed());
	}
	@Test
	public void testUnaSubastaQueDebeFinalizarTieneComoEstadoFinalizada(){
		Auction finishedAuction = new Auction();
		finishedAuction.setOwner(mockedUser);

		LocalDate startDate = LocalDate.now().minusDays(2);
		LocalDateTime endDate = LocalDateTime.now().minusDays(1);
		finishedAuction.setDates(startDate, endDate);
		
		assertFalse(finishedAuction.getAuctionState().isNew());
		assertFalse(finishedAuction.getAuctionState().isInProgress());
		assertTrue(finishedAuction.getAuctionState().isFinished());
		assertFalse(finishedAuction.getAuctionState().isClosed());
	}

	@Test
	public void testUnaSubastaCerradaTieneComoEstadoCerrada(){
		Auction closedAuction = new Auction();
		closedAuction.setOwner(mockedUser);
		closedAuction.closeAuction();
		
		assertFalse(closedAuction.getAuctionState().isNew());
		assertFalse(closedAuction.getAuctionState().isInProgress());
		assertFalse(closedAuction.getAuctionState().isFinished());
		assertTrue(closedAuction.getAuctionState().isClosed());
	}
	@Test(expected = AuctionStateException.class)
	public void testUnaSubastaNoPuedeFinalizarSiNoComenzo(){
		Auction newAuction = new Auction();
		newAuction.setOwner(mockedUser);
		newAuction.finishAuction();
	}
	@Test(expected = AuctionStateException.class)
	public void testUnaSubastaNoPuedeComenzarSiYaFinalizo(){
		Auction finishedAuction = new Auction();
		finishedAuction.setOwner(mockedUser);
		finishedAuction.startAuction();
		finishedAuction.finishAuction();
		finishedAuction.startAuction();
	}
	@Test(expected = AuctionStateException.class)
	public void testUnaSubastaNoPuedeCerrarSiYaFinalizo(){
		Auction finishedAuction = new Auction();
		finishedAuction.setOwner(mockedUser);
		Mockito.when(mockedUser.canStartAnAuction()).thenReturn(true);
		finishedAuction.startAuction();
		finishedAuction.finishAuction();
		finishedAuction.closeAuction();
	}
	@Test(expected = AuctionStateException.class)
	public void testUnaSubastaNoPuedeComenzarSiYaCerro(){
		Auction closedAuction = new Auction();
		closedAuction.setOwner(mockedUser);
		closedAuction.closeAuction();
		closedAuction.startAuction();
	}
	@Test(expected = AuctionStateException.class)
	public void testUnaSubastaNoPuedeFinalizarSiYaCerro(){
		Auction closedAuction = new Auction();
		closedAuction.setOwner(mockedUser);
		closedAuction.closeAuction();
		closedAuction.finishAuction();
	}
	@Test(expected = AuctionStateException.class)
	public void testUnaSubastaNoPuedeCambiarElPrecioInicialSiYaComenzo(){
		Auction startedAuction = new Auction();
		startedAuction.setOwner(mockedUser);
		LocalDate startDate = LocalDate.now().minusDays(1);
		LocalDateTime endDate = LocalDateTime.now().plusDays(1);
		startedAuction.setDates(startDate, endDate);
		
		startedAuction.setInitialPrice(1);
	}
	@Test(expected = AuctionStateException.class)
	public void testUnaSubastaNoPuedeCambiarLaFechaDeInicioSiYaComenzo(){
		Auction startedAuction = new Auction();
		startedAuction.setOwner(mockedUser);
		LocalDate startDate = LocalDate.now().minusDays(1);
		LocalDateTime endDate = LocalDateTime.now().plusDays(1);
		startedAuction.setDates(startDate, endDate);
		
		startedAuction.setStartDate(LocalDate.now().plusDays(1));
	}
	@Test
	public void testAlOfertarEnUnaSubastaAumentaElPrecioPorSuValorCorrespondiente() {
		ManualBid bid = Mockito.mock(ManualBid.class);
		
		anyAuction.setInitialPrice(100);
		
		assertTrue(100 == anyAuction.getActualPrice());
		assertTrue(105 == anyAuction.getNextPrice());
		LocalDate startDate = LocalDate.now().minusDays(1);
		LocalDateTime endDate = LocalDateTime.now().plusDays(1);
		anyAuction.setDates(startDate, endDate);
		
		anyAuction.addBid(bid);
		
		assertTrue(105 == anyAuction.getActualPrice());
	}
	@Test(expected = AuctionStateException.class)
	public void testNoSePuedeOfertarEnUnaSubastaCerrada() {
		ManualBid bid = Mockito.mock(ManualBid.class);
		Auction closedAuction = new Auction();
		closedAuction.setOwner(mockedUser);
		closedAuction.closeAuction();
		

		closedAuction.addBid(bid);
		
	}
	@Test(expected = AuctionStateException.class)
	public void testNoSePuedeOfertarEnUnaSubastaNueva() {
		ManualBid bid = Mockito.mock(ManualBid.class);
		Auction newAuction = new Auction();
		newAuction.setOwner(mockedUser);
		
		newAuction.addBid(bid);
		
	}
	@Test(expected = AuctionStateException.class)
	public void testNoSePuedeOfertarEnUnaSubastaFinalizada() {
		ManualBid bid = Mockito.mock(ManualBid.class);
		Auction finishedAuction = new Auction();
		finishedAuction.setOwner(mockedUser);
		Mockito.when(mockedUser.canStartAnAuction()).thenReturn(true);
		finishedAuction.startAuction();
		finishedAuction.finishAuction();
		
		finishedAuction.addBid(bid);
	}
	
	@Test
	public void testUnaSubastaTieneSubastasPopularesParaRecomendar() {
		AuctionBuilder builder = new AuctionBuilder();
		Auction auctionPrincipal = builder.buildRandom();
		Auction auction1 = builder.buildRandom();
		Auction auction2 = builder.buildRandom();
		Auction auction3 = builder.buildRandom();
		
		LocalDate startDate = LocalDate.now().minusDays(1);
		LocalDateTime endDate = LocalDateTime.now().plusDays(1);
		auctionPrincipal.setDates(startDate, endDate);
		auction1.setDates(startDate, endDate);
		auction2.setDates(startDate, endDate);
		auction3.setDates(startDate, endDate);
		
		User user1 = new User();
		User user2 = new User();
		User user3 = new User();
		
		auction1.setOwner(mockedUser);
		auction2.setOwner(mockedUser);
		auction3.setOwner(mockedUser);
		mockedUser.createAuction(auction1);
		mockedUser.createAuction(auction2);
		mockedUser.createAuction(auction3);
		

		user1.submitManualBid(auctionPrincipal);
		user2.submitManualBid(auctionPrincipal);
		user3.submitManualBid(auctionPrincipal);
		
		user1.submitManualBid(auction3);
		user2.submitManualBid(auction3);
		user3.submitManualBid(auction3);
		
		user1.submitManualBid(auction2);
		user2.submitManualBid(auction2);
		
		user1.submitManualBid(auction1);
		
		
		
		List<Auction> populars = auctionPrincipal.popularAuctions();

//		auctionPrincipal.setTitle("---------principal");
//		auction1		.setTitle("---------menos popular");
//		auction2		.setTitle("---------medio popular");
//		auction3		.setTitle("---------mas popular");
//		System.out.println(auctionPrincipal.popularAuctions().stream().map(auc -> auc.getTitle()).collect(Collectors.toList()));
		
		assertEquals(auction3, populars.get(0));
		assertEquals(auction2, populars.get(1));
		assertEquals(auction1, populars.get(2));
	}
	
}
