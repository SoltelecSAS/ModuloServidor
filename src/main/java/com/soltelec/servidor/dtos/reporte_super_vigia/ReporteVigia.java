package com.soltelec.servidor.dtos.reporte_super_vigia;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReporteVigia {
    String numeroFormato,
    fecha_prueba,
    aprobada,
    consecutive,
    consecutivo_runt,
    carplate,
    nombreServicio,
    nombreClase,
    nombreMarca,
    linea,
    modelo,
    fechaSoat,
    nombreGasolina,
    tiemposMotor,
    ruido,
    intensidad_luz_der,
    inclinacion_luz_der,
    eficacia_total,
    fuerza_eje_der_1,
    fuerza_eje_der_2,
    peso_eje_der_1,
    peso_eje_der_2,
    diseño,
    temp_motor,
    rpmRalenti,
    hcRalenti,
    coRalenti,
    co2Ralenti,
    o2Ralenti;

}
