package br.com.trier.spring_matutino.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.spring_matutino.domain.User;
import br.com.trier.spring_matutino.domain.dto.UserDTO;
import br.com.trier.spring_matutino.services.UserService;

@RestController
@RequestMapping(value = "/usuarios")
public class UserResource {

	@Autowired
	private UserService service;

	@Secured({"ROLE_ADMIN"})
	@PostMapping
	public ResponseEntity<UserDTO> insert(@RequestBody UserDTO user) {
		User newUser = service.salvar(new User(user));
		return newUser != null ? ResponseEntity.ok(newUser.toDto()) : ResponseEntity.badRequest().build();
	}

	@Secured({ "ROLE_USER" })
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> buscaPorCodigo(@PathVariable Integer id) {
        User user = service.findById(id);
        return ResponseEntity.ok(user.toDto());
    }   

	@Secured({ "ROLE_USER" })
	@GetMapping
	public ResponseEntity<List<UserDTO>> listaTudo() {
		List<User> lista = service.listAll();
		return ResponseEntity.ok(lista.stream().map((user) -> user.toDto()).toList());
	}

	@Secured({ "ROLE_ADMIN" })
	@PutMapping("/{id}")
	public ResponseEntity<UserDTO> update(@PathVariable Integer id, @RequestBody UserDTO userDTO) {
		User user = new User(userDTO);
		user.setId(id);
		user = service.update(user);
		return user != null ? ResponseEntity.ok(user.toDto()) : ResponseEntity.badRequest().build();
	}

	@Secured({ "ROLE_ADMIN" })
	@DeleteMapping("/{id}")
	public ResponseEntity<User> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}

	 @Secured({ "ROLE_USER" })
	    @GetMapping("/name/{name}")
	    public ResponseEntity<List<User>> buscaPorNome(@PathVariable String name) {
	        return ResponseEntity.ok(service.findByNameStartsWithIgnoreCase(name));
	    }
}
