package br.com.trier.spring_matutino.services;

import java.util.List;

import br.com.trier.spring_matutino.domain.Pais;
import br.com.trier.spring_matutino.domain.Pista;


public interface PistaService {

	Pista salvar(Pista pista);
	List<Pista> listAll();
	Pista findById(Integer id);
	Pista update(Pista pista);
	void delete(Integer id);
	List<Pista> findByTamanhoBetween(Integer tamMinimo, Integer tamanhoMaximo);
	List<Pista> findByPais(Pais pais);

}
