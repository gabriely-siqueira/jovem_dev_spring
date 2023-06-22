package br.com.trier.spring_matutino.services;

import java.util.List;

import br.com.trier.spring_matutino.domain.Equipe;
import br.com.trier.spring_matutino.domain.Pais;
import br.com.trier.spring_matutino.domain.Piloto;
import br.com.trier.spring_matutino.domain.Pista;


public interface PilotoService {
	Piloto salvar(Piloto piloto);
	List<Piloto> listAll();
	Piloto findById(Integer id);
	Piloto update(Piloto piloto);
	void delete(Integer id);
	List<Piloto> findByPais(Pais pais);
	List<Piloto> findByNameStartsWithIgnoreCase(String nome);
	List<Piloto> findByEquipe(Equipe equipe);
}
