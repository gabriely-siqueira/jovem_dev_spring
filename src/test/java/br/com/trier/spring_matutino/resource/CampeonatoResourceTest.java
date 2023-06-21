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
import br.com.trier.spring_matutino.domain.Campeonato;


@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.ANY)
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/resources/sql/campeonato.sql")
@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:/resources/sql/limpa_tabelas.sql")
@SpringBootTest(classes = SpringMatutinoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CampeonatoResourceTest {
	@Autowired
	protected TestRestTemplate rest;


	private ResponseEntity<Campeonato> getCampeonato(String url){
		return rest.getForEntity(url, Campeonato.class);
	}
	
	private ResponseEntity<List<Campeonato>> getCampeonatos(String url){
		return rest.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Campeonato>>() {});
	}
	@Test
	@DisplayName("Buscar por id")
	void FindById() {
		ResponseEntity<Campeonato> response = getCampeonato("/campeonatos/1");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		Campeonato camp = response.getBody();
		assertEquals("campeonato1", camp.getDescricao());
	}
	
	@Test
	@DisplayName("Buscar por id inexistente")
	void FindByIdNotFound() {
		ResponseEntity<Campeonato> response = getCampeonato("/campeonatos/10");
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	@DisplayName("Listar todos campeonatos")
	void listAll() {
		ResponseEntity<List<Campeonato>> response = getCampeonatos("/campeonatos");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		List<Campeonato> lista = response.getBody();
		assertEquals(2, lista.size());
	}
	
	@Test
	@DisplayName("atualizar campeonato")
	void update() {
		Campeonato camp = new Campeonato(2, "campeonato2", 2015);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Campeonato> requestEntity = new HttpEntity<>(camp, headers);
		ResponseEntity<Campeonato> responseEntity = rest.exchange("/campeonatos/2",
				HttpMethod.PUT,
				requestEntity,
				Campeonato.class);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		camp = responseEntity.getBody();
		assertEquals("campeonato2", camp.getDescricao());
	}
	
	@Test
	@DisplayName("deleta campeonato")
	void delete() {
		ResponseEntity<Campeonato> responseEntity = rest.exchange("/campeonatos/1",
				HttpMethod.DELETE,
				null,
				Campeonato.class);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		ResponseEntity<Campeonato> campResponse = getCampeonato("/campeonatos/1");
		assertEquals(HttpStatus.NOT_FOUND, campResponse.getStatusCode());
	}

	@Test
	@DisplayName("buscar campeonatos pelo ano")
	void findByAnoTest() {
		ResponseEntity<List<Campeonato>> response = getCampeonatos("/campeonato/year/2015");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	
}