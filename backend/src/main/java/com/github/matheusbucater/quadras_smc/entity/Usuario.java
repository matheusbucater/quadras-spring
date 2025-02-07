package com.github.matheusbucater.quadras_smc.entity;

import com.github.matheusbucater.quadras_smc.enums.TipoDeUsuario;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "reserva_jogador",
            joinColumns = @JoinColumn(name = "id_jogador"),
            inverseJoinColumns = @JoinColumn(name = "id_reserva")
    )
    private List<Reserva> jogos;

    @OneToMany(mappedBy = "donoDaReserva", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reserva> reservas;

    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProfessorHorario> horarios;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "sobrenome", nullable = false)
    private String sobrenome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "senha", nullable = false)
    private String senha;

    @Column(name = "telefone", unique = true, nullable = false)
    private String telefone;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_de_usuario", nullable = false)
    private TipoDeUsuario tipoDeUsuario;

    @Column(name = "password_reset_token")
    private String passwordResetToken;

    @Column(name = "verified_at")
    private LocalDateTime verifiedAt;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public Usuario(String nome, String sobrenome, String email, String senha, String descricao, String telefone) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
        this.senha = senha;
        this.descricao = descricao;
        this.telefone = telefone;
        this.tipoDeUsuario = TipoDeUsuario.JOGADOR;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return switch (tipoDeUsuario) {
            case SUPERVISOR -> List.of(
                    new SimpleGrantedAuthority("ROLE_SUPERVISOR")
                    //new SimpleGrantedAuthority("ROLE_ADMIN"),
                    //new SimpleGrantedAuthority("ROLE_USUARIO"),
                    //new SimpleGrantedAuthority("ROLE_PROFESSOR")
            );
            case ADMINISTRADOR -> List.of(
                    new SimpleGrantedAuthority("ROLE_ADMIN")
                    //new SimpleGrantedAuthority("ROLE_USUARIO"),
                    //new SimpleGrantedAuthority("ROLE_PROFESSOR")
            );
            case PROFESSOR -> List.of(
                    new SimpleGrantedAuthority("ROLE_PROFESSOR")
            );
            default -> List.of(
                    new SimpleGrantedAuthority("ROLE_USUARIO")
            );
        };
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isVerfied() {
        return this.verifiedAt != null;
    }

    public boolean isActive() {
        return this.deletedAt == null;
    }
}
