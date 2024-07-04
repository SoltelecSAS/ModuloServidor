/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.soltelec.servidor.dao;

import com.soltelec.servidor.dao.exceptions.IllegalOrphanException;
import com.soltelec.servidor.dao.exceptions.NonexistentEntityException;
import com.soltelec.servidor.dao.exceptions.PreexistingEntityException;
import com.soltelec.servidor.model.TipoVehiculo;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.soltelec.servidor.conexion.PersistenceController;

/**
 *
 * @author Administrador
 */
public class TipoVehiculoJpaController {

    private EntityManager getEntityManager() {
        return PersistenceController.getEntityManager();
    }

    public void create(TipoVehiculo tipoVehiculo) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(tipoVehiculo);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoVehiculo(tipoVehiculo.getCartype()) != null) {
                throw new PreexistingEntityException("TipoVehiculo " + tipoVehiculo + " already exists.", ex);
            }
            throw ex;
        } finally {
           
        }
    }

    public void edit(TipoVehiculo tipoVehiculo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoVehiculo persistentTipoVehiculo = em.find(TipoVehiculo.class, tipoVehiculo.getCartype());
            tipoVehiculo = em.merge(tipoVehiculo);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoVehiculo.getCartype();
                if (findTipoVehiculo(id) == null) {
                    throw new NonexistentEntityException("The tipoVehiculo with id " + id + " no longer exists.");
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
            TipoVehiculo tipoVehiculo;
            try {
                tipoVehiculo = em.getReference(TipoVehiculo.class, id);
                tipoVehiculo.getCartype();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoVehiculo with id " + id + " no longer exists.", enfe);
            }
            em.remove(tipoVehiculo);
            em.getTransaction().commit();
        } finally {
            
        }
    }

    public List<TipoVehiculo> findTipoVehiculoEntities() {
        return findTipoVehiculoEntities(true, -1, -1);
    }

    public List<TipoVehiculo> findTipoVehiculoEntities(int maxResults, int firstResult) {
        return findTipoVehiculoEntities(false, maxResults, firstResult);
    }

    private List<TipoVehiculo> findTipoVehiculoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoVehiculo.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
           
        }
    }

    public TipoVehiculo findTipoVehiculo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoVehiculo.class, id);
        } finally {
           
        }
    }

    public int getTipoVehiculoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoVehiculo> rt = cq.from(TipoVehiculo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
           
        }
    }


     public List<TipoVehiculo> findVehiculo1() {
         EntityManager em = getEntityManager();
        Query query = em.createQuery("SELECT c FROM TipoVehiculo AS c");
        return query.getResultList();
    }

}
