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
import br.com.trier.spring_matutino.services.CampeonatoService;

@RestController
@RequestMapping(value = "/campeonatos")
public class CampeonatoResource {

	
	@Autowired
	private CampeonatoService service;
	
	@PostMapping
	public ResponseEntity<Campeonato> insert(@RequestBody Campeonato campeonato){
		Campeonato newCampeonato = service.salvar(campeonato);
		return newCampeonato != null ? ResponseEntity.ok(newCampeonato) : ResponseEntity.badRequest().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Campeonato> buscaPorCodigo(@PathVariable Integer id) {
		Campeonato campeonato = service.findById(id);
		return campeonato != null ? ResponseEntity.ok(campeonato) : ResponseEntity.noContent().build(); 
	}
	
	@GetMapping
	public ResponseEntity<List<Campeonato>> listaTudo(){
		List<Campeonato> lista = service.listAll();
		return lista.size() > 0 ? ResponseEntity.ok(lista) : ResponseEntity.noContent().build(); 
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Campeonato> update(@PathVariable Integer id, @RequestBody Campeonato campeonato){
		campeonato.setId(id);
		campeonato = service.update(campeonato);
		return campeonato != null ? ResponseEntity.ok(campeonato) : ResponseEntity.badRequest().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Campeonato> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/ano/{year}")
	public ResponseEntity<List<Campeonato>> findByYear(@PathVariable Integer year){
		List<Campeonato> lista = service.findByYear(year);
		return lista.size() > 0 ? ResponseEntity.ok(lista) : ResponseEntity.badRequest().build();
	}
	
	@GetMapping("/ano-entre/{start}/{end}")
	public ResponseEntity<List<Campeonato>> findByYearBetween(@PathVariable Integer start, @PathVariable Integer end){
		List<Campeonato> lista = service.findByYearBetween(start, end);
		return lista.size() > 0 ? ResponseEntity.ok(lista) : ResponseEntity.badRequest().build();
	}
	
	@GetMapping("/desc/{descricao}")
	public ResponseEntity<List<Campeonato>> findByDescricaoContainsIgnoreCase(@PathVariable String descricao) {
		List<Campeonato> lista = service.findByDescricaoContainsIgnoreCase(descricao);
		return lista.size() > 0 ? ResponseEntity.ok(lista) : ResponseEntity.badRequest().build();
	}
	
	@GetMapping("/desc-ano/{descricao}/{year}")
	public ResponseEntity<List<Campeonato>> findByDescricaoContainsIgnoreCaseAndYearEquals(@PathVariable String descricao, @PathVariable Integer year){
		List<Campeonato> lista = service.findByDescricaoContainsIgnoreCaseAndYearEquals(descricao, year);
		return lista.size() > 0 ? ResponseEntity.ok(lista) : ResponseEntity.badRequest().build();
	}
	
}
