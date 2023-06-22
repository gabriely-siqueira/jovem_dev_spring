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
import br.com.trier.spring_matutino.domain.Pista;


@SpringBootTest
public class PistaServiceTest extends BaseTest {

	 @Autowired
	    PistaService pistaService;
	    
	    @Autowired
	    PaisService paisService;

	    @Test
		@DisplayName("Teste inserção pista")
		@Sql({"classpath:/resources/sql/pais.sql"})
		void insertTest() {
			var pista = new Pista(null, 10, new Pais(2, "Brasil"));
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
	@DisplayName("Teste de atualização de pista")
	void updatePistaTest() {
	    Pista pista = pistaService.findById(1);
	    pista.setTamanho(4000);

	    Pista updatedPista = pistaService.salvar(pista);

	    assertThat(updatedPista).isNotNull();
	    assertEquals(1, updatedPista.getId());
	    assertEquals(4000, updatedPista.getTamanho());
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
	void listPistasTest() {
	    List<Pista> pistas = pistaService.listAll();

	    assertThat(pistas).isNotNull();
	    assertThat(pistas).hasSizeGreaterThan(0);
	}

	@Test
    @DisplayName("Teste de busca de pista inexistente")
    @Sql({ "classpath:/resources/sql/pais.sql", "classpath:/resources/sql/pista.sql" })
    void findByIdNonExistentTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            pistaService.findById(9999);
        });
    }
	

}
