package br.com.devdicas.api.services;

import br.com.devdicas.api.domain.User;

public interface UserService {

    User findById(Integer id);
}
