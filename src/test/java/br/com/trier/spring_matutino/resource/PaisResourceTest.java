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
import br.com.trier.spring_matutino.domain.Pais;


@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.ANY)
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/resources/sql/pais.sql")
@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:/resources/sql/limpa_tabelas.sql")
@SpringBootTest(classes = SpringMatutinoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PaisResourceTest {
	@Autowired
	protected TestRestTemplate rest;


	private ResponseEntity<Pais> getPais(String url){
		return rest.getForEntity(url, Pais.class);
	}
	
	private ResponseEntity<List<Pais>> getPaiss(String url){
		return rest.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Pais>>() {});
	}
	@Test
	@DisplayName("Buscar por id")
	void FindById() {
		ResponseEntity<Pais> response = getPais("/pais/1");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		Pais camp = response.getBody();
		assertEquals("Brasil", camp.getName());
	}
	
	@Test
	@DisplayName("Buscar por id inexistente")
	void FindByIdNotFound() {
		ResponseEntity<Pais> response = getPais("/pais/10");
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	@DisplayName("Listar todos paiss")
	void listAll() {
		ResponseEntity<List<Pais>> response = getPaiss("/pais");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		List<Pais> lista = response.getBody();
		assertEquals(2, lista.size());
	}
	
	@Test
	@DisplayName("atualizar pais")
	void update() {
		Pais camp = new Pais(2, "pais2");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Pais> requestEntity = new HttpEntity<>(camp, headers);
		ResponseEntity<Pais> responseEntity = rest.exchange("/pais/2",
				HttpMethod.PUT,
				requestEntity,
				Pais.class);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		camp = responseEntity.getBody();
		assertEquals("México", camp.getName());
	}
	
	@Test
	@DisplayName("deleta pais")
	void delete() {
		ResponseEntity<Pais> responseEntity = rest.exchange("/pais/1",
				HttpMethod.DELETE,
				null,
				Pais.class);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		ResponseEntity<Pais> campResponse = getPais("/pais/1");
		assertEquals(HttpStatus.NOT_FOUND, campResponse.getStatusCode());
	}

	

	
}