package com.freskoexpress.api.controller;

import com.freskoexpress.api.domain.iot.SensorService;
import com.freskoexpress.api.domain.iot.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sensores")
@Tag(name = "IoT / Sensores", description = "Ingesta de lecturas y gestión de alertas")
public class SensorController {

    private final SensorService sensorService;

    public SensorController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @PostMapping("/lectura")
    @Operation(summary = "Recibir lectura IoT — evalúa threshold y publica evento si hay alerta")
    public ResponseEntity<Void> recibirLectura(@Valid @RequestBody LecturaSensorRequest request) {
        sensorService.procesarLectura(request);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/alertas")
    @Operation(summary = "Alertas activas sin reconocer")
    public ResponseEntity<List<AlertaResponse>> alertasActivas() {
        return ResponseEntity.ok(sensorService.alertasActivas());
    }

    @GetMapping("/alertas/vehiculo/{id}")
    @Operation(summary = "Historial de alertas de un vehículo")
    public ResponseEntity<List<AlertaResponse>> alertasPorVehiculo(@PathVariable Integer id) {
        return ResponseEntity.ok(sensorService.alertasPorVehiculo(id));
    }

    @PatchMapping("/alertas/{id}/reconocer")
    @Operation(summary = "Reconocer alerta (marcar como leída)")
    public ResponseEntity<AlertaResponse> reconocer(@PathVariable Integer id) {
        return ResponseEntity.ok(sensorService.reconocerAlerta(id));
    }
}