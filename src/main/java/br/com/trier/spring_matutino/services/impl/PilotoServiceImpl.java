package br.com.trier.spring_matutino.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.spring_matutino.domain.Equipe;
import br.com.trier.spring_matutino.domain.Pais;
import br.com.trier.spring_matutino.domain.Piloto;

import br.com.trier.spring_matutino.repositories.PilotoRepository;
import br.com.trier.spring_matutino.services.PilotoService;
import br.com.trier.spring_matutino.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.spring_matutino.services.exceptions.ViolacaoIntegridade;

@Service
public class PilotoServiceImpl implements PilotoService {

	@Autowired
	private PilotoRepository repository;

	private void validaPiloto(Piloto piloto) {
		if (piloto.getName() == null) {
			throw new ViolacaoIntegridade("Nome nulo");
		}

	}

	public Piloto salvar(Piloto piloto) {
		validaPiloto(piloto);
		return repository.save(piloto);
	}

	@Override
	public List<Piloto> listAll() {

		List<Piloto> lista = repository.findAll();
		if (lista.size() == 0) {
			throw new ObjetoNaoEncontrado("Nenhuma piloto cadastrada");
		}
		return lista;
	}

	@Override
	public Piloto findById(Integer id) {
		return repository.findById(id)
				.orElseThrow(() -> new ObjetoNaoEncontrado(String.format("Piloto id %d não existe", id)));
	}

	@Override
	public Piloto update(Piloto piloto) {
		findById(piloto.getId());
		validaPiloto(piloto);
		return repository.save(piloto);
	}

	@Override
	public void delete(Integer id) {
		Piloto piloto = findById(id);
		repository.delete(piloto);

	}

	@Override
	public List<Piloto> findByPais(Pais pais) {
		List<Piloto> lista = repository.findByPais(pais);
		if (lista.size() == 0) {
			throw new ObjetoNaoEncontrado("Nenhuma piloto cadastrada com esse país %s".formatted(pais));
		}
		return lista;
	}

	@Override
	public List<Piloto> findByNameStartsWithIgnoreCase(String name) {
		List<Piloto> lista = repository.findByNameStartsWithIgnoreCase(name);
		if (lista.size() == 0) {
			throw new ObjetoNaoEncontrado("Nenhum nome de piloto inicia com %s".formatted(name));
		}
		return lista;
	}

	@Override
	public List<Piloto> findByEquipe(Equipe equipe) {
		List<Piloto> lista = repository.findByEquipe(equipe);
		if (lista.size() == 0) {
			throw new ObjetoNaoEncontrado("Nenhuma piloto cadastrado com essa equipe %s".formatted(equipe));
		}
		return lista;
	}
}
