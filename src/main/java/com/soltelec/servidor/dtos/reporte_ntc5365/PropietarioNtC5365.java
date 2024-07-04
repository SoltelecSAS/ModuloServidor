package com.soltelec.servidor.dtos.reporte_ntc5365;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropietarioNtC5365 {
    private String tipoDocumento,
    numeroIdentificacion,
    nombrePropietario,
    direccionPropietario,
    telefono1Propietario,
    telefono2Propietario,
    municipioPropietario,
    emailPropietario;
}
