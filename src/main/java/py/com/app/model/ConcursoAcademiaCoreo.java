/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.app.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Lorena Franco
 */
@Entity
@Table(name = "concurso_academia_coreo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ConcursoAcademiaCoreo.findAll", query = "SELECT c FROM ConcursoAcademiaCoreo c")})
public class ConcursoAcademiaCoreo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre")
    private String nombre;
    
    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "tiempo")
    private String tiempo;
    
    @Column(name = "usu_mod")
    private String usuMod;

    @Column(name = "usu_alta")
    private String usuAlta;
    
    @Column(name = "fecha_alta")
    @Temporal(TemporalType.DATE)
    private Date fechaAlta;
    
    @Column(name = "fecha_mod")
    @Temporal(TemporalType.DATE)
    private Date fechaMod;
    
    @Size(max = 2)
    @Column(name = "estado")
    private String estado;
    
    @OneToMany(mappedBy = "idAcademiaConcursoCoreo", fetch = FetchType.LAZY)
    private List<ConcursoAcademiaCoreoParticipantes> concursoAcademiaCoreoParticipantesList;
    
    @JoinColumn(name = "id_categoria", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER )
    private Categoria categoria;
    
    @JoinColumn(name = "id_concurso_academia", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER )
    private ConcursoAcademia concursoAcademia;
   
    @JoinColumn(name = "id_modalidad", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER )
    private Modalidad modalidad;
   
    @JoinColumn(name = "id_persona", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER )
    private Persona persona;
    
    @JoinColumn(name = "id_tipo_participacion", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER )
    private TipoParticipacion tipoParticipacion;

    public ConcursoAcademiaCoreo() {
    }

    public ConcursoAcademiaCoreo(Integer id) {
        this.id = id;
    }

    public String getUsuMod() {
        return usuMod;
    }

    public void setUsuMod(String usuMod) {
        this.usuMod = usuMod;
    }

    public String getUsuAlta() {
        return usuAlta;
    }

    public void setUsuAlta(String usuAlta) {
        this.usuAlta = usuAlta;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Date getFechaMod() {
        return fechaMod;
    }

    public void setFechaMod(Date fechaMod) {
        this.fechaMod = fechaMod;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    @XmlTransient
    public List<ConcursoAcademiaCoreoParticipantes> getConcursoAcademiaCoreoParticipantesList() {
        return concursoAcademiaCoreoParticipantesList;
    }

    public void setConcursoAcademiaCoreoParticipantesList(List<ConcursoAcademiaCoreoParticipantes> concursoAcademiaCoreoParticipantesList) {
        this.concursoAcademiaCoreoParticipantesList = concursoAcademiaCoreoParticipantesList;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public ConcursoAcademia getConcursoAcademia() {
        return concursoAcademia;
    }

    public void setConcursoAcademia(ConcursoAcademia concursoAcademia) {
        this.concursoAcademia = concursoAcademia;
    }

    public Modalidad getModalidad() {
        return modalidad;
    }

    public void setModalidad(Modalidad idModalidad) {
        this.modalidad = idModalidad;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public TipoParticipacion getTipoParticipacion() {
        return tipoParticipacion;
    }

    public void setTipoParticipacion(TipoParticipacion tipoParticipacion) {
        this.tipoParticipacion = tipoParticipacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConcursoAcademiaCoreo)) {
            return false;
        }
        ConcursoAcademiaCoreo other = (ConcursoAcademiaCoreo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.aprodher.entity.ConcursoAcademiaCoreo[ id=" + id + " ]";
    }
    
}
