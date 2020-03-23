package net.delphym.validation;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
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

	private static Validator validator;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		validator = Validation.buildDefaultValidatorFactory().getValidator();
	}

	@Test
	public void createUserBlankNames() {
		String name = "   ";
		String email = " no email.here";

		User newUser = new User(name, email);

		Set<ConstraintViolation<User>> violations = validator.validate(newUser);
		log.info("email: {}", newUser.getEmail());
		log.info("id: {}", newUser.getId());
		log.info("user: {}", newUser);
		log.info("violations: {}", violations);

		assertThat(violations.size(), equalTo(2));

		assertThat(violations.parallelStream().findFirst().get().getPropertyPath().toString(), equalTo("email"));
		assertThat(violations.parallelStream().findAny().get().getMessage(), equalTo("Email is not valid"));

		assertTrue(violations.parallelStream().filter(v->v.getPropertyPath().toString().equals("name")).findAny().isPresent());
		assertTrue(violations.parallelStream().filter(v->v.getMessage().equals("Name is mandatory")).findAny().isPresent());

		assertTrue(violations.parallelStream().filter(v->v.getPropertyPath().toString().equals("email")).findAny().isPresent());
		assertTrue(violations.parallelStream().filter(v->v.getMessage().equals("Email is not valid")).findAny().isPresent());
	}
}
