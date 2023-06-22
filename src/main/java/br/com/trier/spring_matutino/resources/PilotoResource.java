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

import br.com.trier.spring_matutino.domain.Piloto;
import br.com.trier.spring_matutino.domain.dto.PilotoDTO;
import br.com.trier.spring_matutino.services.EquipeService;
import br.com.trier.spring_matutino.services.PaisService;
import br.com.trier.spring_matutino.services.PilotoService;

@RestController
@RequestMapping(value = "/piloto")
public class PilotoResource {
	@Autowired
	private PilotoService pilotoService;

	@Autowired
	private PaisService paisService;

	@Autowired
	private EquipeService equipeService;

	@PostMapping
	public ResponseEntity<PilotoDTO> insert(@RequestBody PilotoDTO pilotoDTO) {
		equipeService.findById(pilotoDTO.getIdEquipe());
		paisService.findById(pilotoDTO.getIdPais());
		return ResponseEntity.ok(pilotoService.salvar(new Piloto(pilotoDTO)).toDTO());
	}

	@GetMapping("/{id}")
	public ResponseEntity<PilotoDTO> findById(@PathVariable Integer id) {
		return ResponseEntity.ok(pilotoService.findById(id).toDTO());
	}

	@GetMapping
	public ResponseEntity<List<PilotoDTO>> listAll() {
		return ResponseEntity.ok(pilotoService.listAll().stream().map((piloto) -> piloto.toDTO()).toList());
	}

	@PutMapping("/{id}")
	public ResponseEntity<PilotoDTO> update(@RequestBody PilotoDTO pilotoDTO, @PathVariable Integer id) {
		equipeService.findById(pilotoDTO.getIdEquipe());
		paisService.findById(pilotoDTO.getIdPais());
		Piloto piloto = new Piloto(pilotoDTO);
		piloto.setId(id);
		return ResponseEntity.ok(pilotoService.update(piloto).toDTO());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Piloto> delete(@PathVariable Integer id) {
		pilotoService.delete(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/name/{name}")
	public ResponseEntity<List<PilotoDTO>> findByNameStartsWithIgnoreCase(@PathVariable String name) {
		return ResponseEntity.ok(
				pilotoService.findByNameStartsWithIgnoreCase(name).stream().map((piloto) -> piloto.toDTO()).toList());
	}

	@GetMapping("/pais/{idPais}")
	public ResponseEntity<List<PilotoDTO>> findByPais(@PathVariable Integer id) {
		return ResponseEntity.ok(
				pilotoService.findByPais(paisService.findById(id)).stream().map((piloto) -> piloto.toDTO()).toList());
	}

	@GetMapping("/equipe/{idEquipe}")
	public ResponseEntity<List<PilotoDTO>> findByEquipe(@PathVariable Integer id) {
		return ResponseEntity.ok(pilotoService.findByEquipe(equipeService.findById(id)).stream()
				.map((piloto) -> piloto.toDTO()).toList());
	}
}
