package com.soltelec.servidor.dtos.reporte_ntc5365;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehiculoNtc5365 {
    private String placa,
    modelo,
    numMotor,
    vin,
    cilindraje,
    licencia,
    kilometraje,
    conversionGnw,
    fechaVencimientoGnv,
    marca,
    linea,
    clase,
    servicio,
    tCombustible,
    tMotor,
    rpmConocidas,
    rpmMinimasRalenti,
    rpmMaximasRalenti,
    disenoMotor,
    nTubosEscapes;
}
