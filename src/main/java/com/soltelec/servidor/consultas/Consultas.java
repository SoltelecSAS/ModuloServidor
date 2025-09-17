package com.soltelec.servidor.consultas;

import com.soltelec.servidor.conexion.Conexion;

public class Consultas {

    private Consultas() {
        throw new IllegalStateException("Utility class");
    }

    @SuppressWarnings("sonar")
    public static String getSoftware(){
        return "SELECT * FROM software WHERE Id_sw=1";
    }

    public static String getDatosCda(){
        return
        "SELECT \n" + //
        "    cda.*,\n" + //
        "    'NIT' AS tipo_documento,\n" + //
        "    u.Nombre_usuario,\n" + //
        "    \n" + //
        "    CASE WHEN EXISTS (\n" + //
        "                SELECT 1\n" + //
        "                FROM information_schema.columns\n" + //
        "                WHERE table_schema = '"+Conexion.getBaseDatos()+"'\n" + //
        "                AND table_name = 'cda'\n" + //
        "                AND column_name = 'departamento'\n" + //
        "             )\n" + //
        "         THEN 1\n" + //
        "         ELSE 0\n" + //
        "    END AS el_departamento_existe,\n" + //
        "    \n" + //
        "    CASE WHEN EXISTS (\n" + //
        "                SELECT 1\n" + //
        "                FROM information_schema.columns\n" + //
        "                WHERE table_schema = '"+Conexion.getBaseDatos()+"'\n" + //
        "                AND table_name = 'cda'\n" + //
        "                AND column_name = 'norma_aplicada'\n" + //
        "             )\n" + //
        "         THEN 1\n" + //
        "         ELSE 0\n" + //
        "    END AS norma_aplicada_existe\n" + //
        "    \n" + //
        "FROM cda\n" + //
        "INNER JOIN usuarios u ON cda.usuario_resp = u.GEUSER\n" + //
        "WHERE id_cda=1";
    }

    public static String getSqlCorantioquia(){
        return 
        "SELECT \n" + //
        "    hp.Aprobado, \n" + //
        "    hp.consecutivo_runt, \n" + //
        "    m.Nombre_marca, \n" + //
        "    v.Modelo, \n" + //
        "    v.CARPLATE,\n" + //
        "    v.Cinlindraje, \n" + //
        "    v.Tiempos_motor, \n" + //
        "    v.diseño, \n" + //
        "    v.Numero_exostos, \n" + //
        "    p.Fecha_prueba, \n" + //
        "    u.Nombre_usuario,\n" + //
        "    p.Comentario_aborto,\n" + //
        "    p.observaciones,\n" + //
        "    p.Aprobada, \n" + //
        "    p.Abortada,\n" + //
        "    d.Nombre_problema,\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8031 THEN m1.Valor_medida END) AS temperatura_ambiente,\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8032 THEN m1.Valor_medida END) AS humedad_relativa,\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8006 AND v.Tiempos_motor = 4 THEN m1.Valor_medida\n" + //
        "             WHEN m1.MEASURETYPE = 8022 AND v.Tiempos_motor = 2 THEN m1.Valor_medida END) AS temperatura_motor,\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8005 AND v.Tiempos_motor = 4 THEN m1.Valor_medida\n" + //
        "             WHEN m1.MEASURETYPE = 8028 AND v.Tiempos_motor = 2 THEN m1.Valor_medida END) AS rpm_ralenti,\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8001 AND v.Tiempos_motor = 4 THEN m1.Valor_medida\n" + //
        "             WHEN m1.MEASURETYPE = 8018 AND v.Tiempos_motor = 2 THEN m1.Valor_medida END) AS hc_ralenti,\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8002 AND v.Tiempos_motor = 4 THEN m1.Valor_medida\n" + //
        "             WHEN m1.MEASURETYPE = 8020 AND v.Tiempos_motor = 2 THEN m1.Valor_medida END) AS co_ralenti,\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8003 AND v.Tiempos_motor = 4 THEN m1.Valor_medida\n" + //
        "             WHEN m1.MEASURETYPE = 8019 AND v.Tiempos_motor = 2 THEN m1.Valor_medida END) AS co2_ralenti,\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8004 AND v.Tiempos_motor = 4 THEN m1.Valor_medida\n" + //
        "             WHEN m1.MEASURETYPE = 8021 AND v.Tiempos_motor = 2 THEN m1.Valor_medida END) AS O2_ralenti,\n" + //
        "    p.Id_Pruebas, \n" + //
        "    e.pef, \n" + //
        "    e.marca, \n" + //
        "    p.serialEquipo, \n" + //
        "    e.serial, \n" + //
        "    e.id_equipo,\n" + //
        "    lv.CRLNAME as linea_vehiculo,\n" + //
        "    p.Fecha_prueba,\n" + //
        "    p.Fecha_final\n" + //
        "FROM \n" + //
        "    pruebas AS p \n" + //
        "    INNER JOIN usuarios AS u ON u.GEUSER = p.Usuario_for\n" + //
        "    INNER JOIN hoja_pruebas AS hp ON p.hoja_pruebas_for = hp.TESTSHEET\n" + //
        "    INNER JOIN vehiculos AS v ON hp.Vehiculo_for = v.CAR\n" + //
        "    INNER JOIN marcas AS m ON m.CARMARK = v.CARMARK\n" + //
        "    LEFT JOIN lineas_vehiculos lv on lv.CARLINE = v.CARLINE \n" + //
        "    LEFT JOIN equipos AS e ON e.serialresolucion = p.serialEquipo\n" + //
        "    LEFT JOIN defxprueba AS dp ON dp.id_prueba =p.Id_Pruebas\n" + //
        "    LEFT JOIN defectos AS d ON d.CARDEFAULT = dp.id_defecto\n" + //
        "    LEFT JOIN medidas AS m1 ON p.Id_Pruebas = m1.TEST\n" + //
        "WHERE \n" + //
        "    p.Tipo_prueba_for = 8\n" + //
        "    AND v.CLASS IN (10)\n" + //
        "    AND p.Fecha_prueba BETWEEN ? AND ?\n" + //
        "    AND hp.preventiva = 'N'\n" + //
        "    AND hp.Finalizada = 'Y' \n" + //
        "    AND hp.estado_sicov = 'SINCRONIZADO'\n" + //
        "GROUP BY \n" + //
        "    p.Id_Pruebas, hp.Aprobado, hp.consecutivo_runt, m.Nombre_marca, v.Modelo, v.CARPLATE,\n" + //
        "    v.Cinlindraje, v.Tiempos_motor, v.diseño, p.Fecha_prueba, u.Nombre_usuario,\n" + //
        "    p.Aprobada, p.Abortada, p.serialEquipo, e.id_equipo\n" + //
        "ORDER BY \n" + //
        "    p.Fecha_prueba;";
    }

    public static String getEquipoCorantioquiaYCorpocaldas(){
        return 
        "SELECT \n" + //
        "    cdp.bm_hc AS span_hc_baja, \n" + //
        "    cdp.bm_co AS span_co_baja, \n" + //
        "    cdp.bm_co2 AS span_co2_baja,\n" + //
        "    cdp.banco_bm_hc AS valor_leido_hc_baja, \n" + //
        "    cdp.banco_bm_co AS valor_leido_co_baja, \n" + //
        "    cdp.banco_bm_co2 AS valor_leido_co2_baja,\n" + //
        "    cdp.alta_hc AS span_hc_alta, \n" + //
        "    cdp.alta_co AS span_co_alta, \n" + //
        "    cdp.alta_co2 AS span_co2_alta,\n" + //
        "    cdp.banco_alta_hc AS valor_leido_hc_alta, \n" + //
        "    cdp.banco_alta_co AS valor_leido_co_alta, \n" + //
        "    cdp.banco_alta_co2 AS valor_leido_co2_alta,\n" + //
        "    c.CURDATE AS fecha_verificacion, \n" + //
        "    c.aprobada AS calibracion_aprobada, \n" + //
        "    c.id_equipo, e.serial as serialElectronico\n" + //
        "FROM \n" + //
        "    calibraciones AS c \n" + //
        "    INNER JOIN calibracion_dos_puntos AS cdp ON cdp.CALIBRATION = c.CALIBRATION\n" + //
        "    INNER JOIN equipos AS e ON e.id_equipo = c.id_equipo\n" + //
        "WHERE \n" + //
        "    c.CURDATE < ?\n" + //
        "    AND e.serialresolucion LIKE ?\n" + //
        "ORDER BY \n" + //
        "    c.CURDATE DESC\n" + //
        "LIMIT 1";
    }

    public static String getDagma(){
        return
        "SELECT \n" + //
        "   (SELECT cda.nombre FROM cda WHERE cda.id_cda = 1) AS Cda,\n" + //
        "   p.Id_Pruebas AS registro, \n" + //
        "   DATE(h.Fecha_ingreso_vehiculo) AS fecha, \n" + //
        "   c.CONSECUTIVE AS certificado, \n" + //
        "   vc.Nombre_clase AS tipo_vehiculo, \n" + //
        "   s.Nombre_servicio AS tipo_servicio, \n" + //
        "   v.CARPLATE AS placa, v.Modelo AS modelo, \n" + //
        "   tg.Nombre_gasolina AS tipo_combustible,\n" + //
        "   p.Aprobada AS resultado_prueba, \n" + //
        "   p.Abortada AS es_abortada, \n" + //
        "   v.Cinlindraje AS cilindrada, \n" + //
        "   v.kilometraje AS kilometraje,\n" + //
        "    \n" + //
        "   MAX(CASE WHEN m1.MEASURETYPE = 8002 THEN m1.Valor_medida END) AS CO_ralenti,\n" + //
        "   MAX(CASE WHEN m1.MEASURETYPE = 8003 THEN m1.Valor_medida END) AS CO2_ralenti,\n" + //
        "   MAX(CASE WHEN m1.MEASURETYPE = 8004 THEN m1.Valor_medida END) AS O2_ralenti,\n" + //
        "   MAX(CASE WHEN m1.MEASURETYPE = 8001 THEN m1.Valor_medida END) AS HC_ralenti,\n" + //
        "   MAX(CASE WHEN m1.MEASURETYPE = 8008 THEN m1.Valor_medida END) AS CO_crucero,\n" + //
        "   MAX(CASE WHEN m1.MEASURETYPE = 8009 THEN m1.Valor_medida END) AS CO2_crucero,\n" + //
        "   MAX(CASE WHEN m1.MEASURETYPE = 8010 THEN m1.Valor_medida END) AS O2_crucero,\n" + //
        "   MAX(CASE WHEN m1.MEASURETYPE = 8007 THEN m1.Valor_medida END) AS HC_crucero,\n" + //
        "   MAX(CASE WHEN m1.MEASURETYPE = 8017 THEN m1.Valor_medida END) AS Opacidad,\n" + //
        "   MAX(CASE WHEN m1.MEASURETYPE = 7003 \n" + //
        "   OR m1.MEASURETYPE = 7004 \n" + //
        "                OR m1.MEASURETYPE = 7005 THEN m1.Valor_medida END) AS ruido\n" + //
        "FROM \n" + //
        "   hoja_pruebas h\n" + //
        "    \n" + //
        "    INNER JOIN certificados c ON c.TESTSHEET = h.TESTSHEET\n" + //
        "    INNER JOIN vehiculos v ON v.CAR = h.Vehiculo_for\n" + //
        "    INNER JOIN clases_vehiculo vc ON vc.CLASS = v.CLASS\n" + //
        "    INNER JOIN servicios s ON s.SERVICE = v.SERVICE\n" + //
        "    INNER JOIN tipos_gasolina tg ON tg.FUELTYPE = v.FUELTYPE\n" + //
        "    LEFT JOIN pruebas p ON h.TESTSHEET = p.hoja_pruebas_for\n" + //
        "    LEFT JOIN medidas AS m1 ON p.Id_Pruebas = m1.TEST\n" + //
        "WHERE \n" + //
        "    DATE(h.Fecha_ingreso_vehiculo) BETWEEN ? AND ?\n" + //
        "    AND h.preventiva = 'N'\n" + //
        "    AND h.Finalizada = 'Y' \n" + //
        "    AND h.estado_sicov = 'SINCRONIZADO'\n" + //
        "    AND p.Tipo_prueba_for IN (8,7)\n" + //
        "GROUP BY \n" + //
        "   c.CERTIFICATE, DATE(h.Fecha_ingreso_vehiculo),\n" + //
        "    c.CONSECUTIVE, vc.Nombre_clase, s.Nombre_servicio, v.CARPLATE,\n" + //
        "    v.Modelo, tg.Nombre_gasolina, v.Cinlindraje, v.kilometraje\n" + //
        "order by p.Fecha_prueba;";
    }

    @SuppressWarnings("sonar")
    public static String getCorpocaldas(){
        return
        "SELECT \n" + //
        "    hp.TESTSHEET,\n" + //
        "    p.Id_Pruebas,\n" + //
        "    \n" + //
        "    p.Fecha_prueba AS Fecha_prueba,\n" + //
        "    p.Fecha_final AS Fecha_final,\n" + //
        "    hp.con_hoja_prueba AS n_fur,\n" + //
        "    (SELECT CONSECUTIVE FROM certificados WHERE TESTSHEET = hp.TESTSHEET LIMIT 1) AS n_certificado,\n" + //
        "    MAX(CASE WHEN e.pef THEN e.marca END) AS marca_equipo,\n" + //
        "    \n" + //
        "    p.usuario_for AS id_inspector,\n" + //
        "    \n" + //
        "    v.CARPLATE AS placa,\n" + //
        "    m.Nombre_marca as marca, \n" + //
        "    v.Modelo as modelo, \n" + //
        "    v.Cinlindraje as cilindraje, \n" + //
        "    v.kilometraje as km,\n" + //
        "    l.CRLNAME,\n" + //
        "    cl.Nombre_clase,\n" + //
        "    sr.Nombre_servicio,\n" + //
        "    tg.Nombre_gasolina,\n" + //
        "    v.Tiempos_motor as tipo_motor, \n" + //
        "    v.Numero_exostos,\n" + //
        "    v.diseño,\n" + //
        "    null AS temperatura_final,\n" + //
        "    \n" + //
        "    \n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8031 THEN m1.Valor_medida END) AS temperatura_ambiente,\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8032 THEN m1.Valor_medida END) AS humedad_relativa,\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8017 THEN m1.Valor_medida END) AS Opacidad,\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 9113 THEN m1.Valor_medida END) AS LTOE,\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8006 AND v.Tiempos_motor = 4 THEN m1.Valor_medida\n" + //
        "             WHEN m1.MEASURETYPE = 8022 AND v.Tiempos_motor = 2 THEN m1.Valor_medida END) AS temperatura_inicial,\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8005 AND v.Tiempos_motor = 4 THEN m1.Valor_medida\n" + //
        "             WHEN m1.MEASURETYPE = 8028 AND v.Tiempos_motor = 2 THEN m1.Valor_medida END) AS rpm_ralenti,\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8001 AND v.Tiempos_motor = 4 THEN m1.Valor_medida\n" + //
        "             WHEN m1.MEASURETYPE = 8018 AND v.Tiempos_motor = 2 THEN m1.Valor_medida END) AS hc_ralenti,\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8002 AND v.Tiempos_motor = 4 THEN m1.Valor_medida\n" + //
        "             WHEN m1.MEASURETYPE = 8020 AND v.Tiempos_motor = 2 THEN m1.Valor_medida END) AS co_ralenti,\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8003 AND v.Tiempos_motor = 4 THEN m1.Valor_medida\n" + //
        "             WHEN m1.MEASURETYPE = 8019 AND v.Tiempos_motor = 2 THEN m1.Valor_medida END) AS co2_ralenti,\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8004 AND v.Tiempos_motor = 4 THEN m1.Valor_medida\n" + //
        "             WHEN m1.MEASURETYPE = 8021 AND v.Tiempos_motor = 2 THEN m1.Valor_medida END) AS O2_ralenti,\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8011 AND v.Tiempos_motor = 4 THEN m1.Valor_medida\n" + //
        "             WHEN m1.MEASURETYPE = 8029 AND v.Tiempos_motor = 2 THEN m1.Valor_medida END) AS rpm_crucero,\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8007 THEN m1.Valor_medida END) AS HC_crucero,\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8008 THEN m1.Valor_medida END) AS CO_crucero,\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8009 THEN m1.Valor_medida END) AS CO2_crucero,\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8010 THEN m1.Valor_medida END) AS O2_crucero,\n" + //
        "    \n" + //
        "    \n" + //
        "    MAX(CASE WHEN LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%humo%' \n" + //
        "        AND (LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%negro%' OR LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%azul%') \n" + //
        "     THEN 1\n" + //
        "     ELSE 0 END) AS presencia_humo_n_a,\n" + //
        "    \n" + //
        "    null AS nivel_emision_aplicable,\n" + //
        "    \n" + //
        "    MAX(CASE WHEN LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%revoluciones%' \n" + //
        "        AND LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%rango%'\n" + //
        "          AND LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%fuera%'\n" + //
        "     THEN 1\n" + //
        "     ELSE 0 END) AS rpm_fuera_rango,\n" + //
        "     \n" + //
        "    MAX(CASE WHEN LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%fuga%' \n" + //
        "        AND LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%tubo%'\n" + //
        "          AND LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%escape%'\n" + //
        "     THEN 1\n" + //
        "     ELSE 0 END) AS fugas_tubo_escape,\n" + //
        "     \n" + //
        "    MAX(CASE WHEN LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%salida%' \n" + //
        "        AND LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%adicional%'\n" + //
        "          AND LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%diseño%'\n" + //
        "     THEN 1\n" + //
        "     ELSE 0 END) AS salidas_adicionales_diseño,\n" + //
        "     \n" + //
        "     MAX(CASE WHEN LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%tapa%' \n" + //
        "        AND LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%aceite%'\n" + //
        "     THEN 1\n" + //
        "     ELSE 0 END) AS ausencia_tapa_aceite,\n" + //
        "     \n" + //
        "     MAX(CASE WHEN LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%ausencia%' \n" + //
        "        AND LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%tapa%'\n" + //
        "          AND LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%combustible%'\n" + //
        "     THEN 1\n" + //
        "     ELSE 0 END) AS ausencia_tapa_combustible,\n" + //
        "     \n" + //
        "     MAX(CASE WHEN LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%filtro%' \n" + //
        "        AND LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%aire%'\n" + //
        "     THEN 1\n" + //
        "     ELSE 0 END) AS ausencia_malEstado_filtro_aire,\n" + //
        "     \n" + //
        "     MAX(CASE WHEN LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%desconexion%' \n" + //
        "        AND LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%recirculacion%'\n" + //
        "     THEN 1\n" + //
        "     ELSE 0 END) AS desconexion_recirculacion,\n" + //
        "     \n" + //
        "     MAX(CASE WHEN (LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%accesorio%' OR LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%deforma%')\n" + //
        "        AND LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%tubo%'\n" + //
        "          AND LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%escape%'\n" + //
        "     THEN 1\n" + //
        "     ELSE 0 END) AS accesorios_deformaciones_tubo_escape,\n" + //
        "     \n" + //
        "     MAX(CASE WHEN LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%operacion%' \n" + //
        "        AND LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%incorrecta%'\n" + //
        "          AND LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%refrigeracion%'\n" + //
        "     THEN 1\n" + //
        "     ELSE 0 END) AS operacion_incorrecta_refrigeracion,\n" + //
        "     \n" + //
        "     0 AS emisiones,\n" + //
        "     0 AS incorrecta_operacion_gobernador,\n" + //
        "     \n" + //
        "     MAX(CASE WHEN LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%falla%' \n" + //
        "        AND LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%subita%'\n" + //
        "     THEN 1\n" + //
        "     ELSE 0 END) AS falla_subita,\n" + //
        "    \n" + //
        "    MAX(CASE WHEN LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%ejecucion%' \n" + //
        "        AND LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%incorrecta%'\n" + //
        "     THEN 1\n" + //
        "     ELSE 0 END) AS ejecucion_incorrecta,\n" + //
        "     \n" + //
        "     MAX(CASE WHEN LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%diferencia%' \n" + //
        "        AND LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%aritmetica%'\n" + //
        "     THEN 1\n" + //
        "     ELSE 0 END) AS diferencia_aritmetica,\n" + //
        "     \n" + //
        "     MAX(CASE WHEN LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%diferencia%' \n" + //
        "        AND LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%temperatura%'\n" + //
        "     THEN 1\n" + //
        "     ELSE 0 END) AS diferencia_temperatura,\n" + //
        "     \n" + //
        "     MAX(CASE WHEN LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%activacion%' \n" + //
        "        AND LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%dispositivo%'\n" + //
        "     THEN 1\n" + //
        "     ELSE 0 END) AS activacion_dispositivos,\n" + //
        "     \n" + //
        "     MAX(CASE WHEN (LOWER(REPLACE(p.Comentario_aborto, ',', '')) LIKE '%falla%' OR LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%falla%')\n" + //
        "        AND (LOWER(REPLACE(p.Comentario_aborto, ',', '')) LIKE '%equipo%' OR LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%equipo%')\n" + //
        "          AND (LOWER(REPLACE(p.Comentario_aborto, ',', '')) LIKE '%medicion%' OR LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%medicion%')\n" + //
        "          AND p.Tipo_prueba_for = 8\n" + //
        "     THEN 1\n" + //
        "     ELSE 0 END) AS falla_equipo_medicion,\n" + //
        "     \n" + //
        "     MAX(CASE WHEN (LOWER(REPLACE(p.Comentario_aborto, ',', '')) LIKE '%falla%' OR LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%falla%')\n" + //
        "        AND (LOWER(REPLACE(p.Comentario_aborto, ',', '')) LIKE '%subita%' OR LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%subita%')\n" + //
        "          AND (LOWER(REPLACE(p.Comentario_aborto, ',', '')) LIKE '%fluido%' OR LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%fluido%')\n" + //
        "          AND p.Tipo_prueba_for = 8\n" + //
        "     THEN 1\n" + //
        "     ELSE 0 END) AS falla_subita_fluido_electrico,\n" + //
        "     \n" + //
        "     MAX(CASE WHEN (LOWER(REPLACE(p.Comentario_aborto, ',', '')) LIKE '%bloqueo%' OR LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%bloqueo%')\n" + //
        "        AND (LOWER(REPLACE(p.Comentario_aborto, ',', '')) LIKE '%forzado%' OR LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%forzado%')\n" + //
        "          AND (LOWER(REPLACE(p.Comentario_aborto, ',', '')) LIKE '%equipo%' OR LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%equipo%')\n" + //
        "          AND p.Tipo_prueba_for = 8\n" + //
        "     THEN 1\n" + //
        "     ELSE 0 END) AS bloqueo_forzado_equipo_medicion,\n" + //
        "     \n" + //
        "     MAX(CASE WHEN (LOWER(REPLACE(p.Comentario_aborto, ',', '')) LIKE '%ejecucion%' OR LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%ejecucion%')\n" + //
        "        AND (LOWER(REPLACE(p.Comentario_aborto, ',', '')) LIKE '%incorrecta%' OR LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%incorrecta%')\n" + //
        "          AND p.Tipo_prueba_for = 8\n" + //
        "     THEN 1\n" + //
        "     ELSE 0 END) AS ejecucion_incorrecta,\n" + //
        "     \n" + //
        "     MAX(CASE WHEN (LOWER(REPLACE(p.Comentario_aborto, ',', '')) LIKE '%desviacion%' OR LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%desviacion%')\n" + //
        "        AND (LOWER(REPLACE(p.Comentario_aborto, ',', '')) LIKE '%cero%' OR LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%cero%')\n" + //
        "          AND p.Tipo_prueba_for = 8\n" + //
        "     THEN 1\n" + //
        "     ELSE 0 END) AS desviacion_cero,\n" + //
        "     \n" + //
        "     MAX(CASE WHEN e.pef THEN e.pef END) AS pef,\n" + //
        "     \n" + //
        "     (SELECT pr.serialEquipo\n" + //
        "\t\t FROM pruebas pr\n" + //
        "\t\t WHERE pr.Tipo_prueba_for = 8 and pr.hoja_pruebas_for = hp.TESTSHEET\n" + //
        "\t\t LIMIT 1) AS serialEquipo,\n" + //
        "\n" + //
        "     \n" + //
        "     MAX(CASE WHEN e.pef THEN e.marca END) AS marca_equipo,\n" + //
        "     MAX(CASE WHEN m1.MEASURETYPE = 7003 \n" + //
        "            OR m1.MEASURETYPE = 7004 \n" + //
        "                OR m1.MEASURETYPE = 7005 THEN m1.Valor_medida END) AS ruido\n" + //
        "FROM \n" + //
        "    hoja_pruebas hp\n" + //
        "    INNER JOIN pruebas AS p ON p.hoja_pruebas_for = hp.TESTSHEET\n" + //
        "    LEFT JOIN usuarios AS u ON u.GEUSER = p.Usuario_for\n" + //
        "    INNER JOIN vehiculos AS v ON hp.Vehiculo_for = v.CAR\n" + //
        "    INNER JOIN marcas AS m ON m.CARMARK = v.CARMARK\n" + //
        "    LEFT JOIN equipos AS e ON e.serialresolucion = p.serialEquipo\n" + //
        "    LEFT JOIN medidas AS m1 ON p.Id_Pruebas = m1.TEST\n" + //
        "    INNER JOIN lineas_vehiculos l ON l.CARLINE = v.CARLINE\n" + //
        "    INNER JOIN clases_vehiculo cl ON cl.CLASS = v.CLASS\n" + //
        "    INNER JOIN servicios sr ON sr.SERVICE = v.SERVICE\n" + //
        "    INNER JOIN tipos_gasolina tg ON tg.FUELTYPE = v.FUELTYPE\n" + //
        "WHERE \n" + //
        "    p.Tipo_prueba_for IN (8,7)\n" + //
        "    AND v.CLASS IN (10)\n" + //
        "    AND p.Fecha_prueba BETWEEN ? AND ?\n" + //
        "    AND hp.preventiva = 'N'\n" + //
        "    AND hp.Finalizada = 'Y' \n" + //
        "    AND hp.estado_sicov = 'SINCRONIZADO'\n" + //
        "GROUP BY \n" + //
        "    hp.TESTSHEET\n" + //
        "ORDER BY \n" + //
        "    p.Fecha_prueba;";
    }

