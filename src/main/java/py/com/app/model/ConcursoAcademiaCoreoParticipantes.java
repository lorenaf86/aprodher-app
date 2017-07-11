/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.app.model;

import java.io.Serializable;
import javax.persistence.Basic;
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
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lorena Franco
 */
@Entity
@Table(name = "concurso_academia_coreo_participantes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ConcursoAcademiaCoreoParticipantes.findAll", query = "SELECT c FROM ConcursoAcademiaCoreoParticipantes c")})
public class ConcursoAcademiaCoreoParticipantes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "id_academia_concurso_coreo", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ConcursoAcademiaCoreo idAcademiaConcursoCoreo;
    @JoinColumn(name = "id_persona", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Persona idPersona;

    public ConcursoAcademiaCoreoParticipantes() {
    }

    public ConcursoAcademiaCoreoParticipantes(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ConcursoAcademiaCoreo getIdAcademiaConcursoCoreo() {
        return idAcademiaConcursoCoreo;
    }

    public void setIdAcademiaConcursoCoreo(ConcursoAcademiaCoreo idAcademiaConcursoCoreo) {
        this.idAcademiaConcursoCoreo = idAcademiaConcursoCoreo;
    }

    public Persona getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Persona idPersona) {
        this.idPersona = idPersona;
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
        if (!(object instanceof ConcursoAcademiaCoreoParticipantes)) {
            return false;
        }
        ConcursoAcademiaCoreoParticipantes other = (ConcursoAcademiaCoreoParticipantes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.aprodher.entity.ConcursoAcademiaCoreoParticipantes[ id=" + id + " ]";
    }
    
}
