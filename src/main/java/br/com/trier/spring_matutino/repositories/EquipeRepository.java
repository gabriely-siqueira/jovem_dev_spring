package br.com.trier.spring_matutino.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.spring_matutino.domain.Equipe;

@Repository
public interface EquipeRepository extends JpaRepository<Equipe, Integer>{

	List<Equipe> findByNameContainsIgnoreCase(String nome);
}
