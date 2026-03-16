package com.freskoexpress.api.domain.iot.event;

import com.freskoexpress.shared.enums.SeveridadAlerta;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class AlertaNotificacionListener {

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
