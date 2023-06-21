package br.com.trier.spring_matutino.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.spring_matutino.BaseTest;
import br.com.trier.spring_matutino.domain.User;
import br.com.trier.spring_matutino.services.exceptions.ObjetoNaoEncontrado;

import br.com.trier.spring_matutino.services.exceptions.ViolacaoIntegridade;
import jakarta.transaction.Transactional;
import lombok.Setter;

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
	void findByIdNonExistentTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> userService.findById(10));
		assertEquals("Usuário 10 não encontrado!", exception.getMessage());
	}

	@Test
	@DisplayName("Teste atualização de usuário")
	@Sql(("classpath:/resources/sql/usuario.sql"))
	void updateUserTest() {
		User user = new User(1, "Novo Usuario", "test@gmail.com.br", "123");
		userService.update(user);
		List<User> usuarios = userService.listAll();
		assertEquals("Novo Usuario", usuarios.get(0).getName());
	}


	@Test
	@DisplayName("Teste listagem de usuários")
	@Sql(("classpath:/resources/sql/usuario.sql"))
	void listAllTest() {
		List<User> lista = userService.listAll();
		assertEquals(2, lista.size());
		assertEquals(1, lista.get(0).getId());
	}
	
	@Test
	@DisplayName("Teste delete usuario")
	@Sql(("classpath:/resources/sql/usuario.sql"))
	void deleteTest() {
		userService.delete(2);
		List<User> lista = userService.listAll();
		assertEquals(1, lista.size());
		assertEquals(1, lista.get(0).getId());		
	}

	@Test
	@DisplayName("Teste delete usuario invalido")
	@Sql(("classpath:/resources/sql/usuario.sql"))
	void deleteNonExistentTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> userService.delete(10));
		assertEquals("Usuário 10 não encontrado!", exception.getMessage());
		List<User> lista = userService.listAll();
		assertEquals(2, lista.size());
	}
	
	 
	@Test
	@DisplayName("Teste listar usuário por inicial nome")
	@Sql(("classpath:/resources/sql/usuario.sql"))
	void findUserByNameStartsWithTest() {
		List<User> lista = userService.findByNameStartsWithIgnoreCase("u");
		assertEquals(2, lista.size());
		lista = userService.findByNameStartsWithIgnoreCase("Usuario");
		assertEquals(2, lista.size());
	}
	

	@Test
	@DisplayName("Teste cadastra email duplicado")
	@Sql(("classpath:/resources/sql/usuario.sql"))
	void insereExisteEmail() {
		var user = new User(null, "Usuário", "usuario2@gmail.com", "123");
		var exception = assertThrows(ViolacaoIntegridade.class, () -> userService.salvar(user));
		assertEquals("E-mail já cadastrado: " + user.getEmail(), exception.getMessage());
		List<User> lista = userService.listAll();
		assertEquals(2, lista.size());
	}
	
	@Test
	@DisplayName("Teste de alteração de usuário com e-mail duplicado")
	@Sql(("classpath:/resources/sql/usuario.sql"))
	void updateDuplicatedEmailTest() {
		User user = new User(2,"usuario2@gmail.com","Usuario2","123");
		ViolacaoIntegridade exception = assertThrows(ViolacaoIntegridade.class, () -> userService.update(user));
		assertEquals("Esse email já existe", exception.getMessage());
	}
	@Test
	@DisplayName("Teste buscar usuario por nome inexistente")
	@Sql({"classpath:/resources/sql/usuario.sql"})
	void findByNameStartsWithIgnoreCaseNonExistent() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> userService.findByNameStartsWithIgnoreCase("x"));
		assertEquals("Nenhum nome de usuário inicia com x", exception.getMessage());
	}


}