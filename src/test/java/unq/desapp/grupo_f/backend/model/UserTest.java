package unq.desapp.grupo_f.backend.model;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import unq.desapp.grupo_f.backend.model.exceptions.IncorrectParameterException;

public class UserTest {
	
	private User anyUser;
	
	@Before
	public void before() {
		this.anyUser = this.anyUser();
	}
	
	@Test
	public void testUnUsuarioTieneNombre() {
		this.anyUser.setName("Nombre");
		assertTrue(this.anyUser.getName().length() > 0);
	}
	@Test(expected = IncorrectParameterException.class)
	public void testUnUsuarioNoPuedeTenerUnNombreConMasDe30Caracteres() {
		this.anyUser.setName("1234567890123456789012345678901");
	}
	@Test
	public void testUnUsuarioTieneApellido() {
		this.anyUser.setSurname("Apellido");
		assertTrue(this.anyUser.getSurname().length() > 0);
	}
	@Test(expected = IncorrectParameterException.class)
	public void testUnUsuarioNoPuedeTenerUnApellidoConMasDe30Caracteres() {
		this.anyUser.setSurname("1234567890123456789012345678901");
	}
	@Test
	public void testUnUsuarioTieneEmail() {
		this.anyUser.setEmail("email");
		assertTrue(this.anyUser.getEmail().length() > 0);
	}
	//TODO: test email con formato de email
	@Test
	public void testUnUsuarioTieneContraseña() {
		this.anyUser.setPassword("contraseña");
		assertTrue(this.anyUser.getPassword().length() > 0);
	}
	@Test(expected = IncorrectParameterException.class)
	public void testUnUsuarioNoPuedeTenerUnaContraseñaConMenosDe4Caracteres() {
		this.anyUser.setPassword("aaa");
	}
	@Test(expected = IncorrectParameterException.class)
	public void testUnUsuarioNoPuedeTenerUnaContraseñaConMasDe10Caracteres() {
		this.anyUser.setPassword("12345678901");
	}
	@Test
	public void testUnUsuarioTieneEdadDeNacimiento() {
		LocalDate birthDate = LocalDate.of(1995, 10, 9);
		this.anyUser.setBirthDate(birthDate);
		assertTrue(this.anyUser.getBirthDate().equals(birthDate));
	}
	
	private User anyUser() {
		return new User();
	}
}
