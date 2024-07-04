package com.soltelec.servidor.dtos.reporte_corpoboyaca;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultadoOttoBoyaca {
    private String fugaExosto,
    fugaSilenciador,
    accesoriosTubo,
    tapaCombustible,
    tapaAceite,
    sistemaAdqusicionAire,
    salidasAdicionales,
    pcv,
    presenciaHumo,
    rpmFueraRango,
    fallaRefrigeracion,
    tempMotor,
    rpmRalenti,
    hcRalenti,
    coRalenti,
    co2Ralenti,
    o2Ralenti,
    rpmCrucero,
    hcCrucero,
    coCrucero,
    co2Crucero,
    o2Crucero,
    dilucion,
    incumplimientoEmision,
    conceptoFinal,
    ruido;
}
