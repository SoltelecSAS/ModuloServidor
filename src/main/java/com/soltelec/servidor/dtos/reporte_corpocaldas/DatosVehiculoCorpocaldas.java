package com.soltelec.servidor.dtos.reporte_corpocaldas;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DatosVehiculoCorpocaldas {
    private String placa;
    private String marca;
    private String modelo;
    private String cilindraje;
    private String kilometraje;
    private String linea;
    private String clase;
    private String servicio;
    private String combustible;
    private String tipoMotor;
    private String nTubosEscape;
    private String diseño;
}