    public static String getNtc5365(){

        return
        "SELECT \n" +
        "p.Id_Pruebas, \n" +
        "pro.Tipo_identificacion AS t_iden_propietario, \n" +
        "pro.CAROWNER, \n" +
        "CONCAT(pro.Nombres, ' ', pro.Apellidos) AS nombre_propietario, \n" +
        "pro.Direccion AS direccion_propietario, \n" +
        "pro.Numero_telefono AS num_propietario, \n" +
        "ci.Ciudad_principal AS ciudad_propietario, \n" +
        "pro.email as email_propietario, \n" +
        "v.CARPLATE AS placav, \n" +
        "v.Modelo AS modelov, \n" +
        "v.Numero_motor as n_motorv, \n" +
        "v.VIN as vinv, \n" +
        "v.Cinlindraje as cilindrajev, \n" +
        "v.Numero_licencia as nlicenciav, \n" +
        "v.kilometraje as kmv, \n" +
        "v.conversion_gnv as gnvv, \n" +
        "v.fecha_vencimiento_gnv as fgnvv, \n" +
        "m.Nombre_marca as marcav, \n" +
        "l.CRLNAME as lineav, \n" +
        "cv.Nombre_clase as clasev, \n" +
        "s.Nombre_servicio as serviciov, \n" +
        "tg.Nombre_gasolina as tcombustiblev, \n" +
        "v.Tiempos_motor as tiemposMotorv, \n" +
        "v.diseño as diseñoMotor, \n" +
        "v.Numero_exostos as tubos_escapev, \n" +
        "e.marca as marca_analizador, \n" +
        "'AGPS' as modelo_analizador, \n" +
        "e.serialresolucion as serial_analizador, \n" +
        "'SENSORS' as marca_banco, \n" +
        "'AMBII' as modelo_banco, \n" +
        "e.pef, \n" +
        "e.serial as serial_electronico, \n" +
        "(SELECT marca FROM equipos WHERE serialresolucion = (SUBSTRING_INDEX(SUBSTRING_INDEX(e.serialresolucion, ';', 2), ';', -1)))as marca_kit_rpm_sensor_temp, \n" +
        "(SELECT serialresolucion FROM equipos WHERE serialresolucion = (SUBSTRING_INDEX(SUBSTRING_INDEX(e.serialresolucion, ';', 2), ';', -1)))as serial_kit_rpm, \n" +
        "(SELECT SUBSTRING_INDEX(SUBSTRING(e.serialresolucion, 1, INSTR(e.serialresolucion, '/') - 1), ';', -1)) AS serial_kit_rpm, \n" +
        "(SELECT SUBSTRING_INDEX(SUBSTRING_INDEX(e.serialresolucion, '/', -1), ';', 1)) AS serial_sensor_temp, \n" +
        "(SELECT marca FROM equipos WHERE serialresolucion = (SUBSTRING_INDEX(SUBSTRING_INDEX(e.serialresolucion, ';', 3), ';', -1)))as marca_termohigrometro, \n" +
        "(SELECT serialresolucion FROM equipos WHERE serialresolucion = (SUBSTRING_INDEX(SUBSTRING_INDEX(e.serialresolucion, ';', 3), ';', -1)))as serial_termohigrometro, \n" +
        "'CC' as tipo_identificacion, \n" +
        "cedula_calibrador, \n" +
        "usuario_calibrador, \n" +
        "p.Fecha_prueba, \n" +
        "(SELECT max(c.CURDATE) \n" +
        "FROM \n" +
        "calibraciones AS c \n" +
        "WHERE \n" +
        "c.CURDATE < p.Fecha_prueba) AS verificacion_fugas, \n" +
        "(SELECT max(c.CURDATE) \n" +
        "FROM \n" +
        "calibraciones AS c \n" +
        "INNER JOIN calibracion_dos_puntos AS cdp ON cdp.CALIBRATION = c.CALIBRATION \n" +
        "INNER JOIN equipos AS e ON e.id_equipo = c.id_equipo \n" +
        "WHERE \n" +
        "c.CURDATE < hp.Fecha_ingreso_vehiculo \n" +
        "AND e.serialresolucion LIKE CONCAT('%', (SELECT SUBSTRING_INDEX(SUBSTRING(e.serialresolucion, 1, INSTR(e.serialresolucion, '/') - 1), ';', 1)), '%') \n" +
        ") AS verificacion_gases, \n" +
        "laboratorio_alta, \n" +
        "cilindro_alta, \n" +
        "certificado_alta, \n" +
        "laboratorio_baja, \n" +
        "cilindro_baja, \n" +
        "certificado_baja, \n" +
        "(alta_hc/(e.pef/1000)) as alta_hc_p, \n" +
        "alta_hc, \n" +
        "(bm_hc/(e.pef/1000)) as bm_hc_p, \n" +
        "bm_hc, \n" +
        "alta_co, \n" +
        "bm_co, \n" +
        "alta_co2, \n" +
        "bm_co2, \n" +
        "(banco_alta_hc/(e.pef/1000)) as banco_alta_hc_p, \n" +
        "banco_alta_hc, \n" +
        "(banco_bm_hc/(e.pef/1000)) as banco_bm_hc_p, \n" +
        "banco_bm_hc, \n" +
        "banco_alta_co, \n" +
        "banco_bm_co, \n" +
        "banco_alta_co2, \n" +
        "banco_bm_co2, \n" +
        "banco_alta_o2, \n" +
        "banco_bm_o2, \n" +
        "calibracion_aprobada, \n" +
        "ur.cedula AS cedula_director, \n" +
        "ur.Nombre_usuario AS nombre_director, \n" +
        "u.cedula AS cedula_inspector, \n" +
        "u.Nombre_usuario AS nombre_inspector, \n" +
        "hp.con_hoja_prueba as numero_fur, \n" +
        "hp.Fecha_ingreso_vehiculo as fecha_fur, \n" +
        "hp.consecutivo_runt, \n" +
        "c.CONSECUTIVE AS certificado_emitido, \n" +
        "p.Fecha_prueba, \n" +
        "p.Fecha_final, \n" +
        "p.Fecha_aborto, \n" +
        "p.Comentario_aborto, \n" +
        "v.catalizador, \n" +
        "MAX(CASE WHEN m1.MEASURETYPE = 8031 THEN m1.Valor_medida END) AS temperatura_ambiente, \n" +
        "MAX(CASE WHEN m1.MEASURETYPE = 8032 THEN m1.Valor_medida END) AS humedad_relativa, \n" +
        "MAX(CASE WHEN m1.MEASURETYPE = 8006 AND v.Tiempos_motor = 4 THEN m1.Valor_medida \n" +
        "         WHEN m1.MEASURETYPE = 8022 AND v.Tiempos_motor = 2 THEN m1.Valor_medida END) AS temperatura_motor, \n" +
        "MAX(CASE WHEN m1.MEASURETYPE = 8005 AND v.Tiempos_motor = 4 THEN m1.Valor_medida \n" +
        "         WHEN m1.MEASURETYPE = 8028 AND v.Tiempos_motor = 2 THEN m1.Valor_medida END) AS rpm_ralenti, \n" +
        "MAX(CASE WHEN LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%humo%' \n" +
        "      AND (LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%negro%' OR LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%azul%') \n" +
        " THEN 1 \n" +
        " ELSE 0 END) AS presencia_humo_n_a, \n" +
        "MAX(CASE WHEN LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%revoluciones%' \n" +
        "      AND LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%rango%' \n" +
        "      AND LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%fuera%' \n" +
        " THEN 1 \n" +
        " ELSE 0 END) AS rpm_fuera_rango, \n" +
        "MAX(CASE WHEN LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%fuga%' \n" +
        "      AND LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%tubo%' \n" +
        " THEN 1 \n" +
        " ELSE 0 END) AS fugas_tubo_escape, \n" +
        "MAX(CASE WHEN LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%salida%' \n" +
        "      AND LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%adicional%' \n" +
        " THEN 1 \n" +
        " ELSE 0 END) AS salidas_adicionales_diseño, \n" +
        "MAX(CASE WHEN LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%tapa%' \n" +
        "      AND LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%aceite%' \n" +
        " THEN 1 \n" +
        " ELSE 0 END) AS ausencia_tapa_aceite, \n" +
        "MAX(CASE WHEN LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%ausencia%' \n" +
        "      AND LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%tapa%' \n" +
        "      AND LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%combustible%' \n" +
        " THEN 1 \n" +
        " ELSE 0 END) AS ausencia_tapa_combustible, \n" +
        "MAX(CASE WHEN m1.MEASURETYPE = 8001 AND v.Tiempos_motor = 4 THEN m1.Valor_medida \n" +
        "         WHEN m1.MEASURETYPE = 8018 AND v.Tiempos_motor = 2 THEN m1.Valor_medida END) AS hc_ralenti, \n" +
        "MAX(CASE WHEN m1.MEASURETYPE = 8002 AND v.Tiempos_motor = 4 THEN m1.Valor_medida \n" +
        "         WHEN m1.MEASURETYPE = 8020 AND v.Tiempos_motor = 2 THEN m1.Valor_medida END) AS co_ralenti, \n" +
        "MAX(CASE WHEN m1.MEASURETYPE = 8003 AND v.Tiempos_motor = 4 THEN m1.Valor_medida \n" +
        "         WHEN m1.MEASURETYPE = 8019 AND v.Tiempos_motor = 2 THEN m1.Valor_medida END) AS co2_ralenti, \n" +
        "MAX(CASE WHEN m1.MEASURETYPE = 8004 AND v.Tiempos_motor = 4 THEN m1.Valor_medida \n" +
        "         WHEN m1.MEASURETYPE = 8021 AND v.Tiempos_motor = 2 THEN m1.Valor_medida END) AS O2_ralenti, \n" +
        "p.Aprobada as es_prueba_aprobada, \n" +
        "p.Abortada as es_prueba_abortada \n" +
        "FROM \n" +
        "pruebas AS p \n" +
        "INNER JOIN usuarios AS u ON u.GEUSER = p.Usuario_for \n" +
        "INNER JOIN hoja_pruebas AS hp ON p.hoja_pruebas_for = hp.TESTSHEET \n" +
        "INNER JOIN usuarios AS ur ON ur.GEUSER = hp.usuario_resp \n" +
        "INNER JOIN vehiculos AS v ON hp.Vehiculo_for = v.CAR \n" +
        "INNER JOIN lineas_vehiculos as l ON v.CARLINE = l.CARLINE \n" +
        "INNER JOIN clases_vehiculo as cv on cv.CLASS = v.CLASS \n" +
        "INNER JOIN servicios as s on s.SERVICE = v.SERVICE \n" +
        "INNER JOIN propietarios AS pro ON (v.CAROWNER = pro.CAROWNER) \n" +
        "INNER JOIN tipos_gasolina as tg on tg.FUELTYPE = v.FUELTYPE \n" +
        "INNER JOIN ciudades AS ci ON (ci.CITY = pro.CITY) \n" +
        "INNER JOIN marcas AS m ON m.CARMARK = v.CARMARK \n" +
        "INNER JOIN equipos AS e ON e.serialresolucion = p.serialEquipo \n" +
        "INNER JOIN certificados as c on hp.TESTSHEET = c.TESTSHEET \n" +
        "LEFT JOIN medidas AS m1 ON p.Id_Pruebas = m1.TEST \n" +
        "LEFT JOIN ( \n" +
        "    SELECT \n" +
        "        c.CALIBRATION, \n" +
        "        laboratorio_pipeta_alta AS laboratorio_alta, \n" +
        "        cilindro_pipeta_alta AS cilindro_alta, \n" +
        "        certificado_pipeta_alta AS certificado_alta, \n" +
        "        laboratorio_pipeta_baja AS laboratorio_baja, \n" +
        "        cilindro_pipeta_baja AS cilindro_baja, \n" +
        "        certificado_pipeta_baja AS certificado_baja, \n" +
        "        alta_hc, \n" +
        "        bm_hc, \n" +
        "        alta_co, \n" +
        "        bm_co, \n" +
        "        alta_co2, \n" +
        "        bm_co2, \n" +
        "        banco_alta_hc, \n" +
        "        banco_bm_hc, \n" +
        "        banco_alta_co, \n" +
        "        banco_bm_co, \n" +
        "        banco_alta_co2, \n" +
        "        banco_bm_co2, \n" +
        "        banco_alta_o2, \n" +
        "        banco_bm_o2, \n" +
        "        c.aprobada as calibracion_aprobada, \n" +
        "        u.cedula AS cedula_calibrador, \n" +
        "        u.Nombre_usuario AS usuario_calibrador \n" +
        "    FROM \n" +
        "        calibraciones AS c \n" +
        "        INNER JOIN calibracion_dos_puntos AS cdp ON cdp.CALIBRATION = c.CALIBRATION \n" +
        "        INNER JOIN equipos AS e ON e.id_equipo = c.id_equipo \n" +
        "        INNER JOIN hoja_pruebas AS hp ON c.CURDATE < hp.Fecha_ingreso_vehiculo \n" +
        "        INNER JOIN usuarios AS u ON u.GEUSER = c.GEUSER \n" +
        "    WHERE \n" +
        "        e.serialresolucion LIKE CONCAT('%', (SELECT SUBSTRING_INDEX(SUBSTRING(e.serialresolucion, 1, INSTR(e.serialresolucion, '/') - 1), ';', 1)), '%') \n" +
        "    ORDER BY \n" +
        "        c.CURDATE DESC \n" +
        "    LIMIT 1 \n" +
        ") AS alta ON 1 = 1 \n" +
        "WHERE \n" +
        "    p.Tipo_prueba_for = 8 \n" +
        "    AND v.CLASS IN (10) \n" +
        "    AND p.Fecha_prueba BETWEEN ? AND ? \n" +
        "GROUP BY \n" +
        "    p.Id_Pruebas, hp.Aprobado, hp.consecutivo_runt, m.Nombre_marca, v.Modelo, v.CARPLATE, \n" +
        "    v.Cinlindraje, v.Tiempos_motor, v.diseño, p.Fecha_prueba, u.Nombre_usuario, \n" +
        "    p.Aprobada, p.Abortada, p.serialEquipo, e.id_equipo \n" +
        "ORDER BY \n" +
        "    p.Fecha_prueba desc;";
    }

