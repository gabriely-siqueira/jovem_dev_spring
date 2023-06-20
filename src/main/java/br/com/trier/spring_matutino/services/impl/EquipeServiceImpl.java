package br.com.trier.spring_matutino.services.impl;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.spring_matutino.domain.Equipe;
import br.com.trier.spring_matutino.repositories.EquipeRepository;
import br.com.trier.spring_matutino.services.EquipeService;
import br.com.trier.spring_matutino.services.exceptions.ObjetoNaoEncontrado;

@Service
public class EquipeServiceImpl implements EquipeService {

	@Autowired
	EquipeRepository repository;

	
	@Override
	public Equipe salvar(Equipe equipe) {
		return repository.save(equipe);
	}

	@Override
	public List<Equipe> listAll() {
		return repository.findAll();
	}

	@Override
	public Equipe findById(Integer id) {
		Optional<Equipe> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjetoNaoEncontrado("Equipe %s não encontrada!".formatted(id)));
	}

	@Override
	public Equipe update(Equipe obj) {
		Equipe equipe = findById(obj.getId());
		return repository.save(equipe);
	}

	@Override
	public void delete(Integer id) {
		Equipe equipe = findById(id);
			repository.delete(equipe);
	}

	@Override
	public List<Equipe> findByName(String name) {
		List<Equipe> lista = repository.findByNameContainsIgnoreCase(name);
		if(lista.size() == 0) {
			throw new ObjetoNaoEncontrado("Nenhum nome de equipe inicia com %s".formatted(name));
		}
		return lista;
	}

}
