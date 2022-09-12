package br.com.devdicas.api.services.impl;

import br.com.devdicas.api.domain.User;
import br.com.devdicas.api.domain.UserDTO;
import br.com.devdicas.api.repositories.UserRepository;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.Optional;

class UserServiceImplTest {

    public static final Integer ID = 1;
    public static final String Name = "Teste";
    public static final String EMAIL = "asda@teste";
    public static final String PASSWORD = "123456";
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
    void findAll() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
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