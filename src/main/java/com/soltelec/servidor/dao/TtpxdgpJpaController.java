/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.soltelec.servidor.dao;

import com.soltelec.servidor.dao.exceptions.NonexistentEntityException;
import com.soltelec.servidor.dao.exceptions.PreexistingEntityException;
import com.soltelec.servidor.model.Ttpxdgp;
import com.soltelec.servidor.model.TtpxdgpPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.soltelec.servidor.model.Grupos;
import com.soltelec.servidor.conexion.PersistenceController;

/**
 *
 * @author Administrador
 */
public class TtpxdgpJpaController {

   private EntityManager getEntityManager() {
        return PersistenceController.getEntityManager();
    }

    public void create(Ttpxdgp ttpxdgp) throws PreexistingEntityException, Exception {
        if (ttpxdgp.getTtpxdgpPK() == null) {
            ttpxdgp.setTtpxdgpPK(new TtpxdgpPK());
        }
        ttpxdgp.getTtpxdgpPK().setDefgroup(ttpxdgp.getGrupos().getDefgroup());
        ttpxdgp.getTtpxdgpPK().setTesttype(ttpxdgp.getTipoPrueba().getTesttype());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Grupos grupos = ttpxdgp.getGrupos();
            if (grupos != null) {
                grupos = em.getReference(grupos.getClass(), grupos.getDefgroup());
                ttpxdgp.setGrupos(grupos);
            }
            em.persist(ttpxdgp);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTtpxdgp(ttpxdgp.getTtpxdgpPK()) != null) {
                throw new PreexistingEntityException("Ttpxdgp " + ttpxdgp + " already exists.", ex);
            }
            throw ex;
        } finally {
            
        }
    }

    public void edit(Ttpxdgp ttpxdgp) throws NonexistentEntityException, Exception {
        ttpxdgp.getTtpxdgpPK().setDefgroup(ttpxdgp.getGrupos().getDefgroup());
        ttpxdgp.getTtpxdgpPK().setTesttype(ttpxdgp.getTipoPrueba().getTesttype());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ttpxdgp persistentTtpxdgp = em.find(Ttpxdgp.class, ttpxdgp.getTtpxdgpPK());
            Grupos gruposOld = persistentTtpxdgp.getGrupos();
            Grupos gruposNew = ttpxdgp.getGrupos();
            if (gruposNew != null) {
                gruposNew = em.getReference(gruposNew.getClass(), gruposNew.getDefgroup());
                ttpxdgp.setGrupos(gruposNew);
            }
            ttpxdgp = em.merge(ttpxdgp);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                TtpxdgpPK id = ttpxdgp.getTtpxdgpPK();
                if (findTtpxdgp(id) == null) {
                    throw new NonexistentEntityException("The ttpxdgp with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            
        }
    }

    public void destroy(TtpxdgpPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ttpxdgp ttpxdgp;
            try {
                ttpxdgp = em.getReference(Ttpxdgp.class, id);
                ttpxdgp.getTtpxdgpPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ttpxdgp with id " + id + " no longer exists.", enfe);
            }
            Grupos grupos = ttpxdgp.getGrupos();
            em.remove(ttpxdgp);
            em.getTransaction().commit();
        } finally {
          
        }
    }

    public List<Ttpxdgp> findTtpxdgpEntities() {
        return findTtpxdgpEntities(true, -1, -1);
    }

    public List<Ttpxdgp> findTtpxdgpEntities(int maxResults, int firstResult) {
        return findTtpxdgpEntities(false, maxResults, firstResult);
    }

    private List<Ttpxdgp> findTtpxdgpEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ttpxdgp.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
           
        }
    }

    public Ttpxdgp findTtpxdgp(TtpxdgpPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ttpxdgp.class, id);
        } finally {
          
        }
    }

    public int getTtpxdgpCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ttpxdgp> rt = cq.from(Ttpxdgp.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
           
        }
    }

}
