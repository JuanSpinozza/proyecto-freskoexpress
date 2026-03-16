package com.freskoexpress.api.domain.mantenimiento;

import com.freskoexpress.domain.logistica.Vehiculo;
import com.freskoexpress.shared.enums.TipoMantenimiento;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "mantenimiento")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Mantenimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mantenimiento")
    private Integer idMantenimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vehiculo", nullable = false)
    private Vehiculo vehiculo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoMantenimiento tipo;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "km_en_servicio", nullable = false)
    private Integer kmEnServicio;

    @Column(name = "fecha_servicio", nullable = false)
    private LocalDate fechaServicio;

    @Column(name = "prox_km")
    private Integer proxKm;

    @Column(name = "prox_fecha")
    private LocalDate proxFecha;
}
