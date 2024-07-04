/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soltelec.servidor.model;

import static com.soltelec.servidor.conexion.PersistenceController.getEntityManager;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author Administrador
 */
@Entity
@Table(name = "pruebas")
@NamedQueries({
    @NamedQuery(name = "Pruebas.findAll", query = "SELECT p FROM Pruebas p"),
    @NamedQuery(name = "Pruebas.findByIdPruebas", query = "SELECT p FROM Pruebas p WHERE p.idPruebas = :idPruebas"),
    @NamedQuery(name = "Pruebas.findByFechaprueba", query = "SELECT p FROM Pruebas p WHERE p.fechaPrueba = :fechaprueba"),
    @NamedQuery(name = "Pruebas.findByAutorizada", query = "SELECT p FROM Pruebas p WHERE p.autorizada = :autorizada"),
    @NamedQuery(name = "Pruebas.findByAprobada", query = "SELECT p FROM Pruebas p WHERE p.aprobada = :aprobada"),
    @NamedQuery(name = "Pruebas.findByFinalizada", query = "SELECT p FROM Pruebas p WHERE p.finalizada = :finalizada"),
    @NamedQuery(name = "Pruebas.findByAbortada", query = "SELECT p FROM Pruebas p WHERE p.abortada = :abortada"),
    @NamedQuery(name = "Pruebas.findByFechaaborto", query = "SELECT p FROM Pruebas p WHERE p.fechaAborto = :fechaaborto"),
    @NamedQuery(name = "Pruebas.findByComentarioaborto", query = "SELECT p FROM Pruebas p WHERE p.comentarioAborto = :comentarioaborto"),
    @NamedQuery(name = "Pruebas.findBySerialEquipo", query = "SELECT p FROM Pruebas p WHERE p.serialEquipo = :serialEquipo"),
    @NamedQuery(name = "Pruebas.findByPista", query = "SELECT p FROM Pruebas p WHERE p.pista = :pista")})
public class Pruebas implements Serializable {

