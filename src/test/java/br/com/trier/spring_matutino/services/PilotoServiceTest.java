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

import br.com.trier.spring_matutino.domain.Piloto;

import br.com.trier.spring_matutino.services.exceptions.ObjetoNaoEncontrado;

import jakarta.transaction.Transactional;

@Transactional
public class PilotoServiceTest extends BaseTest {

	@Autowired
	PilotoService pilotoService;

	@Autowired
	PaisService paisService;

	@Autowired
	EquipeService equipeService;

	@Test
	@DisplayName("Teste inserir piloto")
	@Sql({ "classpath:/resources/sql/pais.sql" })
	@Sql({ "classpath:/resources/sql/equipe.sql" })
	void insertTest() {
		var piloto = new Piloto(null, "Cleiton", paisService.findById(2), equipeService.findById(1));
		pilotoService.salvar(piloto);
		assertEquals(1, pilotoService.listAll().size());
		assertEquals("Cleiton", piloto.getName());
	}

	@Test
	@DisplayName("Teste busca piloto por ID")
	@Sql({ "classpath:/resources/sql/pais.sql" })
	@Sql({ "classpath:/resources/sql/equipe.sql" })
	@Sql({ "classpath:/resources/sql/piloto.sql" })
	void findById() {
		var piloto = pilotoService.findById(1);
		assertThat(piloto).isNotNull();
		assertEquals(1, piloto.getId());
		assertEquals("Piloto1", piloto.getName());

	}

	@Test
	@DisplayName("Teste atualização de piloto")
	@Sql({ "classpath:/resources/sql/pais.sql" })
	@Sql({ "classpath:/resources/sql/equipe.sql" })
	@Sql({ "classpath:/resources/sql/piloto.sql" })
	void updateTest() {
		var piloto = new Piloto(1, "Novo piloto", paisService.findById(1), equipeService.findById(1));
		pilotoService.update(piloto);
		var novoPiloto = pilotoService.findById(1);
		assertEquals("Novo piloto", novoPiloto.getName());
	}

	@Test
	@DisplayName("Teste buscar piloto pelo id inválido")
	@Sql({ "classpath:/resources/sql/pais.sql" })
	@Sql({ "classpath:/resources/sql/equipe.sql" })
	@Sql({ "classpath:/resources/sql/piloto.sql" })
	void findByIdNotFoundTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> pilotoService.findById(10));
		assertEquals("Piloto id 10 não existe", exception.getMessage());
	}

	@Test
	@DisplayName("Teste deletar piloto")
	@Sql({ "classpath:/resources/sql/pais.sql" })
	@Sql({ "classpath:/resources/sql/equipe.sql" })
	@Sql({ "classpath:/resources/sql/piloto.sql" })
	void deleteTest() {
		pilotoService.delete(1);
		assertEquals(1, pilotoService.listAll().size());
	}

	@Test
	@DisplayName("Teste deletar piloto invalido")
	@Sql({ "classpath:/resources/sql/pais.sql" })
	@Sql({ "classpath:/resources/sql/equipe.sql" })
	@Sql({ "classpath:/resources/sql/piloto.sql" })
	void deleteInvalidTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> pilotoService.delete(5));
		assertEquals("Piloto id 5 não existe", exception.getMessage());
	}

	@Test
	@DisplayName("Teste listar todos os pilotos")
	@Sql({ "classpath:/resources/sql/pais.sql" })
	@Sql({ "classpath:/resources/sql/equipe.sql" })
	@Sql({ "classpath:/resources/sql/piloto.sql" })
	void listAllTest() {
		assertEquals(2, pilotoService.listAll().size());
	}

	@Test
	@DisplayName("Teste listar pilotos com lista vazia")
	void listAllEmptyTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> pilotoService.listAll());
		assertEquals("Nenhuma piloto cadastrada", exception.getMessage());
	}

	@Test
	@DisplayName("Teste busca piloto por pais")
	@Sql({ "classpath:/resources/sql/pais.sql" })
	@Sql({ "classpath:/resources/sql/equipe.sql" })
	@Sql({ "classpath:/resources/sql/piloto.sql" })
	void findByPaisTest() {
		var lista = pilotoService.findByPais(paisService.findById(2));
		assertEquals(2, lista.size());
	}
	@Test
	@DisplayName("Teste buscar piloto por ID páis inválido")
	@Sql({ "classpath:/resources/sql/pais.sql" })
	@Sql({ "classpath:/resources/sql/equipe.sql" })
	@Sql({ "classpath:/resources/sql/piloto.sql" })
	void findByPaisNotFoundTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> paisService.findById(10));
		assertEquals("País 10 não encontrada!", exception.getMessage());
	}

	@Test
	@DisplayName("Teste busca por Equipe")
	@Sql({ "classpath:/resources/sql/pais.sql" })
	@Sql({ "classpath:/resources/sql/equipe.sql" })
	@Sql({ "classpath:/resources/sql/piloto.sql" })
	void findByEquipeTest() {
		var lista = pilotoService.findByEquipe(equipeService.findById(1));
		assertEquals(1, lista.size());
	}
	@Test
	@DisplayName("Teste buscar piloto por equipe inválida")
	@Sql({ "classpath:/resources/sql/pais.sql" })
	@Sql({ "classpath:/resources/sql/equipe.sql" })
	@Sql({ "classpath:/resources/sql/piloto.sql" })
	void findByEquipeNotFoundTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> equipeService.findById(10));
		assertEquals("Equipe 10 não encontrada!", exception.getMessage());
	}
	@Test
	@DisplayName("Teste buscar piloto por nome de equipe inválida")
	@Sql({ "classpath:/resources/sql/pais.sql" })
	@Sql({ "classpath:/resources/sql/equipe.sql" })
	@Sql({ "classpath:/resources/sql/piloto.sql" })
	void findByNameEquipeNotFoundTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> equipeService.findByName("x"));
		assertEquals("Nenhum nome de equipe inicia com x", exception.getMessage());
	}
	@Test
	@DisplayName("Teste busca piloto pelo nome")
	@Sql({ "classpath:/resources/sql/pais.sql" })
	@Sql({ "classpath:/resources/sql/equipe.sql" })
	@Sql({ "classpath:/resources/sql/piloto.sql" })
	void findByNameStartsWithIgnoreCaseTest() {
		var lista = pilotoService.findByNameStartsWithIgnoreCase("P");
		assertEquals(2, lista.size());
	}

	@Test
	@DisplayName("Teste buscar piloto pelo nome inválido")
	@Sql({ "classpath:/resources/sql/pais.sql" })
	@Sql({ "classpath:/resources/sql/equipe.sql" })
	@Sql({ "classpath:/resources/sql/piloto.sql" })
	void findByNameStartsWithIgnoreCaseNotFoundTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class,
				() -> pilotoService.findByNameStartsWithIgnoreCase("J"));
		assertEquals("Nenhum nome de piloto inicia com J", exception.getMessage());
	}

}