    public static String getCorpoboyaca(String tipoVehiculoQuery){
        return 
        "SELECT \n" + //
        "\n" + //
        "\t-- Datos Equipo Analizador\n" + //
        "\te.pef,\n" + //
        "    e.serialresolucion as serial_analizador,\n" + //
        "    p.serialEquipo as serial_nuevo,\n" + //
        "    e.marca as marca_analizador,\n" + //
        "    bm_hc,\n" + //
        "    bm_co,\n" + //
        "    bm_co2,\n" + //
        "    alta_hc,\n" + //
        "    alta_co,\n" + //
        "    alta_co2,\n" + //
        "    \n" + //
        "    (SELECT max(c.CURDATE)\n" + //
        "\t\tFROM \n" + //
        "\t\tcalibraciones AS c \n" + //
        "\t\tINNER JOIN calibracion_dos_puntos AS cdp ON cdp.CALIBRATION = c.CALIBRATION\n" + //
        "\t\tINNER JOIN equipos AS e ON e.id_equipo = c.id_equipo\n" + //
        "\tWHERE \n" + //
        "\t\tc.CURDATE < hp.Fecha_ingreso_vehiculo\n" + //
        "        AND e.serialresolucion LIKE CONCAT('%', (SELECT SUBSTRING_INDEX(SUBSTRING(e.serialresolucion, 1, INSTR(e.serialresolucion, '/') - 1), ';', 1)), '%')\n" + //
        "\t) AS verificacion_gases, \n" + //
        "    \n" + //
        "    -- Datos prueba\n" + //
        "    p.Id_Pruebas as consecutivo_prueba,\n" + //
        "    p.Fecha_prueba as fecha_inicio_prueba,\n" + //
        "    p.Fecha_final as fecha_fin_prueba,\n" + //
        "    p.Fecha_aborto as fecha_aborto_prueba,\n" + //
        "    u.Nombre_usuario AS nombre_inspector,\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8031 THEN m1.Valor_medida END) AS temperatura_ambiente,\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8032 THEN m1.Valor_medida END) AS humedad_relativa,\n" + //
        "    p.Comentario_aborto AS causal_aborto_prueba,\n" + //
        "    \n" + //
        "    -- Datos del propietario\n" + //
        "    CONCAT(pro.Nombres, ' ', pro.Apellidos) AS nombre_propietario,\n" + //
        "    pro.Tipo_identificacion AS t_iden_propietario,\n" + //
        "    pro.CAROWNER AS no_documento,\n" + //
        "    pro.Direccion AS direccion_propietario,\n" + //
        "    pro.Numero_telefono AS num_propietario,\n" + //
        "    ci.Ciudad_principal AS ciudad_propietario,\n" + //
        "    \n" + //
        "    -- Datos vehiculos\n" + //
        "    m.Nombre_marca as marca_vehiculo,\n" + //
        "    l.CRLNAME as linea_vehiculo,\n" + //
        "    v.Modelo AS modelo_vehiculo,\n" + //
        "    v.CARPLATE AS placa_vehiculo,\n" + //
        "    v.Cinlindraje as cilindraje_vehiculo,\n" + //
        "    cv.Nombre_clase as clase_vehiculo,\n" + //
        "    s.Nombre_servicio as servicio_vehiculo,\n" + //
        "    tg.Nombre_gasolina as t_combustible_vehiculo,\n" + //
        "    v.Numero_motor as n_motor_vehiculo,\n" + //
        "    v.VIN as vin_vehiculo,\n" + //
        "    v.Numero_licencia as n_licencia_vehiculo,\n" + //
        "    v.kilometraje as km_vehiculo,\n" + //
        "    v.Tiempos_motor as tiemposMotor_vehiculo,\n" + //
        "\tv.diseño as diseño_motor,\n" + //
        "    \n" + //
        "    -- Resultados prueba\n" + //
        "    MAX(CASE WHEN LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%fuga%' \n" + //
        "\t\t  AND LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%tubo%'\n" + //
        "\t THEN 1\n" + //
        "\t ELSE 0 END) AS fugas_tubo_escape,\n" + //
        "     \n" + //
        "     MAX(CASE WHEN LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%fuga%' \n" + //
        "\t\t  AND LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%silenciador%'\n" + //
        "\t THEN 1\n" + //
        "\t ELSE 0 END) AS fugas_silenciador,\n" + //
        "     \n" + //
        "     MAX(CASE WHEN LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%ausencia%' \n" + //
        "\t\t  AND LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%tapa%'\n" + //
        "          AND LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%combustible%'\n" + //
        "\t THEN 1\n" + //
        "\t ELSE 0 END) AS ausencia_tapa_combustible,\n" + //
        "     \n" + //
        "     MAX(CASE WHEN LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%ausencia%' \n" + //
        "\t\t  AND LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%tapa%'\n" + //
        "          AND LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%aceite%'\n" + //
        "\t THEN 1\n" + //
        "\t ELSE 0 END) AS ausencia_tapa_aceite,\n" + //
        "     \n" + //
        "     MAX(CASE WHEN LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%sistema%' \n" + //
        "\t\t  AND LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%control%'\n" + //
        "          AND LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%motor%'\n" + //
        "\t THEN 1\n" + //
        "\t ELSE 0 END) AS sistema_control_motor,\n" + //
        "     \n" + //
        "     MAX(CASE WHEN LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%dispositivo%' \n" + //
        "\t\t  AND LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%altera%'\n" + //
        "          AND LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%rpm%'\n" + //
        "\t THEN 1\n" + //
        "\t ELSE 0 END) AS dispositivo_altera_rpm,\n" + //
        "     \n" + //
        "     MAX(CASE WHEN LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%velocidad%' \n" + //
        "\t\t  AND LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%5%'\n" + //
        "          AND LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%segundo%'\n" + //
        "\t THEN 1\n" + //
        "\t ELSE 0 END) AS velocidad_5_segundos,\n" + //
        "     \n" + //
        "     MAX(CASE WHEN LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%sistema%' \n" + //
        "\t\t  AND LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%aire%'\n" + //
        "\t THEN 1\n" + //
        "\t ELSE 0 END) AS sistema_aire,\n" + //
        "     \n" + //
        "     MAX(CASE WHEN LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%filtro%' \n" + //
        "\t\t  AND LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%aire%'\n" + //
        "\t THEN 1\n" + //
        "\t ELSE 0 END) AS filtro_aire,\n" + //
        "     \n" + //
        "     \n" + //
        "     \n" + //
        "     MAX(CASE WHEN LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%mal%' \n" + //
        "\t\t  AND LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%funcionamiento%'\n" + //
        "\t THEN 1\n" + //
        "\t ELSE 0 END) AS mal_funcionamiento,\n" + //
        "     \n" + //
        "     MAX(CASE WHEN LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%salida%' \n" + //
        "\t\t  AND LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%adicional%'\n" + //
        "\t THEN 1\n" + //
        "\t ELSE 0 END) AS salidas_adicionales_diseño,\n" + //
        "     \n" + //
        "     MAX(CASE WHEN LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%humo%' \n" + //
        "\t\t  AND (LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%negro%' OR LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%azul%') \n" + //
        "\t THEN 1\n" + //
        "\t ELSE 0 END) AS presencia_humo_n_a,\n" + //
        "     \n" + //
        "     MAX(CASE WHEN LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%revoluciones%' \n" + //
        "\t\t  AND LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%rango%'\n" + //
        "          AND LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%fuera%'\n" + //
        "\t THEN 1\n" + //
        "\t ELSE 0 END) AS rpm_fuera_rango,\n" + //
        "     \n" + //
        "     MAX(CASE WHEN LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%falla%' \n" + //
        "\t\t  AND LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%sistema%'\n" + //
        "          AND LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%refrigeraci%'\n" + //
        "\t THEN 1\n" + //
        "\t ELSE 0 END) AS falla_sistema_refrigeracion,\n" + //
        "     \n" + //
        "     MAX(CASE WHEN LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%modifica%' \n" + //
        "\t\t  AND LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%motor%'\n" + //
        "\t THEN 1\n" + //
        "\t ELSE 0 END) AS modificacion_motor,\n" + //
        "     \n" + //
        "     MAX(CASE WHEN LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%falla%' \n" + //
        "\t\t  AND LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%motor%'\n" + //
        "\t THEN 1\n" + //
        "\t ELSE 0 END) AS falla_motor,\n" + //
        "     \n" + //
        "     MAX(CASE WHEN LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%inestabilidad%' \n" + //
        "\t\t  AND LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%ciclo%'\n" + //
        "\t THEN 1\n" + //
        "\t ELSE 0 END) AS inestabilidad_ciclos,\n" + //
        "     \n" + //
        "     MAX(CASE WHEN LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%pcv%' \n" + //
        "\t THEN 1\n" + //
        "\t ELSE 0 END) AS pcv,\n" + //
        "     \n" + //
        "MAX(CASE WHEN m1.MEASURETYPE = 8006 AND v.Tiempos_motor = 4 THEN m1.Valor_medida\n" + //
        "             WHEN m1.MEASURETYPE = 8022 AND v.Tiempos_motor = 2 THEN m1.Valor_medida \n" + //
        "             WHEN m1.MEASURETYPE =8034 THEN m1.Valor_medida END) AS temperatura_motor,\n" + //
        "             \n" + //
        "\tMAX(CASE WHEN m1.MEASURETYPE = 8037 THEN m1.Valor_medida END) AS temperatura_final_motor,\n" + //
        "             \n" + //
        "\t MAX(CASE WHEN m1.MEASURETYPE = 8005 AND v.Tiempos_motor = 4 THEN m1.Valor_medida\n" + //
        "             WHEN m1.MEASURETYPE = 8028 AND v.Tiempos_motor = 2 THEN m1.Valor_medida \n" + //
        "             WHEN m1.MEASURETYPE IN(8005, 8028) AND v.Tiempos_motor NOT IN (2, 4) THEN m1.Valor_medida END) AS rpm_ralenti,\n" + //
        "             \n" + //
        "\t MAX(CASE WHEN m1.MEASURETYPE = 8001 AND v.Tiempos_motor = 4 THEN m1.Valor_medida\n" + //
        "             WHEN m1.MEASURETYPE = 8018 AND v.Tiempos_motor = 2 THEN m1.Valor_medida\n" + //
        "             WHEN m1.MEASURETYPE = 8001 AND v.Tiempos_motor NOT IN (2, 4) THEN m1.Valor_medida END) AS hc_ralenti,\n" + //
        "             \n" + //
        "     MAX(CASE WHEN m1.MEASURETYPE = 8002 AND v.Tiempos_motor = 4 THEN m1.Valor_medida\n" + //
        "             WHEN m1.MEASURETYPE = 8020 AND v.Tiempos_motor = 2 THEN m1.Valor_medida \n" + //
        "             WHEN m1.MEASURETYPE = 8002 AND v.Tiempos_motor NOT IN (2, 4) THEN m1.Valor_medida END) AS co_ralenti,\n" + //
        "             \n" + //
        "     MAX(CASE WHEN m1.MEASURETYPE = 8003 AND v.Tiempos_motor = 4 THEN m1.Valor_medida\n" + //
        "             WHEN m1.MEASURETYPE = 8019 AND v.Tiempos_motor = 2 THEN m1.Valor_medida \n" + //
        "             WHEN m1.MEASURETYPE = 8003 AND v.Tiempos_motor NOT IN (2, 4) THEN m1.Valor_medida END) AS co2_ralenti,\n" + //
        "             \n" + //
        "     MAX(CASE WHEN m1.MEASURETYPE = 8004 AND v.Tiempos_motor = 4 THEN m1.Valor_medida\n" + //
        "             WHEN m1.MEASURETYPE = 8021 AND v.Tiempos_motor = 2 THEN m1.Valor_medida \n" + //
        "             WHEN m1.MEASURETYPE = 8004 AND v.Tiempos_motor NOT IN (2, 4) THEN m1.Valor_medida END) AS O2_ralenti,\n" + //
        "    \n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8011 THEN m1.Valor_medida END) AS rpm_crucero,\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8007 THEN m1.Valor_medida END) AS hc_crucero,\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8008 THEN m1.Valor_medida END) AS co_crucero,\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8009 THEN m1.Valor_medida END) AS co2_crucero,\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8010 THEN m1.Valor_medida END) AS o2_crucero,\n" + //
        "    \n" + //
        "\tMAX(CASE WHEN m1.MEASURETYPE = 8035 THEN m1.Valor_medida END) AS rpm_ralenti_pre,\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8038 THEN m1.Valor_medida END) AS rpm_gobernada_pre,\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8033 THEN m1.Valor_medida END) AS opacidad_preliminar,\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8035 THEN m1.Valor_medida END) AS rpm_ralenti_c1,\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8039 THEN m1.Valor_medida END) AS rpm_gobernada_c1,\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8013 THEN m1.Valor_medida END) AS opacidad_c1,\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8035 THEN m1.Valor_medida END) AS rpm_ralenti_c2,\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8040 THEN m1.Valor_medida END) AS rpm_gobernada_c2,\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8014 THEN m1.Valor_medida END) AS opacidad_c2,\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8035 THEN m1.Valor_medida END) AS rpm_ralenti_c3,\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8041 THEN m1.Valor_medida END) AS rpm_gobernada_c3,\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8015 THEN m1.Valor_medida END) AS opacidad_c3,\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8017 THEN m1.Valor_medida END) AS resultado_final_opacidad,\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8017 THEN m1.Valor_medida END) AS resultado_final,\n" + //
        "    \n" + //
        "    e.ltoe,\n" + //
        "    p.Aprobada as es_prueba_aprobada,\n" + //
        "    v.Numero_exostos,\n" + //
        "    c.CONSECUTIVE,\n" + //
        "    hp.con_hoja_prueba,\n" + //
        "    u.cedula,\n" + //
        "    hp.forma_med_temp,\n" + //
        "   \n" + //
        "    (SELECT \n" + //
        "\t\tMAX(m.Valor_medida) \n" + //
        "\tFROM hoja_pruebas hp1 \n" + //
        "    INNER JOIN pruebas p on p.hoja_pruebas_for = hp1.TESTSHEET\n" + //
        "    INNER JOIN medidas m on m.TEST = p.Id_Pruebas\n" + //
        "    WHERE (m.MEASURETYPE = 7003 \n" + //
        "\t\tOR m.MEASURETYPE = 7004 \n" + //
        "\t\tOR m.MEASURETYPE = 7005) \n" + //
        "        AND hp1.TESTSHEET = hp.TESTSHEET\n" + //
        "    ) AS ruido\n" + //
        "    \n" + //
        "FROM \n" + //
        "    pruebas AS p \n" + //
        "    INNER JOIN usuarios AS u ON u.GEUSER = p.Usuario_for\n" + //
        "    INNER JOIN hoja_pruebas AS hp ON p.hoja_pruebas_for = hp.TESTSHEET\n" + //
        "    LEFT JOIN usuarios AS ur ON ur.GEUSER = hp.usuario_resp\n" + //
        "    INNER JOIN vehiculos AS v ON hp.Vehiculo_for = v.CAR\n" + //
        "    LEFT JOIN lineas_vehiculos as l ON v.CARLINE = l.CARLINE\n" + //
        "    LEFT JOIN clases_vehiculo as cv on cv.CLASS = v.CLASS\n" + //
        "    LEFT JOIN servicios as s on s.SERVICE = v.SERVICE\n" + //
        "    LEFT JOIN propietarios AS pro ON (v.CAROWNER = pro.CAROWNER)\n" + //
        "    LEFT JOIN tipos_gasolina as tg on tg.FUELTYPE = v.FUELTYPE\n" + //
        "    LEFT JOIN ciudades AS ci ON (ci.CITY = pro.CITY)\n" + //
        "    LEFT JOIN marcas AS m ON m.CARMARK = v.CARMARK\n" + //
        "    LEFT JOIN equipos AS e ON e.serialresolucion = p.serialEquipo\n" + //
        "    LEFT JOIN certificados as c on hp.TESTSHEET = c.TESTSHEET\n" + //
        "    LEFT JOIN medidas AS m1 ON p.Id_Pruebas = m1.TEST\n" + //
        "    LEFT JOIN (\n" + //
        "        SELECT \n" + //
        "            c.CALIBRATION,\n" + //
        "            laboratorio_pipeta_alta AS laboratorio_alta,\n" + //
        "            cilindro_pipeta_alta AS cilindro_alta,\n" + //
        "            certificado_pipeta_alta AS certificado_alta,\n" + //
        "            laboratorio_pipeta_baja AS laboratorio_baja,\n" + //
        "            cilindro_pipeta_baja AS cilindro_baja,\n" + //
        "            certificado_pipeta_baja AS certificado_baja,\n" + //
        "            alta_hc,\n" + //
        "            bm_hc,\n" + //
        "\t\t\talta_co,\n" + //
        "\t\t\tbm_co,\n" + //
        "\t\t\talta_co2,\n" + //
        "\t\t\tbm_co2,\n" + //
        "            banco_alta_hc,\n" + //
        "            banco_bm_hc,\n" + //
        "            banco_alta_co,\n" + //
        "            banco_bm_co,\n" + //
        "            banco_alta_co2,\n" + //
        "            banco_bm_co2,\n" + //
        "            banco_alta_o2,\n" + //
        "            banco_bm_o2,\n" + //
        "            c.aprobada as calibracion_aprobada,\n" + //
        "            u.cedula AS cedula_calibrador,\n" + //
        "\t\t\tu.Nombre_usuario AS usuario_calibrador\n" + //
        "        FROM \n" + //
        "            calibraciones AS c \n" + //
        "            INNER JOIN calibracion_dos_puntos AS cdp ON cdp.CALIBRATION = c.CALIBRATION\n" + //
        "            INNER JOIN equipos AS e ON e.id_equipo = c.id_equipo\n" + //
        "            INNER JOIN hoja_pruebas AS hp ON c.CURDATE < hp.Fecha_ingreso_vehiculo\n" + //
        "            INNER JOIN usuarios AS u ON u.GEUSER = c.GEUSER\n" + //
        "        WHERE \n" + //
        "            e.serialresolucion LIKE CONCAT('%', (SELECT SUBSTRING_INDEX(SUBSTRING(e.serialresolucion, 1, INSTR(e.serialresolucion, '/') - 1), ';', 1)), '%')\n" + //
        "            AND c.CURDATE BETWEEN ? AND ?\n" + //
        "        ORDER BY \n" + //
        "            c.CURDATE DESC\n" + //
        "        LIMIT 1\n" + //
        "    ) AS alta ON 1 = 1\n" + //
        "WHERE \n" + //
        "    p.Tipo_prueba_for IN (8)\n" + //
        tipoVehiculoQuery + "\n" + //
        "    AND p.Fecha_prueba BETWEEN ? AND ?\n" + //
        "GROUP BY \n" + //
        "    hp.TESTSHEET\n" + //
        "ORDER BY \n" + //
        "    p.Fecha_prueba asc;";
    }

