package com.freskoexpress.api.domain.facturacion.strategy;

import com.freskoexpress.domain.facturacion.dto.RegistrarPagoRequest;
import com.freskoexpress.shared.enums.MetodoPago;
import com.freskoexpress.shared.exception.BusinessException;
import org.springframework.stereotype.Component;

/**
 * Estrategia: Transferencia bancaria.
 * Valida que la referencia bancaria exista.
 * En producción aquí iría la lógica de validación contra archivo plano del banco (SFTP).
 */
@Component
public class TransferenciaPagoStrategy implements PagoStrategy {

    @Override
    public String procesar(RegistrarPagoRequest request) {
        if (request.referenciaPago() == null || request.referenciaPago().isBlank()) {
            throw new BusinessException(
                "La referencia bancaria es obligatoria para pagos por transferencia");
        }
        // TODO: BancoAdapter → validar referencia contra archivo plano SFTP
        return request.referenciaPago();
    }

    @Override
    public boolean aplica(MetodoPago metodo) {
        return metodo == MetodoPago.transferencia;
    }
}
