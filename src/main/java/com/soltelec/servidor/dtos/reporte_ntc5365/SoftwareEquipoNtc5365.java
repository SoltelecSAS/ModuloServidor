package com.soltelec.servidor.dtos.reporte_ntc5365;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SoftwareEquipoNtc5365 {
    private String marcaAnalizador,
    modeloAnalizador,
    serialAnalizador,
    marcaBanco,
    modeloBanco,
    serialBanco,
    pefBanco,
    serialElectronico,
    marcaCaptadorRpm,
    serialCaptadorRpm,
    marcaSensorTempMotor,
    serialSensorTempMotor,
    marcaSensorTempAmbiente,
    serialSensorTempAmbiente,
    marcaSensorhumedad,
    serialSensorHumedad,
    nombreSoftware,
    versionSoftware,
    desarrolladorSoftware,
    tipoIdentificacionInspector,
    numeroIdentificacionInspector,
    nombreInspector,
    fechaFugas,
    fechaGasPatron,
    laboratioAlta,
    cilindroAlta,
    certificadorAlta,
    laboratorioBaja,
    cilindroBaja,
    certificadoBaja,
    valorHcAltaP,
    valorHcAltaH,
    valorHcBajaP,
    valorHcBajaH,
    valorCoAlto,
    valorCoBajo,
    valorCo2Alto,
    valorCo2Bajo,
    valorO2Alto,
    valorO2Bajo,
    resultadoHcAltaP,
    resultadoHcAltaH,
    resultadoHcBajaP,
    resultadoHcBajaH,
    resultadoCoAlto,
    resultadoCoBajo,
    resultadoCo2Alto,
    resultadoCo2Bajo,
    resultadoO2Alto,
    resultadoO2Bajo,
    criterioGasPatron,
    resultadoGasPatron;
}
