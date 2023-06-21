package br.com.trier.spring_matutino.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.trier.spring_matutino.domain.Equipe;
import br.com.trier.spring_matutino.domain.Pais;
import br.com.trier.spring_matutino.domain.Piloto;

public interface PilotoRepository extends JpaRepository<Piloto, Integer> {
	List<Piloto> findByNameStartsWithIgnoreCase(String name);

	List<Piloto> findByPais(Pais pais);

	List<Piloto> findByEquipe(Equipe equipe);

}
