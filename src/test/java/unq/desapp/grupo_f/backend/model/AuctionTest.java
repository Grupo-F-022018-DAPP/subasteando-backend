package unq.desapp.grupo_f.backend.model;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import unq.desapp.grupo_f.backend.model.auction.Auction;
import unq.desapp.grupo_f.backend.model.bid.ManualBid;
import unq.desapp.grupo_f.backend.model.exceptions.AuctionStateException;
import unq.desapp.grupo_f.backend.model.exceptions.IncorrectParameterException;

public class AuctionTest {

	private User mockedUser;
	private Auction anyAuction;
	
	@Before
	public void before() {
		mockedUser = Mockito.mock(User.class);
		anyAuction = new Auction(mockedUser);
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
		Auction newAuction = new Auction(mockedUser);
		assertTrue(newAuction.getState().isNew());
		assertFalse(newAuction.getState().isInProgress());
		assertFalse(newAuction.getState().isFinished());
		assertFalse(newAuction.getState().isClosed());
	}
	@Test
	public void testUnaSubastaComenzadaTieneComoEstadoEnProgreso(){
		Auction startedAuction = new Auction(mockedUser);
		Mockito.when(mockedUser.canStartAnAuction()).thenReturn(true);
		startedAuction.startAuction();
		
		assertFalse(startedAuction.getState().isNew());
		assertTrue(startedAuction.getState().isInProgress());
		assertFalse(startedAuction.getState().isFinished());
		assertFalse(startedAuction.getState().isClosed());
	}
	@Test
	public void testUnaSubastaFinalizadaTieneComoEstadoFinalizada(){
		Auction finishedAuction = new Auction(mockedUser);
		Mockito.when(mockedUser.canStartAnAuction()).thenReturn(true);
		finishedAuction.startAuction();
		finishedAuction.finishAuction();
		
		assertFalse(finishedAuction.getState().isNew());
		assertFalse(finishedAuction.getState().isInProgress());
		assertTrue(finishedAuction.getState().isFinished());
		assertFalse(finishedAuction.getState().isClosed());
	}

	@Test
	public void testUnaSubastaCerradaTieneComoEstadoCerrada(){
		Auction closedAuction = new Auction(mockedUser);
		closedAuction.closeAuction();
		
		assertFalse(closedAuction.getState().isNew());
		assertFalse(closedAuction.getState().isInProgress());
		assertFalse(closedAuction.getState().isFinished());
		assertTrue(closedAuction.getState().isClosed());
	}
	@Test(expected = AuctionStateException.class)
	public void testUnaSubastaNoPuedeFinalizarSiNoComenzo(){
		Auction newAuction = new Auction(mockedUser);
		
		newAuction.finishAuction();
	}
	@Test(expected = AuctionStateException.class)
	public void testUnaSubastaNoPuedeComenzarSiYaFinalizo(){
		Auction finishedAuction = new Auction(mockedUser);
		finishedAuction.startAuction();
		finishedAuction.finishAuction();
		finishedAuction.startAuction();
	}
	@Test(expected = AuctionStateException.class)
	public void testUnaSubastaNoPuedeCerrarSiYaFinalizo(){
		Auction finishedAuction = new Auction(mockedUser);
		Mockito.when(mockedUser.canStartAnAuction()).thenReturn(true);
		finishedAuction.startAuction();
		finishedAuction.finishAuction();
		finishedAuction.closeAuction();
	}
	@Test(expected = AuctionStateException.class)
	public void testUnaSubastaNoPuedeComenzarSiYaCerro(){
		Auction closedAuction = new Auction(mockedUser);
		closedAuction.closeAuction();
		closedAuction.startAuction();
	}
	@Test(expected = AuctionStateException.class)
	public void testUnaSubastaNoPuedeFinalizarSiYaCerro(){
		Auction closedAuction = new Auction(mockedUser);
		closedAuction.closeAuction();
		closedAuction.finishAuction();
	}
	@Test(expected = AuctionStateException.class)
	public void testUnaSubastaNoPuedeCambiarElPrecioInicialSiYaComenzo(){
		Auction startedAuction = new Auction(mockedUser);
		Mockito.when(mockedUser.canStartAnAuction()).thenReturn(true);
		startedAuction.startAuction();
		startedAuction.setInitialPrice(1);
	}
	@Test(expected = AuctionStateException.class)
	public void testUnaSubastaNoPuedeCambiarLaFechaDeInicioSiYaComenzo(){
		Auction startedAuction = new Auction(mockedUser);
		Mockito.when(mockedUser.canStartAnAuction()).thenReturn(true);
		startedAuction.startAuction();
		startedAuction.setStartDate(LocalDate.now().plusDays(1));
	}
	@Test
	public void testAlOfertarEnUnaSubastaAumentaElPrecioPorSuValorCorrespondiente() {
		ManualBid bid = Mockito.mock(ManualBid.class);
		
		anyAuction.setInitialPrice(100);
		
		assertTrue(100 == anyAuction.getActualPrice());
		assertTrue(105 == anyAuction.getNextPrice());

		Mockito.when(mockedUser.canStartAnAuction()).thenReturn(true);
		anyAuction.startAuction();
		
		anyAuction.addBid(bid);
		
		assertTrue(105 == anyAuction.getActualPrice());
	}
	@Test(expected = AuctionStateException.class)
	public void testNoSePuedeOfertarEnUnaSubastaCerrada() {
		ManualBid bid = Mockito.mock(ManualBid.class);
		Auction closedAuction = new Auction(mockedUser);
		closedAuction.closeAuction();
		

		closedAuction.addBid(bid);
		
	}
	@Test(expected = AuctionStateException.class)
	public void testNoSePuedeOfertarEnUnaSubastaNueva() {
		ManualBid bid = Mockito.mock(ManualBid.class);
		Auction newAuction = new Auction(mockedUser);
		
		newAuction.addBid(bid);
		
	}
	@Test(expected = AuctionStateException.class)
	public void testNoSePuedeOfertarEnUnaSubastaFinalizada() {
		ManualBid bid = Mockito.mock(ManualBid.class);
		Auction finishedAuction = new Auction(mockedUser);
		Mockito.when(mockedUser.canStartAnAuction()).thenReturn(true);
		finishedAuction.startAuction();
		finishedAuction.finishAuction();
		
		finishedAuction.addBid(bid);
	}
	
	@Test(expected = AuctionStateException.class)
	public void testUnaSubastaCreadaQuedaEncoladaSiUsuarioTieneMasDe4Activas() {
		Auction newAuction = new Auction(mockedUser);
		Mockito.when(mockedUser.canStartAnAuction()).thenReturn(false);
		newAuction.startAuction();
		verify(mockedUser).queueAuction(newAuction);
	}
}
