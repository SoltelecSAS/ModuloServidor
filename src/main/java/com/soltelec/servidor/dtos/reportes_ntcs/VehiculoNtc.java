package com.soltelec.servidor.dtos.reportes_ntcs;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VehiculoNtc {
    private String placa,
    modelo,
    numMotor,
    vin,
    modMotor,
    diaEscape,
    cilindraje,
    licTrans,
    km,
    gnvConv,
    gnvConvV,
    marca,
    linea,
    clase,
    servicio,
    tipComb,
    tipMotor,
    rpmFab,
    ralMinFab,
    ralMaxFab,
    gobMinFab,
    gobMaxFab,
    disMotor,
    numEscape,
    diseno;
}
