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
import br.com.trier.spring_matutino.domain.Campeonato;
import br.com.trier.spring_matutino.domain.User;
import br.com.trier.spring_matutino.services.exceptions.ObjetoNaoEncontrado;
import jakarta.transaction.Transactional;

@Transactional
public class CampeonatoServiceTest extends BaseTest {

	@Autowired
	CampeonatoService campeonatoService;

	@Test
	@DisplayName("Teste busca campeonato por ID")
	@Sql("classpath:/resources/sql/campeonato.sql")
	void findByIdTest() {
		var campeonato = campeonatoService.findById(1);
		assertThat(campeonato).isNotNull();
		assertEquals(1, campeonato.getId());
		assertEquals("campeonato1", campeonato.getDescricao());
		assertEquals(2015, campeonato.getYear());
	}


	@Test
	@DisplayName("Teste busca por ID de campeonato inexistente")
	@Sql("classpath:/resources/sql/campeonato.sql")
	void findNonExistent() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> campeonatoService.findById(10));
		assertEquals("Campeonato 10 não encontrado!", exception.getMessage());
	}

	@Test
	@DisplayName("Teste atualização de campeonato")
	@Sql("classpath:/resources/sql/campeonato.sql")
	void updateCampeonatoTest() {
		var campeonato = campeonatoService.findById(1);
		campeonato.setDescricao("Novo campeonato");
		campeonato.setYear(2010);

		Campeonato updatedCampeonato = campeonatoService.update(campeonato);

		assertThat(updatedCampeonato).isNotNull();
		assertEquals(1, updatedCampeonato.getId());
		assertEquals("Novo campeonato", updatedCampeonato.getDescricao());
		assertEquals(2010, updatedCampeonato.getYear());
	}


	@Test
	@DisplayName("Teste delete campeonato")
	@Sql(("classpath:/resources/sql/campeonato.sql"))
	void deleteTest() {
		campeonatoService.delete(1);
		List<Campeonato> lista = campeonatoService.listAll();
		assertEquals(1, lista.size());
		assertEquals(2, lista.get(0).getId());		
	}

	@Test
	@DisplayName("Teste listagem de campeonato")
	@Sql("classpath:/resources/sql/campeonato.sql")
	void listAllTest() {
		List<Campeonato> lista = campeonatoService.listAll();
		assertEquals(2, lista.size());
		assertEquals(1, lista.get(0).getId());
	}

	@Test
	@DisplayName("Teste busca campeonato por ano entre intervalo")
	@Sql("classpath:/resources/sql/campeonato.sql")
	void listaCampeonatoAnoBetweenTest() {
		var lista = campeonatoService.findByYearBetween(1990, 2015);
		assertEquals(2, lista.size());
	}

	@Test
	@DisplayName("Teste busca campeonato por ano")
	@Sql("classpath:/resources/sql/campeonato.sql")
	void buscaCampeonatoAnoTest() {
		var lista = campeonatoService.findByYear(2015);
		assertEquals(1, lista.size());
		assertEquals("campeonato1", lista.get(0).getDescricao());
		assertEquals(1, lista.get(0).getId());
	}

	@Test
	@DisplayName("Teste busca ano inexistente")
	@Sql("classpath:/resources/sql/campeonato.sql")
	void findByAnoNonExistentTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> campeonatoService.findByYear(1996));
		assertEquals("Campeonato de 1996 não encontrado", exception.getMessage());
	}

	@Test
	@DisplayName("Teste busca campeonato por descrição")
	@Sql("classpath:/resources/sql/campeonato.sql")
	void buscaCampeonatoDescTest() {
		var lista = campeonatoService.findByDescricaoContainsIgnoreCase("campeonato1");
		assertEquals("campeonato1", lista.get(0).getDescricao());
		lista = campeonatoService.findByDescricaoContainsIgnoreCase("nato");
		assertEquals(1, lista.get(0).getId());
	}

	@Test
	@DisplayName("Teste busca descrição inexistente")
	@Sql("classpath:/resources/sql/campeonato.sql")
	void findByDescNonExistentTest() {
		var lista = campeonatoService.findByDescricaoContainsIgnoreCase("campeonato");
		assertEquals(2, lista.size());
	}

	@Test
	@DisplayName("Teste busca campeonato por descrição e por ano")
	@Sql("classpath:/resources/sql/campeonato.sql")
	void buscaCampeonatoDescAnoTest() {
		var lista = campeonatoService.findByDescricaoContainsIgnoreCaseAndYearEquals("campeonato1", 2015);
		assertEquals("campeonato1", lista.get(0).getDescricao());
		assertEquals(1, lista.get(0).getId());
		assertEquals(1, lista.size());
	}

	@Test
	@DisplayName("Teste lista todos sem nenhum")
	void listTodosErroTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> campeonatoService.listAll());
		assertEquals("Nenhum campeonato encontrado", exception.getMessage());
		List<Campeonato> lista = campeonatoService.listAll();
		assertEquals(0, lista.size());
	}
}
