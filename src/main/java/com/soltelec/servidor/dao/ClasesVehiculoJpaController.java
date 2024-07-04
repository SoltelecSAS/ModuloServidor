/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soltelec.servidor.dao;

import com.soltelec.servidor.dao.exceptions.IllegalOrphanException;
import com.soltelec.servidor.dao.exceptions.NonexistentEntityException;
import com.soltelec.servidor.dao.exceptions.PreexistingEntityException;
import com.soltelec.servidor.model.ClasesVehiculo;
import com.soltelec.servidor.conexion.PersistenceController;
import java.util.List;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Administrador
 */
public class ClasesVehiculoJpaController {

    private EntityManager getEntityManager() {
        return PersistenceController.getEntityManager();
    }

    public void create(ClasesVehiculo clasesVehiculo) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(clasesVehiculo);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findClasesVehiculo(clasesVehiculo.getClass1()) != null) {
                throw new PreexistingEntityException("ClasesVehiculo " + clasesVehiculo + " already exists.", ex);
            }
            throw ex;
        } finally {
           
        }
    }

    public void edit(ClasesVehiculo clasesVehiculo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            clasesVehiculo = em.merge(clasesVehiculo);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = clasesVehiculo.getClass1();
                if (findClasesVehiculo(id) == null) {
                    throw new NonexistentEntityException("The clasesVehiculo with id " + id + " no longer exists.");
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
            ClasesVehiculo clasesVehiculo;
            try {
                clasesVehiculo = em.getReference(ClasesVehiculo.class, id);
                clasesVehiculo.getClass1();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The clasesVehiculo with id " + id + " no longer exists.", enfe);
            }
            em.remove(clasesVehiculo);
            em.getTransaction().commit();
        } finally {
            
        }
    }

    public List<ClasesVehiculo> findClasesVehiculoEntities() {
        return findClasesVehiculoEntities(true, -1, -1);
    }

    public List<ClasesVehiculo> findClasesVehiculoEntities(int maxResults, int firstResult) {
        return findClasesVehiculoEntities(false, maxResults, firstResult);
    }

    private List<ClasesVehiculo> findClasesVehiculoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ClasesVehiculo.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
           
        }
    }

    public ClasesVehiculo findClasesVehiculo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ClasesVehiculo.class, id);
        } finally {
            
        }
    }

    public List<ClasesVehiculo> findAll() {
        EntityManager em = getEntityManager();
        try {
            return em.createNamedQuery("ClasesVehiculo.findAll").getResultList();
        } finally {
           
        }
    }

    public int getClasesVehiculoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ClasesVehiculo> rt = cq.from(ClasesVehiculo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
           
        }
    }

}
