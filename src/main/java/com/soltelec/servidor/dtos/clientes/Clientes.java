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
    tipoDocumento,
    numeroDocumento,
    telefono1,
    telefono2,
    Direccion,
    ciudad,
    email,
    nombreMarca,
    nombreLinea,
    modelo,
    tipoVehiculo,
    claseVehiculo,
    tipoCombustible,
    tipoServicio,
    NumeroChasis,
    numeroCertificado,
    directorTecnico,
    recepcionista,
    fechaSoat,
    esPreventiva;
}
