/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.soltelec.servidor.dao;

import com.soltelec.servidor.dao.exceptions.IllegalOrphanException;
import com.soltelec.servidor.dao.exceptions.NonexistentEntityException;
import com.soltelec.servidor.dao.exceptions.PreexistingEntityException;
import com.soltelec.servidor.model.Defectos;
import com.soltelec.servidor.model.Defectoxmedida;
import com.soltelec.servidor.model.Grupos;
import com.soltelec.servidor.model.Permisibles;
import com.soltelec.servidor.model.SubGrupos;
import com.soltelec.servidor.conexion.PersistenceController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author GerenciaDesarrollo
 */
public class DefectosJpaController {

    private EntityManager getEntityManager() {
        return PersistenceController.getEntityManager();
    }

//    public void create(Defectos defectos) throws PreexistingEntityException, Exception {
//        if (defectos.getPermisiblesCollection() == null) {
//            defectos.setPermisiblesCollection(new ArrayList<Permisibles>());
//        }
//        EntityManager em = null;
//        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
//            Grupos grupos = defectos.getGrupos();
//            if (grupos != null) {
//                grupos = em.getReference(grupos.getClass(), grupos.getDefgroup());
//                defectos.setGrupos(grupos);
//            }
//            SubGrupos subGrupos = defectos.getSubGrupos();
//            if (subGrupos != null) {
//                subGrupos = em.getReference(subGrupos.getClass(), subGrupos.getScdefgroupsub());
//                defectos.setSubGrupos(subGrupos);
//            }
//            Collection<Defectoxmedida> attachedDefectoxmedidaCollection = new ArrayList<>();
//            Collection<Permisibles> attachedPermisiblesCollection = new ArrayList<>();
//            for (Permisibles permisiblesCollectionPermisiblesToAttach : defectos.getPermisiblesCollection()) {
//                permisiblesCollectionPermisiblesToAttach = em.getReference(permisiblesCollectionPermisiblesToAttach.getClass(), permisiblesCollectionPermisiblesToAttach.getIdpermisible());
//                attachedPermisiblesCollection.add(permisiblesCollectionPermisiblesToAttach);
//            }
//            defectos.setPermisiblesCollection(attachedPermisiblesCollection);
//            if (grupos != null) {
//                grupos.getDefectosCollection().add(defectos);
//                em.merge(grupos);
//            }
//            for (Permisibles permisiblesCollectionPermisibles : defectos.getPermisiblesCollection()) {
//                Defectos oldDefectosOfPermisiblesCollectionPermisibles = permisiblesCollectionPermisibles.getDefectos();
//                permisiblesCollectionPermisibles.setDefectos(defectos);
//                permisiblesCollectionPermisibles = em.merge(permisiblesCollectionPermisibles);
//                if (oldDefectosOfPermisiblesCollectionPermisibles != null) {
//                    oldDefectosOfPermisiblesCollectionPermisibles.getPermisiblesCollection().remove(permisiblesCollectionPermisibles);
//                    em.merge(oldDefectosOfPermisiblesCollectionPermisibles);
//                }
//            }
//            em.getTransaction().commit();
//        } catch (Exception ex) {
//            if (findDefectos(defectos.getCardefault()) != null) {
//                throw new PreexistingEntityException("Defectos " + defectos + " already exists.", ex);
//            }
//            throw ex;
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
//    }

//    public void edit(Defectos defectos) throws IllegalOrphanException, NonexistentEntityException, Exception {
//        EntityManager em = null;
//        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
//            Defectos persistentDefectos = em.find(Defectos.class, defectos.getCardefault());
//            Grupos gruposOld = persistentDefectos.getGrupos();
//            Grupos gruposNew = defectos.getGrupos();
//            SubGrupos subGruposOld = persistentDefectos.getSubGrupos();
//            SubGrupos subGruposNew = defectos.getSubGrupos();
//            Collection<Permisibles> permisiblesCollectionOld = persistentDefectos.getPermisiblesCollection();
//            Collection<Permisibles> permisiblesCollectionNew = defectos.getPermisiblesCollection();
//            List<String> illegalOrphanMessages = null;
//            for (Permisibles permisiblesCollectionOldPermisibles : permisiblesCollectionOld) {
//                if (!permisiblesCollectionNew.contains(permisiblesCollectionOldPermisibles)) {
//                    if (illegalOrphanMessages == null) {
//                        illegalOrphanMessages = new ArrayList<>();
//                    }
//                    illegalOrphanMessages.add("You must retain Permisibles " + permisiblesCollectionOldPermisibles + " since its defectos field is not nullable.");
//                }
//            }
//            if (illegalOrphanMessages != null) {
//                throw new IllegalOrphanException(illegalOrphanMessages);
//            }
//            if (gruposNew != null) {
//                gruposNew = em.getReference(gruposNew.getClass(), gruposNew.getDefgroup());
//                defectos.setGrupos(gruposNew);
//            }
//            if (subGruposNew != null) {
//                subGruposNew = em.getReference(subGruposNew.getClass(), subGruposNew.getScdefgroupsub());
//                defectos.setSubGrupos(subGruposNew);
//            }
//            Collection<Permisibles> attachedPermisiblesCollectionNew = new ArrayList<>();
//            for (Permisibles permisiblesCollectionNewPermisiblesToAttach : permisiblesCollectionNew) {
//                permisiblesCollectionNewPermisiblesToAttach = em.getReference(permisiblesCollectionNewPermisiblesToAttach.getClass(), permisiblesCollectionNewPermisiblesToAttach.getIdpermisible());
//                attachedPermisiblesCollectionNew.add(permisiblesCollectionNewPermisiblesToAttach);
//            }
//            permisiblesCollectionNew = attachedPermisiblesCollectionNew;
//            defectos.setPermisiblesCollection(permisiblesCollectionNew);
//            
//            if (gruposOld != null && !gruposOld.equals(gruposNew)) {
//                gruposOld.getDefectosCollection().remove(defectos);
//                gruposOld = em.merge(gruposOld);
//            }
//            if (gruposNew != null && !gruposNew.equals(gruposOld)) {
//                gruposNew.getDefectosCollection().add(defectos);
//                em.merge(gruposNew);
//            }
//            
//            
//            for (Permisibles permisiblesCollectionNewPermisibles : permisiblesCollectionNew) {
//                if (!permisiblesCollectionOld.contains(permisiblesCollectionNewPermisibles)) {
//                    Defectos oldDefectosOfPermisiblesCollectionNewPermisibles = permisiblesCollectionNewPermisibles.getDefectos();
//                    permisiblesCollectionNewPermisibles.setDefectos(defectos);
//                    permisiblesCollectionNewPermisibles = em.merge(permisiblesCollectionNewPermisibles);
//                    if (oldDefectosOfPermisiblesCollectionNewPermisibles != null && !oldDefectosOfPermisiblesCollectionNewPermisibles.equals(defectos)) {
//                        oldDefectosOfPermisiblesCollectionNewPermisibles.getPermisiblesCollection().remove(permisiblesCollectionNewPermisibles);
//                        oldDefectosOfPermisiblesCollectionNewPermisibles = em.merge(oldDefectosOfPermisiblesCollectionNewPermisibles);
//                    }
//                }
//            }
//            
//            
//            em.getTransaction().commit();
//        } catch (Exception ex) {
//            String msg = ex.getLocalizedMessage();
//            if (msg == null || msg.length() == 0) {
//                Integer id = defectos.getCardefault();
//                if (findDefectos(id) == null) {
//                    throw new NonexistentEntityException("The defectos with id " + id + " no longer exists.");
//                }
//            }
//            throw ex;
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
//    }

//    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
//        EntityManager em = null;
//        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
//            Defectos defectos;
//            try {
//                defectos = em.getReference(Defectos.class, id);
//                defectos.getCardefault();
//            } catch (EntityNotFoundException enfe) {
//                throw new NonexistentEntityException("The defectos with id " + id + " no longer exists.", enfe);
//            }
//            List<String> illegalOrphanMessages = null;
//            
//            
//            Collection<Permisibles> permisiblesCollectionOrphanCheck = defectos.getPermisiblesCollection();
//            for (Permisibles permisiblesCollectionOrphanCheckPermisibles : permisiblesCollectionOrphanCheck) {
//                if (illegalOrphanMessages == null) {
//                    illegalOrphanMessages = new ArrayList<String>();
//                }
//                illegalOrphanMessages.add("This Defectos (" + defectos + ") cannot be destroyed since the Permisibles " + permisiblesCollectionOrphanCheckPermisibles + " in its permisiblesCollection field has a non-nullable defectos field.");
//            }
//           
//            Grupos grupos = defectos.getGrupos();
//            if (grupos != null) {
//                grupos.getDefectosCollection().remove(defectos);
//                em.merge(grupos);
//            }
//            SubGrupos subGrupos = defectos.getSubGrupos();
//           
//            em.remove(defectos);
//            em.getTransaction().commit();
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
//    }

    public List<Defectos> findDefectosEntities() {
        return findDefectosEntities(true, -1, -1);
    }

    public List<Defectos> findDefectosEntities(int maxResults, int firstResult) {
        return findDefectosEntities(false, maxResults, firstResult);
    }

    private List<Defectos> findDefectosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Defectos.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
           
        }
    }

    public Defectos findDefectos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Defectos.class, id);
        } finally {
           
        }
    }

    public int getDefectosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Defectos> rt = cq.from(Defectos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
           
        }
    }

    public List<Defectos> findTDefectos2() {
        EntityManager em = getEntityManager();
        Query query = em.createQuery("SELECT c FROM Defectos AS c");
        return query.getResultList();
    }



    public List<Object[]> findTDefectos1() {
        EntityManager em = getEntityManager();

        String msql = "SELECT d.cardefault , d.nombreproblema, d.tipodefecto, t.nombretipoprueba, g.nombregrupo FROM Defectos d, Grupos g, TipoPrueba t WHERE  d.tipoPrueba = t and d.grupos = g order by d.cardefault ";
        Query query = em.createQuery(msql);

        return query.getResultList();
    }

}