    public static String getNtc(String tipo){

        return
        "SELECT \n" + //
        "\n" + //
        "\t-- Datos propietario\n" + //
        "    pro.Tipo_identificacion AS TIP_IDE_PROP,\n" + //
        "    pro.CAROWNER AS NUM_IDE_PROP,\n" + //
        "    CONCAT(pro.Nombres, ' ', pro.Apellidos) AS NOM_PROP,\n" + //
        "    pro.Direccion AS DIR_PROP,\n" + //
        "    pro.Numero_telefono AS TEL1_PROP,\n" + //
        "\tcy.Nombre_ciudad AS MUN_PROP,\n" + //
        "    pro.email as CORR_E_PROP,\n" + //
        "\t\n" + //
        "    -- Datos vehiculo\n" + //
        "    v.CARPLATE AS PLACA,\n" + //
        "    v.Modelo AS MODELO,\n" + //
        "    v.Numero_motor as NUM_MOTOR,\n" + //
        "    v.VIN,\n" + //
        "    v.Diametro AS DIA_ESCAPE,\n" + //
        "    v.Cinlindraje as CILINDRAJE,\n" + //
        "    v.Numero_licencia as LIC_TRANS,\n" + //
        "    v.kilometraje as KM,\n" + //
        "    v.conversion_gnv as GNV_CONV,\n" + //
        "\thp.fecha_venc_gnv as GNV_CONV_V,\n" + //
        "    m.Nombre_marca as MARCA,\n" + //
        "    l.CRLNAME as LINEA,\n" + //
        "    cv.Nombre_clase as CLASE,\n" + //
        "    s.Nombre_servicio as SERVICIO,\n" + //
        "    tg.Nombre_gasolina as TIP_COMB,\n" + //
        "    v.Tiempos_motor as TIP_MOTOR,\n" + //
        "    'NO' as RPM_FAB,\n" + //
        "    v.Numero_exostos as NUM_ESCAPE,\n" + //
        "    CONCAT('OTTO ',v.Tiempos_motor,'T') as DIS_MOTOR,\n" + //
        "    \n" + //
        "    -- Datos analizador\n" + //
        "     e.marca as MARCA_AG,\n" + //
        "    'AGPSP' as MOD_AG,\n" + //
        "    e.serialresolucion as SERIAL_AG,\n" + //
        "    'SENSORS' as MARCA_BG,\n" + //
        "    'AMBII' as MOD_BG,\n" + //
        "    (SUBSTRING_INDEX(SUBSTRING_INDEX(e.serialresolucion, ';', 1), ';', -1))as SERIAL_BG,\n" + //
        "    e.pef as PEF,\n" + //
        "    e.pef as LTOE,\n" + //
        "    e.serial as SERIAL_E,\n" + //
        "    (SELECT marca FROM equipos WHERE serialresolucion = (SUBSTRING_INDEX(SUBSTRING_INDEX(e.serialresolucion, ';', 2), ';', -1)))as MARCA_RPM,\n" + //
        "    (SELECT serialresolucion FROM equipos WHERE serialresolucion = (SUBSTRING_INDEX(SUBSTRING_INDEX(e.serialresolucion, ';', 2), ';', -1)))as SERIAL_RPM,\n" + //
        "    (SELECT SUBSTRING_INDEX(SUBSTRING_INDEX(e.serialresolucion, '/', -1), ';', 1)) AS SERIAL_TEMP_M,\n" + //
        "    (SELECT marca FROM equipos WHERE serialresolucion = (SUBSTRING_INDEX(SUBSTRING_INDEX(e.serialresolucion, ';', 3), ';', -1)))as marca_termohigrometro,\n" + //
        "    (SELECT serialresolucion FROM equipos WHERE serialresolucion = (SUBSTRING_INDEX(SUBSTRING_INDEX(e.serialresolucion, ';', 3), ';', -1)))as serial_termohigrometro,\n" + //
        "    'C' as TIP_IDE_VGP,\n" + //
        "    \n" + //
        "    (SELECT u.cedula\n" + //
        "\t\tFROM \n" + //
        "\t\tcalibraciones AS c \n" + //
        "\t\tINNER JOIN calibracion_dos_puntos AS cdp ON cdp.CALIBRATION = c.CALIBRATION\n" + //
        "\t\tINNER JOIN equipos AS e ON e.id_equipo = c.id_equipo\n" + //
        "        INNER JOIN usuarios u ON u.GEUSER = c.GEUSER\n" + //
        "\tWHERE \n" + //
        "\t\tc.CURDATE < hp.Fecha_ingreso_vehiculo\n" + //
        "        AND e.serialresolucion LIKE CONCAT('%', (SELECT SUBSTRING_INDEX(SUBSTRING(e.serialresolucion, 1, INSTR(e.serialresolucion, '/') - 1), ';', 1)), '%')\n" + //
        "\torder by c.CURDATE desc\n" + //
        "\tLIMIT 1\n" + //
        "\t) AS NUM_IDE_VGP, \n" + //
        "    \n" + //
        "    (SELECT u.Nombre_usuario\n" + //
        "\t\tFROM \n" + //
        "\t\tcalibraciones AS c \n" + //
        "\t\tINNER JOIN calibracion_dos_puntos AS cdp ON cdp.CALIBRATION = c.CALIBRATION\n" + //
        "\t\tINNER JOIN equipos AS e ON e.id_equipo = c.id_equipo\n" + //
        "        INNER JOIN usuarios u ON u.GEUSER = c.GEUSER\n" + //
        "\tWHERE \n" + //
        "\t\tc.CURDATE < hp.Fecha_ingreso_vehiculo\n" + //
        "        AND e.serialresolucion LIKE CONCAT('%', (SELECT SUBSTRING_INDEX(SUBSTRING(e.serialresolucion, 1, INSTR(e.serialresolucion, '/') - 1), ';', 1)), '%')\n" + //
        "\torder by c.CURDATE desc\n" + //
        "\tLIMIT 1\n" + //
        "\t) AS NOM_VGP, \n" + //
        "    \n" + //
        "    (SELECT max(c.CURDATE)\n" + //
        "\t\tFROM \n" + //
        "\t\tcalibraciones AS c\n" + //
        "\tWHERE \n" + //
        "\t\tc.CURDATE < p.Fecha_prueba) AS F_FUGAS,\n" + //
        "        \n" + //
        "\t(SELECT max(c.CURDATE)\n" + //
        "\t\tFROM \n" + //
        "\t\tcalibraciones AS c\n" + //
        "\tWHERE \n" + //
        "\t\tc.CURDATE < p.Fecha_prueba\n" + //
        "        AND id_tipo_calibracion = 1\n" + //
        "        AND VALOR1 <> 0) AS FECHA_LIN,\n" + //
        "        \n" + //
        "\t(SELECT lab_filtro_alto\n" + //
        "\t\tFROM \n" + //
        "\t\tcalibraciones AS c\n" + //
        "\tWHERE \n" + //
        "\t\tc.CURDATE < p.Fecha_prueba\n" + //
        "        AND id_tipo_calibracion = 1\n" + //
        "        AND VALOR1 <> 0\n" + //
        "\tLIMIT 1) AS P_ALTO_LAB,\n" + //
        "    \n" + //
        "    (SELECT serial_filtro_alto\n" + //
        "\t\tFROM \n" + //
        "\t\tcalibraciones AS c\n" + //
        "\tWHERE \n" + //
        "\t\tc.CURDATE < p.Fecha_prueba\n" + //
        "        AND id_tipo_calibracion = 1\n" + //
        "        AND VALOR1 <> 0\n" + //
        "\tLIMIT 1) AS P_ALTO_SERIAL,\n" + //
        "    \n" + //
        "    (SELECT certificado_filtro_alto\n" + //
        "\t\tFROM \n" + //
        "\t\tcalibraciones AS c\n" + //
        "\tWHERE \n" + //
        "\t\tc.CURDATE < p.Fecha_prueba\n" + //
        "        AND id_tipo_calibracion = 1\n" + //
        "        AND VALOR1 <> 0\n" + //
        "\tLIMIT 1) AS P_ALTO_CER,\n" + //
        "    \n" + //
        "    (SELECT VALOR2\n" + //
        "\t\tFROM \n" + //
        "\t\tcalibraciones AS c\n" + //
        "\tWHERE \n" + //
        "\t\tc.CURDATE < p.Fecha_prueba\n" + //
        "        AND id_tipo_calibracion = 1\n" + //
        "        AND VALOR1 <> 0\n" + //
        "\tLIMIT 1) AS V_FDN_ALTO,\n" + //
        "    \n" + //
        "    (SELECT lab_filtro_bajo\n" + //
        "\t\tFROM \n" + //
        "\t\tcalibraciones AS c\n" + //
        "\tWHERE \n" + //
        "\t\tc.CURDATE < p.Fecha_prueba\n" + //
        "        AND id_tipo_calibracion = 1\n" + //
        "        AND VALOR1 <> 0\n" + //
        "\tLIMIT 1) AS P_BAJO_LAB,\n" + //
        "    \n" + //
        "    (SELECT serial_filtro_bajo\n" + //
        "\t\tFROM \n" + //
        "\t\tcalibraciones AS c\n" + //
        "\tWHERE \n" + //
        "\t\tc.CURDATE < p.Fecha_prueba\n" + //
        "        AND id_tipo_calibracion = 1\n" + //
        "        AND VALOR1 <> 0\n" + //
        "\tLIMIT 1) AS P_BAJO_SERIAL,\n" + //
        "    \n" + //
        "    (SELECT certificado_filtro_bajo\n" + //
        "\t\tFROM \n" + //
        "\t\tcalibraciones AS c\n" + //
        "\tWHERE \n" + //
        "\t\tc.CURDATE < p.Fecha_prueba\n" + //
        "        AND id_tipo_calibracion = 1\n" + //
        "        AND VALOR1 <> 0\n" + //
        "\tLIMIT 1) AS P_BAJO_CER,\n" + //
        "    \n" + //
        "    (SELECT VALOR1\n" + //
        "\t\tFROM \n" + //
        "\t\tcalibraciones AS c\n" + //
        "\tWHERE \n" + //
        "\t\tc.CURDATE < p.Fecha_prueba\n" + //
        "        AND id_tipo_calibracion = 1\n" + //
        "        AND VALOR1 <> 0\n" + //
        "\tLIMIT 1) AS V_FDN_BAJO,\n" + //
        "    \n" + //
        "    (SELECT VALOR7\n" + //
        "\t\tFROM \n" + //
        "\t\tcalibraciones AS c\n" + //
        "\tWHERE \n" + //
        "\t\tc.CURDATE < p.Fecha_prueba\n" + //
        "        AND id_tipo_calibracion = 1\n" + //
        "        AND VALOR1 <> 0\n" + //
        "\tLIMIT 1) AS R_FDN_CERO,\n" + //
        "    \n" + //
        "    (SELECT VALOR4\n" + //
        "\t\tFROM \n" + //
        "\t\tcalibraciones AS c\n" + //
        "\tWHERE \n" + //
        "\t\tc.CURDATE < p.Fecha_prueba\n" + //
        "        AND id_tipo_calibracion = 1\n" + //
        "        AND VALOR1 <> 0\n" + //
        "\tLIMIT 1) AS R_FDN_BAJO,\n" + //
        "    \n" + //
        "    (SELECT VALOR5\n" + //
        "\t\tFROM \n" + //
        "\t\tcalibraciones AS c\n" + //
        "\tWHERE \n" + //
        "\t\tc.CURDATE < p.Fecha_prueba\n" + //
        "        AND id_tipo_calibracion = 1\n" + //
        "        AND VALOR1 <> 0\n" + //
        "\tLIMIT 1) AS R_FDN_ALTO,\n" + //
        "    \n" + //
        "    (SELECT VALOR6\n" + //
        "\t\tFROM \n" + //
        "\t\tcalibraciones AS c\n" + //
        "\tWHERE \n" + //
        "\t\tc.CURDATE < p.Fecha_prueba\n" + //
        "        AND id_tipo_calibracion = 1\n" + //
        "        AND VALOR1 <> 0\n" + //
        "\tLIMIT 1) AS R_FDN_CIEN,\n" + //
        "    \n" + //
        "    (SELECT aprobada\n" + //
        "\t\tFROM \n" + //
        "\t\tcalibraciones AS c\n" + //
        "\tWHERE \n" + //
        "\t\tc.CURDATE < p.Fecha_prueba\n" + //
        "        AND id_tipo_calibracion = 1\n" + //
        "        AND VALOR1 <> 0\n" + //
        "\tLIMIT 1) AS R_LIN,\n" + //
        "        \n" + //
        "\t(SELECT max(c.CURDATE)\n" + //
        "\t\tFROM \n" + //
        "\t\tcalibraciones AS c \n" + //
        "\t\tINNER JOIN calibracion_dos_puntos AS cdp ON cdp.CALIBRATION = c.CALIBRATION\n" + //
        "\t\tINNER JOIN equipos AS e ON e.id_equipo = c.id_equipo\n" + //
        "\tWHERE \n" + //
        "\t\tc.CURDATE < hp.Fecha_ingreso_vehiculo\n" + //
        "        AND e.serialresolucion LIKE CONCAT('%', (SELECT SUBSTRING_INDEX(SUBSTRING(e.serialresolucion, 1, INSTR(e.serialresolucion, '/') - 1), ';', 1)), '%')\n" + //
        "\t) AS F_VGP, \n" + //
        "    \n" + //
        "    laboratorio_alta as P_ALTA_LAB,\n" + //
        "    cilindro_alta as P_ALTA_CIL,\n" + //
        "    certificado_alta as P_ALTA_CER,\n" + //
        "    laboratorio_baja as P_BAJA_LAB,\n" + //
        "    cilindro_baja as P_BAJA_CIL,\n" + //
        "    certificado_baja as P_BAJA_CER,\n" + //
        "    (alta_hc/(e.pef/1000)) as P_HC_ALTO_P,\n" + //
        "    alta_hc as P_HC_ALTO_H,\n" + //
        "    (bm_hc/(e.pef/1000)) as P_HC_BAJO_P,\n" + //
        "\tbm_hc as P_HC_BAJO_H,\n" + //
        "    alta_co as P_CO_ALTO,\n" + //
        "    bm_co as P_CO_BAJO,\n" + //
        "    alta_co2 as P_CO2_ALTO,\n" + //
        "    bm_co2 as P_CO2_BAJO,\n" + //
        "    (banco_alta_hc/(e.pef/1000)) as R_HC_ALTO_P,\n" + //
        "    banco_alta_hc as R_HC_ALTO_H,\n" + //
        "    (banco_bm_hc/(e.pef/1000)) as R_HC_BAJO_P,\n" + //
        "    banco_bm_hc as R_HC_BAJO_H,\n" + //
        "    banco_alta_co as R_CO_ALTO,\n" + //
        "\tbanco_bm_co as R_CO_BAJO,\n" + //
        "\tbanco_alta_co2 as R_CO2_ALTO,\n" + //
        "\tbanco_bm_co2 as R_CO2_BAJO,\n" + //
        "\tbanco_alta_o2 as R_O2_ALTO,\n" + //
        "\tbanco_bm_o2 as R_O2_BAJO,\n" + //
        "    '' as C_VGP,\n" + //
        "    calibracion_aprobada as R_VGP,\n" + //
        "    \n" + //
        "    -- Datos generales de la inspeccion:\n" + //
        "    ur.cedula AS NUM_IDE_DT,\n" + //
        "    ur.Nombre_usuario AS NOM_DT,\n" + //
        "    u.cedula AS NUM_IDE_IT,\n" + //
        "    u.Nombre_usuario AS NOM_IT,\n" + //
        "    hp.con_hoja_prueba as NUM_FUR,\n" + //
        "    hp.Fecha_ingreso_vehiculo as FECHA_FUR,\n" + //
        "    hp.consecutivo_runt as CONS_RUNT,\n" + //
        "    CONCAT(hp.con_hoja_prueba, '-1') as FUR_ASOC,\n" + //
        "    c.CONSECUTIVE AS CERT_RTMYG,\n" + //
        "    p.Fecha_prueba as F_INI_INSP,\n" + //
        "    p.Fecha_final as F_FIN_INSP,\n" + //
        "    p.Fecha_aborto as F_ABORTO,\n" + //
        "    p.Comentario_aborto as C_ABORTO,\n" + //
        "    v.catalizador as CATALIZADOR,\n" + //
        "    hp.forma_med_temp as LUGAR_TEMP,\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8031 THEN m1.Valor_medida END) AS T_AMB,\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8032 THEN m1.Valor_medida END) AS H_REL,\n" + //
        "    \n" + //
        "    -- Resultados de la inspeccion realizada\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8006 AND v.Tiempos_motor = 4 THEN m1.Valor_medida\n" + //
        "             WHEN m1.MEASURETYPE = 8022 AND v.Tiempos_motor = 2 THEN m1.Valor_medida END) AS T_MOTOR,\n" + //
        "\t\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8005 AND v.Tiempos_motor = 4 THEN m1.Valor_medida\n" + //
        "             WHEN m1.MEASURETYPE = 8028 AND v.Tiempos_motor = 2 THEN m1.Valor_medida \n" + //
        "             WHEN m1.MEASURETYPE IN(8005, 8028) AND v.Tiempos_motor NOT IN (2, 4) THEN m1.Valor_medida END) AS RPM_RAL,\n" + //
        "             \n" + //
        "\tMAX(CASE WHEN m1.MEASURETYPE = 8011 THEN m1.Valor_medida END) AS RPM_CRU,\n" + //
        "    \n" + //
        "    MAX(CASE WHEN LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%humo%' \n" + //
        "\t\t  AND (LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%negro%' OR LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%azul%') \n" + //
        "          OR dfp.id_defecto = 84004\n" + //
        "\t THEN 1\n" + //
        "\t ELSE 0 END) AS HUMO,\n" + //
        "     \n" + //
        "     MAX(CASE WHEN m1.MEASURETYPE = 8004 AND v.Tiempos_motor = 4 AND m1.Valor_medida > 6 THEN 1\n" + //
        "             WHEN m1.MEASURETYPE = 8021 AND v.Tiempos_motor = 2 AND m1.Valor_medida > 6 THEN 1 ELSE 0 END) AS CORR_O2,\n" + //
        "     \n" + //
        "     MAX(CASE WHEN (m1.MEASURETYPE = 8003 AND m1.Valor_medida > 7) \n" + //
        "          OR (m1.MEASURETYPE = 8009 AND m1.Valor_medida > 7) \n" + //
        "          OR (m1.MEASURETYPE = 8004 AND m1.Valor_medida < 5) \n" + //
        "          OR (m1.MEASURETYPE = 8010 AND m1.Valor_medida < 5) \n" + //
        "        THEN 1 \n" + //
        "        ELSE 0 \n" + //
        "    END) AS DILUCION,\n" + //
        "    \n" + //
        "    MAX(CASE WHEN (dfp.id_defecto IN (84019, 84040))\n" + //
        "\t THEN 1\n" + //
        "\t ELSE 0 END) AS RPM_FUERA,\n" + //
        "     \n" + //
        "     MAX(CASE WHEN (dfp.id_defecto IN (84002, 84027, 84035))\n" + //
        "\t THEN 1\n" + //
        "\t ELSE 0 END) AS FUGA_TUBO,\n" + //
        "\t\n" + //
        "    MAX(CASE WHEN (dfp.id_defecto IN (84003, 84030, 84038))\n" + //
        "\t THEN 1\n" + //
        "\t ELSE 0 END) AS SALIDAS_AD,\n" + //
        "     \n" + //
        "     MAX(CASE WHEN (dfp.id_defecto IN (84001, 84031, 84037))\n" + //
        "\t THEN 1\n" + //
        "\t ELSE 0 END) AS FUGA_ACEITE,\n" + //
        "     \n" + //
        "     MAX(CASE WHEN (dfp.id_defecto IN (84000, 84032))\n" + //
        "\t THEN 1\n" + //
        "\t ELSE 0 END) AS FUGA_COMB,\n" + //
        "     \n" + //
        "     MAX(CASE WHEN dfp.id_defecto IN (84009)\n" + //
        "\t THEN 1\n" + //
        "\t ELSE 0 END) AS ADMISION_NC,\n" + //
        "     \n" + //
        "     MAX(CASE WHEN (dfp.id_defecto IN (84010))\n" + //
        "\t THEN 1\n" + //
        "\t ELSE 0 END) AS RECIRCULACION,\n" + //
        "     \n" + //
        "     MAX(CASE WHEN (dfp.id_defecto IN (84023))\n" + //
        "\t THEN 1\n" + //
        "\t ELSE 0 END) AS AC_DISP,\n" + //
        "     \n" + //
        "     MAX(CASE WHEN (dfp.id_defecto IN (84011, 84020))\n" + //
        "\t THEN 1\n" + //
        "\t ELSE 0 END) AS ACC_TUBO,\n" + //
        "     \n" + //
        "     MAX(CASE WHEN (dfp.id_defecto IN (84012, 84021))\n" + //
        "\t THEN 1\n" + //
        "\t ELSE 0 END) AS REFRIG_NC,\n" + //
        "     \n" + //
        "     MAX(CASE WHEN (dfp.id_defecto IN (84028))\n" + //
        "\t THEN 1\n" + //
        "\t ELSE 0 END) AS DIF_TEMP10,\n" + //
        "     \n" + //
        "     MAX(CASE WHEN (dfp.id_defecto IN (84007))\n" + //
        "\t THEN 1\n" + //
        "\t ELSE 0 END) AS GOB_NC,\n" + //
        "     \n" + //
        "     MAX(CASE WHEN (dfp.id_defecto IN (84025))\n" + //
        "\t THEN 1\n" + //
        "\t ELSE 0 END) AS FUN_MOTOR,\n" + //
        "     \n" + //
        "     MAX(CASE WHEN (dfp.id_defecto IN (84024))\n" + //
        "\t THEN 1\n" + //
        "\t ELSE 0 END) AS ACC_SUBITA,\n" + //
        "     \n" + //
        "     MAX(CASE WHEN (dfp.id_defecto IN (84029))\n" + //
        "\t THEN 1\n" + //
        "\t ELSE 0 END) AS FALLA_SUBITA,\n" + //
        "     \n" + //
        "     MAX(CASE WHEN (dfp.id_defecto IN (84028))\n" + //
        "\t THEN 1\n" + //
        "\t ELSE 0 END) AS DIF_ARITM,\n" + //
        "             \n" + //
        "\t MAX(CASE WHEN m1.MEASURETYPE = 8001 AND v.Tiempos_motor = 4 THEN m1.Valor_medida\n" + //
        "             WHEN m1.MEASURETYPE = 8018 AND v.Tiempos_motor = 2 THEN m1.Valor_medida\n" + //
        "             WHEN m1.MEASURETYPE = 8001 AND v.Tiempos_motor NOT IN (2, 4) THEN m1.Valor_medida END) AS R_HC_RAL,\n" + //
        "             \n" + //
        "\t MAX(CASE WHEN m1.MEASURETYPE = 8007 THEN m1.Valor_medida END) AS R_HC_CRU,\n" + //
        "             \n" + //
        "     MAX(CASE WHEN m1.MEASURETYPE = 8002 AND v.Tiempos_motor = 4 THEN m1.Valor_medida\n" + //
        "             WHEN m1.MEASURETYPE = 8020 AND v.Tiempos_motor = 2 THEN m1.Valor_medida \n" + //
        "             WHEN m1.MEASURETYPE = 8002 AND v.Tiempos_motor NOT IN (2, 4) THEN m1.Valor_medida END) AS R_CO_RAL,\n" + //
        "             \n" + //
        "\t MAX(CASE WHEN m1.MEASURETYPE = 8008 THEN m1.Valor_medida END) AS R_CO_CRU,\n" + //
        "             \n" + //
        "     MAX(CASE WHEN m1.MEASURETYPE = 8003 AND v.Tiempos_motor = 4 THEN m1.Valor_medida\n" + //
        "             WHEN m1.MEASURETYPE = 8019 AND v.Tiempos_motor = 2 THEN m1.Valor_medida \n" + //
        "             WHEN m1.MEASURETYPE = 8003 AND v.Tiempos_motor NOT IN (2, 4) THEN m1.Valor_medida END) AS R_CO2_RAL,\n" + //
        "             \n" + //
        "\t MAX(CASE WHEN m1.MEASURETYPE = 8009 THEN m1.Valor_medida END) AS R_CO2_CRU,\n" + //
        "             \n" + //
        "     MAX(CASE WHEN m1.MEASURETYPE = 8004 AND v.Tiempos_motor = 4 THEN m1.Valor_medida\n" + //
        "             WHEN m1.MEASURETYPE = 8021 AND v.Tiempos_motor = 2 THEN m1.Valor_medida \n" + //
        "             WHEN m1.MEASURETYPE = 8004 AND v.Tiempos_motor NOT IN (2, 4) THEN m1.Valor_medida END) AS R_O2_RAL,\n" + //
        "    \n" + //
        "\t MAX(CASE WHEN m1.MEASURETYPE = 8010 THEN m1.Valor_medida END) AS R_O2_CRU,\n" + //
        "    \n" + //
        "     MAX(CASE WHEN (dfp.id_defecto IN (84018, 15057, 80003, 80000))\n" + //
        "\t\t THEN 1\n" + //
        "\t\t ELSE 0 END) AS NC_EMISIONES,\n" + //
        "         \n" + //
        "\t\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8034 THEN m1.Valor_medida END) AS T_INICIAL_MOTOR,\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8037 THEN m1.Valor_medida END) AS T_FINAL_MOTOR,\n" + //
        "         \n" + //
        "\tMAX(CASE WHEN m1.MEASURETYPE = 8035 THEN m1.Valor_medida END) AS RPM_RAL2,\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8036 THEN m1.Valor_medida END) AS RPM_GOB,\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8035 THEN m1.Valor_medida END) AS RPM_RAL_PRE,\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8038 THEN m1.Valor_medida END) AS RPM_GOB_PRE,\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8033 THEN m1.Valor_medida END) AS R_DEN_PRE,\n" + //
        "    \n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8035 THEN m1.Valor_medida END) AS RPM_RAL_C1,\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8039 THEN m1.Valor_medida END) AS RPM_GOB_C1,\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8013 THEN m1.Valor_medida END) AS R_DEN_C1,\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8035 THEN m1.Valor_medida END) AS RPM_RAL_C2,\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8040 THEN m1.Valor_medida END) AS RPM_GOB_C2,\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8014 THEN m1.Valor_medida END) AS R_DEN_C2,\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8035 THEN m1.Valor_medida END) AS RPM_RAL_C3,\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8041 THEN m1.Valor_medida END) AS RPM_GOB_C3,\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8015 THEN m1.Valor_medida END) AS R_DEN_C3,\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8017 THEN m1.Valor_medida END) AS R_FINAL_DEN,\n" + //
        "    \n" + //
        "     p.Aprobada as RES_FINAL\n" + //
        "\n" + //
        "FROM \n" + //
        "    hoja_pruebas AS hp\n" + //
        "    INNER JOIN pruebas AS p ON p.hoja_pruebas_for = hp.TESTSHEET\n" + //
        "    INNER JOIN usuarios AS u ON u.GEUSER = p.Usuario_for\n" + //
        "    INNER JOIN usuarios AS ur ON ur.GEUSER = hp.usuario_resp\n" + //
        "    INNER JOIN vehiculos AS v ON hp.Vehiculo_for = v.CAR\n" + //
        "    INNER JOIN lineas_vehiculos as l ON v.CARLINE = l.CARLINE\n" + //
        "    INNER JOIN clases_vehiculo as cv on cv.CLASS = v.CLASS\n" + //
        "    INNER JOIN servicios as s on s.SERVICE = v.SERVICE\n" + //
        "    INNER JOIN propietarios AS pro ON (v.CAROWNER = pro.CAROWNER)\n" + //
        "    INNER JOIN tipos_gasolina as tg on tg.FUELTYPE = v.FUELTYPE\n" + //
        "    INNER JOIN ciudades AS ci ON (ci.CITY = pro.CITY)\n" + //
        "    INNER JOIN marcas AS m ON m.CARMARK = v.CARMARK\n" + //
        "    INNER JOIN equipos AS e ON e.serialresolucion = p.serialEquipo\n" + //
        "    INNER JOIN certificados as c on hp.TESTSHEET = c.TESTSHEET\n" + //
        "    LEFT JOIN medidas AS m1 ON p.Id_Pruebas = m1.TEST\n" + //
        "    LEFT JOIN defxprueba as dfp ON dfp.id_prueba = p.Id_Pruebas\n" + //
        "    LEFT JOIN defectos as df ON dfp.id_defecto = df.CARDEFAULT\n" + //
        "    INNER JOIN ciudades AS cy ON cy.CITY = pro.CITY\n" + //
        "    LEFT JOIN (\n" + //
        "        SELECT \n" + //
        "            c.CALIBRATION,\n" + //
        "            laboratorio_pipeta_alta AS laboratorio_alta,\n" + //
        "            cilindro_pipeta_alta AS cilindro_alta,\n" + //
        "            certificado_pipeta_alta AS certificado_alta,\n" + //
        "            laboratorio_pipeta_baja AS laboratorio_baja,\n" + //
        "            cilindro_pipeta_baja AS cilindro_baja,\n" + //
        "            certificado_pipeta_baja AS certificado_baja,\n" + //
        "            alta_hc,\n" + //
        "            bm_hc,\n" + //
        "\t\t\talta_co,\n" + //
        "\t\t\tbm_co,\n" + //
        "\t\t\talta_co2,\n" + //
        "\t\t\tbm_co2,\n" + //
        "            banco_alta_hc,\n" + //
        "            banco_bm_hc,\n" + //
        "            banco_alta_co,\n" + //
        "            banco_bm_co,\n" + //
        "            banco_alta_co2,\n" + //
        "            banco_bm_co2,\n" + //
        "            banco_alta_o2,\n" + //
        "            banco_bm_o2,\n" + //
        "            c.aprobada as calibracion_aprobada,\n" + //
        "            u.cedula AS cedula_calibrador,\n" + //
        "\t\t\tu.Nombre_usuario AS usuario_calibrador\n" + //
        "        FROM \n" + //
        "            calibraciones AS c \n" + //
        "            INNER JOIN calibracion_dos_puntos AS cdp ON cdp.CALIBRATION = c.CALIBRATION\n" + //
        "            INNER JOIN equipos AS e ON e.id_equipo = c.id_equipo\n" + //
        "            INNER JOIN usuarios AS u ON u.GEUSER = c.GEUSER\n" + //
        "        WHERE \n" + //
        "            e.serialresolucion LIKE CONCAT('%', (SELECT SUBSTRING_INDEX(SUBSTRING(e.serialresolucion, 1, INSTR(e.serialresolucion, '/') - 1), ';', 1)), '%')\n" + //
        "            AND c.id_tipo_calibracion <> 1\n" + //
        "            AND c.CURDATE BETWEEN ? AND ?\n" + //
        "        ORDER BY \n" + //
        "            c.CURDATE DESC\n" + //
        "        LIMIT 1\n" + //
        "    ) AS alta ON 1 = 1\n" + //
        "WHERE \n" + //
        "    p.Tipo_prueba_for = 8\n" + //
        "    AND p.Fecha_prueba BETWEEN ? AND ?\n" + //
        "    -- AND tg.Nombre_gasolina = 'DIESEL'\n" + //
        tipo +" "+ //
        "GROUP BY \n" + //
        "    hp.TESTSHEET\n" + //
        "ORDER BY \n" + //
        "    p.Fecha_prueba asc;";
    }

