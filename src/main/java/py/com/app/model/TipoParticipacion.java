/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.app.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lorena Franco
 */
@Entity
@Table(name = "tipo_participacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoParticipacion.findAll", query = "SELECT t FROM TipoParticipacion t")
    , @NamedQuery(name = "TipoParticipacion.findByUsuMod", query = "SELECT t FROM TipoParticipacion t WHERE t.usuMod = :usuMod")
    , @NamedQuery(name = "TipoParticipacion.findByUsuAlta", query = "SELECT t FROM TipoParticipacion t WHERE t.usuAlta = :usuAlta")
    , @NamedQuery(name = "TipoParticipacion.findByFechaAlta", query = "SELECT t FROM TipoParticipacion t WHERE t.fechaAlta = :fechaAlta")
    , @NamedQuery(name = "TipoParticipacion.findByFechaMod", query = "SELECT t FROM TipoParticipacion t WHERE t.fechaMod = :fechaMod")
    , @NamedQuery(name = "TipoParticipacion.findById", query = "SELECT t FROM TipoParticipacion t WHERE t.id = :id")
    , @NamedQuery(name = "TipoParticipacion.findByDescripcion", query = "SELECT t FROM TipoParticipacion t WHERE t.descripcion = :descripcion")
    , @NamedQuery(name = "TipoParticipacion.findByEstado", query = "SELECT t FROM TipoParticipacion t WHERE t.estado = :estado")})
public class TipoParticipacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 2147483647)
    @Column(name = "usu_mod")
    private String usuMod;
    @Size(max = 2147483647)
    @Column(name = "usu_alta")
    private String usuAlta;
    @Column(name = "fecha_alta")
    @Temporal(TemporalType.DATE)
    private Date fechaAlta;
    @Column(name = "fecha_mod")
    @Temporal(TemporalType.DATE)
    private Date fechaMod;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 2147483647)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 2)
    @Column(name = "estado")
    private String estado;

    public TipoParticipacion() {
    }

    public TipoParticipacion(Integer id) {
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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
        if (!(object instanceof TipoParticipacion)) {
            return false;
        }
        TipoParticipacion other = (TipoParticipacion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.aprodher.aprodherweb.TipoParticipacion[ id=" + id + " ]";
    }

    
}
