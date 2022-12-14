package br.com.devdicas.api.services.impl;

import br.com.devdicas.api.domain.User;
import br.com.devdicas.api.domain.UserDTO;
import br.com.devdicas.api.repositories.UserRepository;
import br.com.devdicas.api.services.UserService;
import br.com.devdicas.api.services.exceptions.DataIntegratyViolationException;
import br.com.devdicas.api.services.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public User findById(Integer id) {
        Optional<User> obj = userRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
    }

    @Override
    public List<User> findAll(){
        return userRepository.findAll();
    }

    @Override
    public User create(UserDTO obj) {
        findByEmail(obj);
        return userRepository.save(mapper.map(obj, User.class));
    }

    @Override
    public User update(UserDTO obj) {
        findByEmail(obj);
        return userRepository.save(mapper.map(obj, User.class));
    }

    @Override
    public void delete(Integer id) {
        findById(id);
        try {
            userRepository.deleteById(id);
        }catch (DataIntegratyViolationException e){
            throw new DataIntegratyViolationException("Objeto não encontrado!");
        }
    }


    public void findByEmail(UserDTO obj){
        Optional<User> user = userRepository.findByEmail(obj.getEmail());
        if(user.isPresent() && !user.get().getId().equals(obj.getId()) ){
            throw new DataIntegratyViolationException("Email já cadastrado!");
        }
    }
}
