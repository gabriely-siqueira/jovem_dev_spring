package br.com.trier.spring_matutino.services;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.ZonedDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.spring_matutino.BaseTest;
import br.com.trier.spring_matutino.domain.Campeonato;
import br.com.trier.spring_matutino.domain.Corrida;
import br.com.trier.spring_matutino.domain.Piloto;
import br.com.trier.spring_matutino.domain.Pista;
import br.com.trier.spring_matutino.services.exceptions.ObjetoNaoEncontrado;
import jakarta.transaction.Transactional;


	@Transactional
	public class CorridaServiceTest extends BaseTest{
		
		@Autowired
		private CorridaService corridaService;
		@Autowired
		private PistaService pistaService;
		@Autowired
		private PaisService paisService;
		@Autowired
		private CampeonatoService campeonatoService;

		@Test
		@DisplayName("Teste inserção de corrida")
		@Sql({"classpath:/resources/sql/campeonato.sql",
		      "classpath:/resources/sql/pais.sql",
		      "classpath:/resources/sql/pista.sql"})
		void insertTest() {
		    ZonedDateTime data = ZonedDateTime.parse("2015-09-14T12:34:00Z");
		    var corrida = new Corrida(null, data, pistaService.findById(2), campeonatoService.findById(1));
		    corridaService.salvar(corrida);
		    assertEquals(1, corridaService.listAll().size());
		}
 
		@Test
		@DisplayName("Teste atualização de corrida")
		@Sql({"classpath:/resources/sql/campeonato.sql",
		      "classpath:/resources/sql/pais.sql",
		      "classpath:/resources/sql/pista.sql",
		      "classpath:/resources/sql/corrida.sql"})
		void updateTest() {
		    ZonedDateTime data = ZonedDateTime.parse("2015-10-30T18:10:00Z");
		    var corrida = new Corrida(1, data,  pistaService.findById(2), campeonatoService.findById(1));
		    corridaService.update(corrida);
		    assertEquals(data, corridaService.findById(1).getData());
		}
		
		


		@Test
		@DisplayName("Teste exclusão corrida")
		@Sql({"classpath:/resources/sql/campeonato.sql",
		      "classpath:/resources/sql/pais.sql",
		      "classpath:/resources/sql/pista.sql",
		      "classpath:/resources/sql/corrida.sql"})
		void deleteTest() {
		    corridaService.delete(1);
		    assertEquals(1, corridaService.listAll().size());
		}

		@Test
		@DisplayName("Teste listagem de todas as corridas")
		@Sql({"classpath:/resources/sql/campeonato.sql",
		      "classpath:/resources/sql/pais.sql",
		      "classpath:/resources/sql/pista.sql",
		      "classpath:/resources/sql/corrida.sql"})
		void listAllTest() {
		    assertEquals(2, corridaService.listAll().size());
		}

		@Test
		@DisplayName("Teste listagem de todas as listas vazias")
		@Sql({"classpath:/resources/sql/campeonato.sql",
		      "classpath:/resources/sql/pais.sql",
		      "classpath:/resources/sql/pista.sql"})
		void listAllEmptyTest() {
		    var exception = assertThrows(ObjetoNaoEncontrado.class, () -> corridaService.listAll());
		    assertEquals("Não há corridas cadastradas", exception.getMessage());
		}

		@Test
		@DisplayName("Teste buscar corrida pelo id")
		@Sql({"classpath:/resources/sql/campeonato.sql",
		      "classpath:/resources/sql/pais.sql",
		      "classpath:/resources/sql/pista.sql",
		      "classpath:/resources/sql/corrida.sql"})
		void findByIdTest() {
		    ZonedDateTime data = ZonedDateTime.parse("2010-05-20T18:10:00Z");
		    var corrida = corridaService.findById(1);
		    assertEquals(1, corrida.getPista().getId());
		    assertEquals(data, corrida.getData());
		}

		@Test
		@DisplayName("Teste buscar corrida por id inexistente")
		@Sql({"classpath:/resources/sql/campeonato.sql",
		      "classpath:/resources/sql/pais.sql",
		      "classpath:/resources/sql/pista.sql",
		      "classpath:/resources/sql/corrida.sql"})
		void findByIdNotFoundTest() {
		    var exception = assertThrows(ObjetoNaoEncontrado.class, () -> corridaService.findById(5));
		    assertEquals("Corrida id 5 não existe", exception.getMessage());
		}

		@Test
		@DisplayName("Teste buscar corridas pela data")
		@Sql({"classpath:/resources/sql/campeonato.sql",
		      "classpath:/resources/sql/pais.sql",
		      "classpath:/resources/sql/pista.sql",
		      "classpath:/resources/sql/corrida.sql"})
		void findByDataTest() {
		    ZonedDateTime data = ZonedDateTime.parse("2015-10-14T19:20:00Z");
		    var corridas = corridaService.findByData(data);
		    assertEquals(2, corridas.get(0).getId());
		}

		@Test
		@DisplayName("Teste buscar corridas por pista")
		@Sql({"classpath:/resources/sql/campeonato.sql",
		      "classpath:/resources/sql/pais.sql",
		      "classpath:/resources/sql/pista.sql",
		      "classpath:/resources/sql/corrida.sql"})
		void findByPistaTest() {
		    var corridas = corridaService.findByPista(new Pista(1, null, null));
		    assertEquals(1, corridas.size());
		}

		@Test
		@DisplayName("Teste buscar corridas por pista inválida")
		@Sql({"classpath:/resources/sql/campeonato.sql",
		      "classpath:/resources/sql/pais.sql",
		      "classpath:/resources/sql/pista.sql",
		      "classpath:/resources/sql/corrida.sql"})
		void findByPistaNotFoundTest() {
		    var exception = assertThrows(ObjetoNaoEncontrado.class, () -> corridaService.findByPista(new Pista(3, null, null)));
		    assertEquals("Não há corridas na pista 3", exception.getMessage());
		}

		@Test
		@DisplayName("Teste buscar corridas por campeonato")
		@Sql({"classpath:/resources/sql/campeonato.sql",
		      "classpath:/resources/sql/pais.sql",
		      "classpath:/resources/sql/pista.sql",
		      "classpath:/resources/sql/corrida.sql"})
		void findByCampeonatoTest() {
		    var corridas = corridaService.findByCampeonato(new Campeonato(1, null, null));
		    assertEquals(1, corridas.size());
		}
		
		
	}


