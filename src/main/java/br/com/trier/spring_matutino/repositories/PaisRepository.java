package br.com.trier.spring_matutino.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.spring_matutino.domain.Pais;

@Repository
public interface PaisRepository extends JpaRepository<Pais, Integer>{

	List<Pais> findByNameContainsIgnoreCase(String Nome);
	
}
