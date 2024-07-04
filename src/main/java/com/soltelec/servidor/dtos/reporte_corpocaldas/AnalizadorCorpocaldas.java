package com.soltelec.servidor.dtos.reporte_corpocaldas;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AnalizadorCorpocaldas {
    private String vrPef;
    private String nSerieBanco;
    private String marcaAnalizador;
    private String vrSpanBajoHc;
    private String rVrSpanBajoHc;
    private String vrSpanBajoCo;
    private String rVrSpanBajoCo;
    private String vrSpanBajoCo2;
    private String rVrSpanBajoCo2;
    private String vrSpanAltoHc;
    private String rVrSpanAltoHc;
    private String vrSpanAltoCo;
    private String rVrSpanAltoCo;
    private String vrSpanAltoCo2;
    private String rVrSpanAltoCo2;
    private String fechaVerificacion;
}
