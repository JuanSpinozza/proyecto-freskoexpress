package com.freskoexpress.api.domain.logistica;

import com.freskoexpress.shared.enums.TipoVehiculo;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "vehiculo")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vehiculo")
    private Integer idVehiculo;

    @Column(nullable = false, unique = true, length = 15)
    private String placa;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoVehiculo tipo;

    @Column(nullable = false, length = 50)
    private String marca;

    @Column(nullable = false, length = 50)
    private String modelo;

    @Column(name = "capacidad_kg", nullable = false, precision = 8, scale = 2)
    private BigDecimal capacidadKg;

    @Column(nullable = false)
    private Integer kilometraje = 0;

    @Column(nullable = false)
    private Boolean disponible = true;

    @Column(nullable = false, length = 100)
    private String ciudad;
}
