package com.freskoexpress.api.domain.iot;

import com.freskoexpress.domain.logistica.Vehiculo;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "lectura_sensor")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class LecturaSensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lectura")
    private Long idLectura;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vehiculo", nullable = false)
    private Vehiculo vehiculo;

    @Column(name = "id_ruta")
    private Integer idRuta;

    @Column(name = "temperatura_c", nullable = false, precision = 5, scale = 2)
    private BigDecimal temperaturaC;

    @Column(name = "humedad_pct", nullable = false, precision = 5, scale = 2)
    private BigDecimal humedadPct;

    @Column(name = "fuera_de_rango", nullable = false)
    private Boolean fueraDeRango = false;

    @Column(nullable = false, updatable = false)
    private OffsetDateTime timestamp;

    @PrePersist
    protected void onCreate() {
        if (this.timestamp == null) this.timestamp = OffsetDateTime.now();
    }
}
