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
@Table(name = "concurso_academia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ConcursoAcademia.findAll", query = "SELECT c FROM ConcursoAcademia c")})
public class ConcursoAcademia implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

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
   
    @OneToMany(mappedBy = "concursoAcademia", fetch = FetchType.LAZY)
    private List<ConcursoAcademiaCoreo> concursoAcademiaCoreoList;
    
    @JoinColumn(name = "id_academia", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    private Academia academia;
    
    @JoinColumn(name = "id_concurso", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    private Concurso concurso;

    @JoinColumn(name = "id_persona", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    private Persona persona;

    public ConcursoAcademia() {
    }

    public ConcursoAcademia(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    @XmlTransient
    public List<ConcursoAcademiaCoreo> getConcursoAcademiaCoreoList() {
        return concursoAcademiaCoreoList;
    }

    public void setConcursoAcademiaCoreoList(List<ConcursoAcademiaCoreo> concursoAcademiaCoreoList) {
        this.concursoAcademiaCoreoList = concursoAcademiaCoreoList;
    }

    public Academia getAcademia() {
        return academia;
    }

    public void setAcademia(Academia academia) {
        this.academia = academia;
    }

    public Concurso getConcurso() {
        return concurso;
    }

    public void setConcurso(Concurso concurso) {
        this.concurso = concurso;
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
        if (!(object instanceof ConcursoAcademia)) {
            return false;
        }
        ConcursoAcademia other = (ConcursoAcademia) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.aprodher.entity.ConcursoAcademia[ id=" + id + " ]";
    }

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}
    
}
