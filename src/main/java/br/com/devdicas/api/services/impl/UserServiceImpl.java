package br.com.devdicas.api.services.impl;

import br.com.devdicas.api.domain.User;
import br.com.devdicas.api.repositories.UserRepository;
import br.com.devdicas.api.services.UserService;
import br.com.devdicas.api.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findById(Integer id) {
        Optional<User> obj = userRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + User.class.getName()));
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }
}
