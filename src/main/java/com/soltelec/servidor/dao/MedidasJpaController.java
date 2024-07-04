/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.soltelec.servidor.dao;

import com.soltelec.servidor.dao.exceptions.IllegalOrphanException;
import com.soltelec.servidor.dao.exceptions.NonexistentEntityException;
import com.soltelec.servidor.model.Medidas;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.soltelec.servidor.model.TiposMedida;
import com.soltelec.servidor.model.Defectoxmedida;
import com.soltelec.servidor.conexion.PersistenceController;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Administrador
 */
public class MedidasJpaController {

    private EntityManager getEntityManager() {
        return PersistenceController.getEntityManager();
    }

    public void create(Medidas medidas) {
        if (medidas.getDefectoxmedidaCollection() == null) {
            medidas.setDefectoxmedidaCollection(new ArrayList<Defectoxmedida>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TiposMedida tiposMedida = medidas.getTiposMedida();
            if (tiposMedida != null) {
                tiposMedida = em.getReference(tiposMedida.getClass(), tiposMedida.getMeasuretype());
                medidas.setTiposMedida(tiposMedida);
            }
            Collection<Defectoxmedida> attachedDefectoxmedidaCollection = new ArrayList<Defectoxmedida>();
            for (Defectoxmedida defectoxmedidaCollectionDefectoxmedidaToAttach : medidas.getDefectoxmedidaCollection()) {
                defectoxmedidaCollectionDefectoxmedidaToAttach = em.getReference(defectoxmedidaCollectionDefectoxmedidaToAttach.getClass(), defectoxmedidaCollectionDefectoxmedidaToAttach.getDefectoxmedidaPK());
                attachedDefectoxmedidaCollection.add(defectoxmedidaCollectionDefectoxmedidaToAttach);
            }
            medidas.setDefectoxmedidaCollection(attachedDefectoxmedidaCollection);
            em.persist(medidas);
            for (Defectoxmedida defectoxmedidaCollectionDefectoxmedida : medidas.getDefectoxmedidaCollection()) {
                Medidas oldMedidasOfDefectoxmedidaCollectionDefectoxmedida = defectoxmedidaCollectionDefectoxmedida.getMedidas();
                defectoxmedidaCollectionDefectoxmedida.setMedidas(medidas);
                defectoxmedidaCollectionDefectoxmedida = em.merge(defectoxmedidaCollectionDefectoxmedida);
                if (oldMedidasOfDefectoxmedidaCollectionDefectoxmedida != null) {
                    oldMedidasOfDefectoxmedidaCollectionDefectoxmedida.getDefectoxmedidaCollection().remove(defectoxmedidaCollectionDefectoxmedida);
                    oldMedidasOfDefectoxmedidaCollectionDefectoxmedida = em.merge(oldMedidasOfDefectoxmedidaCollectionDefectoxmedida);
                }
            }
            em.getTransaction().commit();
        } finally {           
        }
    }

    public void edit(Medidas medidas) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Medidas persistentMedidas = em.find(Medidas.class, medidas.getMeasure());
            TiposMedida tiposMedidaOld = persistentMedidas.getTiposMedida();
            TiposMedida tiposMedidaNew = medidas.getTiposMedida();
            Collection<Defectoxmedida> defectoxmedidaCollectionOld = persistentMedidas.getDefectoxmedidaCollection();
            Collection<Defectoxmedida> defectoxmedidaCollectionNew = medidas.getDefectoxmedidaCollection();
            List<String> illegalOrphanMessages = null;
            for (Defectoxmedida defectoxmedidaCollectionOldDefectoxmedida : defectoxmedidaCollectionOld) {
                if (!defectoxmedidaCollectionNew.contains(defectoxmedidaCollectionOldDefectoxmedida)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Defectoxmedida " + defectoxmedidaCollectionOldDefectoxmedida + " since its medidas field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tiposMedidaNew != null) {
                tiposMedidaNew = em.getReference(tiposMedidaNew.getClass(), tiposMedidaNew.getMeasuretype());
                medidas.setTiposMedida(tiposMedidaNew);
            }
            Collection<Defectoxmedida> attachedDefectoxmedidaCollectionNew = new ArrayList<Defectoxmedida>();
            for (Defectoxmedida defectoxmedidaCollectionNewDefectoxmedidaToAttach : defectoxmedidaCollectionNew) {
                defectoxmedidaCollectionNewDefectoxmedidaToAttach = em.getReference(defectoxmedidaCollectionNewDefectoxmedidaToAttach.getClass(), defectoxmedidaCollectionNewDefectoxmedidaToAttach.getDefectoxmedidaPK());
                attachedDefectoxmedidaCollectionNew.add(defectoxmedidaCollectionNewDefectoxmedidaToAttach);
            }
            defectoxmedidaCollectionNew = attachedDefectoxmedidaCollectionNew;
            medidas.setDefectoxmedidaCollection(defectoxmedidaCollectionNew);
            medidas = em.merge(medidas);
            for (Defectoxmedida defectoxmedidaCollectionNewDefectoxmedida : defectoxmedidaCollectionNew) {
                if (!defectoxmedidaCollectionOld.contains(defectoxmedidaCollectionNewDefectoxmedida)) {
                    Medidas oldMedidasOfDefectoxmedidaCollectionNewDefectoxmedida = defectoxmedidaCollectionNewDefectoxmedida.getMedidas();
                    defectoxmedidaCollectionNewDefectoxmedida.setMedidas(medidas);
                    defectoxmedidaCollectionNewDefectoxmedida = em.merge(defectoxmedidaCollectionNewDefectoxmedida);
                    if (oldMedidasOfDefectoxmedidaCollectionNewDefectoxmedida != null && !oldMedidasOfDefectoxmedidaCollectionNewDefectoxmedida.equals(medidas)) {
                        oldMedidasOfDefectoxmedidaCollectionNewDefectoxmedida.getDefectoxmedidaCollection().remove(defectoxmedidaCollectionNewDefectoxmedida);
                        oldMedidasOfDefectoxmedidaCollectionNewDefectoxmedida = em.merge(oldMedidasOfDefectoxmedidaCollectionNewDefectoxmedida);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = medidas.getMeasure();
                if (findMedidas(id) == null) {
                    throw new NonexistentEntityException("The medidas with id " + id + " no longer exists.");
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
            Medidas medidas;
            try {
                medidas = em.getReference(Medidas.class, id);
                medidas.getMeasure();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The medidas with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Defectoxmedida> defectoxmedidaCollectionOrphanCheck = medidas.getDefectoxmedidaCollection();
            for (Defectoxmedida defectoxmedidaCollectionOrphanCheckDefectoxmedida : defectoxmedidaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Medidas (" + medidas + ") cannot be destroyed since the Defectoxmedida " + defectoxmedidaCollectionOrphanCheckDefectoxmedida + " in its defectoxmedidaCollection field has a non-nullable medidas field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            TiposMedida tiposMedida = medidas.getTiposMedida();
            em.remove(medidas);
            em.getTransaction().commit();
        } finally {
           
        }
    }

    public List<Medidas> findMedidasEntities() {
        return findMedidasEntities(true, -1, -1);
    }

    public List<Medidas> findMedidasEntities(int maxResults, int firstResult) {
        return findMedidasEntities(false, maxResults, firstResult);
    }

    private List<Medidas> findMedidasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Medidas.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
         
        }
    }

    public Medidas findMedidas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Medidas.class, id);
        } finally {
           
        }
    }

    public int getMedidasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Medidas> rt = cq.from(Medidas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
           
        }
    }

}
