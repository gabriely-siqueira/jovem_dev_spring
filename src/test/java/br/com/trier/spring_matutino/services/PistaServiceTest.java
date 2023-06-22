package br.com.trier.spring_matutino.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.spring_matutino.BaseTest;
import br.com.trier.spring_matutino.domain.Pais;
import br.com.trier.spring_matutino.domain.Piloto;
import br.com.trier.spring_matutino.domain.Pista;
import br.com.trier.spring_matutino.services.exceptions.ObjetoNaoEncontrado;
import jakarta.transaction.Transactional;

@Transactional
public class PistaServiceTest extends BaseTest {

	@Autowired
	PistaService pistaService;

	@Autowired
	PaisService paisService;

	@Test
	@DisplayName("Teste inserção pista")
	@Sql({ "classpath:/resources/sql/pais.sql" })
	void insertTest() {
		var pista = new Pista(null, 5000, paisService.findById(1));
		pistaService.salvar(pista);
		assertEquals(1, pistaService.listAll().size());
		assertEquals("Brasil", pistaService.findById(1).getPais().getName());
	}

	@Test
	@DisplayName("Teste de busca de pista pelo id")
	@Sql({ "classpath:/resources/sql/pais.sql", "classpath:/resources/sql/pista.sql" })
	void findByIdTest() {
		var pista = pistaService.findById(1);
		assertEquals(3000, pista.getTamanho());
		assertEquals(1, pista.getPais().getId());
	}

	@Test
	@DisplayName("Teste buscar pista pelo id inválido")
	@Sql({ "classpath:/resources/sql/pais.sql", "classpath:/resources/sql/pista.sql" })
	void findByIdNotFoundTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> pistaService.findById(10));
		assertEquals("Pista id 10 não existe", exception.getMessage());
	}
	@Test
	@DisplayName("Teste atualização de Pista")
	@Sql({ "classpath:/resources/sql/pais.sql" })
	@Sql({ "classpath:/resources/sql/pista.sql" })
	void updateTest() {
		var pista = new Pista(1, 4000, paisService.findById(1));
		pistaService.update(pista);
		var novaPista = pistaService.findById(1);
		assertEquals(1, novaPista.getId());
	}

	@Test
	@DisplayName("Teste de exclusão de pista")
	@Sql({ "classpath:/resources/sql/pais.sql", "classpath:/resources/sql/pista.sql" })
	void deleteTest() {
		pistaService.delete(1);
		assertEquals(1, pistaService.listAll().size());
	}

	@Test
	@DisplayName("Teste de listagem de pistas")
	@Sql({ "classpath:/resources/sql/pais.sql" })
	@Sql({ "classpath:/resources/sql/pista.sql" })
	void listPistasTest() {
		assertEquals(2, pistaService.listAll().size());
	}

	@Test
	@DisplayName("Teste de busca de pista inexistente")
	@Sql({ "classpath:/resources/sql/pais.sql", "classpath:/resources/sql/pista.sql" })
	void findByIdNonExistentTest() {
		assertThrows(ObjetoNaoEncontrado.class, () -> {
			pistaService.findById(9999);
		});
	}

}
