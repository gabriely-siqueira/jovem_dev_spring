package br.com.trier.spring_matutino.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.spring_matutino.domain.Pais;
import br.com.trier.spring_matutino.domain.Pista;
@Repository
public interface PistaRepository  extends JpaRepository<Pista, Integer>{
	List<Pista> findByTamanhoBetween(Integer tamMinimo, Integer tamanhoMaximo);
	List<Pista> findByPais(Pais pais);
	

}
