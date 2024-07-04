/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.soltelec.servidor.dao;

import com.soltelec.servidor.exceptions.NonexistentEntityException;
import com.soltelec.servidor.exceptions.PreexistingEntityException;
import com.soltelec.servidor.model.Software;
import java.io.Serializable;
import com.soltelec.servidor.conexion.PersistenceController;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author soltelec
 */
public class SoftwareJpaController {

   private EntityManager getEntityManager() {
        return PersistenceController.getEntityManager();
    }

    public void create(Software software) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(software);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSoftware(software.getIdsw()) != null) {
                throw new PreexistingEntityException("Software " + software + " already exists.", ex);
            }
            throw ex;
        } finally {
           
        }
    }

    public void edit(Software software) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            software = em.merge(software);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = software.getIdsw();
                if (findSoftware(id) == null) {
                    throw new NonexistentEntityException("The software with id " + id + " no longer exists.");
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
            Software software;
            try {
                software = em.getReference(Software.class, id);
                software.getIdsw();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The software with id " + id + " no longer exists.", enfe);
            }
            em.remove(software);
            em.getTransaction().commit();
        } finally {
            
        }
    }

    public List<Software> findSoftwareEntities() {
        return findSoftwareEntities(true, -1, -1);
    }

    public List<Software> findSoftwareEntities(int maxResults, int firstResult) {
        return findSoftwareEntities(false, maxResults, firstResult);
    }

    private List<Software> findSoftwareEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Software.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
           
        }
    }

    public Software findSoftware(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Software.class, id);
        } finally {
           
        }
    }

    public int getSoftwareCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Software> rt = cq.from(Software.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
          
        }
    }
    
}
