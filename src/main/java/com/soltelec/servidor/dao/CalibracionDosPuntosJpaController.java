/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soltelec.servidor.dao;

import com.soltelec.servidor.dao.exceptions.NonexistentEntityException;
import com.soltelec.servidor.model.CalibracionDosPuntos;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.soltelec.servidor.model.TipoCalibracion;
import com.soltelec.servidor.model.Usuarios;
import com.soltelec.servidor.model.Equipos;
import com.soltelec.servidor.conexion.PersistenceController;
import com.soltelec.servidor.model.CalibracionDTO;
import com.soltelec.servidor.model.Calibraciones;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.TemporalType;

/**
 *
 * @author Dany
 */
public class CalibracionDosPuntosJpaController implements Serializable {

    private EntityManager getEntityManager() {
        return PersistenceController.getEntityManager();
    }

    public void create(CalibracionDosPuntos calibracionDosPuntos) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoCalibracion tipoCalibracion = calibracionDosPuntos.getTipoCalibracion();
            if (tipoCalibracion != null) {
                tipoCalibracion = em.getReference(tipoCalibracion.getClass(), tipoCalibracion.getIdTipoCalibracion());
                calibracionDosPuntos.setTipoCalibracion(tipoCalibracion);
            }
            Usuarios usuario = calibracionDosPuntos.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getGeuser());
                calibracionDosPuntos.setUsuario(usuario);
            }
            Equipos equipo = calibracionDosPuntos.getEquipo();
            if (equipo != null) {
                equipo = em.getReference(equipo.getClass(), equipo.getIdEquipo());
                calibracionDosPuntos.setEquipo(equipo);
            }
            em.persist(calibracionDosPuntos);
            if (tipoCalibracion != null) {
                tipoCalibracion.getCalibracionesList().add(calibracionDosPuntos);
                tipoCalibracion = em.merge(tipoCalibracion);
            }
            if (usuario != null) {
                usuario.getCalibracionesList().add(calibracionDosPuntos);
                usuario = em.merge(usuario);
            }
            if (equipo != null) {
                equipo.getCalibracionesList().add(calibracionDosPuntos);
                equipo = em.merge(equipo);
            }
            em.getTransaction().commit();
        } finally {
        }
    }

    public void edit(CalibracionDosPuntos calibracionDosPuntos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CalibracionDosPuntos persistentCalibracionDosPuntos = em.find(CalibracionDosPuntos.class, calibracionDosPuntos.getCalibration());
            TipoCalibracion tipoCalibracionOld = persistentCalibracionDosPuntos.getTipoCalibracion();
            TipoCalibracion tipoCalibracionNew = calibracionDosPuntos.getTipoCalibracion();
            Usuarios usuarioOld = persistentCalibracionDosPuntos.getUsuario();
            Usuarios usuarioNew = calibracionDosPuntos.getUsuario();
            Equipos equipoOld = persistentCalibracionDosPuntos.getEquipo();
            Equipos equipoNew = calibracionDosPuntos.getEquipo();
            if (tipoCalibracionNew != null) {
                tipoCalibracionNew = em.getReference(tipoCalibracionNew.getClass(), tipoCalibracionNew.getIdTipoCalibracion());
                calibracionDosPuntos.setTipoCalibracion(tipoCalibracionNew);
            }
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getGeuser());
                calibracionDosPuntos.setUsuario(usuarioNew);
            }
            if (equipoNew != null) {
                equipoNew = em.getReference(equipoNew.getClass(), equipoNew.getIdEquipo());
                calibracionDosPuntos.setEquipo(equipoNew);
            }
            calibracionDosPuntos = em.merge(calibracionDosPuntos);
            if (tipoCalibracionOld != null && !tipoCalibracionOld.equals(tipoCalibracionNew)) {
                tipoCalibracionOld.getCalibracionesList().remove(calibracionDosPuntos);
                tipoCalibracionOld = em.merge(tipoCalibracionOld);
            }
            if (tipoCalibracionNew != null && !tipoCalibracionNew.equals(tipoCalibracionOld)) {
                tipoCalibracionNew.getCalibracionesList().add(calibracionDosPuntos);
                tipoCalibracionNew = em.merge(tipoCalibracionNew);
            }
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.getCalibracionesList().remove(calibracionDosPuntos);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.getCalibracionesList().add(calibracionDosPuntos);
                usuarioNew = em.merge(usuarioNew);
            }
            if (equipoOld != null && !equipoOld.equals(equipoNew)) {
                equipoOld.getCalibracionesList().remove(calibracionDosPuntos);
                equipoOld = em.merge(equipoOld);
            }
            if (equipoNew != null && !equipoNew.equals(equipoOld)) {
                equipoNew.getCalibracionesList().add(calibracionDosPuntos);
                equipoNew = em.merge(equipoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = calibracionDosPuntos.getCalibration();
                if (findCalibracionDosPuntos(id) == null) {
                    throw new NonexistentEntityException("The calibracionDosPuntos with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CalibracionDosPuntos calibracionDosPuntos;
            try {
                calibracionDosPuntos = em.getReference(CalibracionDosPuntos.class, id);
                calibracionDosPuntos.getCalibration();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The calibracionDosPuntos with id " + id + " no longer exists.", enfe);
            }
            TipoCalibracion tipoCalibracion = calibracionDosPuntos.getTipoCalibracion();
            if (tipoCalibracion != null) {
                tipoCalibracion.getCalibracionesList().remove(calibracionDosPuntos);
                tipoCalibracion = em.merge(tipoCalibracion);
            }
            Usuarios usuario = calibracionDosPuntos.getUsuario();
            if (usuario != null) {
                usuario.getCalibracionesList().remove(calibracionDosPuntos);
                usuario = em.merge(usuario);
            }
            Equipos equipo = calibracionDosPuntos.getEquipo();
            if (equipo != null) {
                equipo.getCalibracionesList().remove(calibracionDosPuntos);
                equipo = em.merge(equipo);
            }
            em.remove(calibracionDosPuntos);
            em.getTransaction().commit();
        } finally {
        }
    }

    public List<CalibracionDosPuntos> findCalibracionDosPuntosEntities() {
        return findCalibracionDosPuntosEntities(true, -1, -1);
    }

    public List<CalibracionDosPuntos> findCalibracionDosPuntosEntities(int maxResults, int firstResult) {
        return findCalibracionDosPuntosEntities(false, maxResults, firstResult);
    }

    private List<CalibracionDosPuntos> findCalibracionDosPuntosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CalibracionDosPuntos.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);

                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {

        }
    }

    public CalibracionDosPuntos findCalibracionDosPuntos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CalibracionDosPuntos.class, id);
        } finally {
        }
    }

    public int getCalibracionDosPuntosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CalibracionDosPuntos> rt = cq.from(CalibracionDosPuntos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {

        }
    }

    public List<CalibracionDosPuntos> findCalibracionDosPuntosByFecha(Date com1, Date com2) {
        EntityManager em = getEntityManager();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String fechaInicial = dateFormat.format(com1);
        String fechaFinal = dateFormat.format(com2);
        String msql = "SELECT * FROM calibracion_dos_puntos AS c JOIN calibraciones AS p ON p.CALIBRATION = c.CALIBRATION WHERE ( DATE(p.CURDATE) BETWEEN '" + fechaInicial + "' and '" + fechaFinal + "');";
        System.out.println("sql que se ejecuta para calibracciones: " + msql);
        Query query = em.createNativeQuery(msql, CalibracionDosPuntos.class);
        return query.getResultList();
    }