    public static String getNtc2(String tipo){
        return
        "SELECT \n" + //
        "    -- Datos propietario\n" + //
        "    pro.Tipo_identificacion AS TIP_IDE_PROP,\n" + //
        "    pro.CAROWNER AS NUM_IDE_PROP,\n" + //
        "    CONCAT(pro.Nombres, ' ', pro.Apellidos) AS NOM_PROP,\n" + //
        "    pro.Direccion AS DIR_PROP,\n" + //
        "    pro.Numero_telefono AS TEL1_PROP,\n" + //
        "    cy.Nombre_ciudad AS MUN_PROP,\n" + //
        "    pro.email AS CORR_E_PROP,\n" + //
        "\n" + //
        "    -- Datos vehículo\n" + //
        "    v.CARPLATE AS PLACA,\n" + //
        "    v.Modelo AS MODELO,\n" + //
        "    v.Numero_motor AS NUM_MOTOR,\n" + //
        "    v.VIN,\n" + //
        "    v.Diametro AS DIA_ESCAPE,\n" + //
        "    v.Cinlindraje AS CILINDRAJE,\n" + //
        "    v.Numero_licencia AS LIC_TRANS,\n" + //
        "    v.kilometraje AS KM,\n" + //
        "    v.conversion_gnv AS GNV_CONV,\n" + //
        "    hp.fecha_venc_gnv AS GNV_CONV_V,\n" + //
        "    m.Nombre_marca AS MARCA,\n" + //
        "    l.CRLNAME AS LINEA,\n" + //
        "    cv.Nombre_clase AS CLASE,\n" + //
        "    s.Nombre_servicio AS SERVICIO,\n" + //
        "    tg.Nombre_gasolina AS TIP_COMB,\n" + //
        "    v.Tiempos_motor AS TIP_MOTOR,\n" + //
        "    v.Numero_exostos AS NUM_ESCAPE,\n" + //
        "    CONCAT(v.Tiempos_motor, 'T') AS DIS_MOTOR,\n" + //
        "    v.diseño as diseno,\n" + //
        "\n" + //
        "    -- Datos analizador\n" + //
        "    p.serialEquipo,\n" + //
        "    \n" + //
        "    (SELECT u.cedula\n" + //
        "\tFROM calibraciones c\n" + //
        "\tINNER JOIN usuarios u ON u.GEUSER = c.GEUSER\n" + //
        "\tWHERE c.CURDATE BETWEEN DATE_SUB(p.Fecha_prueba, INTERVAL 3 DAY) AND p.Fecha_prueba\n" + //
        "\t  AND c.id_tipo_calibracion = 2\n" + //
        "\tORDER BY c.CURDATE DESC\n" + //
        "\tLIMIT 1) AS NUM_IDE_VGP,\n" + //
        "    \n" + //
        "    (SELECT u.Nombre_usuario\n" + //
        "\tFROM calibraciones c\n" + //
        "\tINNER JOIN usuarios u ON u.GEUSER = c.GEUSER\n" + //
        "\tWHERE c.CURDATE BETWEEN DATE_SUB(p.Fecha_prueba, INTERVAL 3 DAY) AND p.Fecha_prueba\n" + //
        "\t  AND c.id_tipo_calibracion = 2\n" + //
        "\tORDER BY c.CURDATE DESC\n" + //
        "\tLIMIT 1) AS NOM_VGP,\n" + //
        "    \n" + //
        "    (SELECT MAX(c.CURDATE)\n" + //
        "     FROM calibraciones AS c\n" + //
        "     WHERE c.CURDATE < p.Fecha_prueba\n" + //
        "     AND id_tipo_calibracion = 3) AS F_FUGAS,\n" + //
        "     \n" + //
        "     (SELECT MAX(c.CURDATE)\n" + //
        "     FROM calibraciones AS c\n" + //
        "     WHERE c.CURDATE < p.Fecha_prueba\n" + //
        "     AND id_tipo_calibracion = 2) AS F_VGP,\n" + //
        "    \n" + //
        "    (SELECT cdp.laboratorio_pipeta_alta\n" + //
        "\tFROM calibraciones c\n" + //
        "\tINNER JOIN calibracion_dos_puntos cdp ON cdp.CALIBRATION = c.CALIBRATION\n" + //
        "\tWHERE c.CURDATE BETWEEN DATE_SUB(p.Fecha_prueba, INTERVAL 3 DAY) AND p.Fecha_prueba\n" + //
        "\t  AND c.id_tipo_calibracion = 2\n" + //
        "\tORDER BY c.CURDATE DESC\n" + //
        "\tLIMIT 1) AS P_ALTO_LAB,\n" + //
        "    \n" + //
        "    (SELECT cdp.cilindro_pipeta_alta\n" + //
        "\tFROM calibraciones c\n" + //
        "\tINNER JOIN calibracion_dos_puntos cdp ON cdp.CALIBRATION = c.CALIBRATION\n" + //
        "\tWHERE c.CURDATE BETWEEN DATE_SUB(p.Fecha_prueba, INTERVAL 3 DAY) AND p.Fecha_prueba\n" + //
        "\t  AND c.id_tipo_calibracion = 2\n" + //
        "\tORDER BY c.CURDATE DESC\n" + //
        "\tLIMIT 1) AS P_ALTA_CIL,\n" + //
        "    \n" + //
        "    (SELECT cdp.certificado_pipeta_alta\n" + //
        "\tFROM calibraciones c\n" + //
        "\tINNER JOIN calibracion_dos_puntos cdp ON cdp.CALIBRATION = c.CALIBRATION\n" + //
        "\tWHERE c.CURDATE BETWEEN DATE_SUB(p.Fecha_prueba, INTERVAL 3 DAY) AND p.Fecha_prueba\n" + //
        "\t  AND c.id_tipo_calibracion = 2\n" + //
        "\tORDER BY c.CURDATE DESC\n" + //
        "\tLIMIT 1) AS P_ALTA_CER,\n" + //
        "    \n" + //
        "    (SELECT cdp.laboratorio_pipeta_baja\n" + //
        "\tFROM calibraciones c\n" + //
        "\tINNER JOIN calibracion_dos_puntos cdp ON cdp.CALIBRATION = c.CALIBRATION\n" + //
        "\tWHERE c.CURDATE BETWEEN DATE_SUB(p.Fecha_prueba, INTERVAL 3 DAY) AND p.Fecha_prueba\n" + //
        "\t  AND c.id_tipo_calibracion = 2\n" + //
        "\tORDER BY c.CURDATE DESC\n" + //
        "\tLIMIT 1) AS P_BAJA_LAB,\n" + //
        "    \n" + //
        "    (SELECT cdp.cilindro_pipeta_baja\n" + //
        "\tFROM calibraciones c\n" + //
        "\tINNER JOIN calibracion_dos_puntos cdp ON cdp.CALIBRATION = c.CALIBRATION\n" + //
        "\tWHERE c.CURDATE BETWEEN DATE_SUB(p.Fecha_prueba, INTERVAL 3 DAY) AND p.Fecha_prueba\n" + //
        "\t  AND c.id_tipo_calibracion = 2\n" + //
        "\tORDER BY c.CURDATE DESC\n" + //
        "\tLIMIT 1) AS P_BAJA_CIL,\n" + //
        "    \n" + //
        "    (SELECT cdp.certificado_pipeta_baja\n" + //
        "\tFROM calibraciones c\n" + //
        "\tINNER JOIN calibracion_dos_puntos cdp ON cdp.CALIBRATION = c.CALIBRATION\n" + //
        "\tWHERE c.CURDATE BETWEEN DATE_SUB(p.Fecha_prueba, INTERVAL 3 DAY) AND p.Fecha_prueba\n" + //
        "\t  AND c.id_tipo_calibracion = 2\n" + //
        "\tORDER BY c.CURDATE DESC\n" + //
        "\tLIMIT 1) AS P_BAJA_CER,\n" + //
        "    \n" + //
        "    (SELECT cdp.alta_hc\n" + //
        "\tFROM calibraciones c\n" + //
        "\tINNER JOIN calibracion_dos_puntos cdp ON cdp.CALIBRATION = c.CALIBRATION\n" + //
        "\tWHERE c.CURDATE BETWEEN DATE_SUB(p.Fecha_prueba, INTERVAL 3 DAY) AND p.Fecha_prueba\n" + //
        "\t  AND c.id_tipo_calibracion = 2\n" + //
        "\tORDER BY c.CURDATE DESC\n" + //
        "\tLIMIT 1) AS P_HC_ALTO_H,\n" + //
        "    \n" + //
        "    (SELECT cdp.bm_hc \n" + //
        "\tFROM calibraciones c\n" + //
        "\tINNER JOIN calibracion_dos_puntos cdp ON cdp.CALIBRATION = c.CALIBRATION\n" + //
        "\tWHERE c.CURDATE BETWEEN DATE_SUB(p.Fecha_prueba, INTERVAL 3 DAY) AND p.Fecha_prueba\n" + //
        "\t  AND c.id_tipo_calibracion = 2\n" + //
        "\tORDER BY c.CURDATE DESC\n" + //
        "\tLIMIT 1) AS P_HC_BAJO_H,\n" + //
        "    \n" + //
        "    (SELECT cdp.alta_co \n" + //
        "\tFROM calibraciones c\n" + //
        "\tINNER JOIN calibracion_dos_puntos cdp ON cdp.CALIBRATION = c.CALIBRATION\n" + //
        "\tWHERE c.CURDATE BETWEEN DATE_SUB(p.Fecha_prueba, INTERVAL 3 DAY) AND p.Fecha_prueba\n" + //
        "\t  AND c.id_tipo_calibracion = 2\n" + //
        "\tORDER BY c.CURDATE DESC\n" + //
        "\tLIMIT 1) AS P_CO_ALTO,\n" + //
        "    \n" + //
        "    (SELECT cdp.bm_co \n" + //
        "\tFROM calibraciones c\n" + //
        "\tINNER JOIN calibracion_dos_puntos cdp ON cdp.CALIBRATION = c.CALIBRATION\n" + //
        "\tWHERE c.CURDATE BETWEEN DATE_SUB(p.Fecha_prueba, INTERVAL 3 DAY) AND p.Fecha_prueba\n" + //
        "\t  AND c.id_tipo_calibracion = 2\n" + //
        "\tORDER BY c.CURDATE DESC\n" + //
        "\tLIMIT 1) AS P_CO_BAJO,\n" + //
        "    \n" + //
        "    (SELECT cdp.alta_co2 \n" + //
        "\tFROM calibraciones c\n" + //
        "\tINNER JOIN calibracion_dos_puntos cdp ON cdp.CALIBRATION = c.CALIBRATION\n" + //
        "\tWHERE c.CURDATE BETWEEN DATE_SUB(p.Fecha_prueba, INTERVAL 3 DAY) AND p.Fecha_prueba\n" + //
        "\t  AND c.id_tipo_calibracion = 2\n" + //
        "\tORDER BY c.CURDATE DESC\n" + //
        "\tLIMIT 1) AS P_CO2_ALTO,\n" + //
        "    \n" + //
        "    (SELECT cdp.bm_co2 \n" + //
        "\tFROM calibraciones c\n" + //
        "\tINNER JOIN calibracion_dos_puntos cdp ON cdp.CALIBRATION = c.CALIBRATION\n" + //
        "\tWHERE c.CURDATE BETWEEN DATE_SUB(p.Fecha_prueba, INTERVAL 3 DAY) AND p.Fecha_prueba\n" + //
        "\t  AND c.id_tipo_calibracion = 2\n" + //
        "\tORDER BY c.CURDATE DESC\n" + //
        "\tLIMIT 1) AS P_CO2_BAJO,\n" + //
        "    \n" + //
        "    (SELECT cdp.banco_alta_hc \n" + //
        "\tFROM calibraciones c\n" + //
        "\tINNER JOIN calibracion_dos_puntos cdp ON cdp.CALIBRATION = c.CALIBRATION\n" + //
        "\tWHERE c.CURDATE BETWEEN DATE_SUB(p.Fecha_prueba, INTERVAL 3 DAY) AND p.Fecha_prueba\n" + //
        "\t  AND c.id_tipo_calibracion = 2\n" + //
        "\tORDER BY c.CURDATE DESC\n" + //
        "\tLIMIT 1) AS R_HC_ALTO_H,\n" + //
        "    \n" + //
        "    (SELECT cdp.banco_bm_hc \n" + //
        "\tFROM calibraciones c\n" + //
        "\tINNER JOIN calibracion_dos_puntos cdp ON cdp.CALIBRATION = c.CALIBRATION\n" + //
        "\tWHERE c.CURDATE BETWEEN DATE_SUB(p.Fecha_prueba, INTERVAL 3 DAY) AND p.Fecha_prueba\n" + //
        "\t  AND c.id_tipo_calibracion = 2\n" + //
        "\tORDER BY c.CURDATE DESC\n" + //
        "\tLIMIT 1) AS R_HC_BAJO_H,\n" + //
        "    \n" + //
        "    (SELECT cdp.banco_alta_co  \n" + //
        "\tFROM calibraciones c\n" + //
        "\tINNER JOIN calibracion_dos_puntos cdp ON cdp.CALIBRATION = c.CALIBRATION\n" + //
        "\tWHERE c.CURDATE BETWEEN DATE_SUB(p.Fecha_prueba, INTERVAL 3 DAY) AND p.Fecha_prueba\n" + //
        "\t  AND c.id_tipo_calibracion = 2\n" + //
        "\tORDER BY c.CURDATE DESC\n" + //
        "\tLIMIT 1) AS R_CO_ALTO,\n" + //
        "    \n" + //
        "    (SELECT cdp.banco_bm_co   \n" + //
        "\tFROM calibraciones c\n" + //
        "\tINNER JOIN calibracion_dos_puntos cdp ON cdp.CALIBRATION = c.CALIBRATION\n" + //
        "\tWHERE c.CURDATE BETWEEN DATE_SUB(p.Fecha_prueba, INTERVAL 3 DAY) AND p.Fecha_prueba\n" + //
        "\t  AND c.id_tipo_calibracion = 2\n" + //
        "\tORDER BY c.CURDATE DESC\n" + //
        "\tLIMIT 1) AS R_CO_BAJO,\n" + //
        "    \n" + //
        "    (SELECT cdp.banco_alta_co2    \n" + //
        "\tFROM calibraciones c\n" + //
        "\tINNER JOIN calibracion_dos_puntos cdp ON cdp.CALIBRATION = c.CALIBRATION\n" + //
        "\tWHERE c.CURDATE BETWEEN DATE_SUB(p.Fecha_prueba, INTERVAL 3 DAY) AND p.Fecha_prueba\n" + //
        "\t  AND c.id_tipo_calibracion = 2\n" + //
        "\tORDER BY c.CURDATE DESC\n" + //
        "\tLIMIT 1) AS R_CO2_ALTO,\n" + //
        "    \n" + //
        "    (SELECT cdp.banco_bm_co2     \n" + //
        "\tFROM calibraciones c\n" + //
        "\tINNER JOIN calibracion_dos_puntos cdp ON cdp.CALIBRATION = c.CALIBRATION\n" + //
        "\tWHERE c.CURDATE BETWEEN DATE_SUB(p.Fecha_prueba, INTERVAL 3 DAY) AND p.Fecha_prueba\n" + //
        "\t  AND c.id_tipo_calibracion = 2\n" + //
        "\tORDER BY c.CURDATE DESC\n" + //
        "\tLIMIT 1) AS R_CO2_BAJO,\n" + //
        "    \n" + //
        "    (SELECT cdp.banco_alta_o2      \n" + //
        "\tFROM calibraciones c\n" + //
        "\tINNER JOIN calibracion_dos_puntos cdp ON cdp.CALIBRATION = c.CALIBRATION\n" + //
        "\tWHERE c.CURDATE BETWEEN DATE_SUB(p.Fecha_prueba, INTERVAL 3 DAY) AND p.Fecha_prueba\n" + //
        "\t  AND c.id_tipo_calibracion = 2\n" + //
        "\tORDER BY c.CURDATE DESC\n" + //
        "\tLIMIT 1) AS R_O2_ALTO,\n" + //
        "    \n" + //
        "    (SELECT cdp.banco_bm_o2       \n" + //
        "\tFROM calibraciones c\n" + //
        "\tINNER JOIN calibracion_dos_puntos cdp ON cdp.CALIBRATION = c.CALIBRATION\n" + //
        "\tWHERE c.CURDATE BETWEEN DATE_SUB(p.Fecha_prueba, INTERVAL 3 DAY) AND p.Fecha_prueba\n" + //
        "\t  AND c.id_tipo_calibracion = 2\n" + //
        "\tORDER BY c.CURDATE DESC\n" + //
        "\tLIMIT 1) AS R_O2_BAJO,\n" + //
        "    \n" + //
        "    '' AS C_VGP,\n" + //
        "    \n" + //
        "    (SELECT c.aprobada     \n" + //
        "\tFROM calibraciones c\n" + //
        "\tINNER JOIN calibracion_dos_puntos cdp ON cdp.CALIBRATION = c.CALIBRATION\n" + //
        "\tWHERE c.CURDATE BETWEEN DATE_SUB(p.Fecha_prueba, INTERVAL 3 DAY) AND p.Fecha_prueba\n" + //
        "\t  AND c.id_tipo_calibracion = 2\n" + //
        "\tORDER BY c.CURDATE DESC\n" + //
        "\tLIMIT 1) AS R_VGP,\n" + //
        "    \n" + //
        "    -- Datos generales de la inspección\n" + //
        "    ur.cedula AS NUM_IDE_DT,\n" + //
        "    ur.Nombre_usuario AS NOM_DT,\n" + //
        "    u.cedula AS NUM_IDE_IT,\n" + //
        "    u.Nombre_usuario AS NOM_IT,\n" + //
        "    hp.con_hoja_prueba AS NUM_FUR,\n" + //
        "    hp.Fecha_ingreso_vehiculo AS FECHA_FUR,\n" + //
        "    hp.consecutivo_runt AS CONS_RUNT,\n" + //
        "    hp.con_hoja_prueba AS FUR_ASOC,\n" + //
        "    hp.Numero_intentos,\n" + //
        "    c.CONSECUTIVE AS CERT_RTMYG,\n" + //
        "    p.Fecha_prueba AS F_INI_INSP,\n" + //
        "    p.Fecha_final AS F_FIN_INSP,\n" + //
        "    p.Fecha_aborto AS F_ABORTO,\n" + //
        "    p.observaciones AS C_ABORTO,\n" + //
        "    hp.forma_med_temp AS LUGAR_TEMP,\n" + //
        "\n" + //
        "    (SELECT MAX(c.CURDATE)\n" + //
        "     FROM calibraciones AS c\n" + //
        "     WHERE c.CURDATE < p.Fecha_prueba\n" + //
        "     AND id_tipo_calibracion = 1\n" + //
        "     AND VALOR1 <> 0) AS FECHA_LIN,\n" + //
        "     \n" + //
        "     (SELECT lab_filtro_alto\n" + //
        "     FROM calibraciones AS c\n" + //
        "     WHERE c.CURDATE < p.Fecha_prueba\n" + //
        "     AND id_tipo_calibracion = 1\n" + //
        "     AND lab_filtro_alto IS NOT NULL\n" + //
        "     limit 1) AS P_ALTO_LAB_DI,\n" + //
        "     \n" + //
        "     (SELECT serial_filtro_alto\n" + //
        "     FROM calibraciones AS c\n" + //
        "     WHERE c.CURDATE < p.Fecha_prueba\n" + //
        "     AND id_tipo_calibracion = 1\n" + //
        "     AND lab_filtro_alto IS NOT NULL\n" + //
        "     limit 1) AS P_ALTO_SERIAL_DI,\n" + //
        "     \n" + //
        "     (SELECT certificado_filtro_alto\n" + //
        "     FROM calibraciones AS c\n" + //
        "     WHERE c.CURDATE < p.Fecha_prueba\n" + //
        "     AND id_tipo_calibracion = 1\n" + //
        "     AND lab_filtro_alto IS NOT NULL\n" + //
        "     limit 1) AS P_ALTO_CER_DI,\n" + //
        "     \n" + //
        "     (SELECT VALOR2\n" + //
        "     FROM calibraciones AS c\n" + //
        "     WHERE c.CURDATE < p.Fecha_prueba\n" + //
        "     AND id_tipo_calibracion = 1\n" + //
        "     AND lab_filtro_alto IS NOT NULL\n" + //
        "     limit 1) AS V_FDN_ALTO,\n" + //
        "     \n" + //
        "     (SELECT lab_filtro_bajo\n" + //
        "     FROM calibraciones AS c\n" + //
        "     WHERE c.CURDATE < p.Fecha_prueba\n" + //
        "     AND id_tipo_calibracion = 1\n" + //
        "     AND lab_filtro_alto IS NOT NULL\n" + //
        "     limit 1) AS P_BAJO_LAB_DI,\n" + //
        "     \n" + //
        "     (SELECT serial_filtro_bajo\n" + //
        "     FROM calibraciones AS c\n" + //
        "     WHERE c.CURDATE < p.Fecha_prueba\n" + //
        "     AND id_tipo_calibracion = 1\n" + //
        "     AND lab_filtro_alto IS NOT NULL\n" + //
        "     limit 1) AS P_BAJO_SERIAL_DI,\n" + //
        "     \n" + //
        "     (SELECT certificado_filtro_bajo\n" + //
        "     FROM calibraciones AS c\n" + //
        "     WHERE c.CURDATE < p.Fecha_prueba\n" + //
        "     AND id_tipo_calibracion = 1\n" + //
        "     AND lab_filtro_alto IS NOT NULL\n" + //
        "     limit 1) AS P_BAJO_CER_DI,\n" + //
        "     \n" + //
        "     (SELECT VALOR1\n" + //
        "     FROM calibraciones AS c\n" + //
        "     WHERE c.CURDATE < p.Fecha_prueba\n" + //
        "     AND id_tipo_calibracion = 1\n" + //
        "     AND lab_filtro_alto IS NOT NULL\n" + //
        "     limit 1) AS V_FDN_BAJO,\n" + //
        "     \n" + //
        "     (SELECT VALOR7\n" + //
        "     FROM calibraciones AS c\n" + //
        "     WHERE c.CURDATE < p.Fecha_prueba\n" + //
        "     AND id_tipo_calibracion = 1\n" + //
        "     AND lab_filtro_alto IS NOT NULL\n" + //
        "     limit 1) AS R_FDN_CERO,\n" + //
        "     \n" + //
        "     (SELECT VALOR4\n" + //
        "     FROM calibraciones AS c\n" + //
        "     WHERE c.CURDATE < p.Fecha_prueba\n" + //
        "     AND id_tipo_calibracion = 1\n" + //
        "     AND lab_filtro_alto IS NOT NULL\n" + //
        "     limit 1) AS R_FDN_BAJO,\n" + //
        "     \n" + //
        "     (SELECT VALOR5\n" + //
        "     FROM calibraciones AS c\n" + //
        "     WHERE c.CURDATE < p.Fecha_prueba\n" + //
        "     AND id_tipo_calibracion = 1\n" + //
        "     AND lab_filtro_alto IS NOT NULL\n" + //
        "     limit 1) AS R_FDN_ALTO,\n" + //
        "     \n" + //
        "     (SELECT VALOR6\n" + //
        "     FROM calibraciones AS c\n" + //
        "     WHERE c.CURDATE < p.Fecha_prueba\n" + //
        "     AND id_tipo_calibracion = 1\n" + //
        "     AND lab_filtro_alto IS NOT NULL\n" + //
        "     limit 1) AS R_FDN_CIEN,\n" + //
        "     \n" + //
        "     (SELECT aprobada\n" + //
        "     FROM calibraciones AS c\n" + //
        "     WHERE c.CURDATE < p.Fecha_prueba\n" + //
        "     AND id_tipo_calibracion = 1\n" + //
        "     AND lab_filtro_alto IS NOT NULL\n" + //
        "     limit 1) AS R_LIN,\n" + //
        "     \n" + //
        "\tMAX(CASE WHEN m1.MEASURETYPE = 8031 THEN m1.Valor_medida END) AS temperatura_ambiente,\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8032 THEN m1.Valor_medida END) AS humedad_relativa,\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8006 AND v.Tiempos_motor = 4 THEN m1.Valor_medida\n" + //
        "             WHEN m1.MEASURETYPE = 8022 AND v.Tiempos_motor = 2 THEN m1.Valor_medida END) AS temperatura_motor,\n" + //
        "\tMAX(CASE WHEN m1.MEASURETYPE = 8005 AND v.Tiempos_motor = 4 THEN m1.Valor_medida\n" + //
        "             WHEN m1.MEASURETYPE = 8028 AND v.Tiempos_motor = 2 THEN m1.Valor_medida END) AS rpm_ralenti,\n" + //
        "             \n" + //
        "\tMAX(CASE WHEN m1.MEASURETYPE = 8011 AND v.Tiempos_motor = 4 THEN m1.Valor_medida\n" + //
        "             WHEN m1.MEASURETYPE = 8029 AND v.Tiempos_motor = 2 THEN m1.Valor_medida END) AS rpm_crucero,\n" + //
        "             \n" + //
        "\tMAX(CASE WHEN LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%humo%' \n" + //
        "\t\t  AND (LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%negro%' OR LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%azul%') \n" + //
        "\t THEN 1\n" + //
        "\t ELSE 0 END) AS presencia_humo_n_a,\n" + //
        "             \n" + //
        "\tMAX(CASE WHEN LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%revoluciones FUERA RANGO%' \n" + //
        "\t THEN 1\n" + //
        "\t ELSE 0 END) AS rpm_fuera_rango,\n" + //
        "     \n" + //
        "     MAX(CASE WHEN LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%fuga%' \n" + //
        "\t\t  AND LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%tubo%'\n" + //
        "\t THEN 1\n" + //
        "\t ELSE 0 END) AS fugas_tubo_escape,\n" + //
        "     \n" + //
        "     MAX(CASE WHEN LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%salida%' \n" + //
        "\t\t  AND LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%adicional%'\n" + //
        "\t THEN 1\n" + //
        "\t ELSE 0 END) AS salidas_adicionales_diseno,\n" + //
        "     \n" + //
        "     MAX(CASE WHEN LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%tapa%' \n" + //
        "\t\t  AND LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%aceite%'\n" + //
        "\t THEN 1\n" + //
        "\t ELSE 0 END) AS ausencia_tapa_aceite,\n" + //
        "     \n" + //
        "     MAX(CASE WHEN LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%ausencia%' \n" + //
        "\t\t  AND LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%tapa%'\n" + //
        "          AND LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%combustible%'\n" + //
        "\t THEN 1\n" + //
        "\t ELSE 0 END) AS ausencia_tapa_combustible,\n" + //
        "     \n" + //
        "     MAX(CASE WHEN LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%filtro%' \n" + //
        "\t\t  AND LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%aire%'\n" + //
        "\t THEN 1\n" + //
        "\t ELSE 0 END) AS ausencia_dano_filtro_aire,\n" + //
        "     \n" + //
        "     MAX(CASE WHEN LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%recirculaci%' \n" + //
        "\t THEN 1\n" + //
        "\t ELSE 0 END) AS recirculacion,\n" + //
        "     \n" + //
        "     MAX(CASE WHEN LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%accesorio%' \n" + //
        "\t\t  AND LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%deformacion%'\n" + //
        "          AND LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%tubo%'\n" + //
        "\t THEN 1\n" + //
        "\t ELSE 0 END) AS accesorios,\n" + //
        "     \n" + //
        "     MAX(CASE WHEN LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%refrigeraci%' \n" + //
        "\t\t  AND LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%operaci%'\n" + //
        "\t THEN 1\n" + //
        "\t ELSE 0 END) AS refrigeracion,\n" + //
        "     \n" + //
        "     MAX(CASE WHEN LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%instala%' \n" + //
        "\t\t  AND LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%dispositivo%'\n" + //
        "          AND LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%motor%'\n" + //
        "\t THEN 1\n" + //
        "\t ELSE 0 END) AS AC_DISP,\n" + //
        "     \n" + //
        "     MAX(CASE WHEN LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%temperatura%' \n" + //
        "\t\t  AND LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%10%'\n" + //
        "          AND LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%dif%'\n" + //
        "\t THEN 1\n" + //
        "\t ELSE 0 END) AS DIF_TEMP10,\n" + //
        "     \n" + //
        "     MAX(CASE WHEN LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%Problemas en el sistema de inyección de combustible%' \n" + //
        "\t THEN 1\n" + //
        "\t ELSE 0 END) AS GOB_NC,\n" + //
        "     \n" + //
        "     MAX(CASE WHEN LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%Indicación visible o sonora que pone en duda las condiciones normales del moto%'\n" + //
        "\t THEN 1\n" + //
        "\t ELSE 0 END) AS FUN_MOTOR,\n" + //
        "     \n" + //
        "     MAX(CASE WHEN LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%no cumplir con la velocidad gobernada en el tiempo indicado%' \n" + //
        "\t THEN 1\n" + //
        "\t ELSE 0 END) AS ACC_SUBITA,\n" + //
        "     \n" + //
        "     MAX(CASE WHEN LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%diferencia  aritmetica%'\n" + //
        "\t THEN 1\n" + //
        "\t ELSE 0 END) AS DIF_ARITM,\n" + //
        "     \n" + //
        "     MAX(CASE WHEN LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%Falla%' \n" + //
        "\t\t  AND LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%súbita%'\n" + //
        "          AND LOWER(REPLACE(p.observaciones, ',', '')) LIKE '%motor%'\n" + //
        "\t THEN 1\n" + //
        "\t ELSE 0 END) AS subita_motor,\n" + //
        "     \n" + //
        "     MAX(CASE WHEN m1.MEASURETYPE = 8001 AND v.Tiempos_motor = 4 THEN m1.Valor_medida\n" + //
        "             WHEN m1.MEASURETYPE = 8018 AND v.Tiempos_motor = 2 THEN m1.Valor_medida END) AS hc_ralenti,\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8002 AND v.Tiempos_motor = 4 THEN m1.Valor_medida\n" + //
        "             WHEN m1.MEASURETYPE = 8020 AND v.Tiempos_motor = 2 THEN m1.Valor_medida END) AS co_ralenti,\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8003 AND v.Tiempos_motor = 4 THEN m1.Valor_medida\n" + //
        "             WHEN m1.MEASURETYPE = 8019 AND v.Tiempos_motor = 2 THEN m1.Valor_medida END) AS co2_ralenti,\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8004 AND v.Tiempos_motor = 4 THEN m1.Valor_medida\n" + //
        "             WHEN m1.MEASURETYPE = 8021 AND v.Tiempos_motor = 2 THEN m1.Valor_medida END) AS O2_ralenti,\n" + //
        "\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8007 THEN m1.Valor_medida END) AS HC_crucero,\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8008 THEN m1.Valor_medida END) AS CO_crucero,\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8009 THEN m1.Valor_medida END) AS CO2_crucero,\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8010 THEN m1.Valor_medida END) AS O2_crucero,\n" + //
        "    \n" + //
        "    MAX(CASE WHEN (dfp.id_defecto IN (84018, 15057, 80003, 80000))\n" + //
        "         THEN 1\n" + //
        "         ELSE 0 END) AS NC_EMISIONES,\n" + //
        "         \n" + //
        "\tp.Aprobada as RES_FINAL,\n" + //
        "    \n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8034 THEN m1.Valor_medida END) AS T_INICIAL_MOTOR,\n" + //
        "\tMAX(CASE WHEN m1.MEASURETYPE = 8037 THEN m1.Valor_medida END) AS T_FINAL_MOTOR,\n" + //
        "\n" + //
        "\tMAX(CASE WHEN m1.MEASURETYPE = 8035 THEN m1.Valor_medida END) AS RPM_RAL2,\n" + //
        "\tMAX(CASE WHEN m1.MEASURETYPE = 8036 THEN m1.Valor_medida END) AS RPM_GOB,\n" + //
        "\tMAX(CASE WHEN m1.MEASURETYPE = 8035 THEN m1.Valor_medida END) AS RPM_RAL_PRE,\n" + //
        "\tMAX(CASE WHEN m1.MEASURETYPE = 8038 THEN m1.Valor_medida END) AS RPM_GOB_PRE,\n" + //
        "\tMAX(CASE WHEN m1.MEASURETYPE = 8033 THEN m1.Valor_medida END) AS R_DEN_PRE,\n" + //
        "\n" + //
        "\tMAX(CASE WHEN m1.MEASURETYPE = 8035 THEN m1.Valor_medida END) AS RPM_RAL_C1,\n" + //
        "\tMAX(CASE WHEN m1.MEASURETYPE = 8039 THEN m1.Valor_medida END) AS RPM_GOB_C1,\n" + //
        "\tMAX(CASE WHEN m1.MEASURETYPE = 8013 THEN m1.Valor_medida END) AS R_DEN_C1,\n" + //
        "\tMAX(CASE WHEN m1.MEASURETYPE = 8035 THEN m1.Valor_medida END) AS RPM_RAL_C2,\n" + //
        "\tMAX(CASE WHEN m1.MEASURETYPE = 8040 THEN m1.Valor_medida END) AS RPM_GOB_C2,\n" + //
        "\tMAX(CASE WHEN m1.MEASURETYPE = 8014 THEN m1.Valor_medida END) AS R_DEN_C2,\n" + //
        "\tMAX(CASE WHEN m1.MEASURETYPE = 8035 THEN m1.Valor_medida END) AS RPM_RAL_C3,\n" + //
        "\tMAX(CASE WHEN m1.MEASURETYPE = 8041 THEN m1.Valor_medida END) AS RPM_GOB_C3,\n" + //
        "\tMAX(CASE WHEN m1.MEASURETYPE = 8015 THEN m1.Valor_medida END) AS R_DEN_C3,\n" + //
        "\tMAX(CASE WHEN m1.MEASURETYPE = 8017 THEN m1.Valor_medida END) AS R_FINAL_DEN\n" + //
        "\n" + //
        "FROM \n" + //
        "    hoja_pruebas AS hp\n" + //
        "    INNER JOIN pruebas AS p ON p.hoja_pruebas_for = hp.TESTSHEET\n" + //
        "    INNER JOIN usuarios AS u ON u.GEUSER = p.Usuario_for\n" + //
        "    INNER JOIN usuarios AS ur ON ur.GEUSER = hp.usuario_resp\n" + //
        "    INNER JOIN vehiculos AS v ON hp.Vehiculo_for = v.CAR\n" + //
        "    INNER JOIN lineas_vehiculos AS l ON v.CARLINE = l.CARLINE\n" + //
        "    INNER JOIN clases_vehiculo AS cv ON cv.CLASS = v.CLASS\n" + //
        "    INNER JOIN servicios AS s ON s.SERVICE = v.SERVICE\n" + //
        "    INNER JOIN propietarios AS pro ON (v.CAROWNER = pro.CAROWNER)\n" + //
        "    INNER JOIN tipos_gasolina AS tg ON tg.FUELTYPE = v.FUELTYPE\n" + //
        "    INNER JOIN ciudades AS ci ON (ci.CITY = pro.CITY)\n" + //
        "    INNER JOIN marcas AS m ON m.CARMARK = v.CARMARK\n" + //
        "    left JOIN equipos AS e ON e.serialresolucion = p.serialEquipo\n" + //
        "    INNER JOIN certificados AS c ON hp.TESTSHEET = c.TESTSHEET\n" + //
        "    LEFT JOIN medidas AS m1 ON p.Id_Pruebas = m1.TEST\n" + //
        "    LEFT JOIN defxprueba AS dfp ON dfp.id_prueba = p.Id_Pruebas\n" + //
        "    LEFT JOIN defectos AS df ON dfp.id_defecto = df.CARDEFAULT\n" + //
        "    INNER JOIN ciudades AS cy ON cy.CITY = pro.CITY\n" + //
        "\n" + //
        "WHERE \n" + //
        "    p.Tipo_prueba_for = 8\n" + //
        "    AND p.Fecha_prueba BETWEEN ? AND ?\n" + //
        tipo+" "+
        "GROUP BY \n" + //
        "    p.Id_Pruebas\n" + //
        "ORDER BY \n" + //
        "    p.Fecha_prueba ASC;";
    }

