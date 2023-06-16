package br.com.trier.spring_matutino.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.spring_matutino.BaseTest;
import br.com.trier.spring_matutino.domain.User;
import jakarta.transaction.Transactional;

@Transactional
public class UserServiceTest extends BaseTest {

	@Autowired
	UserService userService;
	@Test
	@DisplayName("Teste Insert usuário")
	@Sql(("classpath:/resources/sql/usuario.sql"))
	void insertUser() {
	    User novoUsuario = new User();
	    novoUsuario.setId(1);
	    novoUsuario.setEmail("usuario3@gmail.com");
	    novoUsuario.setName("Usuário3");
	    novoUsuario.setPassword("452");
	    
	    User insertedUser = userService.salvar(novoUsuario);

	    assertThat(insertedUser).isNotNull();
	    assertEquals(1, insertedUser.getId());
	    assertEquals("usuario3@gmail.com", insertedUser.getEmail());
	    assertEquals("Usuário3", insertedUser.getName());
	    assertEquals("452", insertedUser.getPassword());
	}

	@Test
	@DisplayName("Teste busca usuário por ID")
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
	@DisplayName("Teste busca por ID usuário inexistente")
	@Sql(("classpath:/resources/sql/usuario.sql"))
	void findNonExistentTest() {
		var usuario = userService.findById(10);
		assertThat(usuario).isNull();
	}

	@Test
	@DisplayName("Teste atualização de usuário")
	@Sql(("classpath:/resources/sql/usuario.sql"))
	void updateUserTest() {
		var usuario = userService.findById(1);
		usuario.setName("Novo Nome");
		usuario.setPassword("123456");

		User updatedUser = userService.update(usuario);

		assertThat(updatedUser).isNotNull();
		assertEquals(1, updatedUser.getId());
		assertEquals("usuario@gmail.com", updatedUser.getEmail());
		assertEquals("Novo Nome", updatedUser.getName());
		assertEquals("123456", updatedUser.getPassword());
	}

	@Test
	@DisplayName("Teste exclusão de usuário")
	@Sql(("classpath:/resources/sql/usuario.sql"))
	void deleteUserTest() {
		var usuario = userService.findById(1);
		userService.delete(usuario.getId());

		var deletedUser = userService.findById(1);
		assertThat(deletedUser).isNull();
	}

	@Test
	@DisplayName("Teste listagem de usuários")
	@Sql(("classpath:/resources/sql/usuario.sql"))
	void listAllTest() {
		List<User> lista = userService.listAll();
		assertEquals(2, lista.size());
		assertEquals(1, lista.get(0).getId());
	}
}