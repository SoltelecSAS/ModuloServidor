package com.soltelec.servidor.dtos.reporte_dagma;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class Dagma {
    private String cda;
    private String registro;
    private String fecha;
    private String nuCertificado;
    private String tipoVehiculo;
    private String tipoServicio;
    private String placa;
    private Integer modelo;
    private String Kilometraje;
    private String tipoCombustible;
    private String coRalenti;
    private String co2Ralenti;
    private String o2Ralenti;
    private String hcRalenti;
    private String coCrucero;
    private String co2Crucero;
    private String o2Crucero;
    private String hcCrucero;
    private String norma;
    private String opacidad;
    private String resultado;
    private String ruido;
    private String cilindrada;
}
