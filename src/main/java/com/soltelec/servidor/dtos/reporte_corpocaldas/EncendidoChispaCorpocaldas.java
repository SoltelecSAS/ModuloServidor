package com.soltelec.servidor.dtos.reporte_corpocaldas;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EncendidoChispaCorpocaldas {
    private String rpmRalenti;
    private String hcRalenti;
    private String coRalenti;
    private String co2Ralenti;
    private String o2Ralenti;
    private String rpmCrucero;
    private String hcCrucero;
    private String coCrucero;
    private String co2Crucero;
    private String o2Crucero;
}
