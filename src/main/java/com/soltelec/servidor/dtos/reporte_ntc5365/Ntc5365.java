package com.soltelec.servidor.dtos.reporte_ntc5365;

import java.util.List;

import com.soltelec.servidor.dtos.DatosCda;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Ntc5365 {
    List<DatosCda> datosCda;
    List<PropietarioNtC5365> datosPropietario;
    List<VehiculoNtc5365> datosVehiculo;
    List<SoftwareEquipoNtc5365> datosSoftware;
    List<DatosInspeccionNtc5365> datosInspeccion;
    List<ResultadosInspeccionNtc5365> datosRInspeccion;
}
