/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soltelec.servidor.dao;

import com.soltelec.servidor.dao.exceptions.IllegalOrphanException;
import com.soltelec.servidor.dao.exceptions.NonexistentEntityException;
import com.soltelec.servidor.model.Defectos;
import com.soltelec.servidor.model.Grupos;
import com.soltelec.servidor.model.SubGrupos;
import com.soltelec.servidor.conexion.PersistenceController;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author User
 */
public class GruposJpaController implements Serializable {

    private EntityManager getEntityManager() {
        return PersistenceController.getEntityManager();
    }

//    public void create(Grupos grupos) {
//        if (grupos.getSubGruposCollection() == null) {
//            grupos.setSubGruposCollection(new ArrayList<SubGrupos>());
//        }
//        if (grupos.getDefectosCollection() == null) {
//            grupos.setDefectosCollection(new ArrayList<Defectos>());
//        }
//        EntityManager em = null;
//        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
//            Collection<SubGrupos> attachedSubGruposCollection = new ArrayList<SubGrupos>();
//            for (SubGrupos subGruposCollectionSubGruposToAttach : grupos.getSubGruposCollection()) {
//                subGruposCollectionSubGruposToAttach = em.getReference(subGruposCollectionSubGruposToAttach.getClass(), subGruposCollectionSubGruposToAttach.getScdefgroupsub());
//                attachedSubGruposCollection.add(subGruposCollectionSubGruposToAttach);
//            }
//            grupos.setSubGruposCollection(attachedSubGruposCollection);
//            Collection<Defectos> attachedDefectosCollection = new ArrayList<Defectos>();
//            for (Defectos defectosCollectionDefectosToAttach : grupos.getDefectosCollection()) {
//                defectosCollectionDefectosToAttach = em.getReference(defectosCollectionDefectosToAttach.getClass(), defectosCollectionDefectosToAttach.getCardefault());
//                attachedDefectosCollection.add(defectosCollectionDefectosToAttach);
//            }
//            grupos.setDefectosCollection(attachedDefectosCollection);
//            em.persist(grupos);
//            for (SubGrupos subGruposCollectionSubGrupos : grupos.getSubGruposCollection()) {
//                Grupos oldGruposOfSubGruposCollectionSubGrupos = subGruposCollectionSubGrupos.getGrupos();
//                subGruposCollectionSubGrupos.setGrupos(grupos);
//                subGruposCollectionSubGrupos = em.merge(subGruposCollectionSubGrupos);
//                if (oldGruposOfSubGruposCollectionSubGrupos != null) {
//                    oldGruposOfSubGruposCollectionSubGrupos.getSubGruposCollection().remove(subGruposCollectionSubGrupos);
//                    oldGruposOfSubGruposCollectionSubGrupos = em.merge(oldGruposOfSubGruposCollectionSubGrupos);
//                }
//            }
//            for (Defectos defectosCollectionDefectos : grupos.getDefectosCollection()) {
//                Grupos oldGruposOfDefectosCollectionDefectos = defectosCollectionDefectos.getGrupos();
//                defectosCollectionDefectos.setGrupos(grupos);
//                defectosCollectionDefectos = em.merge(defectosCollectionDefectos);
//                if (oldGruposOfDefectosCollectionDefectos != null) {
//                    oldGruposOfDefectosCollectionDefectos.getDefectosCollection().remove(defectosCollectionDefectos);
//                    oldGruposOfDefectosCollectionDefectos = em.merge(oldGruposOfDefectosCollectionDefectos);
//                }
//            }
//            em.getTransaction().commit();
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
//    }

//    public void edit(Grupos grupos) throws IllegalOrphanException, NonexistentEntityException, Exception {
//        EntityManager em = null;
//        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
//            Grupos persistentGrupos = em.find(Grupos.class, grupos.getDefgroup());
//            Collection<SubGrupos> subGruposCollectionOld = persistentGrupos.getSubGruposCollection();
//            Collection<SubGrupos> subGruposCollectionNew = grupos.getSubGruposCollection();
//            Collection<Defectos> defectosCollectionOld = persistentGrupos.getDefectosCollection();
//            Collection<Defectos> defectosCollectionNew = grupos.getDefectosCollection();
//            List<String> illegalOrphanMessages = null;
//            for (Defectos defectosCollectionOldDefectos : defectosCollectionOld) {
//                if (!defectosCollectionNew.contains(defectosCollectionOldDefectos)) {
//                    if (illegalOrphanMessages == null) {
//                        illegalOrphanMessages = new ArrayList<String>();
//                    }
//                    illegalOrphanMessages.add("You must retain Defectos " + defectosCollectionOldDefectos + " since its grupos field is not nullable.");
//                }
//            }
//            if (illegalOrphanMessages != null) {
//                throw new IllegalOrphanException(illegalOrphanMessages);
//            }
//            Collection<SubGrupos> attachedSubGruposCollectionNew = new ArrayList<SubGrupos>();
//            for (SubGrupos subGruposCollectionNewSubGruposToAttach : subGruposCollectionNew) {
//                subGruposCollectionNewSubGruposToAttach = em.getReference(subGruposCollectionNewSubGruposToAttach.getClass(), subGruposCollectionNewSubGruposToAttach.getScdefgroupsub());
//                attachedSubGruposCollectionNew.add(subGruposCollectionNewSubGruposToAttach);
//            }
//            subGruposCollectionNew = attachedSubGruposCollectionNew;
//            grupos.setSubGruposCollection(subGruposCollectionNew);
//            Collection<Defectos> attachedDefectosCollectionNew = new ArrayList<Defectos>();
//            for (Defectos defectosCollectionNewDefectosToAttach : defectosCollectionNew) {
//                defectosCollectionNewDefectosToAttach = em.getReference(defectosCollectionNewDefectosToAttach.getClass(), defectosCollectionNewDefectosToAttach.getCardefault());
//                attachedDefectosCollectionNew.add(defectosCollectionNewDefectosToAttach);
//            }
//            defectosCollectionNew = attachedDefectosCollectionNew;
//            grupos.setDefectosCollection(defectosCollectionNew);
//            grupos = em.merge(grupos);
//            for (SubGrupos subGruposCollectionOldSubGrupos : subGruposCollectionOld) {
//                if (!subGruposCollectionNew.contains(subGruposCollectionOldSubGrupos)) {
//                    subGruposCollectionOldSubGrupos.setGrupos(null);
//                    subGruposCollectionOldSubGrupos = em.merge(subGruposCollectionOldSubGrupos);
//                }
//            }
//            for (SubGrupos subGruposCollectionNewSubGrupos : subGruposCollectionNew) {
//                if (!subGruposCollectionOld.contains(subGruposCollectionNewSubGrupos)) {
//                    Grupos oldGruposOfSubGruposCollectionNewSubGrupos = subGruposCollectionNewSubGrupos.getGrupos();
//                    subGruposCollectionNewSubGrupos.setGrupos(grupos);
//                    subGruposCollectionNewSubGrupos = em.merge(subGruposCollectionNewSubGrupos);
//                    if (oldGruposOfSubGruposCollectionNewSubGrupos != null && !oldGruposOfSubGruposCollectionNewSubGrupos.equals(grupos)) {
//                        oldGruposOfSubGruposCollectionNewSubGrupos.getSubGruposCollection().remove(subGruposCollectionNewSubGrupos);
//                        oldGruposOfSubGruposCollectionNewSubGrupos = em.merge(oldGruposOfSubGruposCollectionNewSubGrupos);
//                    }
//                }
//            }
//            for (Defectos defectosCollectionNewDefectos : defectosCollectionNew) {
//                if (!defectosCollectionOld.contains(defectosCollectionNewDefectos)) {
//                    Grupos oldGruposOfDefectosCollectionNewDefectos = defectosCollectionNewDefectos.getGrupos();
//                    defectosCollectionNewDefectos.setGrupos(grupos);
//                    defectosCollectionNewDefectos = em.merge(defectosCollectionNewDefectos);
//                    if (oldGruposOfDefectosCollectionNewDefectos != null && !oldGruposOfDefectosCollectionNewDefectos.equals(grupos)) {
//                        oldGruposOfDefectosCollectionNewDefectos.getDefectosCollection().remove(defectosCollectionNewDefectos);
//                        oldGruposOfDefectosCollectionNewDefectos = em.merge(oldGruposOfDefectosCollectionNewDefectos);
//                    }
//                }
//            }
//            em.getTransaction().commit();
//        } catch (Exception ex) {
//            String msg = ex.getLocalizedMessage();
//            if (msg == null || msg.length() == 0) {
//                Integer id = grupos.getDefgroup();
//                if (findGrupos(id) == null) {
//                    throw new NonexistentEntityException("The grupos with id " + id + " no longer exists.");
//                }
//            }
//            throw ex;
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
//    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Grupos grupos;
            try {
                grupos = em.getReference(Grupos.class, id);
                grupos.getDefgroup();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The grupos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Defectos> defectosCollectionOrphanCheck = null;
            for (Defectos defectosCollectionOrphanCheckDefectos : defectosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Grupos (" + grupos + ") cannot be destroyed since the Defectos " + defectosCollectionOrphanCheckDefectos + " in its defectosCollection field has a non-nullable grupos field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<SubGrupos> subGruposCollection = grupos.getSubGruposCollection();
            for (SubGrupos subGruposCollectionSubGrupos : subGruposCollection) {
                subGruposCollectionSubGrupos.setGrupos(null);
                subGruposCollectionSubGrupos = em.merge(subGruposCollectionSubGrupos);
            }
            em.remove(grupos);
            em.getTransaction().commit();
        } finally {            
        }
    }

    public List<Grupos> findGruposEntities() {
        return findGruposEntities(true, -1, -1);
    }

    public List<Grupos> findGruposEntities(int maxResults, int firstResult) {
        return findGruposEntities(false, maxResults, firstResult);
    }

    private List<Grupos> findGruposEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Grupos.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
          
        }
    }

    public Grupos findGrupos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Grupos.class, id);
        } finally {
           
        }
    }
    
    public int getGruposCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Grupos> rt = cq.from(Grupos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
           
        }
    }
    
    public List<Grupos> findGruposLista() {
        EntityManager em = getEntityManager();
        Query query = em.createQuery("SELECT g.defgroup, g.nombregrupo FROM Grupos AS g ORDER BY g.nombregrupo ASC");
        return query.getResultList();
    }

}
