package br.com.trier.spring_matutino.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.spring_matutino.domain.Corrida;
import br.com.trier.spring_matutino.domain.dto.CorridaDTO;
import br.com.trier.spring_matutino.services.CampeonatoService;
import br.com.trier.spring_matutino.services.CorridaService;
import br.com.trier.spring_matutino.services.PistaService;

@RestController
@RequestMapping(value = "/corridas")
public class CorridaResource {
	@Autowired
	CorridaService corridaService;
	@Autowired
	PistaService pistaService;
	@Autowired
	CampeonatoService campeonatoService;

	@PostMapping
	public ResponseEntity<CorridaDTO> insert(@RequestBody CorridaDTO corrida) {
		return ResponseEntity.ok(corridaService.salvar(new Corrida(corrida,
				
		campeonatoService.findById(corrida.getIdCampeonato()),
		pistaService.findById(corrida.getIdPista()).toDto());
	}

	@GetMapping("/{id}")
	public ResponseEntity<CorridaDTO> buscaPorCodigo(@PathVariable Integer id) {
		return ResponseEntity.ok((corridaService.findById(id)).toDto());
	}

	@GetMapping
	public ResponseEntity<List<CorridaDTO>> listaTudo() {
		List<Corrida> corridas = corridaService.listAll();
		List<CorridaDTO> responseDtoList = corridas.stream().map(Corrida::toDto).toList();
		return ResponseEntity.ok(responseDtoList);
	}

	@PutMapping("/{id}")
	public ResponseEntity<CorridaDTO> update(@PathVariable Integer id, @RequestBody CorridaDTO corridaDto) {
		Corrida corrida = corridaService.findById(id);
		corrida.setPista(pistaService.findById(corridaDto.getIdPista()));
		corrida.setCampeonato(campeonatoService.findById(corridaDto.getIdCampeonato()));
		corridaService.salvar(corrida);
		CorridaDTO responseDto = corrida.toDto();
		return ResponseEntity.ok(responseDto);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<CorridaDTO> delete(@PathVariable Integer id) {
		corridaService.delete(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/pista/{idPista}")
	public ResponseEntity<List<CorridaDTO>> findByPista(@PathVariable Integer idPista) {
		return ResponseEntity.ok(corridaService.findByPista(pistaService.findById(idPista)).stream()
				.map((corrida) -> corrida.toDto()).toList());
	}

	@GetMapping("/campeonato/{idCampeonato}")
	public ResponseEntity<List<CorridaDTO>> findByCampeonato(@PathVariable Integer idCampeonato) {
		return ResponseEntity.ok(corridaService.findByCampeonato(campeonatoService.findById(idCampeonato)).stream()
				.map((corrida) -> corrida.toDto()).toList());
	}
}
