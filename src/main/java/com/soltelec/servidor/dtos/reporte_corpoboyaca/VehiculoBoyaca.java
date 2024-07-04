package com.soltelec.servidor.dtos.reporte_corpoboyaca;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehiculoBoyaca {
    private String marca,
    linea,
    modelo,
    placa,
    cilindraje,
    clase,
    servicio,
    combustible,
    noMotor,
    vin,
    noLicencia,
    km,
    tipoMotor,
    desing,
    modificaciones,
    potenciaMotor,
    modificacionesMotor;
}
