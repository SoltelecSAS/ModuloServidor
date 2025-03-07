/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.soltelec.servidor.dao;

import com.soltelec.servidor.dao.exceptions.NonexistentEntityException;
import com.soltelec.servidor.dao.exceptions.PreexistingEntityException;
import com.soltelec.servidor.model.Defaultresume;
import com.soltelec.servidor.model.DefaultresumePK;
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
public class DefaultresumeJpaController {

    private EntityManager getEntityManager() {
        return PersistenceController.getEntityManager();
    }

    public void create(Defaultresume defaultresume) throws PreexistingEntityException, Exception {
        if (defaultresume.getDefaultresumePK() == null) {
            defaultresume.setDefaultresumePK(new DefaultresumePK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(defaultresume);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDefaultresume(defaultresume.getDefaultresumePK()) != null) {
                throw new PreexistingEntityException("Defaultresume " + defaultresume + " already exists.", ex);
            }
            throw ex;
        } finally {
            
        }
    }

    public void edit(Defaultresume defaultresume) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            defaultresume = em.merge(defaultresume);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                DefaultresumePK id = defaultresume.getDefaultresumePK();
                if (findDefaultresume(id) == null) {
                    throw new NonexistentEntityException("The defaultresume with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            
        }
    }

    public void destroy(DefaultresumePK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Defaultresume defaultresume;
            try {
                defaultresume = em.getReference(Defaultresume.class, id);
                defaultresume.getDefaultresumePK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The defaultresume with id " + id + " no longer exists.", enfe);
            }
            em.remove(defaultresume);
            em.getTransaction().commit();
        } finally {
            
        }
    }

    public List<Defaultresume> findDefaultresumeEntities() {
        return findDefaultresumeEntities(true, -1, -1);
    }

    public List<Defaultresume> findDefaultresumeEntities(int maxResults, int firstResult) {
        return findDefaultresumeEntities(false, maxResults, firstResult);
    }

    private List<Defaultresume> findDefaultresumeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Defaultresume.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
           
        }
    }

    public Defaultresume findDefaultresume(DefaultresumePK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Defaultresume.class, id);
        } finally {
           
        }
    }

    public int getDefaultresumeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Defaultresume> rt = cq.from(Defaultresume.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
           
        }
    }

}
