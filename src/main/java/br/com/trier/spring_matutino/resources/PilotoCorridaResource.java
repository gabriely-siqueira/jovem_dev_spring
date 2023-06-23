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

import br.com.trier.spring_matutino.domain.PilotoCorrida;
import br.com.trier.spring_matutino.domain.dto.PilotoCorridaDTO;
import br.com.trier.spring_matutino.services.CorridaService;
import br.com.trier.spring_matutino.services.PilotoCorridaService;
import br.com.trier.spring_matutino.services.PilotoService;

@RestController
@RequestMapping(value = "/piloto_corrida")
public class PilotoCorridaResource {

	@Autowired
	private PilotoCorridaService service;
	@Autowired
	private CorridaService corridaService;
	@Autowired
	private PilotoService pilotoService;

	@PostMapping
	public ResponseEntity<PilotoCorridaDTO> insert(@RequestBody PilotoCorridaDTO dto) {
		return ResponseEntity.ok(service.salvar(new PilotoCorrida(dto, pilotoService.findById(dto.getIdPiloto()),
				corridaService.findById(dto.getIdCorrida()))).toDTO());
	}

	@GetMapping("/{id}")
	public ResponseEntity<PilotoCorridaDTO> findById(@PathVariable Integer id) {
		return ResponseEntity.ok(service.findById(id).toDTO());
	}

	@GetMapping
	public ResponseEntity<List<PilotoCorridaDTO>> listAll() {
		return ResponseEntity.ok(service.listAll().stream().map((pilotoCorrida) -> pilotoCorrida.toDTO()).toList());
	}

	@PutMapping("/{id}")
	public ResponseEntity<PilotoCorridaDTO> update(@RequestBody PilotoCorridaDTO dto, @PathVariable Integer id) {
		PilotoCorrida piloto = new PilotoCorrida(dto, pilotoService.findById(dto.getIdPiloto()),
				corridaService.findById(dto.getIdCorrida()));
		piloto.setId(id);
		return ResponseEntity.ok(service.update(piloto).toDTO());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/piloto/{pilotoId}")
	public ResponseEntity<List<PilotoCorridaDTO>> findByPiloto(@PathVariable Integer pilotoId) {
		return ResponseEntity.ok(service.findByPiloto(pilotoService.findById(pilotoId)).stream()
				.map((pilotoCorrida) -> pilotoCorrida.toDTO()).toList());
	}

	@GetMapping("/corrida/{corridaId}")
	public ResponseEntity<List<PilotoCorridaDTO>> findByCorrida(@PathVariable Integer corridaId) {
		return ResponseEntity.ok(service.findByCorrida(corridaService.findById(corridaId)).stream()
				.map((pilotoCorrida) -> pilotoCorrida.toDTO()).toList());
	}
}
