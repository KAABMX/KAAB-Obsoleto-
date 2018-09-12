/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.unam.ciencias.is.entidad;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Moctezuma19
 */
@Entity
@Table(name = "alumno")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Alumno_1.findAll", query = "SELECT a FROM Alumno_1 a")
    , @NamedQuery(name = "Alumno_1.findByPkIdAlumno", query = "SELECT a FROM Alumno_1 a WHERE a.pkIdAlumno = :pkIdAlumno")
    , @NamedQuery(name = "Alumno_1.findByUltimoNivelEducativo", query = "SELECT a FROM Alumno_1 a WHERE a.ultimoNivelEducativo = :ultimoNivelEducativo")})
public class Alumno implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_alumno")
    private Integer pkIdAlumno;
    @Size(max = 120)
    @Column(name = "ultimo_nivel_educativo")
    private String ultimoNivelEducativo;

    public Alumno() {
    }

    public Alumno(Integer pkIdAlumno) {
        this.pkIdAlumno = pkIdAlumno;
    }

    public Integer getPkIdAlumno() {
        return pkIdAlumno;
    }

    public void setPkIdAlumno(Integer pkIdAlumno) {
        this.pkIdAlumno = pkIdAlumno;
    }

    public String getUltimoNivelEducativo() {
        return ultimoNivelEducativo;
    }

    public void setUltimoNivelEducativo(String ultimoNivelEducativo) {
        this.ultimoNivelEducativo = ultimoNivelEducativo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdAlumno != null ? pkIdAlumno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Alumno)) {
            return false;
        }
        Alumno other = (Alumno) object;
        if ((this.pkIdAlumno == null && other.pkIdAlumno != null) || (this.pkIdAlumno != null && !this.pkIdAlumno.equals(other.pkIdAlumno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.unam.ciencias.is.entidad.Alumno_1[ pkIdAlumno=" + pkIdAlumno + " ]";
    }
    
}
