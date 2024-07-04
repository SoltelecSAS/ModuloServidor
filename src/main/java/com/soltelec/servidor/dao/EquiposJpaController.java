/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soltelec.servidor.dao;

import com.soltelec.servidor.dao.exceptions.IllegalOrphanException;
import com.soltelec.servidor.dao.exceptions.NonexistentEntityException;
import com.soltelec.servidor.dao.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.soltelec.servidor.model.Calibraciones;
import com.soltelec.servidor.model.Equipos;
import com.soltelec.servidor.conexion.PersistenceController;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 *
 * @author Dany
 */
public class EquiposJpaController implements Serializable {

    private EntityManager getEntityManager() {
        return PersistenceController.getEntityManager();
    }

    public void create(Equipos equipos) throws PreexistingEntityException {
        if (equipos.getCalibracionesList() == null) {
            equipos.setCalibracionesList(new ArrayList<Calibraciones>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Calibraciones> attachedCalibracionesList = new ArrayList<>();
            for (Calibraciones calibracionesListCalibracionesToAttach : equipos.getCalibracionesList()) {
                calibracionesListCalibracionesToAttach = em.getReference(calibracionesListCalibracionesToAttach.getClass(), calibracionesListCalibracionesToAttach.getCalibration());
                attachedCalibracionesList.add(calibracionesListCalibracionesToAttach);
            }
            equipos.setCalibracionesList(attachedCalibracionesList);
            em.persist(equipos);
            for (Calibraciones calibracionesListCalibraciones : equipos.getCalibracionesList()) {
                Equipos oldEquipoOfCalibracionesListCalibraciones = calibracionesListCalibraciones.getEquipo();
                calibracionesListCalibraciones.setEquipo(equipos);
                calibracionesListCalibraciones = em.merge(calibracionesListCalibraciones);
                if (oldEquipoOfCalibracionesListCalibraciones != null) {
                    oldEquipoOfCalibracionesListCalibraciones.getCalibracionesList().remove(calibracionesListCalibraciones);
                    em.merge(oldEquipoOfCalibracionesListCalibraciones);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEquipos(equipos.getIdEquipo()) != null) {
                throw new PreexistingEntityException("Equipos " + equipos + " already exists.", ex);
            }
            throw ex;
        } finally {
        }
    }

    public void edit(Equipos equipos) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Equipos persistentEquipos = em.find(Equipos.class, equipos.getIdEquipo());
            List<Calibraciones> calibracionesListOld = persistentEquipos.getCalibracionesList();
            List<Calibraciones> calibracionesListNew = equipos.getCalibracionesList();
            List<String> illegalOrphanMessages = null;
            for (Calibraciones calibracionesListOldCalibraciones : calibracionesListOld) {
                if (!calibracionesListNew.contains(calibracionesListOldCalibraciones)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<>();
                    }
                    illegalOrphanMessages.add("You must retain Calibraciones " + calibracionesListOldCalibraciones + " since its equipo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Calibraciones> attachedCalibracionesListNew = new ArrayList<>();
            for (Calibraciones calibracionesListNewCalibracionesToAttach : calibracionesListNew) {
                calibracionesListNewCalibracionesToAttach = em.getReference(calibracionesListNewCalibracionesToAttach.getClass(), calibracionesListNewCalibracionesToAttach.getCalibration());
                attachedCalibracionesListNew.add(calibracionesListNewCalibracionesToAttach);
            }
            calibracionesListNew = attachedCalibracionesListNew;
            equipos.setCalibracionesList(calibracionesListNew);
            equipos = em.merge(equipos);
            for (Calibraciones calibracionesListNewCalibraciones : calibracionesListNew) {
                if (!calibracionesListOld.contains(calibracionesListNewCalibraciones)) {
                    Equipos oldEquipoOfCalibracionesListNewCalibraciones = calibracionesListNewCalibraciones.getEquipo();
                    calibracionesListNewCalibraciones.setEquipo(equipos);
                    calibracionesListNewCalibraciones = em.merge(calibracionesListNewCalibraciones);
                    if (oldEquipoOfCalibracionesListNewCalibraciones != null && !oldEquipoOfCalibracionesListNewCalibraciones.equals(equipos)) {
                        oldEquipoOfCalibracionesListNewCalibraciones.getCalibracionesList().remove(calibracionesListNewCalibraciones);
                        oldEquipoOfCalibracionesListNewCalibraciones = em.merge(oldEquipoOfCalibracionesListNewCalibraciones);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (IllegalOrphanException ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = equipos.getIdEquipo();
                if (findEquipos(id) == null) {
                    throw new NonexistentEntityException("The equipos with id " + id + " no longer exists.");
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
            Equipos equipos;
            try {
                equipos = em.getReference(Equipos.class, id);
                equipos.getIdEquipo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The equipos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Calibraciones> calibracionesListOrphanCheck = equipos.getCalibracionesList();
            for (Calibraciones calibracionesListOrphanCheckCalibraciones : calibracionesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<>();
                }
                illegalOrphanMessages.add("El Equipo con Id (" + equipos.getIdEquipo() + ") no puede ser eliminado por que tiene calibraciones registras");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(equipos);
            em.getTransaction().commit();
        } finally {
        }
    }

    public List<Equipos> findEquiposEntities() {
        return findEquiposEntities(true, -1, -1);
    }

    public List<Equipos> findEquiposEntities(int maxResults, int firstResult) {
        return findEquiposEntities(false, maxResults, firstResult);
    }

    private List<Equipos> findEquiposEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Equipos.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
        }
    }

    public Equipos findEquipos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Equipos.class, id);
        } finally {
        }
    }

    public int getEquiposCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Equipos> rt = cq.from(Equipos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {

        }
    }

    public List<Equipos> buscarEquipos(String texto) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Equipos> query = em.createQuery("SELECT e FROM Equipos e WHERE e.marca LIKE :marca OR e.serial LIKE :serial", Equipos.class);
            query.setParameter("marca", "%" + texto + "%");
            query.setParameter("serial", "%" + texto + "%");
            return query.getResultList();
        } finally {

        }
    }

    public int buscarCantidadOpacimetros() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Equipos> query = em.createQuery("SELECT e FROM Equipos e WHERE e.tipo LIKE :opacimetro", Equipos.class);
            query.setParameter("opacimetro", "OPACIMETRO");
            int amount = query.getResultList().size();
            return amount;
        } catch (Exception exception) {
            System.out.println("------" + exception.getMessage());
        }
        return 0;
    }

