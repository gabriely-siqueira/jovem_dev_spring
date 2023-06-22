package br.com.trier.spring_matutino.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.spring_matutino.domain.Pais;
import br.com.trier.spring_matutino.domain.Pista;

import br.com.trier.spring_matutino.repositories.PistaRepository;
import br.com.trier.spring_matutino.services.PistaService;
import br.com.trier.spring_matutino.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.spring_matutino.services.exceptions.ViolacaoIntegridade;
@Service
public class PistaServiceImpl implements PistaService {

		
		@Autowired
		private PistaRepository repository;

		@Override
		public Pista salvar(Pista pista) {
			validaPista(pista);
			return repository.save(pista);
		}
		
		private void validaPista(Pista pista) {
			if(pista == null) {
				throw new ViolacaoIntegridade("A pista está nula");
			} else if(pista.getTamanho() == null || pista.getTamanho() <= 0) {
				throw new ViolacaoIntegridade("Tamanho inválido");
			}
		}

		@Override
		public Pista update(Pista pista) {
			if(!listAll().contains(pista)) {
				throw new ObjetoNaoEncontrado("Essa pista não existe");
			}
			return salvar(pista);
		}

		@Override
		public void delete(Integer id) {
			repository.delete(findById(id));
		}

		@Override
		public List<Pista> listAll() {
			List<Pista> lista = repository.findAll();
			if(lista.size() == 0) {
				throw new ObjetoNaoEncontrado("Nenhuma pista cadastrada");
			}
			return lista;
		}

		@Override
		public Pista findById(Integer id) {
			return repository.findById(id).orElseThrow(() -> new ObjetoNaoEncontrado("Pista id %s não existe".formatted(id)));
		}

		@Override
		public List<Pista> findByTamanhoBetween(Integer tamMinimo, Integer tamanhoMaximo) {
			List<Pista> lista = repository.findByTamanhoBetween(tamMinimo, tamanhoMaximo);
			if (lista.size() == 0) {
				throw new ObjetoNaoEncontrado(
						"Nenhuma pista cadastrada com esse tamanho %s".formatted(tamMinimo, tamanhoMaximo));
			}
			return lista;
		}

		@Override
		public List<Pista> findByPais(Pais pais) {
			List<Pista> lista = repository.findByPais(pais);
			if (lista.size() == 0) {
				throw new ObjetoNaoEncontrado("Nenhuma pista cadastrada com esse país %s".formatted(pais));
			}
			return lista;
		}
	}