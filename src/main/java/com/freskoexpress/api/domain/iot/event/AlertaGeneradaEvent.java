package com.freskoexpress.api.domain.iot.event;

import com.freskoexpress.api.domain.iot.Alerta;
import org.springframework.context.ApplicationEvent;

/**
 * Observer Pattern — Evento de alerta generada.
 *
 * Publicado por SensorService (u otros servicios) cuando se crea
 * una alerta. Los listeners reaccionan de forma desacoplada.
 *
 * Beneficio: SensorService no importa ni conoce al servicio de
 * notificaciones. Si mañana se agrega Slack o Firebase como canal,
 * solo se crea un nuevo listener. Nada más cambia.
 */
public class AlertaGeneradaEvent extends ApplicationEvent {

    private final Alerta alerta;

    public AlertaGeneradaEvent(Object source, Alerta alerta) {
        super(source);
        this.alerta = alerta;
    }

    public Alerta getAlerta() { return alerta; }
}
