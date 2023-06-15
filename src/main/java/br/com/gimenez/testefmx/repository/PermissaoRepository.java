package br.com.gimenez.testefmx.repository;

import br.com.gimenez.testefmx.model.Permissao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermissaoRepository extends JpaRepository<Permissao, Long> {
    List<Permissao> findByDescricaoAndPadraoFalse(String descricao);

    List<Permissao> findByPadraoTrue();
    List<Permissao> findByPadraoFalse();

    Optional<Permissao> findByIdAndPadraoFalse(Long id);


}
