package com.soltelec.servidor.dtos.reporte_corantioquia_cornare;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DatosVehiculoCorantioquia {
    private Long numeroCertificado;
    private String numeroConsecutivoPrueba;
    private String marca;
    private Integer anoModelo;
    private String placa;
    private Integer cilindrajeCm3;
    private String tipoMotor;
    private String diseño;
    private String linea;

	@Override
	public String toString() {
		return "DatosVehiculo [numeroCertificado=" + numeroCertificado + ", marca=" + marca + ", anoModelo=" + anoModelo
				+ ", placa=" + placa + ", cilindrajeCm3=" + cilindrajeCm3 + ", tipoMotor=" + tipoMotor + ", linea=" + linea + "]";
	}
}
