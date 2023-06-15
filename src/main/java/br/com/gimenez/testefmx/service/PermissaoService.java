package br.com.gimenez.testefmx.service;

import br.com.gimenez.testefmx.exceptions.BadRequestException;
import br.com.gimenez.testefmx.model.Permissao;
import br.com.gimenez.testefmx.repository.PermissaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PermissaoService {
    private final PermissaoRepository repository;

    public Permissao save(Permissao permissao) {
        return this.repository.save(permissao);
    }

    public void remove(Long id) {
        Permissao permissao = findByIdWithException(id);

        this.repository.delete(permissao);
    }

    public List<Permissao> findByDescricao(String descricao) {
        return this.repository.findByDescricaoAndPadraoFalse(descricao);
    }

    public List<Permissao> findAll() {
        return this.repository.findByPadraoFalse();
    }

    public Permissao findByIdWithException(Long id) {
        return this.repository.findByIdAndPadraoFalse(id)
                .orElseThrow(() -> new BadRequestException("Permissao not found!"));
    }

    public Optional<Permissao> findById(Long id) {
        return this.repository.findByIdAndPadraoFalse(id);
    }
}
