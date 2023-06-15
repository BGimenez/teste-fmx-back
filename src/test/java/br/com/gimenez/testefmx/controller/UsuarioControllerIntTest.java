package br.com.gimenez.testefmx.controller;

import br.com.gimenez.testefmx.exceptions.BadRequestException;
import br.com.gimenez.testefmx.model.Usuario;
import br.com.gimenez.testefmx.repository.PermissaoRepository;
import br.com.gimenez.testefmx.repository.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static br.com.gimenez.testefmx.Util.createPermissaoPadrao;
import static br.com.gimenez.testefmx.Util.createUsuario;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class UsuarioControllerIntTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PermissaoRepository permissaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    void setup() {
        permissaoRepository.save(createPermissaoPadrao());
    }

    @Test
    @DisplayName("Save usuario when success")
    void saveUsuario_WhenSuccess() {
        Usuario usuario = createUsuario();

        ResponseEntity<Usuario> usuarioResponse =
                restTemplate.postForEntity("/usuarios", usuario, Usuario.class);

        Assertions.assertNotNull(usuarioResponse);
        Assertions.assertEquals(HttpStatus.CREATED, usuarioResponse.getStatusCode());
        Assertions.assertNotNull(usuarioResponse.getBody());
        Assertions.assertNotNull(usuarioResponse.getBody().getId());

    }

    @Test
    @DisplayName("Save usuario with not valid email when error")
    void saveUsuarioWithNotValidEmail_WhenError() {
        Usuario usuario = createUsuario();
        usuario.setEmail("email.errado");

        ResponseEntity<BadRequestException> usuarioResponse =
                restTemplate.postForEntity("/usuarios", usuario, BadRequestException.class);

        Assertions.assertNotNull(usuarioResponse);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, usuarioResponse.getStatusCode());
    }

    @Test
    @DisplayName("Save usuario with not valid cpf when error")
    void saveUsuarioWithNotValidCpf_WhenError() {
        Usuario usuario = createUsuario();
        usuario.setCpf("12345678910");

        ResponseEntity<BadRequestException> usuarioResponse =
                restTemplate.postForEntity("/usuarios", usuario, BadRequestException.class);

        Assertions.assertNotNull(usuarioResponse);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, usuarioResponse.getStatusCode());
    }

    @Test
    void listAllUsuarios_WhenSuccess() {
        Usuario usuarioSaved = usuarioRepository.save(createUsuario());

        List<Usuario> usuarios = restTemplate.exchange("/usuarios", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Usuario>>() {
                }).getBody();

        org.assertj.core.api.Assertions.assertThat(usuarios)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertEquals(usuarioSaved.getEmail(), usuarios.get(0).getEmail());
    }
}
