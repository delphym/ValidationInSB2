package net.delphym.validation;

import java.nio.charset.Charset;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import lombok.extern.log4j.Log4j2;
import net.delphym.validation.api.UserController;
import net.delphym.validation.repo.UserRepository;

@Log4j2
@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {
	@MockBean
	private UserRepository userRepository;

	@Autowired
	UserController userController;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void whenPostRequestToUsersAndValidUser_thenCorrectResponse() throws Exception {
		MediaType textPlainUtf8 = new MediaType(MediaType.TEXT_PLAIN, Charset.forName("UTF-8"));
		String user = "{\"name\": \"bob\", \"email\" : \"bob.abobek@domain.com\"}";
		mockMvc.perform(MockMvcRequestBuilders.post("/users")
				.content(user)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content()
				.contentType(textPlainUtf8));
	}

	@Test
	public void whenPostRequestToUsersAndInvalidUser_thenCorrectResponse() throws Exception {
		String user = "{\"name\": \"\", \"email\" : \"bob@domain.com\"}";

		log.info("user is: {}", user);
		mockMvc.perform(MockMvcRequestBuilders.post("/users")
				.content(user)
				.contentType(MediaType.APPLICATION_JSON_VALUE))//.andDo(print())
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andExpect(MockMvcResultMatchers.jsonPath("$.name", Is.is("Name is mandatory")))
		.andExpect(MockMvcResultMatchers.content()
				.contentType(MediaType.APPLICATION_JSON_VALUE));
	}

	@Test
	public void whenPostRequestToUsersAndInvalidEmailUser_thenCorrectResponse() throws Exception {
		String user = "{\"name\": \" Jonas \", \"email\" : \"adam123+pepa@seznam\"}";

		log.info("user is: {}", user);
		mockMvc.perform(MockMvcRequestBuilders.post("/users")
				.content(user)
				.contentType(MediaType.APPLICATION_JSON_VALUE))//.andDo(print())
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andExpect(MockMvcResultMatchers.jsonPath("$.email", Is.is("Email is not valid")))
		.andExpect(MockMvcResultMatchers.content()
				.contentType(MediaType.APPLICATION_JSON_VALUE));
	}

}