    public static String getCormacarenaMotos(){
        return
        "SELECT \r\n" + //
        "    p.Id_Pruebas,\r\n" + //
        "    mr.Nombre_marca,\r\n" + //
        "    v.Modelo,\r\n" + //
        "    v.Tiempos_motor,\r\n" + //
        "    \r\n" + //
        "    MAX(\r\n" + //
        "        CASE \r\n" + //
        "            WHEN m1.MEASURETYPE = 8001 AND v.Tiempos_motor = 4 THEN m1.Valor_medida\r\n" + //
        "            WHEN m1.MEASURETYPE = 8018 AND v.Tiempos_motor = 2 THEN m1.Valor_medida \r\n" + //
        "        END\r\n" + //
        "    ) AS hc_ralenti,\r\n" + //
        "    \r\n" + //
        "    MAX(\r\n" + //
        "        CASE \r\n" + //
        "            WHEN m1.MEASURETYPE = 8002 AND v.Tiempos_motor = 4 THEN m1.Valor_medida\r\n" + //
        "            WHEN m1.MEASURETYPE = 8020 AND v.Tiempos_motor = 2 THEN m1.Valor_medida \r\n" + //
        "        END\r\n" + //
        "    ) AS co_ralenti,\r\n" + //
        "    \r\n" + //
        "    MAX(\r\n" + //
        "        CASE \r\n" + //
        "            WHEN m1.MEASURETYPE = 8003 AND v.Tiempos_motor = 4 THEN m1.Valor_medida\r\n" + //
        "            WHEN m1.MEASURETYPE = 8019 AND v.Tiempos_motor = 2 THEN m1.Valor_medida \r\n" + //
        "        END\r\n" + //
        "    ) AS co2_ralenti,\r\n" + //
        "    \r\n" + //
        "    MAX(\r\n" + //
        "        CASE \r\n" + //
        "            WHEN m1.MEASURETYPE = 8004 AND v.Tiempos_motor = 4 THEN m1.Valor_medida\r\n" + //
        "            WHEN m1.MEASURETYPE = 8021 AND v.Tiempos_motor = 2 THEN m1.Valor_medida \r\n" + //
        "        END\r\n" + //
        "    ) AS O2_ralenti,\r\n" + //
        "    \r\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8031 THEN m1.Valor_medida END) AS temperatura_ambiente,\r\n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8032 THEN m1.Valor_medida END) AS humedad_relativa,\r\n" + //
        "    \r\n" + //
        "    p.Aprobada AS resultado_prueba,\r\n" + //
        "\r\n" + //
        "    -- Subconsulta para obtener el ruido solo si la prueba es de ruido\r\n" + //
        "    (\r\n" + //
        "        SELECT \r\n" + //
        "            MAX(m2.Valor_medida)\r\n" + //
        "        FROM \r\n" + //
        "            pruebas p2\r\n" + //
        "            LEFT JOIN medidas m2 ON p2.Id_Pruebas = m2.TEST\r\n" + //
        "        WHERE \r\n" + //
        "            p2.hoja_pruebas_for = h.TESTSHEET\r\n" + //
        "            AND m2.MEASURETYPE IN (7003, 7004, 7005)\r\n" + //
        "            AND p2.Tipo_prueba_for = 7\r\n" + //
        "    ) AS ruido,\r\n" + //
        "\r\n" + //
        "    p.Fecha_prueba,\r\n" + //
        "    v.CARPLATE\r\n" + //
        "\r\n" + //
        "FROM \r\n" + //
        "    hoja_pruebas h\r\n" + //
        "    INNER JOIN certificados c ON c.TESTSHEET = h.TESTSHEET\r\n" + //
        "    INNER JOIN vehiculos v ON v.CAR = h.Vehiculo_for\r\n" + //
        "    INNER JOIN clases_vehiculo vc ON vc.CLASS = v.CLASS\r\n" + //
        "    INNER JOIN servicios s ON s.SERVICE = v.SERVICE\r\n" + //
        "    INNER JOIN tipos_gasolina tg ON tg.FUELTYPE = v.FUELTYPE\r\n" + //
        "    LEFT JOIN pruebas p ON h.TESTSHEET = p.hoja_pruebas_for\r\n" + //
        "    LEFT JOIN medidas AS m1 ON p.Id_Pruebas = m1.TEST\r\n" + //
        "    INNER JOIN marcas mr ON mr.CARMARK = v.CARMARK\r\n" + //
        "\r\n" + //
        "WHERE \r\n" + //
        "    DATE(h.Fecha_ingreso_vehiculo) BETWEEN ? AND ?\r\n" + //
        "    AND h.preventiva = 'N'\r\n" + //
        "    AND h.Finalizada = 'Y'\r\n" + //
        "    AND h.estado_sicov = 'SINCRONIZADO'\r\n" + //
        "    AND p.Tipo_prueba_for IN (8)\r\n" + //
        "    AND v.CLASS = 10\r\n" + //
        "\r\n" + //
        "GROUP BY \r\n" + //
        "    p.Id_Pruebas\r\n" + //
        "\r\n" + //
        "ORDER BY \r\n" + //
        "    p.Fecha_prueba;";
    }

