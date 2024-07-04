/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soltelec.servidor.dao;

import com.soltelec.servidor.dao.exceptions.NonexistentEntityException;
import com.soltelec.servidor.model.Grupos;
import com.soltelec.servidor.model.SubGrupos;
import com.soltelec.servidor.conexion.PersistenceController;
import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author User
 */
public class SubGruposJpaController implements Serializable {

    private EntityManager getEntityManager() {
        return PersistenceController.getEntityManager();
    }

    public void create(SubGrupos subGrupos) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Grupos grupos = subGrupos.getGrupos();
            if (grupos != null) {
                grupos = em.getReference(grupos.getClass(), grupos.getDefgroup());
                subGrupos.setGrupos(grupos);
            }
            em.persist(subGrupos);
            if (grupos != null) {
                grupos.getSubGruposCollection().add(subGrupos);
                em.merge(grupos);
            }
            em.getTransaction().commit();
        } finally {
            
        }
    }

    public void edit(SubGrupos subGrupos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SubGrupos persistentSubGrupos = em.find(SubGrupos.class, subGrupos.getScdefgroupsub());
            Grupos gruposOld = persistentSubGrupos.getGrupos();
            Grupos gruposNew = subGrupos.getGrupos();
            if (gruposNew != null) {
                gruposNew = em.getReference(gruposNew.getClass(), gruposNew.getDefgroup());
                subGrupos.setGrupos(gruposNew);
            }
            subGrupos = em.merge(subGrupos);
            if (gruposOld != null && !gruposOld.equals(gruposNew)) {
                gruposOld.getSubGruposCollection().remove(subGrupos);
                gruposOld = em.merge(gruposOld);
            }
            if (gruposNew != null && !gruposNew.equals(gruposOld)) {
                gruposNew.getSubGruposCollection().add(subGrupos);
                em.merge(gruposNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = subGrupos.getScdefgroupsub();
                if (findSubGrupos(id) == null) {
                    throw new NonexistentEntityException("The subGrupos with id " + id + " no longer exists.");
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
            SubGrupos subGrupos;
            try {
                subGrupos = em.getReference(SubGrupos.class, id);
                subGrupos.getScdefgroupsub();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The subGrupos with id " + id + " no longer exists.", enfe);
            }
            Grupos grupos = subGrupos.getGrupos();
            if (grupos != null) {
                grupos.getSubGruposCollection().remove(subGrupos);
                em.merge(grupos);
            }
            em.remove(subGrupos);
            em.getTransaction().commit();
        } finally {
            
        }
    }

    public List<SubGrupos> findSubGruposEntities() {
        return findSubGruposEntities(true, -1, -1);
    }

    public List<SubGrupos> findSubGruposEntities(int maxResults, int firstResult) {
        return findSubGruposEntities(false, maxResults, firstResult);
    }

    private List<SubGrupos> findSubGruposEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SubGrupos.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
          
        }
    }

    public SubGrupos findSubGrupos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SubGrupos.class, id);
        } finally {
           
        }
    }

    public int getSubGruposCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SubGrupos> rt = cq.from(SubGrupos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
           
        }
    }
    
    public List<SubGrupos> findFiltroSubGrupos(int idGrupo) {
        EntityManager em = getEntityManager();
        String sql = "SELECT s FROM SubGrupos s WHERE s.scdefgroupsub = " + idGrupo;
        Query query = em.createQuery(sql);
        
        return query.getResultList();
    }
    
}
