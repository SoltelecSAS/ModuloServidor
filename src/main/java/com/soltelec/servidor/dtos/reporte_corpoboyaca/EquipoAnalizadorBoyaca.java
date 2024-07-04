package com.soltelec.servidor.dtos.reporte_corpoboyaca;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquipoAnalizadorBoyaca {
    private String vrPef,
    noSerieBanco,
    noSerieAnalizador,
    marcaAnalizador,
    hcBaja,
    coBaja,
    co2Baja,
    hcAlta,
    coAlta,
    co2Alta,
    fechaVerificacion,
    software,
    vSoftware;
}
