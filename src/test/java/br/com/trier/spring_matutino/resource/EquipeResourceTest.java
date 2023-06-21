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
import br.com.trier.spring_matutino.domain.Equipe;


@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.ANY)
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/resources/sql/equipe.sql")
@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:/resources/sql/limpa_tabelas.sql")
@SpringBootTest(classes = SpringMatutinoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EquipeResourceTest {
	@Autowired
	protected TestRestTemplate rest;


	private ResponseEntity<Equipe> getEquipe(String url){
		return rest.getForEntity(url, Equipe.class);
	}
	
	private ResponseEntity<List<Equipe>> getEquipes(String url){
		return rest.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Equipe>>() {});
	}
	@Test
	@DisplayName("Buscar por id")
	void FindById() {
		ResponseEntity<Equipe> response = getEquipe("/equipes/1");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		Equipe camp = response.getBody();
		assertEquals("equipe1", camp.getName());
	}
	
	@Test
	@DisplayName("Buscar por id inexistente")
	void FindByIdNotFound() {
		ResponseEntity<Equipe> response = getEquipe("/equipes/10");
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	@DisplayName("Listar todos equipes")
	void listAll() {
		ResponseEntity<List<Equipe>> response = getEquipes("/equipes");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		List<Equipe> lista = response.getBody();
		assertEquals(2, lista.size());
	}
	
	@Test
	@DisplayName("atualizar equipe")
	void update() {
		Equipe camp = new Equipe(2, "equipe2");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Equipe> requestEntity = new HttpEntity<>(camp, headers);
		ResponseEntity<Equipe> responseEntity = rest.exchange("/equipes/2",
				HttpMethod.PUT,
				requestEntity,
				Equipe.class);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		camp = responseEntity.getBody();
		assertEquals("equipe2", camp.getName());
	}
	
	@Test
	@DisplayName("deleta equipe")
	void delete() {
		ResponseEntity<Equipe> responseEntity = rest.exchange("/equipes/1",
				HttpMethod.DELETE,
				null,
				Equipe.class);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		ResponseEntity<Equipe> campResponse = getEquipe("/equipes/1");
		assertEquals(HttpStatus.NOT_FOUND, campResponse.getStatusCode());
	}

	

	
}