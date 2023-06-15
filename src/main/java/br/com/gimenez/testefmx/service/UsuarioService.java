package br.com.gimenez.testefmx.service;

import br.com.gimenez.testefmx.exceptions.BadRequestException;
import br.com.gimenez.testefmx.model.Permissao;
import br.com.gimenez.testefmx.model.Usuario;
import br.com.gimenez.testefmx.repository.PermissaoRepository;
import br.com.gimenez.testefmx.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository repository;
    private final PermissaoRepository permissaoRepository;

    @SneakyThrows
    public Usuario save(Usuario usuario) {
        if (usuario.getId() == null) {
            List<Permissao> permissaoPadrao = this.permissaoRepository.findByPadraoTrue();
            if (permissaoPadrao.isEmpty()) {
                throw new Exception("Not found any standard Permissao. Please, verify!");
            }
            usuario.getPermissoes().add(permissaoPadrao.get(0));
        } else {
            if (usuario.getUsuarioResponsavel() != null && usuario.getId().equals(usuario.getUsuarioResponsavel().getId())) {
                throw new Exception("A Usuario cannot be responsible for himself. Please verify!");
            }
        }

        return this.repository.save(usuario);
    }

    public void remove(Long id) {
        Usuario usuario = this.repository.findById(id)
                .orElseThrow(() -> new BadRequestException("Usuario not found!"));

        this.repository.delete(usuario);
    }

    public List<Usuario> findByEmail(String email) {
        return this.repository.findByEmail(email);
    }

    public List<Usuario> findAll() {
        return this.repository.findAll();
    }

    public Usuario findByIdWithException(Long id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new BadRequestException("Usuario not found!"));
    }

    public Optional<Usuario> findById(Long id) {
        return this.repository.findById(id);
    }
}
