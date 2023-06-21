package br.com.trier.spring_matutino.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.trier.spring_matutino.BaseTest;
import br.com.trier.spring_matutino.domain.Equipe;
import br.com.trier.spring_matutino.domain.Pais;
import br.com.trier.spring_matutino.domain.Piloto;
import br.com.trier.spring_matutino.domain.Pista;

@SpringBootTest
public class PilotoServiceTest extends BaseTest{

	@Autowired
	PilotoService pilotoService;

	@Autowired
	PaisService paisService;

	@Autowired
	EquipeService equipeService;

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
	@DisplayName("Teste inserção de equipe")
	void insertEquipeTest() {
		Equipe novoEquipe = new Equipe();
		novoEquipe.setName("equipe3");

		Equipe insertedEquipe = equipeService.salvar(novoEquipe);

		assertThat(insertedEquipe).isNotNull();
		assertThat(insertedEquipe.getId()).isPositive();

		assertEquals("equipe3", insertedEquipe.getName());
	}
	@Test
	@DisplayName("Teste inserção de piloto")
	void insertPilotoTest() {
		Pais pais = paisService.findById(1);
		Equipe equipe = equipeService.findById(2);
		Piloto novoPiloto = new Piloto();
		novoPiloto.setName("Cleiton");
		novoPiloto.setPais(pais);
		novoPiloto.setEquipe(equipe);

		Piloto insertedPiloto = pilotoService.salvar(novoPiloto);

		assertThat(insertedPiloto).isNotNull();
		assertThat(insertedPiloto.getId()).isPositive();
		assertEquals("Cleiton", insertedPiloto.getName());
		assertEquals(pais, insertedPiloto.getPais());
		assertEquals(equipe, insertedPiloto.getEquipe());
	}
}
