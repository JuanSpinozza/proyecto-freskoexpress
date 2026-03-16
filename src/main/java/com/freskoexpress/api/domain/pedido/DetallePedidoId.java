package com.freskoexpress.api.domain.pedido;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
public class DetallePedidoId implements Serializable {
    private Integer idPedido;
    private Integer idLote;
}
