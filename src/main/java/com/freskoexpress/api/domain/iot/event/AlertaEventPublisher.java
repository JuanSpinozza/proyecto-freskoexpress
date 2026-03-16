package com.freskoexpress.api.domain.iot.event;

import com.freskoexpress.domain.iot.Alerta;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * Publicador de eventos de alerta.
 * Los servicios inyectan este componente en lugar del servicio
 * de notificaciones directamente, manteniendo bajo acoplamiento.
 */
@Component
@RequiredArgsConstructor
public class AlertaEventPublisher {

    private final ApplicationEventPublisher publisher;

    public void publicar(Alerta alerta) {
        publisher.publishEvent(new AlertaGeneradaEvent(this, alerta));
    }
}
