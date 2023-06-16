
package br.com.trier.spring_matutino.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.spring_matutino.BaseTest;
import br.com.trier.spring_matutino.domain.Equipe;
import jakarta.transaction.Transactional;

@Transactional
public class EquipeServiceTest extends BaseTest {

	@Autowired
	EquipeService equipeService;

	@Test
	@DisplayName("Teste busca equipe por ID")
	@Sql(("classpath:/resources/sql/equipe.sql"))
	void findByIdTest() {
		var equipe = equipeService.findById(1);
		assertThat(equipe).isNotNull();
		assertEquals(1, equipe.getId());
		assertEquals("equipe1", equipe.getName());


	}

	@Test
	@DisplayName("Teste busca por ID equipe inexistente")
	@Sql(("classpath:/resources/sql/equipe.sql"))
	void findNonExistent() {
		var equipe = equipeService.findById(10);
		assertThat(equipe).isNull();
	}

	@Test
	@DisplayName("Teste atualização de equipe")
	@Sql(("classpath:/resources/sql/equipe.sql"))
	void updateequipeTest() {
		var equipe = equipeService.findById(1);
		equipe.setName("Novo equipe");

		

		Equipe updatedEquipe = equipeService.update(equipe);

		assertThat(updatedEquipe).isNotNull();
		assertEquals(1, updatedEquipe.getId());

		assertEquals("Novo equipe", updatedEquipe.getName());
		

	}

	@Test
	@DisplayName("Teste exclusão de equipe")
	@Sql(("classpath:/resources/sql/equipe.sql"))
	void deleteEquipeTest() {
		var equipe = equipeService.findById(1);
		equipeService.delete(equipe.getId());

		var deletedEquipe = equipeService.findById(1);
		assertThat(deletedEquipe).isNull();
	}

	@Test
	@DisplayName("Teste listagem de equipe")
	@Sql(("classpath:/resources/sql/equipe.sql"))
	void listAllTest() {
		List<Equipe> lista = equipeService.listAll();
		assertEquals(2, lista.size());
		assertEquals(1, lista.get(0).getId());
	}
}
