package com.soltelec.servidor.dtos.reporte_corpocaldas;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CausasAbortoCorpocaldas {
    private String fechaAborto;
    private String fallaEquipoMedicion;
    private String fallaSubitaFluido;
    private String bloqueoForzadoEquipo;
    private String ejecucionIncorrecta;
    private String fallaDesviacion;
    private String normaApllicada;
}
