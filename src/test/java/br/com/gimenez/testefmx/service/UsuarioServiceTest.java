package br.com.gimenez.testefmx.service;

import br.com.gimenez.testefmx.model.Permissao;
import br.com.gimenez.testefmx.model.Situacao;
import br.com.gimenez.testefmx.model.Usuario;
import br.com.gimenez.testefmx.repository.PermissaoRepository;
import br.com.gimenez.testefmx.repository.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static br.com.gimenez.testefmx.Util.createPermissaoPadrao;
import static br.com.gimenez.testefmx.Util.createUsuario;

@DataJpaTest
@DisplayName("Teste Usuario repository")
class UsuarioServiceTest {

    private UsuarioService usuarioService;
    private PermissaoService permissaoService;

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PermissaoRepository permissaoRepository;

    @BeforeEach
    void setup() {
        this.usuarioService = new UsuarioService(usuarioRepository, permissaoRepository);
        this.permissaoService = new PermissaoService(permissaoRepository);
    }

    @Test
    @DisplayName("Save Usuario when success")
    void saveUsuarioSuccess() {
        Permissao permissaoSaved = this.savePermissaoPadrao();

        Usuario usuario = createUsuario();

        Usuario usuarioSaved = this.usuarioService.save(usuario);

        Assertions.assertNotNull(usuarioSaved);
        Assertions.assertNotNull(usuarioSaved.getId());
        Assertions.assertEquals(usuarioSaved.getEmail(), usuario.getEmail());
        Assertions.assertEquals(usuarioSaved.getSenha(), usuario.getSenha());
        Assertions.assertEquals(Situacao.ATIVO, usuarioSaved.getSituacao());
        Assertions.assertNotNull(usuarioSaved.getPermissoes());
        Assertions.assertEquals(1, usuarioSaved.getPermissoes().size());
        Assertions.assertEquals(usuarioSaved.getPermissoes().get(0).getDescricao(), permissaoSaved.getDescricao());
        Assertions.assertNull(usuarioSaved.getUsuarioResponsavel());
    }

    @Test
    @DisplayName("Save Usuario without standard Permissao when error")
    void saveUsuarioWithoutStandartPermissaoError() {
        Usuario usuario = createUsuario();

        Assertions.assertThrows(Exception.class, () -> this.usuarioService.save(usuario));
    }

    @Test
    @DisplayName("Delete Usuario with ID when success")
    void whenIdFound_thenSuccess() {
        Permissao permissaoSaved = this.savePermissaoPadrao();

        Usuario usuario = createUsuario();
        Usuario usuarioSaved = this.usuarioService.save(usuario);

        this.usuarioService.remove(usuarioSaved.getId());

        Optional<Usuario> usuarioNotFound = this.usuarioService.findById(usuarioSaved.getId());

        Assertions.assertTrue(usuarioNotFound.isEmpty());
    }

    @Test
    @DisplayName("Delete Usuario with ID not found when error")
    void whenIdIsNotFound_thenThrowIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> this.usuarioService.remove(1L));
    }

    private Permissao savePermissaoPadrao() {
        return this.permissaoService.save(createPermissaoPadrao());
    }
}