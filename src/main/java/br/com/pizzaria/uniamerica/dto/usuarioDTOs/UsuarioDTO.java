package br.com.pizzaria.uniamerica.dto.usuarioDTOs;

import br.com.pizzaria.uniamerica.entities.Cargo;
import br.com.pizzaria.uniamerica.entities.Usuario;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

@NoArgsConstructor
public class UsuarioDTO {
    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    @NotNull
    private String login;
    @Getter
    @Setter
    @NotNull
    private String senha;
    @Getter
    @Setter
    @NotNull
    @Email
    private String email;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private String cargo;

    public UsuarioDTO(Usuario entity) {
        id = entity.getId();
        login = entity.getLogin();
        senha = entity.getSenha();
        email = entity.getEmail();
        cargo = String.valueOf(entity.getCargo());

    }

    public UsuarioDTO(Long id, String login, String senha, String email, String cargo) {
        this.id = id;
        this.login = login;
        this.senha = senha;
        this.email = email;
        this.cargo = cargo;
    }
}
