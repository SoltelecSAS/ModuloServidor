/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soltelec.servidor.dao;

import com.soltelec.servidor.dao.exceptions.IllegalOrphanException;
import com.soltelec.servidor.dao.exceptions.NonexistentEntityException;
import com.soltelec.servidor.dao.exceptions.PreexistingEntityException;
import com.soltelec.servidor.model.LineasVehiculos;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.soltelec.servidor.model.Marcas;
import com.soltelec.servidor.conexion.PersistenceController;

/**
 *
 * @author Administrador
 */
public class LineasVehiculosJpaController {

    private EntityManager getEntityManager() {
        return PersistenceController.getEntityManager();
    }

    public void create(LineasVehiculos lineasVehiculos) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Marcas marcas = lineasVehiculos.getMarcas();
            if (marcas != null) {
                marcas = em.getReference(marcas.getClass(), marcas.getCarmark());
                lineasVehiculos.setMarcas(marcas);
            }
            em.persist(lineasVehiculos);
            if (marcas != null) {
                marcas.getLineasVehiculosCollection().add(lineasVehiculos);
                marcas = em.merge(marcas);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findLineasVehiculos(lineasVehiculos.getCarline()) != null) {
                throw new PreexistingEntityException("LineasVehiculos " + lineasVehiculos + " already exists.", ex);
            }
            throw ex;
        } finally {
           
        }
    }

    public void edit(LineasVehiculos lineasVehiculos) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LineasVehiculos persistentLineasVehiculos = em.find(LineasVehiculos.class, lineasVehiculos.getCarline());
            Marcas marcasOld = persistentLineasVehiculos.getMarcas();
            Marcas marcasNew = lineasVehiculos.getMarcas();
            if (marcasNew != null) {
                marcasNew = em.getReference(marcasNew.getClass(), marcasNew.getCarmark());
                lineasVehiculos.setMarcas(marcasNew);
            }

            if (marcasOld != null && !marcasOld.equals(marcasNew)) {
                marcasOld.getLineasVehiculosCollection().remove(lineasVehiculos);
                marcasOld = em.merge(marcasOld);
            }

            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = lineasVehiculos.getCarline();
                if (findLineasVehiculos(id) == null) {
                    throw new NonexistentEntityException("The lineasVehiculos with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LineasVehiculos lineasVehiculos;
            try {
                lineasVehiculos = em.getReference(LineasVehiculos.class, id);
                lineasVehiculos.getCarline();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The lineasVehiculos with id " + id + " no longer exists.", enfe);
            }

            Marcas marcas = lineasVehiculos.getMarcas();
            if (marcas != null) {
                marcas.getLineasVehiculosCollection().remove(lineasVehiculos);
                marcas = em.merge(marcas);
            }
            em.remove(lineasVehiculos);
            em.getTransaction().commit();
        } finally {            
        }
    }

    public List<LineasVehiculos> findLineasVehiculosEntities() {
        return findLineasVehiculosEntities(true, -1, -1);
    }

    public List<LineasVehiculos> findLineasVehiculosEntities(int maxResults, int firstResult) {
        return findLineasVehiculosEntities(false, maxResults, firstResult);
    }

    private List<LineasVehiculos> findLineasVehiculosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(LineasVehiculos.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
          
        }
    }

    public LineasVehiculos findLineasVehiculos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(LineasVehiculos.class, id);
        } finally {
           
        }
    }

    public int getLineasVehiculosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<LineasVehiculos> rt = cq.from(LineasVehiculos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
          
        }
    }

    public List<LineasVehiculos> buscarLineasPorMarca(int idMarca) {
        EntityManager em = getEntityManager();
        try {
            //select  max(CRLCOD) from lineas_vehiculos where CARMARK = 2
            Query q = em.createQuery("SELECT object(m) FROM LineasVehiculos m WHERE m.marcas.carmark = :idMarca");
            q.setParameter("idMarca", idMarca);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error " + e);
            return null;
        }
    }

    public List<LineasVehiculos> findAll() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("LineasVehiculos.findAll");
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error " + e);
            return null;
        }
    }

    public Integer buscarNumeroMayorLineas(Integer id) {
        EntityManager em = getEntityManager();
        Query q = em.createNativeQuery("select max(l.CRLCOD)  from lineas_vehiculos l where l.CARMARK = " + id);
        return Integer.parseInt(q.getSingleResult().toString());
    }

    public int ultimoIdLinea() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNativeQuery("select max(m.CARLINE)  from lineas_vehiculos m ");
            return Integer.parseInt(q.getSingleResult().toString());
        } catch (Exception e) {
            System.out.println("Error " + e);
            return 0;
        }
    }
}
