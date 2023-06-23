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

import br.com.trier.spring_matutino.domain.Campeonato;
import br.com.trier.spring_matutino.domain.Corrida;
import br.com.trier.spring_matutino.domain.Pista;
import br.com.trier.spring_matutino.domain.dto.CorridaDTO;
import br.com.trier.spring_matutino.services.CampeonatoService;
import br.com.trier.spring_matutino.services.CorridaService;
import br.com.trier.spring_matutino.services.PistaService;

@RestController
@RequestMapping(value = "/corridas")
public class CorridaResource {
	@Autowired
	CorridaService service;
	
	@Autowired
	PistaService pistaService;

	@Autowired
	CampeonatoService campeonatoService;
	
	@PostMapping
	public ResponseEntity<CorridaDTO> insert(@RequestBody CorridaDTO corrida) {
	    Campeonato campeonato = campeonatoService.findById(corrida.getIdCampeonato());
	    Pista pista = pistaService.findById(corrida.getIdPista());

	    Corrida newCorrida = new Corrida(corrida, campeonato, pista);
	    Corrida savedCorrida = service.salvar(newCorrida);
	    
	    return ResponseEntity.ok(savedCorrida.toDto());
	}

	 @GetMapping("/{id}")
	    public ResponseEntity<CorridaDTO> findById(@PathVariable Integer id) {
	        Corrida corrida = service.findById(id);
	        return ResponseEntity.ok(corrida.toDto());
	    }

	
	@GetMapping
	public ResponseEntity<List<CorridaDTO>> listAll(){
		return ResponseEntity.ok(service.listAll().stream()
				.map((corrida) -> corrida.toDto())
				.toList());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CorridaDTO> update(@PathVariable Integer id, @RequestBody CorridaDTO corrida){
		pistaService.findById(corrida.getIdPista());
		campeonatoService.findById(corrida.getIdCampeonato());
		return ResponseEntity.ok(service.update(new Corrida(corrida)).toDto());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/pista/{idPista}")
	public ResponseEntity<List<CorridaDTO>> findByPista(@PathVariable Integer idPista) {
		return ResponseEntity.ok(service.findByPista(pistaService.findById(idPista))
				.stream().map((corrida) -> corrida.toDto()).toList());
	}

	@GetMapping("/campeonato/{idCampeonato}")
	public ResponseEntity<List<CorridaDTO>> findByEquipe(@PathVariable Integer idCampeonato) {
		return ResponseEntity.ok(service.findByCampeonato(campeonatoService.findById(idCampeonato))
				.stream().map((corrida) -> corrida.toDto()).toList());

	}

}
