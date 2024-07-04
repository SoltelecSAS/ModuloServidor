/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.soltelec.servidor.dao;

import com.soltelec.servidor.dao.exceptions.IllegalOrphanException;
import com.soltelec.servidor.dao.exceptions.NonexistentEntityException;
import com.soltelec.servidor.dao.exceptions.PreexistingEntityException;
import com.soltelec.servidor.model.TiposGasolina;
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
public class TiposGasolinaJpaController {

    private EntityManager getEntityManager() {
        return PersistenceController.getEntityManager();
    }

    public void create(TiposGasolina tiposGasolina) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(tiposGasolina);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTiposGasolina(tiposGasolina.getFueltype()) != null) {
                throw new PreexistingEntityException("TiposGasolina " + tiposGasolina + " already exists.", ex);
            }
            throw ex;
        } finally {
            
        }
    }

    public void edit(TiposGasolina tiposGasolina) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TiposGasolina persistentTiposGasolina = em.find(TiposGasolina.class, tiposGasolina.getFueltype());
            tiposGasolina = em.merge(tiposGasolina);            
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tiposGasolina.getFueltype();
                if (findTiposGasolina(id) == null) {
                    throw new NonexistentEntityException("The tiposGasolina with id " + id + " no longer exists.");
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
            TiposGasolina tiposGasolina;
            try {
                tiposGasolina = em.getReference(TiposGasolina.class, id);
                tiposGasolina.getFueltype();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tiposGasolina with id " + id + " no longer exists.", enfe);
            }
            em.remove(tiposGasolina);
            em.getTransaction().commit();
        } finally {
           
        }
    }

    public List<TiposGasolina> findTiposGasolinaEntities() {
        return findTiposGasolinaEntities(true, -1, -1);
    }

    public List<TiposGasolina> findTiposGasolinaEntities(int maxResults, int firstResult) {
        return findTiposGasolinaEntities(false, maxResults, firstResult);
    }

    private List<TiposGasolina> findTiposGasolinaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TiposGasolina.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
           
        }
    }

    public TiposGasolina findTiposGasolina(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TiposGasolina.class, id);
        } finally {
          
        }
    }

    public int getTiposGasolinaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TiposGasolina> rt = cq.from(TiposGasolina.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
          
        }
    }

}