    public static String getCormacarenaCarros(){
        return
        "SELECT \n"+
        "    mr.Nombre_marca,\n"+
        "    v.Modelo,\n"+
        "    v.Cinlindraje,\n"+
        "    vc.Nombre_clase,\n"+
        "    s.Nombre_servicio,\n"+
            
        "    MAX(CASE WHEN m1.MEASURETYPE = 8001 THEN m1.Valor_medida END) AS HC_ralenti,\n"+
        "    MAX(CASE WHEN m1.MEASURETYPE = 8002 THEN m1.Valor_medida END) AS CO_ralenti,\n"+
        "    MAX(CASE WHEN m1.MEASURETYPE = 8003 THEN m1.Valor_medida END) AS CO2_ralenti,\n"+
        "    MAX(CASE WHEN m1.MEASURETYPE = 8004 THEN m1.Valor_medida END) AS O2_ralenti,\n"+
        "    MAX(CASE WHEN m1.MEASURETYPE = 8011 THEN m1.Valor_medida END) AS RPM_crucero,\n"+
        "    MAX(CASE WHEN m1.MEASURETYPE = 8007 THEN m1.Valor_medida END) AS HC_crucero,\n"+
        "    MAX(CASE WHEN m1.MEASURETYPE = 8008 THEN m1.Valor_medida END) AS CO_crucero,\n"+
        "    MAX(CASE WHEN m1.MEASURETYPE = 8009 THEN m1.Valor_medida END) AS CO2_crucero,\n"+
        "    MAX(CASE WHEN m1.MEASURETYPE = 8010 THEN m1.Valor_medida END) AS O2_crucero,\n"+
            
        "    p.Aprobada AS resultado_prueba, \n"+
        "    MAX(CASE WHEN m1.MEASURETYPE = 7003 \n"+
        "                OR m1.MEASURETYPE = 7004 \n"+
        "                OR m1.MEASURETYPE = 7005 THEN m1.Valor_medida END) AS ruido,\n"+
        "    p.Fecha_prueba,\n"+
        "    v.CARPLATE\n"+

        "FROM \n"+
        "    hoja_pruebas h\n"+
            
        "    INNER JOIN certificados c ON c.TESTSHEET = h.TESTSHEET\n"+
        "    INNER JOIN vehiculos v ON v.CAR = h.Vehiculo_for\n"+
        "    INNER JOIN clases_vehiculo vc ON vc.CLASS = v.CLASS\n"+
        "    INNER JOIN servicios s ON s.SERVICE = v.SERVICE\n"+
        "    INNER JOIN tipos_gasolina tg ON tg.FUELTYPE = v.FUELTYPE\n"+
        "    LEFT JOIN pruebas p ON h.TESTSHEET = p.hoja_pruebas_for\n"+
        "    LEFT JOIN medidas AS m1 ON p.Id_Pruebas = m1.TEST\n"+
        "    INNER JOIN marcas mr ON mr.CARMARK = v.CARMARK\n"+
        "WHERE \n"+
        "    DATE(h.Fecha_ingreso_vehiculo) BETWEEN '2024-01-02' AND '2024-01-29'\n"+
        "    AND h.preventiva = 'N'\n"+
        "    AND h.Finalizada = 'Y' \n"+
        "    AND h.estado_sicov = 'SINCRONIZADO'\n"+
        "    AND p.Tipo_prueba_for IN (8,7)\n"+
        "    AND v.CLASS not in(10)\n"+
        "GROUP BY \n"+
        "    c.CERTIFICATE, DATE(h.Fecha_ingreso_vehiculo),\n"+
        "    c.CONSECUTIVE, vc.Nombre_clase, s.Nombre_servicio, v.CARPLATE,\n"+
        "    v.Modelo, tg.Nombre_gasolina, v.Cinlindraje, v.kilometraje\n"+
        "order by p.Fecha_prueba;";
    }

