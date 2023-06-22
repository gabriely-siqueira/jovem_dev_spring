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
import br.com.trier.spring_matutino.domain.Pais;

import br.com.trier.spring_matutino.services.exceptions.ObjetoNaoEncontrado;
import jakarta.transaction.Transactional;

@Transactional
public class PaisServiceTest extends BaseTest {

	@Autowired
	PaisService paisService;
	@Test
	@DisplayName("Teste inserir um pais")
	void insertTest() {
		var pais = new Pais(null, "Brasil");
		paisService.salvar(pais);
		List<Pais> paises = paisService.listAll();
		assertEquals(1, paises.size());
		assertEquals("Brasil", paises.get(0).getName());
	}

	@Test
	@DisplayName("Teste busca pais por ID")
	@Sql(("classpath:/resources/sql/pais.sql"))
	void findByIdTest() {
		var pais = paisService.findById(1);
		assertThat(pais).isNotNull();
		assertEquals(1, pais.getId());
		assertEquals("Brasil", pais.getName());

	}

	@Test
	@DisplayName("Teste busca por ID pais inexistente")
	@Sql(("classpath:/resources/sql/pais.sql"))
	void findByIdNonExistentTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> paisService.findById(10));
		assertEquals("País 10 não encontrada!", exception.getMessage());
	}


	@Test
	@DisplayName("Teste atualização de país")
	@Sql(("classpath:/resources/sql/pais.sql"))
	void updatepaisTest() {
		var pais = paisService.findById(1);
		pais.setName("Novo País");

		Pais updatedPais = paisService.update(pais);

		assertThat(updatedPais).isNotNull();
		assertEquals(1, updatedPais.getId());

		assertEquals("Novo País", updatedPais.getName());

	}
	@Test
	@DisplayName("Teste delete pais")
	@Sql(("classpath:/resources/sql/pais.sql"))
	void deleteTest() {
		paisService.delete(2);
		List<Pais> lista = paisService.listAll();
		assertEquals(1, lista.size());
		assertEquals(1, lista.get(0).getId());		
	}


	@Test
	@DisplayName("Teste listagem de país")
	@Sql(("classpath:/resources/sql/pais.sql"))
	void listAllTest() {
		List<Pais> lista = paisService.listAll();
		assertEquals(2, lista.size());
		assertEquals(1, lista.get(0).getId());
	}
}
