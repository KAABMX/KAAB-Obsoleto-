/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.unam.ciencias.is.mapeobd;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author luis
 */
@Entity
@Table(name = "Interes_academico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InteresAcademico.findAll", query = "SELECT i FROM InteresAcademico i")
    , @NamedQuery(name = "InteresAcademico.findByPkIdInteres", query = "SELECT i FROM InteresAcademico i WHERE i.pkIdInteres = :pkIdInteres")
    , @NamedQuery(name = "InteresAcademico.findByInteres", query = "SELECT i FROM InteresAcademico i WHERE i.interes = :interes")})
public class InteresAcademico implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_interes")
    private Integer pkIdInteres;
    @Size(max = 90)
    @Column(name = "interes")
    private String interes;
    @JoinColumn(name = "fk_id_alumno", referencedColumnName = "pk_id_alumno")
    @ManyToOne
    private Alumno fkIdAlumno;

    public InteresAcademico() {
    }

    public InteresAcademico(Integer pkIdInteres) {
        this.pkIdInteres = pkIdInteres;
    }

    public Integer getPkIdInteres() {
        return pkIdInteres;
    }

    public void setPkIdInteres(Integer pkIdInteres) {
        this.pkIdInteres = pkIdInteres;
    }

    public String getInteres() {
        return interes;
    }

    public void setInteres(String interes) {
        this.interes = interes;
    }

    public Alumno getFkIdAlumno() {
        return fkIdAlumno;
    }

    public void setFkIdAlumno(Alumno fkIdAlumno) {
        this.fkIdAlumno = fkIdAlumno;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdInteres != null ? pkIdInteres.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InteresAcademico)) {
            return false;
        }
        InteresAcademico other = (InteresAcademico) object;
        if ((this.pkIdInteres == null && other.pkIdInteres != null) || (this.pkIdInteres != null && !this.pkIdInteres.equals(other.pkIdInteres))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.unam.ciencias.is.entidad.InteresAcademico[ pkIdInteres=" + pkIdInteres + " ]";
    }
    
}