    public static String getVigia(){
        return
        "SELECT \n" + //
        "\th.con_hoja_prueba as numeroFormato,\n" + //
        "    p.Fecha_prueba,\n" + //
        "    p.Aprobada,\n" + //
        "    h.con_hoja_prueba as CONSECUTIVE,\n" + //
        "    h.consecutivo_runt as consecutivo_runt,\n" + //
        "    v.CARPLATE,\n" + //
        "    s.Nombre_servicio,\n" + //
        "    vc.Nombre_clase,\n" + //
        "    mr.Nombre_marca,\n" + //
        "    lv.CRLNAME,\n" + //
        "    v.Modelo,\n" + //
        "    v.Fecha_registro,\n" + //
        "    tg.Nombre_gasolina,\n" + //
        "    v.Tiempos_motor,\n" + //
        "    \n" + //
        "    (\n" + //
        "        SELECT MAX(m2.Valor_medida)\n" + //
        "        FROM pruebas p2\n" + //
        "        LEFT JOIN medidas m2 ON p2.Id_Pruebas = m2.TEST\n" + //
        "        WHERE \n" + //
        "            p2.hoja_pruebas_for = h.TESTSHEET\n" + //
        "            AND m2.MEASURETYPE IN (7003, 7004, 7005)\n" + //
        "            AND p2.Tipo_prueba_for = 7\n" + //
        "    ) AS ruido,\n" + //
        "    \n" + //
        "    (\n" + //
        "        SELECT MAX(m2.Valor_medida)\n" + //
        "        FROM pruebas p2\n" + //
        "        LEFT JOIN medidas m2 ON p2.Id_Pruebas = m2.TEST\n" + //
        "        WHERE \n" + //
        "            p2.hoja_pruebas_for = h.TESTSHEET\n" + //
        "            AND m2.MEASURETYPE IN (2014, 2024)\n" + //
        "    ) AS intensidad_luz_der,\n" + //
        "    \n" + //
        "    (\n" + //
        "        SELECT MAX(m2.Valor_medida)\n" + //
        "        FROM pruebas p2\n" + //
        "        LEFT JOIN medidas m2 ON p2.Id_Pruebas = m2.TEST\n" + //
        "        WHERE \n" + //
        "            p2.hoja_pruebas_for = h.TESTSHEET\n" + //
        "            AND m2.MEASURETYPE IN (2031)\n" + //
        "    ) AS intensidad_luz_izq,\n" + //
        "    \n" + //
        "    (\n" + //
        "        SELECT MAX(m2.Valor_medida)\n" + //
        "        FROM pruebas p2\n" + //
        "        LEFT JOIN medidas m2 ON p2.Id_Pruebas = m2.TEST\n" + //
        "        WHERE \n" + //
        "            p2.hoja_pruebas_for = h.TESTSHEET\n" + //
        "            AND m2.MEASURETYPE IN (2040, 2013)\n" + //
        "    ) AS inclinacion_luz_der,\n" + //
        "    \n" + //
        "    (\n" + //
        "        SELECT MAX(m2.Valor_medida)\n" + //
        "        FROM pruebas p2\n" + //
        "        LEFT JOIN medidas m2 ON p2.Id_Pruebas = m2.TEST\n" + //
        "        WHERE \n" + //
        "            p2.hoja_pruebas_for = h.TESTSHEET\n" + //
        "            AND m2.MEASURETYPE IN (2044)\n" + //
        "    ) AS inclinacion_luz_izq,\n" + //
        "    \n" + //
        "    (\n" + //
        "        SELECT MAX(m2.Valor_medida)\n" + //
        "        FROM pruebas p2\n" + //
        "        LEFT JOIN medidas m2 ON p2.Id_Pruebas = m2.TEST\n" + //
        "        WHERE \n" + //
        "            p2.hoja_pruebas_for = h.TESTSHEET\n" + //
        "            AND m2.MEASURETYPE IN (2011)\n" + //
        "    ) AS suma_intensidades,\n" + //
        "    \n" + //
        "    (\n" + //
        "        SELECT MAX(m2.Valor_medida)\n" + //
        "        FROM pruebas p2\n" + //
        "        LEFT JOIN medidas m2 ON p2.Id_Pruebas = m2.TEST\n" + //
        "        WHERE \n" + //
        "            p2.hoja_pruebas_for = h.TESTSHEET\n" + //
        "            AND m2.MEASURETYPE IN (6016)\n" + //
        "    ) AS delantera_derecha,\n" + //
        "    \n" + //
        "    (\n" + //
        "        SELECT MAX(m2.Valor_medida)\n" + //
        "        FROM pruebas p2\n" + //
        "        LEFT JOIN medidas m2 ON p2.Id_Pruebas = m2.TEST\n" + //
        "        WHERE \n" + //
        "            p2.hoja_pruebas_for = h.TESTSHEET\n" + //
        "            AND m2.MEASURETYPE IN (6020)\n" + //
        "    ) AS delantera_izquierda,\n" + //
        "    \n" + //
        "    (\n" + //
        "        SELECT MAX(m2.Valor_medida)\n" + //
        "        FROM pruebas p2\n" + //
        "        LEFT JOIN medidas m2 ON p2.Id_Pruebas = m2.TEST\n" + //
        "        WHERE \n" + //
        "            p2.hoja_pruebas_for = h.TESTSHEET\n" + //
        "            AND m2.MEASURETYPE IN (6017)\n" + //
        "    ) AS trasera_derecha,\n" + //
        "    \n" + //
        "    (\n" + //
        "        SELECT MAX(m2.Valor_medida)\n" + //
        "        FROM pruebas p2\n" + //
        "        LEFT JOIN medidas m2 ON p2.Id_Pruebas = m2.TEST\n" + //
        "        WHERE \n" + //
        "            p2.hoja_pruebas_for = h.TESTSHEET\n" + //
        "            AND m2.MEASURETYPE IN (6021)\n" + //
        "    ) AS trasera_izquierda,\n" + //
        "    \n" + //
        "    (\n" + //
        "        SELECT MAX(m2.Valor_medida)\n" + //
        "        FROM pruebas p2\n" + //
        "        LEFT JOIN medidas m2 ON p2.Id_Pruebas = m2.TEST\n" + //
        "        WHERE \n" + //
        "            p2.hoja_pruebas_for = h.TESTSHEET\n" + //
        "            AND m2.MEASURETYPE IN (5024)\n" + //
        "    ) AS eficacia_total,\n" + //
        "    \n" + //
        "    (\n" + //
        "        SELECT MAX(m2.Valor_medida)\n" + //
        "        FROM pruebas p2\n" + //
        "        LEFT JOIN medidas m2 ON p2.Id_Pruebas = m2.TEST\n" + //
        "        WHERE \n" + //
        "            p2.hoja_pruebas_for = h.TESTSHEET\n" + //
        "            AND m2.MEASURETYPE IN (5036)\n" + //
        "    ) AS eficacia_auxiliar,\n" + //
        "    \n" + //
        "    (\n" + //
        "        SELECT MAX(m2.Valor_medida)\n" + //
        "        FROM pruebas p2\n" + //
        "        LEFT JOIN medidas m2 ON p2.Id_Pruebas = m2.TEST\n" + //
        "        WHERE \n" + //
        "            p2.hoja_pruebas_for = h.TESTSHEET\n" + //
        "            AND m2.MEASURETYPE IN (5008)\n" + //
        "    ) AS fuerza_eje_der_1,\n" + //
        "    \n" + //
        "    (\n" + //
        "        SELECT MAX(m2.Valor_medida)\n" + //
        "        FROM pruebas p2\n" + //
        "        LEFT JOIN medidas m2 ON p2.Id_Pruebas = m2.TEST\n" + //
        "        WHERE \n" + //
        "            p2.hoja_pruebas_for = h.TESTSHEET\n" + //
        "            AND m2.MEASURETYPE IN (5012)\n" + //
        "    ) AS fuerza_eje_izq_1,\n" + //
        "    \n" + //
        "    (\n" + //
        "        SELECT MAX(m2.Valor_medida)\n" + //
        "        FROM pruebas p2\n" + //
        "        LEFT JOIN medidas m2 ON p2.Id_Pruebas = m2.TEST\n" + //
        "        WHERE \n" + //
        "            p2.hoja_pruebas_for = h.TESTSHEET\n" + //
        "            AND m2.MEASURETYPE IN (5009)\n" + //
        "    ) AS fuerza_eje_der_2,\n" + //
        "    \n" + //
        "    (\n" + //
        "        SELECT MAX(m2.Valor_medida)\n" + //
        "        FROM pruebas p2\n" + //
        "        LEFT JOIN medidas m2 ON p2.Id_Pruebas = m2.TEST\n" + //
        "        WHERE \n" + //
        "            p2.hoja_pruebas_for = h.TESTSHEET\n" + //
        "            AND m2.MEASURETYPE IN (5013)\n" + //
        "    ) AS fuerza_eje_izq_2,\n" + //
        "    \n" + //
        "    \n" + //
        "    (\n" + //
        "        SELECT MAX(m2.Valor_medida)\n" + //
        "        FROM pruebas p2\n" + //
        "        LEFT JOIN medidas m2 ON p2.Id_Pruebas = m2.TEST\n" + //
        "        WHERE \n" + //
        "            p2.hoja_pruebas_for = h.TESTSHEET\n" + //
        "            AND m2.MEASURETYPE IN (5000)\n" + //
        "    ) AS peso_eje_der_1,\n" + //
        "    \n" + //
        "    (\n" + //
        "        SELECT MAX(m2.Valor_medida)\n" + //
        "        FROM pruebas p2\n" + //
        "        LEFT JOIN medidas m2 ON p2.Id_Pruebas = m2.TEST\n" + //
        "        WHERE \n" + //
        "            p2.hoja_pruebas_for = h.TESTSHEET\n" + //
        "            AND m2.MEASURETYPE IN (5004)\n" + //
        "    ) AS peso_eje_izq_1,\n" + //
        "    \n" + //
        "    (\n" + //
        "        SELECT MAX(m2.Valor_medida)\n" + //
        "        FROM pruebas p2\n" + //
        "        LEFT JOIN medidas m2 ON p2.Id_Pruebas = m2.TEST\n" + //
        "        WHERE \n" + //
        "            p2.hoja_pruebas_for = h.TESTSHEET\n" + //
        "            AND m2.MEASURETYPE IN (5001)\n" + //
        "    ) AS peso_eje_der_2,\n" + //
        "    \n" + //
        "    (\n" + //
        "        SELECT MAX(m2.Valor_medida)\n" + //
        "        FROM pruebas p2\n" + //
        "        LEFT JOIN medidas m2 ON p2.Id_Pruebas = m2.TEST\n" + //
        "        WHERE \n" + //
        "            p2.hoja_pruebas_for = h.TESTSHEET\n" + //
        "            AND m2.MEASURETYPE IN (5005)\n" + //
        "    ) AS peso_eje_izq_2,\n" + //
        "    \n" + //
        "    (\n" + //
        "        SELECT MAX(m2.Valor_medida)\n" + //
        "        FROM pruebas p2\n" + //
        "        LEFT JOIN medidas m2 ON p2.Id_Pruebas = m2.TEST\n" + //
        "        WHERE \n" + //
        "            p2.hoja_pruebas_for = h.TESTSHEET\n" + //
        "            AND m2.MEASURETYPE IN (4000)\n" + //
        "    ) AS desviacion_eje_1,\n" + //
        "    \n" + //
        "    (\n" + //
        "        SELECT MAX(m2.Valor_medida)\n" + //
        "        FROM pruebas p2\n" + //
        "        LEFT JOIN medidas m2 ON p2.Id_Pruebas = m2.TEST\n" + //
        "        WHERE \n" + //
        "            p2.hoja_pruebas_for = h.TESTSHEET\n" + //
        "            AND m2.MEASURETYPE IN (4001)\n" + //
        "    ) AS desviacion_eje_2,\n" + //
        "    \n" + //
        "    v.diseño,\n" + //
        "    \n" + //
        "    MAX(CASE WHEN m1.MEASURETYPE = 8006 AND v.Tiempos_motor = 4 THEN m1.Valor_medida\n" + //
        "             WHEN m1.MEASURETYPE = 8022 AND v.Tiempos_motor = 2 THEN m1.Valor_medida \n" + //
        "\t\tEND) AS temperatura_motor,\n" + //
        "        \n" + //
        "\tMAX(CASE WHEN m1.MEASURETYPE = 8005 AND v.Tiempos_motor = 4 THEN m1.Valor_medida\n" + //
        "             WHEN m1.MEASURETYPE = 8028 AND v.Tiempos_motor = 2 THEN m1.Valor_medida END) AS rpm_ralenti,\n" + //
        "    \n" + //
        "    MAX(CASE \n" + //
        "            WHEN m1.MEASURETYPE = 8001 AND v.Tiempos_motor = 4 THEN m1.Valor_medida\n" + //
        "            WHEN m1.MEASURETYPE = 8018 AND v.Tiempos_motor = 2 THEN m1.Valor_medida\n" + //
        "        END) AS hc_ralenti,\n" + //
        "    \n" + //
        "    MAX(CASE \n" + //
        "            WHEN m1.MEASURETYPE = 8002 AND v.Tiempos_motor = 4 THEN m1.Valor_medida\n" + //
        "            WHEN m1.MEASURETYPE = 8020 AND v.Tiempos_motor = 2 THEN m1.Valor_medida\n" + //
        "        END) AS co_ralenti,\n" + //
        "    \n" + //
        "    MAX(CASE \n" + //
        "            WHEN m1.MEASURETYPE = 8003 AND v.Tiempos_motor = 4 THEN m1.Valor_medida\n" + //
        "            WHEN m1.MEASURETYPE = 8019 AND v.Tiempos_motor = 2 THEN m1.Valor_medida\n" + //
        "        END) AS co2_ralenti,\n" + //
        "    \n" + //
        "    MAX(CASE \n" + //
        "            WHEN m1.MEASURETYPE = 8004 AND v.Tiempos_motor = 4 THEN m1.Valor_medida\n" + //
        "            WHEN m1.MEASURETYPE = 8021 AND v.Tiempos_motor = 2 THEN m1.Valor_medida\n" + //
        "        END) AS O2_ralenti,\n" + //
        "        \n" + //
        "\tMAX(CASE WHEN m1.MEASURETYPE = 8011 AND v.Tiempos_motor = 4 THEN m1.Valor_medida\n" + //
        "             WHEN m1.MEASURETYPE = 8029 AND v.Tiempos_motor = 2 THEN m1.Valor_medida END) AS rpm_crucero,\n" + //
        "\tMAX(CASE WHEN m1.MEASURETYPE = 8007 THEN m1.Valor_medida END) AS HC_crucero,\n" + //
        "\tMAX(CASE WHEN m1.MEASURETYPE = 8008 THEN m1.Valor_medida END) AS CO_crucero,\n" + //
        "\tMAX(CASE WHEN m1.MEASURETYPE = 8009 THEN m1.Valor_medida END) AS CO2_crucero,\n" + //
        "\tMAX(CASE WHEN m1.MEASURETYPE = 8010 THEN m1.Valor_medida END) AS O2_crucero,\n" + //
        "h.Numero_intentos\n" + //
        "FROM \n" + //
        "    hoja_pruebas h\n" + //
        "    LEFT JOIN certificados c ON c.TESTSHEET = h.TESTSHEET\n" + //
        "    LEFT JOIN vehiculos v ON v.CAR = h.Vehiculo_for\n" + //
        "    LEFT JOIN clases_vehiculo vc ON vc.CLASS = v.CLASS\n" + //
        "    LEFT JOIN servicios s ON s.SERVICE = v.SERVICE\n" + //
        "    LEFT JOIN tipos_gasolina tg ON tg.FUELTYPE = v.FUELTYPE\n" + //
        "    LEFT JOIN (\r\n" + //
        "       SELECT *\r\n" + //
        "       FROM pruebas p1\r\n" + //
        "       WHERE p1.Fecha_prueba = (\r\n" + //
        "           SELECT MAX(p2.Fecha_prueba)\r\n" + //
        "           FROM pruebas p2\r\n" + //
        "           WHERE p2.hoja_pruebas_for = p1.hoja_pruebas_for\r\n" + //
        "           AND p2.Tipo_prueba_for = p1.Tipo_prueba_for\r\n" + //
        "       )\r\n" + //
        "    ) p ON h.TESTSHEET = p.hoja_pruebas_for" + //
        "    LEFT JOIN medidas AS m1 ON p.Id_Pruebas = m1.TEST\n" + //
        "    LEFT JOIN marcas mr ON mr.CARMARK = v.CARMARK\n" + //
        "    LEFT JOIN lineas_vehiculos lv ON lv.CARLINE = v.CARLINE\n" + //
        "\t\n" + //
        "WHERE \n" + //
        "    DATE(h.Fecha_ingreso_vehiculo) BETWEEN ? AND ?\n" + //
        "    AND h.preventiva = 'N'\n" + //
        "    AND h.Finalizada = 'Y'\n" + //
        "    AND h.estado_sicov = 'SINCRONIZADO'\n" + //
        "    AND p.Tipo_prueba_for IN (8)\n" + //
        "    AND v.diseño in('Convencional','Scooter')\n" + //
        "\n" + //
        "GROUP BY \n" + //
        "    p.Id_Pruebas\n" + //
        "\n" + //
        "ORDER BY \n" + //
        "    p.Fecha_prueba;";
    }

    public static String getAbortos(){
        return
        "SELECT \n" + //
        "tp.Nombre_tipo_prueba, p.Fecha_aborto, p.observaciones, u.Nombre_usuario, p.Comentario_aborto\n" + //
        "FROM pruebas p \n" + //
        "INNER JOIN tipo_prueba tp ON tp.TESTTYPE = p.Tipo_prueba_for\n" + //
        "INNER JOIN usuarios u ON u.GEUSER = p.usuario_for\n" + //
        "where p.Abortada <> 'N' \n" + //
        "AND p.observaciones is not null \n" + //
        "AND p.Fecha_aborto not like '%192.168%'\n" + //
        "AND p.Fecha_aborto not like '%;%'\n" + //
        "AND p.Fecha_aborto <> ''\n" + //
        "AND p.Fecha_prueba between ? and ?\n" + //
        "group by p.Id_pruebas\n" + //
        "order by p.Fecha_aborto;";
    }

    public static String getFugas(){
        return
        "select e.num_analizador, e.serialresolucion, e.pef, c.CURDATE, u.Nombre_usuario, c.aprobada from calibraciones c\r\n" + //
        "inner join equipos e on e.id_equipo = c.id_equipo \r\n" + //
        "inner join usuarios u on u.GEUSER = c.GEUSER \r\n" + //
        "where c.CURDATE between ? and ? \r\n" + //
        "and id_tipo_calibracion = 3 order by c.CURDATE desc;";
    }

    public static String getClientes(){
        return
        "select\r\n" + //
        "  v.CARPLATE, p.Nombres, p.Apellidos, p.Numero_telefono, p.celular, m.Nombre_marca, lv.CRLNAME as nombre_linea, \r\n" + //
        "  hp.Fecha_ingreso_vehiculo, v.Modelo, tv.Nombre as tipo_vehiculo, cv.Nombre_clase, tg.Nombre_gasolina, s.Nombre_servicio,\r\n" + //
        "  v.numero_chasis\r\n" + //
        "  from hoja_pruebas hp \r\n" + //
        "  inner join vehiculos v on v.CAR = hp.Vehiculo_for\r\n" + //
        "  inner join propietarios p on p.CAROWNER = v.CAROWNER\r\n" + //
        "  inner join tipo_vehiculo tv on tv.CARTYPE = v.CARTYPE\r\n" + //
        "  inner join clases_vehiculo cv on cv.CLASS = v.CLASS\r\n" + //
        "  inner join tipos_gasolina tg on tg.FUELTYPE = v.FUELTYPE\r\n" + //
        "  inner join servicios s on s.SERVICE = v.SERVICE\r\n" + //
        "  inner join marcas m on m.CARMARK = v.CARMARK\r\n" + //
        "  inner join lineas_vehiculos lv on lv.CARLINE = v.CARLINE\r\n" + //
        "\twhere hp.Fecha_ingreso_vehiculo between ? and ?\r\n" + //
        "    group by v.CARPLATE\r\n" + //
        "    order by hp.Fecha_ingreso_vehiculo asc;";
    }
}
