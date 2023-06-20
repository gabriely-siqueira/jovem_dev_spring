package br.com.trier.spring_matutino.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.spring_matutino.domain.User;


@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	List<User> findByNameStartsWithIgnoreCase(String Nome);
	User findByEmail(String email);
}
