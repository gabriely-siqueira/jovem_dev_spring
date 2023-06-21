package br.com.trier.spring_matutino.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.spring_matutino.BaseTest;
import br.com.trier.spring_matutino.domain.Pais;
import br.com.trier.spring_matutino.domain.Pista;

@SpringBootTest
public class PistaServiceTest extends BaseTest {
	
	@Autowired
	PistaService pistaService;
	
	@Autowired
	PaisService paisService;

	@Test
	@DisplayName("Teste inserção de país")
	void insertPaisTest() {
		Pais novoPais = new Pais();
		novoPais.setName("Brasil");

		Pais insertedPais = paisService.salvar(novoPais);

		assertThat(insertedPais).isNotNull();
		assertThat(insertedPais.getId()).isPositive();

		assertEquals("Brasil", insertedPais.getName());
	}

	@Test
	@DisplayName("Teste inserção de pista")
	void insertPistaTest() {
		Pais pais = paisService.findById(1); 
		Pista novaPista = new Pista();
		novaPista.setTamanho(3000);
		novaPista.setPais(pais); 

		Pista insertedPista = pistaService.salvar(novaPista);

		assertThat(insertedPista).isNotNull();
		assertThat(insertedPista.getId()).isPositive();
		assertEquals(3000, insertedPista.getTamanho());
		assertEquals(pais, insertedPista.getPais());
	}
	@Test
	@DisplayName("Teste busca pista por ID")//não funciona
	@Sql("classpath:/resources/sql/pista.sql")
	void findByIdTest() {
		var pista = pistaService.findById(1);
		assertThat(pista).isNotNull();
		assertEquals(1, pista.getId());
		assertEquals(3000, pista.getTamanho());
		assertEquals(1, pista.getPais());

	}
	@Test
	@DisplayName("Teste atualização de pista")
	void updatePistaTest() {
	    Pista pista = pistaService.findById(1);
	    pista.setTamanho(4000);

	    Pista updatedPista = pistaService.salvar(pista);

	    assertThat(updatedPista).isNotNull();
	    assertEquals(1, updatedPista.getId());
	    assertEquals(4000, updatedPista.getTamanho());
	}

	@Test
	@DisplayName("Teste delete pista")//não funciona
	@Sql(("classpath:/resources/sql/pista.sql"))
	void deleteTest() {
		pistaService.delete(2);
		List<Pista> lista = pistaService.listAll();
		assertEquals(1, lista.size());
		assertEquals(1, lista.get(0).getId());		
	}

	@Test
	@DisplayName("Teste listagem de pistas")
	void listPistasTest() {
	    List<Pista> pistas = pistaService.listAll();

	    assertThat(pistas).isNotNull();
	    assertThat(pistas).hasSizeGreaterThan(0);
	}

	@Test
	@DisplayName("Teste busca de pistas por país")
	void findPistasByPaisTest() {
	    Pais pais = paisService.findById(1);

	    List<Pista> pistas = pistaService.findByPais(pais);

	    assertThat(pistas).isNotNull();
	    assertThat(pistas).hasSizeGreaterThan(0);

	    for (Pista pista : pistas) {
	        assertEquals(pais, pista.getPais());
	    }
	}

	
}
