package com.soltelec.servidor.dtos.reporte_corpoboyaca;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropietarioBoyaca {
    private String nombre,
    tipoDocumento,
    noDocumento,
    direccion,
    telefono1,
    telefono2,
    ciudad;
}
