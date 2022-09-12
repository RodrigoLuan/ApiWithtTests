package br.com.devdicas.api.services.impl;

import br.com.devdicas.api.domain.User;
import br.com.devdicas.api.domain.UserDTO;
import br.com.devdicas.api.repositories.UserRepository;
import br.com.devdicas.api.services.exceptions.DataIntegratyViolationException;
import br.com.devdicas.api.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

class UserServiceImplTest {

    public static final Integer ID = 1;
    public static final String Name = "Teste";
    public static final String EMAIL = "asda@teste";
    public static final String PASSWORD = "123456";
    public static final String OBJETO_NÃO_ENCONTRADO = "Objeto não encontrado";
    public static final int INDEX = 0;
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    private User user;
    private UserDTO userDTO;
    private Optional<User> userOptional;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @Test
    void whenFindByIdThenReturnAnUserInstance() {
        when(userRepository.findById(anyInt())).thenReturn(userOptional);

        User response = userService.findById(ID);

        assertNotNull(response);
        assertEquals(response, user);
        assertEquals(response.getId(), ID);
        assertEquals(response.getName(), Name);
        assertEquals(response.getEmail(), EMAIL);
        assertEquals(response.getPassword(), PASSWORD);
    }

    @Test
    void whenFindByIdThenReturnAnObjectNotFoundException() {
        when(userRepository.findById(anyInt())).thenThrow(new ObjectNotFoundException(OBJETO_NÃO_ENCONTRADO));
        try{
            userService.findById(ID);
        } catch (Exception e) {
            assertEquals(ObjectNotFoundException.class, e.getClass());
            assertEquals(OBJETO_NÃO_ENCONTRADO, e.getMessage());
        }
    }

    @Test
    void whenFindAllThenReturnAnListOfUser() {
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<User> response = userService.findAll();

        assertNotNull(response);
        assertEquals(response.size(), 1);
        assertEquals(response.get(INDEX), user);
        assertEquals(response.get(INDEX).getId(), ID);
        assertEquals(response.get(INDEX).getName(), Name);
        assertEquals(response.get(INDEX).getEmail(), EMAIL);
        assertEquals(response.get(INDEX).getPassword(), PASSWORD);
    }

    @Test
    void whenCreateThenReturnSuccess() {
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(modelMapper.map(any(UserDTO.class), any())).thenReturn(user);

        User response = userService.create(userDTO);
        assertNotNull(response);
        assertEquals(response, user);
        assertEquals(response.getId(), ID);
        assertEquals(response.getName(), Name);
        assertEquals(response.getEmail(), EMAIL);
        assertEquals(response.getPassword(), PASSWORD);
    }

    @Test
    void whenCreateThenReturnDataIntegrityViolationException() {
        when(userRepository.findByEmail(anyString())).thenReturn(userOptional);

       try{
           userOptional.get().setId(2);
           userService.create(userDTO);
       }catch (Exception e){
           assertEquals(DataIntegratyViolationException.class, e.getClass());
           assertEquals("Email já cadastrado!", e.getMessage());
       }
    }

    @Test
    void whenUpdateThenReturnSuccess() {
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(modelMapper.map(any(UserDTO.class), any())).thenReturn(user);

        User response = userService.update(userDTO);
        assertNotNull(response);
        assertEquals(response, user);
        assertEquals(response.getId(), ID);
        assertEquals(response.getName(), Name);
        assertEquals(response.getEmail(), EMAIL);
        assertEquals(response.getPassword(), PASSWORD);
    }

    @Test
    void delete() {
    }

    @Test
    void findByEmail() {
    }

    private void startUser(){
        user = new User();
        user.setId(ID);
        user.setName(Name);
        user.setEmail(EMAIL);
        user.setPassword(PASSWORD);

        userDTO = new UserDTO();
        userDTO.setId(ID);
        userDTO.setName(Name);
        userDTO.setEmail(EMAIL);
        userDTO.setPassword(PASSWORD);

        userOptional = Optional.of(user);
    }
}