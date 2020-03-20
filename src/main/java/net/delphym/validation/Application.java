package net.delphym.validation;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import net.delphym.validation.model.User;
import net.delphym.validation.repo.UserRepository;

//@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner run(UserRepository userRepo) throws Exception {
		return (String[] args) -> {
			User u1 = new User("Bob", "bob@domain.com");
			User u2 = new User("Jenny ", "jenny@domain.co");
			User u3 = new User(" Johny ", "A@domain.co");
			User u4 = new User(" Franta ", "fand^a@dom^$ain.co");
			userRepo.save(u1);
			userRepo.save(u2);
			userRepo.save(u3);
			userRepo.save(u4);
			userRepo.findAll().forEach(System.out::println);
		};
	}
}