    @Id
    @Basic(optional = false)
    @Column(name = "Id_Pruebas")
    private Integer idPruebas;
    @JoinColumn(name = "id_tipo_aborto", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TipoAborto idTipoAborto;
    @Basic(optional = false)
    @Column(name = "Fecha_prueba")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaPrueba;
    @Column(name = "Autorizada")
    private String autorizada;
    @Column(name = "Aprobada")
    private String aprobada;
    @Column(name = "Finalizada")
    private String finalizada;
    @Column(name = "Abortada")
    private String abortada;
    @Column(name = "Fecha_aborto")
    private String fechaAborto;
    @Column(name = "Comentario_aborto")
    private String comentarioAborto;
    @Column(name = "observaciones")
    private String observaciones;
    @Column(name = "Pista")
    private Short pista;
    @JoinColumn(name = "usuario_for", referencedColumnName = "GEUSER")
    @ManyToOne
    private Usuarios usuarios;
    @JoinColumn(name = "Tipo_prueba_for", referencedColumnName = "TESTTYPE")
    @ManyToOne(optional = false)
    private TipoPrueba tipoPrueba;
    @JoinColumn(name = "hoja_pruebas_for", referencedColumnName = "TESTSHEET")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private HojaPruebas hojaPruebas;
//    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "pruebas",fetch= FetchType.LAZY)
    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "pruebas", fetch = FetchType.EAGER)
    private List<Medidas> medidasList;
//    @OneToMany(cascade = CascadeType.MERGE, mappedBy="pruebas",fetch= FetchType.LAZY)
    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "pruebas", fetch = FetchType.EAGER)
    private List<Defxprueba> defectos;
    @Column(name = "Fecha_final")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFinal;
    @Column(name = "serialEquipo")
    private String serialEquipo;

    public Pruebas() {
    }

    public Pruebas(Integer idPruebas) {
        this.idPruebas = idPruebas;
    }

    public Pruebas(Integer idPruebas, Date fechaprueba) {
        this.idPruebas = idPruebas;
        this.fechaPrueba = fechaprueba;
    }

    public Integer getIdPruebas() {
        return idPruebas;
    }

    public void setIdPruebas(Integer idPruebas) {
        this.idPruebas = idPruebas;
    }

    public Date getFechaPrueba() {
        return fechaPrueba;
    }

    public void setFechaPrueba(Date fechaPrueba) {
        this.fechaPrueba = fechaPrueba;
    }

    public String getAutorizada() {
        return autorizada;
    }

    public void setAutorizada(String autorizada) {
        this.autorizada = autorizada;
    }

    public String getAprobada() {
        return aprobada;
    }

    public void setAprobada(String aprobada) {
        this.aprobada = aprobada;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getFinalizada() {
        return finalizada;
    }

    public void setFinalizada(String finalizada) {
        this.finalizada = finalizada;
    }

    public String getAbortada() {
        return abortada;
    }

    public void setAbortada(String abortada) {
        this.abortada = abortada;
    }

    public String getFechaAborto() {
        return fechaAborto;
    }

    public void setFechaAborto(String fechaAborto) {
        this.fechaAborto = fechaAborto;
    }

    public String getComentarioAborto() {
        return comentarioAborto;
    }

    public void setComentarioAborto(String comentarioAborto) {
        this.comentarioAborto = comentarioAborto;
    }

    public Short getPista() {
        return pista;
    }

    public void setPista(Short pista) {
        this.pista = pista;
    }

    public String getSerialEquipo() {
        return serialEquipo;
    }

    public void setSerialEquipo(String serialEquipo) {
        this.serialEquipo = serialEquipo;
    }

    public Usuarios getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }

    public TipoPrueba getTipoPrueba() {
        return tipoPrueba;
    }

    public void setTipoPrueba(TipoPrueba tipoPrueba) {
        this.tipoPrueba = tipoPrueba;
    }

    public HojaPruebas getHojaPruebas() {
        return hojaPruebas;
    }

    public void setHojaPruebas(HojaPruebas hojaPruebas) {
        this.hojaPruebas = hojaPruebas;
    }

    public List<Medidas> getMedidasList() {
        return medidasList;
    }

    public void setMedidasList(List<Medidas> medidasList) {
        this.medidasList = medidasList;
    }

    public List<Defxprueba> getDefectos() {
        return defectos;
    }

    public void setDefectos(List<Defxprueba> defectos) {
        this.defectos = defectos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPruebas != null ? idPruebas.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pruebas)) {
            return false;
        }
        Pruebas other = (Pruebas) object;
        if ((this.idPruebas == null && other.idPruebas != null) || (this.idPruebas != null && !this.idPruebas.equals(other.idPruebas))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.soltelec.model.Pruebas[idPruebas=" + idPruebas + "]";
    }

    public Object get(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return the fechaFinal
     */
    public Date getFechaFinal() {
        return fechaFinal;
    }

    /**
     * @param fechaFinal the fechaFinal to set
     */
    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    /**
     * @return the idTipoAborto
     */
    public TipoAborto getIdTipoAborto() {
        return idTipoAborto;
    }

    /**
     * @param idTipoAborto the idTipoAborto to set
     */
    public void setIdTipoAborto(TipoAborto idTipoAborto) {
        this.idTipoAborto = idTipoAborto;
    }

    public Object getFechaprueba() {
        return fechaPrueba;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Object getFechaaborto() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Object getComentarioaborto() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static String generarConsulta(String serial) {
        String consulta = "SELECT e.pef FROM equipos e WHERE e.serialresolucion = '" + serial + "';";
        return consulta;
    }

    public static String generarIdEquipo(String serial) {
        EntityManager em = getEntityManager();

        try {
            System.out.println("serial" + serial);
            Query query = em.createNativeQuery("SELECT e.id_equipo_calibracion FROM Equipos e WHERE e.serialresolucion = '" + serial + "'");
            query.setParameter("serial", serial);
            System.out.println("serial: "+ serial);
            Integer idEquipo = (Integer) query.getSingleResult();

            if (idEquipo != null) {
                return idEquipo.toString(); // Convierte el ID a cadena y lo retorna.
            } else {
                return ""; // Retorna una cadena vacía si no se encuentra el ID del equipo.
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ""; // En caso de error, retorna una cadena vacía o maneja el error según sea necesario.
        }
    }

}
