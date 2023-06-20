
package br.com.trier.spring_matutino.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.spring_matutino.BaseTest;
import br.com.trier.spring_matutino.domain.Campeonato;
import jakarta.transaction.Transactional;

@Transactional
public class CampeonatoServiceTest extends BaseTest {

	@Autowired
	CampeonatoService campeonatoService;

	@Test
	@DisplayName("Teste busca campeonato por ID")
	@Sql(("classpath:/resources/sql/campeonato.sql"))
	void findByIdTest() {
		var campeonato = campeonatoService.findById(1);
		assertThat(campeonato).isNotNull();
		assertEquals(1, campeonato.getId());
		assertEquals("campeonato1", campeonato.getDescricao());
		assertEquals(2023, campeonato.getYear());

	}

	@Test
	@DisplayName("Teste busca por ID campeonato inexistente")
	@Sql(("classpath:/resources/sql/campeonato.sql"))
	void findNonExistent() {
		var campeonato = campeonatoService.findById(10);
		assertThat(campeonato).isNull();
	}

	@Test
	@DisplayName("Teste atualização de campeonato")
	@Sql(("classpath:/resources/sql/campeonato.sql"))
	void updatecampeonatoTest() {
		var campeonato = campeonatoService.findById(1);
		campeonato.setDescricao("Novo campeonato");
		campeonato.setYear(2022);
		

		Campeonato updatedCampeonato = campeonatoService.update(campeonato);

		assertThat(updatedCampeonato).isNotNull();
		assertEquals(1, updatedCampeonato.getId());

		assertEquals("Novo campeonato", updatedCampeonato.getDescricao());
		assertEquals(2022, updatedCampeonato.getYear());

	}

	@Test
	@DisplayName("Teste exclusão de campeonato")
	@Sql(("classpath:/resources/sql/campeonato.sql"))
	void deleteCampeonatoTest() {
		var campeonato = campeonatoService.findById(1);
		campeonatoService.delete(campeonato.getId());

		var deletedCampeonato = campeonatoService.findById(1);
		assertThat(deletedCampeonato).isNull();
	}

	@Test
	@DisplayName("Teste listagem de campeonato")
	@Sql(("classpath:/resources/sql/campeonato.sql"))
	void listAllTest() {
		List<Campeonato> lista = campeonatoService.listAll();
		assertEquals(2, lista.size());
		assertEquals(1, lista.get(0).getId());
	}
}
