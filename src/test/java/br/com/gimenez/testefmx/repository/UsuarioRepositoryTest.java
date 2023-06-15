package br.com.gimenez.testefmx.repository;

import br.com.gimenez.testefmx.model.Permissao;
import br.com.gimenez.testefmx.model.Situacao;
import br.com.gimenez.testefmx.model.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static br.com.gimenez.testefmx.Util.*;

@DataJpaTest
@DisplayName("Teste Usuario repository")
class UsuarioRepositoryTest {

    //TODO: refatorar teste criando metodo de save padrao para todos os casos e metodo para asserts padrao tbm

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PermissaoRepository permissaoRepository;

    @Test
    @DisplayName("Save Usuario when success")
    void saveUsuarioSuccess() {
        Usuario usuario = createUsuario();

        Usuario usuarioSaved = this.usuarioRepository.save(usuario);

        Assertions.assertNotNull(usuarioSaved);
        Assertions.assertNotNull(usuarioSaved.getId());
        Assertions.assertEquals(usuarioSaved.getEmail(), usuario.getEmail());
        Assertions.assertEquals(usuarioSaved.getSenha(), usuario.getSenha());
        Assertions.assertEquals(Situacao.ATIVO, usuarioSaved.getSituacao());
        Assertions.assertNull(usuarioSaved.getUsuarioResponsavel());
    }

    @Test
    @DisplayName("Save Usuario with Permissao when success")
    void saveUsuarioWithPermissaoSuccess() {
        Permissao permissao = createPermissao();
        Permissao permissaoSaved = this.permissaoRepository.save(permissao);

        Usuario usuario = createUsuario();
        usuario.getPermissoes().add(permissaoSaved);

        Usuario usuarioSaved = this.usuarioRepository.save(usuario);

        Assertions.assertNotNull(usuarioSaved);
        Assertions.assertNotNull(usuarioSaved.getId());
        Assertions.assertEquals(usuarioSaved.getEmail(), usuario.getEmail());
        Assertions.assertEquals(usuarioSaved.getSenha(), usuario.getSenha());
        Assertions.assertEquals(Situacao.ATIVO, usuarioSaved.getSituacao());
        Assertions.assertEquals(1, usuarioSaved.getPermissoes().size());
        Assertions.assertEquals(usuarioSaved.getPermissoes().get(0).getDescricao(), permissaoSaved.getDescricao());
    }

    @Test
    @DisplayName("Save Usuario with other UsuarioResponsavel when success")
    void saveUsuarioWithOtherUsuarioResponsavelSuccess() {
        Usuario usuarioResponsavel = createUsuarioResponsavel();
        Usuario usuarioResponsavelSaved = this.usuarioRepository.save(usuarioResponsavel);

        Usuario usuario = createUsuario();
        usuario.setUsuarioResponsavel(usuarioResponsavelSaved);

        Usuario usuarioSaved = this.usuarioRepository.save(usuario);

        Assertions.assertNotNull(usuarioSaved);
        Assertions.assertNotNull(usuarioSaved.getId());
        Assertions.assertEquals(usuarioSaved.getEmail(), usuario.getEmail());
        Assertions.assertEquals(usuarioSaved.getSenha(), usuario.getSenha());
        Assertions.assertEquals(Situacao.ATIVO, usuarioSaved.getSituacao());
        Assertions.assertNotNull(usuarioSaved.getUsuarioResponsavel());
        Assertions.assertEquals(usuarioSaved.getUsuarioResponsavel(), usuarioResponsavelSaved);
    }

    @Test
    @DisplayName("Update Usuario when success")
    void updateUsuarioSuccess() {
        Usuario usuario = createUsuario();

        Usuario usuarioSaved = this.usuarioRepository.save(usuario);

        usuarioSaved.setEmail("usuario@email.com");

        Usuario usuarioUpdated = this.usuarioRepository.save(usuarioSaved);

        Assertions.assertNotNull(usuarioUpdated);
        Assertions.assertNotNull(usuarioUpdated.getId());
        Assertions.assertEquals(usuarioUpdated.getEmail(), usuarioSaved.getEmail());
    }

