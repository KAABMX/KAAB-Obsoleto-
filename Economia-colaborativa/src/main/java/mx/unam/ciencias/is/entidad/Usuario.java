/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.unam.ciencias.is.entidad;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author luis
 */
@Entity
@Table(name = "Usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")
    , @NamedQuery(name = "Usuario.findByPkIdUsuario", query = "SELECT u FROM Usuario u WHERE u.pkIdUsuario = :pkIdUsuario")
    , @NamedQuery(name = "Usuario.findByNombre", query = "SELECT u FROM Usuario u WHERE u.nombre = :nombre")
    , @NamedQuery(name = "Usuario.findByApellidoPaterno", query = "SELECT u FROM Usuario u WHERE u.apellidoPaterno = :apellidoPaterno")
    , @NamedQuery(name = "Usuario.findByApellidoMaterno", query = "SELECT u FROM Usuario u WHERE u.apellidoMaterno = :apellidoMaterno")
    , @NamedQuery(name = "Usuario.findByCorreo", query = "SELECT u FROM Usuario u WHERE u.correo = :correo")
    , @NamedQuery(name = "Usuario.findByTelefono", query = "SELECT u FROM Usuario u WHERE u.telefono = :telefono")
    , @NamedQuery(name = "Usuario.findBySexo", query = "SELECT u FROM Usuario u WHERE u.sexo = :sexo")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_usuario")
    private Integer pkIdUsuario;
    @Size(max = 90)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 120)
    @Column(name = "apellido_paterno")
    private String apellidoPaterno;
    @Size(max = 120)
    @Column(name = "apellido_materno")
    private String apellidoMaterno;
    @Size(max = 90)
    @Column(name = "correo")
    private String correo;
    @Lob
    @Column(name = "foto")
    private byte[] foto;
    @Size(max = 50)
    @Column(name = "telefono")
    private String telefono;
    @Size(max = 90)
    @Column(name = "sexo")
    private String sexo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdUsuario")
    private List<Profesor> profesorList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdUsuario")
    private List<Alumno> alumnoList;

    public Usuario() {
    }

    public Usuario(Integer pkIdUsuario) {
        this.pkIdUsuario = pkIdUsuario;
    }

    public Integer getPkIdUsuario() {
        return pkIdUsuario;
    }

    public void setPkIdUsuario(Integer pkIdUsuario) {
        this.pkIdUsuario = pkIdUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    @XmlTransient
    public List<Profesor> getProfesorList() {
        return profesorList;
    }

    public void setProfesorList(List<Profesor> profesorList) {
        this.profesorList = profesorList;
    }

    @XmlTransient
    public List<Alumno> getAlumnoList() {
        return alumnoList;
    }

    public void setAlumnoList(List<Alumno> alumnoList) {
        this.alumnoList = alumnoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdUsuario != null ? pkIdUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.pkIdUsuario == null && other.pkIdUsuario != null) || (this.pkIdUsuario != null && !this.pkIdUsuario.equals(other.pkIdUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.unam.ciencias.is.entidad.Usuario[ pkIdUsuario=" + pkIdUsuario + " ]";
    }
    
}
