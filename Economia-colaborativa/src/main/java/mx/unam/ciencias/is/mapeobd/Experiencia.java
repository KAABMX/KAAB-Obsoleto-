/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.unam.ciencias.is.mapeobd;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author luis
 */
@Entity
@Table(name = "Experiencia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Experiencia.findAll", query = "SELECT e FROM Experiencia e")
    , @NamedQuery(name = "Experiencia.findByPkIdExperiencia", query = "SELECT e FROM Experiencia e WHERE e.pkIdExperiencia = :pkIdExperiencia")
    , @NamedQuery(name = "Experiencia.findByFechaInicio", query = "SELECT e FROM Experiencia e WHERE e.fechaInicio = :fechaInicio")
    , @NamedQuery(name = "Experiencia.findByFechaFin", query = "SELECT e FROM Experiencia e WHERE e.fechaFin = :fechaFin")
    , @NamedQuery(name = "Experiencia.findByEmpresa", query = "SELECT e FROM Experiencia e WHERE e.empresa = :empresa")
    , @NamedQuery(name = "Experiencia.findByFuncionTrabajo", query = "SELECT e FROM Experiencia e WHERE e.funcionTrabajo = :funcionTrabajo")
    , @NamedQuery(name = "Experiencia.findByTareaTrabajo", query = "SELECT e FROM Experiencia e WHERE e.tareaTrabajo = :tareaTrabajo")})
public class Experiencia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_experiencia")
    private Integer pkIdExperiencia;
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Column(name = "fecha_fin")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    @Size(max = 90)
    @Column(name = "empresa")
    private String empresa;
    @Size(max = 200)
    @Column(name = "funcion_trabajo")
    private String funcionTrabajo;
    @Size(max = 200)
    @Column(name = "tarea_trabajo")
    private String tareaTrabajo;
    @JoinColumn(name = "fk_id_cv", referencedColumnName = "pk_id_cv")
    @ManyToOne
    private Curriculum fkIdCv;

    public Experiencia() {
    }

    public Experiencia(Integer pkIdExperiencia) {
        this.pkIdExperiencia = pkIdExperiencia;
    }

    public Integer getPkIdExperiencia() {
        return pkIdExperiencia;
    }

    public void setPkIdExperiencia(Integer pkIdExperiencia) {
        this.pkIdExperiencia = pkIdExperiencia;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getFuncionTrabajo() {
        return funcionTrabajo;
    }

    public void setFuncionTrabajo(String funcionTrabajo) {
        this.funcionTrabajo = funcionTrabajo;
    }

    public String getTareaTrabajo() {
        return tareaTrabajo;
    }

    public void setTareaTrabajo(String tareaTrabajo) {
        this.tareaTrabajo = tareaTrabajo;
    }

    public Curriculum getFkIdCv() {
        return fkIdCv;
    }

    public void setFkIdCv(Curriculum fkIdCv) {
        this.fkIdCv = fkIdCv;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkIdExperiencia != null ? pkIdExperiencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Experiencia)) {
            return false;
        }
        Experiencia other = (Experiencia) object;
        if ((this.pkIdExperiencia == null && other.pkIdExperiencia != null) || (this.pkIdExperiencia != null && !this.pkIdExperiencia.equals(other.pkIdExperiencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.unam.ciencias.is.entidad.Experiencia[ pkIdExperiencia=" + pkIdExperiencia + " ]";
    }
    
}
