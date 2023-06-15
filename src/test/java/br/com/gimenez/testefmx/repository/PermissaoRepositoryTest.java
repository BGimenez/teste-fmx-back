package br.com.gimenez.testefmx.repository;

import br.com.gimenez.testefmx.model.Permissao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static br.com.gimenez.testefmx.Util.createPermissao;

@DataJpaTest
@DisplayName("Teste Permissao repository")
class PermissaoRepositoryTest {

    @Autowired
    private PermissaoRepository permissaoRepository;

    @Test
    @DisplayName("Save Permissao when success")
    void savePermissaoSuccess() {
        Permissao permissao = createPermissao();

        Permissao permissaoSaved = this.permissaoRepository.save(permissao);

        Assertions.assertNotNull(permissaoSaved);
        Assertions.assertNotNull(permissaoSaved.getId());
        Assertions.assertEquals(permissaoSaved.getDescricao(), permissao.getDescricao());
    }

    @Test
    @DisplayName("Update Permissao when success")
    void updatePermissaoSuccess() {
        Permissao permissao = createPermissao();

        Permissao permissaoSaved = this.permissaoRepository.save(permissao);

        permissaoSaved.setDescricao("Administrador");

        Permissao permissaoUpdated = this.permissaoRepository.save(permissaoSaved);

        Assertions.assertNotNull(permissaoUpdated);
        Assertions.assertNotNull(permissaoUpdated.getId());
        Assertions.assertEquals(permissaoUpdated.getDescricao(), permissaoSaved.getDescricao());
    }

    @Test
    @DisplayName("Delete Permissao when success")
    void deletePermissaoSuccess() {
        Permissao permissao = createPermissao();

        Permissao permissaoSaved = this.permissaoRepository.save(permissao);

        this.permissaoRepository.delete(permissaoSaved);

        Optional<Permissao> permissaoOptional = this.permissaoRepository.findById(permissaoSaved.getId());

        Assertions.assertTrue(permissaoOptional.isEmpty());
    }

    @Test
    @DisplayName("List by descricao return list of Permissoes when success")
    void findByDescricaoReturnListPermissoesSuccess() {
        Permissao permissao = createPermissao();

        this.permissaoRepository.save(permissao);

        List<Permissao> permissoes = this.permissaoRepository.findByDescricao(permissao.getDescricao());

        Assertions.assertFalse(permissoes.isEmpty());
        Assertions.assertEquals(1, permissoes.size());
        Assertions.assertEquals(permissoes.get(0).getDescricao(), permissao.getDescricao());
    }

    @Test
    @DisplayName("List by descricao return empty list when Permissao is not found")
    void findByDescricaoReturnEmptyListWhenNotFound() {
        List<Permissao> permissoes = this.permissaoRepository.findByDescricao("Permissao");

        Assertions.assertTrue(permissoes.isEmpty());
    }

    @Test
    @DisplayName("List by padrao return list of Permissoes when success")
    void findByPadraoReturnListPermissoesSuccess() {
        Permissao permissao = createPermissao();
        permissao.setPadrao(true);

        this.permissaoRepository.save(permissao);

        List<Permissao> permissoes = this.permissaoRepository.findByPadraoTrue();

        Assertions.assertFalse(permissoes.isEmpty());
        Assertions.assertEquals(1, permissoes.size());
        Assertions.assertEquals(permissoes.get(0).getDescricao(), permissao.getDescricao());
    }

    @Test
    @DisplayName("List by padrao return empty list when Permissao is not found")
    void findByPadraoReturnEmptyListWhenNotFound() {
        List<Permissao> permissoes = this.permissaoRepository.findByPadraoTrue();

        Assertions.assertTrue(permissoes.isEmpty());
    }
}