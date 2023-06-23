package br.com.trier.spring_matutino.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.spring_matutino.BaseTest;
import br.com.trier.spring_matutino.domain.Corrida;
import br.com.trier.spring_matutino.domain.Piloto;
import br.com.trier.spring_matutino.domain.PilotoCorrida;
import br.com.trier.spring_matutino.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.spring_matutino.services.exceptions.ViolacaoIntegridade;
import jakarta.transaction.Transactional;

@Transactional
public class PilotoCorridaServiceTest extends BaseTest {

	@Autowired
	private PilotoCorridaService service;
	@Autowired
	private PilotoService pilotoService;
	@Autowired
	private CorridaService corridaService;

	@Test
	@DisplayName("Teste inserir piloto_corrida")
	@Sql({ "classpath:/resources/sql/bd_piloto_corrida.sql" })

	void insertTest() {
		var pilotoCorrida = new PilotoCorrida(null, pilotoService.findById(1), corridaService.findById(1), 1);
		service.salvar(pilotoCorrida);
		assertEquals(1, service.listAll().size());
	}

	@Test
	@DisplayName("Teste inserir piloto_corrida invalido")
	@Sql({ "classpath:/resources/sql/bd_piloto_corrida.sql" })

	void insertInvalidTest() {
		var pilotoCorrida = new PilotoCorrida(null, pilotoService.findById(1), corridaService.findById(1), null);
		var exception = assertThrows(ViolacaoIntegridade.class, () -> service.salvar(pilotoCorrida));
		assertEquals("Preencha a colocação", exception.getMessage());
	}

	@Test
	@DisplayName("Teste deletar piloto_corrida")
	@Sql({ "classpath:/resources/sql/bd_piloto_corrida.sql" })
	@Sql({ "classpath:/resources/sql/piloto_corrida.sql" })
	void deleteTest() {
		service.delete(1);
		assertEquals(2, service.listAll().size());
	}

	@Test
	@DisplayName("Teste alterar piloto_corrida")
	@Sql({ "classpath:/resources/sql/bd_piloto_corrida.sql" })
	@Sql({ "classpath:/resources/sql/piloto_corrida.sql" })
	void updateTest() {
		var pilotoCorrida = new PilotoCorrida(1, pilotoService.findById(1), corridaService.findById(1), 1);
		service.update(pilotoCorrida);
		assertEquals(1, service.listAll().get(0).getColocacao());
	}

	@Test
	@DisplayName("Teste alterar piloto_corrida inexistente")
	@Sql({ "classpath:/resources/sql/bd_piloto_corrida.sql" })
	@Sql({ "classpath:/resources/sql/piloto_corrida.sql" })
	void updateInvalidTest() {
		var pilotoCorrida = new PilotoCorrida(10, pilotoService.findById(1), corridaService.findById(1), 1);
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.update(pilotoCorrida));
		assertEquals("Esse cadastro não existe", exception.getMessage());
	}

	@Test
	@DisplayName("Teste listar todos piloto_corrida")
	@Sql({ "classpath:/resources/sql/bd_piloto_corrida.sql" })
	@Sql({ "classpath:/resources/sql/piloto_corrida.sql" })
	void listAllTest() {
		assertEquals(3, service.listAll().size());
	}

	@Test
	@DisplayName("Teste listar todos piloto_corrida com lista vazia")
	@Sql({ "classpath:/resources/sql/bd_piloto_corrida.sql" })
	void listAllEmptyTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.listAll());
		assertEquals("Não existe cadastros", exception.getMessage());
	}

	@Test
	@DisplayName("Teste buscar piloto_corrida pelo id")
	@Sql({ "classpath:/resources/sql/bd_piloto_corrida.sql" })
	@Sql({ "classpath:/resources/sql/piloto_corrida.sql" })
	void findByIdTest() {
		var pilotoCorrida = service.findById(1);
		assertEquals(1, pilotoCorrida.getColocacao());
		assertEquals(1, pilotoCorrida.getPiloto().getId());
	}

	@Test
	@DisplayName("Teste buscar piloto_corrida pelo id inválido")
	@Sql({ "classpath:/resources/sql/bd_piloto_corrida.sql" })
	@Sql({ "classpath:/resources/sql/piloto_corrida.sql" })
	void findByIdNotFoundTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.findById(10));
		assertEquals("Piloto-Corrida 10 não existe", exception.getMessage());
	}

	@Test
	@DisplayName("Teste buscar os pilotos de uma corrida")
	@Sql({ "classpath:/resources/sql/bd_piloto_corrida.sql" })
	@Sql({ "classpath:/resources/sql/piloto_corrida.sql" })
	void findByCorridaTest() {
		var corridaPilotos = service.findByCorrida(new Corrida(1, null, null, null));
		assertEquals(3, corridaPilotos.size());
		assertEquals(1, corridaPilotos.get(0).getPiloto().getId());
		assertEquals(2, corridaPilotos.get(1).getPiloto().getId());
	}

	@Test
	@DisplayName("Teste buscar os pilotos de uma corrida inválida")
	@Sql({ "classpath:/resources/sql/bd_piloto_corrida.sql" })
	@Sql({ "classpath:/resources/sql/piloto_corrida.sql" })
	void findByCorridaNotFoundTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class,
				() -> service.findByCorrida(new Corrida(4, null, null, null)));
		assertEquals("Não existe pilotos nessa corrida", exception.getMessage());
	}

	@Test
	@DisplayName("Teste buscar corridas de um piloto")
	@Sql({ "classpath:/resources/sql/bd_piloto_corrida.sql" })
	@Sql({ "classpath:/resources/sql/piloto_corrida.sql" })
	void findByPilotoTest() {
		var pilotoCorridas = service.findByPiloto(pilotoService.findById(2));
		assertEquals(1, pilotoCorridas.size());

	}

	@Test
	@DisplayName("Teste buscar corridas de um piloto inválido")
	@Sql({ "classpath:/resources/sql/bd_piloto_corrida.sql" })
	@Sql({ "classpath:/resources/sql/piloto_corrida.sql" })
	void findByPilotoNotFoundTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class,
				() -> service.findByPiloto(new Piloto(4, null, null, null)));
		assertEquals("Não existe corridas com esse piloto", exception.getMessage());
	}

}
