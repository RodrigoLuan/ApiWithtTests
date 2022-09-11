package br.com.devdicas.api.services;

import br.com.devdicas.api.domain.User;

import java.util.List;

public interface UserService {

    User findById(Integer id);

    List<User> findAll();
}
