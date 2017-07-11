/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.app.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Lorena Franco
 */
@Entity
@Table(name = "funcionalidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Funcionalidad.findAll", query = "SELECT f FROM Funcionalidad f")
    , @NamedQuery(name = "Funcionalidad.findById", query = "SELECT f FROM Funcionalidad f WHERE f.id = :id")
    , @NamedQuery(name = "Funcionalidad.findByNombre", query = "SELECT f FROM Funcionalidad f WHERE f.nombre = :nombre")
    , @NamedQuery(name = "Funcionalidad.findByModulo", query = "SELECT f FROM Funcionalidad f WHERE f.modulo = :modulo")})
public class Funcionalidad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 2147483647)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 2147483647)
    @Column(name = "modulo")
    private String modulo;
    @OneToMany(mappedBy = "idPadre")
    private List<Funcionalidad> funcionalidadList;
    @JoinColumn(name = "id_padre", referencedColumnName = "id")
    @ManyToOne
    private Funcionalidad idPadre;

    public Funcionalidad() {
    }

    public Funcionalidad(Integer id) {
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

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    @XmlTransient
    public List<Funcionalidad> getFuncionalidadList() {
        return funcionalidadList;
    }

    public void setFuncionalidadList(List<Funcionalidad> funcionalidadList) {
        this.funcionalidadList = funcionalidadList;
    }

    public Funcionalidad getIdPadre() {
        return idPadre;
    }

    public void setIdPadre(Funcionalidad idPadre) {
        this.idPadre = idPadre;
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
        if (!(object instanceof Funcionalidad)) {
            return false;
        }
        Funcionalidad other = (Funcionalidad) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.aprodher.entity.Funcionalidad[ id=" + id + " ]";
    }
    
}
