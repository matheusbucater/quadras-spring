package com.github.matheusbucater.quadras_smc.entity;

import com.github.matheusbucater.quadras_smc.enums.EstadoDaQuadra;
import com.github.matheusbucater.quadras_smc.enums.TipoDeQuadra;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "quadras")
public class Quadra {

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @OneToMany(mappedBy = "quadra", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<QuadraHorario> horarios;

    @OneToMany(mappedBy = "quadra", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reserva> reservas;

    @Column(name = "nome", unique = true, nullable = false)
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "eh_coberta", nullable = false)
    private Boolean ehCoberta;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_de_quadra", nullable = false)
    private TipoDeQuadra tipoDeQuadra;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_da_quadra", nullable = false)
    private EstadoDaQuadra estadoDaQuadra;

    @Column(name = "imgurl")
    private String imgurl;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public Quadra(
            String nome,
            String descricao,
            Boolean ehCoberta,
            TipoDeQuadra tipoDeQuadra,
            EstadoDaQuadra estadoDaQuadra,
            String imgurl
    ) {

        this.nome = nome;
        this.descricao = descricao;
        this.ehCoberta = ehCoberta;
        this.tipoDeQuadra = tipoDeQuadra;
        this.estadoDaQuadra = estadoDaQuadra;
        this.imgurl = imgurl;

    }
}
