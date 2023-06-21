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

import br.com.trier.spring_matutino.services.EquipeService;
import br.com.trier.spring_matutino.services.PaisService;
import br.com.trier.spring_matutino.services.PilotoService;
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
	public ResponseEntity<Piloto> insert(@RequestBody Piloto piloto) {
		paisService.findById(piloto.getPais().getId());
		return ResponseEntity.ok(pilotoService.salvar(piloto));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Piloto> buscaPorCodigo(@PathVariable Integer id) {
		return ResponseEntity.ok(pilotoService.findById(id));
		
	}

	@GetMapping
	public ResponseEntity<List<Piloto>> listaTudo() {
		return ResponseEntity.ok(pilotoService.listAll());
	}

	@PutMapping("/{id}")
	public ResponseEntity<Piloto> update(@PathVariable Integer id, @RequestBody Piloto piloto) {
		paisService.findById(piloto.getPais().getId());
		piloto.setId(id);
		return ResponseEntity.ok(pilotoService.update(piloto));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Piloto> delete(@PathVariable Integer id) {
		pilotoService.delete(id);
		return ResponseEntity.ok().build();
	}
	@GetMapping("/name/{name}")
	public ResponseEntity<List<Piloto>> buscaPorNome(@PathVariable String name) {
		return ResponseEntity.ok(pilotoService.findByNameStartsWithIgnoreCase(name));
	}
	@GetMapping("/pais/{idPais}")
	public ResponseEntity<List<Piloto>> findByPais(@PathVariable Integer id_pais) {
		return ResponseEntity.ok(pilotoService.findByPais(paisService.findById(id_pais)));
	}
	@GetMapping("/equipe/{idEquipe}")
	public ResponseEntity<List<Piloto>> findByEquipe(@PathVariable Integer id_equipe) {
		return ResponseEntity.ok(pilotoService.findByEquipe(equipeService.findById(id_equipe)));
	}
}
