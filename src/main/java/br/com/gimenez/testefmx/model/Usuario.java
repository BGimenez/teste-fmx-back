package br.com.gimenez.testefmx.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "usuario")
public class Usuario implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Email
    @NotNull(message = "O campo E-mail é obrigatório!")
    @Column(nullable = false)
    private String email;

    @CPF
    private String cpf;

    @NotNull(message = "O campo Senha é obrigatório!")
    @Column(nullable = false)
    private String senha;

    @Builder.Default
    @Enumerated(value = EnumType.STRING)
    private Situacao situacao = Situacao.ATIVO;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_responsavel_id")
    private Usuario usuarioResponsavel;

    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "usuario_permissao",
    joinColumns = {@JoinColumn(name = "usuario_id")},
    inverseJoinColumns = {@JoinColumn(name = "permissao_id")})
    private List<Permissao> permissoes = new ArrayList<>();

}
