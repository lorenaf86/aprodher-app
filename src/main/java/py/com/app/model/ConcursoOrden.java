/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.app.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lorena Franco
 */
@Entity
@Table(name = "concurso_orden")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ConcursoOrden.findAll", query = "SELECT c FROM ConcursoOrden c")})
public class ConcursoOrden implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre")
    private String nombre;
    
    @JoinColumn(name = "id_categoria", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER )
    private Categoria categoria;
    
    @JoinColumn(name = "id_academia", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER )
    private Academia academia;
   
    @JoinColumn(name = "id_modalidad", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER )
    private Modalidad modalidad;
   
    @JoinColumn(name = "id_persona", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER )
    private Persona persona;
    
    @JoinColumn(name = "id_tipo_participacion", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER )
    private TipoParticipacion tipoParticipacion;
    
    @Column(name = "coreografia")
    private Boolean coreografia;
    
    @Column(name = "orden_categoria")
    private Integer ordenCategoria;

    @Column(name = "orden_modalidad")
    private Integer ordenModalidad;

    @Column(name = "orden_tipo")
    private Integer ordenTipo;

    @Column(name = "orden")
    private Integer orden;

    public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public ConcursoOrden() {
    }

    public ConcursoOrden(Integer id) {
        this.id = id;
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

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Academia getAcademia() {
        return academia;
    }

    public void setAcademia(Academia academia) {
        this.academia = academia;
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
        if (!(object instanceof ConcursoOrden)) {
            return false;
        }
        ConcursoOrden other = (ConcursoOrden) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.aprodher.entity.ConcursoOrden[ id=" + id + " ]";
    }

    public Boolean getCoreografia() {
		return coreografia;
	}

	public void setCoreografia(Boolean coreografia) {
		this.coreografia = coreografia;
	}

	public Integer getOrdenCategoria() {
		return ordenCategoria;
	}

	public void setOrdenCategoria(Integer ordenCategoria) {
		this.ordenCategoria = ordenCategoria;
	}

	public Integer getOrdenModalidad() {
		return ordenModalidad;
	}

	public void setOrdenModalidad(Integer ordenModalidad) {
		this.ordenModalidad = ordenModalidad;
	}

	public Integer getOrdenTipo() {
		return ordenTipo;
	}

	public void setOrdenTipo(Integer ordenTipo) {
		this.ordenTipo = ordenTipo;
	}

    
}
