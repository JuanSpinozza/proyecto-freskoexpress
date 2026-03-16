package com.freskoexpress.api.domain.logistica;

import com.freskoexpress.domain.auth.Usuario;
import com.freskoexpress.shared.enums.EstadoRuta;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name = "ruta")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Ruta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ruta")
    private Integer idRuta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vehiculo", nullable = false)
    private Vehiculo vehiculo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario_conductor", nullable = false)
    private Usuario conductor;

    @Column(name = "fecha_ruta", nullable = false)
    private LocalDate fechaRuta;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoRuta estado = EstadoRuta.planificada;

    @Column(name = "hora_inicio_real")
    private OffsetDateTime horaInicioReal;

    @Column(name = "hora_fin_real")
    private OffsetDateTime horaFinReal;

    @Column(name = "km_recorridos", precision = 8, scale = 2)
    private BigDecimal kmRecorridos;
}
