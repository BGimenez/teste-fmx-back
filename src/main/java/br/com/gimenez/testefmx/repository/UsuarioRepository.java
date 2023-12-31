package br.com.gimenez.testefmx.repository;

import br.com.gimenez.testefmx.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    List<Usuario> findByEmail(String mail);
}
