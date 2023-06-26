package br.com.trier.spring_matutino.resource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import br.com.trier.spring_matutino.SpringMatutinoApplication;
import br.com.trier.spring_matutino.config.jwt.LoginDTO;
import br.com.trier.spring_matutino.domain.dto.UserDTO;
import br.com.trier.spring_matutino.services.exceptions.ObjetoNaoEncontrado;

@ActiveProfiles("teste")
@AutoConfigureTestDatabase(replace = Replace.ANY)
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:resources/sql/usuario.sql")
@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:resources/sql/limpa_tabelas.sql")
@SpringBootTest(classes = SpringMatutinoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserResourceTest {
	@Autowired
	protected TestRestTemplate rest;

	private ResponseEntity<UserDTO> getUser(String url, HttpHeaders headers) {
		return rest.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), UserDTO.class);
	}

	@SuppressWarnings("unused")
	private ResponseEntity<List<UserDTO>> getUsers(String url, HttpHeaders headers) {
		return rest.exchange(url, HttpMethod.GET, new HttpEntity<>(headers),
				new ParameterizedTypeReference<List<UserDTO>>() {
				});
	}


	@Test
	@DisplayName("Buscar por id")
	public void testGetOk() {
		HttpHeaders headers = new HttpHeaders();
	
		ResponseEntity<UserDTO> response = getUser("/usuarios/1", headers);
		assertEquals(response.getStatusCode(), HttpStatus.OK);

		UserDTO user = response.getBody();
		assertEquals("Usuario1", user.getName());
	}

	@Test
	@DisplayName("Buscar por id inexistente")
	public void testGetNotFound() {
		HttpHeaders headers = new HttpHeaders();
	
		ResponseEntity<UserDTO> response = getUser("/usuarios/30", headers);
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	@Test
	@DisplayName("Obter token")
	@Sql(scripts = "classpath:/resources/sql/limpa_tabelas.sql")
	@Sql(scripts = "classpath:/resources/sql/usuario_tabelas.sql")
	public void getTokenTest(){
		LoginDTO login = new LoginDTO("usuario@gmail.com","321");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", "Bearer " + token);
		HttpEntity<UserDTO> requestEntity = new HttpEntity<>(dto, headers);
		
		
	}
	
	@Test
	@DisplayName("Cadastrar usuário")
	@Sql(scripts = "classpath:/resources/sql/limpa_tabelas.sql")
	public void testCreateUser() {
		UserDTO dto = new UserDTO(null, "cadastra", "cadastra", "cadastra", "ADMIN");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", "Bearer " + token);
		HttpEntity<UserDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<UserDTO> responseEntity = rest.exchange("/usuarios", HttpMethod.POST, requestEntity,
				UserDTO.class);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		UserDTO user = responseEntity.getBody();
		assertEquals("cadastra", user.getName());
	}

	@Test
	@DisplayName("Listar usuários")
	public void testListUsers() {
		HttpHeaders headers = new HttpHeaders();
	
		ResponseEntity<List<UserDTO>> response = getUsers("/usuarios", headers);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		List<UserDTO> users = response.getBody();
		assertEquals(2, users.size());
		assertEquals("Usuario1", users.get(0).getName());
		assertEquals("Usuario2", users.get(1).getName());
	}

	@Test
	@DisplayName("Atualizar usuário")
	public void testUpdateUser() {
		UserDTO dto = new UserDTO(1, "atualiza", "atualiza", "atualiza", "ADMIN");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<UserDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<UserDTO> responseEntity = rest.exchange("/usuarios/1", HttpMethod.PUT, requestEntity,
				UserDTO.class);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		UserDTO user = responseEntity.getBody();
		assertEquals("atualiza", user.getName());
	}

	@Test
	@DisplayName("Deletar usuário")
	public void testDeleteUser() {
		HttpHeaders headers = new HttpHeaders();
		
		ResponseEntity<UserDTO> responseEntity = rest.exchange("/usuarios/1", HttpMethod.DELETE,
				new HttpEntity<>(headers), UserDTO.class);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		ResponseEntity<UserDTO> userResponse = getUser("/usuarios/1", headers);
		assertEquals(HttpStatus.NOT_FOUND, userResponse.getStatusCode());
	}

	@Test
	@DisplayName("Buscar usuário pelo nome")
	public void testFindByName() {
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<List<UserDTO>> response = getUsers("/usuarios/name/Usuario1", headers);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		List<UserDTO> user = response.getBody();
		assertEquals("Usuario1", user.get(0).getName());
	}
}