    @Test
    @DisplayName("Update inactivate Usuario when success")
    void updateInactivateUsuarioSuccess() {
        Usuario usuario = createUsuario();

        Usuario usuarioSaved = this.usuarioRepository.save(usuario);

        usuarioSaved.setSituacao(Situacao.INATIVO);

        Usuario usuarioUpdated = this.usuarioRepository.save(usuarioSaved);

        Assertions.assertNotNull(usuarioUpdated);
        Assertions.assertNotNull(usuarioUpdated.getId());
        Assertions.assertEquals(Situacao.INATIVO, usuarioUpdated.getSituacao());
    }

    @Test
    @DisplayName("Update list permissoes Usuario when success")
    void updateListPermissoesUsuarioSuccess() {
        //Create permissoes
        Permissao permissao1 = createPermissao();
        permissao1 = this.permissaoRepository.save(permissao1);

        Permissao permissao2 = createPermissao();
        permissao2.setDescricao("Permissao 2");
        permissao2 = this.permissaoRepository.save(permissao2);

        Permissao permissao3 = createPermissao();
        permissao3.setDescricao("Permissao 3");
        permissao3 = this.permissaoRepository.save(permissao3);

        List<Permissao> permissoes1 = List.of(permissao1, permissao2);
        List<Permissao> permissoes2 = List.of(permissao2, permissao3);


        //Create usuario
        Usuario usuario = createUsuario();
        usuario.getPermissoes().addAll(permissoes1);

        Usuario usuarioSaved = this.usuarioRepository.save(usuario);
        Assertions.assertNotNull(usuarioSaved);
        Assertions.assertNotNull(usuarioSaved.getId());
        Assertions.assertEquals(2, usuarioSaved.getPermissoes().size());
        Assertions.assertEquals(usuarioSaved.getPermissoes().get(0).getDescricao(), permissao1.getDescricao());
        Assertions.assertEquals(usuarioSaved.getPermissoes().get(1).getDescricao(), permissao2.getDescricao());


        //Update permissoes
        usuarioSaved.setPermissoes(permissoes2);

        Usuario usuarioUpdated = this.usuarioRepository.save(usuarioSaved);

        Assertions.assertNotNull(usuarioUpdated);
        Assertions.assertNotNull(usuarioUpdated.getId());
        Assertions.assertEquals(2, usuarioSaved.getPermissoes().size());
        Assertions.assertEquals(usuarioSaved.getPermissoes().get(0).getDescricao(), permissao2.getDescricao());
        Assertions.assertEquals(usuarioSaved.getPermissoes().get(1).getDescricao(), permissao3.getDescricao());
    }

    @Test
    @DisplayName("Delete Usuario when success")
    void deleteUsuarioSuccess() {
        Usuario usuario = createUsuario();

        Usuario usuarioSaved = this.usuarioRepository.save(usuario);

        this.usuarioRepository.delete(usuarioSaved);

        Optional<Usuario> usuarioOptional = this.usuarioRepository.findById(usuarioSaved.getId());

        Assertions.assertTrue(usuarioOptional.isEmpty());
    }

    @Test
    @DisplayName("List by email return list of Usuarios when success")
    void findByEmailReturnListUsuariosSuccess() {
        Usuario usuario = createUsuario();

        this.usuarioRepository.save(usuario);

        List<Usuario> usuarios = this.usuarioRepository.findByEmail(usuario.getEmail());

        Assertions.assertFalse(usuarios.isEmpty());
        Assertions.assertEquals(1, usuarios.size());
        Assertions.assertEquals(usuarios.get(0).getEmail(), usuario.getEmail());
    }

    @Test
    @DisplayName("List by email return empty list when Usuario is not found")
    void findByEmailReturnEmptyListWhenNotFound() {
        List<Usuario> usuarios = this.usuarioRepository.findByEmail("email@email.com");

        Assertions.assertTrue(usuarios.isEmpty());
    }
}