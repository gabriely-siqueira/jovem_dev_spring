package br.com.trier.spring_matutino.services;

import java.time.ZonedDateTime;
import java.util.List;


import br.com.trier.spring_matutino.domain.Pista;
import br.com.trier.spring_matutino.domain.Campeonato;
import br.com.trier.spring_matutino.domain.Corrida;



public interface CorridaService {
	Corrida salvar(Corrida corrida);
	List<Corrida> listAll();
	Corrida findById(Integer id);
	Corrida update(Corrida corrida);
	void delete(Integer id);
	List<Corrida> findByData(ZonedDateTime data);
	List<Corrida> findByPista(Pista pista);
	List<Corrida> findByCampeonato(Campeonato campeonato);

}