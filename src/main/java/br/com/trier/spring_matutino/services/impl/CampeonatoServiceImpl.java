package br.com.trier.spring_matutino.services.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
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
	
	public void vericaCampeonato(Campeonato obj) {
		if(obj == null) {
			throw new ViolacaoIntegridade("campeonato nulo".formatted(obj.getYear()));
		}if(obj.getDescricao() == null ||obj.getDescricao().equals("")) {
			
		}
	}

	public void vericaAno(Campeonato campeonato) {
		int anoAtual = LocalDate.now().getYear();
		int anoMaximo = anoAtual + 1;
		if (campeonato.getYear() <= 1990 || campeonato.getYear() >= anoMaximo)
			throw new ViolacaoIntegridade("Ano %s inválido".formatted(campeonato.getYear()));
	}

	@Override
	public Campeonato salvar(Campeonato obj) {
		vericaCampeonato(obj);
		if (obj.getYear() <= 1990 && obj.getYear() >= LocalDateTime.now().getYear() + 1) {
			throw new ViolacaoIntegridade("O ano está fora do intervalo permitido: %s".formatted(obj.getYear()));

		}
		return repository.save(obj);
	}

	@Override
	public List<Campeonato> listAll() {
		return repository.findAll();
	}

	@Override
	public Campeonato findById(Integer id) {
		Optional<Campeonato> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjetoNaoEncontrado("Campeonato %s não encontrado!".formatted(id)));
	}

	@Override
	public Campeonato update(Campeonato obj) {
		vericaCampeonato(obj);
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
		if(campeonatos.size()==0) {
			throw new ObjetoNaoEncontrado("Não há campeonatos entre %s e %s".formatted(start, end));
		}
		return campeonatos;
	}

	

	public List<Campeonato> findByYear(Integer year) {
		List<Campeonato> campeonatos = repository.findByYear(year);
		if (campeonatos.size() == 0) {
			throw new ObjetoNaoEncontrado("Campeonato de %s não encontrado".formatted(year));
		}
		return campeonatos;

	}

	@Override
	public List<Campeonato> findByDescricaoContainsIgnoreCase(String descricao) {
		List<Campeonato> campeonatos = repository.findByDescricaoContainsIgnoreCase(descricao);
		if(campeonatos.size()==0) {
			throw new ObjetoNaoEncontrado("Campeonato %s não encontrado".formatted(descricao));
		}
		return campeonatos;
	}

	@Override
	public List<Campeonato> findByDescricaoContainsIgnoreCaseAndYearEquals(String descricao, Integer year) {
		List<Campeonato> campeonatos = repository.findByDescricaoContainsIgnoreCaseAndYearEquals(descricao, year);
		if(campeonatos.size()==0) {
			throw new ObjetoNaoEncontrado("Campeonato %s de %s não encontrado".formatted(descricao, year));
		}
		return campeonatos;
	}

}
