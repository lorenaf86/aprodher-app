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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")
})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre")
    private String nombre;
    
    @Column(name = "apellido")
    private String apellido;

    @Column(name = "academia")
    private String academiaNombre;

    @Column(name = "telefono")
    private String telefono;

    @Size(max = 2)
    @Column(name = "estado")
    private String estado;

    @Column(name = "username")
    private String username;

    @Column(name = "contrasena")
    private String contrasena;

    @Column(name = "mail")
    private String mail;

    @Column(name = "bloqueado")
    private Boolean bloqueado;

    @Column(name = "activo")
    private Boolean activo;

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
    
    @JoinColumn(name = "id_academia", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Academia academia;
   
    @Column(name = "contador_accesos")
    private Integer contador;

    public Usuario() {
    }

    public Usuario(Integer id) {
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

    public Boolean getBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(Boolean bloqueado) {
        this.bloqueado = bloqueado;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getAcademiaNombre() {
        return academiaNombre;
    }

    public void setAcademiaNombre(String academiaNombre) {
        this.academiaNombre = academiaNombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }


    public Academia getAcademia() {
        return academia;
    }

    public void setAcademia(Academia academia) {
        this.academia = academia;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Boolean isActivo() {
        return activo;
    }

    public Integer getContador() {
        return contador;
    }

    public void setContador(Integer contador) {
        this.contador = contador;
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
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.aprodher.aprodherweb.Usuario[ id=" + id + " ]";
    }

}
