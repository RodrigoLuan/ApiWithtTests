package br.com.devdicas.api.resources;

import br.com.devdicas.api.domain.User;
import br.com.devdicas.api.domain.UserDTO;
import br.com.devdicas.api.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserResourceTest {
    public static final Integer ID = 1;
    public static final String Name = "Teste";
    public static final String EMAIL = "asda@teste";
    public static final String PASSWORD = "123456";
    public static final String OBJETO_NÃO_ENCONTRADO = "Objeto não encontrado";
    public static final int INDEX = 0;
    public static final String EMAIL_JA_CADASTRADO = "Email já cadastrado!";

    private User user;
    private UserDTO userDTO;

    @InjectMocks
    private UserResource userResource;

    @Mock
    private UserServiceImpl userService;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }


    @Test
    void whenFindByIdThenReturnSuccess() {
        when(userService.findById(anyInt())).thenReturn(user);
        when(modelMapper.map(any(), any())).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = userResource.findById(ID);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(userDTO, response.getBody());

        assertEquals(userDTO.getId(), response.getBody().getId());
        assertEquals(userDTO.getName(), response.getBody().getName());
        assertEquals(userDTO.getEmail(), response.getBody().getEmail());
        assertEquals(userDTO.getPassword(), response.getBody().getPassword());
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
    }

}