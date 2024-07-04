package com.soltelec.servidor.dtos.reporte_corpoboyaca;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultadoDieselBoyaca {
    private String fugaTubo,
    fugaSilenciador,
    tapaCombustible,
    tapaAceite,
    accesorios,
    salidasAdicionales,
    filtroAire,
    refrigeracion,
    rpmFueraRango,
    malFuncionamiento,
    controlVelocidadMotor,
    dispositivosRpm,
    tempMotor,
    velocidad5s,
    fallaMotor,
    rpmGovernadaMediana,
    rpmRalenti,
    densidadPreliminar,
    rpmGobernadaPreliminar,
    dencidadC1,
    gobernadaC1,
    dencidadC2,
    gobernadaC2,
    dencidadC3,
    gobernadaC3,
    ltoe,
    tempFinalMotor,
    fallaTempMotor,
    instabilidadCiclos,
    diferenciaAritmetica,
    promedio,
    incumplimientoNiveles,
    resultadoPrueba,
    ruido;
}
