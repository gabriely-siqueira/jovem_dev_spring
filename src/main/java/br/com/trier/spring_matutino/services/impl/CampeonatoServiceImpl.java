package br.com.trier.spring_matutino.services.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.spring_matutino.domain.Campeonato;
import br.com.trier.spring_matutino.repositories.CampeonatoRepository;
import br.com.trier.spring_matutino.services.CampeonatoService;
import br.com.trier.spring_matutino.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.spring_matutino.services.exceptions.ViolacaoIntegridade;

@Service
public class CampeonatoServiceImpl implements CampeonatoService {

	@Autowired
	private CampeonatoRepository repository;

	@Override
	 public Campeonato salvar(Campeonato campeonato) {
	verificaCampeonato(campeonato);
	  return repository.save(campeonato);
	 }

	private void verificaCampeonato(Campeonato campeonato) {
	    if (campeonato == null) {
	        throw new ViolacaoIntegridade("O campeonato está nulo");
	    } else if (campeonato.getDescricao() == null || campeonato.getDescricao().isBlank()) {
	        throw new ViolacaoIntegridade("A descrição está vazia");
	    } else if (campeonato.getYear() == null || campeonato.getYear() == 0) {
	        throw new ViolacaoIntegridade("O ano está vazio");
	    }
	    verificaAno(campeonato.getYear());
	}

		
	 private void verificaAno(Integer ano) {
	  int anoAtual = LocalDate.now().getYear();
	  if (anoAtual < 1990 || anoAtual > anoAtual) {
	   throw new ViolacaoIntegridade("O ano precisa ser maior que 1990 e menor que 2023");
	  }
	 }

	@Override
	public List<Campeonato> listAll() {
		return repository.findAll();
	}

	@Override
	public Campeonato findById(Integer id) {
	    if (id == null) {
	        throw new IllegalArgumentException("O ID não pode ser nulo.");
	    }

	    Optional<Campeonato> obj = repository.findById(id);
	    if (obj.isEmpty()) {
	        throw new ObjetoNaoEncontrado("Campeonato %s não encontrado!".formatted(id));
	    }
	    return obj.get();
	}

	@Override
	public Campeonato update(Campeonato obj) {
	    verificaCampeonato(obj);
	    Campeonato campeonato = findById(obj.getId());
	    return repository.save(campeonato);
	}

	@Override
	public void delete(Integer id) {
		Campeonato campeonato = findById(id);
		repository.delete(campeonato);
	}

	@Override
	public List<Campeonato> findByYearBetween(Integer start, Integer end) {
		List<Campeonato> campeonatos = repository.findByYearBetween(start, end);
		if (campeonatos.isEmpty()) {
			throw new ObjetoNaoEncontrado("Não há campeonatos entre %s e %s".formatted(start, end));
		}
		return campeonatos;
	}

	public List<Campeonato> findByYear(Integer year) {
		List<Campeonato> campeonatos = repository.findByYear(year);
		if (campeonatos.isEmpty()) {
			throw new ObjetoNaoEncontrado("Campeonato de %s não encontrado".formatted(year));
		}
		return campeonatos;
	}

	@Override
	public List<Campeonato> findByDescricaoContainsIgnoreCase(String descricao) {
		List<Campeonato> campeonatos = repository.findByDescricaoContainsIgnoreCase(descricao);
		if (campeonatos.isEmpty()) {
			throw new ObjetoNaoEncontrado("Campeonato %s não encontrado".formatted(descricao));
		}
		return campeonatos;
	}

	@Override
	public List<Campeonato> findByDescricaoContainsIgnoreCaseAndYearEquals(String descricao, Integer year) {
		List<Campeonato> campeonatos = repository.findByDescricaoContainsIgnoreCaseAndYearEquals(descricao, year);
		if (campeonatos.isEmpty()) {
			throw new ObjetoNaoEncontrado("Campeonato %s de %s não encontrado".formatted(descricao, year));
		}
		return campeonatos;
	}
}
