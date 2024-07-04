package com.soltelec.servidor.dtos.reporte_corantioquia_cornare;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;


//Estos son todos los datos del reporte corantioquia cuidadosamente organizados
@Data
@AllArgsConstructor
public class CorantioquiaCornare {
    private List<DatosVehiculoCorantioquia> datosVehiculo;
    private List<DatosPruebaCorantioquia> datosPrueba;
    private List<ResultadoPruebaCorantioquia> datosResultadoPruebas;
    private List<EquipoAnalizadorCorantioquia> datosEquipoAnalizadors;
}
