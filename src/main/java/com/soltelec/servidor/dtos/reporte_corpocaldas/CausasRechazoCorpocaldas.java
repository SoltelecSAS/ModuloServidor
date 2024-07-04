package com.soltelec.servidor.dtos.reporte_corpocaldas;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CausasRechazoCorpocaldas {
    private String presenciaHumo;
    private String presenciaDilucion;
    private String nivelEmisionAplicable;
    private String rpmFueraRango;
    private String fugasTuboEscape;
    private String salidasAdicionales;
    private String ausenciaTapaAceite;
    private String ausenciaTapaCombustible;
    private String ausenciaMalEstadoFiltroAire;
    private String desconexionRecirculacion;
    private String accesoriosDeformacionesTuboEscape;
    private String operacionIncorrectaRefrigeracion;
    private String emisiones;
    private String incorrectaOperacionGobernador;
    private String fallaSubita;
    private String ejecucionIncorrecta;
    private String diferenciaAritmetica;
    private String diferenciaTemp;
    private String activacionDispositivos;
}
