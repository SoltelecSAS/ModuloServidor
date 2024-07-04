package com.soltelec.servidor.dtos.reporte_corpocaldas;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DatosPruebaCorpocaldas {
    private String tempAmbiente;
    private String humedadRelativa;
    private String ltoeOpacidad;
    private String ltoeDensidadHumo;
    private String tempInicialMotor;
    private String tempFinalMotor;
}
