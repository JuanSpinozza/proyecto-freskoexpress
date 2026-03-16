package com.freskoexpress.api.domain.iot.factory;

import com.freskoexpress.domain.inventario.Producto;
import com.freskoexpress.domain.iot.Alerta;
import com.freskoexpress.domain.iot.LecturaSensor;
import com.freskoexpress.domain.logistica.Vehiculo;
import com.freskoexpress.shared.enums.SeveridadAlerta;
import com.freskoexpress.shared.enums.TipoAlerta;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Factory Method Pattern — Creación de Alertas.
 *
 * Centraliza la construcción de los 4 tipos de Alerta del sistema.
 * Cada método factory encapsula las reglas de severidad y el mensaje
 * correspondiente, garantizando consistencia sin duplicar lógica.
 *
 * Sin este factory, SensorService, InventarioService y
 * MantenimientoService construirían alertas de forma independiente
 * y divergente.
 */
@Component
public class AlertaFactory {

    private static final BigDecimal TEMP_CRITICA_MIN = new BigDecimal("-2");
    private static final BigDecimal TEMP_CRITICA_MAX = new BigDecimal("20");

    /** Crea alerta de temperatura basada en una lectura fuera de rango. */
    public Alerta crearAlertaTemperatura(LecturaSensor lectura, Vehiculo vehiculo) {
        BigDecimal temp = lectura.getTemperaturaC();
        boolean critica = temp.compareTo(TEMP_CRITICA_MIN) < 0
                       || temp.compareTo(TEMP_CRITICA_MAX) > 0;

        return Alerta.builder()
            .tipo(TipoAlerta.temperatura)
            .idLectura(lectura.getIdLectura())
            .vehiculo(vehiculo)
            .severidad(critica ? SeveridadAlerta.critica : SeveridadAlerta.advertencia)
            .mensaje(String.format(
                "Temperatura %.2f°C fuera de rango en vehículo %s (ruta activa)",
                temp, vehiculo.getPlaca()))
            .reconocida(false)
            .build();
    }

    /** Crea alerta de reposición cuando el stock baja del mínimo. */
    public Alerta crearAlertaReposicion(Producto producto) {
        return Alerta.builder()
            .tipo(TipoAlerta.reposicion)
            .producto(producto)
            .severidad(SeveridadAlerta.advertencia)
            .mensaje(String.format(
                "Stock bajo para '%s'. Stock mínimo: %.2f %s",
                producto.getNombre(), producto.getStockMinimo(), producto.getUnidadMedida()))
            .reconocida(false)
            .build();
    }

    /** Crea alerta de vencimiento próximo para un lote. */
    public Alerta crearAlertaVencimiento(Producto producto, LocalDate fechaVencimiento) {
        return Alerta.builder()
            .tipo(TipoAlerta.vencimiento)
            .producto(producto)
            .severidad(SeveridadAlerta.advertencia)
            .mensaje(String.format(
                "Lote de '%s' vence el %s. Priorizar rotación FIFO.",
                producto.getNombre(), fechaVencimiento))
            .reconocida(false)
            .build();
    }

    /** Crea alerta de mantenimiento para un vehículo. */
    public Alerta crearAlertaMantenimiento(Vehiculo vehiculo, String motivo) {
        return Alerta.builder()
            .tipo(TipoAlerta.mantenimiento)
            .vehiculo(vehiculo)
            .severidad(SeveridadAlerta.advertencia)
            .mensaje(String.format(
                "Mantenimiento requerido para vehículo %s (%s %s): %s",
                vehiculo.getPlaca(), vehiculo.getMarca(), vehiculo.getModelo(), motivo))
            .reconocida(false)
            .build();
    }
}
