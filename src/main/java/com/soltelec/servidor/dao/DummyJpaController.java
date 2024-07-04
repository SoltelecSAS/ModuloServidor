/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.soltelec.servidor.dao;

import com.soltelec.servidor.dao.exceptions.NonexistentEntityException;
import com.soltelec.servidor.dao.exceptions.PreexistingEntityException;
import com.soltelec.servidor.model.Dummy;
import com.soltelec.servidor.conexion.PersistenceController;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Administrador
 */
public class DummyJpaController {

    private EntityManager getEntityManager() {
        return PersistenceController.getEntityManager();
    }

    public void create(Dummy dummy) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(dummy);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDummy(dummy.getDummy()) != null) {
                throw new PreexistingEntityException("Dummy " + dummy + " already exists.", ex);
            }
            throw ex;
        } finally {
            
        }
    }

    public void edit(Dummy dummy) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            dummy = em.merge(dummy);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = dummy.getDummy();
                if (findDummy(id) == null) {
                    throw new NonexistentEntityException("The dummy with id " + id + " no longer exists.");
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
            Dummy dummy;
            try {
                dummy = em.getReference(Dummy.class, id);
                dummy.getDummy();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The dummy with id " + id + " no longer exists.", enfe);
            }
            em.remove(dummy);
            em.getTransaction().commit();
        } finally {            
        }
    }

    public List<Dummy> findDummyEntities() {
        return findDummyEntities(true, -1, -1);
    }

    public List<Dummy> findDummyEntities(int maxResults, int firstResult) {
        return findDummyEntities(false, maxResults, firstResult);
    }

    private List<Dummy> findDummyEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Dummy.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {            
        }
    }

    public Dummy findDummy(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Dummy.class, id);
        } finally {
           
        }
    }

    public int getDummyCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Dummy> rt = cq.from(Dummy.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {           
        }
    }

}
