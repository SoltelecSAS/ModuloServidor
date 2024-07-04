/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soltelec.servidor.dao;

import com.soltelec.servidor.dao.exceptions.NonexistentEntityException;
import com.soltelec.servidor.model.CalibracionLinealidad;
import com.soltelec.servidor.model.Calibraciones;
import com.soltelec.servidor.conexion.PersistenceController;
import com.soltelec.servidor.model.CalibracionDosPuntos;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author GerenciaDesarrollo
 */
public class CalibracionesJpaController {

    private EntityManager getEntityManager() {
        return PersistenceController.getEntityManager();
    }

    public void create(Calibraciones calibraciones) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(calibraciones);
            em.getTransaction().commit();
        } finally {
        }
    }

    public void edit(Calibraciones calibraciones) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            calibraciones = em.merge(calibraciones);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = calibraciones.getCalibration();
                if (findCalibraciones(id) == null) {
                    throw new NonexistentEntityException("The calibraciones with id " + id + " no longer exists.");
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
            Calibraciones calibraciones;
            try {
                calibraciones = em.getReference(Calibraciones.class, id);
                calibraciones.getCalibration();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The calibraciones with id " + id + " no longer exists.", enfe);
            }
            em.remove(calibraciones);
            em.getTransaction().commit();
        } finally {
        }
    }

    public List<Calibraciones> findCalibracionesEntities() {
        return findCalibracionesEntities(true, -1, -1);
    }

    public List<Calibraciones> findCalibracionesEntities(int maxResults, int firstResult) {
        return findCalibracionesEntities(false, maxResults, firstResult);
    }

    private List<Calibraciones> findCalibracionesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Calibraciones.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {

        }
    }

    public Calibraciones findCalibraciones(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Calibraciones.class, id);
        } finally {

        }
    }

    public int getCalibracionesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Calibraciones> rt = cq.from(Calibraciones.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {

        }
    }

    public List<Calibraciones> findFugas(Date com1, Date com2) {
        EntityManager em = getEntityManager();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String fechaInicial = sdf.format(com1);
        String fechaFinal = sdf.format(com2) + " 23:59:59";
        String msql = "SELECT c FROM Calibraciones c WHERE (c.fecha >= '" + fechaInicial + "' and c.fecha <= '" + fechaFinal + "') AND c.tipoCalibracion.idTipoCalibracion = 3";
        TypedQuery query = em.createQuery(msql, Calibraciones.class);
        return query.getResultList();
    }

    public List<CalibracionLinealidad> findCalibracionesDiesel(Date com1, Date com2) {
        EntityManager em = getEntityManager();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String fechaInicial = sdf.format(com1);
        String fechaFinal = sdf.format(com2) + " 23:59:59";
//        String msql = "SELECT c FROM CalibracionLinealidad c WHERE (c.fecha >= '" + fechaInicial + "' and c.fecha <= '" + fechaFinal + "')";
        String msql = "SELECT * FROM calibraciones c WHERE (c.CURDATE >= '" + fechaInicial + "' and c.CURDATE <= '" + fechaFinal + "')";
//        String msql = "SELECT c FROM CalibracionLinealidad c WHERE (c.fecha >= '" + fechaInicial + "' and c.fecha <= '" + fechaFinal + "')";
//        TypedQuery<CalibracionLinealidad> query = em.createQuery(msql, CalibracionLinealidad.class);
        Query q = em.createNativeQuery(msql, Calibraciones.class);
        return q.getResultList();
    }

    public List<Calibraciones> findCalibracionesByFecha(Date com1, Date com2) {
        EntityManager em = getEntityManager();
        List<Calibraciones> auxCalibraciones = new ArrayList<>();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String fechaInicial = sdf.format(com1) + " 00:00:00";
        String fechaFinal = sdf.format(com2) + " 23:59:59";
        String msql = "SELECT c FROM Calibraciones c WHERE c.tipoCalibracion.idTipoCalibracion = 2 AND (c.fecha >= '" + fechaInicial + "' AND c.fecha < '" + fechaFinal + "') ORDER BY c.fecha ASC";
//AND c.equipo.idEquipo = 1        
// divecol limitada String msql = "SELECT c FROM Calibraciones c WHERE c.tipoCalibracion.idTipoCalibracion = 2 AND c.equipo.idEquipo = 6 AND (c.fecha >= '" + fechaInicial + "' AND c.fecha < '" + fechaFinal + "') ORDER BY c.fecha ASC";
        System.out.println(msql);
        Query query = em.createQuery(msql);
        for (Calibraciones calibraciones : (List<Calibraciones>) query.getResultList()) {
            if (calibraciones.isAprobada()) {
                auxCalibraciones.add(calibraciones);
            }
        }

        return auxCalibraciones;

    }

    public List<Calibraciones> findCalibracionFechaEquipo(String fecha, Integer equipo, Integer tipoCalibracion) {
        EntityManager em = getEntityManager();
        List<Calibraciones> clb = null;
        try {
            String consulta = "SELECT c FROM Calibraciones c WHERE c.equipo.idEquipo = " + equipo + " AND c.tipoCalibracion.idTipoCalibracion =" + tipoCalibracion + " AND c.fecha < '" + fecha + "' ORDER BY c.fecha DESC";
            Query q = em.createQuery(consulta);
            q.setMaxResults(5);
            clb = q.getResultList();
            return clb;

        } catch (Exception e) {
            return clb;
        }
    }

    //Hermes
    public List<Calibraciones> findCalibracionesByFechaBefore(int idEquipo, Date com) {

        try {

            EntityManager em = getEntityManager();
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String fecha = sdf.format(com);
            String msql = "SELECT c FROM Calibraciones c WHERE c.tipoCalibracion.idTipoCalibracion = 2 AND (c.fecha BETWEEN '" + fecha + "')";
            System.out.println(msql);
            Query query = em.createQuery(msql);
            return query.getResultList();

        } catch (Exception ex) {
            return null;
        }

    }

}
