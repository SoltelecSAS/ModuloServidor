package com.soltelec.servidor.dtos.reporte_corpoboyaca;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PruebasBoyaca {
    private String noPrueba,
    fechaInicio,
    fechaFin,
    fechaAborto,
    inspector,
    temperaturaAmbiente,
    humedadRelativa,
    causaAborto;
}
