package br.com.devdicas.api.repositories;

import br.com.devdicas.api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
