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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
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
        HttpServletRequest mockRequest = new MockHttpServletRequest();
        ServletRequestAttributes servletRequestAttributes = new ServletRequestAttributes(mockRequest);
        RequestContextHolder.setRequestAttributes(servletRequestAttributes);
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
    void whenFindAllThenReturnAListOfUserDTO() {
        when(userService.findAll()).thenReturn(List.of(user));
        when(modelMapper.map(any(), any())).thenReturn(userDTO);

        ResponseEntity<List<UserDTO>> response = userResource.findAll();

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ArrayList.class, response.getBody().getClass());
        assertEquals(userDTO, response.getBody().get(INDEX));

        assertEquals(userDTO.getId(), response.getBody().get(INDEX).getId());
        assertEquals(userDTO.getName(), response.getBody().get(INDEX).getName());
        assertEquals(userDTO.getEmail(), response.getBody().get(INDEX).getEmail());
        assertEquals(userDTO.getPassword(), response.getBody().get(INDEX).getPassword());
    }

    @Test
    void whenCreateThenReturnCreated() {
        when(userService.create(any())).thenReturn(user);
        when(modelMapper.map(any(), any())).thenReturn(userDTO);


        ResponseEntity<UserDTO> response = userResource.create(userDTO);

        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getHeaders().get("Location"));
    }

    @Test
    void whenUpdateThenReturnSuccess() {
        when(userService.update(userDTO)).thenReturn(user);
        when(modelMapper.map(any(), any())).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = userResource.update(ID, userDTO);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(userDTO, response.getBody());

        assertEquals(userDTO.getId(), response.getBody().getId());
        assertEquals(userDTO.getName(), response.getBody().getName());
        assertEquals(userDTO.getEmail(), response.getBody().getEmail());
    }

    @Test
    void whenDeleteThenReturnSucess() {
        doNothing().when(userService).delete(anyInt());

        ResponseEntity<UserDTO> response = userResource.delete(ID);
        assertNotNull(response);
        assertEquals(ResponseEntity.class, response.getClass());
        verify(userService, times(1)).delete(anyInt());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
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