/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soltelec.servidor.dao;

import com.soltelec.servidor.model.HojaPruebas;
import com.soltelec.servidor.model.*;
import com.soltelec.servidor.model.MedidaGases;
import com.soltelec.servidor.model.Pruebas;
import com.soltelec.servidor.model.TipoPrueba;
import com.soltelec.servidor.model.TiposGasolina;
import com.soltelec.servidor.model.Usuarios;
import com.soltelec.servidor.model.Vehiculos;
import com.soltelec.servidor.model.ReporteSonometro;
import com.mysql.cj.jdbc.Blob;
import com.soltelec.servidor.conexion.PersistenceController;
import com.soltelec.servidor.dao.exceptions.NonexistentEntityException;
import com.soltelec.servidor.dao.exceptions.PreexistingEntityException;
import com.soltelec.servidor.utils.CargarArchivos;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;

/**
 *
 * @author Gerencia TIC
 */
public class PruebasJpaController {

    private Image data;

    private EntityManager getEntityManager() {
        return PersistenceController.getEntityManager();
    }

    public void create(Pruebas pruebas) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios usuarios = pruebas.getUsuarios();
            if (usuarios != null) {
                usuarios = em.getReference(usuarios.getClass(), usuarios.getGeuser());
                pruebas.setUsuarios(usuarios);
            }
            TipoPrueba tipoPrueba = pruebas.getTipoPrueba();
            if (tipoPrueba != null) {
                tipoPrueba = em.getReference(tipoPrueba.getClass(), tipoPrueba.getTesttype());
                pruebas.setTipoPrueba(tipoPrueba);
            }
            HojaPruebas hojaPruebas = pruebas.getHojaPruebas();
            if (hojaPruebas != null) {
                hojaPruebas = em.getReference(hojaPruebas.getClass(), hojaPruebas.getTestsheet());
                pruebas.setHojaPruebas(hojaPruebas);
            }
            em.persist(pruebas);
            if (hojaPruebas != null) {
                hojaPruebas.getPruebasCollection().add(pruebas);
                em.merge(hojaPruebas);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPruebas(pruebas.getIdPruebas()) != null) {
                throw new PreexistingEntityException("Pruebas " + pruebas + " already exists.", ex);
            }
            throw ex;
        } finally {

        }
    }

    public void edit(Pruebas pruebas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pruebas persistentPruebas = em.find(Pruebas.class, pruebas.getIdPruebas());
            Usuarios usuariosOld = persistentPruebas.getUsuarios();
            Usuarios usuariosNew = pruebas.getUsuarios();
            TipoPrueba tipoPruebaOld = persistentPruebas.getTipoPrueba();
            TipoPrueba tipoPruebaNew = pruebas.getTipoPrueba();
            HojaPruebas hojaPruebasOld = persistentPruebas.getHojaPruebas();
            HojaPruebas hojaPruebasNew = pruebas.getHojaPruebas();
            if (usuariosNew != null) {
                usuariosNew = em.getReference(usuariosNew.getClass(), usuariosNew.getGeuser());
                pruebas.setUsuarios(usuariosNew);
            }
            if (tipoPruebaNew != null) {
                tipoPruebaNew = em.getReference(tipoPruebaNew.getClass(), tipoPruebaNew.getTesttype());
                pruebas.setTipoPrueba(tipoPruebaNew);
            }
            if (hojaPruebasNew != null) {
                hojaPruebasNew = em.getReference(hojaPruebasNew.getClass(), hojaPruebasNew.getTestsheet());
                pruebas.setHojaPruebas(hojaPruebasNew);
            }
            pruebas = em.merge(pruebas);
            if (hojaPruebasOld != null && !hojaPruebasOld.equals(hojaPruebasNew)) {
                hojaPruebasOld.getPruebasCollection().remove(pruebas);
                hojaPruebasOld = em.merge(hojaPruebasOld);
            }
            if (hojaPruebasNew != null && !hojaPruebasNew.equals(hojaPruebasOld)) {
                hojaPruebasNew.getPruebasCollection().add(pruebas);
                em.merge(hojaPruebasNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pruebas.getIdPruebas();
                if (findPruebas(id) == null) {
                    throw new NonexistentEntityException("The pruebas with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {

        }
    }

    public void editPrueXFechas(Integer idPrueba, Date fecha) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
           if(em.getTransaction().isActive()){
               
           }else{
               em.getTransaction().begin();
           }
            Pruebas pruebas = em.find(Pruebas.class, idPrueba);
            pruebas.setFechaPrueba(fecha);
            em.merge(pruebas);
            em.getTransaction().commit();            
        } catch (Exception ex) {
           int eve=0;
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pruebas pruebas;
            try {
                pruebas = em.getReference(Pruebas.class, id);
                pruebas.getIdPruebas();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pruebas with id " + id + " no longer exists.", enfe);
            }
            Usuarios usuarios = pruebas.getUsuarios();
            HojaPruebas hojaPruebas = pruebas.getHojaPruebas();
            if (hojaPruebas != null) {
                hojaPruebas.getPruebasCollection().remove(pruebas);
                em.merge(hojaPruebas);
            }
            em.remove(pruebas);
            em.getTransaction().commit();
        } finally {

        }
    }

    public List<Pruebas> findPruebasEntities() {
        return findPruebasEntities(true, -1, -1);
    }

    public List<Pruebas> findPruebasEntities(int maxResults, int firstResult) {
        return findPruebasEntities(false, maxResults, firstResult);
    }

    private List<Pruebas> findPruebasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pruebas.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {

        }
    }

    public Pruebas findPruebas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pruebas.class, id);
        } finally {

        }
    }

    public int getPruebasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pruebas> rt = cq.from(Pruebas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {

        }
    }

    public List<Pruebas> findPruebasGasesAbortadas(Date com1, Date com2) {
        com2.setDate(com2.getDate()+1);
        System.out.println("fecha final de aborto= "+ com2);
        EntityManager em = getEntityManager();

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String com = sdf.format(com1);

        java.text.SimpleDateFormat sdf2 = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String com12 = sdf2.format(com2);
        //String msql = "SELECT d  FROM Vehiculos d, Propietarios m WHERE d.propietarios = m  and m.carowner " + com + " " + mat1;
        String msql = "SELECT c FROM Pruebas AS c, TipoPrueba t  WHERE c.tipoPrueba  = t  and t.testtype = 8 and c.abortada='Y' and c.fechaPrueba >= '" + com + "' and c.fechaPrueba <= '" + com12 + "'";
        Query query = em.createQuery(msql);
        return query.getResultList();
    }

    //Reporte Cormacarena
    public List<HojaPruebas> findHP_porFecha(Date com1, Date com2) {
        EntityManager em = getEntityManager();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String com = sdf.format(com1);

        java.text.SimpleDateFormat sdf2 = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String com12 = sdf2.format(com2);
        //String msql = "SELECT d  FROM Vehiculos d, Propietarios m WHERE d.propietarios = m  and m.carowner " + com + " " + mat1;
        String msql = "SELECT c FROM Pruebas AS c JOIN c.hojaPruebas h WHERE c.fechaPrueba >= '" + com + "' and c.fechaPrueba <= '" + com12 + "'";
        Query query = em.createQuery(msql);
        List<HojaPruebas> hojaprueba = null;

        try {
            hojaprueba = (List<HojaPruebas>) query.getResultList();
        } catch (NoResultException e) {
            System.out.println("Error findHP_porFecha(PruebasJpaController)");
            System.out.println(e);
        } finally {

        }
        return hojaprueba;
    }

    public List<HojaPruebas> findHPXFecha(Date com1, Date com2) {
        EntityManager em = getEntityManager();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String com = sdf.format(com1);

        java.text.SimpleDateFormat sdf2 = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String com12 = sdf2.format(com2);
        //String msql = "SELECT d  FROM Vehiculos d, Propietarios m WHERE d.propietarios = m  and m.carowner " + com + " " + mat1;
        String msql = "SELECT hp FROM HojaPruebas  hp join fetch hp.vehiculos  vh WHERE vh.tiposGasolina.fueltype=1 and   hp.fechaingresovehiculo >= '" + com + "' and hp.fechaingresovehiculo <= '" + com12 + "'";
        Query query = em.createQuery(msql);
        List<HojaPruebas> hojaprueba = null;

        try {
            hojaprueba = (List<HojaPruebas>) query.getResultList();
        } catch (NoResultException e) {
            System.out.println("Error findHP_porFecha(PruebasJpaController)");
            System.out.println(e);
        } finally {

        }
        return hojaprueba;
    }

    //FIN rc
    public List<Pruebas> findPruebaSonoraByFecha(Date com1, Date com2, Integer tipoGasolina) {
        EntityManager em = getEntityManager();

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String com = sdf.format(com1);

        java.text.SimpleDateFormat sdf2 = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String com12 = sdf2.format(com2);

        //String msql = "SELECT d  FROM Vehiculos d, Propietarios m WHERE d.propietarios = m  and m.carowner " + com + " " + mat1;
        String msql = "SELECT c FROM Pruebas AS c JOIN c.tipoPrueba t JOIN c.hojaPruebas.vehiculos.tiposGasolina g WHERE g.fueltype = :fueltype and t.testtype = 7  and c.fechaPrueba >= '" + com + "' and c.fechaPrueba <= '" + com12 + "'";
        Query query = em.createQuery(msql);
        query.setParameter("fueltype", tipoGasolina);
        return query.getResultList();
    }

    public List<Object[]> findMedidas_pruebaRuido(Date com1, Date com2) {
        EntityManager em = getEntityManager();

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String com = sdf.format(com1);

        java.text.SimpleDateFormat sdf2 = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String com12 = sdf2.format(com2);

        String msql = "SELECT c.idPruebas ,tm.measuretype, tm.nombremedida, m.valormedida  FROM  Medidas m, Pruebas c, TipoPrueba t, TiposMedida tm  WHERE m.pruebas = c and m.tiposMedida = tm and c.tipoPrueba  = t  and t.testtype = 7  and c.fechaPrueba >= '" + com + "' and c.fechaPrueba <= '" + com12 + "'";

        Query query = em.createQuery(msql);
        return query.getResultList();
    }

    public List<Object[]> findMedidas_pruebaGases(Date com1, Date com2) {
        EntityManager em = getEntityManager();

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String com = sdf.format(com1);

        java.text.SimpleDateFormat sdf2 = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String com12 = sdf2.format(com2);

        String msql = "SELECT tm.measuretype, tm.nombremedida, m.valormedida, c.idPruebas  FROM  Medidas m,Pruebas c, TipoPrueba t, TiposMedida tm  WHERE m.pruebas = c and m.tiposMedida = tm and c.tipoPrueba  = t  and t.testtype = 8  and c.fechaPrueba >= '" + com + "' and c.fechaPrueba <= '" + com12 + "'";

        Query query = em.createQuery(msql);
        return query.getResultList();
    }

    public List<Pruebas> findPruebasReporte(Date fechaInicial, Date fechaFinal) {
        EntityManager em = getEntityManager();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String fechaI = sdf.format(fechaInicial);
        String fechaF = sdf.format(fechaFinal) + " 23:59:59";
        Query query = em.createQuery("SELECT p FROM Pruebas p WHERE p.tipoPrueba.testtype = 8 AND p.finalizada = 'Y' AND (p.fechaPrueba >= '" + fechaI + "' AND p.fechaPrueba <= '" + fechaF + "')");
        //Query query = em.createQuery("SELECT p FROM Pruebas p WHERE p.tipoPrueba.testtype = 8");     
        List<Pruebas> pruebas = null;
        try {
            pruebas = (List<Pruebas>) query.getResultList();
        } catch (NoResultException e) {
            System.out.println("Error findPruebasReporte (PruebasJpaController)");
            System.out.println(e);
        } finally {
        }
        return pruebas;
    }

    public List<HojaPruebas> listaHojaPruebasDieselPorFecha(Date fechaInicial, Date fechaFinal) {
        TypedQuery<HojaPruebas> query = null;
        try {
            EntityManager em = getEntityManager();
            query = em.createQuery("SELECT hp FROM   HojaPruebas  hp join fetch hp.vehiculos  vh WHERE vh.tiposGasolina.fueltype=3 AND hp.finalizada = 'Y' AND hp.fechaingresovehiculo  BETWEEN :inicial and :final ", HojaPruebas.class);
            DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String fechaIni = format.format(fechaInicial);
            String fechaFin = format.format(fechaFinal);
            fechaInicial = format.parse(fechaIni);
            fechaFinal = format.parse(fechaFin);
            query.setParameter("inicial", fechaInicial);
            query.setParameter("final", fechaFinal);

        } catch (ParseException ex) {
            Logger.getLogger(PruebasJpaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<HojaPruebas> pruebas = null;
        try {
            pruebas = (List<HojaPruebas>) query.getResultList();
        } catch (NoResultException e) {
            System.out.println("Error findPruebasReporte (PruebasJpaController)");
            System.out.println(e);
        } finally {
        }
        return pruebas;

    }

    public List<HojaPruebas> listaHojaPruebasNoDieselPorFecha(Date fechaInicial, Date fechaFinal) {
        EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<HojaPruebas> cq = cb.createQuery(HojaPruebas.class);
        Root<HojaPruebas> root = cq.from(HojaPruebas.class);

        /*Join<HojaPruebas, Vehiculos> join = root.join(HojaPruebas_.vehiculos, JoinType.INNER);
         Join<Vehiculos, TiposGasolina> join1 = join.join(Vehiculos_.tiposGasolina, JoinType.INNER);
         Predicate between = cb.between(root.get(HojaPruebas_.fechaingresovehiculo), fechaInicial, fechaFinal);
         Predicate diesel = cb.notEqual(join1.get(TiposGasolina_.fueltype), 3);*/
        //cq.where(cb.and(between, diesel));
        return em.createQuery(cq).getResultList();
    }

    public MedidaGases llenar(Object[] p) {
        MedidaGases l = new MedidaGases();
        return l;
    }

    public List<Object[]> findHojaPrueba() {
        EntityManager em = getEntityManager();

        String msql = "SELECT h.testsheet ,c.carplate, p.carowner , h.fechaingresovehiculo, h.fechaexpiracionrevision, h.aprobado, h.anulado, h.finalizada FROM HojaPruebas h, Propietarios p, Vehiculos AS c WHERE  h.vehiculos = c and h.propietarios = p order by h.testsheet";
        Query query = em.createQuery(msql);
//query.setParameter("keyword", "'" + keyword + "'");

        return query.getResultList();
    }

    public List<Object[]> findHojasPrueba_porParametros(String mat, String campo, String com) {
        EntityManager em = getEntityManager();

        String msql;
        if (campo.equals("c.carplate")) {
            msql = "SELECT h.testsheet ,c.carplate, p.carowner , h.fechaingresovehiculo, h.fechaexpiracionrevision, h.aprobado, h.anulado, h.finalizada FROM HojaPruebas h, Propietarios p, Vehiculos AS c WHERE  h.vehiculos = c and h.propietarios = p and " + campo + " " + com + " '" + mat + "' order by h.testsheet";
        } else {
            msql = "SELECT h.testsheet ,c.carplate, p.carowner , h.fechaingresovehiculo, h.fechaexpiracionrevision, h.aprobado, h.anulado, h.finalizada FROM HojaPruebas h, Propietarios p, Vehiculos AS c WHERE  h.vehiculos = c and h.propietarios = p and " + campo + " " + com + " " + mat + " order by h.testsheet";

        }
        Query query = em.createQuery(msql);

        return query.getResultList();
    }

    public List<Object[]> findHojasPrueba_porParametroCed(String campo, String valor, String condicion) {
        EntityManager em = getEntityManager();

        String msql = "SELECT h.testsheet ,c.carplate, p.carowner , h.fechaingresovehiculo, h.fechaexpiracionrevision, h.aprobado, h.anulado, h.finalizada FROM HojaPruebas h, Propietarios p, Vehiculos AS c WHERE  h.vehiculos = c and h.propietarios = p and " + campo + " " + condicion + " '" + valor + "' order by h.testsheet";
        Query query = em.createQuery(msql);

        return query.getResultList();
    }

    public List<Pruebas> reporteMotosOnacMedellin(Date fechaInicial, Date fechaFinal) {
        EntityManager em = getEntityManager();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String startDate = sdf.format(fechaInicial);
        String endDate = sdf.format(fechaFinal) + " 23:59:59";

        TypedQuery<Pruebas> query = em.createQuery("SELECT p FROM Pruebas p JOIN p.hojaPruebas.vehiculos v WHERE p.tipoPrueba.testtype = 5 AND v.tipoVehiculo.cartype = 4 AND p.fechaPrueba BETWEEN '" + startDate + "' AND '" + endDate + "'", Pruebas.class);

        return query.getResultList();
    }

    public Image findFoto(int mat) throws SQLException, IOException {
        EntityManager em = getEntityManager();

        String msql = "SELECT f.foto1 FROM Fotos f, HojaPruebas h WHERE  f.hojaPruebas = h and h.testsheet = " + mat + " ";
        Query query = em.createQuery(msql);
        Blob blob = (Blob) query.getResultList();

        byte[] imageBytes = blob.getBytes(1, (int) blob.length());
        data = ConvertirImagen(imageBytes);

        return data;
    }

    private Image ConvertirImagen(byte[] bytes) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        Iterator readers = ImageIO.getImageReadersByFormatName("jpeg");
        ImageReader reader = (ImageReader) readers.next();
        Object source = bis; // File or InputStream
        ImageInputStream iis = ImageIO.createImageInputStream(source);
        reader.setInput(iis, true);
        ImageReadParam param = reader.getDefaultReadParam();
        return reader.read(0, param);
    }

    public List<Pruebas> findPruebasByFechaGDM(Date com1, Date com2) {
        EntityManager em = getEntityManager();

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd 00:00:00");
        String com = sdf.format(com1);

        java.text.SimpleDateFormat sdf2 = new java.text.SimpleDateFormat("yyyy-MM-dd 00:00:00");
        String com12 = sdf2.format(com2);

        //String msql = "SELECT d  FROM Vehiculos d, Propietarios m WHERE d.propietarios = m  and m.carowner " + com + " " + mat1;
        String msql = "SELECT c FROM Pruebas AS c JOIN c.tipoPrueba t WHERE c.fechaPrueba >= '" + com + "' and c.fechaPrueba < '" + com12 + "' order by c.fechaPrueba";
        //String msql = "SELECT c FROM Pruebas AS c JOIN c.tipoPrueba t WHERE (t.testtype = 8)  and  c.hojaPruebas.vehiculos.carplate = 'xzl318' ";
        Query query = em.createQuery(msql);
        return query.getResultList();
    }
    
    public List<Pruebas> findPruebasByFechaPrueba8(Date com1, Date com2) {
        EntityManager em = getEntityManager();

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd 00:00:00");
        String com = sdf.format(com1);

        java.text.SimpleDateFormat sdf2 = new java.text.SimpleDateFormat("yyyy-MM-dd 00:00:00");
        String com12 = sdf2.format(com2);

        //String msql = "SELECT d  FROM Vehiculos d, Propietarios m WHERE d.propietarios = m  and m.carowner " + com + " " + mat1;
        //String msql = "SELECT c FROM Pruebas AS c JOIN c.tipoPrueba t WHERE (t.testtype = 8 )  and c.fechaPrueba >= '" + com + "' and c.fechaPrueba <= '" + com12 + "' order by c.hojaPruebas.vehiculos";
          String msql = "SELECT p FROM Pruebas AS p INNER JOIN HojaPruebas AS hp ON(hp.testsheet = p.hojaPruebas.testsheet) INNER JOIN Vehiculos AS v ON(hp.vehiculos.car = v.car) WHERE  p.tipoPrueba.testtype = " + "'8'" + "  AND p.fechaPrueba >= '" + com + "'  AND p.fechaPrueba < '" + com12 + "' AND p.finalizada = " + "'Y'" +" AND hp.preventiva = " +"'N'" +" AND hp.estado_sicov = " +"'SINCRONIZADO'" +" AND v.tiposGasolina.fueltype <> " +"'3'" + " order by p.fechaPrueba asc";  
        //String msql = "SELECT c FROM Pruebas AS c JOIN c.tipoPrueba t WHERE (t.testtype = 8)  and  c.hojaPruebas.vehiculos.carplate = 'xzl318' ";
        Query query = em.createQuery(msql);
        return query.getResultList();
    }

    public List<Pruebas> findPruebasByFechaPruebas(Date com1, Date com2) {
        EntityManager em = getEntityManager();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd 00:00:00");
        String com = sdf.format(com1);
        java.text.SimpleDateFormat sdf2 = new java.text.SimpleDateFormat("yyyy-MM-dd 23:59:00");
        String com12 = sdf2.format(com2);
        //String msql = "SELECT d  FROM Vehiculos d, Propietarios m WHERE d.propietarios = m  and m.carowner " + com + " " + mat1;
        String msql = "SELECT c FROM Pruebas AS c JOIN c.tipoPrueba t WHERE (t.testtype = 7 OR t.testtype = 8)  and c.fechaPrueba >+= '" + com + "' and c.fechaPrueba <= '" + com12 + "' and c.hojaPruebas.preventiva = 'N' '" + "' order by c.hojaPruebas.vehiculos";
        //String msql = "SELECT c FROM Pruebas AS c JOIN c.tipoPrueba t WHERE (t.testtype = 7 OR t.testtype = 8)  and  c.hojaPruebas.vehiculos.carplate = 'xzl318' '";

        //String msql = "SELECT c FROM Pruebas AS c JOIN c.tipoPrueba t WHERE (t.testtype = 2 OR t.testtype = 5 OR t.testtype = 6)  and c.fechaPrueba >+= '" + com + "' and c.fechaPrueba <= '" + com12 + "' order by c.hojaPruebas.vehiculos";
        Query query = em.createQuery(msql);
        return query.getResultList();
    }

    public List<ReporteSonometro> findReporteSonometro(Date fechaInicial, Date fechaFinal) {
        try {
            Properties properties = new Properties();
            properties.load(CargarArchivos.cargarArchivo("conexion.properties"));

            EntityManager em = getEntityManager();

            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");

            String fechaI = sdf.format(fechaInicial);
            String fechaF = sdf.format(fechaFinal) + " 23:59:59";

            Query query = em.createNativeQuery("SELECT  DISTINCT  v.CARPLATE as PLACA_VEHICULO,  \n"
                    + "                                         c.codigo as NO_CDA,\n"
                    + "                                         c.nombre as NOMBRE_CDA,  \n"
                    + "                                         c.NIT as NIT_CDA,  \n"
                    + "                                         c.direccion as DIRECCION_CDA,  \n"
                    + "                                         e.marca as MARCA_SONOMETRO,  \n"
                    + "                                         e.sonometro as MODELO_SONOMETRO,     \n"
                    + "                                         e.serie as SERIE_SONOMETRO,  \n"
                    + "                                         e.tipo as TIPO_SONOMETRO,  \n"
                    + "                                         p.Fecha_prueba as FECHA_PRUEBA,  \n"
                    + "                                         v.Modelo as MODELO,  \n"
                    + "                                         \"OTTO\"   as TIPO_CICLO,  \n"
                    + "                                         v.Cinlindraje as CICLINDRAJE,  \n"
                    + "                                         v.CLASS as TIPOVEHICULO,\n"
                    + "                                          ROUND(AVG (m.Valor_medida)) as RESULTADO_ESCAPE           \n"
                    + "                                          FROM cda c,equipos e, pruebas p   \n"
                    + "                                    INNER JOIN hoja_pruebas hp  \n"
                    + "                                            ON p.hoja_pruebas_for = hp.TESTSHEET  \n"
                    + "                                    INNER JOIN vehiculos v  \n"
                    + "                                            ON v.CAR = hp.Vehiculo_for          \n"
                    + "                                    INNER JOIN tipo_prueba tp  \n"
                    + "                                            ON tp.TESTTYPE = p.Tipo_prueba_for  \n"
                    + "                                    INNER JOIN medidas m\n"
                    + "                                            ON m.TEST = p.Id_Pruebas        \n"
                    + "	                                      WHERE tp.TESTTYPE = 7  \n"
                    + "	                                        AND p.Fecha_prueba   \n"
                    + "	                                    BETWEEN ?\n"
                    + "	                                        AND ?\n"
                    + "	                                        AND e.id_equipo = " + properties.getProperty("numero.equipo") + "  \n"
                    + "	                                        AND m.Valor_medida >1\n"
                    + "	                                   GROUP BY v.CARPLATE", ReporteSonometro.class);

            query.setParameter(1, fechaInicial);
            query.setParameter(2, fechaFinal);
            try {
                return query.getResultList();
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {

            }

        } catch (IOException ex) {
            Logger.getLogger(PruebasJpaController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public List<Pruebas> findPruebas_pruebaGases(Date com1, Date com2) {
        EntityManager em = getEntityManager();

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String com = sdf.format(com1);

        java.text.SimpleDateFormat sdf2 = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String com12 = sdf2.format(com2);
        //String msql = "SELECT d  FROM Vehiculos d, Propietarios m WHERE d.propietarios = m  and m.carowner " + com + " " + mat1;
        String msql = "SELECT c FROM Pruebas AS c, TipoPrueba t  WHERE c.tipoPrueba  = t  and t.testtype = 8 and c.abortada='Y' and c.fechaprueba >= '" + com + "' and c.fechaprueba <= '" + com12 + "'";
        Query query = em.createQuery(msql);
        return query.getResultList();
    }

    public List<Pruebas> findPruebas_pruebaRuido(Date com1, Date com2) {
        EntityManager em = getEntityManager();

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String com = sdf.format(com1);

        java.text.SimpleDateFormat sdf2 = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String com12 = sdf2.format(com2);

        //String msql = "SELECT d  FROM Vehiculos d, Propietarios m WHERE d.propietarios = m  and m.carowner " + com + " " + mat1;
        String msql = "SELECT c FROM Pruebas AS c, TipoPrueba t  WHERE c.tipoPrueba  = t  and t.testtype = 7  and c.fechaprueba >= '" + com + "' and c.fechaprueba <= '" + com12 + "'";
        Query query = em.createQuery(msql);
        return query.getResultList();
    }

    /**
     * Metodo encargado de consultar los defectos de una prueba visuañ
     *
     * @param idTipoVehiculo
     * @param idPruebaVisual
     *
     * @requerimiento SART-11 Reporte de inspección sensorial
     * @author Luis Berna
     * @fecha 03/02/2016
     *
     * @return Listado de defectos
     */
    public List<DefectoSensorial> findReporteDefectosSensorial(Integer idTipoVehiculo, Integer idPruebaVisual) {
        EntityManager em = getEntityManager();
        try {
//            Query q = em.createNativeQuery("SELECT d.CARDEFAULT CODIGO, "
//                    + "d.Nombre_problema DEFECTO, "
//                    + "CASE WHEN dxp.id_defecto > 0 THEN \"SI\" ELSE \"NO\" END CONSEPTO "
//                    + "FROM defectos d "
//                    + "LEFT OUTER JOIN defxprueba dxp "
//                    + "ON d.CARDEFAULT = dxp.id_defecto "
//                    + "AND dxp.id_prueba = ?", DefectoSensorial.class);
////                    + "WHERE d.TESTTYPE = 1", DefectoSensorial.class);
            Query q = em.createNativeQuery("SELECT d.CARDEFAULT CODIGO,d.Nombre_problema DEFECTO, \n"
                    + "CASE WHEN dxp.id_defecto > 0 THEN 'SI' ELSE 'NO' END CONSEPTO\n"
                    + "FROM (\n"
                    + "select d.* \n"
                    + "from grupos_sub_grupos gg \n"
                    + "inner join sub_grupos sg on gg.SCDEFGROUPSUB = sg.SCDEFGROUPSUB\n"
                    + "inner join defectos d on sg.SCDEFGROUPSUB = d.DEFGROUPSSUB\n"
                    + "where gg.CARTYPE = ? and sg.TESTTYPE = 1 ) d\n"
                    + "LEFT OUTER\n"
                    + "JOIN defxprueba dxp ON d.CARDEFAULT = dxp.id_defecto AND dxp.id_prueba = ?", DefectoSensorial.class);
//                    + "WHERE d.TESTTYPE = 1", DefectoSensorial.class);
            q.setParameter(1, idTipoVehiculo);
            q.setParameter(2, idPruebaVisual);
            return q.getResultList();
        } catch (Exception ex) {
            Logger.getLogger(PruebasJpaController.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        } finally {

        }
    }
}
