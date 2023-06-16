package br.com.trier.spring_matutino.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.spring_matutino.BaseTest;
import jakarta.transaction.Transactional;
@Transactional
public class UserSeviceTest extends BaseTest{
	
	@Autowired
	UserService userService;
	
	@Test
	@DisplayName("Teste busca usuario por ID")
	@Sql(("classpath:/resources/sql/usuario.sql"))
	void findByIdTest() {
		var usuario = userService.findById(1);
		assertThat(usuario).isNotNull();
		assertEquals(1, usuario.getId());
		assertEquals("usuario@gmail.com", usuario.getEmail());
		assertEquals("Usuario1", usuario.getName());
		assertEquals("321", usuario.getPassword());
		
	}
	@Test
	@DisplayName("Teste busca usuario por ID")
	@Sql(("classpath:/resources/sql/usuario.sql"))
	void findNonExistent() {
		var usuario = userService.findById(10);
		assertThat(usuario).isNull();
		
	}

}
