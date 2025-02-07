package com.github.matheusbucater.quadras_smc.entity;

import com.github.matheusbucater.quadras_smc.enums.EstadoDaReserva;
import com.github.matheusbucater.quadras_smc.enums.TipoDeReserva;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "reservas")
public class Reserva {

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @ManyToMany(mappedBy = "jogos")
    private List<Usuario> jogadores;

    @ManyToOne
    @JoinColumn(name = "id_dono_da_reserva", nullable = false)
    private Usuario donoDaReserva;

    @ManyToOne
    @JoinColumn(name = "id_quadra", nullable = false)
    private Quadra quadra;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_de_reserva", nullable = false)
    private TipoDeReserva tipoDeReserva;

    @Column(name = "num_jogadores", nullable = false)
    private Integer numJogadores;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "eh_publica", nullable = false)
    private Boolean ehPublica;

    @Column(name = "dt_reserva", nullable = false)
    private LocalDate dtReserva;

    @Column(name = "hr_inicio", nullable = false)
    private LocalTime hrInicio;

    @Column(name = "hr_fim", nullable = false)
    private LocalTime hrFim;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_reseva", nullable = false)
    private EstadoDaReserva estadoDaReseva;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public Reserva(
            Usuario donoDaReserva,
            Quadra quadra,
            List<Usuario> jogadores,
            TipoDeReserva tipoDeReserva,
            Integer numJogadores,
            String desc,
            Boolean ehPublica,
            EstadoDaReserva estadoDaReserva,
            LocalDate dtReserva,
            LocalTime hrInicio,
            LocalTime hrFim
    ) {
        this.donoDaReserva = donoDaReserva;
        this.quadra = quadra;
        this.tipoDeReserva = tipoDeReserva;
        this.jogadores = jogadores;
        this.numJogadores = numJogadores;
        this.descricao = desc;
        this.ehPublica = ehPublica;
        this.estadoDaReseva = estadoDaReserva;
        this.dtReserva = dtReserva;
        this.hrInicio = hrInicio;
        this.hrFim = hrFim;
    }
}