//            public int buscarCantidaAnalizadores4T(){
//        EntityManager em = getEntityManager();
//        try {
//            TypedQuery<Equipos> query = em.createQuery("SELECT e FROM Equipos e WHERE e.Cod_Interno = '4T'", Equipos.class);
//            query.setParameter("4T", "4t");
//            int amount = query.getResultList().size();
//            return amount;
//        }catch(Exception exception){
//            System.out.println("------" + exception.getMessage());
//        }
//        return 0;
//    }
    public int buscarCantidadAnalizadores4T() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Equipos> query = em.createQuery("SELECT e FROM Equipos e WHERE e.Cod_Interno = '4T'", Equipos.class);
            int amount = query.getResultList().size();
            return amount;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    public int buscarCantidaAnalizadores2T() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Equipos> query = em.createQuery("SELECT e FROM Equipos e WHERE e.Cod_Interno = '2T'", Equipos.class);
            int amount = query.getResultList().size();
            return amount;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    public Integer buscarEquiposBySerialRes(String serialRes) {
        EntityManager em = getEntityManager();
        try {
            Query q2 = em.createQuery("SELECT e.idEquipo FROM Equipos e WHERE e.reolucionserial like :serialRes");
            q2.setParameter("serialRes", serialRes);
            //q2.setParameter("serialRes",serialRes.substring(0, 5).concat("%"));
            return (Integer) q2.getSingleResult();
        } catch (Exception e) {
            return 0;
        } finally {

        }
    }


    public Equipos buscarEquipoBySerialRes(String serialRes) {
        EntityManager em = getEntityManager();
        try {
            Query q2 = em.createQuery("SELECT e FROM Equipos e WHERE e.reolucionserial like :serialRes");
            q2.setParameter("serialRes", serialRes);
            //q2.setParameter("serialRes",serialRes.substring(0, 5).concat("%"));
            return (Equipos) q2.getSingleResult();
        } catch (Exception e) {
            return null;
        } finally {

        }
    }

    public Equipos buscarPorSerial(String serialRes) {
        EntityManager em = getEntityManager();
        try {
            Query q2 = em.createQuery("SELECT eq FROM Equipos eq WHERE eq.reolucionserial = :serialRes");
            q2.setParameter("serialRes", serialRes);
            return (Equipos) q2.getSingleResult();
        } catch (NoResultException e) {
            return null; // Devolver null si no se encuentra ningún resultado
        } finally {
        }
    }

    public Boolean isRegistrado(String serial) {
        EntityManager em = getEntityManager();
        Query q = em.createQuery("SELECT e FROM Equipos e WHERE e.serial LIKE '" + serial + "'");

        try {
            q.getSingleResult();
            return true;
        } catch (NoResultException e) {
            return false;
        } finally {

        }
    }
    
    
public String buscarUsuario(Integer usuarioId) {
    EntityManager em = getEntityManager();
    try {
        Query q2 = em.createQuery("SELECT us.nombreusuario FROM Usuarios us WHERE us.geuser = :usuario");
        q2.setParameter("usuario", usuarioId);
        return (String) q2.getSingleResult();
    } catch (Exception e) {
        e.printStackTrace();
        return "";
    } finally {
        // Cierra el EntityManager aquí si es necesario.
    }
}
}
