package com.soltelec.servidor.dtos.clientes;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Clientes {
    String placa,
    ultimaFechaRevision,
    nombre,
    apellido,
    telefono1,
    telefono2,
    nombreMarca,
    nombreLinea,
    modelo,
    tipoVehiculo,
    claseVehiculo,
    tipoCombustible,
    tipoServicio,
    NumeroChasis;
}
