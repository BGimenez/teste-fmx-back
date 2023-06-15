package br.com.gimenez.testefmx.service;

import br.com.gimenez.testefmx.model.Usuario;
import br.com.gimenez.testefmx.repository.PermissaoRepository;
import br.com.gimenez.testefmx.repository.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static br.com.gimenez.testefmx.Util.createUsuario;
import static org.mockito.Mockito.mock;

@DisplayName("Teste Usuario repository mock")
public class UsuarioServiceMockTest {
    //TODO: Criar testes no service: um usuario nao pode ser responsável por ele mesmo (criar teste mocado)

    private UsuarioService usuarioService;

    @BeforeEach
    void setup() {
        this.usuarioService = new UsuarioService(mock(UsuarioRepository.class), mock(PermissaoRepository.class));
    }

    @Test
    @DisplayName("Update with UsuarioResponsavel himself when error")
    void updateWithUsuarioResponsavelHimSelfError() {
        Usuario usuario = createUsuario();
        usuario.setId(1L);
        usuario.setUsuarioResponsavel(usuario);

        Assertions.assertThrows(Exception.class, () -> this.usuarioService.save(usuario));
    }
}
