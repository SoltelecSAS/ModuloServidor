package com.soltelec.servidor.dtos.reporte_corpocaldas;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InicioPruebaCorpocaldas {
    private String fechaInicio;
    private String fechaFin;
    private String municipio;
    private String direccion;
    private String fur;
    private String nCertificado;
    private String nSerieEquipo;
    private String marcaMedidor;
    private String nombreProveedor;
    private String nombrePrograma;
    private String versionPrograma;
    private String idInspector;
}
