package net.delphym.validation;

import static org.junit.Assert.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.log4j.Log4j2;
import net.delphym.validation.model.User;

@Log4j2
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserTest {

	@Test
	public void createUser() {
		String name = "   ";
		String email = " no email.here";

		User newUser = new User(name, email);

		log.info("email: {}", newUser.getEmail());
		log.info("id: {}", newUser.getId());
		assertNotNull(newUser.getEmail());
	}

}
