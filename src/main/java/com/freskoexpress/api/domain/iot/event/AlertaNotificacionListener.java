package com.freskoexpress.api.domain.iot.event;

import com.freskoexpress.api.domain.iot.Alerta;
import com.freskoexpress.api.shared.enums.SeveridadAlerta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Listener concreto del Observer Pattern.
 *
 * Reacciona a AlertaGeneradaEvent y dispara la notificación.
 * @Async garantiza que no bloquea el pipeline de ingestión IoT,
 * lo cual es crítico para soportar 500 dispositivos simultáneos
 * (RNF del proyecto).
 *
 * En producción: inyectar NotificacionAdapter (Twilio/SendGrid/Firebase)
 * para despachar SMS y Push al operador y administrador.
 */
@Component
public class AlertaNotificacionListener {

    private static final Logger log = LoggerFactory.getLogger(AlertaNotificacionListener.class);

    @Async
    @EventListener
    public void onAlertaGenerada(AlertaGeneradaEvent event) {
        Alerta alerta = event.getAlerta();

        if (alerta.getSeveridad() == SeveridadAlerta.critica) {
            log.warn("[ALERTA CRITICA] Tipo={} | Mensaje={}",
                    alerta.getTipo(), alerta.getMensaje());
            // TODO: notificacionAdapter.enviarSMS(operadores, alerta.getMensaje());
            // TODO: notificacionAdapter.enviarPush(admins, alerta.getMensaje());
        } else {
            log.info("[ALERTA] Tipo={} | Mensaje={}",
                    alerta.getTipo(), alerta.getMensaje());
            // TODO: notificacionAdapter.enviarEmail(operadores, alerta.getMensaje());
        }
    }
}