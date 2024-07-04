/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soltelec.servidor.dao;

import com.soltelec.servidor.conexion.PersistenceController;
import com.soltelec.servidor.conexion.exceptions.IllegalOrphanException;
import com.soltelec.servidor.conexion.exceptions.NonexistentEntityException;
import com.soltelec.servidor.conexion.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.soltelec.servidor.model.Ciudades;
import com.soltelec.servidor.model.Departamentos;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Soltelec
 */
public class DepartamentosJpaController implements Serializable {

    /*public DepartamentosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }*/
    private EntityManagerFactory emf = null;

    /*public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }*/
    
    private EntityManager getEntityManager() {
        return PersistenceController.getEntityManager();
    }

    public void create(Departamentos departamentos) throws PreexistingEntityException, Exception {
        if (departamentos.getCiudadesCollection() == null) {
            departamentos.setCiudadesCollection(new ArrayList<Ciudades>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Ciudades> attachedCiudadesCollection = new ArrayList<Ciudades>();
            for (Ciudades ciudadesCollectionCiudadesToAttach : departamentos.getCiudadesCollection()) {
                ciudadesCollectionCiudadesToAttach = em.getReference(ciudadesCollectionCiudadesToAttach.getClass(), ciudadesCollectionCiudadesToAttach.getCity());
                attachedCiudadesCollection.add(ciudadesCollectionCiudadesToAttach);
            }
            departamentos.setCiudadesCollection(attachedCiudadesCollection);
            em.persist(departamentos);
            for (Ciudades ciudadesCollectionCiudades : departamentos.getCiudadesCollection()) {
                Departamentos oldStateOfCiudadesCollectionCiudades = ciudadesCollectionCiudades.getState();
                ciudadesCollectionCiudades.setState(departamentos);
                ciudadesCollectionCiudades = em.merge(ciudadesCollectionCiudades);
                if (oldStateOfCiudadesCollectionCiudades != null) {
                    oldStateOfCiudadesCollectionCiudades.getCiudadesCollection().remove(ciudadesCollectionCiudades);
                    oldStateOfCiudadesCollectionCiudades = em.merge(oldStateOfCiudadesCollectionCiudades);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDepartamentos(departamentos.getIdDepartamento()) != null) {
                throw new PreexistingEntityException("Departamentos " + departamentos + " already exists.", ex);
            }
            throw ex;
        } finally {
           
        }
    }

    public void edit(Departamentos departamentos) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Departamentos persistentDepartamentos = em.find(Departamentos.class, departamentos.getIdDepartamento());
            Collection<Ciudades> ciudadesCollectionOld = persistentDepartamentos.getCiudadesCollection();
            Collection<Ciudades> ciudadesCollectionNew = departamentos.getCiudadesCollection();
            List<String> illegalOrphanMessages = null;
            for (Ciudades ciudadesCollectionOldCiudades : ciudadesCollectionOld) {
                if (!ciudadesCollectionNew.contains(ciudadesCollectionOldCiudades)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Ciudades " + ciudadesCollectionOldCiudades + " since its state field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Ciudades> attachedCiudadesCollectionNew = new ArrayList<Ciudades>();
            for (Ciudades ciudadesCollectionNewCiudadesToAttach : ciudadesCollectionNew) {
                ciudadesCollectionNewCiudadesToAttach = em.getReference(ciudadesCollectionNewCiudadesToAttach.getClass(), ciudadesCollectionNewCiudadesToAttach.getCity());
                attachedCiudadesCollectionNew.add(ciudadesCollectionNewCiudadesToAttach);
            }
            ciudadesCollectionNew = attachedCiudadesCollectionNew;
            departamentos.setCiudadesCollection(ciudadesCollectionNew);
            departamentos = em.merge(departamentos);
            for (Ciudades ciudadesCollectionNewCiudades : ciudadesCollectionNew) {
                if (!ciudadesCollectionOld.contains(ciudadesCollectionNewCiudades)) {
                    Departamentos oldStateOfCiudadesCollectionNewCiudades = ciudadesCollectionNewCiudades.getState();
                    ciudadesCollectionNewCiudades.setState(departamentos);
                    ciudadesCollectionNewCiudades = em.merge(ciudadesCollectionNewCiudades);
                    if (oldStateOfCiudadesCollectionNewCiudades != null && !oldStateOfCiudadesCollectionNewCiudades.equals(departamentos)) {
                        oldStateOfCiudadesCollectionNewCiudades.getCiudadesCollection().remove(ciudadesCollectionNewCiudades);
                        oldStateOfCiudadesCollectionNewCiudades = em.merge(oldStateOfCiudadesCollectionNewCiudades);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = departamentos.getIdDepartamento();
                if (findDepartamentos(id) == null) {
                    throw new NonexistentEntityException("The departamentos with id " + id + " no longer exists.");
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
            Departamentos departamentos;
            try {
                departamentos = em.getReference(Departamentos.class, id);
                departamentos.getIdDepartamento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The departamentos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Ciudades> ciudadesCollectionOrphanCheck = departamentos.getCiudadesCollection();
            for (Ciudades ciudadesCollectionOrphanCheckCiudades : ciudadesCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Departamentos (" + departamentos + ") cannot be destroyed since the Ciudades " + ciudadesCollectionOrphanCheckCiudades + " in its ciudadesCollection field has a non-nullable state field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(departamentos);
            em.getTransaction().commit();
        } finally {
           
        }
    }

    public List<Departamentos> findDepartamentosEntities() {
        return findDepartamentosEntities(true, -1, -1);
    }

    public List<Departamentos> findDepartamentosEntities(int maxResults, int firstResult) {
        return findDepartamentosEntities(false, maxResults, firstResult);
    }

    private List<Departamentos> findDepartamentosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Departamentos.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
          
        }
    }

    public Departamentos findDepartamentos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Departamentos.class, id);
        } finally {
            
        }
    }

    public int getDepartamentosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Departamentos> rt = cq.from(Departamentos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
           
        }
    }
    
}
