/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.unam.ciencias.is.entidad;

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
@Table(name = "Complementarios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Complementario.findAll", query = "SELECT c FROM Complementario c")
    , @NamedQuery(name = "Complementario.findByPkIdComplementarios", query = "SELECT c FROM Complementario c WHERE c.pkIdComplementarios = :pkIdComplementarios")
    , @NamedQuery(name = "Complementario.findByEstudio", query = "SELECT c FROM Complementario c WHERE c.estudio = :estudio")
    , @NamedQuery(name = "Complementario.findByFechaInicio", query = "SELECT c FROM Complementario c WHERE c.fechaInicio = :fechaInicio")
    , @NamedQuery(name = "Complementario.findByFechaFin", query = "SELECT c FROM Complementario c WHERE c.fechaFin = :fechaFin")
    , @NamedQuery(name = "Complementario.findByCentro", query = "SELECT c FROM Complementario c WHERE c.centro = :centro")
    , @NamedQuery(name = "Complementario.findByLugar", query = "SELECT c FROM Complementario c WHERE c.lugar = :lugar")})
public class Complementario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_complementarios")
    private Integer pkIdComplementarios;
    @Size(max = 90)
    @Column(name = "estudio")
    private String estudio;
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Column(name = "fecha_fin")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    @Size(max = 90)
    @Column(name = "centro")
    private String centro;
    @Size(max = 90)
    @Column(name = "lugar")
    private String lugar;
    @JoinColumn(name = "fk_id_cv", referencedColumnName = "pk_id_cv")
    @ManyToOne
    private Curriculum fkIdCv;

    public Complementario() {
    }

    public Complementario(Integer pkIdComplementarios) {
        this.pkIdComplementarios = pkIdComplementarios;
    }

    public Integer getPkIdComplementarios() {
        return pkIdComplementarios;
    }

    public void setPkIdComplementarios(Integer pkIdComplementarios) {
        this.pkIdComplementarios = pkIdComplementarios;
    }

    public String getEstudio() {
        return estudio;
    }

    public void setEstudio(String estudio) {
        this.estudio = estudio;
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

    public String getCentro() {
        return centro;
    }

    public void setCentro(String centro) {
        this.centro = centro;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
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
        hash += (pkIdComplementarios != null ? pkIdComplementarios.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Complementario)) {
            return false;
        }
        Complementario other = (Complementario) object;
        if ((this.pkIdComplementarios == null && other.pkIdComplementarios != null) || (this.pkIdComplementarios != null && !this.pkIdComplementarios.equals(other.pkIdComplementarios))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.unam.ciencias.is.entidad.Complementario[ pkIdComplementarios=" + pkIdComplementarios + " ]";
    }
    
}
