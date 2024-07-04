package com.soltelec.servidor.dtos.reporte_ntc5365;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DatosInspeccionNtc5365 {
    private String
    tipoDocumentoDirector,
    numDocumentoDirector,
    nombreDirector,
    tipoDocumentoInspector,
    numDocumentoInspector,
    nombreInspector,
    numeroFur,
    fechaFur,
    consecutivoFur,
    furAsociado,
    certificadoEmitido,
    fechaInicio,
    fechaFin,
    fechaAborto,
    causalAborto,
    catalizador,
    tempAmbiente,
    humedadRelativa;
}
