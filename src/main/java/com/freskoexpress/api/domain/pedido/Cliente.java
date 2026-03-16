package com.freskoexpress.api.domain.pedido;

import com.freskoexpress.domain.auth.Usuario;
import com.freskoexpress.shared.enums.TipoCliente;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "cliente")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Integer idCliente;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false, unique = true)
    private Usuario usuario;

    @Column(name = "razon_social", nullable = false, length = 150)
    private String razonSocial;

    @Column(nullable = false, unique = true, length = 20)
    private String nit;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoCliente tipo;

    @Column(nullable = false, length = 300)
    private String direccion;

    @Column(nullable = false, length = 100)
    private String ciudad;

    @Column(name = "coordenada_lat", precision = 10, scale = 7)
    private BigDecimal coordenadaLat;

    @Column(name = "coordenada_lng", precision = 10, scale = 7)
    private BigDecimal coordenadaLng;
}
