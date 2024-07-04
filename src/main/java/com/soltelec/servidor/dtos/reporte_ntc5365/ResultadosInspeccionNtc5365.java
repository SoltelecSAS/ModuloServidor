package com.soltelec.servidor.dtos.reporte_ntc5365;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultadosInspeccionNtc5365 {
    private String 
    tempMotor,
    rpmRalenti,
    presenciaHumo,
    correccionOxigeno,
    rpmFuera,
    fugaTubo,
    salidasAdicionales,
    fugaAceite,
    fugaCombustible,
    rHcRalenti,
    rCoRalenti,
    rCo2Ralenti,
    rO2Ralenti,
    rGasSinCorregir,
    ncEmisiones,
    resFinal;
}