//    public CalibracionDosPuntos findCalibracionDosPuntosByFecha(Date fechaInicial) {
//        EntityManager em = getEntityManager();
//        String msql = "SELECT c2p.* FROM calibraciones c " +
//                      "INNER JOIN calibracion_dos_puntos c2p ON c.CALIBRATION = c2p.CALIBRATION " +
//                      "WHERE c.CURDATE <= ? AND c.id_tipo_calibracion = 2 " +
//                      "ORDER BY c.CURDATE DESC LIMIT 1";
//        System.out.println("SQL que se ejecuta para calibraciones: " + msql);
//        Query query = em.createNativeQuery(msql, CalibracionDosPuntos.class);
//        query.setParameter(1, fechaInicial, TemporalType.TIMESTAMP);
//        return (CalibracionDosPuntos) query.getSingleResult();
//    }
    public Object[] findCalibracionDosPuntosByFecha(Date fechaInicial, Integer IdPrueba) {
        EntityManager em = getEntityManager();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fecha = dateFormat.format(fechaInicial);
        String sql
                = "SELECT \n"
                + "abs(DATEDIFF(c.CURDATE, ?1)) > 3 AS alarma, "
                + "    c.CURDATE,\n"
                + "	 c.aprobada,\n"
                + "    c2p.* \n"
                + "FROM \n"
                + "    calibraciones c \n"
                + "    INNER JOIN calibracion_dos_puntos c2p ON c.CALIBRATION = c2p.CALIBRATION \n"
                + "WHERE \n"
                + "    \n"
                + "     c.CURDATE <= ?1\n"
                + "    AND c.id_tipo_calibracion = 2 \n"
                + "    AND \n"
                + "    ABS (TIME_TO_SEC(TIMEDIFF(c.CURDATE, ?1))/3600)<=72\n"
                + "		and c.id_equipo = (\n"
                + "		select e.id_equipo_calibracion \n"
                + "		  from equipos e \n"
                + "		 where e.serialresolucion = (select p.serialEquipo \n"
                + "												 from pruebas p \n"
                + "												where p.Id_Pruebas = ?2))\n"
                + " ORDER BY c.CURDATE DESC "
                + " LIMIT 1 ";
        Query query = em.createNativeQuery(sql);
        query.setParameter(1, fecha);
        query.setParameter(2, IdPrueba);
        return (Object[]) query.getSingleResult();
        
    }

}
