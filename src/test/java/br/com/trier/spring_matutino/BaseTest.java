package br.com.trier.spring_matutino;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import br.com.trier.spring_matutino.services.UserService;
import br.com.trier.spring_matutino.services.impl.UserServiceImpl;

@TestConfiguration
@SpringBootTest
@ActiveProfiles("test")

public class BaseTest {

	@Bean
	public UserService userService() {
		return new UserServiceImpl();
	}

}
