package br.com.trier.spring_matutino.resource;

import static org.junit.jupiter.api.Assertions.assertEquals;


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

@ActiveProfiles("teste")
@AutoConfigureTestDatabase(replace = Replace.ANY)
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:resources/sql/usuario.sql")
@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:resources/sql/limpa_tabelas.sql")
@SpringBootTest(classes = SpringMatutinoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserResourceTest {
	@Autowired
	protected TestRestTemplate rest;

	private HttpHeaders getHeaders(String email, String password) {
	    LoginDTO loginDTO = new LoginDTO(email, password);
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity<LoginDTO> requestEntity = new HttpEntity<>(loginDTO, headers);
	    ResponseEntity<String> responseEntity = rest.exchange(
	            "/auth/token",
	            HttpMethod.POST,
	            requestEntity,
	            String.class
	    );
	    String token = responseEntity.getBody();
	    headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.setBearerAuth(token);
	    return headers;
	}

	private ResponseEntity<UserDTO> getUser(String url) {
	    return rest.exchange(
	            url,
	            HttpMethod.GET,
	            new HttpEntity<>(getHeaders("Usuario1", "321")),
	            UserDTO.class
	    );
	}

	private ResponseEntity<List<UserDTO>> getUsers(String url) {
	    return rest.exchange(
	            url,
	            HttpMethod.GET,
	            new HttpEntity<>(getHeaders("Usuario1", "321")),
	            new ParameterizedTypeReference<List<UserDTO>>() {
	            }
	    );
	}

	@Test
	@DisplayName("Buscar por nome")
	@Sql({ "classpath:/resources/sql/limpa_tabelas.sql" })
	@Sql({ "classpath:/resources/sql/usuario.sql" })
	public void findByNameTest() {
	    ResponseEntity<List<UserDTO>> response = getUsers("/usuarios/name/u");
	    assertEquals(HttpStatus.OK, response.getStatusCode());
	    assertEquals(3, response.getBody().size());
	}

	@Test
	@DisplayName("Buscar por id")
	@Sql({ "classpath:/resources/sql/limpa_tabelas.sql" })
	@Sql({ "classpath:/resources/sql/usuario.sql" })
	public void findByIdTest() {
	    ResponseEntity<UserDTO> response = getUser("/usuarios/2");
	    assertEquals(HttpStatus.OK, response.getStatusCode());
	    UserDTO user = response.getBody();
	    assertEquals("Usuario2", user.getName());
	}

	@Test
	@DisplayName("Buscar por id inexistente")
	@Sql({ "classpath:/resources/sql/limpa_tabelas.sql" })
	@Sql({ "classpath:/resources/sql/usuario.sql" })
	public void testGetNotFound() {
	    ResponseEntity<UserDTO> response = getUser("/usuarios/100");
	    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	@DisplayName("Cadastrar usuário")
	@Sql({ "classpath:/resources/sql/limpa_tabelas.sql" })
	@Sql({ "classpath:/resources/sql/usuario.sql" })
	public void testCreateUser() {
	    UserDTO dto = new UserDTO(null, "nome", "email", "senha", "USER");
	    HttpHeaders headers = getHeaders("Usuario1", "321");
	    HttpEntity<UserDTO> requestEntity = new HttpEntity<>(dto, headers);
	    ResponseEntity<UserDTO> responseEntity = rest.exchange(
	            "/usuarios",
	            HttpMethod.POST,
	            requestEntity,
	            UserDTO.class
	    );
	    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	    UserDTO user = responseEntity.getBody();
	    assertEquals("nome", user.getName());
	}

	@Test
	@DisplayName("Listar Todos")
	@Sql({ "classpath:/resources/sql/limpa_tabelas.sql" })
	@Sql({ "classpath:/resources/sql/usuario.sql" })
	public void findAll() {
	    ResponseEntity<List<UserDTO>> response = rest.exchange(
	            "/usuarios",
	            HttpMethod.GET,
	            new HttpEntity<>(getHeaders("Usuario1", "321")),
	            new ParameterizedTypeReference<List<UserDTO>>() {
	            }
	    );
	    assertEquals(HttpStatus.OK, response.getStatusCode());
	    assertEquals(3, response.getBody().size());
	}

	@Test
	@DisplayName("Alterar usuário")
	@Sql({ "classpath:/resources/sql/limpa_tabelas.sql" })
	@Sql({ "classpath:/resources/sql/usuario.sql" })
	public void testUpdateUser() {
	    UserDTO dto = new UserDTO(3, "Usuario3", "email", "senha", "ADMIN");
	    HttpHeaders headers = getHeaders("Usuario1", "321");
	    HttpEntity<UserDTO> requestEntity = new HttpEntity<>(dto, headers);
	    ResponseEntity<UserDTO> responseEntity = rest.exchange(
	            "/usuarios/3",
	            HttpMethod.PUT,
	            requestEntity,
	            UserDTO.class
	    );
	    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	    UserDTO user = responseEntity.getBody();
	    assertEquals("Usuario3", user.getName());
	}

	@Test
	@DisplayName("Excluir usuário")
	@Sql({ "classpath:/resources/sql/limpa_tabelas.sql" })
	@Sql({ "classpath:/resources/sql/usuario.sql" })
	public void testDeleteUser() {
	    HttpHeaders headers = getHeaders("Usuario1", "321");
	    HttpEntity<Void> requestEntity = new HttpEntity<>(null, headers);
	    ResponseEntity<Void> responseEntity = rest.exchange(
	            "/usuarios/3",
	            HttpMethod.DELETE,
	            requestEntity,
	            Void.class
	    );
	    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	@DisplayName("Excluir usuário inexistente")
	@Sql({ "classpath:/resources/sql/limpa_tabelas.sql" })
	@Sql({ "classpath:/resources/sql/usuario.sql" })
	public void testDeleteNonExistUser() {
	    HttpHeaders headers = getHeaders("Usuario1", "321");
	    HttpEntity<Void> requestEntity = new HttpEntity<>(null, headers);
	    ResponseEntity<Void> responseEntity = rest.exchange(
	            "/usuarios/100",
	            HttpMethod.DELETE,
	            requestEntity,
	            Void.class
	    );
	    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
}
