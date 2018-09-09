package unq.desapp.grupo_f.backend.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class UserTest {
	
	@Test
	public void test_UserCreatesWithName() {
		String name = "Agustina";
		User user = new User(name);
		assertEquals(name, user.name);
	}
}
