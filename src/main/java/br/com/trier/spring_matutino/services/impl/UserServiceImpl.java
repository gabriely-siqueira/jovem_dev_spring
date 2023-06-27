package br.com.trier.spring_matutino.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.spring_matutino.domain.User;
import br.com.trier.spring_matutino.repositories.UserRepository;
import br.com.trier.spring_matutino.services.UserService;
import br.com.trier.spring_matutino.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.spring_matutino.services.exceptions.ViolacaoIntegridade;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository repository;

    private void validateEmail(User user) {
        User existingUser = repository.findByEmail(user.getEmail());
        if (existingUser != null && !existingUser.getId().equals(user.getId())) {
            throw new ViolacaoIntegridade("E-mail já cadastrado: %s".formatted(user.getEmail()));
        }
    }

    @Override
    public User salvar(User user) {
        validateEmail(user);
        return repository.save(user);
    }

    @Override
    public List<User> listAll() {
        return repository.findAll();
    }

    @Override
    public User findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ObjetoNaoEncontrado("Usuário %s não encontrado!".formatted(id)));
    }

    @Override
    public User update(User user) {
        User existingUser = findById(user.getId());
        validateEmail(user, existingUser); // Validate the email before performing the update

        // Update the existing user with the new values
        existingUser.setEmail(user.getEmail());
        existingUser.setName(user.getName());
        existingUser.setPassword(user.getPassword());
        existingUser.setRole(user.getRole());

        return repository.save(existingUser);
    }

    @Override
    public void delete(Integer id) {
        User user = findById(id);
        repository.delete(user);
    }

    @Override
    public List<User> findByNameStartsWithIgnoreCase(String name) {
        List<User> lista = repository.findByNameStartsWithIgnoreCase(name);
        if (lista.isEmpty()) {
            throw new ObjetoNaoEncontrado("Nenhum nome de usuário inicia com %s".formatted(name));
        }
        return lista;
    }

    @Override
    public User findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        return repository.findByEmailAndPassword(email, password);
    }

	@Override
	public Optional<User> findByName(String name) {
	
		return repository.findByName(name);
	}
}
