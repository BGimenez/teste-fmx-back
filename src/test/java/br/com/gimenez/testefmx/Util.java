package br.com.gimenez.testefmx;

import br.com.gimenez.testefmx.model.Permissao;
import br.com.gimenez.testefmx.model.Usuario;

public class Util {
    public static Permissao createPermissao() {
        return Permissao.builder()
                .descricao("Permissao 1")
                .build();
    }

    public static Permissao createPermissaoPadrao() {
        return Permissao.builder()
                .descricao("Permissao 1")
                .padrao(true)
                .build();
    }

    public static Usuario createUsuario() {
        return Usuario.builder()
                .email("email@email.com")
                .senha("password123")
                .build();
    }

    public static Usuario createUsuarioResponsavel() {
        return Usuario.builder()
                .email("responsavel@email.com")
                .senha("password123")
                .build();
    }
}
