package br.com.trier.spring_matutino.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.spring_matutino.domain.Corrida;
import br.com.trier.spring_matutino.domain.Pais;
import br.com.trier.spring_matutino.domain.Pista;
import br.com.trier.spring_matutino.domain.dto.CorridaDTO;
import br.com.trier.spring_matutino.domain.dto.CorridaPaisAnoDTO;
import br.com.trier.spring_matutino.services.CorridaService;
import br.com.trier.spring_matutino.services.PaisService;
import br.com.trier.spring_matutino.services.PistaService;
import br.com.trier.spring_matutino.services.exceptions.ObjetoNaoEncontrado;

@RestController
@RequestMapping("/relatorios")
public class RelatorioResource {

	@Autowired
	private PaisService paisService;

	@Autowired
	private PistaService pistaService;

	@Autowired
	private CorridaService corridaService;

	@GetMapping("corridas-por-pais-ano/{paisId}/{ano}")
	public ResponseEntity<CorridaPaisAnoDTO> findCorridaByPaisAno(@PathVariable Integer paisId,
			@PathVariable Integer ano) {
		Pais pais = paisService.findById(paisId);
		List<Pista> pistaPais = pistaService.findByPais(pais);

		List<CorridaDTO> corridasDTO = pistaPais.stream().flatMap(pista -> {
			try {
				return corridaService.findByPista(pista).stream();
			} catch (ObjetoNaoEncontrado e) {
				return Stream.empty();
			}
		}).filter(corrida -> corrida.getData().getYear() == ano).map(Corrida::toDto).toList();

		CorridaPaisAnoDTO corridaPaisAnoDTO = new CorridaPaisAnoDTO(ano, pais.getName(), corridasDTO);
		return ResponseEntity.ok(corridaPaisAnoDTO);
	}

	@GetMapping("corridas-por-pais/{paisId}")
	public ResponseEntity<List<CorridaDTO>> findCorridasByPais(@PathVariable Integer paisId) {
		Pais pais = paisService.findById(paisId);
		List<Pista> pistaPais = pistaService.findByPais(pais);

		List<CorridaDTO> corridasDTO = pistaPais.stream().flatMap(pista -> {
			try {
				return corridaService.findByPista(pista).stream();
			} catch (ObjetoNaoEncontrado e) {
				return Stream.empty();
			}
		}).map(Corrida::toDto).toList();

		return ResponseEntity.ok(corridasDTO);
	}

	@GetMapping("corridas-por-ano/{ano}")
	public ResponseEntity<List<CorridaPaisAnoDTO>> findCorridasByAno(@PathVariable Integer ano) {
		List<CorridaPaisAnoDTO> corridasPorPaisAno = new ArrayList<>();

		List<Pais> paises = paisService.listAll();

		for (Pais pais : paises) {
			List<Pista> pistaPais = pistaService.findByPais(pais);

			List<CorridaDTO> corridasDTO = pistaPais.stream().flatMap(pista -> {
				try {
					return corridaService.findByPista(pista).stream();
				} catch (ObjetoNaoEncontrado e) {
					return Stream.empty();
				}
			}).filter(corrida -> corrida.getData().getYear() == ano).map(Corrida::toDto).toList();

			CorridaPaisAnoDTO corridaPaisAnoDTO = new CorridaPaisAnoDTO(ano, pais.getName(), corridasDTO);
			corridasPorPaisAno.add(corridaPaisAnoDTO);
		}

		return ResponseEntity.ok(corridasPorPaisAno);
	}

}
