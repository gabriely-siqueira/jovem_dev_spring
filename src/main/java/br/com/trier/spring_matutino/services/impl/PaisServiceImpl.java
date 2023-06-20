package br.com.trier.spring_matutino.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.spring_matutino.domain.Pais;
import br.com.trier.spring_matutino.repositories.PaisRepository;
import br.com.trier.spring_matutino.services.PaisService;
import br.com.trier.spring_matutino.services.exceptions.ObjetoNaoEncontrado;

@Service
public class PaisServiceImpl implements PaisService{

	@Autowired
	PaisRepository repository;
	
	@Override
	public Pais salvar(Pais pais) {
		return repository.save(pais);
	}

	@Override
	public List<Pais> listAll() {
		return repository.findAll();
	}

	@Override
	public Pais findById(Integer id) {
		Optional<Pais> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjetoNaoEncontrado("País %s não encontrada!".formatted(id)));
	}

	@Override
	public Pais update(Pais obj) {
		Pais pais = findById(obj.getId());
		return repository.save(pais);
	}

	@Override
	public void delete(Integer id) {
		Pais pais = findById(id);
		repository.delete(pais);	
	}

	@Override
	public List<Pais> findByName(String name) {
		List<Pais> lista =  repository.findByNameContainsIgnoreCase(name);
		if(lista.size() == 0) {
			throw new ObjetoNaoEncontrado("Nenhum nome de equipe inicia com %s".formatted(name));
		}
		return lista;
	}
	
	
}
