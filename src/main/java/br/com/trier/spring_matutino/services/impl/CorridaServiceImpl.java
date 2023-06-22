package br.com.trier.spring_matutino.services.impl;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.spring_matutino.domain.Campeonato;
import br.com.trier.spring_matutino.domain.Corrida;
import br.com.trier.spring_matutino.domain.Pista;
import br.com.trier.spring_matutino.repositories.CorridaRepository;
import br.com.trier.spring_matutino.services.CorridaService;
import br.com.trier.spring_matutino.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.spring_matutino.services.exceptions.ViolacaoIntegridade;
@Service
public class CorridaServiceImpl implements CorridaService {

		
		@Autowired
		private CorridaRepository repository;

		@Override
		public Corrida salvar(Corrida corrida) {
			validaCorrida(corrida); 
			return repository.save(corrida);
		}

		
		private void validaCorrida(Corrida corrida) {
			if(corrida.getData() == null) {
				throw new ViolacaoIntegridade("A data da corrida não pode ser nula");
			}
			if(corrida.getData().getYear() != corrida.getCampeonato().getYear()) {
				throw new ViolacaoIntegridade("O ano da corrida não pode ser diferente do ano do campeonato!");
			}
		}
		
		@Override
		public Corrida update(Corrida corrida) {
			if(!listAll().contains(corrida)) {
				throw new ObjetoNaoEncontrado("Essa corrida não existe");
			}
			return salvar(corrida);
		}

		@Override
		public void delete(Integer id) {
			repository.delete(findById(id));
		}

		@Override
		public List<Corrida> listAll() {
			if(repository.findAll().size() == 0) {
				throw new ObjetoNaoEncontrado("Não há corridas cadastradas");
			}
			return repository.findAll();
		}

		@Override
		public Corrida findById(Integer id) {
			return repository.findById(id).orElseThrow(() -> new ObjetoNaoEncontrado("Corrida id %s não existe".formatted(id)));
		}

		@Override
		public List<Corrida> findByData(ZonedDateTime data) {
			DateTimeFormatter formatacao = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
			if(repository.findByData(data).size() == 0) {
				throw new ObjetoNaoEncontrado("Não há corridas na data " + formatacao.format(data));
			}
			return repository.findByData(data);
		}

	

		@Override
		public List<Corrida> findByPista(Pista pista) {
			List<Corrida> corridas = repository.findByPista(pista);
			if(corridas.size() == 0) {
				throw new ObjetoNaoEncontrado("Não há corridas na pista " + pista.getId());
			}
			return corridas;
		}

		@Override
		public List<Corrida> findByCampeonato(Campeonato campeonato) {
			List<Corrida> corridas = repository.findByCampeonato(campeonato);
			if(corridas.size() == 0) {
				throw new ObjetoNaoEncontrado("Não há corridas no campeonato " + campeonato.getDescricao());
			}
			return corridas;
		}
	}

